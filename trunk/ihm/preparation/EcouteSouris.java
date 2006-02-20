package ihm.preparation;

import ihm.ModeleTable;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Locale;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

import donnees.Colis;

public class EcouteSouris extends JFrame implements MouseListener{
	private Container container=null;
	//private ModeleTable tableauMod=null;
	//private JTable tableau=null;
	//private Canvas3D canvas3D=null;
	BranchGroup scene=null;
	SimpleUniverse simpleU=null;
	Canvas3D canvas3D=null;
	TransformGroup objSpin=null;
	Box box=null;
	
	public EcouteSouris(/*JTable tableau, */Container container/*, ModeleTable tableauMod, Canvas3D canvas3D*/){
		super();
		this.container=container;
	}
	
	public void mousePressed(MouseEvent ev){
		/*scene.removeChild(objSpin);
	    objSpin.removeChild(box);
	    scene.addChild(objSpin);
	    container.add(canvas3D);*/
	    
	    /*scene.removeChild(objSpin);
	    objSpin.addChild(box);
	    scene.addChild(objSpin);*/
		this.container.repaint();
		
		JOptionPane.showMessageDialog(this,"Veuillez sélectionner un colis","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	//	Fonction permettant de créer l'objet 3D dans le container
	public void Objet3D(float largeur, float hauteur, float profondeur){
		// Zone 3D de la liste des colis
	    canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
	    canvas3D.setBounds(100,40,400,260);
	    	    
	    // Creation d'un objet SimpleUniverse
	    simpleU = new SimpleUniverse(canvas3D);
	    
	    // Positionnement du point d'observation pour avoir une vue correcte de la scene 3D
	    simpleU.getViewingPlatform().setNominalViewingTransform();
	    
	    // Creation de la scene 3D qui contient tous les objets 3D que l'on veut visualiser
	    scene = new BranchGroup();
	    BranchGroup scene2=new BranchGroup();
	    	    
	    // Objet  relatif aux paramêtres du milieu (echelle, ...)
	    Transform3D transform3D=new Transform3D();
	    // Changement de l'échelle 
	    transform3D.setScale(0.1f);

	    // Partie concernant l'animation du cube
	    objSpin = new TransformGroup(transform3D);
	    objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    Alpha rotationAlpha = new Alpha(-1, 4000);
	    RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, objSpin);
	    BoundingSphere bounds = new BoundingSphere();
	    rotator.setSchedulingBounds(bounds);
	    objSpin.addChild(rotator);
	    // Fin animation cube
	    
	    // Arrière plan de la scène 3D
	    Background background = new Background(1, 1, 1);
	    background.setColor(new Color3f(new Color(238,238,238)));
	    background.setApplicationBounds(new BoundingBox());
	    
	    //Test
	    PolygonAttributes pol = new PolygonAttributes();
	    pol.setPolygonMode(PolygonAttributes.POLYGON_LINE);
	    
	    //Zone d'éclairage de la lumière
	    DirectionalLight lumiereDir=new DirectionalLight();
	    AmbientLight lumiere=new AmbientLight();
	    lumiereDir.setColor(new Color3f(Color.cyan));
	    lumiere.setInfluencingBounds(new BoundingBox());
	    lumiereDir.setInfluencingBounds(new BoundingBox());
	    
	    // Couleur du cube
	    Material materiau=new Material();
	    materiau.setAmbientColor(new Color3f(Color.blue));
	    Appearance apparence=new Appearance();
	    apparence.setMaterial(materiau);
	    apparence.setPolygonAttributes(pol);
	    
	    // Ajout des paramètres à la scène
	    scene2.addChild(lumiere);
	    scene2.addChild(lumiereDir);
	    scene2.addChild(background);
	    
	     // Construction du cube
	    box=new Box(largeur, hauteur, profondeur, apparence);
	    objSpin.addChild(box);
	    scene2.addChild(objSpin);
	    
	    // Compilation de la scene 3D
	    scene.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	    scene.setCapability(BranchGroup.ALLOW_COLLISION_BOUNDS_WRITE);
	    scene2.setCapability(BranchGroup.ALLOW_DETACH);
	    scene2.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	    objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    
	    scene.addChild(scene2);
	    scene2.compile();
	    
	    // Phase TEST
	    simpleU.addBranchGraph(scene);
	    
	    scene2.detach();
	    //Locale locale =simpleU.getLocale();
	    //BranchGroup scene2=new BranchGroup();
	    //locale.replaceBranchGraph(scene, scene2);
	 
	    scene.removeChild(scene2);
	    scene2.compile();
	    scene.addChild(scene2);
	    //scene.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	    //scene.setCapability(BranchGroup.ALLOW_COLLISION_BOUNDS_WRITE);
	    //scene2.setCapability(BranchGroup.ALLOW_DETACH);
	    
	    
	    /*locale.addBranchGraph(scene);*/
	    //scene.removeChild(objSpin);
	    //objSpin.addChild(box);
	    //scene.addChild(objSpin);
	    
	    
	    //Fin phase Test
	    
	    //	  Attachement de la scene 3D a l'objet SimpleUniverse
	    //simpleU.addBranchGraph(scene);
	    
	    container.add(canvas3D);
	}
	
	public Canvas3D rm(){
		objSpin.addChild(new Box(0.1f, 0.1f, 0.1f, null));
		/*scene.removeChild(objSpin);
		scene.compile();
		simpleU.addBranchGraph(scene);*/
		scene.compile();
		return canvas3D;
	}
}
