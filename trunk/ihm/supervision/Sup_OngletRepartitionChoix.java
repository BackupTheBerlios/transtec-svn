package ihm.supervision;

import java.awt.*;
import javax.swing.*;

public class Sup_OngletRepartitionChoix extends JPanel{
	
	private ButtonGroup groupeRadio;
	private JRadioButton radioAucun,radioRadin,radioPerenoel;

	public Sup_OngletRepartitionChoix(){
		super();
		
		// Mise en page et taille du panel
		setLayout(new GridLayout(3,1));
		setOpaque(false);
		setSize(new Dimension(600,200));
		
		// Création des boutons radios
		radioAucun = new JRadioButton("Pas d'algorithme");
		radioAucun.setOpaque(false);
		radioAucun.setSelected(true);
		
		radioRadin = new JRadioButton("Minimisation des coûts (radin)");
		radioRadin.setOpaque(false);
		
		radioPerenoel = new JRadioButton("Maximisation de la satisfaction (Père Noël)");
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
		
		if(radioAucun.isSelected()) ret=Sup_OngletRepartition.AUCUN;
		else if(radioRadin.isSelected()) ret=Sup_OngletRepartition.RADIN;
		else if(radioPerenoel.isSelected()) ret=Sup_OngletRepartition.PERENOEL;

		return ret;
	}
}
