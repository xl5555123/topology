import org.junit.Test;
import org.serc.topology.exception.UnregularIpException;
import org.serc.topology.model.Ip;

import static org.junit.Assert.assertEquals;

/**
 * Created by Xander on 2017/3/19.
 */
public class IpTest {

    @Test
    public void testGetNetwork() throws UnregularIpException {
        Ip ip = new Ip("192.168.255.3");
        assertEquals("192.168.255.0", ip.getNetwork(24));
        assertEquals("192.168.254.0", ip.getNetwork(23));
        assertEquals("192.168.252.0", ip.getNetwork(22));
        assertEquals("192.168.255.2", ip.getNetwork(31));
    }
}
