package com.theexceptionulls.projectskullnbones.webservices;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import javax.net.ssl.*;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

public class OfferImageLoader {

	public static final String BITMAP_KEY = "bitmap";
	public static final String OFFER_ID_KEY = "offerId";
	public static final int IMAGE_CALLBACK = 1001;
	private static final int CONNECT_TIME_OUT = 15*1000;
	private static final int READ_TIME_OUT = 30*1000;
	
	private static OfferImageLoader instance = new OfferImageLoader();
	
	private OfferImageLoader() {
		
	}
	
	public static OfferImageLoader getInstance(){
		return instance;
	}
	
	public void downloadImage(final int offerIndex, final String offerImageUrl, final Handler callback, int offerRequestType){
		ImageLoaderThread imageLoaderThread = new ImageLoaderThread(offerIndex, offerImageUrl, callback, offerRequestType);
		imageLoaderThread.start();
	}

	// The ImageLoaderThread class extends Thread class
	// It creates a new thread for downloading an image
	private class ImageLoaderThread extends Thread {

		private int offerIndex;
		private String offerImageUrl;
		private Handler callback;
        private int offerRequestType;

		public ImageLoaderThread(final int offerIndex,
				final String offerImageUrl, final Handler callback, int offerRequestType) {
			this.offerIndex = offerIndex;
			this.offerImageUrl = offerImageUrl;
			this.callback = callback;
            this.offerRequestType = offerRequestType;
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
		
		@Override
		public void run() {
			try {

				URL imageUrl = new URL(offerImageUrl);

				if (offerImageUrl.contains("https")) {

					HttpsURLConnection httpsURLConnection = (HttpsURLConnection) imageUrl
							.openConnection();

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

					httpsURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
					httpsURLConnection.setReadTimeout(READ_TIME_OUT);
					
					InputStream inputStream = httpsURLConnection
							.getInputStream();
					Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put(BITMAP_KEY, bitmap);
					
					Message message = new Message();
					message.obj = bitmap;
					message.arg1 = offerIndex;
                    message.arg2 = offerRequestType;
					message.what = IMAGE_CALLBACK;
					callback.sendMessage(message);
					
				} else {

					HttpURLConnection httpURLConnection = (HttpURLConnection) imageUrl
							.openConnection();
					InputStream inputStream = httpURLConnection
							.getInputStream();
					Bitmap bitmap = BitmapFactory.decodeStream(inputStream);					

					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put(BITMAP_KEY, bitmap);
					
					Message message = new Message();
					message.obj = bitmap;
					message.arg1 = offerIndex;
                    message.arg2 = offerRequestType;
					message.what = IMAGE_CALLBACK;
					callback.sendMessage(message);
				}

			} catch (Exception e) {
                Log.i("offers",e.toString()+offerIndex);
				e.printStackTrace();
				Message message = new Message();
				message.obj = null;
				message.arg1 = offerIndex;
                message.arg2 = offerRequestType;
				message.what = IMAGE_CALLBACK;
				callback.sendMessage(message);
			} 
		}

	}
	
}
