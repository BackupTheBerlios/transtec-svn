package accesBDD;

import donnees.Incident;
import java.sql.*;
import java.util.Vector;

//----- Classe permettant l'acc�s � la table Incident, elle permet de faire les diff�rentes op�rations n�cessaire sur la table -----//

public class AccesBDDIncident extends ConnecteurSQL{
	//----- Ajouter un incident dans la BDD -----//
	public long ajouter(Incident aAjouter) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idIncidents) FROM incidents");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(resultat.getInt(1)+1); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO incidents "
				+ "(idIncidents, Colis_idColis, Users_idUsers, Description, DateCreation, Type_2, Etat) " // Parametre de la table
				+ "VALUES (?,?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId());
		ajout.setInt(2,aAjouter.getIdColis()); // Renvoi l'ID du Colis
		ajout.setInt(3,aAjouter.getIdUtilisateur()); // Renvoi l'ID de l'utilisateur
		ajout.setString(4,aAjouter.getDescription());
		ajout.setTimestamp(5,aAjouter.getDate());
		ajout.setInt(6,aAjouter.getType());
		ajout.setInt(7,aAjouter.getEtat());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		return aAjouter.getId();

	}
	
	//----- Supprimer un incident de la BDD -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		PreparedStatement supprime=
			connecteur.getConnexion().prepareStatement(
				"DELETE FROM incidents WHERE idIncidents=?");
		supprime.setInt(1, aSupprimer.intValue());
				
		supprime.executeUpdate();	// Ex�cution de la requ�te SQL
						
		supprime.close();	// Fermeture requ�te SQL
	}
	
	//----- Changer �tat incident vers �tat sup�rieur -----//
	public void changerEtat(Incident etatAChanger) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		PreparedStatement modifie=connecteur.getConnexion().prepareStatement(
			"UPDATE incidents SET Type_2=? WHERE idIncidents=?");
	
		modifie.setInt(1, etatAChanger.getType().intValue()+1);
		modifie.setInt(2, etatAChanger.getId());
			
		modifie.executeUpdate();	// Ex�cution de la requ�te SQL
		modifie.close();
		// Attention cas derni�re �tat!!!!
	}
	
	//----- Lister les incidents -----//
	public Vector lister() throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		Vector liste=new Vector();
		AccesBDDPersonnes_has_Colis idPers=new AccesBDDPersonnes_has_Colis();
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM incidents ");
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		while(resultat.next()){
			Incident courant=new Incident(resultat.getInt("Colis_idColis"),resultat.getTimestamp("DateCreation"),
					resultat.getInt("Etat"),resultat.getString("Description"), 
					resultat.getInt("Users_idUsers"), resultat.getInt("Type_2"));
			courant.setId(resultat.getInt("idIncidents"));
			liste.add(courant);
		}
				
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		return liste;
	}

	//----- TESTS OKAY -----//
	public static void main(String arg[]){
		AccesBDDIncident test=new AccesBDDIncident();
		Timestamp date=new Timestamp(10);
		Incident aAjouter = new Incident(1,date,1,"Description",1,1);
		Incident aModifier=new Incident(1,date,1,"Description2",1,2);
		try{
			test.ajouter(aAjouter);
			aModifier.setId(aAjouter.getId());
			test.supprimer(aAjouter.getId());
			test.ajouter(aAjouter);
			test.ajouter(aModifier);
			Vector liste=null;
			liste=test.lister();
			int i=0;
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
