package ihm.preparation;

import java.util.Vector;

import donnees.Camion;
import donnees.Entrepot;
import donnees.Preparation;

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
	
	public void ajouterCamion(Preparation preparation){
		Vector courant=preparation.getCamion().toVector();
		courant.add(preparation.getVolume().toString());
		courant.add(preparation.getIdChargementEnCours());
		courant.add(preparation.getIdChargement());
		courant.add(preparation.getId());
		this.volume=new Float(preparation.getVolume().floatValue()+this.volume.floatValue());
		this.listeCamionChargement.add(courant);
	}
	
	public Entrepot getDestination(){
		return this.destination;
	}
	
	public Float getVolume(){
		return this.volume;
	}
	
	public Float getCharge(){
		return this.volumeCharge;
	}
	
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
