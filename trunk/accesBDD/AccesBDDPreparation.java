package accesBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.sql.SQLException;
import donnees.Entrepot;
import donnees.Preparation;

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
			elem.add(new Integer(resultat.getInt("idPreparation")));
			entrepot=bddEntrepot.rechercher(new Integer(resultat.getInt("idDestination")));
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
	
	public void ajouter(Preparation aAjouter) throws SQLException{
		PreparedStatement ajout =connecter().prepareStatement(
				"INSERT INTO preparation "
				+ " (idPreparateur,idDestination,idCamion,Origine,Etat,Volume)" // Param�tre de la table
				+ " VALUES (?,?,?,?,?,?)"); 
		
		ajout.setInt(1, aAjouter.getUtilisateur().getId().intValue());
		ajout.setInt(2, aAjouter.getDestination().getId().intValue());
		ajout.setInt(3, aAjouter.getCamion().getId().intValue());
		ajout.setInt(4, aAjouter.getOrigine().getId().intValue());
		ajout.setInt(5, aAjouter.getEtat().intValue());
		ajout.setInt(6, aAjouter.getVolumeColis().intValue());
		
		ajout.executeUpdate();	// Execution de la requ�te SQL
		ajout.close();	// Fermeture requ�te SQL
		deconnecter();
	}
}
