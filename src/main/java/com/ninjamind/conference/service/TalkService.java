package com.ninjamind.conference.service;

import com.ninjamind.conference.events.talk.*;

/**
 * Service lié aux {@link com.ninjamind.conference.domain.Talk}
 */
public interface TalkService {

    /**
     * Permet de retourner la liste des Talks
     * @param event
     * @return
     */
    ReadAllTalkEvent getAllTalk(ReadAllTalkRequestEvent event);

    /**
     * Permet de retourner un Talk
     * @param event
     * @return
     */
    ReadTalkEvent getTalk(ReadTalkRequestEvent event);

    /**
     * Creation d'un Talk
     * @param event
     * @return
     */
    CreatedTalkEvent createTalk(CreateTalkEvent event);

    /**
     * Modification d'un Talk
     * @param event
     * @return
     */
    UpdatedTalkEvent updateTalk(UpdateTalkEvent event);

    /**
     * Suppression d'un Talk
     * @param event
     * @return
     */
    DeletedTalkEvent deleteTalk(DeleteTalkEvent event);
}
