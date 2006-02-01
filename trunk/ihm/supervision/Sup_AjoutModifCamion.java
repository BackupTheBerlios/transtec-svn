package ihm.supervision;

import java.awt.*;
import java.util.Vector;
import java.awt.event.*;
import javax.swing.*;

import donnees.Camion;
import donnees.Entrepot;
import accesBDD.AccesBDDCamion;
import accesBDD.AccesBDDEntrepot;

// Invite d'ajout/modification d'un camion
public class Sup_AjoutModifCamion extends JFrame implements ActionListener{
	
	private final static String [] TITRES = {"Disponible" , "En réparation" , "En livraison"};
	private Vector vectOrigines = new Vector();
	private Vector vectDestinations = new Vector();
	
	private JTextField textNumero = new JTextField(15);
	private JComboBox comboDispo = new JComboBox(TITRES);
	private JTextField textVolume = new JTextField(15);
	private JComboBox comboOrigine;
	private JComboBox comboDestination;
	private JTextField textWarning = new JTextField(15);
	private JButton boutValider = new JButton();
	private JButton boutAnnuler = new JButton("Annuler");
	private Camion camion;
	private Sup_OngletCamion parent;
	
	private AccesBDDCamion tableCamions;
	private AccesBDDEntrepot tableEntrepots = new AccesBDDEntrepot();
	
	//Constructeur
	public Sup_AjoutModifCamion(Camion c, Sup_OngletCamion parent, AccesBDDCamion tableCamions){
		super("");
		
		//Comportement lors de la fermeture
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				boutAnnuler.doClick();
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
		
		// On récupère le contenu des paramètres
		this.tableCamions = tableCamions;
		this.parent = parent;
		
		// On initialise les listes d'entrepôts
		try{
			vectOrigines=tableEntrepots.lister();
			vectDestinations=tableEntrepots.lister();			
		}
		catch(Exception ev){
			System.out.println(ev.getMessage());
		}
		
		// On initialise les listes de choix d'entrepôts
		comboOrigine = new JComboBox(vectOrigines);
		comboDestination = new JComboBox(vectDestinations);
		
		// Titres des informations à saisir
		JPanel panneauLabels = new JPanel(new GridLayout(6,1,5,5));
		panneauLabels.add(new JLabel("Numéro :"));
		panneauLabels.add(new JLabel("Disponibilité :"));
		panneauLabels.add(new JLabel("Volume :"));
		panneauLabels.add(new JLabel("Origine :"));
		panneauLabels.add(new JLabel("Destination :"));
		
		// Champs de saisie des informations
		JPanel panneauSaisie = new JPanel(new GridLayout(6,1,5,5));
		panneauSaisie.add(textNumero);
		panneauSaisie.add(comboDispo);
		panneauSaisie.add(textVolume);
		panneauSaisie.add(comboOrigine);
		panneauSaisie.add(comboDestination);
		
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
		
		// On rajoute un peu d'espace autour des panneaux
		((JComponent)getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Si on est dans le cas d'une modification
		if(c!= null){
			// On initialise les champs texte
			textNumero.setText(c.getNumero());
			comboDispo.setSelectedIndex(c.getDisponibilite().intValue());
			textVolume.setText(c.getVolume().toString());
			comboOrigine.setSelectedItem(c.getOrigine());
			comboDestination.setSelectedItem(c.getDestination());
		}
		// Si c'est un ajout on positionne les entrepôts sur la ligne vide par défaut
		else{
			try{
				Entrepot ent = tableEntrepots.rechercher(new Integer(0));
				comboOrigine.setSelectedItem(ent);
				comboDestination.setSelectedItem((Object)ent);
			}
			catch(Exception ex){
				System.out.println(ex.getMessage());
			}
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
					// Cas d'un ajout de camion
					if(boutValider.getText().equals("Ajouter")){
						// Ecriture dans la base de données
						camion.setId(tableCamions.ajouter(this.getCamion()));
	
						// Mise à jour du tableau
						parent.ajouterLigne(camion.toVector());						
					}
					// Cas d'une modification de camion existant
					else{
						// Mise à jour du tableau
						parent.modifierLigne(this.getCamion().toVector());
						
						// Ecriture dans la base de données
						tableCamions.modifier(this.getCamion());
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
	private Camion getCamion(){
		camion.setNumero(textNumero.getText());
		camion.setDisponibilite(new Integer(comboDispo.getSelectedIndex()));
		camion.setVolume(new Integer(textVolume.getText().trim()));
		camion.setOrigine((Entrepot)comboOrigine.getSelectedItem());
		camion.setDestination((Entrepot)comboDestination.getSelectedItem());

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
//		else if(comboOrigine.getSelectedIndex()==-1) setWarning("Origine");
//		else if(comboDestination.getSelectedIndex()==-1) setWarning("Destination");
		else ret = true;

		return ret;
	}

	// Ajoute un message d'erreur à la boite de dialogue si un champs est mal renseigné
	private void setWarning(String s){
		textWarning.setText("Le champs \""+s+"\" est mal renseigné.");
		textWarning.updateUI();
	}
}
