package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import donnees.Localisation;

//----- Classe permettant l'accès à la table Localisation, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDLocalisation extends ConnecteurSQL{
	public final static short ID=0;
	public final static short ADRESSE=0;
	public final static short VILLE=1;
	public final static short CODEPOSTAL=2;
	
	//----- Permet l'ajout d'une localisation dans la BDD -----//
	public int ajouter(Localisation aAjouter) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idLocalisation) FROM localisation");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(resultat.getInt(1)+1); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion d'une localisation dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO localisation"
				+ " (idLocalisation,Adresse,CodePostal,Ville)" // Parametre de la table
				+ " VALUES (?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId());
		ajout.setString(2,aAjouter.getAdresse());
		ajout.setString(3,aAjouter.getCodePostal());
		ajout.setString(4,aAjouter.getVille());
				
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		return aAjouter.getId();
	}
	
	//----- Recherche d'une localisation dans la BDD -----//
	public Localisation rechercher(int aChercher) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		Localisation trouvee=null;
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM localisation WHERE idLocalisation=?");
		
		recherche.setInt(1, aChercher);
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		if(resultat.next()){	// S'il a trouvé la localisation
			trouvee=new Localisation(resultat.getString("Adresse"), resultat.getString("CodePostal"), resultat.getString("Ville"));
			trouvee.setId(resultat.getInt("idLocalisation"));
		}
				
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return trouvee;
	}
	
	//	----- Recherche d'une localisation dans la BDD -----//
	public Localisation rechercher(short type, String aChercher) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		Localisation trouvee=null;
		PreparedStatement recherche;
		
		switch(type){
		case 0:	// Recherche d'une adresse
			recherche=connecteur.getConnexion().prepareStatement(
			"SELECT * FROM localisation WHERE Adresse=?");
			break;
			
		case 1:	// Recherche d'une ville
			recherche=connecteur.getConnexion().prepareStatement(
			"SELECT * FROM localisation WHERE Ville=?");
			break;
			
		case 2: // Recherche d'un code postal
			recherche=connecteur.getConnexion().prepareStatement(
			"SELECT * FROM localisation WHERE CodePostal=?");
			break;
			
		default:
			recherche=null;
		}
		
		recherche.setString(1, aChercher);
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		if(resultat.next()){	// S'il a trouvé la localisation
			trouvee=new Localisation(resultat.getString("Adresse"), resultat.getString("CodePostal"), resultat.getString("Ville"));
			trouvee.setId(resultat.getInt("idLocalisation"));
		}
				
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return trouvee;
	}
	
	//----- Modifier une localisation dans la BDD -----//
	public void modifier(Localisation aModifier) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		//----- Modification de la localisation à partir de l'id -----//
		PreparedStatement modifie=
			connecteur.getConnexion().prepareStatement(
				"UPDATE localisation SET "
				+"Adresse=?, CodePostal=?, Ville=?"
				+"WHERE idLocalisation=?");
		modifie.setString(1, aModifier.getAdresse());
		modifie.setString(2, aModifier.getCodePostal());
		modifie.setString(3, aModifier.getVille());
		modifie.setInt(4, aModifier.getId());		
		
		modifie.executeUpdate();	// Exécution de la requête SQL
						
		modifie.close();	// Fermeture requête SQL
	}
	
	//----- Supprimer une localisation dans la BDD -----//
	public void supprimer(Localisation aSupprimer) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		PreparedStatement supprime=
			connecteur.getConnexion().prepareStatement(
				"DELETE FROM localisation WHERE idLocalisation=?");
		supprime.setInt(1, aSupprimer.getId());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		supprime.close();	// Fermeture requête SQL
	}
	
	//----- TESTS OKAY-----//
	public static void main(String arg[]){
		AccesBDDLocalisation test=new AccesBDDLocalisation();
		Localisation resultatRech=null;
		Localisation aAjouter = new Localisation("Adresse","94800","Villejuif");
		Localisation aModifier = new Localisation("AdresseModif","94800","VillejuifModif");
		aModifier.setId(1);
		try{
			test.ajouter(aAjouter);
			test.modifier(aModifier);
			resultatRech=test.rechercher(aModifier.getId());
			resultatRech=null;
			resultatRech=test.rechercher(AccesBDDLocalisation.CODEPOSTAL,aModifier.getCodePostal());
			resultatRech=null;
			resultatRech=test.rechercher(AccesBDDLocalisation.ADRESSE, aModifier.getAdresse());
			resultatRech=null;
			resultatRech=test.rechercher(AccesBDDLocalisation.VILLE, aModifier.getVille());
			test.supprimer(aModifier);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
