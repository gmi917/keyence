package com.joytech.keyence.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.joytech.keyence.bean.BadReasons;
import com.joytech.keyence.bean.IpqcItem;
import com.joytech.keyence.bean.IpqcRecord;
import com.joytech.keyence.bean.IpqcRecordModel;
import com.joytech.keyence.bean.JYT012;
import com.joytech.keyence.bean.MO;
import com.joytech.keyence.bean.MOList;
import com.joytech.keyence.bean.ManufactureNo;
import com.joytech.keyence.bean.ManufactureOrder;
import com.joytech.keyence.bean.ProcessCode;
import com.joytech.keyence.bean.QCProduct;
import com.joytech.keyence.bean.QCResult;

@Service
public class B2BService {
	public static String JDBC_URL = "jdbc:sqlserver://192.168.1.9:1433;databaseName=TEST1;user=pwc;password=PWC@admin;characterEncoding=utf8;encrypt=false";
//	public static String JDBC_URL = "jdbc:sqlserver://192.168.1.9:1433;databaseName=JOYTECH;user=pwc;password=PWC@admin;characterEncoding=utf8;";
	public static String DBName="TEST1";//測試資料庫
//	public static String DBName="JOYTECH";//正式資料庫
	public static String imageURL="http://192.168.1.15";
	public int pageSize=10;//分頁資料筆數

	private Connection getDbConnection() {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			conn = DriverManager.getConnection(JDBC_URL);			

		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		return conn;
	}
	
	
	
