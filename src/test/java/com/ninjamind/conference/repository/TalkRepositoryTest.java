package com.ninjamind.conference.repository;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;

/**
 * Classe de test du Repo {@link com.ninjamind.conference.repository.TalkRepository}. Le principe
 * est de montre un exemple avec Dbunit. Lors de la conference ce test sera transforme pour utilise
 * DbSetup a la place
 *
 * @author EHRET_G
 */
public class TalkRepositoryTest {


    /**
     * Repository a tester
     */
    @Autowired
    private TalkRepository talkRepository;

    protected IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/test/resources/datasets/init_talk.xml"));
    }


}
