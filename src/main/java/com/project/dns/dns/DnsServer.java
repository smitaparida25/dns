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
            }else{
            System.out.println(record.getIpAddress());
            }
        }
    }
}
