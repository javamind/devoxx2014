package com.ninjamind.conference.repository;

import com.ninjamind.conference.domain.Talk;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

/**
 * Test de a classe {@link com.ninjamind.conference.repository.TalkArchiverRepository}
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

    /**
     * Test de {@link com.ninjamind.conference.repository.TalkArchiverRepository#findTalkToArchive(Integer)} quand arg invalide
     */
    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenArgIsNull(){
        talkArchiverRepository.findTalkToArchive(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.repository.TalkArchiverRepository#findTalkToArchive(Integer)} quand tout est OK
     */
    @Test
    public void shouldFindOneConfToArchiveWhenYearIs2014(){
        List<Talk> talks =  talkArchiverRepository.findTalkToArchive(2014);
        assertThat(talks).hasSize(1);
        assertThat(talks.get(0)).isLenientEqualsToByAcceptingFields(
              new Talk(2L, "La conf passee"), "id", "name");

    }


    /**
     * Test de {@link com.ninjamind.conference.repository.TalkArchiverRepository#findTalkToArchive(Integer)} quand rien n'est trouve car la date
     * passee est trop vieille
     */
    @Test
    public void shouldNotFindOneConfToArchiveWhenYearIsTooOld(){
        assertThat(talkArchiverRepository.findTalkToArchive(2000)).isEmpty();

    }


    /**
     * Test de la fonction d'archivage {@link com.ninjamind.conference.repository.TalkArchiverRepository#archiveTalks(Integer)}  quand rien n'est trouve car la date
     * passee est trop vieille
     */
    @Test
    public void shouldNotArchiveTalkWhenNoEntityFound(){
        assertThat(talkArchiverRepository.archiveTalks(2000)).isEqualTo(0);
    }

    /**
     * Test de la fonction d'archivage {@link com.ninjamind.conference.repository.TalkArchiverRepository#archiveTalks(Integer)} quand rien n'est trouve car la date
     * passee est trop vieille
     */
    @Test
    public void shouldArchiveTalkWhenOneIsFound() throws Exception{

        TransactionTemplate tp = new TransactionTemplate(transactionManager);
        tp.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tp.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                assertThat(talkArchiverRepository.archiveTalks(2014)).isEqualTo(1);
            }
        });

        //TODO
//        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
//        ITable actualTable = databaseDataSet.getTable("TALK");
//        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/datasets/talk_archived.xml"));
//        ITable expectedTable = expectedDataSet.getTable("TALK");
//        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"version", "maj*", "date*", "description", "level", "nbpeoplemax", "place"});
//        Assertion.assertEquals(expectedTable, filteredActualTable);
    }
}
