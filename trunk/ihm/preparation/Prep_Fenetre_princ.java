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

import donnees.Utilisateur;

/*
 * Classe permettant d'afficher la fen�tre principale de pr�paration propre � chaque pr�parateur
 */

public class Prep_Fenetre_princ extends JFrame implements ActionListener, ItemListener, MouseListener{
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
	private FenetreType fenetre;	// Container des �l�ments d'affichage
	private Bouton deconnexion, creerChargement, gererChargement, genererPlan, imprimerEtiquette, incident;	// Boutons du menu
	private JComboBox destinations;	// Liste des destinations
	
	public Prep_Fenetre_princ(Utilisateur utilisateur){
		// Cr�ation graphique de la fen�tre
		setTitle("Pr�paration");
		setSize(1024,768);
		setUndecorated(true);
		fenetre=new FenetreType(utilisateur, "images/preparation/fenetre_princBackground.png");
		setContentPane(fenetre);
		fenetre.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Ajout des bouton sur la fen�tre
		this.deconnexion=new Bouton("images/icones/deconnexion.png","images/icones/deconnexion_inv.png");
		this.deconnexion.setBounds(866, 50, 98, 17);
		this.fenetre.add(this.deconnexion);
		this.deconnexion.addActionListener(this);
		this.creerChargement=new Bouton("images/icones/creerChargement.png","images/icones/creerChargement.png");
		this.creerChargement.setBounds(802, 270, 139, 48);
		this.fenetre.add(this.creerChargement);
		this.creerChargement.addActionListener(this);
		this.gererChargement=new Bouton("images/icones/gererChargement.png","images/icones/gererChargement.png");
		this.gererChargement.setBounds(802, 338, 143, 45);
		this.fenetre.add(this.gererChargement);
		this.gererChargement.addActionListener(this);
		this.genererPlan=new Bouton("images/icones/genererPlan.png","images/icones/genererPlan.png");
		this.genererPlan.setBounds(802, 403, 158, 29);
		this.fenetre.add(this.genererPlan);
		this.gererChargement.addActionListener(this);
		this.imprimerEtiquette=new Bouton("images/icones/imprimerEtiquette.png","images/icones/imprimerEtiquette.png");
		this.imprimerEtiquette.setBounds(802, 452, 121, 40);
		this.fenetre.add(this.imprimerEtiquette);
		this.imprimerEtiquette.addActionListener(this);
		this.incident=new Bouton("images/icones/incident.png","images/icones/incident.png");
		this.incident.setBounds(802, 512, 162, 42);
		this.fenetre.add(this.incident);
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
		
		// Affichage des zones textes
		JLabel labelVolume = new JLabel("Volume");
		labelVolume.setBounds(60,305,100,20);
		labelVolume.setFont(font);
		fenetre.add(labelVolume);
		JLabel labelCharge = new JLabel("Charg�");
		labelCharge.setBounds(60,340,100,20);
		labelCharge.setFont(font);
		fenetre.add(labelCharge);	
		
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
		colonnesTable.add("Chargement Valid�");
		
		
		// On rempli les variable dynamique et on l'ajoute au container
		if(listeDonneesPrep.getListe().size()!=0){	// Si le pr�parateur � des pr�parations � effectuer
			// Ajout des destinations
			destinations = new JComboBox(listeDonneesPrep.combo());
			
			// Premi�re destination
			this.selectionnee=(DonneesPrep)listeDonneesPrep.getListe().get(0);
			
			// Affichage du volume
			this.labelVolume = new JLabel(this.selectionnee.getVolume().toString());
			this.labelVolume.setBounds(150,305,100,20);
			this.labelVolume.setFont(font);
			fenetre.add(this.labelVolume);
			
			// Affichage du volume d�j� charg�
			this.labelCharge = new JLabel(this.selectionnee.getCharge().toString());	
			this.labelCharge.setBounds(150,340,100,20);
			this.labelCharge.setFont(font);
			fenetre.add(this.labelCharge);
			
			// On remplit les lignes du tableau
			tableData=this.selectionnee.getListeCamionChargement();
		}
		else{	//Si le pr�parateur n'a pas de pr�paration
			tableData=new Vector();
			destinations = new JComboBox();
			destinations.setEnabled(false);
			// On v�rouille les bouton
			this.creerChargement.setEnabled(false);
			this.gererChargement.setEnabled(false);
			this.genererPlan.setEnabled(false);
			this.imprimerEtiquette.setEnabled(false);
		}
		// Cr�ation de la combo box des destinations
		destinations.setEditable(false);
		destinations.setBounds(60,270,200,20);
		fenetre.add(destinations);
		destinations.addItemListener(this);
		// Cr�ation du tableau de camions
		tableMod = new ModeleTable(colonnesTable,tableData);
		// Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		tableSorter = new TableSorter(tableMod);
		// Cr�ation du tableau
		table = new JTable(tableSorter);
		// initialisation du Sorter
		tableSorter.setTableHeader(table.getTableHeader());
		// Ecoute pour v�rouiller les boutons
		table.addMouseListener(this);
     
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
		table.setPreferredScrollableViewportSize(new Dimension(737,353));
		scrollPane.setBounds(48, 372, 737, 353);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		getContentPane().add(scrollPane);

//		Comportement lors de la fermeture
		/*WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		};
		addWindowListener(l);	*/	
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
						new Prep_Creer_chargement(this.utilisateur, 
								this.selectionnee.getDestination(),
								new AccesBDDCamion().rechercher((Integer)((Vector)tableMod.getRow(ligneActive)).get(0)),
								(Integer)((Vector)tableMod.getRow(ligneActive)).get(12)).setVisible(true);
					}
					catch(SQLException SQLE){
						
					}
				}
				
				// Modification d'un ancien chargement
				else if(source==this.gererChargement) {
					dispose();					
					new Prep_Gerer_chargement(
							this.utilisateur,
							(Integer)((Vector)tableMod.getRow(ligneActive)).get(10), 
							this.selectionnee.getDestination().getId(), 
							this.selectionnee.getVolume()).setVisible(true);
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
					Prep_Consulter_incident cons = new Prep_Consulter_incident(this.utilisateur);
					cons.setVisible(true);
				}
			}
		}
	}

	// Mise � jour de la fen�tre lorsque l'onchange la destination
	public void itemStateChanged(ItemEvent arg0) {
		// Chargement du volume pour la destination selectionn�e
		selectionnee=listeDonneesPrep.exists((String)destinations.getSelectedItem());
		
		// On r�affiche les volumes
		fenetre.remove(this.labelVolume);
		this.labelVolume=new JLabel(selectionnee.getVolume().toString());
		this.labelVolume.setBounds(150,305,100,20);
		fenetre.add(this.labelVolume);
		
		fenetre.remove(this.labelCharge);
		this.labelCharge=new JLabel(selectionnee.getCharge().toString());
		this.labelCharge.setBounds(150,340,100,20);
		fenetre.add(this.labelCharge);
		
		// Mise � jour du tableau
		for(int i=0;i<tableMod.getRowCount();i++)
			tableMod.removeRow(i);	
		for(int i=0;i<selectionnee.getListeCamionChargement().size();i++)
			tableMod.addRow(selectionnee.getListeCamionChargement().get(i));
		tableMod.fireTableDataChanged();
		table.updateUI();
		
		// On remet les boutons actifs
		this.creerChargement.setEnabled(true);
		this.gererChargement.setEnabled(true);
		this.genererPlan.setEnabled(true);
		this.imprimerEtiquette.setEnabled(true);
		
		// On met � jour le container
		fenetre.repaint();
	}


	// Quand on clique sur le tableau
	public void mouseClicked(MouseEvent arg0) {
		int ligneActive = table.getSelectedRow();
		if(ligneActive!=-1){
			Vector ligne=(Vector)this.tableMod.getRow(ligneActive);
			// On bloque le bouton cr�er si pour la pr�paration un chargement a d�j� �t� cr�� auparavant
			if(((Integer)ligne.get(10)).equals(new Integer(0)))
				this.creerChargement.setEnabled(true);
			else	// La pr�paration a d�j� un chargement
				this.creerChargement.setEnabled(false);
			
		}
	}



	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}