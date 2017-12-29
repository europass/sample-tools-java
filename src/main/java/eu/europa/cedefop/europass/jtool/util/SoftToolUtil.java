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
 * @author Eworx S.A.
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
     * This method is used from the previous methods saveFile(InputStream xmlStream), saveFile(File xmlFile) to parsing the XML data and store them to the database.
     * @param doc the Document to be parse
     * @return the logs of the saving
     */
    public static String saveFile(Document doc) {         
        Transaction tx = null;
        Session hsession  = null;
        String logs = "OK...";
        try {          
            doc.getDocumentElement().normalize();

            // SAVE THE MAIN TABLE (ecv_XML)
            String fname = null;
            String lname = null;
            String phone = null;
            String phone2 = null;
            String phone3 = null;
            String email = null;
            String addressLine = null;
            String municipality = null;
            String postalCode = null;
            String countryCode = null;
            String country = null;
            String gender = null;
            String birthDate = null;
            String codeAppl = null;
            String appl = null;
            String social = null;
            String organisational = null;
            String jobRelated = null;
            String computer = null;
            String other = null;
            String additional = null;
            String annexes = null;
            String photo = null;
            String photoType = null;
            String langCode = null;
            String lang = null;
            byte[] photoArray;
          
            Node n = XPathAPI.selectSingleNode(doc, "//Identification/PersonName/FirstName");
            if (n != null && n.getFirstChild()  != null)  fname =n.getFirstChild().getNodeValue();
            n = XPathAPI.selectSingleNode(doc, "//Identification/PersonName/Surname");
            if (n != null && n.getFirstChild()  != null) lname = n.getFirstChild().getNodeValue();

            n = XPathAPI.selectSingleNode(doc, "//Identification/ContactInfo/Email/Contact");
            if (n != null && n.getFirstChild()  != null)  email = n.getFirstChild().getNodeValue();
            n = XPathAPI.selectSingleNode(doc, "//Identification/ContactInfo/Address/Contact/AddressLine");
            if (n != null && n.getFirstChild()  != null)  addressLine = n.getFirstChild().getNodeValue();
            n = XPathAPI.selectSingleNode(doc, "//Identification/ContactInfo/Address/Contact/Municipality");
            if (n != null && n.getFirstChild()  != null)  municipality = n.getFirstChild().getNodeValue();
            n = XPathAPI.selectSingleNode(doc, "//Identification/ContactInfo/Address/Contact/PostalCode");
            if (n != null && n.getFirstChild()  != null)  postalCode =n.getFirstChild().getNodeValue();
            n = XPathAPI.selectSingleNode(doc, "//Identification/ContactInfo/Address/Contact/Country/Code");
            if (n != null && n.getFirstChild()  != null) countryCode = n.getFirstChild().getNodeValue();
            n = XPathAPI.selectSingleNode(doc, "//Identification/ContactInfo/Address/Contact/Country/Label");
            if (n != null && n.getFirstChild()  != null) country = n.getFirstChild().getNodeValue();

            n = XPathAPI.selectSingleNode(doc, "//Identification/Demographics/Gender/Code");
            if (n != null && n.getFirstChild()  != null) gender = n.getFirstChild().getNodeValue();

            // birthdate
            n = XPathAPI.selectSingleNode(doc, "//Identification/Demographics/Birthdate/@year");
            if (n != null && n.getFirstChild() != null) birthDate = n.getFirstChild().getNodeValue();
            n = XPathAPI.selectSingleNode(doc, "//Identification/Demographics/Birthdate/@month");
            if (n != null && n.getFirstChild() != null) birthDate += n.getFirstChild().getNodeValue();
            n = XPathAPI.selectSingleNode(doc, "//Identification/Demographics/Birthdate/@day");
            if (n != null && n.getFirstChild() != null) birthDate += n.getFirstChild().getNodeValue();
            if (birthDate != null) {
                birthDate = birthDate.replaceAll("-","");
            }

            n = XPathAPI.selectSingleNode(doc, "//Headline/Type/Code");
            if (n != null && n.getFirstChild() != null) codeAppl = n.getFirstChild().getNodeValue();
            n = XPathAPI.selectSingleNode(doc, "//Headline/Type/Label");
            if (n != null && n.getFirstChild() != null) appl = n.getFirstChild().getNodeValue();

            final NodeList phoneLst = XPathAPI.selectNodeList(doc, "//Identification/ContactInfo/TelephoneList/Telephone");
            if (phoneLst.getLength() > 0){
            // store only 3 recent phones !
                Node nodePhone = phoneLst.item(0);
                if (nodePhone.getNodeType() == Node.ELEMENT_NODE) {
                    Node tmp1 = XPathAPI.selectSingleNode(nodePhone, "Contact");
                    if (tmp1!= null && tmp1.getFirstChild() != null) phone = tmp1.getFirstChild().getNodeValue();
                }
                Node nodePhone2 = phoneLst.item(1);
                if (nodePhone2 != null && nodePhone2.getNodeType() == Node.ELEMENT_NODE) {
                    Node tmp1 = XPathAPI.selectSingleNode(nodePhone2, "Contact");
                    if (tmp1!= null && tmp1.getFirstChild() != null) phone2 = tmp1.getFirstChild().getNodeValue();
                }
                Node nodePhone3 = phoneLst.item(2);
                if (nodePhone3 != null && nodePhone3.getNodeType() == Node.ELEMENT_NODE) {
                    Node tmp1 = XPathAPI.selectSingleNode(nodePhone3, "Contact");
                    if (tmp1!= null && tmp1.getFirstChild() != null) phone3 = tmp1.getFirstChild().getNodeValue();
                }
            }

            final NodeList mLangLst = XPathAPI.selectNodeList(doc, "//Skills/Linguistic/MotherTongueList/MotherTongue/Description");
            if (mLangLst.getLength() > 0){
                for (int j = 0; j < mLangLst.getLength(); j++) {
                    final Node mLang = mLangLst.item(j);
                    if (mLang.getNodeType() == Node.ELEMENT_NODE) {
                        final Node tmp1 = XPathAPI.selectSingleNode(mLang, "Code");
                        if (tmp1!= null && tmp1.getFirstChild() != null) langCode = tmp1.getFirstChild().getNodeValue();

                        final Node tmp2 = XPathAPI.selectSingleNode(mLang, "Label");
                        if (tmp2!= null && tmp2.getFirstChild() != null) lang = tmp2.getFirstChild().getNodeValue();
                    }
                }
            }

            final Node skillS = XPathAPI.selectSingleNode(doc, "//Skills/Communication/Description");
            if (skillS != null && skillS.getFirstChild() != null) social = skillS.getFirstChild().getNodeValue();
            final Node skillO = XPathAPI.selectSingleNode(doc, "//Skills/Organisational/Description");
            if (skillO != null && skillO.getFirstChild() != null) organisational = skillO.getFirstChild().getNodeValue();
            final Node skillT = XPathAPI.selectSingleNode(doc, "//Skills/JobRelated/Description");
            if (skillT != null && skillT.getFirstChild() != null) jobRelated = skillT.getFirstChild().getNodeValue();
            final Node skillC = XPathAPI.selectSingleNode(doc, "//Skills/Computer/Description");
            if (skillC != null && skillC.getFirstChild() != null) computer = skillC.getFirstChild().getNodeValue();
            final Node skillOt = XPathAPI.selectSingleNode(doc, "//Skills/Other/Description");
            if (skillOt != null && skillOt.getFirstChild() != null) other = skillOt.getFirstChild().getNodeValue();

            final NodeList annexNList = XPathAPI.selectNodeList(doc, "//Attachment");
            if (annexNList.getLength() > 0) {
                for (int j = 0; j < annexNList.getLength(); j++) {
                    final Node annexNode = annexNList.item(j);
                    if (annexNode.getNodeType() == Node.ELEMENT_NODE) {
                        final Node annexName = XPathAPI.selectSingleNode(annexNode, "Name");
                        if (annexName.getFirstChild() != null) {
                            if (annexes == null ) annexes = annexName.getFirstChild().getNodeValue() + ", ";
                            else annexes += annexName.getFirstChild().getNodeValue() + ", ";
                        }
                    }
                }
                // remove space and comma for last annex.
                if (annexes != null) annexes = annexes.substring(0, annexes.length() - 2);
            }

            //additional information
            final NodeList achList = XPathAPI.selectNodeList(doc, "//AchievementList/Achievement");
            if (achList.getLength() > 0) {
                for (int j = 0; j < achList.getLength(); j++) {
                    String achString = null;
                    final Node achNode = achList.item(j);
                    if (achNode.getNodeType() == Node.ELEMENT_NODE) {
                        final Node achLabel = XPathAPI.selectSingleNode(achNode, "Title/Label");
                        final Node achDescr = XPathAPI.selectSingleNode(achNode, "Description");
                        if (achLabel != null && achLabel.getFirstChild() != null) achString = achLabel.getFirstChild().getNodeValue() + ": ";
                        if (achDescr != null && achDescr.getFirstChild() != null) achString += achDescr.getFirstChild().getNodeValue();
                        if (additional == null) additional = achString + ", ";
                        else additional += achString + ", ";
                    }
                }
                // remove space and comma for last achievement
                if (additional != null) additional = additional.substring(0, additional.length() - 2);
            }

            final Node photoN = XPathAPI.selectSingleNode(doc, "//Identification/Photo/Data");
            if (photoN != null && photoN.getFirstChild() != null ) {
                photo = photoN.getFirstChild().getNodeValue();
                final Node nodeMimetype = XPathAPI.selectSingleNode(doc, "//Identification/Photo/MimeType");
                if (nodeMimetype != null && nodeMimetype.getFirstChild() != null) photoType = nodeMimetype.getFirstChild().getNodeValue();
                if (photo != null){
                    photoArray = photo.getBytes();
                } else {
                    photoArray = null;
                }
            } else {
                photoArray = null;
            }

            hsession = HibernateUtil.currentSession();
            tx = hsession.beginTransaction();

            final Xml xml = new Xml();
            xml.setAdditional(trunc(additional,1024));
            xml.setFname(trunc(fname,30));
            xml.setLname(trunc(lname,30));
            xml.setPhone(trunc(phone,30));
            xml.setPhone2(trunc(phone2,30));
            xml.setPhone3(trunc(phone3,30));
            xml.setEmail(trunc(email,50));
            xml.setAddress(trunc(addressLine,50));
            xml.setMunic(trunc(municipality,50));
            xml.setPostalCode(trunc(postalCode,10));
            xml.setCountryCode(trunc(countryCode,5));
            xml.setCountry(trunc(country,30));
            xml.setGender(trunc(gender,2));
            xml.setBirthDate(trunc(birthDate,10));
            xml.setApplCode(trunc(codeAppl,50));
            xml.setAppl(trunc(appl,1024));
            xml.setSocial(trunc(social,1024));
            xml.setOrg(trunc(organisational,1024));
            xml.setJobRelated(trunc(jobRelated,1024));
            xml.setComputer(trunc(computer,1024));
            xml.setOther(trunc(other,1024));
            xml.setAnnexes(trunc(annexes,1024));
            if (photoArray != null)
                xml.setPhoto(photoArray);
            else
                xml.setPhoto(new byte[0]);
            xml.setMotherLangCode(trunc(langCode,5));
            xml.setMotherLang(trunc(lang,100));
            xml.setPhotoType(photoType);
            hsession.save(xml);

            // SAVE THE DETAIL TABLE (ecv_driving_licence)
            final NodeList drLst = XPathAPI.selectNodeList(doc, "//Skills/Driving/Description/Licence");
            if (drLst.getLength()>0) {
                for (int s = 0; s < drLst.getLength(); s++) {
                    final Node dr = drLst.item(s);
                    final String fst = (dr != null && dr.getFirstChild() != null)? dr.getFirstChild().getNodeValue() : null;
                    final DrivingLicence dl = new DrivingLicence();
                    dl.setXml(xml);
                    dl.setDrivingSkill(trunc(fst,3));
                    hsession.save(dl);
                }
            }
          
            // SAVE THE DETAIL TABLE (ecv_nationality)
            final NodeList natLst = XPathAPI.selectNodeList(doc, "//Identification/Demographics/NationalityList/Nationality");
            if (natLst.getLength()>0) {
                for (int s = 0; s < natLst.getLength(); s++) {
                    final Node nat = natLst.item(s);
                    if (nat.getNodeType() == Node.ELEMENT_NODE) {
                        final Node fstChild = XPathAPI.selectSingleNode(nat, "Code");
                        final String fst = (fstChild != null && fstChild.getFirstChild() != null) ? fstChild.getFirstChild().getNodeValue(): null;
                        final Node lstChild = XPathAPI.selectSingleNode(nat, "Label");
                        final String lst = (lstChild != null && lstChild.getFirstChild() != null) ? lstChild.getFirstChild().getNodeValue(): null;

                        final Nationality nt = new Nationality();
                        nt.setXml(xml);
                        nt.setCode(trunc(fst,10));
                        nt.setNationality(trunc(lst,32));
                        hsession.save(nt);
                    }
                }
            }

            // SAVE THE DETAIL TABLE (ecv_work_experience)
            final NodeList wList = XPathAPI.selectNodeList(doc, "//WorkExperienceList/WorkExperience");
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
                    String pos = null;
                    String countryCd = null;
                    String countryName = null;
                    String sector = null;

                    final Node w = wList.item(s);
                    if (w.getNodeType() == Node.ELEMENT_NODE) {

                        Node tmp = XPathAPI.selectSingleNode(w, "Position/Label");
                        if (tmp != null && tmp.getFirstChild() != null) pos = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(w, "Activities");
                        if (tmp != null && tmp.getFirstChild() != null) activities = tmp.getFirstChild().getNodeValue();

                        tmp = XPathAPI.selectSingleNode(w, "Employer/Name");
                        if (tmp  != null && tmp.getFirstChild() != null) name = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(w, "Employer/ContactInfo/Address/Contact/AddressLine");
                        if (tmp != null && tmp.getFirstChild() != null) address = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(w, "Employer/ContactInfo/Address/Contact/Municipality");
                        if (tmp != null && tmp.getFirstChild() != null) mun = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(w, "Employer/ContactInfo/Address/Contact/PostalCode");
                        if (tmp != null && tmp.getFirstChild() != null) zipCode = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(w, "Employer/ContactInfo/Address/Contact/Country/Code");
                        if (tmp != null && tmp.getFirstChild() != null) countryCd = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(w, "Employer/ContactInfo/Address/Contact/Country/Label");
                        if (tmp != null && tmp.getFirstChild() != null) countryName = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(w, "Employer/Sector/Label");
                        if (tmp != null && tmp.getFirstChild() != null) sector = tmp.getFirstChild().getNodeValue();

                        tmp = XPathAPI.selectSingleNode(w, "Period/From/@year");
                        if (tmp != null && tmp.getFirstChild() != null) fromYear = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(w, "Period/From/@month");
                        if (tmp != null && tmp.getFirstChild() != null)  {
                            fromMonth = tmp.getFirstChild().getNodeValue();
                            fromMonth  = fromMonth.replaceAll("-","");
                        }
                        tmp = XPathAPI.selectSingleNode(w, "Period/From/@day");
                        if (tmp != null && tmp.getFirstChild() != null)  {
                            fromDay = tmp.getFirstChild().getNodeValue();
                            fromDay = fromDay.replaceAll("-","");
                        }
                        tmp = XPathAPI.selectSingleNode(w, "Period/To/@year");
                        if (tmp != null && tmp.getFirstChild() != null)  toYear = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(w, "Period/To/@month");
                        if (tmp != null && tmp.getFirstChild() != null)  {
                            toMonth = tmp.getFirstChild().getNodeValue();
                            toMonth = toMonth.replaceAll("-","");
                        }
                        tmp = XPathAPI.selectSingleNode(w, "Period/To/@day");
                        if (tmp != null && tmp.getFirstChild() != null)  {
                            toDay = tmp.getFirstChild().getNodeValue();
                            toDay = toDay.replaceAll("-","");
                        }

                        final WorkExperience we = new WorkExperience();
                        we.setXml(xml);
                        we.setActivities(trunc(activities,100));
                        we.setEmplName(trunc(name,50));
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
                        hsession.save(we);
                    }
                }
            }

            //SAVE THE DETAIL TABLE (ecv_education)
            final NodeList eduLst = XPathAPI.selectNodeList(doc, "//EducationList/Education");
            if (eduLst.getLength() > 0) {
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

                    final Node e = eduLst.item(s);
                    if (e.getNodeType() == Node.ELEMENT_NODE) {
                        Node tmp = XPathAPI.selectSingleNode(e, "Title");
                        if (tmp != null && tmp.getFirstChild() != null) title = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "Activities");
                        if (tmp != null && tmp.getFirstChild() != null)  skills = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "Organisation/Name");
                        if (tmp != null && tmp.getFirstChild() != null)  name = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "Organisation/ContactInfo/Address/Contact/AddressLine");
                        if (tmp != null && tmp.getFirstChild() != null)  address = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "Organisation/ContactInfo/Address/Contact/Municipality");
                        if (tmp != null && tmp.getFirstChild() != null)  mun = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "Organisation/ContactInfo/Address/Contact/PostalCode");
                        if (tmp != null && tmp.getFirstChild() != null) zipCode = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "Organisation/ContactInfo/Address/Contact/Country/Code");
                        if (tmp != null && tmp.getFirstChild() != null)  countryCd = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "Organisation/ContactInfo/Address/Contact/Country/Label");
                        if (tmp != null && tmp.getFirstChild() != null)  countryName = tmp.getFirstChild().getNodeValue();

                        tmp = XPathAPI.selectSingleNode(e, "Period/From/@year");
                        if (tmp != null && tmp.getFirstChild() != null)  fromYear = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "Period/From/@month");
                        if (tmp != null && tmp.getFirstChild() != null)  {
                            fromMonth = tmp.getFirstChild().getNodeValue();
                            fromMonth  = fromMonth.replaceAll("-","");
                        }
                        tmp = XPathAPI.selectSingleNode(e, "Period/From/@day");
                        if (tmp != null && tmp.getFirstChild() != null)  {
                          fromDay = tmp.getFirstChild().getNodeValue();
                          fromDay = fromDay.replaceAll("-","");
                        }
                        tmp = XPathAPI.selectSingleNode(e, "Period/To/@year");
                        if (tmp != null && tmp.getFirstChild() != null)  toYear = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "Period/To/@month");
                        if (tmp != null && tmp.getFirstChild() != null) {
                            toMonth = tmp.getFirstChild().getNodeValue();
                            toMonth = toMonth.replaceAll("-","");
                        }
                        tmp = XPathAPI.selectSingleNode(e, "Period/To/@day");
                        if (tmp != null && tmp.getFirstChild() != null) {
                            toDay = tmp.getFirstChild().getNodeValue();
                            toDay = toDay.replaceAll("-","");
                        }
                        tmp = XPathAPI.selectSingleNode(e, "Level/Code");
                        if (tmp != null && tmp.getFirstChild() != null) levCode = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "Level/Label");
                        if (tmp != null && tmp.getFirstChild() != null) lev = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "Field/Code");
                        if (tmp != null && tmp.getFirstChild() != null) eduCode = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(e, "Field/Label");
                        if (tmp != null && tmp.getFirstChild() != null) edu = tmp.getFirstChild().getNodeValue();

                        final Education ed = new Education();
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
                        hsession.save(ed);
                    }
                }
            }

            // SAVE THE DETAIL TABLE (ecv_language)
            final NodeList fLangLst = XPathAPI.selectNodeList(doc, "//Skills/Linguistic/ForeignLanguageList/ForeignLanguage");
            if (fLangLst.getLength() > 0) {
                for (int s = 0; s < fLangLst.getLength(); s++) {
                    String lngCode = null;
                    String lng = null;
                    String listening = null;
                    String reading = null;
                    String spokeninteraction = null;
                    String spokenproduction = null;
                    String writing = null;

                    final Node fLang = fLangLst.item(s);
                    if (fLang.getNodeType() == Node.ELEMENT_NODE) {

                        Node tmp = XPathAPI.selectSingleNode(fLang, "Description/Code");
                        if (tmp != null && tmp.getFirstChild() != null) lngCode = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(fLang, "Description/Label");
                        if (tmp.getFirstChild() != null) lng = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(fLang, "ProficiencyLevel/Listening");
                        if (tmp.getFirstChild() != null) listening = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(fLang, "ProficiencyLevel/Reading");
                        if (tmp.getFirstChild() != null) reading = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(fLang, "ProficiencyLevel/SpokenInteraction");
                        if (tmp.getFirstChild() != null) spokeninteraction = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(fLang, "ProficiencyLevel/SpokenProduction");
                        if (tmp.getFirstChild() != null) spokenproduction = tmp.getFirstChild().getNodeValue();
                        tmp = XPathAPI.selectSingleNode(fLang, "ProficiencyLevel/Writing");
                        if (tmp.getFirstChild() != null) writing = tmp.getFirstChild().getNodeValue();

                        final Language lg = new Language();
                        lg.setXml(xml);
                        lg.setCodeLang(trunc(lngCode,3));
                        lg.setLang(trunc(lng,30));
                        lg.setListening(trunc(listening,2));
                        lg.setReading(trunc(reading,2));
                        lg.setSpokenInteraction(trunc(spokeninteraction,2));
                        lg.setSpokenProduction(trunc(spokenproduction,2));
                        lg.setWriting(trunc(writing,2));
                        hsession.save(lg);
                    }
                }
            }

            tx.commit();

        } catch (TransformerException te){
            logs = "Error: " + "( transformer exception) "+ te.getMessage();
        } catch (HibernateException e) {
            if ( tx != null ) tx.rollback();
            e.printStackTrace();
            logs = "Error: "+"(parsing XML)"+e.getMessage();
        } finally {
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
                System.out.println("Cannot cleanup temp folder !!");
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
