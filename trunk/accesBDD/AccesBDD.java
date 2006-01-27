package accesBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;

//----- Classe mère de tous les accès à la BDD -----//

public class AccesBDD {
	private String	driver;
	private String	chaineconnexion;
	private	String 	login;
	private String 	password;
	private Connection connexion;
	
	//----- Constructeur mettant les caractéristique de la BDD -----//
	public AccesBDD(){
		this.driver="com.mysql.jdbc.Driver";
		this.chaineconnexion="jdbc:mysql:///transtec";
		this.login="root";
		this.password="";
		
		/*try{
             FileReader fr = new FileReader("\\infoBDD.ini");
             BufferedReader br = new BufferedReader(fr);
             
             String texte = "";
             int a = 0;
                 while(a<2)
                 {
                     texte = texte + br.readLine() + "\n";
                     a++;        
                 }
             br.close();
             //readLine pour lire une ligne
             //note: si il n y a rien, la fonction retournera la valeur null
             System.out.println(texte);
             //on affiche le texte
		}
		catch(IOException ioe){System.out.println("erreur : " + ioe);}*/
	}

	// Se connecte à la BDD
	public Connection connecter() throws SQLException{
		try{
			if (this.connexion == null || this.connexion.isClosed()){
				Class.forName(driver); // chargement de la classe driver
				if (login != null)			
						this.connexion = DriverManager.getConnection("jdbc:mysql://localhost/transtec?user=root&password="); 
					
				else	// ouverture de la connexion
					this.connexion = DriverManager.getConnection(this.chaineconnexion);
			}
			return this.connexion;
		}
		catch(ClassNotFoundException ex)
		{
			throw new SQLException("Connecteur SQL : Classe introuvable " + ex.getMessage());
		}
	}
	
	//	Ferme la connexion si elle est ouverte
	public void deconnecter() throws SQLException{
		if (this.connexion != null && !this.connexion.isClosed())
			this.connexion.close();
	}
}
