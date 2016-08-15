package za.ac.vut.Entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.ac.vut.Entity.Incident;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-04T10:43:54")
@StaticMetamodel(Student.class)
public class Student_ { 

    public static volatile SingularAttribute<Student, String> studentNo;
    public static volatile SingularAttribute<Student, String> phone;
    public static volatile SingularAttribute<Student, String> residence;
    public static volatile SingularAttribute<Student, String> initials;
    public static volatile CollectionAttribute<Student, Incident> incidentCollection;
    public static volatile SingularAttribute<Student, String> surname;
    public static volatile SingularAttribute<Student, String> block;
    public static volatile SingularAttribute<Student, String> room;

}