package accesBDD;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import com.mysql.jdbc.Blob;

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
	public void ajouter(Integer idChargement, InputStream input[]) throws SQLException, IOException{
		PreparedStatement ajouter=connecter().prepareStatement(
				"INSERT INTO plan (idChargement,dessus,dessous,face,arriere,gauche,droite) "
				//+"VALUES (?,empty_blob(),empty_blob(),empty_blob(),empty_blob(),empty_blob(),empty_blob())");
				+"VALUES (?,'','','','','','') ");
		ajouter.setInt(1,idChargement.intValue());
		
		
		
		ajouter.executeUpdate();	// Execution de la requête SQL
		ajouter.close();	// Fermeture requête SQL
		deconnecter();
		
		ajouter=connecter().prepareStatement("SELECT * FROM plan WHERE idChargement=? ");
		ajouter.setInt(1, idChargement.intValue());
		
		ResultSet resultat=ajouter.executeQuery();
		if(resultat.next()){
			//----Peut etre soucy
			byte[] buffer;
			OutputStream out[] = null;
			Blob blob[]=new Blob[6];
			blob[FACE]=resultat.getBlob("face");
			blob[ARRIERE]=resultat.getBlob("arriere");
			blob[GAUCHE]=resultat.getBlob("gauche");
			blob[DROITE]=resultat.getBlob("droite");
			blob[DESSUS]=resultat.getBlob("dessus");
			blob[DESSOUS]=resultat.getBlob("dessous");
			
			int i;
			for(i=0;i<6;i++){
				byte b[]=new byte[1];
				b[0]=0;
				blob[i].setBytes(1, b);
				//blob[i].truncate(0);
				out[i]=blob[i].setBinaryStream(1);
				buffer = new byte[32768];
				int n = 0;
				while ( ( n = input[i].read(buffer) ) != -1)	out[i].write(buffer, 0, n);
				out[i].flush();
			}
			ajouter.close();	// Fermeture requête SQL
			deconnecter();
			
			ajouter=connecter().prepareStatement("UPDATE plan SET dessus=?,dessous=?,face=?,arriere=?,gauche=?,droite=? "
					+"WHERE idChargement=? ");
			ajouter.setBlob(1, blob[DESSUS]);
			ajouter.setBlob(2, blob[DESSOUS]);
			ajouter.setBlob(3, blob[FACE]);
			ajouter.setBlob(4, blob[ARRIERE]);
			ajouter.setBlob(5, blob[GAUCHE]);
			ajouter.setBlob(6, blob[DROITE]);
			ajouter.setInt(7, idChargement.intValue());
		}
		ajouter.executeUpdate();
		ajouter.close();	// Fermeture requête SQL
		deconnecter();
	}
}
