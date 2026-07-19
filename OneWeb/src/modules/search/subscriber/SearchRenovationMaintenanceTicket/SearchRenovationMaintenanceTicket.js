
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import API from "../api/SearchRenovationMaintenanceTicket";
import { Query } from "@syncfusion/ej2-data";
import { column_ds_caiTao_baoDuong, column_ds_chiTiet, column_ds_pt_mang } from "./collums"
import DsVatTu from "./PopUps/DsVatTu.vue"
import TraCuuPTM from "./PopUps/TraCuuPTM.vue"
import TraCuuNhanVienTH from "../SearchInstallDetail/popup/TraCuuNhanVienTH.vue"
import moment from "moment";

export default {
    components: { DatePicker, DsVatTu, TraCuuPTM, TraCuuNhanVienTH },
    data() {
        return {
            fields: {
                trangThaiCC: { value: 'ttcc_id', text: 'trangthai_cc' },
                TTVT: { text: 'ten_dv', value: 'donvi_id' }
            },
            enabled: {
                dtpTuNgay: false,
                txtMa_HS_Search: false,
                txtTenPTM: false,
            },
            txtMa_HS_Search: "",
            ds_trangThaiCC: [],
            ttcc_id: 0,
            ds_TTVT: [],
            ttvt_id: 0,
            dtpTuNgay: null,
            grvDanhSach: [],
            grcDSPT: [],
            grvDSPT: [],
            dgvChiTiet: [],
            column_ds_caiTao_baoDuong,
            column_ds_chiTiet,
            column_ds_pt_mang,
            ckMaHS: false,
            ckTenPTM: false,
            ckTuNgay: false,
            vmahscc: "",
            loaitbi_id: 0,
            ds_ptm_id: 0,
            chitietcc_id: 0,
            phieu_id: 0,
            hscc_id: 0,
            txtTenHS: "",
            txtMaHS: "",
            txtTenND: "",
            txtTenPTM: "",
            daeNgayBD: null,
            daeNgayKT: null,
            ds_nhanvienTH: []
        }
    },
    created() {
        this.NAP_CBO_TRANGTHAI_CC();
        this.NAP_CBO_TTVT();
        this.dtpTuNgay = this.$auth.getNgayCapNhat();
    },
    watch: {
        ckMaHS() {
            this.set_control();
        },
        ckTenPTM() {
            this.set_control();
        },
        ckTuNgay() {
            this.set_control();
        }
    },
    methods: {
        onFilterTrangThaiCC(e) {
            var query = new Query();
            query = (e.text.trim() !== '') ? query.where('trangthai_cc', 'contains', e.text, true) : query;
            e.updateData(this.ds_trangThaiCC, query);
        },
        onFilterTTVT(e) {
            var query = new Query();
            query = (e.text.trim() !== '') ? query.where('TEN_DV', 'contains', e.text, true) : query;
            e.updateData(this.ds_TTVT, query);
        },
        async NAP_CBO_TRANGTHAI_CC() {
            try {
                let response = await API.NAP_CBO_TRANGTHAI_CC();
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_trangThaiCC = response.data.data;
                    if (this.ds_trangThaiCC.length) {
                        this.ttcc_id = this.ds_trangThaiCC[0].ttcc_id;
                    }
                }
            } catch (error) {
                console.log(error)
            }
        },
        async NAP_CBO_TTVT() {
            try {
                let response = await API.NAP_CBO_TTVT(this.$auth.getNhanVienID());
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_TTVT = response.data.data;
                    if (this.ds_TTVT.length) {
                        this.ttvt_id = this.ds_TTVT[0].DONVI_ID;
                    }
                }
            } catch (error) {
                console.log(error)
            }
        },
        async tracuu_hoso_cc_v2(vma_hscc, vloaitbi_id, vloai, vtungay) {
            try {
                this.loading(true);
                let response = await API.tracuu_hoso_cc_v2(
                    {
                        vma_hscc,
                        vloaitbi_id,
                        vloai,
                        vtungay
                    }
                );
                this.loading(false);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    return response.data.data;
                }
                return [];
            } catch (error) {
                return [];
                console.log(error)
            }

        },
        async Search() {
            if (this.ckMaHS) {
                this.vmahscc = this.txtMa_HS_Search.trim();
                let ds_hs = await this.tracuu_hoso_cc_v2(this.vmahscc, 0, 1);
                if (ds_hs.length > 0) {
                    this.grvDanhSach = ds_hs;
                    this.grvDanhSach_FocusedRowChanged(0);
                }
                else {
                    this.$toast.error("Không tìm thấy thông tin mã hồ sơ " + this.vmahscc + " !");
                    this.clear_all();
                }

            }
            if (this.ckTenPTM) {
                if (this.loaitbi_id == 0 || this.ds_ptm_id == "")
                    return;
                let ds_hs = await this.tracuu_hoso_cc_v2("", this.loaitbi_id, 2);
                if (ds_hs.length > 0) {
                    this.grvDanhSach = ds_hs;
                    this.grvDanhSach_FocusedRowChanged(0);
                }
                else {
                    this.$toast.error("Không tìm thấy thông tin hồ sơ !");
                    this.clear_all();
                }
            }
            if (this.ckTuNgay) {
                if (!this.dtpTuNgay)
                    return;
                let ds_hs = await this.tracuu_hoso_cc_v2("", 0, 3, this.dtpTuNgay);
                if (ds_hs.length > 0) {
                    this.grvDanhSach = ds_hs;
                    this.grvDanhSach_FocusedRowChanged(0);
                }
                else {
                    this.$toast.error("Không tìm thấy thông tin hồ sơ !");
                    this.clear_all();
                }
            }
        },
        tsbtnTimKiem_Click() {
            this.Search();
        },
        grvDanhSach_FocusedRowChanged(index, item) {
            try {
                if (index < 0) return;
                this.vmahscc = index == 0 ? this.grvDanhSach[0].ma_hs_cc : item.ma_hs_cc;
                this.NAP_THONGTIN_HOSO(this.vmahscc);

            } catch (error) {
                this.$toast.error(error.toString());
            }
        },
        async NAP_THONGTIN_HOSO(vma_hs) {
            try {
                this.ClearGUI();
                let response = await API.NAP_THONGTIN_HOSO({
                    "v_mahscc": vma_hs
                });
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    var rs = response.data.data;
                }
                if (rs && rs.length) {
                    var dataRow = rs[0];
                    this.hscc_id = new Number(dataRow["hscc_id"]);
                    this.txtTenHS = dataRow["ten_hs"].toString();
                    this.txtMaHS = dataRow["ma_hs_cc"].toString();
                    this.txtTenND = dataRow["nguoi_dk"].toString();
                    this.daeNgayBD = moment(dataRow["ngaybd_dk"]).format("DD/MM/YYYY");
                    this.daeNgayKT = moment(dataRow["ngaykt_dk"]).format("DD/MM/YYYY");
                    let ttvt = this.ds_TTVT.find(x => x.donvi_id == dataRow["ttvt_id"])
                    this.ttvt_id = ttvt ? ttvt.donvi_id : 0;
                    await this.HienThiDS_PhieuTH(this.hscc_id);
                    this.gridChitiet_FocusedRowChanged(0);

                    let ttcc = this.ds_trangThaiCC.find(x => x.ttcc_id == dataRow["TTCC_ID"])
                    this.ttcc_id = ttcc ? ttvt.ttcc_id : 0;
                } else {
                    this.$toast.error("Không tìm thấy thông tin mã hồ sơ!");
                    // ClearGUI();
                    this.grcDSPT = [];
                    this.dgvChiTiet = [];
                    this.phieu_id = 0;
                    this.hscc_id = 0;
                    this.chitietcc_id = 0;
                }
            } catch (error) {
                console.log(error);
            }

        },
        async HienThiDS_PhieuTH(hscc_id) {
            try {
                let response = await API.tracuu_phieu_cc(hscc_id)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.dgvChiTiet = response.data.data;
                }

            }
            catch (exp) {
                this.$toast.error(exp.toString());
            }
        },
        gridChitiet_FocusedRowChanged(index, item) {
            try {
                if (!item) {
                    this.phieu_id = this.dgvChiTiet.length ? this.dgvChiTiet[0].phieucc_id : 0;
                } else
                    this.phieu_id = item.phieucc_id;
                this.NAP_DS_PHANTU_MANG();
            }
            catch (error) {
                this.$toast.error(error.toString());
            }
        },
        async NAP_DS_PHANTU_MANG() {
            try {
                let response = await API.NAP_DS_PHANTU_MANG({
                    "vhscc_id": this.hscc_id,
                    "vphieucc_id": this.phieu_id
                })
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.grvDSPT = response.data.data;
                } else {
                    this.grvDSPT = [];
                }
                //if (GHICHU_HC_DSPT.Width > 250)
                //    GHICHU_HC_DSPT.Width = 250;

            }
            catch (error) {
                this.$toast.error(error.toString());
            }
        },
        clear_all() {
            this.ClearGUI();
            this.vmahscc = "";
            this.hscc_id = 0;
            this.phieu_id = 0;
            this.loaitbi_id = 0;
            this.ds_ptm_id = "";
            this.hitietcc_id = 0;
            this.grcDSPT = [];
            this.dgvChiTiet = [];
            this.grcDanhSach = [];
        },
        ClearGUI() {
            this.txtMaHS = "";
            this.txtTenHS = "";
            this.txtTenND = "";
            this.daeNgayBD = "";
            this.daeNgayKT = "";
            this.ttvt_id = 0;
            this.ttcc_id = 0;
        },
        set_control() {
            this.enabled.txtMa_HS_Search = false;
            this.enabled.txtTenPTM = false;
            this.enabled.dtpTuNgay = false;
            if (this.ckMaHS) {
                this.enabled.txtMa_HS_Search = true;
                this.ckTenPTM = false;
                this.ckTuNgay = false;
            }
            if (this.ckTenPTM) {
                this.enabled.txtTenPTM = true;
                this.ckMaHS = false;
                this.ckTuNgay = false;
            }
            if (this.ckTuNgay) {
                this.enabled.dtpTuNgay = true;
                this.ckTenPTM = false;
                this.ckMaHS = false;
            }
        },
        tsbtnNhapMoi_Click() {
            this.clear_all();
        },
        tsbnVatTu_Click() {
            this.$refs.dsVatTu.showDialog();
        },
        searchPTM() {
            this.$refs.traCuuPTM.showDialog();
        },
        async pt_mang_rowDoubleClicked(i, obj) {
            console.log(obj);
            var data = {
                "phieu_id": this.phieu_id,
                "phieu_bh_id": 0,
                "phieu_kn_id": 0,
                "baohong_id": 0,
                "hdtb_id": 0,
                "chitietcc_id": obj.chitietcc_id,
                "giaophieu_vt_id": 0,
                "nhanvien_id": 0
            }
            this.ds_nhanvienTH = [];
            await API.sp_lay_ds_nhanvien_thuchien(data).then(response => {
                if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                    this.ds_nhanvienTH = response.data.data;
                }
            });
            this.$refs.TraCuuNhanVienTH.show();
        }
    }
}