package donnees;

public class Preparation {
	private Utilisateur utilisateur;
	private Entrepot destination, origine;
	private Integer volumeChargement;
	private Camion camion;
	private Camion aCharger;
	private Integer VolumeColis;
	private Integer etat;
	
	public Preparation(Utilisateur utilisateur, Entrepot destination, Entrepot origine, Integer volumeChargement, Camion camion, Integer etat){
		this.utilisateur=utilisateur;
		this.origine=origine;
		this.destination=destination;
		this.volumeChargement=volumeChargement;
		this.camion=camion;
		this.aCharger=null;
		this.VolumeColis=new Integer(0);
		this.etat=etat;
	}
	
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}
	
	public Entrepot getDestination(){
		return this.destination;
	}
	
	public Integer getVolumeChargement(){
		return this.volumeChargement;
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
	
	public void setVolumeChargement(Integer volumeChargement){
		this.volumeChargement=volumeChargement;
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
}
