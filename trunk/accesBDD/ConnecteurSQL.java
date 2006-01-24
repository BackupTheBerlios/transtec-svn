package accesBDD;

import java.sql.*;
import java.lang.Class;

/*
 * Connecteur permettant de gérer une connexion JDBC pour la base de notre logiciel
 * permettant de vérifier l'existance des tables 
 */

public class ConnecteurSQL {

	private String	driver			= "com.mysql.jdbc.Driver";
	private String	chaineconnexion = "jdbc:mysql:///transtec";
	private	String 	login			= "root";
	private String 	password		= "";
	private Connection connexion;
	
	//	 Ferme la connexion si elle est ouverte
	public void fermerConnexion() throws SQLException{
		if (this.connexion != null && !this.connexion.isClosed())
			this.connexion.close();
	}
	
	// Modifie la classe du driver et ferme la connexion si elle est ouverte
	// pour provoquer l'ouverture d'une nouvelle connexion au prochain appel
	// de la methode getConnexion
	public void setDriver (String driver) throws SQLException{
		this.driver = driver;
		fermerConnexion();
	}
	
	// Modifie la chaine de connexion
	public void setChaineConnexion(String chaineConnexion) throws SQLException{
		this.chaineconnexion = chaineConnexion;
		fermerConnexion();
	}
	
	// Modifie le login
	public void setLogin(String login) throws SQLException{
		this.login = login;
		fermerConnexion();
	}
	
	// Modifie le mot de passe
	public void setPassword(String password) throws SQLException{
		this.password = password;
		fermerConnexion();
	}

	// Renvoie une connexion a la base de donnees
	public Connection getConnexion() throws SQLException{
		try{
			if (this.connexion == null || this.connexion.isClosed()){
				Class.forName(driver); // chargement de la classe driver
				
				if (login != null)
					//this.connexion = DriverManager.getConnection(
						//	this.chaineconnexion, this.login, this.password); 
					//jdbc:mysql://localhost/mysql?user=root&password=
						
						this.connexion = DriverManager.getConnection("jdbc:mysql://localhost/transtec?user=root&password="); 
					// ouverture de la connexion
				else
					this.connexion = DriverManager.getConnection(this.chaineconnexion);
				 	// ouverture de la connexion
			}
			return this.connexion;
		}
		catch(ClassNotFoundException ex)
		{
			throw new SQLException("Connecteur SQL : Classe introuvable " + ex.getMessage());
		}
	}
	
	protected boolean verifierTable(Connection connexion, String table) throws SQLException{
		DatabaseMetaData info = connexion.getMetaData();
		ResultSet resultat = info.getTables(connexion.getCatalog(), null, table, null);
		boolean tableExiste = resultat.next();
		resultat.close();
		return tableExiste;
	}
}
