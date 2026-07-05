package com.project.dns.dns;

import java.net.DatagramSocket;
import java.net.SocketException;

// listen to udp messages
public class DnsServer {
    private DatagramSocket datagramSocket;

    public DnsServer() throws SocketException {
        datagramSocket = new DatagramSocket(8053);
    }
}
