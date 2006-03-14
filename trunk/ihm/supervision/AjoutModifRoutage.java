package ihm.supervision;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;

import accesBDD.AccesBDDEntrepot;
import accesBDD.AccesBDDRoutage;
import donnees.Entrepot;
import donnees.Route;

// Invite d'ajout/modification d'un camion
public class AjoutModifRoutage extends AjoutModif implements ActionListener{
	
	private JComboBox comboOrigine;
	private JComboBox comboDestination;
	private JComboBox comboIntermed;
	private JTextField textDistance = new JTextField(15);

	private Route route;
	private OngletRoutage parent;
	private Vector vectOrigines = new Vector();
	private Vector vectDestinations = new Vector();
	private Vector vectIntermed = new Vector();
	private AccesBDDEntrepot tableEntrepots = new AccesBDDEntrepot();
	private AccesBDDRoutage tableRoutage;
	
	//Constructeur
	public AjoutModifRoutage(Route r, OngletRoutage parent, AccesBDDRoutage tableRoutage){
		super();
		
		// On indique le titre de la fenêtre selon le cas de figure : modification ou ajout
		if(r!=null){
			boutModifier.addActionListener(this);
			panneauBoutons.add(boutModifier);
			route = r;
		}
		else{
			boutAjouter.addActionListener(this);
			panneauBoutons.add(boutAjouter);
			route = new Route();
		}
		
		// On récupère le contenu des paramètres
		this.parent = parent;
		this.tableRoutage = tableRoutage;
		
		// On initialise les listes d'entrepôts
		try{
			vectOrigines=tableEntrepots.lister();
			vectDestinations=tableEntrepots.lister();	
			vectIntermed=tableEntrepots.lister();
		}
		catch(Exception ev){
			System.out.println(ev.getMessage());
		}
		
		// On initialise les listes de choix d'entrepôts
		comboOrigine = new JComboBox(vectOrigines);
		comboDestination = new JComboBox(vectDestinations);
		comboIntermed = new JComboBox(vectIntermed);
	
		// Titres des informations à saisir
		panneauLabels.setLayout(new GridLayout(4,1,5,5));
		panneauLabels.add(new JLabel("Origine :"));
		panneauLabels.add(new JLabel("Destination :"));
		panneauLabels.add(new JLabel("Intermédiaire :"));
		panneauLabels.add(new JLabel("Distance :"));
		
		// Champs de saisie des informations
		panneauSaisie.setLayout(new GridLayout(4,1,5,5));
		panneauSaisie.add(comboOrigine);
		panneauSaisie.add(comboDestination);
		panneauSaisie.add(comboIntermed);
		panneauSaisie.add(textDistance);
		
		// Bouton Annuler
		boutAnnuler.addActionListener(this);
		panneauBoutons.add(boutAnnuler);

		// Si on est dans le cas d'une modification
		if(r!= null){
			// On initialise les ComboBox et le champ texte
			comboOrigine.setSelectedItem(r.getOrigine());
			comboDestination.setSelectedItem(r.getDestination());
			comboIntermed.setSelectedItem(r.getIntermediaire());
			textDistance.setText(r.getDistance().toString());
		}

		pack();
		setAlwaysOnTop(true);
		setVisible(true);
	}

	// Gestion des actions liées aux boutons
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();

		// Validation
		if(source==boutAjouter || source==boutModifier){
			if(verifChamps()){
				try{
					// Cas d'un ajout de route
					if(source==boutAjouter){
						// Ecriture dans la base de données
						route.setId(tableRoutage.ajouter(this.getRoute()));
	
						// Mise à jour du tableau
						parent.ajouterLigne(route.toVector());						
					}
					// Cas d'une modification de route existante
					else{
						// Mise à jour du tableau
						parent.modifierLigne(this.getRoute().toVector());
						
						// Ecriture dans la base de données
						//tableRoutage.modifier(this.getRoute());
					}
				}
				catch(Exception ex){
					System.out.println(ex.getMessage());
				}
				finally{
					// On masque la fenetre
					this.setVisible(false);
					this.dispose();						
				}
			}	
		}
		// Annulation, on masque simplement la fenêtre
		else if(source==boutAnnuler){
			parent.setFenetreActive(true);
			this.setVisible(false);
			this.dispose();
		}
	}

	//Méthodes permettant d'obtenir le contenu des champs
	private Route getRoute(){
		route.setOrigine((Entrepot)comboOrigine.getSelectedItem());
		route.setDestination((Entrepot)comboDestination.getSelectedItem());
		route.setIntermediaire((Entrepot)comboIntermed.getSelectedItem());
		route.setDistance(new Float(textDistance.getText().trim()));

		return route;
	}

	// Vérification de saisie des champs
	private boolean verifChamps(){
		boolean ret = false;
		boolean erreurDistance = false;
		
		// On vérifie que la valeur numérique soit correctement saisie
		try{
			new Float(this.textDistance.getText().trim());
		}
		catch(NumberFormatException e){
			erreurDistance=true;
		}

		// On vérifie que tous les champs sont remplis
		if(((Entrepot)(comboOrigine.getSelectedItem())).getId().equals(new Integer(0))) setWarning("Origine");
		else if(((Entrepot)(comboDestination.getSelectedItem())).getId().equals(new Integer(0))) setWarning("Destination");
		else if(textDistance.getText().equals("") || erreurDistance) setWarning("Distance");
		else ret = true;

		return ret;
	}
}
