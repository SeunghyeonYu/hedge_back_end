package com.koscom.project.hedge.domain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class HedgeLogic implements HedgeService {
	
	/*
	 * 기능 : 헷지(수출대금)
	 * 
	 * @param inquiryInfo 수출대금(exportPrice), 수취예정년도(year)
	 * 
	 * 
	 * @return Hedge 수출의 경우 헷지 결과
	 */
	@Override
	public Hedge hedgingExport(Hedge inquiryInfo) {
		
		// 가격변수정보조회
		Hedge result = new Hedge();
		result = findPriceVariable(inquiryInfo.getYear());
		
		
		// 선물환시장을 통한 헷지
		double exportPrice = Integer.parseInt(inquiryInfo.getExportPrice().replace(",", ""));		// 화면에서 입력받은 수출대금에 콤마(,) 제거
		Hedge forwardSelling = hedgingExportUsingForwardPurchase(result, exportPrice); 
		result.setForwardSell(forwardSelling.getForwardSell());
		result.setResultOfForward(forwardSelling.getResultOfForward());
		result.setSumOfForward(forwardSelling.getSumOfForward());
		
		
		// 단기금융시장을 통한 헷지
		Hedge market = hedgingExportUsingFinancialMarket(result, exportPrice);
		result.setDollarBorrowing(market.getDollarBorrowing());
		result.setDollarBuying(market.getDollarBuying());
		result.setKrwLoanT0(market.getKrwLoanT0());
		result.setKrwLoanTN(market.getKrwLoanTN());
		result.setResultOfMarket(market.getResultOfMarket());
		result.setSumOfMarket(market.getSumOfMarket());
		
		String message = "";
		if(result.getResultOfForward() - result.getResultOfMarket() < 0) {
			message = "단기금융시장에서 달러화 차입과, 현물환 매도, 원화대출을 통한 헷징이 ￦" + String.format("%,.0f", result.getResultOfMarket() - result.getResultOfForward())
				+ " 만큼 더 유리하다.";
		} else {
			message = "선물환 시장에서 달러 선물환 매도를 통한 헷징이 ￦" + String.format("%,.0f", result.getResultOfForward() - result.getResultOfMarket())
			+ " 만큼 더 유리하다.";
		}
		result.setResultHedge(message);
		
		return result;
	}
	

	/*
	 * 기능 : 가격변수 정보 조회(현물환 매입/매도 환율, 선물환 매입/매도 환율, 달러화 예금/차입 이자율, 원화 예금/차입 이자율)  
	 * 
	 * @param year 수취예정년도
	 * 
	 * @return Hedge 가격변수 정보
	 */
	public Hedge findPriceVariable(int year) {
		
		Hedge priceVariable = new Hedge();
		
//		// 수출입은행 API를 통한 현물환 매입/매도 환율정보 조회
//		Hedge exchangeRate = getSpotExchangeRate();
//		priceVariable.setSpotPurchaseRate(exchangeRate.getSpotPurchaseRate());		// 현물환 매입 환율정보 setting
//		priceVariable.setSpotSellingRate(exchangeRate.getSpotSellingRate());		// 현물환 매도 환율정보 setting
//		
//		
//		// 현물환 매입/매도 환율정보를 바탕으로 선물환 매입/매도 환율정보 계산
//		// 선물환 매입환율 = 현물환 매입환율 * (((1+한국기준금리) / (1+달러기준금리))^t(년))
//		BigDecimal spotPurchaseRate = new BigDecimal(exchangeRate.getSpotPurchaseRate().replace(",", ""));
//		BigDecimal forwardPurchaseRate = spotPurchaseRate.multiply(BigDecimal.valueOf(Math.pow((1+(0.5*0.01)) / (1+(0.25*0.01)), year)));
//		priceVariable.setFuturePurchaseRate(String.format("%,.0f", forwardPurchaseRate.setScale(0, RoundingMode.CEILING)));
//		
//		// 선물환 매도환율 = 현물환 매도환율 * (((1+한국기준금리) / (1+달러기준금리))^t(년))
//		BigDecimal spotSellingRate = new BigDecimal(exchangeRate.getSpotSellingRate().replace(",", ""));
//		BigDecimal forwardSellingRate = spotSellingRate.multiply(BigDecimal.valueOf(Math.pow((1+(0.5*0.01)) / (1+(0.25*0.01)), year)));   
//		priceVariable.setFutureSellingRate(String.format("%,.0f", forwardSellingRate.setScale(0, RoundingMode.CEILING)));
		
		
		priceVariable.setSpotPurchaseRate("1,245");		// 현물환 매입 환율정보 setting
		priceVariable.setSpotSellingRate("1,235");		// 현물환 매도 환율정보 setting
		priceVariable.setFuturePurchaseRate("1,242");
		priceVariable.setFutureSellingRate("1,230");
		
		// 달러화 예금/차입 이자율, 원화 예금/차입 이자율		
		priceVariable.setUsdDepositInteresetRate("7.6");		// 달러화 예금이자율
		priceVariable.setUsdBorrowingInteresetRate("8.4");		// 달러화 차입이자율
		priceVariable.setKrwDepositInteresetRate("5.6");		// 원화 예금이자율
		priceVariable.setKrwBorrowingInteresetRate("6.4");		// 원화 차입이자율

		return priceVariable;
	}

	
	/*
	 * 기능 : 한국수출입은행 Open API를 통한 매입/매도 환율정보 조회  
	 * 
	 * @param 
	 * 
	 * @return Hedge 매입/매도 환율정보
	 */	
	public Hedge getSpotExchangeRate() {
		
		Hedge hedge = new Hedge();
		try {
			// 수출입은행 Open API 조회
			String exchangeUrl = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=gX1MJdLX3EGpf3eg9yIvmtfWCuOlOGUg&searchdate=20210118&data=AP01";
			URL url = new URL(exchangeUrl);
			HttpURLConnection exchangeUrlConnection = (HttpURLConnection) url.openConnection();
			exchangeUrlConnection.setRequestMethod("GET");
			
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(exchangeUrlConnection.getInputStream(), "UTF-8"));
	        
			String result = "";
			String line;
			while((line = br.readLine()) != null) {
				result = result + line + "\n";
			}
			
			Gson gson = new Gson(); 
			PriceVariable[] exchangeRateList = gson.fromJson(result, PriceVariable[].class);
			
			// 여러 국가의 환율정보 중 대미환율 정보를 찾에 setting
			for(int i = 0; i < exchangeRateList.length; i++) {

				String curUnit = exchangeRateList[i].getCurUnit();
				if(curUnit != null && curUnit.equals("USD")) {
					
					// 콤마(,) 제거 후 소수점 반올림 하여 세팅
					double spotPurchaseRate = Math.round(Double.parseDouble(exchangeRateList[i].getTtb().replace(",", "")));
					double spotSellingRate = Math.round(Double.parseDouble(exchangeRateList[i].getTts().replace(",", "")));
					
					hedge.setSpotPurchaseRate(String.format("%,.0f", spotPurchaseRate));
					hedge.setSpotSellingRate(String.format("%,.0f", spotSellingRate));
				}
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}

		return hedge;
	}
	
	
	/*
	 * 기능 : 선물환시장을 통한 헷지(수출대금)
	 * 
	 * @param priceVariable 가격변수 정보
	 * 		  exportPrice 수출대금
	 * 
	 * @return Hedge 선물환시장을 통한 헷지 결과
	 */
	public Hedge hedgingExportUsingForwardPurchase(Hedge priceVariable, double exportPrice) {

		Hedge hedge = new Hedge();

		// 선물환 매도
		String forwardSell = "-$" + String.format("%,.0f", exportPrice) + " + " + priceVariable.getFutureSellingRate()
				+ "￦/$" + " X $" + String.format("%,.0f", exportPrice);
		hedge.setForwardSell(forwardSell);

		// 결과
		double resultOfForward = Double.parseDouble(priceVariable.getFutureSellingRate().replace(",", "")) * exportPrice;
		hedge.setResultOfForward(resultOfForward);

		// 합계
		String sumOfForward = "+￦" + String.format("%,.0f", resultOfForward);
		hedge.setSumOfForward(sumOfForward);

		return hedge;
	}
	
	/*
	 * 기능 : 단기금융시장을 통한 헷지(수출대금)
	 * 
	 * @param priceVariable 가격변수 정보
	 * 		  exportPrice 수출대금
	 * 
	 * @return Hedge 단기금융시장을 통한 헷지 결과
	 */
	public Hedge hedgingExportUsingFinancialMarket(Hedge priceVariable, double exportPrice) {

		Hedge hedge = new Hedge();

		// 달러화 차입
		String dollarBorrowing = "+$" + String.format("%,.2f", Math.round((exportPrice / (1 + 8.4 * 0.01 / 4)) * 100) / 100.0);
		hedge.setDollarBorrowing(dollarBorrowing);

		// 현물환 매도
		String dollarBuying = "-$" + String.format("%,.2f", Math.round((exportPrice / (1 + 8.4 * 0.01 / 4)) * 100) / 100.0) + " + ￦" + priceVariable.getSpotSellingRate() 
				+ " X " + String.format("%,.2f", Math.round((exportPrice / (1 + 8.4 * 0.01 / 4)) * 100) / 100.0);
		hedge.setDollarBuying(dollarBuying);

		// 원화 대출   
		String krwLoanT0 = "-￦" + String.format("%,.0f", ((Double.parseDouble(priceVariable.getSpotSellingRate().replace(",", ""))) * (Math.round((exportPrice / (1 + 8.4 * 0.01 / 4)) * 100) / 100.0)));
		String krwLoanTn = "+￦" + String.format("%,.0f", ((Double.parseDouble(priceVariable.getSpotSellingRate().replace(",", ""))) * (Math.round((exportPrice / (1 + 8.4 * 0.01 / 4)) * 100) / 100.0) * (1 + 5.6 * 0.01 / 4)));
		hedge.setKrwLoanT0(krwLoanT0);
		hedge.setKrwLoanTN(krwLoanTn);

		// 결과
		double resultOfMarket = ((Double.parseDouble(priceVariable.getSpotSellingRate().replace(",", ""))) 
				* (Math.round((exportPrice / (1 + 8.4 * 0.01 / 4)) * 100) / 100.0) * (1 + 5.6 * 0.01 / 4));
		hedge.setResultOfMarket(resultOfMarket);

		// 합계
		String sumOfMarket = "+￦" 
				+ String.format("%,.0f", ((Double.parseDouble(priceVariable.getSpotSellingRate().replace(",", ""))) * (Math.round((exportPrice / (1 + 8.4 * 0.01 / 4)) * 100) / 100.0) * (1 + 5.6 * 0.01 / 4)));
		hedge.setSumOfMarket(sumOfMarket);

		return hedge;
	}
	
	/*
	 * 기능 : 헷지(수입대금)
	 * 
	 * @param inquiryInfo 수입대금(exportPrice), 수취예정년도(year)
	 * 
	 * 
	 * @return Hedge 수입의 경우 헷지 결과
	 */
	@Override
	public Hedge hedgingIncome(Hedge inquiryInfo) {

		// 가격변수정보조회
		Hedge result = new Hedge();
		result = findPriceVariable(inquiryInfo.getYear());

		// 선물환시장을 통한 헷지
		double exportPrice = Integer.parseInt(inquiryInfo.getExportPrice().replace(",", "")); // 화면에서 입력받은 수출대금에 콤마(,) 제거
		Hedge forwardPurchase = hedgingIncomeUsingForwardPurchase(result, exportPrice);
		result.setForwardPurchase(forwardPurchase.getForwardPurchase());
		result.setResultOfForward(forwardPurchase.getResultOfForward());
		result.setSumOfForward(forwardPurchase.getSumOfForward());
				
		
		// 단기금융시장을 통한 헷지
		Hedge market = hedgingIncomeUsingFinancialMarket(result, exportPrice);
		result.setDollarLoan(market.getDollarLoan());
		result.setDollarPurchase(market.getDollarPurchase());
		result.setKrwBorrowingT0(market.getKrwBorrowingT0());
		result.setKrwBorrowingTN(market.getKrwBorrowingTN());
		result.setResultOfMarket(market.getResultOfMarket());
		result.setSumOfMarket(market.getSumOfMarket());
				
		
		String message = "";
		if(result.getResultOfForward() - result.getResultOfMarket() > 0) {
			message = "단기금융시장에서 달러화 차입과, 현물환 매도, 원화대출을 통한 헷징이 ￦" + String.format("%,.0f", result.getResultOfForward() - result.getResultOfMarket())
				+ " 만큼 더 유리하다.";
		} else {
			message = "선물환 시장에서 달러 선물환 매도를 통한 헷징이 ￦" + String.format("%,.0f", result.getResultOfMarket() - result.getResultOfForward())
			+ " 만큼 더 유리하다.";
		}
		result.setResultHedge(message);
		
		
		return result;
	}
	
	
	/*
	 * 기능 : 선물환시장을 통한 헷지(수입대금)
	 * 
	 * @param priceVariable 가격변수 정보
	 * 		  exportPrice 수입대금
	 * 
	 * @return Hedge 선물환시장을 통한 헷지 결과
	 */
	public Hedge hedgingIncomeUsingForwardPurchase(Hedge priceVariable, double incomePrice) {

		Hedge hedge = new Hedge();

		// 선물환 매입
		String forwardPurchase = "+$" + String.format("%,.0f", incomePrice) + " - " + priceVariable.getFutureSellingRate()
				+ "￦/$" + " X $" + String.format("%,.0f", incomePrice);
		hedge.setForwardPurchase(forwardPurchase);

		// 결과
		double resultOfForward = Double.parseDouble(priceVariable.getFuturePurchaseRate().replace(",", "")) * incomePrice;
		hedge.setResultOfForward(resultOfForward);

		// 합계
		String sumOfForward = "-￦" + String.format("%,.0f", resultOfForward);
		hedge.setSumOfForward(sumOfForward);

		return hedge;
	}
	
	
	/*
	 * 기능 : 단기금융시장을 통한 헷지(수입대금)
	 * 
	 * @param priceVariable 가격변수 정보
	 * 		  exportPrice 수입대금
	 * 
	 * @return Hedge 단기금융시장을 통한 헷지 결과
	 */
	public Hedge hedgingIncomeUsingFinancialMarket(Hedge priceVariable, double exportPrice) {

		Hedge hedge = new Hedge();

		// 달러화 대출
		String dollarLoan = "-$" + String.format("%,.2f", Math.round((exportPrice / (1 + 7.6 * 0.01 / 4)) * 100) / 100.0);
		hedge.setDollarLoan(dollarLoan);

		// 현물환 매입
		String dollarPurchase = "+$" + String.format("%,.2f", Math.round((exportPrice / (1 + 7.6 * 0.01 / 4)) * 100) / 100.0) + " + ￦" + priceVariable.getSpotPurchaseRate() 
				+ " X " + String.format("%,.2f", Math.round((exportPrice / (1 + 7.6 * 0.01 / 4)) * 100) / 100.0);
		hedge.setDollarPurchase(dollarPurchase);

		// 원화 차입   
		String krwBorrowingT0 = "+￦" + String.format("%,.0f", ((Double.parseDouble(priceVariable.getSpotPurchaseRate().replace(",", ""))) * (Math.round((exportPrice / (1 + 7.6 * 0.01 / 4)) * 100) / 100.0)));
		String krwBorrowingTn = "-￦" + String.format("%,.0f", ((Double.parseDouble(priceVariable.getSpotPurchaseRate().replace(",", ""))) * (Math.round((exportPrice / (1 + 7.6 * 0.01 / 4)) * 100) / 100.0) * (1 + 6.4 * 0.01 / 4)));
		hedge.setKrwBorrowingT0(krwBorrowingT0);
		hedge.setKrwBorrowingTN(krwBorrowingTn);

		// 결과
		double resultOfMarket = ((Double.parseDouble(priceVariable.getSpotPurchaseRate().replace(",", ""))) 
				* (Math.round((exportPrice / (1 + 7.6 * 0.01 / 4)) * 100) / 100.0) * (1 + 6.4 * 0.01 / 4));
		hedge.setResultOfMarket(resultOfMarket);

		// 합계
		String sumOfMarket = "-￦" 
				+ String.format("%,.0f", ((Double.parseDouble(priceVariable.getSpotPurchaseRate().replace(",", ""))) * (Math.round((exportPrice / (1 + 7.6 * 0.01 / 4)) * 100) / 100.0) * (1 + 6.4 * 0.01 / 4)));
		hedge.setSumOfMarket(sumOfMarket);

		return hedge;
	}
}