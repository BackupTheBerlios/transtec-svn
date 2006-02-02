package ihm.preparation;

import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.*;
import donnees.Utilisateur;
import donnees.Camion;
import donnees.Colis;
import donnees.Chargement;
import accesBDD.AccesBDDColis;
import accesBDD.AccesBDDChargement;
import java.sql.SQLException;


public class Prep_Gerer_chargement extends JFrame implements ActionListener{
	
	private JTable tab1;
	private JTable tab2;
	private ModeleTable modeleColis1;
	private ModeleTable modeleColis2;
	private Vector nomColonnes = new Vector();
	private Vector donnees1 = new Vector();
	private Vector donnees2 = new Vector();
	private int ligneActive;
	private JButton gauche_droite = new JButton();
	private JButton droite_gauche = new JButton();
	private Camion cam;
	private TableSorter sorter1;
	private TableSorter sorter2;
	private JButton valider = new JButton("Valider");
	
	public Prep_Gerer_chargement(Vector cVect, Utilisateur utilisateur){
		
		//Constructeur de la fenetre
		super(" - Preparateur");
		Container ct = this.getContentPane();
		


		
		//D�claration d'un nouvel objet Camion permettant d'utiliser le vecteur entr� en parametre
		
		cam = new Camion(cVect);
	
		////System.out.println("le camion " + cam.)
		
		//Cr�ation du menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		
		//taille de la fen�tre
		setSize(800,600);
		setBounds(200,100,800,600);
		
		//Declaration du layout
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		//Ajout des fleches
		ImageIcon flech_gauche_droite = new ImageIcon("flech_gauche_droite.gif");
		ImageIcon flech_droite_gauche = new ImageIcon("flech_droite_gauche.gif");
		
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
		
		JLabel txt = new JLabel("Colis pr�sents dans le camion ");
		txt.setBounds(80,10,200,20);
		ct.add(txt);
		
		JLabel txt1 = new JLabel("Colis libres");
		txt1.setBounds(80,280,200,20);
		ct.add(txt1);
		
		//Cr�ation des deux tableaux
		/*v.add(idExpediteur);
		v.add(idDestinataire);
		v.add(idUtilisateur);
		v.add(poids);
		v.add(dateDepot);
		v.add(fragilite);
		v.add(valeurDeclaree);*/
		//Cr�ation de la premi�re ligne
        nomColonnes.add("Num�ro Colis");
        nomColonnes.add("Volume (m3)");
        nomColonnes.add("Poids (kg)");
        nomColonnes.add("Fragilit�");

//**********************************************APPEL A LA BDD*****************************************
//Pour un camion donn�, il faut afficher tous les colis pr�sents dans celui ci dans un premier tableau
//Cr�ation des lignes objets
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
		
		//Cr�ation du premier tableau (colis dans le camion)
		modeleColis1 = new ModeleTable(nomColonnes,donnees1);
		//Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		sorter1 = new TableSorter(modeleColis1);
		// Cr�ation du tableau
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
		
//**********************************************APPEL A LA BDD*****************************************
//On cherche dans la BDD tous les colis pour une destination qui ne possede pas de chargement		
		 //Cr�ation des lignes objets
		/*try{
			AccesBDDColis bddColis=new AccesBDDColis();
			Vector v=bddColis.listerDest(1);
		}
		catch(SQLException e){
			
		}*/
		
		 try{
			    Vector listeObj=lister.listerDest(cam.getDestination().getId().intValue());
			    for(int i=0;i<listeObj.size();i++){
			    	donnees2.addElement(((Colis)listeObj.get(i)).toVector());
	            }
	        }
	        catch(SQLException e){
	        	
	        }
	        
		
//********************************************************************************************************	
		//Cr�ation du deuxi�me tableau (colis libres)
		 modeleColis2 = new ModeleTable(nomColonnes,donnees2);
		// Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		sorter2 = new TableSorter(modeleColis2);
		// Cr�ation du tableau
		tab2 = new JTable(sorter2);
		// initialisation du Sorter
		sorter2.setTableHeader(tab2.getTableHeader());
			
		//Aspect du tableau
		tab2.createDefaultColumnsFromModel();
		tab2.setOpaque(false);
		tab2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Construction du JScrollPane
		JScrollPane scrollPane2 = new JScrollPane(tab2);
		tab2.setPreferredScrollableViewportSize(new Dimension(600,150));
		scrollPane2.setBounds(80,300,600,150);
		scrollPane2.setOpaque(false);
		scrollPane2.getViewport().setOpaque(false);
		getContentPane().add(scrollPane2);
	}
	
	public void actionPerformed(ActionEvent ev) {
		
		Object source = ev.getSource();
		
		// Action li�e au bouton gauche_droite
		if(source == gauche_droite){
			// On r�cup�re le num�ro de la ligne s�lectionn�e
			ligneActive = tab1.getSelectedRow();
			
			// Si une ligne est effectivement s�lectionn�e, on peut la modifier
			if(ligneActive != -1){
				// On r�cup�re les donn�es de la ligne du tableau
				Vector vec = (Vector) modeleColis1.getRow(ligneActive);
				//On ajoute au deuxi�me tableau
				modeleColis2.addRow(vec);
				modeleColis2.fireTableDataChanged();
				//On supprime du premier
				modeleColis1.removeRow(ligneActive);
				modeleColis1.fireTableDataChanged();
				//Mise � jour des tableaux
				tab1.updateUI();
				tab2.updateUI();
			}
		}
		//Action li�e au bouton droite_gauche
		else if(source == droite_gauche){
			// On r�cup�re le num�ro de la ligne s�lectionn�e
			ligneActive = tab2.getSelectedRow();
				
			// Si une ligne est effectivement s�lectionn�e, on peut la modifier
			if(ligneActive != -1){
				//On r�cup�re les donn�es de la ligne du tableau
				Vector vec = (Vector) modeleColis2.getRow(ligneActive);
				//On ajoute au premier tableau
				modeleColis1.addRow(vec);
				modeleColis1.fireTableDataChanged();
				//On supprime du deuxi�me
				modeleColis2.removeRow(ligneActive);
				modeleColis2.fireTableDataChanged();
				//Mise � jour des tableaux
				tab1.updateUI();
				tab2.updateUI();
			}
		}
		else if(source == valider){
			// Ecriture dans la BDD des colis, on les associent � un chargement //
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

