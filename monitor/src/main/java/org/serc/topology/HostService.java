package org.serc.topology;

import org.serc.topology.model.Sensor;
import org.serc.topology.model.transform.HostDTO;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Xander on 2017/3/20.
 */
@org.springframework.stereotype.Service
public class HostService {

    public final static Logger LOGGER = Logger.getLogger(HostService.class.toString());
    private Map<String, List<HostDTO>> sensorHosts = new HashMap<String, List<HostDTO>>();

    public void addHost(String sensorId, List<HostDTO> hosts) {
        LOGGER.info(String.format("Sensor %s update its hosts.The number of the hosts is %d", sensorId, hosts == null ? 0 : hosts.size()));
        Date cur = new Date();
        if (hosts != null) {
            for (HostDTO hostDTO : hosts) {
                hostDTO.setUpdated(cur);
            }
        }
        sensorHosts.put(sensorId, hosts);
    }

    public void removeHostsBySensorId(String sensorId) {
        if (sensorHosts.containsKey(sensorId)) {
            sensorHosts.remove(sensorId);
        }
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
