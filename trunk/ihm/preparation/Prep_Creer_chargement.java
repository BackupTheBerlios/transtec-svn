package ihm.preparation;

import ihm.preparation.CollisionDetector;
import ihm.Bouton;
import ihm.FenetreType;
import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.Color;
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
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
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

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

import accesBDD.AccesBDDCamion;
import accesBDD.AccesBDDChargement;
import accesBDD.AccesBDDColis;
import donnees.Camion;
import donnees.Chargement;
import donnees.Colis;
import donnees.Entrepot;
import donnees.Preparation;
import donnees.Utilisateur;

public class Prep_Creer_chargement extends JFrame implements ActionListener{
	private Bouton valider, annuler, ajouter, retirer;
	//private JButton creer=new JButton("Créer"), ajouter=new JButton(),supprimer=new JButton(), annuler=new JButton("Annuler");
	private ModeleTable listeColisMod, listeChargementMod;
	private TableSorter sorter_colis, sorter_chargement;
	private JTable listeColisTab, listeChargementTab;
	private Vector listeColis= new Vector(), donnees = new Vector();
	private BranchGroup scene;
	private Canvas3D camion3D;
	private deplacementColis deplacement;
	private float echelle=0;
	private AffichageColisDynamique zoneColis3D=null;
	private Chargement chargement;
	private Utilisateur utilisateur;
		
