/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MobilePartnerController;
import db_pojo.InboundMessage;
import db_pojo.MessageDefine;
import db_pojo.SavedContacts;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.table.AbstractTableModel;
import jpa_controllers.InboundMessageJpaController;
import jpa_controllers.MessageDefineJpaController;
import jpa_controllers.SavedContactsJpaController;

/**
 *
 * @author Shanaka
 */
public class MessagesTableModel extends AbstractTableModel {

    List<Message> modelData = new ArrayList<>();
    String colNames[] = {"Id", "Message", "Number", "Type", "Date Received"};
    Class<?> colClasses[] = {Integer.class, String.class, String.class, String.class, String.class};
    private static MessagesTableModel singletonForm = new MessagesTableModel();

    private MessagesTableModel() {
        modelData.addAll(getAllMessages());
    }

    public void addAllData() {
        modelData.clear();
        modelData.addAll(getAllMessages());
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
            return modelData.get(rowIndex).getMessage();
        }
        if (columnIndex == 2) {
            return modelData.get(rowIndex).getNumber();
        }
        if (columnIndex == 3) {
            return modelData.get(rowIndex).getType();
        }
        if (columnIndex == 4) {
            return modelData.get(rowIndex).getDateReceived();
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
            modelData.get(rowIndex).setMessage((String) aValue);
        }
        if (columnIndex == 2) {
            modelData.get(rowIndex).setNumber((String) aValue);
        }
        if (columnIndex == 3) {
            modelData.get(rowIndex).setType((String) aValue);
        }
        if (columnIndex == 4) {
            modelData.get(rowIndex).setDateReceived((String) aValue);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();
    }

    private ArrayList<Message> getAllMessages() {

        try {
            EntityManagerFactory emf = MobilePartnerController.getInstance().getEntityManagerFactory();
            InboundMessageJpaController imMsgController = new InboundMessageJpaController(emf);
            MessageDefineJpaController msgDefineController = new MessageDefineJpaController(emf);
            SavedContactsJpaController svdCntctController = new SavedContactsJpaController(emf);

            SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

            List<InboundMessage> inMsgs = imMsgController.findInboundMessageEntities();
            ArrayList<Message> tableMsgs = new ArrayList<>();
            for (InboundMessage inMsg : inMsgs) {
                MessageDefine findMessageDefine = msgDefineController.findMessageDefine(inMsg.getMessage(), "typeName");
                SavedContacts findSavedContact = svdCntctController.findSavedContact(inMsg.getOriginator(), "number");
                if (findMessageDefine != null) {
                    if (findSavedContact != null) {
                        tableMsgs.add(new Message(inMsg.getId(), inMsg.getMessage(), findSavedContact.getContactName(),
                                findMessageDefine.getTypeName(), dformat.format(inMsg.getDateRecived())));
                    } else {
                        tableMsgs.add(new Message(inMsg.getId(), inMsg.getMessage(), inMsg.getOriginator(),
                                findMessageDefine.getTypeName(), dformat.format(inMsg.getDateRecived())));
                    }
                }
            }

            return tableMsgs;
        } catch (Exception ex) {
            Logger.getLogger(MessagesTableModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static MessagesTableModel getInstance() {
        return singletonForm;
    }

}
