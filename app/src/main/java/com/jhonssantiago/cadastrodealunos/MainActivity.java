package com.jhonssantiago.cadastrodealunos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//  Coloque uma barra de progresso, para dar a sensação que tem uma operação em andamento.
//  Ao clicar em um elemento da lista, deve ser mostrado uma caixa de diálogo perguntando se realmente deseja excluir o item. Caso seja sim, o elemento deve ser retirado.

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, MyDialogFragment.MyDialogFragmentListener {
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
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();
    private int opcao;
    private int alunoPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.ETNome);
        nota = findViewById(R.id.ETNota);
        linearLayout = findViewById(R.id.lineView);
        spinner = findViewById(R.id.SDisciplinas);
        listView_itens = findViewById(R.id.ListView);
        progressBar = findViewById(R.id.PBcarregando);
        RBOutros = findViewById(R.id.RBOutros);
        RBSistemas = findViewById(R.id.RBSistemas);
        BSalvar = findViewById(R.id.BSalvar);
        BRelatorio = findViewById(R.id.BRelatorio);
        textView =findViewById(R.id.textView);

        RBSistemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                curso = RBSistemas.getText().toString();
                abriSpinner();
            }
        });

        RBOutros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);
                curso = RBOutros.getText().toString();
                disciplina = "Não informada";
            }
        });

        BSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nota.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Verifique se todos os dados foram preenchidos", Toast.LENGTH_SHORT).show();
                }
                if (nome.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Verifique se todos os dados foram preenchidos", Toast.LENGTH_SHORT).show();
                } else {
                    //    Aluno a = new Aluno(nome.getText().toString(), RBSistemas.getText().toString(), disciplina, nota.getText().toString());

                    Aluno a = new Aluno(nome.getText().toString(), curso, disciplina, nota.getText().toString());
                    Toast.makeText(MainActivity.this, "Aluno "+ a.getNome().toString() +"cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    alunoArrayList.add(a);
                    limparCampos();
                }
            }
        });

        BRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meuAdapter = new MyAdapter(getApplicationContext(), alunoArrayList);
                listView_itens.setAdapter(meuAdapter);
                listView_itens.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    public void run() {
                        while (progressStatus < 100) {
                            progressStatus += 25;
                            handler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressStatus);
                                    textView.setText(progressStatus+" %");
                                    if(progressStatus==100){
                                        if(alunoArrayList.size() == 0){
                                            Toast.makeText(MainActivity.this, "Sem Dados", Toast.LENGTH_SHORT).show();
                                        }else{
                                            listView_itens.setVisibility(View.VISIBLE);
                                        }
                                        textView.setVisibility(View.INVISIBLE);
                                        progressBar.setVisibility(View.INVISIBLE);
//                                        listView_itens.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                            try {
                                // Sleep for 200 milliseconds.
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                progressStatus = 0;
            }
        });

        listView_itens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyDialogFragment md = new MyDialogFragment();
                md.show(getSupportFragmentManager(), "excluir");
                alunoPos = i;
            }
        });
    }

    private void excluirAluno(int i) {
        if(opcao == 1){
            alunoArrayList.remove(i);
            Toast.makeText(getApplicationContext(), "Excluido", Toast.LENGTH_SHORT).show();
            BRelatorio.callOnClick();
        }else{
            BRelatorio.callOnClick();
        }

    }

    @Override
    public void opcao(int op) {
        opcao = op;
        excluirAluno(alunoPos);
    }

    private void limparCampos() {
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
        linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}