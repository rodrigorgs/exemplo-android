package br.ufba.matc89.bancodedados;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
		// TODO
		return null;
	}
	
	public void insert(Contato contato) {
		// TODO
	}
	
	public void remove(Contato contato) {
		// TODO
	}
}
