package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import donnees.Localisation;
import donnees.Personne;

//----- Classe permettant l'acc�s � la table Personne, elle permet de faire les diff�rentes op�rations n�cessaire sur la table -----//

public class AccesBDDPersonne implements AccesBDD{
	public final static short ID=0;
	public final static short CODEPOSTAL=1;
	public final static short NOM=0;
	public final static short PRENOM=1;
	public final static short EMAIL=2;
	public final static short TELEPHONE=3;
	public final static short ADRESSE=4;
	public final static short VILLLE=5;
	
	//	----- Ajouter une personne dans la BDD -----//
	public int ajouter(Personne aAjouter, ConnecteurSQL connecteur) throws SQLException{
		AccesBDDLocalisation loc=new AccesBDDLocalisation();
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idPersonnes) FROM Personnes");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(resultat.getInt(1)+1); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		//----- Insertion d'une personne dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO Personnes"
				+ " (idPersonnes,Localisation_idLocalisation,Nom,Prenom,Telephone,Email)" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId());
		// Ajout dans la table de localisation
		ajout.setInt(2,loc.ajouter(aAjouter.getLocalisation(), connecteur));
		ajout.setString(3,aAjouter.getNom());
		ajout.setString(4,aAjouter.getPrenom());
		ajout.setString(5,aAjouter.getTelephone());
		ajout.setString(6,aAjouter.getMail());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		return aAjouter.getId();
	}
	
	//----- Recherche d'une personne dans la BDD -----//
	public Personne rechercher(short type, int aChercher, ConnecteurSQL connecteur) throws SQLException{
		Personne trouvee=null;
		PreparedStatement recherche;
		Localisation locAChercher;
		AccesBDDLocalisation bddLoc=new AccesBDDLocalisation();
		
		switch(type){
		// Rechercher un Id
		case 0:
			recherche=connecteur.getConnexion().prepareStatement(
			"SELECT * FROM personnes WHERE idPersonnes=?");
			break;
			
		// Rechercher un code postal
		case 1:
			locAChercher=bddLoc.rechercher(bddLoc.CODEPOSTAL, aChercher, connecteur);
			recherche=connecteur.getConnexion().prepareStatement(
			"SELECT * FROM personnes WHERE idPersonnes=?");
			aChercher=locAChercher.getId();
			break;
			
		default:
			recherche=null;	
		}
		
		recherche.setInt(1, aChercher);
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		if(resultat.next()){	// S'il a trouv� la personne
			Localisation loc=bddLoc.rechercher(bddLoc.ID, resultat.getInt("Localisation_idLocalisation"), connecteur);
			trouvee=new Personne(resultat.getString("Nom"), resultat.getString("Prenom")
					, loc.getAdresse(), loc.getCodePostal(), loc.getVille(),
					resultat.getString("Email"), resultat.getString("Telephone"));
			trouvee.setId(resultat.getInt("idPersonnes"));
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		
		return trouvee;
	}
	
	public Personne rechercher(short type, String aChercher, ConnecteurSQL connecteur) throws SQLException{
		Personne trouvee=null;
		AccesBDDLocalisation bddLoc=new AccesBDDLocalisation();
		Localisation locAChercher=null;
		
		//----- Recherche de la personne � partir de l'id -----//
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
					
			// Recherhce d'un num�ro de t�l�phone
			case 3:
				recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM personnes WHERE Telephone=?");
				break;
					
			// Recherche d'une adresse
			case 4:	
				locAChercher=bddLoc.rechercher(bddLoc.ADRESSE, aChercher, connecteur);
				recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM personnes WHERE Localisation_idLocalisation=?");
				break;
				
			// Recherche d'une ville
			case 5:	
				locAChercher=bddLoc.rechercher(bddLoc.VILLE, aChercher, connecteur);
				recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM personnes WHERE Localisation_idLocalisation=?");
				break;
				
			default: recherche=null;	// A tester!!!!!!
		}
		
		if(type<4)	recherche.setString(1, aChercher);
		else	recherche.setInt(1, locAChercher.getId());
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		if(resultat.next()){	// S'il a trouv� la personne
			Localisation loc=bddLoc.rechercher(bddLoc.ID, resultat.getInt("Localisation_idLocalisation"), connecteur);
			trouvee=new Personne(resultat.getString("Nom"), resultat.getString("Prenom")
					, loc.getAdresse(), loc.getCodePostal(), loc.getVille(),
					resultat.getString("Email"), resultat.getString("Telephone"));
			trouvee.setId(resultat.getInt("idPersonnes"));
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		
		return trouvee;
	}
	
	//----- Modification d'une personne dans la BDD -----//
	public void modifier(Personne aModifier, ConnecteurSQL connecteur) throws SQLException{
		//----- Modification d'une personne � partir de l'id -----//
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
		
		modifie.executeUpdate();	// Ex�cution de la requ�te SQL
		
		//----- Modification de la localisation associ�e � la personne -----//
		AccesBDDLocalisation bddLoc=new AccesBDDLocalisation();
		bddLoc.modifier(aModifier.getLocalisation(), connecteur);
		
		modifie.close();	// Fermeture requ�te SQL
	}
	
	//----- Supprimer une personne dans la BDD -----//
	public void supprimer(Personne aSupprimer, ConnecteurSQL connecteur) throws SQLException{
		PreparedStatement supprime=connecteur.getConnexion().prepareStatement(
				"DELETE FROM personnes WHERE idPersonnes=?");
		supprime.setInt(1,aSupprimer.getId());
				
		supprime.executeUpdate();//execution de la requete SQL
		// La suppression de la localisation se fera automatiquement suite � la configuration de la BDD
		supprime.close();//fermeture requete SQL
	}

	//----- TEST OKAY SAUF RECHERCHER (ID OKAY) ENCORE SUPPRIMER LES SOUS TABLES-----//
	public static void main(String arg[]){
		AccesBDDPersonne test=new AccesBDDPersonne();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		Personne rec=null;
		//Timestamp date=new Timestamp(10);
		Personne aAjouter = new Personne("nom", "prenom", "adresse", 94800, "ville", "mail", "telephone");
		Personne aModifier = new Personne("Ton cul", "ffddsf", "fdsfdsfds", 94801, "ville", "mail", "telephone");
		
		try{
			test.ajouter(aAjouter,connecteur);
			aModifier.setId(1);
			aModifier.setIdLocalisation(aAjouter.getIdLocalisation());
			test.modifier(aModifier, connecteur);
			rec=test.rechercher(test.ID, aModifier.getId(), connecteur);
			test.supprimer(aModifier, connecteur);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
