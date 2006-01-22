package accesBDD;
import donnees.Entrepot;
import java.sql.*;

//----- Classe permettant l'acc�s � la table Entrepot, elle permet de faire les diff�rentes op�rations n�cessaire sur la table -----//

public class AccesBDDEntrepot {
	//----- Ajouter un entrepot dans BDD -----//
	public long ajouter(Entrepot aAjouter, ConnecteurSQL connecteur) throws SQLException{
		AccesBDDLocalisation loc=new AccesBDDLocalisation();
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idEntrepots) FROM entrepots");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(resultat.getInt(1)+1); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO entrepots"
				+ " (idEntrepots,Localisation_idLocalisation,Telephone)" // Parametre de la table
				+ " VALUES (?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId());
		ajout.setInt(2,loc.ajouter(aAjouter.getLocalisation(), connecteur));
		ajout.setString(3,aAjouter.getTelephone());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		return 1;

	}
	
	//----- Modifier les informations d'une entrep�t -----//
	public void modifier(Entrepot aModifier, ConnecteurSQL connecteur) throws SQLException{
		//----- Modification d'une personne � partir de l'id -----//
		PreparedStatement modifie=
			connecteur.getConnexion().prepareStatement(
				"UPDATE entrepots SET Telephone=? WHERE idEntrepots=?");
		modifie.setString(1, aModifier.getTelephone());
				
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
		supprime.setInt(1, aSupprimer.getId());
				
		supprime.executeUpdate();	// Ex�cution de la requ�te SQL
		
		supprime.close();	// Fermeture requ�te SQL
	}

public static void main(String arg[]){
		AccesBDDEntrepot test=new AccesBDDEntrepot();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		//Timestamp date=new Timestamp(10);
		Entrepot aAjouter = new Entrepot("adresse","94800","Villejuif","06-15-11-31-30");
		try{
			test.ajouter(aAjouter,connecteur);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
