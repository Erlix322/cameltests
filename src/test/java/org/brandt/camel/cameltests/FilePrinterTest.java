package org.brandt.camel.cameltests;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import junit.framework.TestCase;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@MockEndpoints
public class FilePrinterTest extends TestCase {

	@Value("${route.input}")
	private String input;
	
	@EndpointInject(uri = "mock:direct:MyRoute")
	protected MockEndpoint myRoute;
	
	
	@Autowired
	protected ProducerTemplate fileSource;
	
	
	
	@Test
	@DirtiesContext
	public void testFile() throws IOException, InterruptedException {
		File f = File.createTempFile("test", ".txt");		
		myRoute.expectedMessageCount(1);
		fileSource.sendBody("file:" + input, f);	
		myRoute.assertIsSatisfied();		
	}
	
	@Test
	@DirtiesContext
	public void changeModified() throws IOException {
		File f = File.createTempFile("test", ".txt");
		FileBean b = new FileBean();
		b.changeFile(f);
		FileReader fr = new FileReader(f);
		
		assertEquals('l', fr.read());
	}
	
}
