package ihm.entree;

import ihm.AffichageImage;
import ihm.Bouton;
import ihm.FenetreType;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import donnees.Personne;

//Cette classe correspond à la fenetre de création de l'étiquette d'un colis.

public class Entree_Create_etiquette extends JFrame implements ActionListener
{
	public Entree_Create_etiquette(String code_barre,Personne destinataire,Personne expediteur)
	{
		
		setTitle("Création de l'étiquette du colis " + code_barre); 
		
		
		setBounds(180,80,700,560);
		contenu=new FenetreType("images/preparation/etiquette.png");
		
		setContentPane(contenu);
		contenu.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		
		
		
		font=new Font("Verdana", Font.BOLD, 18);
		
		
		
		
		//création du code barre à partir des images
		String a = code_barre;	
		int j=0;
		for (int i=0; i<a.length() ;i++){
			AffichageImage image1 = new AffichageImage("images/code_barre/"+a.charAt(i)+".JPG");
			image1.setBounds(300+(j*7),80,7,49);
			j++;
			contenu.add(image1);  
		}
		
		contenu.validate();
		
		
		label_dest = new JLabel("Destinataire :");
		label_dest.setBounds(120,150,150,20);
		label_dest.setFont(font);
		contenu.add(label_dest);
		
		//données sur le destinataire du colis
		donnees_dest = new JTextArea();
		donnees_dest.setColumns(25);
		donnees_dest.setRows(10);
		donnees_dest.setBounds(40,180,290,120);
		donnees_dest.setLineWrap(true);
		donnees_dest.setWrapStyleWord(true);
		donnees_dest.setFont(font);
		contenu.add(donnees_dest);
		donnees_dest.setEnabled(false);

		//label de l'expéditeur du colis
		label_exp = new JLabel("Expéditeur :");
		label_exp.setBounds(410,150,150,20);
		label_exp.setFont(font);
		contenu.add(label_exp);
		
		//données sur l'expéditeur du colis
		donnees_exp = new JTextArea();
		donnees_exp.setColumns(25);
		donnees_exp.setRows(4);
		donnees_exp.setBounds(360,180,290,120);
		donnees_exp.setLineWrap(true);
		donnees_exp.setWrapStyleWord(true);
		donnees_exp.setFont(font);
		contenu.add(donnees_exp);
		donnees_exp.setEnabled(false);
		
		donnees_dest.setText(destinataire.getNom()+ " "+ destinataire.getPrenom()+ "\n"+ destinataire.getLocalisation().getAdresse()+ "\n"+ destinataire.getLocalisation().getCodePostal()+ " "+ destinataire.getLocalisation().getVille()+ "\n"+destinataire.getMail());
		donnees_exp.setText(expediteur.getNom()+ " "+ expediteur.getPrenom()+ "\n"+ expediteur.getLocalisation().getAdresse()+ "\n"+ expediteur.getLocalisation().getCodePostal()+ " "+ expediteur.getLocalisation().getVille()+ "\n"+expediteur.getMail());
		
	
		imprimer = new Bouton("images/icones/imprimer.png","images/icones/imprimer_inv.png");
		imprimer.setBounds(130,465,170,45);
		contenu.add(imprimer);
		imprimer.addActionListener(this);
		
		fermer = new Bouton("images/icones/fermer.png","images/icones/fermer_inv.png");
		fermer.setBounds(400,465,170,45);
		contenu.add(fermer);
		fermer.addActionListener(this);
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if ( source == fermer){
			dispose();
		}
		//permet d'imprimer l'étiquette
		if ( source == imprimer){
			JOptionPane.showMessageDialog(this,"Impression en cours, Veuillez patienter...","Impression",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	private JLabel label_exp,label_dest;
	private JTextArea donnees_exp,donnees_dest;
	private Bouton fermer,imprimer;
	private FenetreType contenu;
	private Font font;
	
	
	
}
