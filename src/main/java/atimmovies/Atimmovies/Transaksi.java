/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atimmovies.Atimmovies;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OS
 */
@Entity
@Table(name = "transaksi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaksi.findAll", query = "SELECT t FROM Transaksi t"),
    @NamedQuery(name = "Transaksi.findByIdtransaksi", query = "SELECT t FROM Transaksi t WHERE t.idtransaksi = :idtransaksi"),
    @NamedQuery(name = "Transaksi.findByIdPelanggan", query = "SELECT t FROM Transaksi t WHERE t.idPelanggan = :idPelanggan"),
    @NamedQuery(name = "Transaksi.findByIdCD", query = "SELECT t FROM Transaksi t WHERE t.idCD = :idCD"),
    @NamedQuery(name = "Transaksi.findByTglpinjam", query = "SELECT t FROM Transaksi t WHERE t.tglpinjam = :tglpinjam"),
    @NamedQuery(name = "Transaksi.findByBiaya", query = "SELECT t FROM Transaksi t WHERE t.biaya = :biaya")})
public class Transaksi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_transaksi")
    private String idtransaksi;
    @Basic(optional = false)
    @Column(name = "id_pelanggan")
    private String idPelanggan;
    @Basic(optional = false)
    @Column(name = "id_CD")
    private String idCD;
    @Basic(optional = false)
    @Column(name = "Tgl_pinjam")
    @Temporal(TemporalType.DATE)
    private Date tglpinjam;
    @Basic(optional = false)
    @Column(name = "biaya")
    private String biaya;
    @JoinColumn(name = "Id_transaksi", referencedColumnName = "Id_CD", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Dvd dvd;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "transaksi")
    private Pelanggan pelanggan;

    public Transaksi() {
    }

    public Transaksi(String idtransaksi) {
        this.idtransaksi = idtransaksi;
    }

    public Transaksi(String idtransaksi, String idPelanggan, String idCD, Date tglpinjam, String biaya) {
        this.idtransaksi = idtransaksi;
        this.idPelanggan = idPelanggan;
        this.idCD = idCD;
        this.tglpinjam = tglpinjam;
        this.biaya = biaya;
    }

    public String getIdtransaksi() {
        return idtransaksi;
    }

    public void setIdtransaksi(String idtransaksi) {
        this.idtransaksi = idtransaksi;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getIdCD() {
        return idCD;
    }

    public void setIdCD(String idCD) {
        this.idCD = idCD;
    }

    public Date getTglpinjam() {
        return tglpinjam;
    }

    public void setTglpinjam(Date tglpinjam) {
        this.tglpinjam = tglpinjam;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    public Dvd getDvd() {
        return dvd;
    }

    public void setDvd(Dvd dvd) {
        this.dvd = dvd;
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
        hash += (idtransaksi != null ? idtransaksi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaksi)) {
            return false;
        }
        Transaksi other = (Transaksi) object;
        if ((this.idtransaksi == null && other.idtransaksi != null) || (this.idtransaksi != null && !this.idtransaksi.equals(other.idtransaksi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "atimmovies.Atimmovies.Transaksi[ idtransaksi=" + idtransaksi + " ]";
    }
    
}
