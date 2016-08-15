package za.ac.vut.Entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.ac.vut.Entity.Staff;
import za.ac.vut.Entity.Student;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-04T10:43:54")
@StaticMetamodel(Incident.class)
public class Incident_ { 

    public static volatile SingularAttribute<Incident, String> category;
    public static volatile SingularAttribute<Incident, Student> studentNo;
    public static volatile SingularAttribute<Incident, String> status;
    public static volatile SingularAttribute<Incident, String> description;
    public static volatile SingularAttribute<Incident, String> timeLogged;
    public static volatile SingularAttribute<Incident, Date> dateResolved;
    public static volatile SingularAttribute<Incident, Staff> attendent;
    public static volatile SingularAttribute<Incident, Date> dateLogged;
    public static volatile SingularAttribute<Incident, String> comment;
    public static volatile SingularAttribute<Incident, Integer> incidentID;
    public static volatile SingularAttribute<Incident, String> availability;

}