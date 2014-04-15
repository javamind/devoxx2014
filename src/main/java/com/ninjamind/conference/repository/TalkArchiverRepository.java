package com.ninjamind.conference.repository;

import com.ninjamind.conference.domain.Country;
import com.ninjamind.conference.domain.Talk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository associé au {@link com.ninjamind.conference.domain.Talk}
 *
 * @author ehret_g
 */
@org.springframework.stereotype.Repository
public interface TalkArchiverRepository extends Repository<Talk, Long> {
    /**
     * Permet de récupérer la liste des talks à archiver
     * @param year
     * @return
     */
    List<Talk> findTalkToArchive(Integer year);

    /**
     * Permet d'archiver la liste des talks
     * @param year
     * @return
     */
    int archiveTalks(Integer year);
}
