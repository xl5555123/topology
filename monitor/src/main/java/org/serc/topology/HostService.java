package org.serc.topology;

import org.serc.topology.model.Sensor;
import org.serc.topology.model.transform.HostDTO;
import org.springframework.stereotype.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xander on 2017/3/20.
 */
@org.springframework.stereotype.Service
public class HostService {
    private Map<String, List<HostDTO>> sensorHosts = new HashMap<String, List<HostDTO>>();

    public void addHost(String sensorId, List<HostDTO> hosts) {
        sensorHosts.put(sensorId, hosts);
    }

    public List<HostDTO> getHost() {
        List<HostDTO> result = new ArrayList<HostDTO>();
        for (String sensorId : sensorHosts.keySet()) {
            for (HostDTO host : sensorHosts.get(sensorId)) {
                host.setName(host.getName(sensorId));
                result.add(host);
            }
        }
        return result;
    }
}
