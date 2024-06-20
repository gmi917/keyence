package com.joytech.keyence.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.joytech.keyence.bean.BadReasons;
import com.joytech.keyence.bean.IpqcItem;
import com.joytech.keyence.bean.IpqcRecordModel;
import com.joytech.keyence.bean.JYT012;
import com.joytech.keyence.bean.MO;
import com.joytech.keyence.bean.ManufactureNo;
import com.joytech.keyence.bean.ManufactureOrder;
import com.joytech.keyence.bean.MocSearch;
import com.joytech.keyence.bean.ProcessCode;
import com.joytech.keyence.bean.QCProduct;
import com.joytech.keyence.bean.QCResult;
import com.joytech.keyence.service.B2BService;
import com.joytech.keyence.service.OnePage;

@RestController
public class keyenceController {
	@Autowired
    private B2BService b2bService;
	@RequestMapping(value = "/hello")
	public String hello(HttpServletResponse response) throws IOException {
//		System.out.println("system hello");
		PrintWriter out = response.getWriter();
		out.println("hello");
		return null;
	}
	//現場人員找尋未輸入剩餘資料記錄列表
	@RequestMapping(value = "/getIpqcRecord/{pqcCREATOR}")
	public ModelAndView getIpqcRecord(@PathVariable("pqcCREATOR") String pqcCREATOR) throws IOException, SQLException {		
		ModelAndView model = new ModelAndView();
		ArrayList<QCProduct> allQCRecord=b2bService.getIpqcPrtoduct();
		model.setViewName("allQCRecord");
		model.addObject("allQCRecord", allQCRecord);
		model.addObject("pqcCREATOR", pqcCREATOR);
		return model;		
	}	
	//現場人員找尋首件/自主檢查記錄詳細資料
	@RequestMapping(value = "/getTestSpec/{qc_id}/{ipqcCREATOR}", method = RequestMethod.GET)
	public ModelAndView getEngineeringSpec(@PathVariable("qc_id") String qc_id,
			@PathVariable("ipqcCREATOR") String ipqcCREATOR) throws IOException, SQLException {
		
		ModelAndView model = new ModelAndView();
		LocalDate todaysDate = LocalDate.now();
		JYT012 staffData=b2bService.getIpqcData(qc_id);
		ArrayList<IpqcItem> finishedIpqc=b2bService.getIpqcTestData(qc_id);
		model.setViewName("testSpec");
		model.addObject("staffData", staffData);
		model.addObject("finishedIpqc", finishedIpqc);
		model.addObject("ipqcCREATOR", ipqcCREATOR);
		model.addObject("todaysDate", todaysDate);			
		
		return model;
    }
	//現場人員update首件/自主檢查記錄實測狀況
	@RequestMapping(value = "/updSpec/{qc_id}/{ipqcCREATOR}", method = RequestMethod.POST)
	public ModelAndView updSpec(@PathVariable("qc_id") String qc_id,
			@PathVariable("ipqcCREATOR") String ipqcCREATOR,IpqcRecordModel ipqc,JYT012 jyt012,HttpServletResponse response,HttpServletRequest request) throws IOException, SQLException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String strJSON = new Gson().toJson(ipqc);	
		JSONObject root = new JSONObject(strJSON);
		JSONArray a = root.getJSONArray("ipqcRecords");
		
