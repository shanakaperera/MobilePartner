/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MobilePartnerController;
import db_pojo.MessageDefine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.table.AbstractTableModel;
import jpa_controllers.MessageDefineJpaController;

/**
 *
 * @author Shanaka P
 */
public class PredefinesTableModel extends AbstractTableModel {

    List<MessageDefine> modelData = new ArrayList<>();
    String colNames[] = {"Id", "Type", "Notifiying Color"};
    Class<?> colClasses[] = {Integer.class, String.class, Integer.class};
    private static PredefinesTableModel singletonForm = new PredefinesTableModel();

    private PredefinesTableModel() {
        modelData.addAll(getAllPredefines());
    }

    public void addAllData() {
        modelData.clear();
        modelData.addAll(getAllPredefines());
    }

    @Override
    public int getRowCount() {
        return modelData.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return colClasses[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return modelData.get(rowIndex).getId();
        }
        if (columnIndex == 1) {
            return modelData.get(rowIndex).getTypeName();
        }
        if (columnIndex == 2) {
            return Integer.parseInt(modelData.get(rowIndex).getDescription());
        }

        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            modelData.get(rowIndex).setId((Integer) aValue);
        }
        if (columnIndex == 1) {
            modelData.get(rowIndex).setTypeName((String) aValue);
        }
        if (columnIndex == 2) {
            modelData.get(rowIndex).setDescription((String) aValue);
        }

        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public static PredefinesTableModel getInstance() {
        return singletonForm;
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();
    }

    private Collection<? extends MessageDefine> getAllPredefines() {
        try {
            EntityManagerFactory emf = MobilePartnerController.getInstance().getEntityManagerFactory();
            MessageDefineJpaController defineController = new MessageDefineJpaController(emf);

            return defineController.findMessageDefineEntities();
        } catch (Exception ex) {
            Logger.getLogger(PredefinesTableModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
