package ihm;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

public class FenetreWarning extends JDialog implements ActionListener{
	private Bouton warning;
	
	public FenetreWarning(String message){
		//setTitle("Warning");
		setBounds(347,242,330,283);
		setUndecorated(true);
		FenetreType fenetre=new FenetreType("images/preparation/warning.png");
		setContentPane(fenetre);
		fenetre.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		this.warning=new Bouton("images/icones/warning.png","images/icones/warning_inv.png");
		this.warning.setBounds(110,212,110,48);
		fenetre.add(this.warning);
		this.warning.addActionListener(this);
		
		setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		dispose();		
	}	
}
