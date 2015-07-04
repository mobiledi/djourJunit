package com.mobiledi.djour;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import com.mobiledi.djourDAO.DjourAppDAO;


@ApplicationPath("/api")
public class RestApplication extends javax.ws.rs.core.Application {
  Set<Object> singletons;

  @Override
  public Set<Class<?>> getClasses() {
    HashSet<Class<?>> clazzes = new HashSet<>();
    clazzes.add(DjourAppDAO.class);
    return clazzes;
  }

  @Override
  public Set<Object> getSingletons() {
    if (singletons == null) {
      CorsFilter corsFilter = new CorsFilter();
      corsFilter.getAllowedOrigins().add("*");

      singletons = new HashSet<>();
      singletons.add(corsFilter);
    }
    return singletons;
  }
}