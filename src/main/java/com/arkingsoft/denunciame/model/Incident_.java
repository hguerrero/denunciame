package com.arkingsoft.denunciame.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-05-04T14:50:07.239-0500")
@StaticMetamodel(Incident.class)
public class Incident_ {
	public static volatile SingularAttribute<Incident, Long> id;
	public static volatile SingularAttribute<Incident, Type> type;
	public static volatile SingularAttribute<Incident, Location> location;
	public static volatile SingularAttribute<Incident, Date> dateReported;
	public static volatile SingularAttribute<Incident, Date> dateIncident;
	public static volatile SingularAttribute<Incident, String> description;
}
