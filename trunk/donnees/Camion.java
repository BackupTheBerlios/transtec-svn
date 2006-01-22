package donnees;
import java.util.Vector;

public class Camion {
	public Integer id;
	public String numero;
	public String dispo;
	public Integer volume;
	public String chauffeur;
	public String origine;
	public String destination;

	// Constructeur vide
	public Camion(){
	}

	// Constructeur utilisant des paramètres pour chaque champ
	public Camion(Integer id, String numero, String dispo, Integer volume, String chauffeur, String destination, String origine){
		this.id = id;
		this.numero=numero;
		this.dispo=dispo;
		this.volume=volume;
		this.chauffeur=chauffeur;
		this.origine=origine;
		this.destination=destination;
	}

	// Constructeur utilisant un Vector
	public Camion(Vector v){
		this.id = (Integer)v.get(0);
		this.numero=(String)v.get(1);
		this.dispo=(String)v.get(2);
		this.volume=(Integer)v.get(3);
		this.chauffeur=(String)v.get(4);
		this.destination=(String)v.get(5);
		this.origine=(String)v.get(6);
	}

	// Transforme l'objet en un Vector
	public Vector toVector(){
		Vector v = new Vector();

		// ATTENTION l'ordre est très important !!
		// l'ordre doit être :
		// id, numero, dispo, volume, chauffeur, destination, origine
		v.add(id);
		v.add(numero);
		v.add(dispo);
		v.add(volume);
		v.add(chauffeur);
		v.add(destination);
		v.add(origine);

		return v;
	}
	
	/****** Méthodes d'écriture ******/
	
	//----- Insérer l'id du camion -----//
	public void setId(Integer id){
		this.id=id;
	}
	
	/****** Méthodes de lecture ******/


}
