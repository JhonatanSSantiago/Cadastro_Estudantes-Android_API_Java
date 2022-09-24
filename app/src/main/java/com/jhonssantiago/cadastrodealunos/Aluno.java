package com.jhonssantiago.cadastrodealunos;

import static java.lang.Integer.parseInt;

public class Aluno {
    private String nome;
    private String curso;
    private String disciplina;
    private String nota;
    private String status = "";

    public Aluno(String nome, String curso, String disciplina, String nota) {
        this.nome = nome;
        this.curso = curso;
        this.disciplina = disciplina;
        this.nota = nota;
        int notaIn = (Integer) parseInt(nota);
        if(notaIn >= 6 )
            this.status = "Aprovado";
        else
            this.status = "Reprovado";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "nome='" + nome + '\'' +
                ", curso='" + curso + '\'' +
                ", disciplina='" + disciplina + '\'' +
                ", nota='" + nota + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
