package ihm;

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;


//Cette classe correspond à la fenetre login qui permet aux utilisateurs d'accéder
//au programme en fonction de leurs droits

public class Fenetre_login extends JFrame implements ActionListener{

	public Fenetre_login()
	{
		//création graphique de la fenetre
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
	

	
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		String log="julien";
		String pass="julien";
		if (source == valider)
		{
			//le programme accède à la BDD pour vérifier le login et password
			//il renvoit aussi les droits de l'utilisateur
			
			if (log.equals(login.getText()) && pass.equals(pwd1.toString()))
			{
				//si le password est correct, on ferme la fenetre login et on accède à
				//l'un des 3 postes en fonction des droits.
				dispose();
				
				JFrame fen = new Entree_Fenetre_colis(log);
				fen.setVisible(true);
			}
			else
			{
				//Si le password est incorrecte, message d'erreur.
				JOptionPane.showMessageDialog(this,"Login ou password erroné. Veuillez contacter votre administrateur système","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
				
			}
		}
	}
	
	private Container contenu;
	private JLabel label_login,label_pwd;
	private JTextField login;
	private JPasswordField pwd1;
	private JButton valider;
	
	public static void main(String[] args) {
		JFrame fen = new Entree_Fenetre_colis("julien");
		fen.setVisible(true);
	
		//JFrame fen1 = new Fenetre_login();
		//fen1.setVisible(true);
	
	}
	
}
