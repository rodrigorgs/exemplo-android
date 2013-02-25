package br.ufba.matc89.bancodedados;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ContatoResource {
	private static final String URL = 
			"http://contatos-rest.herokuapp.com/contatos";
	
	// Operações
	
	public Long insertContato(Contato contato) throws JSONException,
			ClientProtocolException, IOException {
		Long id = null;

		JSONObject json = toJSON(contato);
		String result = postUrl(URL, json.toString());
		
		JSONObject jsonResult = new JSONObject(result);
		id = jsonResult.getLong("id");
		
		return id;
	}
	
	public List<Contato> getContatos() throws JSONException,
			ClientProtocolException, IOException {
		List<Contato> contatos = new ArrayList<Contato>();
		
		String contatosStr = getUrl(URL);
		JSONArray array = new JSONArray(contatosStr);
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			Contato contato = fromJSON(obj);
			contatos.add(contato);
		}
		
		return contatos;
	}
	
	// Serialização
	
	private Contato fromJSON(JSONObject obj) throws JSONException {
		String nome = obj.getString("nome");
		String telefone = obj.getString("telefone");
		long id = -1;
		if (obj.has("id"))
			id = obj.getLong("id");
		
		return new Contato(id, nome, telefone);
	}
	
	private JSONObject toJSON(Contato contato) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("nome", contato.getNome());
		obj.put("telefone", contato.getTelefone());
		return obj;
	}
	
	// HTTP
	
	private String postUrl(String url, String body)
			throws ClientProtocolException, IOException {
		String responseBody = "";
		
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(body));
		
		HttpResponse response = client.execute(httpPost);
		int status = response.getStatusLine().getStatusCode();
		if (status == 201) {
			responseBody = readBody(response);
		}
		else {
			Log.e("ContatoResource", "Status = " + status);
		}
		
		return responseBody;
	}
	
	private String getUrl(String url) throws ClientProtocolException,
			IOException {
		String responseBody = "";
		
	    HttpClient client = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(url);
	    
		HttpResponse response = client.execute(httpGet);
		int status = response.getStatusLine().getStatusCode();
		if (status == 200) {
			responseBody = readBody(response);
		} else {
			Log.e("ContatoResource", "Status = " + status);
		}
		
		return responseBody;
	}
	
	private String readBody(HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		InputStream content = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				content));
		
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		
		return builder.toString();
	}
}
