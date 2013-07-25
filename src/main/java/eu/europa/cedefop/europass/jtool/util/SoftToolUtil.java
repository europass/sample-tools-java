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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import eu.europa.cedefop.europass.jtool.model.DrivingLicence;
import eu.europa.cedefop.europass.jtool.model.Education;
import eu.europa.cedefop.europass.jtool.model.Language;
import eu.europa.cedefop.europass.jtool.model.Nationality;
import eu.europa.cedefop.europass.jtool.model.WorkExperience;
import eu.europa.cedefop.europass.jtool.model.Xml;

import java.net.URISyntaxException;

import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * This class contains all the necessary methods that needing to parse
 * the PDF/XML file.
 * <p>
 * Also contains all the necessary methods to save the XML data into the users database.
 * @author Gomosidis Apostolos, Quality & Reliability S.A.
 * @version %I%, %G%
 * @since 1.0
 */
public class SoftToolUtil {
    private static Properties properties = null;       
    
    public SoftToolUtil() {
    }        
    
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
                File p = new File(SoftToolUtil.class.getClassLoader().getResource("softtool.properties").toURI());
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
            if (is!=null) {
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
          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
          DocumentBuilder db = dbf.newDocumentBuilder();
          Document doc = db.parse(xmlStream);
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
          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
          DocumentBuilder db = dbf.newDocumentBuilder();
          Document doc = db.parse(xmlFile);
          log = saveFile(doc);
        } catch (Exception e) {          
          log = "Error: "+"(saving file from XML) - "+e.getMessage();
        }        
        return log;
    }
    
    /**
     * This method is used from the previous methods saveFile(InputStream xmlStream), saveFile(File xmlFile) to parsing the XML data and store them to the database.
     * @param doc the Document to be parse
     * @return the logs of the saving
     */
    public static String saveFile(Document doc) {         
        Transaction tx=null;   
        Session hsession =null;
        String logs = "OK...";
        try {          
          doc.getDocumentElement().normalize();          
          
          // SAVE THE MAIN TABLE (ecv_XML)  
          String fname=null; 
          String lname=null; 
          String telephone=null;
          String fax=null;
          String mobile=null;
          String email=null;
          String addressLine=null; 
          String municipality=null;
          String postalCode=null;
          String countryCode=null;
          String country=null;
          String gender=null;
          String birthDate=null;
          String codeAppl=null;
          String appl=null;
          String social=null;
          String organisational=null;
          String technical=null;
          String computer=null;
          String artistic=null;
          String other=null;
          String additional=null; 
          String annexes=null;
          String photo=null;
          String photoType=null;
          String langCode=null;
          String lang=null;        
          byte[] photoArray;  
          
          Node n = XPathAPI.selectSingleNode(doc, "//identification/firstname");
          if (n != null && n.getFirstChild() !=null)  fname =n.getFirstChild().getNodeValue();
          
          n = XPathAPI.selectSingleNode(doc, "//identification/lastname");
          if (n != null && n.getFirstChild() !=null) lname = n.getFirstChild().getNodeValue();
          n = XPathAPI.selectSingleNode(doc, "//identification/contactinfo/telephone");
          if (n != null && n.getFirstChild() !=null)  telephone = n.getFirstChild().getNodeValue();
          n = XPathAPI.selectSingleNode(doc, "//identification/contactinfo/fax");
          if (n != null && n.getFirstChild() !=null)  fax = n.getFirstChild().getNodeValue();
          n = XPathAPI.selectSingleNode(doc, "//identification/contactinfo/mobile");
          if (n != null && n.getFirstChild() !=null)  mobile = n.getFirstChild().getNodeValue();
          n = XPathAPI.selectSingleNode(doc, "//identification/contactinfo/email");
          if (n != null && n.getFirstChild() !=null)  email = n.getFirstChild().getNodeValue();
          
          n = XPathAPI.selectSingleNode(doc, "//identification/contactinfo/address/addressLine");
          if (n != null && n.getFirstChild() !=null)  addressLine = n.getFirstChild().getNodeValue();
          n = XPathAPI.selectSingleNode(doc, "//identification/contactinfo/address/municipality");
          if (n != null && n.getFirstChild() !=null)  municipality = n.getFirstChild().getNodeValue();
          n = XPathAPI.selectSingleNode(doc, "//identification/contactinfo/address/postalCode");
          if (n != null && n.getFirstChild() !=null)  postalCode =n.getFirstChild().getNodeValue();
          n = XPathAPI.selectSingleNode(doc, "//identification/contactinfo/address/country/code");
          if (n != null && n.getFirstChild() !=null) countryCode = n.getFirstChild().getNodeValue();
          n = XPathAPI.selectSingleNode(doc, "//identification/contactinfo/address/country/label");
          if (n != null && n.getFirstChild() !=null) country = n.getFirstChild().getNodeValue();          
         
          n = XPathAPI.selectSingleNode(doc, "//identification/demographics/gender");
          if (n != null && n.getFirstChild() !=null) gender = n.getFirstChild().getNodeValue();
          n = XPathAPI.selectSingleNode(doc, "//identification/demographics/birthdate");
          if (n != null && n.getFirstChild() !=null) birthDate = n.getFirstChild().getNodeValue();
         
          n = XPathAPI.selectSingleNode(doc, "//application/code");
          if (n != null && n.getFirstChild() !=null) codeAppl = n.getFirstChild().getNodeValue();
          n = XPathAPI.selectSingleNode(doc, "//application/label");
          if (n != null && n.getFirstChild() !=null) appl = n.getFirstChild().getNodeValue();
          
          NodeList mLangLst =XPathAPI.selectNodeList(doc, "//language[@type='europass:mother']");    
          if (mLangLst.getLength() > 0){
            for (int j = 0; j < mLangLst.getLength(); j++) {
              Node mLang = mLangLst.item(j);
              if (mLang.getNodeType() == Node.ELEMENT_NODE) {            
                 Node tmp1 = XPathAPI.selectSingleNode(mLang, "code");
                 if (tmp1!= null && tmp1.getFirstChild() != null) langCode = tmp1.getFirstChild().getNodeValue();   
                 
                 Node tmp2 = XPathAPI.selectSingleNode(mLang, "label");
                 if (tmp2!= null && tmp2.getFirstChild() != null) lang = tmp2.getFirstChild().getNodeValue(); 

               }
            }
          }
          
          Node skillS = XPathAPI.selectSingleNode(doc, "//skill[@type='social']");  
          if (skillS!=null && skillS.getFirstChild()!=null) social = skillS.getFirstChild().getNodeValue();
          Node skillO = XPathAPI.selectSingleNode(doc, "//skill[@type='organisational']");  
          if (skillO!=null && skillO.getFirstChild()!=null) organisational = skillO.getFirstChild().getNodeValue();
          Node skillT = XPathAPI.selectSingleNode(doc, "//skill[@type='technical']");  
          if (skillT!=null && skillT.getFirstChild()!=null) technical = skillT.getFirstChild().getNodeValue();
          Node skillC = XPathAPI.selectSingleNode(doc, "//skill[@type='computer']");  
          if (skillC!=null && skillC.getFirstChild()!=null) computer = skillC.getFirstChild().getNodeValue();
          Node skillA = XPathAPI.selectSingleNode(doc, "//skill[@type='artistic']");  
          if (skillA!=null && skillA.getFirstChild()!=null) artistic = skillA.getFirstChild().getNodeValue();
          Node skillOt = XPathAPI.selectSingleNode(doc, "//skill[@type='other']");  
          if (skillOt!=null && skillOt.getFirstChild()!=null) other = skillOt.getFirstChild().getNodeValue();
          
          Node misc = XPathAPI.selectSingleNode(doc, "//misc[@type='additional']");  
          if (misc!=null && misc.getFirstChild()!=null) additional = misc.getFirstChild().getNodeValue();                                    
          misc = XPathAPI.selectSingleNode(doc, "//misc[@type='annexes']");  
          if (misc!=null && misc.getFirstChild()!=null) annexes = misc.getFirstChild().getNodeValue(); 
          
         Node photoN = XPathAPI.selectSingleNode(doc, "//identification/photo")   ;
          if (photoN!=null && photoN.getFirstChild()!=null ) {
            photo = photoN.getFirstChild().getNodeValue();
            photoType = photoN.getAttributes().item(0).getFirstChild().getNodeValue();
            if (photo != null){
              photoArray = photo.getBytes();
            } else {
              photoArray = null;
            }
          } else {
            photoArray=null;
          }
          hsession = HibernateUtil.currentSession(); 
          tx=hsession.beginTransaction();
          Xml xml = null;                           
            //
            xml = new Xml();
            xml.setAdditional(trunc(additional,1024));
            xml.setFname(trunc(fname,30));
            xml.setLname(trunc(lname,30)); 
            xml.setPhone(trunc(telephone,30));
            xml.setFax(trunc(fax,30));
            xml.setMobile(trunc(mobile,30));
            xml.setEmail(trunc(email,50));
            xml.setAddress(trunc(addressLine,50));
            xml.setMunic(trunc(municipality,50));
            xml.setPostalCode(trunc(postalCode,10));
            xml.setCountryCode(trunc(countryCode,5));
            xml.setCountry(trunc(country,30));
            xml.setGender(trunc(gender,2));
            xml.setBirthDate(trunc(birthDate,10));
            xml.setApplCode(trunc(codeAppl,10));
            xml.setAppl(trunc(appl,1024));
            xml.setSocial(trunc(social,1024));
            xml.setOrg(trunc(organisational,1024));
            xml.setTech(trunc(technical,1024));
            xml.setComputer(trunc(computer,1024));
            xml.setArtistic(trunc(artistic,1024));
            xml.setOther(trunc(other,1024));      
            xml.setAnnexes(trunc(annexes,1024));              
            if (photoArray!=null) 
                xml.setPhoto(photoArray);
            else
                xml.setPhoto(new byte[0]);
            xml.setMotherLangCode(trunc(langCode,5));
            xml.setMotherLang(trunc(lang,100));
            xml.setPhotoType(photoType);
            //
            hsession.save(xml);
            //
                
          // SAVE THE DETAIL TABLE (ecv_driving_licence) 
          NodeList drLst = XPathAPI.selectNodeList(doc, "//drivinglicence");  
          if (drLst.getLength()>0){
            for (int s = 0; s < drLst.getLength(); s++) {   
              Node dr = drLst.item(s);  
              String fst = (dr!=null && dr.getFirstChild() != null)? dr.getFirstChild().getNodeValue() : null;
              DrivingLicence dl = new DrivingLicence();
              dl.setXml(xml);
              dl.setDrivingSkill(trunc(fst,3));                    
              //
              hsession.save(dl);
              //                    
            }
          }
          
          // SAVE THE DETAIL TABLE (ecv_nationality) 
          NodeList natLst = XPathAPI.selectNodeList(doc, "//identification/demographics/nationality");   
          if (natLst.getLength()>0){
            for (int s = 0; s < natLst.getLength(); s++) {   
                Node nat = natLst.item(s);                  
                if (nat.getNodeType() == Node.ELEMENT_NODE) {   
                  Node fstChild = XPathAPI.selectSingleNode(nat, "code");
                  String fst = (fstChild!=null && fstChild.getFirstChild()!=null) ? fstChild.getFirstChild().getNodeValue(): null;
                  Node lstChild = XPathAPI.selectSingleNode(nat, "label");
                  String lst = (lstChild!=null && lstChild.getFirstChild()!=null) ? lstChild.getFirstChild().getNodeValue(): null;   
  
                  Nationality nt = new Nationality();
                  nt.setXml(xml);
                  nt.setCode(trunc(fst,10));    
                  nt.setNationality(trunc(lst,32));
                  //
                  hsession.save(nt);
                  //                                                   
                }
            } 
          }
                  
          //
          
            // SAVE THE DETAIL TABLE (ecv_work_experience) 
            NodeList wList = XPathAPI.selectNodeList(doc, "//workexperience");  
            if (wList.getLength() > 0){
              for (int s = 0; s < wList.getLength(); s++) {
                 String activities = null;
                 String name = null;
                 String address = null;
                 String mun = null;
                 String zipCode = null;
                 
                 String fromDay = null;
                 String fromMonth = null;
                 String fromYear = null;
                 
                 String toDay = null;
                 String toMonth = null;
                 String toYear = null;
                 
                 String posCode = null;
                 String pos = null;
                 
                 String countryCd = null;
                 String countryName = null;
                 
                 String sectorCode = null;
                 String sector = null;
                                
                 Node w = wList.item(s);
                 if (w.getNodeType() == Node.ELEMENT_NODE) {
                   
                   Node tmp = XPathAPI.selectSingleNode(w, "position/code");
                   if (tmp!=null && tmp.getFirstChild() != null) posCode = tmp.getFirstChild().getNodeValue();
                   tmp = XPathAPI.selectSingleNode(w, "position/label");
                   if (tmp!=null && tmp.getFirstChild() != null) pos = tmp.getFirstChild().getNodeValue();
                   tmp = XPathAPI.selectSingleNode(w, "activities");
                   if (tmp!=null && tmp.getFirstChild() != null) activities = tmp.getFirstChild().getNodeValue();
                   tmp = XPathAPI.selectSingleNode(w, "employer/name");
                   if (tmp!=null && tmp.getFirstChild() != null) name = tmp.getFirstChild().getNodeValue();
                   tmp = XPathAPI.selectSingleNode(w, "employer/address/addressLine");
                   if (tmp!=null && tmp.getFirstChild() != null) address = tmp.getFirstChild().getNodeValue();
                   tmp = XPathAPI.selectSingleNode(w, "employer/address/municipality");
                   if (tmp!=null && tmp.getFirstChild() != null) mun = tmp.getFirstChild().getNodeValue();
                   tmp = XPathAPI.selectSingleNode(w, "employer/address/postalCode");
                   if (tmp!=null && tmp.getFirstChild() != null) zipCode = tmp.getFirstChild().getNodeValue();
                   tmp = XPathAPI.selectSingleNode(w, "employer/address/country/code");
                   if (tmp!=null && tmp.getFirstChild() != null) countryCd = tmp.getFirstChild().getNodeValue();
                   tmp = XPathAPI.selectSingleNode(w, "employer/address/country/label");
                   if (tmp!=null && tmp.getFirstChild() != null) countryName = tmp.getFirstChild().getNodeValue();
                   tmp = XPathAPI.selectSingleNode(w, "employer/sector/code");
                   if (tmp!=null && tmp.getFirstChild() != null) sectorCode = tmp.getFirstChild().getNodeValue();
                   tmp = XPathAPI.selectSingleNode(w, "employer/sector/label");
                   if (tmp!=null && tmp.getFirstChild() != null) sector = tmp.getFirstChild().getNodeValue();
                   tmp = XPathAPI.selectSingleNode(w, "period/from/year");
                   if (tmp!=null && tmp.getFirstChild() != null) fromYear = tmp.getFirstChild().getNodeValue();
                   tmp = XPathAPI.selectSingleNode(w, "period/from/month");
                   if (tmp!=null && tmp.getFirstChild() != null)  {
                    fromMonth = tmp.getFirstChild().getNodeValue();
                    fromMonth  = fromMonth.replaceAll("-","");
                   }
                   tmp = XPathAPI.selectSingleNode(w, "period/from/day");
                   if (tmp!=null && tmp.getFirstChild() != null)  {
                    fromDay = tmp.getFirstChild().getNodeValue();
                    fromDay = fromDay.replaceAll("-","");
                   }
                   tmp = XPathAPI.selectSingleNode(w, "period/to/year");
                   if (tmp!=null && tmp.getFirstChild() != null)  toYear = tmp.getFirstChild().getNodeValue();
                   tmp = XPathAPI.selectSingleNode(w, "period/to/month");
                   if (tmp!=null && tmp.getFirstChild() != null)  {
                    toMonth = tmp.getFirstChild().getNodeValue();
                    toMonth = toMonth.replaceAll("-","");
                   }
                   tmp = XPathAPI.selectSingleNode(w, "period/to/day");
                   if (tmp!=null && tmp.getFirstChild() != null)  {
                    toDay = tmp.getFirstChild().getNodeValue();      
                    toDay = toDay.replaceAll("-",""); 
                   }
                                    
                   WorkExperience we = new WorkExperience();
                   we.setXml(xml);
                   we.setActivities(trunc(activities,100));
                   we.setEmplName(trunc(name,50));
                   we.setCodePos(trunc(posCode,6));
                   we.setCodeSector(trunc(sectorCode,3));
                   we.setCountry(trunc(countryName,30));
                   we.setCountryCode(trunc(countryCd,3));
                   we.setDayFrom(trunc(fromDay,2));
                   we.setDayTo(trunc(toDay,2));
                   we.setEmplAddress(trunc(address,50));
                   we.setEmplMunic(trunc(mun,50));
                   we.setEmplZcode(trunc(zipCode,10));
                   we.setMonthFrom(trunc(fromMonth,2));
                   we.setMonthTo(trunc(toMonth,2));
                   we.setPos(trunc(pos,1024));
                   we.setSector(trunc(sector,1024));
                   we.setYearFrom(trunc(fromYear,4));
                   we.setYearTo(trunc(toYear,4));
                   //
                   hsession.save(we);
                   //                                               
                 }
              }         
            }
            
           
//             //SAVE THE DETAIL TABLE (ecv_education)   
             NodeList eduLst = XPathAPI.selectNodeList(doc, "//education"); 
             if (eduLst.getLength() > 0){
               for (int s = 0; s < eduLst.getLength(); s++) {
                  String skills = null;
                  String name = null;
                  String address = null;
                  String mun = null;
                  String zipCode = null;
                  String title = null;
                  
                  String fromDay = null;
                  String fromMonth = null;
                  String fromYear = null;
                  
                  String toDay = null;
                  String toMonth = null;
                  String toYear = null;
                  
                  String levCode = null;
                  String lev = null;
                  
                  String countryCd = null;
                  String countryName = null;
                  
                  String eduCode = null;
                  String edu = null;
                                 
                  Node e = eduLst.item(s);                  
                  if (e.getNodeType() == Node.ELEMENT_NODE) { 
                        Node tmp = XPathAPI.selectSingleNode(e, "title");
                        if (tmp!=null && tmp.getFirstChild() != null) title = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "skills");
                        if (tmp!=null && tmp.getFirstChild() != null)  skills = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "organisation/name");
                        if (tmp!=null && tmp.getFirstChild() != null)  name = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "organisation/address/addressLine");
                        if (tmp!=null && tmp.getFirstChild() != null)  address = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "organisation/address/municipality");
                        if (tmp!=null && tmp.getFirstChild() != null)  mun = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "organisation/address/postalCode");
                        if (tmp!=null && tmp.getFirstChild() != null) zipCode = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "organisation/address/country/code");
                        if (tmp!=null && tmp.getFirstChild() != null)  countryCd = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "organisation/address/country/label");
                        if (tmp!=null && tmp.getFirstChild() != null)  countryName = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "organisation/type");
                        if (tmp!=null && tmp.getFirstChild() != null) {
						}
                        tmp = XPathAPI.selectSingleNode(e, "period/from/year");
                        if (tmp!=null && tmp.getFirstChild() != null)  fromYear = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "period/from/month");
                        if (tmp!=null && tmp.getFirstChild() != null)  {
                          fromMonth = tmp.getFirstChild().getNodeValue();
                          fromMonth  = fromMonth.replaceAll("-","");
                        }
                        tmp = XPathAPI.selectSingleNode(e, "period/from/day");
                        if (tmp!=null && tmp.getFirstChild() != null)  {
                          fromDay = tmp.getFirstChild().getNodeValue();
                          fromDay = fromDay.replaceAll("-","");
                        }
                        tmp = XPathAPI.selectSingleNode(e, "period/to/year");
                        if (tmp!=null && tmp.getFirstChild() != null)  toYear = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "period/to/month");
                        if (tmp!=null && tmp.getFirstChild() != null) {
                          toMonth = tmp.getFirstChild().getNodeValue();
                          toMonth = toMonth.replaceAll("-","");
                        }
                        tmp = XPathAPI.selectSingleNode(e, "period/to/day");
                        if (tmp!=null && tmp.getFirstChild() != null) {
                          toDay = tmp.getFirstChild().getNodeValue();      
                          toDay = toDay.replaceAll("-","");        
                        }
                        tmp = XPathAPI.selectSingleNode(e, "level/code");
                        if (tmp!=null && tmp.getFirstChild() != null) levCode = tmp.getFirstChild().getNodeValue();
                        
                        tmp = XPathAPI.selectSingleNode(e, "level/label");
                        if (tmp!=null && tmp.getFirstChild() != null) lev = tmp.getFirstChild().getNodeValue();
                        
                        //
                        Education ed = new Education();
                        ed.setXml(xml);
                        ed.setTitle(trunc(title,50));
                        ed.setSubject(trunc(skills,200));
                        ed.setOrgName(trunc(name,100));
                        ed.setCodeLevel(trunc(levCode,2));
                        ed.setEduField(trunc(edu,1024));
                        ed.setCountry(trunc(countryName,50));
                        ed.setCountryCode(trunc(countryCd,3));
                        ed.setDayFrom(trunc(fromDay,2));
                        ed.setDayTo(trunc(toDay,2));
                        ed.setOrgAddress(trunc(address,100));
                        ed.setOrgMunic(trunc(mun,50));
                        ed.setOrgZcode(trunc(zipCode,10));
                        ed.setMonthFrom(trunc(fromMonth,2));
                        ed.setMonthTo(trunc(toMonth,2));
                        ed.setLevel(trunc(lev,1024));                      
                        ed.setCodeEduField(trunc(eduCode,5));
                        ed.setYearFrom(trunc(fromYear,4));
                        ed.setYearTo(trunc(toYear,4));
                        //
                        hsession.save(ed);
                        //                                            
                  }
               }      
             }
                
              
             
              // SAVE THE DETAIL TABLE (ecv_language) 
              NodeList fLangLst = XPathAPI.selectNodeList(doc, "//language[@type='europass:foreign']");
              if (fLangLst.getLength() > 0){
                for (int s = 0; s < fLangLst.getLength(); s++) {
                   String lngCode = null;
                   String lng = null;
                   String listening = null;
                   String reading = null;
                   String spokeninteraction = null;
                   String spokenproduction = null;
                   String writing = null;                 
                   
                   Node fLang = fLangLst.item(s);                  
                   if (fLang.getNodeType() == Node.ELEMENT_NODE) {            
                     
                     Node tmp = XPathAPI.selectSingleNode(fLang, "code");          
                     if (tmp.getFirstChild()!=null) lngCode = tmp.getFirstChild().getNodeValue();
                     tmp = XPathAPI.selectSingleNode(fLang, "label");          
                     if (tmp.getFirstChild()!=null) lng = tmp.getFirstChild().getNodeValue();
                     tmp = XPathAPI.selectSingleNode(fLang, "level/listening");          
                     if (tmp.getFirstChild()!=null) listening = tmp.getFirstChild().getNodeValue();
                     tmp = XPathAPI.selectSingleNode(fLang, "level/reading");          
                     if (tmp.getFirstChild()!=null) reading = tmp.getFirstChild().getNodeValue();
                     tmp = XPathAPI.selectSingleNode(fLang, "level/spokeninteraction");          
                     if (tmp.getFirstChild()!=null) spokeninteraction = tmp.getFirstChild().getNodeValue();
                     tmp = XPathAPI.selectSingleNode(fLang, "level/spokenproduction");          
                     if (tmp.getFirstChild()!=null) spokenproduction = tmp.getFirstChild().getNodeValue();
                     tmp = XPathAPI.selectSingleNode(fLang, "level/writing");          
                     if (tmp.getFirstChild()!=null) writing = tmp.getFirstChild().getNodeValue();
                     
                     Language lg = new Language();
                     lg.setXml(xml);
                     lg.setCodeLang(trunc(lngCode,3));
                     lg.setLang(trunc(lng,30));
                     lg.setListening(trunc(listening,2));
                     lg.setReading(trunc(reading,2));
                     lg.setSpokenInteraction(trunc(spokeninteraction,2));
                     lg.setSpokenProduction(trunc(spokenproduction,2));
                     lg.setWriting(trunc(writing,2));
                     //
                     hsession.save(lg);
                     //                                                 
                   }
                }
              }
              //                       
              tx.commit();                 
              //
        } catch (TransformerException te){
          logs = "Error: " + "( transformer excpetion) "+ te.getMessage();
        } catch (HibernateException e) {
            if ( tx != null ) tx.rollback();
            e.printStackTrace();
            logs = "Error: "+"(parsing XML)"+e.getMessage();                    
        } finally {
            hsession.close();
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
                                    boolean oracle
                                    ) { 
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
    public static File createTempFile() throws Exception {
      File tempFile = null;
      try {        
        File tempFolder = new File("");
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
    public static String savePDFXML(File selFile) {
        String xmlData="";
        File tempFile = null;
        FileInputStream in = null;
        InputStream is = null;
        String logs = null;
        try {
            // Extract the XML to a temp file
            tempFile = SoftToolUtil.createTempFile();
            in = new FileInputStream(selFile);                            
            try {
              ExtractAttachments tool = new ExtractAttachments(in, tempFile); 
              tool.execute();
            } finally {
              if (in!=null) {
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
            if (is!=null) 
                logs = SoftToolUtil.saveFile(is);
        } catch(Exception ee) {
            return "Error: "+" (reading extracted xml from PDF) "+ee.getMessage();            
        } finally {        
            tempFile.delete();
        }        
        
        return logs;
        //
    }
    
     /**
      * This method is used to extract the attachment XML file from the PDF file.
      * @param selFile the PDF selected file
      * @return the InputStream of the XML file
      */
    public static InputStream getXML(File selFile) {
         String xmlData="";
         File tempFile = null;
         FileInputStream in = null;   
         InputStream is = null;
         try {
             // Extract the XML to a temp file
             tempFile = SoftToolUtil.createTempFile();
             in = new FileInputStream(selFile);                            
             try {
               ExtractAttachments tool = new ExtractAttachments(in, tempFile); 
               tool.execute();
             } finally {
               if (in!=null) {
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
            tempFile.delete();
         }
         return is;
         //
     }
     
      /**
       * This method is used to truncate a string to a specific length.
       * @param in the PDF selected file
       * @param len the PDF selected file
       * @return the InputStream of the XML file
       */
     public static String trunc( String in, int len ) {
         if (in!=null && in.length() > len) return in.substring(0,len);
         return in;
     }
        
}
