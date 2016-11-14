/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;


/**
 *
 * @author Shanaka
 */
public class Message implements Serializable {
    private  int id;
    private String message;
    private String number;
    private String type;
    private String dateReceived;

    public Message() {
    }

    public Message(String message, String number, String type, String dateRecived) {
        this.message = message;
        this.number = number;
        this.type = type;
        this.dateReceived = dateRecived;
    }

    public Message(int id, String message, String number, String type, String dateReceived) {
        this.id = id;
        this.message = message;
        this.number = number;
        this.type = type;
        this.dateReceived = dateReceived;
    }
    
    

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the dateReceived
     */
    public String getDateReceived() {
        return dateReceived;
    }

    /**
     * @param dateReceived the dateReceived to set
     */
    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
