package com.ninjamind.conference.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;

/**
 * @author ehret_g
 */
@Entity
@Table(name = "talk")
public class Talk {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String place;
    private Integer nbpeoplemax;
    private Level level;
    Set<Speaker> speakers;

    public Talk() {
    }

    public Talk(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getNbpeoplemax() {
        return nbpeoplemax;
    }

    public void setNbpeoplemax(Integer nbpeoplemax) {
        this.nbpeoplemax = nbpeoplemax;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Set<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(Set<Speaker> speakers) {
        this.speakers = speakers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Talk talk = (Talk) o;

        if (description != null ? !description.equals(talk.description) : talk.description != null) return false;
        if (level != talk.level) return false;
        if (!name.equals(talk.name)) return false;
        if (nbpeoplemax != null ? !nbpeoplemax.equals(talk.nbpeoplemax) : talk.nbpeoplemax != null) return false;
        if (place != null ? !place.equals(talk.place) : talk.place != null) return false;
        if (speakers != null ? !speakers.equals(talk.speakers) : talk.speakers != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (nbpeoplemax != null ? nbpeoplemax.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (speakers != null ? speakers.hashCode() : 0);
        return result;
    }
}
