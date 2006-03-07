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
	protected AccesBDDCamion tableCamions = new AccesBDDCamion();
	protected AccesBDDColis tableColis = new AccesBDDColis();
	protected AccesBDDPreparation tablePreparations = new AccesBDDPreparation();
	protected AccesBDDUtilisateur tableUtilisateurs = new AccesBDDUtilisateur();

	private final static int DEBUT = 0;
	private final static int CHOIX = 1;
	private final static int FIN = 2;

	public final static int AUCUN = 0;
	public final static int RADIN = 1;
	public final static int PERENOEL = 2;

	private int ecranActuel = DEBUT;

	public Sup_OngletRepartition(){
		// Mise en forme initiale
		setOpaque(false);
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

		// Titre de l'onglet
		titre = new JLabel();
		titre.setText("Disponibilit�s");
		titre.setAlignmentX(Box.LEFT_ALIGNMENT);

		// Panel d'en-t�te
		panTitre= new JPanel();
		panTitre.setLayout(new BoxLayout(panTitre,BoxLayout.LINE_AXIS));
		panTitre.add(titre);
		panTitre.add(Box.createHorizontalGlue());
		panTitre.setAlignmentY(Box.TOP_ALIGNMENT);
		panTitre.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 50));
		panTitre.setOpaque(false);
		add(panTitre);

		// Cr�ation du panel de d�but
		panDonneesDebut = new Sup_OngletRepartitionDebut(this);

		// Cr�ation du panel de choix
		panDonneesChoix = new Sup_OngletRepartitionChoix();

		// Cr�ation du panel de fin
		panDonneesFin = new Sup_OngletRepartitionFin(this);

		// Cr�ation du panel � contenu variable
		panDonnees = new JPanel(new CardLayout(10,10));
		panDonnees.add(panDonneesDebut,"Disponibilit�s");
		panDonnees.add(panDonneesChoix,"Choix d'un algorithme de r�partition");
		panDonnees.add(panDonneesFin,"R�partition");
		panDonnees.setAlignmentY(Box.CENTER_ALIGNMENT);
		panDonnees.setOpaque(false);
		layoutPanDonnees = (CardLayout)panDonnees.getLayout();
		add(panDonnees);
		
		// Mise en page du panel des boutons
		panBoutons = new JPanel();
		panBoutons.setOpaque(false);
		panBoutons.setLayout(new BoxLayout(panBoutons,BoxLayout.X_AXIS));
		panBoutons.setBorder(BorderFactory.createEmptyBorder(40, 50, 20, 50));

		// Bouton Retour
		boutRetour.setSize(100,20);
		boutRetour.setAlignmentX(Box.LEFT_ALIGNMENT);
		boutRetour.addActionListener(this);
		boutRetour.setVisible(false);
		panBoutons.add(boutRetour);

		// Espace entre les boutons
		panBoutons.add(Box.createRigidArea(new Dimension(100,0)));

		// Bouton Suite
		boutSuite.setSize(100,20);
		boutSuite.setAlignmentX(Box.RIGHT_ALIGNMENT);
		boutSuite.addActionListener(this);
		panBoutons.add(boutSuite);
		
		panBoutons.setAlignmentY(Box.BOTTOM_ALIGNMENT);
		add(panBoutons);
	}
	
	// R�partition des chargements � l'aide de l'algorithme s�lectionn�
	private void repartirChargements(){

		// On r�cup�re l'algorithme choisi
		switch(panDonneesChoix.choixAlgoRadio()){
		case AUCUN:
			//panDonneesFin.affTabPreparationsSansAlgo();
			break;
			
		case RADIN:
			
			break;
			
		case PERENOEL:
			PereNoel.calculer(listeCamions,listeVolumesDestinations);
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
