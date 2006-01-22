package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import donnees.Chargement;
import donnees.Colis;

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
	
	//----- Supprimer un chargement -----//
	public void supprimer(Chargement aSupprimer, ConnecteurSQL connecteur) throws SQLException{
		PreparedStatement supprime=
			connecteur.getConnexion().prepareStatement(
				"DELETE FROM chargement WHERE idChargement=?");
		supprime.setInt(1, aSupprimer.getId());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		supprime.close();	// Fermeture requête SQL
	}
	
	//----- Lister le colis appartenant à un chargement -----//
	public Vector listerColis(Chargement chargement, ConnecteurSQL connecteur) throws SQLException{
		Vector liste=new Vector();
		String idChargement="C-"+chargement.getId();
		AccesBDDPersonnes_has_Colis idPers=new AccesBDDPersonnes_has_Colis();
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
		"SELECT * FROM colis WHERE Lieu=?");
		
		recherche.setString(1, idChargement);
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
			
		while(resultat.next()){
			Colis courant=new Colis(idPers.getExpediteur(resultat.getInt("idColis"), connecteur),
					idPers.getDestinataire(resultat.getInt("idColis"), connecteur),resultat.getInt("Users_idUsers"), 
					resultat.getFloat("Poids"),resultat.getTimestamp("DateDepot"), resultat.getInt("Fragilite"), 
					resultat.getInt("Valeur"), resultat.getInt("ModelesColis_idModelesColis"), 
					resultat.getInt("Entrepots_idEntrepots"), resultat.getString("Lieu"));
			liste.add(courant);
		}
	
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return liste;
	}

	public static void main(String arg[]){
		AccesBDDChargement test=new AccesBDDChargement();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		Timestamp date=new Timestamp(10);
		Chargement aAjouter = new Chargement(0,1,1,1,date);
		Chargement aAjouter2 = new Chargement(0,2,1,1,date);
		try{
			test.ajouter(aAjouter,connecteur);
			test.ajouter(aAjouter2,connecteur);
			test.supprimer(aAjouter, connecteur);
			Vector list=test.listerColis(aAjouter2, connecteur);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
