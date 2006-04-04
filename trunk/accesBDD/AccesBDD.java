package accesBDD;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.
import java.sql.SQLException;
import java.io.*;
//import org.gjt.mm.mysql.Driver;

//----- Classe mère de tous les accès à la BDD -----//

public class AccesBDD {
	private String	driver;
	private String	chaineconnexion;
	private	String 	login;
	private String 	password;
	private Connection connexion;
	
	//----- Constructeur mettant les caractéristique de la BDD -----//
	public AccesBDD(){
		driver="org.gjt.mm.mysql.Driver";
        //chaineconnexion="jdbc:mysql://localhost/transtec";
		chaineconnexion="jdbc:mysql://192.168.0.3:3306/transtec";

        login="root";
        password=null;	
        
        
        try {
			connecter();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*try{
            FileReader fr = new FileReader("D:\\EFREI\\JAVA_Workspace\\infoBDD.ini");
            BufferedReader br = new BufferedReader(fr);
             
            String contenu="";
            String s=br.readLine();
            while(s!=null)
            {
                contenu+=s;  
                s=br.readLine();
            }
            String[] aDecomposer=contenu.split("'");
            this.driver=aDecomposer[1];
     		this.chaineconnexion=aDecomposer[3];
     		this.login=aDecomposer[7];
     		this.password=aDecomposer[5];
            br.close();
		}
		catch(IOException ioe){System.out.println("erreur : " + ioe);}*/
	}

	// Se connecte à la BDD
	public Connection connecter() throws SQLException{
		try{
			if (connexion == null || this.connexion.isClosed()){
				Class.forName(driver); // chargement de la classe driver
				if (login != null){
					//this.connexion = DriverManager.getConnection("jdbc:mysql://localhost/transtec?user="+login+"&password="+password); 
					connexion=DriverManager.getConnection(chaineconnexion,login,password);
				}
					
				else{	// ouverture de la connexion
					connexion = DriverManager.getConnection(this.chaineconnexion);
				}
			}
			return connexion;
		}
		catch(Exception ex)
		{
			throw new SQLException(ex.getMessage());
		}
	}
	
	//	Ferme la connexion si elle est ouverte
	public void deconnecter() throws SQLException{
		if (connexion != null && !connexion.isClosed())
			connexion.close();
	}
	
	// Change les informations de la BDD
	
	public void changerInfoBDD(String driver, String chaineConnexion, String login, String password){
		try{
        	FileWriter fw = new FileWriter("D:\\EFREI\\JAVA_Workspace\\infoBDD.ini", false);
        	BufferedWriter output = new BufferedWriter(fw);
        	output.write("driver='"+driver+"'\n"+
        		"chaineconnexion='"+chaineConnexion+"'\n"+
        		"password='"+password+"'\n"+
        		"login='"+login+"'\n");
            output.flush();
            output.close();
		}
		catch(IOException ioe){System.out.println("erreur : " + ioe );}
	}
	
	public Connection getConnexion(){
		return connexion;
	}
}
