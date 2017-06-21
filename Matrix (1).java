package matrixmath;
/**
 * 
 * @author Emily Cohen
 * @version 1.1
 * 
 * Description: This code is based off of the Matrix qualities we have been learning this year. The function in this code are 
 * 		designed to print a matrix, multiply two matrices, add two matrices, scale a row of a matrix, 
 * 
 * Date Last Modified: November 28th, 2014
 *
 */

public class Matrix {
	int rows; //rows of a matrix
	int columns; //columns of a matrix
	double [][] matrix; //the matrix in a double array

	//GETTERS AND SETTERS
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public double[][] getMatrix() {
		return matrix;
	}
	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	/**
	 * 
	 * @param rows
	 * @param columns
	 */
	public Matrix(int rows, int columns){
		this.setRows(rows); //set rows of this matrix
		this.setColumns(columns); //sets columns
		this.matrix = new double[rows][columns]; //makes this matrix a double array with "rows" rows and "columns" column
	}
	/**
	 * 
	 * @param r
	 * @param c
	 * @param entry
	 */
	public void setEntry(int r, int c, double entry) {
		this.matrix [r][c] = entry; //put input into the spot in the matrix at row r and column c
	}
	/**
	 * prints a matrix. For example, trixie.print() prints the matrix trixie to the screen.
	 */
	public void print(){
		for (int i = 0; i < rows; i++)//row loop 
		{
			for (int j = 0; j < columns; j++) //column loop
			{
				System.out.print(matrix[i][j] + "    "); //prints the matrix at row i and column j
			}
			System.out.println();
		}
	}
	/**
	 * adds two matrices. For example, trixie.plus(alice) is the sum of the matrices trixie and alice.
	 * @param that
	 * @return
	 */
	public Matrix plus(Matrix that){
		Matrix newMatrix = new Matrix(this.getRows(), this.getColumns()); //creates a new matrix with the parameters of this matrix
		//go through and add each value
		for (int i = 0; i < newMatrix.getRows(); i++)
		{
			for (int j = 0; j < newMatrix.getColumns(); j++)
			{
				newMatrix.setEntry(i, j, this.getMatrix()[i][j] + that.getMatrix()[i][j]);
				//sets the value at i, j which is the sum of the values in each matrix at that spot
			}
		}
		return newMatrix;
	}
	/**
	 * multiplies a scalar times a row. For example, trixie.scalarTimesRow(2.0,3) multiplies row three of trixie by two.
	 * @param scalar
	 * @param rownumber
	 * @return
	 */
	public Matrix scalarTimesRow(double scalar, int rownumber){
		Matrix newMatrix = new Matrix(this.getRows(), this.getColumns()); //creates a new matrix w parameters of the old one
		newMatrix = this.copy(this);
		//scales the row at hand
		for (int i = 0; i < columns; i++)
		{
			newMatrix.matrix[rownumber][i] = scalar*(matrix[rownumber][i]);
		}
		return newMatrix;
	}

	/**
	 * this is just a helper method which copies a matrix
	 * @param m the matrix being copied
	 * @return the copy of the matrix
	 */
	public Matrix copy(Matrix m){
		Matrix newMatrix = new Matrix(m.getRows(), m.getColumns()); //creates a new matrix w parameters of the old one
		//copies the old matrix
		for (int i = 0; i < m.getRows(); i++)
		{
			for (int j = 0; j < m.getColumns(); j++)
			{
				newMatrix.setEntry(i, j, m.getMatrix()[i][j]);
			}
		}
		return newMatrix;
	}


	/**
	 * switches two rows of a matrix. For example, trixie.switchRows(3,5) exchanges rows three and five of the matrix trixie.
	 * @param firstrow
	 * @param secondrow
	 * @return
	 */
	public Matrix switchRows(int firstrow, int secondrow){
		Matrix newMatrix = new Matrix(this.getRows(), this.getColumns()); //creates a new matrix w parameters of the old one
		newMatrix = this.copy(this);//copies the old matrix
		//puts the "firstrow" of the real matrix in the "secondrow" of the fake matrix
		for (int i = 0; i < columns; i++)
		{
			newMatrix.matrix[secondrow][i] = matrix[firstrow][i];
		}
		//puts the "secondrow" of the real matrix in the "firstrow" of the fake matrix
		for (int i = 0; i < columns; i++)
		{
			newMatrix.matrix[firstrow][i] = matrix[secondrow][i];
		}
		return newMatrix;
	}

