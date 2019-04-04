
/**
 * @author Maxx Persin
 *
 */
import java.io.*;
import java.util.*;

public class HuffmanEncode {
	private BinaryHeap input;
	private int charNum;
	private int[] priority;
	private HuffmanTree[] roots;
	private int curPos;

	public HuffmanEncode(String in, String out) {
		// Implements the Huffman encoding algorithm
		// Add private methods and instance variables as needed
		try {
			FileReader fin = new FileReader(in);
			BufferedReader bin = new BufferedReader(fin);
			charNum = 0;
			for (int read = bin.read(); read != -1; read = bin.read()) {
				charNum++;
			}
			priority = new int[charNum];
			roots = new HuffmanTree[charNum];
			bin.close();
			FileReader fin2 = new FileReader(in);
			BufferedReader bin2 = new BufferedReader(fin2);
			curPos = 0;
			for (int read = bin2.read(); read != -1; read = bin2.read()) {
				HuffmanTree newData = new HuffmanTree((char) read);
				if (!inArray(newData.data())) {
					priority[curPos] = 1;
					roots[curPos] = newData;
					curPos++;
				} else {
					increment(newData.data());
				}
			}
			input = new BinaryHeap(curPos);
			for (int i = 0; i < curPos; i++) {
				input.insert(priority[i], roots[i]);
			}
			bin2.close();
		} catch (IOException e) {

		}
		while (input.size != 1) {
			this.removeMin();
		}
		Iterator<String> iter = input.trees[1].iterator();
		LinkedList<String> characterCodes = new LinkedList<String>();
		while (iter.hasNext()) {
			characterCodes.add((String) iter.next());
		}

		HuffmanOutputStream hOut = new HuffmanOutputStream(out, input.toString(), charNum);
		try {
			FileReader fin3 = new FileReader(in);
			BufferedReader bin3 = new BufferedReader(fin3);
			for (int read = bin3.read(); read != -1; read = bin3.read()) {
				for (int i = 0; i < characterCodes.size() - 1; i++) {
					if (characterCodes.get(i).charAt(0) == (char) read) {
						for (int j = 2; j < characterCodes.get(i).length(); j++) {
							hOut.writeBit(characterCodes.get(i).charAt(j));

						}
					}
				}
			}
			bin3.close();
			hOut.close();

		} catch (IOException e) {

		}
	}

	// Shrinks the heap, reinserts the created node
	private void removeMin() {
		HuffmanTree left = input.getMinTree();
		int leftPriority = input.getMinPriority();
		input.removeMin();
		HuffmanTree right = input.getMinTree();
		int rightPriority = input.getMinPriority();
		input.removeMin();

		HuffmanTree reinsert = new HuffmanTree(left, right, (char) 128);
		input.insert(leftPriority + rightPriority, reinsert);
	}

	// Verifies a character is already being represented
	private boolean inArray(char c) {
		for (int i = 0; i < curPos; i++) {
			if (roots[i].data() == c) {
				return true;
			}
		}
		return false;
	}

	//Increments the priority of a node
	private void increment(char c) {
		for (int i = 0; i < curPos; i++) {
			if (roots[i].data() == c) {
				priority[i]++;
			}
		}
	}

	public static void main(String args[]) {
		// args[0] is the name of the file whose contents should be compressed
		// args[1] is the name of the output file that will hold the compressed
		// content of the input file
		new HuffmanEncode(args[0], args[1]);
		// new HuffmanEncode(args[0], args[1]);
		// do not add anything here
	}
}
