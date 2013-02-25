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
	public Long insertContato(Contato contato) throws JSONException,
			ClientProtocolException, IOException {
		Long id = null;
		String url = "http://contatos-rest.herokuapp.com/contatos";

		JSONObject json = toJSON(contato);
		String result = postUrl(url, json.toString());
		
		JSONObject jsonResult = new JSONObject(result);
		id = jsonResult.getLong("id");
		
		return id;
	}
	
	public List<Contato> getContatos() {
		List<Contato> contatos = new ArrayList<Contato>();
		
		String url = "http://contatos-rest.herokuapp.com/contatos";
		try {
			String contatosStr = getUrl(url);
			JSONArray array = new JSONArray(contatosStr);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Contato contato = createContato(obj);
				contatos.add(contato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contatos;
	}
	
	private Contato createContato(JSONObject obj) throws JSONException {
		String nome = obj.getString("nome");
		String telefone = obj.getString("telefone");
		long id = 0;
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
	
	private String postUrl(String url, String body) throws ClientProtocolException, IOException {
		StringBuilder builder = new StringBuilder();
		
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(body));
		
		HttpResponse response = client.execute(httpPost);
		int status = response.getStatusLine().getStatusCode();
		if (status == 201) {
			HttpEntity entity = response.getEntity();
	        InputStream content = entity.getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	        String line;
	        while ((line = reader.readLine()) != null) {
	          builder.append(line);
	        }
		}
		return builder.toString();
	}
	
	private String getUrl(String url) {
	    StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(url);
	    try {
	      HttpResponse response = client.execute(httpGet);
	      StatusLine statusLine = response.getStatusLine();
	      if (statusLine.getStatusCode() == 200) {
	        HttpEntity entity = response.getEntity();
	        InputStream content = entity.getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	        String line;
	        while ((line = reader.readLine()) != null) {
	          builder.append(line);
	        }
	      } else {
	        Log.e("ContatoResource", "Servidor retornou erro.");
	      }
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return builder.toString();
	}
}
