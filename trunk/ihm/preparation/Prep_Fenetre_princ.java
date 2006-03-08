package ihm.preparation;

import ihm.Fenetre_login;
import ihm.ModeleTable;
import ihm.FenetreType;
import ihm.TableSorter;
import ihm.Bouton;


import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;

import accesBDD.AccesBDDCamion;
import accesBDD.AccesBDDEntrepot;

import donnees.Utilisateur;

/*
 * Classe permettant d'afficher la fen�tre principale de pr�paration propre � chaque pr�parateur
 */

public class Prep_Fenetre_princ extends JFrame implements ActionListener, ItemListener{
	private JLabel labelVolume;	// Volume de la destination	
	private JLabel labelCharge;	// Volume d�j� charg� pour la destination
	private Vector colonnesTable=new Vector();	// Colonnes du tableau de camions
	private Vector tableData = new Vector();	// Donn�es pour remplir le camion
	private ModeleTable tableMod;	// Mod�le de tableau de camions
	private JTable table;	// Tableau de camions
	private TableSorter tableSorter;	// Ordonnancement pour le tableau de camions
	private ListeDonneesPrep listeDonneesPrep;	// Liste associ�e � ce pr�parateur
	private DonneesPrep selectionnee;
	private Utilisateur utilisateur;
	private FenetreType contenu;	// Container des �l�ments d'affichage
	private Bouton deconnexion, creerChargement, gererChargement, genererPlan, imprimerEtiquette, incident;	// Boutons du menu
	private JComboBox destinations;	// Liste des destinations
	
