package za.ac.vut.Entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.ac.vut.Entity.Incident;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-04T10:43:54")
@StaticMetamodel(Staff.class)
public class Staff_ { 

    public static volatile SingularAttribute<Staff, String> forcePassword;
    public static volatile SingularAttribute<Staff, String> staffNo;
    public static volatile SingularAttribute<Staff, String> email;
    public static volatile SingularAttribute<Staff, String> name;
    public static volatile CollectionAttribute<Staff, Incident> incidentCollection;
    public static volatile SingularAttribute<Staff, String> surname;
    public static volatile SingularAttribute<Staff, String> type;
    public static volatile SingularAttribute<Staff, String> password;
    public static volatile SingularAttribute<Staff, String> ext;
    public static volatile SingularAttribute<Staff, String> room;

}