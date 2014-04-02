package com.ninjamind.conference.repository;

import com.ninjamind.conference.domain.Talk;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.io.File;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

/**
 * {@link }
 *
 * @author EHRET_G
 */
public class TalkArchiverRepositoryImplTest  extends AbstractDbunitRepositoryTest{
    /**
     * Repository a tester
     */
    @Autowired
    private TalkArchiverRepository talkArchiverRepository;

    protected IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/test/resources/datasets/init_talk.xml"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenArgIsNull(){
        talkArchiverRepository.findTalkToArchive(null);
    }

    @Test
    public void shouldFindOneConfToArchiveWhenYearIs2014(){
        List<Talk> talks =  talkArchiverRepository.findTalkToArchive(2014);
        assertThat(talks).hasSize(1);
        assertThat(talks.get(0)).isLenientEqualsToByAcceptingFields(
              new Talk(2L, "La conf passee"), "id", "name");
        //d="2" name="La conf passee" dateStart="2010-04-18 13:30:00.0" dateEnd="2010-04-18 14:20:00.0

    }

    @Test
    public void shouldArchiveOneConfWhenYearIs2013(){
        int nb = talkArchiverRepository.archiveTalks(2013);
        assertThat(nb).isEqualTo(1);
    }

}