        for (int i = 0; i < a.length(); i++) {
        	 JSONObject m = (JSONObject) a.get(i);
        	 b2bService.updIpqcData(qc_id, m.get("itemSN").toString(), m.get("ipqc1").toString(), m.get("ipqc2").toString(), m.get("ipqc3").toString(),ipqcCREATOR);
        }
      //回寫現場人員檢驗剩餘項目完成flag為1
        b2bService.updIpqcStatus(qc_id);
		out.println("<script language=\"javascript\">");
		out.print("alert(\"新增資料成功\");location.href=\""+request.getContextPath()+"/getIpqcRecord/"+ipqcCREATOR+"\";");
		out.println("</script>");
		return null;		
	}	
	
	
	//品檢人員找尋未輸入剩餘資料記錄列表
		@RequestMapping(value = "/getPqcRecord/{pqcCREATOR}")
		public ModelAndView getPqcRecord(@PathVariable("pqcCREATOR") String pqcCREATOR) throws IOException, SQLException {		
			ModelAndView model = new ModelAndView();
			ArrayList<QCProduct> allQCRecord=b2bService.getPqcPrtoduct();
			model.setViewName("allFinishedRecord");
			model.addObject("allQCRecord", allQCRecord);
			model.addObject("pqcCREATOR", pqcCREATOR);
			return model;		
		}	
	
	@RequestMapping(value = "/getQCRecord/{pqcCREATOR}")
	public ModelAndView getQCRecord(@PathVariable("pqcCREATOR") String pqcCREATOR,MO mo,HttpServletResponse response,HttpServletRequest request) throws IOException, SQLException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		ModelAndView model = new ModelAndView();
		if(mo.getManufactureNo()!=null && !mo.getManufactureNo().equals("") && mo.getManufactureOrder()!=null && !mo.getManufactureOrder().equals("")) {
//			ArrayList<QCProduct> allQCRecord=b2bService.getQCPrtoduct();
			model.setViewName("allQCRecord");
//			model.addObject("allQCRecord", allQCRecord);
			model.addObject("pqcCREATOR", pqcCREATOR);
			return model;
		}else {
			out.println("<script language=\"javascript\">");
			out.print("alert(\"製令單別和製令單號皆需要點選\");location.href=\""+request.getContextPath()+"/allQCProduct/"+pqcCREATOR+"\";");
			out.println("</script>");
			return null;
		}	
	}
	//品檢人員找尋首件/自主檢查記錄詳細資料
	@RequestMapping(value = "/getFinishedRecordData/{qc_id}/{pqcCREATOR}", method = RequestMethod.GET)
    public ModelAndView getFinishedRecordData(@PathVariable("qc_id") String qc_id,@PathVariable("pqcCREATOR") String pqcCREATOR,HttpServletResponse response,HttpServletRequest request) throws IOException, SQLException {
		response.setContentType("text/html;charset=utf-8");
		ModelAndView model = new ModelAndView();
		PrintWriter out = response.getWriter();
		if(qc_id!=null && !qc_id.equals("")) {
			LocalDate todaysDate = LocalDate.now();
			JYT012 finishedQC=b2bService.getQCData(qc_id);
			ArrayList<IpqcItem> finishedPqc=b2bService.getQCTestData(qc_id);
			ArrayList<BadReasons> badReasons=b2bService.getBadReasons();
			model.setViewName("finishedTestSpec");
			model.addObject("finishedQC", finishedQC);
			model.addObject("finishedPqc", finishedPqc);
			model.addObject("badReasons", badReasons);
			model.addObject("qc_id", qc_id);
			model.addObject("todaysDate", todaysDate);
			model.addObject("pqcCREATOR", pqcCREATOR);
		}else {
			out.println("<script language=\"javascript\">");
			out.print("alert(\"資料有誤,查詢失敗;\");location.href=\""+request.getContextPath()+"/allQCProduct/"+pqcCREATOR+"\";");
			out.println("</script>");
			return null;
		}
        return model;
	}
	//品檢人員update首件/自主檢查記錄實測狀況
	@RequestMapping(value = "/updFinishedSpec/{qc_id}/{pqcCREATOR}", method = RequestMethod.POST)
	public ModelAndView updFinishedSpec(@PathVariable("qc_id") String qc_id,@PathVariable("pqcCREATOR") String pqcCREATOR,String myNumberInput,IpqcRecordModel pqc,String badReasons,String supervisor,String inspectors,String handleResult,HttpServletResponse response,HttpServletRequest request) throws IOException, SQLException {
		response.setContentType("text/html;charset=utf-8");
		ModelAndView model = new ModelAndView();
		PrintWriter out = response.getWriter();
		if(qc_id!=null && !qc_id.equals("")) {
			String strJSON = new Gson().toJson(pqc);
			JSONObject root = new JSONObject(strJSON);
			JSONArray a = root.getJSONArray("ipqcRecords");
			for (int i = 0; i < a.length(); i++) {
				JSONObject m = (JSONObject) a.get(i);
				b2bService.updPqcData(qc_id,m.get("itemSN").toString(), m.get("ipqc1").toString(),m.get("ipqc2").toString(),m.get("ipqc3").toString());
			}			
			System.out.println("myNumberInput="+myNumberInput);
			if(myNumberInput!=null && !myNumberInput.equals("") && myNumberInput.equals("0")) {
				b2bService.insPqcResult(qc_id,badReasons, "合格", supervisor, inspectors, handleResult, pqcCREATOR);
			}else {
				b2bService.insPqcResult(qc_id,badReasons, "不合格", supervisor, inspectors, handleResult, pqcCREATOR);
			}
			
			//回寫品檢檢驗flag為1
			b2bService.updPqcFlag(qc_id);
			//回寫品檢檢驗剩餘項目完成flag為1
			b2bService.updPqcTMXFlag(qc_id);
			
			out.println("<script language=\"javascript\">");
			out.print("alert(\"新增資料成功\");location.href=\""+request.getContextPath()+"/getPqcRecord/"+pqcCREATOR+"\";");
			out.println("</script>");
			return null;	
		}else{
			out.println("<script language=\"javascript\">");
			out.print("alert(\"資料有誤,新增失敗;\");location.href=\""+request.getContextPath()+"/getPqcRecord/"+pqcCREATOR+"\";");
			out.println("</script>");
			return null;
		}
				
	}
	
	@RequestMapping(value ="/showHistory/{pageNum}", method= RequestMethod.GET)
    public ModelAndView showHistory(@PathVariable("pageNum") String pageNum) throws SQLException {
		ModelAndView model = new ModelAndView();
		OnePage onepage = new OnePage();
		String beginPageNum="",endPageNum="",NextPage="",PrevPage="",curpage="";
        ArrayList<QCProduct> qcProductList = b2bService.getQCProductByPage();
        ArrayList data = onepage.OnePage(qcProductList, pageNum);
        //呈現分頁的資料
        endPageNum = String.valueOf(onepage.getendPageNum()); //結束的頁碼
        beginPageNum = String.valueOf(onepage.getbeginPageNum()); //開始的頁碼
        NextPage = String.valueOf(onepage.getNextPage()); //下一頁
        PrevPage = String.valueOf(onepage.getPrevPage()); //上一頁
        curpage = String.valueOf(onepage.getCurpage()); //目前頁數
        if(PrevPage!=null && !PrevPage.equals("") && !PrevPage.equals("null")) {
        	model.addObject("PrevPage", PrevPage);
        }else {
        	model.addObject("PrevPage", "1");
        }
        if(NextPage!=null && !NextPage.equals("") && !NextPage.equals("null") && NextPage.equals("1")) {
        	model.addObject("NextPage", "1");
        }else if(NextPage!=null && !NextPage.equals("") && !NextPage.equals("null")) {
        	model.addObject("NextPage", NextPage);
        }else {
        	model.addObject("NextPage", curpage);
        }
        model.addObject("data", data);
        model.addObject("endPageNum", endPageNum);
        model.addObject("beginPageNum", beginPageNum);                
        model.setViewName("showHistory");       
        return model;
    }
	
	@RequestMapping(value ="/showHistoryV2/{pageNum}", method= RequestMethod.GET)
    public ModelAndView showHistoryV2(@PathVariable("pageNum") String pageNum) throws SQLException {
		ModelAndView model = new ModelAndView();
		OnePage onepage = new OnePage();
		String beginPageNum="",endPageNum="",NextPage="",PrevPage="",curpage="";
        ArrayList<QCProduct> qcProductList = b2bService.getQCProductByPage();
        ArrayList data = onepage.OnePage(qcProductList, pageNum);
      //呈現分頁的資料
        endPageNum = String.valueOf(onepage.getendPageNum()); //結束的頁碼
        beginPageNum = String.valueOf(onepage.getbeginPageNum()); //開始的頁碼
        NextPage = String.valueOf(onepage.getNextPage()); //下一頁
        PrevPage = String.valueOf(onepage.getPrevPage()); //上一頁
        curpage = String.valueOf(onepage.getCurpage()); //目前頁數
        if(PrevPage!=null && !PrevPage.equals("") && !PrevPage.equals("null")) {
        	model.addObject("PrevPage", PrevPage);
        }else {
        	model.addObject("PrevPage", "1");
        }
        if(NextPage!=null && !NextPage.equals("") && !NextPage.equals("null") && NextPage.equals("1")) {
        	model.addObject("NextPage", "1");
        }else if(NextPage!=null && !NextPage.equals("") && !NextPage.equals("null")) {
        	model.addObject("NextPage", NextPage);
        }else {
        	model.addObject("NextPage", curpage);
        }
        model.addObject("data", data);
        model.addObject("endPageNum", endPageNum);
        model.addObject("beginPageNum", beginPageNum);                
        model.setViewName("showHistoryV2");       
        return model;
    }
	
	@RequestMapping(value ="/getHistoryData/{qc_id}", method= RequestMethod.GET)
	 public ModelAndView getHistoryData(@PathVariable("qc_id") String qc_id) throws SQLException {
		ModelAndView model = new ModelAndView();
		JYT012 finishedPrimumQC=b2bService.getQCData(qc_id);
		ArrayList<IpqcItem> qcProductList = b2bService.getQCContentHistory(qc_id);
//		System.out.println("qcProductList="+qcProductList.size());
		QCResult qcResult=b2bService.getQCResultHistory(qc_id);
		model.addObject("finishedPrimumQC", finishedPrimumQC);
		model.addObject("qcProductList", qcProductList);
		model.addObject("qcResult", qcResult);                
		model.setViewName("showHistorySpec");      
		return model;
	}
	
