package edu.bostonu.aws.copylogstos3.repositories;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class LogsRepository {
	
	public static List<String> getLogsList(String directoryName) {
		
	      File directoryPath = new File(directoryName);
	      String files[] = directoryPath.list();
	      
		return Arrays.asList(files);
		
	}

}
