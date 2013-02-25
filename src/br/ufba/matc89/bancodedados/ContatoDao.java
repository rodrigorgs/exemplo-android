package br.ufba.matc89.bancodedados;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ContatoDao {

	DatabaseHelper dbHelper;
	SQLiteDatabase db;
	
	public ContatoDao(Context context) {
		dbHelper = new DatabaseHelper(context);
	}
	
	public void open() {
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public List<Contato> getAll() {
		List<Contato> list = new ArrayList<Contato>();
		
		Cursor cursor = db.query("contato",
				new String[] {"_id", "nome", "telefone"}, 
				null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			long id = cursor.getLong(0);
			String nome = cursor.getString(1);
			String telefone = cursor.getString(2);
			Contato contato = new Contato(id, nome, telefone);
			list.add(contato);
			cursor.moveToNext();
		}
		
		return list;
	}
	
	public void insert(Contato contato) {
		ContentValues values = new ContentValues();
		values.put("nome", contato.getNome());
		values.put("telefone", contato.getTelefone());
		long id = db.insert("contato", null, values);
		Log.d("teste", "inserido contato com id = " + id);
	}
	
	public void remove(Contato contato) {
		db.delete("contato", "_id = " + contato.getId(), null);
	}
	
	public void removeAll() {
		db.delete("contato", null, null);
	}
}
