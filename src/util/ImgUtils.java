package util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;



/**
 * @author Archmage
 */
public class ImgUtils {
	public static Image getPic(InputStream is){
		
		try {
			
			BufferedImage image = ImageIO.read(is);
			return image;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
}
