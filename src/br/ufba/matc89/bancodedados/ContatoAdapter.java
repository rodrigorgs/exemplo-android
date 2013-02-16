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
		View view = convertView;
		
		Contato contato = getItem(position);
		if (contato != null) {
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(android.R.layout.simple_list_item_2, null);
				
				ViewHolder holder = new ViewHolder();
				holder.textNome = (TextView)view.findViewById(android.R.id.text1);
				holder.textTelefone = (TextView)view.findViewById(android.R.id.text2);
				view.setTag(holder);
			}
			ViewHolder holder = (ViewHolder)view.getTag();
			holder.textNome.setText(contato.getNome());
			holder.textTelefone.setText(contato.getTelefone());
		}
		
		return view;
	}
	
	static class ViewHolder {
		public TextView textNome;
		public TextView textTelefone;
	}
}
