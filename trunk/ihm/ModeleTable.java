package ihm;

import javax.swing.table.*;
import java.util.Vector;

// Mod�le de tableau permettant d'afficher et de g�rer les listes de donn�es
public class ModeleTable extends AbstractTableModel{
	protected Vector nomColonnes;
	protected Vector rowData;

	public ModeleTable(Vector paramNomColonnes, Vector paramRowData){
		//nomColonnes=new Vector();
		nomColonnes=paramNomColonnes;
		//rowData=new Vector();
		rowData=paramRowData;
	}

	// Obtenir le nombre de colonnes du tableau
    public int getColumnCount() {
        return nomColonnes.size();
    }
    
	// Obtenir le nombre de lignes du tableau
    public int getRowCount() {
    	return rowData.size();
    }

    // Obtenir le nom d'une colonne donn�e
    public String getColumnName(int col) {
    	String nomCol="";
    	if(col<=getColumnCount()){
    		nomCol=(String)nomColonnes.elementAt(col);
    	}
        return nomCol;
    }

    // Obtenir le contenu d'une cellule
    public Object getValueAt(int row, int col) {
        return ((Vector)rowData.elementAt(row)).get(col);
    }

    // Obtenir le contenu d'une ligne
    public Object getRow(int row){
    	return ((Vector)rowData.elementAt(row));
    }

    // Editer le contenu d'une ligne
    public void setRow(Object value, int row){
    	rowData.setElementAt(value,row);    	
    }

    /*
    public void setValueAt(Object value, int row, int col) {
    	Vector v=(Vector)rowData.elementAt(row);
    	v.setElementAt(value,col);
    }*/

    // Ajouter une ligne au tableau
    public void addRow(Object row){
    	rowData.insertElementAt(row,getRowCount());
    }
    
    // Supprimer une ligne du tableau
    public void removeRow(int ligne){    	
    	rowData.remove(ligne);
    }
}
