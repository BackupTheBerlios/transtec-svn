package ihm.entree;

import ihm.*;

import java.sql.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;

import donnees.*;
import accesBDD.*;


//Cette classe correspond à la fenetre de saisi ou de vérification d'un colis.

public class Entree_Fenetre_colis extends JFrame implements ActionListener, ItemListener{

	public Entree_Fenetre_colis(Utilisateur u)
	{
		
		utilisateur  = u; //on récupère l'utilisateur qui s'est logué
		setUndecorated(true); //on enleve la barre en haut
		setTitle(u.getPersonne().getNom() +" "+ u.getPersonne().getPrenom()  + " - Entrée");
		setSize(1024,768); // on définit la taille de la fenetre
		
		// on initialise le chargement à null
		charg = new Chargement();
		charg = null;
		
		liste_chargement = new Vector();
	
		//Création de la police pour les affichages de texte
		font=new Font("Verdana", Font.BOLD, 12);
		contenu=new FenetreType(utilisateur, "images/entree/bg_entrée1.png");
		create_graphique(); //appel la fonction de la création graphique
	
		informations_colis1();// appel de la fonction qui demande le code barre	
	}
	
	
	//fonction permettant de créer l'interface graphique de la fenetre
	public void create_graphique(){
		
		// on change l'image de fond
		
		
		setContentPane(contenu);
		contenu.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
	
		//label du numéro du colis
		numero_colis = new JLabel("Numéro :");
		numero_colis.setBounds(pos_x+20,260,120,10);
		numero_colis.setFont(font);
		contenu.add(numero_colis);

		//affichage du code barre
		code_barre = new JTextField(15);
		code_barre.setBounds(pos_x + 120,257,175,20);
		code_barre.setFont(font);
		contenu.add(code_barre);
		code_barre.setEnabled(false);
		
		//label de forme du colis
		label_forme_colis = new JLabel("Forme : ");
		label_forme_colis.setBounds(pos_x + 300,290,150,10);
		label_forme_colis.setFont(font);
		contenu.add(label_forme_colis);
		
        //choix de la forme du colis
		forme_colis = new JComboBox(formes);
		forme_colis.setEditable(false);
		forme_colis.setBounds(pos_x + 390,287,175,20);
		forme_colis.setFont(font);
		contenu.add(forme_colis);
		forme_colis.addItemListener(this);
		forme_colis.setEnabled(false);

		//label de modèle du colis
		label_modele_colis = new JLabel("Modèle :");
		label_modele_colis.setBounds(pos_x +20,290,175,10);
		label_modele_colis.setFont(font);
		contenu.add(label_modele_colis);

		//label de hauteur du colis
		label_hauteur_colis = new JLabel("Hauteur :");
		label_hauteur_colis.setBounds(pos_x+300,320,80,15);
		label_hauteur_colis.setFont(font);
		contenu.add(label_hauteur_colis);
		label_hauteur_colis.setVisible(false);

		//label de largeur du colis
		label_largeur_colis = new JLabel("Largeur :");
		label_largeur_colis.setBounds(pos_x+380,320,80,15);
		label_largeur_colis.setFont(font);
		contenu.add(label_largeur_colis);
		label_largeur_colis.setVisible(false);

		//label de profondeur du colis
		label_profondeur_colis = new JLabel("Profondeur :");
		label_profondeur_colis.setBounds(pos_x+460,320,120,15);
		label_profondeur_colis.setFont(font);
		contenu.add(label_profondeur_colis);
		label_profondeur_colis.setVisible(false);
		
        //hauteur du colis	
		hauteur = new JTextField(15);
		hauteur.setBounds(pos_x + 305,347,50,20);
		hauteur.setFont(font);
		contenu.add(hauteur);
		hauteur.setVisible(false);

        //largeur du colis	
		largeur = new JTextField(15);
		largeur.setBounds(pos_x + 385,347,50,20);
		largeur.setFont(font);
		contenu.add(largeur);
		largeur.setVisible(false);

        //profondeur du colis	
		profondeur = new JTextField(15);
		profondeur.setBounds(pos_x + 475,347,50,20);
		profondeur.setFont(font);
		contenu.add(profondeur);
		profondeur.setVisible(false);

		//Choix du modèle de colis
		modele_colis = new JComboBox(modele);
		modele_colis.setBounds(pos_x + 120,287,175,20);
		modele_colis.setFont(font);
		contenu.add(modele_colis);
		modele_colis.addItemListener(this);
		modele_colis.setEnabled(false);

		//label de fragilité du colis
		label_fragile = new JLabel("Fragilité :");
		label_fragile.setBounds(pos_x + 300,260,150,15);
		label_fragile.setFont(font);
		contenu.add(label_fragile);
	
		//Choix de la fragilité du colis
		fragilite_colis = new JComboBox(fragilite);
		fragilite_colis.setBounds(pos_x + 390,257,150,20);
		fragilite_colis.setFont(font);
		contenu.add(fragilite_colis);
		fragilite_colis.setEnabled(false);

		//label de poids du colis
		label_poids = new JLabel("Poids (kg) : ");
		label_poids.setBounds(pos_x+20,320,150,15);
		label_poids.setFont(font);
		contenu.add(label_poids);
	
		//Poids du colis
		poids = new JTextField(5);
		poids.setBounds(pos_x + 120,317,175,20);
		poids.setFont(font);
		contenu.add(poids);
		poids.setEnabled(false);

		//label de date d'envoie du colis
		label_date = new JLabel("Date d'envoie :");
		label_date.setBounds(pos_x + 20,350,175,15);
		label_date.setFont(font);
		contenu.add(label_date);
		
		//date d'envoie du colis
		date_envoie = new JTextField(10);
		date_envoie.setBounds(pos_x + 120,347,175,20);
		date_envoie.setFont(font);
		contenu.add(date_envoie);
		date_envoie.setEnabled(false);

		//label du destinataire du colis
		label_dest = new JLabel("Destinataire :");
		label_dest.setBounds(pos_x +20 ,380,150,15);
		label_dest.setFont(font);
		contenu.add(label_dest);
		
		//données sur le destinataire du colis
		donnees_dest = new JTextArea();
		donnees_dest.setColumns(25);
		donnees_dest.setRows(10);
		donnees_dest.setBounds(pos_x + 120,380,175,65);
		donnees_dest.setLineWrap(true);
		donnees_dest.setWrapStyleWord(true);
		donnees_dest.setFont(font);
		contenu.add(donnees_dest);
		donnees_dest.setEnabled(false);

		//label de l'expéditeur du colis
		label_exp = new JLabel("Expéditeur :");
		label_exp.setBounds(pos_x + 300,380,150,15);
		label_exp.setFont(font);
		contenu.add(label_exp);
		
		//données sur l'expéditeur du colis
		donnees_exp = new JTextArea();
		donnees_exp.setColumns(25);
		donnees_exp.setRows(4);
		donnees_exp.setBounds(pos_x +390,380,175,65);
		donnees_exp.setLineWrap(true);
		donnees_exp.setWrapStyleWord(true);
		donnees_exp.setFont(font);
		contenu.add(donnees_exp);
		donnees_exp.setEnabled(false);
		
		
		//valider_colis = new JButton("Envoyer en zone de stockage");
		//Bouton de validation du colis en zone de stockage
		valider_colis = new Bouton("images/entree/bouton_zone_stockage.png","images/entree/bouton_zone_stockage_appuyer.png");
		contenu.add(valider_colis);
		valider_colis.addActionListener(this);
		
        //Bouton pour modifier les informations du colis
		modif_infos = new Bouton("images/entree/bouton_modifier.png","images/entree/bouton_modifier_appuyer.png");
		contenu.add(modif_infos);
		modif_infos.setBounds(pos_x + 560,260,160,40);
		modif_infos.addActionListener(this);
		
        //Bouton pour créer un incident sur un colis non trouvé dans le chargement		
		//incident_automatique = new JButton("Incident");
		incident_automatique = new Bouton("images/entree/bouton_creerincident.png","images/entree/bouton_creerincident_appuyer.png");
		contenu.add(incident_automatique);
		incident_automatique.setBounds(610,585,160,25);
		incident_automatique.addActionListener(this);
		
		//Bouton pour créer une étiquette du colis
		create_etiquette = new Bouton("images/entree/bouton_etiquette.png","images/entree/bouton_etiquette_appuyer.png");
		contenu.add(create_etiquette);
		create_etiquette.addActionListener(this);
		
		//Bouton pour sélectionner une personne en tant que destinataire ou expéditeur du colis
		select_personne = new Bouton("images/entree/bouton_selection_personnes.png","images/entree/bouton_selection_personnes_appuyer.png");
		contenu.add(select_personne);
		select_personne.addActionListener(this);
		
		//label du chargement du camion
		label_camion = new JLabel("");
		label_camion.setBounds(pos_x+120,590,390,15);
		contenu.add(label_camion);
		
		//Bouton permettant de créer un incident sur le colis
		create_incident = new Bouton("images/entree/bouton_creerincident-colis.png","images/entree/bouton_creerincident-colis.png");
		create_incident.setBounds(pos_x + 580,455,160,45);
		contenu.add(create_incident);
		create_incident.addActionListener(this);
		
		//position des boutons dans la fenetre
		valider_colis.setBounds(pos_x + 580,325,175,40);
		create_etiquette.setBounds(pos_x+565,390,160,40);
		
		//label d'incidents répertoriés pour le colis
		/*label_liste_incidents = new JLabel("Incidents répertoriés pour ce colis :");
		label_liste_incidents.setBounds(pos_x + 370,160,220,15);
		contenu.add(label_liste_incidents);*/
		
		//Bouton d'annulation de vérification ou de création de colis
		annuler = new Bouton("images/entree/bouton_annuler.png","images/entree/bouton_annuler_appuyer.png");
		annuler.setBounds(pos_x+565,520,160,40);
		contenu.add(annuler);
		annuler.addActionListener(this);
		
		deconnexion=new Bouton("images/icones/deconnexion.png","images/icones/deconnexion_inv.png");
		deconnexion.setBounds(866, 50, 98, 17);
		contenu.add(this.deconnexion);
		deconnexion.addActionListener(this);
		
		
		//création du tableau des colis présents dans le chargement
		nomColonnes = new Vector();
		nomColonnes.add("id");
		nomColonnes.add("Code barre");
		nomColonnes.add("expediteur");
	    nomColonnes.add("destinataire");
	    nomColonnes.add("Origine");
	    nomColonnes.add("destination");
	    nomColonnes.add("entrepot");
        nomColonnes.add("utilisateur");
        nomColonnes.add("Poids");
        nomColonnes.add("Date d'envoi");
        nomColonnes.add("Fragilité");
        nomColonnes.add("Modèle");
        nomColonnes.add("Forme");
        nomColonnes.add("Modèle");
        nomColonnes.add("valeur_declaree");
        nomColonnes.add("Volume");
        
    	
	
           
        donnees = new Vector();
		modeleTabColis = new ModeleTable(nomColonnes,donnees);
		
		// Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter1 = new TableSorter(modeleTabColis);
		
		// Création du tableau
		tabColis = new JTable(sorter1);
		
		// initialisation du Sorter
		sorter1.setTableHeader(tabColis.getTableHeader());
		
		// On crée les colonnes du tableau selon le modèle
		tabColis.createDefaultColumnsFromModel();
		tabColis.setOpaque(false);
		tabColis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//supression des colonnes inutiles
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(0));
		//tabColis.removeColumn(tabColis.getColumnModel().getColumn(0));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(1));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(1));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(1));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(1));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(1));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(1));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(3));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(3));
		tabColis.removeColumn(tabColis.getColumnModel().getColumn(5));
		// On place le tableau dans un ScrollPane pour qu'il soit défilable
		JScrollPane scrollPane = new JScrollPane(tabColis);

