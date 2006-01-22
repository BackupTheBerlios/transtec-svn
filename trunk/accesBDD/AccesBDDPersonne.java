package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import donnees.Localisation;
import donnees.Personne;

//----- Classe permettant l'accès à la table Personne, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDPersonne extends AccesBDD{
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
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		System.out.println(resultat.getInt(1)+1);
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion d'une personne dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO personnes"
				+ " (idPersonnes,Localisation_idLocalisation,Nom,Prenom,Telephone,Email)" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		// Ajout dans la table de localisation
		//ajout.setInt(2,loc.ajouter(aAjouter.getLocalisation(), connecteur));
		ajout.setInt(2,loc.ajouter(aAjouter.getLocalisation(), connecteur));
		
		ajout.setString(3,aAjouter.getNom());
		ajout.setString(4,aAjouter.getPrenom());
		ajout.setString(5,aAjouter.getTelephone());
		ajout.setString(6,aAjouter.getMail());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		return aAjouter.getId().intValue();
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
			locAChercher=bddLoc.rechercher(AccesBDDLocalisation.CODEPOSTAL, aChercher, connecteur);
			recherche=connecteur.getConnexion().prepareStatement(
			"SELECT * FROM personnes WHERE idPersonnes=?");
			aChercher=locAChercher.getId().intValue();
			break;
			
		default:
			recherche=null;	
		}
		
		recherche.setInt(1, aChercher);
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		if(resultat.next()){	// S'il a trouvé la personne
			Localisation loc=bddLoc.rechercher(AccesBDDLocalisation.ID, resultat.getInt("Localisation_idLocalisation"), connecteur);
			trouvee=new Personne(resultat.getString("Nom"), resultat.getString("Prenom")
					, loc.getAdresse(), loc.getCodePostal(), loc.getVille(),
					resultat.getString("Email"), resultat.getString("Telephone"));
			trouvee.setId(new Integer(resultat.getInt("idPersonnes")));
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return trouvee;
	}
	
	public Personne rechercher(short type, String aChercher, ConnecteurSQL connecteur) throws SQLException{
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
				locAChercher=bddLoc.rechercher(AccesBDDLocalisation.ADRESSE, aChercher, connecteur);
				recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM personnes WHERE Localisation_idLocalisation=?");
				break;
				
			// Recherche d'une ville
			case 5:	
				locAChercher=bddLoc.rechercher(AccesBDDLocalisation.VILLE, aChercher, connecteur);
				recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM personnes WHERE Localisation_idLocalisation=?");
				break;
				
			default: recherche=null;	// A tester!!!!!!
		}
		
		if(type<4)	recherche.setString(1, aChercher);
		else	recherche.setInt(1, locAChercher.getId().intValue());
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		if(resultat.next()){	// S'il a trouvé la personne
			Localisation loc=bddLoc.rechercher(AccesBDDLocalisation.ID, resultat.getInt("Localisation_idLocalisation"), connecteur);
			trouvee=new Personne(resultat.getString("Nom"), resultat.getString("Prenom")
					, loc.getAdresse(), loc.getCodePostal(), loc.getVille(),
					resultat.getString("Email"), resultat.getString("Telephone"));
			trouvee.setId(new Integer(resultat.getInt("idPersonnes")));
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return trouvee;
	}
	
	//----- Modification d'une personne dans la BDD -----//
	public void modifier(Personne aModifier, ConnecteurSQL connecteur) throws SQLException{
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
		
		modifie.executeUpdate();	// Exécution de la requête SQL
		
		//----- Modification de la localisation associée à la personne -----//
		AccesBDDLocalisation bddLoc=new AccesBDDLocalisation();
		bddLoc.modifier(aModifier.getLocalisation(), connecteur);
		
		modifie.close();	// Fermeture requête SQL
	}
	
	//----- Supprimer une personne dans la BDD -----//
	public void supprimer(Personne aSupprimer, ConnecteurSQL connecteur) throws SQLException{
		PreparedStatement supprime=connecteur.getConnexion().prepareStatement(
				"DELETE FROM personnes WHERE idPersonnes=?");
		supprime.setInt(1,aSupprimer.getId().intValue());
				
		supprime.executeUpdate();//execution de la requete SQL
		// La suppression de la localisation se fera automatiquement suite à la configuration de la BDD
		supprime.close();//fermeture requete SQL
	}

public static void main(String arg[]){
		AccesBDDPersonne test=new AccesBDDPersonne();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		//Timestamp date=new Timestamp(10);
		Personne aAjouter = new Personne("nom", "prenom", "adresse", "94800", "ville", "mail", "telephone");
		try{
			test.ajouter(aAjouter,connecteur);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
