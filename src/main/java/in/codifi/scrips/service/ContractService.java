package in.codifi.scrips.service;

import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.resteasy.reactive.RestResponse;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import in.codifi.cache.model.ContractMasterModel;
import in.codifi.cache.model.PnlLotModel;
import in.codifi.scrips.config.ApplicationProperties;
import in.codifi.scrips.config.HazelcastConfig;
import in.codifi.scrips.entity.chartdb.PromptModel;
import in.codifi.scrips.entity.primary.ContractEntityTR;
import in.codifi.scrips.entity.primary.PnlLotEntity;
import in.codifi.scrips.entity.primary.PromptEntity;
import in.codifi.scrips.model.response.GenericResponse;
import in.codifi.scrips.repository.ContractEntityManager;
import in.codifi.scrips.repository.ContractRepositoryTR;
import in.codifi.scrips.repository.PnlLotRepository;
import in.codifi.scrips.repository.PromptDao;
import in.codifi.scrips.repository.PromptRepository;
import in.codifi.scrips.repository.ScripSearchEntityManager;
import in.codifi.scrips.service.spec.ContractServiceSpecs;
import in.codifi.scrips.utility.AppConstants;
import in.codifi.scrips.utility.PrepareResponse;
import in.codifi.scrips.utility.SshFileTransferService;
import in.codifi.scrips.utility.StringUtil;
import io.quarkus.logging.Log;

@ApplicationScoped
public class ContractService implements ContractServiceSpecs {

//	@Inject
//	ContractRepository contractRepository;

	@Inject
	ContractRepositoryTR contractRepositoryTr;

	@Inject
	PrepareResponse prepareResponse;

	@Inject
	ContractEntityManager contractEntityManager;

	@Inject
	ScripSearchEntityManager entityManager;

	@Inject
	ApplicationProperties props;

	@Inject
	PnlLotRepository pnlLotRepository;

	@Inject
	PromptDao promptDao;

	@Inject
	PromptRepository promptRepository;

	@Inject
	SshFileTransferService sshFileTransferService;

