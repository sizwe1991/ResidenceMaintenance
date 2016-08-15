/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.Controller;

import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import za.ac.vut.Entity.*;
import za.ac.vut.Facade.*;
import za.ac.vut.util.Msg;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped
public class ResidenceControler
{
    
    private Msg msg;
    
    @EJB
    private ResidenceFacade residenceFacade;
    
    private List<Residence> residenceList;
    
    private String searchInput, resName;
    
    private Residence residence;
    
    public void ResidenceControler()
    {
        residenceList = new ArrayList<>();
    }
    
    @PostConstruct
    public void init()
    {
        msg = new Msg();
        residence = new Residence();
        residenceList = residenceFacade.findAll();
    }
    
    public List<Residence> getResidenceList()
    {
        return residenceList;
    }
    
    public void setResidenceList(List<Residence> residenceList)
    {
        this.residenceList = residenceList;
    }
    
    public ResidenceFacade getResidenceFacade()
    {
        return residenceFacade;
    }
    
    public void setSearchInput(String searchInput)
    {
        this.searchInput = searchInput;
    }
    
    public void setResName(String resName)
    {
        this.resName = resName;
    }
    
    public String getResName()
    {
        return resName;
    }
    
    public Residence getResidence()
    {
        return residence;
    }
    
    public void setResidence(Residence residence)
    {
        this.residence = residence;
    }
    
    public void saveResidence()
    {
        if (resName.isEmpty())
        {
            msg.error("Please Enter Residence Name");
        } else if (checkResName(resName))
        {
            msg.error("Residence has already been added");
        } else if (residence.getRequireBlock() == null)
        {
            msg.error("Please Select If Block required or not");
        } else
        {
            residence.setResName(resName);
            residenceFacade.create(residence);
            resName = "";
            residence = new Residence();
            residenceList = residenceFacade.findAll();
            msg.info("New Residence has Successfully been Added");
        }
    }
    
    private boolean checkResName(String name)
    {
        boolean value = false;
        for (Residence res : residenceList)
        {
            if (res.getResName().equalsIgnoreCase(name))
            {
                value = true;
            }
        }
        return value;
    }
    
    public String getSearchInput()
    {
        return searchInput;
    }
    
    public void onRowCancel(RowEditEvent event)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        Residence cancelledUser = (Residence) event.getObject();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Changes cancelled", cancelledUser.getResName()));
        
    }
    
    public void onRowEdit(RowEditEvent event)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        Residence editRes = (Residence) event.getObject();
        
        if (editRes.getResName().isEmpty())
        {
            msg.error("Please Enter Residence");
        } else
        {
            residenceFacade.edit(editRes);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Residence Information editted: ", editRes.getResName()));
            
        }
        
    }
    
    public void removeResidence(Residence myRes)
    {
        residenceFacade.remove(myRes);
        residenceList = residenceFacade.findAll();
        msg.info(myRes.getResName() + " has been removed");
    }
    
    public void refreshResidence()
    {
        searchInput = "";
        residenceList = residenceFacade.findAll();
    }
    
    public void searchResidence()
    {
        residenceList.clear();
        for (Residence res : residenceFacade.findAll())
        {
            if (res.getResName().toLowerCase().contains(searchInput.toLowerCase()))
            {
                residenceList.add(res);
            }
        }
        
        searchInput = "";
    }
    
}
