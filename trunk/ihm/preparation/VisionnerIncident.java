package ihm.preparation;

import ihm.Bouton;
import ihm.FenetreType;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import donnees.Incident;

/*
 * Classe permettant d'afficher le détail d'un incident
 */

public class VisionnerIncident extends JFrame implements ActionListener{
	public VisionnerIncident(Incident incident){
		// Création graphique de la fenêtre
		setTitle("Affichage du détail de l'incident");
		setUndecorated(true);
		FenetreType fenetre=new FenetreType("images/preparation/descriptifIncident.png");
		setContentPane(fenetre);
		fenetre.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		setBounds(252, 239, 520, 290);
		
		// Création de la police
		Font font=new Font("Verdana", Font.BOLD, 12);
		
		// Affichage des zones d'informations sur l'incident
		JLabel lUtilisateur=new JLabel("Utilisateur "+incident.getUtilisateur().toString());
		lUtilisateur.setFont(font);
		lUtilisateur.setBounds(20, 40, 295, 20);
		fenetre.add(lUtilisateur);
		JLabel lDate=new JLabel("Date "+incident.getDate().toString());
		lDate.setFont(font);
		lDate.setBounds(20, 70, 295, 20);
		fenetre.add(lDate);
		JLabel lEtat=new JLabel("Etat "+Incident.constToString(incident.getEtat()));
		lEtat.setFont(font);
		lEtat.setBounds(20, 100, 150, 20);
		fenetre.add(lEtat);
		JLabel lType=new JLabel("Type "+Incident.constToString(incident.getType()));
		lType.setFont(font);
		lType.setBounds(150, 100, 150, 20);
		fenetre.add(lType);
		JLabel lDescription=new JLabel("Description :");
		lDescription.setFont(font);
		lDescription.setBounds(20, 90, 295, 100);
		fenetre.add(lDescription);
		JTextArea description=new JTextArea(incident.getDescription().toString());
		description.setFont(font);
		description.setBounds(20, 150, 292, 110);
		description.setAutoscrolls(true);
		description.setOpaque(false);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		fenetre.add(description);
		
		// Mise en place du bouton "fermer"
		Bouton fermer=new Bouton("images/icones/fermer.png","images/icones/fermer_inv.png");
		fermer.setBounds(350, 50, 97, 35);
		fenetre.add(fermer);
		fermer.addActionListener(this);
		
		setVisible(true);
	}
	public void actionPerformed(ActionEvent arg0) {
		// On ferme la fenêtre quand l'utilisateur clique sur le bouton fermer
		dispose();
	}
}
