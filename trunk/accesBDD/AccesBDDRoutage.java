package accesBDD;

import java.sql.*;
import java.util.Vector;

import donnees.Route;

/*
 * Classe permettant l'acc�s � la table de routage
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
		
		// Pr�paration de la requ�te SQL
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM routage");
		
		// Ex�cution de la requ�te SQL
		ResultSet resultat = recherche.executeQuery();
		
		// On remplit me vector avec le r�sultat de la requ�te
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