	@Override
	public RestResponse<GenericResponse> loadContractMasterTR() {
		try {
			List<ContractEntityTR> contractList = new ArrayList<>();
			contractList = contractRepositoryTr.findAll();
			if (contractList.size() > 0)
				HazelcastConfig.getInstance().getContractMasterTr().clear();
			HazelcastConfig.getInstance().getTradingSymbolTokenMapTR().clear();
			HazelcastConfig.getInstance().getTokenIsin().clear();
			for (ContractEntityTR contractEntity : contractList) {
				ContractMasterModel result = new ContractMasterModel();

				result.setExch(contractEntity.getExch());
				result.setSegment(contractEntity.getSegment());
				result.setSymbol(contractEntity.getSymbol());
				result.setIsin(contractEntity.getIsin());
				result.setFormattedInsName(contractEntity.getFormattedInsName());
				result.setToken(contractEntity.getToken());
				String tradingSymbol = "";
				if (contractEntity.getExch().equalsIgnoreCase("NSE")) {
//					tradingSymbol = contractEntity.getSymbol() + "-" + contractEntity.getGroupName();
					tradingSymbol = contractEntity.getTradingSymbol();
				} else if (contractEntity.getExch().equalsIgnoreCase("BSE")) {
					tradingSymbol = contractEntity.getTradingSymbol();
				} else {
					tradingSymbol = contractEntity.getTradingSymbol();
//					System.out.println("OTHERS-----"+contractEntity.getTradingSymbol());
				}

				result.setTradingSymbol(tradingSymbol);
				result.setGroupName(contractEntity.getGroupName());
				result.setInsType(contractEntity.getInsType());
				result.setOptionType(contractEntity.getOptionType());
				result.setStrikePrice(contractEntity.getStrikePrice());
				result.setExpiry(contractEntity.getExpiryDate());
				result.setLotSize(contractEntity.getLotSize());
				result.setTickSize(contractEntity.getTickSize());
				result.setPdc(contractEntity.getPdc());
				result.setWeekTag(contractEntity.getWeekTag());
				result.setFreezQty(contractEntity.getFreezeQty());
				result.setAlterToken(contractEntity.getAlterToken());
				result.setCompanyName(contractEntity.getCompanyName());
				result.setOptionFlag(contractEntity.getOptionFlag());
				result.setPhysicalQty(contractEntity.getPhysicalQty());
				result.setMultiplier(contractEntity.getMultiplier());
				result.setPnlMultiplier(contractEntity.getPnlMultiplier());
				String key = contractEntity.getExch() + "_" + contractEntity.getToken();
				HazelcastConfig.getInstance().getContractMasterTr().put(key, result);

				String token = contractEntity.getToken();
				String isin = contractEntity.getIsin();
				String exch = contractEntity.getExch().toUpperCase();
				if (StringUtil.isNotNullOrEmpty(isin) && StringUtil.isNotNullOrEmpty(token)) {
					HazelcastConfig.getInstance().getTokenIsin().put(token, isin);
				}
				if (tradingSymbol != null) {
					HazelcastConfig.getInstance().getTradingSymbolTokenMapTR().put(tradingSymbol.toUpperCase(),
							token + "_" + exch);
				}
			}
			System.out.println("Loaded SucessFully");
			System.out.println("Full Size " + HazelcastConfig.getInstance().getContractMasterTr().size());

			System.out.println("Trading symbol token map TR Loaded SucessFully");
			System.out.println("Trading symbol token map TR Full Size "
					+ HazelcastConfig.getInstance().getTradingSymbolTokenMapTR().size());
		} catch (Exception e) {
			e.printStackTrace();
			return prepareResponse.prepareFailedResponse(AppConstants.CONTRACT_LOAD_FAILED);
		}
		return prepareResponse.prepareSuccessMessage(AppConstants.CONTRACT_LOAD_SUCESS);
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

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = format.format(date);
		return contractEntityManager.deleteExpiredContract(todayDate);
	}

