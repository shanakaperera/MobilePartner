/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MobilePartnerController;
import db_pojo.SavedContacts;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.table.AbstractTableModel;
import jpa_controllers.SavedContactsJpaController;

/**
 *
 * @author Shanaka
 */
public class ContactsTableModel extends AbstractTableModel {

    List<SavedContacts> modelData = new ArrayList<>();
    String colNames[] = {"Id", "Contact Name", "Number", "Description"};
    Class<?> colClasses[] = {Integer.class, String.class, String.class, String.class};
    private static ContactsTableModel singletonForm = new ContactsTableModel();

    private ContactsTableModel() {
        modelData.addAll(getAllContacts());
    }

    public void addAllData() {
        modelData.clear();
        modelData.addAll(getAllContacts());
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return modelData.get(rowIndex).getId();
        }
        if (columnIndex == 1) {
            return modelData.get(rowIndex).getContactName();
        }
        if (columnIndex == 2) {
            return modelData.get(rowIndex).getNumber();
        }
        if (columnIndex == 3) {
            return modelData.get(rowIndex).getDescription();
        }
        return null;
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
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            modelData.get(rowIndex).setId((Integer) aValue);
        }
        if (columnIndex == 1) {
            modelData.get(rowIndex).setContactName((String) aValue);
        }
        if (columnIndex == 2) {
            modelData.get(rowIndex).setNumber((String) aValue);
        }
        if (columnIndex == 3) {
            modelData.get(rowIndex).setDescription((String) aValue);
        }

        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();
    }

    public static ContactsTableModel getInstance() {
        return singletonForm;
    }

    private Collection<? extends SavedContacts> getAllContacts() {
        try {
            EntityManagerFactory emf = MobilePartnerController.getInstance().getEntityManagerFactory();
            SavedContactsJpaController svdCntctController = new SavedContactsJpaController(emf);

            return svdCntctController.findSavedContactsEntities();
        } catch (Exception ex) {
            Logger.getLogger(ContactsTableModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
