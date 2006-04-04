package ihm.entree;

import ihm.Bouton;
import ihm.FenetreType;
import ihm.ModeleTable;
import ihm.TableSorter;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Vector;
import accesBDD.*;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import donnees.Personne;

public class Entree_select_personne extends JFrame implements ActionListener, ItemListener{

	private Vector nomColonnes = new Vector();
	private Vector donnees = new Vector();
	private AccesBDDPersonne tablePersonnes;
	private JTable table;
	private ModeleTable modeleTab;
	private TableSorter sorter;
	private FenetreType contenu;
	private int ligneActive;
	private JButton ajout_exp,ajout_dest,fermer;
	private Entree_Fenetre_colis fenetre1;
	private String[] recherche={"Tous","Nom","Prénom","Adresse","Code Postal","Ville","Email","Telephone"};
	private JComboBox style_recherche;
	private JTextField donnees_recherche;
	private JButton rechercher;
	private Font font;
	private AccesBDD accesBDD;
	
	public Entree_select_personne(Entree_Fenetre_colis fenetre, AccesBDD accesBDD)
	{
		fenetre1=fenetre;
		this.accesBDD=accesBDD;
		this.tablePersonnes = new AccesBDDPersonne(this.accesBDD); 
		
		setTitle("Sélection d'un expéditeur et/ou destinataire");
		//setBounds(100,100,800,500);
		
		//setUndecorated(true); //on enleve la barre en haut
		//setTitle(u.getPersonne().getNom() +" "+ u.getPersonne().getPrenom()  + " - Entrée");
		//setSize(700,527); // on définit la taille de la fenetre
		setBounds(150,120,710,560);
		contenu=new FenetreType("images/entree/bg_packentrée2.png");
		
		setContentPane(contenu);
		contenu.setLayout(new FlowLayout());
		getContentPane().setLayout(null);
		
		font=new Font("Verdana", Font.BOLD, 12);
		
		style_recherche = new JComboBox(recherche);
		style_recherche.setBounds(20,45,100,20);
		contenu.add(style_recherche);
		style_recherche.addItemListener(this);
		
		donnees_recherche = new JTextField(15);
		donnees_recherche.setBounds(130,45,120,20);
		contenu.add(donnees_recherche);
		
		rechercher = new Bouton("images/entree/bouton_rechercher.png","images/entree/bouton_rechercher_appuyer.png");
		//rechercher = new JButton("Rechercher");
		contenu.add(rechercher);
		rechercher.setBounds(260,40,150,35);
		rechercher.addActionListener(this);
		
		
		nomColonnes.add("ID");
        nomColonnes.add("Nom");
        nomColonnes.add("Prénom");
        nomColonnes.add("ID");
        nomColonnes.add("Adresse");
        nomColonnes.add("Code Postal");
        nomColonnes.add("Ville");
        nomColonnes.add("Email");
        nomColonnes.add("Téléphone");
        //nomColonnes.add("ID");
        try{
        	//On récupère les utilisateurs de la base de données et on les affiche
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
		
		// Création du TableSorter qui permet de réordonner les lignes à volonté
		sorter = new TableSorter(modeleTab);
		
		// Création du tableau
		table = new JTable(sorter);
		
		// initialisation du Sorter
		sorter.setTableHeader(table.getTableHeader());
		
		// On crée les colonnes du tableau selon le modèle
		table.setAutoCreateColumnsFromModel(true);
		table.setOpaque(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.removeColumn(table.getColumnModel().getColumn(0));
		table.removeColumn(table.getColumnModel().getColumn(2));
		// On place le tableau dans un ScrollPane pour qu'il soit défilable
		JScrollPane scrollPane = new JScrollPane(table);

		// On définit les dimensions du tableau
		table.setPreferredScrollableViewportSize(new Dimension(760,400));

		// On place le tableau
		scrollPane.setBounds(20,80,660,320);

		// On définit le tableau transparent
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		// On ajoute le tableau au Panneau principal
		contenu.add(scrollPane);
        
		ajout_exp = new Bouton("images/entree/bouton_ajout_expéditeur.png","images/entree/bouton_ajout_expéditeur_appuyer.png");
		ajout_exp.setBounds(270,470,180,40);
		contenu.add(ajout_exp);
		ajout_exp.addActionListener(this);
		
		ajout_dest = new Bouton("images/entree/bouton_ajout_destinataire.png","images/entree/bouton_ajout_destinataire_appuyer.png");
		ajout_dest.setBounds(20,470,180,40);
		contenu.add(ajout_dest);
		ajout_dest.addActionListener(this);
		
		fermer= new Bouton("images/entree/bouton_fermer.png","images/entree/bouton_fermer_appuyer.png");
		fermer.setBounds(520,470,180,40);
		contenu.add(fermer);
		fermer.addActionListener(this);
   
		donnees_recherche.setEnabled(false);
		
		contenu.validate();
		repaint();
		
	}
	
	public void ajouterPers(Personne p){
		
		// Ajout de la ligne
		modeleTab.addRow(p.toVector());
		modeleTab.fireTableDataChanged();
		// On redessine le tableau
		table.updateUI();
	}
	public void supprimerLigne(int ligne){
		// Suppression de la ligne
		modeleTab.removeRow(ligne);
		
		// On indique au modèle que les données ont changé
		modeleTab.fireTableDataChanged();
		
		// On redessine le tableau
		//setFenetreActive(true);
		table.updateUI();
	}
	
	
	public void itemStateChanged (ItemEvent e)
	{
		int num = style_recherche.getSelectedIndex();
		if (num ==0)
		{
			donnees_recherche.setEnabled(false);
			donnees_recherche.setText("");			
		}else{
			donnees_recherche.setEnabled(true);
			donnees_recherche.setText("");	
		}
	}
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		int ligneSelect = table.getSelectedRow();
		
		if(ligneSelect != -1){

			
				//On cherche la ligne réellement sélectionnée (au cas où un tri ait été lancé)
				ligneActive = sorter.modelIndex(ligneSelect);
				
				// Action liée au bouton de modification d'un utilisateur
				if(source==ajout_exp){
				
					// On récupère les données de la ligne du tableau
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
		
		if (source == rechercher)
		{
			//AccesBDDPersonne recherche = new AccesBDDPersonne();
			//table.removeAll();
			//System.out.println(sorter.getRowCount());
			int temp1 = sorter.getRowCount();
			/*for(int i=0 ;i <sorter.getRowCount();i++)
			{
				supprimerLigne(i);
				
			}*/
			for(int i=0 ;i <temp1;i++)
			{
				supprimerLigne(0);
				
			}
			
			
			
			switch(style_recherche.getSelectedIndex()){
			case 0:
				try{
		        	//On récupère les utilisateurs de la base de données et on les affiche
		            Vector listePersonnes = tablePersonnes.lister();
		            
		            for(int i=0;i<listePersonnes.size();i++){
		            	donnees.addElement(((Personne)listePersonnes.get(i)).toVector());
		            }
		        }
		        catch(SQLException e1){
		        	System.out.println(e1.getMessage());
		        }
				
				break;
			case 1:
				try{
		        	//On récupère les utilisateurs de la base de données et on les affiche
		            Vector listePersonnes = tablePersonnes.rechercher(AccesBDDPersonne.NOM,donnees_recherche.getText());
		            
		            for(int i=0;i<listePersonnes.size();i++){
		            	//donnees.addElement(((Personne)listePersonnes.get(i)).toVector());
		            	ajouterPers((Personne)listePersonnes.get(i));
		        		
		            }
		        }
		        catch(SQLException e2){
		        	System.out.println(e2.getMessage());
		        }
		
				break;
			case 2:
				try{
		        	//On récupère les utilisateurs de la base de données et on les affiche
		            Vector listePersonnes = tablePersonnes.rechercher(AccesBDDPersonne.PRENOM,donnees_recherche.getText());
		            
		            for(int i=0;i<listePersonnes.size();i++){
		            	//donnees.addElement(((Personne)listePersonnes.get(i)).toVector());
		            	ajouterPers((Personne)listePersonnes.get(i));
		        		
		            }
		        }
		        catch(SQLException e2){
		        	System.out.println(e2.getMessage());
		        }
				
				break;
			case 3:
				try{
		        	//On récupère les utilisateurs de la base de données et on les affiche
		            Vector listePersonnes = tablePersonnes.rechercher(AccesBDDPersonne.ADRESSE,donnees_recherche.getText());
		            
		            for(int i=0;i<listePersonnes.size();i++){
		            	//donnees.addElement(((Personne)listePersonnes.get(i)).toVector());
		            	ajouterPers((Personne)listePersonnes.get(i));
		        		
		            }
		        }
		        catch(SQLException e2){
		        	System.out.println(e2.getMessage());
		        }
				
				break;
			case 4:
				boolean noerreur = verifChamps();
				if (noerreur ==true)
				{
				try{
		        	//On récupère les utilisateurs de la base de données et on les affiche
		            Vector listePersonnes = tablePersonnes.rechercher(AccesBDDPersonne.CODEPOSTAL,donnees_recherche.getText());
		            
		            for(int i=0;i<listePersonnes.size();i++){
		            	//donnees.addElement(((Personne)listePersonnes.get(i)).toVector());
		            	ajouterPers((Personne)listePersonnes.get(i));
		        		
		            }
		        }
		        catch(SQLException e2){
		        	System.out.println(e2.getMessage());
		        }
				}
				
				break;
			case 5:
				try{
		        	//On récupère les utilisateurs de la base de données et on les affiche
		            Vector listePersonnes = tablePersonnes.rechercher(AccesBDDPersonne.VILLE,donnees_recherche.getText());
		            
		            for(int i=0;i<listePersonnes.size();i++){
		            	//donnees.addElement(((Personne)listePersonnes.get(i)).toVector());
		            	ajouterPers((Personne)listePersonnes.get(i));
		        		
		            }
		        }
		        catch(SQLException e2){
		        	System.out.println(e2.getMessage());
		        }
				
				break;
			case 6:
				try{
		        	//On récupère les utilisateurs de la base de données et on les affiche
		            Vector listePersonnes = tablePersonnes.rechercher(AccesBDDPersonne.EMAIL,donnees_recherche.getText());
		            
		            for(int i=0;i<listePersonnes.size();i++){
		            	//donnees.addElement(((Personne)listePersonnes.get(i)).toVector());
		            	ajouterPers((Personne)listePersonnes.get(i));
		        		
		            }
		        }
		        catch(SQLException e2){
		        	System.out.println(e2.getMessage());
		        }
				
				break;
			case 7:
				try{
		        	//On récupère les utilisateurs de la base de données et on les affiche
		            Vector listePersonnes = tablePersonnes.rechercher(AccesBDDPersonne.TELEPHONE,donnees_recherche.getText());
		            
		            for(int i=0;i<listePersonnes.size();i++){
		            	//donnees.addElement(((Personne)listePersonnes.get(i)).toVector());
		            	ajouterPers((Personne)listePersonnes.get(i));
		        		
		            }
		        }
		        catch(SQLException e2){
		        	System.out.println(e2.getMessage());
		        }
				
				break;
			}
		}

		if(source==fermer){
			dispose();
		}
		
		
	
	}
	private boolean verifChamps(){
		boolean ret = false;		
		boolean erreurCP = false;
		
		try{
			new Integer(donnees_recherche.getText().trim());
		}
		catch(NumberFormatException e){
			erreurCP = true;
		}
		
		if (erreurCP ==true){
			JOptionPane.showMessageDialog(this,"Le champs Code postal est mal renseigné.","Message d'avertissement",JOptionPane.ERROR_MESSAGE);
			
		}
		else ret = true;
		return ret;
	}
	
}
