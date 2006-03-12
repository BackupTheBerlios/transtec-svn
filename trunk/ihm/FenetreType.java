package ihm;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import donnees.Utilisateur;

public class FenetreType extends JPanel{
	private final static int GRANDE_FEN=0;
	private final static int BOITE_DIALOG=1;
	
	private Utilisateur utilisateur=null;
	private String cheminImage;
	private int typeFentre;
	
	public FenetreType(Utilisateur utilisateur, String cheminImage){
		super();
		this.utilisateur=utilisateur;
		this.cheminImage=cheminImage;
		this.typeFentre=GRANDE_FEN;
	}
	
	public FenetreType(String cheminImage){
		super();
		this.cheminImage=cheminImage;
		this.typeFentre=BOITE_DIALOG;
	}
	
	// Permet de définir une image de fond
	public void paintComponent(Graphics g){
		if(this.typeFentre==GRANDE_FEN){
			ImageIcon img = new ImageIcon(this.cheminImage);
			g.drawImage(img.getImage(), 0, 0, null);
			setOpaque(false);
			setLayout(null);
			
			// Mise en place des paramètres par défaut
			Font font=new Font("Verdana", Font.BOLD, 13);
			JLabel labelUtilisateur=new JLabel(utilisateur.toTitre());
			labelUtilisateur.setBounds(73,70,300,20);
			labelUtilisateur.setFont(font);
			add(labelUtilisateur);
			super.paintComponent(g);
		}
		if(this.typeFentre==BOITE_DIALOG){
			ImageIcon img = new ImageIcon(this.cheminImage);
			g.drawImage(img.getImage(), 0, 0, null);
			setOpaque(false);
			setLayout(null);
		}
	}	
}