		// On définit les dimensions du tableau
		tabColis.setPreferredScrollableViewportSize(new Dimension(20,20));

		// On place le tableau
		scrollPane.setBounds(240,610,545,120);

		// On définit le tableau transparent
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		// On ajoute le tableau au Panneau principal
		contenu.add(scrollPane);
		
		
		//Création du tableau des incidents répertoriés sur le colis
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
		
		//suppressio  des colonnes inutiles
		tabInc.removeColumn(tabInc.getColumnModel().getColumn(0));
		tabInc.removeColumn(tabInc.getColumnModel().getColumn(0));
		tabInc.removeColumn(tabInc.getColumnModel().getColumn(1));
		tabInc.removeColumn(tabInc.getColumnModel().getColumn(2));
		tabInc.removeColumn(tabInc.getColumnModel().getColumn(2));
		
		scrollPane1 = new JScrollPane(tabInc);
		tabInc.setPreferredScrollableViewportSize(new Dimension(20,20));
		scrollPane1.setBounds(240,485,545,95);
		scrollPane1.setOpaque(false);
		scrollPane1.getViewport().setOpaque(false);
		
		contenu.add(scrollPane1);
		
		//Bouton permettant de voir les informations sur l'incident
		voir_incident = new Bouton("images/entree/bouton_voirincident.png","images/entree/bouton_voirincident_appuyer.png");
		voir_incident.setBounds(610,460,160,25);
		contenu.add(voir_incident);
		voir_incident.addActionListener(this);
		
