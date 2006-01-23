package accesBDD;

import java.sql.*;
import java.util.Vector;

import donnees.Colis;

//----- Classe permettant l'accès à la table Colis, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDColis extends ConnecteurSQL{
	//----- Ajouter un colis dans la BDD -----//
	public long ajouter(Colis aAjouter) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idColis) FROM colis ");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		aAjouter.setId(resultat.getInt(1)+1); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO colis "
				+ " (idColis,Poids,DateDepot,Valeur,Fragilite,Lieu,ModelesColis_idModelesColis,Entrepots_idEntrepots, Code_barre, Users_idUsers )" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setString(2,aAjouter.getPoids());
		ajout.setTimestamp(3,aAjouter.getDate());
		ajout.setString(4,aAjouter.getValeurDeclaree());
		ajout.setInt(5,aAjouter.getFragilite().intValue());
		ajout.setString(6,aAjouter.getLieu());
		ajout.setInt(7,aAjouter.getModele().intValue());
		ajout.setInt(8,aAjouter.getIdDestination().intValue());
		ajout.setString(9, aAjouter.getCode_barre());
		ajout.setInt(10, aAjouter.getIdUtilisateur().intValue());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		
		/*----- Ajout de la relation entre l'expéditeur/destinataire et le colis dans la 
				table Personnes_has_Colis -----*/
		AccesBDDPersonnes_has_Colis rel=new AccesBDDPersonnes_has_Colis();
		rel.ajouter(aAjouter.getId(), aAjouter.getIdExpediteur(), aAjouter.getIdDestinataire(), connecteur);
		
		return aAjouter.getId();
	}
	
	//----- Rechercher un colis dans la BDD -----//
	public int rechercher(int idColis, int typeRecherche){
		ConnecteurSQL connecteur=new ConnecteurSQL();
		//----- Insertion du colis dans la BDD -----//
		/*String RequetteSQL = "SELECT * FROM Colis";
		
		switch(typeRecherche){
			case 0 : // recherche par ID
				RequetteSQL = RequetteSQL.concat(" WHERE id =?");
			case 1 : // recherche par 
			case 2 :
		}
		
		PreparedStatement rechercher =
			connecteur.getConnexion().prepareStatement(
					"SELECT idColis,Poids,DateDepot,Valeur,Fragilite,Lieu"
					+" FROM Colis ");
			
			
		
		ajout.setInt(1,aAjouter.getId());
		ajout.setFloat(2,aAjouter.getPoids());
		ajout.setTimestamp(3,aAjouter.getDate());
		ajout.setFloat(4,aAjouter.getValeurDeclaree());
		ajout.setInt(5,aAjouter.getFragilite());
		ajout.setInt(6,aAjouter.getLieu());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL*/
		return 1;
	}
	
	//----- Supprimer un colis de la BDD -----//
	public void supprimer(Integer aSupprimer) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		PreparedStatement supprime=
			connecteur.getConnexion().prepareStatement(
				"DELETE FROM colis WHERE idColis=?");
		supprime.setInt(1, aSupprimer.intValue());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		supprime.close();	// Fermeture requête SQL
	}
	
	//----- Modifier les informations d'un colis -----//
	public void modifier(Colis aModifier) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		//----- Modification de la localisation à partir de l'id -----//
		PreparedStatement modifie=
			connecteur.getConnexion().prepareStatement(
				"UPDATE colis SET "
				+"Poids=?, DateDepot=?, Valeur=?, Fragilite=?, Lieu=?, ModelesColis_idModelesColis=?, Entrepots_idEntrepots=?, Code_barre=?, Users_idUsers=?"
				+"WHERE idColis=?");
		modifie.setString(1, aModifier.getPoids());
		modifie.setTimestamp(2, aModifier.getDate());
		modifie.setString(3, aModifier.getValeurDeclaree());
		modifie.setInt(4, aModifier.getFragilite().intValue());
		modifie.setString(5, aModifier.getLieu());
		modifie.setInt(6, aModifier.getId().intValue());
		modifie.setInt(7, aModifier.getForme().intValue());
		modifie.setString(8, aModifier.getCode_barre());
		modifie.setInt(9, aModifier.getIdUtilisateur().intValue());
		modifie.setInt(10, aModifier.getIdDestination());

		modifie.executeUpdate();	// Exécution de la requête SQL
		
		//Recherche dans personne has_colis, mais est-ce nécéssaire
						
		modifie.close();	// Fermeture requête SQL
	}
	
	//----- Changer le "lieu" du colis -----//
	public boolean changerLieu(long nouveauLieu, Colis aModifier){
		return true;
	}
	
	//----- Lister les colis par destination -----//
	public Vector listerDest(int idEntrepot) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		Vector liste=new Vector();
		AccesBDDPersonnes_has_Colis idPers=new AccesBDDPersonnes_has_Colis();
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM colis WHERE Entrepots_idEntrepots=? ");
		recherche.setInt(1, idEntrepot);
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			Colis courant=new Colis(new Integer(resultat.getInt("idColis")),
					idPers.getExpediteur(new Integer(resultat.getInt("idColis"))
					, idPers.getDestinataire(new Integer(resultat.getInt("idColis")))
					,resultat.getInt("Users_idUsers"), 
					resultat.getFloat("Poids"),
					resultat.getTimestamp("DateDepot"),
					resultat.getInt("Fragilite"), 
					resultat.getInt("Valeur"), 
					resultat.getInt("ModelesColis_idModelesColis"), 
					resultat.getInt("Entrepots_idEntrepots"), 
					resultat.getString("Lieu"));
			liste.add(courant);
		}
				
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return liste;
	}
	
	//----- TESTES OKAY -----//
	public static void main(String arg[]){
		AccesBDDColis test=new AccesBDDColis();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		Colis rec=null;
		Vector liste=new Vector();
		Timestamp date=new Timestamp(10);
		Colis aAjouter = new Colis(3,4,1,2,date,2,400,1,1,"E-1");
		Colis aModifier=new Colis(3,4,2,25,date,8,6,2,1,"E-1");
		try{
			test.ajouter(aAjouter);
			aModifier.setId(aAjouter.getId());
			test.modifier(aModifier);
			test.ajouter(aAjouter);
			liste=test.listerDest(aModifier.getIdDestination());
			test.supprimer(aModifier.getId());
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
