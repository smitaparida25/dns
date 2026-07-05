package com.project.dns.dns;

import com.project.dns.entity.DnsRecord;
import com.project.dns.repository.DNSRecordRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

@Component
// listen to udp messages
public class DnsServer {
    private final DNSRecordRepository dnsRecordRepository;
    private final DatagramSocket datagramSocket;

    public DnsServer(DNSRecordRepository dnsRecordRepository) throws SocketException {
        this.dnsRecordRepository = dnsRecordRepository;
        datagramSocket = new DatagramSocket(8053);
    }
    // storage for the incoming datagram
    private byte[] buffer = new byte[512];

    // buffer only contains the data, we need to also store the additional info such as sender ip, sender address,length etc.
    // we use datagram packet as the envelope that contains buffer and also the additional info

    public void start() throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        while(true) {
            datagramSocket.receive(datagramPacket);
            System.out.println("Packet received!");
            StringBuilder domainName = new StringBuilder();
            byte[] data = datagramPacket.getData();
            int i = 12;
            while(true){
                int len = data[i] & 0xFF;
                if(len == 0){
                    break;
                }
                for(int j = 1; j<=len; j++){
                    char curr = (char) (data[j+i] & 0xFF);
                    domainName.append(curr);
                }
                domainName.append('.');
                i = i + len + 1;
            }
            if (domainName.length() > 0) {
                domainName.deleteCharAt(domainName.length() - 1);
            }
            System.out.println(domainName.toString());
            DnsRecord record = dnsRecordRepository.findByDomainName(domainName.toString());
            if (record == null) {
                System.out.println("Domain not found");
                continue;
            }
            System.out.println(record.getIpAddress());

            byte[] response = new byte[512];

            // first two bytes are same
            response[0] = data[0];
            response[1] = data[1];

            // flag which tells the sender that this is a successful response
            response[2] = (byte) 0x81;
            response[3] = (byte) 0x80;

            // question count
            response[4] = 0;
            response[5] = 1;

            // answer count
            response[6] = 0;
            response[7] = 1;

            // as of right now no additional stuff
            response[8] = 0;
            response[9] = 0;
            response[10] = 0;
            response[11] = 0;

            // copy the question
           for(int j = 12; j<=i+4; j++){
               response[j] = data[j];
           }
           int index = i+5;
           // name of domain
            response[index++] = (byte) 0xC0;
            response[index++] = 0x0C;

            // type of record -> ipv4 -> A record
            response[index++] = 0;
            response[index++] = 1;

            // what network class -> in (internet)
            response[index++] = 0;
            response[index++] = 1;

            // ttl
            response[index++] = 0;
            response[index++] = 0;
            response[index++] = 0;
            response[index++] = 60;

            // Rdlength -> total byte of answer -> for ipv4 -> 4 (x.y.z.q)
            response[index++] = 0;
            response[index++] = 4;

            // ip address
            String ip = record.getIpAddress();
            String[] parts = ip.split("\\.");

            for (String part : parts) {
                response[index++] = (byte) Integer.parseInt(part);
            }

        }
    }
}
