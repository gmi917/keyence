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

import com.joytech.premium.bean.BadReasons;
import com.joytech.premium.bean.IpqcItem;
import com.joytech.premium.bean.IpqcRecord;
import com.joytech.premium.bean.IpqcRecordModel;
import com.joytech.premium.bean.JYT012;
import com.joytech.premium.bean.MO;
import com.joytech.premium.bean.ManufactureNo;
import com.joytech.premium.bean.ManufactureOrder;
import com.joytech.premium.bean.QCProduct;
import com.joytech.premium.bean.QCResult;

@Service
public class KeyenceService {
	public static String JDBC_URL = "jdbc:sqlserver://192.168.1.9:1433;databaseName=TEST1;user=pwc;password=PWC@admin;characterEncoding=utf8;";
//	public static String JDBC_URL = "jdbc:sqlserver://192.168.1.9:1433;databaseName=JOYTECH;user=pwc;password=PWC@admin;characterEncoding=utf8;";
	public static String DBName="TEST1";//測試資料庫
//	public static String DBName="JOYTECH";//正式資料庫
	public static String PremiumCode="150E0";//卓越線生產線別
	public static String imageURL="http://192.168.1.15";
	public static String processCode="000";//製程代號(固定是000)
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
	
	public String getPrizeID() throws SQLException {
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		String resultData=null;
		try {
			conn = getDbConnection();
			ps = conn.prepareStatement("select TOP (1) CREATE_DATE from "+DBName+".dbo.JYT012");
			rs = ps.executeQuery();
			if(rs.next()) {
				resultData=rs.getString("CREATE_DATE");
//				System.out.println("resultSet="+resultData);				
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) rs.close();
		    if(ps!=null) ps.close();
		    if(conn!=null) conn.close();
		}		
		return resultData;
	}
	//找未完工的製令單別
	public ArrayList<ManufactureOrder> getAllManufactureOrder(String MOStateCode) throws SQLException{
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList to =new ArrayList();
		try {
			conn = getDbConnection();
			String sql = "select distinct m.TA001 as TA001 from "+DBName+".dbo.MOCTA as m"
					+ "  left join "+DBName+".dbo.SFCTA as s on m.TA001 = s.TA001 AND m.TA002 = s.TA002"
					+ "  where m.TA021='"+PremiumCode+"' and s.TA006='"+PremiumCode+"' and m.TA013 ='Y' and s.TA032 ='N'"
					+" and m.TA011 ='"+MOStateCode+"' order by m.TA001 asc";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				ManufactureOrder mo=new ManufactureOrder();
				mo.setManufactureOrder(rs.getString("TA001"));
//				System.out.println("rs.getString(TA001)="+rs.getString("TA001"));
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
	
	//找未完工的製令單號
		public ArrayList<ManufactureNo> getAllManufactureNo(String MOStateCode) throws SQLException{
			Connection conn = null;
			ResultSet rs = null;		
			PreparedStatement  ps = null;
			ArrayList to =new ArrayList();
			try {
				conn = getDbConnection();
				String sql = "select distinct m.TA002 as TA002 from "+DBName+".dbo.MOCTA as m"
						+ "  left join "+DBName+".dbo.SFCTA as s on m.TA001 = s.TA001 AND m.TA002 = s.TA002"
						+ "  where m.TA021='"+PremiumCode+"' and s.TA006='"+PremiumCode+"' and m.TA013 ='Y' and s.TA032 ='N'"
						+" and m.TA011 ='"+MOStateCode+"' order by m.TA002 asc";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()) {
					ManufactureNo mn=new ManufactureNo();
					mn.setManufactureNo(rs.getString("TA002"));
//					System.out.println("rs.getString(TA002)="+rs.getString("TA002"));
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
		
	//找產品品號
	public String getItemNumber(String manufactureOrder,String manufactureNo) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		String itemNumber=null;
		try {
			conn = getDbConnection();
			String sql = "select TA006 from "+DBName+".dbo.MOCTA"
					+" where TA001='"+manufactureOrder+"' and TA002='"+manufactureNo+"'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				itemNumber=rs.getString("TA006");
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) rs.close();
		    if(ps!=null) ps.close();
		    if(conn!=null) conn.close();
		}			
		return itemNumber;		
	}
	
	//找該產品品號的表單號碼最大值
	public String getMaxFormNumber(String itemNumber) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		String maxFormNumber=null;
		try {
			conn = getDbConnection();
			String sql = "SELECT  JYT012a002 FROM "+DBName+".dbo.JYT012 where JYT012a002=(select  max(JYT012a002) from "+DBName+".dbo.JYT012"
					+" where JYT012a005='"+itemNumber+"')";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				maxFormNumber=rs.getString("JYT012a002");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) rs.close();
		    if(ps!=null) ps.close();
		    if(conn!=null) conn.close();
		}	
		return maxFormNumber;
	}
	
