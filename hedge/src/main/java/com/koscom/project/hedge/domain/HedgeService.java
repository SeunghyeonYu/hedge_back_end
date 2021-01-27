package com.koscom.project.hedge.domain;

public interface HedgeService {
	
	/*
	 * 기능 : 수출대금과 수취예정년도에 따른 수출대금 환 헷지  
	 * @param searchData 수출대금(exportPrice), 수취예정년도(year)
	 * @return Hedge 
	 */
	Hedge hedgingExport(Hedge hedge);
	
	/*
	 * 기능 : 수입대금과 수취예정년도에 따른 수출대금 환 헷지  
	 * @param searchData 수입대금(exportPrice), 수취예정년도(year)
	 * @return Hedge 
	 */
	Hedge hedgingIncome(Hedge hedge);
}
