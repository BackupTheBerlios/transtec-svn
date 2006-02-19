package donnees;


public class ModeleColis {
	private Integer id;
	private Integer forme;
	private Integer modele;
	private Integer hauteur;
	private Integer largeur;
	private Integer profondeur;
	private Integer diametre;
	
	// Constantes définissant la forme du colis
	public final static int PAVE=0;
	public final static int CYLINDRE=1;
	
	// Constructeur avec tous les paramètres
	public ModeleColis(Integer id,Integer forme,Integer modele,Integer hauteur,Integer largeur,Integer profondeur,Integer diametre){
		this.id=id;
		this.forme=forme;
		this.modele=modele;
		this.hauteur=hauteur;
		this.largeur=largeur;
		this.profondeur=profondeur;
		this.diametre=diametre;
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
	public Integer getHauteur(){
		return this.hauteur;
	}
	public Integer getLargeur(){
		return this.largeur;
	}
	public Integer getProfondeur(){
		return this.profondeur;
	}
	public Integer getDiametre(){
		return this.diametre;
	}
	
	// Calcul du volume (en cm3) lié aux données.
	public Integer calculerVolume(){
		Integer vol = new Integer(0);
		
		// On regarde le type de modèle
		switch(forme.intValue()){
		// Cas d'un pavé
		case PAVE:
			vol = Integer.valueOf(largeur.intValue()*hauteur.intValue()*profondeur.intValue());
			break;
		// Cas d'un cylindre
		case CYLINDRE:
			vol = Integer.valueOf((int)Math.round(hauteur.intValue()*Math.PI*Math.pow((double)(diametre.intValue()/2.0),2.0)));
			break;		
		}
		
		return vol;
	}
}
