package vn.vnpt.vnptit.ecms.dto.idc;

public class CauHinhPhanCungThietBiDtoIn {
    private Number id;
    private Number idc_id;
    private Number thietbi_id;
    //cpu
    private Number sl_khecpu;
    private Number sl_corecpu;
    private Number sl_threadcpu;
    private String model_cpu;
    private Number sl_cpu;
    //ram
    private Number sl_kheram;
    private Number sl_ram;
    private Number dl_ram;
    //gpu
    private Number sl_khegpu;
    private Number sl_gpu;
    //fan
    private Number sl_khefan;
    private Number sl_fan;
    private Number sl_khepci;
    private Number sl_pci;
    //ổ cứng
    private Number sl_khessd;
    private Number sl_ssd;
    private Number sl_khehdd;
    private Number sl_hdd;
    private Number sl_khenvme;
    private Number sl_nvme;
    //blade
    private Number sl_kheblade;
    //server, blade
    private String ds_cpu;
    private String ds_gpu;
    private String ds_ocung;
    private String ds_ram;
    private String ds_blade;
    // network
    private Number tocdo_cpu_mang;
    private Number dl_ram_mang;
    private Number dl_hdd_mang;
    private Number dl_ssd_mang;
    private Number sl_fan_mang;
    // tủ đĩa
    private String ds_dkh;
    private String ds_khd;
    private Number type;

    private String dsxoa_cpu;
    private String dsxoa_ram;
    private String dsxoa_gpu;
    private String dsxoa_ocung;
    private String dsxoa_blade;
    private String dsxoa_khd;
    private String dsxoa_dkh;

    //isBoDieuKhien
    private Number dkh_dl_ram;
    private Number dkh_sl_corecpu;
    private Number dkh_dl_cache;
    //isKhayDia
    private Number khd_sl_khessd;
    private Number khd_sl_ssd;
    private Number khd_sl_khehdd;
    private Number khd_sl_hdd;
    private Number khd_sl_khenvme;
    private Number khd_sl_nvme;
    private String khd_ds_ocung;
    private String khd_dsxoa_ocung;

    public Number getDkh_dl_ram() {
        return dkh_dl_ram;
    }

    public void setDkh_dl_ram(Number dkh_dl_ram) {
        this.dkh_dl_ram = dkh_dl_ram;
    }

    public Number getDkh_sl_corecpu() {
        return dkh_sl_corecpu;
    }

    public void setDkh_sl_corecpu(Number dkh_sl_corecpu) {
        this.dkh_sl_corecpu = dkh_sl_corecpu;
    }

    public Number getDkh_dl_cache() {
        return dkh_dl_cache;
    }

    public void setDkh_dl_cache(Number dkh_dl_cache) {
        this.dkh_dl_cache = dkh_dl_cache;
    }

    public Number getKhd_sl_khessd() {
        return khd_sl_khessd;
    }

    public void setKhd_sl_khessd(Number khd_sl_khessd) {
        this.khd_sl_khessd = khd_sl_khessd;
    }

    public Number getKhd_sl_ssd() {
        return khd_sl_ssd;
    }

    public void setKhd_sl_ssd(Number khd_sl_ssd) {
        this.khd_sl_ssd = khd_sl_ssd;
    }

    public Number getKhd_sl_khehdd() {
        return khd_sl_khehdd;
    }

    public void setKhd_sl_khehdd(Number khd_sl_khehdd) {
        this.khd_sl_khehdd = khd_sl_khehdd;
    }

    public Number getKhd_sl_hdd() {
        return khd_sl_hdd;
    }

    public void setKhd_sl_hdd(Number khd_sl_hdd) {
        this.khd_sl_hdd = khd_sl_hdd;
    }

    public Number getKhd_sl_khenvme() {
        return khd_sl_khenvme;
    }

    public void setKhd_sl_khenvme(Number khd_sl_khenvme) {
        this.khd_sl_khenvme = khd_sl_khenvme;
    }

    public Number getKhd_sl_nvme() {
        return khd_sl_nvme;
    }

    public void setKhd_sl_nvme(Number khd_sl_nvme) {
        this.khd_sl_nvme = khd_sl_nvme;
    }

