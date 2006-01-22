package accesBDD;
import donnees.Entrepot;
//import donnees.Personne;
//import donnees.Utilisateur;

import java.util.Vector;
import java.sql.*;

//----- Classe permettant l'acc�s � la table Entrepot, elle permet de faire les diff�rentes op�rations n�cessaire sur la table -----//

public class AccesBDDEntrepot {
	//----- Ajouter un entrepot dans BDD -----//
	public Integer ajouter(Entrepot aAjouter, ConnecteurSQL connecteur) throws SQLException{
		AccesBDDLocalisation loc=new AccesBDDLocalisation();
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idEntrepots) FROM entrepots");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO entrepots "
				+ "(idEntrepots,Localisation_idLocalisation,Telephone) " // Parametre de la table
				+ "VALUES (?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setInt(2,loc.ajouter(aAjouter.getLocalisation(), connecteur));
		ajout.setString(3,aAjouter.getTelephone());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL

		return aAjouter.getId();
	}
	
	//----- Modifier les informations d'une entrep�t -----//
	public void modifier(Entrepot aModifier, ConnecteurSQL connecteur) throws SQLException{
		//----- Modification d'une personne � partir de l'id -----//
		PreparedStatement modifie=
			connecteur.getConnexion().prepareStatement(
				"UPDATE entrepots SET Telephone=? WHERE idEntrepots=?");
		modifie.setString(1, aModifier.getTelephone());
		modifie.setInt(2, aModifier.getId().intValue());
				
		modifie.executeUpdate();	// Ex�cution de la requ�te SQL
		
		//----- Modification de la localisation associ�e � la personne -----//
		AccesBDDLocalisation bddLoc=new AccesBDDLocalisation();
		bddLoc.modifier(aModifier.getLocalisation(), connecteur);
		
		modifie.close();	// Fermeture requ�te SQL
	}
	
	//----- Supprimer un entrep�t de la BDD -----//
	public void supprimer(Entrepot aSupprimer, ConnecteurSQL connecteur) throws SQLException{
		PreparedStatement supprime=
			connecteur.getConnexion().prepareStatement(
				"DELETE FROM entrepots WHERE idEntrepots=?");
		supprime.setInt(1, aSupprimer.getId().intValue());
				
		supprime.executeUpdate();	// Ex�cution de la requ�te SQL
		
		supprime.close();	// Fermeture requ�te SQL
	}
	
	//----- Lister les entrepots -----//
	public Vector lister(ConnecteurSQL connecteur) throws SQLException{
		AccesBDDLocalisation loc=new AccesBDDLocalisation();
		Vector liste=new Vector();
		Entrepot courant=null;
		
		PreparedStatement rechercheMaxID=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM entrepots");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		
		while(resultat.next()){
			courant=new Entrepot(loc.rechercher(AccesBDDLocalisation.ID, resultat.getInt("Localisation_idLocalisation"), connecteur), 
					resultat.getString("telephone"));
			courant.setId(new Integer(resultat.getInt("idEntrepots")));
			liste.add(courant);
		}
		
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		return liste;
	}
	
	//----- Rechercher un entrepot -----//
	public Entrepot rechercher(Integer aChercher, ConnecteurSQL connecteur) throws SQLException{
		Entrepot trouvee=null;
		AccesBDDLocalisation loc=new AccesBDDLocalisation();
				
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
			"SELECT * FROM entrepots WHERE idEntrepots=?");
		
		recherche.setInt(1, aChercher.intValue());
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();
		trouvee=new Entrepot(loc.rechercher(AccesBDDLocalisation.ID, resultat.getInt("Localisation_idLocalisation"), connecteur), 
				resultat.getString("telephone"));
		trouvee.setId(new Integer(resultat.getInt("idEntrepots")));
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		
		return trouvee;
	}

	//----- TEST OKAY -----//
	public static void main(String arg[]){
		AccesBDDEntrepot test=new AccesBDDEntrepot();
		Entrepot rec=null;
		ConnecteurSQL connecteur = new ConnecteurSQL();
		//Timestamp date=new Timestamp(10);
		Entrepot aAjouter = new Entrepot("adresse","94800","Villejuif","06-15-11-31-30");
		Entrepot aModifier = new Entrepot("adresse2","94800","Villejuif","06-15-115225230");
		try{
			test.ajouter(aAjouter,connecteur);
			aModifier.setId(aAjouter.getId());
			aModifier.getLocalisation().setId(aAjouter.getLocalisation().getId());
			test.modifier(aModifier, connecteur);
			rec=test.rechercher(aModifier.getId(), connecteur);
			test.ajouter(aAjouter, connecteur);
			Vector liste=null;
			liste=test.lister(connecteur);
			test.supprimer(aModifier, connecteur);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
