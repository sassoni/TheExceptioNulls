package com.theexceptionulls.projectskullnbones.webservices;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public abstract class BaseWebService {

	private Handler callback;
	private String url;
	private boolean isPostRequest = false;
	
	public abstract void parseJsonResponse(String jsonResponseString);
	public abstract Context getApplicationContext();
	
	// Request headers
	private static final String HTTP_DEVICEID_HEADER = "3";
	private static final String HTTP_TRACK_HEADER = "7Gk4cFI6D3l8TxE4ej7M";
	private static final String HTTP_HEADER_ACCEPT_TYPE_JSON = "application/json";	
    
    public static final int WEB_SERVICE_SUCCESS = 0;
    public static final int WEB_SERVICE_FAILURE = 1;
	
    private static final int CONNECT_TIME_OUT = 5*1000;
	private static final int READ_TIME_OUT = 5*1000;
    
	public void setCallback(Handler callback){
		this.callback = callback;
	}
	
	public Handler getCallback(){
		return this.callback;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getUrl(){
		return this.url;
	}

	public boolean isPostRequest(){
		return isPostRequest;
	}
	
	public int getConnectTimeOut(){
		return CONNECT_TIME_OUT;
	}
	
	public int getReadTimeOut(){
		return READ_TIME_OUT;
	}
	
	public void execute(){
		
		WorkerThread workerThread = new WorkerThread();
		workerThread.start();
	}
	
	final TrustManager tm = new X509TrustManager() {
        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
            // nothing to do here
        }

        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
            // nothing to do here
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };
    
    private class WorkerThread extends Thread{
    	
    	@Override
    	public void run() {
    		
    		InputStream inputStream = null;
    		BufferedReader bufferedReader = null;
    		StringBuilder responseStringBuilder = null;
    		String line = null;
    		Message message = null;
    		JSONObject jsonObject = null;
    		HttpsURLConnection httpsURLConnection = null;
    		HttpURLConnection httpURLConnection = null;
    		
    		try {
    			
    			URL url = new URL(getUrl());
    			
    			if(getUrl().contains("https")){
    				
    				httpsURLConnection = (HttpsURLConnection) url.openConnection();	
    				
    				KeyStore keyStore = KeyStore.getInstance(KeyStore
    						.getDefaultType());
    				keyStore.load(null, null);

    				TrustManagerFactory trustManagerFactory = TrustManagerFactory
    						.getInstance(TrustManagerFactory
    								.getDefaultAlgorithm());
    				trustManagerFactory.init(keyStore);

    				SSLContext sslContext = SSLContext.getInstance("TLS");
    				sslContext.init(null, new TrustManager[] { tm }, null);
    				
    				httpsURLConnection.setSSLSocketFactory(sslContext
    						.getSocketFactory());
    				httpsURLConnection
    						.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    				
    				if(isPostRequest()){					
    					
    					httpsURLConnection.setRequestMethod("POST");
//                        httpsURLConnection.setRequestProperty("version", AppSettings.getInstance().getApplicationVersion(getApplicationContext()));
//                        httpsURLConnection.setRequestProperty("deviceId", AppSettings.getInstance().getDeviceId());
//                        httpsURLConnection.setRequestProperty("deviceType", HTTP_DEVICEID_HEADER);
//                        httpsURLConnection.setRequestProperty("track", HTTP_TRACK_HEADER);
    					httpsURLConnection.setRequestProperty("accept", HTTP_HEADER_ACCEPT_TYPE_JSON);
    					
    				}else {
    					httpsURLConnection.setRequestMethod("GET");
//                        httpsURLConnection.setRequestProperty("version", AppSettings.getInstance().getApplicationVersion(getApplicationContext()));
//                        httpsURLConnection.setRequestProperty("deviceId", AppSettings.getInstance().getDeviceId());
//                        httpsURLConnection.setRequestProperty("deviceType", HTTP_DEVICEID_HEADER);
//                        httpsURLConnection.setRequestProperty("track", HTTP_TRACK_HEADER);
    					httpsURLConnection.setRequestProperty("accept", HTTP_HEADER_ACCEPT_TYPE_JSON);
					}
        			
    				httpsURLConnection.setConnectTimeout(getConnectTimeOut());
    				httpsURLConnection.setReadTimeout(getReadTimeOut());
    				
    				inputStream = httpsURLConnection.getInputStream();
    				
    			}else {
    				
    				httpURLConnection = (HttpURLConnection) url.openConnection();
    				if(isPostRequest()){					
    					
    					httpURLConnection.setRequestMethod("POST");
//    					httpURLConnection.setRequestProperty("deviceId", AppSettings.getInstance().getDeviceId());
//    					httpURLConnection.setRequestProperty("deviceType", HTTP_DEVICEID_HEADER);
//    					httpURLConnection.setRequestProperty("track", HTTP_TRACK_HEADER);
//    					httpURLConnection.setRequestProperty("version", AppSettings.getInstance().getApplicationVersion(getApplicationContext()));
    					httpURLConnection.setRequestProperty("accept", HTTP_HEADER_ACCEPT_TYPE_JSON);					
    				}else {
    					httpURLConnection.setRequestMethod("GET");
//    					httpURLConnection.setRequestProperty("deviceId", AppSettings.getInstance().getDeviceId());
//    					httpURLConnection.setRequestProperty("deviceType", HTTP_DEVICEID_HEADER);
//    					httpURLConnection.setRequestProperty("track", HTTP_TRACK_HEADER);
//    					httpURLConnection.setRequestProperty("version", AppSettings.getInstance().getApplicationVersion(getApplicationContext()));
    					httpURLConnection.setRequestProperty("accept", HTTP_HEADER_ACCEPT_TYPE_JSON);
					}
    				
    				httpURLConnection.setConnectTimeout(getConnectTimeOut());
    				httpURLConnection.setReadTimeout(getReadTimeOut());
    				
    				inputStream = httpURLConnection.getInputStream();
    			}
    			    			
    			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    			responseStringBuilder = new StringBuilder();

    			while ((line = bufferedReader.readLine()) != null) {
    				responseStringBuilder.append(line);
    			}
    			
    			parseJsonResponse(responseStringBuilder.toString());
    			
    		} catch (Exception e) {			
    			e.printStackTrace();
    			
    			message = new Message();
    			message.what = WEB_SERVICE_FAILURE;
    			getCallback().sendMessage(message);
    			
    		}finally{
    			
    			if(httpsURLConnection!=null){
    				httpsURLConnection.disconnect();
    				httpsURLConnection = null;
    			}
    			
    			if(httpURLConnection!=null){
    				httpURLConnection.disconnect();
    				httpURLConnection = null;
    			}
    			
    			if(inputStream != null){
    				try {
    					inputStream.close();
    				} catch (IOException e) {					
    					e.printStackTrace();
    				}finally{
    					inputStream = null;
    				}
    			}
    			
    			if(bufferedReader != null){
    				try {
    					bufferedReader.close();
    				} catch (IOException e) {					
    					e.printStackTrace();
    				}finally{
    					bufferedReader = null;
    				}
    			}
    			
    		}
    		
    	}	
    	
    }
	
}
