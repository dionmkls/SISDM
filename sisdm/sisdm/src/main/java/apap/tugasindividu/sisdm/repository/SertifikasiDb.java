package apap.tugasindividu.sisdm.repository;

import apap.tugasindividu.sisdm.model.KaryawanModel;
import apap.tugasindividu.sisdm.model.SertifikasiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SertifikasiDb extends JpaRepository<SertifikasiModel, Long> {
    Optional<SertifikasiModel> findById(Long idSertifikasi);
}