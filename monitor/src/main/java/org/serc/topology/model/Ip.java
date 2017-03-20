package org.serc.topology.model;

import org.serc.topology.exception.UnregularIpException;

/**
 * Created by Xander on 2017/3/15.
 */
public class Ip {

    private int[] ip;

    public Ip(String ip) throws UnregularIpException {
        this.ip = new int[4];
        if (!ip.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
            throw new UnregularIpException(ip);
        }
        String[] split = ip.split("\\.");
        for (int i = 0; i < 4; i++) {
            this.ip[i] = Integer.valueOf(split[i]);
        }
    }

    public String getNetwork(int mask) {
        int n = mask;
        int a = n / 8;
        int b = n - 8 * a;
        int c = 0;
        for (int i = 0; i < b; i++) {
            c += (int) Math.pow(2, 7 - i);
        }
        int[] newIp = new int[4];
        for (int i = 0; i < 4; i++) {
            newIp[i] = ip[i];
        }
        newIp[a] = ip[a] & c;
        for (int i = a + 1; i < 4; i++) {
            newIp[i] = 0;
        }
        return String.format("%d.%d.%d.%d", newIp[0], newIp[1], newIp[2], newIp[3]);
    }

    public Ip(int first, int second, int third, int forth) {
        ip = new int[4];
        ip[0] = first;
        ip[1] = second;
        ip[2] = third;
        ip[3] = forth;
    }

    @Override
    public String toString() {
        return String.format("%d.%d.%d.%d", ip[0], ip[1], ip[2], ip[3]);
    }
}
