package org.serc.topology.model.transform;

import org.serc.topology.model.Ip;
import org.serc.topology.model.Sensor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xander on 2017/3/20.
 */
public class SensorDTO {
    private String id;
    private String ip;
    private int mask;
    private List<String> line;
    private String parentId;
    private String network;

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    private SensorDTO parentSensor;
    private List<SensorDTO> childSensors;

    public SensorDTO(Sensor sensor) {
        this.id = sensor.getId();
        this.ip = sensor.getIp().toString();
        this.network = sensor.getIp().getNetwork(sensor.getMask());
        this.mask = sensor.getMask();
        line = new ArrayList<String>();
        for (Ip l : sensor.getLine()) {
            line.add(l.toString());
        }
        parentId = sensor.getId();
        if (sensor.getParentSensor() != null) {
            parentSensor = new SensorDTO(sensor.getParentSensor());
        }
        if (sensor.getChildSensors() != null) {
            childSensors = new ArrayList<SensorDTO>();
            for (Sensor childSensor : sensor.getChildSensors()) {
                childSensors.add(new SensorDTO(childSensor));
            }
        }

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public SensorDTO getParentSensor() {
        return parentSensor;
    }

    public void setParentSensor(SensorDTO parentSensor) {
        this.parentSensor = parentSensor;
    }

    public List<SensorDTO> getChildSensors() {
        return childSensors;
    }

    public void setChildSensors(List<SensorDTO> childSensors) {
        this.childSensors = childSensors;
    }

    public SensorDTO() {

    }
}
