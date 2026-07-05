package com.project.dns.dns;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

// listen to udp messages
public class DnsServer {
    private DatagramSocket datagramSocket;

    public DnsServer() throws SocketException {
        datagramSocket = new DatagramSocket(8053);
    }

    // storage for the incoming datagram
    private byte[] buffer = new byte[512];

    // buffer only contains the data, we need to also store the additional info such as sender ip, sender address,length etc.
    // we use datagram packet as the envelope that contains buffer and also the additional info

    public void start() throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(datagramPacket);
        System.out.println("Packet received!");
    }
}
