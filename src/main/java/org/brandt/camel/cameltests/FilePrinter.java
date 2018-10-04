package org.brandt.camel.cameltests;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilePrinter extends RouteBuilder {

	
	@Autowired
	FileBean fileBean;
	
	@Override
	public void configure() throws Exception {
		
		from("file://{{route.input}}?delete=false")
		.bean(fileBean, "changeFile")	
		.to("file://out")
		.to("direct:MyRoute");
				
		
		from("direct:MyRoute")
			.log("MyRoute test: ${body.fileName}");
		
	}

}
