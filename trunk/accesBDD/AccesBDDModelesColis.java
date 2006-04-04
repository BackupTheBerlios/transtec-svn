package accesBDD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import donnees.ModeleColis;

public class AccesBDDModelesColis{
	private AccesBDD accesbdd;
	public AccesBDDModelesColis(AccesBDD accesbdd){
		this.accesbdd=accesbdd;
	}
	
	//----- Ajouter une nouvelle forme (cube) -----//
	public int ajouter(ModeleColis aAjouter)throws SQLException{
		//----- Recherche de l'identifiant le plus grand -----//
		PreparedStatement rechercheMaxID=accesbdd.getConnexion().prepareStatement("SELECT MAX(idModelesColis ) FROM modelescolis");
		ResultSet resultat = rechercheMaxID.executeQuery();	// Exécution de la requête SQL
		resultat.next();	// Renvoie le plus grand ID
		
		
		aAjouter.setId(new Integer(resultat.getInt(1)+1)); // Incrementation du dernier ID et mettre dans l'objet
		resultat.close();	// Fermeture requête SQL
		rechercheMaxID.close();	// Fermeture requête SQL
		
		//----- Insertion d'une personne dans la BDD -----//
		PreparedStatement ajout =accesbdd.getConnexion().prepareStatement(
				"INSERT INTO modelescolis"
				+ " (idModelesColis,Forme,Modele,hauteur,largeur,Profondeur)" // Parametre de la table
				+ " VALUES (?,?,?,?,?,?)"); 
		
		ajout.setInt(1,aAjouter.getId().intValue());
		ajout.setInt(2,aAjouter.getForme().intValue());
		ajout.setInt(3,aAjouter.getModele().intValue());
		ajout.setFloat(4,aAjouter.getHauteur().floatValue());
		ajout.setFloat(5,aAjouter.getLargeur().floatValue());
		ajout.setFloat(6,aAjouter.getProfondeur().floatValue());
		
		ajout.executeUpdate();//execution de la requete SQL
		ajout.close();//fermeture requete SQL
		//deconnecter();
		return aAjouter.getId().intValue();
	}
	
	public void modifier(ModeleColis aModifier) throws SQLException{
		//----- Modification de la localisation à partir de l'id -----//
		PreparedStatement modifie=accesbdd.getConnexion().prepareStatement(
				"UPDATE modelescolis SET "
				+"Forme =?,Modele =?,hauteur =?,largeur =?,Profondeur =? "
				+"WHERE idModelesColis =?");
		
		
		modifie.setInt(1,aModifier.getForme().intValue());
		modifie.setInt(2,aModifier.getModele().intValue());
		modifie.setFloat(3,aModifier.getHauteur().floatValue());
		modifie.setFloat(4,aModifier.getLargeur().floatValue());
		modifie.setFloat(5,aModifier.getProfondeur().floatValue());
		modifie.setInt(6,aModifier.getId().intValue());

		modifie.executeUpdate();	// Exécution de la requête SQL
		
		//Recherche dans personne has_colis, mais est-ce nécéssaire
						
		modifie.close();	// Fermeture requête SQL
		//deconnecter();
	}
	
	public ModeleColis rechercher(Integer aChercher) throws SQLException{
		ModeleColis trouvee=null;
		//AccesBDDPersonne bddPersonne=new AccesBDDPersonne();
		
		PreparedStatement recherche=accesbdd.getConnexion().prepareStatement("SELECT * FROM modelescolis WHERE idModelesColis=?");
		recherche.setInt(1, aChercher.intValue());
		
		ResultSet resultat = recherche.executeQuery();	// Exécution de la requête SQL
		
		if(resultat.next()){	// S'il a trouvé le colis
			//public ModeleColis(Integer id,Integer forme,Integer modele,Integer hauteur,Integer largeur,Integer profondeur,Integer diametre,Integer volume){
			trouvee=new ModeleColis(
					new Integer(resultat.getInt("idModelesColis")),
					new Integer(resultat.getInt("Forme")),
					new Integer(resultat.getInt("Modele")),
					new Float(resultat.getFloat("hauteur")),
					new Float(resultat.getFloat("largeur")),
					new Float(resultat.getFloat("Profondeur")));
		}
		
		resultat.close();	// Fermeture requête SQL
		recherche.close();	// Fermeture requête SQL
		//deconnecter();
		
		return trouvee;
	}	
}
