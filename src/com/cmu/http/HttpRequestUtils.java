package com.cmu.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;

public class HttpRequestUtils {

	private static String Tag = "HttpRequestUtils";
	private static String ServerIP = "http://128.237.201.73:8080/MajorityWin/";

	public static String createRoom(String username) throws ClientProtocolException,
			IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(ServerIP + "CreateRoom?username=" + username + "&roomsize=10");
		HttpResponse response = httpClient.execute(httpGet);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			InputStream is = response.getEntity().getContent();
			String result = convertStreamToString(is);
			if(result.equals("")){
				throw new IllegalStateException("Unexpected Exception");
			}else{
				return result;
			}
		} else {
			throw new IllegalStateException("Network Failure");
		}
	}

	public static String getInfo(String roomID) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		String param = URLEncoder.encode(roomID);
		HttpGet httpGet = new HttpGet(ServerIP + "GetRoomInfo?roomID=" + param);
		HttpResponse response = httpClient.execute(httpGet);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			InputStream is = response.getEntity().getContent();
			String result = convertStreamToString(is);
			if(result.equals("")){
				throw new IllegalStateException("Unexpected Exception");
			}else{
				return result;
			}
		} else {
			throw new IllegalStateException("Network Failure");
		}
	}
	
	public static boolean joinRoom(String roomID, String username) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		String param1 = URLEncoder.encode(roomID);
		String param2 = URLEncoder.encode(username);
		HttpGet httpGet = new HttpGet(ServerIP + "JoinRoom?roomID=" + param1 + "&username=" + param2);
		HttpResponse response = httpClient.execute(httpGet);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			InputStream is = response.getEntity().getContent();
			String result = convertStreamToString(is);
			if(!result.equals("")){
				return true;
			}else{
				return false;
			}
		} else {
			Log.e(Tag, "Code:" + code);
			throw new IllegalStateException("Network Failure");
		}
	}

	public static boolean pickLeader(String roomID) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		String param = URLEncoder.encode(roomID);
		HttpGet httpGet = new HttpGet(ServerIP + "PickLeader?roomID=" + param);
		HttpResponse response = httpClient.execute(httpGet);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			InputStream is = response.getEntity().getContent();
			String result = convertStreamToString(is);
			if(!result.equals("")){
				return true;
			}else{
				return false;
			}
		} else {
			Log.e(Tag, "Code:" + code);
			throw new IllegalStateException("Network Failure");
		}
	}
	
	public static void submitQuestion(String json, String roomID) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		String param = URLEncoder.encode(roomID);
		String param2 = URLEncoder.encode(json);
		HttpGet httpGet = new HttpGet(ServerIP + "SubmitQuestion?roomID=" + param + "&question=" + param2);
		HttpResponse response = httpClient.execute(httpGet);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			InputStream is = response.getEntity().getContent();
			String result = convertStreamToString(is);
			if(result.equals("")){
				throw new IllegalStateException("Unexpected Error");
			}
		} else {
			Log.e(Tag, "Code:" + code);
			throw new IllegalStateException("Network Failure");
		}
	}

	public static void submitVote(String roomID, String username, int option) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		String param1 = URLEncoder.encode(roomID);
		String param2 = URLEncoder.encode(username);
		String param3 = URLEncoder.encode(option+"");
		HttpGet httpGet = new HttpGet(ServerIP + "SubmitVote?roomID=" + param1 +"&username=" + param2 + "&option=" + param3);
		HttpResponse response = httpClient.execute(httpGet);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			InputStream is = response.getEntity().getContent();
			String result = convertStreamToString(is);
			if(result.equals("")){
				throw new IllegalStateException("Network Failure");
			}
		} else {
			Log.e(Tag, "Code:" + code);
			throw new IllegalStateException("Network Failure");
		}
	}

	public static String checkSubmitQuestionsStatus(String roomID) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		String param = URLEncoder.encode(roomID);
		//checkSubmitQuestionsStatus return jsonString of jsonObject: OK: boolean, leader: String, questions: String
		HttpGet httpGet = new HttpGet(ServerIP + "CheckQuestionStatus?roomID=" + param);
		HttpResponse response = httpClient.execute(httpGet);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			InputStream is = response.getEntity().getContent();
			String result = convertStreamToString(is);
			if(!result.equals("")){
				return result;
			}else{
				throw new IllegalStateException("Unexpected Error");
			}
		} else {
			Log.e(Tag, "Code:" + code);
			throw new IllegalStateException("Network Failure");
		}
	}

	public static String checkSubmitVoteStatus(String roomID) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		String param = URLEncoder.encode(roomID);
		//checkSubmitVoteStatus return jsonString of jsonObject: roomSize int, numOfFinished int, numOfMajority int, int status, result String;
		HttpGet httpGet = new HttpGet(ServerIP + "CheckSubmitStatus?roomID=" + param);
		HttpResponse response = httpClient.execute(httpGet);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			InputStream is = response.getEntity().getContent();
			String result = convertStreamToString(is);
			if(result.equals("")){
				throw new IllegalStateException("Network Failure");
			}else{
				return result;
			}
		} else {
			Log.e(Tag, "Code:" + code);
			throw new IllegalStateException("Network Failure");
		}
	}

	public static String giveUp(String roomID, String username) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		String param1 = URLEncoder.encode(roomID);
		String param2 = URLEncoder.encode(username);
		//checkSubmitVoteStatus reture newLeader string;
		HttpGet httpGet = new HttpGet(ServerIP + "GiveUpLeader?roomID=" + param1 +"&currentLeader=" + param2);
		HttpResponse response = httpClient.execute(httpGet);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			InputStream is = response.getEntity().getContent();
			String result = convertStreamToString(is);
			if(!result.equals("")){
				return result;
			}else{
				throw new IllegalStateException("Unexpected Exception");
			}
		} else {
			Log.e(Tag, "Code:" + code);
			throw new IllegalStateException("Network Failure");
		}
	}

	public static boolean register(String username, String password) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		String param = URLEncoder.encode(username);
		String param2 = URLEncoder.encode(password);
		//checkSubmitVoteStatus return jsonString of jsonObject: numOfFinished int, numOfMajority int, int status, result String;
		HttpGet httpGet = new HttpGet(ServerIP + "Register?username=" + param +"&password=" + param2);
		HttpResponse response = httpClient.execute(httpGet);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			InputStream is = response.getEntity().getContent();
			String result = convertStreamToString(is);
			if(result.equals("")){
				return false;
			}else{
				return true;
			}
		} else {
			Log.e(Tag, "Code:" + code);
			throw new IllegalStateException("Network Failure");
		}
	}
	
	public static boolean login(String username, String password) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		String param = URLEncoder.encode(username);
		String param2 = URLEncoder.encode(password);
		//checkSubmitVoteStatus return jsonString of jsonObject: numOfFinished int, numOfMajority int, int status, result String;
		HttpGet httpGet = new HttpGet(ServerIP + "Login?username=" + param +"&password=" + param2);
		HttpResponse response = httpClient.execute(httpGet);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			InputStream is = response.getEntity().getContent();
			String result = convertStreamToString(is);
			if(result.equals("")){
				return false;
			}else{
				return true;
			}
		} else {
			Log.e(Tag, "Code:" + code);
			throw new IllegalStateException("Network Failure");
		}
	}
	
	public static boolean startNextRound(String roomID, String username) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		String param1 = URLEncoder.encode(roomID);
		String param2 = URLEncoder.encode(username);
		//checkSubmitVoteStatus return jsonString of jsonObject: roomSize int, numOfFinished int, numOfMajority int, int status, result String;
		HttpGet httpGet = new HttpGet(ServerIP + "StartNewRound?roomID=" + param1 + "&username=" + param2);
		HttpResponse response = httpClient.execute(httpGet);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			InputStream is = response.getEntity().getContent();
			String result = convertStreamToString(is);
			if(result.equals("")){
				return false;
			}else{
				return true;
			}
		} else {
			Log.e(Tag, "Code:" + code);
			throw new IllegalStateException("Network Failure");
		}
	}

	
	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	
}
