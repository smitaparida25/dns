package com.project.dns;

import com.project.dns.dns.DnsServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.SocketException;

@SpringBootApplication
public class DnsApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(DnsApplication.class, args);
	}
}
