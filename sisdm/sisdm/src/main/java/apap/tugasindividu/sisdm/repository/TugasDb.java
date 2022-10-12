package apap.tugasindividu.sisdm.repository;

import apap.tugasindividu.sisdm.model.SertifikasiModel;
import apap.tugasindividu.sisdm.model.TugasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TugasDb extends JpaRepository<TugasModel, Long> {
    Optional<TugasModel> findByIdTugas(Long idTugas);
}
