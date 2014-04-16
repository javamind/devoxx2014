package com.ninjamind.conference.repository;

import com.google.common.base.Preconditions;
import com.ninjamind.conference.domain.Talk;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 *
 * @author EHRET_G
 */
@Repository
public class TalkArchiverRepositoryImpl implements TalkArchiverRepository {

    @PersistenceContext
    private EntityManager em;

    private final static String message = "year is important to archive all talks before this year";

    @Override
    public List<Talk> findTalkToArchive(Integer year) {
        Preconditions.checkNotNull(year, message);
        return em
                .createNamedQuery("findTalkToArchive", Talk.class)
                .setParameter("year", year)
                .getResultList();
    }

    @Override
    public int archiveTalks(Integer year) {
        Preconditions.checkNotNull(year, message);
        return em.createNamedQuery("archiveTalks").setParameter("year", year).executeUpdate();
    }
}
