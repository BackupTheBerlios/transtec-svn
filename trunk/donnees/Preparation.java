package donnees;

import java.util.Vector;

public class Preparation {
	public final static int A_FAIRE=0;
	public final static int EN_COURS=1;
	
	private Integer id;
	private Utilisateur utilisateur;
	private Entrepot destination, origine;
	private Float volume;
	private Camion camion;
	private Camion aCharger;
	private Integer VolumeColis;
	private Integer etat;
	
	
	public Preparation(Utilisateur utilisateur, Entrepot origine, Entrepot destination, Float volume, Camion camion, Integer etat){
		this.utilisateur=utilisateur;
		this.origine=origine;
		this.destination=destination;
		this.volume=volume;
		this.camion=camion;
		this.aCharger=null;
		this.VolumeColis=new Integer(0);
		this.etat=etat;
	}
	
	public Preparation(Integer id, Utilisateur utilisateur, Entrepot origine, Entrepot destination, Float volume, Camion camion, Integer etat){
		this.id=id;
		this.utilisateur=utilisateur;
		this.origine=origine;
		this.destination=destination;
		this.volume=volume;
		this.camion=camion;
		this.aCharger=null;
		this.VolumeColis=new Integer(0);
		this.etat=etat;
	}
	
	public Integer getId(){
		return this.id;
	}
	
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}
	
	public Entrepot getDestination(){
		return this.destination;
	}
	
	public Float getVolume(){
		return this.volume;
	}
	
	public Camion getCamion(){
		return this.camion;
	}
	
	public Entrepot getOrigine(){
		return this.origine;
	}
	
	public void setDestination(Entrepot destination){
		this.destination=destination;
	}
	
	public Vector toVector(){
		Vector vector=new Vector();
		
		vector.add(this.id);
		vector.add(this.utilisateur);
		vector.add(this.origine);
		vector.add(this.origine.getLocalisation().getVille());
		vector.add(this.destination);
		vector.add(this.destination.getLocalisation().getVille());
		vector.add(this.camion);
		vector.add(this.camion.getNumero());
		vector.add(this.VolumeColis.floatValue());
		if(this.etat==A_FAIRE)	vector.add("A faire");
		else	vector.add("En cours");
		
		return vector;
	}
	
	public void setVolumeChargement(Float volume){
		this.volume=volume;
	}
	
	public void setUtilisateur(Utilisateur utilisateur){
		this.utilisateur=utilisateur;
	}
	
	public void setCamionACharger(Camion aCharger){
		this.aCharger=aCharger;
	}
	
	public Camion getCamionACharger(){
		return this.aCharger;
	}
	
	public void ajouterVolumeColis(Integer Volume){
		
		this.VolumeColis=new Integer(Volume.intValue()+this.VolumeColis.intValue());
	}
	
	public void soustraireVolumeColis(Integer Volume){
		this.VolumeColis=new Integer(this.VolumeColis.intValue()-Volume.intValue());
	}
	
	public Integer getVolumeColis(){
		return this.VolumeColis;
	}
	
	public Integer getEtat(){
		return this.etat;
	}
	
	public void initializeChargement(){
		this.aCharger=null;
		this.VolumeColis=new Integer(0);
	}
	
	public void setId(Integer id){
		this.id=id;
	}
}
