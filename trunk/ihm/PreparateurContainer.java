package ihm;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import donnees.Utilisateur;

public class PreparateurContainer extends JPanel{
	private Utilisateur utilisateur;
	private Bouton deconnexion;
	public PreparateurContainer(Utilisateur utilisateur, Bouton deconnexion){
		super();
		this.utilisateur=utilisateur;
		this.deconnexion=deconnexion;
	}
	
	// Permet de définir une image de fond
	public void paintComponent(Graphics g){
			ImageIcon img = new ImageIcon("images/preparation/fenetre_princBackground.png");
			g.drawImage(img.getImage(), 0, 0, null);
			setOpaque(false);
			setLayout(null);
			
			// Mise en place des paramètres par défaut
			JLabel labelUtilisateur=new JLabel(utilisateur.getPersonne().getNom()+" "+utilisateur.getPersonne().getPrenom()+" - Préparateur");
			labelUtilisateur.setBounds(73,76,200,20);
			add(labelUtilisateur);
			this.deconnexion=new Bouton("images/icones/deconnexion.png","images/icones/deconnexion.png");
			this.deconnexion.setBounds(866, 65, 100, 50);
			add(this.deconnexion);
			super.paintComponent(g);
	}	
}
