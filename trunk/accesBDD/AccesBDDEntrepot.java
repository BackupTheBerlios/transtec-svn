package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;

import donnees.Entrepot;

//----- Classe permettant l'acc�s � la table Entrepot, elle permet de faire les diff�rentes op�rations n�cessaire sur la table -----//

public class AccesBDDEntrepot{
		
	private AccesBDD accesbdd;
	
	public AccesBDDEntrepot(AccesBDD accesbdd){
		this.accesbdd = accesbdd;
	}
	
	//----- Ajouter un entrepot dans BDD -----//
	public Integer ajouter(Entrepot aAjouter) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=accesbdd.getConnexion().prepareStatement("SELECT MAX(idEntrepots) FROM entrepots");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();	// Renvoie le plus grand ID
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =accesbdd.getConnexion().prepareStatement(
				"INSERT INTO entrepots "
				+ "(idEntrepots,Localisation_idLocalisation,Telephone) " // Parametre de la table
				+ "VALUES (?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		// Ajout de la localisation
		ajout.setInt(2,new AccesBDDLocalisation(accesbdd).ajouter(aAjouter.getLocalisation()).intValue());
		ajout.setString(3,aAjouter.getTelephone());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		//deconnecter();

		return aAjouter.getId();
	}
	
	//----- Modifier les informations d'une entrep�t -----//
	public void modifier(Entrepot aModifier) throws SQLException{
		PreparedStatement modifie=accesbdd.getConnexion().prepareStatement("UPDATE entrepots SET Telephone=? WHERE idEntrepots=?");
		modifie.setString(1, aModifier.getTelephone());
		modifie.setInt(2, aModifier.getId().intValue());
				
		modifie.executeUpdate();	// Ex�cution de la requ�te SQL
		
		//----- Modification de la localisation associ�e � la personne -----//
		new AccesBDDLocalisation(accesbdd).modifier(aModifier.getLocalisation());
		
		modifie.close();	// Fermeture requ�te SQL
		//deconnecter();
	}
	
	//----- Supprimer un entrep�t de la BDD -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		PreparedStatement supprime=accesbdd.getConnexion().prepareStatement("DELETE FROM entrepots WHERE idEntrepots=?");
		supprime.setInt(1, aSupprimer.intValue());
				
		supprime.executeUpdate();	// Ex�cution de la requ�te SQL
		
		supprime.close();	// Fermeture requ�te SQL
		//deconnecter();
	}
	
	//----- Lister les entrepots -----//
	public Vector lister() throws SQLException{
		AccesBDDLocalisation loc=new AccesBDDLocalisation(accesbdd);
		Vector liste=new Vector();
		Entrepot courant=null;
		
		PreparedStatement rechercheMaxID=accesbdd.getConnexion().prepareStatement("SELECT * FROM entrepots ORDER BY idEntrepots");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		
		while(resultat.next()){
			courant=new Entrepot(loc.rechercher(new Integer(resultat.getInt("Localisation_idLocalisation"))), 
					resultat.getString("telephone"));
			courant.setId(new Integer(resultat.getInt("idEntrepots")));
			liste.add(courant);
		}
		
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		//deconnecter();
		
		// On ordonne la liste
		Collections.sort(liste);
		
		return liste;
	}
	
	//----- Rechercher un entrepot -----//
	public Entrepot rechercher(Integer aChercher) throws SQLException{
		Entrepot trouvee=null;
				
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM entrepots WHERE idEntrepots=?");
		recherche.setInt(1, aChercher.intValue());
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		if(resultat.next()){
			trouvee=new Entrepot(new AccesBDDLocalisation(accesbdd).rechercher(new Integer(
					resultat.getInt("Localisation_idLocalisation"))), 
					resultat.getString("telephone"));
			trouvee.setId(new Integer(resultat.getInt("idEntrepots")));
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		//deconnecter();
		
		return trouvee;
	}

	//----- TEST OKAY -----//
	/*public static void main(String arg[]){
		AccesBDDEntrepot test=new AccesBDDEntrepot();
		Entrepot rec=null;
		Entrepot aAjouter = new Entrepot("adresse","94800","Villejuif","06-15-11-31-30");
		Entrepot aAjouter1 = new Entrepot("adresse","94800","Villejuif","06-15-11-31-30");
		Entrepot aAjouter2 = new Entrepot("adresse","94800","Villejuif","06-15-11-31-30");
		Entrepot aAjouter3 = new Entrepot("adresse","94800","Villejuif","06-15-11-31-30");
		Entrepot aModifier = new Entrepot("adresse2","94800","Villejuif","06-15-115225230");
		try{
			test.ajouter(aAjouter);
			test.ajouter(aAjouter1);
			test.ajouter(aAjouter2);
			test.ajouter(aAjouter3);
			aModifier.setId(aAjouter.getId());
			aModifier.getLocalisation().setId(aAjouter.getLocalisation().getId());
			test.modifier(aModifier);
			rec=test.rechercher(aModifier.getId());
			test.ajouter(aAjouter);
			Vector liste=null;
			liste=test.lister();
			test.supprimer(aModifier.getId());
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}*/
}