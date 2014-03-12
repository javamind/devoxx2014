package com.ninjamind.conference.events.conference;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.domain.Country;
import com.ninjamind.conference.domain.Level;
import com.ninjamind.conference.utils.Utils;

import java.io.Serializable;

/**
 * Objet de transit lie aux évenements sur les conferences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class ConferenceDetail implements Serializable {
    protected Long id;
    protected String name;
    protected String streetAdress;
    protected String city;
    protected String postalCode;
    protected String level;
    protected String codeCountry;
    protected String dateStart;
    protected String dateEnd;

    public ConferenceDetail() {
    }

    /**
     * @param id
     * @param name
     * @param start
     * @param end
     */
    public ConferenceDetail(Long id, String name, String start, String end) {
        this.id = id;
        this.name = name;
        this.dateStart = start;
        this.dateEnd = end;
    }

    /**
     * @param id
     * @param name
     * @param streetAdress
     * @param city
     * @param postalCode
     * @param level
     * @param codeCountry
     * @param dateStart
     * @param dateEnd
     */
    public ConferenceDetail(Long id, String name, String streetAdress, String city, String postalCode, String level, String codeCountry, String dateStart, String dateEnd) {
        this(id, name, dateStart, dateEnd);
        this.streetAdress = streetAdress;
        this.city = city;
        this.postalCode = postalCode;
        this.level = level;
        this.codeCountry = codeCountry;
    }

    public ConferenceDetail(Conference conference) {
        this(
                conference.getId(),
                conference.getName(),
                conference.getStreetAdress(),
                conference.getCity(),
                conference.getPostalCode(),
                conference.getLevel() != null ? conference.getLevel().toString() : null,
                conference.getCountry() != null ? conference.getCountry().getCode() : null,
                Utils.dateJavaToJson(conference.getDateStart()),
                Utils.dateJavaToJson(conference.getDateEnd())
        );
    }

    public Conference toConference() {
        Conference conference = new Conference(
                getName(),
                Utils.dateJsonToJava(getDateStart()),
                Utils.dateJsonToJava(getDateEnd()));
        conference.setId(id);
        conference.setStreetAdress(streetAdress);
        conference.setCity(city);
        conference.setPostalCode(postalCode);
        conference.setLevel(level!=null ? Level.valueOf(level) : null);
        conference.setCountry(new Country(codeCountry, null));
        return conference;
    }

    public String getName() {
        return name;
    }

    public String getStreetAdress() {
        return streetAdress;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getLevel() {
        return level;
    }

    public String getCodeCountry() {
        return codeCountry;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public Long getId() {
        return id;
    }
}
