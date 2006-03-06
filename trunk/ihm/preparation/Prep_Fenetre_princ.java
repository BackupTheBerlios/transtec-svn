package ihm.preparation;

import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;

import accesBDD.AccesBDDPreparation;

import donnees.Camion;
import donnees.Preparation;
import donnees.Utilisateur;


public class Prep_Fenetre_princ extends JFrame implements ActionListener, ItemListener{
	JButton gerer_chargement = new JButton("<html>gérer le<br>chargement</html>");
	JButton generer_le_plan = new JButton("<html>générer le<br>plan de chargement</html>");
	JButton imprimer_etiquette = new JButton("<html>imprimer<br>étiquette</html>");
	JButton incident = new JButton("<html>incidents<br>archivés</html>");
	JButton creer_chargement=new JButton("<HTML>Créer un<br>chargement (3D)");
	private JComboBox destinations;	// Liste des destinations
	private JLabel labelVolume;	// Volume de la destination	
	private JLabel labelCharge;
	private Vector nomColonnes_cam = new Vector();
	private Vector donnees_cam = new Vector();
	private ModeleTable modeleCam;
	private JTable tab_cam;
	private int ligneActive;
	private TableSorter sorter;
	private Preparation preparation=null;
	private ListeDonneesPrep listeDonneesPrep;	// Liste associée à ce préparateur
	private Container ct;	// Conatiner des éléments d'affichage
	
	public Prep_Fenetre_princ(Utilisateur utilisateur){
		
		//Constructeur de la fenetre
		super(utilisateur.getPersonne().getNom()+" "+utilisateur.getPersonne().getPrenom()+" - Preparateur");
		ct = this.getContentPane();
		
		// On sauve l'objet
//		this.preparation=preparation;
		
		//Comportement lors de la fermeture
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		};
		addWindowListener(l);		
		
		//Création du menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		//Section Fichier	
		menuBar.add(menuFichier);
		menuFichier.add("Quitter");
		//Construction du menu
		setJMenuBar(menuBar);
		try{
			listeDonneesPrep=new ListeDonneesPrep(utilisateur);
		}
		catch(SQLException e){
			
		}
		
		
		// Choix de la destination
		destinations = new JComboBox(listeDonneesPrep.combo());
		destinations.setEditable(false);
		destinations.setBounds(65,37,200,20);
		ct.add(destinations);
		destinations.addItemListener(this);
		
		//Création des icones
		ImageIcon icone_cam = new ImageIcon("images/icones/camion.gif");
		ImageIcon icone_plan = new ImageIcon("images/icones/plan.gif");
		ImageIcon icone_eti = new ImageIcon("images/icones/etiquette.gif");
		ImageIcon icone_inc = new ImageIcon("images/icones/incident.gif");
		
		//Taile de la fenêtre
		setSize(800,600);
		setBounds(200,100,800,600);
		
		//Insertion des icones dans les boutons
		gerer_chargement.setIcon(icone_cam);
		generer_le_plan.setIcon(icone_plan);
		imprimer_etiquette.setIcon(icone_eti);
		incident.setIcon(icone_inc);
		
		//Declaration du layout
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		//Création de la police
		Font font;
		font = new Font("Verdana", Font.BOLD, 15);
		
		//Insertion des boutons
	
		creer_chargement.setBounds(520,10,250,50);
		creer_chargement.setFont(font);
		ct.add(creer_chargement);
		creer_chargement.addActionListener(this);
		
		gerer_chargement.setBounds(520,70,250,50);
		gerer_chargement.setFont(font);
		ct.add(gerer_chargement);
		gerer_chargement.addActionListener(this);
		
		generer_le_plan.setBounds(520,130,250,50);
		generer_le_plan.setFont(font);
		ct.add(generer_le_plan);
		generer_le_plan.addActionListener(this);
		
		imprimer_etiquette.setBounds(520,190,250,50);
		imprimer_etiquette.setFont(font);
		ct.add(imprimer_etiquette);
		imprimer_etiquette.addActionListener(this);
		
		incident.setBounds(520,380,250,50);
		incident.setFont(font);
		ct.add(incident);
		incident.addActionListener(this);
		
		// Affichage des zones textes
		JLabel labelVolume = new JLabel("Volume");
		labelVolume.setBounds(110,100,100,20);
		ct.add(labelVolume);
		JLabel labelCharge = new JLabel("Chargé");
		labelCharge.setBounds(110,150,100,20);
		ct.add(labelCharge);
		
		if(listeDonneesPrep.getListe()!=null){
			// Affichage du volume
			this.labelVolume = new JLabel(((DonneesPrep)listeDonneesPrep.getListe().get(0)).getVolume().toString());
			this.labelVolume.setBounds(230,100,100,20);
			ct.add(this.labelVolume);
			
			// Affichage du volume déjà chargé
			this.labelCharge = new JLabel(((DonneesPrep)listeDonneesPrep.getListe().get(0)).getCharge().toString());	
			this.labelCharge.setBounds(230,150,100,20);
			ct.add(this.labelCharge);
		}
		

		
		
		
		
		
		
