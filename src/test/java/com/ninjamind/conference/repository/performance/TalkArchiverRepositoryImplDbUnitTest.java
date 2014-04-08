package com.ninjamind.conference.repository.performance;

import com.ninjamind.conference.config.PersistenceConfig;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.repository.TalkArchiverRepository;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test de a classe {@link com.ninjamind.conference.repository.TalkArchiverRepository}
 * en utilisant testNg et DbUnit. Le but est de voir le temps mis par les tests quand
 * on accède en boucle à la base de données
 *
 * @see com.ninjamind.conference.repository.performance.TalkArchiverRepositoryDbSetupTest
 * @author EHRET_G
 */
@ContextConfiguration(classes = {PersistenceConfig.class})
public class TalkArchiverRepositoryImplDbUnitTest extends AbstractDbunitTestNgRepositoryTest {


    @Autowired
    private TalkArchiverRepository talkArchiverRepository;



    protected IDataSet readDataSet() {
        try {
            return new FlatXmlDataSetBuilder().build(new File("src/test/resources/datasets/talk_init.xml"));
        } catch (MalformedURLException | DataSetException e) {
            throw new RuntimeException(e);
        }
    }



    @Test(invocationCount = 200)
    public void shouldFindOneConfToArchiveWhenYearIs2014() {
        List<Talk> talks = talkArchiverRepository.findTalkToArchive(2014);
        assertThat(talks).hasSize(1);
        assertThat(talks.get(0)).isEqualToComparingOnlyGivenFields(
                new Talk(2L, "La conf passee"), "id", "name");

    }
}
