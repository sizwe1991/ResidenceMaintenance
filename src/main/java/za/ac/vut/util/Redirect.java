/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped
public class Redirect
{

    public void newStaff()
    {
        navigate("/staff/newStaff.xhtml?faces-redirect=true");
    }

    public void dashboard()
    {
        navigate("/other/dashboard.xhtml?faces-redirect=true");
    }

    public void stduent()
    {
        navigate("/student/student.xhtml?faces-redirect=true");
    }

    public void manageStaff()
    {
        navigate("/staff/manageStaff.xhtml?faces-redirect=true");
    }

    public void manageResidence()
    {
        navigate("/staff/manageResidence.xhtml?faces-redirect=true");
    }

     public void dashBoard()
    {
        navigate("/other/dashboard.xhtml?faces-redirect=true");
    }
     
       public void report()
    {
        navigate("/other/report.xhtml?faces-redirect=true");
    }
       
    public void viewIncident()
    {
        navigate("/staff/viewIncident.xhtml?faces-redirect=true");
    }

    public void manageCategory()
    {
        navigate("/staff/manageCategories.xhtml?faces-redirect=true");
    }

    private void navigate(String url)
    {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, url);
    }
}