	/**
	 * adds a scalar multiple of the first row to the second row. For example, trixie.linearCombRows(.5,3,2) adds .5 times row 
	 * 	three to row two.
	 * @param scalar
	 * @param firstrow
	 * @param secondrow
	 * @return
	 */
	public Matrix linearCombRows(double scalar, int firstrow, int secondrow) {
		Matrix newMatrix1 = new Matrix(this.getRows(), this.getColumns()); //creates a new matrix w parameters of the old one
		Matrix newMatrix2 = new Matrix(this.getRows(), this.getColumns()); //creates a new matrix w parameters of the old one
		//copies the old matrix into both of the new matrices
		for (int i = 0; i < this.getRows(); i++){
			for (int j = 0; j < this.getColumns(); j++){
				newMatrix1.setEntry(i, j, this.getMatrix()[i][j]);
				newMatrix2.setEntry(i, j, this.getMatrix()[i][j]);
			}
		}
		newMatrix2 = newMatrix1.scalarTimesRow(scalar, firstrow); //scales the first row and puts this into new matrix two
		//runs through a loop of the columns in the second row
		for (int k = 0; k<newMatrix1.getRows(); k++){
			newMatrix1.setEntry(secondrow, k, newMatrix2.getMatrix()[secondrow][k]+newMatrix2.getMatrix()[firstrow][k]);
			//adds the scaled first row the second row and puts in new matrix two which still has the normal first row
		}
		return newMatrix1;
	}

	/**
	 * multiplies two matrices. For example, trixie.times(alice) is the product of the matrices trixie and alice.
	 * @param that
	 * @return
	 */
	public Matrix times(Matrix that){
		//if the two matrices can be multiplied
		if (this.getColumns() == that.getRows()){
			int rows = this.getRows();
			int cols = that.getColumns();
			double position = 0; //the position that i am working on
			Matrix newMatrix = new Matrix (rows, cols);//new matrix w rows of this and columns of that
			for (int i = 0; i < rows; i++){
				for (int j = 0; j < cols; j++){
					//what goes in each slot
					for (int k = 0; k < this.getColumns(); k++) 
					{
						position = position + this.getMatrix()[i][k] * that.getMatrix()[k][j];
					}
					newMatrix.setEntry(i, j, position);
					position = 0; //resets after doing each position
				}
			}	
			return newMatrix;
		}
		else{ //if the matrices can not be multiplied 
			System.out.println("These matrices can not be multiplied: be change the rows of your matrice to equal: " + this.getColumns());
			System.out.println("Here is your inputed matrix: ");
			return that;
		}
	}
	/**
	 * row reduces a matrix. For example, trixie.rowreduce().print() should print the row reduction of trixie. 
	 * 	rowreduce works for all matrices. 
	 * @return
	 */
	public Matrix rowreduce(){
		Matrix newMatrix = new Matrix(this.getRows(), this.getColumns());
		newMatrix = this.copy(this);//copies the old matrix into the new matrix
		int colpush = 0; //if you need to push back the entire program because a full column is zero
		int numcolzero = 0; //a variable that stores the value of how many zeroes are in a column
		int numrowzero = 0; //a variable that stores the value of how many zeroes are in a row
		int rowzero = 1; //a variable used when rows need to be switched i.e. if the number at hand is zero
		//reduces a row
		for (int i = 0; i< newMatrix.getColumns(); i++){
			//checks to see if the entire column is full of zeros
			for (int a = 0; a <newMatrix.getColumns(); a++){
				for (int b = 0; b < newMatrix.getRows(); b++){
					if(newMatrix.matrix[b][a] == 0) numcolzero ++;
					else numcolzero = numcolzero +100;
				}
			}
			//if the entire column is full of zeros, it skips it
			if (numcolzero == newMatrix.getRows()){ 
				colpush++; //adds to colpush because the entire column was zero
				numcolzero = 0; //resets the helper variable
			}
			//if the column is not full of zeros 
			else{ 
				//this loops effectively row reduces for the numbers that have to be ones and below, the next part will do the rest
				for (int j = i-colpush; j < newMatrix.getRows(); j++) 
				{
					//make sure your dealing with rows that need to be adjusted
					if (i-colpush < newMatrix.getRows() || i-colpush == newMatrix.getRows()){
						for(int k = i-colpush; k < newMatrix.getRows(); k++){
							if(newMatrix.matrix[k][i] == 0){
								numrowzero++;
							}
							else{
								numrowzero = newMatrix.getRows() + 1;
							}
						}
						if(numrowzero == newMatrix.getRows()-(i-colpush)){
							colpush++;
						}
						else{
							//if the number is a number that needs to become a one
							if (j == i-colpush){
								if(newMatrix.matrix[j][i] != 0){
									newMatrix = newMatrix.scalarTimesRow(1/(newMatrix.matrix[j][i]), j);
								}
								else{
									newMatrix = newMatrix.switchRows(j, j+rowzero);
									rowzero++;
									j--;
								}
							}
							//if the number is below the number that needs to become a one
							else
							{
								//if it is a zero don't change it
								if(newMatrix.matrix[j][i] == 0){
								}
								//if it is not a zero, make it into a zero
								else{
									Matrix newMatrix2 = new Matrix(this.getRows(), this.getColumns());
									newMatrix2 = newMatrix.copy(newMatrix);
									double multipliyer = newMatrix.matrix[j][i];
									//Slightly altered version of linear combine rows method
									for (int k = 0; k < newMatrix2.getColumns(); k++){
										newMatrix2.matrix[i-colpush][k] = newMatrix.matrix[i-colpush][k]*(-1*multipliyer);
										newMatrix.matrix[j][k] = newMatrix.matrix[j][k] + newMatrix2.matrix[i-colpush][k];
									}
								}
							}
						}
					}
				}//closes the loop for the 1s and the zeros below the ones
				numrowzero = 0; //sets this back to zero because you are re-entering the loop in a differnt row

				//make sure your adjusting columns that need to be adjusted
				if(i-colpush==newMatrix.getRows() || i-colpush>newMatrix.getRows()){
				}
				else{
					//adjust rows above the number one, basically goes backwards
					for (int j = 0; j < i-colpush; j++){
						Matrix newMatrix3 = new Matrix(this.getRows(), this.getColumns());
						newMatrix3 = newMatrix.copy(newMatrix);
						double multipliyer = newMatrix.matrix[j][i];
						//goes through all of the columns and creates zeros in the necessary numbers above the numbers i made 1s, similar to the linear combine rows method
						for (int k = 0; k < newMatrix3.getColumns(); k++){
							newMatrix3.matrix[i-colpush][k] = newMatrix.matrix[i-colpush][k]*(-1*multipliyer);
							newMatrix.matrix[j][k] = newMatrix.matrix[j][k] + newMatrix3.matrix[i-colpush][k];
						}
					}
				} //closes the loop for adjusting above the ones. 
				rowzero = 1;
			}
		}
		return newMatrix;
	}

