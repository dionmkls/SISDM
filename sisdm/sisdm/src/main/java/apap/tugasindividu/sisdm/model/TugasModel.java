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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tugas")
public class TugasModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Max(20)
    @Column(nullable = false)
    private Long idTugas;

    @NotNull
    @Size(max=255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max=255)
    @Column(name = "deskripsi", nullable = false)
    private String deskripsi;

    @NotNull
    @Column(name = "story_point", nullable = false)
    private Integer storyPoint;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "idPresensi", referencedColumnName = "idPresensi", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PresensiModel presensi;
}
