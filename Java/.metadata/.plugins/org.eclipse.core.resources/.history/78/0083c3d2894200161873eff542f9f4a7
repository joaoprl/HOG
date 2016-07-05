
// file, nThreads, histogram&vectors, output

public class Main {
   public static void main(String[] args) {
	   String file = "penguin.jpg";
	   int nThreads = 8;
	   
	   if(args.length == 1){
			file = args[0];
		}
		if(args.length == 2){
			file = args[0];
			nThreads = Integer.valueOf(args[1]);
		}
	   
	   Serial serial = new Serial(file);
	   serial.Run();
	   
	   Parallel parallel = new Parallel(file, nThreads);
	   parallel.Run();
	   
	   System.out.print("\n");
	   System.out.println(file + "----------");
	   System.out.print("\n");
	   double speedup = (double)(serial.histogramsDuration + serial.outputDuration) / (parallel.histogramsDuration + parallel.outputDuration);
	   
	   System.out.println(speedup);
       
   }
}
