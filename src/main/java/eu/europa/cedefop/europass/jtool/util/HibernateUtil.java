package eu.europa.cedefop.europass.jtool.util;

   /*
		* Copyright European Union 2002-2010
		*
		*
		* Licensed under the EUPL, Version 1.1 or – as soon they
		* will be approved by the European Commission - subsequent
		* versions of the EUPL (the "Licence");
		* You may not use this work except in compliance with the
		* Licence.
		* You may obtain a copy of the Licence at:
		*
		* http://ec.europa.eu/idabc/eupl.html
		*
		*
		* Unless required by applicable law or agreed to in
		* writing, software distributed under the Licence is
		* distributed on an "AS IS" basis,
		* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
		* express or implied.
		* See the Licence for the specific language governing
		* permissions and limitations under the Licence.
		*
		*/

import java.io.File;

import java.net.URISyntaxException;

import java.util.Properties;

import org.hibernate.*;
import org.hibernate.cfg.*;

 /**
  * This class contains all the necessary methods to connect with databases using hibernate.  
  * @author Gomosidis Apostolos, Quality & Reliability S.A.
  * @version %I%, %G%
  * @since 1.0
  */
public class HibernateUtil  {  
  
  public static final SessionFactory sessionFactory;
  private static Configuration cfg=null;
  private static SessionFactory hibfactory=null;
  static {
    try {
      Configuration config = new Configuration();
      config.setNamingStrategy(InstoreNamingStrategy.INSTANCE);
      config = config.configure(new File(HibernateUtil.class.getClassLoader().getResource("hibernate.cfg.xml").toURI()));
      config.setProperties(readConfigProperties(config.getProperties()));
      sessionFactory = config.buildSessionFactory();
    } catch ( HibernateException ex ) {
      System.out.println("Config problem: "+ex);
      throw new RuntimeException("Config problem", ex);
    } catch ( URISyntaxException ex ) {
      System.out.println("Config problem (mappings file uri): "+ex);
      throw new RuntimeException("Config problem (mappings file uri):", ex);
    }
  }

  public static final ThreadLocal session = new ThreadLocal();
    /**
     * Return the current hibernate session or open a new one.
     * @return the current hibernate session
     * @throws HibernateException
     */
  public static Session currentSession() throws HibernateException {    
    Session s = getHibSession();
    if ( s == null ) {
      System.out.println("Session not found");
      s = sessionFactory.openSession();
      session.set(s);
    } else {
        System.out.println("Session found");
    }
    return s;
  }  

/**
     * Close the hibernate session
     * @throws HibernateException
     */
  public static void closeSession() throws HibernateException {
    Session s = (Session) session.get();
    session.set( null );
    if ( s!= null ) { 
      System.out.println("closing session");
      s.close();
    } else {
      System.out.println("session not found");
    }
  }
  
    /**
     * Initializing the hibernate configuration.
     * @param sqlDialect the hibernate sql dialect (SQL server, MySQL, Oracle)
     */
    public static void initialize(String sqlDialect) {
        try {
            cfg = new Configuration();        
            cfg.configure(new File(HibernateUtil.class.getClassLoader().getResource("hibernate.cfg.xml").toURI()));                                                       
            Properties props = cfg.getProperties();   
            
            cfg.setProperties(readConfigProperties(props));
            hibfactory = cfg.buildSessionFactory();
        } catch (Throwable ex) {
            System.out.println("Error initializing connection: "+ex);
        }
    }
    
    /**
     * Returns the hibernate session using the configuration property file.
     * @return the hibernate session
     */
    public static Session getHibSession() {
        String defaultDatabase = SoftToolUtil.getProperty("default_database");
        if (defaultDatabase==null) return null;
        initialize(defaultDatabase);
        if ( hibfactory != null ) {
            return hibfactory.openSession();
        } else {
            return null;
        }        
    }
    
    private static Properties readConfigProperties( Properties props ){
    	if ( props == null ){
    		props = new Properties();
    	}
    	props.setProperty("hibernate.connection.url",SoftToolUtil.getProperty("url")); 
        props.setProperty("hibernate.connection.databasename",SoftToolUtil.getProperty("databasename")); 
        props.setProperty("hibernate.connection.username",SoftToolUtil.getProperty("username")); 
        props.setProperty("hibernate.connection.password",SoftToolUtil.getProperty("password")); 
        String defaultDatabase = SoftToolUtil.getProperty("default_database");
        if ("1".equals(defaultDatabase)) { // SQL Server
            props.setProperty("hibernate.connection.driver_class","net.sourceforge.jtds.jdbc.Driver");                 
            props.setProperty("hibernate.dialect","org.hibernate.dialect.SQLServerDialect"); 
        } else if ("2".equals(defaultDatabase)) { // MySQL
            props.setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver");
            props.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        } else if ("3".equals(defaultDatabase)) { // Oracle
            props.setProperty("hibernate.connection.driver_class","oracle.jdbc.driver.OracleDriver");
            props.setProperty("hibernate.dialect","org.hibernate.dialect.OracleDialect");
        }
        return props;
    }
}