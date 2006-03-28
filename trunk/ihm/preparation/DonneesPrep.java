package ihm.preparation;

import java.sql.SQLException;
import java.util.Vector;

import accesBDD.AccesBDDChargement;

import donnees.Camion;
import donnees.Entrepot;
import donnees.Preparation;

/*
 * Classe regroupant tous les paramètres nécéssaires à préparer une destination
 */

public class DonneesPrep {
	private Entrepot destination;
	private Float volume, volumeCharge;
	private Vector listeCamionChargement;
	
	public DonneesPrep(Entrepot destination){
		this.destination=destination;
		this.volume=new Float(0);
		this.volumeCharge=new Float(0);
		this.listeCamionChargement=new Vector();
	}
	
	// Méthode permettant d'ajouter un camions dans liste de camions et de faire varier le
	// volume pour la destination, ceci en rapport avec l'objet de type "preparation"
	public void ajouterCamion(Preparation preparation){
		AccesBDDChargement bddChargement=new AccesBDDChargement();
		Vector courant=preparation.getCamion().toVector();
		// Le vector courant servira à afficher le tableau des cmaions pour une destination donnée
		courant.add(preparation.getVolume().toString());
		courant.add(preparation.getIdChargementEnCours());
		courant.add(preparation.getIdChargement());
		if(preparation.getIdChargementEnCours().intValue()!=0){
			courant.add("En Cours");
			try {
				this.volumeCharge=new Float(this.volumeCharge.floatValue()+bddChargement.volume(preparation.getIdChargementEnCours()).floatValue());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			if(preparation.getIdChargement().intValue()!=0){
				courant.add("Validé");
				try {
					this.volumeCharge=new Float(this.volumeCharge.floatValue()+bddChargement.volume(preparation.getIdChargement()).floatValue());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
				courant.add("A préparer");
		}
		courant.add(preparation.getId());
		this.volume=new Float(preparation.getVolume().floatValue()+this.volume.floatValue());
		this.listeCamionChargement.add(courant);
	}
	// Récupération de la destination
	public Entrepot getDestination(){
		return this.destination;
	}
	
	// Récupération du volume
	public Float getVolume(){
		return this.volume;
	}
	
	// Récuparation du volume chargé
	public Float getCharge(){
		return this.volumeCharge;
	}
	
	// Récupération de la liste de camion
	public Vector getListeCamionChargement(){
		return this.listeCamionChargement;
	}
	
	// Donne le camion à partir de l'idCamion
	public Camion getCamion(Integer idCamion){
		Camion camion=null;
		Vector courant=null;
		
		for(int i=0;i<this.listeCamionChargement.size();i++){
			courant=(Vector)this.listeCamionChargement.get(i);
			if(idCamion.intValue()==((Integer)courant.get(0)).intValue()){
				camion=new Camion(courant);
				i=this.listeCamionChargement.size();
			}
		}
		return camion;
	}
	
	//Donne le volume à partir de l'idCamion
	public Float getVolume(Integer idCamion){
		Float volume=null;
		Vector courant=null;
		
		for(int i=0;i<this.listeCamionChargement.size();i++){
			courant=(Vector)this.listeCamionChargement.get(i);
			if(idCamion.intValue()==((Integer)courant.get(0)).intValue()){
				volume=new Float(courant.get(9).toString());
				i=this.listeCamionChargement.size();
			}
		}
		return volume;
	}
}
