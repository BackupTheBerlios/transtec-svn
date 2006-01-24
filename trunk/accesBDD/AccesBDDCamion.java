package accesBDD;

import java.sql.PreparedStatement;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;

import donnees.Camion;
import donnees.Entrepot;
import donnees.Localisation;

//----- Classe permettant l'accès à la table Camion, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDCamion extends AccesBDD{
	public AccesBDDCamion(){
		super();
	}
	
	//----- Ajouter un camion dans la BDD -----//
	public Integer ajouter(Camion aAjouter) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=connecter().prepareStatement("SELECT MAX(idCamions) FROM Camions ");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion d'un camion dans la BDD -----//
		PreparedStatement ajout =connecter().prepareStatement(
				"INSERT INTO camions "
				+ " (idCamions,Etat,Volume, Immatriculation,Origine,Destination)" // Paramètre de la table
				+ " VALUES (?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setInt(2,aAjouter.getDisponibilite().intValue());
		ajout.setInt(3,aAjouter.getVolume().intValue());
		ajout.setString(4, aAjouter.getNumero());
		ajout.setInt(5, aAjouter.getOrigine().getId().intValue());
		ajout.setInt(6, aAjouter.getDestination().getId().intValue());
		
		ajout.executeUpdate();	// Execution de la requête SQL
		ajout.close();	// Fermeture requête SQL
		deconnecter();
		
		return aAjouter.getId();
	}
	
	//----- Supprimer un camion -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		PreparedStatement supprime=connecter().prepareStatement("DELETE FROM camions WHERE idCamions=?");
		supprime.setInt(1, aSupprimer.intValue());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		supprime.close();	// Fermeture requête SQL
		deconnecter();
	}
	
	//----- Lister les camions -----//
	public Vector lister() throws SQLException{
		Vector liste=new Vector();
		AccesBDDEntrepot bddEnt=new AccesBDDEntrepot();
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM camions");
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			Camion courant=new Camion(
					new Integer(resultat.getInt("idCamions")),
					resultat.getString("Immatriculation"), 
					new Integer(resultat.getInt("Etat")),
					new Integer(resultat.getInt("Volume")), 
					bddEnt.rechercher(new Integer(resultat.getInt("Origine"))), 
					bddEnt.rechercher(new Integer(resultat.getInt("Destination"))));
			liste.add(courant);
		}
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		deconnecter();
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
