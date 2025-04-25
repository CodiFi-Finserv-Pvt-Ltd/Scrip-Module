package in.codifi.scrips.loader;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import in.codifi.scrips.config.HazelcastConfig;
import in.codifi.scrips.repository.ScripSearchEntityManager;
import in.codifi.scrips.service.ContractService;
import in.codifi.scrips.utility.AppConstants;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;

@SuppressWarnings("serial")
@ApplicationScoped
public class InitialLoader extends HttpServlet {

	@Inject
	ScripSearchEntityManager scripSearchRepo;
	@Inject
	ContractService contractService;

	public void init(@Observes StartupEvent ev) throws ServletException {
		Log.info("Started to Index contract scrips");
		HazelcastConfig.getInstance().getFetchDataFromCache().put(AppConstants.FETCH_DATA_FROM_CACHE, true);
		HazelcastConfig.getInstance().getLoadedSearchData().clear();
		scripSearchRepo.loadDistintValue(2);
		scripSearchRepo.loadIndexValue();
		Log.info("Started to loading contract master");
		contractService.loadContractMasterTR();
		contractService.loadIsin();
		contractService.loadPnlLotSize();
		contractService.loadPromptData();
		Log.info("All the pre-Lodings are ended");
	}

}
