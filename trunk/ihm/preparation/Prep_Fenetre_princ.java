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
import donnees.Utilisateur;
import accesBDD.AccesBDDCamion;

public class Prep_Fenetre_princ extends JFrame implements ActionListener{

	/**
	 * @param args
	 */
	JButton gerer_chargement = new JButton("<html>gérer le<br>chargement</html>");
	JButton generer_le_plan = new JButton("<html>générer le<br>plan de chargement</html>");
	JButton imprimer_etiquette = new JButton("<html>imprimer<br>étiquette</html>");
	JButton incident = new JButton("<html>incidents<br>archivés</html>");
	private Vector nomColonnes_cam = new Vector();
	private Vector donnees_cam = new Vector();
	private ModeleTable modeleCam;
	private JTable tab_cam;
	private int ligneActive;
	private TableSorter sorter;
	
	
	public Prep_Fenetre_princ(Utilisateur utilisateur){
		
		//Constructeur de la fenetre
		super(utilisateur.getPersonne().getNom()+" "+utilisateur.getPersonne().getPrenom()+" - Preparateur");
		Container ct = this.getContentPane();
		
		
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
		
		//Création des icones
		ImageIcon icone_cam = new ImageIcon("camion.gif");
		ImageIcon icone_plan = new ImageIcon("plan.gif");
		ImageIcon icone_eti = new ImageIcon("etiquette.gif");
		ImageIcon icone_inc = new ImageIcon("incident.gif");
		
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
	
		gerer_chargement.setBounds(520,10,250,100);
		gerer_chargement.setFont(font);
		ct.add(gerer_chargement);
		gerer_chargement.addActionListener(this);
		
		generer_le_plan.setBounds(520,130,250,100);
		generer_le_plan.setFont(font);
		ct.add(generer_le_plan);
		generer_le_plan.addActionListener(this);
		
		imprimer_etiquette.setBounds(520,250,250,100);
		imprimer_etiquette.setFont(font);
		ct.add(imprimer_etiquette);
		imprimer_etiquette.addActionListener(this);
		
		incident.setBounds(520,380,250,100);
		incident.setFont(font);
		ct.add(incident);
		incident.addActionListener(this);
		
//Création du Panel stockant les informations sur les camions
		
		//Création du titre
		JLabel titre_list_cam = new JLabel("information sur le travail");
		titre_list_cam.setBounds(150,10,200,20);
		ct.add(titre_list_cam);
		
		//Création de la destination
		JLabel txt_dest = new JLabel("destination");
		String destination = "A faire dynamique";
		JLabel champ_dest = new JLabel(destination);
		txt_dest.setBounds(110,50,100,20);
		champ_dest.setBounds(230,50,100,20);
		ct.add(txt_dest);
		ct.add(champ_dest);
		
		
		//Création du volume
		JLabel txt_vol = new JLabel("volume");
		String volume = "volume restant à charger";
		JLabel champ_vol = new JLabel(volume);	
		txt_vol.setBounds(110,100,100,20);
		champ_vol.setBounds(230,100,100,20);
		ct.add(txt_vol);
		ct.add(champ_vol);

		
		//Création de la charge
		JLabel txt_charge = new JLabel("chargé");
		String chargé = "volume déja chargé";
		JLabel champ_chargé = new JLabel(chargé);	
		txt_charge.setBounds(110,150,100,20);
		champ_chargé.setBounds(230,150,100,20);
		ct.add(txt_charge);
		ct.add(champ_chargé);
		
		//Création de la première ligne
		nomColonnes_cam.add("Camion");
		nomColonnes_cam.add("Volume restant");
		
//**********************APPEL A LA BDD************************************
		
		// Lister les camions pour cette destination
		try{
			Vector listeObj=new AccesBDDCamion().listerParDest(/* destination */);
			 for(int i=0;i<listeObj.size();i++){
	            	donnees_cam.addElement(((Camion)listeObj.get(i)).toVector());
	            }
		}
		catch(SQLException e){
			
		}
		
		
//*******************************************************************************
		
//Création du tableau
        modeleCam = new ModeleTable(nomColonnes_cam,donnees_cam);
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
		
		//Selection de "Gérer le chargement"
		if (source == gerer_chargement) {
			//Si une ligne est selectionnée
			if (ligneActive != -1){
				//On récupère les données de la ligne du tableau
				Vector cVect = (Vector) modeleCam.getRow(ligneActive);
				//dispose();
//				ATTENTION:On passe un vecteur comme argument et pas un objet camion
				Prep_Gerer_chargement fen1 = new Prep_Gerer_chargement(cVect);
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
	
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Prep_Fenetre_princ p = new Prep_Fenetre_princ(new Utilisateur("login2", "motDePasse2", new Integer(Utilisateur.PREPARATIOIN), "dfsfdfds", "fsdsfddfs", "fsdfdsfdsfdss", "9481", "vill2e", "mail2", "69686696"));
		//JFrame p1 = new preparateur();

	}*/
}