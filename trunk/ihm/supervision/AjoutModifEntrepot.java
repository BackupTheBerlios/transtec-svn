package ihm.supervision;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import accesBDD.AccesBDDEntrepot;
import donnees.Entrepot;
import donnees.Localisation;


// Invite d'ajout/modification d'un utilisateur
public class AjoutModifEntrepot extends AjoutModif implements ActionListener{

	private JTextField textAdresse = new JTextField(15);
	private JTextField textCodepostal = new JTextField(15);
	private JTextField textVille = new JTextField(15);
	private JTextField textTelephone = new JTextField(15);
	
	private Entrepot e;
	private Localisation l;
	private OngletEntrepot parent;
	
	private AccesBDDEntrepot tableEntrepots; 
	
	//Constructeur
	public AjoutModifEntrepot(Entrepot e, OngletEntrepot parent, AccesBDDEntrepot tableEntrepots){
		super();
		
		// On indique le titre de la fen�tre selon le cas de figure : modification ou ajout
		if(e!=null){
			boutModifier.addActionListener(this);
			panneauBoutons.add(boutModifier);
			this.e = e;
			this.l = e.getLocalisation();
		}
		else{
			boutAjouter.addActionListener(this);
			panneauBoutons.add(boutAjouter);
			this.e = new Entrepot();
			this.l = new Localisation();
		}
		
		// On r�cup�re le contenu des param�tres
		this.tableEntrepots = tableEntrepots;
		this.parent = parent;
	
		// Titres des informations � saisir
		panneauLabels.setLayout(new GridLayout(4,1,5,5));
		panneauLabels.add(new JLabel("Adresse :"));
		panneauLabels.add(new JLabel("Code Postal :"));
		panneauLabels.add(new JLabel("Ville :"));
		panneauLabels.add(new JLabel("T�l�phone :"));

		// Champs de saisie des informations
		panneauSaisie.setLayout(new GridLayout(4,1,5,5));
		panneauSaisie.add(textAdresse);
		panneauSaisie.add(textCodepostal);
		panneauSaisie.add(textVille);
		panneauSaisie.add(textTelephone);
		
		// Bouton Annuler
		boutAnnuler.addActionListener(this);
		panneauBoutons.add(boutAnnuler);

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

	// Gestion des actions li�es au boutons
	public void actionPerformed(ActionEvent ev){
		Object source = ev.getSource();
	
		//Validation
		if(source==boutAjouter || source==boutModifier){
			if(verifChamps()){
				try{
					// Cas d'un ajout d'entrep�t
					if(source==boutAjouter){
						// Ecriture dans la base de donn�es
						e.setId(tableEntrepots.ajouter(this.getEntrepot()));
	
						// Mise � jour du tableau
						parent.ajouterLigne(e.toVector());
					}
					// Cas d'une modification d'entrep�t
					else{
						// Mise � jour du tableau
						parent.modifierLigne(this.getEntrepot().toVector());
						
						// Ecriture dans la base de donn�es
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
		// Annulation, on masque simplement la fen�tre
		else if(source==boutAnnuler){
			parent.setFenetreActive(true);
			this.setVisible(false);
			this.dispose();
		}		
	}

	//M�thodes permettant d'obtenir le contenu des champs
	private Entrepot getEntrepot(){
		e.setLocalisation(l);
		e.setTelephone(textTelephone.getText());
		l.setAdresse(textAdresse.getText());
		l.setCodePostal(textCodepostal.getText());
		l.setVille(textVille.getText());

		return e;
	}

	// V�rification de saisie des champs
	private boolean verifChamps(){
		boolean ret = false;

		// On v�rifie que tous les champs sont remplis
		if(textAdresse.getText().equals("")) setWarning("Adresse");
		else if(textCodepostal.getText().equals("")) setWarning("Code Postal");
		else if(textVille.getText().equals("")) setWarning("Ville");
		else if(textTelephone.getText().equals("")) setWarning("T�l�phone");
		else ret = true;

		return ret;
	}
}
