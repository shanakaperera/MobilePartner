/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MobilePartnerController;
import db_pojo.MessageDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import jpa_controllers.MessageDefineJpaController;

/**
 *
 * @author Shanaka P
 */
public class CustomComboModel extends AbstractListModel implements ComboBoxModel {

    List<String> messageTypes = new ArrayList<>();
    String selection = null;
    private static CustomComboModel singletonForm = new CustomComboModel();

    private CustomComboModel() {
        messageTypes.add("All");
        messageTypes.addAll(getMessageTypes());
    }

    @Override
    public int getSize() {
        return messageTypes.size();
    }

    @Override
    public Object getElementAt(int index) {
        return messageTypes.get(index);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selection = (String) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }

    public static CustomComboModel getInstance() {
        return singletonForm;
    }

    private List<String> getMessageTypes() {
        List<String> messageTyps = new ArrayList<>();
        try {
            EntityManagerFactory emf = MobilePartnerController.getInstance().getEntityManagerFactory();
            MessageDefineJpaController msgDefineController = new MessageDefineJpaController(emf);
            List<MessageDefine> msgEnt = msgDefineController.findMessageDefineEntities();
            for (MessageDefine messageDefine : msgEnt) {
                messageTyps.add(messageDefine.getTypeName());
            }
            return messageTyps;
        } catch (Exception ex) {
            Logger.getLogger(CustomComboModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
