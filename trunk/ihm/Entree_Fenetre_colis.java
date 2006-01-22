package ihm;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.*;import java.awt.event.*; 
import javax.swing.*;

import java.util.*;
import java.text.DateFormat;
import java.util.Vector;

import accesBDD.AccesBDDColis;
import accesBDD.ConnecteurSQL;
import donnees.*;

//Cette classe correspond à la fenetre de saisi ou de vérification d'un colis.

public class Entree_Fenetre_colis extends JFrame implements ActionListener, ItemListener{

	public Entree_Fenetre_colis(String name)
	{
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		};
		addWindowListener(l);
		
		setTitle(name + " - Préparateur");
		setBounds(72,24,900,720);
		login = name;
		//création de la barre de menus
		barreMenus = new JMenuBar();
		setJMenuBar(barreMenus);
		//création du menu fichier;
		fichier = new JMenu("Fichier");
		//ajout du menu fichier dans la barre de menus
		barreMenus.add(fichier);
		//création du sous menu se déloguer
		se_deloguer = new JMenuItem("Se déloguer");
		//ajout du sous menu dans le menu fichier
		fichier.add(se_deloguer);
		// Ajouter l'écouteur de se déloguer
		se_deloguer.addActionListener(this);
		//idem pour les autres menus
		
		etiquette = new JMenu("Etiquette");
		barreMenus.add(etiquette);
		creation = new JMenuItem("Création");
		etiquette.add(creation);
		creation.addActionListener(this);
		
		
		create_graphique(); //appel la fonction de la création graphique
	
		
		informations_colis();	
	}
	
	
	//fonction permettant de créer l'interface graphique de la fenetre
	public void create_graphique(){
		
		contenu = getContentPane();
		contenu.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		numero_colis = new JLabel("Numéro :");
		numero_colis.setBounds(pos_x,10,120,10);
		contenu.add(numero_colis);


		code_barre = new JTextField(15);
		code_barre.setBounds(pos_x + 65,7,200,20);
		contenu.add(code_barre);
		
		label_forme_colis = new JLabel("Forme : ");
		label_forme_colis.setBounds(pos_x,40,120,10);
		contenu.add(label_forme_colis);
		
		forme_colis = new JComboBox(formes);
		forme_colis.setEditable(false);
		forme_colis.setBounds(pos_x + 65,37,200,20);
		contenu.add(forme_colis);
		forme_colis.addActionListener(this);
		
		label_modele_colis = new JLabel("Modèle :");
		label_modele_colis.setBounds(pos_x ,70,120,10);
		contenu.add(label_modele_colis);
		
		label_hauteur_colis = new JLabel("Hauteur (cm) :");
		label_hauteur_colis.setBounds(pos_x+300, 70,120,15);
		contenu.add(label_hauteur_colis);
		label_hauteur_colis.setVisible(false);
		
		label_largeur_colis = new JLabel("Largeur (cm) :");
		label_largeur_colis.setBounds(pos_x+300,100,120,15);
		contenu.add(label_largeur_colis);
		label_largeur_colis.setVisible(false);
		
		label_profondeur_colis = new JLabel("Profondeur (cm) :");
		label_profondeur_colis.setBounds(pos_x+300,130,120,15);
		contenu.add(label_profondeur_colis);
		label_profondeur_colis.setVisible(false);
		
		hauteur = new JTextField(15);
		hauteur.setBounds(pos_x + 405,67,50,20);
		contenu.add(hauteur);
		hauteur.setVisible(false);
		
		largeur = new JTextField(15);
		largeur.setBounds(pos_x + 405,97,50,20);
		contenu.add(largeur);
		largeur.setVisible(false);
		
		profondeur = new JTextField(15);
		profondeur.setBounds(pos_x + 405,127,50,20);
		contenu.add(profondeur);
		profondeur.setVisible(false);
		
		modele_colis = new JComboBox(modele);
		modele_colis.setBounds(pos_x + 65,67,200,20);
		contenu.add(modele_colis);
		
		modele_colis.addItemListener(this);
		
		label_fragile = new JLabel("Fragilité :");
		label_fragile.setBounds(pos_x,100,150,15);
		contenu.add(label_fragile);
		
		fragilite_colis = new JComboBox(fragilite);
		fragilite_colis.setBounds(pos_x + 65,97,200,20);
		contenu.add(fragilite_colis);
		
		label_poids = new JLabel("Poids (kg) : ");
		label_poids.setBounds(pos_x,130,150,15);
		contenu.add(label_poids);
		
		poids = new JTextField(5);
		poids.setBounds(pos_x + 70,127,30,20);
		contenu.add(poids);
		
		label_date = new JLabel("Date d'envoie :");
		label_date.setBounds(pos_x + 105,130,150,15);
		contenu.add(label_date);
		
		date_envoie = new JTextField(10);
		date_envoie.setBounds(pos_x + 195,127,70,20);
		contenu.add(date_envoie);
		
		
		label_dest = new JLabel("Destinataire :");
		label_dest.setBounds(pos_x,160,150,15);
		contenu.add(label_dest);
		
		donnees_dest = new JTextArea();
		donnees_dest.setColumns(25);
		donnees_dest.setRows(10);
		donnees_dest.setBounds(pos_x + 90,160,175,65);
		donnees_dest.setLineWrap(true);
		
		donnees_dest.setWrapStyleWord(true);
		contenu.add(donnees_dest);
		
		
		label_exp = new JLabel("Expéditeur :");
		label_exp.setBounds(pos_x,230,150,15);
		contenu.add(label_exp);
		
		donnees_exp = new JTextArea();
		donnees_exp.setColumns(25);
		donnees_exp.setRows(4);
		donnees_exp.setBounds(pos_x + 90,230,175,65);
		donnees_exp.setLineWrap(true);
		
		donnees_exp.setWrapStyleWord(true);
		contenu.add(donnees_exp);
		
		valider_colis = new JButton("Envoyer en zone de stockage");
		contenu.add(valider_colis);
		valider_colis.addActionListener(this);
		
		create_etiquette = new JButton("Etiquette");
		contenu.add(create_etiquette);
		create_etiquette.addActionListener(this);
		
		label_camion = new JLabel("Liste des colis présents dans le chargement :");
		label_camion.setBounds(pos_x,410,280,15);
		contenu.add(label_camion);
		
		create_incident = new JButton("Incident");
		create_incident.setBounds(pos_x + 30,340,100,25);
		contenu.add(create_incident);
		create_incident.addActionListener(this);
		valider_colis.setBounds(pos_x + 30,310,210,25);
		create_etiquette.setBounds(pos_x + 140,340,100,25);
		
		label_liste_incidents = new JLabel("Incidents répertoriés pour ce colis :");
		label_liste_incidents.setBounds(pos_x + 370,160,220,15);
		contenu.add(label_liste_incidents);
		
		annuler = new JButton("Annuler");
		annuler.setBounds(pos_x+85,370,100,25);
		contenu.add(annuler);
		annuler.addActionListener(this);
		
		nomColonnes = new Vector();
		nomColonnes.add("Numéro");
        nomColonnes.add("Forme");
        nomColonnes.add("Modèle");
        nomColonnes.add("Poids");
        nomColonnes.add("Fragilité");
       
        //donnees.addElement(new colis("0123456789123","1","1","1","1","19/05/2005","coucou","la").toVector());
		//donnees.addElement(new colis("9876543210321","1","1","1","1","19/05/2005","cici","la").toVector());
		
		// donnees.addElement(new colis("4135135351",new Integer(2),new Integer(3),"120","60","20",new Integer(1),"18","18/05/05","Lacombe Julien \n46 rue de villacoublay \n78140 Vélizy Villacoublay","Sengler Nicolas \n28 rue de la bas \n94... Villejuif").toVector());
		
		/*donnees.addElement(new colis("5478954785412","5478954785412","HS",new Integer(45),"Joffre","Bordeaux","Lyon").toVector());
		donnees.addElement(new colis("1258745968745","1258745968745","HS",new Integer(60),"Bergman","Lyon","Chartres").toVector());
		donnees.addElement(new colis("8745986325471","8745986325471","OK",new Integer(12),"Dufour","Nancy","Nancy").toVector());
        */
        donnees = new Vector();
		modeleTabColis = new ModeleTable(nomColonnes,donnees);
		
		// Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter = new TableSorter(modeleTabColis);
		
		// Création du tableau
		tabColis = new JTable(sorter);
		
		// initialisation du Sorter
		sorter.setTableHeader(tabColis.getTableHeader());
		
		// On crée les colonnes du tableau selon le modèle
		tabColis.createDefaultColumnsFromModel();
		tabColis.setOpaque(false);
		tabColis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// On place le tableau dans un ScrollPane pour qu'il soit défilable
		JScrollPane scrollPane = new JScrollPane(tabColis);

		// On définit les dimensions du tableau
		tabColis.setPreferredScrollableViewportSize(new Dimension(20,20));

		// On place le tableau
		scrollPane.setBounds(230,440,580,210);

		// On définit le tableau transparent
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		// On ajoute le tableau au Panneau principal
		contenu.add(scrollPane);
		
		//colis col = new colis("413513555",1,2,1,"18","18/05/05",new Integer(1),new Integer(2),new Integer(1),"120","60","20");
		//colis col1 =new colis();
		//colis col2 = col1.changement_colis(col);
		//ajouterColis(col2);
		
		/*String[] nomColonnes1 = {
				"Date",
				"Heure",
				"Description",
		};

		Object[][] data1 = {
				{"18/05/05", "12:30","Probleme"},
				{"24/02/05", "09:30","Probleme"},
				{"15/03/05", "08:30","Probleme"},
				{"06/08/05", "18:30","Probleme"},
				{"17/09/05", "15:30","Probleme"}
		};
		
		tabIncidents = new JTable(data1,nomColonnes1);
		tabIncidents.setOpaque(false);
		scrollPane1 = new JScrollPane(tabIncidents);
		tabIncidents.setPreferredScrollableViewportSize(new Dimension(20,20));
		scrollPane1.setBounds(550,185,300,150);
		scrollPane1.setOpaque(false);
		scrollPane1.getViewport().setOpaque(false);
		contenu.add(scrollPane1);*/
		
		nomColonnes1 = new Vector();
		nomColonnes1.add("id");
		nomColonnes1.add("idcolis");
		nomColonnes1.add("Date");
		nomColonnes1.add("truc");
        nomColonnes1.add("Description");
        nomColonnes1.add("machin");
        nomColonnes1.add("truc1");
        
        donnees1 = new Vector();
		modeleTabInc = new ModeleTable(nomColonnes1,donnees1);
		sorter = new TableSorter(modeleTabInc);
		tabInc = new JTable(sorter);
		sorter.setTableHeader(tabInc.getTableHeader());
		tabInc.createDefaultColumnsFromModel();
		tabInc.setOpaque(false);
		tabInc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tabInc.removeColumn(tabInc.getColumnModel().getColumn(0));
		tabInc.removeColumn(tabInc.getColumnModel().getColumn(0));
		
		tabInc.removeColumn(tabInc.getColumnModel().getColumn(1));
		tabInc.removeColumn(tabInc.getColumnModel().getColumn(2));
		tabInc.removeColumn(tabInc.getColumnModel().getColumn(2));
	
		
		scrollPane1 = new JScrollPane(tabInc);
		tabInc.setPreferredScrollableViewportSize(new Dimension(20,20));
		scrollPane1.setBounds(550,185,300,150);
		scrollPane1.setOpaque(false);
		scrollPane1.getViewport().setOpaque(false);
		
		contenu.add(scrollPane1);
		
		voir_incident = new JButton("Voir l'incident");
		voir_incident.setBounds(630,340,150,25);
		contenu.add(voir_incident);
		voir_incident.addActionListener(this);
		
		
		Incident inc = new Incident(new Integer(1),new Integer(46468468),new Timestamp(0105,05,05,03,03,03,03),new Integer(0),"problème sur le colis",new Integer(2),new Integer(0));
		ajouterInc(inc);
		//inc = new Incident("02/04/05","Abimé",new Integer(1513513153));
		//ajouterInc(inc);
		//affichage des 5 faces du colis
		
		label_cam = new JLabel(" Vues caméra :");
		label_cam.setBounds(65,10,160,10);
		contenu.add(label_cam);
		
		int j=0;
		for (int i =1; i< 6;i++){
			AffichageImage image = new AffichageImage("colis/face"+i+".JPG");
			image.setBounds(20,27+(j*128),163,120);
			j++;
			contenu.add(image);
		}
		
	}
	
	public void ajouterColis(Colis c){
		
		// OBTENIR UN NOUVEL ID APRES ACCES A LA BDD
		
		// Ajout de la ligne
		modeleTabColis.addRow(c.toVector());
		modeleTabColis.fireTableDataChanged();
		// On redessine le tableau
		tabColis.updateUI();
	}
	public void ajouterInc(Incident c){
		
		// OBTENIR UN NOUVEL ID APRES ACCES A LA BDD
		
		// Ajout de la ligne
		modeleTabInc.addRow(c.toVector());
		modeleTabInc.fireTableDataChanged();
		// On redessine le tableau
		tabInc.updateUI();
	}
	//Fonction permettant de simuler la douchette
	public String douchette(){
		String barre_code;
		barre_code = JOptionPane.showInputDialog(null,"Code barre:","Simulation de la douchette",JOptionPane.PLAIN_MESSAGE);
		return barre_code;
	}
	
	//fonction qui affiche les informations du colis 
	public void informations_colis(){
		
		String barre_code = douchette();
		contenu.removeAll();
		contenu.validate();
		create_graphique();	
		contenu.validate();
		repaint();
		int de;
		String id="";
		int error = 1;
		
		//Si on veut saisir un nouveau colis.
		if (barre_code==null || barre_code.length()==0)
		{
			
			//création du code barre ( il faut vérifier qu'il n'existe pas dans la BDD
			do{
				for (int i = 0; i< 9;i++)
				{
					Random r = new Random();
					de = r.nextInt(10);
					id = id + de;
				}
				//Si le code barre n'existe pas on sort du while
				
				
			}while(error==0);
		
	
			create = true;
			code_barre.setEnabled(false);
			code_barre.setText(id);
			
			
			DateFormat dfs = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
			//Timestamp dfs1;
		
			//String temp3= dfs1.toGMTString();
			
			//date_envoie.setText(temp3);
			date_envoie.setText(dfs.format(new Date()) );
			date_envoie.setEnabled(false);
			scrollPane1.setVisible(false);
			label_liste_incidents.setVisible(false);
			voir_incident.setVisible(false);
			create_incident.setVisible(false);
			
	
		}
		//Si on vérifie un colis
		else
		{
			
			create = false;
			DateFormat dfs = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
			//col = new Colis("413513555",3,2,3,"18","18/05/05",new Integer(1),new Integer(1),new Integer(3),"120","60","20");
			Timestamp date=new Timestamp(20);
			
			col = new Colis(new Integer(0),"69696969",new Integer(1),"18",date,"150",new Integer(1),"Villejuif");
			
			//récupérer les infos dans la Bdd et les afficher
			code_barre.setEnabled(false);
			code_barre.setText(col.getCode_barre());
			forme_colis.setEnabled(false);
			
			/*
			forme_colis.setSelectedIndex(col.getForme().intValue());
			modele_colis.setEnabled(false);
			modele_colis.setSelectedIndex(col.getModele().intValue());
			fragilite_colis.setEnabled(false);
			fragilite_colis.setSelectedIndex(col.getFragilite().intValue());
			*/
			largeur.setEnabled(false);
			hauteur.setEnabled(false);
			profondeur.setEnabled(false);
			largeur.setText(col.getLargeur());
			hauteur.setText(col.getHauteur());
			profondeur.setText(col.getProfondeur());
			
			poids.setEnabled(false);
			poids.setText(col.getPoids());
			
			date_envoie.setEnabled(true);
			date_envoie.setText(col.getDate().toString());
			
			donnees_dest.setEnabled(false);
			//donnees_dest.setText(col.getIdDestinataire());
			
			
			donnees_exp.setEnabled(false);
			//donnees_exp.setText(col.expéditeur);
			voir_incident.setVisible(true);
			
			
		}
		contenu.validate();
	}
	

	public void itemStateChanged (ItemEvent e)
	{
		int num = modele_colis.getSelectedIndex();
		if (num <3)
		{
			label_hauteur_colis.setVisible(false);
			label_largeur_colis.setVisible(false);
			label_profondeur_colis.setVisible(false);
			hauteur.setVisible(false);
			largeur.setVisible(false);
			profondeur.setVisible(false);
			
		}else{
			label_hauteur_colis.setVisible(true);
			label_largeur_colis.setVisible(true);
			label_profondeur_colis.setVisible(true);
			hauteur.setVisible(true);
			largeur.setVisible(true);
			profondeur.setVisible(true);
		}
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source ==se_deloguer) {
			
			dispose();
			JFrame fen1 = new Fenetre_login();
			fen1.setVisible(true);
		}
		if (source ==creation) {
			JFrame fen4 = new Entree_Create_etiquette("5456465");
			fen4.setVisible(true);
		}
		
		//if (source ==forme_colis) System.out.println("forme colis");
		if (source == valider_colis)
		{
			//Cas d'un nouveau colis
			if(create == true){
				
				/*
				enregistrer toutes ces infos dans la BDD
				code_barre.getText()
				forme_colis.getSelectedIndex()
				modele_colis.getSelectedIndex()
				fragilite_colis.getSelectedIndex()
				hauteur.getText()
				largeur.getText()
				profondeur.getText()
				poids.getText()
				date_envoie.getText()
				donnees_dest.getText()
				donnees_exp.getText()
				*/
				//enregistrer infos dans la Bdd
				
				AccesBDDColis test=new AccesBDDColis();
				ConnecteurSQL connecteur = new ConnecteurSQL();
				Timestamp date=new Timestamp(10);
				Colis aAjouter = new Colis(new Integer(0),code_barre.getText(),new Integer(1),poids.getText(),date,"150",new Integer(fragilite_colis.getSelectedIndex()),"Villejuif");
				try{
					test.ajouter(aAjouter,connecteur);
				}
				catch(SQLException e2){
					System.out.println(e2.getMessage());
				}
			}
			//cas d'un colis à vérifier
			else
			{
				//enregistrer heure de passage au poste d'entrée
				
			}
			informations_colis();
				
		}
		
		if (source == create_incident)
		{
			JFrame fen3 = new Fenetre_create_incident(col,login);
			fen3.setVisible(true);
			
		}
		if (source == create_etiquette)
		{
			JFrame fen4 = new Entree_Create_etiquette(code_barre.getText());
			fen4.setVisible(true);
		}
		
		if (source == annuler)
		{
			informations_colis();
		}
		if ( source == voir_incident)
		{
			int ligneActive;
			int ligneSelect = tabInc.getSelectedRow();
			if(ligneSelect != -1){
				ligneActive = sorter.modelIndex(ligneSelect);
				Vector cVect = (Vector) modeleTabInc.getRow(ligneActive);
				Incident c = new Incident(cVect);
				String temp = c.getId().toString();
				JFrame fen3 = new Fenetre_create_incident(col,login);
				fen3.setVisible(true);
				
			}
		}
		
		
		
	}
	private JMenuBar barreMenus;
	private JMenu fichier,etiquette;
	private JMenuItem se_deloguer,creation;
	private JLabel label_liste_incidents,label_profondeur_colis,label_hauteur_colis,label_largeur_colis,label_camion,label_cam,label_date,label_poids,label_dest,label_exp,numero_colis,label_forme_colis,label_modele_colis,label_fragile;
	private JTextField hauteur,profondeur,largeur,date_envoie,poids,code_barre;
	private String[] formes={"cube","pavé","cylindre"}, modele={"modèle1","modèle2","modèle3","personalisé"},fragilite={"trés fragile","fragile","pas fragile"};
	private JComboBox forme_colis,modele_colis,fragilite_colis;
	private JButton voir_incident,annuler,create_etiquette,create_incident,valider_colis;
	private JTextArea donnees_dest,donnees_exp;
	private Container contenu ;
	private boolean create;
	private String login;
	private static final int pos_x = 230;
	private Vector nomColonnes,nomColonnes1;
	private Vector donnees,donnees1;
	private JTable tabColis,tabInc;
	private ModeleTable modeleTabColis,modeleTabInc;
	private TableSorter sorter;
	private JScrollPane scrollPane1;
	
	private Colis col;
	
	
	
	
}
