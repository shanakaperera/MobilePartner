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
@Table(name = "inbound_message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InboundMessage.findAll", query = "SELECT i FROM InboundMessage i")
    , @NamedQuery(name = "InboundMessage.findById", query = "SELECT i FROM InboundMessage i WHERE i.id = :id")
    , @NamedQuery(name = "InboundMessage.findByOriginator", query = "SELECT i FROM InboundMessage i WHERE i.originator = :originator")
    , @NamedQuery(name = "InboundMessage.findByMemIndex", query = "SELECT i FROM InboundMessage i WHERE i.memIndex = :memIndex")
    , @NamedQuery(name = "InboundMessage.findByMpRefNo", query = "SELECT i FROM InboundMessage i WHERE i.mpRefNo = :mpRefNo")
    , @NamedQuery(name = "InboundMessage.findByMpMaxNo", query = "SELECT i FROM InboundMessage i WHERE i.mpMaxNo = :mpMaxNo")
    , @NamedQuery(name = "InboundMessage.findByMpSeqNo", query = "SELECT i FROM InboundMessage i WHERE i.mpSeqNo = :mpSeqNo")
    , @NamedQuery(name = "InboundMessage.findByMpMemIndex", query = "SELECT i FROM InboundMessage i WHERE i.mpMemIndex = :mpMemIndex")
    , @NamedQuery(name = "InboundMessage.findByDateRecived", query = "SELECT i FROM InboundMessage i WHERE i.dateRecived = :dateRecived")
    , @NamedQuery(name = "InboundMessage.findByIsRead", query = "SELECT i FROM InboundMessage i WHERE i.isRead = :isRead")})
public class InboundMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "message")
    private String message;
    @Column(name = "originator")
    private String originator;
    @Column(name = "mem_index")
    private Integer memIndex;
    @Lob
    @Column(name = "mem_location")
    private String memLocation;
    @Column(name = "mp_ref_no")
    private Integer mpRefNo;
    @Column(name = "mp_max_no")
    private Integer mpMaxNo;
    @Column(name = "mp_seq_no")
    private Integer mpSeqNo;
    @Column(name = "mp_mem_index")
    private Integer mpMemIndex;
    @Column(name = "date_recived")
    @Temporal(TemporalType.DATE)
    private Date dateRecived;
    @Basic(optional = false)
    @Column(name = "is_read")
    private boolean isRead;

    public InboundMessage() {
    }

    public InboundMessage(Integer id) {
        this.id = id;
    }

    public InboundMessage(Integer id, boolean isRead) {
        this.id = id;
        this.isRead = isRead;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public Integer getMemIndex() {
        return memIndex;
    }

    public void setMemIndex(Integer memIndex) {
        this.memIndex = memIndex;
    }

    public String getMemLocation() {
        return memLocation;
    }

    public void setMemLocation(String memLocation) {
        this.memLocation = memLocation;
    }

    public Integer getMpRefNo() {
        return mpRefNo;
    }

    public void setMpRefNo(Integer mpRefNo) {
        this.mpRefNo = mpRefNo;
    }

    public Integer getMpMaxNo() {
        return mpMaxNo;
    }

    public void setMpMaxNo(Integer mpMaxNo) {
        this.mpMaxNo = mpMaxNo;
    }

    public Integer getMpSeqNo() {
        return mpSeqNo;
    }

    public void setMpSeqNo(Integer mpSeqNo) {
        this.mpSeqNo = mpSeqNo;
    }

    public Integer getMpMemIndex() {
        return mpMemIndex;
    }

    public void setMpMemIndex(Integer mpMemIndex) {
        this.mpMemIndex = mpMemIndex;
    }

    public Date getDateRecived() {
        return dateRecived;
    }

    public void setDateRecived(Date dateRecived) {
        this.dateRecived = dateRecived;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
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
        if (!(object instanceof InboundMessage)) {
            return false;
        }
        InboundMessage other = (InboundMessage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db_pojo.InboundMessage[ id=" + id + " ]";
    }
    
}
