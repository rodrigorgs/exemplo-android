package br.ufba.matc89.bancodedados;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText editNome;
	EditText editTelefone;
	ListView listView;
	ContatoAdapter listAdapter;
	ContatoDao contatoDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editNome = (EditText)findViewById(R.id.editNome);
		editTelefone = (EditText)findViewById(R.id.editTelefone);
		listView = (ListView)findViewById(R.id.listView);
				
		contatoDao = new ContatoDao(this);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy); 
	}

	@Override
	protected void onResume() {
		contatoDao.open();
		
		listAdapter = new ContatoAdapter(this, contatoDao.getAll());
		listView.setAdapter(listAdapter);
		
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		contatoDao.close();
		super.onPause();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void atualizarContatos() {
		List<Contato> list = contatoDao.getAll();
		
		listAdapter.clear();
		listAdapter.addAll(list);
	}
	
	public void inserir(View v) {
		Log.d("teste", "inserir");
		
		String nome = editNome.getText().toString();
		String telefone = editTelefone.getText().toString();
		
		Contato contato = new Contato(0, nome, telefone);
		contatoDao.insert(contato);
		
		atualizarContatos();
	}
	
	public void removerPrimeiro(View v) {
		Log.d("teste", "removerPrimeiro");
		
		List<Contato> contatos = contatoDao.getAll();
		if (contatos.size() > 0) {
			Contato primeiro = contatos.get(0);
			contatoDao.remove(primeiro);
		}
		
		atualizarContatos();
	}
	
	public void download(View v) {
		ContatoResource res = new ContatoResource();
		List<Contato> contatos = res.getContatos();
		
		contatoDao.removeAll();
		for (Contato contato : contatos)
			contatoDao.insert(contato);
		
		atualizarContatos();
	}
	
	public void upload(View v) throws Exception {
		String nome = editNome.getText().toString();
		String telefone = editTelefone.getText().toString();
		Contato contato = new Contato(0, nome, telefone);
		
		ContatoResource res = new ContatoResource();
		Long id = res.insertContato(contato);
		
		Toast.makeText(this, 
				"Criado contato com id = " + id, 
				Toast.LENGTH_SHORT).show();
	}
}
