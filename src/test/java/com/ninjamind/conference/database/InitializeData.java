package com.ninjamind.conference.database;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.ninjamind.conference.category.InitializeTest;
import com.ninjamind.conference.config.PersistenceConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.sql.DataSource;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;

/**
 * Cette classe se base sur DbSetup pour charger un jeu de données plus complet
 * pour pouvoir faire des tests d'intégration
 *
 * @author EHRET_G
 */
@ContextConfiguration(classes = {PersistenceConfig.class})
@Category(InitializeTest.class)
public class InitializeData extends AbstractTransactionalJUnit4SpringContextTests {
    /**
     * Datasource utilisee dans les tests
     */
    @Autowired
    protected DataSource dataSource;

    /**
     * Avant chaque test un jeu de données est injecté
     * @throws Exception
     */
    @Before
    public void prepare() throws Exception {
        Operation operation =
                sequenceOf(
                        InitializeOperations.DELETE_ALL,
                        InitializeOperations.INSERT_COUNTRY_DATA,
                        InitializeOperations.INSERT_CONFERENCE_DATA,
                        InitializeOperations.INSERT_SPEAKER_DATA,
                        InitializeOperations.INSERT_TALK_DATA,
                        InitializeOperations.INSERT_CONFERENCE_TALK_DATA,
                        InitializeOperations.INSERT_SPEAKER_TALK_DATA);
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();
    }

    @Test
    public void shouldCreateDataSetInDatabase(){
        //Vide car le but est de juste lancer la mise a jour de la base de données
    }
}
