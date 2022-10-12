package apap.tugasindividu.sisdm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sertifikasi_karyawan")
public class SertifikasiKaryawanModel implements Serializable {

    @EmbeddedId
    SertifikasiKaryawanKey keyId;

    @NotNull
    @Size(max=14)
    @Column(name = "no_sertifikasi", nullable = false)
    private String noSertifikasi;

    @NotNull
    @Column(name = "tanggal_pengambilan", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalPengambilan;

    @MapsId("idSertifikasi")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name ="idSertifikasi", referencedColumnName = "idSertifikasi", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SertifikasiModel sertifikasi;

    @MapsId("idKaryawan")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name ="idKaryawan", referencedColumnName = "idKaryawan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KaryawanModel karyawan;
}