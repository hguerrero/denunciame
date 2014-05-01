package com.finders.api;


import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/application")
@ApplicationScoped
public class ApplicationBean extends Application {

}