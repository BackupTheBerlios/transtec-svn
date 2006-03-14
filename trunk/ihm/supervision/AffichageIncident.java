package ihm.supervision;

import donnees.Incident;
import accesBDD.AccesBDDIncident;

public class AffichageIncident extends AjoutModifIncident{

	//Constructeur
	public AffichageIncident(Incident incid, OngletIncident parent, AccesBDDIncident tableIncidents){
		
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
