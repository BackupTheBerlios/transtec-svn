package ihm.preparation;

import java.sql.SQLException;
import java.util.Vector;

import accesBDD.AccesBDD;
import accesBDD.AccesBDDPreparation;
import donnees.Preparation;
import donnees.Utilisateur;

/*
 * Classe permettant de regrouper toute les informations nécessaire à l'affichage de la fenêtre principale de préparation
 */

public class ListeDonneesPrep {
	private Vector liste=new Vector();
	private String listeDest[];
	
	// On créer une liste de destinations à préparer en fonction de l'utilisateur
	public ListeDonneesPrep(Utilisateur utilisateur, AccesBDD accesBDD) throws SQLException{
		Vector listePrep=new AccesBDDPreparation(accesBDD).listerDestAPreparer(utilisateur);
		Preparation tmp_prep;
		DonneesPrep courante;
		int exists;
		
		for(int i=0;i<listePrep.size();i++){
			tmp_prep=(Preparation)listePrep.get(i);
			exists=destExists(tmp_prep.getDestination().getLocalisation().getVille());
			if(i==0 || exists==-1){
				courante=new DonneesPrep(tmp_prep.getDestination(),accesBDD);
				courante.ajouterCamion(tmp_prep);
				liste.add(courante);
			}
			else
				((DonneesPrep)liste.get(exists)).ajouterCamion(tmp_prep);
		}
		
		listeDest=new String[liste.size()];
		for(int i=0;i<liste.size();i++)
			listeDest[i]=((DonneesPrep)liste.get(i)).getDestination().toString();
	}
	
	// Méthode permettant de vérifier si une destination existe
	private int destExists(String destination){
		int retour=-1;

		for(int i=0;i<liste.size();i++){
			if(((DonneesPrep)liste.get(i)).getDestination().getLocalisation().getVille().equals(destination)==true){
				retour=i;
				i=liste.size();
			}	
		}
		return retour;
	}
	
	// Méthode permettant de retourner l'objet de type DonneesPrep s'il existe pour la desiantion données
	public DonneesPrep exists(String destination){
		DonneesPrep trouvee=null;
		for(int i=0;i<liste.size();i++){
			if(((DonneesPrep)liste.get(i)).getDestination().toString().equals(destination)==true){
				trouvee=(DonneesPrep)liste.get(i);
				i=liste.size();
			}	
		}
		return trouvee;
	}
	
	// Méthode permettant de retourner la liste de destination spour la comboBox
	public String[] combo(){
		return this.listeDest;
	}
	
	// Méthode permettant de retourner la liste de DonneesPrep
	public Vector getListe(){
		return this.liste;
	}
}
