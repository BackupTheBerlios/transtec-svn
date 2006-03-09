package ihm;

import ihm.entree.Entree_Fenetre_colis;
import ihm.preparation.Prep_Fenetre_princ;
import ihm.supervision.Sup_Interface;

import java.awt.event.*; 

import javax.swing.*;

import accesBDD.AccesBDDUtilisateur;
import donnees.*;

//Cette classe correspond � la fenetre login qui permet aux utilisateurs d'acc�der
//au programme en fonction de leurs droits

public class Fenetre_login extends JFrame implements ActionListener{

	public Fenetre_login()
	{
		// Cr�ation graphique de la fenetre
		setTitle("Ouverture de session");
		setSize(1024,768);
		setUndecorated(true);
		
		// Cr�ation du Panel principal
		contenu = new PanelContenu("images/login/bg.png");
		contenu.setOpaque(false);
		contenu.setLayout(null);
		setContentPane(contenu);
		
		// Label du champs de login
		label_login = new JLabel("Login : ");
		label_login.setBounds(410,390,50,15);
		contenu.add(label_login);
		
		// Label du champ de mot de passe
		label_pwd = new JLabel("Password : ");
		label_pwd.setBounds(410,430,70,15);
		contenu.add(label_pwd);
		
		// Champ de login
		login = new JTextField(15);
		login.setBounds(480,387,150,20);
		
		// Un clic sur le bouton de validation est simul� lorsque 
		//	l'utilisateur presse la touche entr�e
		login.addKeyListener( new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER) valider.doClick();
			}
		});
		contenu.add(login);

		// Champ de mot de passe
		pwd1 = new JPasswordField(15);
		pwd1.setBounds(480,427,150,20);
		
		// Un clic sur le bouton de validation est simul� lorsque 
		//	l'utilisateur presse la touche entr�e
		pwd1.addKeyListener( new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER) valider.doClick();
			}
		});
		contenu.add(pwd1);
		
		// Bouton de validation
		valider = new Bouton("images/login/valider.png","images/login/valideronclick.png");
		valider.setBounds(520,465,48,51);
		contenu.add(valider);
		valider.addActionListener(this);		
		
		// Bouton permettant de quitter l'application
		quitter = new Bouton("images/icones/deconnexion.png","images/icones/deconnexion_inv.png");
		quitter.setBounds(10,735,98,17);
		contenu.add(quitter);
		quitter.addActionListener(this);
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
				// Si aucun utilisateur n'est trouv�
				else{
					JOptionPane.showMessageDialog(this,"Le couple login/mot de\npasse est incorrect.","Erreur",JOptionPane.ERROR_MESSAGE);					
				}		
			}
			catch(Exception ex){				
				System.out.println(ex.getMessage());
			}				
		}
		// Si l'utilisateur veut quitter l'application
		else if(source==quitter){
			System.exit(0);			
		}
	}
	
	private Utilisateur u;
	private AccesBDDUtilisateur bdd;
	private PanelContenu contenu;
	private JLabel label_login,label_pwd;
	private JTextField login;
	private JPasswordField pwd1;
	private JButton valider,quitter;
	
	public static void main(String[] args) {
		JFrame fen1 = new Fenetre_login();
		fen1.setVisible(true);	
	}	
	
	
	
	
}
