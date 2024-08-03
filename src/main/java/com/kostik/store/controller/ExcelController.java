package com.kostik.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kostik.store.exception.ExcelParseException;
import com.kostik.store.exception.ResponseMessage;
import com.kostik.store.service.ExcelService;

@CrossOrigin("http://localhost:3000")
@Controller
@RequestMapping("/api/excel")
public class ExcelController {

	@Autowired
	ExcelService excelService;

	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadExcelFile(@RequestParam("file") MultipartFile file) {
		String message = "";

		try {
			excelService.process(file);

			message = "Uploaded Excel file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (ExcelParseException e) {
			message = "Could not upload Excel file " + file.getOriginalFilename() + ": " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(message));
		}
	}
	
	@GetMapping("/download")
    public ResponseEntity<byte[]> downloadExcelFile() {
        try {
            byte[] fileContent = excelService.exportProducts();
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + getProductFilename())
                    .body(fileContent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new byte[0]);
        }
    }

	private String getProductFilename() {
		return "Products.xlsx";
	}
}
