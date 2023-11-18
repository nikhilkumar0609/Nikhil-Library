package com.springboot.processors.services;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PdfService {

	private static final Logger LOG = LoggerFactory.getLogger(PdfService.class);
	
	public ByteArrayInputStream createPdf() {
		LOG.info("Pdf Creation Started...");
		String title = "Welcome to My PDF";
		String content = "Nikhil Kumar";
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Document doc = new Document();
		PdfWriter.getInstance(doc, output);
		
		doc.open();
		
		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
		Paragraph titlePara = new Paragraph(title, titleFont);
		titlePara.setAlignment(Element.ALIGN_CENTER);
		doc.add(titlePara);
		
		Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 16);
		Paragraph contentPara = new Paragraph(content, contentFont);
		contentPara.add(new Chunk("Added while creating Content"));
		doc.add(contentPara);
		
		doc.close();
		
		return new ByteArrayInputStream(output.toByteArray());
	}
}
