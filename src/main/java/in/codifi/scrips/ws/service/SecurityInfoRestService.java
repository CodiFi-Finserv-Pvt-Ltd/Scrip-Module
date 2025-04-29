package in.codifi.scrips.ws.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.resteasy.reactive.RestResponse;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sas.dto.CustomerDTO;
import com.tr.nest.crypto.CryptoRSA;

import in.codifi.cache.model.ClinetInfoModel;
import in.codifi.scrips.config.HazelcastConfig;
import in.codifi.scrips.config.RestProperties;
import in.codifi.scrips.model.request.SecurityInfoReqModel;
import in.codifi.scrips.model.response.GenericResponse;
import in.codifi.scrips.model.transformation.SecurityInfoRespModel;
import in.codifi.scrips.utility.AppConstants;
import in.codifi.scrips.utility.CodifiUtil;
import in.codifi.scrips.utility.PrepareResponse;
import in.codifi.scrips.utility.StringUtil;
import in.codifi.scrips.ws.model.SecurityInfoRestFailRespModel;
import in.codifi.scrips.ws.model.SecurityInfoRestSuccRespModel;
import in.codifi.scrips.ws.remodeling.SecurityInfoRemodeling;
import io.quarkus.logging.Log;

@ApplicationScoped
public class SecurityInfoRestService {
	@Inject
	PrepareResponse prepareResponse;
	@Inject
	RestProperties props;
	@Inject
	SecurityInfoRemodeling securityRemodeling;

	CryptoRSA nRSA = new CryptoRSA();

	/*
	 * method to connect get security information to ODIN server
	 * 
	 * @author SOWMIYA
	 * 
	 * @return
	 */

	public RestResponse<GenericResponse> getSecurityInfo(SecurityInfoReqModel securityInfoReqModel,
			ClinetInfoModel info) {
		CodifiUtil.trustedManagement();
		ObjectMapper mapper = new ObjectMapper();
		JSONObject jObj = new JSONObject();
		jObj.put("uid", info.getUserId());
		jObj.put("Exchange", securityInfoReqModel.getExch());
		jObj.put("SrchFor", securityInfoReqModel.getToken());
		Log.info("security info request" + securityInfoReqModel.getExch() + " - " + securityInfoReqModel.getSymbol());
		try {
			CodifiUtil.trustedManagement();
			String baseUrl = props.getGetSecurityInfo();
			CustomerDTO customerKeyDto = HazelcastConfig.getInstance().getUserKeyMap().get(info.getUserId());
			String ejData = nRSA.encrypt(jObj.toJSONString(), customerKeyDto.getPublicKey4());
			baseUrl = baseUrl + "?" + AppConstants.JSESSONID + "=." + customerKeyDto.getTomcatcount() + "&jData="
					+ URLEncoder.encode(jObj.toJSONString(), "UTF-8") + "&jKey=" + customerKeyDto.getStringPkey4();
			Log.info("Security info BaseUrl - " + baseUrl);
			URL url = new URL(baseUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(AppConstants.POST_METHOD);
			conn.setRequestProperty(AppConstants.ACCEPT, AppConstants.APPLICATION_JSON);
			conn.setDoOutput(true);
			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = jObj.toString().getBytes(AppConstants.UTF_8);
				os.write(input, 0, input.length);
			}
			int responseCode = conn.getResponseCode();
			BufferedReader bufferedReader;
			String output = null;

			if (responseCode == 401) {
				Log.error("Unauthorized error in security info");
				return prepareResponse.prepareUnauthorizedResponse();
			} else if (responseCode == 200) {
				bufferedReader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				while ((output = bufferedReader.readLine()) != null) {
					Log.info("security info response" + output);
					SecurityInfoRestSuccRespModel respModel = mapper.readValue(output,
							SecurityInfoRestSuccRespModel.class);
					SecurityInfoRespModel response = securityRemodeling.bindSecurityInfoData(respModel);
					return prepareResponse.prepareSuccessResponseObject(response);
				}
			} else {
				Log.info("Error Connection in get security info. Response Code -" + responseCode);
				bufferedReader = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
				output = bufferedReader.readLine();
				if (StringUtil.isNotNullOrEmpty(output)) {
					SecurityInfoRestFailRespModel failModel = mapper.readValue(output,
							SecurityInfoRestFailRespModel.class);
					if (StringUtil.isNotNullOrEmpty(failModel.getEmsg()))
						return prepareResponse.prepareFailedResponse(failModel.getEmsg());
				}
			}
		} catch (Exception e) {
			Log.error(e.getMessage());
			e.printStackTrace();
		}
		return prepareResponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
	}

}
