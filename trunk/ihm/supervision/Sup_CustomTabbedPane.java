package ihm.supervision;

import javax.swing.ImageIcon;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

// Classe dérivant du type basique de gestionnaire d'onglets, permettant ainsi
//	de définir des onglets au design totalement personnalisé
public class Sup_CustomTabbedPane extends BasicTabbedPaneUI{

	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
	{
	}

	// Arrière plan de chaque onglet
	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
	{
		ImageIcon img = new ImageIcon("images/supervision/onglet.png");
		ImageIcon img_onclick = new ImageIcon("images/supervision/onglet_onclick.png");
		
		// On affiche un arrière plan différent si l'onglet est sélectionné ou non
		if(isSelected) g.drawImage(img_onclick.getImage(), x, y, null);
		else g.drawImage(img.getImage(), x, y, null);
	}

	
	protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
	{
	}

	// Hauteur des onglets
	protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight)
	{
		return 18;//vHeight;
	}
	
	// Largeur des onglets
	protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics)
	{
		return 115;
	}
	
	// Propriétés par défaut
	protected void installDefaults()
	{
		super.installDefaults();
		tabAreaInsets.left = 0;
		selectedTabPadInsets = new Insets(0, 0, 0, 0);
		tabInsets = new Insets(0, 0, 0, 0);
	}

	// Décalage partant de la gauche
	protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected)
	{
		return 0;
	}

	protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
	{
	}

	protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
	{
	}
	
	protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
	{
	}
	
	protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
	{
	}

	// Espacements entre le contenu et la bordure
	protected Insets getContentBorderInsets(int tabPlacement)
	{
		return new Insets(0, 0, 0, 0);
	}



}
