package br.com.cinemacultural.model;

public class Analise {

    private Long id;
    private Long filmeId;
    private String texto;
    private double nota;

    public Analise(Long id, Long filmeId, String texto, double nota) {
        this.id = id;
        this.filmeId = filmeId;
        this.texto = texto;
        this.nota = nota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFilmeId() {
        return filmeId;
    }

    public String getTexto() {
        return texto;
    }

    public double getNota() {
        return nota;
    }
}
