package apap.tugasindividu.sisdm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SertifikasiKaryawanKey implements Serializable {
    @Column(name="idKaryawan")
    private Long idKaryawan;

    @Column(name="id_sertifikasi")
    private Long idSertifikasi;
}