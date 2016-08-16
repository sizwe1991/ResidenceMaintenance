/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.Controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpSession;
import za.ac.vut.Entity.Staff;
import za.ac.vut.Facade.*;
import za.ac.vut.util.ActiveDirectory;
import za.ac.vut.util.Msg;
import za.ac.vut.util.Redirect;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped
public class LoginController
{

    @EJB
    private StaffFacade staffFacade;

    private List<Staff> staffList;

    private Redirect redirect;

    private Msg msg = new Msg();
    private String usernametxt = "Staff Number", type = "STAFF", username, password, message;

    @PostConstruct
    public void init()
    {
        staffList = staffFacade.findAll();
        redirect = new Redirect();
    }

    public LoginController()
    {
        staffList = new ArrayList<>();
    }

    public String getUsernametxt()
    {
        return usernametxt;
    }

    public void setUsernametxt(String usernametxt)
    {
        this.usernametxt = usernametxt;
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

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void login()
    {
        if (username.isEmpty())
        {
            msg.error("Please Enter Username");
        } else if (password.isEmpty())
        {
            msg.error("Please Enter Password");
        } else if (type.equalsIgnoreCase("STAFF"))
        {
            if (!isStaffAuthenticated())
            {
                msg.error("Invalid Username or Password");
            } else
            {
                FacesContext context2 = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);
                session.setAttribute("staff", username);
                redirect.dashboard();
            }
        } else if (type.equalsIgnoreCase("STUDENT"))
        {
            if (!isStudentAuthenticated())
            {
                if (message.equals(""))
                {
                    msg.error("Invalid Student Number or Password");
                } else
                {
                    msg.error("Failed to Authenticate you because \n " + message);
                }
            } else
            {
                FacesContext context2 = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);
                session.setAttribute("student", username);
                redirect.stduent();
            }
        }
    }

    private boolean isStaffAuthenticated()
    {
        boolean value = false;
        for (Staff staff : staffList)
        {
            if (staff.getPassword() != null)
            {
                if (staff.getStaffNo().equalsIgnoreCase(username)
                        && staff.getPassword().equals(password))
                {
                    value = true;
                }
            }
        }
        return value;
    }

    public boolean isStudentAuthenticated()
    {
        boolean value = false;
        try
        {
            // LdapContext ctx = ActiveDirectory.getConnection("210136758", "38892211");

            LdapContext ctx = ActiveDirectory.getConnection(username, password);
            ctx.close();
            value = true;
        } catch (NamingException s)
        {
            message = "";
        } catch (Exception e)
        {
            //Failed to authenticate user!
            //   e.printStackTrace();
            message = e.getMessage();
        }
        return value;
    }

    public String getStaffName()
    {
        return staffFacade.find(username).getName();
    }

    public void typeChange()
    {
        if (type != null)
        {
            if (getType().equalsIgnoreCase("STAFF"))
            {
                setUsernametxt("Staff Number ");

            } else if (getType().equalsIgnoreCase("STUDENT"))
            {
                setUsernametxt("Student Number ");

            }
        }
    }
}
