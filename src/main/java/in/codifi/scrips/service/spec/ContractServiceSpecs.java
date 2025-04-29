package in.codifi.scrips.service.spec;

import org.jboss.resteasy.reactive.RestResponse;

import in.codifi.scrips.model.response.GenericResponse;

public interface ContractServiceSpecs {

	/**
	 * Delete Expired contract manually
	 * 
	 * @author Dinesh Kumar
	 *
	 * @return
	 */
	RestResponse<GenericResponse> deleteExpiredContract();

	/**
	 * 
	 * Method to Delete BSE Contract
	 * 
	 * @author Dinesh Kumar
	 *
	 * @return
	 */
	RestResponse<GenericResponse> deleteBSEContract();

	/**
	 * Method to get reload contract master file from server
	 * 
	 * @author Nesan
	 *
	 * @return
	 */
	RestResponse<GenericResponse> reloadContractMasterFile();

	/**
	 * Method to load contract master for TR
	 * 
	 * @author Gowrisankar
	 *
	 * @return
	 */
	RestResponse<GenericResponse> loadContractMasterTR();

	/**
	 * Method to load PNL Lot
	 * 
	 * @author Dinesh Kumar
	 * @return
	 */
	RestResponse<GenericResponse> loadPnlLotSize();

	/**
	 * Method to get ASM/GSM file from server
	 * 
	 * @author Dinesh Kumar
	 *
	 * @return
	 */
	RestResponse<GenericResponse> reloadAsmGsmFile(int daysOffset);

	RestResponse<GenericResponse> getContractFiles(String type);

}
