/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobilepartner;

import org.smslib.AGateway;
import org.smslib.IOutboundMessageNotification;
import org.smslib.OutboundMessage;

/**
 *
 * @author Shanaka
 */

//Get triggered when a SMS is sent

public class OutboundNotification implements IOutboundMessageNotification {

    @Override
    public void process(AGateway ag, OutboundMessage om) {
        System.out.println(om);
    }

}