	public Prep_Fenetre_princ(Utilisateur utilisateur){
		// Cr�ation graphique de la fen�tre
		setTitle("Pr�paration");
		setSize(1024,768);
		setUndecorated(true);
		contenu=new FenetreType(utilisateur, "images/preparation/fenetre_princBackground.png");
		setContentPane(contenu);
		contenu.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Ajout des bouton sur la fen�tre
		this.deconnexion=new Bouton("images/icones/deconnexion.png","images/icones/deconnexion.png");
		this.deconnexion.setBounds(866, 50, 98, 17);
		this.contenu.add(this.deconnexion);
		this.deconnexion.addActionListener(this);
		this.creerChargement=new Bouton("images/icones/creerChargement.png","images/icones/creerChargement.png");
		this.creerChargement.setBounds(802, 270, 139, 48);
		this.contenu.add(this.creerChargement);
		this.creerChargement.addActionListener(this);
		this.gererChargement=new Bouton("images/icones/gererChargement.png","images/icones/gererChargement.png");
		this.gererChargement.setBounds(802, 338, 143, 45);
		this.contenu.add(this.gererChargement);
		this.gererChargement.addActionListener(this);
		this.genererPlan=new Bouton("images/icones/genererPlan.png","images/icones/genererPlan.png");
		this.genererPlan.setBounds(802, 403, 158, 29);
		this.contenu.add(this.genererPlan);
		this.gererChargement.addActionListener(this);
		this.imprimerEtiquette=new Bouton("images/icones/imprimerEtiquette.png","images/icones/imprimerEtiquette.png");
		this.imprimerEtiquette.setBounds(802, 452, 121, 40);
		this.contenu.add(this.imprimerEtiquette);
		this.imprimerEtiquette.addActionListener(this);
		this.incident=new Bouton("images/icones/incident.png","images/icones/incident.png");
		this.incident.setBounds(802, 512, 162, 42);
		this.contenu.add(this.incident);
		this.incident.addActionListener(this);
		
		// On garde en m�moire l'utilisateur
		this.utilisateur=utilisateur;
		
		// Recherche des informations propre � l'utilisateur dans la BDD
		try{
			listeDonneesPrep=new ListeDonneesPrep(this.utilisateur);
		}
		catch(SQLException e){
			
		}
		
		// Cr�ation de la police pour les affichages de texte
		Font font=new Font("Verdana", Font.BOLD, 12);
		
		// Ajout des champs dans la fen�tre
		destinations = new JComboBox(listeDonneesPrep.combo());
		destinations.setEditable(false);
		destinations.setBounds(60,270,200,20);
		contenu.add(destinations);
		destinations.addItemListener(this);
		// Affichage des zones textes
		JLabel labelVolume = new JLabel("Volume");
		labelVolume.setBounds(60,305,100,20);
		labelVolume.setFont(font);
		contenu.add(labelVolume);
		JLabel labelCharge = new JLabel("Charg�");
		labelCharge.setBounds(60,340,100,20);
		labelCharge.setFont(font);
		contenu.add(labelCharge);	
		
		
		// On rempli les variable dynamique et on l'ajoute au container
		if(listeDonneesPrep.getListe()!=null){
			// Premi�re destination
			this.selectionnee=(DonneesPrep)listeDonneesPrep.getListe().get(0);
			
			// Affichage du volume
			this.labelVolume = new JLabel(this.selectionnee.getVolume().toString());
			this.labelVolume.setBounds(150,305,100,20);
			this.labelVolume.setFont(font);
			contenu.add(this.labelVolume);
			
			// Affichage du volume d�j� charg�
			this.labelCharge = new JLabel(this.selectionnee.getCharge().toString());	
			this.labelCharge.setBounds(150,340,100,20);
			this.labelCharge.setFont(font);
			contenu.add(this.labelCharge);
			
			// On remplit les lignes du tableau
			tableData=this.selectionnee.getListeCamionChargement();
		}
		
		// Inititialisation des colonnes du tableau de camions
		colonnesTable.add("idCamion");
		colonnesTable.add("Immatriculation");
		colonnesTable.add("Disponibilit�");
		colonnesTable.add("Largeur");
		colonnesTable.add("Hauteur");
		colonnesTable.add("Profondeur");
		colonnesTable.add("Volume");
		colonnesTable.add("Origine");
		colonnesTable.add("Destination");
		colonnesTable.add("Volume � charger");
		colonnesTable.add("Chargement en cours");
		
		
		//Comportement lors de la fermeture
		/*WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		};
		addWindowListener(l);	*/	
		
		
		// Cr�ation du tableau de camions
		tableMod = new ModeleTable(colonnesTable,tableData);
		// Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		tableSorter = new TableSorter(tableMod);
		// Cr�ation du tableau
		table = new JTable(tableSorter);
		// initialisation du Sorter
		tableSorter.setTableHeader(table.getTableHeader());
     
		//Aspect du tableau
		table.setAutoCreateColumnsFromModel(true);
		table.setOpaque(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Suppression de certaines colonnes
		table.removeColumn(table.getColumnModel().getColumn(0));
		table.removeColumn(table.getColumnModel().getColumn(1));
		table.removeColumn(table.getColumnModel().getColumn(1));
		table.removeColumn(table.getColumnModel().getColumn(1));
		table.removeColumn(table.getColumnModel().getColumn(1));
		table.removeColumn(table.getColumnModel().getColumn(1));
		table.removeColumn(table.getColumnModel().getColumn(1));
		table.removeColumn(table.getColumnModel().getColumn(1));
		
		//Construction du JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension(300,150));
		scrollPane.setBounds(48, 372, 737, 353);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		getContentPane().add(scrollPane);
		
		// Fin de cr�ation du tableau

		setVisible(true);
	}
	


	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		// On revient � la fen�tre de login
		if(source==this.deconnexion){
			dispose();
			Fenetre_login login=new Fenetre_login();
			login.setVisible(true);
		}
		else{
			int ligneActive = table.getSelectedRow();
			// Cr�ation d'un nouveau chargement pour la destination selectionn�e
			if(ligneActive==-1)
				JOptionPane.showMessageDialog(this,"Veuillez s�lectionner un camion","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
			else{
				if(source==this.creerChargement){
					dispose();
					try{
						Prep_Creer_chargement fen1 = new Prep_Creer_chargement(this.utilisateur, 
								this.selectionnee.getDestination(),
								new AccesBDDCamion().rechercher((Integer)((Vector)tableMod.getRow(ligneActive)).get(0)));
						fen1.setVisible(true);
					}
					catch(SQLException SQLE){
						
					}
				}
				
				// Modification d'un ancien chargement
				else if(source==this.gererChargement) {
					//On r�cup�re les donn�es de la ligne du tableau
					Vector cVect = (Vector) tableMod.getRow(ligneActive);
					//dispose();
	//				ATTENTION:On passe un vecteur comme argument et pas un objet camion
					//Prep_Gerer_chargement fen1 = new Prep_Gerer_chargement("987654321", 7, this.selectionnee.getVolume());
					//fen1.setVisible(true);
				}
				
				// Cr�ation du plan de chargement
				else if(source==this.genererPlan){
					Prep_Plan_chargement plan = new Prep_Plan_chargement();
					plan.setVisible(true);
				}
				
				// Imprimer une �tiquette
				else if(source==this.imprimerEtiquette)
						JOptionPane.showMessageDialog(this,"L'impression a �t� lanc�e","Message de confirmation",JOptionPane.YES_NO_CANCEL_OPTION);
				
				// Afficher les incidents
				else if(source==this.incident){
					Prep_Consulter_incident cons = new Prep_Consulter_incident();
					cons.setVisible(true);
				}
			}
		}
	}



	public void itemStateChanged(ItemEvent arg0) {
		// Chargement du volume pour la destination selectionn�e
		selectionnee=listeDonneesPrep.exists((String)destinations.getSelectedItem());
		
		// On r�affiche les volumes
		contenu.remove(this.labelVolume);
		this.labelVolume=new JLabel(selectionnee.getVolume().toString());
		this.labelVolume.setBounds(150,305,100,20);
		contenu.add(this.labelVolume);
		
		contenu.remove(this.labelCharge);
		this.labelCharge=new JLabel(selectionnee.getCharge().toString());
		this.labelCharge.setBounds(150,340,100,20);
		contenu.add(this.labelCharge);
		
		// Mise � jour du tableau
		for(int i=0;i<tableMod.getRowCount();i++)
			tableMod.removeRow(i);	
		for(int i=0;i<selectionnee.getListeCamionChargement().size();i++)
			tableMod.addRow(selectionnee.getListeCamionChargement().get(i));
		tableMod.fireTableDataChanged();
		table.updateUI();
		
		// On met � jour le container
		contenu.repaint();
		
		
	}
}