package in.codifi.scrips.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.ObjectUtils;
import org.jboss.resteasy.reactive.RestResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.codifi.cache.model.ClinetInfoModel;
import in.codifi.cache.model.ContractMasterModel;
import in.codifi.scrips.config.HazelcastConfig;
import in.codifi.scrips.entity.chartdb.PromptModel;
import in.codifi.scrips.model.request.GetContractInfoReqModel;
import in.codifi.scrips.model.request.SearchScripReqModel;
import in.codifi.scrips.model.request.SecurityInfoReqModel;
import in.codifi.scrips.model.response.ContractInfoDetails;
import in.codifi.scrips.model.response.ContractInfoRespModel;
import in.codifi.scrips.model.response.GenericResponse;
import in.codifi.scrips.model.response.ScripSearchResp;
import in.codifi.scrips.repository.ScripSearchEntityManager;
import in.codifi.scrips.service.spec.ScripsServiceSpecs;
import in.codifi.scrips.utility.AppConstants;
import in.codifi.scrips.utility.PrepareResponse;
import in.codifi.scrips.utility.StringUtil;
import in.codifi.scrips.ws.model.SecurityInfoRestReqModel;
import in.codifi.scrips.ws.service.SecurityInfoRestService;
import io.quarkus.logging.Log;

@ApplicationScoped
public class ScripsService implements ScripsServiceSpecs {

	@Inject
	ScripSearchEntityManager scripSearchRepo;

	@Inject
	PrepareResponse prepareResponse;

	@Inject
	SecurityInfoRestService restService;

	/**
	 * Method to get scrip
	 * 
	 * @author Dinesh Kumar
	 *
	 * @param reqModel
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> getScrips(SearchScripReqModel reqModel) {

		List<ScripSearchResp> responses = new ArrayList<>();
		try {
			/* To check where to fetch data */
			if (HazelcastConfig.getInstance().getFetchDataFromCache().get(AppConstants.FETCH_DATA_FROM_CACHE) != null
					&& HazelcastConfig.getInstance().getFetchDataFromCache().get(AppConstants.FETCH_DATA_FROM_CACHE)) {

				if (reqModel.getSymbol().trim().length() < 3) {
					if (HazelcastConfig.getInstance().getDistinctSymbols()
							.get(reqModel.getSymbol().trim().length()) != null
							&& HazelcastConfig.getInstance().getDistinctSymbols()
									.get(reqModel.getSymbol().trim().length()).size() > 0
							&& HazelcastConfig.getInstance().getDistinctSymbols()
									.get(reqModel.getSymbol().trim().length())
									.contains(reqModel.getSymbol().trim().toUpperCase())) {
						responses = getSearchDetailsFromCache(reqModel);
					}
				} else {
					responses = getSearchDetailsFromCache(reqModel);
				}
			} else {
				if (reqModel.getSymbol().trim().length() < 3) {
					if (HazelcastConfig.getInstance().getDistinctSymbols()
							.get(reqModel.getSymbol().trim().length()) != null
							&& HazelcastConfig.getInstance().getDistinctSymbols()
									.get(reqModel.getSymbol().trim().length()).size() > 0
							&& HazelcastConfig.getInstance().getDistinctSymbols()
									.get(reqModel.getSymbol().trim().length())
									.contains(reqModel.getSymbol().trim().toUpperCase())) {
						responses = scripSearchRepo.getScrips(reqModel);
					}
				} else {
					responses = scripSearchRepo.getScrips(reqModel);
				}
			}
			if (responses != null && responses.size() > 0) {
				return prepareResponse.prepareSuccessResponseObject(responses);
			} else {
				return prepareResponse.prepareFailedResponse(AppConstants.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
		}
		return prepareResponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
	}

	private List<ScripSearchResp> getSearchDetailsFromCache(SearchScripReqModel reqModel) {

		List<ScripSearchResp> responses = new ArrayList<>();
		/*
		 * Check the cache is not and storing is enabled or not
		 */
		String[] exchange = reqModel.getExchange();
		/*
		 * Check Exchange array contains ALL
		 */
		if (Arrays.stream(exchange).anyMatch("all"::equalsIgnoreCase)) {
			if (HazelcastConfig.getInstance().getLoadedSearchData()
					.get(reqModel.getSymbol().trim().toUpperCase()) != null) {
				responses = HazelcastConfig.getInstance().getLoadedSearchData()
						.get(reqModel.getSymbol().trim().toUpperCase());
			} else {
				responses = scripSearchRepo.getScrips(reqModel);
				if (responses != null && responses.size() > 0) {
					if (HazelcastConfig.getInstance().getIndexDetails()
							.get(reqModel.getSymbol().trim().toUpperCase()) != null) {
						ScripSearchResp result = HazelcastConfig.getInstance().getIndexDetails()
								.get(reqModel.getSymbol().trim().toUpperCase());
						responses.set(0, result);
						if (responses.size() > 24) {
							responses.remove(25);
						}
					}
					HazelcastConfig.getInstance().getLoadedSearchData().put(reqModel.getSymbol().trim().toUpperCase(),
							responses);
				}
			}
		} else {
			responses = scripSearchRepo.getScrips(reqModel);
		}
		return responses;
	}