	/**
	 * 
	 * Method to Delete BSE Contract
	 * 
	 * @author Dinesh Kumar
	 *
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> deleteBSEContract() {
		return contractEntityManager.deleteBSEContract();
	}

	/**
	 * Method can be used for Update contract master
	 * 
	 * Desc : GET SQL file from server and update the same in DB
	 * 
	 * @author Nesan created on 22-03-23
	 * 
	 * @return
	 */
	public Boolean executeSqlFileFromServer() {
		boolean status = false;
		try {
			String localFilePath = props.getLocalcontractDir();
			File localFileDir = new File(props.getLocalcontractDir());
			if (!localFileDir.exists()) {
				localFileDir.mkdirs();
			}

			Date today = new Date();
			String date = new SimpleDateFormat("ddMMYY").format(today);
			String fileName = AppConstants.CONTRACT_FILE_NMAE + date + AppConstants.SQL;

			String remoteDir = props.getRemoteContractDire() + fileName;
			boolean isFileMoved = getsqlFileFromServer(localFilePath.toString(), remoteDir);

			if (isFileMoved) {
				boolean isInserted = executeSqlFile(localFilePath, fileName, date);
				if (isInserted) {
					deleteExpiredContract();
					loadContractIntoCache();
					status = true;
				} else {
					deleteExpiredContract();
					loadContractIntoCache();
				}
//				deleteExpiredContract();
//				loadContractIntoCache();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return status;

	}

	/**
	 * Method to clear cache and load latest data into cache
	 * 
	 * @author DINESH KUMAR
	 *
	 */
	public void loadContractIntoCache() {
		Log.info("Started to clear cache");
		HazelcastConfig.getInstance().getFetchDataFromCache().clear();
		HazelcastConfig.getInstance().getDistinctSymbols().clear();
		HazelcastConfig.getInstance().getLoadedSearchData().clear();
		HazelcastConfig.getInstance().getFetchDataFromCache().clear();
		Log.info("Cache cleared and started to load new data");
		HazelcastConfig.getInstance().getFetchDataFromCache().put(AppConstants.FETCH_DATA_FROM_CACHE, true);
		entityManager.loadDistintValue(2);
		entityManager.loadDistintValue(3);
		loadContractMasterTR();
		loadIsin();
		Log.info("Cache loaded sucessfully");
	}

	/**
	 * Method to move file from server to local
	 * 
	 * @author Nesan
	 * 
	 * @param localFilePath
	 * @param remotefilePath
	 * @return
	 */
	private boolean getsqlFileFromServer(String localFilePath, String remotefilePath) {
		boolean status = false;

		String localHost = AppConstants.LOCALHOST;
		int localPort = AppConstants.PORT_3306;
		int forwardPort = AppConstants.PORT_3308;

		Session session = null;
		ChannelSftp channelSftp = null;
		try {
			JSch jsch = new JSch();

			session = jsch.getSession(props.getSshUserName(), props.getSshHost(), props.getSshPort());
//			session.setPortForwardingL(forwardPort, localHost, localPort);

			session.setPassword(props.getSshPassword());
			session.setConfig(AppConstants.STRICTHOSTKEYCHECKING, AppConstants.NO);
			session.connect();
			/* File movement from server to local */
			Channel sftp = session.openChannel(AppConstants.SFTP);
			// 5 seconds timeout
			sftp.connect(5000);
			channelSftp = (ChannelSftp) sftp;
			/* transfer file from remote server to local */
			channelSftp.stat(remotefilePath);
			channelSftp.get(remotefilePath, localFilePath);
			channelSftp.exit();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.disconnect();
		}
		return status;
	}

	/**
	 * Method to execute sql file
	 * 
	 * @author Nesan
	 * 
	 * @param string
	 */
	private boolean executeSqlFile(String localFilePath, String fileName, String date) {
		boolean status = false;
		File directory = new File(localFilePath + fileName);
		int size = localFilePath.lastIndexOf("/");
		String slash = "//";
		if (size > 0) {
			slash = "/";
		}
		try {

			if (directory.isFile()) {
				/* This one can be finalized */
//				String tCommand = "mysql -u " + props.getDbUserName() + " -p" + props.getDbpassword() + " "
//						+ props.getDbSchema();
				String tCommand = "mysql -u " + props.getDbUserName() + " -p" + props.getDbpassword() + " -h "
						+ props.getDbHost() + " " + props.getDbSchema();
				System.out.println(tCommand);
				String sqlQueries = new String(Files.readAllBytes(Paths.get(directory.toURI())));
				Process tProcess = Runtime.getRuntime().exec(tCommand); // 20
				OutputStream tOutputStream = tProcess.getOutputStream();
				Writer w = new OutputStreamWriter(tOutputStream);
				w.write(sqlQueries);
				w.flush();
				status = true;
				File completed = new File(localFilePath + "completed");
				if (!completed.exists()) {
					completed.mkdirs();
				}
				if (directory.renameTo(new File(completed.toString() + slash + date))) {
					directory.delete();
					Log.info("File Moved Successfully");
				}
			} else {
				/* sent mail */
				File completed = new File(localFilePath + "failed");
				if (!completed.exists()) {
					completed.mkdirs();
				}
				if (directory.renameTo(new File(completed.toString() + slash + date))) {
					directory.delete();
					Log.info("Contract master update is failed");
				}
			}
		} catch (Exception e) {
			/* sent mail */
			File completed = new File(localFilePath + "failed");
			if (!completed.exists()) {
				completed.mkdirs();
			}
			if (directory.renameTo(new File(completed.toString() + slash + date))) {
				directory.delete();
				Log.info("Contract master update is failed");
			}
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Method to get reload contract master file from server
	 * 
	 * @author Nesan
	 *
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> reloadContractMasterFile() {

		boolean status = executeSqlFileFromServer();
		if (status) {
			return prepareResponse.prepareSuccessMessage(AppConstants.CONTRACT_LOAD_SUCESS);
		} else {
			return prepareResponse.prepareFailedResponse(AppConstants.CONTRACT_LOAD_FAILED);
		}

	}

	/**
	 * 
	 * Method to load isin token into cache for holdings
	 * 
	 * @author Dinesh Kumar
	 *
	 */
	public void loadIsin() {
		contractEntityManager.loadIsin();
	}

	/**
	 * Method to load PNL Lot
	 * 
	 * @author Dinesh Kumar
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> loadPnlLotSize() {
		try {
			List<PnlLotEntity> pnlLotEntities = new ArrayList<>();
			List<PnlLotModel> pnlLotModels = new ArrayList<>();
			pnlLotEntities = pnlLotRepository.findAll();
			if (pnlLotEntities.size() > 0)
				HazelcastConfig.getInstance().getPnlLot().clear();
			for (PnlLotEntity pnlLotEntity : pnlLotEntities) {
				PnlLotModel result = new PnlLotModel();
				result.setExch(pnlLotEntity.getExch());
				result.setSymbol(pnlLotEntity.getSymbol());
				result.setToken(pnlLotEntity.getToken());
				result.setTradingSymbol(pnlLotEntity.getTradingSymbol());
				result.setExpiry(pnlLotEntity.getExpiryDate());
				result.setLotSize(pnlLotEntity.getLotSize());
				pnlLotModels.add(result);
			}
			HazelcastConfig.getInstance().getPnlLot().put(AppConstants.PNL_LOT, pnlLotModels);
			System.out.println("Pnl Lot loaded sucessFully");
		} catch (Exception e) {
			e.printStackTrace();
			return prepareResponse.prepareFailedResponse(AppConstants.CONTRACT_LOAD_FAILED);
		}
		return prepareResponse.prepareSuccessMessage(AppConstants.CONTRACT_LOAD_SUCESS);
	}

	/**
	 * 
	 * Method to load prompt
	 * 
	 * @author Dinesh Kumar
	 *
	 */
	public void loadPromptData() {
		promptDao.loadPromptData();
	}

	/**
	 * Method to get ASM/GSM file from server
	 * 
	 * @author Dinesh Kumar
	 *
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> reloadAsmGsmFile(int daysOffset) {

		boolean status = executeAsmGsmSqlFileFromServer(daysOffset);
		if (status) {
			return prepareResponse.prepareSuccessMessage(AppConstants.ASM_GSM_LOAD_SUCESS);
		} else {
			return prepareResponse.prepareFailedResponse(AppConstants.ASM_GSM_LOAD_FAILED);
		}

	}

	/**
	 * Method to get ASM and GSM dump file from server
	 * 
	 * @author Dinsh Kumar
	 * @return
	 */
	public Boolean executeAsmGsmSqlFileFromServer(int daysOffset) {
		boolean status = false;
		try {
			String localFilePath = props.getLocalAsmGsmDir();
			File localFileDir = new File(props.getLocalAsmGsmDir());
			if (!localFileDir.exists()) {
				localFileDir.mkdirs();
			}

//			Date today = new Date();
//			String date = new SimpleDateFormat("ddMMYY").format(today);
			String date = getFormattedDateWithOffset(daysOffset);
			if (StringUtil.isNullOrEmpty(date)) {
				Log.error("Failed to load ASM/GSM file from server due to date is empty");
				return status;
			}

			String fileName = AppConstants.ASMGSM_FILE_NMAE + date + AppConstants.SQL;
			Log.info("ASM GSM fileName - " + fileName);
			String remoteDir = props.getRemoteAsmGsmDir() + fileName;
			boolean isFileMoved = getsqlFileFromServer(localFilePath.toString(), remoteDir);
			if (isFileMoved) {
				boolean isInserted = executeSqlFile(localFilePath, fileName, date);
				loadPromptDataInThread();
				Log.info("ASM GSM Loaded sucessfully - " + fileName);
			} else {
				Log.error("Failed to get ASM/GSM file from server");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return status;

	}

	public static String getFormattedDateWithOffset(int daysOffset) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMYY");
		return LocalDate.now().minusDays(daysOffset).format(formatter);
	}

	/**
	 * 
	 * Method to load prompt
	 * 
	 * @author Dinesh Kumar
	 *
	 */
	public void loadPromptDataInThread() {
		try {
//			List<PromptModel> promptModels = promptDao.getPromptData();
			List<PromptEntity> promptEntities = promptRepository.findAll();

			ExecutorService pool = Executors.newSingleThreadExecutor();
			pool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						if (!promptEntities.isEmpty()) {
							List<PromptModel> promptModels = preparePromptModel(promptEntities);
							List<PromptModel> response = new ArrayList<PromptModel>();
							HazelcastConfig.getInstance().getPromptMaster().clear();
							for (PromptModel promptModel : promptModels) {
								String key = (promptModel.getIsin() + "_" + promptModel.getExch()).toUpperCase();
								response = HazelcastConfig.getInstance().getPromptMaster().get(key);
								if (response != null && response.size() > 0) {
									response = HazelcastConfig.getInstance().getPromptMaster().get(key);
									response.add(promptModel);
									HazelcastConfig.getInstance().getPromptMaster().put(key, response);
								} else {
									response = new ArrayList<>();
									response.add(promptModel);
									HazelcastConfig.getInstance().getPromptMaster().put(key, response);
								}
							}
						} else {
							Log.error("Prompt data is empty in DB");
						}

					} catch (Exception e) {
						e.printStackTrace();
						Log.error(e.getMessage());
					} finally {
						pool.shutdown();
					}
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
		}
	}

	/**
	 * Helper method to prepare prompt model from entity
	 * 
	 * @author Dinesh Kumar
	 * @param promptEntities
	 * @return
	 */
	private List<PromptModel> preparePromptModel(List<PromptEntity> promptEntities) {
		List<PromptModel> promptModels = new ArrayList<PromptModel>();
		try {
			for (PromptEntity entity : promptEntities) {
				PromptModel result = new PromptModel();
				result.setIsin(entity.getIsin());
				result.setExch(entity.getExch());
				result.setCompany_name(entity.getCompanyName());
				result.setMsg(entity.getMsg());
				result.setType(entity.getType());
				result.setSeverity(entity.getSeverity());
				result.setPrompt(entity.getPrompt());
				promptModels.add(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
		}
		return promptModels;
	}

	/**
	 * Method to get contract file different format like CSV,JSON and ZIP
	 * 
	 * @author Dinesh Kumar
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> getContractFiles(String type) {
		try {

			int size = props.getContractSourcePath().lastIndexOf("/");
			String slash = "//";
			if (size > 0) {
				slash = "/";
			}
			String sourcePath = props.getContractSourcePath() + type + slash;
			File sourcefileDir = new File(sourcePath);
			if (!sourcefileDir.exists()) {
				sourcefileDir.mkdirs();
			}
			String destPath = props.getContractDestPath() + type + slash;
			File destFileDir = new File(destPath);
			if (!destFileDir.exists()) {
				destFileDir.mkdirs();
			}
			Log.info(sourcePath);
			Log.info(destPath);
			boolean resp = sshFileTransferService.transferFiles(props.getContractSourceUserName(),
					props.getContractSourceHost(), props.getContractSourcePort(), props.getContractSourcePassword(),
					sourcePath, destPath);
			if (resp)
				return prepareResponse.prepareSuccessMessage(AppConstants.SUCCESS_STATUS);
		} catch (Exception e) {
			e.printStackTrace();
			Log.info("getContractFiles", e);
		}
		return prepareResponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
	}
}
