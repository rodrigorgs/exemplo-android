package br.ufba.matc89.bancodedados;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContatoAdapter extends ArrayAdapter<Contato> {
	public ContatoAdapter(Context context, List<Contato> contatos) {
		super(context, 0, contatos);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		Contato contato = getItem(position);
		if (contato != null) {
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(android.R.layout.simple_list_item_2, null);
			TextView text1 = (TextView)view.findViewById(android.R.id.text1);
			TextView text2 = (TextView)view.findViewById(android.R.id.text2);
			text1.setText(contato.getNome());
			text2.setText(contato.getTelefone());
		}
		
		return view;
	}
}
