package ihm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.sql.Date;
import java.util.Vector;
import javax.swing.*;

import donnees.Camion;
import donnees.*;

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
	
	
	public Prep_Fenetre_princ(Utilisateur u){
		
		//Constructeur de la fenetre
		super("ALBERT MUDA - Preparateur");
		Container ct = this.getContentPane();
		
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
		String destination = "Paris";
		JLabel champ_dest = new JLabel(destination);
		txt_dest.setBounds(110,50,100,20);
		champ_dest.setBounds(230,50,100,20);
		ct.add(txt_dest);
		ct.add(champ_dest);
		
		
		//Création du volume
		JLabel txt_vol = new JLabel("volume");
		String volume = "200 m3";
		JLabel champ_vol = new JLabel(volume);	
		txt_vol.setBounds(110,100,100,20);
		champ_vol.setBounds(230,100,100,20);
		ct.add(txt_vol);
		ct.add(champ_vol);

		
		//Création de la charge
		JLabel txt_charge = new JLabel("chargé");
		String chargé = "100 m3";
		JLabel champ_chargé = new JLabel(chargé);	
		txt_charge.setBounds(110,150,100,20);
		champ_chargé.setBounds(230,150,100,20);
		ct.add(txt_charge);
		ct.add(champ_chargé);
		
		//Création de la première ligne
		nomColonnes_cam.add("Camion");
		nomColonnes_cam.add("Volume restant");
		
//**********************APPEL A LA BDD************************************
		
		//On devra exporter les objets Camion associés au préparateur 
        //Création des autres lignes
		donnees_cam.addElement(new Camion(new Integer(0),"25TR76",new Integer(Camion.DISPONIBLE),new Integer(27),new Integer(0),new Integer(0),new Integer(2)).toVector());
		donnees_cam.addElement(new Camion(new Integer(1),"1013TW78",new Integer(Camion.LIVRAISON),new Integer(12),new Integer(1),new Integer(1),new Integer(1)).toVector());
		donnees_cam.addElement(new Camion(new Integer(2),"356LJ45",new Integer(Camion.REPARATION),new Integer(45),new Integer(0),new Integer(1),new Integer(2)).toVector());
		donnees_cam.addElement(new Camion(new Integer(3),"654LLL1",new Integer(Camion.DISPONIBLE),new Integer(6),new Integer(2),new Integer(2),new Integer(4)).toVector());
		donnees_cam.addElement(new Camion(new Integer(4),"M-AR1265",new Integer(Camion.LIVRAISON),new Integer(18),new Integer(1),new Integer(4),new Integer(0)).toVector());
    
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
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Prep_Fenetre_princ p = new Prep_Fenetre_princ();
		//JFrame p1 = new preparateur();

	}
}