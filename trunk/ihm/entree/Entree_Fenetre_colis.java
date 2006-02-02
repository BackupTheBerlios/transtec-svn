package ihm.entree;

import ihm.Fenetre_login;
import ihm.ModeleTable;
import ihm.TableSorter;
import ihm.preparation.Prep_Fenetre_princ;
import ihm.supervision.Sup_Interface;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;

import accesBDD.AccesBDDColis;
import accesBDD.AccesBDDPersonne;
import donnees.*;
import accesBDD.AccesBDDModelesColis;
import accesBDD.AccesBDDColis;
import accesBDD.AccesBDDEntrepot;
import accesBDD.AccesBDDLocalisation;
import accesBDD.AccesBDDModelesColis;
import accesBDD.AccesBDDIncident;

//Cette classe correspond à la fenetre de saisi ou de vérification d'un colis.

public class Entree_Fenetre_colis extends JFrame implements ActionListener, ItemListener{

	public Entree_Fenetre_colis(Utilisateur u)
	{
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		};
		addWindowListener(l);
		utilisateur  = u;
		setTitle(u.getLogin() + " - Préparateur");
		setBounds(72,24,900,720);
		login = u.getLogin();
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
	
		
		informations_colis1();	
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
		
		label_cam = new JLabel(" Vues caméra :");
		label_cam.setBounds(65,10,160,10);
		contenu.add(label_cam);
		
