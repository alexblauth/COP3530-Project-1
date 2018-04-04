/**
 * This class creates the node for the linked list called 
 * entry. The constructor takes three parameters row, col,
 * and data, and it initializes getter and setter methods.
 */

public class Entry {
	private int row;
	private int col;
	private int data;

	public Entry(int row, int col, int data) {
		this.row = row;
		this.col = col;
		this.data = data;
	}

	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}

	public int getData() {
		return this.data;
	}
}

