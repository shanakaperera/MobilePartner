/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import config.Configuration;
import java.io.File;
import java.io.FileWriter;
import org.smslib.helper.CommPortIdentifier;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import mobilepartner.InboundNotification;
import mobilepartner.OutboundNotification;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.smslib.GatewayException;
import org.smslib.InboundMessage;
import org.smslib.Message;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.helper.SerialPort;
import org.smslib.modem.SerialModemGateway;

/**
 *
 * @author Shanaka
 */
public class MobilePartnerController {

    private static final Service SERVICE = Service.getInstance();
    final static String DIR = System.getProperty("user.dir");
    private static SerialModemGateway gateway;
    private static final int BUD_RATE = 115200;
    private static final String NO_DEVICE_FOUND = "  no device found";
    private static final File DB_CONF_FILE = new File(DIR + "\\dbconf.xml");
    private String foundPort;
    private static MobilePartnerController singletonForm = new MobilePartnerController();

    private MobilePartnerController() {
    }

    public static MobilePartnerController getInstance() {
        return singletonForm;
    }

    private String getFoundPort() {
        return foundPort;
    }

    private void setFoundPort(String foundPort) {
        this.foundPort = foundPort;
    }

    public boolean IsServiceStarted() {
        return SERVICE.getServiceStatus() == Service.ServiceStatus.STARTED;
    }

    private void startConnection(String port) {
        OutboundNotification outboundNotification = new OutboundNotification();
        InboundNotification inboundNotification = new InboundNotification();
        SERVICE.setOutboundMessageNotification(outboundNotification);
        SERVICE.setInboundMessageNotification(inboundNotification);
        Configuration configuration = new Configuration("", port, BUD_RATE, "", "");
        gateway = configuration.getConfiguredGateway(true, true);
        try {
            SERVICE.addGateway(gateway);
        } catch (GatewayException ex) {
            Logger.getLogger(MobilePartnerController.class.getName()).log(Level.SEVERE,
                    "Problem occured in configuring gateway !", ex);
        }
        try {
            SERVICE.startService();
        } catch (SMSLibException | IOException | InterruptedException ex) {
            Logger.getLogger(MobilePartnerController.class.getName()).log(Level.SEVERE,
                    "Problem occured in starting the service !", ex);
        }
    }

    public void endConnection() {
        if (SERVICE != null) {
            try {
                SERVICE.stopService();
                gateway.stopGateway();
                Service.getInstance().removeGateway(gateway);

            } catch (SMSLibException | IOException | InterruptedException ex) {
                Logger.getLogger(MobilePartnerController.class.getName()).log(Level.SEVERE,
                        "Problem occured in stopping the service !", ex);
            }
        }
    }

