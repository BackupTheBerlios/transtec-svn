package ihm.preparation;


import com.sun.j3d.utils.geometry.*;

import java.awt.Color;
import java.util.Enumeration;
import javax.media.j3d.*;
import javax.vecmath.*;

public class CollisionDetector extends Behavior {

  private static Material highlight;
  private boolean inCollision = false;
  private Box box;
  private Shape3D shape;
  private Material shapeMaterial;
  private Appearance shapeAppearance;
  private WakeupOnCollisionEntry wEnter;
  private WakeupOnCollisionExit wExit;

  public CollisionDetector(Box s) {
    highlight = new Material();
    //highlight.setDiffuseColor(new Color3f(1.0f,.0f,0.0f));
    highlight.setAmbientColor(new Color3f(Color.red));
    box = s;
    shapeAppearance = box.getAppearance();
    shapeAppearance.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
    shapeMaterial = shapeAppearance.getMaterial();
    inCollision = false;
    }
  
  public CollisionDetector(Shape3D s){
	  
	highlight = new Material();
    highlight.setDiffuseColor(new Color3f(1.0f,0.0f,0.0f));
    shape = s;
    shapeAppearance = shape.getAppearance();
    shapeAppearance.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
    shapeMaterial = shapeAppearance.getMaterial();
    inCollision = false;
	  
  	}


  public void initialize() {
    wEnter = new WakeupOnCollisionEntry(box);
    wExit = new WakeupOnCollisionExit(box);
    wakeupOn(wEnter);
    }

  public void processStimulus(Enumeration criteria) {
    inCollision = !inCollision;
    if (inCollision) {
    	System.out.println("entre de la zone");
    	shapeAppearance.setMaterial(shapeMaterial);
      wakeupOn(wExit);}
      else {
    	  System.out.println("sors dans la zone");
    	  shapeAppearance.setMaterial(highlight);
      wakeupOn(wEnter);}
    }
  }


