package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import donnees.Localisation;
import donnees.Personne;

//----- Classe permettant l'accès à la table Personne, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDPersonne extends AccesBDD{
	public final static short NOM=0;
	public final static short PRENOM=1;
	public final static short EMAIL=2;
	public final static short TELEPHONE=3;
	public final static short ADRESSE=4;
	public final static short VILLE=5;
	public final static short CODEPOSTAL=6;
	
	public AccesBDDPersonne(){
		super();
	}
	
	//----- Ajouter une personne dans la BDD -----//
	public Integer ajouter(Personne aAjouter) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=connecter().prepareStatement("SELECT MAX(idPersonnes) FROM Personnes");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion d'une personne dans la BDD -----//
		PreparedStatement ajout=connecter().prepareStatement(
				"INSERT INTO Personnes"
				+ " (idPersonnes,Localisation_idLocalisation,Nom,Prenom,Telephone,Email)" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		// Ajout dans la table de localisation
		ajout.setInt(2,new AccesBDDLocalisation().ajouter(aAjouter.getLocalisation()).intValue());
		ajout.setString(3,aAjouter.getNom());
		ajout.setString(4,aAjouter.getPrenom());
		ajout.setString(5,aAjouter.getTelephone());
		ajout.setString(6,aAjouter.getMail());
		
		ajout.executeUpdate();//execution de la requete SQL
		
		ajout.close();//fermeture requete SQL
		deconnecter();
		
		return aAjouter.getId();
	}
	
	//----- Recherche d'une personne dans la BDD -----//
	public Personne rechercher(Integer aChercher) throws SQLException{
		Personne trouvee=null;
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM personnes WHERE idPersonnes=?");
		recherche.setInt(1, aChercher.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next()){	// S'il a trouvé la personne
			AccesBDDLocalisation bddLoc=new AccesBDDLocalisation();
			Localisation loc=bddLoc.rechercher(new Integer(resultat.getInt("Localisation_idLocalisation")));
			trouvee=new Personne(new Integer(resultat.getInt("idPersonnes")),
					resultat.getString("Nom"), 
					resultat.getString("Prenom"),
					resultat.getString("Email"), 
					resultat.getString("Telephone"),
					loc);
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		deconnecter();
		
		return trouvee;
	}
	
	public Vector lister() throws SQLException{
		AccesBDDLocalisation bddLoc=new AccesBDDLocalisation();
		
		Vector liste=new Vector();
	
		Personne courantPers=null;
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM personnes");
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			Localisation loc=bddLoc.rechercher(new Integer(resultat.getInt("Localisation_idLocalisation")));
			courantPers=new Personne(new Integer(resultat.getInt("idPersonnes")),
					resultat.getString("Nom"), 
					resultat.getString("Prenom"),
					resultat.getString("Email"), 
					resultat.getString("Telephone"),
					loc);

			liste.add(courantPers);
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		deconnecter();
		
		return liste;
	}
	
	public Vector rechercher(short type, String aChercher) throws SQLException{
		Vector liste =new Vector();
		AccesBDDLocalisation bddLoc=new AccesBDDLocalisation();
		Localisation locAChercher=null;
		
		//----- Recherche de la personne à partir de l'id -----//
		PreparedStatement recherche;
		switch(type){
			// Recherche d'un Nom
			case NOM:
				recherche=connecter().prepareStatement("SELECT * FROM personnes WHERE Nom=?");
				break;
					
			// Recherche d'un Prenom
			case PRENOM:
				recherche=connecter().prepareStatement("SELECT * FROM personnes WHERE Prenom=?");
				break;
					
			// Recherche d'un Email
			case EMAIL:
				recherche=connecter().prepareStatement("SELECT * FROM personnes WHERE Email=?");
				break;
					
			// Recherhce d'un numéro de téléphone
			case TELEPHONE:
				recherche=connecter().prepareStatement("SELECT * FROM personnes WHERE Telephone=?");
				break;
					
			// Recherche d'une adresse
			case ADRESSE:	
				locAChercher=bddLoc.rechercher(AccesBDDLocalisation.ADRESSE, aChercher);
				recherche=connecter().prepareStatement(	"SELECT * FROM personnes WHERE Localisation_idLocalisation=?");
				break;
				
			// Recherche d'une ville
			case VILLE:	
				locAChercher=bddLoc.rechercher(AccesBDDLocalisation.VILLE, aChercher);
				recherche=connecter().prepareStatement("SELECT * FROM personnes WHERE Localisation_idLocalisation=?");
				break;
			
			// Recherhce d'un code postal
			case CODEPOSTAL:
				locAChercher=bddLoc.rechercher(AccesBDDLocalisation.CODEPOSTAL, aChercher);
				recherche=connecter().prepareStatement("SELECT * FROM personnes WHERE Localisation_idLocalisation=?");
				break;
				
			default: recherche=null;	// A tester!!!!!!
		}
		
		if(type<4)	recherche.setString(1, aChercher);
		else	recherche.setInt(1, locAChercher.getId().intValue());
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		while(resultat.next()){	// S'il a trouvé la personne
			Localisation loc=bddLoc.rechercher(new Integer(resultat.getInt("Localisation_idLocalisation")));
			liste.add(new Personne(
					new Integer(resultat.getInt("idPersonnes")),
					resultat.getString("Nom"), 
					resultat.getString("Prenom"), 
					loc.getAdresse(), 
					loc.getCodePostal(), 
					loc.getVille(),
					resultat.getString("Email"), 
					resultat.getString("Telephone")));
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return liste;
	}
	
	//----- Modification d'une personne dans la BDD -----//
	public void modifier(Personne aModifier) throws SQLException{
		//----- Modification d'une personne à partir de l'id -----//
		PreparedStatement modifie=connecter().prepareStatement(
				"UPDATE personnes SET "
				+"Nom=?, Prenom=?, Telephone=?, Email=? "
				+"WHERE idPersonnes=?");
		modifie.setString(1, aModifier.getNom());
		modifie.setString(2, aModifier.getPrenom());
		modifie.setString(3, aModifier.getTelephone());
		modifie.setString(4, aModifier.getMail());
		modifie.setInt(5, aModifier.getId().intValue());
		
		modifie.executeUpdate();	// Exécution de la requête SQL
		
		//----- Modification de la localisation associée à la personne -----//
		new AccesBDDLocalisation().modifier(aModifier.getLocalisation());
		
		modifie.close();	// Fermeture requête SQL
		deconnecter();
	}
	
	//----- Supprimer une personne dans la BDD -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		PreparedStatement supprime=connecter().prepareStatement("DELETE FROM personnes WHERE idPersonnes=?");
		supprime.setInt(1,aSupprimer.intValue());
				
		supprime.executeUpdate(); // Exécution de la requête SQL
		// La suppression de la localisation se fera automatiquement suite à la configuration de la BDD
		supprime.close();//fermeture requete SQL
		deconnecter();
	}

	//----- TEST OKAY SAUF RECHERCHER (ID OKAY) ENCORE SUPPRIMER LES SOUS TABLES-----//
	/*public static void main(String arg[]){
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
	}*/
}
