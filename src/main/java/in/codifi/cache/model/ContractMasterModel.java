package in.codifi.cache.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractMasterModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private String exch;
//	private String segment;
//	private String token;
//	private String alterToken;
//	private String symbol;
//	private String tradingSymbol;
//	private String formattedInsName;
//	private String isin;
//	private String groupName;
//	private String insType;
//	private String optionType;
//	private String strikePrice;
//	private Date expiry;
//	private String lotSize;
//	private String tickSize;
//	private String freezQty;
//	private String pdc;
//	private String weekTag;
//	private String companyName;

	@JsonProperty("exch")
	public String exch;
	@JsonProperty("segment")
	public String segment;
	@JsonProperty("token")
	public String token;
	@JsonProperty("alterToken")
	public String alterToken;
	@JsonProperty("symbol")
	public String symbol;
	@JsonProperty("tradingSymbol")
	public String tradingSymbol;
	@JsonProperty("formattedInsName")
	public String formattedInsName;
	@JsonProperty("isin")
	public String isin;
	@JsonProperty("groupName")
	public String groupName;
	@JsonProperty("insType")
	public String insType;
	@JsonProperty("optionType")
	public String optionType;
	@JsonProperty("strikePrice")
	public String strikePrice;
	@JsonProperty("expiry")
	public Date expiry;
	@JsonProperty("lotSize")
	public String lotSize;
	@JsonProperty("tickSize")
	public String tickSize;
	@JsonProperty("freezQty")
	public String freezQty;
	@JsonProperty("pdc")
	public String pdc;
	@JsonProperty("multiplier")
	public String multiplier;
	@JsonProperty("weekTag")
	public String weekTag;
	@JsonProperty("companyName")
	public String companyName;
	@JsonProperty("optionFlag")
	public int optionFlag;
	@JsonProperty("physicalQty")
	public String physicalQty;
	@JsonProperty("PnlMultiplier")
	public String PnlMultiplier;

}
