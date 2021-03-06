package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import donnees.Localisation;

//----- Classe permettant l'acc�s � la table Localisation, elle permet de faire les diff�rentes op�rations n�cessaire sur la table -----//

public class AccesBDDLocalisation{
	public final static short ID=0;
	public final static short ADRESSE=0;
	public final static short VILLE=1;
	public final static short CODEPOSTAL=2;

	private AccesBDD accesbdd;
	
	public AccesBDDLocalisation(AccesBDD accesbdd){
		this.accesbdd = accesbdd;
	}
	
	//----- Permet l'ajout d'une localisation dans la BDD -----//
	public Integer ajouter(Localisation aAjouter) throws SQLException{
		//----- Recherche du maximum de l'id dans la BDD -----//
		PreparedStatement rechercheMaxID=accesbdd.getConnexion().prepareStatement(
				"SELECT MAX(idLocalisation) FROM localisation");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incr�mentation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		//----- Insertion d'une localisation dans la BDD -----//
		PreparedStatement ajout =accesbdd.getConnexion().prepareStatement(
				"INSERT INTO localisation "
				+ "(idLocalisation,Adresse,CodePostal,Ville) " // Parametre de la table
				+ "VALUES (?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setString(2,aAjouter.getAdresse());
		ajout.setString(3,aAjouter.getCodePostal());
		ajout.setString(4,aAjouter.getVille());
				
		ajout.executeUpdate(); // Ex�cution de la requete SQL
		ajout.close(); // Fermeture de la requete SQL
		//deconnecter();
		
		return aAjouter.getId();
	}
	
	//----- Recherche d'une localisation dans la BDD -----//
	public Localisation rechercher(Integer aChercher) throws SQLException{
		Localisation trouvee=null;
		
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM localisation WHERE idLocalisation=?");
		
		recherche.setInt(1, aChercher.intValue());
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		if(resultat.next()){	// S'il a trouv� la localisation
			trouvee=new Localisation(resultat.getString("Adresse"), resultat.getString("CodePostal"), resultat.getString("Ville"));
			trouvee.setId(new Integer(resultat.getInt("idLocalisation")));
		}
				
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		//deconnecter();
		
		return trouvee;
	}
	
	//----- Recherche d'une localisation dans la BDD -----//
	public Localisation rechercher(short type, String aChercher) throws SQLException{
		Localisation trouvee=null;
		PreparedStatement recherche;
		
		switch(type){
		case 0:	// Recherche d'une adresse
			recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM localisation WHERE Adresse=?");
			break;
			
		case 1:	// Recherche d'une ville
			recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM localisation WHERE Ville=?");
			break;
			
		case 2: // Recherche d'un code postal
			recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM localisation WHERE CodePostal=?");
			break;
			
		default:
			recherche=null;
		}
		
		recherche.setString(1, aChercher);
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		if(resultat.next()){	// S'il a trouv� la localisation
			trouvee=new Localisation(resultat.getString("Adresse"), resultat.getString("CodePostal"), resultat.getString("Ville"));
			trouvee.setId(new Integer(resultat.getInt("idLocalisation")));
		}
				
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		
		return trouvee;
	}
	
	//----- Modifier une localisation dans la BDD -----//
	public void modifier(Localisation aModifier) throws SQLException{
		//----- Modification de la localisation � partir de l'id -----//
		PreparedStatement modifie=accesbdd.getConnexion().prepareStatement(
				"UPDATE localisation SET "
				+"Adresse=?, CodePostal=?, Ville=? "
				+"WHERE idLocalisation=?");
		modifie.setString(1, aModifier.getAdresse());
		modifie.setString(2, aModifier.getCodePostal());
		modifie.setString(3, aModifier.getVille());
		modifie.setInt(4, aModifier.getId().intValue());		
		
		modifie.executeUpdate();	// Ex�cution de la requ�te SQL
						
		modifie.close();	// Fermeture requ�te SQL
		//deconnecter();
	}
	
	//----- Supprimer une localisation dans la BDD -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		PreparedStatement supprime=accesbdd.getConnexion().prepareStatement("DELETE FROM localisation WHERE idLocalisation=?");
		supprime.setInt(1, aSupprimer.intValue());
			
		supprime.executeUpdate();	// Ex�cution de la requ�te SQL
						
		supprime.close();	// Fermeture requ�te SQL
		//deconnecter();
	}
	
	public Localisation localisationExist(Localisation localisation) throws SQLException{
		Localisation aVerifier=localisation;
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * "
				+"FROM localisation "
				+"WHERE Adresse=? AND CodePostal=? AND Ville=? ");
		recherche.setString(1, aVerifier.getAdresse());
		recherche.setString(2, aVerifier.getCodePostal());
		recherche.setString(3, aVerifier.getVille());
		
		// On recherche si une personne correspond � celle pass�e en param�tre dans la BDD
		ResultSet resultat=recherche.executeQuery();
		
		if(resultat.next()){			
				aVerifier.setId(new Integer(resultat.getInt("idLocalisation")));			
		}
		else aVerifier=null;
		
		resultat.close();
		recherche.close();
		//deconnecter();
		
		return aVerifier;
	}
	
	
	//----- TESTS OKAY-----//
	/*public static void main(String arg[]){
		AccesBDDLocalisation test=new AccesBDDLocalisation();
		Localisation resultatRech=null;
		Localisation aAjouter = new Localisation("Adresse","94800","Villejuif");
		Localisation aModifier = new Localisation("AdresseModif","94800","VillejuifModif");
		aModifier.setId(new Integer(1));
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
			test.supprimer(aModifier.getId());
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}*/
}
