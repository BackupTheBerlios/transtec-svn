package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import donnees.Camion;

//----- Classe permettant l'accès à la table Camion, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDCamion implements AccesBDD{
	//----- Ajouter un camion dans la BDD -----//
	public Integer ajouter(Camion aAjouter, ConnecteurSQL connecteur) throws SQLException{
		
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idCamions) FROM camions");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion d'un camion dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO camions"
				+ " (idCamions,Personnes_idPersonnes,Etat,Volume)" // Paramètre de la table
				+ " VALUES (?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setInt(2,aAjouter.getIdChauffeur().intValue());
		ajout.setInt(3,aAjouter.getDispo().intValue());
		ajout.setFloat(4,aAjouter.getVolume().intValue());
		
		ajout.executeUpdate();	// Execution de la requête SQL
		ajout.close();	// Fermeture requête SQL
		
		/*----- Ajout de la relation entre l'origine/destination et le camion dans la 
				table camions_has_localisation -----*/
		AccesBDDCamion_has_Localisation rel=new AccesBDDCamion_has_Localisation();
		rel.ajouter(aAjouter.getId(), aAjouter.getIdOrigine(), aAjouter.getIdDestination(), connecteur);
		
		return aAjouter.getId();
	}
	
	//----- Supprimer un camion -----//
	public void supprimer(Camion aSupprimer, ConnecteurSQL connecteur) throws SQLException{
		PreparedStatement supprime=
			connecteur.getConnexion().prepareStatement(
				"DELETE FROM camions WHERE idCamions=?");
		supprime.setInt(1, aSupprimer.getId().intValue());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		supprime.close();	// Fermeture requête SQL
	}

public static void main(String arg[]){
		AccesBDDCamion test=new AccesBDDCamion();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		Camion aAjouter = new Camion("1013TW78",new Integer(0),new Integer(2),new Integer(2),new Integer(21),new Integer(1));
		try{
			test.ajouter(aAjouter,connecteur);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
