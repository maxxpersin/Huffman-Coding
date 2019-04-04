
/**
 * @author Maxx Persin
 *
 */
import java.io.*;
import java.util.Stack;

public class HuffmanInputStream {
	// add additional private variables and methods as needed
	// DO NOT modify the public method signatures or add public methods
	private String tree;
	private int totalChars;
	private DataInputStream d;
	private Stack<Integer> stack = new Stack<Integer>();
	private int i = 0;
	private int count = 8;

	public HuffmanInputStream(String filename) {
		try {
			d = new DataInputStream(new FileInputStream(filename));
			tree = d.readUTF();
			totalChars = d.readInt();

		} catch (IOException e) {
		}
		// add other initialization statements as needed

	}

	public int readBit() {
		// returns the next bit is the file
		// the value returned will be either a 0 or a 1
		if (count == 8) {
			try {
				i = d.readUnsignedByte();
				for (int j = 0; j < 8; j++) {
					stack.push((i % 2));
					i = i / 2;
				}
				count = 0;
			} catch (IOException e) {
			}

		}
		int fin = 0;
		if (!stack.isEmpty()) {
			fin = stack.pop();
		}
		count++;
		return fin;
	}

	public String getTree() {
		// return the tree representation read from the file
		HuffmanTree treeFromFile = new HuffmanTree(tree, (char) 128);
		return treeFromFile.toString();
	}

	public int getTotalChars() {
		// return the character count read from the file
		return totalChars;
	}
}
