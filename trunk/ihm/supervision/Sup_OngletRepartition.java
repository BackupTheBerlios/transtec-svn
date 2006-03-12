package ihm.supervision;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import accesBDD.AccesBDDCamion;
import accesBDD.AccesBDDColis;
import accesBDD.AccesBDDPreparation;
import accesBDD.AccesBDDUtilisateur;
import algos.*;

public class Sup_OngletRepartition extends JPanel implements ActionListener{
	private JLabel titre;
	private JPanel panTitre;
	private CardLayout layoutPanDonnees;
	private JPanel panDonnees;
	private Sup_OngletRepartitionDebut panDonneesDebut;
	private Sup_OngletRepartitionChoix panDonneesChoix;
	private Sup_OngletRepartitionFin panDonneesFin;
	private JPanel panBoutons;
	private JButton boutSuite = new JButton("Suite  >");
	private JButton boutRetour = new JButton("<  Retour");
	protected Vector listePreparateurs,listeVolumesDestinations,listeCamions;	
	protected Vector resultatAlgos = new Vector();
	protected AccesBDDCamion tableCamions = new AccesBDDCamion();
	protected AccesBDDColis tableColis = new AccesBDDColis();
	protected AccesBDDPreparation tablePreparations = new AccesBDDPreparation();
	protected AccesBDDUtilisateur tableUtilisateurs = new AccesBDDUtilisateur();

	private final static int DEBUT = 0;
	private final static int CHOIX = 1;
	private final static int FIN = 2;

	public final static int AUCUN = 0;
	public final static int RADIN = 1;
	public final static int PERE_NOEL = 2;

	private int ecranActuel = DEBUT;

	public Sup_OngletRepartition(){
		// Mise en forme initiale
		setOpaque(false);
		setLayout(null);

		// Titre de l'onglet
		titre = new JLabel();
		titre.setText("Disponibilités");

		// Panel d'en-tête
		titre.setBounds(10,14,300,20);
		titre.setFont(new Font("Verdana", Font.BOLD, 14));
		add(titre);

		// Création du panel de début
		panDonneesDebut = new Sup_OngletRepartitionDebut(this);

		// Création du panel de choix
		panDonneesChoix = new Sup_OngletRepartitionChoix();

		// Création du panel de fin
		panDonneesFin = new Sup_OngletRepartitionFin(this);

		// Création du panel à contenu variable
		panDonnees = new JPanel(new CardLayout(10,10));
		panDonnees.setBounds(1,37,738,483);
		panDonnees.add(panDonneesDebut,"Disponibilités");
		panDonnees.add(panDonneesChoix,"Choix d'un algorithme de répartition");
		panDonnees.add(panDonneesFin,"Répartition");
		panDonnees.setOpaque(false);
		layoutPanDonnees = (CardLayout)panDonnees.getLayout();
		add(panDonnees);
		
		// Mise en page du panel des boutons
		panBoutons = new JPanel();
		panBoutons.setBounds(748,37,177,360);
		panBoutons.setOpaque(false);
		panBoutons.setLayout(null);

		// Bouton Retour
		boutRetour.setBounds(10,320,80,20);
		boutRetour.addActionListener(this);
		boutRetour.setVisible(false);
		panBoutons.add(boutRetour);

		// Espace entre les boutons
		panBoutons.add(Box.createRigidArea(new Dimension(100,0)));

		// Bouton Suite
		boutSuite.setBounds(100,320,80,20);
		boutSuite.addActionListener(this);
		panBoutons.add(boutSuite);
		
		add(panBoutons);
	}
	
	// Permet de définir une image de fond
	public void paintComponent(Graphics g)	{
		ImageIcon img = new ImageIcon("images/supervision/bg_onglet.png");
		g.drawImage(img.getImage(), 0, 0, null);
		super.paintComponent(g);
	}
	
	// Répartition des chargements à l'aide de l'algorithme sélectionné
	private void repartirChargements(){

		// On récupère l'algorithme choisi
		switch(panDonneesChoix.choixAlgoRadio()){
		case AUCUN:
			panDonneesFin.construireTableau(AUCUN);
			break;
			
		case RADIN:
			panDonneesFin.construireTableau(RADIN);
			break;
			
		case PERE_NOEL:
			resultatAlgos = PereNoel.calculer(listeCamions,listeVolumesDestinations);
			panDonneesFin.construireTableau(PERE_NOEL);
			break;			
		}
	}
	
	// Gestion des actions liées aux boutons
	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();
		
		// Passage à l'écran suivant
		if(source==boutSuite){			
			// On définit les actions selon l'écran actuel
			switch(ecranActuel){
			// Ecran de début
			case DEBUT:
				ecranActuel++;
				boutRetour.setVisible(true);
				layoutPanDonnees.next(panDonnees);
				titre.setText("Choix d'un algorithme de répartition");
				break;
				
				// Ecran de choix d'algorithme
			case CHOIX:
				ecranActuel++;
				boutSuite.setText("Publier");
				//boutRetour.setEnabled(false);
				layoutPanDonnees.next(panDonnees);
				titre.setText("Répartition");
				
				// Répartition des chargements à l'aide de l'algorithme choisi
				repartirChargements();
				
				
				break;
				
				// Ecran d'affichage final
			case FIN:
				// On publie la liste des chargements répartis
				panDonneesFin.publierPreparations();
				break;
			}				
		}
		// Retour à l'écran précédent
		else if(source==boutRetour){			
			// On définit les actions selon l'écran actuel
			switch(ecranActuel){
			// Ecran de début
			case DEBUT:
				break;
				// Ecran de choix d'algorithme
			case CHOIX:
				ecranActuel--;
				boutRetour.setVisible(false);
				layoutPanDonnees.previous(panDonnees);
				titre.setText("Disponibilités");
				break;
				// Ecran d'affichage final
			case FIN:
				ecranActuel--;
				boutSuite.setText("Suite  >");
				layoutPanDonnees.previous(panDonnees);
				titre.setText("Choix d'un algorithme de répartition");
				break;
			}			
		}
	}
	
	// Permet de valider les mises à jour dans les cellules d'un tableau
	public static void traverseAllCells(JTable table)
	{
		table.setSurrendersFocusOnKeystroke(true);
		for (int x = 0; x < table.getRowCount(); x++)
		{
			for (int y = 0; y < table.getColumnCount(); y++)
			{
				table.editCellAt(x ,y);
			}
		}
	}
}
