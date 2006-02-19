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
	private ModeleTable tableauMod=null;
	private JTable tableau=null;
	
	public EcouteSouris(JTable tableau, Container container, ModeleTable tableauMod){
		super();
		this.tableau=tableau;
		this.tableau.addMouseListener(this);
		this.container=container;
		this.tableauMod=tableauMod;
	}
	
	public void mousePressed(MouseEvent ev){
		Colis colis=new Colis((Vector)tableauMod.getRow(tableau.getSelectedRow()));
		Objet3D(container,
				colis.getModele().getLargeur().intValue(),
				colis.getModele().getProfondeur().intValue(),
				colis.getModele().getHauteur().intValue());
		//JOptionPane.showMessageDialog(this,"Veuillez sélectionner un colis","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
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
	public void Objet3D(Container container, float largeur, float hauteur, float profondeur){
		// Zone 3D de la liste des colis
	    Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
	    canvas3D.setBounds(100,40,400,260);
	    	    
	    // Creation d'un objet SimpleUniverse
	    SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
	    
	    // Positionnement du point d'observation pour avoir une vue correcte de la scene 3D
	    simpleU.getViewingPlatform().setNominalViewingTransform();
	    
	    // Creation de la scene 3D qui contient tous les objets 3D que l'on veut visualiser
	    BranchGroup scene = new BranchGroup();
	    	    
	    // Objet  relatif aux paramêtres du milieu (echelle, ...)
	    Transform3D transform3D=new Transform3D();
	    // Changement de l'échelle 
	    transform3D.setScale(0.1f);

	    // Partie concernant l'animation du cube
	    TransformGroup objSpin = new TransformGroup(transform3D);
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
	    scene.addChild(lumiere);
	    scene.addChild(lumiereDir);
	    scene.addChild(background);
	    
	     // Construction du cube
	    objSpin.addChild(new Box(largeur, hauteur, profondeur, apparence));
	    scene.addChild(objSpin);
	    
	    // Compilation de la scene 3D
	    scene.compile();
	    
	    // Attachement de la scene 3D a l'objet SimpleUniverse
	    simpleU.addBranchGraph(scene);
	    container.add(canvas3D);
	}
}