	//現場人員找尋未輸入剩餘資料記錄列表
	public ArrayList<QCProduct> getIpqcPrtoduct() throws SQLException{
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<QCProduct> to =new ArrayList<QCProduct>();
		try {
			conn = getDbConnection();					
			String sql = "select p.qc_id,p.itemName,p.manufactureOrder,p.manufactureNo,p.partNumber,p.ipqcCREATE_DATE from "+DBName+".dbo.QCDataCollection as p"
					+" left join "+DBName+".dbo.QCDataCollectionContent as c on p.qc_id = c.qc_id where p.IpqcTMXStatus='0' "
					+" group by p.qc_id,p.itemName,p.manufactureOrder,p.manufactureNo,p.partNumber,p.ipqcCREATE_DATE";			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				QCProduct qcProduct=new QCProduct();
				qcProduct.setQc_id(rs.getString("qc_id"));
				qcProduct.setManufactureOrder(rs.getString("manufactureOrder"));
				qcProduct.setManufactureNo(rs.getString("manufactureNo"));
				qcProduct.setPartNumber(rs.getString("partNumber"));
				qcProduct.setItemName(rs.getString("itemName"));
				qcProduct.setCheckDate(rs.getString("ipqcCREATE_DATE"));
				to.add(qcProduct);				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) rs.close();
		    if(ps!=null) ps.close();
		    if(conn!=null) conn.close();
		}
		return to;		
	}
	
	//現場人員找尋檢驗說明書表頭詳細資料
		public JYT012 getIpqcData(String qc_id) throws SQLException {
			Connection conn = null;
			ResultSet rs = null;		
			PreparedStatement  ps = null;
			JYT012 jyt012=new JYT012();
			try {
				conn = getDbConnection();			
				String sql = "select qc_id,itemName,specification,manufactureOrder,manufactureNo,partNumber,imageNumber,version,formNumber,customerCode,firstItemDate,firstItemStaff,predictQty,machineNumber,imageFileName from "+DBName+".dbo.QCDataCollection where "
						+"qc_id='"+qc_id+"'";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if(rs.next()) {
					jyt012.setQc_id(rs.getString("qc_id"));
					jyt012.setJYT012a003(rs.getString("itemName"));
					jyt012.setUDF02(rs.getString("specification"));
					jyt012.setJYT012a004(rs.getString("imageNumber"));
					jyt012.setJYT012a005(rs.getString("partNumber"));				
					jyt012.setManufactureOrder(rs.getString("manufactureOrder"));
					jyt012.setManufactureNo(rs.getString("manufactureNo"));
					jyt012.setCustomerCode(rs.getString("customerCode"));
					jyt012.setJYT012a002(rs.getString("formNumber"));
					jyt012.setFirstItemDate(rs.getString("firstItemDate"));
					jyt012.setFirstItemStaff(rs.getString("firstItemStaff"));
					jyt012.setPredictQty(rs.getString("predictQty"));
					jyt012.setMachineNumber(rs.getString("machineNumber"));
					jyt012.setJYT012a006(rs.getString("version"));
					jyt012.setUDF01(rs.getString("imageFileName"));
					jyt012.setFullImageName(imageURL+"/img/"+rs.getString("formNumber")+"/"+rs.getString("imageFileName"));	
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(rs!=null) rs.close();
			    if(ps!=null) ps.close();
			    if(conn!=null) conn.close();
			}			
			return jyt012;
		}
		
		//現場人員找尋首件/自主檢查記錄詳細資料
		public ArrayList<IpqcItem> getIpqcTestData(String qc_id) throws SQLException {
			Connection conn = null;
			ResultSet rs = null;		
			PreparedStatement  ps = null;
			ArrayList<IpqcItem> to =new ArrayList<IpqcItem>();
			String  NegativeValue="";
			String  PositiveValue="";
			try {
				conn = getDbConnection();			
				String sql = "select qc_id,itemSN,testItem,testUnit,standardValue,upperLimit,lowerLimit,testTool,flag,ipqc1,ipqc2,ipqc3 from "+DBName+".dbo.QCDataCollectionContent where "
						+"qc_id='"+qc_id+"'";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()) {
					IpqcItem ipqcItem =new IpqcItem();
					ipqcItem.setJYT012b003(rs.getString("itemSN"));
					ipqcItem.setJYT012b005(rs.getString("testItem"));
					ipqcItem.setJYT012b006(rs.getString("testUnit"));
					ipqcItem.setJYT012b007(rs.getString("standardValue"));
					ipqcItem.setJYT012b008(rs.getString("upperLimit"));
					ipqcItem.setJYT012b009(rs.getString("lowerLimit"));
					if(rs.getString("flag").equals("1")) {
						if(rs.getString("upperLimit")!=null && !rs.getString("upperLimit").equals("") &&
								rs.getString("lowerLimit")!=null && !rs.getString("lowerLimit").equals("") ) {
							NegativeValue = rs.getString("lowerLimit").replace("-", "");
							PositiveValue = rs.getString("upperLimit").replace("+", "");
							BigDecimal bdNegativeValue=new BigDecimal(NegativeValue);
							BigDecimal bdPositiveValue=new BigDecimal(PositiveValue);
							ipqcItem.setMin(String.valueOf(rs.getBigDecimal("standardValue").subtract(bdNegativeValue)));
							ipqcItem.setMax(String.valueOf(rs.getBigDecimal("standardValue").add(bdPositiveValue)));
						}else if(rs.getString("upperLimit")!=null && !rs.getString("upperLimit").equals("")) {
							PositiveValue = rs.getString("upperLimit").replace("+", "");
							BigDecimal bdPositiveValue=new BigDecimal(PositiveValue);
							ipqcItem.setMin("0");
							ipqcItem.setMax(String.valueOf(rs.getBigDecimal("standardValue").add(bdPositiveValue)));	
						}else if(rs.getString("lowerLimit")!=null && !rs.getString("lowerLimit").equals("")) {
							NegativeValue = rs.getString("lowerLimit").replace("-", "");
							BigDecimal bdNegativeValue=new BigDecimal(NegativeValue);
							ipqcItem.setMin(String.valueOf(rs.getBigDecimal("standardValue").subtract(bdNegativeValue)));	
						}
					}
					ipqcItem.setJYT012b010(rs.getString("testTool"));
					ipqcItem.setUDF03(rs.getString("flag"));
					ipqcItem.setIpqc1(rs.getString("ipqc1"));
					ipqcItem.setIpqc2(rs.getString("ipqc2"));
					ipqcItem.setIpqc3(rs.getString("ipqc3"));
					to.add(ipqcItem);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(rs!=null) rs.close();
			    if(ps!=null) ps.close();
			    if(conn!=null) conn.close();
			}		
			return to;		
		}
		
		//現場人員update首件/自主檢查記錄實測狀況
		public void updIpqcData(String qc_id,String itemSN,String ipqc1,String ipqc2,String ipqc3,String ipqcCREATOR) throws SQLException {
			Connection conn = null;
			Statement  stmt = null;
			try {
//				System.out.println("testItem="+testItem);
				conn = getDbConnection();
				stmt = conn.createStatement();
				String sql = "update "+DBName+".dbo.QCDataCollectionContent set ipqc1='"+ipqc1+"',ipqc2='"+ipqc2+"',ipqc3='"+ipqc3+"'"+
		                   ",ipqcCREATOR='"+ipqcCREATOR+"' where qc_id='"+qc_id+"' and itemSN='"+itemSN+"'";
//				System.out.println("sql="+sql);
				stmt.executeUpdate(sql);
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(stmt!=null) stmt.close();
			    if(conn!=null) conn.close();			
			}	
		}
		
		//品檢人員找尋未輸入剩餘資料記錄列表
		public ArrayList<QCProduct> getPqcPrtoduct() throws SQLException{
			Connection conn = null;
			ResultSet rs = null;		
			PreparedStatement  ps = null;
			ArrayList<QCProduct> to =new ArrayList<QCProduct>();
			try {
				conn = getDbConnection();					
				String sql = "select p.qc_id,p.itemName,p.manufactureOrder,p.manufactureNo,p.partNumber,p.ipqcCREATE_DATE from "+DBName+".dbo.QCDataCollection as p"
						+" left join "+DBName+".dbo.QCDataCollectionContent as c on p.qc_id = c.qc_id where p.IpqcTMXStatus='1' and p.pqcTMXStatus='0'"
						+" group by p.qc_id,p.itemName,p.manufactureOrder,p.manufactureNo,p.partNumber,p.ipqcCREATE_DATE";			
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()) {
					QCProduct qcProduct=new QCProduct();
					qcProduct.setQc_id(rs.getString("qc_id"));
					qcProduct.setManufactureOrder(rs.getString("manufactureOrder"));
					qcProduct.setManufactureNo(rs.getString("manufactureNo"));
					qcProduct.setPartNumber(rs.getString("partNumber"));
					qcProduct.setItemName(rs.getString("itemName"));
					qcProduct.setCheckDate(rs.getString("ipqcCREATE_DATE"));
					to.add(qcProduct);				
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(rs!=null) rs.close();
			    if(ps!=null) ps.close();
			    if(conn!=null) conn.close();
			}
			return to;		
		}
		
		//回寫現場人員TMX檢驗flag為1
		public void updIpqcStatus(String qc_id) throws SQLException {
			Connection conn = null;
			Statement  stmt = null;
			try {
				conn = getDbConnection();
				stmt = conn.createStatement();
				String sql = "update "+DBName+".dbo.QCDataCollection set IpqcTMXStatus='1',pqcTMXStatus='0' where qc_id='"+qc_id+"'";
//				System.out.println("sql="+sql);
				stmt.executeUpdate(sql);
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(stmt!=null) stmt.close();
			    if(conn!=null) conn.close();			
			}	
		}
	
	//去QCDataCollection和QCDataCollectionContent找未完成的製令單別
	public ArrayList<ManufactureOrder> getQCManufactureOrder() throws SQLException{
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<ManufactureOrder> to =new ArrayList<ManufactureOrder>();
		try {
			conn = getDbConnection();			
			String sql = "select distinct p.manufactureOrder as manufactureOrder from "+DBName+".dbo.QCDataCollection p,"+DBName+".dbo.QCDataCollectionContent c where "
					+"p.qc_id=c.qc_id and p.pqcStatus='0'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				ManufactureOrder mo =new ManufactureOrder();
				mo.setManufactureOrder(rs.getString("manufactureOrder"));
				to.add(mo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) rs.close();
		    if(ps!=null) ps.close();
		    if(conn!=null) conn.close();
		}
		return to;			
	}
	
	//去QCDataCollection和QCDataCollectionContent找未完成的製令單號
	public ArrayList<ManufactureNo> getQCManufactureNo() throws SQLException{
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<ManufactureNo> to =new ArrayList<ManufactureNo>();
		try {
			conn = getDbConnection();			
			String sql = "select distinct p.manufactureNo from "+DBName+".dbo.QCDataCollection p,"+DBName+".dbo.QCDataCollectionContent c where "
					+"p.qc_id=c.qc_id  and p.pqcStatus='0'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				ManufactureNo mn =new ManufactureNo();
				mn.setManufactureNo(rs.getString("manufactureNo"));
				to.add(mn);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) rs.close();
		    if(ps!=null) ps.close();
		    if(conn!=null) conn.close();
		}
		return to;	
	}
	
	//找尋檢驗說明書表頭詳細資料
	public JYT012 getQCData(String qc_id) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		JYT012 jyt012=new JYT012();
		try {
			conn = getDbConnection();			
			String sql = "select qc_id,itemName,specification,manufactureOrder,manufactureNo,partNumber,imageNumber,version,formNumber,customerCode,firstItemDate,firstItemStaff,predictQty,machineNumber,imageFileName from "+DBName+".dbo.QCDataCollection where "
					+"qc_id='"+qc_id+"'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				jyt012.setJYT012a003(rs.getString("itemName"));
				jyt012.setUDF02(rs.getString("specification"));
				jyt012.setJYT012a004(rs.getString("imageNumber"));
				jyt012.setJYT012a005(rs.getString("partNumber"));				
				jyt012.setManufactureOrder(rs.getString("manufactureOrder"));
				jyt012.setManufactureNo(rs.getString("manufactureNo"));
				jyt012.setCustomerCode(rs.getString("customerCode"));
				jyt012.setJYT012a002(rs.getString("formNumber"));
				jyt012.setFirstItemDate(rs.getString("firstItemDate"));
				jyt012.setFirstItemStaff(rs.getString("firstItemStaff"));
				jyt012.setPredictQty(rs.getString("predictQty"));
				jyt012.setMachineNumber(rs.getString("machineNumber"));
				jyt012.setJYT012a006(rs.getString("version"));
				jyt012.setUDF01(rs.getString("imageFileName"));
				jyt012.setFullImageName(imageURL+"/img/"+rs.getString("formNumber")+"/"+rs.getString("imageFileName"));	
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) rs.close();
		    if(ps!=null) ps.close();
		    if(conn!=null) conn.close();
		}			
		return jyt012;
	}
	
	//品檢人員找尋未檢驗的首件/自主檢查記錄詳細資料
	public ArrayList<IpqcItem> getQCTestData(String qc_id) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<IpqcItem> to =new ArrayList<IpqcItem>();
		String  NegativeValue="";
		String  PositiveValue="";
		try {
			conn = getDbConnection();			
			String sql = "select qc_id,itemSN,testItem,testUnit,standardValue,upperLimit,lowerLimit,testTool,flag,ipqc1,ipqc2,ipqc3,pqc1,pqc2,pqc3 from "+DBName+".dbo.QCDataCollectionContent where "
					+"qc_id='"+qc_id+"'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				IpqcItem ipqcItem =new IpqcItem();
				ipqcItem.setJYT012b003(rs.getString("itemSN"));
				ipqcItem.setJYT012b005(rs.getString("testItem"));
				ipqcItem.setJYT012b006(rs.getString("testUnit"));
				ipqcItem.setJYT012b007(rs.getString("standardValue"));
				ipqcItem.setJYT012b008(rs.getString("upperLimit"));
				ipqcItem.setJYT012b009(rs.getString("lowerLimit"));
				if(rs.getString("flag").equals("1")) {
					if(rs.getString("upperLimit")!=null && !rs.getString("upperLimit").equals("") &&
							rs.getString("lowerLimit")!=null && !rs.getString("lowerLimit").equals("") ) {
						NegativeValue = rs.getString("lowerLimit").replace("-", "");
						PositiveValue = rs.getString("upperLimit").replace("+", "");
						BigDecimal bdNegativeValue=new BigDecimal(NegativeValue);
						BigDecimal bdPositiveValue=new BigDecimal(PositiveValue);
						ipqcItem.setMin(String.valueOf(rs.getBigDecimal("standardValue").subtract(bdNegativeValue)));
						ipqcItem.setMax(String.valueOf(rs.getBigDecimal("standardValue").add(bdPositiveValue)));
					}else if(rs.getString("upperLimit")!=null && !rs.getString("upperLimit").equals("")) {
						PositiveValue = rs.getString("upperLimit").replace("+", "");
						BigDecimal bdPositiveValue=new BigDecimal(PositiveValue);
						ipqcItem.setMin("0");
						ipqcItem.setMax(String.valueOf(rs.getBigDecimal("standardValue").add(bdPositiveValue)));	
					}else if(rs.getString("lowerLimit")!=null && !rs.getString("lowerLimit").equals("")) {
						NegativeValue = rs.getString("lowerLimit").replace("-", "");
						BigDecimal bdNegativeValue=new BigDecimal(NegativeValue);
						ipqcItem.setMin(String.valueOf(rs.getBigDecimal("standardValue").subtract(bdNegativeValue)));	
					}
				}
				ipqcItem.setJYT012b010(rs.getString("testTool"));
				ipqcItem.setUDF03(rs.getString("flag"));
				ipqcItem.setIpqc1(rs.getString("ipqc1"));
				ipqcItem.setIpqc2(rs.getString("ipqc2"));
				ipqcItem.setIpqc3(rs.getString("ipqc3"));
				ipqcItem.setPqc1(rs.getString("pqc1"));
				ipqcItem.setPqc2(rs.getString("pqc2"));
				ipqcItem.setPqc3(rs.getString("pqc3"));
				to.add(ipqcItem);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) rs.close();
		    if(ps!=null) ps.close();
		    if(conn!=null) conn.close();
		}		
		return to;		
	}
	
	//找ERP不良原因
	public ArrayList<BadReasons> getBadReasons() throws SQLException{
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<BadReasons> to =new ArrayList<BadReasons>();
		try {
			conn = getDbConnection();			
			String sql = "select MH001,MH002 from "+DBName+".dbo.QMSMH where UDF01='1'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				BadReasons badReasons = new BadReasons();
				badReasons.setMH001(rs.getString("MH001"));
				badReasons.setMH002(rs.getString("MH002"));
				to.add(badReasons);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) rs.close();
		    if(ps!=null) ps.close();
		    if(conn!=null) conn.close();
		}		
		return to;						
	}
	
	//寫品檢成品檢驗記錄
	public void updPqcData(String qc_id,String itemSN,String pqc1,String pqc2,String pqc3) throws SQLException {
		Connection conn = null;
		Statement  stmt = null;
		try {
			conn = getDbConnection();
			stmt = conn.createStatement();
			String sql = "update "+DBName+".dbo.QCDataCollectionContent set pqc1='"+pqc1.toString()+"',pqc2='"+pqc2.toString()+"',pqc3='"+pqc3.toString()+"'"
						+" where qc_id='"+qc_id+"' and itemSN='"+itemSN+"'";	                  
//			System.out.println("sql="+sql);
			stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(stmt!=null) stmt.close();
		    if(conn!=null) conn.close();			
		}	
	}
	
	//寫判定結果
	public void insPqcResult(String qc_id,String defective,String determination,String supervisor,String inspectors,String handleResult,String pqcCREATOR) throws SQLException {
		Connection conn = null;
		Statement  stmt = null;
		try {
			conn = getDbConnection();
			stmt = conn.createStatement();
			String sql = "insert into "+DBName+".dbo.QCDataCollectionResult(qc_id,defective,determination,supervisor,inspectors,handleResult,pqcCREATOR)"
						+" values('"+qc_id+"','"+defective+"','"+determination+"','"+supervisor+"','"+inspectors+"','"+handleResult+"','"+pqcCREATOR+"')";	                  
//			System.out.println("sql="+sql);
			stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(stmt!=null) stmt.close();
		    if(conn!=null) conn.close();			
		}			
	}
	
	//回寫品檢檢驗flag為1
	public void updPqcFlag(String qc_id) throws SQLException {
		Connection conn = null;
		Statement  stmt = null;
		try {
			conn = getDbConnection();
			stmt = conn.createStatement();
			String sql = "update "+DBName+".dbo.QCDataCollection set pqcStatus='1' where qc_id='"+qc_id+"'";	                  
			stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(stmt!=null) stmt.close();
		    if(conn!=null) conn.close();			
		}	
	}
	
	//回寫品檢TMX檢驗flag為1
		public void updPqcTMXFlag(String qc_id) throws SQLException {
			Connection conn = null;
			Statement  stmt = null;
			try {
				conn = getDbConnection();
				stmt = conn.createStatement();
				String sql = "update "+DBName+".dbo.QCDataCollection set pqcTMXStatus='1' where qc_id='"+qc_id+"'";	                  
				stmt.executeUpdate(sql);
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(stmt!=null) stmt.close();
			    if(conn!=null) conn.close();			
			}	
		}
	
	public ArrayList<QCProduct> getQCProductByPage() throws SQLException {
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<QCProduct> to = new ArrayList<>();
        try {
        	conn = getDbConnection();            
        	String sql = "SELECT distinct p.qc_id,p.itemName,p.manufactureOrder,p.manufactureNo,p.partNumber,p.ipqcCREATE_DATE FROM "+DBName+".dbo.QCDataCollection as p,"
        			+""+DBName+".dbo.QCDataCollectionContent as c where p.qc_id=c.qc_id and p.pqcStatus='0'";
        	ps = conn.prepareStatement(sql);           
            rs = ps.executeQuery();
            while (rs.next()) {
            	QCProduct qcProduct = new QCProduct();
            	qcProduct.setQc_id(rs.getString("qc_id"));
            	qcProduct.setManufactureOrder(rs.getString("manufactureOrder"));
            	qcProduct.setManufactureNo(rs.getString("manufactureNo"));
            	qcProduct.setPartNumber(rs.getString("partNumber"));
            	qcProduct.setItemName(rs.getString("itemName"));
            	qcProduct.setCheckDate(rs.getString("ipqcCREATE_DATE"));
                to.add(qcProduct);
            }
        }catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) rs.close();
		    if(ps!=null) ps.close();
		    if(conn!=null) conn.close();
		}
        return to;
    }
	
	//找檢查記錄實測狀況資料
	public ArrayList<IpqcItem> getQCContentHistory(String qc_id) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<IpqcItem> to = new ArrayList<>();
		String  NegativeValue="";
		String  PositiveValue="";
		try {
			conn = getDbConnection();            
        	String sql = "SELECT qc_id,itemSN,testItem,testUnit,standardValue,upperLimit,lowerLimit,testTool,flag,ipqc1,ipqc2,ipqc3,pqc1,pqc2,pqc3 FROM "+DBName+".dbo.QCDataCollectionContent"
        			+" where qc_id='"+qc_id+"' order by itemSN asc";
        	ps = conn.prepareStatement(sql);           
            rs = ps.executeQuery();
            while (rs.next()) {
            	IpqcItem ipqcItem = new IpqcItem();
            	ipqcItem.setJYT012b003(rs.getString("itemSN"));
            	ipqcItem.setJYT012b005(rs.getString("testItem"));
            	ipqcItem.setJYT012b006(rs.getString("testUnit"));
            	ipqcItem.setJYT012b007(rs.getString("standardValue").strip());
            	ipqcItem.setJYT012b008(rs.getString("upperLimit").strip());
            	ipqcItem.setJYT012b009(rs.getString("lowerLimit").strip());
            	ipqcItem.setJYT012b010(rs.getString("testTool"));
            	ipqcItem.setUDF03(rs.getString("flag"));
            	ipqcItem.setIpqc1(rs.getString("ipqc1").strip());
            	ipqcItem.setIpqc2(rs.getString("ipqc2").strip());
            	ipqcItem.setIpqc3(rs.getString("ipqc3").strip());
            	if(rs.getString("flag").equals("1")) {
					if(rs.getString("upperLimit")!=null && !rs.getString("upperLimit").equals("") &&
							rs.getString("lowerLimit")!=null && !rs.getString("lowerLimit").equals("") ) {
						NegativeValue = rs.getString("lowerLimit").replace("-", "");
						PositiveValue = rs.getString("upperLimit").replace("+", "");
						BigDecimal bdNegativeValue=new BigDecimal(NegativeValue);
						BigDecimal bdPositiveValue=new BigDecimal(PositiveValue);
						ipqcItem.setMin(String.valueOf(rs.getBigDecimal("standardValue").subtract(bdNegativeValue)));
						ipqcItem.setMax(String.valueOf(rs.getBigDecimal("standardValue").add(bdPositiveValue)));
					}else if(rs.getString("upperLimit")!=null && !rs.getString("upperLimit").equals("")) {
						PositiveValue = rs.getString("upperLimit").replace("+", "");
						BigDecimal bdPositiveValue=new BigDecimal(PositiveValue);
						ipqcItem.setMin("0");
						ipqcItem.setMax(String.valueOf(rs.getBigDecimal("standardValue").add(bdPositiveValue)).strip());					
					}else if(rs.getString("lowerLimit")!=null && !rs.getString("lowerLimit").equals("")) {
						NegativeValue = rs.getString("lowerLimit").replace("-", "");
						BigDecimal bdNegativeValue=new BigDecimal(NegativeValue);
						ipqcItem.setMin(String.valueOf(rs.getBigDecimal("standardValue").subtract(bdNegativeValue)));	
					}
				}
            	ipqcItem.setPqc1(rs.getString("pqc1"));
            	ipqcItem.setPqc2(rs.getString("pqc2"));
            	ipqcItem.setPqc3(rs.getString("pqc3"));
            	//設定合格(藍色)和不合格(紅色)顯示顏色
            	if(rs.getString("upperLimit")!=null && !rs.getString("upperLimit").equals("") &&
						rs.getString("lowerLimit")!=null && !rs.getString("lowerLimit").equals("") ) {
            		if(rs.getString("pqc1")!=null && !rs.getString("pqc1").equals("")) {
            			ipqcItem.setPqc1Color(rs.getFloat("pqc1") >= Float. parseFloat(ipqcItem.getMin()) && rs.getFloat("pqc1") <= Float. parseFloat(ipqcItem.getMax()) ? "blue" : "red");            			
            		}
            		if(rs.getString("pqc2")!=null && !rs.getString("pqc2").equals("")) {
            			ipqcItem.setPqc2Color(rs.getFloat("pqc2") >= Float. parseFloat(ipqcItem.getMin()) && rs.getFloat("pqc2") <= Float. parseFloat(ipqcItem.getMax()) ? "blue" : "red");
            		}
            		if(rs.getString("pqc3")!=null && !rs.getString("pqc3").equals("")) {
            			ipqcItem.setPqc3Color(rs.getFloat("pqc3") >= Float. parseFloat(ipqcItem.getMin()) && rs.getFloat("pqc3") <= Float. parseFloat(ipqcItem.getMax()) ? "blue" : "red");            			
            		}
            		 
            	}else if(rs.getString("upperLimit")!=null && !rs.getString("upperLimit").equals("")) {
            		if(rs.getString("pqc1")!=null && !rs.getString("pqc1").equals("")) {
            			ipqcItem.setPqc1Color(rs.getFloat("pqc1") <= Float. parseFloat(ipqcItem.getMax()) ? "blue" : "red");            			
            		}
            		if(rs.getString("pqc2")!=null && !rs.getString("pqc2").equals("")) {
            			ipqcItem.setPqc2Color(rs.getFloat("pqc2") <= Float. parseFloat(ipqcItem.getMax()) ? "blue" : "red");
            		}
            		if(rs.getString("pqc3")!=null && !rs.getString("pqc3").equals("")) {
            			ipqcItem.setPqc3Color(rs.getFloat("pqc3") <= Float. parseFloat(ipqcItem.getMax()) ? "blue" : "red");
            		}
            		 
            	}else if(rs.getString("lowerLimit")!=null && !rs.getString("lowerLimit").equals("")) {
            		if(rs.getString("pqc1")!=null && !rs.getString("pqc1").equals("")) {
            			ipqcItem.setPqc1Color(rs.getFloat("pqc1") >= Float. parseFloat(ipqcItem.getMin()) ? "blue" : "red");
            		}
            		if(rs.getString("pqc2")!=null && !rs.getString("pqc2").equals("")) {
            			ipqcItem.setPqc2Color(rs.getFloat("pqc3") >= Float. parseFloat(ipqcItem.getMin()) ? "blue" : "red"); 
            		}
            		if(rs.getString("pqc3")!=null && !rs.getString("pqc3").equals("")) {
            			ipqcItem.setPqc3Color(rs.getFloat("pqc3") >= Float. parseFloat(ipqcItem.getMin()) ? "blue" : "red"); 
            		}
            		
            	}
//            	System.out.println(rs.getFloat("pqc3"));
//            	System.out.println(ipqcItem.getMax());
                to.add(ipqcItem);
            }
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) rs.close();
		    if(ps!=null) ps.close();
		    if(conn!=null) conn.close();
		}
        return to;
	}
	
	//找判結果資料
	public QCResult getQCResultHistory(String qc_id) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		QCResult qcResult =new QCResult();
		try {
			conn = getDbConnection();            
        	String sql = "SELECT qc_id,defective,determination,supervisor,inspectors,handleResult FROM "+DBName+".dbo.QCDataCollectionResult"
        			+" where qc_id='"+qc_id+"'";
        	ps = conn.prepareStatement(sql);           
            rs = ps.executeQuery();
            if(rs.next()) {
            	qcResult.setDefective(rs.getString("defective"));
            	qcResult.setDetermination(rs.getString("determination"));
            	qcResult.setSupervisor(rs.getString("supervisor"));
            	qcResult.setInspectors(rs.getString("inspectors"));
            	qcResult.setHandleResult(rs.getString("handleResult"));
            }
		}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		if(rs!=null) rs.close();
    	    if(ps!=null) ps.close();
    	    if(conn!=null) conn.close();
    	}    	
		return qcResult;		
	}
	
	public ArrayList<JYT012> getQCDataList(String manufactureOrderBegin,String manufactureOrderEnd,String manufactureNoBegin,String manufactureNoEnd,String beginDate,String endDate,String partNumberBegin,String partNumberEnd) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		StringBuilder whereSql = new StringBuilder();
		ArrayList<JYT012> jyt012Arry=new ArrayList<JYT012>();
		try {
			conn = getDbConnection();
			if(manufactureOrderBegin.equals("")  && manufactureOrderEnd.equals("")){whereSql.append("");}else{whereSql.append("and manufactureOrder between '"+manufactureOrderBegin+"' and '"+manufactureOrderEnd+"'");}
			if(manufactureNoBegin.equals("")  && manufactureNoEnd.equals("") ){whereSql.append("");}else{whereSql.append("and manufactureNo between '"+manufactureNoBegin+"' and '"+manufactureNoEnd+"'");}
			if(beginDate.equals("") && endDate.equals("")){whereSql.append("");}else{whereSql.append("and firstItemDate between '"+beginDate+"' and '"+endDate+"'");}
			if(partNumberBegin.equals("") && partNumberEnd.equals("")){whereSql.append("");}else{ whereSql.append("and partNumber between '"+partNumberBegin+"' and '"+partNumberEnd+"'");}
			String sql = "select top 1000 QCDataCollection.qc_id,max(itemName)itemName,max(specification)specification,max(manufactureOrder)manufactureOrder,max(manufactureNo)manufactureNo,max(partNumber)partNumber,max(imageNumber)imageNumber,max(version)version,max(formNumber)formNumber,max(customerCode)customerCode,max(firstItemDate)firstItemDate,max(firstItemStaff) firstItemStaff,"
					+ "max(predictQty)predictQty,max(machineNumber)machineNumber,max(imageFileName)imageFileName from "+DBName+".dbo.QCDataCollection "
					+"left join QCDataCollectionContent on QCDataCollection.qc_id = QCDataCollectionContent.qc_id "
					+ "where 1=1 and pqcStatus='0'"+whereSql.toString()
					+"group by QCDataCollection.qc_id "
					+" order by firstItemDate desc ";	
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				JYT012 jyt012=new JYT012();
				jyt012.setQc_id(rs.getString("qc_id"));
				jyt012.setJYT012a003(rs.getString("itemName"));
				jyt012.setUDF02(rs.getString("specification"));
				jyt012.setJYT012a004(rs.getString("imageNumber"));
				jyt012.setJYT012a005(rs.getString("partNumber"));				
				jyt012.setManufactureOrder(rs.getString("manufactureOrder"));
				jyt012.setManufactureNo(rs.getString("manufactureNo"));
				jyt012.setCustomerCode(rs.getString("manufactureNo"));
				jyt012.setJYT012a002(rs.getString("customerCode"));
				jyt012.setFirstItemDate(rs.getString("firstItemDate"));
				jyt012.setFirstItemStaff(rs.getString("firstItemStaff"));
				jyt012.setPredictQty(rs.getString("predictQty"));
				jyt012.setMachineNumber(rs.getString("machineNumber"));
				jyt012.setJYT012a006(rs.getString("version"));
				jyt012.setUDF01(rs.getString("imageFileName"));
				jyt012.setFullImageName(imageURL+"/img/"+rs.getString("formNumber")+"/"+rs.getString("imageFileName"));	
				jyt012Arry.add(jyt012);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) rs.close();
		    if(ps!=null) ps.close();
		    if(conn!=null) conn.close();
		}			
		return jyt012Arry;
	}
	//找自主檢查單別單號資料
	public ArrayList<MO> getAllSelfCheckQCData() throws SQLException {
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<MO> moArry=new ArrayList<MO>();
		try {
			conn = getDbConnection();       
			String sql="select q.qc_id,q.manufactureOrder,q.manufactureNo from "+DBName+".dbo.QCDataCollection as q"
					+ " left join SFCTA as s on q.manufactureOrder = s.TA001 AND q.manufactureNo = s.TA002 and s.TA032 ='N'";        	
        	ps = conn.prepareStatement(sql);           
            rs = ps.executeQuery();
            while(rs.next()) {
            	MO mo =new MO();
            	mo.setQc_id(rs.getString("qc_id"));
            	mo.setManufactureOrder(rs.getString("manufactureOrder"));
            	mo.setManufactureNo(rs.getString("manufactureNo"));   
            	moArry.add(mo);
            }
		}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		if(rs!=null) rs.close();
    	    if(ps!=null) ps.close();
    	    if(conn!=null) conn.close();
    	}    	
		return moArry;		
	}
	
	//抓製成代號(ERP與DSS去join)
	public ArrayList<ProcessCode> getAllProcessCodeData() throws SQLException {
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<ProcessCode> processCodeArry=new ArrayList<ProcessCode>();
		try {
			conn = getDbConnection(); 
			String sql="select MW001,MW002 from "+DBName+".dbo.CMSMW where MW001 in "
					+ "(select JYT012a008 from "+DBName+".dbo.JYT012 group by JYT012a008)";			    
        	ps = conn.prepareStatement(sql);           
            rs = ps.executeQuery();
            while(rs.next()) {
            	ProcessCode pc= new ProcessCode();
            	pc.setProcessCode(rs.getString("MW001"));
            	pc.setProcessName(rs.getString("MW002"));
            	processCodeArry.add(pc);
            }			
		}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		if(rs!=null) rs.close();
    	    if(ps!=null) ps.close();
    	    if(conn!=null) conn.close();
    	}
		return processCodeArry;  		
	}
	
}
