package ihm;

import javax.swing.*;

import donnees.Incident;

public class Sup_AffichageIncident extends Sup_AjoutModifIncident{

	Incident incid;
	
	
	//Constructeur
	public Sup_AffichageIncident(Incident incid, Sup_OngletIncident parent){
		
		// Appel du constructeur de Sup_AjoutModifIncident
		super(incid,parent);
		
		this.setTitle("Affichage d'un incident");
		
		// On est dans le cas d'un affichage : on rend les champs texte non éditables
		textColis.setEditable(false);
		comboEtat.setEditable(false);
		textDate.setEditable(false);
		textDescription.setEditable(false);
		textType.setEditable(false);
		textUtilisateur.setEditable(false);
		textWarning.setVisible(false);
		boutModifier.setVisible(false);
		boutAnnuler.setText("Fermer");
		boutAnnuler.setAlignmentX(Box.RIGHT_ALIGNMENT);
		
		pack();
	}
	
	
	
}
