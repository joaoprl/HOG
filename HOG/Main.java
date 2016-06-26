
public class Main {
   public static void main(String[] args) {
	   String file = "universe.jpg";
       if(args.length == 1){
	        file = args[0];
       }

       Hog hog = new Hog(file);

       double[][][] histograms = hog.getHistograms();
       hog.getOutput(histograms);
       
   }
}