		int j=0;
		for (int i =1; i< 6;i++){
			AffichageImage image = new AffichageImage("images/colis/face"+i+".JPG");
			image.setBounds(20,27+(j*128),163,120);
			j++;
			contenu.add(image);
		}
		
	}
	
	public void ajouterColis(Colis c){
		
		// OBTENIR UN NOUVEL ID APRES ACCES A LA BDD
		
		// Ajout de la ligne
		//modeleTabColis.addRow(c.toVector());
		//modeleTabColis.fireTableDataChanged();
		// On redessine le tableau
		//tabColis.updateUI();
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
	public String douchette()
		{
		String barre_code;
		barre_code = JOptionPane.showInputDialog(null,"Code barre:","Simulation de la douchette",JOptionPane.PLAIN_MESSAGE);
	
		return barre_code;
	}
	
	//fonction qui affiche les informations du colis 
	
	public void informations_colis1()
	{
		
		String barre_code,temp="";
		int Ok=0;
		do{	
			barre_code = douchette();
			temp = barre_code;
			AccesBDDColis rechercher_colis=new AccesBDDColis();
			
			if (barre_code==null || barre_code.length()==0)
			{
				col = new Colis();
				Ok = 1;
			
			}
			else{
					try{
						new Integer(temp.trim());
						int i = Integer.parseInt(temp);
						try {
							col = rechercher_colis.rechercherCode_barre(i);
							if(col!=null){
								Ok =1;
							}
							else{
								JOptionPane.showMessageDialog(this,"Le code barre ne correspond à aucun colis ou chargement.","Message d'avertissement",JOptionPane.ERROR_MESSAGE);					
							}
							
						} catch (SQLException e) {
					
							e.printStackTrace();
						}
					}
					catch(NumberFormatException e){
						//erreurVolume=true;
						JOptionPane.showMessageDialog(this,"Le code barre ne correspond à aucun colis ou chargement.","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
					}
					
			}
	
		}while(Ok == 0);
		
		contenu.removeAll();
		contenu.validate();
		create_graphique();	
		contenu.validate();
		repaint();
		int de;
		String id="";
		int error = 1;
		
		//Si on veut saisir un nouveau colis.
		if (col.getCode_barre()==null)
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
			date_envoie.setText(new Timestamp(System.currentTimeMillis()).toString() );
			date_envoie.setEnabled(false);
			scrollPane1.setVisible(false);
			label_liste_incidents.setVisible(false);
			voir_incident.setVisible(false);
			create_incident.setVisible(false);
			
			label_dest.setBounds(pos_x+120,160,150,15);
			label_exp.setBounds(pos_x+470,160,150,15);
			donnees_dest.setVisible(false);
			donnees_exp.setVisible(false);
			
			label_nom_dest = new JLabel("Nom :");
			label_nom_dest.setBounds(pos_x,190,50,15);
			contenu.add(label_nom_dest);
			
			nom_dest = new JTextField(15);
			nom_dest.setBounds(pos_x + 40,187,100,20);
			contenu.add(nom_dest);
			
			label_prenom_dest = new JLabel("Prénom :");
			label_prenom_dest.setBounds(pos_x+150,190,60,15);
			contenu.add(label_prenom_dest);
			
			prenom_dest = new JTextField(15);
			prenom_dest.setBounds(pos_x + 210,187,100,20);
			contenu.add(prenom_dest);
			
			label_adresse_dest= new JLabel("Adresse :");
			label_adresse_dest.setBounds(pos_x,220,60,15);
			contenu.add(label_adresse_dest);
			
			adresse_dest = new JTextField(15);
			adresse_dest.setBounds(pos_x + 60,217,250,20);
			contenu.add(adresse_dest);
			
			label_cp_dest= new JLabel("Code Postal :");
			label_cp_dest.setBounds(pos_x,250,90,15);
			contenu.add(label_cp_dest);
			
			cp_dest = new JTextField(15);
			cp_dest.setBounds(pos_x + 80,247,60,20);
			contenu.add(cp_dest);
			
			label_ville_dest= new JLabel(" Ville :");
			label_ville_dest.setBounds(pos_x+150,250,60,15);
			contenu.add(label_ville_dest);
			
			ville_dest = new JTextField(15);
			ville_dest.setBounds(pos_x + 190,247,120,20);
			contenu.add(ville_dest);
			
			label_email_dest= new JLabel("Email :");
			label_email_dest.setBounds(pos_x,280,60,15);
			contenu.add(label_email_dest);
			
			email_dest = new JTextField(15);
			email_dest.setBounds(pos_x + 40,277,270,20);
			contenu.add(email_dest);
			
			label_tel_dest= new JLabel("Telephone :");
			label_tel_dest.setBounds(pos_x,310,80,15);
			contenu.add(label_tel_dest);
			
			tel_dest = new JTextField(15);
			tel_dest.setBounds(pos_x + 70,307,100,20);
			contenu.add(tel_dest);
			
			//
			label_nom_exp = new JLabel("Nom :");
			label_nom_exp.setBounds(pos_x1 + pos_x,190,50,15);
			contenu.add(label_nom_exp);
			
			nom_exp = new JTextField(15);
			nom_exp.setBounds(pos_x1 + pos_x + 40,187,100,20);
			contenu.add(nom_exp);
			
			label_prenom_exp = new JLabel("Prénom :");
			label_prenom_exp.setBounds(pos_x1 + pos_x+150,190,60,15);
			contenu.add(label_prenom_exp);
			
			prenom_exp = new JTextField(15);
			prenom_exp.setBounds(pos_x1 + pos_x + 210,187,100,20);
			contenu.add(prenom_exp);
			
			label_adresse_exp= new JLabel("Adresse :");
			label_adresse_exp.setBounds(pos_x1 + pos_x,220,60,15);
			contenu.add(label_adresse_exp);
			
			adresse_exp = new JTextField(15);
			adresse_exp.setBounds(pos_x1 + pos_x + 60,217,250,20);
			contenu.add(adresse_exp);
			
			label_cp_exp= new JLabel("Code Postal :");
			label_cp_exp.setBounds(pos_x1 + pos_x,250,90,15);
			contenu.add(label_cp_exp);
			
			cp_exp = new JTextField(15);
			cp_exp.setBounds(pos_x1 + pos_x + 80,247,60,20);
			contenu.add(cp_exp);
			
			label_ville_exp= new JLabel(" Ville :");
			label_ville_exp.setBounds(pos_x1 + pos_x+150,250,60,15);
			contenu.add(label_ville_exp);
			
			ville_exp = new JTextField(15);
			ville_exp.setBounds(pos_x1 + pos_x + 190,247,120,20);
			contenu.add(ville_exp);
			
			label_email_exp= new JLabel("Email :");
			label_email_exp.setBounds(pos_x1 + pos_x,280,60,15);
			contenu.add(label_email_exp);
			
			email_exp = new JTextField(15);
			email_exp.setBounds(pos_x1 + pos_x + 40,277,270,20);
			contenu.add(email_exp);
			
			label_tel_exp= new JLabel("Telephone :");
			label_tel_exp.setBounds(pos_x1 + pos_x,310,80,15);
			contenu.add(label_tel_exp);
			
			tel_exp= new JTextField(15);
			tel_exp.setBounds(pos_x1 + pos_x + 70,307,100,20);
			contenu.add(tel_exp);
			//
			
			valider_colis.setBounds(pos_x + 220,340,210,25);
			create_etiquette.setBounds(pos_x + 330,370,100,25);
			annuler.setBounds(pos_x + 220,370,100,25);
			
	
		}
		//Si on vérifie un colis
		else
		{
			
			create = false;
			DateFormat dfs = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
			Timestamp date=new Timestamp(System.currentTimeMillis());	 
			//récupérer les infos dans la Bdd et les afficher
			code_barre.setEnabled(false);
			code_barre.setText(col.getCode_barre());
			forme_colis.setEnabled(false);
			modele_colis.setEnabled(false);
			fragilite_colis.setEnabled(false);
			fragilite_colis.setSelectedIndex(col.getFragilite().intValue());
			largeur.setEnabled(false);
			hauteur.setEnabled(false);
			profondeur.setEnabled(false);
			poids.setEnabled(false);
			poids.setText(col.getPoids().toString());	
			date_envoie.setEnabled(false);
			date_envoie.setText(col.getDate().toString());	
			donnees_dest.setEnabled(false);
			donnees_exp.setEnabled(false);
			voir_incident.setVisible(true);
			
			AccesBDDModelesColis test3=new AccesBDDModelesColis();

			try{
				modelecolis = test3.rechercher(col.getModele());
				System.out.println(col.getModele());
			}
			catch(SQLException e2){
				System.out.println(e2.getMessage());
			}
			
			forme_colis.setSelectedIndex(modelecolis.getForme().intValue());
			modele_colis.setEnabled(false);
			modele_colis.setSelectedIndex(modelecolis.getModele().intValue());
			largeur.setText(modelecolis.getLargeur().toString());
			hauteur.setText(modelecolis.getHauteur().toString());
			profondeur.setText(modelecolis.getProfondeur().toString());
			
			AccesBDDPersonne test1=new AccesBDDPersonne();

			try{
				destinataire = test1.rechercher(col.getDestinataire().getId());
			}
			catch(SQLException e2){
				System.out.println(e2.getMessage());
			}
			AccesBDDLocalisation test2=new AccesBDDLocalisation();
			try{
				localisation1 = test2.rechercher(destinataire.getLocalisation().getId());
			}
			catch(SQLException e2){
				System.out.println(e2.getMessage());
			}
			
			donnees_dest.setText(destinataire.getNom()+ " "+ destinataire.getPrenom()+ "\n"+ localisation1.getAdresse()+ "\n"+ localisation1.getCodePostal()+ " "+ localisation1.getVille()+ "\n"+destinataire.getMail());
			
			
		

			try{
				expediteur = test1.rechercher(col.getExpediteur().getId());
				
			}
			catch(SQLException e2){
				System.out.println(e2.getMessage());
			}
		
			try{
				localisation2 = test2.rechercher(expediteur.getLocalisation().getId());
			}
			catch(SQLException e2){
				System.out.println(e2.getMessage());
			}
			
			donnees_exp.setText(expediteur.getNom()+ " "+ expediteur.getPrenom()+ "\n"+ localisation2.getAdresse()+ "\n"+ localisation2.getCodePostal()+ " "+ localisation2.getVille()+ "\n"+expediteur.getMail());
			
			Vector liste_incidents = new Vector();
			AccesBDDIncident test5=new AccesBDDIncident();
			
			try {
				liste_incidents = test5.lister_colis(col.getId());
				
		            
		          for(int i=0;i<liste_incidents.size();i++){
		        	  ajouterInc((Incident)liste_incidents.get(i));
		            	//donnees.addElement(((Utilisateur)listeUtilisateurs.get(i)).toVector());
		            }
				
				
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			
			
		}
		contenu.validate();
	}
	public int SelectionId(int forme,int modele)
	{
		int temp=0;
		
		switch (forme)
		{
			case 0:
				switch(modele)
				{
					case 0: temp = 1;
					break;
					case 1: temp = 2;
					break;
					case 2: temp = 3;
					break;
					case 3: temp = 69;
					break;	
				}
				
			break;
			
			case 1:
				switch(modele)
				{
					case 0: temp = 4;
					break;
					case 1: temp = 5;
					break;
					case 2: temp = 6;
					break;
					case 3: temp = 69;
					break;	
				}
			break;
				
			case 2:
				switch(modele)
				{
					case 0: temp = 7;
					break;
					case 1: temp = 8;
					break;
					case 2: temp = 9;
					break;
					case 3: temp = 69;
					break;	
				}
			break;
		
		}
		
		
		return temp;
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
				boolean verif;
				
				//enregistrer infos dans la Bdd
				verif = verifChamps();
				if (verif == true){
				
					AccesBDDPersonne test1=new AccesBDDPersonne();
	
					//Timestamp date=new Timestamp(10);
					destinataire = new Personne(new Integer(0),nom_dest.getText(),prenom_dest.getText(), adresse_dest.getText(), cp_dest.getText(), ville_dest.getText(), email_dest.getText(), tel_dest.getText());
					//public Personne(Integer id, String nom, String prenom, String adresse, String codePostal, String ville, String mail, String telephone)
					try{
						test1.ajouter(destinataire);
					}
					catch(SQLException e2){
						System.out.println(e2.getMessage());
					}
					
					
					AccesBDDPersonne test4=new AccesBDDPersonne();
	
					//Timestamp date=new Timestamp(10);
					expediteur = new Personne(new Integer(0),nom_exp.getText(),prenom_exp.getText(), adresse_exp.getText(), cp_exp.getText(), ville_exp.getText(), email_exp.getText(), tel_exp.getText());
					try{
						test4.ajouter(expediteur);
					}
					catch(SQLException e4){
						System.out.println(e4.getMessage());
					}
				
					
					ModeleColis modele=null;
					
					//int selectionidcolis = modele.SelectionId(forme_colis.getSelectedIndex(),modele_colis.getSelectedIndex());
					
					int selectmodelecolis = SelectionId(forme_colis.getSelectedIndex(),modele_colis.getSelectedIndex());
					
					if(selectmodelecolis == 69)
					{
						//float volume=0 ;
						AccesBDDModelesColis test6=new AccesBDDModelesColis();
						modele = new ModeleColis(new Integer(selectmodelecolis),new Integer(forme_colis.getSelectedIndex()),new Integer(modele_colis.getSelectedIndex()),new Integer(hauteur.getText()),new Integer(largeur.getText()),new Integer(profondeur.getText()),new Integer(0),new Integer(20));
						try{
							selectmodelecolis = test6.ajouter(modele);
						}
						catch(SQLException e6){
							System.out.println(e6.getMessage());
						}
						
						
					}
					
					
					
					AccesBDDEntrepot test6=new AccesBDDEntrepot();
					entrepot = new Entrepot("25 rue des peupliers","94250","Villejuif","0136523698");
					
					try{
						test6.ajouter(entrepot);
					}
					catch(SQLException e2){
						System.out.println(e2.getMessage());
					}
	
					AccesBDDColis test=new AccesBDDColis();
	
					Timestamp date=new Timestamp(10);
					col = new Colis(new Integer(0),code_barre.getText(),expediteur,destinataire,utilisateur,new Integer(poids.getText()),date,new Integer(fragilite_colis.getSelectedIndex()),new Integer(selectmodelecolis),entrepot,"20");
				
					try{
						test.ajouter(col);
					}
					catch(SQLException e2){
						System.out.println(e2.getMessage());
					}
				informations_colis1();
				}
			}
			//cas d'un colis à vérifier
			else
			{
				//enregistrer heure de passage au poste d'entrée
				informations_colis1();
			}
			
				
		}
		
		if (source == create_incident)
		{
			create1 = true;
			JFrame fen3 = new Fenetre_create_incident(col,utilisateur,incident,create1,this);
			fen3.setVisible(true);
			
		}
		if (source == create_etiquette)
		{
			JFrame fen4 = new Entree_Create_etiquette(code_barre.getText());
			fen4.setVisible(true);
		}
		
		if (source == annuler)
		{
			informations_colis1();
		}
		if ( source == voir_incident)
		{
			create1 = false;
			int ligneActive;
			int ligneSelect = tabInc.getSelectedRow();
			if(ligneSelect != -1){
				ligneActive = sorter.modelIndex(ligneSelect);
				Vector cVect = (Vector) modeleTabInc.getRow(ligneActive);
				Incident c = new Incident(cVect);
				//String temp = c.getId().toString();
				JFrame fen3 = new Fenetre_create_incident(col,utilisateur,c,create1,this);
				fen3.setVisible(true);
				
			}
		}
		
		
		
	}
	private boolean verifChamps(){
		boolean ret = false;		
		boolean erreurPoids = false;
		boolean erreurCPdest = false;
		boolean erreurCPexp = false;
		boolean erreurhaut = false;
		boolean erreurlarg = false;
		boolean erreurprof = false;
	
		
		// On vérifie que la valeur numérique soit correctement saisie
		try{
			new Integer(poids.getText().trim());
			try{
				new Integer(cp_exp.getText().trim());
				try{
					new Integer(cp_dest.getText().trim());
					try{
						new Integer(hauteur.getText().trim());
						try{
							new Integer(largeur.getText().trim());
							try{
								new Integer(profondeur.getText().trim());
											
							}				
							catch(Exception e){
								erreurprof = true;
							}			
						}				
						catch(Exception e){
							erreurlarg = true;
						}			
					}				
					catch(Exception e){
						erreurhaut = true;
					}			
				}				
				catch(Exception e){
					erreurCPdest = true;
				}
			}
			catch(Exception e){
				erreurCPexp = true;
			}
		}
		catch(NumberFormatException e){
			erreurPoids = true;
		}

		// On vérifie que tous les champs sont remplis
		if(poids.getText().equals("") || erreurPoids) setWarning("Poids");
		else if(nom_dest.getText().equals("")) setWarning("Nom destinataire");
		else if(prenom_dest.getText().equals("")) setWarning("Prénom destinataire");
		else if(adresse_dest.getText().equals("")) setWarning("Adresse destinataire");
		else if(ville_dest.getText().equals("")) setWarning("Ville destinataire");
		else if(cp_dest.getText().equals("") || erreurCPdest) setWarning("CP destinataire");
		else if(email_dest.getText().equals("")) setWarning("Email destinataire");
		else if(tel_dest.getText().equals("")) setWarning("Téléphone destinataire");
		else if(nom_exp.getText().equals("")) setWarning("Nom expéditeur");
		else if(prenom_exp.getText().equals("")) setWarning("Prénom expéditeur");
		else if(adresse_exp.getText().equals("")) setWarning("Adresse expéditeur");
		else if(ville_exp.getText().equals("")) setWarning("Ville expéditeur");
		else if(cp_exp.getText().equals("") || erreurCPexp) setWarning("CP expéditeur");
		else if(email_exp.getText().equals("")) setWarning("Email expéditeur");
		else if(tel_exp.getText().equals("")) setWarning("Téléphone expéditeur");
		else if (modele_colis.getSelectedIndex()== 3 && (hauteur.getText().equals("") || erreurhaut))setWarning("Hauteur");
		else if (modele_colis.getSelectedIndex()== 3 && (largeur.getText().equals("") || erreurlarg))setWarning("Largeur");
		else if (modele_colis.getSelectedIndex()== 3 && (profondeur.getText().equals("") || erreurprof))setWarning("Profondeur");
		else ret = true;
		
		return ret;
	}

	// Ajoute un message d'erreur à la boite de dialogue si un champs est mal renseigné
	private void setWarning(String s){
		JOptionPane.showMessageDialog(this,"Le champs \""+s+"\" est mal renseigné.","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
	
	}
	
	private JMenuBar barreMenus;
	private JMenu fichier,etiquette;
	private JMenuItem se_deloguer,creation;
	private JLabel label_email_dest,label_tel_exp,label_ville_exp,label_adresse_exp,label_cp_exp,label_nom_exp,label_prenom_exp,label_email_exp,label_tel_dest,label_ville_dest,label_adresse_dest,label_cp_dest,label_nom_dest,label_prenom_dest,label_liste_incidents,label_profondeur_colis,label_hauteur_colis,label_largeur_colis,label_camion,label_cam,label_date,label_poids,label_dest,label_exp,numero_colis,label_forme_colis,label_modele_colis,label_fragile;
	private JTextField tel_exp,email_exp,ville_exp,adresse_exp,cp_exp,nom_exp,prenom_exp,tel_dest,email_dest,ville_dest,adresse_dest,cp_dest,nom_dest,prenom_dest,hauteur,profondeur,largeur,date_envoie,poids,code_barre;
	private String[] formes={"cube","pavé","cylindre"}, modele={"modèle1","modèle2","modèle3","personalisé"},fragilite={"trés fragile","fragile","pas fragile"};
	private JComboBox forme_colis,modele_colis,fragilite_colis;
	private JButton voir_incident,annuler,create_etiquette,create_incident,valider_colis;
	private JTextArea donnees_dest,donnees_exp;
	private Container contenu ;
	private boolean create,create1;
	private String login;
	private static final int pos_x = 220 , pos_x1 = 350;
	private Vector nomColonnes,nomColonnes1;
	private Vector donnees,donnees1;
	private JTable tabColis,tabInc;
	private ModeleTable modeleTabColis,modeleTabInc;
	private TableSorter sorter;
	private JScrollPane scrollPane1;
	private Colis col;
	private Utilisateur utilisateur;
	private Personne destinataire,expediteur;
	private ModeleColis modelecolis;
	private Entrepot entrepot;
	private Localisation localisation1,localisation2;
	private Incident incident;

}
