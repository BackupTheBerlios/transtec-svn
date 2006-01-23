package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import donnees.Localisation;
import donnees.Personne;

//----- Classe permettant l'accès à la table Personne, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDPersonne extends ConnecteurSQL{
	public final static short ID=0;
	public final static short NOM=0;
	public final static short PRENOM=1;
	public final static short EMAIL=2;
	public final static short TELEPHONE=3;
	public final static short ADRESSE=4;
	public final static short VILLLE=5;
	public final static short CODEPOSTAL=6;
	
	//	----- Ajouter une personne dans la BDD -----//
	public int ajouter(Personne aAjouter) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		AccesBDDLocalisation loc=new AccesBDDLocalisation();
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idPersonnes) FROM Personnes");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(resultat.getInt(1)+1); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion d'une personne dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO Personnes"
				+ " (idPersonnes,Localisation_idLocalisation,Nom,Prenom,Telephone,Email)" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId());
		// Ajout dans la table de localisation
		ajout.setInt(2,loc.ajouter(aAjouter.getLocalisation()));
		ajout.setString(3,aAjouter.getNom());
		ajout.setString(4,aAjouter.getPrenom());
		ajout.setString(5,aAjouter.getTelephone());
		ajout.setString(6,aAjouter.getMail());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		return aAjouter.getId();
	}
	
	//----- Recherche d'une personne dans la BDD -----//
	public Personne rechercher(int aChercher) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		Personne trouvee=null;
		AccesBDDLocalisation bddLoc=new AccesBDDLocalisation();
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM personnes WHERE idPersonnes=?");
		
		recherche.setInt(1, aChercher);
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next()){	// S'il a trouvé la personne
			Localisation loc=bddLoc.rechercher(resultat.getInt("Localisation_idLocalisation"));
			trouvee=new Personne(resultat.getString("Nom"), resultat.getString("Prenom"),
					resultat.getString("Email"), resultat.getString("Telephone"), loc);
			trouvee.setId(resultat.getInt("idPersonnes"));
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return trouvee;
	}
	
	public Personne rechercher(short type, String aChercher) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		Personne trouvee=null;
		AccesBDDLocalisation bddLoc=new AccesBDDLocalisation();
		Localisation locAChercher=null;
		
		//----- Recherche de la personne à partir de l'id -----//
		PreparedStatement recherche;
		switch(type){
			// Recherche d'un Nom
			case 0:
				recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM personnes WHERE Nom=?");
				break;
					
			// Recherche d'un Prenom
			case 1:
				recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM personnes WHERE Prenom=?");
				break;
					
			// Recherche d'un Email
			case 2:
				recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM personnes WHERE Email=?");
				break;
					
			// Recherhce d'un numéro de téléphone
			case 3:
				recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM personnes WHERE Telephone=?");
				break;
					
			// Recherche d'une adresse
			case 4:	
				locAChercher=bddLoc.rechercher(AccesBDDLocalisation.ADRESSE, aChercher);
				recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM personnes WHERE Localisation_idLocalisation=?");
				break;
				
			// Recherche d'une ville
			case 5:	
				locAChercher=bddLoc.rechercher(AccesBDDLocalisation.VILLE, aChercher);
				recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM personnes WHERE Localisation_idLocalisation=?");
				break;
			
				// Recherhce d'un code postal
			case 6:
				locAChercher=bddLoc.rechercher(AccesBDDLocalisation.CODEPOSTAL, aChercher);
				recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM personnes WHERE Localisation_idLocalisation=?");
				break;
				
			default: recherche=null;	// A tester!!!!!!
		}
		
		if(type<4)	recherche.setString(1, aChercher);
		else	recherche.setInt(1, locAChercher.getId());
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		if(resultat.next()){	// S'il a trouvé la personne
			Localisation loc=bddLoc.rechercher(resultat.getInt("Localisation_idLocalisation"));
			trouvee=new Personne(resultat.getString("Nom"), resultat.getString("Prenom")
					, loc.getAdresse(), loc.getCodePostal(), loc.getVille(),
					resultat.getString("Email"), resultat.getString("Telephone"));
			trouvee.setId(resultat.getInt("idPersonnes"));
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return trouvee;
	}
	
	//----- Modification d'une personne dans la BDD -----//
	public void modifier(Personne aModifier) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		//----- Modification d'une personne à partir de l'id -----//
		PreparedStatement modifie=
			connecteur.getConnexion().prepareStatement(
				"UPDATE personnes SET "
				+"Nom=?, Prenom=?, Telephone=?, Email=? "
				+"WHERE idPersonnes=?");
		modifie.setString(1, aModifier.getNom());
		modifie.setString(2, aModifier.getPrenom());
		modifie.setString(3, aModifier.getTelephone());
		modifie.setString(4, aModifier.getMail());
		modifie.setInt(5, aModifier.getId());
		
		modifie.executeUpdate();	// Exécution de la requête SQL
		
		//----- Modification de la localisation associée à la personne -----//
		AccesBDDLocalisation bddLoc=new AccesBDDLocalisation();
		bddLoc.modifier(aModifier.getLocalisation());
		
		modifie.close();	// Fermeture requête SQL
	}
	
	//----- Supprimer une personne dans la BDD -----//
	public void supprimer(Personne aSupprimer) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		PreparedStatement supprime=connecteur.getConnexion().prepareStatement(
				"DELETE FROM personnes WHERE idPersonnes=?");
		supprime.setInt(1,aSupprimer.getId());
				
		supprime.executeUpdate();//execution de la requete SQL
		// La suppression de la localisation se fera automatiquement suite à la configuration de la BDD
		supprime.close();//fermeture requete SQL
	}

	//----- TEST OKAY SAUF RECHERCHER (ID OKAY) ENCORE SUPPRIMER LES SOUS TABLES-----//
	public static void main(String arg[]){
		AccesBDDPersonne test=new AccesBDDPersonne();
		Personne rec=null;
		//Timestamp date=new Timestamp(10);
		Personne aAjouter = new Personne("nom", "prenom", "adresse", "94800", "ville", "mail", "telephone");
		Personne aModifier = new Personne("Ton cul", "ffddsf", "fdsfdsfds", "94801", "ville", "mail", "telephone");
		
		try{
			test.ajouter(aAjouter);
			aModifier.setId(1);
			aModifier.setIdLocalisation(aAjouter.getIdLocalisation());
			test.modifier(aModifier);
			rec=test.rechercher(aModifier.getId());
			test.supprimer(aModifier);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
