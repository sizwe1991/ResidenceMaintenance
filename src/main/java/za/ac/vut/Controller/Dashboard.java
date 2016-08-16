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

    private List<Incident> incidentList;
    private List<Category> categoryList;
    private List<Residence> residenceList;

    private BarChartModel chartModel, chartModel2;
    private PieChartModel pieModel1;

    @PostConstruct
    public void init()
    {
        incidentList = incidentFacade.findAll();
        categoryList = categoryFacade.findAll();
        residenceList = residenceFacade.findAll();

        createAnimatedModel();
        createAnimatedModel2();
        createPieModels();
    }

    public Dashboard()
    {
        incidentList = new ArrayList<>();
        categoryList = new ArrayList<>();
        residenceList = new ArrayList<>();
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

}
