package com.koscom.project.hedge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.koscom.project.hedge.domain.Hedge;
import com.koscom.project.hedge.domain.HedgeLogic;
import com.koscom.project.hedge.jpa.HedgeRepository;

@RestController
class HedgeController {

	private final HedgeRepository repository;

	@Autowired
	private HedgeLogic logic;

	HedgeController(HedgeRepository repository) {
		this.repository = repository;
	}

	/*
	 * 기능 : 수출대금과 수취예정년도에 따른 수출대금 환 헷지  
	 * 
	 * @param inquiryInfo 수출대금(exportPrice), 수취예정년도(year)
	 * 
	 * @return Hedge 
	 */
	@PostMapping("/hedge/export")
	Hedge hedgingExport(@RequestBody Hedge inquiryInfo) {
		return logic.hedgingExport(inquiryInfo);
	}
		
	/*
	 * 기능 : 수입대금과 수취예정년도에 따른 수출대금 환 헷지  
	 * 
	 * @param inquiryInfo 수입대금(exportPrice), 수취예정년도(year)
	 * 
	 * @return Hedge 
	 */
	@PostMapping("/hedge/income")
	Hedge hedgingIncome(@RequestBody Hedge inquiryInfo) {
		return logic.hedgingIncome(inquiryInfo);
	}	
}