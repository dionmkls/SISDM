package apap.tugasindividu.sisdm.repository;

import apap.tugasindividu.sisdm.model.KaryawanModel;
import apap.tugasindividu.sisdm.model.PresensiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresensiDb extends JpaRepository<PresensiModel, Long> {

    Optional<PresensiModel> findByIdPresensi(Long idPresensi);
}
