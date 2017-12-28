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
 * @hibernate.class table="ecv_work_experience"
 */
public class WorkExperience {
    public WorkExperience() {
    }
    private Long id;
    private Xml xml;        
    private String dayFrom;
    private String dayTo;
    private String monthFrom;
    private String monthTo;
    private String yearFrom;
    private String yearTo;    
    private String pos;
    private String activities;
    private String emplName;
    private String emplAddress;
    private String emplMunic;
    private String emplZcode;
    private String countryCode;
    private String country;
    private String sector;
    
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
      * @hibernate.many-to-one column="xml_id" foreign-key="ecv_work_experience_ibfk_1" 
      * @return 
      */
    public Xml getXml() {
        return xml;
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

    public void setPos(String pos) {
        this.pos = pos;
    }
    /**
     * @hibernate.property column="wposition"
     * @return
     */
    public String getPos() {
        return pos;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }
    /**
     * @hibernate.property column="activities"
     * @return
     */
    public String getActivities() {
        return activities;
    }

    public void setEmplName(String emplName) {
        this.emplName = emplName;
    }
    /**
     * @hibernate.property column="employer_name"
     * @return
     */
    public String getEmplName() {
        return emplName;
    }

    public void setEmplAddress(String emplAddress) {
        this.emplAddress = emplAddress;
    }
    /**
     * @hibernate.property column="employer_address"
     * @return
     */
    public String getEmplAddress() {
        return emplAddress;
    }

    public void setEmplMunic(String emplMunic) {
        this.emplMunic = emplMunic;
    }
    /**
     * @hibernate.property column="employer_munic"
     * @return
     */
    public String getEmplMunic() {
        return emplMunic;
    }

    public void setEmplZcode(String emplZcode) {
        this.emplZcode = emplZcode;
    }
    /**
     * @hibernate.property column="employer_zcode"
     * @return
     */
    public String getEmplZcode() {
        return emplZcode;
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

    public void setSector(String sector) {
        this.sector = sector;
    }
    /**
     * @hibernate.property column="sector"
     * @return
     */
    public String getSector() {
        return sector;
    }
}
