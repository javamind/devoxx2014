package com.ninjamind.conference.events.talk;

import com.ninjamind.conference.domain.Country;
import com.ninjamind.conference.domain.Level;
import com.ninjamind.conference.domain.Talk;

import java.io.Serializable;

/**
 * Objet de transit lie
 *
 * @author EHRET_G
 * @see com.ninjamind.conference.domain.Talk
 */
public class TalkDetail implements Serializable {
    protected Long id;
    protected String name;
    protected String description;
    protected String place;
    protected Integer nbpeoplemax;
    protected String level;

    /**
     *
     */
    public TalkDetail() {
    }

    /**
     * @param id
     * @param name
     */
    public TalkDetail(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TalkDetail(Long id, String name, String description, String place, Integer nbpeoplemax, String level) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.place = place;
        this.nbpeoplemax = nbpeoplemax;
        this.level = level;
    }

    public TalkDetail(Talk talk) {
        this(
                talk.getId(),
                talk.getName(),
                talk.getDescription(),
                talk.getPlace(),
                talk.getNbpeoplemax(),
                talk.getLevel() != null ? talk.getLevel().toString() : null
        );
    }

    public Talk toTalk() {
        Talk talk = new Talk(name);
        talk.setId(id);
        talk.setDescription(description);
        talk.setPlace(place);
        talk.setNbpeoplemax(nbpeoplemax);
        talk.setLevel(level!=null ? Level.valueOf(level) : null);
        return talk;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace() {
        return place;
    }

    public Integer getNbpeoplemax() {
        return nbpeoplemax;
    }

    public String getLevel() {
        return level;
    }
}
