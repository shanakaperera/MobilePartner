/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Shanaka
 */
@Entity
@Table(name = "saved_contacts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SavedContacts.findAll", query = "SELECT s FROM SavedContacts s")
    , @NamedQuery(name = "SavedContacts.findById", query = "SELECT s FROM SavedContacts s WHERE s.id = :id")
    , @NamedQuery(name = "SavedContacts.findByContactName", query = "SELECT s FROM SavedContacts s WHERE s.contactName = :contactName")
    , @NamedQuery(name = "SavedContacts.findByNumber", query = "SELECT s FROM SavedContacts s WHERE s.number = :number")
    , @NamedQuery(name = "SavedContacts.findByDateAdded", query = "SELECT s FROM SavedContacts s WHERE s.dateAdded = :dateAdded")
    , @NamedQuery(name = "SavedContacts.findByDateUpdated", query = "SELECT s FROM SavedContacts s WHERE s.dateUpdated = :dateUpdated")
    , @NamedQuery(name = "SavedContacts.findByStatus", query = "SELECT s FROM SavedContacts s WHERE s.status = :status")})
public class SavedContacts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "contact_name")
    private String contactName;
    @Column(name = "number")
    private String number;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "date_added")
    @Temporal(TemporalType.DATE)
    private Date dateAdded;
    @Column(name = "date_updated")
    @Temporal(TemporalType.DATE)
    private Date dateUpdated;
    @Basic(optional = false)
    @Column(name = "status")
    private boolean status;

    public SavedContacts() {
    }

    public SavedContacts(Integer id) {
        this.id = id;
    }

    public SavedContacts(Integer id, boolean status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SavedContacts)) {
            return false;
        }
        SavedContacts other = (SavedContacts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db_pojo.SavedContacts[ id=" + id + " ]";
    }
    
}
