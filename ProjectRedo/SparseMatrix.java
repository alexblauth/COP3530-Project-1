import java.util.*;

/** Alexander Blauth
 *  COP3530 Project 1 Redo
 *  4/6/18
 * 
 */
public class SparseMatrix implements SparseInterface {
    private int numRows;
    private int numCols;
    public LinkedList<Entry> myMatrix;
    
    /*
     * Default constructor which creates a 5 x 5 matrix on
     * startup.
     */
    public SparseMatrix() {
        this.numRows = 5;
        this.numCols = 5;
        myMatrix = new LinkedList<Entry>();
    }
    
    /*
     * Constructor which allows the user to set the matrix
     * dimensions.
     * 
     * @param size sets length x width of square matrix
     */
    public SparseMatrix(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        myMatrix = new LinkedList<Entry>();
    }

    public int getNumRows() {
        return numRows;
    }
    
    public int getNumCols() {
        return this.numCols;
    }
    
    /*
     * Static method to print out the option menu.
     */
    public static void printMenu() {
        System.out.print("\n[1] Set the size of the matrix\n" +
         "[2] Add a matrix entry\n" +
         "[3] Remove a matrix entry\n" +
         "[6] Show specific entry\n" + 
         "[7] Show the matrix\n" +
         "[8] Clear matrix\n" +
         "[Q] Quit the program\n" +
         "Please make a selection: ");
    }
    
    /*
     * Main Method
     */
    public static void main(String args[]) {
        Scanner userInput = new Scanner(System.in);
        int row, col, determinant, data;
        
        SparseInterface userMatrix = new SparseMatrix();
        
        printMenu();
        String userChoice = " ";
        
        while(!userChoice.equalsIgnoreCase("Q")) {
            userChoice = userInput.next();
            
            if(userChoice.equalsIgnoreCase("1")) {
                System.out.print("How many rows and columns would you like the matrix to be? ");
                row = userInput.nextInt();
                col = userInput.nextInt();
                userMatrix.setSize(row, col);        
            }
            else if(userChoice.equalsIgnoreCase("2")) {
                System.out.print("In which row would you like to insert the entry? ");
                row = userInput.nextInt();
                System.out.print("In which column would you like to insert the entry? ");
                col = userInput.nextInt();
                System.out.print("What number would you like to add to the matrix? ");
                data = userInput.nextInt();
                userMatrix.addElement(row, col, data);
            }
            else if(userChoice.equalsIgnoreCase("3")) {
                System.out.print("Which row would you like to remove an entry from? ");
                row = userInput.nextInt();
                System.out.print("Which column would you like to remove an entry from? ");
                col = userInput.nextInt();
                userMatrix.removeElement(row, col);
            }
            else if(userChoice.equalsIgnoreCase("6")) {
                System.out.print("Which row would you like to get an entry from? ");
                row = userInput.nextInt();
                System.out.print("Which column would you like to get an entry from? ");
                col = userInput.nextInt();
                System.out.println(userMatrix.getElement(row, col));
            }
            else if(userChoice.equalsIgnoreCase("7")) {
                System.out.println(userMatrix.toString());
            }
            else if(userChoice.equalsIgnoreCase("8")) {
                userMatrix.clear();
                System.out.println("The matrix now contains no values.");
            }
            else if(userChoice.equalsIgnoreCase("Q")) {
                System.exit(10);
            }
            else {
                System.out.println("Error: Please make a selection from the list");
            }
            printMenu();
        } 
    } 
    
    /*
     *  Should clear the matrix of all entries (make all entries 0)
     */
    public void clear() {
        myMatrix.clear();
    }

    /*
     *  Sets maximum size of the matrix.  Number of rows. Itshould also clear the matrix (make all elements 0)
     */
    public void setSize(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        myMatrix = new LinkedList<Entry>();
    }

    /*
     *  Adds an element to the row and column passed as arguments (overwrites if element is already present at that position).
     *  Throws an error if row/column combination is out of bounds.
     */
    public void addElement(int row, int col, int data) {
        if(data == 0) {
            removeElement(row, col);
            //System.out.println("Cannot add 0 to sparse matrix");
            return;
        }
        
        if (row >= this.numRows || col >= this.numCols || row < 0 || col < 0) {
            System.out.println("Index does not exist, matrix is unchanged.");
            return;
        }
        
        int i = 0;
        for (Entry c: myMatrix) {
            if (row > c.getRow()) {
                i++;
            }
            
            if (row == c.getRow()) {
                if (col > c.getCol()) {
                    i++;
                }
            }
        }
        
        if(getElement(row, col) != -1) {
            removeElement(row, col);
        }
        
        Entry entry = new Entry(row, col, data);  // Creates new node
        myMatrix.add(i, entry); //Adds node to linked list
    }

