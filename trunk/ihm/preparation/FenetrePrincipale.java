package ihm.preparation;

import ihm.Bouton;
import ihm.FenetreType;
import ihm.FenetreWarning;
import ihm.Fenetre_login;
import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import accesBDD.AccesBDDCamion;
import accesBDD.AccesBDDChargement;
import accesBDD.AccesBDDPreparation;
import accesBDD.AccesBDDUtilisateur;
import donnees.Utilisateur;

public class FenetrePrincipale extends JFrame implements ActionListener, ItemListener, MouseListener{
	private JLabel labelVolume;	// Volume de la destination	
	private JLabel labelCharge;	// Volume déjà chargé pour la destination
	private Vector colonnesTable=new Vector();	// Colonnes du tableau de camions
	private Vector tableData = new Vector();	// Données pour remplir le camion
	private ModeleTable tableMod;	// Modèle de tableau de camions
	private JTable table;	// Tableau de camions
	private TableSorter tableSorter;	// Ordonnancement pour le tableau de camions
	private ListeDonneesPrep listeDonneesPrep, recopie;	// Liste associée à ce préparateur
	private DonneesPrep selectionnee;
	private Utilisateur utilisateur;
	private FenetreType fenetre;	// Container des éléments d'affichage
	private Bouton deconnexion, creerChargement, gererChargement, genererPlan, imprimerEtiquette, incident, validerCharg, cloturerPrep;	// Boutons du menu
	private JComboBox destinations;	// Liste des destinations
	private int ligneActive;
	
