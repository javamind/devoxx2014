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
 * Cette classe se base sur DbSetup pour supprimer toutes les donn�es pr�sentes en base de donn�es
 *
 * @author EHRET_G
 */
@ContextConfiguration(classes = {PersistenceConfig.class})
@Category(InitializeTest.class)
public class DeleteAllData extends AbstractTransactionalJUnit4SpringContextTests {
    /**
     * Datasource utilisee dans les tests
     */
    @Autowired
    protected DataSource dataSource;

    /**
     * Avant chaque test un jeu de donn�es est inject�
     * @throws Exception
     */
    @Before
    public void prepare() throws Exception {
        Operation operation =
                sequenceOf(InitializeOperations.DELETE_ALL);
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();
    }

    @Test
    public void shouldDeleteAllDataInDatabase(){
        //Vide car le but est de juste lancer la mise a jour de la base de donn�es
    }
}
