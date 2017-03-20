package org.serc.topology;

import org.serc.topology.exception.UnregularIpException;
import org.serc.topology.model.Ip;
import org.serc.topology.model.Sensor;
import org.serc.topology.model.transform.AddSensorDTO;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Xander on 2017/3/15.
 */
@org.springframework.stereotype.Service
public class SensorService {

    public final static String MONITER_GATEWAY = "192.168.200.2";

    private Map<String, Sensor> sensors = new ConcurrentHashMap<String, Sensor>();

    public List<Sensor> getSensors() {
        calculateSensor();
        return new ArrayList<Sensor>(sensors.values());
    }

    public String addSensor(AddSensorDTO sensorDTO) throws UnregularIpException {
        Sensor sensor = new Sensor();
        sensor.setId(sensorDTO.getId());
        Ip ip = new Ip(sensorDTO.getIp());
        sensor.setIp(ip);
        sensor.setMask(sensorDTO.getMask());
        List<Ip> line = new ArrayList<Ip>();
        for (String router : sensorDTO.getLine()) {
            line.add(new Ip(router));
        }
        sensor.setLine(line);
        sensors.put(sensor.getId(), sensor);
        return sensor.getId();
    }

    private void addSensorToNetworkSensorMap(Sensor sensor, Map<String, List<Sensor>> ipSensorMap) {
        List<Sensor> ipSensors = ipSensorMap.get(sensor.getIp().getNetwork(sensor.getMask()));
        ipSensors = ipSensors == null ? new ArrayList<Sensor>() : ipSensors;
        ipSensors.add(sensor);
        ipSensorMap.put(sensor.getIp().toString(), ipSensors);
        sensors.put(sensor.getId(), sensor);
    }

    private void setSensorParent(Sensor sensor, Sensor parent, Map<String, List<Sensor>> networkSensorMap, Map<String, Boolean> visited) {
        sensor.setParentId(parent.getId());
        sensor.setParentSensor(parent);
        addSensorToNetworkSensorMap(sensor, networkSensorMap);
        visited.put(sensor.getId(), true);
    }

    public void calculateSensor() {
        Map<String, List<Sensor>> networkSensorMap = new HashMap<String, List<Sensor>>();
        int caledSensorCount = 0;
        Sensor rootSensor = null;
        Map<String, Boolean> visited = new ConcurrentHashMap<String, Boolean>();
        for (String sensorId : sensors.keySet()) {
            visited.put(sensorId, false);
        }
        while (caledSensorCount < sensors.keySet().size()) {
            int lastCount = caledSensorCount;
            for (String sensorId : sensors.keySet()) {
                Sensor sensor = sensors.get(sensorId);
                List<Ip> line = sensor.getLine();
                if (visited.get(sensorId)) {
                    continue;
                }
                if (line.size() == 0) {
                    setSensorParent(sensor, Sensor.ROOT, networkSensorMap, visited);
                    rootSensor = sensor;
                    caledSensorCount++;
                }
                else if (line.size() == 1 && rootSensor != null) {
                    setSensorParent(sensor, rootSensor, networkSensorMap, visited);
                    caledSensorCount++;
                }
                else if (line.size() > 1){
                    Ip parentIp = line.get(1);
                    List<Sensor> probableSensors = networkSensorMap.get(parentIp.getNetwork(sensor.getMask()));
                    if (probableSensors != null) {
                        for (Sensor probableSensor : probableSensors) {
                            List<Ip> parentLine = probableSensor.getLine();
                            if (parentLine.size() == line.size() - 1) {
                                boolean isSame = true;
                                for (int i = 1; i < line.size(); i++) {
                                    if (!parentLine.get(i - 1).toString().equals(line.get(i).toString())) {
                                        isSame = false;
                                        break;
                                    }
                                }
                                if (isSame) {
                                    setSensorParent(sensor, probableSensor, networkSensorMap, visited);
                                    caledSensorCount++;
                                }
                            }

                        }
                    }
                }
            }
            if (lastCount == caledSensorCount) {
                break;
            }
        }
    }
}