//	@RequestMapping(value = "/getQCData",produces="application/json")
//	public @ResponseBody JYT012 getQCData() throws SQLException{
//		return b2bService.getQCData("12");
//	}
	
	@RequestMapping(value = "/getTestData",produces="application/json")
	public @ResponseBody ArrayList<JYT012> getQCDataList(MocSearch ms) throws SQLException{
		String otb=ms.getOrderTypeBegin();//找製令單別起
		String ote=ms.getOrderTypeEnd();//找製令單別迄
		String onb=ms.getOrderNoBegin();//找製令單號起
		String one=ms.getOrderNoEnd();//找製令單號迄
		String odb=ms.getOrderDateBegin();//找單據日期起
		String ode=ms.getOrderDateEnd();//找單據日期迄
		String inb = ms.getItemNoBegin();//找品號起
		String ine = ms.getItemNoEnd();//找品號迄
		return b2bService.getQCDataList(otb,ote,onb,one,odb, ode,inb,ine);
	}
	
	
	//自主檢查
	@RequestMapping(value = "/getAllSelfCheckQC/{pqcCREATOR}",produces="application/json")
	public @ResponseBody ModelAndView getSelfCheckQCData(@PathVariable("pqcCREATOR") String pqcCREATOR,HttpServletResponse response,HttpServletRequest request) throws SQLException, IOException{
		response.setContentType("text/html;charset=utf-8");
		ModelAndView model = new ModelAndView();
		PrintWriter out = response.getWriter();
		if(pqcCREATOR!=null && !pqcCREATOR.equals("")) {
			ArrayList allSelfCheckQCData = b2bService.getAllSelfCheckQCData();
			model.setViewName("allSelfCheckQCData");
			model.addObject("allSelfCheckQCData", allSelfCheckQCData);
			model.addObject("ipqcCREATOR", pqcCREATOR);
//			return model;		
		}else {
			out.println("<script language=\"javascript\">");
			out.print("alert(\"無登入者帳號,請重新登入DSS系統;\");");
			out.println("</script>");
			return null;
		}		
		return model;
	}
	
	//顯示自主檢查規格
