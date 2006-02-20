package ihm.preparation;

import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.RenderingAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.Box;

import accesBDD.AccesBDDChargement;
import accesBDD.AccesBDDColis;
import donnees.Camion;
import donnees.Chargement;
import donnees.Colis;
import donnees.Preparation;

public class Prep_Creer_chargement extends JFrame implements ActionListener{
	private JButton creer=new JButton("Créer"), ajouter=new JButton(),supprimer=new JButton(), annuler=new JButton("Annuler");
	private Vector nomColonnes = new Vector();
	private ModeleTable listeColisMod, listeChargementMod;
	private TableSorter sorter_colis, sorter_chargement;
	private JTable listeColisTab, listeChargementTab;
	private Vector listeColis= new Vector(), donnees = new Vector();
	private int ligneActive;
	private Preparation preparation=null;
	EcouteSouris test=null;
		
	public Prep_Creer_chargement(Preparation preparation) {
		super(preparation.getUtilisateur().getPersonne().getNom()+" "+preparation.getUtilisateur().getPersonne().getPrenom()+" - Preparateur");
		
		Colis premierColisAAfficher=null;
		// Initialisation de la preparation pour la classe
		this.preparation=preparation;
		
		Container ct = this.getContentPane();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		
		// Taille de la fenêtre
		setSize(800,600);
		setBounds(0,0,1260,750);
		
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
		creer.setBounds(220,560,100,50);
	    ct.add(creer);
	    creer.addActionListener(this);
	    
	    // Affichage du bouton "Annuler"
	    annuler.setBounds(400, 560, 100, 50);
	    ct.add(annuler);
	    annuler.addActionListener(this);
	    
	    // Affichage du bouton "->"
	   	ajouter.setBounds(620,140,100,50);
	    ct.add(ajouter);
	    ajouter.addActionListener(this);
	    
	    // Affichage du bouton "<-"
	    supprimer.setBounds(620, 400, 80, 40);
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
			}
			premierColisAAfficher=(Colis)listeColisBDD.get(0);
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
		scrollPane.setBounds(100,400,500,150);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		getContentPane().add(scrollPane);

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
		scrollPane_chargement.setBounds(650,400,500,150);
		scrollPane_chargement.setOpaque(false);
		scrollPane_chargement.getViewport().setOpaque(false);
		getContentPane().add(scrollPane_chargement);
		
		//Ajout d'un objet 3D
		//Objet3D(ct, 0.5f, 0.6f, 0.3f);
		test=new EcouteSouris(ct);
		if(premierColisAAfficher!=null){
			
			test./*Objet3D(
					premierColisAAfficher.getModele().getLargeur().intValue(),
					premierColisAAfficher.getModele().getProfondeur().intValue(),
					premierColisAAfficher.getModele().getHauteur().intValue())*/
					Objet3D(0.5f, 0.6f, 0.3f);
		}
		
		// Creation de la zone 3D correspondant au camion
		Canvas3D camion3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
	    camion3D.setBounds(700,100,400,260);
	    
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
	    
	    // Objet  relatif aux paramêtres du milieu (echelle, ...)
	    Transform3D transform3D=new Transform3D();
	    
	    // Rotation
	    transform3D.rotY(-0.4f);
	    
	    //Nouvel objet
	    TransformGroup objSpin = new TransformGroup(transform3D);
	    
	    //Affichage du camion
	    PolygonAttributes pol = new PolygonAttributes();
	    pol.setPolygonMode(PolygonAttributes.POLYGON_LINE);
	    pol.setCullFace(PolygonAttributes.CULL_NONE);
	    
	    //Création d'une apparence
	    Appearance apparence=new Appearance();
	    apparence.setPolygonAttributes(pol);
	    
//*********************************CREATION DU CUBE*****************************************//
	    
