package ihm.preparation;

import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.media.j3d.Alpha;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.TransformGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

import accesBDD.AccesBDDColis;

import donnees.Colis;
import donnees.Preparation;

public class Prep_Creer_chargement extends JFrame implements ActionListener{
	private JButton creer=new JButton("Créer");
	private JButton ajouter=new JButton("->");
	private Vector nomColonnes = new Vector();
	private ModeleTable listeColis;
	private TableSorter sorter;
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
		creer.setBounds(350,400,100,50);
	    ct.add(creer);
	    creer.addActionListener(this);
	    
	    // Affichage du bouton "->"
	   	creer.setBounds(540,300,100,50);
	    ct.add(creer);
	    creer.addActionListener(this);
	    
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
		sorter = new TableSorter(listeColis);
		// Création du tableau
		tab = new JTable(sorter);
		// initialisation du Sorter
		sorter.setTableHeader(tab.getTableHeader());
	
		tab.setAutoCreateColumnsFromModel(true);
		tab.setOpaque(false);
		tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tab.removeColumn(tab.getColumnModel().getColumn(0));
		JScrollPane scrollPane = new JScrollPane(tab);
		tab.setPreferredScrollableViewportSize(new Dimension(400,150));
		scrollPane.setBounds(100,400,500,150);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		getContentPane().add(scrollPane);
		
		//Ajout d'un objet 3D
		Objet3D(ct, 0.5f, 0.6f, 0.3f);
		
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
	   /* BranchGroup scene = new BranchGroup();
	    Background background = new Background(new Color3f(Color.blue));     
		background.setApplicationBounds(new BoundingBox());
	    // On ajoute l'instance de Background a la scene 3D
	    scene.addChild(background);*/
	    
	    // Etape 7 :
	    // Compilation de la scene 3D
	    scene.compile();
	    // Etape 8:
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
