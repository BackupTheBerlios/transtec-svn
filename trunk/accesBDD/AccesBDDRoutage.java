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

	public Integer ajouter(Route r){
		Integer ID = new Integer(-1);
		
		return ID;
	}
	
	public void modifier(Route r){
		
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
}
