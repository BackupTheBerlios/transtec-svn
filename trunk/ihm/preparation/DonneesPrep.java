package ihm.preparation;

import java.util.Vector;

import donnees.Camion;
import donnees.Entrepot;

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
	
	public void ajouterCamion(Camion aAjouter, Float volume){
		Vector courant=aAjouter.toVector();
		courant.add(volume.toString());
		courant.add("");
		this.volume+=volume;
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
}
