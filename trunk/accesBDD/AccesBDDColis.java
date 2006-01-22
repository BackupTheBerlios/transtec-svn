package accesBDD;

import java.sql.*;

import donnees.Colis;

//----- Classe permettant l'accès à la table Colis, elle permet de faire les différentes opérations nécessaire sur la table -----//

public class AccesBDDColis  extends AccesBDD {
	//----- Ajouter un colis dans la BDD -----//
	public int ajouter(Colis aAjouter, ConnecteurSQL connecteur) throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=
			connecteur.getConnexion().prepareStatement(
				"SELECT MAX(idColis) FROM colis");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion du colis dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO colis"
				+ " (idColis,Code_barre,ModelesColis_idModelesColis,Entrepots_idEntrepots,Poids,DateDepot,Valeur,Fragilite,Lieu)" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setString(2,aAjouter.getCode_barre());
		ajout.setInt(3,aAjouter.getModele().intValue());
		ajout.setInt(4,aAjouter.getEntrepot().intValue());
		ajout.setString(5,aAjouter.getPoids());
		ajout.setTimestamp(6,aAjouter.getDate());
		ajout.setString(7,aAjouter.getValeurDeclaree());
		ajout.setInt(8,aAjouter.getFragilite().intValue());
		ajout.setString(9,aAjouter.getLieu());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		
		/*----- Ajout de la relation entre l'expéditeur/destinataire et le colis dans la 
				table Personnes_has_Colis -----*/
		//AccesBDDPersonnes_has_Colis rel=new AccesBDDPersonnes_has_Colis();
		//rel.ajouter(aAjouter.getId(), aAjouter.getIdExpediteur(), aAjouter.getIdDestinataire(), connecteur);
		
		return aAjouter.getId().intValue();
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
				"DELETE FROM colis WHERE idLColis=?");
		supprime.setInt(1, aSupprimer.getId().intValue());
				
		supprime.executeUpdate();	// Exécution de la requête SQL
						
		supprime.close();	// Fermeture requête SQL
	}
	
	//----- Modifier les informations d'un colis -----//
	public void modifier(Colis aModifier, ConnecteurSQL connecteur) throws SQLException{
		/*//----- Modification de la localisation à partir de l'id -----//
		PreparedStatement modifie=
			connecteur.getConnexion().prepareStatement(
				"UPDATE colis SET "
				+"Poids=?, DateDepot=?, Valeur=?, Fragilite=?, Lieu=?"
				+"WHERE idColis=?");
		modifie.setFloat(1, aModifier.getPoids());
		modifie.setTimestamp(2, aModifier.getDate());
		modifie.setFloat(3, aModifier.getValeurDeclaree());
		modifie.setInt(4, aModifier.getFragilite());
		modifie.setInt(5, aModifier.getLieu());
		modifie.setInt(6, aModifier.getId());
		
		
		modifie.setInt(3,aModifier.getModele().intValue());
		modifie.setString(4,aModifier.getPoids());
		modifie.Timestamp(5,aModifier.getDate());
		modifie.setString(6,aModifier.getValeurDeclaree());
		modifie.setInt(7,aModifier.getFragilite().intValue());
		modifie.setString(8,aModifier.getLieu());

		modifie.executeUpdate();	// Exécution de la requête SQL
		
		//Recherche dans personne has_colis, mais est-ce nécéssaire
						
		modifie.close();	// Fermeture requête SQL*/
	}
	
	//----- Changer le "lieu" du colis -----//
	public boolean changerLieu(long nouveauLieu, Colis aModifier){
		return true;
	}
	
	public static void main(String arg[]){
		AccesBDDColis test=new AccesBDDColis();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		Timestamp date=new Timestamp(10);
		Colis aAjouter = new Colis(new Integer(0),"1236987458",new Integer(1),new Integer(2),"18",date,"150",new Integer(1),"Villejuif");
		try{
			System.out.println("ici");
			test.ajouter(aAjouter,connecteur);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
