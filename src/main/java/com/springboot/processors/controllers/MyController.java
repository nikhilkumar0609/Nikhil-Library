package com.springboot.processors.controllers;

import com.springboot.processors.response.JsonResponse;
import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.processors.services.PdfService;
import com.springboot.processors.services.XLSXService;

@Controller
@RequestMapping("/pdf")
public class MyController {
	
	@Autowired
	private PdfService pdfService;
	
	@Autowired
	private XLSXService xlsxService;
	
	@GetMapping("/createPdf")
	public ResponseEntity<InputStreamResource> createPdf() {
		ByteArrayInputStream pdf = pdfService.createPdf();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition", "inline;file=nikhil.pdf");
		
		return ResponseEntity.ok()
				.headers(httpHeaders)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(pdf));
	}
	
	@GetMapping("/readXlsx")
	public ResponseEntity<JsonResponse> readXlsx() {
		try {
            xlsxService.readXLSXFile();
            // If the file is read successfully
            JsonResponse response = new JsonResponse(HttpStatus.OK, 200, "File read successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            // If an error occurs during file reading
            JsonResponse response = new JsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Error reading file");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
	}
	
	@GetMapping("/writeToXlsx")
	public ResponseEntity<JsonResponse> writeToXlsx() {
		try {
            xlsxService.writeToXlsx();
            // If the file is read successfully
            JsonResponse response = new JsonResponse(HttpStatus.OK, 200, "File read successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            // If an error occurs during file reading
            JsonResponse response = new JsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Error reading file");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
	}
	
}
