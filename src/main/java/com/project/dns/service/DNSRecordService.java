package com.project.dns.service;

import com.project.dns.entity.DnsRecord;
import com.project.dns.repository.DNSRecordRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class DNSRecordService {
    private final DNSRecordRepository repository; // creating the variable just like String name;
    // need to initialised the var as it's a final var. and we can't do it in with new keyword because it's an interface.

    // so we create a constructor
    // if we're using a repo inside service, spring pass it as parameter for creating the service object
    public DNSRecordService(DNSRecordRepository repository){
        this.repository = repository;
    }

    public DnsRecord saveRecord(DnsRecord dnsRecord){
            if (dnsRecord == null) throw new IllegalArgumentException("Dns record cannot be null");
            if (dnsRecord.getDomainName() == null) throw new IllegalArgumentException("Domain name canoot be null");
            if (dnsRecord.getIpAddress() == null) throw new IllegalArgumentException("Ip address cannot be null");

            DnsRecord existing = repository.findByDomainName(dnsRecord.getDomainName());

            if (existing != null) {
                throw new IllegalArgumentException("domain already exists");
            }
            return repository.save(dnsRecord);
    }


}
