package ihm.preparation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Vector;

import ihm.AffichageImage;
import ihm.Bouton;
import ihm.FenetreType;
import ihm.ModeleTable;
import ihm.TableSorter;

import javax.swing.*;

import accesBDD.AccesBDDChargement;
import accesBDD.AccesBDDPlan;

import donnees.Colis;
import donnees.Utilisateur;

public class PlanChargement extends JFrame implements ActionListener{
	private FenetreType fenetre;
	private Bouton imprimer, annuler;
	private Utilisateur utilisateur;
	private ModeleTable modColis;
	private TableSorter sorter1;
	private JTable tableColis;
	
	public PlanChargement(Utilisateur utilisateur, Integer idChargement) {
		// Création graphique de la fenêtre
		setTitle("Plan de chargement");
		setSize(1024,768);
		setUndecorated(true);
		fenetre=new FenetreType(utilisateur,"images/preparation/fenetre_planBackground.png");
		setContentPane(fenetre);
		fenetre.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Mémorisation de l'utilisateur
		this.utilisateur=utilisateur;
		
		// Mise en place du menu
		this.imprimer=new Bouton("images/icones/imprimer.png","images/icones/imprimer_inv.png");
		this.imprimer.setBounds(805, 265, 116, 46);
		this.fenetre.add(this.imprimer);
		this.imprimer.addActionListener(this);
		this.annuler=new Bouton("images/icones/annuler.png","images/icones/annuler_inv.png");
		this.annuler.setBounds(800, 331, 116, 46);
		this.fenetre.add(this.annuler);
		this.annuler.addActionListener(this);
		
		
		try{
			//	On recherche les fichiers dans la BDD
			Blob fichiers[]=new AccesBDDPlan().rechercher(idChargement);
		
			// Mise en place des vues
			AffichageImage image = new AffichageImage(fichiers[AccesBDDPlan.DESSUS].getBinaryStream());
			image.setBounds(247,251,257,129);
			this.fenetre.add(image);
			
			image = new AffichageImage(fichiers[AccesBDDPlan.GAUCHE].getBinaryStream());
			image.setBounds(247,424,257,129);
			this.fenetre.add(image);
			
			image = new AffichageImage(fichiers[AccesBDDPlan.FACE].getBinaryStream());
			image.setBounds(247,597,257,129);
			this.fenetre.add(image);
			
			image = new AffichageImage(fichiers[AccesBDDPlan.DESSOUS].getBinaryStream());
			image.setBounds(524,251,257,129);
			this.fenetre.add(image);
			
			image = new AffichageImage(fichiers[AccesBDDPlan.DROITE].getBinaryStream());
			image.setBounds(524,424,257,129);
			this.fenetre.add(image);
			
			image = new AffichageImage(fichiers[AccesBDDPlan.ARRIERE].getBinaryStream());
			image.setBounds(524,597,257,129);
			this.fenetre.add(image);
		}
		catch(SQLException e){
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Crétaion des colonnes
		Vector nomColonnes=new Vector();
        nomColonnes.add("Id");
        nomColonnes.add("Code barre");
        nomColonnes.add("Expéditeur");
        nomColonnes.add("Destinataire");
        nomColonnes.add("Origine");
        nomColonnes.add("Destination");
        nomColonnes.add("Entrepot en Cours");
        nomColonnes.add("Utilisateur");
        nomColonnes.add("Poids");
        nomColonnes.add("Date d'entrée");
        nomColonnes.add("Fragilité");
        nomColonnes.add("Modèle");
        nomColonnes.add("Modèle");
        nomColonnes.add("Valeur Déclarée");
        nomColonnes.add("Volume");
        nomColonnes.add("N°");
       
        Vector donneesColis=new Vector();
        // Vector conteannt les infos de la BDD
        try{
        	Vector liste=new AccesBDDChargement().listerColis(idChargement);
        	for(int i=0;i<liste.size();i++)
        		donneesColis.add(((Colis)liste.get(i)).toVector());
        }
        catch(SQLException e){
        	
        }
        
        // Création du tableau contenant les colis appartenant au chargement
        modColis = new ModeleTable(nomColonnes,donneesColis);
		//Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter1 = new TableSorter(modColis);
		// Création du tableau
		tableColis = new JTable(sorter1);
		// initialisation du Sorter
		sorter1.setTableHeader(tableColis.getTableHeader());
		
		//Aspect du tableau
		tableColis.setAutoCreateColumnsFromModel(true);
		tableColis.setOpaque(false);
		tableColis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// On supprime les colonnes inutiles
		tableColis.removeColumn(tableColis.getColumnModel().getColumn(0));
		for(int i=0;i<13;i++)
			tableColis.removeColumn(tableColis.getColumnModel().getColumn(1));
		
		
		//Construction du JScrollPane
		JScrollPane scrollPane1 = new JScrollPane(tableColis);
		tableColis.setPreferredScrollableViewportSize(new Dimension(800,150));
		scrollPane1.setBounds(56,251,174,477);
		scrollPane1.setOpaque(false);
		scrollPane1.getViewport().setOpaque(false);
		getContentPane().add(scrollPane1);
		
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ev) {
		Object source = ev.getSource();
		
		if(source==this.imprimer){
			//Impression
			dispose();
			new FenetrePrincipale(this.utilisateur).setVisible(true);
		}
		else if(source==this.annuler){
			dispose();
			new FenetrePrincipale(this.utilisateur).setVisible(true);
		}
	}
}
