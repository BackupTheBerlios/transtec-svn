package donnees;


public class ModeleColis {

	private Integer id;
	private Integer forme;
	private Integer modele;
	private Integer hauteur;
	private Integer largeur;
	private Integer profondeur;
	private Integer diametre;	
	private Integer volume;// en cm3
	
	// Constructeur avec tous les paramètres
	public ModeleColis(Integer id,Integer forme,Integer modele,Integer hauteur,Integer largeur,Integer profondeur,Integer diametre,Integer volume){
		this.id=id;
		this.forme=forme;
		this.modele=modele;
		this.hauteur=hauteur;
		this.largeur=largeur;
		this.profondeur=profondeur;
		this.diametre=diametre;
		this.volume=volume;
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
	public Integer getVolume(){
		return this.volume;
	}
}
