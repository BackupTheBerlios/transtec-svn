package donnees;

import java.sql.Timestamp;

public class ModeleColis {

	private Integer id;
	private Integer forme;
	private Integer modele;
	private Integer hauteur;
	private Integer largeur;
	private Integer profondeur;
	private Integer diametre;	
	private float volume;
	
	public ModeleColis(Integer id,Integer forme,Integer modele,Integer hauteur,Integer largeur,Integer profondeur,Integer diametre,float volume){
		this.id=id;
		this.forme=forme;
		this.modele=modele;
		this.hauteur=hauteur;
		this.largeur=largeur;
		this.profondeur=profondeur;
		this.diametre=diametre;
		this.diametre=diametre;
	}
	
	public int SelectionId(int forme,int modele)
	{
		int temp=0;
		
		switch (forme)
		{
			case 0:
				switch(modele)
				{
					case 0: temp = 1;
					break;
					case 1: temp = 2;
					break;
					case 2: temp = 3;
					break;
					case 3: temp = 69;
					break;	
				}
				
			break;
			
			case 1:
				switch(modele)
				{
					case 0: temp = 4;
					break;
					case 1: temp = 5;
					break;
					case 2: temp = 6;
					break;
					case 3: temp = 69;
					break;	
				}
			break;
				
			case 2:
				switch(modele)
				{
					case 0: temp = 7;
					break;
					case 1: temp = 8;
					break;
					case 2: temp = 9;
					break;
					case 3: temp = 69;
					break;	
				}
			break;
		
		}
		
		
		return temp;
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
	public float getVolume(){
		return this.volume;
	}
	
	
	
	
	
	

}
