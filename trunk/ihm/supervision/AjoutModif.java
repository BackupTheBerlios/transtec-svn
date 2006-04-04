package ihm.supervision;

//import java.awt.event.*;

import ihm.Bouton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AjoutModif extends JFrame{
	
	protected JTextField textWarning = new JTextField(15);
	protected Bouton boutAjouter = new Bouton("images/supervision/bouton_valider.png","images/supervision/bouton_valider_appuyer.png");
	protected Bouton boutModifier = new Bouton("images/supervision/bouton_valider.png","images/supervision/bouton_valider_appuyer.png");
	protected Bouton boutAnnuler = new Bouton("images/supervision/bouton_annuler.png","images/supervision/bouton_annuler_appuyer.png");
	protected JPanel panneauBoutons = new JPanel(new GridLayout(1,2,15,15));
	protected JPanel panneauWarning = new JPanel(new GridLayout(1,1,5,5));
	protected JPanel panneauHaut = new JPanel(new BorderLayout(5,5));
	protected JPanel panneauLabels = new JPanel();
	protected JPanel panneauSaisie = new JPanel();
	protected JPanel contenu;
	
	public AjoutModif(){	
		// On enlève les barres d'état
		setUndecorated(true);
		
		// Taille de la fenêtre
		setLocation(300,200);
		
		// Panel recevant le contenu de la fenêtre
		contenu = new JPanel(){
			// Permet de définir une image de fond
			public void paintComponent(Graphics g)	{
				ImageIcon img = new ImageIcon("images/supervision/bg_details.png");
				g.drawImage(img.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		
		// On place contenu comme Panel principal
		setContentPane(contenu);
		
		// On active la transparence pour que l'arrière plan soit visible
		contenu.setOpaque(false);
		
		// Champ d'avertissement en cas de saisie incomplète
		textWarning.setEditable(false);
		textWarning.setForeground(Color.RED);
		textWarning.setHorizontalAlignment(JTextField.CENTER);
		textWarning.setBorder(BorderFactory.createLineBorder(Color.black));
		panneauWarning.add(textWarning);

		// Panel regroupant les labels et les champs de saisie
		panneauHaut.add(panneauLabels,BorderLayout.WEST);
		panneauHaut.add(panneauSaisie,BorderLayout.CENTER);

		// On ajoute tous les panneaux secondaires au panneau principal
		contenu.setLayout(new BorderLayout(20,20));
		contenu.add(panneauHaut,BorderLayout.NORTH);
		contenu.add(panneauBoutons,BorderLayout.CENTER);
		contenu.add(panneauWarning,BorderLayout.SOUTH);
		
		// On rajoute un peu d'espace autour des panneaux
		contenu.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 1),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		// On définit les panels transparents
		panneauBoutons.setOpaque(false);
		panneauWarning.setOpaque(false);
		panneauHaut.setOpaque(false);
		panneauLabels.setOpaque(false);
		panneauSaisie.setOpaque(false);		
	}
	
	
	// Ajoute un message d'erreur à la boite de dialogue si un champs est mal renseigné
	protected void setWarning(String s){
		textWarning.setText("Le champs \""+s+"\" est mal renseigné.");
		textWarning.updateUI();
	}

}
