package ihm.preparation;

import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.*;

import donnees.Chargement;
import donnees.Colis;
import accesBDD.AccesBDDChargement;
import accesBDD.AccesBDDColis;


public class Prep_Gerer_chargement extends JFrame implements ActionListener{
	private JTable tableColis;
	private JTable tableChargement;
	private ModeleTable modColis;
	private ModeleTable modChargement;
	private Vector nomColonnes = new Vector();
	private JButton bas = new JButton();
	private JButton haut = new JButton();
	private TableSorter sorter1;
	private TableSorter sorter2;
	private JButton valider = new JButton("Valider");
	private JButton annuler=new JButton("Annuler");
	private Chargement chargement;
	private JLabel labelVolumeChargement,labelVolumeMax;
	private Float volumeChargement, volumeMax;	// Ne sert plus
	private Vector listeColis=null;
	private Container ct;
	
	public Prep_Gerer_chargement(String codeBarreChargement, Integer idDestination, Float volumeMax){
		//Constructeur de la fenetre
		super(" - Preparateur");
		ct = this.getContentPane();
		
		// R�cup�ration de la fen�tre pr�c�dente
		AccesBDDChargement bddChargement=new AccesBDDChargement();
		try{
			this.chargement=bddChargement.rechercher(codeBarreChargement);
		}
		catch(SQLException e){
			
		}
		this.volumeMax=volumeMax;
		this.volumeChargement=this.chargement.getVolChargement().floatValue();
		
		//taille de la fen�tre
		setSize(800,600);
		setBounds(200,100,800,600);
		
		//Declaration du layout
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		//Ajout des fl�ches
		ImageIcon iconeBas = new ImageIcon("images/icones/bas.gif");
		ImageIcon iconeHaut = new ImageIcon("images/icones/haut.gif");
		
		//Insertion des icones dans les boutons
		bas.setIcon(iconeBas);
		haut.setIcon(iconeHaut);
		
		//Ajout du bouton gauche_droite
		 bas.setBounds(325,240,33,29);
	     ct.add(bas);
	     bas.addActionListener(this);
	     
	     //Ajout du bouton droite_gauche
		 haut.setBounds(405,240,33,29);
	     ct.add(haut);
	     haut.addActionListener(this);
	     
	     // Bouton valider
	     valider.setBounds(400,450,80,40);
		 ct.add(valider);
		 valider.addActionListener(this);
		 
		 // Boutin annuler
		 annuler.setBounds(520,450,80,40);
		 ct.add(annuler);
		 annuler.addActionListener(this);
		
		//Ajout des zones de texte
		JLabel labelInfo1= new JLabel("Volume charg� max :");
		labelInfo1.setBounds(80,30,200,20);
		ct.add(labelInfo1);
		this.labelVolumeMax=new JLabel(this.volumeMax.toString());
		this.labelVolumeMax.setBounds(200,30,200,20);
		ct.add(this.labelVolumeMax);
		JLabel labelInfo2= new JLabel("Volume charg� : ");
		labelInfo2.setBounds(320,30,200,20);
		ct.add(labelInfo2);
		this.labelVolumeChargement=new JLabel(this.volumeChargement.toString());
		this.labelVolumeChargement.setBounds(440,30,200,20);
		ct.add(this.labelVolumeChargement);
		
		JLabel txt = new JLabel("Colis libres ");
		txt.setBounds(80,10,200,20);
		ct.add(txt);
		
		JLabel txt1 = new JLabel("Colis charg�s");
		txt1.setBounds(80,280,200,20);
		ct.add(txt1);
		
		
		// Cr�taion des colonnes
        nomColonnes.add("Id");
        nomColonnes.add("Code barre");
        nomColonnes.add("Exp�diteur");
        nomColonnes.add("Destinataire");
        nomColonnes.add("Origine");
        nomColonnes.add("Destination");
        nomColonnes.add("Entrepot en Cours");
        nomColonnes.add("Utilisateur");
        nomColonnes.add("Poids");
        nomColonnes.add("Date d'entr�e");
        nomColonnes.add("Fragilit�");
        nomColonnes.add("Mod�le");
        nomColonnes.add("Mod�le");
        nomColonnes.add("Volume");
        
        Vector donneesColis = new Vector();
        // Recherche des colis pouvant �tre charg�s
		try{
			Vector colisNonCharge=new AccesBDDColis().colisACharger(idDestination.intValue());
			
			// Transformation pour l'affichage dans le tableau
			for(int i=0;i<colisNonCharge.size();i++)
				donneesColis.add(((Colis)colisNonCharge.get(i)).toVector());
			
		}
		catch(SQLException e){
			
		}
		
		// Cr�ation du tableau contenant les colis pouvant �tre charg� pour la destination
        modColis = new ModeleTable(nomColonnes,donneesColis);
		//Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		sorter1 = new TableSorter(modColis);
		// Cr�ation du tableau
		tableColis = new JTable(sorter1);
		// initialisation du Sorter
		sorter1.setTableHeader(tableColis.getTableHeader());
		
		//Aspect du tableau
		tableColis.setAutoCreateColumnsFromModel(true);
		tableColis.setOpaque(false);
		tableColis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// On supprime les colonnes inutiles
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(0));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(1));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(1));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(1));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(1));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(1));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(1));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(4));
		
		//Construction du JScrollPane
		JScrollPane scrollPane1 = new JScrollPane(tableColis);
		tableColis.setPreferredScrollableViewportSize(new Dimension(800,150));
		scrollPane1.setBounds(80,50,600,150);
		scrollPane1.setOpaque(false);
		scrollPane1.getViewport().setOpaque(false);
		getContentPane().add(scrollPane1);
		
    	Vector donneesColisChargement = new Vector();
		// Recherche des colis appartenant au chargement
		try{			
			Vector colisChargement=bddChargement.listerColis(this.chargement.getId());
			
			// Sauvegarde du chargement initial
			this.listeColis=colisChargement;
			
			// Transformation pour l'affichage dans le tableau
			for(int i=0;i<colisChargement.size();i++)
				donneesColisChargement.add(((Colis)colisChargement.get(i)).toVector());
			
		}
		catch(SQLException e){
			
		}
		
		
		
		// Cr�ation du tableau contenant la liste des colis appartenant au chargement
		modChargement = new ModeleTable(nomColonnes,donneesColisChargement);
		// Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		sorter2 = new TableSorter(modChargement);
		// Cr�ation du tableau
		tableChargement = new JTable(sorter2);
		// initialisation du Sorter
		sorter2.setTableHeader(tableChargement.getTableHeader());
			
		//Aspect du tableau
		tableChargement.createDefaultColumnsFromModel();
		tableChargement.setOpaque(false);
		tableChargement.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// On supprime les colonnes inutiles
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(0));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(1));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(1));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(1));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(1));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(1));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(1));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(4));
	
		//Construction du JScrollPane
		JScrollPane scrollPane2 = new JScrollPane(tableChargement);
		tableChargement.setPreferredScrollableViewportSize(new Dimension(600,150));
		scrollPane2.setBounds(80,300,600,150);
		scrollPane2.setOpaque(false);
		scrollPane2.getViewport().setOpaque(false);
		getContentPane().add(scrollPane2);
	}
	
	public void actionPerformed(ActionEvent ev) {
		int ligneActive;
		Object source = ev.getSource();
		
		// Action li�e au bouton gauche_droite
		if(source == bas){
			// On r�cup�re le num�ro de la ligne s�lectionn�e
			ligneActive = tableColis.getSelectedRow();
			
			// Si une ligne est effectivement s�lectionn�e, on peut la modifier
			if(ligneActive != -1){
				// On r�cup�re les donn�es de la ligne du tableau
				Vector vec = (Vector) modColis.getRow(ligneActive);
				//On ajoute dans le tableau repr�santant le chargement
				modChargement.addRow(vec);
				modChargement.fireTableDataChanged();
				//On supprime dans le tableau repr�sentant les colis disponibles
				modColis.removeRow(ligneActive);
				modColis.fireTableDataChanged();
				//Mise � jour des tableaux
				tableColis.updateUI();
				tableChargement.updateUI();
				
				// On remet � jour le volume
				this.ct.remove(this.labelVolumeChargement);
				this.labelVolumeChargement=new JLabel(this.chargement.ajouterVolumeColis(new Float(new Colis(vec).getVolume())));
				this.labelVolumeChargement.setBounds(440,30,200,20);
				ct.add(this.labelVolumeChargement);
				this.ct.repaint();
			}
			else
				JOptionPane.showMessageDialog(this,"Veuillez s�lectionner un camion","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
		}
		//Action li�e au bouton droite_gauche
		else if(source == haut){
			// On r�cup�re le num�ro de la ligne s�lectionn�e
			ligneActive = tableChargement.getSelectedRow();
				
			// Si une ligne est effectivement s�lectionn�e, on peut la modifier
			if(ligneActive != -1){
				//On r�cup�re les donn�es de la ligne du tableau
				Vector vec = (Vector) modChargement.getRow(ligneActive);
				//On ajoute au premier tableau
				modColis.addRow(vec);
				modColis.fireTableDataChanged();
				//On supprime du deuxi�me
				modChargement.removeRow(ligneActive);
				modChargement.fireTableDataChanged();
				//Mise � jour des tableaux
				tableColis.updateUI();
				tableChargement.updateUI();
				
				// On remet � jour le volume
				this.ct.remove(this.labelVolumeChargement);
				this.labelVolumeChargement=new JLabel(this.chargement.soustraireVolumeColis(new Float(new Colis(vec).getVolume())));
				this.labelVolumeChargement.setBounds(440,30,200,20);
				ct.add(this.labelVolumeChargement);
				this.ct.repaint();
			}
		}
		else if(source == valider){
			Vector nouvCharg=new Vector();
			AccesBDDChargement bddChargement=new AccesBDDChargement();
			
			// Cr�ation de la liste de colis du nouveau chargement
			for(int i=0;1<this.modChargement.getRowCount();i++)
				nouvCharg.add(new Colis((Vector)this.modChargement.getRow(i)));
			
			// Modifications des infos concernant le chargement
			this.chargement.setDate(new Timestamp(System.currentTimeMillis()));
			this.chargement.validerEtat();
			
			// Comparaison de l'ancien et du nouveau chargement afin de d�tecter les colis supprim�s
			// On supprime dans l'ancienne liste les colis pr�sents dansla nouvelle -> Apparition des colis supprim� dans l'ancien chargement
			for(int i=0;i<nouvCharg.size();i++){
				if(this.listeColis.contains(nouvCharg.get(i))==true){
					this.listeColis.removeElement(nouvCharg.get(i));
					nouvCharg.remove(i);
				}
			}
			
			// Mise � jour BDD
			try{
				// Suppression des colis dans le chargement
				for(int i=0;i<this.listeColis.size();i++)
					bddChargement.supprimer_colis((Colis)this.listeColis.get(i),this.chargement);
				
				// Ajout des colis dans le chargement
				bddChargement.AjouterColis(this.chargement, nouvCharg);
				
				// Mise � jour des infos concernant le chargement
				bddChargement.valider(this.chargement);
			}
			catch(SQLException e){
				
			}
			dispose();
		}
		else if(source==annuler){
			dispose();
		}
	}
}

