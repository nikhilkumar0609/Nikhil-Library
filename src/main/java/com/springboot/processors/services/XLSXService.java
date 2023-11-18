package com.springboot.processors.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.springboot.processors.utilities.XLSXUtil;

@Service
public class XLSXService {
	
	private static final Logger LOG = LoggerFactory.getLogger(XLSXService.class);
	
	String dstFilePath = "D:\\Testing";
	
	
	public void readXLSXFile() {
		
		String[] headerTokens = { "id", "Name", "Age", "City" };
		
		List<File> filesInFolder = new ArrayList<>();
		
		//Reading all files from local folder
		try(Stream<Path> filesStream = Files.walk(Paths.get(dstFilePath))){
			filesInFolder = filesStream.filter(Files::isRegularFile).map(Path::toFile).toList();
			
			//folder is empty
			if(filesInFolder.isEmpty()) {
				LOG.debug("No files are available in the local folder. Path : {}", dstFilePath);
			}
			
			//files are available
			for(File file : filesInFolder) {
				Path filePath = Paths.get(dstFilePath, file.getName());
				String filepath = filePath.toString();
				File filename = new File(filepath);
				
				XLSXUtil.readXLSXFile(filename, headerTokens, 1);
			}
		}catch(Exception e){
			LOG.error("Error while processing files in folder : {}", dstFilePath, e);
		}
	}

	public void writeToXlsx() throws IOException {
		
		LocalDate todayDate = LocalDate.now();
        String fileName = "write_testing" + todayDate + ".xlsx";
		String filePath = dstFilePath + File.separatorChar + fileName;
		
		String[] headerTokens = { "id", "Name", "Age", "City" };
		
		ArrayList<HashMap<String,String>> rowsList = new ArrayList();
		
		rowsList.add(createRowData(1, "Nikhil Kumar", 25, "Bangalore"));
		rowsList.add(createRowData(2, "Rupam Kumari", 24, "Pune"));
		
		XLSXUtil.writeToXLSXFile(headerTokens, rowsList, fileName, filePath);
		LOG.info("File Created");
	}
	
	public HashMap<String, String> createRowData(int id, String name, int age, String city) {
        HashMap<String, String> data = new HashMap<>();
        data.put("Id", String.valueOf(id));
        data.put("Name", name);
        data.put("Age", String.valueOf(age));
        data.put("City", city);
        return data;
    }
}
