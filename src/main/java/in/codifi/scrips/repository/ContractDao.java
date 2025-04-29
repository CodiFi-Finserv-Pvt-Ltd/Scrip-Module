package in.codifi.scrips.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class ContractDao {

	@Inject
//	@PersistenceUnit("kbcontract")
	EntityManager entityManager;

	/**
	 * 
	 * Method to load isin into cache
	 * 
	 * @author Dinesh Kumar
	 *
	 */
//	public void loadKbContractMaster() {
//		List<Object[]> resultData = null;
//		try {
//			Query query = entityManager.createNativeQuery(
//					"SELECT exch,isin,token FROM tbl_global_contract_master_details where isin is not null and isin != '' and exch in('BSE','NSE')");
//			resultData = query.getResultList();
//			
//			if(resultData)
//			for (Object[] values : resultData) {
//				ContractMasterModel result = new ContractMasterModel();
//				result.setExch(contractEntity.getExch());
//				result.setSegment(contractEntity.getSegment());
//				result.setSymbol(contractEntity.getSymbol());
//				result.setIsin(contractEntity.getIsin());
//				result.setFormattedInsName(contractEntity.getFormattedInsName());
//				result.setToken(contractEntity.getToken());
//				result.setTradingSymbol(contractEntity.getTradingSymbol());
//				result.setGroupName(contractEntity.getGroupName());
//				result.setInsType(contractEntity.getInsType());
//				result.setOptionType(contractEntity.getOptionType());
//				result.setStrikePrice(contractEntity.getStrikePrice());
//				result.setExpiry(contractEntity.getExpiryDate());
//				result.setLotSize(contractEntity.getLotSize());
//				result.setTickSize(contractEntity.getTickSize());
//				result.setPdc(contractEntity.getPdc());
//				result.setWeekTag(contractEntity.getWeekTag());
//				result.setFreezQty(contractEntity.getFreezeQty());
//				result.setAlterToken(contractEntity.getAlterToken());
//				result.setCompanyName(contractEntity.getCompanyName());
//				String key = contractEntity.getExch() + "_" + contractEntity.getToken();
//				HazelcastConfig.getInstance().getContractMaster().put(key, result);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
