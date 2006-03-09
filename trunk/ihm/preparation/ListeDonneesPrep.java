package ihm.preparation;

import java.sql.SQLException;
import java.util.Vector;

import accesBDD.AccesBDDPreparation;

import donnees.Camion;
import donnees.Preparation;
import donnees.Utilisateur;

public class ListeDonneesPrep {
	private Vector liste=new Vector();
	private String listeDest[];
	
	public ListeDonneesPrep(Utilisateur utilisateur) throws SQLException{
		Vector listePrep=new AccesBDDPreparation().listerDestAPreparer(utilisateur);
		Preparation tmp_prep;
		DonneesPrep courante;
		int exists;
		
		for(int i=0;i<listePrep.size();i++){
			tmp_prep=(Preparation)listePrep.get(i);
			exists=destExists(tmp_prep.getDestination().getLocalisation().getVille());
			if(i==0 || exists==-1){
				courante=new DonneesPrep(tmp_prep.getDestination());
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
	
	public DonneesPrep exists(String destination){
		DonneesPrep trouvee=null;
		for(int i=0;i<liste.size();i++){
			if(((DonneesPrep)liste.get(i)).getDestination().getLocalisation().getVille().equals(destination)==true){
				trouvee=(DonneesPrep)liste.get(i);
				i=liste.size();
			}	
		}
		return trouvee;
	}
	
	public String[] combo(){
		return this.listeDest;
	}
	
	public Vector getListe(){
		return this.liste;
	}
}
