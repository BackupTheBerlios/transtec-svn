package ihm.preparation;

/*
/*	Classe permettant d'afficher le colis dans le container (fenêtre)
/* 	et surtout de changer l'affichage en fonction du colis cliqué sur le tableau
*/	

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
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

import donnees.Colis;

public class AffichageColisDynamique extends JFrame implements MouseListener{
	private Container container;
	private BranchGroup scene;
	private Canvas3D canvas3D;
	private TransformGroup objSpin;
	ModeleTable tableColisMod;
	JTable tableColis;
	
	public AffichageColisDynamique(Container container, ModeleTable tableColisMod, JTable tableColis){
		super();
		this.scene=null;
		this.canvas3D=null;
		this.objSpin=null;
		this.container=container;
		this.tableColisMod=tableColisMod;
		this.tableColis=tableColis;
	}
	
	public void mousePressed(MouseEvent ev){
		// On supprime la branche -> tout l'objet 3D
		this.scene.removeAllChildren();
		
		// On rajoute le nouveau
		int a=this.tableColis.getSelectedRow();
		if(a==-1)	a=0;
		Colis colis=new Colis((Vector)tableColisMod.getRow(a));
		this.scene.addChild(creationObjet(colis));
		
		// Mise à jour de la zone graphique
		this.canvas3D.repaint();
	}
	
	public void update(Colis colis, ModeleTable tableColisMod, JTable tableColis){
//		 On supprime la branche -> tout l'objet 3D
		this.scene.removeAllChildren();
		
		// On rajoute le nouveau
		this.scene.addChild(creationObjet(colis));
		
		// Mise à jour de la zone graphique
		this.canvas3D.repaint();
		
		// Màj tableau
		this.tableColisMod=tableColisMod;
		this.tableColis=tableColis;
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
	public void Initialisation(Colis colis){
		// Zone 3D de la liste des colis
	    canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
	    canvas3D.setBounds(55,255,232,300);
	    	    
	    // Creation d'un objet SimpleUniverse
	    SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
	    
	    // Positionnement du point d'observation pour avoir une vue correcte de la scene 3D
	    simpleU.getViewingPlatform().setNominalViewingTransform();
	    
	    // Creation de la scene 3D qui contient tous les objets 3D que l'on veut visualiser
	    scene = new BranchGroup();
	    	    
	    // Ajout de la capacité à enlever des noeuds dans la branche
	    scene.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	    scene.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
	    
	    // Ajout de la branche (objet + contexte)
	    scene.addChild(creationObjet(colis));
	    
	    // Attachement de la scene 3D a l'objet SimpleUniverse
	    simpleU.addBranchGraph(scene);
	    
	    container.add(canvas3D);
	}
	
	// Fonction permettant de créer un cube
	private BranchGroup creationObjet(Colis colis){
		// Création de la branche
		BranchGroup branche=new BranchGroup();
		
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
		
	    // Zone d'éclairage de la lumière
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
	    
	    // Ajout des paramètres à la scène
	    branche.addChild(lumiere);
	    branche.addChild(lumiereDir);
	    branche.addChild(background);
	    
	    //Construction du cube
	    // Echelle
	    float taille[]=echelleColis(colis);
	    objSpin.addChild(new Box(taille[1], taille[2], taille[0], apparence));
	    branche.addChild(objSpin);
	    
	    // Ajout de la capacité à séparer la branche
	    branche.setCapability(BranchGroup.ALLOW_DETACH);
	    
	    branche.compile();
	    
		return branche;
	}
	
	// Permet au colis de ne pas être plus grand que la scène 3D
	private float[] echelleColis(Colis colis){
		float taille[] =new float[3];
		if(colis!=null){
			// initialisation
			taille[0]=colis.getModele().getLargeur().floatValue();
			taille[1]=colis.getModele().getProfondeur().floatValue();
			taille[2]=colis.getModele().getHauteur().floatValue();
			
			// On réduit la taille
			while(taille[0]>0.5f || taille[1]>0.5f || taille[2]>0.5f){
				taille[0]/=1.1f;
				taille[1]/=1.1f;
				taille[2]/=1.1f;
			}
		}
		else{
			taille[0]=0;
			taille[1]=0;
			taille[2]=0;
		}
		
		return taille;
	}
}
