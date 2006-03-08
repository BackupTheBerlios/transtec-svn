package ihm.preparation;

import ihm.Bouton;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JButton;
import javax.vecmath.Vector3f;

public class deplacementColis implements KeyListener{
	private float xloc=0, yloc=0, zloc=0;
	private Transform3D translation=null;
	private TransformGroup objSpin3=null;
	
	public deplacementColis(Bouton aEcouter){
		aEcouter.addKeyListener(this);
	}
	
	public void objetADeplacer(TransformGroup colis, Transform3D translation){
		objSpin3=colis;
		this.translation=translation;
		xloc=0;
		yloc=0;
		zloc=0;
	}
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(this.objSpin3!=null || this.translation!=null){
			if (e.getKeyChar()=='s') {xloc = xloc + .1f;}
		
			if (e.getKeyChar()=='a') {xloc = xloc - .1f;}
			
			if (e.getKeyChar()=='d') {yloc = yloc + .1f;}
			
			if (e.getKeyChar()=='z') {yloc = yloc - .1f;}
			
			if (e.getKeyChar()=='f') {zloc = zloc + .1f;}
			
			if (e.getKeyChar()=='e') {zloc = zloc - .1f;}
			
			translation.setTranslation(new Vector3f(xloc,yloc,zloc));
			objSpin3.setTransform(translation);
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
