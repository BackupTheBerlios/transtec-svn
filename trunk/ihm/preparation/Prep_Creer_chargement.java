package ihm.preparation;

import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Geometry;
import javax.media.j3d.LineArray;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.Box;

import accesBDD.AccesBDDColis;
import donnees.Colis;
import donnees.Preparation;

public class Prep_Creer_chargement extends JFrame implements ActionListener{
	private JButton creer=new JButton("Créer");
	private JButton ajouter=new JButton("->");
	private Vector nomColonnes = new Vector();
	private ModeleTable listeColis, listeChargement;
	private TableSorter sorter_colis, sorter_chargement;
	private JTable tab;
	private Vector donnees = new Vector();
	
	public Prep_Creer_chargement(Preparation preparation) {
		super(preparation.getUtilisateur().getPersonne().getNom()+" "+preparation.getUtilisateur().getPersonne().getPrenom()+" - Preparateur");
		
		Container ct = this.getContentPane();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		
		// Taille de la fenêtre
		setSize(800,600);
		setBounds(0,0,1260,750);
		
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Affichage du bouton "Créer"
		creer.setBounds(220,560,100,50);
	    ct.add(creer);
	    creer.addActionListener(this);
	    
	    // Affichage du bouton "->"
	   	ajouter.setBounds(520,140,100,50);
	    ct.add(ajouter);
	    ajouter.addActionListener(this);
	    
//	  Création de la première ligne
		nomColonnes.add("id");
		nomColonnes.add("entrepot");
		nomColonnes.add("code_barre");
		nomColonnes.add("expediteur");
		nomColonnes.add("destinataire");
		nomColonnes.add("destination");
		nomColonnes.add("utilisateur");
		nomColonnes.add("poids");
		nomColonnes.add("date_envoi");
		nomColonnes.add("modele");
		nomColonnes.add("valeur_declaree");
		
		
		// Acces BDD pour récupération liste des colis pour la destination donnée
		AccesBDDColis bddColis=new AccesBDDColis();
		try{
			Vector listeColisBDD=bddColis.listerDest(preparation.getDestination().getId());
			for(int i=0;i<listeColisBDD.size();i++){
				donnees.addElement(((Colis)listeColisBDD.get(i)).toVector());
			}
		}
		catch(SQLException SQLe){
			
		}
		
		
		//Création du premier tableau
		
		listeColis = new ModeleTable(nomColonnes,donnees);
		//Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter_colis = new TableSorter(listeColis);
		// Création du tableau
		tab = new JTable(sorter_colis);
		// initialisation du Sorter
		sorter_colis.setTableHeader(tab.getTableHeader());
	
		tab.setAutoCreateColumnsFromModel(true);
		tab.setOpaque(false);
		tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		tab.removeColumn(tab.getColumnModel().getColumn(2));
		tab.removeColumn(tab.getColumnModel().getColumn(2));
		JScrollPane scrollPane = new JScrollPane(tab);
		tab.setPreferredScrollableViewportSize(new Dimension(400,150));
		scrollPane.setBounds(100,400,500,150);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		getContentPane().add(scrollPane);

		// Création du tableau contenant les colis que l'on veut mettre dans le chargement
		
		listeColis = new ModeleTable(nomColonnes,donnees);
		//Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter_chargement = new TableSorter(listeColis);
		// Création du tableau
		tab = new JTable(sorter_chargement);
		// initialisation du Sorter
		sorter_chargement.setTableHeader(tab.getTableHeader());
	
		tab.setAutoCreateColumnsFromModel(true);
		tab.setOpaque(false);
		tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		tab.removeColumn(tab.getColumnModel().getColumn(2));
		tab.removeColumn(tab.getColumnModel().getColumn(2));
		JScrollPane scrollPane_chargement = new JScrollPane(tab);
		tab.setPreferredScrollableViewportSize(new Dimension(400,150));
		scrollPane_chargement.setBounds(650,400,500,150);
		scrollPane_chargement.setOpaque(false);
		scrollPane_chargement.getViewport().setOpaque(false);
		getContentPane().add(scrollPane_chargement);
		
		//Ajout d'un objet 3D
		Objet3D(ct, 0.5f, 0.6f, 0.3f);
		
		// Creation de la zone 3D correspondant au camion
		
		Canvas3D camion3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
	    camion3D.setBounds(520,100,400,260);
	    
	    // Creation d'un objet SimpleUniverse
	    SimpleUniverse simpleU = new SimpleUniverse(camion3D);
	    
	    // Positionnement du point d'observation pour avoir une vue correcte de la
	    // scene 3D
	    simpleU.getViewingPlatform().setNominalViewingTransform();
	    
	    // Creation de la scene 3D qui contient tous les objets 3D que l'on veut
	    // visualiser
	    
	    BranchGroup parent = new BranchGroup();
	    
	    // Arriere plan en blanc
	    Background background = new Background(1, 1, 1);
	    background.setColor(new Color3f(Color.LIGHT_GRAY));
	    background.setApplicationBounds(new BoundingBox());
	    parent.addChild(background);
	    
	    // Création d'un repère
	    Point3f repere[]=new Point3f[4];
	    repere[0]=new Point3f(0,0,0);
	    repere[1]=new Point3f(2,0,0);
	    repere[2]=new Point3f(0,0,8);
	    repere[3]=new Point3f(0,5,0);
	    
//	  Objet  relatif aux paramêtres du milieu (echelle, ...)
	    Transform3D transform3D=new Transform3D();
	    // Changement de l'échelle 
	    transform3D.setScale(0.3f);
	    // Rotation
	    transform3D.rotX(1);
	    transform3D.rotY(1);
	    
	    LineArray lineArray = new LineArray(4, LineArray.COORDINATES | 
                LineArray.COLOR_3); 
	    lineArray.setCoordinates(0, repere);
	    lineArray.setColor(0,new Color3f(Color.black));
	    lineArray.setColor(0,new Color3f(Color.black));
	    TransformGroup objSpin=new TransformGroup(transform3D);
	    Shape3D test=new Shape3D();
	    test.setGeometry(lineArray);
	    objSpin.addChild(test);
	    parent.addChild(objSpin);


	    /*// Construction du parallelepipede
	    
	    // Objet  relatif aux paramêtres du milieu (echelle, ...)
	    Transform3D transform3D=new Transform3D();
	    // Changement de l'échelle 
	    transform3D.setScale(0.3f);
	    // Rotation
	    transform3D.rotX(1);
	    transform3D.rotY(1);
	    
	    TransformGroup objSpin = new TransformGroup(transform3D);
	    // Couleur de l'objet 3D
	    Material materiau=new Material();
	    materiau.setAmbientColor(new Color3f(Color.blue));
	    
	    DirectionalLight lumiereDir=new DirectionalLight();
	    AmbientLight lumiere=new AmbientLight();
	    // Zone d'éclairage de la lumière
	    lumiere.setInfluencingBounds(new BoundingBox());
	    lumiereDir.setInfluencingBounds(new BoundingBox());
	    // Ajout de lalumière dans le système
	    parent.addChild(lumiere);
	    parent.addChild(lumiereDir);
	    
	    TransparencyAttributes transparence=new TransparencyAttributes();
	    transparence.setTransparency(0.5f);
	    transparence.setTransparencyMode(TransparencyAttributes.NICEST);
	    Appearance apparence=new Appearance();
	    apparence.setMaterial(materiau);
	    apparence.setTransparencyAttributes(transparence);
	    Box cam = new Box(0.2f, 0.5f, 0.8f, apparence);
	    objSpin.addChild(cam);
	    parent.addChild(objSpin);
	    */
	    BranchGroup scene= parent;
	    
	    // Compilation de la scene 3D
	    scene.compile();
	    
	    // Attachement de la scene 3D a l'objet SimpleUniverse
	    simpleU.addBranchGraph(scene);
	    ct.add(camion3D);
	    
	    
	    
		setVisible(true);
	}
	
