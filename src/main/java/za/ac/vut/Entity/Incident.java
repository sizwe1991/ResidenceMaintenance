/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2015127
 */
@Entity
@Table(name = "incident")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Incident.findAll", query = "SELECT i FROM Incident i"),
    @NamedQuery(name = "Incident.findByIncidentID", query = "SELECT i FROM Incident i WHERE i.incidentID = :incidentID"),
    @NamedQuery(name = "Incident.findByDateLogged", query = "SELECT i FROM Incident i WHERE i.dateLogged = :dateLogged"),
    @NamedQuery(name = "Incident.findByStatus", query = "SELECT i FROM Incident i WHERE i.status = :status"),
    @NamedQuery(name = "Incident.findByDateResolved", query = "SELECT i FROM Incident i WHERE i.dateResolved = :dateResolved"),
    @NamedQuery(name = "Incident.findByDescription", query = "SELECT i FROM Incident i WHERE i.description = :description"),
    @NamedQuery(name = "Incident.findByCategory", query = "SELECT i FROM Incident i WHERE i.category = :category"),
    @NamedQuery(name = "Incident.findByAvailability", query = "SELECT i FROM Incident i WHERE i.availability = :availability"),
    @NamedQuery(name = "Incident.findByTimeLogged", query = "SELECT i FROM Incident i WHERE i.timeLogged = :timeLogged"),
    @NamedQuery(name = "Incident.findByComment", query = "SELECT i FROM Incident i WHERE i.comment = :comment")
})
public class Incident implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "incidentID")
    private Integer incidentID;
    @Column(name = "dateLogged")
    @Temporal(TemporalType.DATE)
    private Date dateLogged;
    @Size(max = 45)
    @Column(name = "status")
    private String status;
    @Column(name = "dateResolved")
    @Temporal(TemporalType.DATE)
    private Date dateResolved;
    @Size(max = 105)
    @Column(name = "description")
    private String description;
    @Size(max = 45)
    @Column(name = "category")
    private String category;
    @Size(max = 45)
    @Column(name = "availability")
    private String availability;
    @Size(max = 45)
    @Column(name = "timeLogged")
    private String timeLogged;
    @Size(max = 200)
    @Column(name = "comment")
    private String comment;
    @JoinColumn(name = "attendent", referencedColumnName = "staffNo")
    @ManyToOne
    private Staff attendent;
    @JoinColumn(name = "studentNo", referencedColumnName = "studentNo")
    @ManyToOne
    private Student studentNo;

    public Incident()
    {
    }

    public Incident(Integer incidentID)
    {
        this.incidentID = incidentID;
    }

    public Integer getIncidentID()
    {
        return incidentID;
    }

    public void setIncidentID(Integer incidentID)
    {
        this.incidentID = incidentID;
    }

    public Date getDateLogged()
    {
        return dateLogged;
    }

    public void setDateLogged(Date dateLogged)
    {
        this.dateLogged = dateLogged;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getDateResolved()
    {
        return dateResolved;
    }

    public void setDateResolved(Date dateResolved)
    {
        this.dateResolved = dateResolved;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getAvailability()
    {
        return availability;
    }

    public void setAvailability(String availability)
    {
        this.availability = availability;
    }

    public String getTimeLogged()
    {
        return timeLogged;
    }

    public void setTimeLogged(String timeLogged)
    {
        this.timeLogged = timeLogged;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public Staff getAttendent()
    {
        return attendent;
    }

    public void setAttendent(Staff attendent)
    {
        this.attendent = attendent;
    }

    public Student getStudentNo()
    {
        return studentNo;
    }

    public void setStudentNo(Student studentNo)
    {
        this.studentNo = studentNo;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (incidentID != null ? incidentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Incident))
        {
            return false;
        }
        Incident other = (Incident) object;
        if ((this.incidentID == null && other.incidentID != null) || (this.incidentID != null && !this.incidentID.equals(other.incidentID)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "za.ac.vut.Entity.Incident[ incidentID=" + incidentID + " ]";
    }
    
}
