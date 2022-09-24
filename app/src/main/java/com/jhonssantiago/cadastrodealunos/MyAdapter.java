package com.jhonssantiago.cadastrodealunos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Aluno> alunoArrayList;

    public MyAdapter(Context context, ArrayList<Aluno> alunoArrayList) {
        this.alunoArrayList = alunoArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return alunoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return alunoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Aluno aluno= alunoArrayList.get(position);
        view = inflater.inflate(R.layout.lista_disciplina, null); //view vazia

        TextView textViewAluno = view.findViewById(R.id.textView_aluno); //preenchendo view
        textViewAluno.setText(aluno.getNome());
        TextView textViewStatus = view.findViewById(R.id.textView_status);
        textViewStatus.setText(aluno.getStatus());
        return view;
    }
}
