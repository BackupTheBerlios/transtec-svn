package ihm;

import javax.swing.ImageIcon;
import javax.swing.JButton;

//Bouton personnalisé composé d'une image uniquement
class Bouton extends JButton{
	public Bouton(String cheminImage, String cheminImageOnClick){
		super();
		this.setIcon(new ImageIcon(cheminImage));
		this.setBorder(null);
		this.setContentAreaFilled(false);
		this.setMargin(null);
		this.setRolloverEnabled(false);
		this.setPressedIcon(new ImageIcon(cheminImageOnClick));
	}
}