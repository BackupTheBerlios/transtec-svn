package accesBDD;

import java.sql.*;
import java.util.Vector;
import donnees.Colis;
import donnees.Entrepot;

//----- Classe permettant l'acc�s � la table Colis, elle permet de faire les diff�rentes op�rations n�cessaire sur la table -----//

public class AccesBDDColis extends AccesBDD{
//	----- Ajouter un colis dans la BDD -----//
	public Integer ajouter(Colis aAjouter) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=connecter().prepareStatement("SELECT MAX(idColis) FROM colis ");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Ex�cution de la requ�te SQL
		resultat.next();	// Renvoie le plus grand ID
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requ�te SQL
		rechercheMaxID.close();	// Fermeture requ�te SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =connecter().prepareStatement(
				"INSERT INTO colis "
				+ "(idColis,ModelesColis_idModelesColis,Createur,Expediteur,Destinataire,Destination,Code_barre, "
				+"Poids,DateDepot,Valeur,Fragilite,Volume)" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"); 
		
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
		ajout.setInt(12, aAjouter.getVolume().intValue());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		deconnecter();
		
		return aAjouter.getId();
	}
		
	//----- Supprimer un colis de la BDD -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		PreparedStatement supprime=connecter().prepareStatement("DELETE FROM colis WHERE idColis=?");
		supprime.setInt(1, aSupprimer.intValue());
				
		supprime.executeUpdate();	// Ex�cution de la requ�te SQL
						
		supprime.close();	// Fermeture requ�te SQL
		deconnecter();
	}
	
	//----- Modifier les informations d'un colis -----//
	public void modifier(Colis aModifier) throws SQLException{
		//----- Modification de la localisation � partir de l'id -----//
		PreparedStatement modifie=connecter().prepareStatement(
				"UPDATE colis SET "
				+"ModelesColis_idModelesColis=?,Createur=?,Expediteur=?,Destinataire=?,Destination=?,Code_barre=?, "
				+"Poids=?,DateDepot=?, Valeur=?,Fragilite=?, Volume=?)"
				+" WHERE idColis=?");
		
		modifie.setInt(1,aModifier.getId().intValue());
		modifie.setInt(2,aModifier.getModele().getId().intValue());
		modifie.setInt(3,aModifier.getUtilisateur().getId().intValue());
		modifie.setInt(4,aModifier.getExpediteur().getId().intValue());
		modifie.setInt(5,aModifier.getDestinataire().getId().intValue());
		modifie.setInt(6,aModifier.getDestination().getId().intValue());
		modifie.setString(7,aModifier.getCode_barre());
		modifie.setInt(8,aModifier.getPoids().intValue());
		modifie.setTimestamp(9, aModifier.getDate());
		modifie.setString(10, aModifier.getValeurDeclaree());
		modifie.setInt(11, aModifier.getFragilite().intValue());
		modifie.setInt(12, aModifier.getVolume().intValue());

		modifie.executeUpdate();	// Ex�cution de la requ�te SQL
		
		//Recherche dans personne has_colis, mais est-ce n�c�ssaire
						
		modifie.close();	// Fermeture requ�te SQL
		deconnecter();
	}
		
	//----- Lister les colis par destination -----//
	public Vector listerDest(Integer idEntrepot) throws SQLException{
		Vector liste=new Vector();
		AccesBDDPersonne bddPersonne=new AccesBDDPersonne();
		AccesBDDModelesColis bddModele=new AccesBDDModelesColis();
				
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM colis WHERE Destination=? ");
		recherche.setInt(1, idEntrepot.intValue());
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		while(resultat.next()){
			liste.add(new Colis(
					new Integer(resultat.getInt("idColis")),
					resultat.getString("Code_barre"),
					bddPersonne.rechercher(new Integer(resultat.getInt("Expediteur"))),
					bddPersonne.rechercher(new Integer(resultat.getInt("Destinataire"))),
					new AccesBDDUtilisateur().rechercher(new Integer(resultat.getInt("Createur"))),
					new Integer(resultat.getInt("Poids")),
					resultat.getTimestamp("DateDepot"),
					new Integer(resultat.getInt("Fragilite")),
					bddModele.rechercher(new Integer(resultat.getInt("ModelesColis_idModelesColis"))),
					new AccesBDDEntrepot().rechercher(new Integer(resultat.getInt("Destination"))),
					resultat.getString("Valeur"),
					new Integer(resultat.getInt("Volume"))));
		}
				
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		deconnecter();
		
		return liste;
	}
	
	//----- Rechercher un colis dans la BDD -----//
	public Colis rechercher(Integer aChercher) throws SQLException{
		Colis trouvee=null;
		AccesBDDPersonne bddPersonne=new AccesBDDPersonne();
		AccesBDDModelesColis bddModele=new AccesBDDModelesColis();
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM Colis WHERE idColis=?");
		recherche.setInt(1, aChercher.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		if(resultat.next()){	// S'il a trouv� le colis
			trouvee=new Colis(
					new Integer(resultat.getInt("idColis")),
					resultat.getString("Code_barre"),
					bddPersonne.rechercher(new Integer(resultat.getInt("Expediteur"))),
					bddPersonne.rechercher(new Integer(resultat.getInt("Destinataire"))),
					new AccesBDDUtilisateur().rechercher(new Integer(resultat.getInt("Createur"))),
					new Integer(resultat.getInt("Poids")),
					resultat.getTimestamp("DateDepot"),
					new Integer(resultat.getInt("Fragilite")),
					bddModele.rechercher(new Integer(resultat.getInt("ModelesColis_idModelesColis"))),
					new AccesBDDEntrepot().rechercher(new Integer(resultat.getInt("Destination"))),
					resultat.getString("Valeur"),
					new Integer(resultat.getInt("Volume")));
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		deconnecter();
		
		return trouvee;
	}
	
	public Colis rechercherCode_barre(int aChercher) throws SQLException{
		Colis trouvee=null;
		AccesBDDPersonne bddPersonne=new AccesBDDPersonne();
		AccesBDDModelesColis bddModele=new AccesBDDModelesColis();
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM Colis WHERE Code_barre=?");
		recherche.setInt(1, aChercher);
		
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		if(resultat.next()){	// S'il a trouv� le colis
			trouvee=new Colis(
					new Integer(resultat.getInt("idColis")),
					resultat.getString("Code_barre"),
					bddPersonne.rechercher(new Integer(resultat.getInt("Expediteur"))),
					bddPersonne.rechercher(new Integer(resultat.getInt("Destinataire"))),
					new AccesBDDUtilisateur().rechercher(new Integer(resultat.getInt("Createur"))),
					new Integer(resultat.getInt("Poids")),
					resultat.getTimestamp("DateDepot"),
					new Integer(resultat.getInt("Fragilite")),
					bddModele.rechercher(new Integer(resultat.getInt("ModelesColis_idModelesColis"))),
					new AccesBDDEntrepot().rechercher(new Integer(resultat.getInt("Destination"))),
					resultat.getString("Valeur"),
					new Integer(resultat.getInt("Volume")));	       
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		deconnecter();
		
		return trouvee;
	}
	
	//----- Recherche de l'id du chargement du colis -----//
	public Integer rechercherIdChargement(Integer idColis) throws SQLException{
		Integer trouvee=null;
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM Chargement_Colis WHERE idColis=?");
		recherche.setInt(1, idColis.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		if(resultat.next())	trouvee=new Integer(resultat.getInt("idChargement"));
		
		recherche.close();
		resultat.close();
		deconnecter();
		
		return trouvee;
	}
	
	// Permet de lister tous les entrepots et le volume des colis a exp�dier
	// Attention cas ou colis appartenant � un chargement pas encor g�rer!!!!!!
	public Vector VolumeDestination() throws SQLException{
		AccesBDDEntrepot bddEntrepot=new AccesBDDEntrepot();
		//AccesBDDColis bddColis=new AccesBDDColis();
		Vector liste=new Vector(), couple=null, listeColis=null;
		int volume;
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM entrepots");
		ResultSet resultat=recherche.executeQuery();
		
		while(resultat.next()){
			volume=0;
			couple=new Vector();
			couple.add(bddEntrepot.rechercher(new Integer(resultat.getInt("Destination"))));
			
			//listeColis=bddColis.listerDest(new Integer(resultat.getInt("idEntrepots")));
			//for(int i=0;i<listeColis.size();i++)	volume+=((Colis)listeColis.get(i)).getModele().getVolume().intValue();
			couple.add(new Integer(resultat.getInt("Volume")));
			liste.add(couple);
		}
		
		recherche.close();
		resultat.close();
		deconnecter();
		
		return liste;
	}
	
	//----- TESTES OKAY -----//
	/*public static void main(String arg[]){
		AccesBDDColis test=new AccesBDDColis();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		Colis rec=null;
		Vector liste=new Vector();
		Timestamp date=new Timestamp(10);
		Colis aAjouter = new Colis("24154654",new Integer(1), new Integer(2),new Integer(2),"52", date, new Integer(1),
				new Integer(2), new Integer(2),"5245");
		Colis aAjouter1 = new Colis("24fdsfds4654",new Integer(1), new Integer(2),new Integer(2),"52", date, new Integer(1),
				new Integer(2), new Integer(2),"5245");
		Colis aAjouter2 = new Colis("24tyuy654",new Integer(1), new Integer(2),new Integer(2),"52", date, new Integer(1),
				new Integer(2), new Integer(2),"5245");
		//Colis aModifier=new Colis(3,4,2,25,date,8,6,2,1,"E-1");
		try{
			test.ajouter(aAjouter);
			test.ajouter(aAjouter1);
			test.ajouter(aAjouter2);
			//aModifier.setId(aAjouter.getId());
			//test.modifier(aModifier);
			//test.ajouter(aAjouter);
			//liste=test.listerDest(aModifier.getIdDestination());
			//test.supprimer(aModifier.getId());
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}*/
}
