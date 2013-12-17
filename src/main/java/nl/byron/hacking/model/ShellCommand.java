package nl.byron.hacking.model;

/**
 * @author Byron Voorbach
 */
public class ShellCommand {

    private String id;
    private String date;
    private String type;
    private String metaData;

    public ShellCommand() {
    }

    public ShellCommand(String id, String date, String type, String metaData) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.metaData = metaData;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getMetaData() {
        return metaData;
    }

    @Override
    public String toString() {
        return "ShellCommand{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", metaData='" + metaData + '\'' +
                '}';
    }
}
