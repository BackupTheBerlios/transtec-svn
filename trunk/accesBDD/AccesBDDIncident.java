package accesBDD;

import donnees.Incident;
import java.sql.*;
import java.util.Vector;

//----- Classe permettant l'accès à la table Incident, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDIncident extends AccesBDD{
	public AccesBDDIncident(){
		super();
	}
	
	//----- Ajouter un incident dans la BDD -----//
	public Integer ajouter(Incident aAjouter) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=connecter().prepareStatement("SELECT MAX(idIncidents) FROM incidents");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =connecter().prepareStatement(
				"INSERT INTO incidents "
				+ "(idIncidents, Colis_idColis, Users_idUsers, Description, DateCreation, Type_2, Etat) " // Parametre de la table
				+ "VALUES (?,?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setInt(2,aAjouter.getColis().getId().intValue()); // Renvoi l'ID du Colis
		ajout.setInt(3,aAjouter.getUtilisateur().getId().intValue()); // Renvoi l'ID de l'utilisateur
		ajout.setString(4,aAjouter.getDescription());
		ajout.setTimestamp(5,aAjouter.getDate());
		ajout.setInt(6,aAjouter.getType().intValue());
		ajout.setInt(7,aAjouter.getEtat().intValue());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		deconnecter();
		
		return aAjouter.getId();
	}
	
	//----- Supprimer un incident de la BDD -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		PreparedStatement supprime=connecter().prepareStatement("DELETE FROM incidents WHERE idIncidents=?");
		supprime.setInt(1, aSupprimer.intValue());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		supprime.close();	// Fermeture requête SQL
		deconnecter();
	}
	
	//----- Changer état incident vers état supérieur -----//
	public void changerEtat(Incident etatAChanger) throws SQLException{
		if(etatAChanger.getEtat().intValue()<Incident.TRAITE){
			PreparedStatement modifie=connecter().prepareStatement("UPDATE incidents SET Etat=? WHERE idIncidents=?");
		
			modifie.setInt(1, etatAChanger.getEtat().intValue()+1);
			modifie.setInt(2, etatAChanger.getId().intValue());
				
			modifie.executeUpdate();	// Exécution de la requête SQL
			modifie.close();
			deconnecter();
		}		
	}
	
	//----- Lister les incidents -----//
	public Vector lister() throws SQLException{
		Vector liste=new Vector();
		AccesBDDUtilisateur bddUtilisateur=new AccesBDDUtilisateur();
		AccesBDDColis bddColis=new AccesBDDColis();
				
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM incidents ");
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			liste.add(new Incident(
					new Integer(resultat.getInt("idIncidents")),
					bddColis.rechercher(new Integer(resultat.getInt("Colis_idColis"))),
					resultat.getTimestamp("DateCreation"),
					new Integer(resultat.getInt("Etat")),
					resultat.getString("Description"), 
					bddUtilisateur.rechercher(new Integer(resultat.getInt("Users_idUsers"))),
					new Integer(resultat.getInt("Type_2"))));
		}
				
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		deconnecter();
		
		return liste;
	}

	//----- TESTS OKAY -----//
	/*public static void main(String arg[]){
		AccesBDDIncident test=new AccesBDDIncident();
		Timestamp date=new Timestamp(System.currentTimeMillis());
		Incident aAjouter = new Incident(new Integer(3),date,new Integer(1),"Description",new Integer(1),new Integer(1));
		Incident aModifier=new Incident(new Integer(1),date,new Integer(1),"Description2",new Integer(1),new Integer(1));
		try{
			test.ajouter(aAjouter);
			aModifier.setId(aAjouter.getId());
			test.supprimer(aAjouter.getId());
			test.ajouter(aAjouter);
			test.ajouter(aModifier);
			Vector liste=null;
			liste=test.lister();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}*/
}
