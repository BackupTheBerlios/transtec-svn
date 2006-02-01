package ihm.supervision;

import javax.swing.*;

import donnees.Incident;
import accesBDD.AccesBDDIncident;

public class Sup_AffichageIncident extends Sup_AjoutModifIncident{

	private Incident incid;
	
	
	//Constructeur
	public Sup_AffichageIncident(Incident incid, Sup_OngletIncident parent, AccesBDDIncident tableIncidents){
		
		// Appel du constructeur de Sup_AjoutModifIncident
		super(incid,parent,tableIncidents);
		
		this.setTitle("Affichage d'un incident");
		
		// On est dans le cas d'un affichage : on rend les champs texte non éditables
		textColis.setEditable(false);
		comboEtat.setEditable(false);
		comboEtat.setEnabled(false);
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
