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
	// Ne pas oublier l'état A RAJOUTER
	public Vector listerDestAPreparer(Integer idPreparateur) throws SQLException{
		Vector liste=new Vector();
		AccesBDDEntrepot bddEntrepot=new AccesBDDEntrepot();
		Entrepot entrepot=null;
				
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM Preparation WHERE idPreparateur=?");
		recherche.setInt(1, idPreparateur.intValue());
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		while(resultat.next()){
			Vector elem=new Vector();
			elem.add(new Integer(resultat.getInt("idPreparation")));
			entrepot=bddEntrepot.rechercher(resultat.getInt("idDestination"));
			elem.add(entrepot.getLocalisation().getVille());
			if(resultat.getInt("Etat")==A_FAIRE)	elem.add("A faire");
			else	elem.add("En cours");
			elem.add(new Integer(resultat.getInt("Volume")));
			liste.add(elem);
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		deconnecter();
		return liste;
	}
	
	public Preparation rechercher(Integer idPreparation) throws SQLException{
		AccesBDDCamion bddCamion=new AccesBDDCamion();
		Vector listeCamions=new Vector();
		
		PreparedStatement recherche=connecter().prepareStatement("SELECT * FROM Preparation WHERE idPreparation=?");
		recherche.setInt(1, idPreparation.intValue());
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		resultat.next();
		Preparation preparation=new Preparation(
				null, 
				new AccesBDDEntrepot().rechercher(new Integer(resultat.getInt("idDestination"))), 
				new Integer(resultat.getInt("Volume")),
				null);
		
		recherche=connecter().prepareStatement("SELECT * FROM Prep_camions WHERE idPreparation=?");
		recherche.setInt(1, idPreparation.intValue());
		resultat=recherche.executeQuery();
		
		while(resultat.next())	listeCamions.add(bddCamion.rechercher(new Integer(resultat.getInt("idCamion"))));
		
		preparation.setListeCamion(listeCamions);
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		deconnecter();
		
		return preparation;		
	}
	
}
