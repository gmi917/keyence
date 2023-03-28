package com.joytech.keyence.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.joytech.keyence.bean.excelData;
import com.joytech.keyence.service.KeyenceService;


@RestController
public class keyenceController {
	@Autowired
    private KeyenceService keyenceService;
	@RequestMapping(value = "/hello")
	public String hello(HttpServletResponse response) throws IOException {
//		System.out.println("system hello");
		PrintWriter out = response.getWriter();
		out.println("hello");
		return null;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
	    try {
	        // 檢查上傳的檔案是否為 Excel 檔案
	        if (!file.getContentType().equals("application/vnd.ms-excel") &&
	            !file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
	            throw new Exception("檔案格式不正確，請上傳 Excel 檔案");
	        }

	        // 讀取 Excel 檔案中的資料
	        InputStream inputStream = file.getInputStream();
	        Workbook workbook = new XSSFWorkbook(inputStream);
	        Sheet sheet = workbook.getSheetAt(0);

	        List<excelData> dataList = new ArrayList<>();
	        for (Row row : sheet) {
	        	excelData data = new excelData();
	            data.setField1(row.getCell(0).getStringCellValue());
	            data.setField2(row.getCell(1).getNumericCellValue());
	            dataList.add(data);
	        }

	        // 寫資料庫
	        keyenceService.saveDataList(dataList);

	        // 回應使用者操作成功
	        model.addAttribute("successMessage", "資料已成功上傳");
	    } catch (Exception e) {
	        // 回應使用者操作失敗
	        model.addAttribute("errorMessage", "資料上傳失敗：" + e.getMessage());
	    }
	    return "uploadResult";
	}
	
	

}
