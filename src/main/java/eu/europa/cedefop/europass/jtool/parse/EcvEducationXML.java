package eu.europa.cedefop.europass.jtool.parse;

import eu.europa.cedefop.europass.jtool.model.Education;
import eu.europa.cedefop.europass.jtool.model.Xml;
import org.apache.xpath.XPathAPI;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;

import static eu.europa.cedefop.europass.jtool.util.SoftToolUtil.trunc;

public class EcvEducationXML implements EcvParseTablesXML {

    //SAVE THE DETAIL TABLE (ecv_education)
    public void saveFile(final Xml xml, final Document doc, final Session hsession) throws TransformerException {

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
    }
}
