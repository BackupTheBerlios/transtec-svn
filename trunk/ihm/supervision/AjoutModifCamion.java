package ihm.supervision;

import java.util.Vector;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

import donnees.Camion;
import donnees.Entrepot;
import accesBDD.AccesBDD;
import accesBDD.AccesBDDCamion;
import accesBDD.AccesBDDEntrepot;

// Invite d'ajout/modification d'un camion
public class AjoutModifCamion extends AjoutModif implements ActionListener{
	
	private final static String [] TITRES = {"Disponible" , "En réparation" , "En livraison"};
	private Vector vectOrigines = new Vector();
	private Vector vectDestinations = new Vector();
	
	private JTextField textNumero = new JTextField(15);
	private JComboBox comboDispo = new JComboBox(TITRES);
	private JTextField textLargeur = new JTextField(15);
	private JTextField textHauteur = new JTextField(15);
	private JTextField textProfondeur = new JTextField(15);
	private JComboBox comboOrigine;
	private JComboBox comboDestination;
	private Camion camion;
	private OngletCamion parent;
	
	private AccesBDDCamion tableCamions;
	private AccesBDDEntrepot tableEntrepots;
	
	//Constructeur
	public AjoutModifCamion(Camion c, OngletCamion parent, AccesBDDCamion tableCamions,AccesBDD accesBDD){
		super();
		this.tableEntrepots = new AccesBDDEntrepot(accesBDD);
		
		// On indique le titre de la fenêtre selon le cas de figure : modification ou ajout
		if(c!=null){
			boutModifier.addActionListener(this);
			panneauBoutons.add(boutModifier);
			camion = c;
		}
		else{
			boutAjouter.addActionListener(this);
			panneauBoutons.add(boutAjouter);
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
		panneauLabels.setLayout(new GridLayout(7,1,5,5));
		panneauLabels.add(new JLabel("Numéro :"));
		panneauLabels.add(new JLabel("Disponibilité :"));
		panneauLabels.add(new JLabel("Largeur :"));
		panneauLabels.add(new JLabel("Hauteur :"));
		panneauLabels.add(new JLabel("Profondeur :"));
		panneauLabels.add(new JLabel("Origine :"));
		panneauLabels.add(new JLabel("Destination :"));
		
		// Champs de saisie des informations
		panneauSaisie.setLayout(new GridLayout(7,1,5,5));
		panneauSaisie.add(textNumero);
		panneauSaisie.add(comboDispo);
		panneauSaisie.add(textLargeur);
		panneauSaisie.add(textHauteur);
		panneauSaisie.add(textProfondeur);		
		panneauSaisie.add(comboOrigine);
		panneauSaisie.add(comboDestination);
		
		// Bouton Annuler
		boutAnnuler.addActionListener(this);
		panneauBoutons.add(boutAnnuler);

		// Si on est dans le cas d'une modification
		if(c!= null){
			// On initialise les champs texte
			textNumero.setText(c.getNumero());
			comboDispo.setSelectedIndex(c.getDisponibilite().intValue());
			textLargeur.setText(c.getLargeur().toString());
			textHauteur.setText(c.getHauteur().toString());
			textProfondeur.setText(c.getProfondeur().toString());
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
		setSize(300,350);
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
					// Cas d'un ajout de camion
					if(source==boutAjouter){
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
		camion.setLargeur(new Float(textLargeur.getText().trim()));
		camion.setHauteur(new Float(textHauteur.getText().trim()));
		camion.setProfondeur(new Float(textProfondeur.getText().trim()));		
		camion.calculerVolume();
		camion.setVolumeDispo(camion.getVolume());
		camion.setOrigine((Entrepot)comboOrigine.getSelectedItem());
		camion.setDestination((Entrepot)comboDestination.getSelectedItem());

		return camion;
	}

	// Vérification de saisie des champs
	private boolean verifChamps(){
		boolean ret = false;
		boolean erreurLargeur = false;
		boolean erreurHauteur = false;
		boolean erreurProfondeur = false;
		
		// On vérifie que la valeur numérique soit correctement saisie
		try{
			new Float(this.textLargeur.getText().trim());			
			try{
				new Float(this.textHauteur.getText().trim());				
				try{
					new Float(this.textProfondeur.getText().trim());
				}
				catch(NumberFormatException e){
					erreurProfondeur=true;
				}
			}
			catch(NumberFormatException e){
				erreurHauteur=true;
			}
		}
		catch(NumberFormatException e){
			erreurLargeur=true;
		}

		// On vérifie que tous les champs sont remplis
		if(textNumero.getText().equals("")) setWarning("Numéro");
		else if(textLargeur.getText().equals("") || erreurLargeur) setWarning("Largeur");
		else if(textHauteur.getText().equals("") || erreurHauteur) setWarning("Hauteur");
		else if(textProfondeur.getText().equals("") || erreurProfondeur) setWarning("Profondeur");	
		else ret = true;

		return ret;
	}
}
