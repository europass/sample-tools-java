package eu.europa.cedefop.europass.jtool.parse;

import eu.europa.cedefop.europass.jtool.model.Xml;
import org.hibernate.Session;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;

interface EcvParseTablesXML {

    void saveFile(final Xml xml, final Document doc, final Session hsession) throws TransformerException;
}
