package accesBDD;
import donnees.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccesBDDModelesColis {
	
	//----- Ajouter une nouvelle forme (cube) -----//
	public int ajouter(ModeleColis aAjouter)throws SQLException{
		
		ConnecteurSQL connecteur=new ConnecteurSQL();
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idModelesColis ) FROM modelescolis");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion d'une personne dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
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
		ajout.setInt(8,aAjouter.getVolume().intValue());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		return aAjouter.getId().intValue();
	}
	
	
	
	
}
