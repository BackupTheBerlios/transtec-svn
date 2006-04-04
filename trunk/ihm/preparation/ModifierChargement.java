package ihm.preparation;

import ihm.Bouton;
import ihm.FenetreType;
import ihm.FenetreWarning;
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
import donnees.Utilisateur;
import accesBDD.AccesBDDChargement;
import accesBDD.AccesBDDColis;

public class ModifierChargement extends JFrame implements ActionListener{
	private JTable tableColis;
	private JTable tableChargement;
	private ModeleTable modColis;
	private ModeleTable modChargement;
	private Vector nomColonnes = new Vector();
	private TableSorter sorter1;
	private TableSorter sorter2;
	private Chargement chargement;
	private JLabel labelVolumeChargement,labelVolumeMax;
	private Float volumeMax;
	private Vector listeColis=null;
	private FenetreType fenetre;
	private Bouton ajouter, retirer, valider, annuler;
	private Utilisateur utilisateur;
	
	public ModifierChargement(Utilisateur utilisateur, Integer idChargement, Integer idDestination, Float volumeMax){
		// Cr�ation graphique de la fen�tre
		setTitle("Modification du chargement");
		setSize(1024,768);
		setUndecorated(true);
		fenetre=new FenetreType(utilisateur, "images/preparation/fenetre_gererBackground.png");
		setContentPane(fenetre);
		fenetre.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Ajout des bouton sur la fen�tre
		this.ajouter=new Bouton("images/icones/ajouter_haut.png","images/icones/ajouter_haut_inv.png");
		this.ajouter.setBounds(810, 270, 109, 44);
		this.fenetre.add(this.ajouter);
		this.ajouter.addActionListener(this);
		this.retirer=new Bouton("images/icones/retirer_bas.png","images/icones/retirer_bas_inv.png");
		this.retirer.setBounds(810, 329, 109, 44);
		this.fenetre.add(this.retirer);
		this.retirer.addActionListener(this);
		this.valider=new Bouton("images/icones/valider.png","images/icones/valider_inv.png");
		this.valider.setBounds(810, 485, 165, 41);
		this.fenetre.add(this.valider);
		this.valider.addActionListener(this);
		this.annuler=new Bouton("images/icones/annuler.png","images/icones/annuler_inv.png");
		this.annuler.setBounds(810, 543, 165, 41);
		this.fenetre.add(this.annuler);
		this.annuler.addActionListener(this);
		
		// Cr�ation d'un police pour l'affichage des diff�rents textes
		Font font=new Font("Verdana", Font.BOLD, 12);
		
		// On m�morise
		this.utilisateur=utilisateur;
		
		// Recherche du chargement
		AccesBDDChargement bddChargement=new AccesBDDChargement();
		try{
			this.chargement=bddChargement.rechercher(idChargement);
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(this,e,"Erreur BDD",JOptionPane.ERROR_MESSAGE);
		}
		this.volumeMax=volumeMax;
		//this.volumeChargement=new Float(this.chargement.getVolChargement().floatValue());
		
		// Affichage du chargement trait�
		JLabel labelChargement=new JLabel("Chargement n�"+this.chargement.getCodeBarre());
		labelChargement.setBounds(60, 227, 200, 20);
		labelChargement.setFont(font);
		this.fenetre.add(labelChargement);
		// Affichage des volumes
		JLabel labelInfo1= new JLabel("Volume charg� max :");
		labelInfo1.setBounds(380,227,200,20);
		fenetre.add(labelInfo1);
		this.labelVolumeMax=new JLabel(this.volumeMax.toString());
		this.labelVolumeMax.setBounds(510,227,200,20);
		fenetre.add(this.labelVolumeMax);
		JLabel labelInfo2= new JLabel("Volume charg� : ");
		labelInfo2.setBounds(570,227,200,20);
		fenetre.add(labelInfo2);
		this.labelVolumeChargement=new JLabel(this.chargement.getVolChargement().toString());
		this.labelVolumeChargement.setBounds(670,227,200,20);
		fenetre.add(this.labelVolumeChargement);
		
		// Cr�taion des colonnes
		nomColonnes.add("Id");
		nomColonnes.add("Code Barre");
		nomColonnes.add("expediteur");
		nomColonnes.add("destinataire");
		nomColonnes.add("origine");
		nomColonnes.add("destination");
		nomColonnes.add("entrepot en cours");
		nomColonnes.add("utilisateur");
		nomColonnes.add("Poids");
		nomColonnes.add("Date Envoi");
		nomColonnes.add("fragilite");
		nomColonnes.add("Modele");
		nomColonnes.add("forme");
		nomColonnes.add("modele");
		nomColonnes.add("valeur_declaree");
		nomColonnes.add("Volume");
		nomColonnes.add("numeroDsCharg");
        
        Vector donneesColis = new Vector();
        // Recherche des colis pouvant �tre charg�s
		try{
			Vector colisNonCharge=new AccesBDDColis().colisACharger(idDestination);
			
			// Transformation pour l'affichage dans le tableau
			for(int i=0;i<colisNonCharge.size();i++)
				donneesColis.add(((Colis)colisNonCharge.get(i)).toVector());
			
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(this,e,"Erreur BDD",JOptionPane.ERROR_MESSAGE);
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
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(0));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(0));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(0));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(0));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(0));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(0));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(0));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(3));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(3));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(3));
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(5));
		
		//Construction du JScrollPane
		JScrollPane scrollPane1 = new JScrollPane(tableColis);
		tableColis.setPreferredScrollableViewportSize(new Dimension(800,150));
		scrollPane1.setBounds(52,504,729,222);
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
			JOptionPane.showMessageDialog(this,e,"Erreur BDD",JOptionPane.ERROR_MESSAGE);
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
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(0));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(0));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(0));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(0));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(0));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(0));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(0));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(3));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(3));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(3));
		tableChargement.removeColumn(tableChargement.getColumnModel().getColumn(5));
	
		//Construction du JScrollPane
		JScrollPane scrollPane2 = new JScrollPane(tableChargement);
		tableChargement.setPreferredScrollableViewportSize(new Dimension(600,150));
		scrollPane2.setBounds(52,253,729,209);
		scrollPane2.setOpaque(false);
		scrollPane2.getViewport().setOpaque(false);
		getContentPane().add(scrollPane2);
		
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ev) {
		int ligneActive;
		Object source = ev.getSource();
		
		// Action li�e au bouton gauche_droite
		if(source==this.ajouter){
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
				this.labelVolumeChargement.setText(this.chargement.ajouterVolumeColis(new Colis(vec).getVolume()));
				this.labelVolumeChargement.updateUI();

			}
			else
				new FenetreWarning("Veuillez s�lectionner un colis").setVisible(true);
		}
		//Action li�e au bouton droite_gauche
		else if(source==this.retirer){
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
				this.labelVolumeChargement.setText(this.chargement.soustraireVolumeColis(new Colis(vec).getVolume()));
				this.labelVolumeChargement.updateUI();
			}
			else
				new FenetreWarning("Veuillez s�lectionner un colis").setVisible(true);
		}
		else if(source==this.valider){
			new FenetrePrincipale(this.utilisateur).setVisible(true);
			Vector nouvCharg=new Vector();
			AccesBDDChargement bddChargement=new AccesBDDChargement();
			
			// Cr�ation de la liste de colis du nouveau chargement
			for(int i=0;i<this.modChargement.getRowCount();i++)
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
			}
			catch(SQLException e){
				JOptionPane.showMessageDialog(this,e,"Erreur BDD",JOptionPane.ERROR_MESSAGE);
			}
			dispose();
		}
		
		// Annulation de l'op�ration de modification du chargement
		else if(source==this.annuler){
			new FenetrePrincipale(this.utilisateur).setVisible(true);
			dispose();
		}
	}
}

