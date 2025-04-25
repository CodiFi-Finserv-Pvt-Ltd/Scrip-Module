package in.codifi.scrips.scheduler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;

import in.codifi.scrips.repository.ScripSearchEntityManager;
import in.codifi.scrips.service.ContractService;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;

@ApplicationScoped
public class Scheduler {

	@Inject
	ScripSearchEntityManager entityManager;
	@Inject
	ContractService contractService;

	/**
	 * Scheduler to Load contract at morning 7:00 AM
	 * 
	 * @author DINESH KUMAR
	 *
	 * @param execution
	 * @throws ServletException
	 */
	@Scheduled(cron = "0 0 7 * * ?")
	public void loadContractMasterFile(ScheduledExecution execution) throws ServletException {
		Log.info("Scheduler started to Load Contract Master File");
		contractService.reloadContractMasterFile();
		contractService.reloadAsmGsmFile(0);
		Log.info("Scheduler completed to Load Contract Master File");
	}

	/**
	 * Scheduler to Load contract at morning 7:20 AM
	 * 
	 * @author LOKESH
	 *
	 * @param execution
	 * @throws ServletException
	 */
	@Scheduled(cron = "0 20 7 * * ?")
	public void loadAsmGsmFile(ScheduledExecution execution) throws ServletException {
		Log.info("Scheduler started to Load ASM GSM File");
		contractService.reloadAsmGsmFile(0);
		Log.info("Scheduler completed to Load ASM GSM File");
	}

	/**
	 * Scheduler to Load contract at morning 7:00 AM
	 * 
	 * @author DINESH KUMAR
	 *
	 * @param execution
	 * @throws ServletException
	 */
	@Scheduled(cron = "0 5 7 * * ?")
	public void loadContractMaster(ScheduledExecution execution) throws ServletException {
		Log.info("Scheduler started to Load Contract Master");
		contractService.loadContractMasterTR();
		Log.info("Scheduler completed to Load Contract Master");
	}

	/**
	 * Scheduler to Load contract at morning 8:15 AM
	 * 
	 * @author Gowthaman M
	 * @param execution
	 * @throws ServletException
	 */
	@Scheduled(cron = "0 15 7 * * ?")
	public void reLoadContractMasterFile(ScheduledExecution execution) throws ServletException {
		Log.info("Scheduler started to Re Load Contract Master File");
		contractService.reloadContractMasterFile();
		Log.info("Scheduler completed to Re Load Contract Master File");
	}

	/**
	 * Scheduler to Load contract at morning 8:15 AM
	 * 
	 * @author Gowthaman M
	 * @param execution
	 * @throws ServletException
	 */
	@Scheduled(cron = "0 20 7 * * ?")
	public void reLoadContractMaster(ScheduledExecution execution) throws ServletException {
		Log.info("Scheduler started to Re Load Contract Master");
		contractService.loadContractMasterTR();
		Log.info("Scheduler completed to Re Load Contract Master");
	}

	@Scheduled(cron = "0 10 7 * * ?")
	public void loadContractJson(ScheduledExecution execution) throws ServletException {
		Log.info("Scheduler started to Load Contract JSON");
		contractService.getContractFiles("JSON");
		Log.info("Scheduler completed to Load Contract JSON");
	}

	@Scheduled(cron = "0 11 7 * * ?")
	public void loadContractCSV(ScheduledExecution execution) throws ServletException {
		Log.info("Scheduler started to Load Contract CSV");
		contractService.getContractFiles("CSV");
		Log.info("Scheduler completed to Load Contract CSV");
	}

	@Scheduled(cron = "0 12 7 * * ?")
	public void loadContractZip(ScheduledExecution execution) throws ServletException {
		Log.info("Scheduler started to Load Contract ZIP_JSON");
		contractService.getContractFiles("ZIP_JSON");
		Log.info("Scheduler completed to Load Contract ZIP_JSON");
	}
}