	//去DSS找基本資料
	public JYT012 getPrimumQC(String manufactureOrder,String manufactureNo,String itemNumber,String maxFormNumber) throws SQLException{
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
//		ArrayList to =new ArrayList();
		JYT012 jyt012=new JYT012();
		try {
			conn = getDbConnection();
			String sql = "select JYT012a002,JYT012a003,JYT012a004,JYT012a005,JYT012a006,UDF01,UDF02 from "+DBName+".dbo.JYT012 where JYT012a005='"+itemNumber+"'"
					+" and JYT012a002='"+maxFormNumber+"'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {				
				jyt012.setManufactureOrder(manufactureOrder);
				jyt012.setManufactureNo(manufactureNo);
				jyt012.setJYT012a002(rs.getString("JYT012a002"));
				jyt012.setJYT012a003(rs.getString("JYT012a003"));
				jyt012.setUDF02(rs.getString("UDF02"));
				jyt012.setJYT012a004(rs.getString("JYT012a004"));
				jyt012.setJYT012a005(rs.getString("JYT012a005"));
				jyt012.setJYT012a006(rs.getString("JYT012a006"));
//				jyt012.setJYT012b006(rs.getString("JYT012b006"));
				jyt012.setUDF01(rs.getString("UDF01"));
				jyt012.setFullImageName(imageURL+"/img/"+rs.getString("JYT012a002")+"/"+rs.getString("UDF01"));				
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
	
	//找首件/自主檢查項目
	public ArrayList<IpqcItem> getIpqc(String itemNumber,String maxFormNumber) throws SQLException{
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<IpqcItem> to =new ArrayList<IpqcItem>();
		String  NegativeValue="";
		String  PositiveValue="";
		try {
			conn = getDbConnection();
			String sql = "select JYT012b003,JYT012b005,JYT012b006,JYT012b007,JYT012b008,JYT012b009,JYT012b010,UDF03 from "+DBName+".dbo.JYT012 where JYT012a005='"+itemNumber+"'"
					+" and JYT012a002='"+maxFormNumber+"' order by JYT012b003 asc";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				IpqcItem ipqcItem =new IpqcItem();
				ipqcItem.setJYT012b003(rs.getString("JYT012b003"));
				ipqcItem.setJYT012b005(rs.getString("JYT012b005"));
				ipqcItem.setJYT012b006(rs.getString("JYT012b006"));
				ipqcItem.setJYT012b007(rs.getString("JYT012b007"));
				ipqcItem.setJYT012b008(rs.getString("JYT012b008"));
				ipqcItem.setJYT012b009(rs.getString("JYT012b009"));
				if(rs.getString("UDF03").equals("1")) {
					if(rs.getString("JYT012b008")!=null && !rs.getString("JYT012b008").equals("") &&
							rs.getString("JYT012b009")!=null && !rs.getString("JYT012b009").equals("") ) {
						NegativeValue = rs.getString("JYT012b009").replace("-", "");
						PositiveValue = rs.getString("JYT012b008").replace("+", "");
						BigDecimal bdNegativeValue=new BigDecimal(NegativeValue);
						BigDecimal bdPositiveValue=new BigDecimal(PositiveValue);
//						System.out.println(Float.valueOf(rs.getString("JYT012b009").replace("-", "")));
//						System.out.println("doubleValueNegative="+bdNegativeValue);
						ipqcItem.setMin(String.valueOf(rs.getBigDecimal("JYT012b007").subtract(bdNegativeValue)));
						ipqcItem.setMax(String.valueOf(rs.getBigDecimal("JYT012b007").add(bdPositiveValue)));
					}else if(rs.getString("JYT012b008")!=null && !rs.getString("JYT012b008").equals("")) {
						PositiveValue = rs.getString("JYT012b008").replace("+", "");
						BigDecimal bdPositiveValue=new BigDecimal(PositiveValue);
						ipqcItem.setMin("0");
						ipqcItem.setMax(String.valueOf(rs.getBigDecimal("JYT012b007").add(bdPositiveValue)));	
					}else if(rs.getString("JYT012b009")!=null && !rs.getString("JYT012b009").equals("")) {
						NegativeValue = rs.getString("JYT012b009").replace("-", "");
						BigDecimal bdNegativeValue=new BigDecimal(NegativeValue);
						ipqcItem.setMin(String.valueOf(rs.getBigDecimal("JYT012b007").subtract(bdNegativeValue)));	
					}
				}
				ipqcItem.setJYT012b010(rs.getString("JYT012b010"));
				ipqcItem.setUDF03(rs.getString("UDF03"));
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
	
	//找首件/自主檢查項目
		public IpqcItem getIpqcData(String itemNumber,String maxFormNumber,String SN) throws SQLException{
			Connection conn = null;
			ResultSet rs = null;		
			PreparedStatement  ps = null;
			IpqcItem ipqcItem =new IpqcItem();
//			ArrayList<IpqcItem> to =new ArrayList<IpqcItem>();
			try {
				conn = getDbConnection();
				String sql = "select JYT012b003,JYT012b005,JYT012b006,JYT012b007,JYT012b008,JYT012b009,JYT012b010,UDF03 from "+DBName+".dbo.JYT012 where JYT012a005='"+itemNumber+"'"
						+" and JYT012a002='"+maxFormNumber+"' and JYT012b003='"+SN+"' ";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if(rs.next()) {					
					ipqcItem.setJYT012b003(rs.getString("JYT012b003"));
					ipqcItem.setJYT012b005(rs.getString("JYT012b005"));
//					System.out.println("rs.getString(\"JYT012b005\")="+rs.getString("JYT012b005"));
					ipqcItem.setJYT012b006(rs.getString("JYT012b006"));
					ipqcItem.setJYT012b007(rs.getString("JYT012b007"));
					ipqcItem.setJYT012b008(rs.getString("JYT012b008"));
					ipqcItem.setJYT012b009(rs.getString("JYT012b009"));
					ipqcItem.setJYT012b010(rs.getString("JYT012b010"));
					ipqcItem.setUDF03(rs.getString("UDF03"));
//					to.add(ipqcItem);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(rs!=null) rs.close();
			    if(ps!=null) ps.close();
			    if(conn!=null) conn.close();
			}		
			return ipqcItem;		
		}
	
	//找下一個qc_id(主鍵)
	public String getQCId() throws SQLException{
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		String qcId=null;
		try {
			conn = getDbConnection();
			String sql = "SELECT IDENT_CURRENT('"+DBName+".dbo.PrimumQC')  +  (SELECT  IDENT_INCR('"+DBName+".dbo.PrimumQC')) as id";					
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				qcId=rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) rs.close();
		    if(ps!=null) ps.close();
		    if(conn!=null) conn.close();
		}
		return qcId;			
	}
		
	//insert檢驗說明書基本資料並回傳qc_id
	public String insSpecData(JYT012 jyt012,String ipqcCREATOR) throws SQLException {
		Connection conn = null;
		Statement  stmt = null;
		ResultSet rs = null;
		PreparedStatement  ps = null;
		String qcId=null;
		try {
			conn = getDbConnection();
			stmt = conn.createStatement();
			String sql = "INSERT INTO "+DBName+".dbo.PrimumQC(itemName,specification,manufactureOrder,manufactureNo,partNumber,imageNumber,processCode,version," +
	                   "formNumber,customerCode,firstItemDate,firstItemStaff,predictQty,machineNumber,productionLine,imageFileName,ipqcCREATOR)"+
	                   " VALUES (N'"+jyt012.getJYT012a003()+"',N'"+jyt012.getUDF02()+"','"+jyt012.getManufactureOrder()+"','"+jyt012.getManufactureNo()+"','"+jyt012.getJYT012a005()+"','"+jyt012.getJYT012a004()+
	                   "','"+processCode+"','"+jyt012.getJYT012a006()+"','"+jyt012.getJYT012a002()+"','"+jyt012.getCustomerCode()+
	                   "','"+jyt012.getFirstItemDate()+"','"+jyt012.getFirstItemStaff()+"','"+jyt012.getPredictQty()+"','"+jyt012.getMachineNumber()+
	                   "','"+PremiumCode+"','"+jyt012.getUDF01()+"','"+ipqcCREATOR+"')";					
			stmt.executeUpdate(sql);
			String sqlID="SELECT IDENT_CURRENT('"+DBName+".dbo.PrimumQC') as id";
			ps = conn.prepareStatement(sqlID);
			rs = ps.executeQuery();
			if(rs.next()) {
				qcId=rs.getString("id");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) rs.close();
		    if(ps!=null) ps.close();
			if(stmt!=null) stmt.close();
		    if(conn!=null) conn.close();			
		}
		return qcId;	
	}
	
	//insert首件/自主檢查記錄實測狀況
	public void insIpqcData(String qc_id,String itemSN,String testItem,String testUnit,String standardValue,String upperLimit,String lowerLimit,
			String testTool,String flag,String ipqc1,String ipqc2,String ipqc3,String ipqcCREATOR) throws SQLException {
		Connection conn = null;
		Statement  stmt = null;
		try {
//			System.out.println("testItem="+testItem);
			conn = getDbConnection();
			stmt = conn.createStatement();
			String sql = "INSERT INTO "+DBName+".dbo.PrimumQCContent(qc_id,itemSN,testItem,testUnit,standardValue,upperLimit,lowerLimit," +
	                   "testTool,flag,ipqc1,ipqc2,ipqc3,ipqcCREATOR)"+
	                   " VALUES ('"+qc_id+"','"+itemSN+"',N'"+testItem+"',N'"+testUnit+"',N'"+standardValue+
	                   "','"+upperLimit+"','"+lowerLimit+"','"+testTool+"','"+flag+
	                   "','"+ipqc1+"','"+ipqc2+"','"+ipqc3+"','"+ipqcCREATOR+"')";
//			System.out.println("sql="+sql);
			stmt.executeUpdate(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(stmt!=null) stmt.close();
		    if(conn!=null) conn.close();			
		}	
	}
	
	//品檢人員找尋未檢驗的首件/自主檢查記錄列表
	public ArrayList<QCProduct> getQCPrtoduct(String manufactureOrder,String manufactureNo,String beginDate,String endDate) throws SQLException{
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<QCProduct> to =new ArrayList<QCProduct>();
		try {
			conn = getDbConnection();					
			String sql = "select p.qc_id,p.itemName,p.manufactureOrder,p.manufactureNo,p.partNumber,p.ipqcCREATE_DATE from "+DBName+".dbo.PrimumQC as p"
					+" left join "+DBName+".dbo.PrimumQCContent as c on p.qc_id = c.qc_id where p.manufactureOrder='"+manufactureOrder+"' and p.manufactureNo='"+manufactureNo+"'"
					+" and c.pqc1 is null and p.ipqcCREATE_DATE>='"+beginDate+"' AND p.ipqcCREATE_DATE<='"+endDate+"'"
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
	
	//去PrimumQC和PrimumQCContent找未完成的製令單別
	public ArrayList<ManufactureOrder> getQCManufactureOrder() throws SQLException{
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<ManufactureOrder> to =new ArrayList<ManufactureOrder>();
		try {
			conn = getDbConnection();			
			String sql = "select distinct p.manufactureOrder as manufactureOrder from "+DBName+".dbo.PrimumQC p,"+DBName+".dbo.PrimumQCContent c where "
					+"p.qc_id=c.qc_id and c.pqc1 is null";
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
	
	//去PrimumQC和PrimumQCContent找未完成的製令單號
	public ArrayList<ManufactureNo> getQCManufactureNo() throws SQLException{
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<ManufactureNo> to =new ArrayList<ManufactureNo>();
		try {
			conn = getDbConnection();			
			String sql = "select distinct p.manufactureNo from "+DBName+".dbo.PrimumQC p,"+DBName+".dbo.PrimumQCContent c where "
					+"p.qc_id=c.qc_id and c.pqc1 is null";
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
			String sql = "select qc_id,itemName,specification,manufactureOrder,manufactureNo,partNumber,imageNumber,version,formNumber,customerCode,firstItemDate,firstItemStaff,predictQty,machineNumber,imageFileName from "+DBName+".dbo.PrimumQC where "
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
				jyt012.setCustomerCode(rs.getString("manufactureNo"));
				jyt012.setJYT012a002(rs.getString("customerCode"));
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
			String sql = "select qc_id,itemSN,testItem,testUnit,standardValue,upperLimit,lowerLimit,testTool,flag,ipqc1,ipqc2,ipqc3 from "+DBName+".dbo.PrimumQCContent where "
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
	
	//找ERP不良原因
	public ArrayList<BadReasons> getBadReasons() throws SQLException{
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<BadReasons> to =new ArrayList<BadReasons>();
		try {
			conn = getDbConnection();			
			String sql = "select MH001,MH002 from "+DBName+".dbo.QMSMH ";
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
			String sql = "update "+DBName+".dbo.PrimumQCContent set pqc1='"+pqc1.toString()+"',pqc2='"+pqc2.toString()+"',pqc3='"+pqc3.toString()+"'"
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
			String sql = "insert into "+DBName+".dbo.PrimumQCResult(qc_id,defective,determination,supervisor,inspectors,handleResult,pqcCREATOR)"
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
	
	public ArrayList<QCProduct> getQCProductByPage() throws SQLException {
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		ArrayList<QCProduct> to = new ArrayList<>();
        try {
        	conn = getDbConnection();            
        	String sql = "SELECT distinct p.qc_id,p.itemName,p.manufactureOrder,p.manufactureNo,p.partNumber,p.ipqcCREATE_DATE FROM "+DBName+".dbo.PrimumQC as p,"
        			+""+DBName+".dbo.PrimumQCContent as c where p.qc_id=c.qc_id and c.pqc1 is not null";
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
        	String sql = "SELECT qc_id,itemSN,testItem,testUnit,standardValue,upperLimit,lowerLimit,testTool,flag,ipqc1,ipqc2,ipqc3,pqc1,pqc2,pqc3 FROM "+DBName+".dbo.PrimumQCContent"
        			+" where qc_id='"+qc_id+"'";
        	ps = conn.prepareStatement(sql);           
            rs = ps.executeQuery();
            while (rs.next()) {
            	IpqcItem ipqcItem = new IpqcItem();
            	ipqcItem.setJYT012b003(rs.getString("itemSN"));
            	ipqcItem.setJYT012b005(rs.getString("testItem"));
            	ipqcItem.setJYT012b006(rs.getString("testUnit"));
            	ipqcItem.setJYT012b007(rs.getString("standardValue"));
            	ipqcItem.setJYT012b008(rs.getString("upperLimit"));
            	ipqcItem.setJYT012b009(rs.getString("lowerLimit"));
            	ipqcItem.setJYT012b010(rs.getString("testTool"));
            	ipqcItem.setUDF03(rs.getString("flag"));
            	ipqcItem.setIpqc1(rs.getString("ipqc1"));
            	ipqcItem.setIpqc2(rs.getString("ipqc2"));
            	ipqcItem.setIpqc3(rs.getString("ipqc3"));
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
	
	//找判結果資料
	public QCResult getQCResultHistory(String qc_id) throws SQLException {
		Connection conn = null;
		ResultSet rs = null;		
		PreparedStatement  ps = null;
		QCResult qcResult =new QCResult();
		try {
			conn = getDbConnection();            
        	String sql = "SELECT qc_id,defective,determination,supervisor,inspectors,handleResult FROM "+DBName+".dbo.PrimumQCResult"
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
	
}
