package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;

import donnees.Camion;
import donnees.Entrepot;

//----- Classe permettant l'accès à la table Camion, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDCamion{
	
	private AccesBDD accesbdd;
	
	public AccesBDDCamion(AccesBDD accesbdd){
		this.accesbdd = accesbdd;
	}
	
	//----- Ajouter un camion dans la BDD -----//
	public Integer ajouter(Camion aAjouter) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=accesbdd.getConnexion().prepareStatement("SELECT MAX(idCamions) FROM Camions ");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion d'un camion dans la BDD -----//
		PreparedStatement ajout =accesbdd.getConnexion().prepareStatement(
				"INSERT INTO camions "
				+ " (idCamions,Etat,Volume, Immatriculation,Origine,Destination,Hauteur,Largeur,Profondeur,VolumeDispo)" // Paramètre de la table
				+ " VALUES (?,?,?,?,?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setInt(2,aAjouter.getDisponibilite().intValue());
		ajout.setFloat(3,aAjouter.getVolume().floatValue());
		ajout.setString(4, aAjouter.getNumero());
		ajout.setInt(5, aAjouter.getOrigine().getId().intValue());
		ajout.setInt(6, aAjouter.getDestination().getId().intValue());
		ajout.setFloat(7,aAjouter.getHauteur().floatValue());
		ajout.setFloat(8, aAjouter.getLargeur().floatValue());
		ajout.setFloat(9, aAjouter.getProfondeur().floatValue());
		ajout.setFloat(10, aAjouter.getVolumeDispo().floatValue());
		
		ajout.executeUpdate();	// Execution de la requête SQL
		ajout.close();	// Fermeture requête SQL
		//deconnecter();
		
		return aAjouter.getId();
	}
	
	//----- Supprimer un camion -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		PreparedStatement supprime=accesbdd.getConnexion().prepareStatement("DELETE FROM camions WHERE idCamions=?");
		supprime.setInt(1, aSupprimer.intValue());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		supprime.close();	// Fermeture requête SQL
		//deconnecter();
	}
	
	//----- Lister les camions -----//
	public Vector lister(/*Entrepot entActuel*/) throws SQLException{
		Vector liste=new Vector();
		AccesBDDEntrepot tableEnt = new AccesBDDEntrepot(accesbdd);
		
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM camions "/*WHERE Origine=?"*/);
		//recherche.setInt(1, entActuel.getId().intValue());
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			liste.add(new Camion(
					new Integer(resultat.getInt("idCamions")),
					resultat.getString("Immatriculation"), 
					new Integer(resultat.getInt("Etat")),
					new Float(resultat.getFloat("Largeur")), 
					new Float(resultat.getFloat("Hauteur")),
					new Float(resultat.getFloat("Profondeur")),
					new Float(resultat.getFloat("Volume")),
					new Float(resultat.getFloat("VolumeDispo")),
					tableEnt.rechercher(new Integer(resultat.getInt("Origine"))), 
					tableEnt.rechercher(new Integer(resultat.getInt("Destination")))));
		}
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		//deconnecter();
		
		return liste;
	}
	
	//----- Liste les camions en fonction de leur état
	public Vector listerParEtat(int etat,Entrepot entActuel) throws SQLException{
		Vector liste=new Vector();
		
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM camions WHERE Etat=? AND Origine=?");
		recherche.setInt(1, etat);
		recherche.setInt(2, entActuel.getId().intValue());
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			// Création de l'objet courant et ajout dans la liste
			liste.add(new Camion(
					new Integer(resultat.getInt("idCamions")),
					resultat.getString("Immatriculation"), 
					new Integer(resultat.getInt("Etat")),
					new Float(resultat.getFloat("Largeur")), 
					new Float(resultat.getFloat("Hauteur")),
					new Float(resultat.getFloat("Profondeur")),
					new Float(resultat.getFloat("Volume")),
					new Float(resultat.getFloat("VolumeDispo")),
					new AccesBDDEntrepot(accesbdd).rechercher(new Integer(resultat.getInt("Origine"))), 
					new AccesBDDEntrepot(accesbdd).rechercher(new Integer(resultat.getInt("Destination")))));
		}
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		//deconnecter();
		
		// On ordonne la liste
		Collections.sort(liste);
		
		return liste;
	}
	
	//	----- Lister les camions -----//
	public Vector listerParDest(Integer Destination) throws SQLException{
		Vector liste=new Vector();
		
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM camions WHERE Destination=?");
		recherche.setInt(1, Destination.intValue());
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		Entrepot destination=new AccesBDDEntrepot(accesbdd).rechercher(Destination);
		while(resultat.next()){
			liste.add(new Camion(
					new Integer(resultat.getInt("idCamions")),
					resultat.getString("Immatriculation"), 
					new Integer(resultat.getInt("Etat")),
					new Float(resultat.getFloat("Largeur")), 
					new Float(resultat.getFloat("Hauteur")),
					new Float(resultat.getFloat("Profondeur")),
					new Float(resultat.getFloat("Volume")),
					new Float(resultat.getFloat("VolumeDispo")),
					new AccesBDDEntrepot(accesbdd).rechercher(new Integer(resultat.getInt("Origine"))), 
					destination));
		}
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		//deconnecter();
		
		return liste;
	}
	
	//----- Modification des attributs d'un camion dans la BDD -----//
	public void modifier(Camion aModifier) throws SQLException{
		//----- Modification d'une personne à partir de l'id -----//
		PreparedStatement modifie=accesbdd.getConnexion().prepareStatement(
				"UPDATE camions SET Immatriculation=?, Etat=?, Volume=?, Origine=?, Destination=?, Largeur=?, Hauteur=?, Profondeur=?, VolumeDispo=?"
				+"WHERE idCamions=?");
		modifie.setString(1, aModifier.getNumero());
		modifie.setInt(2, aModifier.getDisponibilite().intValue());
		modifie.setFloat(3, aModifier.getVolume().floatValue());
		modifie.setInt(4, aModifier.getOrigine().getId().intValue());
		modifie.setInt(5, aModifier.getDestination().getId().intValue());
		modifie.setFloat(6, aModifier.getLargeur().floatValue());
		modifie.setFloat(7, aModifier.getHauteur().floatValue());
		modifie.setFloat(8, aModifier.getProfondeur().floatValue());
		modifie.setFloat(9, aModifier.getVolumeDispo().floatValue());
		modifie.setInt(10, aModifier.getId().intValue());
		
		
		modifie.executeUpdate();	// Exécution de la requête SQL
		
		modifie.close();	// Fermeture requête SQL
		//deconnecter();
	}
	
	//----- Rechercher un camion dans la BDD -----//
	public Camion rechercher(Integer aChercher) throws SQLException{
		Camion trouvee=null;
		
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM camions WHERE idCamions=?");
		recherche.setInt(1, aChercher.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next()){	// S'il a trouvé la personne
			AccesBDDEntrepot bddLoc=new AccesBDDEntrepot(accesbdd);
			trouvee=new Camion(new Integer(resultat.getInt("idCamions")),
					resultat.getString("Immatriculation"), 
					new Integer(resultat.getInt("Etat")),
					new Float(resultat.getFloat("Largeur")), 
					new Float(resultat.getFloat("Hauteur")),
					new Float(resultat.getFloat("Profondeur")),
					new Float(resultat.getFloat("Volume")),
					new Float(resultat.getFloat("VolumeDispo")),
					bddLoc.rechercher(new Integer(resultat.getInt("Origine"))),
					bddLoc.rechercher(new Integer(resultat.getInt("Destination"))));
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		//deconnecter();
		
		return trouvee;
	}
}
