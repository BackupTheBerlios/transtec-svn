package accesBDD;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Classe permettant l'accès à la table contenant les prises de vues de la benne
 */

public class AccesBDDPlan extends AccesBDD{
	public final static int FACE=0;
	public final static int ARRIERE=1;
	public final static int GAUCHE=2;
	public final static int DROITE=3;
	public final static int DESSUS=4;
	public final static int DESSOUS=5;
	
	public AccesBDDPlan(){
		super();
	}
	
	// Méthode permettant de rechercher les images à partir de l'id du chargement
	public Blob[] rechercher(Integer idChargement)throws SQLException{
		Blob retour[]=new Blob[6];
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM plan WHERE idChargement=?");
		recherche.setInt(1, idChargement.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next()){	// S'il a trouvé la ligne
			retour[FACE]=resultat.getBlob("face");
			retour[ARRIERE]=resultat.getBlob("arriere");
			retour[GAUCHE]=resultat.getBlob("gauche");
			retour[DROITE]=resultat.getBlob("droite");
			retour[DESSUS]=resultat.getBlob("dessus");
			retour[DESSOUS]=resultat.getBlob("dessous");
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		deconnecter();
		
		// On retourne un tableau contenant les fichiers
		return retour;
	}
	
	// Méthode permettant d'ajouter les fichiers dansla BDD
	public void ajouter(Integer idChargement, Blob aAjouter[]) throws SQLException{
		PreparedStatement ajouter=connecter().prepareStatement(
				"INSERT INTO plan (idChargement,dessus,dessous,face,arriere,gauche,droite) "
				+"VALUES (?,?,?,?,?,?,?)");
		ajouter.setInt(1,idChargement.intValue());
		ajouter.setBlob(2,aAjouter[DESSUS]);
		ajouter.setBlob(3,aAjouter[DESSOUS]);
		ajouter.setBlob(4,aAjouter[FACE]);
		ajouter.setBlob(5,aAjouter[ARRIERE]);
		ajouter.setBlob(6,aAjouter[GAUCHE]);
		ajouter.setBlob(7,aAjouter[DROITE]);
		
		ajouter.executeUpdate();	// Execution de la requête SQL
		ajouter.close();	// Fermeture requête SQL
		deconnecter();
	}
}
