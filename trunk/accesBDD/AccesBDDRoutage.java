package accesBDD;

import java.sql.*;
import java.util.Vector;

import donnees.Route;

/*
 * Classe permettant l'accès à la table de routage
 */

public class AccesBDDRoutage extends AccesBDD{
	public AccesBDDRoutage(){
		super();
	}

	//----- Méthode permettant de modier une route dans la BDD -----//
	public void modifier(Route aModifier) throws SQLException{
		PreparedStatement modifie=connecter().prepareStatement(
				"UPDATE routage SET Origine=?,Destination=?,PlatInter=?,Distance=? "
				+"WHERE idRoutage=?");
		modifie.setInt(1, aModifier.getOrigine().getId().intValue());
		modifie.setInt(2, aModifier.getDestination().getId().intValue());
		modifie.setInt(3, aModifier.getIntermediaire().getId().intValue());
		modifie.setFloat(4, aModifier.getDistance().floatValue());
		modifie.setInt(5, aModifier.getId().intValue());
		
		modifie.executeUpdate();	// Exécution de la requête SQL
		
		modifie.close();	// Fermeture requête SQL
		deconnecter();
	}
	
	//----- Lister les routes -----//
	public Vector lister() throws SQLException{
		Vector liste=new Vector();
		
		// Préparation de la requête SQL
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM routage");
		
		// Exécution de la requête SQL
		ResultSet resultat = recherche.executeQuery();
		
		// On remplit me vector avec le résultat de la requête
		while(resultat.next()){
			liste.add(new Route(
					new Integer(resultat.getInt("idRoutage")),
					new AccesBDDEntrepot().rechercher(new Integer(resultat.getInt("Origine"))), 
					new AccesBDDEntrepot().rechercher(new Integer(resultat.getInt("Destination"))), 
					new AccesBDDEntrepot().rechercher(new Integer(resultat.getInt("PlatInter"))),
					new Float(resultat.getFloat("Distance"))));
		}
		
		// Fermeture des connexions
		resultat.close();
		recherche.close();
		deconnecter();
		
		return liste;
	}
	
	//----- Ajouter une route dans la BDD -----//
	public Integer ajouter(Route aAjouter) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=connecter().prepareStatement("SELECT MAX(idCamions) FROM Camions ");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion d'un camion dans la BDD -----//
		PreparedStatement ajout =connecter().prepareStatement(
				"INSERT INTO routage "
				+ " (idRoutage,Origine,Destination,PlatInter,Distance)" // Paramètre de la table
				+ " VALUES (?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setInt(2,aAjouter.getOrigine().getId().intValue());
		ajout.setInt(3, aAjouter.getDestination().getId().intValue());
		ajout.setInt(4, aAjouter.getIntermediaire().getId().intValue());
		ajout.setFloat(5,aAjouter.getDistance().floatValue());
				
		ajout.executeUpdate();	// Execution de la requête SQL
		ajout.close();	// Fermeture requête SQL
		deconnecter();
		
		return aAjouter.getId();
	}
	
	//----- Supprimer une route -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		PreparedStatement supprime=connecter().prepareStatement("DELETE FROM routage WHERE idRoutage=?");
		supprime.setInt(1, aSupprimer.intValue());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		supprime.close();	// Fermeture requête SQL
		deconnecter();
	}
}
