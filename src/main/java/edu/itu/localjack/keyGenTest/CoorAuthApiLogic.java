package edu.itu.localjack.keyGenTest;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import edu.itu.html.JSONResult;
import edu.itu.localjack.common.ClientLogic;
import edu.itu.localjack.common.JsonUtil;
import edu.itu.localjack.common.ServerType;
import edu.itu.util.InterfaceUtil;

public class CoorAuthApiLogic extends ClientLogic<String,JsonArray> {


	
	
	@Override
	protected JsonArray executeLogicDetail(String server) {
		String serverUrl = StringUtils.EMPTY;
		try {
			/*logger.info("##################start to connect server##########");
			logger.info(String.format("server:%s\n", server));
			serverUrl = InterfaceUtil.getUrl(InterfaceUtil.LOCALSERVER_LIGHTS_SEARCH_URL);
			if (server.equals(ServerType.dataServer.toString()))
				serverUrl = InterfaceUtil.getUrl(InterfaceUtil.DATASERVER_LIGHTS_SEARCH_URL);
			logger.info(String.format("request url:%s\n", serverUrl));
			logger.info(String.format("username:%s, password:%s\n", this.getUserName(), this.getPassword()));*/
			
			
			
			serverUrl = "http://127.0.0.1:8080/AuthInterface/v2/coorauth/1";
			
			JSONResult jr = new JSONResult().setHttpcode(200);

			Response response = ClientLogic.getOperation(serverUrl, jr, this.getUserName(), this.getPassword());

			if (jr.getHttpcode() != 200) {
				logger.info(String.format("http code is %d, msg is %s", jr.getHttpcode(), jr.getResult()));
				JsonObject jObject = Json.createObjectBuilder().add("code", jr.getHttpcode()).add("msg", jr.getResult()).build();
				return Json.createArrayBuilder().add(jObject).build();
			}

			String entity = response.readEntity(String.class);
			logger.info("request result is: " + entity);

			// use javax.json.JsonArray.
			JsonObject jb = JsonUtil.convertStringToJxObj(entity);
			//JsonArray jArray = JsonUtil.convertGsonObjToJxObj(entity);

			logger.info("####################to server finished#######################");
			return Json.createArrayBuilder().add(jb).build();

		} catch (NullPointerException e) {
			logger.error("return results is not a array", e);
			JsonObject jObject = Json.createObjectBuilder().add("code", 500).add("msg", " NullPointerException; emg is " + e.getMessage()).build();
			return Json.createArrayBuilder().add(jObject).build();
		} catch (ProcessingException e) {
			logger.error("ProcessingException", e);
			JsonObject jObject = Json.createObjectBuilder().add("code", 404).add("msg", " server url is " + serverUrl + "; emg is " + e.getMessage()).build();
			return Json.createArrayBuilder().add(jObject).build();
		} catch (Exception e) {
			logger.error("get info error", e);
			JsonObject jObject = Json.createObjectBuilder().add("code", 500).add("msg", e.getClass().getSimpleName() + "; emg is " + e.getMessage()).build();
			return Json.createArrayBuilder().add(jObject).build();
		}
	}
	/*
		
		@Override
		protected JsonArray executeLogicDetail(String server) {
			String serverUrl = StringUtils.EMPTY;
			try {
				logger.info("##################start to connect server##########");
				logger.info(String.format("server:%s\n", server));
				serverUrl = InterfaceUtil.getUrl(InterfaceUtil.LOCALSERVER_LIGHTS_SEARCH_URL);
				if (server.equals(ServerType.dataServer.toString()))
					serverUrl = InterfaceUtil.getUrl(InterfaceUtil.DATASERVER_LIGHTS_SEARCH_URL);
				logger.info(String.format("request url:%s\n", serverUrl));
				logger.info(String.format("username:%s, password:%s\n", this.getUserName(), this.getPassword()));
				
				
				
				serverUrl = "http://127.0.0.1:8080/AuthInterface/v2/coorauth/1";
				
				JSONResult jr = new JSONResult().setHttpcode(200);

				Response response = ClientLogic.getOperation(serverUrl, jr, this.getUserName(), this.getPassword());

				if (jr.getHttpcode() != 200) {
					logger.info(String.format("http code is %d, msg is %s", jr.getHttpcode(), jr.getResult()));
					JsonObject jObject = Json.createObjectBuilder().add("code", jr.getHttpcode()).add("msg", jr.getResult()).build();
					return Json.createArrayBuilder().add(jObject).build();
				}

				String entity = response.readEntity(String.class);
				logger.info("request result is: " + entity);

				// use javax.json.JsonArray.
				JsonArray jArray = JsonUtil.convertStringToJxArr(entity);

				logger.info("####################to server finished#######################");
				return jArray;

			} catch (NullPointerException e) {
				logger.error("return results is not a array", e);
				JsonObject jObject = Json.createObjectBuilder().add("code", 500).add("msg", " NullPointerException; emg is " + e.getMessage()).build();
				return Json.createArrayBuilder().add(jObject).build();
			} catch (ProcessingException e) {
				logger.error("ProcessingException", e);
				JsonObject jObject = Json.createObjectBuilder().add("code", 404).add("msg", " server url is " + serverUrl + "; emg is " + e.getMessage()).build();
				return Json.createArrayBuilder().add(jObject).build();
			} catch (Exception e) {
				logger.error("get info error", e);
				JsonObject jObject = Json.createObjectBuilder().add("code", 500).add("msg", e.getClass().getSimpleName() + "; emg is " + e.getMessage()).build();
				return Json.createArrayBuilder().add(jObject).build();
			}
		}*/

		public static void main(String[] args) throws Exception {
			// new LightsSearchLogic().executeLogic("dataServer");
//			JSONResult jr = new JSONResult().setHttpcode(200);
			/*LightsSearchLogic lsl = new LightsSearchLogic();
			lsl.setUserName("test1");
			lsl.setPassword("test1");
			
			JsonArray results = lsl.executeLogic("localserver");
			System.out.println(results);*/
			CoorAuthApiLogic  coorAuthApiLogic = new CoorAuthApiLogic();
			coorAuthApiLogic.setPassword("V3SqN4d5tQCi6SpH6z9r4BN6");
			coorAuthApiLogic.setUserName("275076194490-18qc1p950jq7m3vu4dneobuavk0ej9r1.apps.googleusercontent.com");
			JsonArray results = coorAuthApiLogic.executeLogic("dataserver");
			System.out.println(results);
//			ClientLogic.getOperation("http://127.0.0.1:8082/LocalServerInterface/v2/devices/relays", jr, "test1", "test1");
		}
	}