//	@RequestMapping(value = "/getSelfCheckQCSpec/{qc_id}/{pqcCREATOR}", method = RequestMethod.GET)
//	public ModelAndView getSelfCheckQCSpec(@PathVariable("qc_id") String qc_id,@PathVariable("pqcCREATOR") String pqcCREATOR,HttpServletResponse response,HttpServletRequest request) throws IOException, SQLException {
//		response.setContentType("text/html;charset=utf-8");
//		PrintWriter out = response.getWriter();
//		ModelAndView model = new ModelAndView();
//		if(qc_id!=null && !qc_id.equals("")) {
//			LocalDate todaysDate = LocalDate.now();
//			String itemNumber= b2bService.getItemNumber(MO, NO);
//			String maxFormNumber= b2bService.getMaxFormNumber(itemNumber);
//			JYT012 selfCheckQC=b2bService.getQCData(MO, NO, itemNumber, maxFormNumber);
//			ArrayList<IpqcItem> ipqc=b2bService.getIpqc(itemNumber, maxFormNumber);
//			model.setViewName("selfCheckTestSpec");
//			model.addObject("selfCheckQC", selfCheckQC);
//			model.addObject("ipqc", ipqc);
//			model.addObject("MO", MO);
//			model.addObject("MN", NO);
//			model.addObject("pqcCREATOR", pqcCREATOR);
//			model.addObject("todaysDate", todaysDate);					
//		}else {
//			out.println("<script language=\"javascript\">");
//			out.print("alert(\"資料有誤,查詢失敗\");");
//			out.println("</script>");
//		}			
//		return model;			
//	}

}
