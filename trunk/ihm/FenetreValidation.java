package ihm;

import java.awt.*;

import javax.swing.*;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 *  Fenêtre de validation
 */

public class FenetreValidation extends JDialog implements ChangeListener{
	private Bouton valider, annuler;
	private boolean resultat=false, blocage=true;
	private JLabel labelMessage = new JLabel();
	//JPanel panel = new JPanel(new BorderLayout());

	public FenetreValidation(String message){
		setBounds(347,242,330,283);
		setUndecorated(true);
		FenetreType fenetre=new FenetreType("images/preparation/validation.png");
		setContentPane(fenetre);
		// Création de la police
		Font font=new Font("Verdana", Font.BOLD, 12);
		//setTitle("Validation");
		/*labelMessage.setText(message);
		labelMessage.setHorizontalTextPosition(SwingConstants.CENTER);
		labelMessage.setVerticalTextPosition(SwingConstants.CENTER);
		labelMessage.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		panel.setOpaque(false);
		panel.add(labelMessage,BorderLayout.CENTER);
		panel.setBounds(0, 30, 330, 100);*/
		JTextArea description=new JTextArea(message);
		description.setFont(font);
		description.setBounds(20, 40, 300, 180);
		description.setAutoscrolls(true);
		description.setOpaque(false);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		description.setEditable(false);
		fenetre.add(description);

		getContentPane().setLayout(null);
		//getContentPane().add(panel);

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
	
	public static void main(String[] args) {
		new FenetreValidation("OUH le joli Message aligné au centre du milieu de la fenêtre").setVisible(true);
	}
}
