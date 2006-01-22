package accesBDD;

import java.sql.PreparedStatement;
import java.sql.SQLException;

//----- Classe permettant l'accès à la table Camion_has_Localisation, liant un camion à une origine et une destination -----//

public class AccesBDDCamion_has_Localisation {
	//----- Fonction permettant l'ajout d'une ligne dans la table Personnes_has_Colis -----//
	public void ajouter(int idCamion, int idOrigine, int idDestination, ConnecteurSQL connecteur) throws SQLException{
		//----- Insertion de la relation entre un camion et une origine dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO camions_has_localisation"
				+ " (Camions_idCamions,Localisation_idLocalisation,Origine)" // Parametre de la table
				+ " VALUES (?,?,?)"); 
		
		ajout.setInt(1,idCamion);
		ajout.setInt(2,idOrigine);
		ajout.setBoolean(3,true);
				
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		
		//----- Insertion de la relation entre un camion et une destination dans la BDD -----//
		ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO camions_has_localisation"
				+ " (Camions_idCamions,Localisation_idLocalisation,Origine)" // Parametre de la table
				+ " VALUES (?,?,?)"); 
		
		ajout.setInt(1,idCamion);
		ajout.setInt(2,idDestination);
		ajout.setBoolean(3,false);
				
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
