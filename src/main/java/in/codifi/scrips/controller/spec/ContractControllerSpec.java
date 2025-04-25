package in.codifi.scrips.controller.spec;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestResponse;

import in.codifi.scrips.model.request.LoaderReqModel;
import in.codifi.scrips.model.response.GenericResponse;

public interface ContractControllerSpec {

	@Path("/reloadcache")
	@POST
	RestResponse<GenericResponse> loadContractMasterTR();

	/**
	 * 
	 * Method to Delete expired contract
	 * 
	 * @author Dinesh Kumar
	 *
	 * @return
	 */
	@Path("/delete/expired")
	@POST
	RestResponse<GenericResponse> deleteExpiredContract();

	/**
	 * Delete Delete BSE contract
	 * 
	 * @author Dinesh Kumar
	 *
	 * @return
	 */
	@Path("/delete/bse")
	@GET
	RestResponse<GenericResponse> deleteBSEContract();

	/**
	 * Method to get reload - contract master file from server
	 * 
	 * @author Nesan
	 *
	 * @return
	 */
	@Path("/reload/contractmaster")
	@GET
	RestResponse<GenericResponse> reloadContractMasterFile();

	/**
	 * Method to load PNL Lot data into cache
	 * 
	 * @author Dinesh Kumar
	 * @return
	 */
	@Path("/reload/pnllot")
	@POST
	RestResponse<GenericResponse> loadPnlLotSize();

	/**
	 * Method to get ASM/GSM file from server
	 * 
	 * @author Dinesh Kumar
	 *
	 * @return
	 */
	@Path("/reload/asmgsm")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse> reloadAsmGsmFile(LoaderReqModel model);

	/**
	 * Method to get contract file different format like CSV,JSON and ZIP
	 * 
	 * @author Dinesh Kumar
	 * @return
	 */
	@Path("/get/contract/files")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse> getContractFiles(LoaderReqModel model);
}
