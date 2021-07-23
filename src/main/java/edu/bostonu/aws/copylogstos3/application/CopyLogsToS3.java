package edu.bostonu.aws.copylogstos3.application;

import java.io.File;
import java.util.List;

import edu.bostonu.aws.copylogstos3.repositories.LogsRepository;
import edu.bostonu.aws.copylogstos3.repositories.S3Repository;

public class CopyLogsToS3 {
	
	private static String filePath = "D:/radiantone/vds/vds_server/logs/vds_server_archive";

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<String> fileList = LogsRepository.getLogsList(filePath);
		
		S3Repository awsRepository = new S3Repository("radiant_user", "us-west-2");
		
		
		List<String> archivedFiles = awsRepository.getBucketFiles("vds-logs-archive", "vds-server");
		
		fileList.removeAll(archivedFiles);
		
		
		for (String filename : fileList) {
			
			File file = new File(filePath + "/" + filename);
			
			awsRepository.copyFileToS3("vds-logs-archive", "vds-server", file);
			
		}
		
		
		


	}

}
