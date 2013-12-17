package nl.byron.hacking.model;

/**
 * @author Byron Voorbach
 */
public class TunnelCommand {

    private String id;
    private String type;
    private String metadata;

    public TunnelCommand(String id, String type, String metadata) {
        this.id = id;
        this.type = type;
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "TunnelCommand{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}
