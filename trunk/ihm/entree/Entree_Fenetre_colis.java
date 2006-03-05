package ihm.entree;

import ihm.Fenetre_login;
import ihm.ModeleTable;
import ihm.TableSorter;

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
import accesBDD.AccesBDDChargement;
import accesBDD.AccesBDDModelesColis;
import accesBDD.AccesBDDEntrepot;
import accesBDD.AccesBDDLocalisation;
import accesBDD.AccesBDDIncident;

//Cette classe correspond � la fenetre de saisi ou de v�rification d'un colis.

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
		setTitle(u.getPersonne().getNom() +" "+ u.getPersonne().getPrenom()  + " - Entr�e");
		setBounds(72,24,900,720);
		//login = u.getLogin();
		//cr�ation de la barre de menus
		barreMenus = new JMenuBar();
		setJMenuBar(barreMenus);
		//cr�ation du menu fichier;
		fichier = new JMenu("Fichier");
		//ajout du menu fichier dans la barre de menus
		barreMenus.add(fichier);
		//cr�ation du sous menu se d�loguer
		se_deloguer = new JMenuItem("Se d�loguer");
		//ajout du sous menu dans le menu fichier
		fichier.add(se_deloguer);
		// Ajouter l'�couteur de se d�loguer
		se_deloguer.addActionListener(this);
		//idem pour les autres menus
		
		etiquette = new JMenu("Etiquette");
		barreMenus.add(etiquette);
		creation = new JMenuItem("Cr�ation");
		etiquette.add(creation);
		creation.addActionListener(this);
		charg = new Chargement();
		charg = null;
		liste_chargement = new Vector();
	
		create_graphique(); //appel la fonction de la cr�ation graphique
	
		
		informations_colis1();// appel de la fonction qui demande le code barre	
	}
	
	
	//fonction permettant de cr�er l'interface graphique de la fenetre
	public void create_graphique(){
		
		contenu = getContentPane();
		contenu.setLayout(new FlowLayout());
		
		contenu1 = getContentPane();
		contenu1.setLayout(new FlowLayout());
		
		getContentPane().setLayout(null);
		numero_colis = new JLabel("Num�ro :");
		numero_colis.setBounds(pos_x,10,120,10);
		contenu.add(numero_colis);

		code_barre = new JTextField(15);
		code_barre.setBounds(pos_x + 65,7,200,20);
		contenu.add(code_barre);
		code_barre.setEnabled(false);
		
		label_forme_colis = new JLabel("Forme : ");
		label_forme_colis.setBounds(pos_x,40,120,10);
		contenu.add(label_forme_colis);
		
		forme_colis = new JComboBox(formes);
		forme_colis.setEditable(false);
		forme_colis.setBounds(pos_x + 65,37,200,20);
		contenu.add(forme_colis);
		forme_colis.addItemListener(this);
		forme_colis.setEnabled(false);
		
		label_modele_colis = new JLabel("Mod�le :");
		label_modele_colis.setBounds(pos_x ,70,120,10);
		contenu.add(label_modele_colis);
		
		label_hauteur_colis = new JLabel("Hauteur (cm) :");
		label_hauteur_colis.setBounds(pos_x+340, 67,120,15);
		contenu.add(label_hauteur_colis);
		label_hauteur_colis.setVisible(false);
		
		label_largeur_colis = new JLabel("Largeur (cm) :");
		label_largeur_colis.setBounds(pos_x+340,97,120,15);
		contenu.add(label_largeur_colis);
		label_largeur_colis.setVisible(false);
		
		label_profondeur_colis = new JLabel("Profondeur (cm) :");
		label_profondeur_colis.setBounds(pos_x+340,127,120,15);
		contenu.add(label_profondeur_colis);
		label_profondeur_colis.setVisible(false);
		
		hauteur = new JTextField(15);
		hauteur.setBounds(pos_x + 445,67,50,20);
		contenu.add(hauteur);
		hauteur.setVisible(false);
		
		largeur = new JTextField(15);
		largeur.setBounds(pos_x + 445,97,50,20);
		contenu.add(largeur);
		largeur.setVisible(false);
		
		profondeur = new JTextField(15);
		profondeur.setBounds(pos_x + 445,127,50,20);
		contenu.add(profondeur);
		profondeur.setVisible(false);
		
		modele_colis = new JComboBox(modele);
		modele_colis.setBounds(pos_x + 65,67,200,20);
		contenu.add(modele_colis);
		modele_colis.addItemListener(this);
		modele_colis.setEnabled(false);
		
		label_fragile = new JLabel("Fragilit� :");
		label_fragile.setBounds(pos_x,100,150,15);
		contenu.add(label_fragile);
		
		fragilite_colis = new JComboBox(fragilite);
		fragilite_colis.setBounds(pos_x + 65,97,200,20);
		contenu.add(fragilite_colis);
		fragilite_colis.setEnabled(false);
		
		label_poids = new JLabel("Poids (kg) : ");
		label_poids.setBounds(pos_x,130,150,15);
		contenu.add(label_poids);
		
		poids = new JTextField(5);
		poids.setBounds(pos_x + 70,127,30,20);
		contenu.add(poids);
		poids.setEnabled(false);
		
		label_date = new JLabel("Date d'envoie :");
		label_date.setBounds(pos_x + 105,130,150,15);
		contenu.add(label_date);
		
		date_envoie = new JTextField(10);
		date_envoie.setBounds(pos_x + 195,127,130,20);
		contenu.add(date_envoie);
		date_envoie.setEnabled(false);
			
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
		donnees_dest.setEnabled(false);
		
		label_exp = new JLabel("Exp�diteur :");
		label_exp.setBounds(pos_x,230,150,15);
		contenu.add(label_exp);
		
		donnees_exp = new JTextArea();
		donnees_exp.setColumns(25);
		donnees_exp.setRows(4);
		donnees_exp.setBounds(pos_x + 90,230,175,65);
		donnees_exp.setLineWrap(true);
		
		donnees_exp.setWrapStyleWord(true);
		contenu.add(donnees_exp);
		donnees_exp.setEnabled(false);
		valider_colis = new JButton("Envoyer en zone de stockage");
		contenu.add(valider_colis);
		valider_colis.addActionListener(this);
		
		modif_infos = new JButton("Modification");
		contenu.add(modif_infos);
		modif_infos.setBounds(pos_x + 20,340,110,25);
		modif_infos.addActionListener(this);
		
		incident_automatique = new JButton("Incident");
		contenu.add(incident_automatique);
		incident_automatique.setBounds(805,530,80,25);
		incident_automatique.addActionListener(this);
		
		create_etiquette = new JButton("Etiquette");
		contenu.add(create_etiquette);
		create_etiquette.addActionListener(this);
		
		select_personne = new JButton("S�lection des personnes");
		contenu.add(select_personne);
		select_personne.addActionListener(this);
		
		label_camion = new JLabel("");
		label_camion.setBounds(pos_x,410,390,15);
		contenu.add(label_camion);
		
		create_incident = new JButton("Incident");
		create_incident.setBounds(pos_x + 140,340,110,25);
		contenu.add(create_incident);
		create_incident.addActionListener(this);
		
		valider_colis.setBounds(pos_x + 20,310,230,25);
		create_etiquette.setBounds(pos_x+20,370,110,25);
		
		label_liste_incidents = new JLabel("Incidents r�pertori�s pour ce colis :");
		label_liste_incidents.setBounds(pos_x + 370,160,220,15);
		contenu.add(label_liste_incidents);
		
		annuler = new JButton("Annuler");
		annuler.setBounds(pos_x+140,370,110,25);
		contenu.add(annuler);
		annuler.addActionListener(this);
		
		nomColonnes = new Vector();
		nomColonnes.add("id");
        nomColonnes.add("entrepot");
        nomColonnes.add("Code barre");
        nomColonnes.add("expediteur");
        nomColonnes.add("destinataire");
        nomColonnes.add("destination");
        nomColonnes.add("utilisateur");
        nomColonnes.add("Poids");
        nomColonnes.add("Date d'envoi");
        nomColonnes.add("Fragilit�");
       // nomColonnes.add("modele id");
        nomColonnes.add("Forme");
        nomColonnes.add("Mod�le");
        nomColonnes.add("valeur_declaree");
           
        donnees = new Vector();
		modeleTabColis = new ModeleTable(nomColonnes,donnees);
		
		// Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		sorter1 = new TableSorter(modeleTabColis);
		
		// Cr�ation du tableau
		tabColis = new JTable(sorter1);
		
		// initialisation du Sorter
		sorter1.setTableHeader(tabColis.getTableHeader());
		
		// On cr�e les colonnes du tableau selon le mod�le
		tabColis.createDefaultColumnsFromModel();
		tabColis.setOpaque(false);
		tabColis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabColis.removeColumn(tabColis.getColumnModel().getColumn(0));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(0));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(1));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(1));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(1));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(1));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(6));
		// On place le tableau dans un ScrollPane pour qu'il soit d�filable
		JScrollPane scrollPane = new JScrollPane(tabColis);

		// On d�finit les dimensions du tableau
		tabColis.setPreferredScrollableViewportSize(new Dimension(20,20));

		// On place le tableau
		scrollPane.setBounds(220,440,580,210);

		// On d�finit le tableau transparent
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
		
		label_cam = new JLabel(" Vues cam�ra :");
		label_cam.setBounds(65,10,160,10);
		contenu.add(label_cam);
		
		//On affiche les images du colis
		int j=0;
		for (int i =1; i< 6;i++){
			AffichageImage image = new AffichageImage("images/colis/face"+i+".JPG");
			image.setBounds(20,27+(j*128),163,120);
			j++;
			contenu.add(image);
		}
		
	}
	
	//	Fonction permettant d'ajouter un colis dans le tableau de chargement
	public void ajouterColis(Colis c){
		modeleTabColis.addRow(c.toVector());
		modeleTabColis.fireTableDataChanged();
		tabColis.updateUI();
	}
	
	//Fonction permettant d'ajouter un incident dans le tableau des incidents
	public void ajouterInc(Incident c){
		modeleTabInc.addRow(c.toVector());
		modeleTabInc.fireTableDataChanged();
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
		contenu.removeAll();
		contenu.validate();
		create_graphique();	
		contenu.validate();
		repaint();
		String barre_code,temp="";
		int Ok=0;
		if (charg !=null){
			ajout_chargement();
		}
		
		// Tant que le code barre saisi n'est pas valide ou null ( pour la cr�ation d'un nouveau colis)
		//On affiche la fenetre douchette
		do{	
			barre_code = douchette();
			temp = barre_code;
			AccesBDDColis rechercher_colis=new AccesBDDColis();
			AccesBDDChargement rechercher_chargement=new AccesBDDChargement();
			
			//Si on veut cr�er un nouveau colis
			if (barre_code==null || barre_code.length()==0)
			{
				col = new Colis();
				Ok = 1;
			
			}
			//Si on veut chercher un colis ou un chargement dans la Bdd
			else{
					try{
						new Integer(temp.trim());
						int i = Integer.parseInt(temp);
						try {
							//on cherche un colis dans la Bdd
							col = rechercher_colis.rechercherCode_barre(i);
							
							//Si le colis existe on quitte la boucle
								if(col!=null){
									Ok =1;
									break;
								}
								
							
							} catch (SQLException e) {
					
								e.printStackTrace();
							}
							
							
					}
					//Si le code barre n'est pas un chiffre
					catch(NumberFormatException e){
						JOptionPane.showMessageDialog(this,"Le code barre ne correspond � aucun colis ou chargement.","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
					}
					
					nombre_colis = liste_chargement.size();
					
					if (nombre_colis == 0 || charg ==null){
					
					try {
						//on cherche un colis dans la Bdd
						charg = rechercher_chargement.rechercher(barre_code);
						
						//Si le colis existe on quitte la boucle
							if(charg!=null){
								//ajout_chargement(charg);
								JOptionPane.showMessageDialog(this,"Le chargement "+ charg.getCodeBarre() +" vient d'�tre ajout�","Message d'information",JOptionPane.INFORMATION_MESSAGE);
								ajout_chargement();
								informations_colis1();
								Ok =1;
							}
							
							else{
							//Si il n'existe ni colis ni chargement avec ce code barre :
							//JOptionPane.showMessageDialog(this,"Le code barre ne correspond � aucun colis ou chargement.","Message d'avertissement",JOptionPane.ERROR_MESSAGE);					
							}
						
						} catch (SQLException e) {
				
							e.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(this,"Il y a d�ja le chargement "+ charg.getCodeBarre() +" en cours de validation","Message d'information",JOptionPane.INFORMATION_MESSAGE);
					}
					
			}
	
		}while(Ok == 0); // tant que le code barre n'est pas valide ou null
	
		//On reaffiche tout le contenu graphique 
		
		int de;
		String id="";
		int error = 1;
		
		
		
		//Si on veut saisir un nouveau colis.
		if (col.getCode_barre()==null)
		{
			
			//cr�ation du code barre 
			do{
				for (int i = 0; i< 9;i++)
				{
					Random r = new Random();
					de = r.nextInt(10);
					id = id + de;
				}
						
			}while(error==0);
		
			// On affiche toutes les donn�es vierges
			create = 0;
			informations_vierge();
			code_barre.setText(id);
			date_envoie.setText(new Timestamp(System.currentTimeMillis()).toLocaleString());
			
			
			
	
		}
		//Si on v�rifie un colis
		else
		{
			create = 1;
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
			date_envoie.setText(col.getDate().toLocaleString());
			donnees_dest.setEnabled(false);
			donnees_exp.setEnabled(false);
			voir_incident.setVisible(true);
			
			//On r�cup�re les infos dans la Bdd et on les affiche
			
			// On cherche les infos sur le mod�le du colis
			AccesBDDModelesColis test3=new AccesBDDModelesColis();
			try{
				modelecolis = test3.rechercher(col.getModele().getId());
				
			}
			catch(SQLException e2){
				System.out.println(e2.getMessage());
			}
			//On affiche les infos sur le mod�le de colis
			forme_colis.setSelectedIndex(modelecolis.getForme().intValue());
			modele_colis.setEnabled(false);
			modele_colis.setSelectedIndex(modelecolis.getModele().intValue());
			largeur.setText(modelecolis.getLargeur().toString());
			hauteur.setText(modelecolis.getHauteur().toString());
			profondeur.setText(modelecolis.getProfondeur().toString());
			
			//On cherche les infos sur le destinataire
			AccesBDDPersonne test1=new AccesBDDPersonne();

			try{
				destinataire = test1.rechercher(col.getDestinataire().getId());
			}
			catch(SQLException e2){
				System.out.println(e2.getMessage());
			}
			//On cherche les infos sur l'adresse du destinataire
			AccesBDDLocalisation test2=new AccesBDDLocalisation();
			try{
				localisation1 = test2.rechercher(destinataire.getLocalisation().getId());
			}
			catch(SQLException e2){
				System.out.println(e2.getMessage());
			}
			//On affiche les donn�es du destinataire
			donnees_dest.setText(destinataire.getNom()+ " "+ destinataire.getPrenom()+ "\n"+ localisation1.getAdresse()+ "\n"+ localisation1.getCodePostal()+ " "+ localisation1.getVille()+ "\n"+destinataire.getMail());
			
			
		
			//On cherche les infos sur l'exp�diteur
			try{
				expediteur = test1.rechercher(col.getExpediteur().getId());
				
			}
			catch(SQLException e2){
				System.out.println(e2.getMessage());
			}
			//On cherche les infos sur l'adresse de l'exp�diteur
			try{
				localisation2 = test2.rechercher(expediteur.getLocalisation().getId());
			}
			catch(SQLException e2){
				System.out.println(e2.getMessage());
			}
			//On affiche les infos de l'exp�diteur
			donnees_exp.setText(expediteur.getNom()+ " "+ expediteur.getPrenom()+ "\n"+ localisation2.getAdresse()+ "\n"+ localisation2.getCodePostal()+ " "+ localisation2.getVille()+ "\n"+expediteur.getMail());
			
			//On liste les incidents qu'il y a eu sur le colis
			Vector liste_incidents = new Vector();
			AccesBDDIncident test5=new AccesBDDIncident();
			
			try {
				liste_incidents = test5.lister_colis(col.getId());
				
		           //On ajoute les incidents dans le tableau 
		          for(int i=0;i<liste_incidents.size();i++){
		        	  ajouterInc((Incident)liste_incidents.get(i));
		           
		            }
				
				
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			
			
		}
		contenu.validate();
	}
	public void informations_vierge()
	{
		
		code_barre.setEnabled(false);
		date_envoie.setEnabled(false);
		scrollPane1.setVisible(false);
		label_liste_incidents.setVisible(false);
		voir_incident.setVisible(false);
		create_incident.setVisible(false);
		select_personne.setVisible(true);
		select_personne.setBounds(pos_x+240,157,180,20);
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
		
		label_prenom_dest = new JLabel("Pr�nom :");
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
		
		label_nom_exp = new JLabel("Nom :");
		label_nom_exp.setBounds(pos_x1 + pos_x,190,50,15);
		contenu.add(label_nom_exp);
		
		nom_exp = new JTextField(15);
		nom_exp.setBounds(pos_x1 + pos_x + 40,187,100,20);
		contenu.add(nom_exp);
		
		label_prenom_exp = new JLabel("Pr�nom :");
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
		modif_infos.setVisible(false);
		
		forme_colis.setEnabled(true);
		modele_colis.setEnabled(true);
		fragilite_colis.setEnabled(true);
		poids.setEnabled(true);
		
	}
	
	//Fonction permettant d'ajouter les colis d'un chargement dans le tableau
	public void ajout_chargement()
	{
		
		
		AccesBDDChargement test=new AccesBDDChargement();
		try {
			liste_chargement = test.listerColis(charg.getId());
			nombre_colis = liste_chargement.size();
			label_camion.setText("Liste des colis pr�sents dans le chargement : " + charg.getCodeBarre() +" (" + nombre_colis +" colis)");
			
	           //On ajoute les incidents dans le tableau 
	          for(int i=0;i<liste_chargement.size();i++){
	        	  ajouterColis((Colis)liste_chargement.get(i));
	           
	            }
	         
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	
	}
	
	//M�thode permettant de choisir un ID_mod�le de colis en fonction de sa forme et de son mod�le
	public int SelectionId(int forme,int modele)
	{
		int temp=0;
		
		switch (forme)
		{
			//Si le colis est carr�
			case 0:
				switch(modele)
				{
					case 0: temp = 1;//Mod�le 1
					break;
					case 1: temp = 2;//Mod�le 2
					break;
					case 2: temp = 3;//Mod�le 3
					break;
					case 3: temp = 69;////Mod�le peronnalis�
					break;	
				}
				
			break;
			
			// Sile colis est un pav�
			case 1:
				switch(modele)
				{
					case 0: temp = 4;//Mod�le 1
					break;
					case 1: temp = 5;//Mod�le 2
					break;
					case 2: temp = 6;//Mod�le 3
					break;
					case 3: temp = 69;//personnalis�
					break;	
				}
			break;
				
			//Sile colis est cylindrique
			case 2:
				switch(modele)
				{
					case 0: temp = 7;//Mod�le 1
					break;
					case 1: temp = 8;//Mod�le 2
					break;
					case 2: temp = 9;//Mod�le 3
					break;
					case 3: temp = 69;//Mod�le personnalis�
					break;	
				}
			break;
		
		}
		
		
		return temp;
	}
	

	//Si le mod�le choisi est "personnalis�"  on affiche les dimensions du colis
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
			hauteur.setText("");
			largeur.setText("");
			profondeur.setText("");
			
			int num1 = forme_colis.getSelectedIndex();
			if (num1 == 2)
			{
				label_profondeur_colis.setVisible(false);
				profondeur.setVisible(false);
				label_largeur_colis.setText("Diam�tre (cm) :");
				
				
			}
			else if(num1==0)
			{
				label_profondeur_colis.setVisible(false);
				profondeur.setVisible(false);
				label_largeur_colis.setVisible(false);
				largeur.setVisible(false);
				label_hauteur_colis.setText("Cot� (cm) :");
				
				
			}
			else
			{
				label_profondeur_colis.setVisible(true);
				profondeur.setVisible(true);
				label_largeur_colis.setVisible(true);
				largeur.setVisible(true);
				label_largeur_colis.setText("Largeur (cm) :");
				label_hauteur_colis.setText("Hauteur (cm) :");
				
			}
			
		}
		
		
		
		
		
	}
	
	public void modification_informations()
	{
		create =2;
		informations_vierge();
		code_barre.setText(col.getCode_barre());
		date_envoie.setText(col.getDate().toLocaleString());
		modifier_info_personne(expediteur,1);
		modifier_info_personne(destinataire,2);
		forme_colis.setEnabled(true);
		modele_colis.setEnabled(true);
		fragilite_colis.setEnabled(true);
		hauteur.setEnabled(true);
		largeur.setEnabled(true);
		profondeur.setEnabled(true);
		poids.setEnabled(true);
		
		
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		//Si l'utilisateur veut se d�loguer
		if (source ==se_deloguer) {
			
			dispose();
			JFrame fen1 = new Fenetre_login();
			fen1.setVisible(true);
		}
		//Si l'utilisateur veut cr�er une �tiquette
		if (source ==creation) {
			
			JFrame fen4 = new Entree_Create_etiquette(code_barre.getText());
			fen4.setVisible(true);
		}
		//Si l'utilisateur veut choisir une personne comme exp�diteur ou destinataire
		if (source ==select_personne) {
			JFrame fen5 = new Entree_select_personne(this);
			fen5.setVisible(true);
		}
		
		//Lorsqu'on envoie le colis en zone de stockage
		if (source == valider_colis)
		{
			//Cas d'un nouveau colis
			if(create == 0){
				boolean verif;
				
				//enregistrer infos dans la Bdd.
				//On v�rifie tous les champs
				verif = verifChamps();
				//Si la v�rification est Ok
				if (verif == true){
				
					//On enregistre le destinataire dans la bdd
					AccesBDDPersonne test1=new AccesBDDPersonne();
					destinataire = new Personne(new Integer(0),nom_dest.getText(),prenom_dest.getText(), adresse_dest.getText(), cp_dest.getText(), ville_dest.getText(), email_dest.getText(), tel_dest.getText());
			
					try{
						
						test1.ajouter(destinataire);
						
					}
					catch(SQLException e2){
						
						System.out.println(e2.getMessage());
						
					}
					
					//On enregistre l'exp�diteur dans la bdd
					AccesBDDPersonne test4=new AccesBDDPersonne();
					expediteur = new Personne(new Integer(0),nom_exp.getText(),prenom_exp.getText(), adresse_exp.getText(), cp_exp.getText(), ville_exp.getText(), email_exp.getText(), tel_exp.getText());
					try{
						test4.ajouter(expediteur);
					}
					catch(SQLException e4){
						System.out.println(e4.getMessage());
					}
				
					
					ModeleColis modele=null;
					//On recherche l' id_modele de colis
					int selectmodelecolis = SelectionId(forme_colis.getSelectedIndex(),modele_colis.getSelectedIndex());
					AccesBDDModelesColis test6=new AccesBDDModelesColis();
					//Si le mod�le n'est pas standard
					if(selectmodelecolis == 69)
					{
						String profondeur_temp= "",largeur_temp="";
						//On cr�e un nouveau mod�le de colis avec les dimensions dans la bdd
						
						if(forme_colis.getSelectedIndex() == 2)
						{
							largeur_temp = largeur.getText();
							profondeur_temp = "0";
						}
						else if(forme_colis.getSelectedIndex() == 0)
						{
							largeur_temp ="0";
							profondeur_temp = "0";
						}
						else{
							largeur_temp = largeur.getText();
							profondeur_temp = profondeur.getText();
						}
						
						modele = new ModeleColis(new Integer(selectmodelecolis),new Integer(forme_colis.getSelectedIndex()),new Integer(modele_colis.getSelectedIndex()),new Integer(hauteur.getText()),new Integer(largeur_temp),new Integer(profondeur_temp),new Integer(0));
						try{
							selectmodelecolis = test6.ajouter(modele);
						}
						catch(SQLException e6){
							
							System.out.println(e6.getMessage());
							
						}
						
						
					}
					else // On va chercher les infos sur le mod�le de colis existant
					{
						try {
							modele = test6.rechercher(new Integer(selectmodelecolis));
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
					}
					
					
					//On ajoute l'entrepot dans la bdd
					//
					String numero_cp = cp_dest.getText();
					Integer cp_entrepot = new Integer (numero_cp.substring(0,1));
					entrepot = new Entrepot();
					entrepot = rechercher_entrepot(cp_entrepot);
				
			
					//On ajoute le colis dans la BDD
					AccesBDDColis test=new AccesBDDColis();
					col = new Colis(new Integer(0),code_barre.getText(),expediteur,destinataire,utilisateur,new Integer(poids.getText()),new Timestamp(System.currentTimeMillis()),new Integer(fragilite_colis.getSelectedIndex()),modele,entrepot,"0",modele.calculerVolume());
				
					try{
						test.ajouter(col);
					}
					catch(SQLException e2){
						System.out.println(e2.getMessage());
					}
				informations_colis1();
				}
			}
			//cas d'un colis � v�rifier
			else if(create ==1)
			{
			
				//On envoie le colis en zone de stockage et on le supprime de la liste de chargement
				if (charg !=null){
				AccesBDDChargement test10=new AccesBDDChargement();
				try {
					test10.supprimer_colis(col,charg);
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				}
				//ajout_chargement();
				informations_colis1();
			}
			//Cas d'un colis � modifier
			else if(create ==2)
			{
				//update des donn�es
				boolean verif;
				
				//enregistrer infos dans la Bdd.
				//On v�rifie tous les champs
				verif = verifChamps();
				//Si la v�rification est Ok
				if (verif == true){
				
					//On enregistre le destinataire dans la bdd
					AccesBDDPersonne test1=new AccesBDDPersonne();
					Personne destinataire_temp = new Personne(destinataire.getId(),nom_dest.getText(),prenom_dest.getText(),localisation1.getId(),adresse_dest.getText(), cp_dest.getText(), ville_dest.getText(), email_dest.getText(), tel_dest.getText());
			
					try{
						test1.modifier(destinataire_temp);
					}
					catch(SQLException e2){
						System.out.println(e2.getMessage());
					}
					
					//On enregistre l'exp�diteur dans la bdd
					AccesBDDPersonne test4=new AccesBDDPersonne();
					Personne expediteur_temp = new Personne(expediteur.getId(),nom_exp.getText(),prenom_exp.getText(),localisation2.getId(),adresse_exp.getText(), cp_exp.getText(), ville_exp.getText(), email_exp.getText(), tel_exp.getText());
					try{
						test4.modifier(expediteur_temp);
					}
					catch(SQLException e4){
						System.out.println(e4.getMessage());
					}
				
					
					
					//ModeleColis modele=null;
					ModeleColis modele_temp = null;
					//On recherche l' id_modele de colis
					int selectmodelecolis = SelectionId(forme_colis.getSelectedIndex(),modele_colis.getSelectedIndex());
					AccesBDDModelesColis test6=new AccesBDDModelesColis();
					//Si le mod�le n'est pas standard
					if(selectmodelecolis == 69 && modelecolis.getId().intValue() > 9)
					{
						//On cr�e un nouveau mod�le de colis avec les dimensions dans la bdd
						String profondeur_temp= "",largeur_temp="";
						//On cr�e un nouveau mod�le de colis avec les dimensions dans la bdd
						
						if(forme_colis.getSelectedIndex() == 2)
						{
							largeur_temp = largeur.getText();
							profondeur_temp = "0";
						}
						else if(forme_colis.getSelectedIndex() == 0)
						{
							largeur_temp ="0";
							profondeur_temp = "0";
						}
						else{
							largeur_temp = largeur.getText();
							profondeur_temp = profondeur.getText();
						}
						
						modele_temp = new ModeleColis(modelecolis.getId(),new Integer(forme_colis.getSelectedIndex()),new Integer(modele_colis.getSelectedIndex()),new Integer(hauteur.getText()),new Integer(largeur_temp),new Integer(profondeur_temp),new Integer(0));
						try{
							test6.modifier(modele_temp);
						}
						catch(SQLException e6){
							System.out.println(e6.getMessage());
						}
						
						
					}
					else if(selectmodelecolis == 69 && modelecolis.getId().intValue() <= 9)
					{
						String profondeur_temp= "",largeur_temp="";
						
						if(forme_colis.getSelectedIndex() == 2)
						{
							largeur_temp = largeur.getText();
							profondeur_temp = "0";
						}
						else if(forme_colis.getSelectedIndex() == 0)
						{
							largeur_temp ="0";
							profondeur_temp = "0";
						}
						else{
							largeur_temp = largeur.getText();
							profondeur_temp = profondeur.getText();
						}
						
						modele_temp = new ModeleColis(new Integer(0),new Integer(forme_colis.getSelectedIndex()),new Integer(modele_colis.getSelectedIndex()),new Integer(hauteur.getText()),new Integer(largeur_temp),new Integer(profondeur_temp),new Integer(0));
						try{
							selectmodelecolis = test6.ajouter(modele_temp);
						}
						catch(SQLException e6){
							System.out.println(e6.getMessage());
						}
						
					}
					else // On va chercher les infos sur le mod�le de colis existant
					{
						try {
							modele_temp = test6.rechercher(new Integer(selectmodelecolis));
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
					}
					
					String numero_cp = cp_dest.getText();
					Integer cp_entrepot = new Integer (numero_cp.substring(0,1));
					entrepot = new Entrepot();
					entrepot = rechercher_entrepot(cp_entrepot);
					//On ajoute l'entrepot dans la bdd
					/*AccesBDDEntrepot test7=new AccesBDDEntrepot();
					entrepot = new Entrepot("25 rue des peupliers","94250","Villejuif","0136523698");
					
					try{
						test7.ajouter(entrepot);
					}
					catch(SQLException e2){
						System.out.println(e2.getMessage());
					}*/
	
					//On ajoute le colis dans la BDD
					AccesBDDColis test=new AccesBDDColis();
					//System.out.println(col.getId());
					Colis col_temp = new Colis(col.getId(),code_barre.getText(),expediteur,destinataire,utilisateur,new Integer(poids.getText()),col.getDate(),new Integer(fragilite_colis.getSelectedIndex()),modele_temp,entrepot,"0",modele_temp.calculerVolume());
				
					try{
						test.modifier(col_temp);
					}
					catch(SQLException e2){
						System.out.println(e2.getMessage());
					}
					if (charg!=null){
					AccesBDDChargement test10=new AccesBDDChargement();
					try {
						test10.supprimer_colis(col,charg);
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					}
				informations_colis1();
				}
				
			}
				
		}
		
		//Si on veut cr�er un incident
		if (source == create_incident)
		{
			incident = null;
			JFrame fen3 = new Fenetre_create_incident(col,utilisateur,incident,this,false);
			fen3.setVisible(true);
			
		}
		//Si on veut cr�er une �tiquette
		if (source == create_etiquette)
		{
			JFrame fen4 = new Entree_Create_etiquette(code_barre.getText());
			fen4.setVisible(true);
		}
		//Si on veut annuler le colis en cours
		if (source == annuler)
		{
			informations_colis1();
		}
		
		if (source == modif_infos)
		{
			modification_informations();
		}
		
		if ( source == incident_automatique)
		{
			//create1 = false;
			int ligneActive;
			int ligneSelect = tabColis.getSelectedRow();
			//Si une ligne a bien �t� s�lectionn�, on affiche l'incident
			if(ligneSelect != -1){
				
				ligneActive = sorter1.modelIndex(ligneSelect);
				Vector cVect = (Vector) modeleTabColis.getRow(ligneActive);
				Colis c = new Colis(cVect);
				
				
				incident = null;
				JFrame fen3 = new Fenetre_create_incident(c,utilisateur,incident,this,true);
				fen3.setVisible(true);
			
				
			}
		}
		
		//Si on veut voir un incident
		if ( source == voir_incident)
		{
			//create1 = false;
			int ligneActive;
			int ligneSelect = tabInc.getSelectedRow();
			//Si une ligne a bien �t� s�lectionn�, on affiche l'incident
			if(ligneSelect != -1){
				
				ligneActive = sorter.modelIndex(ligneSelect);
				Vector cVect = (Vector) modeleTabInc.getRow(ligneActive);
				Incident c = new Incident(cVect);
				//String temp = c.getId().toString();
				JFrame fen3 = new Fenetre_create_incident(col,utilisateur,c,this,false);
				fen3.setVisible(true);
				
			}
		}
		
		
		
	}
	private Entrepot rechercher_entrepot(Integer numero_entrepot){
		
		
		
		AccesBDDEntrepot test7=new AccesBDDEntrepot();
		Entrepot entre = new Entrepot();
		try{
			entre = test7.rechercher(numero_entrepot);
		}
		catch(SQLException e2){
			System.out.println(e2.getMessage());
		}
	
		return entre;
	}
	//M�thode permettant de v�rifier les champs 
	private boolean verifChamps(){
		boolean ret = false;		
		boolean erreurPoids = false;
		boolean erreurCPdest = false;
		boolean erreurCPexp = false;
		boolean erreurhaut = false;
		boolean erreurlarg = false;
		boolean erreurprof = false;
	
		
		// On v�rifie que la valeur num�rique soit correctement saisie
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

		// On v�rifie que tous les champs sont remplis
		if(poids.getText().equals("") || erreurPoids) setWarning("Poids");
		else if(nom_dest.getText().equals("")) setWarning("Nom destinataire");
		else if(prenom_dest.getText().equals("")) setWarning("Pr�nom destinataire");
		else if(adresse_dest.getText().equals("")) setWarning("Adresse destinataire");
		else if(ville_dest.getText().equals("")) setWarning("Ville destinataire");
		else if(cp_dest.getText().equals("") || erreurCPdest || cp_dest.getText().length() < 2) setWarning("CP destinataire");
		else if(email_dest.getText().equals("")) setWarning("Email destinataire");
		else if(tel_dest.getText().equals("")) setWarning("T�l�phone destinataire");
		else if(nom_exp.getText().equals("")) setWarning("Nom exp�diteur");
		else if(prenom_exp.getText().equals("")) setWarning("Pr�nom exp�diteur");
		else if(adresse_exp.getText().equals("")) setWarning("Adresse exp�diteur");
		else if(ville_exp.getText().equals("")) setWarning("Ville exp�diteur");
		else if(cp_exp.getText().equals("") || erreurCPexp ||cp_exp.getText().length() < 2) setWarning("CP exp�diteur");
		else if(email_exp.getText().equals("")) setWarning("Email exp�diteur");
		else if(tel_exp.getText().equals("")) setWarning("T�l�phone exp�diteur");
		else if (modele_colis.getSelectedIndex()== 3 && (hauteur.getText().equals("") || erreurhaut)){
			if (forme_colis.getSelectedIndex()==0)
			{
				setWarning("Cot�");
			}
			else setWarning("Hauteur");
		}
		else if (modele_colis.getSelectedIndex()== 3 && (forme_colis.getSelectedIndex() == 1 || forme_colis.getSelectedIndex() == 2) && (largeur.getText().equals("") || erreurlarg)) {
			if (forme_colis.getSelectedIndex()==2)
			{
				setWarning("Diam�tre");
			}
			else setWarning("Largeur");
			
		}
		else if (modele_colis.getSelectedIndex()== 3 && forme_colis.getSelectedIndex() == 1 &&(profondeur.getText().equals("") || erreurprof))setWarning("Profondeur");
		else ret = true;
		
		return ret;
	}

	// On affiche un message d'erreur  si un champs est mal renseign�
	private void setWarning(String s){
		JOptionPane.showMessageDialog(this,"Le champs \""+s+"\" est mal renseign�.","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
	
	}
	
	//M�thode permettant de saisir automatiquement les infos sur une personne
	public void modifier_info_personne(Personne p,int choix){
		if(choix ==1)
		{
			nom_exp.setText(p.getNom());
			prenom_exp.setText(p.getPrenom());
			adresse_exp.setText(p.getLocalisation().getAdresse());
			cp_exp.setText(p.getLocalisation().getCodePostal());
			ville_exp.setText(p.getLocalisation().getVille());
			email_exp.setText(p.getMail());
			tel_exp.setText(p.getTelephone());
		}
		else{
			nom_dest.setText(p.getNom());
			prenom_dest.setText(p.getPrenom());
			adresse_dest.setText(p.getLocalisation().getAdresse());
			cp_dest.setText(p.getLocalisation().getCodePostal());
			ville_dest.setText(p.getLocalisation().getVille());
			email_dest.setText(p.getMail());
			tel_dest.setText(p.getTelephone());
		}
	}

	public Chargement getChargement()		
	{
		return charg;
	}
	
	private JMenuBar barreMenus;
	private JMenu fichier,etiquette;
	private JMenuItem se_deloguer,creation;
	private JLabel label_email_dest,label_tel_exp,label_ville_exp,label_adresse_exp,label_cp_exp,label_nom_exp,label_prenom_exp,label_email_exp,label_tel_dest,label_ville_dest,label_adresse_dest,label_cp_dest,label_nom_dest,label_prenom_dest,label_liste_incidents,label_profondeur_colis,label_hauteur_colis,label_largeur_colis,label_camion,label_cam,label_date,label_poids,label_dest,label_exp,numero_colis,label_forme_colis,label_modele_colis,label_fragile;
	private JTextField tel_exp,email_exp,ville_exp,adresse_exp,cp_exp,nom_exp,prenom_exp,tel_dest,email_dest,ville_dest,adresse_dest,cp_dest,nom_dest,prenom_dest,hauteur,profondeur,largeur,date_envoie,poids,code_barre;
	private String[] formes={"cube","pav�","cylindre"}, modele={"mod�le1","mod�le2","mod�le3","personalis�"},fragilite={"tr�s fragile","fragile","pas fragile"};
	private JComboBox forme_colis,modele_colis,fragilite_colis;
	private JButton modif_infos,incident_automatique,select_personne,voir_incident,annuler,create_etiquette,create_incident,valider_colis;
	private JTextArea donnees_dest,donnees_exp;
	private Container contenu,contenu1 ;
	private int create;
	private static final int pos_x = 220 , pos_x1 = 350;
	private Vector nomColonnes,nomColonnes1;
	private Vector donnees,donnees1;
	private JTable tabColis,tabInc;
	private ModeleTable modeleTabColis,modeleTabInc;
	private TableSorter sorter,sorter1;
	private JScrollPane scrollPane1;
	private Colis col;
	private Utilisateur utilisateur;
	private Personne destinataire,expediteur;
	private ModeleColis modelecolis;
	private Entrepot entrepot;
	private Localisation localisation1,localisation2;
	private Incident incident;
	private int nombre_colis=0;
	private Vector liste_chargement;
	private Chargement charg;
	

}