	    // Les coordonnees des 16 sommets des 4 faces visibles du cube
	    // Face 1
	    Point3f face1_s1 = new Point3f(-0.5f, 0.5f, 0.5f);
	    Point3f face1_s2 = new Point3f(-0.5f, -0.5f, 0.5f);
	    Point3f face1_s3 = new Point3f( 0.5f, -0.5f, 0.5f);
	    Point3f face1_s4 = new Point3f( 0.5f, 0.5f, 0.5f);
	    // Face 2
	    Point3f face2_s1 = new Point3f( 0.5f, 0.5f, 0.5f);
	    Point3f face2_s2 = new Point3f( 0.5f, -0.5f, 0.5f);
	    Point3f face2_s3 = new Point3f( 0.5f, -0.5f, -0.5f);
	    Point3f face2_s4 = new Point3f( 0.5f, 0.5f, -0.5f);
	    // Face 3
	    Point3f face3_s1 = new Point3f( 0.5f, 0.5f, -0.5f);
	    Point3f face3_s2 = new Point3f( 0.5f, -0.5f, -0.5f);
	    Point3f face3_s3 = new Point3f(-0.5f, -0.5f, -0.5f);
	    Point3f face3_s4 = new Point3f(-0.5f, 0.5f, -0.5f);
	    // Face 4
	    Point3f face4_s1 = new Point3f(-0.5f, 0.5f, -0.5f);
	    Point3f face4_s2 = new Point3f(-0.5f, -0.5f, -0.5f);
	    Point3f face4_s3 = new Point3f(-0.5f, -0.5f, 0.5f);
	    Point3f face4_s4 = new Point3f(-0.5f, 0.5f, 0.5f);
	    // Les couleurs des 4 faces visibles du cube
	    Color4f color1 = new Color4f(Color.darkGray);
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
	    /* couleur face 1 */ color1, color1, color1, color1,
	    /* couleur face 2 */ color1, color1, color1, color1,
	    /* couleur face 3 */ color1, color1, color1, color1,
	    /* couleur face 4 */ color1, color1, color1, color1
	    });
	    
//******************************************************************************************//	    
	    
	    //Création du shade3D
	    Shape3D shape = new Shape3D();
	    shape.setGeometry(quadArray);
	    shape.setAppearance(apparence);
	    
	    //Ajout de l'objet crée
	    objSpin.addChild(shape);
	    parent.addChild(objSpin);
	    
	    // Compilation de la scene 3D
	    parent.compile();
	    
	    // Attachement de la scene 3D a l'objet SimpleUniverse
	    simpleU.addBranchGraph(parent);
	    
	    //Ajout au container
	    ct.add(camion3D);
	    
		setVisible(true);
	}
	
	//Fonction permettant de créer l'objet 3D dans le container
	/*public Canvas3D Objet3D(/*Container container, float largeur, float hauteur, float profondeur){
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
	    container.repaint();
	    
	    
	    JOptionPane.showMessageDialog(this,"Veuillez sélectionner un colis","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
	    scene.removeAllChildren();
	    scene.compile();
	    simpleU.addBranchGraph(scene);
	    container.repaint();
	    //return canvas3D;
	}*/
	
	public void actionPerformed(ActionEvent ev) {
		AccesBDDChargement bddChargement=new AccesBDDChargement();
		Vector aCharger=new Vector();
		Object source = ev.getSource();
		
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
				Colis colis=new Colis((Vector)listeColisMod.getRow(ligneActive));
				preparation.ajouterVolumeColis(colis.getVolume());
				listeChargementMod.fireTableDataChanged();
				//On supprime de la liste des colis
				listeColisMod.removeRow(ligneActive);
				listeColisMod.fireTableDataChanged();
				//Mise à jour des tableaux
				listeColisTab.updateUI();
				listeChargementTab.updateUI();
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
				Colis colis=new Colis((Vector)listeChargementMod.getRow(ligneActive));
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
			test.rm();
			
		}
	}
}
