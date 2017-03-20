package org.serc.topology.exception;

/**
 * Created by Xander on 2017/3/15.
 */
public class UnregularIpException extends Exception {
    private String ip;
    public UnregularIpException(String ip) {
        this.ip = ip;
    }
}
