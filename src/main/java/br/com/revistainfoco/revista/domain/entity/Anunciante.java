package br.com.revistainfoco.revista.domain.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "anunciante")
public class Anunciante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cnpj", length = 16, columnDefinition = "VARCHAR(16)", nullable = false, unique = true)
    private String cnpj;

    @Column(name = "razaoSocial", length = 50, columnDefinition = "VARCHAR(50)", nullable = false)
    private String razaoSocial;

    @Column(name = "nomeFantasia", length = 60, columnDefinition = "VARCHAR(60)", nullable = false)
    private String nomeFantasia;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Column(name = "email", length = 60, columnDefinition = "VARCHAR(60)", unique = true)
    private String email;

    @Column(name = "site", length = 60, columnDefinition = "VARCHAR(60)")
    private String site;

    @OneToOne
    @JoinColumn(name = "contato_id")
    private Contato contato;

    public Anunciante() {
    }

    public Anunciante(Long id, String cnpj, String razaoSocial, String nomeFantasia, Endereco endereco, String email, String site, Contato contato) {
        this.id = id;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.endereco = endereco;
        this.email = email;
        this.site = site;
        this.contato = contato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anunciante that = (Anunciante) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Anunciante{" +
                "id=" + id +
                ", cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", email='" + email + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
