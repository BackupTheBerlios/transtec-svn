package ihm.preparation;

import ihm.Bouton;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;

public class DeplacementColis implements KeyListener{
	private float xloc=0, yloc=0, zloc=0;
	private Transform3D translation=null;
	private TransformGroup objSpin3=null;
	private Bouton b;
	private float prof;
	private float haut;
	private float larg;
	private float benne_prof;
	private float benne_haut;
	private float benne_larg;
	private Vector v;
	
	public DeplacementColis(Bouton aEcouter,float benne_profondeur,float benne_hauteur,float benne_largeur,Vector vect){
		b = aEcouter;
		b.addKeyListener(this);
		benne_prof = benne_profondeur;
		benne_haut = benne_hauteur;
		benne_larg = benne_largeur;
		v = vect;
	}
	
	public void objetADeplacer(TransformGroup colis, Transform3D translation,Box b){
		prof = b.getXdimension();
		haut = b.getYdimension();
		larg = b.getZdimension();
		objSpin3=colis;
		this.translation=translation;
		xloc=0;
		yloc=0;
		zloc=0;
	}
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(this.objSpin3!=null || this.translation!=null){
			
			if (e.getKeyChar()== 'a') {
				yloc = (-benne_haut)+haut;
				xloc = xloc + .01f;
				Verif_Benne();
				Cherch_haut();
			}
			if (e.getKeyChar()== 's') {
				yloc = (-benne_haut)+haut;
				xloc = xloc - .01f;
				Verif_Benne();
				Cherch_haut();
			}
			if (e.getKeyChar()=='f') {
				yloc = (-benne_haut)+haut;
				zloc = zloc + .01f;
				Verif_Benne();
				Cherch_haut();
			}
			if (e.getKeyChar()=='e') {
				yloc = (-benne_haut)+haut;
				zloc = zloc - .01f;
				Verif_Benne();
				Cherch_haut();
			}
			
			translation.setTranslation(new Vector3f(xloc,yloc,zloc));
			objSpin3.setTransform(translation);
		}
	}
	
	public void Verif_Benne(){
		if(xloc < -benne_prof + (prof)){
			xloc = xloc + 0.01f;
		}
		else if(xloc > benne_prof - (prof)){
			xloc = xloc - 0.01f;
		}
		else if(zloc < -benne_larg + (larg)){
			zloc = zloc + 0.01f;
		}
		else if(zloc > benne_larg - (larg)){
			zloc = zloc - 0.01f;
		}	
	}
	
	public void Cherch_haut(){
		float[] tab ; 
		int i =0;
		//Si le colis en mouvement entre dans la zone du colis i+1
		//Selon x
		if(v!=null){
			for(i=0;i<v.size();i++){
				tab = (float[])v.elementAt(i);
				if( ((xloc - prof) >= tab[0]) && ((xloc - prof) <= tab[1]) ||  ((xloc + prof) >= tab[0]) && ((xloc + prof) <= tab[1]) || ((xloc - prof) <= tab[0]) && ((xloc + prof) >= tab[1]) || ((xloc - prof) >= tab[0]) && ((xloc + prof) <= tab[1])){
					//Selon z
					if( ((zloc - larg) >= tab[2]) && ((zloc - larg) <= tab[3]) ||  ((zloc + larg) >= tab[2]) && ((zloc + larg) <= tab[3]) || ((zloc - larg) <= tab[2]) && ((zloc + larg) >= tab[3]) || ((zloc - larg) >= tab[2]) && ((zloc + larg) <= tab[3])){
						if(tab[4] + haut > yloc){
							yloc = (tab[4] + haut);
						}
					}
				}
			}
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
