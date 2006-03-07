package ihm;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

//Classe dérivée de JPanel correspondant au panel principal de la fenêtre de login 
class PanelContenu extends JPanel{
	private String chemin;
	
	public PanelContenu(String cheminBackground){
		super();
		this.chemin=cheminBackground;
	}
	
	// Permet de définir une image de fond
	public void paintComponent(Graphics g){
			ImageIcon img = new ImageIcon(this.chemin);
			g.drawImage(img.getImage(), 0, 0, null);
			super.paintComponent(g);
	}	
}