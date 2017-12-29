package eu.europa.cedefop.europass.jtool.parse;

import eu.europa.cedefop.europass.jtool.model.Language;
import eu.europa.cedefop.europass.jtool.model.Xml;
import org.apache.xpath.XPathAPI;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;

import static eu.europa.cedefop.europass.jtool.util.SoftToolUtil.trunc;

public class EcvLanguageXML implements EcvParseTablesXML {

    // SAVE THE DETAIL TABLE (ecv_language)
    public void saveFile(final Xml xml, final Document doc, final Session hsession) throws TransformerException {

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
    }
}
