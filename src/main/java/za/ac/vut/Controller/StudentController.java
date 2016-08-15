/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.Controller;

import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import za.ac.vut.Entity.*;
import za.ac.vut.Facade.*;
import za.ac.vut.util.Msg;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped

public class StudentController
{

    private Date today = new Date();

    private String time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

    private Msg msg;
    private List<String> availableTimes;

    @EJB
    private ResidenceFacade residenceFacade;

    @EJB
    private StudentFacade studentFacade;

    @EJB
    private CategoryFacade categoryFacade;

    @EJB
    private IncidentFacade incidentFacade;

    private boolean blockRequired = false;

    private List<String> residenceList, categoryList;
    private List<Student> studentList;
    private List<Incident> incidentList, incidentDataList;
    private List<Residence> allResidenceList;

    private Incident incident;
    private Student student, retrievedStudent;

    private String searchInput, status = "All";

    public StudentController()
    {
        retrievedStudent = new Student();
        student = new Student();
        msg = new Msg();

        allResidenceList = new ArrayList<>();
        residenceList = new ArrayList<>();
        categoryList = new ArrayList<>();
        studentList = new ArrayList<>();
        incidentList = new ArrayList<>();
        incidentDataList = new ArrayList<>();

        incident = new Incident();

        //   {
    }

    @PostConstruct
    public void init()
    {
        allResidenceList = residenceFacade.findAll();
        incidentList = incidentFacade.findAll();
        studentList = studentFacade.findAll();

        for (Residence residence : residenceFacade.findAll())
        {
            residenceList.add(residence.getResName());
        }

        for (Category category : categoryFacade.findAll())
        {
            categoryList.add(category.getCategoryName());
        }

        FacesContext context2 = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

        String studentNo = (String) session.getAttribute("student");

        if (studentNo != null)
        {
            student = studentFacade.find(studentNo);
            student.setResidence("");
            student.setBlock("");
            student.setPhone("");
            student.setRoom("");
        }

        incidentDataList.clear();

        for (Incident myIncident : incidentList)
        {
            if (myIncident.getStudentNo().getStudentNo().
                    equalsIgnoreCase(studentNo))
            {
                incidentDataList.add(myIncident);
                retrievedStudent = myIncident.getStudentNo();
            }
        }

    }

    public List<String> getResidenceList()
    {
        return residenceList;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Student getRetrievedStudent()
    {
        return retrievedStudent;
    }

    public void setRetrievedStudent(Student retrievedStudent)
    {
        this.retrievedStudent = retrievedStudent;
    }

    public List<Incident> getIncidentDataList()
    {
        return incidentDataList;
    }

    public void setIncidentDataList(List<Incident> incidentDataList)
    {
        this.incidentDataList = incidentDataList;
    }

    public List<String> getCategoryList()
    {
        return categoryList;
    }

    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }

    public void setBlockRequired(boolean blockRequired)
    {
        this.blockRequired = blockRequired;
    }

    public List<String> getAvailableTimes()
    {
        return availableTimes;
    }

    public void setSearchInput(String searchInput)
    {
        this.searchInput = searchInput;
    }

    public String getSearchInput()
    {
        return searchInput;
    }

    public void setAvailableTimes(List<String> availableTimes)
    {
        this.availableTimes = availableTimes;
    }

    public boolean getBlockRequired()
    {
        return blockRequired;
    }

    public Incident getIncident()
    {
        return incident;
    }

    public void setIncident(Incident incident)
    {
        this.incident = incident;
    }

    private boolean checkStudentNo(String stNum)
    {
        boolean value = false;
        for (Student objStudent : studentList)
        {
            if (objStudent.getStudentNo().equalsIgnoreCase(stNum))
            {
                value = true;
            }
        }
        return value;
    }

    public void reset()
    {
        searchInput = "";
        incidentDataList.clear();

//        newIncidentRender = false;
        blockRequired = false;

        student = new Student();
        incident = new Incident();

    }

    public void onStatusChange()
    {
        incidentDataList.clear();

        if (status.equalsIgnoreCase("All"))
        {
            for (Incident myIncident : incidentList)
            {
                if (myIncident.getStudentNo().getStudentNo().
                        equalsIgnoreCase(student.getStudentNo()))
                {
                    incidentDataList.add(myIncident);
                    incident = myIncident;
                }
            }
        } else
        {
            for (Incident myIncident : incidentList)
            {
                if (myIncident.getStudentNo().getStudentNo().
                        equalsIgnoreCase(student.getStudentNo())
                        && myIncident.getStatus().equalsIgnoreCase(status))
                {
                    incidentDataList.add(myIncident);
                    incident = myIncident;
                }
            }
        }
    }