	/**
	 * Method to get contract info
	 * 
	 * @author Dinesh Kumar
	 *
	 * @param model
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> getContractInfo(GetContractInfoReqModel model) {
		try {
			ContractInfoRespModel response = new ContractInfoRespModel();
			List<ContractInfoDetails> detailsList = new ArrayList<>();
			if (model != null && StringUtil.isNotNullOrEmpty(model.getToken())
					&& StringUtil.isNotNullOrEmpty(model.getExch())) {
				String token = model.getToken();
				String exch = model.getExch().toUpperCase();
				ContractMasterModel contractMasterModel = HazelcastConfig.getInstance().getContractMasterTr()
						.get(exch + "_" + token);
				System.out.println("contractMasterModelresponse---------" + contractMasterModel);
				if (ObjectUtils.isNotEmpty(contractMasterModel)) {
					ContractInfoDetails details = prepareContractInfoResp(contractMasterModel);
					detailsList.add(details);
					/** To add alter token details **/
					if (contractMasterModel != null && (exch.equalsIgnoreCase("NSE") || exch.equalsIgnoreCase("BSE"))
							&& StringUtil.isNotNullOrEmpty(contractMasterModel.getAlterToken())) {
						String altExch = exch.equalsIgnoreCase("BSE") ? "NSE" : "BSE";
						ContractMasterModel alterContractMasterModel = HazelcastConfig.getInstance()
								.getContractMasterTr().get(altExch + "_" + contractMasterModel.getAlterToken());
						if (alterContractMasterModel != null) {
							ContractInfoDetails altDetails = prepareContractInfoResp(alterContractMasterModel);
							detailsList.add(altDetails);
						}
					}
					response.setFreezeQty(contractMasterModel.getFreezQty());
					response.setIsin(contractMasterModel.getIsin());
					response.setScrips(detailsList);
					return prepareResponse.prepareSuccessResponseObject(response);
				} else {
					return prepareResponse.prepareFailedResponse(AppConstants.TOKEN_NOT_EXISTS);
				}
			} else {
				return prepareResponse.prepareFailedResponse(AppConstants.INVALID_PARAMETER);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
		}

		return prepareResponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
	}

	/**
	 * Method to prepare response for get contract info
	 * 
	 * @param tempDTO
	 * @author dinesh
	 * @return
	 */
	private ContractInfoDetails prepareContractInfoResp(ContractMasterModel model) {

		ContractInfoDetails details = new ContractInfoDetails();

		/** To add prompt message **/
		if (model != null && (model.getExch().equalsIgnoreCase("NSE") || model.getExch().equalsIgnoreCase("BSE"))) {
			if (HazelcastConfig.getInstance().getPromptMaster().get(model.getIsin() + "_" + model.getExch()) != null
					&& HazelcastConfig.getInstance().getPromptMaster().get(model.getIsin() + "_" + model.getExch())
							.size() > 0) {
				List<PromptModel> prompt = HazelcastConfig.getInstance().getPromptMaster()
						.get(model.getIsin() + "_" + model.getExch());
				if (prompt != null && prompt.size() > 0) {
					details.setPrompt(prompt);
				}
			}
		}

		details.setExchange(model.getExch());
		details.setLotSize(model.getLotSize());
		details.setTickSize(model.getTickSize());
		details.setToken(model.getToken());
		details.setTradingSymbol(model.getTradingSymbol());
		details.setSymbol(model.getSymbol());
		details.setFormattedInsName(model.getFormattedInsName());
		details.setPdc(model.getPdc());
		details.setInsType(model.getInsType());
		details.setExpiry(model.getExpiry());
		details.setOptionFlag(model.getOptionFlag());
		details.setMultiplier(model.getMultiplier());
		details.setPnlMultiplier(model.getPnlMultiplier());
		details.setPhysicalQty(model.getPhysicalQty());
		return details;
	}

	/*
	 * method to get security information
	 * 
	 * @author SOWMIYA
	 * 
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> getSecurityInfo(SecurityInfoReqModel model, ClinetInfoModel info) {
		try {

			/** Validate Request **/
			if (!validateSecurityInfoParameters(model))
				return prepareResponse.prepareFailedResponse(AppConstants.INVALID_PARAMETER);

			/** connect to TR server **/
			return restService.getSecurityInfo(model, info);

		} catch (Exception e) {
			Log.error(e.getMessage());
			e.printStackTrace();
		}

		return prepareResponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
	}

	/*
	 * method to get security information
	 * 
	 * @author SOWMIYA
	 * 
	 * @modified author nesan
	 * 
	 * @return
	 */
	private String prepareSecurityInfoRequest(SecurityInfoReqModel model, ClinetInfoModel info) {
		ObjectMapper mapper = new ObjectMapper();
		String request = "";
		try {
			SecurityInfoRestReqModel reqModel = new SecurityInfoRestReqModel();
			reqModel.setUid(info.getUserId());
			reqModel.setSrchFor(model.getToken());
			reqModel.setExchange(model.getExch());
			request = mapper.writeValueAsString(reqModel);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;

	}

	/*
	 * method to get security information
	 * 
	 * @author SOWMIYA
	 * 
	 * @return
	 */
	private boolean validateSecurityInfoParameters(SecurityInfoReqModel model) {
		if (StringUtil.isNotNullOrEmpty(model.getExch()) && StringUtil.isNotNullOrEmpty(model.getToken())) {
			return true;
		}
		return false;
	}
}
