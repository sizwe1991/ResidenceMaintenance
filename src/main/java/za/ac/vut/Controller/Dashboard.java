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

    private BarChartModel chartModel;

    @PostConstruct
    public void init()
    {
        incidentList = incidentFacade.findAll();
        categoryList = categoryFacade.findAll();
        residenceList = residenceFacade.findAll();

        createAnimatedModel();
    }

    public Dashboard()
    {
        incidentList = new ArrayList<>();
        categoryList = new ArrayList<>();
        residenceList = new ArrayList<>();
    }

    public void setChartModel(BarChartModel chartModel)
    {
        this.chartModel = chartModel;
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

    private int getCategoryTotal(String Cat)
    {
        int total = 0;
        for (Incident incident : incidentList)
        {
            if (incident.getCategory().equalsIgnoreCase(Cat))
            {
                total++;
            }
        }
        return total;
    }

    private int getNewValue(String Cat)
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

    private int getOpenValue(String Cat)
    {
        int total = 0;
        for (Incident incident : incidentList)
        {
            if (incident.getCategory().equalsIgnoreCase(Cat)
                    && incident.getStatus().equalsIgnoreCase("Open"))
            {
                total++;
            }
        }
        return total;
    }

    private int getCloseValue(String Cat)
    {
        int total = 0;

        for (Incident incident : incidentList)
        {
            if (incident.getCategory().equalsIgnoreCase(Cat)
                    && incident.getStatus().equalsIgnoreCase("Close"))
            {
                total++;
            }
        }

        return total;
    }

    private int getOnHoldValue(String Cat)
    {
        int total = 0;

        for (Incident incident : incidentList)
        {
            if (incident.getCategory().equalsIgnoreCase(Cat)
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

            int newNumber = getNewValue(category.getCategoryName());
            int openNumber = getOpenValue(category.getCategoryName());
            int closeNumber = getCloseValue(category.getCategoryName());
            int onHoldNumber = getOnHoldValue(category.getCategoryName());

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

}
