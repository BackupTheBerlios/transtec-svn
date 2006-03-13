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
	private Bouton b;
	private float prof;
	private float haut;
	private float larg;
	
	public deplacementColis(Bouton aEcouter){
		b = aEcouter;
		b.addKeyListener(this);
	}
	
	public void objetADeplacer(TransformGroup colis, Transform3D translation,float profondeur,float hauteur,float largeur){
		prof = profondeur;
		haut = hauteur;
		larg = largeur;
		objSpin3=colis;
		this.translation=translation;
		xloc=0;
		yloc=0;
		zloc=0;
	}
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(this.objSpin3!=null || this.translation!=null){
			if (e.getKeyChar()=='s') {xloc = xloc + .01f;}
		
			if (e.getKeyChar()=='a') {xloc = xloc - .01f;}
			
			if (e.getKeyChar()=='d') {yloc = yloc + .01f;}
			
			if (e.getKeyChar()=='z') {yloc = yloc - .01f;}
			
			if (e.getKeyChar()=='f') {zloc = zloc + .01f;}
			
			if (e.getKeyChar()=='e') {zloc = zloc - .01f;}
			
			translation.setTranslation(new Vector3f(xloc,yloc,zloc));
			objSpin3.setTransform(translation);
		}
	}
	
	public void ArretEcoute(){
		b.removeKeyListener(this);
	}
	
	public float GetX(){
		return xloc;
	}
	
	public float GetY(){
		return yloc;
	}
	
	public float GetZ(){
		return zloc;
	}

	public float GetProfondeur(){
		return prof;
	}
	
	public float GetHauteur(){
		return haut;
	}
	
	public float GetLargeur(){
		return larg;
	}
	
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
