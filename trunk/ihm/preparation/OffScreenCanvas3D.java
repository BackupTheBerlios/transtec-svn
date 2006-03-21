package ihm.preparation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Screen3D;

public class OffScreenCanvas3D extends Canvas3D{
	//private boolean offscreen;
	private Screen3D onScreen, offScreen;
	public OffScreenCanvas3D(Canvas3D onScreenCanvas3D) {
	    super(onScreenCanvas3D.getGraphicsConfiguration(), true);

	    // On regle le screen 3D (offscreen) a la taille du screen 3D (onscreen)
	    onScreen = onScreenCanvas3D.getScreen3D();
	    offScreen = this.getScreen3D();
	    offScreen.setSize(onScreen.getSize());
	    offScreen.setPhysicalScreenWidth(onScreen.getPhysicalScreenWidth());
	    offScreen.setPhysicalScreenHeight(onScreen.getPhysicalScreenHeight());
	    
//		 Dimension (en pixels) de l'image a mettre dans le presse-papiers
		Dimension dim = new Dimension(660, 300);

//		 On recupere l'image (pixmap) rendue par le canvas 3D offscreen
		BufferedImage image = getOffScreenImage(dim);
		
		
		
		
		// Fichier
		File imageFile = new File("image.png");
		
		Graphics2D gc = image.createGraphics();
		gc.drawImage(image, 0, 0, null);
		
		try {
		    ImageIO.write(image, "png", imageFile);
		}
		catch (IOException ex) {
		    System.out.println("Impossible de sauvegarder l'image");
		}
	}
	
	public BufferedImage getOffScreenImage(Dimension dim){
		BufferedImage bImage = new BufferedImage(dim.width, dim.height,
				BufferedImage.TYPE_INT_ARGB);
				ImageComponent2D buffer = new ImageComponent2D(ImageComponent.FORMAT_RGBA, bImage);
				buffer.setCapability(ImageComponent2D.ALLOW_IMAGE_READ);
				
		setOffScreenBuffer(buffer);
		//renderOffScreenBuffer();
		waitForOffScreenRendering();
		bImage = getOffScreenBuffer().getImage();
		bImage = getOffScreenBuffer().getImage();
		return bImage;
	}
}
