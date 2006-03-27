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
	private JLabel labelCharge;	// Volume d�j� charg� pour la destination
	private Vector colonnesTable=new Vector();	// Colonnes du tableau de camions
	private Vector tableData = new Vector();	// Donn�es pour remplir le camion
	private ModeleTable tableMod;	// Mod�le de tableau de camions
	private JTable table;	// Tableau de camions
	private TableSorter tableSorter;	// Ordonnancement pour le tableau de camions
	private ListeDonneesPrep listeDonneesPrep, recopie;	// Liste associ�e � ce pr�parateur
	private DonneesPrep selectionnee;
	private Utilisateur utilisateur;
	private FenetreType fenetre;	// Container des �l�ments d'affichage
	private Bouton deconnexion, creerChargement, gererChargement, genererPlan, imprimerEtiquette, incident, validerCharg, cloturerPrep;	// Boutons du menu
	private JComboBox destinations;	// Liste des destinations
	private int ligneActive;
	
	public FenetrePrincipale(Utilisateur utilisateur){
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
		
		// On garde en m�moire l'utilisateur
		this.utilisateur=utilisateur;
		
		// Recherche des informations propre � l'utilisateur dans la BDD
		try{
			listeDonneesPrep=new ListeDonneesPrep(this.utilisateur);
			this.recopie=new ListeDonneesPrep(this.utilisateur);
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
		colonnesTable.add("Volume Disponible");
		colonnesTable.add("Origine");
		colonnesTable.add("Destination");
		colonnesTable.add("Volume � charger");
		colonnesTable.add("Chargement en cours");
		colonnesTable.add("Chargement Valid�");
		colonnesTable.add("Etat du chargement");
		colonnesTable.add("idPreparation");
		
		
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
		
		// On revient � la fen�tre de login
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
			// Cr�ation d'un nouveau chargement pour la destination selectionn�e
			if(ligneActive==-1)
				new FenetreWarning("Veuillez s�lectionner un camion dans le tableau");
			else{
				// Cr�ation d'un chargement
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
				
				// Cr�ation du plan de chargement
				else if(source==this.genererPlan){
					dispose();
					new PlanChargement(this.utilisateur,/*(Integer)((Vector)tableMod.getRow(ligneActive)).get(11)*/new Integer(2)).setVisible(true);
				}
					
				// Imprimer une �tiquette
				else if(source==this.imprimerEtiquette)
						JOptionPane.showMessageDialog(this,"L'impression a �t� lanc�e","Message de confirmation",JOptionPane.YES_NO_CANCEL_OPTION);
				
				// Valider le chargement
				else if(source==this.validerCharg){
					// Mise � jour dans la BDD
					Integer idChargement=(Integer)((Vector)this.tableMod.getRow(ligneActive)).get(11);
					try{
						AccesBDDChargement bddChargement=new AccesBDDChargement();
						bddChargement.valider(
								bddChargement.rechercher(idChargement),
								(Integer)((Vector)this.tableMod.getRow(ligneActive)).get(14));
					}
					catch(SQLException SQLE){						
					}
					// Mise � jour du tableau
					this.tableMod.setValueAt(new Integer(0), ligneActive,11);
					this.tableMod.setValueAt(idChargement, ligneActive,12);
					this.tableMod.setValueAt("Valid�", ligneActive,13);
					this.table.updateUI();
					// Mise � jour des boutons
					this.gererChargement.setEnabled(false);
					this.validerCharg.setEnabled(false);
					this.imprimerEtiquette.setEnabled(true);
					this.cloturerPrep.setEnabled(true);
				}
				
				// Cloturer la pr�paration
				else if(source==this.cloturerPrep){
					// On enl�ve la pr�paration de la BDD
					try{
						new AccesBDDPreparation().supprimer((Integer)((Vector)this.tableMod.getRow(ligneActive)).get(14));
					}
					catch(SQLException esql){
						
					}
					// On supprime la ligne du tableau et on m�j
					this.tableMod.removeRow(ligneActive);
					this.table.updateUI();
					
					// Destruction des images en locak sur l'ordinateur
					File file=null;
					for(int i=0;i<6;i++){
						file=new File(((Integer)((Vector)this.tableMod.getRow(ligneActive)).get(11)).toString()+"/plan"+i+".png");
						file.delete();
					}
					// On d�ttruit le r�pertoire
					File repertoire=new File(((Integer)((Vector)this.tableMod.getRow(ligneActive)).get(11)).toString());
					repertoire.delete();
					dispose();
				}
			}
		}
	}

	// Mise � jour de la fen�tre lorsque l'on change la destination
	public void itemStateChanged(ItemEvent arg0) {
		// Chargement du volume pour la destination selectionn�e
		selectionnee=listeDonneesPrep.exists((String)destinations.getSelectedItem());
		
		// On r�affiche les volumes
		this.labelVolume.setText(selectionnee.getVolume().toString());
		this.labelVolume.updateUI();
		
		this.labelCharge.setText(selectionnee.getCharge().toString());
		this.labelCharge.updateUI();
				
		// Mise � jour du tableau
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
			// Cas o� la pr�paration est vierge!
			if(((Integer)ligne.get(11)).intValue()==0){
				this.creerChargement.setEnabled(true);
				this.genererPlan.setEnabled(false);
				this.gererChargement.setEnabled(false);
				this.validerCharg.setEnabled(false);
				this.imprimerEtiquette.setEnabled(false);
				this.cloturerPrep.setEnabled(false);
			}
			else{
				// Cas o� le chargement est en cours
				if(((Integer)ligne.get(12)).intValue()==0){
					this.creerChargement.setEnabled(false);
					this.genererPlan.setEnabled(true);
					this.gererChargement.setEnabled(true);
					this.validerCharg.setEnabled(true);
					this.imprimerEtiquette.setEnabled(false);
					this.cloturerPrep.setEnabled(false);
				}
				// Cas o� le chargement a �t� valid�
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
