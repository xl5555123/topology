package org.serc.topology.model;

import org.serc.topology.exception.UnregularIpException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Xander on 2017/3/15.
 */
public class Sensor {

    public final static String ROOT_ID = "0";
    public final static Sensor ROOT = new Sensor(ROOT_ID);

    private String id;
    private Ip ip;
    private int mask;
    private List<Ip> line;
    private String parentId;
    private Date updated;

    private Sensor parentSensor;
    private List<Sensor> childSensors;

    public Sensor() {

    }
    public Sensor(String id) {
        this.id = id;
        try {
            this.ip = new Ip("0.0.0.0");
        } catch (UnregularIpException e) {
            e.printStackTrace();
        }
        line = new ArrayList<Ip>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ip getIp() {
        return ip;
    }

    public void setIp(Ip ip) {
        this.ip = ip;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    public List<Ip> getLine() {
        return line;
    }

    public void setLine(List<Ip> line) {
        this.line = line;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Sensor getParentSensor() {
        return parentSensor;
    }

    public void setParentSensor(Sensor parentSensor) {
        this.parentSensor = parentSensor;
    }

    public List<Sensor> getChildSensors() {
        return childSensors;
    }

    public void setChildSensors(List<Sensor> childSensors) {
        this.childSensors = childSensors;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
