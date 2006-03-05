package ihm.preparation;

import ihm.preparation.CollisionDetector;
import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

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
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.picking.PickRotateBehavior;
import com.sun.j3d.utils.behaviors.picking.PickTranslateBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

import accesBDD.AccesBDDChargement;
import accesBDD.AccesBDDColis;
import donnees.Chargement;
import donnees.Colis;
import donnees.Preparation;

public class Prep_Creer_chargement extends JFrame implements ActionListener{
	private JButton creer=new JButton("Créer"), ajouter=new JButton(),supprimer=new JButton(), annuler=new JButton("Annuler");
	private ModeleTable listeColisMod, listeChargementMod;
	private TableSorter sorter_colis, sorter_chargement;
	private JTable listeColisTab, listeChargementTab;
	private Vector listeColis= new Vector(), donnees = new Vector();
	private Preparation preparation=null;
	private BranchGroup scene;
	private Canvas3D camion3D;
	private deplacementColis deplacement=new deplacementColis(ajouter);
		
	public Prep_Creer_chargement(Preparation preparation) {
		super(preparation.getUtilisateur().getPersonne().getNom()+" "+preparation.getUtilisateur().getPersonne().getPrenom()+" - Preparateur");
		
		Vector nomColonnes = new Vector();
		Colis premierColisAAfficher=null;
		
		// Initialisation de la preparation pour la classe
		this.preparation=preparation;
		
		Container ct = this.getContentPane();
		
		// Taille de la fenêtre
		setSize(800,600);
		setBounds(20,100,1240,680);
		
		ct = getContentPane();
		ct.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Création des icônes
		ImageIcon icone_ajouter=new ImageIcon("images/icones/flech_droite_gauche.gif");
		ImageIcon icone_supprimer=new ImageIcon("images/icones/flech_gauche_droite.gif");
		
		// Insertion des icônes dans les boutons
		ajouter.setIcon(icone_ajouter);
		supprimer.setIcon(icone_supprimer);
		
		// Affichage du bouton "Créer"
		creer.setBounds(345,540,100,40);
	    ct.add(creer);
	    creer.addActionListener(this);
	    
	    // Affichage du bouton "Annuler"
	    annuler.setBounds(525, 540, 100, 40);
	    ct.add(annuler);
	    annuler.addActionListener(this);
	    
	    // Affichage du bouton "->"
	   	ajouter.setBounds(460,100,55,40);
	    ct.add(ajouter);
	    ajouter.addActionListener(this);
	    
	    // Affichage du bouton "<-"
	    supprimer.setBounds(460, 180, 55, 40);
	    ct.add(supprimer);
	    supprimer.addActionListener(this);
	    
	    // Création de la première ligne
		nomColonnes.add("Id");
		nomColonnes.add("entrepot");
		nomColonnes.add("Code Barre");
		nomColonnes.add("expediteur");
		nomColonnes.add("destinataire");
		nomColonnes.add("destination");
		nomColonnes.add("utilisateur");
		nomColonnes.add("Poids");
		nomColonnes.add("Date Envoi");
		nomColonnes.add("Modele");
		nomColonnes.add("valeur_declaree");
		nomColonnes.add("Volume");
		
		// Acces BDD pour récupération liste des colis pour la destination donnée
		AccesBDDColis bddColis=new AccesBDDColis();
		try{
			Vector listeColisBDD=bddColis.listerDest(preparation.getDestination().getId());
			for(int i=0;i<listeColisBDD.size();i++){
				listeColis.addElement(((Colis)listeColisBDD.get(i)).toVector());
				if(i==0)	premierColisAAfficher=(Colis)listeColisBDD.get(i);
			}
		}
		catch(SQLException SQLe){
			
		}
		
		//Création du tableau listant les colis pour la destination
		
		listeColisMod = new ModeleTable(nomColonnes,listeColis);
		//Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter_colis = new TableSorter(listeColisMod);
		// Création du tableau
		listeColisTab = new JTable(sorter_colis);
		// initialisation du Sorter
		sorter_colis.setTableHeader(listeColisTab.getTableHeader());
	
		listeColisTab.setAutoCreateColumnsFromModel(true);
		listeColisTab.setOpaque(false);
		listeColisTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeColisTab.removeColumn(listeColisTab.getColumnModel().getColumn(0));
		listeColisTab.removeColumn(listeColisTab.getColumnModel().getColumn(0));
		listeColisTab.removeColumn(listeColisTab.getColumnModel().getColumn(1));
		listeColisTab.removeColumn(listeColisTab.getColumnModel().getColumn(1));
		listeColisTab.removeColumn(listeColisTab.getColumnModel().getColumn(1));
		listeColisTab.removeColumn(listeColisTab.getColumnModel().getColumn(1));
		listeColisTab.removeColumn(listeColisTab.getColumnModel().getColumn(3));
		listeColisTab.removeColumn(listeColisTab.getColumnModel().getColumn(3));
		JScrollPane scrollPane = new JScrollPane(listeColisTab);
		listeColisTab.setPreferredScrollableViewportSize(new Dimension(400,150));
		scrollPane.setBounds(20,360,400,150);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		getContentPane().add(scrollPane);
		
		// Ajout de l'écoute souris et de la zone graphique au dessus
		// SANS DOUTE ENLEVER FCT INITIALIZE
		AffichageColisDynamique zoneColis3D=new AffichageColisDynamique(ct, listeColisMod, listeColisTab);
		// Le premier colis de la liste que l'on affiche
		zoneColis3D.Initialisation(premierColisAAfficher);
		// Ecoute de la souris par rapport au tableau
		listeColisTab.addMouseListener(zoneColis3D);

		// Création du tableau contenant les colis que l'on veut mettre dans le chargement
		
		listeChargementMod = new ModeleTable(nomColonnes,donnees);
		//Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter_chargement = new TableSorter(listeChargementMod);
		// Création du tableau
		listeChargementTab = new JTable(sorter_chargement);
		// initialisation du Sorter
		sorter_chargement.setTableHeader(listeChargementTab.getTableHeader());
	
		listeChargementTab.setAutoCreateColumnsFromModel(true);
		listeChargementTab.setOpaque(false);
		listeChargementTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeChargementTab.removeColumn(listeChargementTab.getColumnModel().getColumn(0));
		listeChargementTab.removeColumn(listeChargementTab.getColumnModel().getColumn(0));
		listeChargementTab.removeColumn(listeChargementTab.getColumnModel().getColumn(1));
		listeChargementTab.removeColumn(listeChargementTab.getColumnModel().getColumn(1));
		listeChargementTab.removeColumn(listeChargementTab.getColumnModel().getColumn(1));
		listeChargementTab.removeColumn(listeChargementTab.getColumnModel().getColumn(1));
		listeChargementTab.removeColumn(listeChargementTab.getColumnModel().getColumn(3));
		listeChargementTab.removeColumn(listeChargementTab.getColumnModel().getColumn(3));
		JScrollPane scrollPane_chargement = new JScrollPane(listeChargementTab);
		listeChargementTab.setPreferredScrollableViewportSize(new Dimension(400,150));
		scrollPane_chargement.setBounds(555,360,650,150);
		scrollPane_chargement.setOpaque(false);
		scrollPane_chargement.getViewport().setOpaque(false);
		getContentPane().add(scrollPane_chargement);
		
				
		// Creation de la zone 3D correspondant au camion
		camion3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
	    camion3D.setBounds(555,20,650,300);
	    
	    
	    
	    // Creation d'un objet SimpleUniverse
	    SimpleUniverse simpleU = new SimpleUniverse(camion3D);
	    
	    // Positionnement du point d'observation pour avoir une vue correcte de la scene 3D
	    simpleU.getViewingPlatform().setNominalViewingTransform();
	    
	    // Creation de la scene 3D qui contient tous les objets 3D que l'on veut visualiser
	    this.scene=new BranchGroup();
	    BranchGroup branche = new BranchGroup();
	    
	    // Arriere plan
	    Background background = new Background(1, 1, 1);
	    background.setColor(new Color3f(new Color(238,238,238)));
	    background.setApplicationBounds(new BoundingBox());
	    branche.addChild(background);
	    
//*********************************CREATION DU CAMION*****************************************//
	    
	    // Les coordonnees des 16 sommets des 4 faces visibles du cube
	    // Face 1
	    Point3f face1_s1 = new Point3f(-0.9f, 0.3f, 0.165f);
	    Point3f face1_s2 = new Point3f(0.9f, 0.3f, 0.165f);
	    Point3f face1_s3 = new Point3f( 0.9f, -0.3f, 0.165f);
	    Point3f face1_s4 = new Point3f( -0.9f, -0.3f, 0.165f);
	    // Face 2
	    Point3f face2_s1 = new Point3f( 0.9f, 0.3f, 0.165f);
	    Point3f face2_s2 = new Point3f( 0.9f, 0.3f, -0.165f);
	    Point3f face2_s3 = new Point3f( 0.9f, -0.3f, -0.165f);
	    Point3f face2_s4 = new Point3f( 0.9f, -0.3f, 0.165f);
	    // Face 3
	    Point3f face3_s1 = new Point3f( -0.9f, 0.3f, -0.165f);
	    Point3f face3_s2 = new Point3f( 0.9f, 0.3f, -0.165f);
	    Point3f face3_s3 = new Point3f(0.9f, -0.3f, -0.165f);
	    Point3f face3_s4 = new Point3f(-0.9f, -0.3f, -0.165f);
	    // Face 4
	    Point3f face4_s1 = new Point3f(-0.9f, 0.3f, 0.165f);
	    Point3f face4_s2 = new Point3f(-0.9f, 0.3f, -0.165f);
	    Point3f face4_s3 = new Point3f(-0.9f, -0.3f, -0.165f);
	    Point3f face4_s4 = new Point3f(-0.9f, -0.3f, 0.165f);
	     
	    //Les couleurs des 4 faces visibles du cube
	    Color4f color1 = new Color4f(Color.blue);
	    Color4f color2 = new Color4f(200,200,200,1.0f);
	    
	  
	    // Construction de l'objet geometrique QuadArray constitue de 16
	    // points
	    QuadArray quadArray = new QuadArray(16,
	                                        QuadArray.COORDINATES | QuadArray.COLOR_4);
	    // Tableau des points constituant les 4 faces (quadrilateres) qui
	    // sont visibles
	    quadArray.setCoordinates(0, new Point3f[] {
	    /* face 1 */             face1_s1, face1_s2, face1_s3, face1_s4,
	    /* face 2 */             face2_s1, face2_s2, face2_s3, face2_s4,
	    /* face 3 */             face3_s1, face3_s2, face3_s3, face3_s4,
	    /* face 4 */             face4_s1, face4_s2, face4_s3, face4_s4
	    });
	    // Tableau des couleurs des 4 sommets de chaque face
	    quadArray.setColors(0, new Color4f[] {
	    /* couleur face 1 */ color2, color2, color2, color2,
	    /* couleur face 2 */ color1, color1, color1, color1,
	    /* couleur face 3 */ color1, color1, color1, color1,
	    /* couleur face 4 */ color1, color1, color1, color1
	    });
//******************************************************************************************//	    
	   
//	  Objet  relatif aux paramêtres du milieu (echelle, ...)
	    Transform3D echelle=new Transform3D();
	    Transform3D rotation=new Transform3D();
	  
	    // Echelle
	    echelle.setScale(1);
	    // Rotation
	    rotation.rotY(-0.4f);
	    
	    //Nouvel objet
	    TransformGroup objSpin1 = new TransformGroup(echelle);
	    TransformGroup objSpin2 = new TransformGroup(rotation);
	    
	    //Affichage du camion
	    PolygonAttributes pol = new PolygonAttributes();
	    pol.setPolygonMode(PolygonAttributes.POLYGON_LINE);
	    pol.setCullFace(PolygonAttributes.CULL_NONE);
	    
	    //Création d'une apparence
	    Appearance apparence=new Appearance();
	    apparence.setPolygonAttributes(pol);
	    
	    //Création du shade3D
	    Shape3D shape = new Shape3D();
	    shape.setGeometry(quadArray);
	    shape.setAppearance(apparence);
	   
	    CollisionDetector cd=new CollisionDetector(shape);
		BoundingBox bounds1;
	    bounds1 = new BoundingBox(new Point3d(-0.9f, -0.3f, -0.165f),new Point3d(0.9f, 0.3f, 0.165f));
	    cd.setSchedulingBounds(bounds1);
	    
	    //Ajout de l'objet crée
	    objSpin1.addChild(objSpin2);
	    objSpin2.addChild(shape);
	    //objSpin1.addChild(createSceneGraph(camion3D));
	    //Box cam = new Box(0.1f, 0.1f, 0.1f, apparence);
	    //objSpin.addChild(cam);
	    branche.addChild(objSpin1);
	    
	    // Compilation de la scene 3D
	    branche.compile();
	    //this.scene.addChild(createSceneGraph(camion3D));
	    this.scene.addChild(branche);
	    this.scene.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	    this.scene.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
	    
	    // Attachement de la scene 3D a l'objet SimpleUniverse
	    simpleU.addBranchGraph(this.scene);
	    
	    //Ajout au container
	    ct.add(camion3D);
	    
		setVisible(true);
	}
		
