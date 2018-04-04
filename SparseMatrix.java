import java.util.*;
/** Alexander Blauth
 *  COP3530 Project 1
 *  3/30/18
 * 
 */
public class SparseMatrix implements SparseInterface {
    private int size;
    private LinkedList<Entry> myMatrix;

    /*
     * Default constructor which creates a 5 x 5 matrix on
     * startup.
     */
    public SparseMatrix() {
        this.size = 5;
        myMatrix = new LinkedList<Entry>();
    }

    /*
     * Constructor which allows the user to set the matrix
     * dimensions.
     * 
     * @param size sets length x width of square matrix
     */
    public SparseMatrix(int size) {
        this.size = size;
        myMatrix = new LinkedList<Entry>();
    }

    /*
     * Static method to print out the option menu.
     */
    public static void printMenu() {
        System.out.print("\n[1] Set the size of the matrix\n" +
         "[2] Add a matrix entry\n" +
         "[3] Remove a matrix entry\n" +
         "[4] Compute deteminant\n" +
         "[5] Show matrix size\n" +
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
        int row, col, determinant;
        int size, data;
        
        SparseInterface userMatrix = new SparseMatrix();
        
        printMenu();
        String userChoice = " ";
        
        while(!userChoice.equalsIgnoreCase("Q")) {
            userChoice = userInput.next();
            
            if(userChoice.equalsIgnoreCase("1")) {
                System.out.print("What size (N x N) would you like the matrix to be? ");
                size = userInput.nextInt();
            userMatrix.setSize(size);        
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
            else if(userChoice.equalsIgnoreCase("4")) {
                determinant = userMatrix.determinant();
            System.out.println(determinant);
            }
            else if(userChoice.equalsIgnoreCase("5")) {
                System.out.println("The size of the matrix is " + userMatrix.getSize() );
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
    public void setSize(int size) {
        this.size = size;
        myMatrix = new LinkedList<Entry>();
    }

    /*
     *  Adds an element to the row and column passed as arguments (overwrites if element is already present at that position).
     *  Throws an error if row/column combination is out of bounds.
     */
    public void addElement(int row, int col, int data) {
        if(data == 0) {
            System.out.println("Cannot add 0 to sparse matrix");
            return;
        }
        
        if (row >= this.size || col >= this.size || row < 0 || col < 0) {
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
        
        Entry entry = new Entry(row, col, data);
        myMatrix.add(i, entry);
    }

    /*
        Remove (make 0) the element at the specified row and column.
        Throws an error if row/column combination is out of bounds.
    */
    public void removeElement(int row, int col) {
        if (row >= this.size || col >= this.size || row < 0 || col < 0) {
            System.out.println("Index does not exist, matrix is unchanged.");
            return;
        }
        
        for (Entry c : myMatrix) {
            if (c.getRow() == row && c.getCol() == col) {
                myMatrix.remove(c);
            }
        }
    }

    /*
     *  Return the element at the specified row and column
     *  Throws an error if row/column combination is out of bounds.
     */
    public int getElement(int row, int col) {
        if (row >= this.size || col >= this.size || row < 0 || col < 0) {
            System.out.println("Index does not exist, matrix is unchanged.");
            return -1;
        }
        
        for (Entry c : myMatrix) {
            if (c.getRow() == row && c.getCol() == col) {
                return c.getData();
            }
        }
        if (row < size && col < size) {
            //System.out.println("Error: Index out of bounds");
            return 0;
        }
        return 0;
    }

    /*
        Returns the determinant of the matrix calculated recursively (Use the formula provided in the project description).
     */
    public int determinant() {
        int determinant = 0;
        
        if (size == 1) {
            return (getElement(0,0));
        }
        else if (size == 2) {
            return ( (getElement(0,0) * getElement(1,1)) - (getElement(0,1) * getElement(1,0)) );
        }
        
        for (int c = 0; c < size; c++) {
            determinant += Math.pow(-1,c) * getElement(0,c) * minor(0,c).determinant();
        }
        return determinant;
    }

    /*
        Returns a new matrix which is the minor of the original (See project description for minor definition).
     */
    public SparseInterface minor(int row, int col) {
        SparseMatrix minorMatrix = new SparseMatrix(size - 1);
        
        for (Entry c : myMatrix) {
            if (c.getRow() != row && c.getCol() != col) {
                if (c.getRow() > row && c.getCol() > col) {
                    minorMatrix.addElement(c.getRow()-1, c.getCol()-1, c.getData());
                } 
                else if (c.getRow() > row && c.getCol() < col) {
                    minorMatrix.addElement(c.getRow()-1, c.getCol(), c.getData());
                }
            }
        }
        
        return minorMatrix;
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

    /*
    Should return the size of the matrix.
     */
    public int getSize() {
        return this.size;
    }
}