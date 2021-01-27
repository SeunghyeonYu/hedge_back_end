package com.koscom.project.hedge.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Hedge {

	
	private @Id int year;			// 연도
	private String exportPrice;		// 수출대금
	private String receiveExportPayment;	// 수출대금 수취
	private String spotPurchaseRate; 		// 현물환 매입환율
	private String spotSellingRate; 		// 현물환 매도환율
	private String futurePurchaseRate; 		// 선물환 매입환율
	private String futureSellingRate; 		// 선물환 매도환율
	private String usdDepositInteresetRate; 		// 달러화 예금이자율
	private String usdBorrowingInteresetRate; 		// 달러화 차입이자율
	private String krwDepositInteresetRate;			// 원화 예금이자율
	private String krwBorrowingInteresetRate;		// 원화 차입이자율

	private String forwardSell;					// 선물환시장을 이용한 헷지 - 선물환매도
	private String forwardPurchase;				// 선물환시장을 이용한 헷지 - 선물환매입
	private String sumOfForward;				// 선물환시장을 이용한 헷지 - 합계
	private double resultOfForward;				// 선물환시장을 이용한 헷지 - 합계
	
	private String dollarBorrowing;		// 단기금융시장을 이용한 헷지 - 달러화 차입
	private String dollarBuying;		// 단기금융시장을 이용한 헷지 - 현물환 매도 
	private String krwLoanT0;			// 단기금융시장을 이용한 헷지 - T0일때 원화 대출
	private String krwLoanTN;			// 단기금융시장을 이용한 헷지 - Tn일때 원화 대출
	private String dollarLoan;			// 단기금융시장을 이용한 헷지 - 달러화 대출
	private String dollarPurchase;		// 단기금융시장을 이용한 헷지 - 현물환 매입 
	private String krwBorrowingT0;		// 단기금융시장을 이용한 헷지 - T0일때 원화 대출
	private String krwBorrowingTN;		// 단기금융시장을 이용한 헷지 - Tn일때 원화 대출
	private String sumOfMarket;			// 단기금융시장을 이용한 헷지 - 합계
	private double resultOfMarket;		// 단기금융시장을 이용한 헷지 - 합계
	
	private String resultHedge;			// 헷지 결과
	
	Hedge() {
	}

	public int getYear() {
		return this.year;
	}
	
	public String getExportPrice() {
		return this.exportPrice;
	}

	public String getReceiveExportPayment() {
		return this.receiveExportPayment;
	}
	
	public String getSpotPurchaseRate() {
		return this.spotPurchaseRate;
	}

	public String getSpotSellingRate() {
		return this.spotSellingRate;
	}

	public String getFuturePurchaseRate() {
		return this.futurePurchaseRate;
	}

	public String getFutureSellingRate() {
		return this.futureSellingRate;
	}
	
	public String getUsdDepositInteresetRate() {
		return this.usdDepositInteresetRate;
	}
	
	public String getUsdBorrowingInteresetRate() {
		return this.usdBorrowingInteresetRate;
	}
	
	public String getKrwDepositInteresetRate() {
		return this.krwDepositInteresetRate;
	}
	
	public String getKrwBorrowingInteresetRate() {
		return this.krwBorrowingInteresetRate;
	}
	
	public String getForwardSell() {
		return this.forwardSell;
	}

	public String getForwardPurchase() {
		return this.forwardPurchase;
	}
	
	public String getSumOfForward() {
		return this.sumOfForward;
	}
	
	public double getResultOfForward() {
		return this.resultOfForward;
	}
	
	public String getDollarBorrowing() {
		return this.dollarBorrowing;
	}
	
	public String getDollarBuying() {
		return this.dollarBuying;
	}
	
	public String getKrwLoanT0() {
		return this.krwLoanT0;
	}
	
	public String getKrwLoanTN() {
		return this.krwLoanTN;
	}
	
	public String getDollarLoan() {
		return this.dollarLoan;
	}
	
	public String getDollarPurchase() {
		return this.dollarPurchase;
	}
	
	public String getKrwBorrowingT0() {
		return this.krwBorrowingT0;
	}
	
	public String getKrwBorrowingTN() {
		return this.krwBorrowingTN;
	}
	
	
	public String getSumOfMarket() {
		return this.sumOfMarket;
	}
	
	public double getResultOfMarket() {
		return this.resultOfMarket;
	}
	
	public String getResultHedge() {
		return this.resultHedge;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public void setExportPrice(String exportPrice) {
		this.exportPrice = exportPrice;
	}
	
	public void setReceiveExportPayment(String receiveExportPayment) {
		this.receiveExportPayment = receiveExportPayment;
	}
	
	public void setSpotPurchaseRate(String spotPurchaseRate) {
		this.spotPurchaseRate = spotPurchaseRate;
	}

	public void setSpotSellingRate(String spotSellingRate) {
		this.spotSellingRate = spotSellingRate;
	}
	
	public void setFuturePurchaseRate(String futurePurchaseRate) {
		this.futurePurchaseRate = futurePurchaseRate;
	}

	public void setFutureSellingRate(String futureSellingRate) {
		this.futureSellingRate = futureSellingRate;
	}

	public void setUsdDepositInteresetRate(String usdDepositInteresetRate) {
		this.usdDepositInteresetRate = usdDepositInteresetRate;
	}

	public void setUsdBorrowingInteresetRate(String usdBorrowingInteresetRate) {
		this.usdBorrowingInteresetRate = usdBorrowingInteresetRate;
	}

	public void setKrwDepositInteresetRate(String krwDepositInteresetRate) {
		this.krwDepositInteresetRate = krwDepositInteresetRate;
	}
	
	public void setKrwBorrowingInteresetRate(String krwBorrowingInteresetRate) {
		this.krwBorrowingInteresetRate = krwBorrowingInteresetRate;
	}
	
	public void setForwardSell(String forwardSell) {
		this.forwardSell = forwardSell;
	}
	
	public void setForwardPurchase(String forwardPurchase) {
		this.forwardPurchase = forwardPurchase;
	}
	
	public void setSumOfForward(String sumOfForward) {
		this.sumOfForward = sumOfForward;
	}
	
	public void setResultOfForward(double resultOfForward) {
		this.resultOfForward = resultOfForward;
	}
	
	public void setDollarBorrowing(String dollarBorrowing) {
		this.dollarBorrowing = dollarBorrowing;
	}
	
	public void setDollarBuying(String dollarBuying) {
		this.dollarBuying = dollarBuying;
	}
	
	public void setKrwLoanT0(String krwLoanT0) {
		this.krwLoanT0 = krwLoanT0;
	}
	
	public void setKrwLoanTN(String krwLoanTN) {
		this.krwLoanTN = krwLoanTN;
	}
	
	public void setDollarLoan(String dollarLoan) {
		this.dollarLoan = dollarLoan;
	}
	
	public void setDollarPurchase(String dollarPurchase) {
		this.dollarPurchase = dollarPurchase;
	}
	
	public void setKrwBorrowingT0(String krwBorrwingT0) {
		this.krwBorrowingT0 = krwBorrwingT0;
	}
	
	public void setKrwBorrowingTN(String krwBorrowingTN) {
		this.krwBorrowingTN = krwBorrowingTN;
	}
	
	public void setSumOfMarket(String sumOfMarket) {
		this.sumOfMarket = sumOfMarket;
	}
	
	public void setResultOfMarket(double resultOfMarket) {
		this.resultOfMarket = resultOfMarket;
	}

	public void setResultHedge(String resultHedge) {
		this.resultHedge = resultHedge;
	}
	
	
//  @Override
//  public boolean equals(Object o) {
//
//    if (this == o)
//      return true;
//    if (!(o instanceof Hedge))
//      return false;
//    Hedge hedge = (Hedge) o;
//    return Objects.equals(this.year, hedge.year);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(this.id, this.name, this.role);
//  }
//
//  @Override
//  public String toString() {
//    return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
//  }
}