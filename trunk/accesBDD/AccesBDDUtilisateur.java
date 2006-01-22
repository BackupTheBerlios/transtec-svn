package accesBDD;

import donnees.Personne;
import donnees.Utilisateur;

import java.sql.*;
import java.util.Vector;

//----- Classe permettant l'accès à la table Utilisateur, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDUtilisateur implements AccesBDD {
	public final static int SUPERVISEUR=0;
	public final static int PREPARATEUR=1;
	public final static int ENTREE=2;
	public final static short INCONNU=0;
	public final static short MAUVAIS_PASS=1;
	public final static short EXISTANT=2;
	
	//----- Ajouter un utilisateur -----//
	public int ajouter(Utilisateur aAjouter, ConnecteurSQL connecteur) throws SQLException{
		AccesBDDPersonne pers=new AccesBDDPersonne();
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idUsers) FROM users");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(resultat.getInt(1)+1); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO users"
				+ " (idUsers,Personnes_idPersonnes,Login,Password_2,Type_2)" // Parametre de la table
				+ " VALUES (?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId());
		ajout.setFloat(2,pers.ajouter(aAjouter.getPersonne(), connecteur));
		ajout.setString(3,aAjouter.getLogin());
		ajout.setString(4,aAjouter.getMotDePasse());
		ajout.setInt(5,aAjouter.getType());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		
		return aAjouter.getId();
	}
	
	//----- Mettre à jour un utilisateur sur la BDD -----//
	public void modifier(Utilisateur aModifier, ConnecteurSQL connecteur) throws SQLException{
		//----- Modification d'une personne à partir de l'id -----//
		PreparedStatement modifie=
			connecteur.getConnexion().prepareStatement(
				"UPDATE users SET "
				+"Login=?, Password_2=?, Type_2=? "
				+"WHERE idUsers=?");
		
		modifie.setString(1, aModifier.getLogin());
		modifie.setString(2, aModifier.getMotDePasse());
		modifie.setInt(3, aModifier.getType());
		modifie.setInt(4, aModifier.getId());
		
		modifie.executeUpdate();	// Exécution de la requête SQL
		
		//----- Modification de la localisation associée à la personne -----//
		AccesBDDPersonne bddLoc=new AccesBDDPersonne();
		bddLoc.modifier(aModifier.getPersonne(), connecteur);
		
		modifie.close();	// Fermeture requête SQL
	}
	
	public void supprimer(Utilisateur aSupprimer, ConnecteurSQL connecteur) throws SQLException{
		PreparedStatement supprime=connecteur.getConnexion().prepareStatement(
			"DELETE FROM users WHERE idUsers=?");
		supprime.setInt(1,aSupprimer.getId());
				
		supprime.executeUpdate();//execution de la requete SQL
		// La suppression de la personne se fera automatiquement suite à la configuration de la BDD
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
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			courantPers=pers.rechercher(pers.ID, resultat.getInt("Personnes_idPersonnes"), connecteur);
			courantUtilisateur=new Utilisateur(resultat.getString("Login"), resultat.getString("Password_2"),
					resultat.getInt("Type_2"), courantPers);
			courantUtilisateur.setId(resultat.getInt("idUsers"));
			liste.add(courantUtilisateur);
		}
		
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		return liste;
	}
	
	//----- Recherche d'un utilisateur dans la BDD -----//
	public Utilisateur rechercher(int aChercher, ConnecteurSQL connecteur) throws SQLException{
		Utilisateur trouvee=null;
		AccesBDDPersonne pers=new AccesBDDPersonne();
				
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
			"SELECT * FROM users WHERE idUsers=?");
		
		recherche.setInt(1, aChercher);
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		resultat.next();
		trouvee=new Utilisateur(resultat.getString("Login"), resultat.getString("Password_2"), resultat.getInt("Type_2"), 
				pers.rechercher(pers.ID, resultat.getInt("Personnes_idPersonnes"), connecteur));
		trouvee.setId(resultat.getInt("idUsers"));
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return trouvee;
	}
	
	//----- Vérifie si l'utilisateur a été créé auparavant, retourn 0 si utilisateur inconnu, 1 si password inexact, 2 si Ok -----//
	public short isRegistered(String login, String password, ConnecteurSQL connecteur)throws SQLException{
		short retour;
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
		"SELECT * FROM users WHERE Login=?");
	
		recherche.setString(1, login);
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		if(resultat.next()){
			retour=MAUVAIS_PASS;
			if(resultat.getString("Password_2").equals(password))	retour=EXISTANT;
		}
		else retour=INCONNU;
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return retour;
	}

	//----- TEST OKAY -----//
	public static void main(String arg[]){
		AccesBDDUtilisateur test=new AccesBDDUtilisateur();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		Utilisateur rec=null;
		//Timestamp date=new Timestamp(10);
		Utilisateur aAjouter = new Utilisateur("login", "motDePasse", 0, "nom", "prenom", "adresse", 94800, "ville", "mail", "telephone");
		Utilisateur aModifier = new Utilisateur("Soph", "iach", 1, "nom2", "prenom2", "adresse2", 22222, "ville2", "mail2", "telephone2");
		try{
			test.ajouter(aAjouter,connecteur);
			aModifier.setId(aAjouter.getId());
			aModifier.setIdPersonne(aAjouter.getIdPersonne());
			aModifier.setIdLocalisation(aAjouter.getIdLocalisation());
			test.modifier(aModifier, connecteur);
			rec=test.rechercher(aModifier.getId(), connecteur);
			rec=null;
			int rec2=0;
			rec2=test.isRegistered(aModifier.getLogin(), aModifier.getMotDePasse(), connecteur);
			rec2=0;
			rec2=test.isRegistered(aModifier.getLogin(), "fjkf", connecteur);
			test.ajouter(aAjouter, connecteur);
			Vector list=null;
			list=test.lister(connecteur);
			test.supprimer(aModifier, connecteur);			
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