    public void sendMessage(String mobileNumber, String message) {
        if (SERVICE != null) {
            //OutboundMessage msg = new OutboundMessage("+94713882827", "test111");
            OutboundMessage msg = new OutboundMessage(mobileNumber, message);
            try {
                SERVICE.sendMessage(msg);
            } catch (TimeoutException ex) {
                Logger.getLogger(MobilePartnerController.class.getName()).log(Level.SEVERE,
                        "Proccess timeout problem !", ex);
            } catch (GatewayException ex) {
                Logger.getLogger(MobilePartnerController.class.getName()).log(Level.SEVERE,
                        "Problem occured in configuring gateway !", ex);
            } catch (IOException ex) {
                Logger.getLogger(MobilePartnerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(MobilePartnerController.class.getName()).log(Level.SEVERE,
                        "Service Interrupted ! ", ex);
            }
        }
    }

    public void deleteMessage(Message.MessageTypes type, int memIndex, String memLocation) {
        try {
            InboundMessage inMsg = new InboundMessage(type, memIndex, memLocation);
            SERVICE.deleteMessage(inMsg);
        } catch (TimeoutException ex) {
            Logger.getLogger(MobilePartnerController.class.getName()).log(Level.SEVERE,
                    "Proccess timeout problem !", ex);
        } catch (GatewayException ex) {
            Logger.getLogger(MobilePartnerController.class.getName()).log(Level.SEVERE,
                    "Problem occured in configuring gateway !", ex);
        } catch (IOException ex) {
            Logger.getLogger(MobilePartnerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MobilePartnerController.class.getName()).log(Level.SEVERE,
                    "Service Interrupted ! ", ex);
        }
    }

    public void readInbox() {
        try {
            ArrayList<InboundMessage> msgList = new ArrayList<>();
            Service.getInstance().readMessages(msgList, InboundMessage.MessageClasses.ALL);
            for (InboundMessage im : msgList) {
                System.out.println(im.getOriginator() + " : " + im.getText() + " : " + im.getDate());
            }
        } catch (TimeoutException | GatewayException | IOException | InterruptedException ex) {
            Logger.getLogger(MobilePartnerController.class.getName()).log(Level.SEVERE,
                    "Error ! Something went wrong . ", ex);
        }
    }

    private void figureOutPort() {
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            if (portIdentifier.getPortType() == org.smslib.helper.CommPortIdentifier.PORT_SERIAL) {
                new Formatter().format("%nFound port: %-5s%n", portIdentifier.getName());

                SerialPort serialPort = null;
                new Formatter().format("       Trying at %6d...", BUD_RATE);
                try {
                    InputStream inStream;
                    OutputStream outStream;
                    int c;
                    String response;
                    serialPort = portIdentifier.open("SMSLibCommTester", 1971);
                    serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
                    serialPort.setSerialPortParams(BUD_RATE, SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                    inStream = serialPort.getInputStream();
                    outStream = serialPort.getOutputStream();
                    serialPort.enableReceiveTimeout(1000);
                    c = inStream.read();
                    while (c != -1) {
                        c = inStream.read();
                    }
                    outStream.write('A');
                    outStream.write('T');
                    outStream.write('\r');
                    Thread.sleep(1000);
                    response = "";
                    StringBuilder sb = new StringBuilder();
                    c = inStream.read();
                    while (c != -1) {
                        sb.append((char) c);
                        c = inStream.read();
                    }
                    response = sb.toString();
                    if (response.contains("OK")) {
                        try {
                            System.out.print("  Getting Info...");
                            outStream.write('A');
                            outStream.write('T');
                            outStream.write('+');
                            outStream.write('C');
                            outStream.write('G');
                            outStream.write('M');
                            outStream.write('M');
                            outStream.write('\r');
                            response = "";
                            c = inStream.read();
                            while (c != -1) {
                                response += (char) c;
                                c = inStream.read();
                            }
                            System.out.println(" Found: " + response.replaceAll("\\s+OK\\s+", "")
                                    .replaceAll("\n", "").replaceAll("\r", ""));

                            setFoundPort(portIdentifier.getName());
                            break;

                        } catch (Exception e) {
                            System.out.println(NO_DEVICE_FOUND);
                        }
                    } else {
                        System.out.println(NO_DEVICE_FOUND);
                    }
                } catch (Exception e) {
                    System.out.print(NO_DEVICE_FOUND);
                    Throwable cause = e;
                    while (cause.getCause() != null) {
                        cause = cause.getCause();
                    }
                    System.out.println(" (" + cause.getMessage() + ")");
                } finally {
                    if (serialPort != null) {
                        serialPort.close();
                    }
                }
            }
        }
    }

    public void beginConnection() {
        figureOutPort();
        System.out.println("PORT USE : " + getFoundPort());
        startConnection(getFoundPort());
    }

    public Map<String, Object> getConfiguredOverrides() throws Exception {

        Map<String, Object> configOverrides = new HashMap<>();
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(DIR + "\\dbconf.xml");
        Document document = (Document) builder.build(xmlFile);
        Element rootNode = document.getRootElement();

        List<Element> list = rootNode.getChildren();

        for (Element element : list) {
            configOverrides.put(element.getAttributeValue("name"), element.getText());
        }

        return configOverrides;
    }

    public void createXML() throws Exception {
        Element root = new Element("dbconf");
        Document doc = new Document();

        Element child1 = new Element("url");
        child1.addContent("jdbc:mysql://localhost:3306/mobile_partner");
        child1.setAttribute("name", "javax.persistence.jdbc.url");

        Element child2 = new Element("user");
        child2.addContent("root");
        child2.setAttribute("name", "javax.persistence.jdbc.user");

        Element child3 = new Element("driver");
        child3.addContent("com.mysql.jdbc.Driver");
        child3.setAttribute("name", "javax.persistence.jdbc.driver");

        Element child4 = new Element("password");
        child4.addContent("nbuser");
        child4.setAttribute("name", "javax.persistence.jdbc.password");

        root.addContent(child1);
        root.addContent(child2);
        root.addContent(child3);
        root.addContent(child4);

        doc.setRootElement(root);

        XMLOutputter outter = new XMLOutputter();
        outter.setFormat(Format.getPrettyFormat());
        outter.output(doc, new FileWriter(new File(DIR + "\\dbconf.xml")));
    }

    public EntityManagerFactory getEntityManagerFactory() throws Exception {
        EntityManagerFactory emf = null;
        if (DB_CONF_FILE.exists() && !DB_CONF_FILE.isDirectory()) {
            emf = Persistence.createEntityManagerFactory("MobilePartnerPU",
                    getConfiguredOverrides());
        } else {
            createXML();
            emf = Persistence.createEntityManagerFactory("MobilePartnerPU",
                    getConfiguredOverrides());

        }
        return emf;
    }

//    public static void main(String[] args) {
//        MobilePartnerController controller = new MobilePartnerController();
//        controller.beginConnection();
//        System.out.println("===============================================");
//        controller.readInbox();
//        //  System.out.println(SERVICE.getServiceStatus() == Service.ServiceStatus.STARTED);
//
//        // controller.sendMessage(mobileNumber, message);
//    }
}
