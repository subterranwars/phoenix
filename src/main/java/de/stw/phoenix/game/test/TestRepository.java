package de.stw.phoenix.game.test;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import javax.persistence.LockModeType;

public interface TestRepository extends Repository<TestEntity, Long> {

    @Query("Select e from TestEntity e where e.id = ?1")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    TestEntity readForUpdate(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    TestEntity getOne(Long aLong);

    void save(TestEntity entity);

    long count();
}
