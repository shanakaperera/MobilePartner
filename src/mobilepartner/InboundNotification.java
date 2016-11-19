/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobilepartner;

import controller.MobilePartnerController;
import javax.persistence.EntityManagerFactory;
import jpa_controllers.InboundMessageJpaController;
import org.smslib.AGateway;
import org.smslib.IInboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Message;
import view.MessagesTableModel;
import view.MobilePartnerView;

/**
 *
 * @author Shanaka
 */
//Get triggered when a SMS is recieved
public class InboundNotification implements IInboundMessageNotification {

    @Override
    public void process(AGateway ag, Message.MessageTypes mt, InboundMessage im) {
        System.out.println(im);
        try {
            //save message from inbox to database after reading 
            EntityManagerFactory emf = MobilePartnerController.getInstance().getEntityManagerFactory();
            InboundMessageJpaController controller = new InboundMessageJpaController(emf);

            db_pojo.InboundMessage dbMessage = new db_pojo.InboundMessage();
            dbMessage.setDateRecived(im.getDate());
            dbMessage.setMemIndex(im.getMemIndex());
            dbMessage.setMemLocation(im.getMemLocation());
            dbMessage.setMessage(im.getText().toLowerCase());
            dbMessage.setOriginator(im.getOriginator());
            dbMessage.setMpSeqNo(im.getMpSeqNo());
            dbMessage.setMpRefNo(im.getMpRefNo());
            dbMessage.setMpMaxNo(im.getMpMaxNo());

            controller.create(dbMessage);
            emf.close();
            //delete message from inbox after reading 
            ag.deleteMessage(im);
            MessagesTableModel model = MessagesTableModel.getInstance();
            model.addAllData();
            MobilePartnerView.messageTable.setModel(model);
            model.fireTableDataChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // msg.getOriginator(); == sender phone number
    /*
    
        if (msgType == MessageTypes.INBOUND) System.out.println(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
        else if (msgType == MessageTypes.STATUSREPORT) System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
        System.out.println(msg);
    
    
     */
}
