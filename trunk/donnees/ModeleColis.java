package donnees;


public class ModeleColis {
	private Integer id;
	private Integer forme;
	private Integer modele;
	private Float hauteur;
	private Float largeur;
	private Float profondeur;
	
	// Constantes définissant la forme du colis
	public final static int CUBE=0;
	public final static int PAVE=1;
	
	// Constructeur avec tous les paramètres
	public ModeleColis(Integer id,Integer forme,Integer modele,Float hauteur,Float largeur,Float profondeur){
		this.id=id;
		this.forme=forme;
		this.modele=modele;
		this.hauteur=hauteur;
		this.largeur=largeur;
		this.profondeur=profondeur;
	}
	
	public ModeleColis(Integer forme,Integer modele){
		this.forme=forme;
		this.modele=modele;
	}
	
	public void setId(Integer id){
		this.id=id;
	}
	
	public Integer getId(){
		return this.id;
	}
	public Integer getForme(){
		return this.forme;
	}
	public Integer getModele(){
		return this.modele;
	}
	public Float getHauteur(){
		return this.hauteur;
	}
	public Float getLargeur(){
		return this.largeur;
	}
	public Float getProfondeur(){
		return this.profondeur;
	}

	// Calcul du volume (en cm3) lié aux données.
	public Float calculerVolume(){
		Float vol = new Float(0);
		
		// On regarde le type de modèle
		switch(forme.intValue()){
		// Cas d'un pavé
		case CUBE:
			vol = new Float (hauteur.floatValue()*hauteur.floatValue()*hauteur.floatValue());
			break;
			
		case PAVE:

			vol = new Float(largeur.floatValue()*hauteur.floatValue()*profondeur.floatValue());
			break;
		}
		return vol;
	}
}
