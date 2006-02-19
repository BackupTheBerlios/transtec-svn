package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import donnees.Chargement;
import donnees.Colis;


//----- Classe permettant l'accès à la table Chargement, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDChargement extends AccesBDD{
	public AccesBDDChargement(){
		super();
	}
	
	//	----- Ajouter un colis dans la BDD -----//
	public Integer ajouter(Chargement aAjouter) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=connecter().prepareStatement("SELECT MAX(idChargement) FROM chargement");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =connecter().prepareStatement(
				"INSERT INTO chargement"
				+ " (idChargement,Camions_idCamions, NbColis, VolChargement, DateCreation, Users_idUsers, CodeBarre)" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setInt(2,aAjouter.getCamion().getId().intValue());
		ajout.setInt(3, aAjouter.getNbColis().intValue());
		ajout.setFloat(4, aAjouter.getVolChargement().intValue());
		ajout.setTimestamp(5, aAjouter.getDate());
		ajout.setInt(6, aAjouter.getUtilisateur().getId().intValue());
		ajout.setInt(7, aAjouter.getCodeBarre().intValue());
				
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close(); //fermeture requete SQL
		deconnecter();
		
		return aAjouter.getId();
	}
	
	//----- Supprimer un chargement -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		PreparedStatement supprime=connecter().prepareStatement("DELETE FROM chargement WHERE idChargement=?");
		supprime.setInt(1, aSupprimer.intValue());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		supprime.close();	// Fermeture requête SQL
		deconnecter();
	}
	//-----Supprimer un colis d'un chargement----//
	public void supprimer_colis(Colis aSupprimer) throws SQLException{
		PreparedStatement supprime=connecter().prepareStatement("DELETE FROM chargement_colis WHERE idColis=?");
		supprime.setInt(1, aSupprimer.getId().intValue());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		supprime.close();	// Fermeture requête SQL
		deconnecter();
	}
	
	//----- Lister les chargements -----//
	public Vector lister() throws SQLException{
		Vector liste=new Vector();
		AccesBDDCamion bddCamion=new AccesBDDCamion();
		AccesBDDUtilisateur bddUtilisateur=new AccesBDDUtilisateur();
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM chargement");
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			liste.add(new Chargement(
					new Integer(resultat.getInt("idChargement")),
					bddCamion.rechercher(new Integer(resultat.getInt("Camions_idCamions"))),
					new Integer(resultat.getInt("NbColis")),
					new Integer("VolChargement"),
					bddUtilisateur.rechercher(new Integer(resultat.getInt("Users_idUsers"))),
					resultat.getTimestamp("DateCreation"),
					new Integer(resultat.getInt("CodeBarre"))));
		}
		
		recherche.close();
		resultat.close();
		deconnecter();
		
		return liste;
	}
	
	//----- Lister les colis présents dans un chargement -----//
	public Vector listerColis(Integer idChargement) throws SQLException{
		Vector liste=new Vector();
	
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM Chargement_Colis WHERE idChargement=?");
		recherche.setInt(1, idChargement.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			liste.add(new AccesBDDColis().rechercher(new Integer(resultat.getInt("idColis"))));
		}
		
		recherche.close();
		resultat.close();
		deconnecter();
		
		return liste;
	}
	
	public Chargement rechercher(Integer codeBarre) throws SQLException{
		Chargement chargement=null;
		AccesBDDCamion bddCamion=new AccesBDDCamion();
		AccesBDDUtilisateur bddUtilisateur=new AccesBDDUtilisateur();
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM chargement WHERE CodeBarre=?");
		recherche.setInt(1, codeBarre.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next()){
			chargement=new Chargement(
				new Integer(resultat.getInt("idChargement")),
				bddCamion.rechercher(new Integer(resultat.getInt("Camions_idCamions"))),
				new Integer(resultat.getInt("NbColis")),
				new Integer("VolChargement"),
				bddUtilisateur.rechercher(new Integer(resultat.getInt("Users_idUsers"))),
				resultat.getTimestamp("DateCreation"),
				new Integer(resultat.getInt("CodeBarre")));
		}
		
		recherche.close();
		resultat.close();
		deconnecter();
		
		return chargement;
	}
	
	public static void main(String arg[]){
		/*AccesBDDChargement test=new AccesBDDChargement();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		Timestamp date=new Timestamp(10);
		Chargement aAjouter = new Chargement(new Integer(0),new Integer(1),1,new Integer(1),date);
		Chargement aAjouter2 = new Chargement(new Integer(0),new Integer(2),1,new Integer(1),date);
		try{
			test.ajouter(aAjouter,connecteur);
			test.ajouter(aAjouter2,connecteur);
			test.supprimer(aAjouter, connecteur);
			Vector list=test.listerColis(aAjouter2, connecteur);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}*/
	}
}
