package ihm.entree;

import ihm.ModeleTable;
import ihm.TableSorter;
import ihm.supervision.Sup_AjoutModifUtilisateur;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;
import accesBDD.*;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import donnees.Personne;
import donnees.Utilisateur;

import accesBDD.AccesBDDUtilisateur;

public class Entree_select_personne extends JFrame implements ActionListener{

	private Vector nomColonnes = new Vector();
	private Vector donnees = new Vector();
	private AccesBDDPersonne tablePersonnes = new AccesBDDPersonne(); 
	private JTable table;
	private ModeleTable modeleTab;
	private TableSorter sorter;
	private Container contenu ;
	private int ligneActive;
	private JButton ajout_exp,ajout_dest,fermer;
	private Entree_Fenetre_colis fenetre1;
	
	public Entree_select_personne(Entree_Fenetre_colis fenetre)
	{
		fenetre1=fenetre;
		contenu = getContentPane();
		contenu.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		setTitle("S�lection d'un exp�diteur et/ou destinataire");
		setBounds(100,100,800,500);
		
		nomColonnes.add("ID");
        nomColonnes.add("Nom");
        nomColonnes.add("Pr�nom");
        nomColonnes.add("ID");
        nomColonnes.add("Adresse");
        nomColonnes.add("Code Postal");
        nomColonnes.add("Ville");
        nomColonnes.add("Email");
        nomColonnes.add("T�l�phone");
        //nomColonnes.add("ID");
        try{
        	//On r�cup�re les utilisateurs de la base de donn�es et on les affiche
            Vector listePersonnes = tablePersonnes.lister();
            
            for(int i=0;i<listePersonnes.size();i++){
            	donnees.addElement(((Personne)listePersonnes.get(i)).toVector());
            }
        }
        catch(SQLException e){
        	System.out.println(e.getMessage());
        }
        //table.removeColumn(table.getColumnModel().getColumn(2));
        
        modeleTab = new ModeleTable(nomColonnes,donnees);
		
		// Cr�ation du TableSorter qui permet de r�ordonner les lignes � volont�
		sorter = new TableSorter(modeleTab);
		
		// Cr�ation du tableau
		table = new JTable(sorter);
		
		// initialisation du Sorter
		sorter.setTableHeader(table.getTableHeader());
		
		// On cr�e les colonnes du tableau selon le mod�le
		table.setAutoCreateColumnsFromModel(true);
		table.setOpaque(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.removeColumn(table.getColumnModel().getColumn(0));
		table.removeColumn(table.getColumnModel().getColumn(2));
		// On place le tableau dans un ScrollPane pour qu'il soit d�filable
		JScrollPane scrollPane = new JScrollPane(table);

		// On d�finit les dimensions du tableau
		table.setPreferredScrollableViewportSize(new Dimension(760,400));

		// On place le tableau
		scrollPane.setBounds(20,10,760,400);

		// On d�finit le tableau transparent
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		// On ajoute le tableau au Panneau principal
		contenu.add(scrollPane);
        
		ajout_exp = new JButton("Ajouter l'exp�diteur");
		ajout_exp.setBounds(300,420,180,25);
		contenu.add(ajout_exp);
		ajout_exp.addActionListener(this);
		
		ajout_dest = new JButton("Ajouter le destinataire");
		ajout_dest.setBounds(50,420,180,25);
		contenu.add(ajout_dest);
		ajout_dest.addActionListener(this);
		
		fermer= new JButton("Fermer");
		fermer.setBounds(550,420,180,25);
		contenu.add(fermer);
		fermer.addActionListener(this);
   
		
	}
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		int ligneSelect = table.getSelectedRow();
		
		if(ligneSelect != -1){

			
				//On cherche la ligne r�ellement s�lectionn�e (au cas o� un tri ait �t� lanc�)
				ligneActive = sorter.modelIndex(ligneSelect);
				
				// Action li�e au bouton de modification d'un utilisateur
				if(source==ajout_exp){
				
					// On r�cup�re les donn�es de la ligne du tableau
					Vector cVect = (Vector) modeleTab.getRow(ligneActive);
					Personne p = new Personne(cVect);
					
					fenetre1.modifier_info_personne(p,1);
					
				}
				// Suppression d'un utilisateur
				else if(source==ajout_dest){
					
					Vector cVect = (Vector) modeleTab.getRow(ligneActive);
					Personne p = new Personne(cVect);
					fenetre1.modifier_info_personne(p,2);
					
				}

		}
		if(source==fermer){
			dispose();
		}
		
		
	
	}
	
}
