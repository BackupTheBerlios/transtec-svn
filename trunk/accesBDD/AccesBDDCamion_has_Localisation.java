package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//----- Classe permettant l'accès à la table Camion_has_Localisation, liant un camion à une origine et une destination -----//

public class AccesBDDCamion_has_Localisation extends ConnecteurSQL{
	public final static boolean ORIGINE=true;
	public final static boolean DESTINATION=false;
	
	//----- Fonction permettant l'ajout d'une ligne dans la table Personnes_has_Colis -----//
	public void ajouter(Integer idCamion, Integer idOrigine, Integer idDestination) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		//----- Insertion de la relation entre un camion et une origine dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO camions_has_localisation"
				+ " (Camions_idCamions,Localisation_idLocalisation,Origine)" // Parametre de la table
				+ " VALUES (?,?,?)"); 
		
		ajout.setInt(1,idCamion.intValue());
		ajout.setInt(2,idOrigine.intValue());
		ajout.setBoolean(3,ORIGINE);
				
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		
		//----- Insertion de la relation entre un camion et une destination dans la BDD -----//
		ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO camions_has_localisation"
				+ " (Camions_idCamions,Localisation_idLocalisation,Origine)" // Parametre de la table
				+ " VALUES (?,?,?)"); 
		
		ajout.setInt(1,idCamion.intValue());
		ajout.setInt(2,idDestination.intValue());
		ajout.setBoolean(3,DESTINATION);
				
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
	}
	
	//----- Recherche de l'origine ou la destination d'un camion -----//
	public Integer recherche(boolean type, Integer idCamion) throws SQLException{
		Integer trouve=null;
		ConnecteurSQL connecteur=new ConnecteurSQL();
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
				"SELECT Localisation_idLocalisation WHERE Camions_idCamions=? AND Origine=? ");
		recherche.setInt(1, idCamion.intValue());
		recherche.setBoolean(2, type);
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		if(resultat.next())	trouve=new Integer(resultat.getInt("Localisation_idLocalisation"));
		return trouve;
	}
	
	//----- Modifier l'origine ou la destination d'un camion -----//
	public void modifier(Integer idCamion, Integer idOrigine, Integer idDestination) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		//----- Insertion de la relation entre un camion et une origine dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"UPDATE camions_has_localisation SET"
				+ " Camions_idCamions,Localisation_idLocalisation,Origine" // Parametre de la table
				+ " VALUES (?,?,?)"); 
		ajout.setInt(1,idCamion.intValue());
		ajout.setInt(2,idOrigine.intValue());
		ajout.setBoolean(3,ORIGINE);
				
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		
		//----- Insertion de la relation entre un camion et une destination dans la BDD -----//
		ajout =connecteur.getConnexion().prepareStatement(
				"UPDATE camions_has_localisation SET"
				+ " Camions_idCamions,Localisation_idLocalisation,Origine" // Parametre de la table
				+ " VALUES (?,?,?)");		
		ajout.setInt(1,idCamion.intValue());
		ajout.setInt(2,idDestination.intValue());
		ajout.setBoolean(3,DESTINATION);
				
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
	}
	
	/*public static void main(String arg[]){
		AccesBDDCamion_has_Localisation test=new AccesBDDCamion_has_Localisation();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		//Timestamp date=new Timestamp(10);
		Camion aAjouter = new Camion(0,3,3,3,32);
		try{
			test.ajouter(aAjouter.getId(), aAjouter.getIdOrigine(), aAjouter.getIdDestination(),connecteur);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}*/
}
