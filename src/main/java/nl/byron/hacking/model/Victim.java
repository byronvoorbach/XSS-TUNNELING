package nl.byron.hacking.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.logging.LogRecord;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Byron Voorbach
 */
public class Victim {

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
        this.id = id;
        this.ip = request.getRemoteAddr();
        this.userAgent = request.getHeader("user-agent");
        this.referer = request.getHeader("referer");
        this.firstSeen = firstSeen;
        this.lastSeen = firstSeen;
        commandsToBeSent = new ArrayList<TunnelCommand>();
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