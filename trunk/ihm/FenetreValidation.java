package ihm;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 *  Fen�tre de validation
 */

public class FenetreValidation extends JDialog implements ChangeListener{
	private Bouton valider, annuler;
	private boolean resultat=false, blocage=true;
	
	public FenetreValidation(String message){
		setTitle("Validation");
		setBounds(347,242,330,283);
		setUndecorated(true);
		FenetreType fenetre=new FenetreType("images/preparation/validation.png");
		setContentPane(fenetre);
		fenetre.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		valider=new Bouton("images/icones/valider.png","images/icones/valider_inv.png");
		valider.setBounds(40,215,108,44);
		fenetre.add(valider);
		valider.addChangeListener(this);
		annuler=new Bouton("images/icones/annuler.png","images/icones/annuler_inv.png");
		annuler.setBounds(175,215,108,44);
		fenetre.add(annuler);
		annuler.addChangeListener(this);
		
		setVisible(true);
	}
	public boolean getResultat(){
		while(this.blocage==true);
		return resultat;
	}
	
	public void stateChanged(ChangeEvent arg0) {
		if(arg0.getSource()==valider)
			resultat=true;
		setVisible(false);
		this.blocage=false;
	}
	
	public void fermer(){
		dispose();
	}
}