		//Label de vues caméra
		/*label_cam = new JLabel(" Vues caméra :");
		label_cam.setBounds(65,10,160,10);
		contenu.add(label_cam);*/
		
		//On affiche les images du colis
		int j=0;
		for (int i =1; i< 6;i++){
			AffichageImage image = new AffichageImage("images/colis/face"+i+".JPG");
			image.setBounds(85,270+(j*90),100,75);
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
		// on intialise les variables
		String barre_code,temp="";
		int Ok=0;
		// on supprime tout le contenu de la fenetre
		contenu.removeAll();
		contenu.validate();
		// on appel la fonction pour afficher tous les composants graphiques
		create_graphique();	
		contenu.validate();
		repaint();
		
		//si un chargement est enregistré, on appel la fonction permettant d'afficher la liste des colis présents dans celui-ci
		if (charg !=null){
			ajout_chargement();
		}
		
		// Tant que le code barre saisi n'est pas valide ou null ( pour la création d'un nouveau colis)
		//On affiche la fenetre douchette
		do{	
			//on appel la focntio ndouchette
			barre_code = douchette();
			temp = barre_code;
			
			// on crée 2 variables permettant d'accéder à la bdd
			AccesBDDColis rechercher_colis=new AccesBDDColis();
			AccesBDDChargement rechercher_chargement=new AccesBDDChargement();
			
			//Si on veut créer un nouveau colis
			if (barre_code==null || barre_code.length()==0)
			{
				col = new Colis(); // création d'un colis
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
						JOptionPane.showMessageDialog(this,"Le code barre ne correspond à aucun colis ou chargement.","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
					}
					
					nombre_colis = liste_chargement.size();
					
					if (nombre_colis == 0 || charg ==null){
					
					try {
						//on cherche un colis dans la Bdd
						charg = rechercher_chargement.rechercher(barre_code);
						
						//Si le colis existe on quitte la boucle
							if(charg!=null){
								//ajout_chargement(charg);
								JOptionPane.showMessageDialog(this,"Le chargement "+ charg.getCodeBarre() +" vient d'être ajouté","Message d'information",JOptionPane.INFORMATION_MESSAGE);
								ajout_chargement();
								informations_colis1();
								Ok =1;
							}
							
							else{
							//Si il n'existe ni colis ni chargement avec ce code barre :
							//JOptionPane.showMessageDialog(this,"Le code barre ne correspond à aucun colis ou chargement.","Message d'avertissement",JOptionPane.ERROR_MESSAGE);					
							}
						
						} catch (SQLException e) {
				
							e.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(this,"Il y a déja le chargement "+ charg.getCodeBarre() +" en cours de validation","Message d'information",JOptionPane.INFORMATION_MESSAGE);
					}
					
			}
	
		}while(Ok == 0); // tant que le code barre n'est pas valide ou null
	
		//Si on veut saisir un nouveau colis.
		if (col.getCode_barre()==null)
		{
			
			// On affiche toutes les données vierges
			create = 0;
			informations_vierge();	
			
		}
		//Si on vérifie un colis
		else
		{
			contenu=new FenetreType(utilisateur, "images/entree/bg_entrée1.png");
			setContentPane(contenu);
			contenu.setLayout(new FlowLayout());
			getContentPane().setLayout(null);
			create_graphique();
			if (charg !=null){
				ajout_chargement();
			}
			create = 1;
			// on affiche toutes les infos sur le colis
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
			
			//On récupère les infos dans la Bdd et on les affiche
			
			// On cherche les infos sur le modèle du colis
			AccesBDDModelesColis test3=new AccesBDDModelesColis();
			try{
				modelecolis = test3.rechercher(col.getModele().getId());
				
			}
			catch(SQLException e2){
				System.out.println(e2.getMessage());
			}
			//On affiche les infos sur le modèle de colis
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
			//On affiche les données du destinataire
			donnees_dest.setText(destinataire.getNom()+ " "+ destinataire.getPrenom()+ "\n"+ localisation1.getAdresse()+ "\n"+ localisation1.getCodePostal()+ " "+ localisation1.getVille()+ "\n"+destinataire.getMail());
			
			
		
			//On cherche les infos sur l'expéditeur
			try{
				expediteur = test1.rechercher(col.getExpediteur().getId());
				
			}
			catch(SQLException e2){
				System.out.println(e2.getMessage());
			}
			//On cherche les infos sur l'adresse de l'expéditeur
			try{
				localisation2 = test2.rechercher(expediteur.getLocalisation().getId());
			}
			catch(SQLException e2){
				System.out.println(e2.getMessage());
			}
			//On affiche les infos de l'expéditeur
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
		contenu=new FenetreType(utilisateur, "images/entree/bg_entrée4.png");
		setContentPane(contenu);
		contenu.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		create_graphique();
		if (charg !=null){
			ajout_chargement();
		}
		//initialisation des variables
		int de;
		String id="";
		int error = 1;
		//création d'un code barre à 9 chiffres
		do{
			for (int i = 0; i< 9;i++)
			{
				Random r = new Random();
				de = r.nextInt(10);
				id = id + de;
			}
					
		}while(error==0);
	
		//on affiche le code barre
		code_barre.setText(id);
		//on affiche la date et l'heure 
		date_envoie.setText(new Timestamp(System.currentTimeMillis()).toLocaleString());
		//on grise plusieurs JtextField pour qu'elle ne puisse pas être modifier par l'utilisateur
		code_barre.setEnabled(false);
		date_envoie.setEnabled(false);
		scrollPane1.setVisible(false);
		//label_liste_incidents.setVisible(false);
		voir_incident.setVisible(false);
		create_incident.setVisible(false);
		
		//On affiche le bouton permettant de choisir la personne
		select_personne.setVisible(true);
		//on déplace les objets dans la fenetre
		select_personne.setBounds(pos_x + 575,260,160,40);
		
		label_dest.setBounds(pos_x + 20,380,175,15);
		label_exp.setBounds(pos_x+470,160,150,15);
		donnees_dest.setVisible(false);
		donnees_exp.setVisible(false);
		
		//Label du nom du destinataire
		label_nom_dest = new JLabel("Nom :");
		label_nom_dest.setBounds(pos_x + 20,410,175,15);
		label_nom_dest.setFont(font);
		contenu.add(label_nom_dest);
		
		//Nom du destinataire
		nom_dest = new JTextField(15);
		nom_dest.setBounds(pos_x + 90,407,205,20);
		nom_dest.setFont(font);
		contenu.add(nom_dest);
		
		//Label du prénom du destinataire
		label_prenom_dest = new JLabel("Prénom :");
		label_prenom_dest.setBounds(pos_x + 20,435,175,15);
		label_prenom_dest.setFont(font);
		contenu.add(label_prenom_dest);

		//Prénom du destinataire
		prenom_dest = new JTextField(15);
		prenom_dest.setBounds(pos_x + 90,432,205,20);
		prenom_dest.setFont(font);
		contenu.add(prenom_dest);
		
		//Label de l'adresse du destinataire
		label_adresse_dest= new JLabel("Adresse :");
		label_adresse_dest.setBounds(pos_x + 20,460,175,15);
		label_adresse_dest.setFont(font);
		contenu.add(label_adresse_dest);

		//Adresse du destinataire
		adresse_dest = new JTextField(15);
		adresse_dest.setBounds(pos_x + 90,457,205,20);
		adresse_dest.setFont(font);
		contenu.add(adresse_dest);
		
		//Label du CP du destinataire
		label_cp_dest= new JLabel("CP :");
		label_cp_dest.setBounds(pos_x + 20,485,175,15);
		label_cp_dest.setFont(font);
		contenu.add(label_cp_dest);

		//CP du destinataire
		cp_dest = new JTextField(15);
		cp_dest.setBounds(pos_x + 90,482,205,20);
		cp_dest.setFont(font);
		contenu.add(cp_dest);
		
		//Label de la ville du destinataire
		label_ville_dest= new JLabel("Ville :");
		label_ville_dest.setBounds(pos_x + 20,510,175,15);
		label_ville_dest.setFont(font);
		contenu.add(label_ville_dest);

		//Ville du destinataire
		ville_dest = new JTextField(15);
		ville_dest.setBounds(pos_x + 90,507,205,20);
		ville_dest.setFont(font);
		contenu.add(ville_dest);
		
		//Label de l'email du destinataire
		label_email_dest= new JLabel("Email :");
		label_email_dest.setBounds(pos_x + 20,535,175,15);
		label_email_dest.setFont(font);
		contenu.add(label_email_dest);
	
		//Email du destinataire
		email_dest = new JTextField(15);
		email_dest.setBounds(pos_x + 90,532,205,20);
		email_dest.setFont(font);
		contenu.add(email_dest);
		
		//Label du tel du destinataire
		label_tel_dest= new JLabel("Tel :");
		label_tel_dest.setBounds(pos_x + 20,560,175,15);
		label_tel_dest.setFont(font);
		contenu.add(label_tel_dest);

		//tel du destinataire
		tel_dest = new JTextField(15);
		tel_dest.setBounds(pos_x + 90,557,205,20);
		tel_dest.setFont(font);
		contenu.add(tel_dest);
		
		//Label du nom de l'expéditeur
		label_nom_exp = new JLabel("Nom :");
		label_nom_exp.setBounds(pos_x1 + pos_x,190,50,15);
		label_nom_exp.setFont(font);
		contenu.add(label_nom_exp);

		//Nom de l'expéditeur
		nom_exp = new JTextField(15);
		nom_exp.setBounds(pos_x1 + pos_x + 40,187,100,20);
		nom_exp.setFont(font);
		contenu.add(nom_exp);
		
		//Label du prénom de l'expéditeur
		label_prenom_exp = new JLabel("Prénom :");
		label_prenom_exp.setBounds(pos_x1 + pos_x+150,190,60,15);
		label_prenom_exp.setFont(font);
		contenu.add(label_prenom_exp);

		//Préom de l'expéditeur
		prenom_exp = new JTextField(15);
		prenom_exp.setBounds(pos_x1 + pos_x + 210,187,100,20);
		prenom_exp.setFont(font);
		contenu.add(prenom_exp);
		
		//Label de l'adresse de l'expéditeur
		label_adresse_exp= new JLabel("Adresse :");
		label_adresse_exp.setBounds(pos_x1 + pos_x,220,60,15);
		label_adresse_exp.setFont(font);
		contenu.add(label_adresse_exp);

		//Adresse de l'expéditeur
		adresse_exp = new JTextField(15);
		adresse_exp.setBounds(pos_x1 + pos_x + 60,217,250,20);
		adresse_exp.setFont(font);
		contenu.add(adresse_exp);
		
		//Label du cp de l'expéditeur
		label_cp_exp= new JLabel("Code Postal :");
		label_cp_exp.setBounds(pos_x1 + pos_x,250,90,15);
		label_cp_exp.setFont(font);
		contenu.add(label_cp_exp);

		//Cp de l'expéditeur
		cp_exp = new JTextField(15);
		cp_exp.setBounds(pos_x1 + pos_x + 80,247,60,20);
		cp_exp.setFont(font);
		contenu.add(cp_exp);
		
		//Label de la ville de l'expéditeur
		label_ville_exp= new JLabel(" Ville :");
		label_ville_exp.setBounds(pos_x1 + pos_x+150,250,60,15);
		label_ville_exp.setFont(font);
		contenu.add(label_ville_exp);
	
		//Ville de l'expéditeur
		ville_exp = new JTextField(15);
		ville_exp.setBounds(pos_x1 + pos_x + 190,247,120,20);
		ville_exp.setFont(font);
		contenu.add(ville_exp);
		
		//Label de l'email de l'expéditeur
		label_email_exp= new JLabel("Email :");
		label_email_exp.setBounds(pos_x1 + pos_x,280,60,15);
		label_email_exp.setFont(font);
		contenu.add(label_email_exp);
		
		//Email de l'expéditeur
		email_exp = new JTextField(15);
		email_exp.setBounds(pos_x1 + pos_x + 40,277,270,20);
		email_exp.setFont(font);
		contenu.add(email_exp);
		
		//Label du tel de l'expéditeur
		label_tel_exp= new JLabel("Telephone :");
		label_tel_exp.setBounds(pos_x1 + pos_x,310,80,15);
		label_tel_exp.setFont(font);
		contenu.add(label_tel_exp);
		
		//Tel de l'expéditeur
		tel_exp= new JTextField(15);
		tel_exp.setBounds(pos_x1 + pos_x + 70,307,100,20);
		tel_exp.setFont(font);
		contenu.add(tel_exp);
		
		
		//On positionne tous les boutons
		//valider_colis.setBounds(pos_x + 220,340,210,25);
		//create_etiquette.setBounds(pos_x + 330,370,100,25);
		//annuler.setBounds(pos_x + 220,370,100,25);
		modif_infos.setVisible(false);
	
		//on dégrise différente infos sur le colis pour permettre à l'utilisateur de changer les infos
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
			label_camion.setText(charg.getCodeBarre() +" (" + nombre_colis +" colis)");
			
	           //On ajoute les incidents dans le tableau 
	          for(int i=0;i<liste_chargement.size();i++){
	        	  ajouterColis((Colis)liste_chargement.get(i));
	           
	            }
	         
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	
	}
	
	//Méthode permettant de choisir un ID_modèle de colis en fonction de sa forme et de son modèle
	public int SelectionId(int forme,int modele)
	{
		int temp=0;
		
		switch (forme)
		{
			//Si le colis est carré
			case 0:
				switch(modele)
				{
					case 0: temp = 1;//Modèle 1
					break;
					case 1: temp = 2;//Modèle 2
					break;
					case 2: temp = 3;//Modèle 3
					break;
					case 3: temp = 69;////Modèle peronnalisé
					break;	
				}
				
			break;
			
			// Sile colis est un pavé
			case 1:
				switch(modele)
				{
					case 0: temp = 4;//Modèle 1
					break;
					case 1: temp = 5;//Modèle 2
					break;
					case 2: temp = 6;//Modèle 3
					break;
					case 3: temp = 69;//personnalisé
					break;	
				}
			break;
				
			//Sile colis est cylindrique
			case 2:
				switch(modele)
				{
					case 0: temp = 7;//Modèle 1
					break;
					case 1: temp = 8;//Modèle 2
					break;
					case 2: temp = 9;//Modèle 3
					break;
					case 3: temp = 69;//Modèle personnalisé
					break;	
				}
			break;
		
		}
		
		
		return temp;
	}
	

	//Si le modèle choisi est "personnalisé"  on affiche les dimensions du colis
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
				label_largeur_colis.setText("Diamètre :");
				
				
			}
			else if(num1==0)
			{
				label_profondeur_colis.setVisible(false);
				profondeur.setVisible(false);
				label_largeur_colis.setVisible(false);
				largeur.setVisible(false);
				label_hauteur_colis.setText("Coté :");
				
				
			}
			else
			{
				label_profondeur_colis.setVisible(true);
				profondeur.setVisible(true);
				label_largeur_colis.setVisible(true);
				largeur.setVisible(true);
				label_largeur_colis.setText("Largeur :");
				label_hauteur_colis.setText("Hauteur :");
				
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
		if(source==this.deconnexion){
			dispose();
			Fenetre_login login=new Fenetre_login();
			login.setVisible(true);
		}
		//Si l'utilisateur veut se déloguer
		if (source ==se_deloguer) {
			
			dispose();
			JFrame fen1 = new Fenetre_login();
			fen1.setVisible(true);
		}
		//Si l'utilisateur veut créer une étiquette
		if (source ==creation) {
			
			JFrame fen4 = new Entree_Create_etiquette(code_barre.getText());
			fen4.setVisible(true);
		}
		//Si l'utilisateur veut choisir une personne comme expéditeur ou destinataire
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
				//On vérifie tous les champs
				verif = verifChamps();
				//Si la vérification est Ok
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
					
					//On enregistre l'expéditeur dans la bdd
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
					//Si le modèle n'est pas standard
					if(selectmodelecolis == 69)
					{
						String profondeur_temp= "",largeur_temp="";
						//On crée un nouveau modèle de colis avec les dimensions dans la bdd
						
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
					else // On va chercher les infos sur le modèle de colis existant
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
					col = new Colis(new Integer(0),code_barre.getText(),expediteur,destinataire,utilisateur,new Integer(poids.getText()),new Timestamp(System.currentTimeMillis()),new Integer(fragilite_colis.getSelectedIndex()),modele,entrepot,entrepot,entrepot,"0",modele.calculerVolume());
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
			//Cas d'un colis à modifier
			else if(create ==2)
			{
				//update des données
				boolean verif;
				
				//enregistrer infos dans la Bdd.
				//On vérifie tous les champs
				verif = verifChamps();
				//Si la vérification est Ok
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
					
					//On enregistre l'expéditeur dans la bdd
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
					//Si le modèle n'est pas standard
					if(selectmodelecolis == 69 && modelecolis.getId().intValue() > 9)
					{
						//On crée un nouveau modèle de colis avec les dimensions dans la bdd
						String profondeur_temp= "",largeur_temp="";
						//On crée un nouveau modèle de colis avec les dimensions dans la bdd
						
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
					else // On va chercher les infos sur le modèle de colis existant
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
					Colis col_temp = new Colis(col.getId(),code_barre.getText(),expediteur,destinataire,utilisateur,new Integer(poids.getText()),col.getDate(),new Integer(fragilite_colis.getSelectedIndex()),modele_temp,entrepot,entrepot,entrepot,"0",modele_temp.calculerVolume());
				
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
		
		//Si on veut créer un incident
		if (source == create_incident)
		{
			incident = null;
			JFrame fen3 = new Fenetre_create_incident(col,utilisateur,incident,this,false);
			fen3.setVisible(true);
			
		}
		//Si on veut créer une étiquette
		if (source == create_etiquette)
		{
			JFrame fen4 = new Entree_Create_etiquette(code_barre.getText());
			fen4.setVisible(true);
		}
		//Si on veut annuler le colis en cours
		if (source == annuler)
		{
			contenu=new FenetreType(utilisateur, "images/entree/bg_entrée1.png");
			setContentPane(contenu);
			contenu.setLayout(new FlowLayout());
			getContentPane().setLayout(null);
			create_graphique();
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
			//Si une ligne a bien été sélectionné, on affiche l'incident
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
			//Si une ligne a bien été sélectionné, on affiche l'incident
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
	//Méthode permettant de vérifier les champs 
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
		else if(cp_dest.getText().equals("") || erreurCPdest || cp_dest.getText().length() < 2) setWarning("CP destinataire");
		else if(email_dest.getText().equals("")) setWarning("Email destinataire");
		else if(tel_dest.getText().equals("")) setWarning("Téléphone destinataire");
		else if(nom_exp.getText().equals("")) setWarning("Nom expéditeur");
		else if(prenom_exp.getText().equals("")) setWarning("Prénom expéditeur");
		else if(adresse_exp.getText().equals("")) setWarning("Adresse expéditeur");
		else if(ville_exp.getText().equals("")) setWarning("Ville expéditeur");
		else if(cp_exp.getText().equals("") || erreurCPexp ||cp_exp.getText().length() < 2) setWarning("CP expéditeur");
		else if(email_exp.getText().equals("")) setWarning("Email expéditeur");
		else if(tel_exp.getText().equals("")) setWarning("Téléphone expéditeur");
		else if (modele_colis.getSelectedIndex()== 3 && (hauteur.getText().equals("") || erreurhaut)){
			if (forme_colis.getSelectedIndex()==0)
			{
				setWarning("Coté");
			}
			else setWarning("Hauteur");
		}
		else if (modele_colis.getSelectedIndex()== 3 && (forme_colis.getSelectedIndex() == 1 || forme_colis.getSelectedIndex() == 2) && (largeur.getText().equals("") || erreurlarg)) {
			if (forme_colis.getSelectedIndex()==2)
			{
				setWarning("Diamètre");
			}
			else setWarning("Largeur");
			
		}
		else if (modele_colis.getSelectedIndex()== 3 && forme_colis.getSelectedIndex() == 1 &&(profondeur.getText().equals("") || erreurprof))setWarning("Profondeur");
		else ret = true;
		
		return ret;
	}

	// On affiche un message d'erreur  si un champs est mal renseigné
	private void setWarning(String s){
		JOptionPane.showMessageDialog(this,"Le champs \""+s+"\" est mal renseigné.","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
	
	}
	
	//Méthode permettant de saisir automatiquement les infos sur une personne
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
	
	//private JMenuBar barreMenus;
	//private JMenu fichier,etiquette;
	private JMenuItem se_deloguer,creation;
	private JLabel label_email_dest,label_tel_exp,label_ville_exp,label_adresse_exp,label_cp_exp,label_nom_exp,label_prenom_exp,label_email_exp,label_tel_dest,label_ville_dest,label_adresse_dest,label_cp_dest,label_nom_dest,label_prenom_dest,/*label_liste_incidents,*/label_profondeur_colis,label_hauteur_colis,label_largeur_colis,label_camion,/*label_cam,*/label_date,label_poids,label_dest,label_exp,numero_colis,label_forme_colis,label_modele_colis,label_fragile;
	private JTextField tel_exp,email_exp,ville_exp,adresse_exp,cp_exp,nom_exp,prenom_exp,tel_dest,email_dest,ville_dest,adresse_dest,cp_dest,nom_dest,prenom_dest,hauteur,profondeur,largeur,date_envoie,poids,code_barre;
	private String[] formes={"cube","pavé","cylindre"}, modele={"modèle1","modèle2","modèle3","personalisé"},fragilite={"trés fragile","fragile","pas fragile"};
	private JComboBox forme_colis,modele_colis,fragilite_colis;
	private JButton deconnexion,modif_infos,incident_automatique,select_personne,voir_incident,annuler,create_etiquette,create_incident,valider_colis;
	private JTextArea donnees_dest,donnees_exp;
	//private Container contenu1;//,contenu1 ;
	private FenetreType contenu;
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
	private Font font;
}
