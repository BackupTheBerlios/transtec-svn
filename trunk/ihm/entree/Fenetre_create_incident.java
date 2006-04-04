package ihm.entree;

import ihm.Bouton;
import ihm.FenetreType;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import accesBDD.AccesBDD;
import accesBDD.AccesBDDChargement;
import accesBDD.AccesBDDIncident;
import accesBDD.AccesBDDUtilisateur;
import donnees.Colis;
import donnees.Incident;
import donnees.Utilisateur;


//Cette classe correspond à la fenetre création d'un incident
//elle va permettre au poste d'entrée et de préparation de créer un
//incident pour un colis.

public class Fenetre_create_incident extends JFrame implements ActionListener{
	private AccesBDD accesBDD;
	public Fenetre_create_incident(Colis col,Utilisateur user,Incident inc,Entree_Fenetre_colis fenetre,boolean inc_auto, AccesBDD accesBDD)
	{
		this.accesBDD=accesBDD;
		colis = col;
		utilis = user;
		incident = inc;
		//création graphique
		//DateFormat dfs = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
		fenetre1 = fenetre;
		setBounds(250,200,530,320);// on définit la taille de la fenetre
		setTitle("Création d'un incident"); 
		contenu=new FenetreType("images/entree/bg_packentrée3.png");
		
		setContentPane(contenu);
		contenu.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		font=new Font("Verdana", Font.BOLD, 12);
		
		numero_colis = new JLabel("Numéro du colis :");
		numero_colis.setBounds(20,50,120,10);
		numero_colis.setFont(font);
		contenu.add(numero_colis);
		
		code_barre = new JTextField(15);
		code_barre.setBounds(150,47,155,20);
		code_barre.setText(col.getCode_barre());
		code_barre.setEnabled(false);
		code_barre.setFont(font);
		contenu.add(code_barre);
		
		label_utilisateur = new JLabel("Utilisateur :");
		label_utilisateur.setBounds(20,80,120,10);
		label_utilisateur.setFont(font);
		contenu.add(label_utilisateur);
		
		utilisateur = new JTextField(15);
		utilisateur.setBounds(150,77,155,20);
		utilisateur.setText(utilis.getPersonne().getNom());
		utilisateur.setEnabled(false);
		utilisateur.setFont(font);
		contenu.add(utilisateur);
		
		label_date = new JLabel("Date de l'incident :");
		label_date.setBounds(20,110,130,10);
		label_date.setFont(font);
		contenu.add(label_date);
		
		
		date = new JTextField(15);
		date.setBounds(150,107,155,20);
		date.setText(new Timestamp(System.currentTimeMillis()).toLocaleString());
		//date.setText(dfs.format(new Date()));
		date.setEnabled(false);
		date.setFont(font);
		contenu.add(date);
		
		
		description_incident = new JLabel("Description de l'incident :");
		description_incident.setBounds(80,140,180,15);
		description_incident.setFont(font);
		contenu.add(description_incident);
		
		donnees_description = new JTextArea();
		donnees_description.setColumns(25);
		donnees_description.setRows(6);
		donnees_description.setBounds(20,160,285,100);
		donnees_description.setLineWrap(true);
		donnees_description.setFont(font);
		donnees_description.setWrapStyleWord(true);
		contenu.add(donnees_description);
		
		valider_incident = new Bouton("images/entree/bouton_zone_expertise.png","images/entree/bouton_zone_expertise_appuyer.png");
		valider_incident.setBounds(325,50,185,45);
		contenu.add(valider_incident);
		valider_incident.addActionListener(this);
		
		envoie_stockage = new Bouton("images/entree/bouton_zone_stockage.png","images/entree/bouton_zone_stockage_appuyer.png");
		envoie_stockage.setBounds(325,110,185,40);
		contenu.add(envoie_stockage);
		envoie_stockage.addActionListener(this);
		
		annuler_incident = new Bouton("images/entree/bouton_annuler.png","images/entree/bouton_annuler_appuyer.png");
		annuler_incident.setBounds(300,170,185,40);
		contenu.add(annuler_incident);
		annuler_incident.addActionListener(this);
		
		// Si on veut voir les infos sur l'incident
		if ( incident != null){
			
			setTitle("Informations sur l'incident");
			envoie_stockage.setVisible(false);
			donnees_description.setEnabled(false);
			//annuler_incident.setText("Ok");
			valider_incident.setVisible(false);
			annuler_incident.setBounds(325,50,185,45);
			date.setText(incident.getDate().toLocaleString());
			donnees_description.setText(incident.getDescription());
			//setBounds(292,200,370,310);
			AccesBDDUtilisateur test=new AccesBDDUtilisateur(this.accesBDD);
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
			//valider_incident.setText("Créer l'incident");
			//setBounds(292,200,370,335);
			annuler_incident.setBounds(300,110,185,40);
			
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
			
			AccesBDDIncident test=new AccesBDDIncident(this.accesBDD);
			Incident aAjouter =new Incident(new Integer(0),colis,new Timestamp(System.currentTimeMillis()),new Integer(Incident.NON_TRAITE),donnees_description.getText(),utilis,new Integer(Incident.ENTREE), new Integer(0));
		
				try {
					test.ajouter(aAjouter);

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (fenetre1.getChargement()!=null){
			AccesBDDChargement test10=new AccesBDDChargement(this.accesBDD);
			try {
				test10.supprimer_colis(colis,fenetre1.getChargement());
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
			
			AccesBDDIncident test=new AccesBDDIncident(this.accesBDD);
			Incident aAjouter =new Incident(new Integer(0),colis,new Timestamp(System.currentTimeMillis()),new Integer(Incident.NON_TRAITE),donnees_description.getText(),utilis,new Integer(Incident.ENTREE),new Integer(0));
		
				try {
					test.ajouter(aAjouter);

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if (fenetre1.getChargement()!=null){
			AccesBDDChargement test10=new AccesBDDChargement(this.accesBDD);
			try {
				test10.supprimer_colis(colis,fenetre1.getChargement());
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
	
	private FenetreType contenu;
	private JLabel label_date,numero_colis,label_utilisateur,description_incident;
	private JButton envoie_stockage,valider_incident,annuler_incident;
	private JTextField date,code_barre,utilisateur;
	private JTextArea donnees_description;
	private Entree_Fenetre_colis fenetre1;
	private Colis colis;
	private Utilisateur utilis;
	private Incident incident;
	private Font font;
}
