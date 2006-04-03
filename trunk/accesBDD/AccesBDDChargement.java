package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import donnees.Chargement;
import donnees.Colis;


//----- Classe permettant l'accès à la table Chargement, elle permet de faire les différentes
//		opérations nécessaire sur la table -----//
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
		ajout.setFloat(4, aAjouter.getVolChargement().floatValue());
		ajout.setTimestamp(5, aAjouter.getDate());
		ajout.setInt(6, aAjouter.getUtilisateur().getId().intValue());
		ajout.setString(7, aAjouter.getCodeBarre());
				
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
	public void supprimer_colis(Colis aSupprimer,Chargement charg) throws SQLException{
		
		PreparedStatement supprime=connecter().prepareStatement("DELETE FROM chargement_colis WHERE idChargement=? AND idColis=? ");
		supprime.setInt(1, charg.getId().intValue());
		supprime.setInt(2, aSupprimer.getId().intValue());
				
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
					new Float(resultat.getFloat("VolChargement")),
					bddUtilisateur.rechercher(new Integer(resultat.getInt("Users_idUsers"))),
					resultat.getTimestamp("DateCreation"),
					resultat.getString("CodeBarre")));
		}
		
		recherche.close();
		resultat.close();
		deconnecter();
		
		return liste;
	}
	
	//----- Lister les colis présents dans un chargement -----//
	public Vector listerColis(Integer idChargement) throws SQLException{
		Vector liste=new Vector();
		AccesBDDColis bddColis=new AccesBDDColis();
		Colis courant;
	
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM Chargement_Colis WHERE idChargement=?");
		recherche.setInt(1, idChargement.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			courant=bddColis.rechercher(new Integer(resultat.getInt("idColis")));
			courant.setNumeroDsCharg(new Integer(resultat.getInt("Numero")));
			liste.add(courant);
		}
		
		recherche.close();
		resultat.close();
		deconnecter();
		
		return liste;
	}
	
	//----- Ajouter des colis dans un chargement -----//
	public void AjouterColis(Chargement chargement, Vector listeColis) throws SQLException{
		PreparedStatement ajouter=null;
		for(int i=0;i<listeColis.size();i++){
			ajouter=connecter().prepareStatement("INSERT INTO Chargement_Colis "
					+"(idChargement, idColis, Numero) "
					+"VALUES (?,?,?)");
			ajouter.setInt(1, chargement.getId().intValue());
			ajouter.setInt(2, ((Colis)listeColis.get(i)).getId().intValue());
			ajouter.setInt(3, ((Colis)listeColis.get(i)).getNumeroDsCharg().intValue());
			
			ajouter.executeUpdate();
		}
		if(listeColis.size()!=0){
			ajouter.close();
			deconnecter();
		}
	}
	
	public Chargement rechercher(String codeBarre) throws SQLException{
		Chargement chargement=null;
		AccesBDDCamion bddCamion=new AccesBDDCamion();
		AccesBDDUtilisateur bddUtilisateur=new AccesBDDUtilisateur();
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM chargement WHERE CodeBarre=?");
		recherche.setString(1, codeBarre);
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next()){
			chargement=new Chargement(
				new Integer(resultat.getInt("idChargement")),
				bddCamion.rechercher(new Integer(resultat.getInt("Camions_idCamions"))),
				new Integer(resultat.getInt("NbColis")),
				new Float(resultat.getFloat("VolChargement")),
				bddUtilisateur.rechercher(new Integer(resultat.getInt("Users_idUsers"))),
				resultat.getTimestamp("DateCreation"),
				resultat.getString("CodeBarre"));
			
		}
		
		recherche.close();
		resultat.close();
		deconnecter();
		
		return chargement;
	}
	
	public Chargement rechercher(Integer idChargement) throws SQLException{
		Chargement chargement=null;
		AccesBDDCamion bddCamion=new AccesBDDCamion();
		AccesBDDUtilisateur bddUtilisateur=new AccesBDDUtilisateur();
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM chargement WHERE idChargement=?");
		recherche.setInt(1, idChargement.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next()){
			chargement=new Chargement(
				new Integer(resultat.getInt("idChargement")),
				bddCamion.rechercher(new Integer(resultat.getInt("Camions_idCamions"))),
				new Integer(resultat.getInt("NbColis")),
				new Float(resultat.getFloat("VolChargement")),
				bddUtilisateur.rechercher(new Integer(resultat.getInt("Users_idUsers"))),
				resultat.getTimestamp("DateCreation"),
				resultat.getString("CodeBarre"));
			
		}
		
		recherche.close();
		resultat.close();
		deconnecter();
		
		return chargement;
	}
	
	// Permet de valider un chargement ATTENTION LE SORTIR DE LA PREP DANS CE CAS
	public void valider(Chargement aModifier, Integer idPreparation) throws SQLException{
		//----- Modification de la localisation à partir de l'id -----//
		PreparedStatement modifie=connecter().prepareStatement(
				"UPDATE chargement SET "
				+"NbColis=?,VolChargement=?,DateCreation=?,Etat=? "
				+"WHERE idChargement=?");
		
		
		modifie.setInt(1,aModifier.getNbColis().intValue());
		modifie.setFloat(2,aModifier.getVolChargement().floatValue());
		modifie.setTimestamp(3,aModifier.getDate());
		modifie.setInt(4,aModifier.getEtat());
		modifie.setInt(5,aModifier.getId().intValue());
		
		modifie.executeUpdate();	// Exécution de la requête SQL
						
		modifie.close();	// Fermeture requête SQL
		deconnecter();
		
		AccesBDDPreparation bddPreparation=new AccesBDDPreparation();
		// On enlève également el chargement temporaire temporaire
		bddPreparation.retirerChargementTemp(idPreparation);
		// on ajoute le chargement dans la colonne chargement effectué
		bddPreparation.ajouterChargement(idPreparation, aModifier.getId());
	}
	
	public Float volume(Integer idChargement) throws SQLException{
		Float volume=new Float(0);
		//Chargement chargement=null;
		//AccesBDDCamion bddCamion=new AccesBDDCamion();
		//AccesBDDUtilisateur bddUtilisateur=new AccesBDDUtilisateur();
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT VolChargement FROM chargement WHERE idChargement=?");
		recherche.setInt(1, idChargement.intValue());
		
		ResultSet resultat=recherche.executeQuery();
		if(resultat.next())
			volume=new Float(resultat.getFloat("VolChargement"));
		
		recherche.close();
		resultat.close();
		deconnecter();
		
		return volume;
	}
}
