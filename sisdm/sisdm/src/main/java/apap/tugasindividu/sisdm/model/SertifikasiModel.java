package apap.tugasindividu.sisdm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sertifikasi")
public class SertifikasiModel {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Max(20)
    private Long idSertifikasi;

    @NotNull
    @Size(max=255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @OneToMany(mappedBy = "sertifikasi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SertifikasiKaryawanModel> listSertifikasiKaryawan;

//    @ManyToMany(mappedBy = "listSertifikasi")
//    List<KaryawanModel> listKaryawan;
}