		//Création de la première ligne
		nomColonnes_cam.add("idPreparation");
		nomColonnes_cam.add("Utilisateur");
		nomColonnes_cam.add("Origine");
		nomColonnes_cam.add("Origine");
		nomColonnes_cam.add("Destination");
		nomColonnes_cam.add("Destination");
		nomColonnes_cam.add("Camion");
		nomColonnes_cam.add("Camion");
		nomColonnes_cam.add("Volume");
		nomColonnes_cam.add("Etat");
		
		/*try{
			donnees_cam=new AccesBDDPreparation().listerDestAPreparer(utilisateur);
		}
		catch(SQLException e){
			
		}*/
		donnees_cam=null;
		
		
//Création du tableau
        /*modeleCam = new ModeleTable(nomColonnes_cam,donnees_cam);
		// Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter = new TableSorter(modeleCam);
		// Création du tableau
		tab_cam = new JTable(sorter);
		// initialisation du Sorter
		sorter.setTableHeader(tab_cam.getTableHeader());
     
		//Aspect du tableau
		tab_cam.setAutoCreateColumnsFromModel(true);
		tab_cam.setOpaque(false);
		tab_cam.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tab_cam.removeColumn(tab_cam.getColumnModel().getColumn(0));
		tab_cam.removeColumn(tab_cam.getColumnModel().getColumn(0));
		tab_cam.removeColumn(tab_cam.getColumnModel().getColumn(0));
		tab_cam.removeColumn(tab_cam.getColumnModel().getColumn(1));
		tab_cam.removeColumn(tab_cam.getColumnModel().getColumn(2));
		
		//Construction du JScrollPane
		JScrollPane scrollPane = new JScrollPane(tab_cam);
		tab_cam.setPreferredScrollableViewportSize(new Dimension(300,150));
		scrollPane.setBounds(80,250,320,150);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		getContentPane().add(scrollPane);*/

		setVisible(true);	
		
	}
	


	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		ligneActive = tab_cam.getSelectedRow();
		
		
		if (source == creer_chargement) {
			//Si une ligne est selectionnée
			if (ligneActive != -1){
				//On récupère les données de la ligne du tablea
				//dispose();
				preparation.initializeChargement();
				preparation.setCamionACharger(new Camion((Vector) modeleCam.getRow(ligneActive)));
				Prep_Creer_chargement fen1 = new Prep_Creer_chargement(preparation);
				fen1.setVisible(true);
			}
			else{
				JOptionPane.showMessageDialog(this,"Veuillez sélectionner un camion","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
			}
		}
		//Selection de "Gérer le chargement"
		if (source == gerer_chargement) {
			//Si une ligne est selectionnée
			if (ligneActive != -1){
				//On récupère les données de la ligne du tableau
				Vector cVect = (Vector) modeleCam.getRow(ligneActive);
				//dispose();
//				ATTENTION:On passe un vecteur comme argument et pas un objet camion
				Prep_Gerer_chargement fen1 = new Prep_Gerer_chargement(cVect,this.preparation.getUtilisateur() );
				fen1.setVisible(true);
			}
			else{
				JOptionPane.showMessageDialog(this,"Veuillez sélectionner un camion","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//Selection de "Générer le plan"
		else if (source == generer_le_plan){
			//Si une ligne est selectionnée
			if (ligneActive != -1){
				Prep_Plan_chargement plan = new Prep_Plan_chargement();
				plan.setVisible(true);
			}
			else{
				JOptionPane.showMessageDialog(this,"Veuillez sélectionner un camion","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//Selection de "Imprimer étiquette"
		else if (source == imprimer_etiquette){
			//Si une ligne est selectionnée
			if (ligneActive != -1){
				JOptionPane.showMessageDialog(this,"L'impression a été lancée","Message de confirmation",JOptionPane.YES_NO_CANCEL_OPTION);
			}
			
			else JOptionPane.showMessageDialog(this,"Veuillez sélectionner un camion","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
		}
		
		//Selection de "Consulter les incidents"
		else if (source == incident){
			Prep_Consulter_incident cons = new Prep_Consulter_incident();
			cons.setVisible(true);
		}	
	}



	public void itemStateChanged(ItemEvent arg0) {
		// Chargement du volume pour la destination selectionnée
		DonneesPrep selectionnee=listeDonneesPrep.exists((String)destinations.getSelectedItem());
		
		// On réaffiche les volumes
		ct.remove(this.labelVolume);
		this.labelVolume=new JLabel(selectionnee.getVolume().toString());
		this.labelVolume.setBounds(230,100,100,20);
		ct.add(this.labelVolume);
		
		ct.remove(this.labelCharge);
		this.labelCharge=new JLabel(selectionnee.getCharge().toString());
		this.labelCharge.setBounds(230,150,100,20);
		ct.add(this.labelCharge);
		
		
		ct.repaint();
		
		
	}
}