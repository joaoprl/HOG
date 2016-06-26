
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Hog {
  BufferedImage image = null;

    public Hog(String file){
      BufferedImage colorImage;
      try {
        colorImage = ImageIO.read(new File(file));
      } catch (IOException e) {
      }

      ImageFilter filter = new GrayFilter(true, 50);
      ImageProducer producer = new FilteredImageSource(colorImage.getSource(), filter);
      image = Toolkit.getDefaultToolkit().createImage(producer);

  }
  public void getHistograms(){

  }

}
