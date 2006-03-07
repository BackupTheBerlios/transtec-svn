package ihm.preparation;

import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;

import donnees.Chargement;
import donnees.Utilisateur;
import donnees.Camion;
import accesBDD.AccesBDDChargement;
import accesBDD.AccesBDDColis;


public class Prep_Gerer_chargement extends JFrame implements ActionListener{
	
	private JTable tab1;
	private JTable tableChargement;
	private ModeleTable modeleColis1;
	private ModeleTable modChargement;
	private Vector nomColonnes = new Vector();
	private Vector donnees1 = new Vector();
	private Vector donneesColisChargement = new Vector();
	private int ligneActive;
	private JButton gauche_droite = new JButton();
	private JButton droite_gauche = new JButton();
	private Camion cam;
	private TableSorter sorter1;
	private TableSorter sorter2;
	private JButton valider = new JButton("Valider");
	
	public Prep_Gerer_chargement(String codeBarreChargement){
		//Constructeur de la fenetre
		super(" - Preparateur");
		Container ct = this.getContentPane();
		
		//Création du menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		
		//taille de la fenêtre
		setSize(800,600);
		setBounds(200,100,800,600);
		
		//Declaration du layout
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		//Ajout des fleches
		ImageIcon flech_gauche_droite = new ImageIcon("images/icones/bas.gif");
		ImageIcon flech_droite_gauche = new ImageIcon("images/icones/haut.gif");
		
		//Insertion des icones dans les boutons
		gauche_droite.setIcon(flech_gauche_droite);
		droite_gauche.setIcon(flech_droite_gauche);
		
		//Ajout du bouton gauche_droite
		 gauche_droite.setBounds(325,240,33,29);
	     ct.add(gauche_droite);
	     gauche_droite.addActionListener(this);
	     
	     //Ajout du bouton droite_gauche
		 droite_gauche.setBounds(405,240,33,29);
	     ct.add(droite_gauche);
	     droite_gauche.addActionListener(this);
	     
	     valider.setBounds(520,450,100,68);
		 ct.add(valider);
		 valider.addActionListener(this);
		
		//Ajout des zones de texte
		JLabel info_volume1= new JLabel("Volume disponible :");
		info_volume1.setBounds(80,30,200,20);
		ct.add(info_volume1);
		
		JLabel txt = new JLabel("Colis présents dans le camion ");
		txt.setBounds(80,10,200,20);
		ct.add(txt);
		
		JLabel txt1 = new JLabel("Colis libres");
		txt1.setBounds(80,280,200,20);
		ct.add(txt1);
		
		//Création des deux tableaux
		/*v.add(idExpediteur);
		v.add(idDestinataire);
		v.add(idUtilisateur);
		v.add(poids);
		v.add(dateDepot);
		v.add(fragilite);
		v.add(valeurDeclaree);*/
		//Création de la première ligne
        nomColonnes.add("Numéro Colis");
        nomColonnes.add("Volume (m3)");
        nomColonnes.add("Poids (kg)");
        nomColonnes.add("Fragilité");

//**********************************************APPEL A LA BDD*****************************************
//Pour un camion donné, il faut afficher tous les colis présents dans celui ci dans un premier tableau
//Création des lignes objets
        /*Timestamp date=new Timestamp(10);
        Colis c=new Colis("454646416", 
				new Integer(0), 
				new Integer(1),
				new Integer(1),
				"52.6", 
				new Timestamp(12-12-1978), 
				new Integer(2),
				new Integer(1), 
				new Integer(1),
				"100");
		c.setId(new Integer(123));
		Vector v = new Vector(c.toVector());
		//v.add(0,c.getId());
		donnees1.addElement(v);
		
		c = new Colis(new Integer(0),"5345343",new Integer(1),new Integer(1),"18",date,"150",new Integer(1),"Villejuif");
		c.setId(new Integer(127));
		v = new Vector(c.toVector());
		//v.add(0,c.getId());
		donnees1.addElement(v);*///------>>>PQ
        AccesBDDColis lister=new AccesBDDColis();
        
       
//*********************************************************************************************
		
		//Création du premier tableau (colis dans le camion)
		modeleColis1 = new ModeleTable(nomColonnes,donnees1);
		//Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter1 = new TableSorter(modeleColis1);
		// Création du tableau
		tab1 = new JTable(sorter1);
		// initialisation du Sorter
		sorter1.setTableHeader(tab1.getTableHeader());
		
		//Aspect du tableau
		tab1.setAutoCreateColumnsFromModel(true);
		tab1.setOpaque(false);
		tab1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Construction du JScrollPane
		JScrollPane scrollPane1 = new JScrollPane(tab1);
		tab1.setPreferredScrollableViewportSize(new Dimension(800,150));
		scrollPane1.setBounds(80,50,600,150);
		scrollPane1.setOpaque(false);
		scrollPane1.getViewport().setOpaque(false);
		getContentPane().add(scrollPane1);
		
		// Recherche du chargement
		try{
			AccesBDDChargement bddChargement=new AccesBDDChargement();
			Chargement chargement=bddChargement.rechercher(codeBarreChargement);
			Vector colisChargement=bddChargement.listerColis(chargement.getId());
			
			// Transformation pour l'affichage dans le tableau
			for(int i=0;i<listeColisChargement.size();i++){
				Colis courant=colisChargement.get(i);
				donneesColisChargement.add(courant.)
			}
		}
		catch(SQLException e){
			
		}
		
		//Création du deuxième tableau (colis libres)
		modChargement = new ModeleTable(nomColonnes,donneesColisChargement);
		// Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter2 = new TableSorter(modChargement);
		// Création du tableau
		tableChargement = new JTable(sorter2);
		// initialisation du Sorter
		sorter2.setTableHeader(tableChargement.getTableHeader());
			
		//Aspect du tableau
		tableChargement.createDefaultColumnsFromModel();
		tableChargement.setOpaque(false);
		tableChargement.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Construction du JScrollPane
		JScrollPane scrollPane2 = new JScrollPane(tableChargement);
		tableChargement.setPreferredScrollableViewportSize(new Dimension(600,150));
		scrollPane2.setBounds(80,300,600,150);
		scrollPane2.setOpaque(false);
		scrollPane2.getViewport().setOpaque(false);
		getContentPane().add(scrollPane2);
	}
	