	/**
	 *  inverts a matrix. For example, trixie.invert().print() should print the inverse matrix of trixie.
	 *  invert works for square matrices which are invertible.
	 * @return
	 */
	public Matrix invert(){
		Matrix inverse = new Matrix(this.getRows(), this.getColumns());
		inverse = this.copy(this); //copies the old matrix into new one
		if (this.getRows() != this.getColumns()){
			System.out.println("You can not invert this matrix! You can only invert square matrices.");
		}
		else{
			Matrix newMatrix = new Matrix(this.getRows(), 2*this.getColumns());
			Matrix identity = new Matrix(this.getRows(), this.getColumns());
			//fills the identity matrix with zeros
			for (int i = 0; i < identity.getRows(); i++)//row loop 
			{
				for (int j = 0; j < identity.getColumns(); j++) //column loop
				{
					identity.matrix[i][j] = 0;
				}
				System.out.println();
			}
			//adds the 1s to the identity matrix
			for (int k = 0; k<this.getRows(); k++){
				identity.matrix[k][k] =1;
			}
			for (int i =0; i<identity.getRows(); i++){
				for (int j =0; j<identity.getRows(); j++){
					newMatrix.matrix[i][j] = this.matrix[i][j];
					newMatrix.matrix[i][j+this.getColumns()] =  identity.matrix[i][j];
				}
			}
			//reduces the matrix
			newMatrix = newMatrix.rowreduce();
			//put the values from the second half of the columns into a new matrix which is the identity by definition
			for (int i =0; i<newMatrix.getRows(); i++){
				for (int j =0; j<newMatrix.getRows(); j++){
					inverse.matrix[i][j] = newMatrix.matrix[i][j+identity.getColumns()];
				}
			}
		}
		return inverse;
	}
}
