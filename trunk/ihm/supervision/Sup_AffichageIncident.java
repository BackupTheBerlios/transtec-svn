package ihm.supervision;

import donnees.Incident;
import accesBDD.AccesBDDIncident;

public class Sup_AffichageIncident extends Sup_AjoutModifIncident{

	//Constructeur
	public Sup_AffichageIncident(Incident incid, Sup_OngletIncident parent, AccesBDDIncident tableIncidents){
		
		// Appel du constructeur de Sup_AjoutModifIncident
		super(incid,parent,tableIncidents);
		
		this.setTitle("Affichage d'un incident");
		
		// On est dans le cas d'un affichage : on rend les champs texte non éditables
		textColis.setEditable(false);
		textDate.setEditable(false);
		textDescription.setEditable(false);
		textType.setEditable(false);
		textUtilisateur.setEditable(false);
		textWarning.setVisible(false);
		
		pack();
	}
}
