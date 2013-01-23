package br.ufba.matc89.bancodedados;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	EditText editNome;
	EditText editTelefone;
	TextView textContatos;
	
	ContatoDao contatoDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editNome = (EditText)findViewById(R.id.editNome);
		editTelefone = (EditText)findViewById(R.id.editTelefone);
		textContatos = (TextView)findViewById(R.id.textContatos);
		
		contatoDao = new ContatoDao(this);
	}

	@Override
	protected void onResume() {
		contatoDao.open();
		atualizarContatos();
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
		String s = "";
		
		List<Contato> list = contatoDao.getAll();
		for (Contato contato : list) {
			s += contato.toString();
		}
		
		textContatos.setText(s);
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
}