	public void actionPerformed(ActionEvent ev) {
		AccesBDDChargement bddChargement=new AccesBDDChargement();
		Vector aCharger=new Vector();
		Object source = ev.getSource();
		int ligneActive;
		Colis colis=null;
		
		// Création d'un chargement à l'état en cours
		if(source == creer){
			// A changer code barre
			Chargement chargement=new Chargement(
					preparation.getCamionACharger(), 
					new Integer(listeChargementMod.getRowCount()), 
					preparation.getVolumeColis(),
					preparation.getUtilisateur(),
					new Timestamp(System.currentTimeMillis()),
					"42465fssdfsddf");
			try{
				bddChargement.ajouter(chargement);
				for(int i=0;i<listeChargementMod.getRowCount();i++)	aCharger.add(new Colis((Vector)listeChargementMod.getRow(i)));
				bddChargement.AjouterColis(chargement, aCharger);
			}
			catch(SQLException e){
				
			}
			dispose();
		}
		
		// Ajouter un colis dans le camion
		else if(source==ajouter){
			ligneActive = listeColisTab.getSelectedRow();
			if (ligneActive != -1){
				//On ajoute au chargement la ligne selectionnée
				listeChargementMod.addRow(listeColisMod.getRow(ligneActive));
				colis=new Colis((Vector)listeColisMod.getRow(ligneActive));
				preparation.ajouterVolumeColis(colis.getVolume());
				listeChargementMod.fireTableDataChanged();
				//On supprime de la liste des colis
				listeColisMod.removeRow(ligneActive);
				listeColisMod.fireTableDataChanged();
				//Mise à jour des tableaux
				listeColisTab.updateUI();
				listeChargementTab.updateUI();
				
				// Ajout de l'objet 3D
				scene.addChild(brancheCube(0.1f/*colis.getModele().getLargeur()*/, 
					0.1f/*colis.getModele().getProfondeur()*/, 
						0.1f/*colis.getModele().getHauteur()*/));
				//scene.addChild(new deplacementColis(ajouter).ajouterColis(0.1f,0.1f,0.1f));
			}
			else{
				JOptionPane.showMessageDialog(this,"Veuillez sélectionner un colis","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// Supprimer un colis dans le camion
		else if(source==supprimer){
			ligneActive = listeChargementTab.getSelectedRow();
			if (ligneActive != -1){
				// On ajoute à la liste des colis
				listeColisMod.addRow(listeChargementMod.getRow(ligneActive));
				colis=new Colis((Vector)listeChargementMod.getRow(ligneActive));
				preparation.soustraireVolumeColis(colis.getVolume());
				listeColisMod.fireTableDataChanged();
				//On supprime du chargement
				listeChargementMod.removeRow(ligneActive);
				listeChargementMod.fireTableDataChanged();
				//Mise à jour des tableaux
				listeColisTab.updateUI();
				listeChargementTab.updateUI();
			}
			else{
				JOptionPane.showMessageDialog(this,"Veuillez sélectionner un colis","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// Annulation de la création d'un chargement
		else if(source==annuler){
			dispose();			
		}
	}
	
	private BranchGroup brancheCube(float largeur, float profondeur, float hauteur){
		// Création de la branche
		BranchGroup branche=new BranchGroup();
		
		// Objet  relatif aux paramêtres du milieu (echelle, ...)
	    Transform3D echelle=new Transform3D();
	    Transform3D rotation=new Transform3D();
	    Transform3D translation=new Transform3D();
	    // Création des objets
	    // Echelle
	    echelle.setScale(1);
	    // Rotation
	    rotation.rotY(-0.4f);
	    // Translation
	    Vector3f vector = new Vector3f( 0.5f, .0f, .0f);
	    translation.setTranslation(vector);
	    
	    
	    
	    //Création des TransformGroup
	    TransformGroup objSpin1=new TransformGroup(echelle);
	    TransformGroup objSpin2=new TransformGroup(rotation);
	    TransformGroup objSpin3=new TransformGroup(translation);
	    
	    
	    objSpin3.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	    objSpin3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    objSpin3.setCapability(TransformGroup.ALLOW_PICKABLE_WRITE);
	    objSpin3.setCapability(TransformGroup.ALLOW_PICKABLE_READ);
	
	    
	    // Déplacement du colis
	    deplacement.objetADeplacer(objSpin3, translation);

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
	    
	    Box b = new Box(largeur, hauteur, profondeur, apparence);
	    CollisionDetector cd=new CollisionDetector(b);
		BoundingBox bounds1;
	    bounds1 = new BoundingBox(new Point3d(-0.9f, -0.3f, -0.165f),new Point3d(0.9f, 0.3f, 0.165f));
	    cd.setSchedulingBounds(bounds1);
	    
	    //CollisionDetector cd1=new CollisionDetector(b);
		BoundingBox bounds2;
	    bounds2 = new BoundingBox(new Point3d(-0.1f, -0.1f, -0.1f),new Point3d(0.1f, 0.1f, 0.1f));
	    cd.setSchedulingBounds(bounds2);
	    
	    //Construction du cube
	    objSpin1.addChild(objSpin2);
	    objSpin2.addChild(objSpin3);
	    objSpin3.addChild(b);
	    objSpin3.addChild(cd);
	   // objSpin3.addChild(cd1);
	    
	    
	    
	    branche.addChild(objSpin1);
	    
	    // Ajout de la capacité à séparer la branche
	    branche.setCapability(BranchGroup.ALLOW_DETACH);
	    
	    
//	  TEST DYNAMIQUE SOURIS
	    PickTranslateBehavior test=new PickTranslateBehavior(branche, camion3D, new BoundingSphere());
	    branche.addChild(test);
	    // Fin test
	    
	    branche.compile();
	    
		return branche;
	}
	
	public BranchGroup createSceneGraph(Canvas3D canvas) {
		 // Create the root of the branch graph
		 BranchGroup objRoot = new BranchGroup();
		
		 TransformGroup objRotate = null;

		PickRotateBehavior pickRotate = null;
		 //PickTranslateBehavior pickTranslate = null;
		Transform3D transform = new Transform3D();
		BoundingSphere behaveBounds = new BoundingSphere();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0,
			      0.0), 100.0);
		
		 // create ColorCube and PickRotateBehavior objects
		 transform.setTranslation(new Vector3f(-0.6f, 0.0f, -0.6f));
		 objRotate = new TransformGroup(transform);
		 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		 objRotate.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		
		 objRoot.addChild(objRotate);
		 objRotate.addChild(new ColorCube(0.4));
		
		 pickRotate = new PickRotateBehavior(objRoot,canvas, bounds);
		 //pickTranslate=new PickTranslateBehavior(objRoot,canvas, bounds);
		 //pickTranslate.setPickMode(PickTool.GEOMETRY);

		 objRoot.addChild(pickRotate);
		
		 // add a second ColorCube object to the scene graph
		 transform.setTranslation(new Vector3f( 0.6f, 0.0f, -0.6f));

		 objRotate = new TransformGroup(transform);
		 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		 objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		 objRotate.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		
		 objRoot.addChild(objRotate);
		 objRotate.addChild(new ColorCube(0.4));
		
		 // Let Java 3D perform optimizations on this scene graph.
		 objRoot.compile();
		
		return objRoot;
		} // end of createSceneGraph method of MousePickApp
}