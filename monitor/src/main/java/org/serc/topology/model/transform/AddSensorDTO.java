package org.serc.topology.model.transform;

import java.util.List;

/**
 * Created by Xander on 2017/3/15.
 */
public class AddSensorDTO {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String ip;
    private int mask;
    private List<String> line;

    public AddSensorDTO() {

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    public List<String> getLine() {
        return line;
    }

    public void setLine(List<String> line) {
        this.line = line;
    }
}
