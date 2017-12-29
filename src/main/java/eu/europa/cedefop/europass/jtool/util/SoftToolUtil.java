package eu.europa.cedefop.europass.jtool.util;

/*
*   Copyright European Union 2002-2010
*
*
*   Licensed under the EUPL, Version 1.1 or – as soon they
*   will be approved by the European Commission - subsequent
*   versions of the EUPL (the "Licence");
*   You may not use this work except in compliance with the
*   Licence.
*   You may obtain a copy of the Licence at:
*
*   http://ec.europa.eu/idabc/eupl.html
*
*
*   Unless required by applicable law or agreed to in
*   writing, software distributed under the Licence is
*   distributed on an "AS IS" basis,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
*   express or implied.
*   See the Licence for the specific language governing
*   permissions and limitations under the Licence.
*
*/

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import eu.europa.cedefop.europass.jtool.parse.*;
import org.apache.commons.io.FileUtils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import eu.europa.cedefop.europass.jtool.model.Xml;

import java.net.URISyntaxException;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;


/**
 * This class contains all the necessary methods that needing to parse
 * the PDF/XML file.
 * <p>
 * Also contains all the necessary methods to save the XML data into the users database.
 * @author Eworx S.A.
 * @version %I%, %G%
 * @since 1.0
 */
public class SoftToolUtil {
    private static Properties properties = null;       
    
    public SoftToolUtil() {}
    
    /**
     * This method returns the property value of the configuration file property.
     * @param propertyName the property name of the configuration file
     * @return the value of the property
     */
    public static String getProperty(String propertyName) {
        FileInputStream is = null;
        String propertyValue = "";

        try {
            if ( properties == null) {
                final File p = new File(SoftToolUtil.class.getClassLoader().getResource("softtool.properties").toURI());
                is = new FileInputStream(p);                
                if ( is != null ) {
                    properties=new Properties();
                    properties.load(is);
                }
            }
            propertyValue = properties.getProperty(propertyName);                
        } catch (Throwable ex) {
            System.out.println("Error: "+"(getting property '"+propertyName+"' from config file)"+ex);
        } finally {
            if (is != null) {
                try {is.close();} catch (Exception in_ex) {System.out.println("Error: "+"(getting property '"+propertyName+"' from config file)"+in_ex);}
            }
        }

        return propertyValue;
    }
    
    /**
     * This method is used when the user select a PDF file to open and save the XML data to the database.
     * @param xmlStream a InputStream of the xml
     * @return the logs of the saving
     */
    public static String saveFile(InputStream xmlStream) {
        String log = null;
        try {
            final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            final DocumentBuilder db = dbf.newDocumentBuilder();
            final Document doc = db.parse(xmlStream);
            log = saveFile(doc);
        } catch (Exception e) {
            log = "Error: "+"(saving file from PDF)"+e.getMessage();
        }
        return log;
    }
    
     /**
      * This method is used when the user select a XML file to open and save the XML data to the database.
      * @param xmlFile the xml File
      * @return the logs of the saving
      */
    public static String saveFile(File xmlFile) {
        String log = null;
        try {
            final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            final DocumentBuilder db = dbf.newDocumentBuilder();
            final Document doc = db.parse(xmlFile);
            log = saveFile(doc);
        } catch (Exception e) {
            log = "Error: "+"(saving file from XML) - "+e.getMessage();
        }
        return log;
    }
    
    /**
     * This method is used from the previous methods saveFile(InputStream xmlStream), saveFile(File xmlFile)
     * for parsing the XML data and store them to the database.
     * @param doc the Document to be parse
     * @return the logs of the saving
     */
    public static String saveFile(Document doc) {         
        Transaction tx = null;
        Session hsession  = null;
        String logs = "OK...";
        try {          
            doc.getDocumentElement().normalize();

            hsession = HibernateUtil.currentSession();
            tx = hsession.beginTransaction();

            final Xml xml = EcvMainXML.saveFile(doc, hsession);

            new EcvDrivingLicenceXML().saveFile(xml, doc, hsession);
            new EcvNationalityXML().saveFile(xml, doc, hsession);
            new EcvWorkExperienceXML().saveFile(xml, doc, hsession);
            new EcvEducationXML().saveFile(xml, doc, hsession);
            new EcvLanguageXML().saveFile(xml, doc, hsession);

            tx.commit();
        }
        catch (TransformerException te)
        {
            logs = "Error: " + "( transformer exception) "+ te.getMessage();
        }
        catch (HibernateException e) {
            if ( tx != null ) tx.rollback();
            e.printStackTrace();
            logs = "Error: "+"(parsing XML)"+e.getMessage();
        }
        catch (Exception e) {

        }
        finally {
            if (hsession != null) {
                hsession.close();
            }
        }
        return logs;
    }
    
