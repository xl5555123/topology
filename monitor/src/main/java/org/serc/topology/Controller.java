package org.serc.topology;

import org.serc.topology.exception.BadRequestException;
import org.serc.topology.exception.UnregularIpException;
import org.serc.topology.model.Sensor;
import org.serc.topology.model.transform.AddSensorDTO;
import org.serc.topology.model.transform.HostDTO;
import org.serc.topology.model.transform.SensorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Xander on 2017/3/10.
 */
@RestController
@EnableAutoConfiguration
public class Controller {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private HostService hostService;

    public final static int SENSOR_QUERING_PORT = 8080;

    @RequestMapping(value = "/sensors", method = RequestMethod.GET)
    public List<SensorDTO> getSensors() {
        List<SensorDTO> ret = new ArrayList<SensorDTO>();
        for (Sensor sensor : sensorService.getSensors()) {
            ret.add(new SensorDTO(sensor));
        }
        return ret;
    }

    @RequestMapping(value = "/sensors/{sensorId}", method = RequestMethod.GET)
    public String getSensorParentIpAndPort(@PathVariable String sensorId) {
        List<Sensor> sensors = sensorService.getSensors();
        for (Sensor sensor : sensors) {
            if (sensor.getId().equals(sensorId) && sensor.getParentId() != Sensor.ROOT_ID) {
                return String.format("http://%s:%d", sensor.getParentSensor().getIp().toString(), SENSOR_QUERING_PORT);
            }
        }
        return null;
    }

    @RequestMapping(value = "/sensors", method = RequestMethod.POST)
    public String addSensor(@RequestBody AddSensorDTO sensor) {
        try {
            return sensorService.addSensor(sensor);
        } catch (UnregularIpException e) {
            throw new BadRequestException();
        }
    }

    @RequestMapping(value = "/sensors/{sensorId}/hosts", method = RequestMethod.POST)
    public void collectHosts(@RequestBody List<HostDTO> hosts, @PathVariable String sensorId) {
        hostService.addHost(sensorId, hosts);
    }

    @RequestMapping(value = "/hosts", method = RequestMethod.GET)
    public List<HostDTO> get() {
        return hostService.getHost();
    }
}
