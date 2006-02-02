package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.sql.SQLException;
import donnees.Entrepot;

public class AccesBDDPreparation extends AccesBDD{
	public final static int A_FAIRE=0;
	public final static int EN_COURS=1;
	
	public AccesBDDPreparation(){
		super();
	}
	// Ne pas oublier l'�tat A RAJOUTER
	public Vector listerDestAPreparer(Integer idPreparateur) throws SQLException{
		Vector liste=new Vector();
		AccesBDDEntrepot bddEntrepot=new AccesBDDEntrepot();
		Entrepot entrepot=null;
				
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM Preparation WHERE idPreparateur=?");
		recherche.setInt(1, idPreparateur.intValue());
		ResultSet resultat = recherche.executeQuery();	// Ex�cution de la requ�te SQL
		
		while(resultat.next()){
			Vector elem=new Vector();
			entrepot=bddEntrepot.rechercher(resultat.getInt("idDestination"));
			elem.add(entrepot.getId());
			elem.add(entrepot.getLocalisation().getVille());
			if(resultat.getInt("Etat")==A_FAIRE)	elem.add("A faire");
			else	elem.add("En cours");
			elem.add(new Integer(resultat.getInt("Volume")));
			liste.add(elem);
		}
		
		resultat.close();	// Fermeture requ�te SQL
		recherche.close();	// Fermeture requ�te SQL
		deconnecter();
		return liste;
	}

}
