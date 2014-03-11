package com.ninjamind.conference.events.conference;

import com.ninjamind.conference.events.Event;

import java.util.UUID;

/**
 * Event retourné lors de la recherche de la liste des conférences
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Conference
 */
public class ConferenceDetail extends Event{
    protected String name;
    protected String streetAdress;
    protected String city;
    protected String postalCode;
    protected String level;
    protected String codeCountry;
    protected String dateStart;
    protected String dateEnd;

    /**
     * Constructeur minimal
     * @param uuid
     * @param name
     */
    public ConferenceDetail(UUID uuid, String name, String start, String end) {
        this.key = key;
        this.name = name;
        this.dateStart=start;
        this.dateEnd=end;
    }

    /**
     *
     * @param uuid
     * @param name
     * @param streetAdress
     * @param city
     * @param postalCode
     * @param level
     * @param codeCountry
     * @param dateStart
     * @param dateEnd
     */
    public ConferenceDetail(UUID uuid, String name, String streetAdress, String city, String postalCode, String level, String codeCountry, String dateStart, String dateEnd) {
        this(uuid, name, dateStart, dateEnd);
        this.streetAdress = streetAdress;
        this.city = city;
        this.postalCode = postalCode;
        this.level = level;
        this.codeCountry = codeCountry;
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
}
