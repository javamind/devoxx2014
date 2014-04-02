package com.ninjamind.conference.repository;

import com.google.common.base.Preconditions;
import com.ninjamind.conference.domain.Talk;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * {@link }
 *
 * @author EHRET_G
 */
@Repository
@NamedQueries(value = {
        @NamedQuery(name = "findTalkToArchive", query = "SELECT t FROM Talk t WHERE year(t.dateStart) < :year"),
        @NamedQuery(name = "archiveTalks", query = "UPDATE Talk t SET t.status='Archived' WHERE year(t.dateStart) < :year")
})
public class TalkArchiverRepositoryImpl implements TalkArchiverRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Talk> findTalkToArchive(Integer year) {
        Preconditions.checkNotNull(year, "year is important to archive all talks before this year");
        return em
                .createNamedQuery("findTalkToArchive", Talk.class)
                .setParameter("year", year)
                .getResultList();
    }

    @Override
    public int archiveTalks(Integer year) {
        Preconditions.checkNotNull(year, "year is important to archive all talks before this year");
        return em.createNamedQuery("archiveTalks").setParameter("year", year).executeUpdate();
    }
}
