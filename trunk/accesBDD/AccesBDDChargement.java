package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import donnees.Chargement;
import donnees.Colis;

//----- Classe permettant l'accès à la table Chargement, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDChargement extends ConnecteurSQL{
	//	----- Ajouter un colis dans la BDD -----//
	public Integer ajouter(Chargement aAjouter) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idChargement) FROM chargement");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO chargement"
				+ " (idChargement,Camions_idCamions, NbColis, VolChargement, DateCreation, Users_idUsers)" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?)"); 
		
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setInt(2,aAjouter.getIdCamion().intValue());
		ajout.setInt(3, aAjouter.getNbColis().intValue());
		ajout.setFloat(4, aAjouter.getVolChargement());
		ajout.setTimestamp(5, aAjouter.getDate());
		ajout.setInt(6, aAjouter.getIdUtilisateur().intValue());
				
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		
		return aAjouter.getId();
	}
	
	//----- Supprimer un chargement -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		PreparedStatement supprime=
			connecteur.getConnexion().prepareStatement(
				"DELETE FROM chargement WHERE idChargement=?");
		supprime.setInt(1, aSupprimer.intValue());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		supprime.close();	// Fermeture requête SQL
	}
	
	//----- Lister le colis appartenant à un chargement -----//
	public Vector listerColis(Integer chargement) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		Vector liste=new Vector();
		String idChargement="C-"+chargement.intValue();
		AccesBDDPersonnes_has_Colis idPers=new AccesBDDPersonnes_has_Colis();
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
		"SELECT * FROM colis WHERE Lieu=?");
		
		recherche.setString(1, idChargement);
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
			
		while(resultat.next()){
			Colis courant=new Colis(new Integer(resultat.getInt("idColis")), 
					resultat.getString("Code_barre"), 
					new Integer(idPers.getExpediteur(new Integer(resultat.getInt("idColis")))), 
					new Integer(idPers.getDestinataire(new Integer(resultat.getInt("idColis")))),
					new Integer(resultat.getInt("Users_idUsers")),
					resultat.getString("Poids"), 
					resultat.getTimestamp("DateDepot"), 
					new Integer(resultat.getInt("Fragilite")),
					new Integer(resultat.getInt("ModelesColis_idModelesColis")), 
					new Integer(resultat.getInt("Entrepots_idEntrepots")),
					resultat.getString("Valeur"));			
			liste.add(courant);
		}

		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return liste;
	}
	
	public Integer getCamion(Integer idChargement) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		Integer trouvee=null;
		
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
				"SELECT idChargement FROM chargement WHERE Camions_idCamions=?");
		recherche.setInt(1, idChargement.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next())	trouvee=new Integer(resultat.getInt(idChargement));
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return trouvee;
	}


	public static void main(String arg[]){
		/*AccesBDDChargement test=new AccesBDDChargement();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		Timestamp date=new Timestamp(10);
		Chargement aAjouter = new Chargement(new Integer(0),new Integer(1),1,new Integer(1),date);
		Chargement aAjouter2 = new Chargement(new Integer(0),new Integer(2),1,new Integer(1),date);
		try{
			test.ajouter(aAjouter,connecteur);
			test.ajouter(aAjouter2,connecteur);
			test.supprimer(aAjouter, connecteur);
			Vector list=test.listerColis(aAjouter2, connecteur);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}*/
	}
}
