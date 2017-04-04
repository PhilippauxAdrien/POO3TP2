package model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Kub
 */
public class TableModelResultSet extends AbstractTableModel 
{

    ResultSet RS;
    int nbcolonnes=Integer.MAX_VALUE;
    
    public TableModelResultSet(ResultSet rs) {
      RS=rs;  
        
    }
    public TableModelResultSet(ResultSet rs,int N) {
      RS=rs;  
      this.nbcolonnes=N;
        
    }

    
    
    @Override
    public int getRowCount() {
        try {
            RS.last();
            return RS.getRow();
        } catch (SQLException ex) {
            System.out.println("error 4 ");
        }
        return -1;
    }

    /*
    @Override
    public int getColumnCount()  {
        
        try {
               return RS.getMetaData().getColumnCount();
        } 
        catch (SQLException ex) {
            System.out.println("error 5 ");
        }
        return -1;
     
    }
    * */
    
    public int getColumnCount() {

           try {
                   return Math.min(this.nbcolonnes, RS.getMetaData().getColumnCount());
        } 
        catch (SQLException ex) {
            System.out.println("error 5 ");
        }
        return -1;
     }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)  {
        
        
        try {
               RS.absolute(rowIndex+1);
        return RS.getObject(columnIndex + 1);
        } 
        catch (SQLException ex) {
            System.out.println("error 6 ");
        }
        return null;
     
        
    }
    
    public Class<?> getColumnClass(int columnIndex) {
        try {
     return Class.forName(RS.getMetaData().getColumnClassName(columnIndex+1));
        } catch (Exception  ex) {
            System.out.println("error 7 ");
        }
        
        return null;
}
    
    
    public String getColumnName(int column) {
            
        try {
            return RS.getMetaData().getColumnLabel(column+1);
        } catch (Exception  ex) {
            System.out.println("error 8 ");
        }
        return "";
        
}
    
    
    
}