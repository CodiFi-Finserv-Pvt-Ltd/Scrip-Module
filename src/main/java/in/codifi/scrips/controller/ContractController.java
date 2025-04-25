package in.codifi.scrips.controller;

import javax.inject.Inject;
import javax.ws.rs.Path;

import org.jboss.resteasy.reactive.RestResponse;

import in.codifi.scrips.controller.spec.ContractControllerSpec;
import in.codifi.scrips.model.request.LoaderReqModel;
import in.codifi.scrips.model.response.GenericResponse;
import in.codifi.scrips.service.spec.ContractServiceSpecs;
import in.codifi.scrips.utility.AppConstants;
import in.codifi.scrips.utility.PrepareResponse;
import in.codifi.scrips.utility.StringUtil;

@Path("/contract")
public class ContractController implements ContractControllerSpec {

	@Inject
	ContractServiceSpecs service;
	@Inject
	PrepareResponse prepareResponse;

	/**
	 * Method to load contract master into cache
	 * 
	 * @author Dinesh Kumar
	 *
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> loadContractMasterTR() {
		return service.loadContractMasterTR();
	}

	/**
	 * Delete Expired contract manually
	 * 
	 * @author Dinesh Kumar
	 *
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> deleteExpiredContract() {
		return service.deleteExpiredContract();
	}

	/**
	 * Delete Delete BSE contract
	 * 
	 * @author Dinesh Kumar
	 *
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> deleteBSEContract() {
		return service.deleteBSEContract();
	}

	/**
	 * Method to reload contract master file from server
	 * 
	 * @author Nesan
	 * 
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> reloadContractMasterFile() {
		return service.reloadContractMasterFile();
	}

	/**
	 * Method to load PNL Lot data into cache
	 * 
	 * @author Dinesh Kumar
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> loadPnlLotSize() {
		return service.loadPnlLotSize();
	}

	/**
	 * Method to get ASM/GSM file from server
	 * 
	 * @author Dinesh Kumar
	 *
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> reloadAsmGsmFile(LoaderReqModel model) {
		return service.reloadAsmGsmFile(model.getDaysOffset());
	}

	/**
	 * Method to get contract file different format like CSV,JSON and ZIP
	 * 
	 * @author Dinesh Kumar
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> getContractFiles(LoaderReqModel model) {
		if (model == null || StringUtil.isNullOrEmpty(model.getType()))
			prepareResponse.prepareFailedResponse(AppConstants.INVALID_PARAMETER);
		return service.getContractFiles(model.getType());
	}
}
