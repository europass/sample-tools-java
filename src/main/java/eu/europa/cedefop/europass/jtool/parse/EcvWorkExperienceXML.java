package eu.europa.cedefop.europass.jtool.parse;

import eu.europa.cedefop.europass.jtool.model.WorkExperience;
import eu.europa.cedefop.europass.jtool.model.Xml;
import org.apache.xpath.XPathAPI;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;

import static eu.europa.cedefop.europass.jtool.util.SoftToolUtil.trunc;

public class EcvWorkExperienceXML implements EcvParseTablesXML{

    public void saveFile(final Xml xml, final Document doc, final Session hsession) throws TransformerException {

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
    }
}
