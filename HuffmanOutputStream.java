
/**
 * @author Maxx Persin
 *
 */
import java.io.*;

public class HuffmanOutputStream {
	// add additional private variables as needed
	// do not modify the public methods signatures or add public methods
	DataOutputStream d;
	private int count = 0;
	private int bytes = 0;

	public HuffmanOutputStream(String filename, String tree, int totalChars) {
		try {
			d = new DataOutputStream(new FileOutputStream(filename));
			d.writeUTF(tree);
			d.writeInt(totalChars);
		} catch (IOException e) {
		}
		// add other initialization statements as needed
	}

	public void writeBit(char bit) {
		// PRE:bit == '0' || bit == '1'
		if (bit == '1') {
			bytes += Math.pow(2, (7 - count));
		}
		count++;

		if (count == 8) {
			try {
				d.writeByte(bytes);
			} catch (IOException e) {
			}
			count = 0;
			bytes = 0;
		}
	}

	public void close() {
		// write final byte (if needed)
		// close the DataOutputStream
		try {
			if (count < 8) {
				d.writeByte(bytes);
			}
			d.flush();
			d.close();
		} catch (IOException e) {
		}
	}

}
