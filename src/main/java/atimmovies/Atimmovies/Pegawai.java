/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atimmovies.Atimmovies;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OS
 */
@Entity
@Table(name = "pegawai")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pegawai.findAll", query = "SELECT p FROM Pegawai p"),
    @NamedQuery(name = "Pegawai.findByIdPegawai", query = "SELECT p FROM Pegawai p WHERE p.idPegawai = :idPegawai"),
    @NamedQuery(name = "Pegawai.findByNamapegawai", query = "SELECT p FROM Pegawai p WHERE p.namapegawai = :namapegawai"),
    @NamedQuery(name = "Pegawai.findByAlamat", query = "SELECT p FROM Pegawai p WHERE p.alamat = :alamat"),
    @NamedQuery(name = "Pegawai.findByNoTelepn", query = "SELECT p FROM Pegawai p WHERE p.noTelepn = :noTelepn")})
public class Pegawai implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_pegawai")
    private String idPegawai;
    @Basic(optional = false)
    @Column(name = "Nama_pegawai")
    private String namapegawai;
    @Basic(optional = false)
    @Column(name = "Alamat")
    private String alamat;
    @Basic(optional = false)
    @Column(name = "no_telepn")
    private String noTelepn;
    @JoinColumn(name = "id_pegawai", referencedColumnName = "id_pelanggan", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Pelanggan pelanggan;

    public Pegawai() {
    }

    public Pegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public Pegawai(String idPegawai, String namapegawai, String alamat, String noTelepn) {
        this.idPegawai = idPegawai;
        this.namapegawai = namapegawai;
        this.alamat = alamat;
        this.noTelepn = noTelepn;
    }

    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getNamapegawai() {
        return namapegawai;
    }

    public void setNamapegawai(String namapegawai) {
        this.namapegawai = namapegawai;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelepn() {
        return noTelepn;
    }

    public void setNoTelepn(String noTelepn) {
        this.noTelepn = noTelepn;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPegawai != null ? idPegawai.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pegawai)) {
            return false;
        }
        Pegawai other = (Pegawai) object;
        if ((this.idPegawai == null && other.idPegawai != null) || (this.idPegawai != null && !this.idPegawai.equals(other.idPegawai))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "atimmovies.Atimmovies.Pegawai[ idPegawai=" + idPegawai + " ]";
    }
    
}
