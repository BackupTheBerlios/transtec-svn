package ihm;

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;

//Cette classe correspond à la fenetre de création de l'étiquette d'un colis.

public class Entree_Create_etiquette extends JFrame implements ActionListener
{
	public Entree_Create_etiquette(String code_barre)
	{
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				dispose();
			}
	
		};
		
		//création graphique de l'incident
		
		contenu = getContentPane();
		contenu.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		addWindowListener(l);
		setTitle("Création de l'étiquette du colis " + code_barre);
		setBounds(240,180,500,400);
		
		
		String a = code_barre;	
		int j=0;
		for (int i=0; i<a.length() ;i++){
			AffichageImage image1 = new AffichageImage("images/code_barre/"+a.charAt(i)+".JPG");
			image1.setBounds(40+(j*7),30,7,49);
			j++;
			contenu.add(image1);  
		}
		
		contenu.validate();
	
		imprimer = new JButton("Imprimer");
		imprimer.setBounds(130,320,100,25);
		contenu.add(imprimer);
		imprimer.addActionListener(this);
		
		fermer = new JButton("Fermer");
		fermer.setBounds(270,320,100,25);
		contenu.add(fermer);
		fermer.addActionListener(this);
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if ( source == fermer){
			dispose();
		}
		//permet d'imprimer l'étiquette
		if ( source == imprimer){
			JOptionPane.showMessageDialog(this,"Impression en cours, Veuillez patienter...","Impression",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	
	private JButton fermer,imprimer;
	private Container contenu;
	
	
	
}
