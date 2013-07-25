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
 * @hibernate.class table="ecv_nationality"
 */
public class Nationality {
    public Nationality() {
    }
    private Long id;
    private Xml xml;        
    private String code;
    private String nationality;
    
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
      * @hibernate.many-to-one column="xml_id" foreign-key="ecv_nationality_ibfk_1" 
      * @return 
      */
    public Xml getXml() {
        return xml;
    }

    public void setCode(String code) {
        this.code = code;
    }
    /**
     * @hibernate.property column="code"
     * @return
     */
    public String getCode() {
        return code;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    /**
     * @hibernate.property column="nationality"
     * @return
     */
    public String getNationality() {
        return nationality;
    }
}
