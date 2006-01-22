package ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import accesBDD.AccesBDDUtilisateur;
import donnees.Utilisateur;
import donnees.Personne;
import donnees.Localisation;


// Invite d'ajout/modification d'un utilisateur
public class Sup_AjoutModifUtilisateur extends JFrame implements ActionListener{
	
	private JTextField textLogin = new JTextField(15);
	private JTextField textMDP = new JTextField(15);
	private JTextField textType = new JTextField(15);
	private JTextField textNom = new JTextField(15);
	private JTextField textPrenom = new JTextField(15);
	private JTextField textAdresse = new JTextField(15);
	private JTextField textCodepostal = new JTextField(15);
	private JTextField textVille = new JTextField(15);
	private JTextField textMail = new JTextField(15);
	private JTextField textTelephone = new JTextField(15);
	
	private JTextField textWarning = new JTextField(15);
	
	private JButton boutValider = new JButton();
	private JButton boutAnnuler = new JButton("Annuler");
	private Utilisateur u;
	private Personne p;
	private Localisation l;
	public boolean modif = false;
	private Sup_OngletUtilisateur parent;
	private AccesBDDUtilisateur bdd = new AccesBDDUtilisateur(); 

	
	//Constructeur
	public Sup_AjoutModifUtilisateur(Utilisateur u, Sup_OngletUtilisateur parent){
		super("");
		
		//Comportement lors de la fermeture
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				boutAnnuler.doClick();
			}
		};
		addWindowListener(l);

		// On indique le titre de la fenêtre selon le cas de figure : modification ou ajout
		if(u!=null){
			setTitle("Modification d'un utilisateur");
			boutValider.setText("Modifier");
			this.u = u;
			this.p = u.getPersonne();
			this.l = p.getLocalisation();
		}
		else{
			setTitle("Ajout d'un utilisateur");
			boutValider.setText("Ajouter");
			this.u = new Utilisateur();
			this.p = new Personne();
			this.l = new Localisation();
		}
		
		this.parent = parent;
	
		// Titres des informations à saisir
		JPanel panneauLabels = new JPanel(new GridLayout(10,1,5,5));
		panneauLabels.add(new JLabel("Login :"));
		panneauLabels.add(new JLabel("Password :"));
		panneauLabels.add(new JLabel("type :"));
		panneauLabels.add(new JLabel("Nom :"));
		panneauLabels.add(new JLabel("Prénom :"));
		panneauLabels.add(new JLabel("Adresse :"));
		panneauLabels.add(new JLabel("Code Postal :"));
		panneauLabels.add(new JLabel("Ville :"));
		panneauLabels.add(new JLabel("E-Mail :"));
		panneauLabels.add(new JLabel("Téléphone :"));

		// Champs de saisie des informations
		JPanel panneauSaisie = new JPanel(new GridLayout(10,1,5,5));
		panneauSaisie.add(textLogin);
		panneauSaisie.add(textMDP);
		panneauSaisie.add(textType);
		panneauSaisie.add(textNom);
		panneauSaisie.add(textPrenom);
		panneauSaisie.add(textAdresse);
		panneauSaisie.add(textCodepostal);
		panneauSaisie.add(textVille);
		panneauSaisie.add(textMail);
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
		if(u!= null){
			// On initialise les champs texte
			textLogin.setText(u.getLogin());
			textMDP.setText(u.getMotDePasse());
			textType.setText(u.getType().toString());
			textNom.setText(u.getPersonne().getNom());
			textPrenom.setText(u.getPersonne().getPrenom());
			textAdresse.setText(u.getPersonne().getLocalisation().getAdresse());
			textCodepostal.setText(u.getPersonne().getLocalisation().getCodePostal());
			textVille.setText(u.getPersonne().getLocalisation().getVille());
			textMail.setText(u.getPersonne().getMail());
			textTelephone.setText(u.getPersonne().getTelephone());
		}

		pack();
		setAlwaysOnTop(true);
		setVisible(true);
	}

	// Gestion des actions liées au boutons
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();

		// Validation
		if(source==boutValider){
			if(verifChamps()){
				// Cas d'un ajout d'utilisateur
				if(boutValider.getText().equals("Ajouter")){
					// Mise à jour du tableau
					parent.ajouterLigne(this.getUtilisateur().toVector());
					
					// Ecriture dans la base de données
					bdd.ajouter(this.getUtilisateur());
				}
				// Cas d'une modification d'utilisateur
				else{
					// Mise à jour du tableau
					parent.modifierLigne(this.getUtilisateur().toVector());
					
					// Ecriture dans la base de données
					bdd.modifier(this.getUtilisateur());					
				}				
				// On masque la fenetre
				parent.setFenetreActive(true);
				this.setVisible(false);
				this.dispose();
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
	private Utilisateur getUtilisateur(){
		u.setLogin(textLogin.getText());
		u.setMotDePasse(textMDP.getText());
		u.setType(new Integer(textType.getText().trim()));
		u.setPersonne(p);
		p.setMail(textMail.getText());
		p.setNom(textNom.getText());
		p.setPrenom(textPrenom.getText());
		p.setTelephone(textTelephone.getText());
		p.setLocalisation(l);
		l.setAdresse(textAdresse.getText());
		l.setCodePostal(textCodepostal.getText());
		l.setVille(textVille.getText());

		return u;
	}

	// Vérification de saisie des champs
	private boolean verifChamps(){
		boolean ret = false;
		boolean erreurType = false;

		// On vérifie que la valeur numérique soit correctement saisie
		try{
			new Integer(this.textType.getText().trim());
		}				
		catch(NumberFormatException e){
			erreurType = true;
		}

		// On vérifie que tous les champs sont remplis
		if(textLogin.getText().equals("")) setWarning("Login");
		else if(textMDP.getText().equals("")) setWarning("Password");
		else if(textType.getText().equals("") || erreurType) setWarning("Type");
		else if(textNom.getText().equals("")) setWarning("Nom");
		else if(textPrenom.getText().equals("")) setWarning("Prénom");
		else if(textAdresse.getText().equals("")) setWarning("Adresse");
		else if(textCodepostal.getText().equals("")) setWarning("Code Postal");
		else if(textVille.getText().equals("")) setWarning("Ville");
		else if(textMail.getText().equals("")) setWarning("E-Mail");
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
