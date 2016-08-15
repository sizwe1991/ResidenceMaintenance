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
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import za.ac.vut.Entity.*;
import za.ac.vut.Facade.*;
import za.ac.vut.util.*;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped

public class UserController
{

    private boolean isPassword;
    private Staff staff;
    private Msg msg;
    private String staffNo, confirmPassword, StuffInput;

    @EJB
    private StaffFacade staffFacade;

    private List<Staff> staffList, technicianList;

    @PostConstruct
    public void init()
    {
        staffList = staffFacade.findAll();

        for (Staff objStaff : staffList)
        {
            if (objStaff.getType().equalsIgnoreCase("Technician"))
            {
                technicianList.add(objStaff);
            }
        }
    }

    public UserController()
    {
        technicianList = new ArrayList<>();
        staffList = new ArrayList<>();
        msg = new Msg();
        staff = new Staff();
        isPassword = false;
    }

    public Staff getStaff()
    {
        return staff;
    }

    public void setStaff(Staff staff)
    {
        this.staff = staff;
    }

    public void setIsPassword(boolean isPassword)
    {
        this.isPassword = isPassword;
    }

    public String getConfirmPassword()
    {
        return confirmPassword;
    }

    public void setTechnicianList(List<Staff> technicianList)
    {
        this.technicianList = technicianList;
    }

    public List<Staff> getTechnicianList()
    {
        return technicianList;
    }

    public void setConfirmPassword(String confirmPassword)
    {
        this.confirmPassword = confirmPassword;
    }

    public String getStuffInput()
    {
        return StuffInput;
    }

    public void setStuffInput(String StuffInput)
    {
        this.StuffInput = StuffInput;
    }

    public void refreshStaff()
    {
        staffList = staffFacade.findAll();
        StuffInput = "";
    }

    public void btnSearchUser()
    {
        staffList.clear();
        for (Staff objStaff : staffFacade.findAll())
        {
            String number = objStaff.getStaffNo() + "";
            if (objStaff.getSurname().toLowerCase().contains(StuffInput.toLowerCase())
                    || objStaff.getName().toLowerCase().contains(StuffInput.toLowerCase())
                    || objStaff.getEmail().toLowerCase().contains(StuffInput.toLowerCase())
                    || objStaff.getRoom().toLowerCase().contains(StuffInput.toLowerCase())
                    || objStaff.getType().toLowerCase().contains(StuffInput.toLowerCase())
                    || number.contains(StuffInput))
            {
                staffList.add(objStaff);
            }
        }

    }

    public boolean getIsPassword()
    {
        return isPassword;
    }

    public void onTypeChange()
    {
        isPassword = !(staff.getType() == null || staff.getType().equalsIgnoreCase("Technician"));
    }

    public String getStaffNo()
    {
        return staffNo;
    }

    public List<Staff> getStaffList()
    {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList)
    {
        this.staffList = staffList;
    }

    public void setStaffNo(String staffNo)
    {
        this.staffNo = staffNo;
    }

    private boolean checkEmail(String email)
    {
        boolean value = false;

        for (Staff Objstaff : staffList)
        {
            if (Objstaff.getEmail().equalsIgnoreCase(email))
            {
                value = true;
            }
        }
        return value;
    }

    private boolean checkstaffNo(String staffNo)
    {
        boolean value = false;

        for (Staff Objstaff : staffList)
        {
            if (Objstaff.getStaffNo().equalsIgnoreCase(staffNo))
            {
                value = true;
            }
        }
        return value;
    }

    public void onRowCancel(RowEditEvent event)
    {
        FacesContext context = FacesContext.getCurrentInstance();

        Staff cancelledUser = (Staff) event.getObject();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Changes cancelled", cancelledUser.getName() + " ( " + cancelledUser.getStaffNo() + " )"));

    }

    public void saveUser()
    {
        if (staffNo == null)
        {
            msg.error("Please Enter Staff Number");
        } else if (staffNo.length() < 5)
        {
            msg.error("Staff Number is too short it must be greater 5 numbers");
        } else if (staff.getName().isEmpty())
        {
            msg.error("Please Enter Staff Name");
        } else if (staff.getName().length() < 3)
        {
            msg.error("Name is too short it must be atleast be 3 characters");
        } else if (staff.getSurname().isEmpty())
        {
            msg.error("Please Enter Staff Surname");
        } else if (staff.getSurname().length() < 3)
        {
            msg.error("Surname is too short it must be atleast be 3 Characters");
        } else if (staff.getEmail().isEmpty())
        {
            msg.error("Please Enter Email Address");
        } else if (!staff.getEmail().endsWith("@vut.ac.za"))
        {
            msg.error("Plase Enter a valid VUT email address");
        } else if (staff.getEmail().startsWith("@vut.ac.za"))
        {
            msg.error("invalid email address");
        } else if (checkEmail(staff.getEmail()))
        {
            msg.error("Staff with the Same email exists already");
        } else if (staff.getRoom().isEmpty())
        {
            msg.error("Please Enter Room for Staff");
        } else if (staff.getExt().isEmpty())
        {
            msg.error("Please Enter Staff Extension Number");
        } else if (staff.getExt().length() != 4)
        {
            msg.error("Extension Number must be 4 numbers");
        } else
        {
            int extNum;

            try
            {
                staff.setStaffNo("" + Integer.parseInt(staffNo));
            } catch (NumberFormatException es)
            {
                msg.error("Staff Number must only contain numbers only");
                return;
            }

            try
            {
                extNum = Integer.parseInt(staff.getExt());
            } catch (NumberFormatException es)
            {
                msg.error("Extension Number must contain only numbers");
                return;
            }

            if (checkstaffNo(staff.getStaffNo()))
            {
                msg.error("Please Enter Staff Number");
            } else if (staff.getType() == null)
            {
                msg.error("Please Select Staff Type");
            } else if (staff.getType().equalsIgnoreCase("Technician"))
            {
                staffFacade.create(staff);
                staff = new Staff();
                staffNo = null;
                staffList = staffFacade.findAll();
                msg.info("New Staff Member has Successfully Been Saved");
            } else if (staff.getPassword().isEmpty())
            {
                msg.error("Please Enter Staff Password");
            } else if (staff.getPassword().length() < 5)
            {
                msg.error("Password must be atleast be 5 characters long");
            } else if (!staff.getPassword().equals(confirmPassword))
            {
                msg.error("Password does not match");
            } else
            {
                int number = 0;

                if (isPassword)
                {
                    number = 1;
                }

                staff.setForcePassword("" + number);
                staffFacade.create(staff);
                staff = new Staff();
                staffNo = null;
                staffList = staffFacade.findAll();
                msg.info("New Staff Member has Successfully Been Saved");
            }
        }
    }
}
