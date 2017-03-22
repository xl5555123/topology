package org.serc.topology.model.transform;

import org.serc.topology.model.Sensor;

import java.util.Date;
import java.util.List;

/**
 * Created by Xander on 2017/3/20.
 */
public class HostDTO {
    private String inner_interface;
    private String outer_interface;
    private String gateway;
    private List<ServiceDTO> services;
    private String mac;
    private String name;
    private String sensorName;

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    private Date updated;

    public String getName(String sensorId) {
        return String.format("%s_%s", sensorId, inner_interface).replace(".", "_").replace("-", "_");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public HostDTO() {

    }

    public String getInner_interface() {
        return inner_interface;
    }

    public void setInner_interface(String inner_interface) {
        this.inner_interface = inner_interface;
    }

    public String getOuter_interface() {
        return outer_interface;
    }

    public void setOuter_interface(String outer_interface) {
        this.outer_interface = outer_interface;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public List<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }
}
