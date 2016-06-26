
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Hog {
	
	private BufferedImage image = null;
        private int nThreads = 8;

	public Hog(String file){
		BufferedImage colorImage = null;
		try {
			colorImage = ImageIO.read(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);  
		ColorConvertOp op = new ColorConvertOp(cs, null);  
		image = op.filter(colorImage, null);

	}
	
	public double[][][] getHistograms(){
		double [][][] histograms = new double[image.getWidth() / 8 + 1][image.getHeight() / 8 + 1][9];
		
		Thread[] threads = new Thread[nThreads];
		for(int i = 0; i < nThreads; i++){
			Thread t = new Thread(new HogGradients(this, histograms, i * getHeight() / nThreads, (i + 1) * getHeight() / nThreads));
			t.start();
			threads[i] = t;
		}
		for(int i = 0; i < nThreads; i++){
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		/*
		for(int x = 0; x < image.getWidth() / 8; x++){				
			for(int y = 0; y < image.getHeight() / 8; y++){
				for(int z = 0; z < 9; z++)
					System.out.print(histograms[x][y][z] + " ");
				System.out.print("\n");
			}
		}*/
		
		return histograms;
	}

	public double[][][] getOutput(double[][][] histograms){
		
		double output[][][] = new double[image.getWidth()/8 - 1][image.getHeight()/8 - 1][36];
				
		Thread[] threads = new Thread[nThreads];
		for(int i = 0; i < nThreads; i++){
			Thread t = new Thread(new HogOutput(this, histograms, output, i * (getHeight() / 8 - 1) / nThreads, (i + 1) * (getHeight() / 8 - 1) / nThreads));
			t.start();
			threads[i] = t;
		}
		for(int i = 0; i < nThreads; i++){
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}/*
		
		for(int x = 0; x < image.getWidth() / 8 - 1; x++){
			for(int y = 0; y < image.getHeight() / 8 - 1; y++){
				for(int z = 0; z < 36; z++)
						System.out.print(output[x][y][z] + " ");
				System.out.print("\n");
			}
		}*/
		return output;
	}

	public int getValue(int x, int y){
		return image.getRGB(x, y) & 0xFF;
	}
	
	public int getWidth(){
		return image.getWidth();
	}
	public int getHeight(){
		return image.getHeight();
	}
}
