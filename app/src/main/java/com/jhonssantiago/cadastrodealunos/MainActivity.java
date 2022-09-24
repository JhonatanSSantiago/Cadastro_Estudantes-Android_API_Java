package com.jhonssantiago.cadastrodealunos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;

//  Coloque uma barra de progresso, para dar a sensação que tem uma operação em andamento.
//  Ao clicar em um elemento da lista, deve ser mostrado uma caixa de diálogo perguntando se realmente deseja excluir o item. Caso seja sim, o elemento deve ser retirado.

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText nome, nota;
    private RadioButton RBSistemas, RBOutros;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private LinearLayout linearLayout;
    private Button BSalvar, BRelatorio;
    private String disciplina, curso;
    private ArrayList<Aluno> alunoArrayList = new ArrayList<>();
    private MyAdapter meuAdapter;
    private ListView listView_itens;
    private TableLayout tabelaR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome=findViewById(R.id.ETNome);
        nota=findViewById(R.id.ETNota);
        linearLayout=findViewById(R.id.lineView);
        spinner=findViewById(R.id.SDisciplinas);
        listView_itens=findViewById(R.id.ListView);
        tabelaR=findViewById(R.id.Tabela);

        RBSistemas=findViewById(R.id.RBSistemas);
        RBSistemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                curso =  RBSistemas.getText().toString();
                abriSpinner();
            }
        });
        RBOutros=findViewById(R.id.RBOutros);
        RBOutros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);
                curso = RBOutros.getText().toString();
                disciplina="Não informada";
            }
        });

        BSalvar=findViewById(R.id.BSalvar);
        BSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nota.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Verifique se todos os dados foram preenchidos", Toast.LENGTH_SHORT).show();
                }if (nome.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Verifique se todos os dados foram preenchidos", Toast.LENGTH_SHORT).show();
                }else{
                //    Aluno a = new Aluno(nome.getText().toString(), RBSistemas.getText().toString(), disciplina, nota.getText().toString());
                    Aluno a = new Aluno(nome.getText().toString(), curso, disciplina, nota.getText().toString());
               //     Toast.makeText(MainActivity.this, a.toString(), Toast.LENGTH_SHORT).show();
                    alunoArrayList.add(a);
                    limparCampos();
                }
            }
        });

        BRelatorio=findViewById(R.id.BRelatorio);
        BRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alunoArrayList.isEmpty()){
                    tabelaR.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Sem Dados", Toast.LENGTH_SHORT).show();
                }else{
                    tabelaR.setVisibility(View.VISIBLE);
                }
                meuAdapter = new MyAdapter(getApplicationContext(), alunoArrayList);

                listView_itens.setAdapter(meuAdapter);
                listView_itens.setVisibility(View.VISIBLE);
            }
        });

        listView_itens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                alunoArrayList.remove(i);
              //  Aluno b = (Aluno) meuAdapter.getItem(i);
              //  Toast.makeText(getApplicationContext(), b.toString(), Toast.LENGTH_SHORT).show();
                BRelatorio.callOnClick();
            }
        });
    }

    private void limparCampos(){
        nome.setText("");
        nota.setText("");
        RBSistemas.setChecked(false);
        spinner.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
    }

    private void abriSpinner() {
        adapter = ArrayAdapter.createFromResource(this, R.array.disciplinas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = (String) spinner.getItemAtPosition(i);
        disciplina = item;
     //   Toast.makeText(MainActivity.this, "item: "+item, Toast.LENGTH_SHORT).show();
        linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}