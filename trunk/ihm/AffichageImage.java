package ihm;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

// Cette classe est utilis�e pour l'affichage des images cam�ra des colis

public class AffichageImage extends Canvas{

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int largeurEcran = screenSize.width;
	int hauteurEcran = screenSize.height;
	Image image;

	public AffichageImage(String url)
	  {
	  image = getToolkit().getImage(url);
	  prepareImage(image, this);
	  }
	
	public AffichageImage(InputStream inputStream) throws IOException{
		image=ImageIO.read(inputStream);
	}

	public void paint(Graphics g)
	  {
	  g.drawImage(image, 0, 0, this);
	  }

	public boolean imageUpdate(Image image, int info, int x, int y, int l, int h)
	  {
	  if ((info & (WIDTH | HEIGHT)) != 0)
	    {
	    setSize(l, h);
	    
	    }

	  if ((info & (ALLBITS)) != 0)
	    {
	    repaint();
	    return false;
	    }
	  else
	    {
	    return true;
	    }
	  }
	
	
}
