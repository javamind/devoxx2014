package com.ninjamind.conference.service;

import com.ninjamind.conference.events.conference.*;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;

/**
 * Gestion des {@link com.ninjamind.conference.domain.Conference}
 * @author EHRET_G
 */
@Service
public class ConferenceHandlerEvent implements ConferenceService
{
    @Override
    public ReadAllConferenceEvent getAllConference(ReadAllConferenceRequestEvent event) {
        return null;
    }

    @Override
    public ReadConferenceEvent getConference(ReadConferenceRequestEvent event) {
        if(event.getId().equals("3"))
            throw new JpaSystemException(new PersistenceException("dsf"));
        else
            return new ReadConferenceEvent(true, new ConferenceDetail(1L, "name", null, null));
    }

    @Override
    public CreatedConferenceEvent createConference(CreateConferenceEvent event) {
        return null;
    }

    @Override
    public UpdatedConferenceEvent createConference(UpdateConferenceEvent event) {
        return null;
    }

    @Override
    public DeletedConferenceEvent createConference(DeletedConferenceEvent event) {
        return null;
    }
}
