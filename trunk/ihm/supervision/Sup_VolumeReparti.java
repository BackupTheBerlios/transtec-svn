package ihm.supervision;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

// Classe correspondant au petit panel d'aide � la r�partition des
//	volumes, en troisi�me �tape de la r�partition.
// Ce panel affiche la destination concern�e, le volume r�parti et le volume total.
public class Sup_VolumeReparti extends JPanel{
	
	private JLabel labelDestination = new JLabel("Destination :");
	private JLabel labelVolRep = new JLabel("Volume r�parti :");
	private JLabel labelSur = new JLabel("/");
	private JTextField texteDestination = new JTextField();
	private JTextField texteVolReparti = new JTextField();
	private JTextField texteVolTotal = new JTextField();
	
	public Sup_VolumeReparti(){
		
		// Mise en page
		setLayout(null);
		setOpaque(false);
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
		
		// Label "Destination :"
		labelDestination.setBounds(5,5,125,25);
		labelDestination.setFont(new Font("Arial", Font.BOLD, 12));
		add(labelDestination);		
		
		// Zone de texte pour la destination
		texteDestination.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		texteDestination.setEnabled(false);
		texteDestination.setOpaque(false);
		texteDestination.setDisabledTextColor(new Color(89,128,165));
		texteDestination.setBounds(5,35,125,16);
		add(texteDestination);
		
		// Label "Volume r�parti :"
		labelVolRep.setBounds(5,60,120,25);
		add(labelVolRep);
		
		// Zone de texte pour le volume r�parti
		texteVolReparti.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		texteVolReparti.setEnabled(false);
		texteVolReparti.setOpaque(false);
		texteVolReparti.setDisabledTextColor(new Color(89,128,165));
		texteVolReparti.setBounds(5,90,50,16);
		add(texteVolReparti);
		
		// Label "/"
		labelSur.setBounds(65,90,5,16);
		add(labelSur);
		
		// Zone de texte pour le volume total
		texteVolTotal.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		texteVolTotal.setEnabled(false);
		texteVolTotal.setOpaque(false);
		texteVolTotal.setDisabledTextColor(new Color(89,128,165));
		texteVolTotal.setBounds(80,90,50,16);
		add(texteVolTotal);
	}
	
	// Methode permettant d'ins�rer un nom de destination
	public void setTextDestination(String s){
		texteDestination.setText(s);
	};
	
	// Methode permettant d'ins�rer un volume r�parti
	public void setTextVolReparti(String s){
		texteVolReparti.setText(s);
	};
	
	// Methode permettant d'ins�rer un volume total
	public void setTextVolTotal(String s){
		texteVolTotal.setText(s);
	};
	
}
