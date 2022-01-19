package com.kbs.datajpa.etc;

import com.kbs.datajpa.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Find;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@SpringBootTest
@Transactional
public class OptimisticLockTest {

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    public void setData() {
        entityManager.persist(Student.builder().name("bs").lastName("k").build());
        entityManager.persist(Student.builder().name("js").lastName("l").build());
        entityManager.persist(Student.builder().name("sy").lastName("k").build());

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void test() {
        Long studentId = 6L;

        /* Find */
        entityManager.find(Student.class, studentId, LockModeType.OPTIMISTIC);

        /* Query */
        Query query = entityManager.createQuery("select s from Student s where s.id = :id");
        query.setParameter("id", studentId);
        query.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        query.getResultList();

        /*Explicit Locking(명시적 잠금)*/
        Student student = entityManager.find(Student.class, studentId);
        entityManager.lock(student, LockModeType.OPTIMISTIC);

        /* Refresh */
        Student student2 = entityManager.find(Student.class, studentId);
        entityManager.refresh(student2, LockModeType.READ);

        /* NamedQuery */
//        @NamedQuery(name="optimisticLock",
//                query="SELECT s FROM Student s WHERE s.id LIKE :id",
//                lockMode = WRITE)


    }

}
