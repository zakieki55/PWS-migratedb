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
@Table(name = "dvd")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dvd.findAll", query = "SELECT d FROM Dvd d"),
    @NamedQuery(name = "Dvd.findByIdCD", query = "SELECT d FROM Dvd d WHERE d.idCD = :idCD"),
    @NamedQuery(name = "Dvd.findByJudul", query = "SELECT d FROM Dvd d WHERE d.judul = :judul"),
    @NamedQuery(name = "Dvd.findByGenre", query = "SELECT d FROM Dvd d WHERE d.genre = :genre")})
public class Dvd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_CD")
    private String idCD;
    @Basic(optional = false)
    @Column(name = "judul")
    private String judul;
    @Basic(optional = false)
    @Column(name = "genre")
    private String genre;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "dvd")
    private Transaksi transaksi;

    public Dvd() {
    }

    public Dvd(String idCD) {
        this.idCD = idCD;
    }

    public Dvd(String idCD, String judul, String genre) {
        this.idCD = idCD;
        this.judul = judul;
        this.genre = genre;
    }

    public String getIdCD() {
        return idCD;
    }

    public void setIdCD(String idCD) {
        this.idCD = idCD;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
        hash += (idCD != null ? idCD.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dvd)) {
            return false;
        }
        Dvd other = (Dvd) object;
        if ((this.idCD == null && other.idCD != null) || (this.idCD != null && !this.idCD.equals(other.idCD))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "atimmovies.Atimmovies.Dvd[ idCD=" + idCD + " ]";
    }
    
}
