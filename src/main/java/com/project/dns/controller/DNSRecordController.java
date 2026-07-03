package com.project.dns.controller;

import com.project.dns.entity.DnsRecord;
import com.project.dns.repository.DNSRecordRepository;
import com.project.dns.service.DNSRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DNSRecordController {
    private final DNSRecordService dnsRecordService;
    public DNSRecordController(DNSRecordService dnsRecordService){
        this.dnsRecordService = dnsRecordService;
    }

    @PostMapping("/record")
    public DnsRecord saveRecord(@RequestBody DnsRecord dnsRecord){ // converts json to dnsRecord object
        return dnsRecordService.saveRecord(dnsRecord);
    }
}
