package ihm.supervision;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import javax.swing.*;

import accesBDD.AccesBDDIncident;

import donnees.Incident;


// Invite de modification d'un incident
public class Sup_AjoutModifIncident extends JFrame implements ActionListener{
	
	protected final static String [] ETATS = {"Non traité" , "En cours" , "Traîté"};
	
	protected JTextField textColis = new JTextField(20);
	protected JTextField textDate = new JTextField(20);
	protected JComboBox comboEtat = new JComboBox(ETATS);
	protected JTextArea textDescription = new JTextArea(20,5);
	protected JTextField textUtilisateur = new JTextField(20);
	protected JTextField textType = new JTextField(20);
	protected JTextField textWarning = new JTextField();	
	protected JButton boutModifier = new JButton("Modifier");
	protected JButton boutAnnuler = new JButton("Annuler");

	protected Incident incid;
	protected Sup_OngletIncident parent;
	
	private AccesBDDIncident tableIncidents; 
	
	//Constructeur avec paramètres
	public Sup_AjoutModifIncident(Incident incid, Sup_OngletIncident parent, AccesBDDIncident tableIncidents){
		super("");
		
		//Comportement lors de la fermeture
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				boutAnnuler.doClick();
			}
		};
		addWindowListener(l);

		// On indique le titre de la fenêtre
		setTitle("Modification d'un incident");
		
		// On récupère les variables saisies en paramètres
		this.incid = incid;
		this.parent = parent;
		this.tableIncidents = tableIncidents;
		
		// On définit la mise en forme
		getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		
		// On rajoute un peu d'espace autour des panneaux
		((JComponent)getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// Ligne du colis
		JPanel panColis = new JPanel();
		panColis.setLayout(new BoxLayout(panColis,BoxLayout.X_AXIS));
		JLabel labelColis = new JLabel("Colis :");
		labelColis.setAlignmentX(Box.LEFT_ALIGNMENT);
		textColis.setAlignmentX(Box.RIGHT_ALIGNMENT);
		textColis.setMaximumSize(new Dimension(100,20));
		panColis.add(labelColis);
		panColis.add(Box.createHorizontalGlue());
		panColis.add(Box.createRigidArea(new Dimension(10, 0)));
		panColis.add(textColis);
				
		// Ligne de la date
		JPanel panDate = new JPanel();
		panDate.setLayout(new BoxLayout(panDate,BoxLayout.LINE_AXIS));
		JLabel labelDate = new JLabel("Date :");
		labelDate.setAlignmentX(Box.LEFT_ALIGNMENT);
		textDate.setAlignmentX(Box.RIGHT_ALIGNMENT);
		textDate.setMaximumSize(new Dimension(100,20));
		panDate.add(labelDate);
		panDate.add(Box.createHorizontalGlue());
		panDate.add(Box.createRigidArea(new Dimension(10, 0)));
		panDate.add(textDate);
		
		// Ligne de l'état
		JPanel panEtat = new JPanel();
		panEtat.setLayout(new BoxLayout(panEtat,BoxLayout.LINE_AXIS));
		JLabel labelEtat = new JLabel("Etat :");
		labelEtat.setAlignmentX(Component.LEFT_ALIGNMENT);
		comboEtat.setAlignmentX(Component.RIGHT_ALIGNMENT);
		comboEtat.setMaximumSize(new Dimension(100,20));
		panEtat.add(labelEtat);
		panEtat.add(Box.createHorizontalGlue());
		panEtat.add(Box.createRigidArea(new Dimension(10, 0)));
		panEtat.add(comboEtat);
		
		// Ligne de la description
		JPanel panDescription = new JPanel();
		panDescription.setLayout(new BoxLayout(panDescription,BoxLayout.Y_AXIS));
		JLabel labelDescription = new JLabel("Description :");
		labelDescription.setAlignmentX(Box.LEFT_ALIGNMENT);
		textDescription.setLineWrap(true);
		textDescription.setWrapStyleWord(true);
		JScrollPane areaScrollPane = new JScrollPane(textDescription);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(160, 150));
        areaScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        panDescription.add(labelDescription);
		panDescription.add(Box.createRigidArea(new Dimension(0, 5)));
		panDescription.add(areaScrollPane);
		
		// Ligne de l'utilisateur
		JPanel panUtilisateur = new JPanel();
		panUtilisateur.setLayout(new BoxLayout(panUtilisateur,BoxLayout.LINE_AXIS));
		JLabel labelUtilisateur = new JLabel("Utilisateur :");
		labelUtilisateur.setAlignmentX(Box.LEFT_ALIGNMENT);
		textUtilisateur.setAlignmentX(Box.RIGHT_ALIGNMENT);
		textUtilisateur.setMaximumSize(new Dimension(100,20));
		panUtilisateur.add(labelUtilisateur);
		panUtilisateur.add(Box.createHorizontalGlue());
		panUtilisateur.add(Box.createRigidArea(new Dimension(10, 0)));
		panUtilisateur.add(textUtilisateur);
		
		// Ligne du type
		JPanel panType = new JPanel();
		panType.setLayout(new BoxLayout(panType,BoxLayout.X_AXIS));
		JLabel labelType = new JLabel("Type :");
		labelType.setAlignmentX(Box.LEFT_ALIGNMENT);
		textType.setAlignmentX(Box.RIGHT_ALIGNMENT);
		textType.setMaximumSize(new Dimension(100,20));
		panType.add(labelType);
		panType.add(Box.createHorizontalGlue());
		panType.add(Box.createRigidArea(new Dimension(10,10)));
		panType.add(textType);
		
		// Boutons d'actions : Valider/Modifier et Annuler
		JPanel panBoutons = new JPanel();
		panBoutons.setLayout(new BoxLayout(panBoutons,BoxLayout.X_AXIS));
		boutModifier.addActionListener(this);
		boutAnnuler.addActionListener(this);
		panBoutons.add(boutModifier);
		panBoutons.add(Box.createRigidArea(new Dimension(20, 0)));
		panBoutons.add(boutAnnuler);
		
		// Champ d'avertissement en cas de saisie incomplète
		JPanel panWarning = new JPanel();
		panWarning.setLayout(new BoxLayout(panWarning,BoxLayout.LINE_AXIS));
		textWarning.setEditable(false);
		textWarning.setForeground(Color.RED);
		//textWarning.setHorizontalAlignment(JTextField.CENTER);
		textWarning.setBorder(BorderFactory.createLineBorder(Color.black));
		panWarning.add(textWarning);

		
		// On ajoute tous les panneaux secondaires au panneau principal
		getContentPane().add(panColis);
		getContentPane().add(Box.createRigidArea(new Dimension(0,10)));
		getContentPane().add(panDate);
		getContentPane().add(Box.createRigidArea(new Dimension(0,10)));
		getContentPane().add(panEtat);
		getContentPane().add(Box.createRigidArea(new Dimension(0,10)));
		getContentPane().add(panDescription);
		getContentPane().add(Box.createRigidArea(new Dimension(0,10)));
		getContentPane().add(panUtilisateur);
		getContentPane().add(panType);
		getContentPane().add(Box.createRigidArea(new Dimension(0,10)));
		getContentPane().add(panBoutons);
		getContentPane().add(Box.createRigidArea(new Dimension(0,10)));
		getContentPane().add(panWarning);

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

	// Ajoute un message d'erreur à la boite de dialogue si un champs est mal renseigné
	private void setWarning(String s){
		textWarning.setText("Le champs \""+s+"\" est mal renseigné.");
		textWarning.updateUI();
	}
}
