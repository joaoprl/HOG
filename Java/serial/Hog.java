
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Hog {
	
	private static boolean drawImage = false;
	
	private BufferedImage image = null;
	private BufferedImage colorImage = null;
	private JFrame frame;
	private Graphics graphics;
    
	public Hog(String file){
		try {
			colorImage = ImageIO.read(new File(file));
		} catch (IOException e) {
		}
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);  
		ColorConvertOp op = new ColorConvertOp(cs, null);  
		image = op.filter(colorImage, null);
		
		graphics = colorImage.getGraphics();
		
		if(drawImage){
			this.frame = new JFrame();
			this.frame.getContentPane().setLayout(new FlowLayout());
			this.frame.getContentPane().add(new JLabel(new ImageIcon(image)));
			this.frame.pack();
			this.frame.setVisible(true);
		}		
		else
			this.frame = null;

	}
	public double[][][] getHistograms(){

		double [][][] histograms = new double[image.getWidth()][image.getHeight()][9];
		double [][][] vectors = new double[image.getWidth() / 8 + 1][image.getHeight() / 8 + 1][2];
		

		for(int y = 0; y < this.getHeight(); y++){
			for(int x = 0; x < this.getWidth(); x++){

				double x_vec, y_vec;

				if (x == 0 || x == this.getWidth() - 1 || y == 0 || y == this.getHeight() - 1){
					x_vec = 0;
					y_vec = 0;
				}

				else{
					x_vec = this.getValue(x + 1, y) - this.getValue(x - 1, y);
					y_vec = this.getValue(x, y + 1) - this.getValue(x, y - 1);
				}
				double v = Math.pow(x_vec, 2) + Math.pow(y_vec, 2);
				double mag = Math.pow( v, 0.5);
				
				double ang = Math.atan2(Math.abs(y_vec), Math.abs(x_vec));
					
				double angRad = Math.toDegrees(ang);

				int pos1 = (int)((angRad + 10) / 20 - 1) % 9;
				int pos2 = (int)((angRad + 10) / 20) % 9;

				histograms[x / 8][y / 8][pos1] += ((20 - ((angRad % 10.0) % 20)) / 20) * mag;
				histograms[x / 8][y / 8][pos2] += (1 - (20 - ((angRad % 10.0) % 20)) / 20) * mag;
				
				vectors[x / 8][y / 8][0] += x_vec;
				vectors[x / 8][y / 8][1] += y_vec;

			}
		}
		

		for(int y = 0; y < image.getHeight() / 8; y++){
			for(int x = 0; x < image.getWidth() / 8; x++){
				double x_vec = vectors[x][y][0], y_vec = vectors[x][y][1];
				
				double v = Math.pow(x_vec, 2) + Math.pow(y_vec, 2);
				double mag = Math.pow(v, 0.5);
				
				if(mag > 500){
					x_vec /= mag/8;
					y_vec /= mag/8;
					
					double aux = x_vec;
					x_vec = y_vec;
					y_vec = -aux;
					
					graphics.drawLine(x * 8 + 4, y * 8 + 4, (int)x_vec + x * 8 + 4, (int)y_vec + y * 8 + 4);

				}
			}
		}		
		this.repaint();
		
		return histograms;
	}

	public double[][][] getOutput(double[][][] histograms){
		double output[][][] = new double[image.getWidth() - 1][image.getHeight() - 1][36];
		for(int x = 0; x < image.getWidth() / 8 - 1; x++){
			for(int y = 0; y < image.getHeight() / 8 - 1; y++){
				int counter = 0;
				double mag = 0;
				for(int a = 0; a < 2; a++){
					for(int b = 0; b < 2; b++){
						for(int z = 0; z < 9; z++){
							output[x][y][counter] = histograms[x + a][y + b][z];
							mag += output[x][y][counter];
							counter++;				        				
						}
					}
				}

				if(mag != 0){
					for(int z = 0; z < 36; z++){
						output[x][y][z] /= mag;
					}
				}
			}
		}
		
		return output;
	}

	private int getValue(int x, int y){
		return image.getRGB(x, y) & 0xFF;
	}
	
	public int getWidth(){
		return image.getWidth();
	}
	public int getHeight(){
		return image.getHeight();
	}
	
	public void repaint(){
		if(frame != null)
			frame.repaint();
		try {
			ImageIO.write(colorImage, "jpg", new File("output.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
