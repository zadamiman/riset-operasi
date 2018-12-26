/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoapplication;

/**
 *
 * @author ASPIRE E 14
 */
public class Hungarian {
 	private int[][] originalValues; 
 	private int[][] values; 
        private int[][] lines;
 	private int numLines;
 
  	int rows[];
 	int occupiedCols[];
 
  	public Hungarian(int[][] matrix) {
 		
 		originalValues = matrix; // Given matrix
 		values = cloneMatrix(matrix); // Cloned matrix to be processed
 		rows = new int[values.length];
 		occupiedCols = new int[values.length];
 
  		
 		subtractRowMinimal(); 			
 		subtractColMinimal();				
 		coverZeros();					
 		while(numLines < values.length){
 			createAdditionalZeros();		
 			coverZeros();				
 		}
 		optimization();					
 	}
 
  	/**
 	 * Step 1
 	 * */
 	public void subtractRowMinimal(){
 		int rowMinValue[] = new int[values.length];
 		//get the minimum for each row and store in rowMinValue[]
 		for(int row=0; row<values.length;row++){
 			rowMinValue[row] = values[row][0];
 			for(int col=1; col<values.length;col++){
 				if(values[row][col] < rowMinValue[row])
 					rowMinValue[row] = values[row][col];
 			}
 		}
 
  		//subtract minimum from each row using rowMinValue[]
 		for(int row=0; row<values.length;row++){
 			for(int col=0; col<values.length;col++){
 				values[row][col] -= rowMinValue[row];
 			}
 		}
 	} //End Step 1
 
  	/**
 	 * Step 2
 	 * */
 	public void subtractColMinimal(){
 		int colMinValue[] = new int[values.length];
 		//get the minimum for each column and store them in colMinValue[]
 		for(int col=0; col<values.length;col++){
 			colMinValue[col] = values[0][col];
 			for(int row=1; row<values.length;row++){
 				if(values[row][col] < colMinValue[col])
 					colMinValue[col] = values[row][col];
 			}
 		}
 
  		//subtract minimum from each column using colMinValue[]
 		for(int col=0; col<values.length;col++){
 			for(int row=0; row<values.length;row++){
 				values[row][col] -= colMinValue[col];
 			}
 		}
 	} //End Step 2
 
  	/**
 	 * Step 3.1
 	 * */
 	public void coverZeros(){
 		numLines = 0;
 		lines = new int[values.length][values.length];
 
  		for(int row=0; row<values.length;row++){
 			for(int col=0; col<values.length;col++){
 				if(values[row][col] == 0)
 					colorNeighbors(row, col, maxVH(row, col));
 			}
 		}
 	}
 
  	/**
 	 * Step 3.2
 	 * */
 	private int maxVH(int row, int col){
 		int result = 0;
 		for(int i=0; i<values.length;i++){
 			if(values[i][col] == 0)
 				result++;
 			if(values[row][i] == 0)
 				result--;
 		}
 		return result;
 	}
 
  	/**
 	 * Step 3.3
 	 * */
 	private void colorNeighbors(int row, int col, int maxVH){
 		if(lines[row][col] == 2)
                    return;
 
  		if(maxVH > 0 && lines[row][col] == 1) 
                    return;
 
  		if(maxVH <= 0 && lines[row][col] == -1) 
 			return;
 
  		for(int i=0; i<values.length;i++){ 
                    if(maxVH > 0)
                            lines[i][col] = lines[i][col] == -1 || lines[i][col] == 2 ? 2 : 1;
                        else	lines[row][i] = lines[row][i] == 1 || lines[row][i] == 2 ? 2 : -1;}
 
  		
 		numLines++;
	}
        //end step 3
 	public void createAdditionalZeros(){
 		int minUncoveredValue = 0;
  		for(int row=0; row<values.length;row++){
 			for(int col=0; col<values.length;col++){
 				if(lines[row][col] == 0 && (values[row][col] < minUncoveredValue || minUncoveredValue == 0))
 					minUncoveredValue = values[row][col];
 			}
 		}
 
  		for(int row=0; row<values.length;row++){
 			for(int col=0; col<values.length;col++){
 				if(lines[row][col] == 0) 
 					values[row][col] -= minUncoveredValue;
 
  				else if(lines[row][col] == 2)
 					values[row][col] += minUncoveredValue;
 			}
 		}
 	} 
 	private boolean optimization(int row){
 		if(row == rows.length) 
 			return true;
 
  		for(int col=0; col<values.length;col++){
 			if(values[row][col] == 0 && occupiedCols[col] == 0){
                            rows[row] = col; 
                                occupiedCols[col] = 1;
                                if(optimization(row+1))
 					return true;
 				occupiedCols[col] = 0;
 			}
 		}
 		return false; 
        }
 
 	public boolean optimization(){
 		return optimization(0);
 	}
        //end
 	public int[] getResult(){
 		return rows;
 	}
 
  	
 	public int getTotal(){
 		int total = 0;
 		for(int row = 0; row < values.length; row++)
 			total += originalValues[row][rows[row]];
 		return total;
 	}
 
  	
 	public int[][] cloneMatrix(int[][] matrix){
 		int[][] tmp = new int[matrix.length][matrix.length];
 		for(int row = 0; row < matrix.length; row++){
 			tmp[row] = matrix[row].clone();
 		}
 		return tmp;
 	}
 
        
 	public void printMatrix(int[][] matrix){
 		for(int row=0; row<values.length;row++){
 			for(int col=0; col<values.length;col++){
 				System.out.print(matrix[row][col]+"\t");
 			}
 			System.out.println();
 		}
 		System.out.println();
 	}
 }