package in.codifi.scrips.model.transformation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityInfoRespModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String lowerCircuit;
	private String upperCircuit;
	private String spotPrice;
	private String tickSize;
	private String lotSize;
	private String lastTradedDate;
	private String startDate;
	private String openInterest;
	private String time;
	private String exchangeTime;
	private String endDate;
	private String totalTradedVolume;
	private String marketType;

	private String exchange;
	private String tradingSymbol;
	private String companyName;
	private String symbolName;
	private String segment;
	private String instrumentName;
	private String isin;
	private String pricePrecision;
	private String multiplier;
	private String tradeUnits;
	private String deliveryUnits;
	private String token;
	private String freezeQty;
	private String expiry;
	private String faceValue;
	private String remarks;
	private String issuedCapital;
	private String recordDate;
	private String settlementType;
	private String avmBuyMargin;
	private String avmSellMargin;
	private String bookClosureEndDate;
	private String bookClosureStartDate;
	private String intrinsicValue;
	

//	private String varMargin;
//	private String prcftr_d;
//	private String strikePrice;
//	private String optionType;
//	private String gp_nd;
//	private String priceUnit;
//	private String priceQuoteQty;
//	
//	private String scripupdateGsmInd;
//	private String elmBuyMargin;
//	private String elmSellMargin;
//	private String additionalLongMargin;
//	private String additionalShortMargin;
//	private String specialLongMargin;
//	private String SpecialShortMargin;
//	private String deliveryMargin;
//	private String tenderMargin;
//	private String tenderStartDate;
//
//	private String tenderEndEate;
//	private String exerciseStartDate;
//	private String exerciseEndDate;
//	private String markettype;
//	private String issuedate;
//	private String listingDate;
//	private String lastTradingDate;
//	private String elmMargin;
//	private String exposureMargin;
//	private String weekly;
//	private String Nontradableinstruments;
//	private String dname;
//	

}
