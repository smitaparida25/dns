package com.project.dns.repository;

import com.project.dns.entity.DnsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DNSRecordRepository extends JpaRepository<DnsRecord, Long> {
    // a method is declared here if it's custom and not one of these
    // save()
    //findById()
    //findAll()
    //delete()
    //deleteById()
    //existsById()
    //count()

    DnsRecord findByDomainName(String domainName);
}
