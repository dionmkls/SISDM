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
public class PresensiController {
    @Autowired
    private KaryawanService karyawanService;

    @Autowired
    private PresensiService presensiService;

    @Autowired
    private TugasService tugasService;

    @Autowired
    private SertifikasiService sertifikasiService;

    @Autowired
    private SertifikasiKaryawanService sertifikasiKaryawanService;

    //    View All Presensi
    @GetMapping("presensi")
    public String listPresensi(Model model) {
        List<PresensiModel> listPresensi = presensiService.getListPresensi();
        model.addAttribute("listPresensi", listPresensi);

        return "viewall-presensi";
    }

    //    Add Presensi
    @GetMapping("/presensi/tambah")
    public String addPresensiFormPage(Model model) {
        PresensiModel presensi = new PresensiModel();

        //        Existing
        List<KaryawanModel> listKaryawan = karyawanService.getListKaryawan();
        List<TugasModel> listTugas = tugasService.getListTugas();

        //        New
        List<TugasModel> listTugasNew = new ArrayList<>();

        presensi.setListTugas(listTugasNew);
        presensi.getListTugas().add(new TugasModel());
        presensi.setKaryawan(new KaryawanModel());

        model.addAttribute("presensi", presensi);
        model.addAttribute("listKaryawanExisting", listKaryawan);
        model.addAttribute("listTugasExisting", listTugas);

        return "form-add-presensi";
    }

    @PostMapping(value = "/presensi/tambah", params = {"save"})
    public String addPresensiSubmitPage(@ModelAttribute PresensiModel presensi, Model model) {
        List <TugasModel> listTugas =  presensi.getListTugas();

        if (listTugas == null) {
            presensi.setListTugas(new ArrayList<>());
        }

        KaryawanModel karyawan = karyawanService.getKaryawanByIdKaryawan(presensi.getKaryawan().getIdKaryawan());
        presensi.setKaryawan(karyawan);

        presensiService.addPresensi(presensi);

        for (int i = 0; i < listTugas.size(); i++) {
            TugasModel tugas = tugasService.getTugasByIdTugas(listTugas.get(i).getIdTugas());
            tugas.setPresensi(presensi);
            tugas.setStatus(listTugas.get(i).getStatus());
            tugasService.updateTugas(tugas);
        }
        model.addAttribute("presensi", presensi);
        return "add-presensi";
    }

    //    Add Presensi (add row)
    @PostMapping(value = "/presensi/tambah", params = {"addRowTugas"})
    private String addRowTugasMultiple(
            @ModelAttribute PresensiModel presensi,
            Model model
    ) {
        if (presensi.getListTugas() == null || presensi.getListTugas().size() == 0) {
            presensi.setListTugas(new ArrayList<>());
        }
        presensi.getListTugas().add(new TugasModel());
        List<TugasModel> listTugas = tugasService.getListTugas();
        List<KaryawanModel> listKaryawan = karyawanService.getListKaryawan();

        model.addAttribute("presensi", presensi);
        model.addAttribute("listTugasExisting", listTugas);
        model.addAttribute("listKaryawanExisting", listKaryawan);

        return "form-add-presensi";
    }

    @PostMapping(value = "/presensi/tambah", params = {"deleteRowTugas"})
    private String deleteRowTugasMultiple(
            @ModelAttribute PresensiModel presensi,
            @RequestParam("deleteRowTugas") Integer row,
            Model model
    ) {
        final Integer rowId = Integer.valueOf(row);
        presensi.getListTugas().remove(rowId.intValue());

        List<TugasModel> listTugas = tugasService.getListTugas();
        List<KaryawanModel> listKaryawan = karyawanService.getListKaryawan();

        model.addAttribute("presensi", presensi);
        model.addAttribute("listTugasExisting", listTugas);
        model.addAttribute("listKaryawanExisting", listKaryawan);

        return "form-add-karyawan";
    }

    //    Detail Presensi
    @GetMapping("presensi/{idPresensi}")
    public String viewDetailPresensiPage(@PathVariable Long idPresensi, Model model) {
        PresensiModel presensi = presensiService.getPresensiByIdPresensi(idPresensi);

        List<TugasModel> listTugas = presensi.getListTugas();

        model.addAttribute("listTugas", listTugas);
        model.addAttribute("presensi", presensi);

        return "view-presensi";
    }
}