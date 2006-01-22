package accesBDD;

import java.sql.*;
import java.util.Vector;
import donnees.Colis;

//----- Classe permettant l'accès à la table Colis, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDColis  implements AccesBDD {
	//----- Ajouter un colis dans la BDD -----//
	public long ajouter(Colis aAjouter, ConnecteurSQL connecteur) throws SQLException{
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
				+ " (idColis,Poids,DateDepot,Valeur,Fragilite,Lieu,ModelesColis_idModelesColis,Entrepots_idEntrepots)" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId());
		ajout.setFloat(2,aAjouter.getPoids());
		ajout.setTimestamp(3,aAjouter.getDate());
		ajout.setFloat(4,aAjouter.getValeurDeclaree());
		ajout.setInt(5,aAjouter.getFragilite());
		ajout.setString(6,aAjouter.getLieu());
		ajout.setInt(7,aAjouter.getIdModele());
		ajout.setInt(8,aAjouter.getIdDestination());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		
		/*----- Ajout de la relation entre l'expéditeur/destinataire et le colis dans la 
				table Personnes_has_Colis -----*/
		AccesBDDPersonnes_has_Colis rel=new AccesBDDPersonnes_has_Colis();
		rel.ajouter(aAjouter.getId(), aAjouter.getIdExpediteur(), aAjouter.getIdDestinataire(), connecteur);
		
		return aAjouter.getId();
	}
	
	//----- Rechercher un colis dans la BDD -----//
	public int rechercher(int idColis, ConnecteurSQL connecteur, int typeRecherche){
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
	public void supprimer(Colis aSupprimer, ConnecteurSQL connecteur) throws SQLException{
		PreparedStatement supprime=
			connecteur.getConnexion().prepareStatement(
				"DELETE FROM colis WHERE idColis=?");
		supprime.setInt(1, aSupprimer.getId());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		supprime.close();	// Fermeture requête SQL
	}
	
	//----- Modifier les informations d'un colis -----//
	public void modifier(Colis aModifier, ConnecteurSQL connecteur) throws SQLException{
		//----- Modification de la localisation à partir de l'id -----//
		PreparedStatement modifie=
			connecteur.getConnexion().prepareStatement(
				"UPDATE colis SET "
				+"Poids=?, DateDepot=?, Valeur=?, Fragilite=?, Lieu=?, ModelesColis_idModelesColis=?, Entrepots_idEntrepots=? "
				+"WHERE idColis=?");
		modifie.setFloat(1, aModifier.getPoids());
		modifie.setTimestamp(2, aModifier.getDate());
		modifie.setFloat(3, aModifier.getValeurDeclaree());
		modifie.setInt(4, aModifier.getFragilite());
		modifie.setString(5, aModifier.getLieu());
		modifie.setInt(6, aModifier.getId());
		modifie.setInt(7, aModifier.getIdModele());
		modifie.setInt(8, aModifier.getIdDestination());

		modifie.executeUpdate();	// Exécution de la requête SQL
		
		//Recherche dans personne has_colis, mais est-ce nécéssaire
						
		modifie.close();	// Fermeture requête SQL
	}
	
	//----- Changer le "lieu" du colis -----//
	public boolean changerLieu(long nouveauLieu, Colis aModifier){
		return true;
	}
	
	//----- Lister les colis par destination -----//
	public Vector listerDest(int idEntrepot, ConnecteurSQL connecteur) throws SQLException{
		Vector liste=new Vector();
		AccesBDDPersonnes_has_Colis idPers=new AccesBDDPersonnes_has_Colis();
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
				"SELECT * FROM colis WHERE Entrepots_idEntrepots=? ");
		recherche.setInt(1, idEntrepot);
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			Colis courant=new Colis(idPers.getExpediteur(resultat.getInt("idColis"), connecteur),
					idPers.getDestinataire(resultat.getInt("idColis"), connecteur),resultat.getInt("Users_idUsers"), 
					resultat.getFloat("Poids"),resultat.getTimestamp("DateDepot"), resultat.getInt("Fragilite"), 
					resultat.getInt("Valeur"), resultat.getInt("ModelesColis_idModelesColis"), 
					resultat.getInt("Entrepots_idEntrepots"), resultat.getString("Lieu"));
			courant.setId(resultat.getInt("idColis"));
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
			test.ajouter(aAjouter,connecteur);
			aModifier.setId(aAjouter.getId());
			test.modifier(aModifier,connecteur);
			test.ajouter(aAjouter, connecteur);
			liste=test.listerDest(aModifier.getIdDestination(), connecteur);
			test.supprimer(aModifier, connecteur);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
