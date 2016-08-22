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
import org.primefaces.model.chart.*;
import za.ac.vut.Entity.*;
import za.ac.vut.Facade.*;
import za.ac.vut.util.*;
import za.ac.vut.util.Redirect;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped
public class Dashboard
{
    
    @EJB
    private IncidentFacade incidentFacade;
    
    @EJB
    private CategoryFacade categoryFacade;
    
    @EJB
    private ResidenceFacade residenceFacade;
    
    @EJB
    private StudentFacade studentFacade;
    
    @EJB
    private StaffFacade staffFacade;
    
    private List<Incident> incidentList, reportIncidentList;
    private List<Category> categoryList;
    private List<Residence> residenceList;
    private List<Staff> staffList;
    private List<Student> studentList;
    
    private BarChartModel chartModel, chartModel2;
    private PieChartModel pieModel1;
    private Redirect rd;
    private Msg msg;
    
    private String studentNo, status, technicianNo, categoryName, residenceName;
    
    @PostConstruct
    public void init()
    {
        reportIncidentList = incidentFacade.findAll();
        incidentList = incidentFacade.findAll();
        categoryList = categoryFacade.findAll();
        residenceList = residenceFacade.findAll();
        studentList = studentFacade.findAll();
        staffList = staffFacade.findAll();
        
        createAnimatedModel();
        createAnimatedModel2();
        createPieModels();
    }
    
    public Dashboard()
    {
        msg = new Msg();
        incidentList = new ArrayList<>();
        categoryList = new ArrayList<>();
        residenceList = new ArrayList<>();
        staffList = new ArrayList<>();
        studentList = new ArrayList<>();
        
        rd = new Redirect();
    }
    
    public List<Incident> getReportIncidentList()
    {
        return reportIncidentList;
    }
    
    public String getStudentNo()
    {
        return studentNo;
    }
    
    public void setTechnicianNo(String technicianNo)
    {
        this.technicianNo = technicianNo;
    }
    
    public String getTechnicianNo()
    {
        return technicianNo;
    }
    
    public String getResidenceName()
    {
        return residenceName;
    }
    
    public void setResidenceName(String residenceName)
    {
        this.residenceName = residenceName;
    }
    
    public void setStudentNo(String studentNo)
    {
        this.studentNo = studentNo;
    }
    
    public String getCategoryName()
    {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }
    
