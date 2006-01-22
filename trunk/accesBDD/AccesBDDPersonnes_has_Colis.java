package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//----- Classe permettant l'acc�s � la table Personnes_has_Colis, liant un colis � un exp�diteur et un destinataire -----//

public class AccesBDDPersonnes_has_Colis {
	public final static boolean DESTINATAIRE=false;
	public final static boolean EXPEDITEUR=true;
	
	//----- Fonction permettant l'ajout d'une ligne dans la table Personnes_has_Colis -----//
	public void ajouter(int idColis, int idExpediteur, int idDestinataire, ConnecteurSQL connecteur) throws SQLException{
		//----- Insertion de la relation entre un destinataire et un colis dans la BDD -----//
		PreparedStatement ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO Personnes_has_Colis"
				+ " (Personnes_idPersonnes,Colis_idColis,Expediteur)" // Parametre de la table
				+ " VALUES (?,?,?)"); 
		
		ajout.setInt(1,idDestinataire);
		ajout.setInt(2,idColis);
		ajout.setBoolean(3,DESTINATAIRE);
				
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		
		//----- Insertion de la relation entre un expediteur et un colis dans la BDD -----//
		ajout =
			connecteur.getConnexion().prepareStatement(
				"INSERT INTO Personnes_has_Colis"
				+ " (Personnes_idPersonnes,Colis_idColis,Expediteur)" // Parametre de la table
				+ " VALUES (?,?,?)"); 
		
		ajout.setInt(1,idExpediteur);
		ajout.setInt(2,idColis);
		ajout.setBoolean(3,EXPEDITEUR);
				
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
	}
	
	//----- R�cup�ration du destinataire du colis -----//
	public int getDestinataire(int idColis, ConnecteurSQL connecteur) throws SQLException{
		int trouvee=0;
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
			"SELECT Personnes_idPersonnes FROM Personnes_has_Colis WHERE Colis_idColis=? AND Expediteur=?");
		
		recherche.setInt(1, idColis);
		recherche.setBoolean(2, DESTINATAIRE);
		
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		resultat.next();
		trouvee=resultat.getInt("Personnes_idPersonnes");
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		
		return trouvee;
	}
	
	//----- R�cup�ration de l'exp�diteur du colis -----//
	public int getExpediteur(int idColis, ConnecteurSQL connecteur) throws SQLException{
		int trouvee=0;
		
		PreparedStatement recherche=connecteur.getConnexion().prepareStatement(
			"SELECT Personnes_idPersonnes FROM Personnes_has_Colis WHERE Colis_idColis=? AND Expediteur=? ");
		
		recherche.setInt(1, idColis);
		recherche.setBoolean(2, EXPEDITEUR);
		
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		resultat.next();
		trouvee=resultat.getInt("Personnes_idPersonnes");
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		
		return trouvee;
	}
	
	public static void main(String arg[]){
		AccesBDDPersonnes_has_Colis test=new AccesBDDPersonnes_has_Colis();
		ConnecteurSQL connecteur = new ConnecteurSQL();
		//Timestamp date=new Timestamp(10);
		//Colis aAjouter = new Colis(-1,00,01,02,2,date,45,6);
		try{
			test.ajouter(1,1,1,connecteur);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
