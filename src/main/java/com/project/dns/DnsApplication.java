package com.project.dns;

import com.project.dns.dns.DnsServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.SocketException;

@SpringBootApplication
public class DnsApplication implements CommandLineRunner {
	private final DnsServer dnsServer;

	public DnsApplication(DnsServer dnsServer) {
		this.dnsServer = dnsServer;
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(DnsApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Thread dnsThread = new Thread(() -> {
			try {
				dnsServer.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		dnsThread.setDaemon(true);
		dnsThread.start();
	}
}
