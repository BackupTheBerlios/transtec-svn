package ihm.entree;

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

	public Fenetre_create_incident(Colis col,Utilisateur user,Incident inc,Entree_Fenetre_colis fenetre,boolean inc_auto)
	{
		colis = col;
		utilis = user;
		incident = inc;
		//création graphique
		DateFormat dfs = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
		fenetre1 = fenetre;
		
		setTitle("Création d'un incident");
		setBounds(292,200,370,360);
		
		contenu = getContentPane();
		contenu.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		numero_colis = new JLabel("Numéro du colis :");
		numero_colis.setBounds(50,10,120,10);
		contenu.add(numero_colis);
		
		code_barre = new JTextField(15);
		code_barre.setBounds(175,7,130,20);
		code_barre.setText(col.getCode_barre());
		code_barre.setEnabled(false);
		contenu.add(code_barre);
		
		label_utilisateur = new JLabel("Utilisateur :");
		label_utilisateur.setBounds(50,40,120,10);
		contenu.add(label_utilisateur);
		
		utilisateur = new JTextField(15);
		utilisateur.setBounds(175,37,130,20);
		utilisateur.setText(utilis.getPersonne().getNom());
		utilisateur.setEnabled(false);
		contenu.add(utilisateur);
		
		label_date = new JLabel("Date de l'incident :");
		label_date.setBounds(50,70,120,10);
		contenu.add(label_date);
		
		
		date = new JTextField(15);
		date.setBounds(175,67,130,20);
		date.setText(new Timestamp(System.currentTimeMillis()).toLocaleString());
		//date.setText(dfs.format(new Date()));
		date.setEnabled(false);
		contenu.add(date);
		
		
		description_incident = new JLabel("Description de l'incident :");
		description_incident.setBounds(105,100,180,15);
		contenu.add(description_incident);
		
		donnees_description = new JTextArea();
		donnees_description.setColumns(25);
		donnees_description.setRows(6);
		donnees_description.setBounds(50,120,250,100);
		donnees_description.setLineWrap(true);
		
		donnees_description.setWrapStyleWord(true);
		contenu.add(donnees_description);
		
		valider_incident = new JButton("Envoyer en zone d'expertise");
		valider_incident.setBounds(75,230,200,25);
		contenu.add(valider_incident);
		valider_incident.addActionListener(this);
		
		envoie_stockage = new JButton("Envoyer en zone de stockage");
		envoie_stockage.setBounds(75,260,200,25);
		contenu.add(envoie_stockage);
		envoie_stockage.addActionListener(this);
		
		annuler_incident = new JButton("Annuler");
		annuler_incident.setBounds(125,290,100,25);
		contenu.add(annuler_incident);
		annuler_incident.addActionListener(this);
		
		// Si on veut voir les infos sur l'incident
		if ( incident != null){
			
			setTitle("Informations sur l'incident");
			envoie_stockage.setVisible(false);
			donnees_description.setEnabled(false);
			annuler_incident.setText("Ok");
			valider_incident.setVisible(false);
			annuler_incident.setBounds(125,240,100,25);
			date.setText(incident.getDate().toLocaleString());
			donnees_description.setText(incident.getDescription());
			setBounds(292,200,370,310);
			AccesBDDUtilisateur test=new AccesBDDUtilisateur();
			try{
				utilis = test.rechercher(incident.getUtilisateur().getId());
				utilisateur.setText(utilis.getPersonne().getNom());
				
			}
			catch(SQLException e2){
				System.out.println(e2.getMessage());
			}
			
		}
		//Lors de la création d'un incident automatique
		if (inc_auto == true){
			envoie_stockage.setVisible(false);
			valider_incident.setText("Créer l'incident");
			setBounds(292,200,370,335);
			annuler_incident.setBounds(125,265,100,25);
			
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
			Incident aAjouter =new Incident(new Integer(0),colis,new Timestamp(System.currentTimeMillis()),new Integer(Incident.NON_TRAITE),donnees_description.getText(),utilis,new Integer(Incident.ENTREE));
		
				try {
					test.ajouter(aAjouter);

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (fenetre1.charg!=null){
			AccesBDDChargement test10=new AccesBDDChargement();
			try {
				test10.supprimer_colis(colis,fenetre1.charg);
			} catch (SQLException e1) {
					// TODO Bloc catch auto-généré
				e1.printStackTrace();
			}
				}
			dispose();
			//fenetre1.ajout_chargement();
			fenetre1.informations_colis1();
				
		}
		if (source == envoie_stockage)
		{
			//enregistrement dans la BDD
			
			AccesBDDIncident test=new AccesBDDIncident();
			Incident aAjouter =new Incident(new Integer(0),colis,new Timestamp(System.currentTimeMillis()),new Integer(Incident.NON_TRAITE),donnees_description.getText(),utilis,new Integer(Incident.ENTREE));
		
				try {
					test.ajouter(aAjouter);

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if (fenetre1.charg!=null){
			AccesBDDChargement test10=new AccesBDDChargement();
			try {
				test10.supprimer_colis(colis,fenetre1.charg);
			} catch (SQLException e1) {
					// TODO Bloc catch auto-généré
				e1.printStackTrace();
			}
			}
			dispose();
			//fenetre1.ajout_chargement();
			fenetre1.informations_colis1();
				
		}
	
	}
	
	private Container contenu;
	private JLabel label_date,numero_colis,label_utilisateur,description_incident;
	private JButton envoie_stockage,valider_incident,annuler_incident;
	private JTextField date,code_barre,utilisateur;
	private JTextArea donnees_description;
	private Entree_Fenetre_colis fenetre1;
	private Colis colis;
	private Utilisateur utilis;
	private Incident incident;
}
