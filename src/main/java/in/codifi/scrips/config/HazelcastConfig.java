package in.codifi.scrips.config;

import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.jcraft.jsch.ChannelSftp;
import com.sas.dto.CustomerDTO;

import in.codifi.cache.model.ContractMasterModel;
import in.codifi.cache.model.PnlLotModel;
import in.codifi.scrips.entity.chartdb.PromptModel;
import in.codifi.scrips.model.response.ScripSearchResp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HazelcastConfig {

	public static HazelcastConfig HazelcastConfig = null;
	private HazelcastInstance hz = null;

	public static HazelcastConfig getInstance() {
		if (HazelcastConfig == null) {
			HazelcastConfig = new HazelcastConfig();

		}
		return HazelcastConfig;
	}

	public HazelcastInstance getHz() {
		if (hz == null) {
			ClientConfig clientConfig = new ClientConfig();
			clientConfig.setClusterName(ConfigProvider.getConfig().getValue("config.app.hazel.cluster", String.class));
			List<String> hazelAddress = List
					.of(ConfigProvider.getConfig().getValue("config.app.hazel.address", String.class).split(","));
			hazelAddress.stream().forEach(address -> {
				clientConfig.getNetworkConfig().addAddress(address);
			});

			clientConfig.setClassLoader(ContractMasterModel.class.getClassLoader());

			hz = HazelcastClient.newHazelcastClient(clientConfig);
		}
		return hz;
	}

	private Map<String, Boolean> fetchDataFromCache = getHz().getMap("fetchDataFromCache");
	private Map<Integer, List<String>> distinctSymbols = getHz().getMap("distinctSymbols");
	private Map<String, List<ScripSearchResp>> loadedSearchData = getHz().getMap("loadedSearchData");
	private Map<String, ScripSearchResp> indexDetails = getHz().getMap("indexDetails");
//	private Map<String, ContractMasterModel> contractMaster = getHz().getMap("contractMaster");
	private Map<String, String> restUserSession = getHz().getMap("restUserSession");
	private Map<String, String> isin = getHz().getMap("isin");
	private Map<String, ContractMasterModel> contractMasterTr = getHz().getMap("contractMasterTr");
	private Map<String, CustomerDTO> userKeyMap = getHz().getMap("userKeyMap");
	private Map<String, String> tradingSymbolTokenMapTR = getHz().getMap("tradingSymbolTokenMapTR");
	private Map<String, String> tradingSymbolTokenMapKB = getHz().getMap("tradingSymbolTokenMapKB");
	private Map<String, List<PnlLotModel>> pnlLot = getHz().getMap("pnlLot");
	private Map<String, List<PromptModel>> promptMaster = getHz().getMap("promptMaster");

	private Map<String, String> tokenIsin = getHz().getMap("tokenIsin");

}