    /*
        Remove (make 0) the element at the specified row and column.
        Throws an error if row/column combination is out of bounds.
     */
    public void removeElement(int row, int col) {
        if (row >= this.numRows || col >= this.numCols || row < 0 || col < 0) {
            System.out.println("Index does not exist, matrix is unchanged.");
            return;
        }
        
        int i = 0;
        for (Entry c: myMatrix) {
            if (row > c.getRow()) {
                i++;
            }
            
            if (row == c.getRow()) {
                if (col > c.getCol()) {
                    i++;
                }
            }
        }
        
        for (Entry c : myMatrix) {
            if (c.getRow() == row && c.getCol() == col) {
                myMatrix.remove(i);
            }
        } 
    } 

    /*
     *  Return the element at the specified row and column
     *  Throws an error if row/column combination is out of bounds.
     */
    public int getElement(int row, int col) {
        if (row >= this.numRows || col >= this.numCols || row < 0 || col < 0) {
            System.out.println("Index does not exist.");
            return -1;
        }
        
        for (Entry c : myMatrix) {
            if (c.getRow() == row && c.getCol() == col) {
                return c.getData();
            }
        }
        return 0;
    } 

    /*
    Should return the nonzero elements of your sparse matrix as a string.
    The String should be k lines, where k is the number of nonzero elements.
    Each line should be in the format "row column data" where row and column are the "coordinate" of the data and all are separated by spaces.
    An empty matrix should return an empty string.
    The print should be from left to right and from top to bottom (like reading a book) i.e. the matrix

                                                     3 0 1
                                                     0 2 0
                                                     0 0 4

                                                 Should print as:
                                                     0 0 3
                                                     0 2 1
                                                     1 1 2
                                                     2 2 4

     */
    public String toString() {
        String matrixAsString = "";
        for (Entry c : myMatrix) {
            matrixAsString += (c.getRow() + " " + c.getCol() + " " + c.getData() + "\n");
        }
        
        return matrixAsString;
    }

    /*takes another matrix as input and returns the sum of the two matrices
     *return NULL if sizes incompatible
     */
    public SparseInterface addMatrices(SparseInterface matrixToAdd) {
        if((matrixToAdd.getNumRows() != numRows) || (matrixToAdd.getNumCols() != numCols)) {
            return null;
        }
        SparseInterface addedMatrices = new SparseMatrix(numRows, numCols);
        
        for (Entry c : myMatrix) {
            int row2 = c.getRow();
            int col2 = c.getCol();
            
            int dataToAdd = matrixToAdd.getElement(row2,col2);
            addedMatrices.addElement(row2,col2,dataToAdd + c.getData());
        }
        return addedMatrices;        
    }
    
    /*
     * Takes another matrix as input and returns the product of the two matrices
     * return NULL if sizes incompatible
     */
    public SparseInterface multiplyMatrices(SparseInterface matrixToMultiply) {
        if(matrixToMultiply.getNumRows() != numCols) {
            return null;
        }
        SparseInterface multipliedMatrix = new SparseMatrix(numRows, matrixToMultiply.getNumCols());
        
        int[][] arrA = new int[numRows][numCols];
        int[][] arrB = new int[matrixToMultiply.getNumRows()][matrixToMultiply.getNumCols()];
        
        for(int c = 0; c < numRows; c++) {
            for(int i = 0; i < numCols; i++) {
                arrA[c][i] = 0;
            }
        }
        
        for(Entry x : myMatrix) {
            int col = x.getCol();
            int row = x.getRow();
            int data = x.getData();
            
            arrA[row][col] = data;
        }
        
        for(int c = 0; c < matrixToMultiply.getNumRows(); c++) {
            for(int i = 0; i < matrixToMultiply.getNumCols(); i++) {
                if(matrixToMultiply.getElement(c, i) != 0) {
                    arrB[c][i] = matrixToMultiply.getElement(c,i); 
                }
                else {
                    arrB[c][i] = 0; 
                }
            }
        }
        
        int aRows = arrA.length;
        int aCols = arrA[0].length;
        int bRows = arrB.length;
        int bCols = arrB[0].length;

        int[][] arrC = new int[aRows][bCols];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {
                arrC[i][j] = 0;
            }
        }

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {
                for (int k = 0; k < aCols; k++) {
                    arrC[i][j] += arrA[i][k] * arrB[k][j];
                }
            }
        }
        
        for(int c = 0; c < arrC.length; c++) {
            for(int i = 0; i < arrC[c].length; i++) {
                if(arrC[c][i] != 0) {
                    multipliedMatrix.addElement(c,i,arrC[c][i]);
                }
            }
        }
        return multipliedMatrix;
    }
}