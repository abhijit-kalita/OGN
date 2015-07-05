package com.ogn.financing.hystrix.java.example;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:7000")
@DirtiesContext
public class HystrixWebserviceTest {

	@Value("${local.server.port}")
	private int port;

	@Test
	public void hasHalLinks() throws Exception {
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
				"http://localhost:" + this.port + "/employees/1", String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(entity.getBody(), startsWith("{\"id\":1,\"firstName\":\"First\""
				+ ",\"lastName\":\"Employee\""));
		assertThat(entity.getBody(), containsString("_links\":{\"self\":{\"href\""));
	}
	
	@Test
	public void testfallBack() throws Exception {
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
				"http://localhost:" + this.port + "/employees/booleanString/notFail", String.class);
		assertNotNull("response was null", entity);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(entity.getBody(), containsString("First"));
		ResponseEntity<String> response = new TestRestTemplate().getForEntity(
				"http://localhost:" + this.port + "/employees/booleanString/fail", String.class);
		assertNotNull("response was null", response);
		assertEquals("Bad status code", HttpStatus.OK, response.getStatusCode());
		assertThat(response.getBody(), containsString("Test"));
	}
	

	@Test
	public void hystrixStreamWorks() throws Exception {
		String url = "http://localhost:" + port;
		System.out.println(url);
		//you have to hit a Hystrix circuit breaker before the stream sends anything
		ResponseEntity<String> response = new TestRestTemplate().getForEntity(
				"http://localhost:" + this.port + "/employees/1", String.class);
		
		assertEquals("bad response code", HttpStatus.OK, response.getStatusCode());

		URL hystrixUrl = new URL(url + "/hystrix.stream");
		InputStream in = hystrixUrl.openStream();
		byte[] buffer = new byte[1024];
		in.read(buffer);
		String contents = new String(buffer);
		assertTrue(contents.contains("ping"));
		in.close();	
	}
	
	@Test
	public void dashboardTest() throws Exception {
		ResponseEntity<String> entity = null;
		for(int i=1;i<5000;i++){
			System.out.println("Times" +i);
			 entity = new TestRestTemplate().getForEntity(
					"http://localhost:7070/employees/1", String.class);	
			 System.out.println(entity.getStatusCode());
		}
		
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
		
	}
}
