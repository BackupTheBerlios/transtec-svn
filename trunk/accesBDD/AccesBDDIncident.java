package accesBDD;

import donnees.Incident;
import java.sql.*;
//import java.util.Date;

//----- Classe permettant l'accès à la table Incident, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDIncident implements AccesBDD {
	//----- Ajouter un incident dans la BDD -----//
	public long ajouter(Incident aAjouter, ConnecteurSQL connecteur) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idIncidents) FROM incidents");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO incidents"
				+ " (idIncidents, Colis_idColis,Users_idUsers,Description, DateCreation, Type_2, Etat)" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?,?)"); 
		
		ajout.setLong(1,aAjouter.getId().intValue());
		ajout.setLong(2,aAjouter.getIdColis().intValue()); // Renvoi l'ID du Colis
		ajout.setLong(3,aAjouter.getIdUtilisateur().intValue()); // Renvoi l'ID de l'utilisateur
		ajout.setString(4,aAjouter.getDescription());
		//ajout.setDate(5,aAjouter.getDate());
		ajout.setTimestamp(5,aAjouter.getDate());
		ajout.setInt(6,aAjouter.getType().intValue());
		ajout.setInt(7,aAjouter.getEtat().intValue());
		//ajout.setTimestamp(5,)
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		return aAjouter.getId().intValue();

	}
	
	//----- Supprimer un incident de la BDD -----//
	public boolean supprimer(Incident aSupprimer){
		return true;
	}
	
	//----- Changer état incident -----//
	public boolean changerEtat(int nouvelEtat, Incident aModifier){
		return true;
	}

public static void main(String arg[]){
		AccesBDDIncident test=new AccesBDDIncident();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		Timestamp date=new Timestamp(10);
		Incident aAjouter = new Incident(new Integer(1),new Integer(46468468),new Timestamp(2005,05,05,03,03,03,03),new Integer(0),"problème sur le colis",new Integer(2),new Integer(0));
		try{
			test.ajouter(aAjouter,connecteur);
			System.out.println("ici");
		}
		catch(SQLException e){
			
			System.out.println(e.getMessage());
		}
	}
}
