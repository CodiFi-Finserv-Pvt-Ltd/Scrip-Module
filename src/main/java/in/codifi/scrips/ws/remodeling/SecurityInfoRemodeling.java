package in.codifi.scrips.ws.remodeling;

import javax.enterprise.context.ApplicationScoped;

import in.codifi.scrips.model.transformation.SecurityInfoRespModel;
import in.codifi.scrips.ws.model.SecurityInfoRestSuccRespModel;
import io.quarkus.logging.Log;

@ApplicationScoped
public class SecurityInfoRemodeling {

	/*
	 * method to bind get security information
	 * 
	 * @author SOWMIYA
	 * 
	 * @return
	 */
	public SecurityInfoRespModel bindSecurityInfoData(SecurityInfoRestSuccRespModel respModel) {
		SecurityInfoRespModel response = new SecurityInfoRespModel();
		try {
			response.setLowerCircuit(respModel.getLCircuitLimit());
			response.setUpperCircuit(respModel.getHCircuitLimit());
			response.setCompanyName(respModel.getContractName());
			response.setExchange(respModel.getExchange());
			response.setTime(respModel.getLtt());
			response.setTotalTradedVolume(respModel.getValueTradedToday());
			response.setInstrumentName(respModel.getUniqueKey());
			response.setIsin(respModel.getISINCode());
			response.setLotSize(String.valueOf(respModel.getBlq()));
			response.setMultiplier(respModel.getGeneralNumerator());
//			response.setExpiry(respModel.getDeliveryEndDate());
			response.setExpiry(respModel.getExpiryDte());
//			response.setPricePrecision(respModel.getPp());
			response.setSegment(respModel.getExchSeg());
			response.setSymbolName(respModel.getSymbol());
			double tickSize = Double.parseDouble(respModel.getTickSize()) / 100.0;
			response.setTickSize(String.valueOf(tickSize));
			response.setLastTradedDate(respModel.getLtt());
			response.setToken(respModel.getToken());
			response.setFreezeQty(String.valueOf(respModel.getFreeze()));
			response.setTradingSymbol(respModel.getSymbol());
			response.setDeliveryUnits(respModel.getDeliveryUnits());
			response.setStartDate(respModel.getIsuStartDate());
			response.setMarketType(respModel.getMarketType());
			response.setOpenInterest(String.valueOf(respModel.getOpeninterest()));
			response.setSpotPrice(respModel.getLastTradePrice());
			response.setExchangeTime(respModel.getLtt());
			response.setEndDate(respModel.getLtt());

			String issuedCapitalStr = respModel.getIssedCapital();
			String numericalPart = issuedCapitalStr.replaceAll("[^0-9.]", "");
			double issuedCapital = Double.parseDouble(numericalPart);
//			double issuedCapital = Double.parseDouble(respModel.getIssedCapital());
			issuedCapital *= 100000;
			response.setIssuedCapital(String.format("%.0f", issuedCapital));
			response.setSettlementType(respModel.getSettlementType());
			response.setAvmBuyMargin(respModel.getBuyVarMargin());
			response.setAvmSellMargin(respModel.getSellVarMargin());
			response.setFaceValue(respModel.getFaceValue());
			response.setRemarks(respModel.getRemarks());
			response.setBookClosureStartDate(respModel.getBookClsStartTime());
			response.setBookClosureEndDate(respModel.getBookClsEndTime());
		} catch (Exception e) {
			Log.error(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

}
