package eu.europa.cedefop.europass.jtool.parse;

import eu.europa.cedefop.europass.jtool.model.DrivingLicence;
import eu.europa.cedefop.europass.jtool.model.Xml;
import org.apache.xpath.XPathAPI;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;

import static eu.europa.cedefop.europass.jtool.util.SoftToolUtil.trunc;

public class EcvDrivingLicenceXML implements EcvParseTablesXML {

    // SAVE THE DETAIL TABLE (ecv_driving_licence)
    public void saveFile(final Xml xml, final Document doc, final Session hsession) throws TransformerException {

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
    }
}
