package accesBDD;

import donnees.Personne;
import donnees.Utilisateur;
import java.sql.*;
import java.util.Vector;

//----- Classe permettant l'accès à la table Utilisateur, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDUtilisateur extends AccesBDD{
	public AccesBDDUtilisateur(){
		super();
	}
	
	//----- Ajouter un utilisateur -----//
	public Integer ajouter(Utilisateur aAjouter) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=connecter().prepareStatement("SELECT MAX(idUsers) FROM users");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
				
		aAjouter.setId(new Integer (resultat.getInt(1)+1)); // Incrémentation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =connecter().prepareStatement(
				"INSERT INTO users"
				+ " (idUsers,Personnes_idPersonnes,Login,Password_2,Type_2)" // Paramètre de la table
				+ " VALUES (?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		// Ajout dans la table personne
		ajout.setInt(2,new AccesBDDPersonne().ajouter(aAjouter.getPersonne()).intValue());
		ajout.setString(3,aAjouter.getLogin());
		ajout.setString(4,aAjouter.getMotDePasse());
		ajout.setInt(5,aAjouter.getType().intValue());
		
		ajout.executeUpdate();//execution de la requete SQL
		
		ajout.close();//fermeture requete SQL
		deconnecter();
		
		return aAjouter.getId();
	}
	
	//----- Mettre à jour un utilisateur sur la BDD -----//
	public void modifier(Utilisateur aModifier) throws SQLException{
		//----- Modification d'une personne à partir de l'id -----//
		PreparedStatement modifie=connecter().prepareStatement(
				"UPDATE users SET "
				+"Login=?, Password_2=?, Type_2=? "
				+"WHERE idUsers=?");
		modifie.setString(1, aModifier.getLogin());
		modifie.setString(2, aModifier.getMotDePasse());
		modifie.setInt(3, aModifier.getType().intValue());
		modifie.setInt(4, aModifier.getId().intValue());
		
		modifie.executeUpdate();	// Exécution de la requête SQL
		
		//----- Modification de la personne associée à l'utilisateur -----//
		new AccesBDDPersonne().modifier(aModifier.getPersonne());
		
		modifie.close();	// Fermeture requête SQL
		deconnecter();
	}
	
	public void supprimer(Integer aSupprimer) throws SQLException{
		PreparedStatement supprime=connecter().prepareStatement("DELETE FROM users WHERE idUsers=?");
		supprime.setInt(1,aSupprimer.intValue());
				
		supprime.executeUpdate();//execution de la requete SQL
		// La suppression de la personne se fera automatiquement suite à la configuration de la BDD
		supprime.close();//fermeture requete SQL
		deconnecter();
	}
	
	//----- Lister tous les users -----//
	public Vector lister() throws SQLException{
		AccesBDDPersonne pers=new AccesBDDPersonne();
		Vector liste=new Vector();
		Utilisateur courantUtilisateur=null;
		Personne courantPers=null;
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM users");
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			courantPers=pers.rechercher(new Integer(resultat.getInt("Personnes_idPersonnes")));
			courantUtilisateur=new Utilisateur(new Integer(resultat.getInt("idUsers")),
					resultat.getString("Login"),
					resultat.getString("Password_2"),
					new Integer(resultat.getInt("Type_2")),
					courantPers);

			liste.add(courantUtilisateur);
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		deconnecter();
		
		return liste;
	}
	
	//----- Recherche d'un utilisateur dans la BDD -----//
	/*public Utilisateur rechercher(String aChercher) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		Utilisateur trouvee=null;
		AccesBDDPersonne pers=new AccesBDDPersonne();
				
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
			"SELECT * FROM users WHERE Login=?");
		
		recherche.setString(1, aChercher);
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		resultat.next();
		trouvee=new Utilisateur(
				resultat.getString("Login"),
				resultat.getString("Password_2"),
				new Integer(resultat.getInt("Type_2")), 
				pers.rechercher(new Integer(resultat.getInt("Personnes_idPersonnes"))));
		
		trouvee.setId(new Integer(resultat.getInt("idUsers")));
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return trouvee;
	}*/
	
	//----- Vérifie si l'utilisateur a été créé auparavant, retourn 0 si utilisateur inconnu, 1 si password inexact, 2 si Ok -----//
	public Utilisateur isRegistered(String login, String password)throws SQLException{
		Utilisateur trouvee=null;
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM users WHERE Login=? AND Password_2=?");
		recherche.setString(1, login);
		recherche.setString(1, password);
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next()){
			trouvee=new Utilisateur(new Integer(resultat.getInt("idUsers")),
					resultat.getString("Login"),
					resultat.getString("Password_2"),
					new Integer(resultat.getInt("Type_2")),
					new AccesBDDPersonne().rechercher(new Integer(resultat.getInt("Personnes_idPersonnes"))));
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		deconnecter();
		
		return trouvee;
	}

	//----- TEST OKAY -----//
	/*public static void main(String arg[]){
		AccesBDDUtilisateur test=new AccesBDDUtilisateur();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		Utilisateur rec=null;
		//Timestamp date=new Timestamp(10);
		Utilisateur aAjouter = new Utilisateur("login1", "motDePasse1", new Integer(Utilisateur.ENTREE), "fdfdfd", "fddfssdf", "fdssdffdsfdsfdsfsdfd", "94804", "ville1", "mail1", "6767866969");
		Utilisateur aAjouter1 = new Utilisateur("login2", "motDePasse2", new Integer(Utilisateur.PREPARATIOIN), "dfsfdfds", "fsdsfddfs", "fsdfdsfdsfdss", "9481", "vill2e", "mail2", "69686696");
		Utilisateur aAjouter2 = new Utilisateur("login3", "motDePasse3", new Integer(Utilisateur.SUPERVISION), "fdfsdsdf", "fdsfdsfds", "dfssdffdsdfs", "94802", "ville3", "mail3", "6886669");
		Utilisateur aModifier = new Utilisateur("Soph", "ia", new Integer(Utilisateur.SUPERVISION), "nom2", "prenom2", "adresse2", "22222", "ville2", "mail2", "telephone2");
		try{
			test.ajouter(aAjouter);
			test.ajouter(aAjouter1);
			test.ajouter(aAjouter2);
			aModifier.setId(aAjouter.getId());
			aModifier.getPersonne().setId(aAjouter.getPersonne().getId());
			aModifier.getPersonne().getLocalisation().setId(aAjouter.getPersonne().getLocalisation().getId());
			test.modifier(aModifier);
			rec=test.rechercher("julien");
			rec=null;
			int rec2=0;
			rec2=test.isRegistered(aModifier.getLogin(), aModifier.getMotDePasse());
			rec2=0;
			rec2=test.isRegistered(aModifier.getLogin(), "fjkf");
			test.ajouter(aAjouter);
			Vector list=null;
			list=test.lister();
			test.supprimer(aModifier.getId());			
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}*/
}
