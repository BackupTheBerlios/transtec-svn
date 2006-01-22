package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import donnees.Camion;

//----- Classe permettant l'acc�s � la table Camion, elle permet de faire les diff�rentes op�rations n�cessaire sur la table -----//

public class AccesBDDCamion implements AccesBDD{
	//----- Ajouter un camion dans la BDD -----//
	public long ajouter(Camion aAjouter, ConnecteurSQL connecteur) throws SQLException{
		
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idCamions) FROM camions");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		//----- Insertion d'un camion dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO camions"
				+ " (idCamions,Personnes_idPersonnes,Etat,Volume)" // Param�tre de la table
				+ " VALUES (?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId());
		ajout.setInt(2,aAjouter.getIdChauffeur());
		ajout.setInt(3,aAjouter.getEtat());
		ajout.setFloat(4,aAjouter.getVolume());
		
		ajout.executeUpdate();	// Execution de la requ�te SQL
		ajout.close();	// Fermeture requ�te SQL
		
		/*----- Ajout de la relation entre l'origine/destination et le camion dans la 
				table camions_has_localisation -----*/
		AccesBDDCamion_has_Localisation rel=new AccesBDDCamion_has_Localisation();
		rel.ajouter(aAjouter.getId(), aAjouter.getIdOrigine(), aAjouter.getIdDestination(), connecteur);
		
		return aAjouter.getId();
	}

public static void main(String arg[]){
		AccesBDDCamion test=new AccesBDDCamion();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		//Timestamp date=new Timestamp(10);
		Camion aAjouter = new Camion(0,2,2,21,1);
		try{
			test.ajouter(aAjouter,connecteur);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
