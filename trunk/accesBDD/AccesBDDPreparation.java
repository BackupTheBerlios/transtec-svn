package accesBDD;

import java.sql.*;
import java.util.Vector;

import donnees.Preparation;
import donnees.Utilisateur;

/*
 * Classe permettant l'acc�s � la table pr�paration, lien entre le superviseur et le pr�parateur
 */
public class AccesBDDPreparation extends AccesBDD{
	
	public AccesBDDPreparation(){
		super();
	}
	
	public Vector listerDestAPreparer(Utilisateur preparateur) throws SQLException{
		Vector liste=new Vector();
		Preparation courante=null;
		AccesBDDEntrepot bddEntrepot=new AccesBDDEntrepot();
						
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM Preparation WHERE idPreparateur=?");
		recherche.setInt(1, preparateur.getId().intValue());
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		while(resultat.next()){
			courante=new Preparation(
					new Integer(resultat.getInt("idPreparation")),
					preparateur,
					bddEntrepot.rechercher(new Integer(resultat.getInt("Origine"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("idDestination"))), 
					new Float(resultat.getFloat("Volume")),
					new AccesBDDCamion().rechercher(new Integer(resultat.getInt("idCamion"))),
					new Integer(resultat.getInt("Etat")));
			liste.add(courante.toVector());
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		deconnecter();
		return liste;
	}
	
	public Integer ajouter(Preparation aAjouter) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=connecter().prepareStatement("SELECT MAX(idCamions) FROM Camions ");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();	// Renvoie le plus grand ID
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		PreparedStatement ajout =connecter().prepareStatement(
				"INSERT INTO preparation "
				+ " (idPreparation,idPreparateur,idDestination,idCamion,Origine,Etat,Volume)" // Param�tre de la table
				+ " VALUES (?,?,?,?,?,?,?)"); 
		
		ajout.setInt(1, aAjouter.getId().intValue());
		ajout.setInt(2, aAjouter.getUtilisateur().getId().intValue());
		ajout.setInt(3, aAjouter.getDestination().getId().intValue());
		ajout.setInt(4, aAjouter.getCamion().getId().intValue());
		ajout.setInt(5, aAjouter.getOrigine().getId().intValue());
		ajout.setInt(6, aAjouter.getEtat().intValue());
		ajout.setFloat(7, aAjouter.getVolume().floatValue());
		
		ajout.executeUpdate();	// Execution de la requ�te SQL
		ajout.close();	// Fermeture requ�te SQL
		deconnecter();
		
		return aAjouter.getId();
	}
	
	public Preparation rechercher(Integer aChercher) throws SQLException{
		Preparation trouvee=null;
		AccesBDDEntrepot bddEntrepot=new AccesBDDEntrepot();
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM preparation WHERE idPreparation=?");
		recherche.setInt(1, aChercher.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		if(resultat.next()){	// S'il a trouv� la pr�paration
			trouvee=new Preparation(
					aChercher,
					new AccesBDDUtilisateur().rechercher(new Integer(resultat.getInt("idPreparateur"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("Origine"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("idDestination"))), 
					new Float(resultat.getFloat("Volume")),
					new AccesBDDCamion().rechercher(new Integer(resultat.getInt("idCamion"))),
					new Integer(resultat.getInt("Etat")));
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		deconnecter();
		
		return trouvee;
	}
	
	
}
