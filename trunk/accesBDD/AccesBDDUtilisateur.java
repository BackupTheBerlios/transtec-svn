package accesBDD;

import donnees.Personne;
import donnees.Utilisateur;

import java.sql.*;
import java.util.Vector;

//----- Classe permettant l'acc�s � la table Utilisateur, elle permet de faire les diff�rentes op�rations n�cessaire sur la table -----//

public class AccesBDDUtilisateur implements AccesBDD {
	public final static int SUPERVISEUR=0;
	public final static int PREPARATEUR=1;
	public final static int ENTREE=2;
	
	//----- Ajouter un utilisateur -----//
	public int ajouter(Utilisateur aAjouter, ConnecteurSQL connecteur) throws SQLException{
		AccesBDDPersonne pers=new AccesBDDPersonne();
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idUsers) FROM users");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO users"
				+ " (idUsers,Personnes_idPersonnes,Login,Password_2,Type_2)" // Parametre de la table
				+ " VALUES (?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setFloat(2,pers.ajouter(aAjouter.getPersonne(), connecteur));
		ajout.setString(3,aAjouter.getLogin());
		ajout.setString(4,aAjouter.getMotDePasse());
		ajout.setInt(5,aAjouter.getType().intValue());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		
		return aAjouter.getId().intValue();
	}
	
	//----- Mettre � jour un utilisateur sur la BDD -----//
	public void modifier(Utilisateur aModifier, ConnecteurSQL connecteur) throws SQLException{
		//----- Modification d'une personne � partir de l'id -----//
		PreparedStatement modifie=
			connecteur.getConnexion().prepareStatement(
				"UPDATE users SET"
				+"Login=?, Password_2=?, Type_2=? "
				+"WHERE idUsers=,");
		
		modifie.setString(1, aModifier.getLogin());
		modifie.setString(2, aModifier.getMotDePasse());
		modifie.setInt(3, aModifier.getType().intValue());	
		
		modifie.executeUpdate();	// Ex�cution de la requ�te SQL
		
		//----- Modification de la localisation associ�e � la personne -----//
		AccesBDDPersonne bddLoc=new AccesBDDPersonne();
		bddLoc.modifier(aModifier.getPersonne(), connecteur);
		
		modifie.close();	// Fermeture requ�te SQL
	}
	
	public void supprimer(Utilisateur aSupprimer, ConnecteurSQL connecteur) throws SQLException{
		PreparedStatement supprime=connecteur.getConnexion().prepareStatement(
			"DELETE FROM users WHERE idUsers=?");
		supprime.setInt(1,aSupprimer.getId().intValue());
				
		supprime.executeUpdate();//execution de la requete SQL
		// La suppression de la personne se fera automatiquement suite � la configuration de la BDD
		supprime.close();//fermeture requete SQL
	}
	
	//----- Changer les droit d'un utilisateur -----//
	public boolean changerDroit(int nouveauDroit, Utilisateur aChanger){
		return true;
	}
	
	//----- Lister tous les users -----//
	public Vector lister(ConnecteurSQL connecteur) throws SQLException{
		AccesBDDPersonne pers=new AccesBDDPersonne();
		Vector liste=new Vector();
		Utilisateur courantUtilisateur=null;
		Personne courantPers=null;
		
		PreparedStatement rechercheMaxID=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM users");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		
		while(resultat.next()){
			courantPers=pers.rechercher(AccesBDDPersonne.ID, resultat.getInt("Personnes_idPersonnes"), connecteur);
			courantUtilisateur=new Utilisateur(resultat.getString("Login"), resultat.getString("Password"),new Integer(resultat.getString("Type_2")),
					courantPers);
			courantUtilisateur.setId(new Integer(resultat.getInt("idUsers")));
			liste.add(courantUtilisateur);
		}
		
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		return liste;
	}
	
	//----- Recherche d'un utilisateur dans la BDD -----//
	public Utilisateur rechercher(int aChercher, ConnecteurSQL connecteur) throws SQLException{
		Utilisateur trouvee=null;
		AccesBDDPersonne pers=new AccesBDDPersonne();
				
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
			"SELECT * FROM users WHERE idUsers=?");
		
		recherche.setInt(1, aChercher);
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		trouvee=new Utilisateur(resultat.getString("Login"), resultat.getString("Password"),new Integer(resultat.getString("Type_2")), pers.rechercher(AccesBDDPersonne.ID, resultat.getString("idUsers"), connecteur));
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		
		return trouvee;
	}

public static void main(String arg[]){
		AccesBDDUtilisateur test=new AccesBDDUtilisateur();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		//Timestamp date=new Timestamp(10);
		//Utilisateur aAjouter = new Utilisateur("login", "motDePasse", "nom", "prenom", "adresse", 94800, "ville", "mail", "telephone");
		try{
			//test.ajouter(aAjouter,connecteur);
			Vector essai;
			essai = new Vector (test.lister(connecteur));
			//Utilisateur temp = test.rechercher(1,connecteur);
			
			//boolean temp = essai.contains("julien");
			//System.out.println(temp);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
