package ihm;

import ihm.entree.Entree_Fenetre_colis;
import ihm.preparation.Prep_Choix_Dest;
import ihm.supervision.Sup_Interface;

import java.awt.*;
import java.awt.event.*; 

import javax.swing.*;

import accesBDD.AccesBDDUtilisateur;
import donnees.*;

//Cette classe correspond � la fenetre login qui permet aux utilisateurs d'acc�der
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
		
		// Cr�ation graphique de la fenetre
		setTitle("Ouverture de session");
		setSize(1024,768);
		contenu = new panelContenu();
		setContentPane(contenu);
		setUndecorated(true);
		//setBounds(350,300,300,150);
		contenu.setOpaque(false);
		
		contenu.setLayout(null);
		
		label_login = new JLabel("Login : ");
		label_login.setBounds(410,390,50,15);
		contenu.add(label_login);
		
		label_pwd = new JLabel("Password : ");
		label_pwd.setBounds(410,430,70,15);
		contenu.add(label_pwd);
		
		login = new JTextField(15);
		login.setBounds(480,387,150,20);
		contenu.add(login);

		pwd1 = new JPasswordField(15);
		pwd1.setBounds(480,427,150,20);
		contenu.add(pwd1);
		
		valider = new JButton(new ImageIcon("images/login/valider.png"));
		valider.setBounds(520,465,48,51);
		valider.setBorder(null);
		valider.setOpaque(false);
		valider.setContentAreaFilled(false);
		valider.setMargin(null);
		valider.setRolloverEnabled(false);
		valider.setPressedIcon(new ImageIcon("images/login/valideronclick.png"));
		contenu.add(valider);
		valider.addActionListener(this);			
	}

	// Gestion des actions li�es aux boutons
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		// Bouton valider
		if (source == valider)
		{
			JFrame fen;
			u = null;
			bdd=new AccesBDDUtilisateur();
			
			try {
				// On r�cup�re l'utilisateur associ� au couple login/mot de passe
				u = bdd.isRegistered(login.getText(), new String(pwd1.getPassword()));
				
				// Si un utilisateur correspond aux informations saisies
				if(u!=null){
					
					// on fait disparaitre la fen�tre de login
					dispose();
					
					// On ouvre l'interface du poste correspondant � l'utilisateur
					switch(u.getType().intValue())
					{
					// Poste d'entr�e
					case Utilisateur.ENTREE :
						fen = new Entree_Fenetre_colis(u);
						fen.setVisible(true);
						break;
						
					// Poste de pr�paration
					case Utilisateur.PREPARATION :
						fen = new Prep_Choix_Dest(u);
						fen.setVisible(true);
						break;
						
					// Poste de supervision
					case Utilisateur.SUPERVISION :
						fen = new Sup_Interface(u);
						fen.setVisible(true);
						break;
					}		
				}
				// Si aucun utilisateur n'est trouv�
				else{
					JOptionPane.showMessageDialog(this,"Le couple login/mot de passe est incorrect.","Message d'avertissement",JOptionPane.ERROR_MESSAGE);					
				}		
			}
			catch(Exception m){				
				System.out.println(m.getMessage());
			}				
		}
	}
	
	private Utilisateur u;
	private AccesBDDUtilisateur bdd;
	private panelContenu contenu;
	private JLabel label_login,label_pwd;
	private JTextField login;
	private JPasswordField pwd1;
	private JButton valider;
	
	public static void main(String[] args) {
		
		//Personne pers = null;
		//Utilisateur u= new Utilisateur(new Integer(0), "julien","julien",new Integer (2), "Catala", "Julien", "dfshgdskjfsd", "94800", "Villejuif", "jgeazvhgzea", "0669696969");
		//public Utilisateur(Integer id, String login, String motDePasse, Integer type, Personne personne){
		
		//JFrame fen = new Prep_Choix_Dest(u);
		//fen.setVisible(true);
	
		JFrame fen1 = new Fenetre_login();
		fen1.setVisible(true);	
	}	
	
	// Classe d�riv�e de JPanel correspondant au panel principal de la fen�tre de login 
	class panelContenu extends JPanel{		
		public panelContenu(){
			super();
		}
		
		// Permet de d�finir une image de fond
		public void paintComponent(Graphics g){
				ImageIcon img = new ImageIcon("images/login/bg.png");
				g.drawImage(img.getImage(), 0, 0, null);
				super.paintComponent(g);
		}	
	}
}
