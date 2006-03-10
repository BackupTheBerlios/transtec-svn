package ihm;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FenetreValidation extends JDialog implements ChangeListener{
	private Bouton valider, annuler;
	private boolean resultat=false;
	public FenetreValidation(String message){
		setSize(500,500);
		Container container=getContentPane();
		container.setLayout(new FlowLayout());
		container.setSize(500,500);
		setUndecorated(true);
		valider=new Bouton("images/icones/valider.png","images/icones/valider.png");
		valider.setBounds(0,0,10,10);
		container.add(valider);
		valider.addChangeListener(this);
		annuler=new Bouton("images/icones/annuler.png","images/icones/annuler.png");
		annuler.setBounds(0,0,5,10);
		container.add(annuler);
		annuler.addChangeListener(this);
		container.setVisible(true);
		setVisible(true);
	}
	public boolean getResultat(){
		return resultat;
	}
	
	public static void main(String[] args) {
		FenetreValidation test=new FenetreValidation("");
		System.out.println(test.resultat);
		test.dispose();
	}
	
	public void stateChanged(ChangeEvent arg0) {
		if(arg0.getSource()==valider)
			resultat=true;
		setVisible(false);
	}
}
