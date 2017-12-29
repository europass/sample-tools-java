package eu.europa.cedefop.europass.jtool.parse;

import eu.europa.cedefop.europass.jtool.model.Nationality;
import eu.europa.cedefop.europass.jtool.model.Xml;
import org.apache.xpath.XPathAPI;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;

import static eu.europa.cedefop.europass.jtool.util.SoftToolUtil.trunc;

public class EcvNationalityXML implements EcvParseTablesXML {

    // SAVE THE DETAIL TABLE (ecv_nationality)
    public void saveFile(final Xml xml, final Document doc, final Session hsession) throws TransformerException {

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
    }
}
