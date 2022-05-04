package no.hvl.dat110.aciotdevice.client;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;	
import java.io.IOException;
import com.google.gson.Gson;

public class RestClient {
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	public RestClient() {
		// TODO Auto-generated constructor stub
	}

	private static String logpath = "/accessdevice/log/";

	public void doPostAccessEntry(String message) {

		OkHttpClient client  = new OkHttpClient();
		Gson gson = new Gson();
		AccessMessage melding = new AccessMessage(message);
		
		RequestBody body = RequestBody.create(JSON, gson.toJson(melding));
		
		Request req = new Request.Builder()
				.url("http://localhost:8080" + logpath)
				.post(body)
				.build();
		
		try(Response response = client.newCall(req).execute()){
			System.out.println(response.body().string());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String codepath = "/accessdevice/code";
	
	public AccessCode doGetAccessCode() {
		
		OkHttpClient client  = new OkHttpClient();
		Gson gson = new Gson();
		
		Request req = new Request.Builder()	
				.url("http://localhost:8080" + codepath)
				.get()
				.build();

		AccessCode code = null;
		
		try(Response resp = client.newCall(req).execute()){
			
			code = gson.fromJson(resp.body().string(), AccessCode.class);
			
		}catch (IOException e) {
			e.printStackTrace();
		}
			
		return code;
	}
}
