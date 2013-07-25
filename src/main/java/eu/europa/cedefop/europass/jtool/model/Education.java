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
 * @hibernate.class table="ecv_education"
 */
public class Education {
    public Education() {
    }
    
    private Long id;
    private Xml xml;        
    private String title;
    private String subject;
    private String orgName;
    private String orgType;
    private String orgAddress;
    private String orgMunic;
    private String orgZcode;
    private String countryCode;
    private String country;
    private String codeLevel;
    private String level;
    private String codeEduField;
    private String eduField;
    private String dayFrom;
    private String dayTo;
    private String monthFrom;
    private String monthTo;
    private String yearFrom;
    private String yearTo;
    
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
      * @hibernate.many-to-one column="xml_id" foreign-key="ecv_education_ibfk_1" 
      * @return 
      */
    public Xml getXml() {
        return xml;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @hibernate.property column="title"
     * @return
     */
    public String getTitle() {
        return title;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    /**
     * @hibernate.property column="subject"
     * @return
     */
    public String getSubject() {
        return subject;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    /**
     * @hibernate.property column="org_name"
     * @return
     */
    public String getOrgName() {
        return orgName;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
    /**
     * @hibernate.property column="org_type"
     * @return
     */
    public String getOrgType() {
        return orgType;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }
    /**
     * @hibernate.property column="org_address"
     * @return
     */
    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgMunic(String orgMunic) {
        this.orgMunic = orgMunic;
    }
    /**
     * @hibernate.property column="org_munic"
     * @return
     */
    public String getOrgMunic() {
        return orgMunic;
    }

    public void setOrgZcode(String orgZcode) {
        this.orgZcode = orgZcode;
    }
    /**
     * @hibernate.property column="org_zcode"
     * @return
     */
    public String getOrgZcode() {
        return orgZcode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    /**
     * @hibernate.property column="code_country"
     * @return
     */
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * @hibernate.property column="country"
     * @return
     */
    public String getCountry() {
        return country;
    }

    public void setCodeLevel(String codeLevel) {
        this.codeLevel = codeLevel;
    }
    /**
     * @hibernate.property column="code_level"
     * @return
     */
    public String getCodeLevel() {
        return codeLevel;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    /**
     * @hibernate.property column="edulevel"
     * @return
     */
    public String getLevel() {
        return level;
    }

    public void setCodeEduField(String codeEduField) {
        this.codeEduField = codeEduField;
    }
    /**
     * @hibernate.property column="code_edu_field"
     * @return
     */
    public String getCodeEduField() {
        return codeEduField;
    }

    public void setEduField(String eduField) {
        this.eduField = eduField;
    }
    /**
     * @hibernate.property column="edu_field"
     * @return
     */
    public String getEduField() {
        return eduField;
    }

    public void setDayFrom(String dayFrom) {
        this.dayFrom = dayFrom;
    }
    /**
     * @hibernate.property column="day_from"
     * @return
     */
    public String getDayFrom() {
        return dayFrom;
    }

    public void setDayTo(String dayTo) {
        this.dayTo = dayTo;
    }
    /**
     * @hibernate.property column="day_to"
     * @return
     */
    public String getDayTo() {
        return dayTo;
    }

    public void setMonthFrom(String monthFrom) {
        this.monthFrom = monthFrom;
    }
    /**
     * @hibernate.property column="month_from"
     * @return
     */
    public String getMonthFrom() {
        return monthFrom;
    }

    public void setMonthTo(String monthTo) {
        this.monthTo = monthTo;
    }
    /**
     * @hibernate.property column="month_to"
     * @return
     */
    public String getMonthTo() {
        return monthTo;
    }

    public void setYearFrom(String yearFrom) {
        this.yearFrom = yearFrom;
    }
    /**
     * @hibernate.property column="year_from"
     * @return
     */
    public String getYearFrom() {
        return yearFrom;
    }

    public void setYearTo(String yearTo) {
        this.yearTo = yearTo;
    }
    /**
     * @hibernate.property column="year_to"
     * @return
     */
    public String getYearTo() {
        return yearTo;
    }
}