	public FenetrePrincipale(Utilisateur utilisateur){
		// Création graphique de la fenêtre
		setTitle("Préparation");
		setSize(1024,768);
		setUndecorated(true);
		fenetre=new FenetreType(utilisateur, "images/preparation/fenetre_princBackground.png");
		setContentPane(fenetre);
		fenetre.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Ajout des bouton sur la fenêtre
		this.deconnexion=new Bouton("images/icones/deconnexion.png","images/icones/deconnexion_inv.png");
		this.deconnexion.setBounds(866, 50, 98, 17);
		this.fenetre.add(this.deconnexion);
		this.deconnexion.addActionListener(this);
		this.creerChargement=new Bouton("images/icones/creerChargement.png","images/icones/creerChargement_inv.png");
		this.creerChargement.setBounds(802, 270, 165, 41);
		this.fenetre.add(this.creerChargement);
		this.genererPlan=new Bouton("images/icones/genererPlan.png","images/icones/genererPlan_inv.png");
		this.genererPlan.setBounds(802, 331, 165, 41);
		this.fenetre.add(this.genererPlan);
		this.genererPlan.addActionListener(this);
		this.creerChargement.addActionListener(this);
		this.gererChargement=new Bouton("images/icones/gererChargement.png","images/icones/gererChargement_inv.png");
		this.gererChargement.setBounds(802, 392, 165, 41);
		this.fenetre.add(this.gererChargement);
		this.gererChargement.addActionListener(this);		
		this.validerCharg=new Bouton("images/icones/validerCharg.png","images/icones/validerCharg_inv.png");
		this.validerCharg.setBounds(802, 453, 165, 41);
		this.fenetre.add(this.validerCharg);
		this.validerCharg.addActionListener(this);
		this.imprimerEtiquette=new Bouton("images/icones/imprimerEtiquette.png","images/icones/imprimerEtiquette_inv.png");
		this.imprimerEtiquette.setBounds(802, 514, 165, 41);
		this.fenetre.add(this.imprimerEtiquette);
		this.imprimerEtiquette.addActionListener(this);
		this.cloturerPrep=new Bouton("images/icones/cloturer.png","images/icones/cloturer_inv.png");
		this.cloturerPrep.setBounds(802, 575, 165, 41);
		this.fenetre.add(this.cloturerPrep);
		this.cloturerPrep.addActionListener(this);
		this.incident=new Bouton("images/icones/incident.png","images/icones/incident_inv.png");
		this.incident.setBounds(802, 636, 165, 41);
		this.fenetre.add(this.incident);
		this.incident.addActionListener(this);
		
		// On garde en mémoire l'utilisateur
		this.utilisateur=utilisateur;
		
		// Recherche des informations propre à l'utilisateur dans la BDD
		try{
			listeDonneesPrep=new ListeDonneesPrep(this.utilisateur);
			this.recopie=new ListeDonneesPrep(this.utilisateur);
		}
		catch(SQLException e){
			
		}
		// Création de la police pour les affichages de texte
		Font font=new Font("Verdana", Font.BOLD, 12);
		
		// Affichage des zones textes
		JLabel labelVolume = new JLabel("Volume");
		labelVolume.setBounds(60,305,100,20);
		labelVolume.setFont(font);
		fenetre.add(labelVolume);
		JLabel labelCharge = new JLabel("Chargé");
		labelCharge.setBounds(60,340,100,20);
		labelCharge.setFont(font);
		fenetre.add(labelCharge);	
		
		// Inititialisation des colonnes du tableau de camions
		colonnesTable.add("idCamion");
		colonnesTable.add("Immatriculation");
		colonnesTable.add("Disponibilité");
		colonnesTable.add("Largeur");
		colonnesTable.add("Hauteur");
		colonnesTable.add("Profondeur");
		colonnesTable.add("Volume");
		colonnesTable.add("Volume Disponible");
		colonnesTable.add("Origine");
		colonnesTable.add("Destination");
		colonnesTable.add("Volume à charger");
		colonnesTable.add("Chargement en cours");
		colonnesTable.add("Chargement Validé");
		colonnesTable.add("Etat du chargement");
		colonnesTable.add("idPreparation");
		
		
		// On rempli les variable dynamique et on l'ajoute au container
		if(listeDonneesPrep.getListe().size()!=0){	// Si le préparateur à des préparations à effectuer
			// Ajout des destinations
			destinations = new JComboBox(listeDonneesPrep.combo());
			
			// Première destination
			this.selectionnee=(DonneesPrep)listeDonneesPrep.getListe().get(0);
			
			// Affichage du volume
			this.labelVolume = new JLabel(this.selectionnee.getVolume().toString());
			this.labelVolume.setBounds(150,305,100,20);
			this.labelVolume.setFont(font);
			fenetre.add(this.labelVolume);
			
			// Affichage du volume déjà chargé
			this.labelCharge = new JLabel(this.selectionnee.getCharge().toString());	
			this.labelCharge.setBounds(150,340,100,20);
			this.labelCharge.setFont(font);
			fenetre.add(this.labelCharge);
			
			// On remplit les lignes du tableau
			tableData=this.selectionnee.getListeCamionChargement();
		}
		else{	//Si le préparateur n'a pas de préparation
			tableData=new Vector();
			destinations = new JComboBox();
			destinations.setEnabled(false);
			// On vérouille les bouton
			this.creerChargement.setEnabled(false);
			this.gererChargement.setEnabled(false);
			this.genererPlan.setEnabled(false);
			this.imprimerEtiquette.setEnabled(false);
		}
		// Création de la combo box des destinations
		destinations.setEditable(false);
		destinations.setBounds(60,270,200,20);
		fenetre.add(destinations);
		destinations.addItemListener(this);
		// Création du tableau de camions
		tableMod = new ModeleTable(colonnesTable,tableData);
		// Création du TableSorter qui permet de réordonner les lignes à volonté
		tableSorter = new TableSorter(tableMod);
		// Création du tableau
		table = new JTable(tableSorter);
		// initialisation du Sorter
		tableSorter.setTableHeader(table.getTableHeader());
		// Ecoute pour vérouiller les boutons
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
		table.removeColumn(table.getColumnModel().getColumn(1));
		table.removeColumn(table.getColumnModel().getColumn(2));
		table.removeColumn(table.getColumnModel().getColumn(2));
		table.removeColumn(table.getColumnModel().getColumn(3));
		
		//Construction du JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension(737,353));
		scrollPane.setBounds(48, 372, 737, 353);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		getContentPane().add(scrollPane);
		
		setVisible(true);
	}
	


	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		// On revient à la fenêtre de login
		if(source==this.deconnexion){
			dispose();
			Fenetre_login login=new Fenetre_login();
			login.setVisible(true);
		}
		// Afficher les incidents
		else if(source==this.incident){
			dispose();
			new ConsulterIncident(this.utilisateur).setVisible(true);
		}
		else{
			int ligneActive = table.getSelectedRow();
			// Création d'un nouveau chargement pour la destination selectionnée
			if(ligneActive==-1)
				new FenetreWarning("Veuillez sélectionner un camion dans le tableau");
			else{
				// Création d'un chargement
				if(source==this.creerChargement){
					dispose();
					try{
						new CreerChargement(this.utilisateur, 
								this.selectionnee.getDestination(),
								new AccesBDDCamion().rechercher((Integer)((Vector)tableMod.getRow(ligneActive)).get(0))/*,
								(Integer)((Vector)tableMod.getRow(ligneActive)).get(14)*/).setVisible(true);
					}
					catch(SQLException SQLE){
						
					}
				}
				
				// Modification d'un ancien chargement
				else if(source==this.gererChargement) {
					dispose();					
					new ModifierChargement(
							this.utilisateur,
							(Integer)((Vector)tableMod.getRow(ligneActive)).get(11), 
							this.selectionnee.getDestination().getId(), 
							this.selectionnee.getVolume()).setVisible(true);
				}
				
				// Création du plan de chargement
				else if(source==this.genererPlan){
					dispose();
					new PlanChargement(this.utilisateur,/*(Integer)((Vector)tableMod.getRow(ligneActive)).get(11)*/new Integer(2)).setVisible(true);
				}
					
				// Imprimer une étiquette
				else if(source==this.imprimerEtiquette)
						JOptionPane.showMessageDialog(this,"L'impression a été lancée","Message de confirmation",JOptionPane.YES_NO_CANCEL_OPTION);
				
				// Valider le chargement
				else if(source==this.validerCharg){
					// Mise à jour dans la BDD
					Integer idChargement=(Integer)((Vector)this.tableMod.getRow(ligneActive)).get(11);
					try{
						AccesBDDChargement bddChargement=new AccesBDDChargement();
						bddChargement.valider(
								bddChargement.rechercher(idChargement),
								(Integer)((Vector)this.tableMod.getRow(ligneActive)).get(14));
					}
					catch(SQLException SQLE){						
					}
					// Mise à jour du tableau
					this.tableMod.setValueAt(new Integer(0), ligneActive,11);
					this.tableMod.setValueAt(idChargement, ligneActive,12);
					this.tableMod.setValueAt("Validé", ligneActive,13);
					this.table.updateUI();
					// Mise à jour des boutons
					this.gererChargement.setEnabled(false);
					this.validerCharg.setEnabled(false);
					this.imprimerEtiquette.setEnabled(true);
					this.cloturerPrep.setEnabled(true);
				}
				
				// Cloturer la préparation
				else if(source==this.cloturerPrep){
					// On enlève la préparation de la BDD
					try{
						new AccesBDDPreparation().supprimer((Integer)((Vector)this.tableMod.getRow(ligneActive)).get(14));
					}
					catch(SQLException esql){
						
					}
					// On supprime la ligne du tableau et on màj
					this.tableMod.removeRow(ligneActive);
					this.table.updateUI();
					
					// Destruction des images en locak sur l'ordinateur
					File file=null;
					for(int i=0;i<6;i++){
						file=new File(((Integer)((Vector)this.tableMod.getRow(ligneActive)).get(11)).toString()+"/plan"+i+".png");
						file.delete();
					}
					// On déttruit le répertoire
					File repertoire=new File(((Integer)((Vector)this.tableMod.getRow(ligneActive)).get(11)).toString());
					repertoire.delete();
					dispose();
				}
			}
		}
	}

	// Mise à jour de la fenêtre lorsque l'on change la destination
	public void itemStateChanged(ItemEvent arg0) {
		// Chargement du volume pour la destination selectionnée
		selectionnee=listeDonneesPrep.exists((String)destinations.getSelectedItem());
		
		// On réaffiche les volumes
		this.labelVolume.setText(selectionnee.getVolume().toString());
		this.labelVolume.updateUI();
		
		this.labelCharge.setText(selectionnee.getCharge().toString());
		this.labelCharge.updateUI();
				
		// Mise à jour du tableau
		int i,max=tableMod.getRowCount();
		for(i=0;i<max;i++)
			tableMod.removeRow(0);
		//table.updateUI();
		for(i=0;i<selectionnee.getListeCamionChargement().size();i++)
			tableMod.addRow(selectionnee.getListeCamionChargement().get(i));
		tableMod.fireTableDataChanged();
		table.updateUI();
		
		// On remet les boutons actifs
		this.creerChargement.setEnabled(true);
		this.gererChargement.setEnabled(true);
		this.genererPlan.setEnabled(true);
		this.imprimerEtiquette.setEnabled(true);
		this.validerCharg.setEnabled(true);
		
		this.listeDonneesPrep=this.recopie;
	}


	// Quand on clique sur le tableau
	public void mouseClicked(MouseEvent arg0) {
		this.ligneActive = table.getSelectedRow();
		if(ligneActive!=-1){
			Vector ligne=(Vector)this.tableMod.getRow(ligneActive);
			// Cas où la préparation est vierge!
			if(((Integer)ligne.get(11)).intValue()==0){
				this.creerChargement.setEnabled(true);
				this.genererPlan.setEnabled(false);
				this.gererChargement.setEnabled(false);
				this.validerCharg.setEnabled(false);
				this.imprimerEtiquette.setEnabled(false);
				this.cloturerPrep.setEnabled(false);
			}
			else{
				// Cas où le chargement est en cours
				if(((Integer)ligne.get(12)).intValue()==0){
					this.creerChargement.setEnabled(false);
					this.genererPlan.setEnabled(true);
					this.gererChargement.setEnabled(true);
					this.validerCharg.setEnabled(true);
					this.imprimerEtiquette.setEnabled(false);
					this.cloturerPrep.setEnabled(false);
				}
				// Cas où le chargement a été validé
				else{
					this.creerChargement.setEnabled(false);
					this.genererPlan.setEnabled(true);
					this.gererChargement.setEnabled(false);
					this.validerCharg.setEnabled(false);
					this.imprimerEtiquette.setEnabled(true);
					this.cloturerPrep.setEnabled(true);
				}
			}
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
	
	public static void main(String[] args) {
		try{
		FenetrePrincipale fen1 = new FenetrePrincipale(new AccesBDDUtilisateur().isRegistered("user3", "user3"));
		fen1.setVisible(true);	
		}
		catch(SQLException e){
			
		}
	}	

}
