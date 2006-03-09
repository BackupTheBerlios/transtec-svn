package ihm.supervision;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class Sup_CustomTabbedPane extends BasicTabbedPaneUI{
	
	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
	{
		g.setColor(Color.BLACK);
		g.drawLine(x, y, x, y + h);
		g.drawLine(x, y, x + w - (h / 2), y);
		g.drawLine(x + w - (h / 2), y, x + w + (h / 2), y + h);
	
		if (isSelected)
		{
			g.setColor(Color.WHITE);
			g.drawLine(x + 1, y + 1, x + 1, y + h);
			g.drawLine(x + 1, y + 1, x + w - (h / 2), y + 1);
	
			g.setColor(shadow);
			g.drawLine(x + w - (h / 2), y + 1, x + w + (h / 2)-1, y + h);
		}
	}

	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
	{
		Polygon shape = new Polygon();
	
		shape.addPoint(x, y + h);
		shape.addPoint(x, y);
		shape.addPoint(x + w - (h / 2), y);
	
		if (isSelected || (tabIndex == (rects.length - 1)))
		{
			shape.addPoint(x + w + (h / 2), y + h);
		}
		else
		{
			shape.addPoint(x + w, y + (h / 2));
			shape.addPoint(x + w, y + h);
		}
	
		g.setColor(tabPane.getBackground());
		g.fillPolygon(shape);
	}

	
	protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
	{
		// Do nothing
	}

	protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight)
	{
		int vHeight = fontHeight;
		if (vHeight % 2 > 0)
		{
			vHeight += 1;
		}
		return vHeight;
	}
	
	protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics)
	{
		return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + metrics.getHeight();
	}

	protected void installDefaults()
	{
		super.installDefaults();
		tabAreaInsets.left = 4;
		selectedTabPadInsets = new Insets(0, 0, 0, 0);
		tabInsets = selectedTabPadInsets;
	}

	protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected)
	{
		return 0;
	}


	/*protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex)
	{
		int tw = tabPane.getBounds().width;
	
		g.fillRect(0, 0, tw, rects[0].height + 3);
	
		super.paintTabArea(g, tabPlacement, selectedIndex);
	}*/
	protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
	{
		Rectangle selectedRect = selectedIndex < 0 ? null : getTabBounds(selectedIndex, calcRect);
	
		selectedRect.width = selectedRect.width + (selectedRect.height / 2) - 1;
	
		g.setColor(Color.BLACK);
	
		g.drawLine(x, y, selectedRect.x, y);
		g.drawLine(selectedRect.x + selectedRect.width + 1, y, x + w, y);
	
		g.setColor(Color.WHITE);
	
		g.drawLine(x, y + 1, selectedRect.x, y + 1);
		g.drawLine(selectedRect.x + 1, y + 1, selectedRect.x + 1, y);
		g.drawLine(selectedRect.x + selectedRect.width + 2, y + 1, x + w, y + 1);
	
		g.setColor(shadow);
		g.drawLine(selectedRect.x + selectedRect.width, y, selectedRect.x + selectedRect.width + 1, y + 1);
	}

	protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
	{
		// Do nothing
	}
	
	protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
	{
		// Do nothing
	}
	
	protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h)
	{
		// Do nothing
	}

	protected Insets getContentBorderInsets(int tabPlacement)
	{
		return new Insets(2, 0, 0, 0);
	}



}
