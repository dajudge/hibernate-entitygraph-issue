package com.example;

import com.example.entities.A;
import com.example.entities.B;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by dajudge on 28.08.14.
 */
public class IssueTest {
    private static final EntityManager ENTITY_MANAGER = Persistence.createEntityManagerFactory("test").createEntityManager();

    @BeforeClass
    public static void setupDatabase() {
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.createNativeQuery("CREATE TABLE A(ID DECIMAL, PRIMARY KEY(ID))").executeUpdate();
        ENTITY_MANAGER.createNativeQuery("CREATE TABLE B(ID DECIMAL, A_ID DECIMAL, PRIMARY KEY(ID))").executeUpdate();
        ENTITY_MANAGER.getTransaction().commit();
    }

    @AfterClass
    public static void dropDatabase() {
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.createNativeQuery("DROP TABLE A").executeUpdate();
        ENTITY_MANAGER.createNativeQuery("DROP TABLE B").executeUpdate();
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Test
    public void testEntityGraphsAreNotAppliedToSubqueries() {
        EntityGraph<A> graph = ENTITY_MANAGER.createEntityGraph(A.class);
        graph.addSubgraph("bs", B.class);

        ENTITY_MANAGER.getTransaction().begin();
        try {
            ENTITY_MANAGER.createQuery("SELECT a FROM A a WHERE NOT a.bs IS EMPTY").setHint("javax.persistence.loadgraph", graph).getResultList();
        } finally {
            ENTITY_MANAGER.getTransaction().rollback();
        }
    }
}