	//Fonction permettant de créer l'objet 3D
	public void Objet3D(Container ct_tmp, float largeur, float hauteur, float profondeur){
//		Zone 3D de la liste des colis
		//Creation du Canvas 3D
	    Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
	    canvas3D.setBounds(100,40,400,260);
	   // this.add(canvas3D, BorderLayout.CENTER);
	    // Etape 4 :
	    // Creation d'un objet SimpleUniverse
	    SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
	    // Etape 5 :
	    // Positionnement du point d'observation pour avoir une vue correcte de la
	    // scene 3D
	    simpleU.getViewingPlatform().setNominalViewingTransform();
	    // Etape 6 :
	    // Creation de la scene 3D qui contient tous les objets 3D que l'on veut
	    // visualiser
	    BranchGroup scene = createSceneGraph(hauteur, largeur, profondeur);
	    
	    // Compilation de la scene 3D
	    scene.compile();
	    
	    // Attachement de la scene 3D a l'objet SimpleUniverse
	    simpleU.addBranchGraph(scene);
	    ct_tmp.add(canvas3D);
	}
	
	
	 public BranchGroup createSceneGraph(float largeur, float hauteur, float profondeur) {
		    // Creation de l'objet parent qui contiendra tous les autres objets 3D
		    BranchGroup parent = new BranchGroup();
		    /************ Partie de code concernant l'animation du cube *************/
		    /* Elle sera expliquee en details dans les chapitres relatifs aux
		       transformations geometriques et aux animations */
		    TransformGroup objSpin = new TransformGroup();
		    objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		    Alpha rotationAlpha = new Alpha(-1, 4000);
		    RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, objSpin);
		    BoundingSphere bounds = new BoundingSphere();
		    rotator.setSchedulingBounds(bounds);
		    objSpin.addChild(rotator);
		    /*************** Fin de la partie relative a l'animation ***************/
		    // Arriere plan en blanc
		    Background background = new Background(1, 1, 1);
		    background.setApplicationBounds(new BoundingBox());
		    parent.addChild(background);
		     // Construction du parallelepipede
		    objSpin.addChild(new Box(largeur, hauteur, profondeur, null));
		    parent.addChild(objSpin);
		    return parent;
		  }
	
	public void actionPerformed(ActionEvent ev) {
		
		Object source = ev.getSource();
		
		//Sélection de "Créer"
		if(source == creer){
			dispose();
		}		
	}
}
