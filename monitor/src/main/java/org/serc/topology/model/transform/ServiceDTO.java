package org.serc.topology.model.transform;

/**
 * Created by Xander on 2017/3/20.
 */
public class ServiceDTO {
    private int port;
    private String protocol;
    private String name;
    private String status;
    public ServiceDTO() {

    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
