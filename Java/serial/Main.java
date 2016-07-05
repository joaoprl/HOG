import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

// file, nThreads, histogram&vectors, output

public class Main {
   public static void main(String[] args) {
	   
	   long tic, toc;
	      		
	   String file = "tiger.jpg";
	   
       if(args.length == 1){
	        file = args[0];
       }
       
       System.out.print(file + ", " + "serial" + ", ");
       
       PrintWriter writer = null;
       try {
    	   writer = new PrintWriter(file + ".dat", "UTF-8");
       } catch (FileNotFoundException | UnsupportedEncodingException e1) {
    	   e1.printStackTrace();
       }

       Hog hog = new Hog(file);
       
       tic = System.nanoTime();
       double[][][] histograms = hog.getHistograms();
       toc = System.nanoTime();
       System.out.print((toc - tic)/10000l + ", ");
       /*
       for(double[][] arr : histograms){
    	   for(double [] ar : arr){
    		   for(double value : ar)
    			   writer.print(value + " ");
    		   writer.print("\n");
    	   }
       }
       writer.print("\n");*/
       
       tic = System.nanoTime();
       double[][][] output = hog.getOutput(histograms);
       toc = System.nanoTime();
       System.out.print((toc - tic)/10000l + ", ");
       System.out.print("\n");       
       /*
       for(double[][] arr : output){
    	   for(double [] ar : arr){
    		   for(double value : ar)
    			   writer.print(value + " ");
    	   }
       }
       writer.print("\n");*/
       
       writer.close();
      
   }
}
