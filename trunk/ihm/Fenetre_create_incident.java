package ihm;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//import java.awt.*;
//import java.awt.event.*; 
//import javax.swing.*;

import java.util.*;
import java.text.DateFormat;

import donnees.*;
import accesBDD.*;

import java.sql.SQLException;
import java.sql.Timestamp;


//Cette classe correspond à la fenetre création d'un incident
//elle va permettre au poste d'entrée et de préparation de créer un
//incident pour un colis.

public class Fenetre_create_incident extends JFrame implements ActionListener{

	public Fenetre_create_incident(Colis col,String name,boolean create,Entree_Fenetre_colis fenetre)
	{
		//création graphique
		DateFormat dfs = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
		fenetre1 = fenetre;
		
		setTitle("Création d'un incident");
		setBounds(292,200,360,340);
		
		contenu = getContentPane();
		contenu.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		/*numero_incident = new JLabel("Numéro de l'incident :");
		numero_incident.setBounds(50,10,150,10);
		contenu.add(numero_incident);
		int error = 1,de;
		String id ="";
		do{
			for (int i = 0; i< 10;i++)
			{
				Random r = new Random();
				de = r.nextInt(10);
				id = id + de;
			}
			//Si le code barre n'existe pas on sort du while
			
			
		}while(error==0);
		
		incident = new JTextField(15);
		incident.setBounds(175,7,110,20);
		if (inc.compareTo("")==0){
			incident.setText(id);
		}
		else incident.setText(inc);
		incident.setEnabled(false);
		contenu.add(incident);*/
		
		numero_colis = new JLabel("Numéro du colis :");
		numero_colis.setBounds(50,10,120,10);
		contenu.add(numero_colis);
		
		code_barre = new JTextField(15);
		code_barre.setBounds(175,7,110,20);
		code_barre.setText(col.getCode_barre());
		code_barre.setEnabled(false);
		contenu.add(code_barre);
		
		label_utilisateur = new JLabel("Utilisateur :");
		label_utilisateur.setBounds(50,40,120,10);
		contenu.add(label_utilisateur);
		
		utilisateur = new JTextField(15);
		utilisateur.setBounds(175,37,110,20);
		utilisateur.setText(name);
		utilisateur.setEnabled(false);
		contenu.add(utilisateur);
		
		label_date = new JLabel("Date de l'incident :");
		label_date.setBounds(50,70,120,10);
		contenu.add(label_date);
		
		
		date = new JTextField(15);
		date.setBounds(175,67,110,20);
		date.setText(dfs.format(new Date()));
		date.setEnabled(false);
		contenu.add(date);
		
		
		description_incident = new JLabel("Description de l'incident :");
		description_incident.setBounds(100,100,180,15);
		contenu.add(description_incident);
		
		donnees_description = new JTextArea();
		donnees_description.setColumns(25);
		donnees_description.setRows(6);
		donnees_description.setBounds(50,120,240,100);
		donnees_description.setLineWrap(true);
		
		donnees_description.setWrapStyleWord(true);
		contenu.add(donnees_description);
		
		valider_incident = new JButton("Envoyer en zone d'expertise");
		valider_incident.setBounds(70,240,200,25);
		contenu.add(valider_incident);
		valider_incident.addActionListener(this);
		
		annuler_incident = new JButton("Annuler");
		annuler_incident.setBounds(120,270,100,25);
		contenu.add(annuler_incident);
		annuler_incident.addActionListener(this);
		
		if ( create == false){
			donnees_description.setEnabled(false);
			annuler_incident.setText("Ok");
			valider_incident.setVisible(false);
			annuler_incident.setBounds(120,240,100,25);
			setBounds(292,200,360,310);
		}
		
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source == annuler_incident) //annulation de l'incident
		{
	
			dispose();
		}
		//on valide la création de l'incident
		if (source == valider_incident)
		{
			//enregistrement dans la BDD
			
			AccesBDDIncident test=new AccesBDDIncident();
			Incident aAjouter =new Incident(new Integer(0),new Integer(code_barre.getText()),new Timestamp(System.currentTimeMillis()),new Integer(0),donnees_description.getText(),new Integer(2),new Integer(Incident.EN_COURS));
			
				try {
					test.ajouter(aAjouter);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				System.out.println("ici");
			
			
			
			//System.out.println(inc);
				dispose();
				fenetre1.informations_colis();
				
		}
	
	}
	
	private Container contenu;
	private JLabel label_date,numero_incident,numero_colis,label_utilisateur,description_incident;
	private JButton valider_incident,annuler_incident;
	private JTextField date,incident,code_barre,utilisateur;
	private JTextArea donnees_description;
	private Entree_Fenetre_colis fenetre1;
}
