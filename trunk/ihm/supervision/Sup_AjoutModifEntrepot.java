package ihm.supervision;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import accesBDD.AccesBDDEntrepot;
import donnees.Entrepot;
import donnees.Localisation;


// Invite d'ajout/modification d'un utilisateur
public class Sup_AjoutModifEntrepot extends JFrame implements ActionListener{

	private JTextField textAdresse = new JTextField(15);
	private JTextField textCodepostal = new JTextField(15);
	private JTextField textVille = new JTextField(15);
	private JTextField textTelephone = new JTextField(15);
	
	private JTextField textWarning = new JTextField(15);
	
	private JButton boutValider = new JButton();
	protected JButton boutAnnuler = new JButton("Annuler");
	private Entrepot e;
	private Localisation l;
	private Sup_OngletEntrepot parent;
	
	private AccesBDDEntrepot tableEntrepots; 
	
	//Constructeur
	public Sup_AjoutModifEntrepot(Entrepot e, Sup_OngletEntrepot parent, AccesBDDEntrepot tableEntrepots){
		super("");
		
		//Comportement lors de la fermeture
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				boutAnnuler.doClick();
			}
		};
		addWindowListener(l);

		// On indique le titre de la fenêtre selon le cas de figure : modification ou ajout
		if(e!=null){
			setTitle("Modification d'un entrepôt");
			boutValider.setText("Modifier");
			this.e = e;
			this.l = e.getLocalisation();
		}
		else{
			setTitle("Ajout d'un entrepôt");
			boutValider.setText("Ajouter");
			this.e = new Entrepot();
			this.l = new Localisation();
		}
		
		// On récupère le contenu des paramètres
		this.tableEntrepots = tableEntrepots;
		this.parent = parent;
	
		// Titres des informations à saisir
		JPanel panneauLabels = new JPanel(new GridLayout(4,1,5,5));
		panneauLabels.add(new JLabel("Adresse :"));
		panneauLabels.add(new JLabel("Code Postal :"));
		panneauLabels.add(new JLabel("Ville :"));
		panneauLabels.add(new JLabel("Téléphone :"));

		// Champs de saisie des informations
		JPanel panneauSaisie = new JPanel(new GridLayout(4,1,5,5));
		panneauSaisie.add(textAdresse);
		panneauSaisie.add(textCodepostal);
		panneauSaisie.add(textVille);
		panneauSaisie.add(textTelephone);
		
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
		if(e!= null){
			// On initialise les champs texte
			textAdresse.setText(e.getLocalisation().getAdresse());
			textCodepostal.setText(e.getLocalisation().getCodePostal());
			textVille.setText(e.getLocalisation().getVille());
			textTelephone.setText(e.getTelephone());
		}

		pack();
		setAlwaysOnTop(true);
		setVisible(true);
	}

	// Gestion des actions liées au boutons
	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();
	
		//Validation
		if(source==boutValider){
			if(verifChamps()){
				try{
					// Cas d'un ajout d'entrepôt
					if(boutValider.getText().equals("Ajouter")){
						// Ecriture dans la base de données
						e.setId(tableEntrepots.ajouter(this.getEntrepot()));
	
						// Mise à jour du tableau
						parent.ajouterLigne(e.toVector());
					}
					// Cas d'une modification d'entrepôt
					else{
						// Mise à jour du tableau
						parent.modifierLigne(this.getEntrepot().toVector());
						
						// Ecriture dans la base de données
						tableEntrepots.modifier(this.getEntrepot());					
					}				
				}
				catch(Exception exc){
					System.out.println(exc.getMessage());
				}
				finally{
					// On masque la fenetre
					parent.setFenetreActive(true);
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
	private Entrepot getEntrepot(){
		e.setLocalisation(l);
		e.setTelephone(textTelephone.getText());
		l.setAdresse(textAdresse.getText());
		l.setCodePostal(textCodepostal.getText());
		l.setVille(textVille.getText());

		return e;
	}

	// Vérification de saisie des champs
	private boolean verifChamps(){
		boolean ret = false;

		// On vérifie que tous les champs sont remplis
		if(textAdresse.getText().equals("")) setWarning("Adresse");
		else if(textCodepostal.getText().equals("")) setWarning("Code Postal");
		else if(textVille.getText().equals("")) setWarning("Ville");
		else if(textTelephone.getText().equals("")) setWarning("Téléphone");
		else ret = true;

		return ret;
	}

	// Ajoute un message d'erreur à la boite de dialogue si un champs est mal renseigné
	private void setWarning(String s){
		textWarning.setText("Le champs \""+s+"\" est mal renseigné.");
		textWarning.updateUI();
	}
}
