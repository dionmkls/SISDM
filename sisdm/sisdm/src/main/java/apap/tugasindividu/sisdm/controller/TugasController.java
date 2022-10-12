package apap.tugasindividu.sisdm.controller;

import apap.tugasindividu.sisdm.model.*;
import apap.tugasindividu.sisdm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TugasController {
    @Autowired
    private TugasService tugasService;

    @Autowired
    private SertifikasiService sertifikasiService;

    @Autowired
    private KaryawanService karyawanService;

    //    Add Tugas
    @GetMapping("/tambah-tugas")
    public String addTugasFormPage(Model model) {
        TugasModel tugas = new TugasModel();
        model.addAttribute("tugas", tugas);

        return "form-add-tugas";
    }

    @PostMapping(value = "/tambah-tugas")
    public String addTugasSubmitPage(@ModelAttribute TugasModel tugas, Model model) {
        tugasService.addTugas(tugas);
        model.addAttribute("tugas", tugas);

        return "add-tugas";
    }

    //    Filter Tugas By Karyawan and Status
    @GetMapping("/filter-tugas")
    public String filterTugasFormPage(Model model) {
        List<TugasModel> listTugas = tugasService.getListTugas();
        List<KaryawanModel> listKaryawan = karyawanService.getListKaryawan();

        model.addAttribute("listKaryawan", listKaryawan);
        model.addAttribute("listTugas", listTugas);

        return "filter-tugas";
    }

    @GetMapping(value = "/filter-tugas", params = {"filterKaryawan"})
    public String filterTugasSubmitPage(
            @RequestParam(value = "id-karyawan", required = true) Long id_karyawan,
            @RequestParam(value = "status", required = true) Integer status,
            Model model) {
        List<KaryawanModel> listKaryawan = karyawanService.getListKaryawan();
        KaryawanModel karyawan = karyawanService.getKaryawanByIdKaryawan(id_karyawan);

        List<TugasModel> listTugas = tugasService.getListTugas();
        List<TugasModel> listTugasBaru = new ArrayList<>();

        for (int ii = 0; ii < listTugas.size(); ii++) {
            KaryawanModel karyawanTugas = listTugas.get(ii).getPresensi().getKaryawan();
            if ((listTugas.get(ii).getStatus() == status) && (karyawanTugas.getIdKaryawan()) == karyawan.getIdKaryawan()) {
                listTugasBaru.add(listTugas.get(ii));
            }
        }
        model.addAttribute("listKaryawan", listKaryawan);
        model.addAttribute("listTugas", listTugasBaru);

        return "filter-tugas";
    }
}