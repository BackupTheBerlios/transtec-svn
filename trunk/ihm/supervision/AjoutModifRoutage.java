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
public class AjoutModifRoutage extends JFrame implements ActionListener{
	
	private JComboBox comboOrigine;
	private JComboBox comboDestination;
	private JComboBox comboIntermed;
	private JTextField textDistance = new JTextField(15);
	private JTextField textWarning = new JTextField(15);
	private JButton boutValider = new JButton();
	protected JButton boutAnnuler = new JButton("Annuler");
	private Route route;
	private OngletRoutage parent;
	private Vector vectOrigines = new Vector();
	private Vector vectDestinations = new Vector();
	private Vector vectIntermed = new Vector();
	private AccesBDDEntrepot tableEntrepots = new AccesBDDEntrepot();
	private AccesBDDRoutage tableRoutage;
	
	//Constructeur
	public AjoutModifRoutage(Route r, OngletRoutage parent, AccesBDDRoutage tableRoutage){
		super("");
		
		//Comportement lors de la fermeture
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				boutAnnuler.doClick();
			}
		};
		addWindowListener(l);

		// On indique le titre de la fenêtre selon le cas de figure : modification ou ajout
		if(r!=null){
			setTitle("Modification d'une route");
			boutValider.setText("Modifier");
			route = r;
		}
		else{
			setTitle("Ajout d'une route");
			boutValider.setText("Ajouter");
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
		JPanel panneauLabels = new JPanel(new GridLayout(4,1,5,5));
		panneauLabels.add(new JLabel("Origine :"));
		panneauLabels.add(new JLabel("Destination :"));
		panneauLabels.add(new JLabel("Intermédiaire :"));
		panneauLabels.add(new JLabel("Distance :"));
		
		// Champs de saisie des informations
		JPanel panneauSaisie = new JPanel(new GridLayout(4,1,5,5));
		panneauSaisie.add(comboOrigine);
		panneauSaisie.add(comboDestination);
		panneauSaisie.add(comboIntermed);
		panneauSaisie.add(textDistance);
		
		// Boutons d'actions : Valider/Modifier et Annuler
		JPanel panneauBoutons = new JPanel(new GridLayout(1,2,15,15));
		boutValider.addActionListener(this);
		boutAnnuler.addActionListener(this);
		panneauBoutons.add(boutValider);
		panneauBoutons.add(boutAnnuler);
		
		// Champ d'avertissement en cas de saisie incomplète
		JPanel panneauWarning = new JPanel(new GridLayout(1,1,5,5));
		textWarning.setEditable(false);
		textWarning.setForeground(Color.RED);
		textWarning.setHorizontalAlignment(JTextField.CENTER);
		textWarning.setBorder(BorderFactory.createLineBorder(Color.black));
		panneauWarning.add(textWarning);

		// Panel regroupant les labels et les champs de saisie
		JPanel panneauHaut = new JPanel(new BorderLayout(5,5));
		panneauHaut.add(panneauLabels,BorderLayout.WEST);
		panneauHaut.add(panneauSaisie,BorderLayout.CENTER);

		// On ajoute tous les panneaux secondaires au panneau principal
		getContentPane().setLayout(new BorderLayout(20,20));
		getContentPane().add(panneauHaut,BorderLayout.NORTH);
		getContentPane().add(panneauBoutons,BorderLayout.CENTER);
		getContentPane().add(panneauWarning,BorderLayout.SOUTH);

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
		if(source==boutValider){
			if(verifChamps()){
				try{
					// Cas d'un ajout de route
					if(boutValider.getText().equals("Ajouter")){
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

	// Ajoute un message d'erreur à la boite de dialogue si un champs est mal renseigné
	private void setWarning(String s){
		textWarning.setText("Le champs \""+s+"\" est mal renseigné.");
		textWarning.updateUI();
	}
}
