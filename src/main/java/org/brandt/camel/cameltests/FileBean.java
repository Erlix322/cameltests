package org.brandt.camel.cameltests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component("fileReader")
public class FileBean {

	
	public void read(File file) {
		System.out.println("Read: " + file.getName());
	}
	
	public void changeFile(File file) throws IOException {
		FileWriter fw = new FileWriter(file);
		fw.append('l');	
	}
}
