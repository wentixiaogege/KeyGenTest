package edu.itu.localjack.common;

//import java.util.Base64;

import javax.json.JsonObject;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.internal.util.Base64;


import edu.itu.html.JSONResult;
import edu.itu.util.Log4jUtil;

public abstract class ClientLogic<T1, V> {

	protected Logger logger = Log4jUtil.getLogger(this.getClass());

	protected static Logger slogger = Log4jUtil.getLogger(ClientLogic.class);

	protected String userName;
	protected String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public V executeLogic(T1 params) throws Exception {
		V res = executeLogicDetail(params);
		return res;
	}

	protected abstract V executeLogicDetail(T1 params) throws Exception;

	/**
	 * postOperation with jersey2
	 * 
	 * @param t1
	 * @param url
	 * @return
	 */
	public static Response postOperation2(Object t1, String url, JSONResult jr, String userName, String passwd) {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 2000);
//		Client client = ClientBuilder.newClient(clientConfig);
		Client client = ClientLogic.getSSLClient();
		slogger.debug("url is ---- " + url);
		slogger.debug("t1 is ---- " + t1.toString());
		WebTarget target = client.target(url);
		Builder bd = target.request(MediaType.APPLICATION_JSON);
		if (!(StringUtils.isEmpty(userName) || StringUtils.isEmpty(passwd))) {
			bd = bd.header(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encodeAsString(String.format("%s:%s", userName, passwd))));
		}

		Response response = bd.post(Entity.entity(t1, MediaType.APPLICATION_JSON));
		// Response response =
		// target.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION,
		// "Basic " + new String(Base64.encodeAsString("gqq:gqq")))
		// .post(Entity.entity(t1, MediaType.APPLICATION_JSON));

		int status = response.getStatus();

		slogger.debug("status is " + status);
		if (status == Status.UNAUTHORIZED.getStatusCode() || status == Status.FORBIDDEN.getStatusCode()) {
			String readEntity2 = response.readEntity(String.class);
			JsonObject readEntity = JsonUtil.convertStringToJxObj(readEntity2);
			jr.setHttpcode(status).setResult(readEntity.getString("errMsg"));
		} else if (response.getStatus() != Status.OK.getStatusCode()) {
			slogger.info("error from server, code is " + response.getStatus());
			JSONResult jsonObject = response.readEntity(JSONResult.class);
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + String.format(" code:%d msg:%s", jsonObject.getHttpcode(), jsonObject.getResult()));
		}
		return response;
	}

	public static Response getOperation(String url, JSONResult jr, String userName, String passwd) {
//		ClientConfig clientConfig = new ClientConfig();
//		clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 2000);
//		Client client = ClientBuilder.newClient(clientConfig);
		Client client = ClientLogic.getSSLClient();

		WebTarget target = client.target(url);

		Builder bd = target.request(MediaType.APPLICATION_JSON);
		if (!(StringUtils.isEmpty(userName) || StringUtils.isEmpty(passwd))) {
			bd = bd.header(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encodeAsString(String.format("%s:%s", userName, passwd))));
		}
		Response response = bd.get();
		int status = response.getStatus();

		slogger.debug("status is " + status);
		if (status == Status.UNAUTHORIZED.getStatusCode() || status == Status.FORBIDDEN.getStatusCode()) {
			String readEntity2 = response.readEntity(String.class);
			JsonObject readEntity = JsonUtil.convertStringToJxObj(readEntity2);
			jr.setHttpcode(status).setResult(readEntity.getString("errMsg"));
		} else if (response.getStatus() != Status.OK.getStatusCode()) {
			slogger.info("error from server, code is " + response.getStatus());
			JSONResult jsonObject = response.readEntity(JSONResult.class);
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + String.format(" code:%d msg:%s", jsonObject.getHttpcode(), jsonObject.getResult()));
		}
		return response;
	}

	/**
	 * the client used to do https request.
	 * @return
	 */
	private static Client getSSLClient() {
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { new X509TrustManager() {

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new java.security.cert.X509Certificate[0];
				}

			} }, new java.security.SecureRandom());

			return ClientBuilder.newBuilder().sslContext(sslcontext).property(ClientProperties.CONNECT_TIMEOUT, 2000).hostnameVerifier((s1, s2) -> true).build();
		} catch (Exception e) {
			return null;
		}

	}
	
}
