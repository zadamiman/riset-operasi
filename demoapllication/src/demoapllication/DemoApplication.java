package demoapplication;

 public class DemoApplication {
 	public static void main(String[] args) {
 
  		int[][] values = { 
 		{ 150, 200, 180, 220 },
 		{ 140, 160, 210, 170 },
 		{ 250, 200, 230, 200 },
 		{ 170, 180, 180,160 }
 		 };
 
  		long time = System.currentTimeMillis();
 		Hungarian hungarian = new Hungarian(values);


 		int[] result = hungarian.getResult();
 		for(int i = 0; i < result.length; i++)
 			System.out.println(String.format("Row%d => Col%d (%d)", i+1, result[i]+1, values[i][result[i]]));
 
  		System.out.println(String.format("\nTotal: %d", hungarian.getTotal()));
 	}
 }