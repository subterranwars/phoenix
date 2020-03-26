package de.stw.phoenix.game.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ArtificialClockService implements ClockService {

    private static final String ID = "artificial";

    @Autowired
    private ClockRepository clockRepository;

    @Value("${de.stw.clock.tickLengthInMs}")
    private long tickLengthInMs;

    @Override
    @Transactional
    public Clock getCurrentClock() {
        Optional<ArtificialClock> clock = clockRepository.findById(ID);
        if (!clock.isPresent()) {
            final ArtificialClock artificialClock = new ArtificialClock(tickLengthInMs, TimeUnit.MILLISECONDS);
            clockRepository.save(artificialClock);
        }
        return clockRepository.getOne(ID);
    }

    @Override
    @Transactional
    public Tick getCurrentTick() {
        return getCurrentClock().getCurrentTick();
    }

    @Override
    @Transactional
    public Clock tick() {
        getCurrentClock().nextTick();
        return getCurrentClock();
    }
}
