package com.ninjamind.conference.domain;

import java.util.Set;

/**
 * @author ehret_g
 */
public class Speaker {
    private Long id;
    private String firstname;
    private String lastname;
    private String company;
    private String streetAdress;
    private String city;
    private String postalCode;
    private Country country;
    Set<Talk> talks;

    public Speaker() {
    }

    public Speaker(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStreetAdress() {
        return streetAdress;
    }

    public void setStreetAdress(String streetAdress) {
        this.streetAdress = streetAdress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Talk> getTalks() {
        return talks;
    }

    public void setTalks(Set<Talk> talks) {
        this.talks = talks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Speaker speaker = (Speaker) o;

        if (city != null ? !city.equals(speaker.city) : speaker.city != null) return false;
        if (company != null ? !company.equals(speaker.company) : speaker.company != null) return false;
        if (country != null ? !country.equals(speaker.country) : speaker.country != null) return false;
        if (!firstname.equals(speaker.firstname)) return false;
        if (!lastname.equals(speaker.lastname)) return false;
        if (postalCode != null ? !postalCode.equals(speaker.postalCode) : speaker.postalCode != null) return false;
        if (streetAdress != null ? !streetAdress.equals(speaker.streetAdress) : speaker.streetAdress != null)
            return false;
        if (talks != null ? !talks.equals(speaker.talks) : speaker.talks != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstname.hashCode();
        result = 31 * result + lastname.hashCode();
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (streetAdress != null ? streetAdress.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (talks != null ? talks.hashCode() : 0);
        return result;
    }
}
