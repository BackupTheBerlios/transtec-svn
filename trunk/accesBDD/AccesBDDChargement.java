package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import donnees.Chargement;

//----- Classe permettant l'accès à la table Chargement, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDChargement {
	//	----- Ajouter un colis dans la BDD -----//
	public long ajouter(Chargement aAjouter, ConnecteurSQL connecteur) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idChargement) FROM chargement");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(resultat.getInt(1)+1); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO chargement"
				+ " (idChargement,Camions_idCamions,Colis_idColis)" // Parametre de la table
				+ " VALUES (?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId());
		ajout.setInt(2,aAjouter.getIdCamion());
		//------->PBajout.setInt(3,aAjouter.getidColis);
				
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		
		return aAjouter.getId();
	}

	/*public static void main(String arg[]){
		AccesBDDChargement test=new AccesBDDChargement();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		Timestamp date=new Timestamp(10);
		Chargement aAjouter = new Chargement(0,1,1,1,date);
		try{
			test.ajouter(aAjouter,connecteur);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}*/
}