    public void onResidenceChange()
    {

        for (Residence myResidence : allResidenceList)
        {
            if (myResidence.getResName().equalsIgnoreCase(student.getResidence()))
            {
                if (myResidence.getRequireBlock().equalsIgnoreCase("No"))
                {
                    blockRequired = false;
                } else if (myResidence.getRequireBlock().equalsIgnoreCase("Yes"))
                {
                    blockRequired = true;
                }
            }
        }
    }

    public String isblockRequired(String residenceName)
    {
        String value = "";
        for (Residence myResidence : allResidenceList)
        {
            if (myResidence.getResName().equalsIgnoreCase(residenceName))
            {
                value = myResidence.getRequireBlock();
            }
        }

        return value;
    }

    public void btnAddRequest()
    {

        if (student.getInitials().isEmpty())
        {
            msg.error("Please Enter Initials");
        } else if (!isAlpha(student.getInitials()))
        {
            msg.error("Initials must be alphabets only");
        } else if (student.getSurname().isEmpty())
        {
            msg.error("Please Enter Surname");
        } else if (student.getSurname().length() < 3)
        {
            msg.error("Surname must be atleast be 3 characters");
        } else if (!isAlpha(student.getSurname()))
        {
            msg.error("Surname must be alphabets only");
        } else if (student.getPhone().isEmpty())
        {
            msg.error("Please Enter Phone No");
        } else if (!student.getPhone().substring(1, 2).equals("0"))
        {
            msg.error("Phone No must start with 0");
        } else if (student.getResidence().isEmpty())
        {
            msg.error("Please Select Residence Name");
        } else if (residenceFacade.find(student.getResidence()).getRequireBlock().
                equalsIgnoreCase("Yes") && student.getBlock().isEmpty())
        {
            msg.error("Selected Residence Name requires a block number and is not supplied");
        } else if (student.getRoom().isEmpty())
        {
            msg.error("Please Enter Room Number");
        } else if (availableTimes.isEmpty())
        {
            msg.error("Please Select Available Times");
        } else if (incident.getCategory().isEmpty())
        {
            msg.error("Please Select Category");
        } else if (incident.getDescription().isEmpty())
        {
            msg.error("Please Enter description of the Incident");
        } else if (incident.getDescription().length() < 10)
        {
            msg.error("Description of the incident is too short");
        } else
        {

            if (!checkStudentNo(student.getStudentNo()))
            {
                //save                
                studentFacade.create(student);
            } else
            {
                //edit
                studentFacade.edit(student);
            }

            String availability = "";

            for (String str : availableTimes)
            {
                availability += str + ",";
            }
            availableTimes.clear();

            incident.setAvailability(availability);
            incident.setStatus("New");
            incident.setDateLogged(today);
            incident.setStudentNo(student);
            incident.setTimeLogged(time);

            incidentFacade.create(incident);

            student.setResidence("");
            student.setBlock("");
            student.setPhone("");
            student.setRoom("");

            incident = new Incident();

            incidentList = incidentFacade.findAll();
            
            incidentDataList.clear();
            for (Incident myIncident : incidentList)
            {
                if (myIncident.getStudentNo().getStudentNo().
                        equalsIgnoreCase(student.getStudentNo()))
                {
                    incidentDataList.add(myIncident);
                    retrievedStudent = myIncident.getStudentNo();
                }
            }

            msg.info("Incident Logged Successfully, Incident No: " + incident.getIncidentID());

        }

    }

    public String getAttendent(Incident objIncident)
    {
        String name = "";
        if (objIncident.getAttendent() == null)
        {
            name = "UnAssigned";
        } else
        {
            name = objIncident.getAttendent().getSurname()
                    + " " + objIncident.getAttendent().getName().substring(0, 1).
                    toUpperCase();
        }
        return name;
    }

    public boolean isAlpha(String name)
    {
        return name.matches("[a-zA-Z]+");
    }

    public String daysBetween(Date one)
    {

        long difference = (one.getTime() - today.getTime()) / 86400000;

        String number = "";

        if (Math.abs(difference) <= 1)
        {
            number = Math.abs(difference) + " Day";
        } else
        {
            number = Math.abs(difference) + " Days";
        }

        return number;
    }

    public String formatDate(String myDate)
    {
        String year = myDate.substring(24);
        String month = myDate.substring(4, 8);
        String day = myDate.substring(8, 11);
        return day + "-" + month + "-" + year;
    }
}
