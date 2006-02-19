package ihm.entree;

import java.awt.*;

// Cette classe est utilisée pour l'affichage des images caméra des colis

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
