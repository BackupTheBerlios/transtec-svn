package ihm.preparation;
// Etape 1 :
// Importation des packages Java 2
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

// Etape 2 :
// Importation des packages Java 3D
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class SaveImage3D extends Applet {

  private JPanel bottom = new JPanel();
  private JButton save = new JButton("Sauvegarder");
  private Canvas3D canvas3D = null;
  private OffScreenCanvas3D offScreenCanvas = null;

  public SaveImage3D() {

    this.setLayout(new BorderLayout());
    bottom.setLayout(new FlowLayout(FlowLayout.CENTER));

    save.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveImageCB(e);
      }
    });

    bottom.add(save);
    this.add(bottom, BorderLayout.SOUTH);

    // Etape 3 :
    // Creation du Canvas 3D
    canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    this.add(canvas3D, BorderLayout.CENTER);

    // Etape 4 :
    // Cree un Canvas3D off-screen (on ne peut pas recuperer directement une
    // image on-screen)
    offScreenCanvas = new OffScreenCanvas3D(canvas3D);

    // Etape 5 :
    // Creation d'un objet SimpleUniverse
    SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

    // Etape 6 :
    // Positionnement du point d'observation pour avoir une vue correcte de la
    // scene 3D
    simpleU.getViewingPlatform().setNominalViewingTransform();

    // Etape 7 :
    // On ajoute le canvas offscreen a l'objet View du SimpleUniverse
    simpleU.getViewer().getView().addCanvas3D(offScreenCanvas);

    // Etape 8 :
    // Creation de la scene 3D qui contient tous les objets 3D que l'on veut
    // visualiser
    BranchGroup scene = createSceneGraph();

    // Etape 9 :
    // Compilation de la scene 3D
    scene.compile();

    // Etape 10 :
    // Attachement de la scene 3D a l'objet SimpleUniverse
    simpleU.addBranchGraph(scene);
  }

  /**
   * Creation de la scene 3D qui contient tous les objets 3D
   * @return scene 3D
   */
  public BranchGroup createSceneGraph() {
    // Creation de l'objet parent qui contiendra tous les autres objets 3D
    BranchGroup parent = new BranchGroup();

    // Ajout du cone a la scene 3D
    parent.addChild(new ConeWithTriangles());

    return parent;
  }

  /**
   * Objet geometrique qui represente un cone constitue de triangles
   * (facettes) et construit grace a l'objet TriangleFanArray.
   * Ce cone est ferme par une base en forme de disque plein.
   */
  class ConeWithTriangles extends Shape3D {

    /**
     * Constructeur
     */
    public ConeWithTriangles() {
      // conversion degres -> radians
      final float DEG_RAD = (float)Math.PI/180;

      int nbPtsCorps = 9;   // nombre de points du corps du cone
      int nbPtsBase = 9;    // nombre de points de la base du cone
      int nbEventails = 2;  // nombre d'eventails (corps + base)

      // Tableau definissant le nombre de points pour chaque eventail
      int[] stripVertexCount = new int[nbEventails];
      stripVertexCount[0] = nbPtsCorps;
      stripVertexCount[1] = nbPtsBase;

      // Allocation des tableaux permettant de stocker en memoire les
      // coordonnees de chaque point constituant notre cone 3D ainsi que
      // des couleurs associees a ces points
      Point3f[] points = new Point3f[nbPtsCorps+nbPtsBase];
      Color3f[] colors = new Color3f[nbPtsCorps+nbPtsBase];

      // Determination des coordonnees des points constituant notre cone 3D
      // ainsi que de la couleur associee a chacun de ces points
      // Corps :
      points[0] = new Point3f(0f, 0.6f, 0f);  // sommet du cone
      colors[0] = new Color3f(Color.blue);
      for (int iPt = 1 ; iPt < nbPtsCorps ; iPt++) {
        float angle = ((iPt-1)*360f/(nbPtsCorps-2)) * DEG_RAD;

        // ATTENTION : on met un signe - devant l'argument "angle" des
        // fonctions sin et cos car il faut que les sommets du corps du
        // cone soient enumeres dans le sens direct trigonometrique
        // (= sens inverse des aiguilles d'une montre) pour que les
        // facettes soient visibles.
        points[iPt] =
            new Point3f(0.4f*(float)Math.cos(-angle),
                        -0.6f,
                        0.4f*(float)Math.sin(-angle));

        switch(iPt) {
          case 1:
            colors[iPt] = new Color3f(Color.cyan);
            break;

          case 2:
            colors[iPt] = new Color3f(Color.green);
            break;

          case 3:
            colors[iPt] = new Color3f(Color.magenta);
            break;

          case 4:
            colors[iPt] = new Color3f(Color.orange);
            break;

          case 5:
            colors[iPt] = new Color3f(Color.pink);
            break;

          case 6:
            colors[iPt] = new Color3f(Color.red);
            break;

          case 7:
            colors[iPt] = new Color3f(Color.white);
            break;

          case 8:
            colors[iPt] = new Color3f(Color.yellow);
            break;
        }  // fin switch(iPt)
      }  // fin for (iPt = 1 ; iPt < nbPtsCorps ; iPt++)

      // Base :
      // On choisit une couleur blanche unique pour tous les points de la base
      points[nbPtsCorps] = new Point3f(0f, -0.6f, 0f);  // centre de la base
      colors[nbPtsCorps] = new Color3f(Color.white);
      for (int iPt = 1 ; iPt < nbPtsBase ; iPt++) {
        float angle = ((iPt-1)*360f/(nbPtsBase-2)) * DEG_RAD;

        points[nbPtsCorps+iPt] =
            new Point3f(0.4f*(float)Math.cos(angle),
                        -0.6f,
                        0.4f*(float)Math.sin(angle));

        colors[nbPtsCorps+iPt] = new Color3f(Color.white);
      }  // fin for (int iPt = 1 ; iPt < nbPtsBase ; iPt++)

      // Construction de l'objet geometrique TriangleFanArray constitue de
      // 2 eventails (corps + base)
      TriangleFanArray triangleFanArray =
          new TriangleFanArray(nbPtsCorps+nbPtsBase,
                               TriangleFanArray.COORDINATES |
                               TriangleFanArray.COLOR_3,
                               stripVertexCount);

      // On rentre le tableau des points dans l'objet TriangleFanArray
      triangleFanArray.setCoordinates(0, points);
      triangleFanArray.setColors(0, colors);

      // Mise a jour de la geometrie de l'objet ConeWithTriangles
      this.setGeometry(triangleFanArray);
    }  // fin constructeur
  }  // fin class ConeWithTriangles extends Shape3D

  /**
   * Callback appele lorsqu'on veut sauvegarder une image
   */
  protected void saveImageCB(ActionEvent e) {
    // Creation du fichier dans lequel on va sauvegarder notre image 3D
    File imageFile = new File("image.png");

    // Dimension (en pixels) de l'image a sauvegarder dans le fichier
    Dimension dim = new Dimension(512, 512);

    // On recupere l'image (pixmap) rendue par le canvas 3D offscreen
    BufferedImage image = offScreenCanvas.getOffScreenImage(dim);

    // On recupere le contexte graphique de l'image finale de sortie
    Graphics2D gc = image.createGraphics();
    gc.drawImage(image, 0, 0, null);

    // Sauvegarde de l'image dans un fichier au format PNG
    try {
      ImageIO.write(image, "png", imageFile);
    }
    catch (IOException ex) {
      System.out.println("Impossible de sauvegarder l'image");
    }
  }

  /**
   * Etape 11 :
   * Methode main() nous permettant d'utiliser cette classe comme une applet
   * ou une application.
   * @param args parametres de la ligne de commande
   */
  public static void main(String[] args) {
    Frame frame = new MainFrame(new SaveImage3D(), 256, 256);
  }
}
