package ihm;

import java.awt.*;
import java.awt.event.*; 
import java.sql.SQLException;

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
		//cr�ation graphique de la fenetre
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
		//String log="julien";
		//String pass="julien";
		if (source == valider)
		{
			
			JFrame fen;
			
			// rechercher la personne avec la m�thode de Nico.
			Utilisateur u= new Utilisateur("rochef","pass",new Integer(1),"Roche","Fran�ois","67 rue Jean Jaur�s","94800","Villejuif","roche@efrei.fr","0871732639");
			
			
			switch(u.getType().intValue())
			{
		
			
			case 0 : 	JOptionPane.showMessageDialog(this,"Login ou password erron�. Veuillez contacter votre administrateur syst�me","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
						login.setText("");
						pwd1.setText("");
						break;
			case Utilisateur.ENTREE : 	dispose();
						fen = new Entree_Fenetre_colis(u);
						fen.setVisible(true);
						break;
			case Utilisateur.PREPARATIOIN : 	dispose();
						fen = new Prep_Fenetre_princ(u);
						fen.setVisible(true);
						break;
			case Utilisateur.SUPERVISION : 	dispose();
						fen = new Sup_Interface(u);
						fen.setVisible(true);
						break;
			}			
		}
	}
	
	private Container contenu;
	private JLabel label_login,label_pwd;
	private JTextField login;
	private JPasswordField pwd1;
	private JButton valider;
	
	public static void main(String[] args) {
		//JFrame fen = new Entree_Fenetre_colis("julien");
		//fen.setVisible(true);
	
		JFrame fen1 = new Fenetre_login();
		fen1.setVisible(true);
	
	}
	
}
