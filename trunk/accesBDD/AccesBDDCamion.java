package accesBDD;

import java.sql.PreparedStatement;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;

import donnees.Camion;
import donnees.Entrepot;
import donnees.Localisation;
import donnees.Personne;

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
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM camions");
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			Camion courant=new Camion(
					new Integer(resultat.getInt("idCamions")),
					resultat.getString("Immatriculation"), 
					new Integer(resultat.getInt("Etat")),
					new Integer(resultat.getInt("Volume")), 
					new AccesBDDEntrepot().rechercher(new Integer(resultat.getInt("Origine"))), 
					new AccesBDDEntrepot().rechercher(new Integer(resultat.getInt("Destination"))));
			liste.add(courant);
		}
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		deconnecter();
		
		return liste;
	}
	
	//----- Modification des attributs d'un camion dans la BDD -----//
	public void modifier(Camion aModifier) throws SQLException{
		//----- Modification d'une personne à partir de l'id -----//
		PreparedStatement modifie=connecter().prepareStatement(
				"UPDATE camions SET Immatriculation=?, Etat=?, Volume=?, Origine=?, Destination=? "
				+"WHERE idCamions=?");
		modifie.setString(1, aModifier.getNumero());
		modifie.setInt(2, aModifier.getDisponibilite().intValue());
		modifie.setInt(3, aModifier.getVolume().intValue());
		modifie.setInt(4, aModifier.getOrigine().getId().intValue());
		modifie.setInt(5, aModifier.getDestination().getId().intValue());
		modifie.setInt(6, aModifier.getId().intValue());
		
		modifie.executeUpdate();	// Exécution de la requête SQL
		
		modifie.close();	// Fermeture requête SQL
		deconnecter();
	}
	
	//----- Rechercher un camion dans la BDD -----//
	public Camion rechercher(Integer aChercher) throws SQLException{
		Camion trouvee=null;
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM camions WHERE idCamions=?");
		recherche.setInt(1, aChercher.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next()){	// S'il a trouvé la personne
			AccesBDDEntrepot bddLoc=new AccesBDDEntrepot();
			trouvee=new Camion(new Integer(resultat.getInt("idCamions")),
					resultat.getString("Immatriculation"), 
					new Integer(resultat.getInt("Etat")),
					new Integer(resultat.getString("Volume")), 
					bddLoc.rechercher(new Integer(resultat.getInt("Origine"))),
					bddLoc.rechercher(new Integer(resultat.getInt("Destination"))));
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		deconnecter();
		
		return trouvee;
	}

	/*public static void main(String arg[]){
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
	}*/
}
