/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.Entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 2015127
 */
@Entity
@Table(name = "staff")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s"),
    @NamedQuery(name = "Staff.findByStaffNo", query = "SELECT s FROM Staff s WHERE s.staffNo = :staffNo"),
    @NamedQuery(name = "Staff.findByName", query = "SELECT s FROM Staff s WHERE s.name = :name"),
    @NamedQuery(name = "Staff.findBySurname", query = "SELECT s FROM Staff s WHERE s.surname = :surname"),
    @NamedQuery(name = "Staff.findByRoom", query = "SELECT s FROM Staff s WHERE s.room = :room"),
    @NamedQuery(name = "Staff.findByExt", query = "SELECT s FROM Staff s WHERE s.ext = :ext"),
    @NamedQuery(name = "Staff.findByEmail", query = "SELECT s FROM Staff s WHERE s.email = :email"),
    @NamedQuery(name = "Staff.findByType", query = "SELECT s FROM Staff s WHERE s.type = :type"),
    @NamedQuery(name = "Staff.findByPassword", query = "SELECT s FROM Staff s WHERE s.password = :password"),
    @NamedQuery(name = "Staff.findByForcePassword", query = "SELECT s FROM Staff s WHERE s.forcePassword = :forcePassword")
})
public class Staff implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "staffNo")
    private String staffNo;
    @Size(max = 45)
    @Column(name = "Name")
    private String name;
    @Size(max = 45)
    @Column(name = "Surname")
    private String surname;
    @Size(max = 45)
    @Column(name = "Room")
    private String room;
    @Size(max = 45)
    @Column(name = "Ext")
    private String ext;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "Email")
    private String email;
    @Size(max = 45)
    @Column(name = "Type")
    private String type;
    @Size(max = 45)
    @Column(name = "Password")
    private String password;
    @Size(max = 45)
    @Column(name = "force_password")
    private String forcePassword;
    @OneToMany(mappedBy = "attendent")
    private Collection<Incident> incidentCollection;

    public Staff()
    {
    }

    public Staff(String staffNo)
    {
        this.staffNo = staffNo;
    }

    public String getStaffNo()
    {
        return staffNo;
    }

    public void setStaffNo(String staffNo)
    {
        this.staffNo = staffNo;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getRoom()
    {
        return room;
    }

    public void setRoom(String room)
    {
        this.room = room;
    }

    public String getExt()
    {
        return ext;
    }

    public void setExt(String ext)
    {
        this.ext = ext;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getForcePassword()
    {
        return forcePassword;
    }

    public void setForcePassword(String forcePassword)
    {
        this.forcePassword = forcePassword;
    }

    @XmlTransient
    public Collection<Incident> getIncidentCollection()
    {
        return incidentCollection;
    }

    public void setIncidentCollection(Collection<Incident> incidentCollection)
    {
        this.incidentCollection = incidentCollection;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (staffNo != null ? staffNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Staff))
        {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.staffNo == null && other.staffNo != null) || (this.staffNo != null && !this.staffNo.equals(other.staffNo)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "za.ac.vut.Entity.Staff[ staffNo=" + staffNo + " ]";
    }
    
}
