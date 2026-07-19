<template src="@/modules/contract/setup/HandoverInventoryManagement/HandoverInventoryManagement.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from "@/modules/contract/setup/HandoverCompleteProfile/api/index.js"
import Split from '@/modules/contract/setup/HandoverCompleteProfile/components/split.js'
import QuanLyGiaoNhanHoSo from '@/modules/contract/setup/HandoverCompleteProfile/QuanLyGiaoNhanHoSo.vue'

export default {
    components: {
        breadcrumb,
        QuanLyGiaoNhanHoSo
    },
    data() {
        return {
            target: '.main-wrapper',
            header: {
                title: "QUẢN LÝ GIAO NHẬN HỒ SƠ",
                list: [
                { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
                {
                    name: "QUẢN LÝ GIAO NHẬN HỒ SƠ",
                    link: { name: "Ui.buttons" },
                },
                ],
            },
            enable: {
                capnhattt: false,
                giaophieu: false,
                traphieu: false,
                xacnhan:false,
                themfile: true,
                xoafile: true,
            },
            visible: {
                tab1: false,
                tab2: true,
                capnhattt: false,
                giaophieu: false,
                traphieu: false,
                xacnhan:false,
                hoancong: false,
                themfile: false,
                xoafile: false,
            },
            tableLayoutPanel2: false,
            dsQuyen: [],
            isLoading: false,
            tths_id: 1,
            quytrinh_id: 550,
            hg_id: null,
            dtThaoTac: [],
            HOSO_LC: {
                TRANGTHAI_MOI: 1,
                TRANGTHAI_KSTH: 2,
                TRANGTHAI_HTHS: 3,
                TRANGTHAI_QLHS: 4,
                TRANGTHAI_HT: 6,
                QUYEN_CNHS: 2075,
                QUYEN_LCHS: 2076,
                QUYEN_THEMFILE: 2082,
                QUYEN_THAOTAC: 2083,
                QUYEN_THEMSUA_FILE_DG: 2090,
            },
            nuthc: false,
            nutgp: false,
        };
    },
    mounted() {
        this.init();
        this.jsClose();
    },
    methods: {
        preventSubmit() {
            return false;
        },
        async init() {
            try {
                this.loading(true);
                await this.frmGiaoPhieu_BHS_Load();       
            } catch (e) {
                console.log(e);
                this.$toast.error("Khởi tạo thất bại: " + e.data ? e.data.message : "");
            } finally {
                this.loading(false);
            }
        },
        async frmGiaoPhieu_BHS_Load() {
            try {
                await this.THIETLAP_GIAODIEN();
                await this.load_quyen();
            } catch (e) {
                console.log(e);
                this.$toast.error("Lỗi khi load giao diện: " + e.data ? e.data.message : "");
            }
        },
        async load_quyen() {
            this.dsQuyen = []
            try {
                let response = await api.ur_29002_001_ds_quyen_nguoidung(this.axios);        
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.dsQuyen = response.data.data;        
                }
            } catch (e) {
                console.log(e)
            }
        },
        PHAILAM(CODE) {
            for (let i=0; i<this.dtThaoTac.length; i++) {
                if (this.dtThaoTac[i].code == CODE && this.dtThaoTac[i].enable == "1") {
                    return true;
                }
            }
            return false;
        },
        async NAP_COMB_GIAOPHIEU() {
            try {
                this.$refs.QuanLyGiaoNhanHoSo.visible.luufile = false;
                this.visible.xoafile = false;

                let bosung_filehs = this.PHAILAM("BOSUNG_FILEHS");
                let giaophieu_qltt = this.PHAILAM("GIAOPHIEU_QLTT")
                await this.$refs.QuanLyGiaoNhanHoSo.NAP_COMB_GIAOPHIEU(bosung_filehs, giaophieu_qltt);     
                if (bosung_filehs) {
                    this.$refs.QuanLyGiaoNhanHoSo.visible.luufile = true;
                    this.visible.xoafile = true;
                }
            } catch (e) {
                this.$toast.error("Có lỗi khi lấy danh sách dữ liệu danh mục: " + e);
            }
        },
        async TAB_GIAODIEN() {
            this.enable.themfile = true
            this.enable.xoafile = true
            this.visible.themfile = this.PHAILAM("BOSUNG_FILEHS");
            this.visible.xoafile = this.PHAILAM("BOSUNG_FILEHS");
            this.visible.giaophieu = this.nutgp;
            this.visible.xacnhan = true;
            this.visible.traphieu = true;
            this.visible.hoancong = this.nuthc;
            if (!this.isLoading) {
                await this.$refs.QuanLyGiaoNhanHoSo.TIMKIEM("", 0);
            }
        },
        async sp_lay_ds_huonggiao_theo_quytrinh() {
            let dt_hg = []
            try {
                let params = {
                    "quytrinh_id": this.quytrinh_id,
                    "tths_id": this.tths_id
                }
                let response = await api.ur_29003_000_sp_lay_ds_huonggiao_theo_quytrinh(this.axios, params);  
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    dt_hg = response.data.data
                }
            } catch (e) {
                console.log(e)
            }
            return dt_hg
        },
        async sp_lay_ds_thaotac(huonggiao_id) {
            this.dtThaoTac = []
            try {
                let params = {
                    "quytrinh_id": this.quytrinh_id,
                    "huonggiao_id": huonggiao_id
                }
                let response = await api.ur_29003_000_sp_lay_ds_thaotac(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.dtThaoTac = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
        },
        async THIETLAP_GIAODIEN() {
            if (this.$route.query.tag != undefined && this.$route.query.tag != null && this.$route.query.tag != "") {
                let temps = this.$route.query.tag.split("-")
                this.tths_id = temps.length > 0 ? Number(temps[0]) : 1
                this.quytrinh_id = temps.length > 1 ? Number(temps[1]) : 550
            }

            this.isLoading = true;
            this.$refs.QuanLyGiaoNhanHoSo.init();

            let dt_hg = await this.sp_lay_ds_huonggiao_theo_quytrinh();
            if (dt_hg == null || dt_hg.length == 0) {
                this.$toast.error("Không lấy được thông tin hướng giao");
                return;
            }

            let huonggiao_id = String(dt_hg[0].huonggiao_id)
            let ten_hg = String(dt_hg[0].huonggiao)
            this.header.title += " : " + ten_hg;
            this.hg_id = Number(huonggiao_id);
            await this.sp_lay_ds_thaotac(Number(huonggiao_id));

            await this.NAP_COMB_GIAOPHIEU();

            this.nuthc = this.PHAILAM("HOANCONG_LC");
            this.nutgp = this.PHAILAM("GIAOPHIEU_LC");
            this.TAB_GIAODIEN();
           
            this.isLoading = false;
        },
        async tsbtnLayTT_Click(){          
            await this.$refs.QuanLyGiaoNhanHoSo.TIMKIEM("", 1);
            if (this.nuthc) {
                // NAP_DS_DL_DOITUONG();
            }          
        },
        KIEMTRA_QUYEN(kieu) {
            if (this.PHAILAM("BOSUNG_FILEHS")) {
                if (kieu == 0) {
                    if (!this.dsQuyen.includes(this.HOSO_LC.QUYEN_THEMFILE)) {
                        this.$toast.error("Bạn không có quyền thêm file hồ sơ (QID: " + this.HOSO_LC.QUYEN_THEMFILE + ")");
                        return false;
                    }
                }
                if (kieu == 1) {
                    if (!this.dsQuyen.includes(this.HOSO_LC.QUYEN_THAOTAC)) {
                        this.$toast.error("Bạn không có quyền thao tác trên bộ hồ sơ(QID: " + this.HOSO_LC.QUYEN_THAOTAC + ")");
                        return false;
                    }
                }
            }
            return true;
        },
        tab2_cboTrangThai_Changed(val) {
            if (this.tths_id == 1) {            
                this.enable.xacnhan = false;
                this.enable.traphieu = false;
            } else {
                this.enable.xacnhan = val;
                this.enable.traphieu = val;         
            }
            this.enable.giaophieu = val;

            if (this.$refs.QuanLyGiaoNhanHoSo.trangthai == "2" || this.$refs.QuanLyGiaoNhanHoSo.trangthai == "3") {
                this.enable.themfile = false;
            } else {
                this.enable.themfile = true;
            }
        },
        async tsbtnThemFile_Click() {
            if (!this.enable.themfile) return;
            try {
                this.loading(true)
                this.jsOpen()
                if (!this.KIEMTRA_QUYEN(0)) return;
                if (this.$refs.QuanLyGiaoNhanHoSo.vbohs_id == -1) {
                    this.$toast.error("Bạn chưa chọn bộ hồ sơ để thêm file");
                    return;
                }
                this.enable.themfile = false;
                await this.$refs.QuanLyGiaoNhanHoSo.btnClear_Click()
                await this.$refs.QuanLyGiaoNhanHoSo.NAP_DS_HOPDONGTB(this.$refs.QuanLyGiaoNhanHoSo.vhdkh_id_luoi);
            } catch (e) {
                console.log(e)
                this.jsClose()
            } finally {
                this.loading(false)
            }
        },
        async btnThemFile_Click() {
            if (!this.KIEMTRA_QUYEN(0)) return;
            await this.$refs.QuanLyGiaoNhanHoSo.CAPNHAT_FILE(this.dsQuyen.includes(this.HOSO_LC.QUYEN_THEMSUA_FILE_DG));
        },
        async tsbtnGiaoPhieu_Click() {
            if (!this.enable.giaophieu) return;
            if (!this.KIEMTRA_QUYEN(1)) return;
            await this.$refs.QuanLyGiaoNhanHoSo.GIAOPHIEU();
        },
        async tsbtnTraLai_Click() {
            if (!this.enable.traphieu) return;
            if (!this.KIEMTRA_QUYEN(1)) return;
            await this.$refs.QuanLyGiaoNhanHoSo.TRAPHIEU();
        },
        async tsbtnXacNhan_Click() {
            if (!this.enable.xacnhan) return;
            if (!this.KIEMTRA_QUYEN(1))  return;
            await this.$refs.QuanLyGiaoNhanHoSo.XACNHAN_DS();
        },
        async btnXoaFile_Click() {
            if (!this.enable.xoafile) return;
            if (!this.KIEMTRA_QUYEN(0)) return;
            await this.$refs.QuanLyGiaoNhanHoSo.XOA_FILE(this.dsQuyen.includes(this.HOSO_LC.QUYEN_THEMSUA_FILE_DG));
        },
        closePanelThemfile() {
            this.enable.themfile = true
            this.jsClose()
        },
        jsOpen() {
            Split(['boxLeft','boxRight'], {
                sizes: [70, 30],
                gutterSize: 16,
                onDragEnd: function (sizes) {

                },
            })

            this.clearGutter()
        },
        jsClose() {
            Split(['boxLeft','boxRight'], {
                sizes: [100, 0],
                gutterSize: 0,
                onDragEnd: function (sizes) {

                },
            })

            document.getElementById("boxLeft").style.width = "calc(100% - 0px)"
            this.clearGutter()
        },
        clearGutter() {
            const elements = document.getElementsByClassName("gutter-horizontal");
            while(elements.length > 1) {
                for (let i=0; i<elements.length; i++) {
                    elements[i].parentNode.removeChild(elements[i]);
                }              
            }
        },
        async btnCapNhatTT_Click() {
            await this.$refs.QuanLyGiaoNhanHoSo.btnCapNhatTT_Click()
        }
    },
};

</script>
<style>
    .non-ative a {
        color: #d3d3d3 !important;
    }
    .clblue {
        color: blue !important;
    }
    .clbrown {
        color: brown !important;
    }
    .clred {
        color: red !important;
    }
</style>
