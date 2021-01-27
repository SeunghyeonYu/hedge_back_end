package com.koscom.project.hedge.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceVariable {

	@SerializedName("cur_unit")
	private String curUnit;		// 통화코드
	private String ttb;			// 현물환 매입환율
	private String tts;			// 현물환 매도환율
	private String futurePurchaseRate;		// 선물환 매입환율
	private String futureSellingRate;		// 선물환 매도환율
	private String usdDepositInteresetRate;		// 달러화 예금이자율
	private String usdBorrowingInteresetRate;	// 달러화 차입이자율
	private String krwDepositInteresetRate;		// 원화 예금이자율
	private String krwBorrowingInteresetRate;	// 원화 차입이자율
	
	public String getCurUnit() { return this.curUnit; }
	public void setCurUnit(String curUnit) { this.curUnit = curUnit;}
	
	public String getTtb() { return this.ttb; }
	public void setTtb(String ttb) { this.ttb = ttb; }
	
	public String getTts() { return this.tts; }
	public void setTts(String tts) { this.tts = tts; }
	
	public String getFuturePurchaseRate() { return this.futurePurchaseRate; }
	public void setFuturePurchaseRate(String futurePurchaseRate) { this.futurePurchaseRate = futurePurchaseRate; }
	
	public String getFutureSellingRate() { return this.futureSellingRate; }
	public void setFutureSellingRate(String futureSellingRate) { this.futureSellingRate = futureSellingRate; }
	
	public String getUsdDepositInteresetRate() { return this.usdDepositInteresetRate; }
	public void setUsdDepositInteresetRate(String usdDepositInteresetRate) { this.usdDepositInteresetRate = usdDepositInteresetRate; }
	
	public String getUsdBorrowingInteresetRate() { return this.usdBorrowingInteresetRate; }
	public void setUsdBorrowingInteresetRate(String usdBorrowingInteresetRate) { this.usdBorrowingInteresetRate = usdBorrowingInteresetRate; }
	
	public String getKrwDepositInteresetRate() { return this.krwDepositInteresetRate; }
	public void setKrwDepositInteresetRate(String krwDepositInteresetRate) { this.krwDepositInteresetRate = krwDepositInteresetRate; }
	
	public String getKrwBorrowingInteresetRate() { return this.krwBorrowingInteresetRate; }
	public void setKrwBorrowingInteresetRate(String krwBorrowingInteresetRate) { this.krwBorrowingInteresetRate = krwBorrowingInteresetRate; }
}
