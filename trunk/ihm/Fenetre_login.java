package ihm;

import ihm.entree.Entree_Fenetre_colis;
import ihm.preparation.Prep_Fenetre_princ;
import ihm.supervision.Sup_Interface;

import java.awt.*;
import java.awt.event.*; 

import javax.swing.*;

import accesBDD.AccesBDDUtilisateur;
import donnees.*;

//Cette classe correspond à la fenetre login qui permet aux utilisateurs d'accéder
//au programme en fonction de leurs droits

public class Fenetre_login extends JFrame implements ActionListener{

	public Fenetre_login()
	{
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		};
		addWindowListener(l);
		
		// Création graphique de la fenetre
		setTitle("Ouverture de session");
		setBounds(350,300,300,150);
		
		contenu = getContentPane();
		contenu.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		label_login = new JLabel("Login : ");
		label_login.setBounds(20,20,50,15);
		contenu.add(label_login);
		
		label_pwd = new JLabel("Password : ");
		label_pwd.setBounds(20,45,70,15);
		contenu.add(label_pwd);
		
		login = new JTextField(15);
		login.setBounds(90,17,150,20);
		contenu.add(login);

		pwd1 = new JPasswordField(15);
		pwd1.setBounds(90,42,150,20);
		contenu.add(pwd1);
		
		valider = new JButton("Valider");
		valider.setBounds(90,70,100,25);
		contenu.add(valider);
		valider.addActionListener(this);			
	}

	// Gestion des actions liées aux boutons
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		// Bouton valider
		if (source == valider)
		{
			JFrame fen;
			Utilisateur u = null;
			AccesBDDUtilisateur bdd=new AccesBDDUtilisateur();
			
			try {
				// On récupère l'utilisateur associé au couple login/mot de passe
				u = bdd.isRegistered(login.getText(), new String(pwd1.getPassword()));
				
				// Si un utilisateur correspond aux informations saisies
				if(u!=null){
					
					// on fait disparaitre la fenêtre de login
					dispose();
					
					// On ouvre l'interface du poste correspondant à l'utilisateur
					switch(u.getType().intValue())
					{
					// Poste d'entrée
					case Utilisateur.ENTREE :
						fen = new Entree_Fenetre_colis(u);
						fen.setVisible(true);
						break;
						
					// Poste de préparation
					case Utilisateur.PREPARATION :
						fen = new Prep_Fenetre_princ(u);
						fen.setVisible(true);
						break;
						
					// Poste de supervision
					case Utilisateur.SUPERVISION :
						fen = new Sup_Interface(u);
						fen.setVisible(true);
						break;
					}		
				}
				// Si aucun utilisateur n'est trouvé
				else{
					JOptionPane.showMessageDialog(this,"Le couple login/mot de passe est incorrect.","Message d'avertissement",JOptionPane.ERROR_MESSAGE);					
				}		
			}
			catch(Exception m){				
				System.out.println(m.getMessage());
			}				
		}
	}
	
	private Container contenu;
	private JLabel label_login,label_pwd;
	private JTextField login;
	private JPasswordField pwd1;
	private JButton valider;
	
	public static void main(String[] args) {
		
		Personne pers = null;
		Utilisateur u= new Utilisateur(new Integer(0),"julien","julien",new Integer (0),pers);
		//public Utilisateur(Integer id, String login, String motDePasse, Integer type, Personne personne){
		
		JFrame fen = new Entree_Fenetre_colis(u);
		fen.setVisible(true);
	
		//JFrame fen1 = new Fenetre_login();
		//fen1.setVisible(true);	
	}	
}
