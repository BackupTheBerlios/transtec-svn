package accesBDD;

import java.sql.*;
import java.util.Vector;
import donnees.Colis;
import donnees.Destination;

//----- Classe permettant l'accès à la table Colis, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDColis{
	private AccesBDD accesbdd;
	public AccesBDDColis(AccesBDD accesbdd){
		this.accesbdd=accesbdd;
	}
	//----- Ajouter un colis dans la BDD -----//
	public Integer ajouter(Colis aAjouter) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		
		// Préparation de la requête SQL
		PreparedStatement rechercheMaxID=accesbdd.getConnexion().prepareStatement("SELECT MAX(idColis) FROM colis ");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet

		// Fermeture des connexions
		resultat.close();
		rechercheMaxID.close();
		
		//----- Insertion du colis dans la BDD -----//
		
		// Préparation de la requête SQL
		PreparedStatement ajout =accesbdd.getConnexion().prepareStatement(
				"INSERT INTO colis "
				+ "(idColis,ModelesColis_idModelesColis,Createur,Expediteur,Destinataire,Destination,Code_barre, "
				+"Poids,DateDepot,Valeur,Fragilite,Volume,Origine,EntrepotEnCours)" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setInt(2,aAjouter.getModele().getId().intValue());
		ajout.setInt(3,aAjouter.getUtilisateur().getId().intValue());
		ajout.setInt(4,aAjouter.getExpediteur().getId().intValue());
		ajout.setInt(5,aAjouter.getDestinataire().getId().intValue());
		ajout.setInt(6,aAjouter.getDestination().getId().intValue());
		ajout.setString(7,aAjouter.getCode_barre());
		ajout.setInt(8,aAjouter.getPoids().intValue());
		ajout.setTimestamp(9, aAjouter.getDate());
		ajout.setString(10, aAjouter.getValeurDeclaree());
		ajout.setInt(11, aAjouter.getFragilite().intValue());
		ajout.setFloat(12, aAjouter.getVolume().floatValue());
		ajout.setInt(13, aAjouter.getOrigine().getId().intValue());
		ajout.setInt(14, aAjouter.getEntrepot().getId().intValue());
		
		ajout.executeUpdate();//execution de la requete SQL
		
		// Fermeture des connexions
		ajout.close();
		//deconnecter();
		
		return aAjouter.getId();
	}
		
	//----- Supprimer un colis de la BDD -----//
	public void supprimer(Integer aSupprimer) throws SQLException{

		// Préparation de la requête SQL
		PreparedStatement supprime=accesbdd.getConnexion().prepareStatement("DELETE FROM colis WHERE idColis=?");
		supprime.setInt(1, aSupprimer.intValue());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		// Fermeture des connexions
		supprime.close();
		//deconnecter();
	}
	
	//----- Modifier les informations d'un colis -----//
	public void modifier(Colis aModifier) throws SQLException{
		//----- Modification de la localisation à partir de l'id -----//
		PreparedStatement modifie=accesbdd.getConnexion().prepareStatement(
				"UPDATE colis SET "
				+"ModelesColis_idModelesColis=?,Createur=?,Expediteur=?,Destinataire=?,Destination=?,Code_barre=?,Poids=?,DateDepot=?, Valeur=?,Fragilite=?,Volume=?, Origine=?, EntrepotEnCours=?"
				+"WHERE idColis=?");
		
		
		modifie.setInt(1,aModifier.getModele().getId().intValue());
		modifie.setInt(2,aModifier.getUtilisateur().getId().intValue());
		modifie.setInt(3,aModifier.getExpediteur().getId().intValue());
		modifie.setInt(4,aModifier.getDestinataire().getId().intValue());
		modifie.setInt(5,aModifier.getDestination().getId().intValue());
		modifie.setString(6,aModifier.getCode_barre());
		modifie.setInt(7,aModifier.getPoids().intValue());
		modifie.setTimestamp(8, aModifier.getDate());
		modifie.setString(9, aModifier.getValeurDeclaree());
		modifie.setInt(10, aModifier.getFragilite().intValue());
		modifie.setFloat(11, aModifier.getVolume().floatValue());
		modifie.setInt(12,aModifier.getId().intValue());
		modifie.setInt(13, aModifier.getOrigine().getId().intValue());
		modifie.setInt(14, aModifier.getEntrepot().getId().intValue());

		modifie.executeUpdate();	// Exécution de la requête SQL
		
		//Recherche dans personne has_colis, mais est-ce nécéssaire
						
		modifie.close();	// Fermeture requête SQL
		//deconnecter();
	}
		
	//----- Lister les colis par destination -----//
	public Vector listerDest(Integer idEntrepot) throws SQLException{
		Vector liste=new Vector();
		AccesBDDPersonne bddPersonne=new AccesBDDPersonne(this.accesbdd);
		AccesBDDModelesColis bddModele=new AccesBDDModelesColis(this.accesbdd);
		AccesBDDEntrepot bddEntrepot=new AccesBDDEntrepot(this.accesbdd);
				
		// Préparation de la requête SQL
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM colis WHERE Destination=? ");
		recherche.setInt(1, idEntrepot.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			liste.add(new Colis(
					new Integer(resultat.getInt("idColis")),
					resultat.getString("Code_barre"),
					bddPersonne.rechercher(new Integer(resultat.getInt("Expediteur"))),
					bddPersonne.rechercher(new Integer(resultat.getInt("Destinataire"))),
					new AccesBDDUtilisateur(this.accesbdd).rechercher(new Integer(resultat.getInt("Createur"))),
					new Integer(resultat.getInt("Poids")),
					resultat.getTimestamp("DateDepot"),
					new Integer(resultat.getInt("Fragilite")),
					bddModele.rechercher(new Integer(resultat.getInt("ModelesColis_idModelesColis"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("Origine"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("Destination"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("EntrepotEnCours"))),
					resultat.getString("Valeur"),
					new Float(resultat.getFloat("Volume"))));
		}
				
		// Fermeture des connexions
		resultat.close();
		recherche.close();
		//deconnecter();
		
		return liste;
	}
	
	//----- Rechercher un colis dans la BDD -----//
	public Colis rechercher(Integer aChercher) throws SQLException{
		Colis trouvee=null;
		AccesBDDPersonne bddPersonne=new AccesBDDPersonne(this.accesbdd);
		AccesBDDModelesColis bddModele=new AccesBDDModelesColis(this.accesbdd);
		AccesBDDEntrepot bddEntrepot=new AccesBDDEntrepot(this.accesbdd);
		
		// Préparation de la requête SQL
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM Colis WHERE idColis=?");
		recherche.setInt(1, aChercher.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next()){	// S'il a trouvé le colis
			trouvee=new Colis(
					new Integer(resultat.getInt("idColis")),
					resultat.getString("Code_barre"),
					bddPersonne.rechercher(new Integer(resultat.getInt("Expediteur"))),
					bddPersonne.rechercher(new Integer(resultat.getInt("Destinataire"))),
					new AccesBDDUtilisateur(this.accesbdd).rechercher(new Integer(resultat.getInt("Createur"))),
					new Integer(resultat.getInt("Poids")),
					resultat.getTimestamp("DateDepot"),
					new Integer(resultat.getInt("Fragilite")),
					bddModele.rechercher(new Integer(resultat.getInt("ModelesColis_idModelesColis"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("Origine"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("Destination"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("EntrepotEnCours"))),
					resultat.getString("Valeur"),
					new Float(resultat.getFloat("Volume")));
		}
		
		// Fermeture des connexions
		resultat.close();
		recherche.close();
		//deconnecter();
		
		return trouvee;
	}
	
	public Colis rechercherCode_barre(int aChercher) throws SQLException{
		Colis trouvee=null;
		AccesBDDPersonne bddPersonne=new AccesBDDPersonne(this.accesbdd);
		AccesBDDModelesColis bddModele=new AccesBDDModelesColis(this.accesbdd);
		AccesBDDEntrepot bddEntrepot=new AccesBDDEntrepot(this.accesbdd);
		
		// Préparation de la requête SQL
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM Colis WHERE Code_barre=?");
		recherche.setInt(1, aChercher);
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next()){	// S'il a trouvé le colis
			trouvee=new Colis(
					new Integer(resultat.getInt("idColis")),
					resultat.getString("Code_barre"),
					bddPersonne.rechercher(new Integer(resultat.getInt("Expediteur"))),
					bddPersonne.rechercher(new Integer(resultat.getInt("Destinataire"))),
					new AccesBDDUtilisateur(this.accesbdd).rechercher(new Integer(resultat.getInt("Createur"))),
					new Integer(resultat.getInt("Poids")),
					resultat.getTimestamp("DateDepot"),
					new Integer(resultat.getInt("Fragilite")),
					bddModele.rechercher(new Integer(resultat.getInt("ModelesColis_idModelesColis"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("Origine"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("Destination"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("EntrepotEnCours"))),
					resultat.getString("Valeur"),
					new Float(resultat.getFloat("Volume")));	       
		}
		
		// Fermeture des connexions
		resultat.close();
		recherche.close();
		//deconnecter();
		
		return trouvee;
	}
	
	//----- Recherche de l'id du chargement du colis -----//
	public Integer rechercherIdChargement(Integer idColis) throws SQLException{
		Integer trouvee=null;
		
		// Préparation de la requête SQL
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM Chargement_Colis WHERE idColis=?");
		recherche.setInt(1, idColis.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next())	trouvee=new Integer(resultat.getInt("idChargement"));
		
		// Fermeture des connexions
		recherche.close();
		resultat.close();
		//deconnecter();
		
		return trouvee;
	}
	
	
	// Permet de lister le volume lié à chaque destination
	// Attention cas ou colis appartenant à un chargement pas encor géré!!!!!!
	public Vector calculVolumesDestinations(Integer idEntrepotActuel) throws SQLException{
		AccesBDDEntrepot bddEntrepot=new AccesBDDEntrepot(this.accesbdd);
		Vector liste=new Vector();
		Destination couple;

		// Préparation de la requête SQL
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT Destination,SUM(Volume) Volume FROM colis WHERE EntrepotEnCours=? GROUP BY Destination");
		recherche.setInt(1, idEntrepotActuel.intValue());
		ResultSet resultat=recherche.executeQuery();
		
		// Création d'une Destination la destination et le volume, puis ajout au Vector liste
		while(resultat.next()){
			couple = new Destination(bddEntrepot.rechercher(new Integer(resultat.getInt("Destination"))),
									// On passe des cm3 aux m3 en divisant par 1 000 000
									new Float(resultat.getFloat("Volume")/1000000),
									false);
			liste.add(couple);
		}
		
		// Fermeture des connexions
		recherche.close();
		resultat.close();
		//deconnecter();
		
		return liste;
	}
	
	//----- Lister les colis pouvant être chargé par destination -----//
	public Vector colisACharger(Integer idEntrepot) throws SQLException{
		Vector liste=new Vector();
		AccesBDDPersonne bddPersonne=new AccesBDDPersonne(this.accesbdd);
		AccesBDDModelesColis bddModele=new AccesBDDModelesColis(this.accesbdd);
		AccesBDDEntrepot bddEntrepot=new AccesBDDEntrepot(this.accesbdd);
				
		// Préparation de la requête SQL
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement(
				"SELECT * FROM colis WHERE Destination =? "
				+"AND idColis NOT IN (SELECT idColis FROM chargement_colis)");
		recherche.setInt(1, idEntrepot.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			liste.add(new Colis(
					new Integer(resultat.getInt("idColis")),
					resultat.getString("Code_barre"),
					bddPersonne.rechercher(new Integer(resultat.getInt("Expediteur"))),
					bddPersonne.rechercher(new Integer(resultat.getInt("Destinataire"))),
					new AccesBDDUtilisateur(this.accesbdd).rechercher(new Integer(resultat.getInt("Createur"))),
					new Integer(resultat.getInt("Poids")),
					resultat.getTimestamp("DateDepot"),
					new Integer(resultat.getInt("Fragilite")),
					bddModele.rechercher(new Integer(resultat.getInt("ModelesColis_idModelesColis"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("Origine"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("Destination"))),
					bddEntrepot.rechercher(new Integer(resultat.getInt("EntrepotEnCours"))),
					resultat.getString("Valeur"),
					new Float(resultat.getFloat("Volume"))));
		}
				
		// Fermeture des connexions
		resultat.close();
		recherche.close();
		//deconnecter();
		
		return liste;
	}
}
