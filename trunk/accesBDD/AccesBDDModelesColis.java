package accesBDD;
import donnees.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccesBDDModelesColis extends AccesBDD{
	public AccesBDDModelesColis(){
		super();
	}
	
	//----- Ajouter une nouvelle forme (cube) -----//
	public int ajouter(ModeleColis aAjouter)throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=connecter().prepareStatement("SELECT MAX(idModelesColis ) FROM modelescolis");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion d'une personne dans la BDD -----//
		PreparedStatement ajout =connecter().prepareStatement(
				"INSERT INTO modelescolis"
				+ " (idModelesColis,Forme,Modele,hauteur,largeur,Profondeur,Diametre,Volume)" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setInt(2,aAjouter.getForme().intValue());
		ajout.setInt(3,aAjouter.getModele().intValue());
		ajout.setInt(4,aAjouter.getHauteur().intValue());
		ajout.setInt(5,aAjouter.getLargeur().intValue());
		ajout.setInt(6,aAjouter.getProfondeur().intValue());
		ajout.setInt(7,aAjouter.getDiametre().intValue());
		ajout.setInt(8,aAjouter.calculerVolume().intValue());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		deconnecter();
		return aAjouter.getId().intValue();
	}
	
	public ModeleColis rechercher(Integer aChercher) throws SQLException{
		ModeleColis trouvee=null;
		//AccesBDDPersonne bddPersonne=new AccesBDDPersonne();
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM modelescolis WHERE idModelesColis=?");
		recherche.setInt(1, aChercher.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next()){	// S'il a trouvé le colis
			//public ModeleColis(Integer id,Integer forme,Integer modele,Integer hauteur,Integer largeur,Integer profondeur,Integer diametre,Integer volume){
			trouvee=new ModeleColis(
					new Integer(resultat.getInt("idModelesColis")),
					new Integer(resultat.getInt("Forme")),
					new Integer(resultat.getInt("Modele")),
					new Integer(resultat.getInt("hauteur")),
					new Integer(resultat.getInt("largeur")),
					new Integer(resultat.getInt("Profondeur")),
					new Integer(resultat.getInt("Diametre")));
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		deconnecter();
		
		return trouvee;
	}	
}
