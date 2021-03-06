package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import donnees.Preparation;
import donnees.Utilisateur;

/*
 * Classe permettant l'acc�s � la table pr�paration, lien entre le superviseur et le pr�parateur
 */
public class AccesBDDPreparation{
	private AccesBDD accesbdd;
	public AccesBDDPreparation(AccesBDD accesbdd){
		this.accesbdd=accesbdd;
	}
	
	public Vector listerDestAPreparer(Utilisateur preparateur) throws SQLException{
		Vector liste=new Vector();
		Preparation courante=null;
		AccesBDDEntrepot bddEntrepot=new AccesBDDEntrepot(this.accesbdd);
						
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM Preparation WHERE idPreparateur=?");
		recherche.setInt(1, preparateur.getId().intValue());
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		while(resultat.next()){
			courante=new Preparation(
					new Integer(resultat.getInt("idPreparation")),
					preparateur,
					bddEntrepot.rechercher(new Integer(resultat.getInt("Origine"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("idDestination"))), 
					new Float(resultat.getFloat("Volume")),
					new AccesBDDCamion(this.accesbdd).rechercher(new Integer(resultat.getInt("idCamion"))),
					new Integer(resultat.getInt("Etat")),
					new Integer(resultat.getInt("ChargementEnCours")),
					new Integer(resultat.getInt("Chargement")));
			liste.add(courante);
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		//deconnecter();
		return liste;
	}
	
	public Integer ajouter(Preparation aAjouter) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=accesbdd.getConnexion().prepareStatement("SELECT MAX(idPreparation) FROM Preparation");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();	// Renvoie le plus grand ID
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		PreparedStatement ajout =accesbdd.getConnexion().prepareStatement(
				"INSERT INTO preparation "
				+ " (idPreparation,idPreparateur,idDestination,idCamion,Origine,Etat,Volume,ChargementEnCours,Chargement)" // Param�tre de la table
				+ " VALUES (?,?,?,?,?,?,?,?,?)"); 
		
		ajout.setInt(1, aAjouter.getId().intValue());
		ajout.setInt(2, aAjouter.getUtilisateur().getId().intValue());
		ajout.setInt(3, aAjouter.getDestination().getId().intValue());
		ajout.setInt(4, aAjouter.getCamion().getId().intValue());
		ajout.setInt(5, aAjouter.getOrigine().getId().intValue());
		ajout.setInt(6, aAjouter.getEtat().intValue());
		ajout.setFloat(7, aAjouter.getVolume().floatValue());
		ajout.setInt(8, aAjouter.getIdChargementEnCours().intValue());
		ajout.setInt(9, aAjouter.getIdChargement().intValue());
		
		ajout.executeUpdate();	// Execution de la requ�te SQL
		ajout.close();	// Fermeture requ�te SQL
		//deconnecter();
		
		return aAjouter.getId();
	}
	
	public Preparation rechercher(Integer aChercher) throws SQLException{
		Preparation trouvee=null;
		AccesBDDEntrepot bddEntrepot=new AccesBDDEntrepot(this.accesbdd);
		
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM preparation WHERE idPreparation=?");
		recherche.setInt(1, aChercher.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		if(resultat.next()){	// S'il a trouv� la pr�paration
			trouvee=new Preparation(
					aChercher,
					new AccesBDDUtilisateur(this.accesbdd).rechercher(new Integer(resultat.getInt("idPreparateur"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("Origine"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("idDestination"))), 
					new Float(resultat.getFloat("Volume")),
					new AccesBDDCamion(this.accesbdd).rechercher(new Integer(resultat.getInt("idCamion"))),
					new Integer(resultat.getInt("Etat")),
					new Integer(resultat.getInt("ChargementEnCours")),
					new Integer(resultat.getInt("Chargement")));
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		//deconnecter();
		
		return trouvee;
	}
	
	// Permet d'affecter un chargement temporaire � une pr�paration
	public void ajouterChargementTemp(Integer idPreparation, Integer idChargement) throws SQLException{
		PreparedStatement modifier=accesbdd.getConnexion().prepareStatement("UPDATE preparation SET ChargementEnCours=? WHERE idPreparation=?");
		
		modifier.setInt(1, idChargement.intValue());
		modifier.setInt(2, idPreparation.intValue());
		
		modifier.executeUpdate();
		
		modifier.close();	// Fermeture requ�te SQL
		//deconnecter();
	}
	
	public void retirerChargementTemp(Integer idPreparation) throws SQLException{
		PreparedStatement modifier=accesbdd.getConnexion().prepareStatement("UPDATE preparation SET ChargementEnCours=? WHERE idPreparation=?");
		
		modifier.setInt(1, 0);
		modifier.setInt(2, idPreparation.intValue());
		
		modifier.executeUpdate();
		
		modifier.close();	// Fermeture requ�te SQL
		//deconnecter();
	}
	
	public Preparation rechercherAvecChargementTemp(Integer aChercher) throws SQLException{
		Preparation trouvee=null;
		AccesBDDEntrepot bddEntrepot=new AccesBDDEntrepot(this.accesbdd);
		
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM preparation WHERE ChargementEnCours=?");
		recherche.setInt(1, aChercher.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		if(resultat.next()){	// S'il a trouv� la pr�paration
			trouvee=new Preparation(
					new Integer(resultat.getInt("idPreparation")),
					new AccesBDDUtilisateur(this.accesbdd).rechercher(new Integer(resultat.getInt("idPreparateur"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("Origine"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("idDestination"))), 
					new Float(resultat.getFloat("Volume")),
					new AccesBDDCamion(this.accesbdd).rechercher(new Integer(resultat.getInt("idCamion"))),
					new Integer(resultat.getInt("Etat")),
					new Integer(resultat.getInt("ChargementEnCours")),
					new Integer(resultat.getInt("Chargement")));
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		//deconnecter();
		
		return trouvee;
	}
	
	// Permet de garder enm�moire pour la pr�paration le chargement
	public void ajouterChargement(Integer idPreparation, Integer idChargement) throws SQLException{
		PreparedStatement modifier=accesbdd.getConnexion().prepareStatement("UPDATE preparation SET Chargement=? WHERE idPreparation=?");
		
		modifier.setInt(1, idChargement.intValue());
		modifier.setInt(2, idPreparation.intValue());
		
		modifier.executeUpdate();
		
		modifier.close();	// Fermeture requ�te SQL
		//deconnecter();
	}
	
	// Finaliser une pr�paration = supprimer la pr�paration
	public void supprimer(Integer aSupprimer) throws SQLException{
		PreparedStatement supprime=accesbdd.getConnexion().prepareStatement("DELETE FROM preparation WHERE idPreparation=?");
		supprime.setInt(1, aSupprimer.intValue());
				
		supprime.executeUpdate();	// Ex�cution de la requ�te SQL
		
		supprime.close();	// Fermeture requ�te SQL
		//deconnecter();
	}
}
