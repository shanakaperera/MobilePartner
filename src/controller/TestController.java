/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import db_pojo.MessageDefine;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa_controllers.MessageDefineJpaController;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Shanaka
 */
public class TestController {

    final static String DIR = System.getProperty("user.dir");

    public static void main(String[] args) throws Exception {

        File f = new File(DIR + "\\dbconf.xml");
        if (f.exists() && !f.isDirectory()) {
            TestController tc = new TestController();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MobilePartnerPU",
                    tc.getConfiguredOverrides());
            MessageDefineJpaController mdjc = new MessageDefineJpaController(emf);
            List<MessageDefine> msgs = mdjc.findMessageDefineEntities();
            for (MessageDefine msg : msgs) {
                System.out.println(msg.getTypeName());
            }

        } else {
            TestController tc = new TestController();
            tc.createXML();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MobilePartnerPU",
                    tc.getConfiguredOverrides());
            MessageDefineJpaController mdjc = new MessageDefineJpaController(emf);

            List<MessageDefine> msgs = mdjc.findMessageDefineEntities();
            for (MessageDefine msg : msgs) {
                System.out.println(msg.getTypeName());
            }
        }

//        
//        MessageDefine messageDefine = mdjc.findMessageDefine(2);
//       // messageDefine.setId(2);
//        messageDefine.setTypeName("success");
//        messageDefine.setDateUpdated(new Date());
//        messageDefine.setDateAdded(new Date());
//        messageDefine.setStatus(true);
//      //  mdjc.create(messageDefine);
//        
//        mdjc.edit(messageDefine);
//        List<MessageDefine> msgs = mdjc.findMessageDefineEntities();
//        for (MessageDefine msg : msgs) {
//            System.out.println(msg.getTypeName());
//        }
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
}
