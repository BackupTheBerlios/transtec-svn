package accesBDD;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public Blob[] rechercher(Integer idChargement)throws SQLException{
		Blob retour[]=new Blob[6];
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM plan WHERE idChargement=?");
		recherche.setInt(1, idChargement.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		if(resultat.next()){	// S'il a trouv� la personne
			retour[FACE]=resultat.getBlob("face");
			retour[ARRIERE]=resultat.getBlob("arriere");
			retour[GAUCHE]=resultat.getBlob("gauche");
			retour[DROITE]=resultat.getBlob("droite");
			retour[DESSUS]=resultat.getBlob("dessus");
			retour[DESSOUS]=resultat.getBlob("dessous");
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		deconnecter();
		
		return retour;
	}
}
