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
		titre.setText("Disponibilit�s");

		// Panel d'en-t�te
		titre.setBounds(10,14,300,20);
		titre.setFont(new Font("Verdana", Font.BOLD, 14));
		add(titre);

		// Cr�ation du panel de d�but
		panDonneesDebut = new Sup_OngletRepartitionDebut(this);

		// Cr�ation du panel de choix
		panDonneesChoix = new Sup_OngletRepartitionChoix();

		// Cr�ation du panel de fin
		panDonneesFin = new Sup_OngletRepartitionFin(this);

		// Cr�ation du panel � contenu variable
		panDonnees = new JPanel(new CardLayout(10,10));
		panDonnees.setBounds(1,37,738,483);
		panDonnees.add(panDonneesDebut,"Disponibilit�s");
		panDonnees.add(panDonneesChoix,"Choix d'un algorithme de r�partition");
		panDonnees.add(panDonneesFin,"R�partition");
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
	
	// Permet de d�finir une image de fond
	public void paintComponent(Graphics g)	{
		ImageIcon img = new ImageIcon("images/supervision/bg_onglet.png");
		g.drawImage(img.getImage(), 0, 0, null);
		super.paintComponent(g);
	}
	
	// R�partition des chargements � l'aide de l'algorithme s�lectionn�
	private void repartirChargements(){

		// On r�cup�re l'algorithme choisi
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
	
	// Gestion des actions li�es aux boutons
	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();
		
		// Passage � l'�cran suivant
		if(source==boutSuite){			
			// On d�finit les actions selon l'�cran actuel
			switch(ecranActuel){
			// Ecran de d�but
			case DEBUT:
				ecranActuel++;
				boutRetour.setVisible(true);
				layoutPanDonnees.next(panDonnees);
				titre.setText("Choix d'un algorithme de r�partition");
				break;
				
				// Ecran de choix d'algorithme
			case CHOIX:
				ecranActuel++;
				boutSuite.setText("Publier");
				//boutRetour.setEnabled(false);
				layoutPanDonnees.next(panDonnees);
				titre.setText("R�partition");
				
				// R�partition des chargements � l'aide de l'algorithme choisi
				repartirChargements();
				
				
				break;
				
				// Ecran d'affichage final
			case FIN:
				// On publie la liste des chargements r�partis
				panDonneesFin.publierPreparations();
				break;
			}				
		}
		// Retour � l'�cran pr�c�dent
		else if(source==boutRetour){			
			// On d�finit les actions selon l'�cran actuel
			switch(ecranActuel){
			// Ecran de d�but
			case DEBUT:
				break;
				// Ecran de choix d'algorithme
			case CHOIX:
				ecranActuel--;
				boutRetour.setVisible(false);
				layoutPanDonnees.previous(panDonnees);
				titre.setText("Disponibilit�s");
				break;
				// Ecran d'affichage final
			case FIN:
				ecranActuel--;
				boutSuite.setText("Suite  >");
				layoutPanDonnees.previous(panDonnees);
				titre.setText("Choix d'un algorithme de r�partition");
				break;
			}			
		}
	}
	
	// Permet de valider les mises � jour dans les cellules d'un tableau
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
