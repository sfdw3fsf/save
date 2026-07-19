package vn.vnpt.vnptit.ecms.dto.sync.vcenter;

public class VCterHostDTO {
    private String host;
    private String name;
    private String connection_state;
    private String power_state;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConnection_state() {
        return connection_state;
    }

    public void setConnection_state(String connection_state) {
        this.connection_state = connection_state;
    }

    public String getPower_state() {
        return power_state;
    }

    public void setPower_state(String power_state) {
        this.power_state = power_state;
    }
}
