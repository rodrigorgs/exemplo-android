package br.ufba.matc89.bancodedados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int versao = 1;
	
	public DatabaseHelper(Context context) {
		super(context, "banco.db", null, versao);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE contato(" +
				"  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"  nome TEXT," +
				"  telefone TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
