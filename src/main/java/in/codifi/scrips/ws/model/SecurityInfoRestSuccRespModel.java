package in.codifi.scrips.ws.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecurityInfoRestSuccRespModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("openinterest")
	public float openinterest;
	@JsonProperty("elmSellMargin")
	public String elmSellMargin;
	@JsonProperty("IntPayDate")
	public String intPayDate;
	@JsonProperty("DeliveryLastDate")
	public String deliveryLastDate;
	@JsonProperty("TenderPeriodStart")
	public String tenderPeriodStart;
	@JsonProperty("Token")
	public String token;
	@JsonProperty("ValueTradedToday")
	public String valueTradedToday;
	@JsonProperty("LocalUpdateTime")
	public String localUpdateTime;
	@JsonProperty("MaxOrderSize")
	public String maxOrderSize;
	@JsonProperty("TickSize")
	public String tickSize;
	@JsonProperty("exDate")
	public String exDate;
	@JsonProperty("ExpulsionDate")
	public String expulsionDate;
	@JsonProperty("BuyVarMargin")
	public String buyVarMargin;
	@JsonProperty("CreditRating")
	public String creditRating;
	@JsonProperty("RecordDate")
	public String recordDate;
	@JsonProperty("Spotprc")
	public String spotprc;
	@JsonProperty("LastTradePrice")
	public String lastTradePrice;
	@JsonProperty("Exchange")
	public String exchange;
	@JsonProperty("deliveryLot")
	public String deliveryLot;
	@JsonProperty("deliveryReqd")
	public String deliveryReqd;
	@JsonProperty("Margin")
	public String margin;
	@JsonProperty("HCircuitLimit")
	public String hCircuitLimit;
	@JsonProperty("Warning")
	public Integer warning;
	@JsonProperty("Blq")
	public Integer blq;
	@JsonProperty("NoDelEndTime")
	public String noDelEndTime;
	@JsonProperty("bloombergCode")
	public String bloombergCode;
	@JsonProperty("dividend")
	public String dividend;
	@JsonProperty("additionalBuyMargin")
	public String additionalBuyMargin;
	@JsonProperty("dividendPayDate")
	public String dividendPayDate;
	@JsonProperty("Freeze")
	public Integer freeze;
	@JsonProperty("CA")
	public String ca;
	@JsonProperty("CouponRate")
	public String couponRate;
	@JsonProperty("stat")
	public String stat;
	@JsonProperty("PriceDenomenator")
	public String priceDenomenator;
	@JsonProperty("SedolCode")
	public String sedolCode;
	@JsonProperty("ReuterCode")
	public String reuterCode;
	@JsonProperty("IsuStartDate")
	public String isuStartDate;
	@JsonProperty("GeneralDenomenator")
	public String generalDenomenator;
	@JsonProperty("ReAdminDate")
	public String reAdminDate;
	@JsonProperty("DeliveryUnits")
	public String deliveryUnits;
	@JsonProperty("information")
	public String information;
	@JsonProperty("SpecialBuyMargin")
	public String specialBuyMargin;
	@JsonProperty("preExpirySellMargin")
	public String preExpirySellMargin;
	@JsonProperty("ISINCode")
	public String iSINCode;
	@JsonProperty("eligibilityToRepay")
	public String eligibilityToRepay;
	@JsonProperty("ListingDate")
	public String listingDate;
	@JsonProperty("DPR1")
	public String dpr1;
	@JsonProperty("DPR2")
	public String dpr2;
	@JsonProperty("OtherSellMargin")
	public String otherSellMargin;
	@JsonProperty("TenderPeriodEnd")
	public String tenderPeriodEnd;
	@JsonProperty("preExpiryBuyMargin")
	public String preExpiryBuyMargin;
	@JsonProperty("Remarks")
	public String remarks;
	@JsonProperty("Exposuremargin")
	public String exposuremargin;
	@JsonProperty("SellCryCost")
	public Float sellCryCost;
	@JsonProperty("Issuerate")
	public Integer issuerate;
	@JsonProperty("eligibilityToRecall")
	public String eligibilityToRecall;
	@JsonProperty("TenderPeriod")
	public String tenderPeriod;
	@JsonProperty("BuyCryCost")
	public Integer buyCryCost;
	@JsonProperty("ELMMargin")
	public String eLMMargin;
	@JsonProperty("SellVarMargin")
	public String sellVarMargin;
	@JsonProperty("NoDelStartDate")
	public String noDelStartDate;
	@JsonProperty("CoverOrderTrigger")
	@JsonAlias({ "CoverOrderTrigger%" })
	public String coverOrderTrigger;
	@JsonProperty("MaxOrderLimit")
	public String maxOrderLimit;
	@JsonProperty("Totalopeninterest")
	public Integer totalopeninterest;
	@JsonProperty("maxOrderQty")
	public String maxOrderQty;
	@JsonProperty("LCircuitLimit")
	public String lCircuitLimit;
	@JsonProperty("ContractType")
	public String contractType;
	@JsonProperty("SpecialSellMargin")
	public String specialSellMargin;
	@JsonProperty("ScripMargin")
	public String scripMargin;
	@JsonProperty("LastTradingDate")
	public String lastTradingDate;
	@JsonProperty("DeliveryStartDate")
	public String deliveryStartDate;
	@JsonProperty("IMSpreadBenefit")
	public String iMSpreadBenefit;
	@JsonProperty("IsuMaturityDate")
	public String isuMaturityDate;
	@JsonProperty("BookClsStartTime")
	public String bookClsStartTime;
	@JsonProperty("Symbol")
	public String symbol;
	@JsonProperty("Perchgopeninterest")
	public Integer perchgopeninterest;
	@JsonProperty("ExchSeg")
	public String exchSeg;
	@JsonProperty("PriceUnits")
	public String priceUnits;
	@JsonProperty("LTT")
	public String ltt;
	@JsonProperty("MarketType")
	public String marketType;
	@JsonProperty("varMargin")
	public String varMargin;
	@JsonProperty("adhocScripMargin")
	@JsonAlias({ "adhocScripMargin " })
	public String adhocScripMargin;
	@JsonProperty("BookClsEndTime")
	public String bookClsEndTime;
	@JsonProperty("additionalSellMargin")
	public String additionalSellMargin;
	@JsonProperty("PermitedtoTrade")
	public Integer permitedtoTrade;
	@JsonProperty("ExpiryDte")
	public String expiryDte;
	@JsonProperty("Comments")
	public String comments;
	@JsonProperty("elmBuyMargin")
	public String elmBuyMargin;
	@JsonProperty("PriceNumerator")
	public String priceNumerator;
	@JsonProperty("DeliveryEndDate")
	public String deliveryEndDate;
	@JsonProperty("PriceQuatation")
	public String priceQuatation;
	@JsonProperty("ContractName")
	public String contractName;
	@JsonProperty("Series")
	public String series;
	@JsonProperty("UniqueKey")
	public String uniqueKey;
	@JsonProperty("faceValue")
	public String faceValue;
	@JsonProperty("Instrument")
	public String instrument;
	@JsonProperty("assetName")
	public String assetName;
	@JsonProperty("IssedCapital")
	public String issedCapital;
	@JsonProperty("GeneralNumerator")
	public String generalNumerator;
	@JsonProperty("QuantityUnit")
	public String quantityUnit;
	@JsonProperty("OtherBuyMargin")
	public String otherBuyMargin;
	@JsonProperty("settlementType")
	public String settlementType;

}
