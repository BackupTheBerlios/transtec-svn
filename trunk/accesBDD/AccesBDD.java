package accesBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
