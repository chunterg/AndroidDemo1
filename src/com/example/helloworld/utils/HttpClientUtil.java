package com.example.helloworld.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpClientUtil {
	
	public static JSONObject getJsonResault(String url,JSONObject params) {
		JSONObject resJsonObject = null;
//		 JSONObject param = new JSONObject();
	        try {
	        	
	        	
	            HttpPost httpPost = new HttpPost(url);
	           // httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
	            StringEntity se = new StringEntity(params.toString());
	            httpPost.setEntity(se);
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            

	            HttpResponse httpResponse = httpClient.execute(httpPost);
	            Log.d("App-http",String.valueOf(httpResponse.getStatusLine().getStatusCode()));
	            if(httpResponse.getStatusLine().getStatusCode()==200){ 
	            	String strResult = EntityUtils.toString(httpResponse.getEntity());   
	 	           
	                 try {
	                         /**把json字符串转换成json对象**/
	                 	resJsonObject = new JSONObject(strResult);
	                 } catch (JSONException e1) {
	                         // TODO Auto-generated catch block
	                         e1.printStackTrace();
	                 }
//	 	            for (String s = reader.readLine(); s != null; s = reader.readLine()) {
//	 	                builder.append(s);
//	 	            }
	            }else{
	            	resJsonObject = new JSONObject("{data:\"error\"}");
	            }
	           
	        } catch (Exception e) {
	            Log.e("App-http", e.toString());
	            e.printStackTrace();
	        }
	        return resJsonObject;
	    }
}
