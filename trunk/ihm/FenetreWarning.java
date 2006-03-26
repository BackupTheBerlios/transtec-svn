package ihm;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JTextArea;

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
		
		// Création de la police
		Font font=new Font("Verdana", Font.BOLD, 12);
		
		JTextArea description=new JTextArea(message);
		description.setFont(font);
		description.setBounds(20, 40, 300, 180);
		description.setAutoscrolls(true);
		description.setOpaque(false);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		description.setEditable(false);
		fenetre.add(description);
		
		this.warning=new Bouton("images/icones/warning.png","images/icones/warning_inv.png");
		this.warning.setBounds(110,222,165,41);
		fenetre.add(this.warning);
		this.warning.addActionListener(this);
		
		setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		dispose();		
	}	
	public static void main(String[] args) {
		new FenetreWarning("OUH le joli Message aligné au centre du milieu de la fenêtre").setVisible(true);
	}
}
