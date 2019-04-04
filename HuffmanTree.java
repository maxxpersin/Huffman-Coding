
/**
 * @author Maxx Persin
 *
 */
import java.util.*;

public class HuffmanTree {
	// DO NOT include the frequency or priority in the tree
	private class Node {
		private Node left;
		private char data;
		private Node right;
		private Node parent;

		private Node(Node L, char d, Node R, Node P) {
			left = L;
			data = d;
			right = R;
			parent = P;
		}
	}

	private Node root;
	private Node current;

	// this value is changed by the move methods
	public HuffmanTree() {
		root = null;
		current = null;
	}

	public HuffmanTree(char d) {
		// makes a single node tree
		root = new Node(null, d, null, null);
	}

	public HuffmanTree(String t, char nonLeaf) {
		// Assumes t represents a post order representation of the tree as discussed
		// in class
		// nonLeaf is the char value of the data in the non-leaf nodes
		// in class we used (char) 128 for the non-leaf value
		Stack<HuffmanTree> stack = new Stack<HuffmanTree>();
		for (int i = 0; i < t.length(); i++) {
			if (t.charAt(i) != nonLeaf) {
				HuffmanTree data = new HuffmanTree(t.charAt(i));
				stack.push(data);
			} else if (t.charAt(i) == nonLeaf) {
				HuffmanTree right = stack.pop();
				HuffmanTree left = stack.pop();
				HuffmanTree data = new HuffmanTree(left, right, t.charAt(i));
				stack.push(data);
			}
		}
		root = stack.pop().root;
	}

	public HuffmanTree(HuffmanTree b1, HuffmanTree b2, char d) {
		// makes a new tree where b1 is the left subtree and b2 is the right subtree
		// d is the data in the root
		root = new Node(b1.root, d, b2.root, null);
		if (b1.root != null) {
			b1.root.parent = root;
		}
		if (b2.root != null) {
			b2.root.parent = root;
		}
	}

	public char data() {
		return root.data;
	}

	// use the move methods to traverse the tree
	// the move methods change the value of current
	// use these in the decoding process
	public void moveToRoot() {
		// change current to reference the root of the tree
		current = root;
	}

	public void moveToLeft() {
		// PRE: the current node is not a leaf
		// change current to reference the left child of the current node
		// System.out.println("moved left " + current.left.data);
		current = current.left;
	}

	public void moveToRight() {
		// PRE: the current node is not a leaf
		// change current to reference the right child of the current node
		// System.out.println("moved right " + current.right.data);
		current = current.right;
	}

	public void moveToParent() {
		// PRE: the current node is not the root
		// change current to reference the parent of the current node
		current = current.parent;
	}

	public boolean atRoot() {
		// returns true if the current node is the root otherwise returns false
		return current == root;
	}

	public boolean atLeaf() {
		// returns true if current references a leaf other wise returns false
		return (!hasLeft() && !hasRight());
	}

	private boolean hasLeft() {
		return current.left != null;
	}

	private boolean hasRight() {
		return current.right != null;
	}

	public char current() {
		// returns the data value in the node referenced by current
		return current.data;
	}

	public class PathIterator implements Iterator<String> {
		String path = "";
		boolean firstRun = true;

		public PathIterator() {
			moveToRoot();
			while (!atLeaf()) {
				moveToLeft();
				path += "0";
			}
		}

		public boolean hasNext() {
			return current != root;
		}

		public String next() {
			if (firstRun) {
				firstRun = false;
				return current.data + " " + path;
			}
			Node prev = current;
			moveToParent();
			if (atRoot()) {
				path = "";
			} else {
				path = path.substring(0, path.length() - 1);
			}
			if (current.right == prev) {
				while (current.right == prev) {
					prev = current;
					moveToParent();
					if (path.length() > 1) {
						path = path.substring(0, path.length() - 1);
					}
					if (current == null) {
						moveToRoot();
						return "";
					}
				}
				if (atRoot()) {
					path = "";
				}
				moveToRight();
				path += "1";
				while (!atLeaf()) {
					moveToLeft();
					path += "0";
				}
			} else if (current.left == prev) {
				if (atRoot()) {
					path = "";
				}
				moveToRight();
				path += "1";
				while (!atLeaf()) {
					moveToLeft();
					path += "0";
				}
			}
			return current.data + " " + path;
		}

		public void remove() {
			// optional method not implemented
		}

	}

	public Iterator<String> iterator() {
		// return a new path iterator object
		return new PathIterator();
	}

	public String toString() {
		// returns a string representation of the tree using the postorder format
		// discussed in class
		return toString(root);
	}

	public String toString(Node r) {
		if (r != null) {
			if (r.left != null && r.right != null) { // Both nodes are populated
				return toString(r.left) + toString(r.right) + r.data;
			} else if (r.left != null && r.right == null) { // left node is populated
				return toString(r.left) + r.data;
			} else if (r.left == null && r.right != null) { // right node is populated
				return toString(r.right) + r.data;
			} else { // Neither nodes are populated
				return r.data + "";
			}
		} else {
			return "!"; // Empty tree
		}
	}
}