	public Prep_Creer_chargement(Utilisateur utilisateur, Entrepot entrepot, Camion camion) {
		// Création graphique de la fenêtre
		setTitle("Créer Chargement");
		setSize(1024,768);
		setUndecorated(true);
		FenetreType fenetre=new FenetreType(utilisateur,"images/preparation/fenetre_creerBackground.png");
		setContentPane(fenetre);
		fenetre.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		// Ajout des bouton sur la fenêtre
		this.retirer=new Bouton("images/icones/retirer.png","images/icones/retirer.png");
		this.retirer.setBounds(193, 702, 95, 44);
		fenetre.add(this.retirer);
		this.retirer.addActionListener(this);
		this.ajouter=new Bouton("images/icones/ajouter.png","images/icones/ajouter.png");
		this.ajouter.setBounds(302, 702, 95, 44);
		fenetre.add(this.ajouter);
		this.ajouter.addActionListener(this);
		this.valider=new Bouton("images/icones/valider.png","images/icones/valider.png");
		this.valider.setBounds(550, 702, 108, 44);
		fenetre.add(this.valider);
		this.valider.addActionListener(this);
		this.annuler=new Bouton("images/icones/annuler.png","images/icones/annuler.png");
		this.annuler.setBounds(675, 702, 108, 44);
		fenetre.add(this.annuler);
		this.annuler.addActionListener(this);
		
		// Mémorisation de l'utilisateur
		this.utilisateur=utilisateur;
		Vector nomColonnes = new Vector();
		Colis premierColisAAfficher=null;
		
	
		
	    
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
			Vector listeColisBDD=bddColis.colisACharger(entrepot.getId());
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
		scrollPane.setBounds(55,570,232,120);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		getContentPane().add(scrollPane);
		
		// Ajout de l'écoute souris et de la zone graphique au dessus
		zoneColis3D=new AffichageColisDynamique(fenetre, listeColisMod, listeColisTab);
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
		scrollPane_chargement.setBounds(305,570,660,120);
		scrollPane_chargement.setOpaque(false);
		scrollPane_chargement.getViewport().setOpaque(false);
		getContentPane().add(scrollPane_chargement);
		
				
		// Creation de la zone 3D correspondant au camion
		camion3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
	    camion3D.setBounds(305,255,660,300);
	    
	    
	    
	    // Creation d'un objet SimpleUniverse
	    SimpleUniverse simpleU = new SimpleUniverse(camion3D);
	    
	    // Positionnement du point d'observation pour avoir une vue correcte de la scene 3D
	    simpleU.getViewingPlatform().setNominalViewingTransform();
	    
	    // Creation de la scene 3D qui contient tous les objets 3D que l'on veut visualiser
	    this.scene=new BranchGroup();
	    BranchGroup branche = new BranchGroup();
	    
	    // Arriere plan
	    Background background = new Background(1, 1, 1);
	    background.setColor(new Color3f(new Color(230,255,255)));
	    background.setApplicationBounds(new BoundingBox());
	    branche.addChild(background);
	    
//*********************************CREATION DU CAMION*****************************************//
	   
	    // Les coordonnees des 16 sommets des 4 faces visibles du cube
	    Point3f benne[]=tailleBenne(2.55f, 4, 12);
	    
	    //Les couleurs des 4 faces visibles du cube
//	  Les couleurs des 4 faces visibles du cube
	    Color4f color1 = new Color4f(Color.darkGray);
	    Color4f color2 = new Color4f(Color.gray);
	    Color4f color3 = new Color4f(Color.lightGray);
	    
	    // Construction de l'objet geometrique QuadArray constitue de 16
	    // points
	    QuadArray quadArray = new QuadArray(20, QuadArray.COORDINATES | QuadArray.COLOR_4);
	    // Tableau des points constituant les 4 faces (quadrilateres) qui
	    // sont visibles
	    quadArray.setCoordinates(0, new Point3f[] {
	    /* face 1 */             benne[0],benne[1],benne[2],benne[3],
	    /* face 2 */             benne[4],benne[5],benne[6],benne[7],
	    /* face 3 */             benne[8],benne[9],benne[10],benne[11],
	    /* face 4 */             benne[12],benne[13],benne[14],benne[15],
	    /* face 5 */             benne[16],benne[17],benne[18],benne[19]                                                             
	    });
	    // Tableau des couleurs des 4 sommets de chaque face
	    quadArray.setColors(0, new Color4f[] {
	    	/* couleur face 1 */ color1, color1, color1, color1,
	    	/* couleur face 2 */ color1, color1, color1, color1,
	    	/* couleur face 3 */ color3, color3, color3, color3,
	    	/* couleur face 4 */ color2, color2, color2, color2,
	    	/* couleur face 5 */ color1, color1, color1, color1
	    });
//******************************************************************************************//	    
	   
//	  Objet  relatif aux paramêtres du milieu (echelle, ...)
	    Transform3D echelle=new Transform3D();
	    Transform3D rotation=new Transform3D();
	  
	    // Echelle
	    echelle.setScale(1);
	    // Rotation
	    rotation.rotY(-0.8f);
	    
	    //Nouvel objet
	    TransformGroup objSpin1 = new TransformGroup(echelle);
	    TransformGroup objSpin2 = new TransformGroup(rotation);
	    
	    //Affichage du camion
	    TransparencyAttributes transparence=new TransparencyAttributes();
	    transparence.setTransparency(0.5f);
	    transparence.setTransparencyMode(TransparencyAttributes.NICEST);
	    
	    PolygonAttributes pol = new PolygonAttributes();
	    pol.setCullFace(PolygonAttributes.CULL_NONE);
	    
	    //Création d'une apparence
	    Appearance apparence=new Appearance();
	    apparence.setPolygonAttributes(pol);
	    apparence.setTransparencyAttributes(transparence);
	    
	    //Création du shade3D
	    Shape3D shape = new Shape3D();
	    shape.setGeometry(quadArray);
	    shape.setAppearance(apparence);
	   
	 /*   CollisionDetector cd=new CollisionDetector(shape);
		BoundingBox bounds1;
	    bounds1 = new BoundingBox(new Point3d(-0.9f, -0.3f, -0.165f),new Point3d(0.9f, 0.3f, 0.165f));
	    cd.setSchedulingBounds(bounds1);*/
	    
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
	    fenetre.add(camion3D);
	    
	    // Création d'un nouveau chargement
	    this.chargement=new Chargement(
	    		camion,
	    		new Integer(0),
	    		new Integer(0),
	    		this.utilisateur,
	    		new Timestamp(System.currentTimeMillis()),
	    		"PB A VOIR APRES");
		setVisible(true);
	}
		
