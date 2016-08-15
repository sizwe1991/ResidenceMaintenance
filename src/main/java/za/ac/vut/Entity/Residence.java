/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2015127
 */
@Entity
@Table(name = "residence")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Residence.findAll", query = "SELECT r FROM Residence r"),
    @NamedQuery(name = "Residence.findByResName", query = "SELECT r FROM Residence r WHERE r.resName = :resName"),
    @NamedQuery(name = "Residence.findByRequireBlock", query = "SELECT r FROM Residence r WHERE r.requireBlock = :requireBlock")
})
public class Residence implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "resName")
    private String resName;
    @Size(max = 45)
    @Column(name = "requireBlock")
    private String requireBlock;

    public Residence()
    {
    }

    public Residence(String resName)
    {
        this.resName = resName;
    }

    public String getResName()
    {
        return resName;
    }

    public void setResName(String resName)
    {
        this.resName = resName;
    }

    public String getRequireBlock()
    {
        return requireBlock;
    }

    public void setRequireBlock(String requireBlock)
    {
        this.requireBlock = requireBlock;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (resName != null ? resName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Residence))
        {
            return false;
        }
        Residence other = (Residence) object;
        if ((this.resName == null && other.resName != null) || (this.resName != null && !this.resName.equals(other.resName)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "za.ac.vut.Entity.Residence[ resName=" + resName + " ]";
    }
    
}