    public String getKhd_ds_ocung() {
        return khd_ds_ocung;
    }

    public void setKhd_ds_ocung(String khd_ds_ocung) {
        this.khd_ds_ocung = khd_ds_ocung;
    }

    public String getKhd_dsxoa_ocung() {
        return khd_dsxoa_ocung;
    }

    public void setKhd_dsxoa_ocung(String khd_dsxoa_ocung) {
        this.khd_dsxoa_ocung = khd_dsxoa_ocung;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Number getThietbi_id() {
        return thietbi_id;
    }

    public void setThietbi_id(Number thietbi_id) {
        this.thietbi_id = thietbi_id;
    }

    public Number getSl_khecpu() {
        return sl_khecpu;
    }

    public void setSl_khecpu(Number sl_khecpu) {
        this.sl_khecpu = sl_khecpu;
    }

    public Number getSl_corecpu() {
        return sl_corecpu;
    }

    public void setSl_corecpu(Number sl_corecpu) {
        this.sl_corecpu = sl_corecpu;
    }

    public Number getSl_threadcpu() {
        return sl_threadcpu;
    }

    public void setSl_threadcpu(Number sl_threadcpu) {
        this.sl_threadcpu = sl_threadcpu;
    }

    public String getModel_cpu() {
        return model_cpu;
    }

    public void setModel_cpu(String model_cpu) {
        this.model_cpu = model_cpu;
    }

    public Number getSl_cpu() {
        return sl_cpu;
    }

    public void setSl_cpu(Number sl_cpu) {
        this.sl_cpu = sl_cpu;
    }

    public Number getSl_kheram() {
        return sl_kheram;
    }

    public void setSl_kheram(Number sl_kheram) {
        this.sl_kheram = sl_kheram;
    }

    public Number getSl_ram() {
        return sl_ram;
    }

    public void setSl_ram(Number sl_ram) {
        this.sl_ram = sl_ram;
    }

    public Number getDl_ram() {
        return dl_ram;
    }

    public void setDl_ram(Number dl_ram) {
        this.dl_ram = dl_ram;
    }

    public Number getSl_khegpu() {
        return sl_khegpu;
    }

    public void setSl_khegpu(Number sl_khegpu) {
        this.sl_khegpu = sl_khegpu;
    }

    public Number getSl_gpu() {
        return sl_gpu;
    }

    public void setSl_gpu(Number sl_gpu) {
        this.sl_gpu = sl_gpu;
    }

    public Number getSl_khefan() {
        return sl_khefan;
    }

    public void setSl_khefan(Number sl_khefan) {
        this.sl_khefan = sl_khefan;
    }

    public Number getSl_fan() {
        return sl_fan;
    }

    public void setSl_fan(Number sl_fan) {
        this.sl_fan = sl_fan;
    }

    public Number getSl_khepci() {
        return sl_khepci;
    }

    public void setSl_khepci(Number sl_khepci) {
        this.sl_khepci = sl_khepci;
    }

    public Number getSl_pci() {
        return sl_pci;
    }

    public void setSl_pci(Number sl_pci) {
        this.sl_pci = sl_pci;
    }

    public Number getSl_khessd() {
        return sl_khessd;
    }

    public void setSl_khessd(Number sl_khessd) {
        this.sl_khessd = sl_khessd;
    }

    public Number getSl_ssd() {
        return sl_ssd;
    }

    public void setSl_ssd(Number sl_ssd) {
        this.sl_ssd = sl_ssd;
    }

    public Number getSl_khehdd() {
        return sl_khehdd;
    }

    public void setSl_khehdd(Number sl_khehdd) {
        this.sl_khehdd = sl_khehdd;
    }

    public Number getSl_hdd() {
        return sl_hdd;
    }

    public void setSl_hdd(Number sl_hdd) {
        this.sl_hdd = sl_hdd;
    }

    public Number getSl_khenvme() {
        return sl_khenvme;
    }

    public void setSl_khenvme(Number sl_khenvme) {
        this.sl_khenvme = sl_khenvme;
    }

    public Number getSl_nvme() {
        return sl_nvme;
    }

    public void setSl_nvme(Number sl_nvme) {
        this.sl_nvme = sl_nvme;
    }

    public Number getSl_kheblade() {
        return sl_kheblade;
    }

    public void setSl_kheblade(Number sl_kheblade) {
        this.sl_kheblade = sl_kheblade;
    }

    public String getDs_cpu() {
        return ds_cpu;
    }

    public void setDs_cpu(String ds_cpu) {
        this.ds_cpu = ds_cpu;
    }

    public String getDs_gpu() {
        return ds_gpu;
    }

    public void setDs_gpu(String ds_gpu) {
        this.ds_gpu = ds_gpu;
    }

    public String getDs_ocung() {
        return ds_ocung;
    }

    public void setDs_ocung(String ds_ocung) {
        this.ds_ocung = ds_ocung;
    }

    public String getDs_ram() {
        return ds_ram;
    }

    public void setDs_ram(String ds_ram) {
        this.ds_ram = ds_ram;
    }

    public String getDs_blade() {
        return ds_blade;
    }

    public void setDs_blade(String ds_blade) {
        this.ds_blade = ds_blade;
    }

    public Number getTocdo_cpu_mang() {
        return tocdo_cpu_mang;
    }

    public void setTocdo_cpu_mang(Number tocdo_cpu_mang) {
        this.tocdo_cpu_mang = tocdo_cpu_mang;
    }

    public Number getDl_ram_mang() {
        return dl_ram_mang;
    }

    public void setDl_ram_mang(Number dl_ram_mang) {
        this.dl_ram_mang = dl_ram_mang;
    }

    public Number getDl_hdd_mang() {
        return dl_hdd_mang;
    }

    public void setDl_hdd_mang(Number dl_hdd_mang) {
        this.dl_hdd_mang = dl_hdd_mang;
    }

    public Number getDl_ssd_mang() {
        return dl_ssd_mang;
    }

    public void setDl_ssd_mang(Number dl_ssd_mang) {
        this.dl_ssd_mang = dl_ssd_mang;
    }

    public Number getSl_fan_mang() {
        return sl_fan_mang;
    }

    public void setSl_fan_mang(Number sl_fan_mang) {
        this.sl_fan_mang = sl_fan_mang;
    }

    public String getDs_dkh() {
        return ds_dkh;
    }

    public void setDs_dkh(String ds_dkh) {
        this.ds_dkh = ds_dkh;
    }

    public String getDs_khd() {
        return ds_khd;
    }

    public void setDs_khd(String ds_khd) {
        this.ds_khd = ds_khd;
    }

    public Number getType() {
        return type;
    }

    public void setType(Number type) {
        this.type = type;
    }

    public String getDsxoa_cpu() {
        return dsxoa_cpu;
    }

    public void setDsxoa_cpu(String dsxoa_cpu) {
        this.dsxoa_cpu = dsxoa_cpu;
    }

    public String getDsxoa_ram() {
        return dsxoa_ram;
    }

    public void setDsxoa_ram(String dsxoa_ram) {
        this.dsxoa_ram = dsxoa_ram;
    }

    public String getDsxoa_gpu() {
        return dsxoa_gpu;
    }

    public void setDsxoa_gpu(String dsxoa_gpu) {
        this.dsxoa_gpu = dsxoa_gpu;
    }

    public String getDsxoa_ocung() {
        return dsxoa_ocung;
    }

    public void setDsxoa_ocung(String dsxoa_ocung) {
        this.dsxoa_ocung = dsxoa_ocung;
    }

    public String getDsxoa_blade() {
        return dsxoa_blade;
    }

    public void setDsxoa_blade(String dsxoa_blade) {
        this.dsxoa_blade = dsxoa_blade;
    }

    public String getDsxoa_khd() {
        return dsxoa_khd;
    }

    public void setDsxoa_khd(String dsxoa_khd) {
        this.dsxoa_khd = dsxoa_khd;
    }

    public String getDsxoa_dkh() {
        return dsxoa_dkh;
    }

    public void setDsxoa_dkh(String dsxoa_dkh) {
        this.dsxoa_dkh = dsxoa_dkh;
    }

    public Number getIdc_id() {
        return idc_id;
    }

    public void setIdc_id(Number idc_id) {
        this.idc_id = idc_id;
    }
}
