package eu.europa.cedefop.europass.jtool.parse;

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


import eu.europa.cedefop.europass.jtool.model.Xml;
import org.apache.xpath.XPathAPI;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;
import static eu.europa.cedefop.europass.jtool.util.SoftToolUtil.trunc;


public class EcvMainXML {

    public static Xml saveFile(final Document doc, final Session hsession) throws TransformerException {

        final Xml xml = new Xml();

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

        return xml;
    }
}
