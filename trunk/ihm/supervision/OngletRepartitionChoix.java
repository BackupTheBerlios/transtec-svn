package ihm.supervision;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class OngletRepartitionChoix extends JPanel{
	
	private ButtonGroup groupeRadio;
	private JRadioButton radioAucun,radioRadin,radioPerenoel;

	public OngletRepartitionChoix(){
		super();

		// Mise en page et taille du panel
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setOpaque(false);
		
		// Création des boutons radios
		radioAucun = new JRadioButton(new ImageIcon("images/supervision/bouton_aucun_algo.png"));
		radioAucun.setSelectedIcon(new ImageIcon("images/supervision/bouton_aucun_algo_appuyer.png"));
		radioAucun.setOpaque(false);
		radioAucun.setSelected(true);
		
		radioRadin = new JRadioButton(new ImageIcon("images/supervision/bouton_algo_radin.png"));
		radioRadin.setSelectedIcon(new ImageIcon("images/supervision/bouton_algo_radin_appuyer.png"));
		radioRadin.setOpaque(false);
		
		radioPerenoel = new JRadioButton(new ImageIcon("images/supervision/bouton_algo_noel.png"));
		radioPerenoel.setSelectedIcon(new ImageIcon("images/supervision/bouton_algo_noel_appuyer.png"));
		radioPerenoel.setOpaque(false);
		
		// Regroupement des boutons radio
		groupeRadio = new ButtonGroup();
		groupeRadio.add(radioAucun);
		groupeRadio.add(radioRadin);
		groupeRadio.add(radioPerenoel);
		
		// Ajout des boutons au panel
		add(radioAucun);
		add(radioRadin);
		add(radioPerenoel);
	}
	
	// Renvoie l'algorithme choisi par l'utilisateur
	public int choixAlgoRadio(){
		int ret=0;
		
		if(radioAucun.isSelected()) ret=OngletRepartition.AUCUN;
		else if(radioRadin.isSelected()) ret=OngletRepartition.RADIN;
		else if(radioPerenoel.isSelected()) ret=OngletRepartition.PERE_NOEL;

		return ret;
	}
}