    /**
     * This method is used to save the properties of the configuration file.
     * 
     * @param fldDatabasename the database name
     * @param fldPassword the database password     
     * @param fldURL the database URL
     * @param fldUsername the database username
     * @param sqlServer is SQL server?
     * @param mySQL is mySQL?
     * @param oracle is Oracle?
     * @return the logs of the saving
     */
    public static String saveConfig(String fldDatabasename, 
                                    String fldPassword,
                                    String fldURL,
                                    String fldUsername,
                                    boolean sqlServer,
                                    boolean mySQL,
                                    boolean oracle) {
        try { 
            properties.setProperty("databasename", fldDatabasename);
            properties.setProperty("password", fldPassword);
            properties.setProperty("url", fldURL);
            properties.setProperty("username", fldUsername);
            if (sqlServer) properties.setProperty("default_database", "1");
            if (mySQL) properties.setProperty("default_database", "2");
            if (oracle) properties.setProperty("default_database", "3");                
            try {
              properties.store(new FileOutputStream(new File(SoftToolUtil.class.getClassLoader().getResource("softtool.properties").toURI())), null); 
            } catch (URISyntaxException e){
                return "Error: "+"(saving configurations to file) - "+e.getMessage();
            }
        } catch (IOException e) { 
            return "Error: "+"(saving configurations to file) "+e.getMessage(); 
        }
        return null;
    }
    
    /**
     * This method is used only to temporary save the XML file.   
     * @return the temporary XML file
     */
    public static File createTempFile(final File tempFolder) throws Exception {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("CV-", ".xml", tempFolder);
            if ( !tempFile.exists() )
            throw new Exception("TempFile creation fails!!!");
        } catch(Exception e) {
            System.out.println("TempFile creation fails!!!" + e);
        }
        return tempFile;
    }
    
    /**
     * This method is used to extract the attachment XML file from the PDF file.
     * @param selFile the PDF selected file
     * @return the logs of the saving
     */
    public static String savePDFXML(File selFile, final String folderPath) {
        String xmlData = "";
        File tempFile = null;
        File tempFolder = null;
        FileInputStream in = null;
        InputStream is = null;
        String logs = null;
        try {
            // Extract the XML to a temp file
            tempFolder = new File(folderPath);
            tempFolder.mkdirs();
            tempFile = SoftToolUtil.createTempFile(tempFolder);
            in = new FileInputStream(selFile);
            try {
                final WebServiceExtractXML tool = new WebServiceExtractXML(selFile.getPath(), tempFile.getPath());
                tool.execute();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception ignore) {
                        return "Error: "+ " (extracting xml) " +ignore.getMessage();
                    }
                }
            }
            // Create string containing the XML
            xmlData = FileUtils.readFileToString(tempFile, "UTF-8");
            is = new ByteArrayInputStream(xmlData.getBytes("UTF-8"));
            if (is != null) logs = SoftToolUtil.saveFile(is);
        } catch(Exception ee) {
            return "Error: "+" (reading extracted xml from PDF) "+ee.getMessage();
        } finally {
            try {
                FileUtils.deleteDirectory(tempFolder);
            }
            catch (IOException e) {
                System.out.println("Temp files to delete are in use ..");
            }
        }

        return logs;
    }
    
     /**
      * This method is used to extract the attachment XML file from the PDF file.
      * @param selFile the PDF selected file
      * @return the InputStream of the XML file
      */
    public static InputStream getXML(File selFile, final String folderPath) {
        String xmlData = "";
        File tempFile = null;
        File tempFolder = null;
        FileInputStream in = null;
        InputStream is = null;
        try {
            // Extract the XML to a temp file
            tempFolder = new File(folderPath);
            tempFolder.mkdirs();
            tempFile = SoftToolUtil.createTempFile(tempFolder);
            in = new FileInputStream(selFile);
            try {
                final WebServiceExtractXML tool = new WebServiceExtractXML(selFile.getPath(), tempFile.getPath());
                tool.execute();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception ignore) {
                        System.out.println("Error: "+ " (extracting XML from PDF) " +ignore);
                    }
                }
            }
            // Create string containing the XML
            xmlData = FileUtils.readFileToString(tempFile, "UTF-8");
            is = new ByteArrayInputStream(xmlData.getBytes("UTF-8"));
        } catch(Exception ee) {
            System.out.println("Error: "+ " (reading XML from PDF) " +ee);
            return null;
        } finally {
            try {
                FileUtils.deleteDirectory(tempFolder);
            }
            catch (IOException e) {
                System.out.println("Temp files to delete are in use ..");
            }
        }

        return is;
    }
     
    /**
    * This method is used to truncate a string to a specific length.
    * @param in the PDF selected file
    * @param len the PDF selected file
    * @return the InputStream of the XML file
    */
    public static String trunc( String in, int len ) {
        if (in != null && in.length() > len) return in.substring(0,len);
        return in;
    }
        
}
