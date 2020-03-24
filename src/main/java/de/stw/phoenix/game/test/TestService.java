package de.stw.phoenix.game.test;

import com.google.common.base.Preconditions;
import de.stw.phoenix.game.engine.resources.api.Resource;
import de.stw.phoenix.game.engine.resources.api.ResourceRepository;
import de.stw.phoenix.game.engine.resources.api.Resources;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Transactional
    public void doSomethingTakingVeryLong(long playerId) throws InterruptedException {
        LoggerFactory.getLogger(getClass()).info("started long task");
        Preconditions.checkArgument(testRepository.count() == 1);
        TestEntity testEntity = testRepository.getOne(playerId);
        Thread.sleep(10000);
        final Resource ironResource = resourceRepository.getOne(Resources.Iron.getId());
        testEntity.add(ironResource, 9000);
        LoggerFactory.getLogger(getClass()).info("finished long task");
    }

    @Transactional
    public void addResources(long playerId, Resource resource, double amount) {
        final Resource theResource = resourceRepository.getOne(resource.getId());
        TestEntity testEntity = testRepository.getOne(playerId);
        testEntity.add(theResource, amount);
    }

    @Transactional
    public void updateCount(long playerId) {
        System.out.println(Thread.currentThread().getName() + " Updating");
        TestEntity one = testRepository.readForUpdate(playerId);
        one.incCount();
        try {
            Thread.sleep(2000);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        testRepository.save(one);
    }
}
