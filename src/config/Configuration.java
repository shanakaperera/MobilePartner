/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import org.smslib.modem.SerialModemGateway;

/**
 *
 * @author Shanaka
 */
public class Configuration {

    private final String id;
    private final String comPort;
    private final int baudRate;
    private final String manufacturer;
    private final String model;

    public Configuration(String id, String comPort, int baudRate, String manufacturer, String model) {
        this.id = id;
        this.comPort = comPort;
        this.baudRate = baudRate;
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public SerialModemGateway getConfiguredGateway(boolean sendMessage, boolean recieveMessasge) {
        SerialModemGateway gateway = new SerialModemGateway(id, comPort, baudRate,
                manufacturer, model);
        gateway.setInbound(recieveMessasge);
        gateway.setOutbound(sendMessage);

        return gateway;

    }

}
