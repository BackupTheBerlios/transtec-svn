package accesBDD;

import java.sql.PreparedStatement;
import java.util.Vector;
import java.util.Collections;
import java.sql.ResultSet;
import java.sql.SQLException;
import donnees.Camion;
import donnees.Entrepot;

//----- Classe permettant l'acc�s � la table Camion, elle permet de faire les diff�rentes op�rations n�cessaire sur la table -----//

public class AccesBDDCamion extends AccesBDD{
	public AccesBDDCamion(){
		super();
	}
	
	//----- Ajouter un camion dans la BDD -----//
	public Integer ajouter(Camion aAjouter) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=connecter().prepareStatement("SELECT MAX(idCamions) FROM Camions ");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();	// Renvoie le plus grand ID
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		//----- Insertion d'un camion dans la BDD -----//
		PreparedStatement ajout =connecter().prepareStatement(
				"INSERT INTO camions "
				+ " (idCamions,Etat,Volume, Immatriculation,Origine,Destination,Hauteur,Largeur,Profondeur)" // Param�tre de la table
				+ " VALUES (?,?,?,?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setInt(2,aAjouter.getDisponibilite().intValue());
		ajout.setFloat(3,aAjouter.getVolume().floatValue());
		ajout.setString(4, aAjouter.getNumero());
		ajout.setInt(5, aAjouter.getOrigine().getId().intValue());
		ajout.setInt(6, aAjouter.getDestination().getId().intValue());
		ajout.setFloat(7,aAjouter.getHauteur().floatValue());
		ajout.setFloat(8, aAjouter.getLargeur().floatValue());
		ajout.setFloat(9, aAjouter.getProfondeur().floatValue());
		
		ajout.executeUpdate();	// Execution de la requ�te SQL
		ajout.close();	// Fermeture requ�te SQL
		deconnecter();
		
		return aAjouter.getId();
	}
	
	//----- Supprimer un camion -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		PreparedStatement supprime=connecter().prepareStatement("DELETE FROM camions WHERE idCamions=?");
		supprime.setInt(1, aSupprimer.intValue());
				
		supprime.executeUpdate();	// Ex�cution de la requ�te SQL
						
		supprime.close();	// Fermeture requ�te SQL
		deconnecter();
	}
	
	//----- Lister les camions -----//
	public Vector lister() throws SQLException{
		Vector liste=new Vector();
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM camions");
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		while(resultat.next()){
			liste.add(new Camion(
					new Integer(resultat.getInt("idCamions")),
					resultat.getString("Immatriculation"), 
					new Integer(resultat.getInt("Etat")),
					new Float(resultat.getFloat("Largeur")), 
					new Float(resultat.getFloat("Hauteur")),
					new Float(resultat.getFloat("Profondeur")),
					new Float(resultat.getFloat("Volume")),
					new AccesBDDEntrepot().rechercher(new Integer(resultat.getInt("Origine"))), 
					new AccesBDDEntrepot().rechercher(new Integer(resultat.getInt("Destination")))));
		}
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		deconnecter();
		
		return liste;
	}
	
	//----- Liste les camions en fonction de leur �tat
	public Vector listerParEtat(int etat) throws SQLException{
		Vector liste=new Vector();
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM camions WHERE Etat=?");
		recherche.setInt(1, etat);
		
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		while(resultat.next()){
			// Cr�ation de l'objet courant et ajout dans la liste
			liste.add(new Camion(
					new Integer(resultat.getInt("idCamions")),
					resultat.getString("Immatriculation"), 
					new Integer(resultat.getInt("Etat")),
					new Float(resultat.getFloat("Largeur")), 
					new Float(resultat.getFloat("Hauteur")),
					new Float(resultat.getFloat("Profondeur")),
					new Float(resultat.getFloat("Volume")),
					new AccesBDDEntrepot().rechercher(new Integer(resultat.getInt("Origine"))), 
					new AccesBDDEntrepot().rechercher(new Integer(resultat.getInt("Destination")))));
		}
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		deconnecter();
		
		// On ordonne la liste
		Collections.sort(liste);
		
		return liste;
	}
	
	//	----- Lister les camions -----//
	public Vector listerParDest(Integer Destination) throws SQLException{
		Vector liste=new Vector();
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM camions WHERE Destination=?");
		recherche.setInt(1, Destination.intValue());
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		Entrepot destination=new AccesBDDEntrepot().rechercher(Destination);
		while(resultat.next()){
			liste.add(new Camion(
					new Integer(resultat.getInt("idCamions")),
					resultat.getString("Immatriculation"), 
					new Integer(resultat.getInt("Etat")),
					new Float(resultat.getFloat("Largeur")), 
					new Float(resultat.getFloat("Hauteur")),
					new Float(resultat.getFloat("Profondeur")),
					new Float(resultat.getFloat("Volume")),
					new AccesBDDEntrepot().rechercher(new Integer(resultat.getInt("Origine"))), 
					destination));
		}
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		deconnecter();
		
		return liste;
	}
	
	//----- Modification des attributs d'un camion dans la BDD -----//
	public void modifier(Camion aModifier) throws SQLException{
		//----- Modification d'une personne � partir de l'id -----//
		PreparedStatement modifie=connecter().prepareStatement(
				"UPDATE camions SET Immatriculation=?, Etat=?, Volume=?, Origine=?, Destination=?, Largeur=?, Hauteur=?, Profondeur=? "
				+"WHERE idCamions=?");
		modifie.setString(1, aModifier.getNumero());
		modifie.setInt(2, aModifier.getDisponibilite().intValue());
		modifie.setFloat(3, aModifier.getVolume().floatValue());
		modifie.setInt(4, aModifier.getOrigine().getId().intValue());
		modifie.setInt(5, aModifier.getDestination().getId().intValue());
		modifie.setFloat(6, aModifier.getLargeur().floatValue());
		modifie.setFloat(7, aModifier.getHauteur().floatValue());
		modifie.setFloat(8, aModifier.getProfondeur().floatValue());
		modifie.setInt(9, aModifier.getId().intValue());
		
		
		modifie.executeUpdate();	// Ex�cution de la requ�te SQL
		
		modifie.close();	// Fermeture requ�te SQL
		deconnecter();
	}
	
	//----- Rechercher un camion dans la BDD -----//
	public Camion rechercher(Integer aChercher) throws SQLException{
		Camion trouvee=null;
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM camions WHERE idCamions=?");
		recherche.setInt(1, aChercher.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		if(resultat.next()){	// S'il a trouv� la personne
			AccesBDDEntrepot bddLoc=new AccesBDDEntrepot();
			trouvee=new Camion(new Integer(resultat.getInt("idCamions")),
					resultat.getString("Immatriculation"), 
					new Integer(resultat.getInt("Etat")),
					new Float(resultat.getFloat("Largeur")), 
					new Float(resultat.getFloat("Hauteur")),
					new Float(resultat.getFloat("Profondeur")),
					new Float(resultat.getFloat("Volume")),
					bddLoc.rechercher(new Integer(resultat.getInt("Origine"))),
					bddLoc.rechercher(new Integer(resultat.getInt("Destination"))));
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
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
