package ihm.preparation;

import ihm.Bouton;
import ihm.FenetreType;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import donnees.Utilisateur;

public class ImprimerEtiquette extends JFrame implements ActionListener{
	private Bouton imprimer,fermer;
	private Utilisateur utilisateur;
	
	public ImprimerEtiquette(Utilisateur utilisateur, Integer idChargement){
		// Création graphique de la fenêtre
		setTitle("Créer Chargement");
		setBounds(162,120,700,527);
		setUndecorated(true);
		FenetreType fenetre=new FenetreType("images/preparation/imprimerEtiquette.png");
		setContentPane(fenetre);
		fenetre.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Mémorisation de l'utilisateur
		this.utilisateur=utilisateur;
		
		// Ajout des boutons dans la fenêtre
		this.imprimer=new Bouton("images/icones/imprimer.png","images/icones/imprimer_inv.png");
		this.imprimer.setBounds(10,10,10,10);
		this.imprimer.addActionListener(this);
		fenetre.add(this.imprimer);
		this.fermer=new Bouton("images/icones/fermer.png","images/icones/fermer_inv.png");
		this.fermer.setBounds(10,60,10,10);
		this.fermer.addActionListener(this);
		fenetre.add(this.fermer);
		
		// Recherche des infos sur le chargement dansla BDD
		
		// Affichage des informations concernant le chargement
	}
	public void actionPerformed(ActionEvent ev) {
		Object source = ev.getSource();
		// L'utilisateur clique sur le bouton "Imprimer"
		if(this.imprimer==source){
			// On imprime!!!
		}
		// On retourne au menu principal dans le cas de la fermeture et de l'impression
		dispose();
		new FenetrePrincipale(this.utilisateur).setVisible(true);
	}
}
