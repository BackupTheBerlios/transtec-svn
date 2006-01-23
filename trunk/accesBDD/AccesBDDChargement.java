package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import donnees.Chargement;
import donnees.Colis;

//----- Classe permettant l'acc�s � la table Chargement, elle permet de faire les diff�rentes op�rations n�cessaire sur la table -----//

public class AccesBDDChargement {
	//	----- Ajouter un colis dans la BDD -----//
	public Integer ajouter(Chargement aAjouter, ConnecteurSQL connecteur) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idChargement) FROM chargement");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO chargement"
				+ " (idChargement,Camions_idCamions,Colis_idColis)" // Parametre de la table
				+ " VALUES (?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setInt(2,aAjouter.getIdCamion().intValue());
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
		supprime.setInt(1, aSupprimer.getId().intValue());
				
		supprime.executeUpdate();	// Ex�cution de la requ�te SQL
						
		supprime.close();	// Fermeture requ�te SQL
	}
	
	//----- Lister le colis appartenant � un chargement -----//
	public Vector listerColis(Chargement chargement, ConnecteurSQL connecteur) throws SQLException{
		Vector liste=new Vector();
		String idChargement="C-"+chargement.getId();
		AccesBDDPersonnes_has_Colis idPers=new AccesBDDPersonnes_has_Colis();
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
		"SELECT * FROM colis WHERE Lieu=?");
		
		recherche.setString(1, idChargement);
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
			
		while(resultat.next()){
			/*Colis courant=new Colis(
					new Integer(idPers.getExpediteur(resultat.getInt("idColis"), connecteur)),
					new Integer(idPers.getDestinataire(resultat.getInt("idColis"), connecteur)),
					new Integer(resultat.getInt("Users_idUsers")),
					resultat.getFloat("Poids"),
					resultat.getTimestamp("DateDepot"),
					new Integer(resultat.getInt("Fragilite")),
					new Integer(resultat.getInt("Valeur")),
					new Integer(resultat.getInt("ModelesColis_idModelesColis")),
					new Integer(resultat.getInt("Entrepots_idEntrepots")),
					resultat.getString("Lieu"));*/
			/*Colis courant=new Colis(
					new Integer(resultat.getInt("idColis ")),
					resultat.getString("Code_barre"),
					new Integer(resultat.getInt("ModelesColis_idModelesColis")),
					new Integer(resultat.getInt("Entrepots_idEntrepots")),
					resultat.getString("Poids"),
					resultat.getTimestamp("DateDepot"),
					resultat.getString("Valeur"),
					new Integer(resultat.getInt("Fragilite")),
					resultat.getString("Lieu"));
			

			
			liste.add(courant);*/
		}

		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		
		return liste;
	}

	public static void main(String arg[]){
		AccesBDDChargement test=new AccesBDDChargement();
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
		}
	}
}
