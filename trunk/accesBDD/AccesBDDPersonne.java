package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import donnees.Localisation;
import donnees.Personne;

//----- Classe permettant l'acc�s � la table Personne, elle permet de faire les diff�rentes op�rations n�cessaire sur la table -----//

public class AccesBDDPersonne extends ConnecteurSQL{
	public final static short NOM=0;
	public final static short PRENOM=1;
	public final static short EMAIL=2;
	public final static short TELEPHONE=3;
	public final static short ADRESSE=4;
	public final static short VILLLE=5;
	public final static short CODEPOSTAL=6;
	
	//----- Ajouter une personne dans la BDD -----//
	public Integer ajouter(Personne aAjouter) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		AccesBDDLocalisation loc=new AccesBDDLocalisation();
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idPersonnes) FROM Personnes");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		//----- Insertion d'une personne dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO Personnes"
				+ " (idPersonnes,Localisation_idLocalisation,Nom,Prenom,Telephone,Email)" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		// Ajout dans la table de localisation
		ajout.setInt(2,loc.ajouter(aAjouter.getLocalisation()).intValue());
		ajout.setString(3,aAjouter.getNom());
		ajout.setString(4,aAjouter.getPrenom());
		ajout.setString(5,aAjouter.getTelephone());
		ajout.setString(6,aAjouter.getMail());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		return aAjouter.getId();
	}
	
	//----- Recherche d'une personne dans la BDD -----//
	public Personne rechercher(Integer aChercher) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		Personne trouvee=null;
		AccesBDDLocalisation bddLoc=new AccesBDDLocalisation();
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM personnes WHERE idPersonnes=?");
		
		recherche.setInt(1, aChercher.intValue());
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		if(resultat.next()){	// S'il a trouv� la personne
			Localisation loc=bddLoc.rechercher(new Integer(resultat.getInt("Localisation_idLocalisation")));
			trouvee=new Personne(
					resultat.getString("Nom"), 
					resultat.getString("Prenom"),
					resultat.getString("Email"), 
					resultat.getString("Telephone"),
					loc);
			
			trouvee.setId(new Integer(resultat.getInt("idPersonnes")));
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		
		return trouvee;
	}
	
	public Personne rechercher(short type, String aChercher) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
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
		else	recherche.setInt(1, locAChercher.getId().intValue());
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		if(resultat.next()){	// S'il a trouv� la personne
			Localisation loc=bddLoc.rechercher(new Integer(resultat.getInt("Localisation_idLocalisation")));
			trouvee=new Personne(resultat.getString("Nom"), resultat.getString("Prenom")
					, loc.getAdresse(), loc.getCodePostal(), loc.getVille(),
					resultat.getString("Email"), resultat.getString("Telephone"));
			trouvee.setId(new Integer(resultat.getInt("idPersonnes")));
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		
		return trouvee;
	}
	
	//----- Modification d'une personne dans la BDD -----//
	public void modifier(Personne aModifier) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
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
		modifie.setInt(5, aModifier.getId().intValue());
		
		modifie.executeUpdate();	// Ex�cution de la requ�te SQL
		
		//----- Modification de la localisation associ�e � la personne -----//
		AccesBDDLocalisation bddLoc=new AccesBDDLocalisation();
		bddLoc.modifier(aModifier.getLocalisation());
		
		modifie.close();	// Fermeture requ�te SQL
	}
	
	//----- Supprimer une personne dans la BDD -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		PreparedStatement supprime=connecteur.getConnexion().prepareStatement(
				"DELETE FROM personnes WHERE idPersonnes=?");
		supprime.setInt(1,aSupprimer.intValue());
				
		supprime.executeUpdate();//execution de la requete SQL
		// La suppression de la localisation se fera automatiquement suite � la configuration de la BDD
		supprime.close();//fermeture requete SQL
	}

	//----- TEST OKAY SAUF RECHERCHER (ID OKAY) ENCORE SUPPRIMER LES SOUS TABLES-----//
	public static void main(String arg[]){
		AccesBDDPersonne test=new AccesBDDPersonne();
		Personne rec=null;
		//Timestamp date=new Timestamp(10);
		Personne aAjouter = new Personne("nom", "prenom", "adresse", "94800", "ville", "mail", "telephone");
		Personne aAjouter1 = new Personne("nom1", "prenom1", "adresse1", "95576", "ville1", "mail1", "8787858558857");
		Personne aAjouter2 = new Personne("nom2", "prenom2", "adresse2", "94333", "ville2", "mail2", "8785855287");
		Personne aAjouter3 = new Personne("nom3", "prenom3", "adresse3", "94804", "ville3", "mail3", "2252575752725");
		Personne aAjouter4 = new Personne("nom4", "prenom4", "adresse4", "94840", "ville4", "mail4", "8785857557");
		Personne aAjouter5 = new Personne("nom5", "prenom5", "adresse5", "94807", "ville5", "mail5", "87878585858");
		Personne aModifier = new Personne("dgdf", "ffddsf", "fdsfdsfds", "94801", "ville", "mail", "telephone");
		
		try{
			test.ajouter(aAjouter);
			test.ajouter(aAjouter1);
			test.ajouter(aAjouter2);
			test.ajouter(aAjouter3);
			test.ajouter(aAjouter4);
			test.ajouter(aAjouter5);
			//aModifier.setId(new Integer(1));
			//aModifier.getLocalisation().setId(aAjouter.getLocalisation().getId());
			//test.modifier(aModifier);
			//rec=test.rechercher(aModifier.getId());
			//test.supprimer(aModifier.getId());
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
