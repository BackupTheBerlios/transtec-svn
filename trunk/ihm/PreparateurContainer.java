package ihm;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import donnees.Utilisateur;

public class PreparateurContainer extends JPanel{
	private Utilisateur utilisateur;
	public PreparateurContainer(Utilisateur utilisateur){
		super();
		this.utilisateur=utilisateur;
	}
	
	// Permet de définir une image de fond
	public void paintComponent(Graphics g){
			ImageIcon img = new ImageIcon("images/preparation/fenetre_princBackground.png");
			g.drawImage(img.getImage(), 0, 0, null);
			setOpaque(false);
			setLayout(null);
			
			// Mise en place des paramètres par défaut
			Font font=new Font("Verdana", Font.BOLD, 13);
			JLabel labelUtilisateur=new JLabel(utilisateur.getPersonne().getNom()+" "+utilisateur.getPersonne().getPrenom()+" - Préparateur");
			labelUtilisateur.setBounds(73,70,300,20);
			labelUtilisateur.setFont(font);
			add(labelUtilisateur);
			super.paintComponent(g);
	}	
}
