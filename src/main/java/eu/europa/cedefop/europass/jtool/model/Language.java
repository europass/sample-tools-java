package eu.europa.cedefop.europass.jtool.model;

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
/**
 * @hibernate.class table="ecv_language"
 */
public class Language {
    public Language() {
    }
    
    private Long id;
    private Xml xml;  
    private String codeLang;
    private String lang;
    private String listening;
    private String reading;
    private String spokenInteraction;
    private String spokenProduction;
    private String writing;
    
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @hibernate.id generator-class="native" column="id"
     * @hibernate.generator-param name="sequence" value="d_id_seq" 
     * @return the id of the data object.
     */   
    public Long getId() {
        return id;
    }
    
    public void setXml(Xml xml) {
        this.xml = xml;
    }

     /**
      * @hibernate.many-to-one column="xml_id" foreign-key="ecv_langauge_ibfk_1" 
      * @return 
      */
    public Xml getXml() {
        return xml;
    }

    public void setCodeLang(String codeLang) {
        this.codeLang = codeLang;
    }
    /**
     * @hibernate.property column="code_language"
     * @return
     */
    public String getCodeLang() {
        return codeLang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
    /**
     * @hibernate.property column="olanguage"
     * @return
     */
    public String getLang() {
        return lang;
    }

    public void setListening(String listening) {
        this.listening = listening;
    }
    /**
     * @hibernate.property column="listening"
     * @return
     */
    public String getListening() {
        return listening;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }
    /**
     * @hibernate.property column="reading"
     * @return
     */
    public String getReading() {
        return reading;
    }

    public void setSpokenInteraction(String spokenInteraction) {
        this.spokenInteraction = spokenInteraction;
    }
    /**
     * @hibernate.property column="spoken_interaction"
     * @return
     */
    public String getSpokenInteraction() {
        return spokenInteraction;
    }

    public void setSpokenProduction(String spokenProduction) {
        this.spokenProduction = spokenProduction;
    }
    /**
     * @hibernate.property column="spoken_production"
     * @return
     */
    public String getSpokenProduction() {
        return spokenProduction;
    }

    public void setWriting(String writing) {
        this.writing = writing;
    }
    /**
     * @hibernate.property column="writing"
     * @return
     */
    public String getWriting() {
        return writing;
    }
}
