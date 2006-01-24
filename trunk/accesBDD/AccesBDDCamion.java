package accesBDD;

import java.sql.PreparedStatement;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;

import donnees.Camion;

//----- Classe permettant l'accès à la table Camion, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDCamion extends ConnecteurSQL{
	//----- Ajouter un camion dans la BDD -----//
	public Integer ajouter(Camion aAjouter) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idCamions) FROM Camions ");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion d'un camion dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO camions "
				+ " (idCamions,NomChauffeur,Etat,Volume, Immatriculation)" // Paramètre de la table
				+ " VALUES (?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setString(2,aAjouter.getNomChauffeur());
		ajout.setInt(3,aAjouter.getDispo().intValue());
		ajout.setInt(4,aAjouter.getVolume().intValue());
		ajout.setString(5, aAjouter.getNumero());
		
		ajout.executeUpdate();	// Execution de la requête SQL
		ajout.close();	// Fermeture requête SQL
		
		/*----- Ajout de la relation entre l'origine/destination et le camion dans la 
				table camions_has_localisation -----*/
		AccesBDDCamion_has_Localisation rel=new AccesBDDCamion_has_Localisation();
		rel.ajouter(aAjouter.getId(), aAjouter.getIdOrigine(), aAjouter.getIdDestination());
		
		return aAjouter.getId();
	}
	
	//----- Supprimer un camion -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		PreparedStatement supprime=
			connecteur.getConnexion().prepareStatement(
				"DELETE FROM camions WHERE idCamions=?");
		supprime.setInt(1, aSupprimer.intValue());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		supprime.close();	// Fermeture requête SQL
	}
	
	//----- Lister les camions -----//
	public Vector lister() throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		Vector liste=new Vector();
		AccesBDDCamion_has_Localisation bddLoc=new  AccesBDDCamion_has_Localisation();
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
		"SELECT * FROM camions");
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			Camion courant=new Camion(new Integer(resultat.getInt("idCamions")),
					resultat.getString("Immatriculation"), 
					new Integer(resultat.getInt("Etat")),
					new Integer(resultat.getInt("Volume")), 
					resultat.getString("nomChauffeur"),
					bddLoc.recherche(AccesBDDCamion_has_Localisation.ORIGINE, new Integer(resultat.getInt("idCamions"))), 
					bddLoc.recherche(AccesBDDCamion_has_Localisation.DESTINATION, new Integer(resultat.getInt("idCamions"))));
			liste.add(courant);
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		return liste;
	}
	
	//----- Modification des attributs d'un camion dans la BDD -----//
	public void modifier(Camion aModifier) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		//----- Modification d'une personne à partir de l'id -----//
		PreparedStatement modifie=connecteur.getConnexion().prepareStatement(
				"UPDATE camions SET NomChauffeur=?, Etat=?, Volume=?, Immatriculation=? "
				+"WHERE idCamions=?");
		modifie.setString(1, aModifier.getNomChauffeur());
		modifie.setInt(2, aModifier.getDispo().intValue());
		modifie.setInt(3, aModifier.getVolume().intValue());
		modifie.setString(4, aModifier.getNumero());
		modifie.setInt(5, aModifier.getId().intValue());
		
		modifie.executeUpdate();	// Exécution de la requête SQL
		
		//----- Modification de la localisation associée à la camion -----//
		AccesBDDCamion_has_Localisation bddLoc=new AccesBDDCamion_has_Localisation();
		bddLoc.modifier(aModifier.getId(), aModifier.getIdOrigine(), aModifier.getIdDestination());
		
		modifie.close();	// Fermeture requête SQL
	}

	public static void main(String arg[]){
		AccesBDDCamion test=new AccesBDDCamion();

		Camion aAjouter = new Camion("1013Tfdgf",new Integer(0),new Integer(2),"Legfg",new Integer(1),new Integer(2));
		Camion aAjouter1 = new Camion("101gfgf",new Integer(0),new Integer(2),"Lgfgfc",new Integer(1),new Integer(2));
		Camion aAjouter2 = new Camion("1013fddf",new Integer(0),new Integer(2),"Lgfdfgfanc",new Integer(1),new Integer(2));
		try{
			test.ajouter(aAjouter);
			test.ajouter(aAjouter1);
			test.ajouter(aAjouter2);
			Vector v=null;
			v=test.lister();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
