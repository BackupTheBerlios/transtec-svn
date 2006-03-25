package ihm.supervision;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import accesBDD.AccesBDDCamion;
import accesBDD.AccesBDDColis;
import accesBDD.AccesBDDPreparation;
import accesBDD.AccesBDDUtilisateur;

import donnees.Entrepot;

import ihm.Bouton;

import algos.*;

public class OngletRepartition extends JPanel implements ActionListener{
	private JLabel titre;
	private CardLayout layoutPanDonnees;
	private JPanel panDonnees;
	private OngletRepartitionDebut panDonneesDebut;
	private OngletRepartitionChoix panDonneesChoix;
	private OngletRepartitionFin panDonneesFin;
	private JPanel panBoutons;
	private Bouton boutSuite = new Bouton("images/supervision/bouton_suivant.png","images/supervision/bouton_suivant_appuyer.png");
	private Bouton boutRetour = new Bouton("images/supervision/bouton_retour.png","images/supervision/bouton_retour_appuyer.png");
	private Bouton boutUpdate = new Bouton("images/supervision/bouton_actualiser.png","images/supervision/bouton_actualiser_appuyer.png");
	private Bouton boutPublier = new Bouton("images/supervision/bouton_publier.png","images/supervision/bouton_publier_appuyer.png");
	protected Vector listePreparateurs,listeVolumesDestinations,listeCamions;	
	protected Vector resultatAlgos = new Vector();
	protected AccesBDDCamion tableCamions = new AccesBDDCamion();
	protected AccesBDDColis tableColis = new AccesBDDColis();
	protected AccesBDDPreparation tablePreparations = new AccesBDDPreparation();
	protected AccesBDDUtilisateur tableUtilisateurs = new AccesBDDUtilisateur();
	protected Entrepot entActuel;
	
	private final static int DEBUT = 0;
	private final static int CHOIX = 1;
	private final static int FIN = 2;

	public final static int AUCUN = 0;
	public final static int RADIN = 1;
	public final static int PERE_NOEL = 2;

	private int ecranActuel = DEBUT;

	public OngletRepartition(Entrepot entActuel){
		
		// Transmission de l'entrepot o� l'on se trouve
		this.entActuel = entActuel;
		
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
		panDonneesDebut = new OngletRepartitionDebut(this);

		// Cr�ation du panel de choix
		panDonneesChoix = new OngletRepartitionChoix();

		// Cr�ation du panel de fin
		panDonneesFin = new OngletRepartitionFin(this);

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
		boutRetour.setBounds(15,280,65,50);
		boutRetour.addActionListener(this);
		boutRetour.setVisible(false);
		panBoutons.add(boutRetour);

		// Bouton Suite
		boutSuite.setBounds(93,280,65,50);
		boutSuite.addActionListener(this);
		panBoutons.add(boutSuite);
		
		// Bouton de mise � jour
		boutUpdate.setBounds(21,23,165,41);
		boutUpdate.addActionListener(this);
		panBoutons.add(boutUpdate);
		
		// Bouton de publication
		boutPublier.setBounds(21,23,165,41);
		boutPublier.addActionListener(this);
		boutPublier.setVisible(false);
		panBoutons.add(boutPublier);
		
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
			resultatAlgos = Radin.calculer(listeCamions,listeVolumesDestinations);
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
				boutUpdate.setVisible(false);
				layoutPanDonnees.next(panDonnees);
				titre.setText("Choix d'un algorithme de r�partition");
				break;
				
				// Ecran de choix d'algorithme
			case CHOIX:
				ecranActuel++;
				boutPublier.setVisible(true);
				boutSuite.setVisible(false);
				layoutPanDonnees.next(panDonnees);
				titre.setText("R�partition");
				
				// R�partition des chargements � l'aide de l'algorithme choisi
				repartirChargements();
				
				
				break;
				
				// Ecran d'affichage final
			case FIN:
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
				boutUpdate.setVisible(true);
				layoutPanDonnees.previous(panDonnees);
				titre.setText("Disponibilit�s");
				break;
				// Ecran d'affichage final
			case FIN:
				ecranActuel--;
				layoutPanDonnees.previous(panDonnees);
				titre.setText("Choix d'un algorithme de r�partition");
				boutPublier.setVisible(false);
				boutSuite.setVisible(true);
				break;
			}			
		}
		else if(source==boutUpdate){
			panDonneesDebut.updateTableaux();
		}
		else if(source==boutPublier){
			// On publie la liste des chargements r�partis
			if(panDonneesFin.verifierSaisie())
				panDonneesFin.publierPreparations();
		}
	}
	
	// Permet de valider les mises � jour dans les cellules d'un tableau
/*	public static void traverseAllCells(JTable table)
	{
		table.setSurrendersFocusOnKeystroke(true);
		for (int x = 0; x < table.getRowCount(); x++)
		{
			for (int y = 0; y < table.getColumnCount(); y++)
			{
				table.editCellAt(x ,y);
			}
		}
	}*/
}
