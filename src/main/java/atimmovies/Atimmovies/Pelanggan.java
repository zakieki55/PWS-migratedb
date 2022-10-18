/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atimmovies.Atimmovies;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "pelanggan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pelanggan.findAll", query = "SELECT p FROM Pelanggan p"),
    @NamedQuery(name = "Pelanggan.findByIdPelanggan", query = "SELECT p FROM Pelanggan p WHERE p.idPelanggan = :idPelanggan"),
    @NamedQuery(name = "Pelanggan.findByNamaPelanggan", query = "SELECT p FROM Pelanggan p WHERE p.namaPelanggan = :namaPelanggan"),
    @NamedQuery(name = "Pelanggan.findByNoTelpn", query = "SELECT p FROM Pelanggan p WHERE p.noTelpn = :noTelpn"),
    @NamedQuery(name = "Pelanggan.findByPegawaiIdpegawai", query = "SELECT p FROM Pelanggan p WHERE p.pegawaiIdpegawai = :pegawaiIdpegawai")})
public class Pelanggan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_pelanggan")
    private String idPelanggan;
    @Basic(optional = false)
    @Column(name = "nama_pelanggan")
    private String namaPelanggan;
    @Basic(optional = false)
    @Column(name = "no_telpn")
    private String noTelpn;
    @Basic(optional = false)
    @Column(name = "pegawaiId_pegawai")
    private String pegawaiIdpegawai;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pelanggan")
    private Pegawai pegawai;
    @JoinColumn(name = "id_pelanggan", referencedColumnName = "Id_transaksi", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Transaksi transaksi;

    public Pelanggan() {
    }

    public Pelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public Pelanggan(String idPelanggan, String namaPelanggan, String noTelpn, String pegawaiIdpegawai) {
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.noTelpn = noTelpn;
        this.pegawaiIdpegawai = pegawaiIdpegawai;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getNoTelpn() {
        return noTelpn;
    }

    public void setNoTelpn(String noTelpn) {
        this.noTelpn = noTelpn;
    }

    public String getPegawaiIdpegawai() {
        return pegawaiIdpegawai;
    }

    public void setPegawaiIdpegawai(String pegawaiIdpegawai) {
        this.pegawaiIdpegawai = pegawaiIdpegawai;
    }

    public Pegawai getPegawai() {
        return pegawai;
    }

    public void setPegawai(Pegawai pegawai) {
        this.pegawai = pegawai;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPelanggan != null ? idPelanggan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pelanggan)) {
            return false;
        }
        Pelanggan other = (Pelanggan) object;
        if ((this.idPelanggan == null && other.idPelanggan != null) || (this.idPelanggan != null && !this.idPelanggan.equals(other.idPelanggan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "atimmovies.Atimmovies.Pelanggan[ idPelanggan=" + idPelanggan + " ]";
    }
    
}