	public void actionPerformed(ActionEvent ev) {
		
		Object source = ev.getSource();
		
		// Action liée au bouton gauche_droite
		if(source == gauche_droite){
			// On récupère le numéro de la ligne sélectionnée
			ligneActive = tab1.getSelectedRow();
			
			// Si une ligne est effectivement sélectionnée, on peut la modifier
			if(ligneActive != -1){
				// On récupère les données de la ligne du tableau
				Vector vec = (Vector) modeleColis1.getRow(ligneActive);
				//On ajoute au deuxième tableau
				modChargement.addRow(vec);
				modChargement.fireTableDataChanged();
				//On supprime du premier
				modeleColis1.removeRow(ligneActive);
				modeleColis1.fireTableDataChanged();
				//Mise à jour des tableaux
				tab1.updateUI();
				tableChargement.updateUI();
			}
		}
		//Action liée au bouton droite_gauche
		else if(source == droite_gauche){
			// On récupère le numéro de la ligne sélectionnée
			ligneActive = tableChargement.getSelectedRow();
				
			// Si une ligne est effectivement sélectionnée, on peut la modifier
			if(ligneActive != -1){
				//On récupère les données de la ligne du tableau
				Vector vec = (Vector) modChargement.getRow(ligneActive);
				//On ajoute au premier tableau
				modeleColis1.addRow(vec);
				modeleColis1.fireTableDataChanged();
				//On supprime du deuxième
				modChargement.removeRow(ligneActive);
				modChargement.fireTableDataChanged();
				//Mise à jour des tableaux
				tab1.updateUI();
				tableChargement.updateUI();
			}
		}
		else if(source == valider){
			// Ecriture dans la BDD des colis, on les associent à un chargement //
			/*Timestamp date=new Timestamp(12-12-06);
			Chargement nouvCharg=new Chargement(cam.getId(), new Integer(donnees1.size()), 12, new Integer(2), date);
			try{
				 AccesBDDChargement bddCharg=new AccesBDDChargement();
				 AccesBDDColis mod=new AccesBDDColis();
					bddCharg.ajouter(nouvCharg);
				    for(int i=0;i<modeleColis1.getRowCount();i++){
				    	Vector v=(Vector)modeleColis1.getRow(i);
				    	Colis c=new Colis(v);
				    	c.setLieu("C-"+nouvCharg.getId());
				    	mod.modifier(c);
				    	//donnees2.addElement(((Colis)listeObj.get(i)).toVector());
		            }
		        }
		        catch(Exception e){
		        	System.out.println(e.getMessage());
		        }*/
		        dispose();
		}
	}
}

