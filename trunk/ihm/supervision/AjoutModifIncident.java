package ihm.supervision;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import accesBDD.AccesBDDIncident;
import donnees.Incident;


// Invite de modification d'un incident
public class AjoutModifIncident extends AjoutModif implements ActionListener{
	
	protected final static String [] ETATS = {"Non traité" , "En cours" , "Traîté"};
	
	protected JTextField textColis = new JTextField(20);
	protected JTextField textDate = new JTextField(20);
	protected JComboBox comboEtat = new JComboBox(ETATS);
	protected JTextArea textDescription = new JTextArea(10,2);
	protected JTextField textUtilisateur = new JTextField(20);
	protected JTextField textType = new JTextField(20);
	private JPanel panDescription = new JPanel();
	private JPanel panneauInter = new JPanel();

	protected Incident incid;
	protected OngletIncident parent;
	
	private AccesBDDIncident tableIncidents; 
	
	//Constructeur avec paramètres
	public AjoutModifIncident(Incident incid, OngletIncident parent, AccesBDDIncident tableIncidents){
		super();
		
		// On récupère les variables saisies en paramètres
		this.incid = incid;
		this.parent = parent;
		this.tableIncidents = tableIncidents;
		
		// On définit la mise en forme
		panneauHaut.setLayout(new BoxLayout(panneauHaut,BoxLayout.Y_AXIS));
		panneauHaut.removeAll();
		
		// Titres des informations à saisir
		panneauLabels.setLayout(new GridLayout(5,1,5,5));
		panneauLabels.add(new JLabel("Colis :"));
		panneauLabels.add(new JLabel("Date :"));
		panneauLabels.add(new JLabel("Etat :"));
		panneauLabels.add(new JLabel("Utilisateur :"));
		panneauLabels.add(new JLabel("Type :"));
		
		// Champs de saisie des informations
		panneauSaisie.setLayout(new GridLayout(5,1,5,5));
		panneauSaisie.add(textColis);
		panneauSaisie.add(textDate);
		panneauSaisie.add(comboEtat);
		panneauSaisie.add(textUtilisateur);		
		panneauSaisie.add(textType);
		
		// Panel regroupant les labels et les champs de saisie
		panneauInter.setLayout(new BoxLayout(panneauInter,BoxLayout.X_AXIS));
		panneauInter.setOpaque(false);
		panneauInter.add(panneauLabels);
		panneauInter.add(panneauSaisie);
		panneauInter.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// Panel contenant uniquement la zone de description
		textDescription.setPreferredSize(new Dimension(50,50));
		panDescription.setOpaque(false);
		panDescription.setLayout(new BoxLayout(panDescription,BoxLayout.Y_AXIS));
		JLabel labelDescription = new JLabel("Description :");
		labelDescription.setAlignmentX(Component.LEFT_ALIGNMENT);
		textDescription.setAlignmentX(Component.LEFT_ALIGNMENT);
		panDescription.add(labelDescription);
		panDescription.add(textDescription);
		panDescription.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// Panneau regroupant les zones de saisies
		panneauHaut.add(panneauInter);
		panneauHaut.add(panDescription);
		
		// boutons Modifier et Annuler
		boutModifier.addActionListener(this);
		boutAnnuler.addActionListener(this);
		panneauBoutons.add(boutModifier);
		panneauBoutons.add(boutAnnuler);
		
		if(incid != null){
			// On initialise les champs texte
			textColis.setText(incid.getColis().getCode_barre().toString());
			textDate.setText(incid.getDate().toString());
			comboEtat.setSelectedIndex(incid.getEtat().intValue());
			textDescription.setText(incid.getDescription());
			textUtilisateur.setText(incid.getUtilisateur().getPersonne().getNom().toString());
			textType.setText(Incident.constToString(incid.getType()));
		}

		pack();
		setAlwaysOnTop(true);
		setVisible(true);
	}

	// Gestion des actions liées au boutons
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();

		// Validation
		if(source==boutModifier){
			if(verifChamps()){				
				try{
					// Mise à jour du tableau
					parent.modifierLigne(this.getIncident().toVector());			
	
					// Ecriture dans la base de données
					tableIncidents.changerEtat(this.getIncident());
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
	private Incident getIncident(){
		incid.setEtat(new Integer(this.comboEtat.getSelectedIndex()));
		return incid;
	}

	// Vérification de saisie des champs
	private boolean verifChamps(){
		boolean ret = false;		
		boolean erreurColis = false;
		boolean erreurDate = false;
		
		// On vérifie que la valeur numérique soit correctement saisie
		try{
			new Integer(this.textColis.getText().trim());
			try{
				new Timestamp(System.currentTimeMillis());
			}
			catch(Exception e){
				erreurDate = true;
			}
		}
		catch(NumberFormatException e){
			 erreurColis = true;
		}

		// On vérifie que tous les champs sont remplis
		if(textColis.getText().equals("") || erreurColis) setWarning("Colis");
		else if(textDate.getText().equals("") || erreurDate) setWarning("Date");
		else if(textDescription.getText().equals("")) setWarning("Description");
		else if(textUtilisateur.getText().equals("")) setWarning("Utilisateur");
		else if(textType.getText().equals("")) setWarning("Type");
		else ret = true;

		return ret;
	}
}
