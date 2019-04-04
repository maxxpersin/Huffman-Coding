
/**
 * @author Maxx Persin
 *
 */
import java.io.*;
import java.util.*;

public class HuffmanDecode {
	private HuffmanTree tree;
	private int totalChars;
	private int charsDecoded = 1;

	public HuffmanDecode(String in, String out) {
		// implements the Huffman Decode Algorithm
		// Add private methods and instance variables as needed
		HuffmanInputStream hIn = new HuffmanInputStream(in);
		tree = new HuffmanTree(hIn.getTree(), (char) 128);
		totalChars = hIn.getTotalChars();
		try {
			FileWriter fileWrite = new FileWriter(out);
			BufferedWriter buffWrite = new BufferedWriter(fileWrite);
			PrintWriter pWrite = new PrintWriter(buffWrite);
			int current = 0;
			tree.moveToRoot();
			while (charsDecoded != totalChars) {
				current = hIn.readBit();
				if (current == 1) {
					tree.moveToRight();
					if (tree.atLeaf()) {
						pWrite.write(tree.current());
						tree.moveToRoot();
						charsDecoded++;
					}
				} else if (current == 0) {
					tree.moveToLeft();
					if (tree.atLeaf()) {
						pWrite.write(tree.current());
						charsDecoded++;
						tree.moveToRoot();
					}
				}
			}
			pWrite.flush();
			pWrite.close();
		} catch (IOException e) {

		}
	}

	public static void main(String args[]) {
		// args[0] is the name of a input file (a file created by Huffman Encode)
		// args[1] is the name of the output file for the uncompressed file
		new HuffmanDecode(args[0], args[1]);
		// do not add anything here
	}
}