	public void actionPerformed(ActionEvent ev) {
		AccesBDDChargement bddChargement=new AccesBDDChargement();
		Vector aCharger=new Vector();
		Object source = ev.getSource();
		Colis colis=null, colisSuiv=null;
		int ligneActive;
		
		// Annulation de la création d'un chargement
		if(source==this.annuler){
			dispose();
			new Prep_Fenetre_princ(this.utilisateur).setVisible(true);
		}
		
		// Création d'un chargement à l'état en cours
		else if(source==this.valider){
			// On met à jour la date
			this.chargement.setDate(new Timestamp(System.currentTimeMillis()));
			try{
				bddChargement.ajouter(this.chargement);
				for(int i=0;i<listeChargementMod.getRowCount();i++)	aCharger.add(new Colis((Vector)listeChargementMod.getRow(i)));
				bddChargement.AjouterColis(chargement, aCharger);
			}
			catch(SQLException e){
				
			}
			dispose();
			new Prep_Fenetre_princ(this.utilisateur).setVisible(true);
		}
		
		// Ajouter un colis dans le camion
		else if(source==this.ajouter){
			ligneActive = listeColisTab.getSelectedRow();
			if (ligneActive != -1){
				//On ajoute au chargement la ligne selectionnée
				listeChargementMod.addRow(listeColisMod.getRow(ligneActive));
				colis=new Colis((Vector)listeColisMod.getRow(ligneActive));
				if(listeColisTab.getRowCount()>1)
					colisSuiv=new Colis((Vector)listeColisMod.getRow(ligneActive+1));
				else	colisSuiv=null;
				// Ajout du volume
				this.chargement.ajouterVolumeColis(new Float(colis.getVolume().intValue()));
				listeChargementMod.fireTableDataChanged();
				//On supprime de la liste des colis
				listeColisMod.removeRow(ligneActive);
				listeColisMod.fireTableDataChanged();
				//Mise à jour des tableaux
				listeColisTab.updateUI();
				listeChargementTab.updateUI();
				
				// Ajout de l'objet 3D
				scene.addChild(brancheCube(
						colis.getModele().getLargeur().intValue()/(this.echelle*100), 
						colis.getModele().getProfondeur().intValue()/(this.echelle*100), 
						colis.getModele().getHauteur().intValue()/(this.echelle*100)));
				this.zoneColis3D.update(colisSuiv, listeColisMod, listeColisTab);
			}
			else{
				JOptionPane.showMessageDialog(this,"Veuillez sélectionner un colis dans les colis disponibles","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// Supprimer un colis dans le camion
		else if(source==this.retirer){
			ligneActive = listeChargementTab.getSelectedRow();
			if (ligneActive != -1){
				// On ajoute à la liste des colis
				listeColisMod.addRow(listeChargementMod.getRow(ligneActive));
				colis=new Colis((Vector)listeChargementMod.getRow(ligneActive));
				// Soustraction du volume
				this.chargement.soustraireVolumeColis(new Float(colis.getVolume().intValue()));
				listeColisMod.fireTableDataChanged();
				//On supprime du chargement
				listeChargementMod.removeRow(ligneActive);
				listeChargementMod.fireTableDataChanged();
				//Mise à jour des tableaux
				listeColisTab.updateUI();
				listeChargementTab.updateUI();
			}
			else{
				JOptionPane.showMessageDialog(this,"Veuillez sélectionner un colis dans le chargement","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
			}
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
	    this.deplacement=new deplacementColis(ajouter);
	    deplacement.objetADeplacer(objSpin3, translation);

	    // Arrière plan de la scène 3D
	    Background background = new Background(1, 1, 1);
	    background.setColor(new Color3f(new Color(238,238,238)));
	    background.setApplicationBounds(new BoundingBox());
		
	    // Zone d'éclairage de la lumière
	    DirectionalLight lumiereDir=new DirectionalLight();
	    AmbientLight lumiere=new AmbientLight();
	    lumiereDir.setColor(new Color3f(Color.cyan));
	    lumiere.setInfluencingBounds(new BoundingBox(new Point3d(-2,-2,-2),new Point3d(2,2,2)));
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
	    
	    Box b = new Box(profondeur, hauteur, largeur, apparence);
	    CollisionDetector cd=new CollisionDetector(b);
		BoundingBox bounds1;
	    bounds1 = new BoundingBox(new Point3d(-2f, -2f, -2f),new Point3d(-1f, -1f, -1f)/*new Point3d(-0.9f, -0.3f, -0.165f),new Point3d(0.9f, 0.3f, 0.165f)*/);
	    Point3d p1 = new Point3d();
	    bounds1.getLower(p1);
	    Point3d p2 = new Point3d();
	    bounds1.getUpper(p2);
	    cd.setSchedulingBounds(bounds1);
	    
	    //CollisionDetector cd1=new CollisionDetector(b);
		BoundingBox bounds2;
	    bounds2 = new BoundingBox(new Point3d(-0.1f, -0.1f, -0.1f),new Point3d(0.1f, 0.1f, 0.1f));
	   // cd.setSchedulingBounds(bounds2);
	    
	    //Construction du cube
	    objSpin1.addChild(objSpin2);
	    objSpin2.addChild(objSpin3);
	    objSpin3.addChild(b);
	    objSpin3.addChild(cd);
	   // objSpin3.addChild(cd1);
	    
	    
	    
	    branche.addChild(objSpin1);
	    
	    // Ajout de la capacité à séparer la branche
	    branche.setCapability(BranchGroup.ALLOW_DETACH);
	        
	    branche.compile();
	    
		return branche;
	}
	
	// Calcul de l'échelle pour le camion et donne les point pour le dessin de la benne
	private Point3f[] tailleBenne(float profondeur, float hauteur, float largeur){
		Point3f benne[]=new Point3f[20];
		this.echelle=1.1f;
		float tmp_profondeur=profondeur, tmp_hauteur=hauteur, tmp_largeur=largeur;
		
		while(tmp_largeur>2.1f || tmp_hauteur>0.61f || tmp_profondeur>0.51f){
			tmp_profondeur/=1.1f;
			tmp_hauteur/=1.1f;
			tmp_largeur/=1.1f;
			this.echelle*=1.1f;
		}
		tmp_largeur/=2;
		tmp_hauteur/=2;
		tmp_profondeur/=2;
		
		// Face 1
		benne[0]=new Point3f(-tmp_largeur, -tmp_hauteur, -tmp_profondeur);
		benne[1]=new Point3f(-tmp_largeur, -tmp_hauteur, tmp_profondeur);
		benne[2]=new Point3f(tmp_largeur, -tmp_hauteur, tmp_profondeur);
		benne[3]=new Point3f(tmp_largeur, -tmp_hauteur, -tmp_profondeur);
		// Face 2
		benne[4]=new Point3f(tmp_largeur, tmp_hauteur, tmp_profondeur);
		benne[5]=new Point3f(tmp_largeur, tmp_hauteur, -tmp_profondeur);
		benne[6]=new Point3f(tmp_largeur, -tmp_hauteur, -tmp_profondeur);
		benne[7]=new Point3f(tmp_largeur, -tmp_hauteur, tmp_profondeur);
		// Face 3
		benne[8]=new Point3f(-tmp_largeur, tmp_hauteur, -tmp_profondeur);
		benne[9]=new Point3f(tmp_largeur, tmp_hauteur, -tmp_profondeur);
		benne[10]=new Point3f(tmp_largeur, -tmp_hauteur, -tmp_profondeur);
		benne[11]=new Point3f(-tmp_largeur, -tmp_hauteur, -tmp_profondeur);
		// Face 4
		benne[12]=new Point3f(-tmp_largeur, tmp_hauteur, tmp_profondeur);
		benne[13]=new Point3f(-tmp_largeur, tmp_hauteur, -tmp_profondeur);
		benne[14]=new Point3f(-tmp_largeur, -tmp_hauteur, -tmp_profondeur);
		benne[15]=new Point3f(-tmp_largeur, -tmp_hauteur, tmp_profondeur);
	    //Face 5
		benne[16] = new Point3f(-tmp_largeur, tmp_hauteur, -tmp_profondeur);
		benne[17] = new Point3f(-tmp_largeur, tmp_hauteur, tmp_profondeur);
		benne[18] = new Point3f(tmp_largeur, tmp_hauteur, tmp_profondeur);
		benne[19] = new Point3f( tmp_largeur, tmp_hauteur, -tmp_profondeur);
		
		return benne;
	}
}