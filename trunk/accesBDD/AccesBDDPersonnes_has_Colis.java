package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//----- Classe permettant l'accès à la table Personnes_has_Colis, liant un colis à un expéditeur et un destinataire -----//

public class AccesBDDPersonnes_has_Colis extends ConnecteurSQL{
	/*public final static boolean DESTINATAIRE=false;
	public final static boolean EXPEDITEUR=true;*/
	
	//----- Fonction permettant l'ajout d'une ligne dans la table Personnes_has_Colis -----//
	public void ajouter(Integer idColis, Integer idExpediteur, Integer idDestinataire) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		//----- Insertion de la relation entre un destinataire et un colis dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO Personnes_has_Colis"
				+ " (Personnes_idPersonnes,Colis_idColis,Expediteur)" // Parametre de la table
				+ " VALUES (?,?,?)"); 
		
		ajout.setInt(1,idExpediteur.intValue());
		ajout.setInt(2,idColis.intValue());
		ajout.setInt(3,idDestinataire.intValue());
				
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
	}
	
	//----- Récupération du destinataire du colis -----//
	public Integer getDestinataire(Integer idColis) throws SQLException{
		Integer trouvee = new Integer(0);
		ConnecteurSQL connecteur=new ConnecteurSQL();
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
			"SELECT Personnes_idPersonnes FROM Personnes_has_Colis WHERE Colis_idColis=?");
		
		recherche.setInt(1, idColis.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next())	trouvee=new Integer(resultat.getInt("Personnes_idPersonnes"));
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return trouvee;
	}
	
	//----- Récupération de l'expéditeur du colis -----//
	public Integer getExpediteur(Integer idColis) throws SQLException{
		ConnecteurSQL connecteur=new ConnecteurSQL();
		Integer trouvee = new Integer(0);
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
			"SELECT Expediteur FROM Personnes_has_Colis WHERE Colis_idColis=? ");
		
		recherche.setInt(1,idColis.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next())	trouvee=new Integer(resultat.getInt("Expediteur"));
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		
		return trouvee;
	}
	
	/*public static void main(String arg[]){
		AccesBDDPersonnes_has_Colis test=new AccesBDDPersonnes_has_Colis();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		//Timestamp date=new Timestamp(10);
		//Colis aAjouter = new Colis(-1,00,01,02,2,date,45,6);
		try{
			test.ajouter(new Integer(1),new Integer(1),new Integer(1));
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}*/
}