    public void setReportIncidentList(List<Incident> reportIncidentList)
    {
        this.reportIncidentList = reportIncidentList;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void showDashBoard()
    {
        reportIncidentList = incidentFacade.findAll();
        rd.dashBoard();
    }
    
    public PieChartModel getPieModel1()
    {
        return pieModel1;
    }
    
    public void setPieModel1(PieChartModel pieModel1)
    {
        this.pieModel1 = pieModel1;
    }
    
    private void createPieModels()
    {
        createPieModel1();
        
    }
    
    private void createPieModel1()
    {
        pieModel1 = new PieChartModel();
        
        double openValue = 0, newValue = 0, closeValue = 0, onHoldValue = 0;
        
        for (Incident incident : incidentList)
        {
            
            if (incident.getStatus().equalsIgnoreCase("New"))
            {
                newValue++;
            } else if (incident.getStatus().equalsIgnoreCase("Open"))
            {
                openValue++;
            } else if (incident.getStatus().equalsIgnoreCase("Close"))
            {
                closeValue++;
            } else if (incident.getStatus().equalsIgnoreCase("On Hold"))
            {
                onHoldValue++;
            }
            
        }
        
        if (onHoldValue > 0)
        {
            pieModel1.set("On Hold", onHoldValue);
        }
        
        if (newValue > 0)
        {
            pieModel1.set("New", newValue);
        }
        
        if (newValue > 0)
        {
            pieModel1.set("Open", openValue);
        }
        
        if (closeValue > 0)
        {
            pieModel1.set("Close", closeValue);
        }
        
        pieModel1.setTitle("Vaal University of Technology: Incidents");
        pieModel1.setLegendPosition("w");
    }
    
    public void setChartModel(BarChartModel chartModel)
    {
        this.chartModel = chartModel;
    }
    
    public void setChartModel2(BarChartModel chartModel2)
    {
        this.chartModel2 = chartModel2;
    }
    
    public BarChartModel getChartModel2()
    {
        return chartModel2;
    }
    
    public BarChartModel getChartModel()
    {
        return chartModel;
    }
    
    public void createAnimatedModel()
    {
        
        chartModel = initBarModel();
        chartModel.setTitle("Vaal University of Technology: Categories Incidents");
        chartModel.setAnimate(true);
        chartModel.setLegendPosition("ne");
        Axis yAxis = chartModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(100);
        
    }
    
    public void createAnimatedModel2()
    {
        
        chartModel2 = initBarModel2();
        chartModel2.setTitle("Vaal University of Technology: Residence Incidents");
        chartModel2.setAnimate(true);
        chartModel2.setLegendPosition("ne");
        Axis yAxis = chartModel2.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(100);
        
    }
    
    private int getCategoryTotal(String category)
    {
        int total = 0;
        for (Incident incident : incidentList)
        {
            if (incident.getCategory().equalsIgnoreCase(category))
            {
                total++;
            }
        }
        return total;
    }
    
    private int getResidenceTotal(String residence)
    {
        int total = 0;
        for (Incident incident : incidentList)
        {
            if (incident.getStudentNo().getResidence().equalsIgnoreCase(residence))
            {
                total++;
            }
        }
        return total;
    }
    
    private int getNewCategoryValue(String Cat)
    {
        int total = 0;
        for (Incident incident : incidentList)
        {
            if (incident.getCategory().equalsIgnoreCase(Cat)
                    && incident.getStatus().equalsIgnoreCase("New"))
            {
                total++;
            }
        }
        return total;
    }
    
    private int getNewResidenceValue(String residence)
    {
        int total = 0;
        for (Incident incident : incidentList)
        {
            if (incident.getStudentNo().getResidence().equalsIgnoreCase(residence)
                    && incident.getStatus().equalsIgnoreCase("New"))
            {
                total++;
            }
        }
        return total;
    }
    
    private int getOpenCategoryValue(String category)
    {
        int total = 0;
        for (Incident incident : incidentList)
        {
            if (incident.getCategory().equalsIgnoreCase(category)
                    && incident.getStatus().equalsIgnoreCase("Open"))
            {
                total++;
            }
        }
        return total;
    }
    
    private int getOpenResidenceValue(String residence)
    {
        int total = 0;
        for (Incident incident : incidentList)
        {
            if (incident.getStudentNo().getResidence().equalsIgnoreCase(residence)
                    && incident.getStatus().equalsIgnoreCase("Open"))
            {
                total++;
            }
        }
        return total;
    }
    
    private int getCloseCategoryValue(String category)
    {
        int total = 0;
        
        for (Incident incident : incidentList)
        {
            if (incident.getCategory().equalsIgnoreCase(category)
                    && incident.getStatus().equalsIgnoreCase("Close"))
            {
                total++;
            }
        }
        
        return total;
    }
    
    private int getCloseResidenceValue(String residence)
    {
        int total = 0;
        
        for (Incident incident : incidentList)
        {
            if (incident.getStudentNo().getResidence().equalsIgnoreCase(residence)
                    && incident.getStatus().equalsIgnoreCase("Close"))
            {
                total++;
            }
        }
        
        return total;
    }
    
    private int getOnHoldCategoryValue(String category)
    {
        int total = 0;
        
        for (Incident incident : incidentList)
        {
            if (incident.getCategory().equalsIgnoreCase(category)
                    && incident.getStatus().equalsIgnoreCase("On Hold"))
            {
                total++;
            }
        }
        
        return total;
    }
    
    private int getOnHoldResidenceValue(String residence)
    {
        int total = 0;
        
        for (Incident incident : incidentList)
        {
            if (incident.getStudentNo().getResidence().equalsIgnoreCase(residence)
                    && incident.getStatus().equalsIgnoreCase("On Hold"))
            {
                total++;
            }
        }
        
        return total;
    }
    
    private BarChartModel initBarModel()
    {
        
        BarChartModel model = new BarChartModel();
        
        ChartSeries newIncident = new ChartSeries();
        ChartSeries openIncident = new ChartSeries();
        ChartSeries closedIncidents = new ChartSeries();
        ChartSeries onHoldIncident = new ChartSeries();
        
        newIncident.setLabel("New Incidents");
        openIncident.setLabel("Open Incidents");
        closedIncidents.setLabel("Close Incidents");
        onHoldIncident.setLabel("On Hold Incidents");
        
        for (Category category : categoryList)
        {
            double newValue = 0.0;
            double openValue = 0.0;
            double closeValue = 0.0;
            double onHoldValue = 0.0;
            
            int newNumber = getNewCategoryValue(category.getCategoryName());
            int openNumber = getOpenCategoryValue(category.getCategoryName());
            int closeNumber = getCloseCategoryValue(category.getCategoryName());
            int onHoldNumber = getOnHoldCategoryValue(category.getCategoryName());
            
            int total = getCategoryTotal(category.getCategoryName());
            
            if (newNumber > 0)
            {
                newValue = (newNumber * 100) / total;
            }
            
            if (openNumber > 0)
            {
                openValue = (openNumber * 100) / total;
            }
            
            if (closeNumber > 0)
            {
                closeValue = (closeNumber * 100) / total;
            }
            
            if (onHoldNumber > 0)
            {
                onHoldValue = (onHoldNumber * 100) / total;
            }
            
            newIncident.set(category.getCategoryName(), newValue);
            openIncident.set(category.getCategoryName(), openValue);
            closedIncidents.set(category.getCategoryName(), closeValue);
            onHoldIncident.set(category.getCategoryName(), onHoldValue);
            
        }
        
        model.addSeries(newIncident);
        model.addSeries(openIncident);
        model.addSeries(closedIncidents);
        model.addSeries(onHoldIncident);
        
        return model;
    }
    
    private BarChartModel initBarModel2()
    {
        
        BarChartModel model = new BarChartModel();
        
        ChartSeries newIncident = new ChartSeries();
        ChartSeries openIncident = new ChartSeries();
        ChartSeries closedIncidents = new ChartSeries();
        ChartSeries onHoldIncident = new ChartSeries();
        
        newIncident.setLabel("New Incidents");
        openIncident.setLabel("Open Incidents");
        closedIncidents.setLabel("Close Incidents");
        onHoldIncident.setLabel("On Hold Incidents");
        
        for (Residence residence : residenceList)
        {
            double newValue = 0.0;
            double openValue = 0.0;
            double closeValue = 0.0;
            double onHoldValue = 0.0;
            
            int newNumber = getNewResidenceValue(residence.getResName());
            int openNumber = getOpenResidenceValue(residence.getResName());
            int closeNumber = getCloseResidenceValue(residence.getResName());
            int onHoldNumber = getOnHoldResidenceValue(residence.getResName());
            
            int total = getResidenceTotal(residence.getResName());
            
            if (newNumber > 0)
            {
                newValue = (newNumber * 100) / total;
            }
            
            if (openNumber > 0)
            {
                openValue = (openNumber * 100) / total;
            }
            
            if (closeNumber > 0)
            {
                closeValue = (closeNumber * 100) / total;
            }
            
            if (onHoldNumber > 0)
            {
                onHoldValue = (onHoldNumber * 100) / total;
            }
            
            newIncident.set(residence.getResName(), newValue);
            openIncident.set(residence.getResName(), openValue);
            closedIncidents.set(residence.getResName(), closeValue);
            onHoldIncident.set(residence.getResName(), onHoldValue);
            
        }
        
        model.addSeries(newIncident);
        model.addSeries(openIncident);
        model.addSeries(closedIncidents);
        model.addSeries(onHoldIncident);
        
        return model;
    }
    
    public void filterReport()
    {
        
        int stNum = 0, techNum = 0;
        
        if (!studentNo.isEmpty())
        {
            try
            {
                stNum = Integer.parseInt(studentNo);
                
                if (!searchStudent(stNum))
                {
                    msg.error("Invalid Student Number");
                    return;
                }
            } catch (NumberFormatException es)
            {
                msg.error("Student number must be numbers only");
                return;
            }
        }
        
        if (!technicianNo.isEmpty())
        {
            try
            {
                techNum = Integer.parseInt(technicianNo);
                if (!searchStaff(techNum))
                {
                    msg.error("Invalid Technician Number");
                    return;
                }
            } catch (NumberFormatException es)
            {
                msg.error("Technician number must be numbers only");
                return;
            }
        }
        
        reportIncidentList.clear();
        for (Incident incident : incidentList)
        {
            if (studentNo.isEmpty()
                    && status == null
                    && technicianNo.isEmpty()
                    && categoryName == null
                    && residenceName == null)
            
            {
                reportIncidentList.add(incident);
            } else if (!studentNo.isEmpty()
                    && status == null
                    && technicianNo.isEmpty()
                    && categoryName == null
                    && residenceName == null)
            {
                if (incident.getStudentNo().getStudentNo().equalsIgnoreCase(studentNo))
                {
                    reportIncidentList.add(incident);
                }
            } else if (!studentNo.isEmpty()
                    && status == null
                    && technicianNo.isEmpty()
                    && categoryName != null
                    && residenceName == null)
            {
                if (incident.getStudentNo().getStudentNo().equalsIgnoreCase(studentNo)
                        && incident.getCategory().equalsIgnoreCase(categoryName))
                {
                    reportIncidentList.add(incident);
                }
            } else if (!studentNo.isEmpty()
                    && status == null
                    && technicianNo.isEmpty()
                    && categoryName == null
                    && residenceName != null)
            {
                if (incident.getStudentNo().getStudentNo().equalsIgnoreCase(studentNo)
                        && incident.getStudentNo().getResidence().equalsIgnoreCase(residenceName))
                {
                    reportIncidentList.add(incident);
                }
            } else if (!studentNo.isEmpty()
                    && status == null
                    && !technicianNo.isEmpty()
                    && categoryName == null
                    && residenceName == null)
            {
                if (incident.getAttendent() != null)
                {
                    if (incident.getStudentNo().getStudentNo().equalsIgnoreCase(studentNo)
                            && incident.getAttendent().getStaffNo().equalsIgnoreCase(technicianNo))
                    {
                        reportIncidentList.add(incident);
                    }
                }
            } else if (!studentNo.isEmpty()
                    && status != null
                    && technicianNo.isEmpty()
                    && categoryName == null
                    && residenceName == null)
            {
                if (incident.getStudentNo().getStudentNo().equalsIgnoreCase(studentNo)
                        && incident.getStatus().equalsIgnoreCase(status))
                {
                    reportIncidentList.add(incident);
                }
            } else if (!studentNo.isEmpty()
                    && status != null
                    && !technicianNo.isEmpty()
                    && categoryName == null
                    && residenceName == null)
            {
                if (incident.getAttendent() != null)
                {
                    if (incident.getStudentNo().getStudentNo().equalsIgnoreCase(studentNo)
                            && incident.getStatus().equalsIgnoreCase(status)
                            && incident.getAttendent().getStaffNo().equalsIgnoreCase(technicianNo))
                    {
                        reportIncidentList.add(incident);
                    }
                }
            } else if (!studentNo.isEmpty()
                    && status != null
                    && !technicianNo.isEmpty()
                    && categoryName != null
                    && residenceName == null)
            {
                if (incident.getAttendent() != null)
                {
                    if (incident.getStudentNo().getStudentNo().equalsIgnoreCase(studentNo)
                            && incident.getStatus().equalsIgnoreCase(status)
                            && incident.getAttendent().getStaffNo().equalsIgnoreCase(technicianNo)
                            && incident.getCategory().equalsIgnoreCase(categoryName))
                    {
                        reportIncidentList.add(incident);
                    }
                }
            } else if (!studentNo.isEmpty()
                    && status != null
                    && !technicianNo.isEmpty()
                    && categoryName != null
                    && residenceName != null)
            {
                if (incident.getStudentNo().getStudentNo().equalsIgnoreCase(studentNo)
                        && incident.getStatus().equalsIgnoreCase(status)
                        && incident.getAttendent().getStaffNo().equalsIgnoreCase(technicianNo)
                        && incident.getCategory().equalsIgnoreCase(categoryName)
                        && incident.getStudentNo().getResidence().equalsIgnoreCase(residenceName))
                {
                    reportIncidentList.add(incident);
                }
            } ///search from status
            else if (studentNo.isEmpty()
                    && status != null
                    && technicianNo.isEmpty()
                    && categoryName == null
                    && residenceName == null)
            {
                if (incident.getStatus().equalsIgnoreCase(status))
                {
                    reportIncidentList.add(incident);
                }
            } else if (studentNo.isEmpty()
                    && status != null
                    && technicianNo.isEmpty()
                    && categoryName != null
                    && residenceName == null)
            {
                if (incident.getStatus().equalsIgnoreCase(status)
                        && incident.getCategory().equalsIgnoreCase(categoryName))
                {
                    reportIncidentList.add(incident);
                }
            } else if (studentNo.isEmpty()
                    && status != null
                    && technicianNo.isEmpty()
                    && categoryName == null
                    && residenceName != null)
            {
                if (incident.getStatus().equalsIgnoreCase(status)
                        && incident.getStudentNo().getResidence().equalsIgnoreCase(residenceName))
                {
                    reportIncidentList.add(incident);
                }
            } else if (studentNo.isEmpty()
                    && status != null
                    && !technicianNo.isEmpty()
                    && categoryName == null
                    && residenceName == null)
            {
                if (incident.getAttendent() != null)
                {
                    if (incident.getStatus().equalsIgnoreCase(status)
                            && incident.getAttendent().getStaffNo().equalsIgnoreCase(technicianNo))
                    {
                        reportIncidentList.add(incident);
                    }
                }
            } else if (studentNo.isEmpty()
                    && status != null
                    && !technicianNo.isEmpty()
                    && categoryName != null
                    && residenceName == null)
            {
                if (incident.getAttendent() != null)
                {
                    if (incident.getStatus().equalsIgnoreCase(status)
                            && incident.getAttendent().getStaffNo().equalsIgnoreCase(technicianNo)
                            && incident.getCategory().equalsIgnoreCase(categoryName))
                    {
                        reportIncidentList.add(incident);
                    }
                }
            } else if (studentNo.isEmpty()
                    && status != null
                    && !technicianNo.isEmpty()
                    && categoryName == null
                    && residenceName != null)
            {
                if (incident.getAttendent() != null)
                {
                    if (incident.getStatus().equalsIgnoreCase(status)
                            && incident.getAttendent().getStaffNo().equalsIgnoreCase(technicianNo)
                            && incident.getStudentNo().getResidence()
                            .equalsIgnoreCase(residenceName))
                    {
                        reportIncidentList.add(incident);
                    }
                }
            } else if (studentNo.isEmpty()
                    && status != null
                    && technicianNo.isEmpty()
                    && categoryName != null
                    && residenceName == null)
            {
                if (incident.getStatus().equalsIgnoreCase(status)
                        && incident.getCategory().equalsIgnoreCase(categoryName))
                {
                    reportIncidentList.add(incident);
                }
            } else if (studentNo.isEmpty()
                    && status != null
                    && !technicianNo.isEmpty()
                    && categoryName != null
                    && residenceName != null)
            {
                if (incident.getAttendent() != null)
                {
                    if (incident.getStatus().equalsIgnoreCase(status)
                            && incident.getAttendent().getStaffNo().equalsIgnoreCase(technicianNo)
                            && incident.getCategory().equalsIgnoreCase(categoryName)
                            && incident.getStudentNo().getResidence().equalsIgnoreCase(residenceName))
                    {
                        reportIncidentList.add(incident);
                    }
                }
            } ///search from technician
            else if (studentNo.isEmpty()
                    && status == null
                    && !technicianNo.isEmpty()
                    && categoryName == null
                    && residenceName == null)
            {
                if (incident.getAttendent() != null)
                {
                    if (incident.getAttendent() != null)
                    {
                        if (incident.getAttendent().getStaffNo().equalsIgnoreCase(technicianNo))
                        {
                            reportIncidentList.add(incident);
                        }
                    }
                }
            } else if (studentNo.isEmpty()
                    && status == null
                    && !technicianNo.isEmpty()
                    && categoryName != null
                    && residenceName == null)
            {
                if (incident.getAttendent() != null)
                {
                    if (incident.getAttendent().getStaffNo().equalsIgnoreCase(technicianNo)
                            && incident.getCategory().equalsIgnoreCase(categoryName))
                    {
                        reportIncidentList.add(incident);
                    }
                }
            } else if (studentNo.isEmpty()
                    && status == null
                    && !technicianNo.isEmpty()
                    && categoryName != null
                    && residenceName != null)
            {
                if (incident.getAttendent() != null)
                {
                    if (incident.getAttendent().getStaffNo().equalsIgnoreCase(technicianNo)
                            && incident.getCategory().equalsIgnoreCase(categoryName)
                            && incident.getStudentNo().getResidence().equalsIgnoreCase(residenceName))
                    {
                        reportIncidentList.add(incident);
                    }
                }
            } //searh from category
            else if (studentNo.isEmpty()
                    && status == null
                    && technicianNo.isEmpty()
                    && categoryName != null
                    && residenceName == null)
            {
                if (incident.getCategory().equalsIgnoreCase(categoryName))
                {
                    reportIncidentList.add(incident);
                }
            } else if (studentNo.isEmpty()
                    && status == null
                    && technicianNo.isEmpty()
                    && categoryName != null
                    && residenceName != null)
            {
                if (incident.getCategory().equalsIgnoreCase(categoryName)
                        && incident.getStudentNo().getResidence().equalsIgnoreCase(residenceName))
                {
                    reportIncidentList.add(incident);
                }
            } else if (studentNo.isEmpty()
                    && status == null
                    && technicianNo.isEmpty()
                    && categoryName == null
                    && residenceName != null)
            {
                if (incident.getStudentNo().getResidence().equalsIgnoreCase(residenceName))
                {
                    reportIncidentList.add(incident);
                }
            }
        }
        
    }
    
    private boolean searchStudent(int stNo)
    {
        boolean value = false;
        for (Student student : studentList)
        {
            if (student.getStudentNo().equalsIgnoreCase("" + stNo))
            {
                value = true;
            }
        }
        return value;
    }
    
    private boolean searchStaff(int staffNo)
    {
        boolean value = false;
        for (Staff staff : staffList)
        {
            if (staff.getStaffNo().equalsIgnoreCase("" + staffNo))
            {
                value = true;
            }
        }
        return value;
    }
}
