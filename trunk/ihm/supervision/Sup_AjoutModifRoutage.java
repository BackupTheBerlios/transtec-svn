package ihm.supervision;

import java.awt.event.*;
import javax.swing.*;

import donnees.Camion;

// Invite d'ajout/modification d'un camion
public class Sup_AjoutModifRoutage extends JFrame implements ActionListener{
	
	private final static String [] TITRES = {"Disponible" , "En livraison" , "En réparation"};
	
	private JTextField textNumero = new JTextField(15);
	private JComboBox comboDispo = new JComboBox(TITRES);
	private JTextField textVolume = new JTextField(15);
	private JTextField textChauffeur = new JTextField(15);
	private JTextField textDestination = new JTextField(15);
	private JTextField textAppartenance = new JTextField(15);
	private JTextField textWarning = new JTextField(15);
	private JButton boutValider = new JButton();
	private JButton boutAnnuler = new JButton("Annuler");
	private Camion camion;
	public boolean modif = false;
	private Sup_OngletRoutage parent;
	
	//Constructeur
	public Sup_AjoutModifRoutage(Camion c, Sup_OngletRoutage parent){
		super("");
		/*
		//Comportement lors de la fermeture
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				setVisible(false);
				modif = false;
			}
		};
		addWindowListener(l);

		// On indique le titre de la fenêtre selon le cas de figure : modification ou ajout
		if(c!=null){
			setTitle("Modification d'un camion");
			boutValider.setText("Modifier");
			camion = c;
		}
		else{
			setTitle("Ajout d'un camion");
			boutValider.setText("Ajouter");
			camion = new Camion();
		}
		
		this.parent = parent;
	
		// Titres des informations à saisir
		JPanel panneauLabels = new JPanel(new GridLayout(6,1,5,5));
		panneauLabels.add(new JLabel("Numéro :"));
		panneauLabels.add(new JLabel("Disponibilité :"));
		panneauLabels.add(new JLabel("Volume :"));
		panneauLabels.add(new JLabel("Chauffeur :"));
		panneauLabels.add(new JLabel("Destination :"));
		panneauLabels.add(new JLabel("Appartenance :"));
		
		// Champs de saisie des informations
		JPanel panneauSaisie = new JPanel(new GridLayout(6,1,5,5));
		panneauSaisie.add(textNumero);
		panneauSaisie.add(comboDispo);
		panneauSaisie.add(textVolume);
		panneauSaisie.add(textChauffeur);
		panneauSaisie.add(textDestination);
		panneauSaisie.add(textAppartenance);
		
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
		if(c!= null){
			// On initialise les champs texte
			textNumero.setText(c.numero);
			comboDispo.setSelectedItem(c.dispo);
			textVolume.setText(c.volume.toString());
			textChauffeur.setText(c.chauffeur);
			textDestination.setText(c.destination);
			textAppartenance.setText(c.origine);
		}

		pack();
		setAlwaysOnTop(true);
		setVisible(true);*/
	}

	// Gestion des actions liées au boutons
	public void actionPerformed(ActionEvent e){
/*		Object source = e.getSource();

		// Validation
		if(source==boutValider){
			if(verifChamps()){
				// Cas d'un ajout de camion
				if(boutValider.getText().equals("Ajouter")){
					parent.ajouterLigne(this.getCamion().toVector());
				}
				// Cas d'une modification de camion existant
				else{
					parent.modifierLigne(this.getCamion().toVector());
				}				
				// On masque la fenetre
				this.setVisible(false);
				this.dispose();
			}
		}
		// Annulation, on masque simplement la fenêtre
		else if(source==boutAnnuler){
			this.setVisible(false);
			this.dispose();
		}
*/	}
/*
	//Méthodes permettant d'obtenir le contenu des champs
	private Camion getCamion(){
		camion.numero = (String)this.textNumero.getText();
		camion.dispo = (String)this.comboDispo.getSelectedItem();
		camion.volume = new Integer(this.textVolume.getText().trim());
		camion.chauffeur = (String)this.textChauffeur.getText();
		camion.destination = (String)this.textDestination.getText();
		camion.origine = (String)this.textAppartenance.getText();

		return camion;
	}

	// Vérification de saisie des champs
	private boolean verifChamps(){
		boolean ret = false;
		boolean erreurVolume = false;
		
		// On vérifie que la valeur numérique soit correctement saisie
		try{
			new Integer(this.textVolume.getText().trim());
		}
		catch(NumberFormatException e){
			erreurVolume=true;
		}

		// On vérifie que tous les champs sont remplis
		if(textNumero.getText().equals("")) setWarning("Numéro");
		else if(textVolume.getText().equals("") || erreurVolume) setWarning("Volume");
		else if(textChauffeur.getText().equals("")) setWarning("Chauffeur");
		else if(textDestination.getText().equals("")) setWarning("Destination");
		else if(textAppartenance.getText().equals("")) setWarning("Appartenance");
		else ret = true;

		return ret;
	}

	// Ajoute un message d'erreur à la boite de dialogue si un champs est mal renseigné
	private void setWarning(String s){
		textWarning.setText("Le champs \""+s+"\" est mal renseigné.");
		textWarning.updateUI();
	}
	
*/
}
