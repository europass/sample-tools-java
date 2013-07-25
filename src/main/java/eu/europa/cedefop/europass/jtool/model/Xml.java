package eu.europa.cedefop.europass.jtool.model;
/**
 * @hibernate.class table="ecv_xml"
 */
public class Xml {
    public Xml() {
    }
    private Long id;
    private String lname;
    private String fname;
    private String address;
    private String munic;
    private String postalCode;
    private String countryCode;
    private String country;
    private String phone;
    private String fax;
    private String mobile;
    private String gender;
    private String birthDate;    
    private byte[] photo;   
    private String applCode;
    private String appl;
    private String social;
    private String org;
    private String tech;
    private String computer;
    private String artistic;
    private String other;
    private String additional;
    private String annexes;
    
    private String motherLang;
    private String motherLangCode;
    private String email;
    private String photoType;
    
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

    public void setLname(String lname) {
        this.lname = lname;
    }
    /**
     * @hibernate.property column="lname"
     * @return
     */
    public String getLname() {
        return lname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
    /**
     * @hibernate.property column="fname"
     * @return
     */
    public String getFname() {
        return fname;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * @hibernate.property column="address"
     * @return
     */
    public String getAddress() {
        return address;
    }

    public void setMunic(String munic) {
        this.munic = munic;
    }
    /**
     * @hibernate.property column="munic"
     * @return
     */
    public String getMunic() {
        return munic;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * @hibernate.property column="postal_code"
     * @return
     */
    public String getPostalCode() {
        return postalCode;
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

    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * @hibernate.property column="phone"
     * @return
     */
    public String getPhone() {
        return phone;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    /**
     * @hibernate.property column="fax"
     * @return
     */
    public String getFax() {
        return fax;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    /**
     * @hibernate.property column="mobile"
     * @return
     */
    public String getMobile() {
        return mobile;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    /**
     * @hibernate.property column="gender"
     * @return
     */
    public String getGender() {
        return gender;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    /**
     * @hibernate.property column="birthdate"
     * @return
     */
    public String getBirthDate() {
        return birthDate;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    /**
     * @hibernate.property column="photo"
     * @return
     */
    public byte[] getPhoto() {
        return photo;
    }

    public void setApplCode(String applCode) {
        this.applCode = applCode;
    }
    /**
     * @hibernate.property column="code_application"
     * @return
     */
    public String getApplCode() {
        return applCode;
    }

    public void setAppl(String appl) {
        this.appl = appl;
    }
    /**
     * @hibernate.property column="application"
     * @return
     */
    public String getAppl() {
        return appl;
    }

    public void setSocial(String social) {
        this.social = social;
    }
    /**
     * @hibernate.property column="social"
     * @return
     */
    public String getSocial() {
        return social;
    }

    public void setOrg(String org) {
        this.org = org;
    }
    /**
     * @hibernate.property column="organisational"
     * @return
     */
    public String getOrg() {
        return org;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }
    /**
     * @hibernate.property column="technical"
     * @return
     */
    public String getTech() {
        return tech;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }
    /**
     * @hibernate.property column="computer"
     * @return
     */
    public String getComputer() {
        return computer;
    }

    public void setArtistic(String artistic) {
        this.artistic = artistic;
    }
    /**
     * @hibernate.property column="artistic"
     * @return
     */
    public String getArtistic() {
        return artistic;
    }

    public void setOther(String other) {
        this.other = other;
    }
    /**
     * @hibernate.property column="other"
     * @return
     */
    public String getOther() {
        return other;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }
    /**
     * @hibernate.property column="additional"
     * @return
     */
    public String getAdditional() {
        return additional;
    }

    public void setAnnexes(String annexes) {
        this.annexes = annexes;
    }
    /**
     * @hibernate.property column="annexes"
     * @return
     */
    public String getAnnexes() {
        return annexes;
    }

    public void setMotherLang(String motherLang) {
        this.motherLang = motherLang;
    }
    /**
     * @hibernate.property column="mother_language"
     * @return
     */
    public String getMotherLang() {
        return motherLang;
    }

    public void setMotherLangCode(String motherLangCode) {
        this.motherLangCode = motherLangCode;
    }
    /**
     * @hibernate.property column="code_mother_language"
     * @return
     */
    public String getMotherLangCode() {
        return motherLangCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @hibernate.property column="email"
     * @return
     */
    public String getEmail() {
        return email;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }
    /**
     * @hibernate.property column="photo_type"
     * @return
     */
    public String getPhotoType() {
        return photoType;
    }
}
