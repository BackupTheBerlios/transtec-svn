package ihm;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

//Classe d�riv�e de JPanel correspondant au panel principal de la fen�tre de login 
class PanelContenu extends JPanel{
	private String chemin;
	
	public PanelContenu(String cheminBackground){
		super();
		this.chemin=cheminBackground;
	}
	
	// Permet de d�finir une image de fond
	public void paintComponent(Graphics g){
			ImageIcon img = new ImageIcon(this.chemin);
			g.drawImage(img.getImage(), 0, 0, null);
			super.paintComponent(g);
	}	
}