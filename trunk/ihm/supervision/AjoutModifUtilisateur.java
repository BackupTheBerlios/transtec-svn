package ihm.supervision;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import accesBDD.AccesBDDUtilisateur;
import donnees.Localisation;
import donnees.Personne;
import donnees.Utilisateur;


// Invite d'ajout/modification d'un utilisateur
public class AjoutModifUtilisateur extends AjoutModif  implements ActionListener{

	protected final static String [] ETATS = {"Entrée" , "Préparation" , "Supervision"};
	
	private JTextField textLogin = new JTextField(15);
	private JTextField textMDP = new JTextField(15);
	private JComboBox comboType = new JComboBox(ETATS);
	private JTextField textNom = new JTextField(15);
	private JTextField textPrenom = new JTextField(15);
	private JTextField textAdresse = new JTextField(15);
	private JTextField textCodepostal = new JTextField(15);
	private JTextField textVille = new JTextField(15);
	private JTextField textMail = new JTextField(15);
	private JTextField textTelephone = new JTextField(15);
	
	private Utilisateur u;
	private Personne p;
	private Localisation l;
	private OngletUtilisateur parent;
	
	private AccesBDDUtilisateur tableUtilisateurs; 
	
	//Constructeur
	public AjoutModifUtilisateur(Utilisateur u, OngletUtilisateur parent, AccesBDDUtilisateur tableUtilisateurs){
		super();
		
		// On indique le titre de la fenêtre selon le cas de figure : modification ou ajout
		if(u!=null){
			boutModifier.addActionListener(this);
			panneauBoutons.add(boutModifier);
			this.u = u;
			this.p = u.getPersonne();
			this.l = p.getLocalisation();
		}
		else{
			boutAjouter.addActionListener(this);
			panneauBoutons.add(boutAjouter);
			this.u = new Utilisateur();
			this.p = new Personne();
			this.l = new Localisation();
		}
		
		// On récupère le contenu des paramètres
		this.tableUtilisateurs = tableUtilisateurs;
		this.parent = parent;
	
		// Titres des informations à saisir
		panneauLabels.setLayout(new GridLayout(10,1,5,5));
		panneauLabels.add(new JLabel("Login :"));
		panneauLabels.add(new JLabel("Password :"));
		panneauLabels.add(new JLabel("Type :"));
		panneauLabels.add(new JLabel("Nom :"));
		panneauLabels.add(new JLabel("Prénom :"));
		panneauLabels.add(new JLabel("Adresse :"));
		panneauLabels.add(new JLabel("Code Postal :"));
		panneauLabels.add(new JLabel("Ville :"));
		panneauLabels.add(new JLabel("E-Mail :"));
		panneauLabels.add(new JLabel("Téléphone :"));

		// Champs de saisie des informations
		panneauSaisie.setLayout(new GridLayout(10,1,5,5));
		panneauSaisie.add(textLogin);
		panneauSaisie.add(textMDP);
		panneauSaisie.add(comboType);
		panneauSaisie.add(textNom);
		panneauSaisie.add(textPrenom);
		panneauSaisie.add(textAdresse);
		panneauSaisie.add(textCodepostal);
		panneauSaisie.add(textVille);
		panneauSaisie.add(textMail);
		panneauSaisie.add(textTelephone);
		
		// Bouton Annuler
		boutAnnuler.addActionListener(this);
		panneauBoutons.add(boutAnnuler);

		// Si on est dans le cas d'une modification
		if(u!= null){
			// On initialise les champs texte
			textLogin.setText(u.getLogin());
			textMDP.setText(u.getMotDePasse());
			comboType.setSelectedIndex(u.getType().intValue());
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
	
		//Validation
		if(source==boutAjouter || source==boutModifier){
			if(verifChamps()){
				try{
					// Cas d'un ajout d'utilisateur
					if(source==boutAjouter){
						// Ecriture dans la base de données
						u.setId(tableUtilisateurs.ajouter(this.getUtilisateur()));
	
						// Mise à jour du tableau
						parent.ajouterLigne(u.toVector());
					}
					// Cas d'une modification d'utilisateur
					else{
						// Mise à jour du tableau
						parent.modifierLigne(this.getUtilisateur().toVector());
						
						// Ecriture dans la base de données
						tableUtilisateurs.modifier(this.getUtilisateur());					
					}				
				}
				catch(SQLException eSQL){
					System.out.println(eSQL.getMessage());
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
	private Utilisateur getUtilisateur(){
		u.setLogin(textLogin.getText());
		u.setMotDePasse(textMDP.getText());
		u.setType(new Integer(comboType.getSelectedIndex()));
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

		// On vérifie que tous les champs sont remplis
		if(textLogin.getText().equals("")) setWarning("Login");
		else if(textMDP.getText().equals("")) setWarning("Password");
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
}
