package ihm.preparation;

import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;

import donnees.Camion;
import donnees.Preparation;
import donnees.Utilisateur;
import accesBDD.AccesBDDCamion;

public class Prep_Fenetre_princ extends JFrame implements ActionListener{

	/**
	 * @param args
	 */
	JButton gerer_chargement = new JButton("<html>g�rer le<br>chargement</html>");
	JButton generer_le_plan = new JButton("<html>g�n�rer le<br>plan de chargement</html>");
	JButton imprimer_etiquette = new JButton("<html>imprimer<br>�tiquette</html>");
	JButton incident = new JButton("<html>incidents<br>archiv�s</html>");
	JButton creer_chargement=new JButton("<HTML>Cr�er un<br>chargement (3D)");
	private Vector nomColonnes_cam = new Vector();
	private Vector donnees_cam = new Vector();
	private ModeleTable modeleCam;
	private JTable tab_cam;
	private int ligneActive;
	private TableSorter sorter;
	private Preparation preparation=null;
	
	
	public Prep_Fenetre_princ(Preparation preparation){
		
		//Constructeur de la fenetre
		super(preparation.getUtilisateur().getPersonne().getNom()+" "+preparation.getUtilisateur().getPersonne().getPrenom()+" - Preparateur");
		Container ct = this.getContentPane();
		
		// On sauve l'objet
		this.preparation=preparation;
		
		//Comportement lors de la fermeture
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		};
		addWindowListener(l);		
		
		//Cr�ation du menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		//Section Fichier	
		menuBar.add(menuFichier);
		menuFichier.add("Quitter");
		//Construction du menu
		setJMenuBar(menuBar);
		
		//Cr�ation des icones
		ImageIcon icone_cam = new ImageIcon("images/icones/camion.gif");
		ImageIcon icone_plan = new ImageIcon("images/icones/plan.gif");
		ImageIcon icone_eti = new ImageIcon("images/icones/etiquette.gif");
		ImageIcon icone_inc = new ImageIcon("images/icones/incident.gif");
		
		//Taile de la fen�tre
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
		
		//Cr�ation de la police
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
		
		
//Cr�ation du Panel stockant les informations sur les camions
		
		//Cr�ation du titre
		JLabel titre_list_cam = new JLabel("information sur le travail");
		titre_list_cam.setBounds(150,10,200,20);
		ct.add(titre_list_cam);
		
		//Cr�ation de la destination
		JLabel txt_dest = new JLabel("destination");
		JLabel champ_dest = new JLabel(preparation.getDestination().getLocalisation().getVille());
		txt_dest.setBounds(110,50,100,20);
		champ_dest.setBounds(230,50,100,20);
		ct.add(txt_dest);
		ct.add(champ_dest);
		
		
		//Cr�ation du volume
		JLabel txt_vol = new JLabel("volume");
		JLabel champ_vol = new JLabel(preparation.getVolumeChargement().toString());	
		txt_vol.setBounds(110,100,100,20);
		champ_vol.setBounds(230,100,100,20);
		ct.add(txt_vol);
		ct.add(champ_vol);

		
		//Cr�ation de la charge
		JLabel txt_charge = new JLabel("charg�");
		String charg� = "volume d�ja charg�";
		JLabel champ_charg� = new JLabel(charg�);	
		txt_charge.setBounds(110,150,100,20);
		champ_charg�.setBounds(230,150,100,20);
		ct.add(txt_charge);
		ct.add(champ_charg�);
		
		//Cr�ation de la premi�re ligne
		nomColonnes_cam.add("idCamion");
		nomColonnes_cam.add("Camion");
		nomColonnes_cam.add("Volume restant");
		
//**********************APPEL A LA BDD************************************
		
	// Lister les camions pour cette destination
		Camion camion=null;
		
		for(int i=0;i<preparation.getListeCamion().size();i++){
			Vector courant=new Vector();
			camion=(Camion)preparation.getListeCamion().get(i);
			courant.add(camion.getId());
			courant.add(camion.getNumero());
			courant.add(camion.getVolume());
			
			donnees_cam.add(courant);
		}
		
//*******************************************************************************
		
//Cr�ation du tableau
        modeleCam = new ModeleTable(nomColonnes_cam,donnees_cam);
		// Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		sorter = new TableSorter(modeleCam);
		// Cr�ation du tableau
		tab_cam = new JTable(sorter);
		// initialisation du Sorter
		sorter.setTableHeader(tab_cam.getTableHeader());
     
		//Aspect du tableau
		tab_cam.setAutoCreateColumnsFromModel(true);
		tab_cam.setOpaque(false);
		tab_cam.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Construction du JScrollPane
		JScrollPane scrollPane = new JScrollPane(tab_cam);
		tab_cam.setPreferredScrollableViewportSize(new Dimension(300,150));
		scrollPane.setBounds(80,250,320,150);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		getContentPane().add(scrollPane);

		setVisible(true);	
		
	}
	


	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		ligneActive = tab_cam.getSelectedRow();
		
		
		if (source == creer_chargement) {
			//Si une ligne est selectionn�e
			/*if (ligneActive != -1){
				//On r�cup�re les donn�es de la ligne du tableau
				Vector cVect = (Vector) modeleCam.getRow(ligneActive);
				//dispose();
//				ATTENTION:On passe un vecteur comme argument et pas un objet camion*/
				Prep_Creer_chargement fen1 = new Prep_Creer_chargement(preparation);
				fen1.setVisible(true);
			/*}
			else{
				JOptionPane.showMessageDialog(this,"Veuillez s�lectionner un camion","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
			}*/
		}
		//Selection de "G�rer le chargement"
		if (source == gerer_chargement) {
			//Si une ligne est selectionn�e
			if (ligneActive != -1){
				//On r�cup�re les donn�es de la ligne du tableau
				Vector cVect = (Vector) modeleCam.getRow(ligneActive);
				//dispose();
//				ATTENTION:On passe un vecteur comme argument et pas un objet camion
				Prep_Gerer_chargement fen1 = new Prep_Gerer_chargement(cVect,this.preparation.getUtilisateur() );
				fen1.setVisible(true);
			}
			else{
				JOptionPane.showMessageDialog(this,"Veuillez s�lectionner un camion","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//Selection de "G�n�rer le plan"
		else if (source == generer_le_plan){
			//Si une ligne est selectionn�e
			if (ligneActive != -1){
				Prep_Plan_chargement plan = new Prep_Plan_chargement();
				plan.setVisible(true);
			}
			else{
				JOptionPane.showMessageDialog(this,"Veuillez s�lectionner un camion","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//Selection de "Imprimer �tiquette"
		else if (source == imprimer_etiquette){
			//Si une ligne est selectionn�e
			if (ligneActive != -1){
				JOptionPane.showMessageDialog(this,"L'impression a �t� lanc�e","Message de confirmation",JOptionPane.YES_NO_CANCEL_OPTION);
			}
			
			else JOptionPane.showMessageDialog(this,"Veuillez s�lectionner un camion","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
		}
		
		//Selection de "Consulter les incidents"
		else if (source == incident){
			Prep_Consulter_incident cons = new Prep_Consulter_incident();
			cons.setVisible(true);
		}	
	}
	
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Prep_Fenetre_princ p = new Prep_Fenetre_princ(new Utilisateur("login2", "motDePasse2", new Integer(Utilisateur.PREPARATIOIN), "dfsfdfds", "fsdsfddfs", "fsdfdsfdsfdss", "9481", "vill2e", "mail2", "69686696"));
		//JFrame p1 = new preparateur();

	}*/
}