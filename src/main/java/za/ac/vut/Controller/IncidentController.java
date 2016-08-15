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
import javax.faces.application.ViewHandler;
import javax.faces.bean.*;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import za.ac.vut.Entity.*;
import za.ac.vut.Facade.*;
import za.ac.vut.util.Msg;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped

public class IncidentController
{

    private Msg msg;

    private String technicianName, comment;

    private Incident selectedIncident;

    @EJB
    private IncidentFacade incidentFacade;

    @EJB
    private StaffFacade staffFacade;

    private List<Staff> staffList;
    private List<Incident> incidentList;

    @PostConstruct
    public void init()
    {
        staffList = staffFacade.findAll();
        incidentList = incidentList = incidentFacade.findAll();
    }

    public IncidentController()
    {
        staffList = new ArrayList<>();
        msg = new Msg();

        incidentList = new ArrayList<>();
    }

    public List<Incident> getIncidentList()
    {
        return incidentList;
    }

    public void setIncidentList(List<Incident> incidentList)
    {
        this.incidentList = incidentList;
    }

    public String getTechnicianName()
    {
        return technicianName;
    }

    public void setTechnicianName(String technicianName)
    {
        this.technicianName = technicianName;
    }

    public String getTechnicianText(Staff attendant)
    {
        String text = "";
        if (attendant == null)
        {
            text = "UnAssigned";
        } else
        {
            text = attendant.getSurname() + " " + attendant.getName().substring(0, 1).toUpperCase();
        }
        return text;
    }

    public void showTechnicianModel(Incident theIncident)
    {
        selectedIncident = theIncident;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg').show();");
    }

    public void saveTechnician()
    {

        if (technicianName.isEmpty())
        {
            msg.error("Please Select Technician");
            refreshPage();
        } else
        {
            Staff technician = null;
            for (Staff staff : staffList)
            {
                if (staff.getStaffNo().equalsIgnoreCase(technicianName))
                {
                    technician = staff;
                }
            }

            technicianName = "";

            selectedIncident.setAttendent(technician);
            if (selectedIncident.getStatus().equalsIgnoreCase("New"))
            {
                selectedIncident.setStatus("Open");
            }

            incidentFacade.edit(selectedIncident);
            incidentList = incidentFacade.findAll();

            msg.info("Technician Assigned Successfully to Incident");
        }

    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public void onCellEdit(CellEditEvent event)
    {

//         FacesContext context = FacesContext.getCurrentInstance();
//
        int index = event.getRowIndex();

        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue))
        {
            String value = (String) newValue;

            Incident incident = incidentList.get(index);
            incident.setStatus(value);

            if (incident.getAttendent() == null)
            {
                refreshPage();
                msg.error("Technican is required before status of the incident can be changed");
            } else if (value.equalsIgnoreCase("Open"))
            {
                incidentFacade.edit(incident);
                incidentList = incidentFacade.findAll();
                msg.info("Incident Status Changed Successfully");
            } else
            {
                selectedIncident = incidentList.get(index);
                selectedIncident.setStatus(value);

                //acsaSystem.out.println(selectedIncident + "\n" + incident);
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dlgcomment').show();");
            }

        } else
        {
            msg.error("Status is not Changed as old Status and New Status is the Same");
            refreshPage();
        }

    }

    public Incident getSelectedIncident()
    {
        return selectedIncident;
    }

    public void setSelectedIncident(Incident selectedIncident)
    {
        this.selectedIncident = selectedIncident;
    }

    private void refreshPage()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
    }

    public void changeStatus()
    {
        selectedIncident.setComment(comment);
        incidentFacade.edit(selectedIncident);
        incidentList = incidentFacade.findAll();
        comment = "";
        msg.info("Status has been changed Successfully");
    }

    public String hasData(String data)
    {
        String value = "No";
        if (data.isEmpty())
        {
            value = "Yes";
        }
        return value;
    }
}
