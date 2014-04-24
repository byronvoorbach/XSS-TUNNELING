package nl.byron.hacking.model;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Byron Voorbach
 */
public class Victim {

    private String os;
    private String id;
    private String ip;
    private String userAgent;
    private String referer;
    private DateTime firstSeen;
    private DateTime lastSeen;
    private ArrayList<TunnelCommand> commandsToBeSent;
    private String currentPage;

    private static final DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");

    public Victim(String id, HttpServletRequest request, DateTime firstSeen) {
        String userAgent = request.getHeader("User-Agent");

        UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
        ReadableUserAgent agent = parser.parse(userAgent);

        this.id = id;
        this.ip = request.getRemoteAddr();
        this.userAgent = userAgent;
        this.os = agent.getOperatingSystem().getName();
        this.referer = request.getHeader("referer");
        this.firstSeen = firstSeen;
        this.lastSeen = firstSeen;
        commandsToBeSent = new ArrayList<>();
    }

    public TunnelCommand getCommand() {
        if (commandsToBeSent.size() > 0) {
            TunnelCommand tunnelCommand = commandsToBeSent.get(0);
            commandsToBeSent.remove(tunnelCommand);

            return tunnelCommand;
        }
        return null;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getFirstSeen() {
        return firstSeen.toString(fmt);
    }

    public void setFirstSeen(DateTime firstSeen) {
        this.firstSeen = firstSeen;
    }

    public String getLastSeen() {
        return lastSeen.toString(fmt);
    }

    public void setLastSeen(DateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public ArrayList<TunnelCommand> getCommandsToBeSent() {
        return commandsToBeSent;
    }

    public void setCommandsToBeSent(ArrayList<TunnelCommand> commandsToBeSent) {
        this.commandsToBeSent = commandsToBeSent;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public DateTime getLastSeenAsDateTime() {
        return lastSeen;
    }
}
