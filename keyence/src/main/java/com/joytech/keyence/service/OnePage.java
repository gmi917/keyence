package com.joytech.keyence.service;

import java.util.ArrayList;

import com.joytech.keyence.bean.QCProduct;

public class OnePage {
	/**
     *
     * @param itemNumPerPage int    每頁顯示幾筆資料
     * @param totalNum       int    總筆數
     * @param TotalpageNum   int    總頁數
     * @param curpage        int    目前所在的頁數
     * @param beginNr        int    第一筆資料
     * @param endNr          int    最後一筆資料
     * @return pagedata ArrayList   顯示資料
     */
	public OnePage() {
	  }
	  int TotalpageNum=1;
	  int curpage=1;
	  int endPageNum=1;
	  int itemNumPerPage=10;//分頁筆數
	  
	  //結束的頁碼
	  public int getendPageNum() {
	    if (this.TotalpageNum<=10) {
	      endPageNum=this.TotalpageNum;
	    }else {//網頁顯示的1~10頁數,會隨使用者行為而遞移;但最多顯示10頁頁數
	      if (this.TotalpageNum>10) {//頁數遞移
	        endPageNum=this.curpage+9;
	        if (endPageNum>this.TotalpageNum) {
	          endPageNum=this.TotalpageNum;
	        }
	      }
	    }
	    return endPageNum;
	  }

	  //開始的頁碼
	  public int getbeginPageNum() {
	    int beginPageNum=0;
	    if (this.curpage==1) {
	      beginPageNum=1;
	    }else {
	      beginPageNum=this.endPageNum-9;
	      if (beginPageNum<=1) {
	        beginPageNum=1;
	      }
	    }
	    return beginPageNum;
	  }

	  //目前所在的頁碼
	  public int getCurpage() {
	    return this.curpage;
	  }
	  //前一頁頁碼
	  public String getPrevPage() {
	    if (curpage==1) {
	      return null;
	    }else if (curpage-1<0) {
	      return null;
	    }else {
	      return String.valueOf(curpage - 1);
	    }
	  }
	  //下一頁頁碼
	  public String getNextPage() {
	    if (this.curpage >= this.TotalpageNum) {
	      return null;
	    }
	    return String.valueOf(curpage+1);
	  }
	  
	  public ArrayList OnePage(ArrayList data, String page) {
		    ArrayList pagedata = new ArrayList();		    
		    int totalNum = data.size();
		    if (totalNum<=itemNumPerPage) {
		      this.TotalpageNum=1;
		    }else {
		      if (totalNum % itemNumPerPage == 0) {
		        this.TotalpageNum = totalNum / itemNumPerPage;
		      }
		      else {
		        this.TotalpageNum = (totalNum / itemNumPerPage) + 1;
		      }
		    }
		    if (page!=null && !page.equals("")) {
		      this.curpage = Integer.parseInt(page);
		    }else {
		      this.curpage = 1;
		    }
		    int beginNr = (this.curpage-1)*itemNumPerPage+1;
		    int endNr = this.curpage*itemNumPerPage;
		    if (endNr>totalNum)	endNr=totalNum;

		    for (int i=beginNr-1;i<endNr;i++) {
		    	QCProduct qcProductCurdata = new QCProduct();
		        qcProductCurdata = (QCProduct) data.get(i);
		        pagedata.add(qcProductCurdata);
		    }
		    return pagedata;
		  }

}
