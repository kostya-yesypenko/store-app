package com.kostik.store.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kostik.store.domain.Category;
import com.kostik.store.domain.Product;
import com.kostik.store.exception.ExcelParseException;
import com.kostik.store.repository.ProductRepository;

@Service
public class ExcelService {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Name", "Price", "Quantity", "Category_id" };
	static String PRODUCT_SHEET = "Products";

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductService productService;

	public void process(MultipartFile file) throws ExcelParseException {
		if (file == null || file.getSize() == 0) {
			throw new ExcelParseException("Empty Excel file: " + file.getOriginalFilename());
		}
		if (!hasExcelFormat(file)) {
			throw new ExcelParseException("Not an Excel file: " + file.getOriginalFilename());
		}
		try {
			List<Product> products = getProductsFromExcel(file);
			productRepository.saveAll(products);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcelParseException("Failed to store excel data: " + e.getMessage());
		}
	}

	public boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	private List<Product> getProductsFromExcel(MultipartFile file) throws ExcelParseException {
		try (InputStream is = file.getInputStream()) {
			Workbook workbook = WorkbookFactory.create(is);
			Sheet sheet = workbook.getSheet(PRODUCT_SHEET);
			Iterator<Row> rows = sheet.iterator();

			List<Product> products = new ArrayList<Product>();

			var categories = categoryService.getCategories();
			var allProducts = productService.getProducts();

			Map<String, Category> categoryByName = categories.stream().collect(Collectors.toMap(c -> c.getName(), c -> c));
			Map<String, Product> productByName = allProducts.stream().collect(Collectors.toMap(c -> c.getName().toLowerCase(), c -> c));

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Product product = new Product();

				int cellIdx = 0;

				String productName = currentRow.getCell(cellIdx++).getStringCellValue();

				// skip already existing products
				if (!productByName.containsKey(productName.toLowerCase())) {
					product.setName(productName);
					product.setPrice(currentRow.getCell(cellIdx++).getNumericCellValue());
					product.setQty((long) currentRow.getCell(cellIdx++).getNumericCellValue());

					String categoryName = currentRow.getCell(cellIdx++).getStringCellValue();

					if (!categoryByName.containsKey(categoryName)) {
						throw new ExcelParseException("Category missing: " + categoryName);
					}
					product.setCategory(categoryByName.get(categoryName));

					products.add(product);
				}
			}

			workbook.close();

			return products;
		} catch (IOException e) {
			throw new ExcelParseException("fail to parse Excel file: " + e.getMessage());
		}
	}

	public byte[] exportProducts() throws Exception {
        try (Workbook workbook = new SXSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Products");

            // Fetch products from the database
            List<Product> products = productRepository.findAll();

            int rowCount = 0;

            // Write header row
            Row headerRow = sheet.createRow(rowCount++);
            headerRow.createCell(0).setCellValue("Id");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Price");
            headerRow.createCell(3).setCellValue("Quantity");
            headerRow.createCell(4).setCellValue("Category");

            // Write product data
            for (Product product : products) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getPrice());
                row.createCell(3).setCellValue(product.getQty());
                row.createCell(4).setCellValue(product.getCategory().getName());
            }

            // Write the workbook to the Excel file  
            byte[] bytes = null;
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                bytes = outputStream.toByteArray();
            }    

            System.out.println("Excel file exported successfully.");
            return bytes;

        } catch (IOException e) {
            throw new Exception("Failed to export Excel file: " + e.getMessage());
        }
    }
}
