<template src="./HandoverCompleteProfile.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from "./api/index.js";
import '@/assets/vendor/jquery/split'
import Split from './components/split.js/dist/split.min.js';
import QuanLyLoHoSo from './QuanLyLoHoSo.vue'
import QuanLyGiaoNhanHoSo from './QuanLyGiaoNhanHoSo.vue'

export default {
    components: {
        breadcrumb,
        QuanLyLoHoSo,
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
                nhapmoi: false,
                capnhat: true,
                huybo: true,
                xoaphieu: false,
                giaophieu: false,
                traphieu: false,
                xacnhan:false,
                hoancong: false,
                themfile: true,
                xoafile: true,
            },
            visible: {
                tab1: true,
                tab2: true,
                nhapmoi: false,
                capnhat: false,
                huybo: false,
                xoaphieu: false,
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
            tths_id: 2,
            quytrinh_id: 550,
            hg_id: null,
            tabIndex: 2,
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

                await this.$refs.QuanLyLoHoSo.NAP_COMB_GIAOPHIEU();

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
        async onChangeTab(tabIndex) {
            this.tabIndex = tabIndex;
            await this.TAB_GIAODIEN(tabIndex);
        },
        async TAB_GIAODIEN(tabIndex) {
            if (tabIndex == 2) {
                this.jsClose()
                this.enable.themfile = true;
                this.visible.themfile = this.PHAILAM("BOSUNG_FILEHS");
                this.visible.xoafile = this.PHAILAM("BOSUNG_FILEHS");  
                this.visible.giaophieu = this.nutgp;
                this.visible.xacnhan = true;
                this.visible.traphieu = true;
                this.visible.nhapmoi = false;
                this.visible.capnhat = false;
                this.visible.xoaphieu = false;
                this.visible.huybo = false;
                this.visible.hoancong = this.nuthc;
                if (!this.isLoading) {
                   await this.$refs.QuanLyGiaoNhanHoSo.TIMKIEM("", 0);
                }
            } else if (tabIndex == 1) {
                this.enable.themfile = false;
                this.visible.themfile = this.PHAILAM("BOSUNG_FILEHS");
                this.visible.xoafile = this.PHAILAM("BOSUNG_FILEHS");
                this.visible.giaophieu = false;
                this.visible.hoancong = false;
                this.visible.xacnhan = false;
                this.visible.traphieu = false;
                this.visible.nhapmoi = true;
                if (this.enable.nhapmoi == false) {
                    this.enable.nhapmoi = true;
                }
                if (!this.isLoading) {
                    await this.$refs.QuanLyLoHoSo.NAP_DS_LOHS(0);
                }
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
                this.tths_id = temps.length > 0 ? Number(temps[0]) : 2
                this.quytrinh_id = temps.length > 1 ? Number(temps[1]) : 550
            }

            this.isLoading = true;
            this.$refs.QuanLyLoHoSo.init();
            this.$refs.QuanLyGiaoNhanHoSo.init();

            let dt_hg = await this.sp_lay_ds_huonggiao_theo_quytrinh();
            if (dt_hg == null || dt_hg.length == 0) {
                this.$toast.error("Không lấy được thông tin hướng giao");

                Split(['#boxLeft1','#boxRight1'], {
                    sizes: [30, 70],
                    gutterSize: 16,
                    onDragEnd: function (sizes) {

                    },
                })
                Split(['#boxTop', '#boxBottom'], {
                    sizes: [50, 50],
                    direction: 'vertical',

                });
                jQuery('#tabs a')[1].click();

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
            this.tabIndex = 2;
            this.TAB_GIAODIEN(this.tabIndex);

            if (!this.PHAILAM("HT_GIAOPHIEU")) {
                if (this.PHAILAM("HT_LOHS")) {
                    jQuery('#tabs a')[0].click();
                }
                jQuery('#tabs a')[1].hidden = true;
                this.visible.tab2 = false;
            }

            if (!this.PHAILAM("HT_LOHS")) {
                jQuery('#tabs a')[0].hidden = true;
                this.visible.tab1 = false;
            }
           
            this.isLoading = false;
        },
        async tsbtnLayTT_Click(){          
            if (this.tabIndex == 2) {
                await this.$refs.QuanLyGiaoNhanHoSo.TIMKIEM("", 1);
                if (this.nuthc) {
                    // NAP_DS_DL_DOITUONG();
                }          
            } else if (this.tabIndex == 1) {
                await this.$refs.QuanLyLoHoSo.NAP_DS_LOHS(1);
            }
        },
        tab1_onLoadDS(hasInfo) {
            if (!hasInfo) {
                this.XOA_FORM();
                this.KHOAMO_FORM(false);
                this.enable.nhapmoi = true;
                this.visible.xoaphieu = false;
            } else {
                this.XOA_FORM();
                this.KHOAMO_FORM(false);
                this.visible.xoaphieu = false;
            }    
        },
        XOA_FORM() {
            this.$refs.QuanLyLoHoSo.XOA_FORM();
            this.visible.capnhat = false;
            this.visible.huybo = false;
            this.enable.xoaphieu = false;
        },
        KHOAMO_FORM(mo) {
            this.$refs.QuanLyLoHoSo.KHOAMO_FORM(mo);
        },
        tab1_onFocusRow() {
            this.visible.capnhat = true;
            this.visible.huybo = true;
            this.enable.xoaphieu = true;
            this.visible.xoaphieu = true;
            this.enable.nhapmoi = true;
        },
        tab1_btnGo_Click() {
            if (!this.enable.nhapmoi) {
                this.$toast.error("Bạn đang nhập mới không thể gán/gỡ lô");
                return;
            }
            this.$refs.QuanLyLoHoSo.GAN_GO_LO_HS(2);
        },
        tab1_btnGan_Click() {
            if (!this.enable.nhapmoi) {
                this.$toast.error("Bạn đang nhập mới không thể gán/gỡ lô");
                return;
            }
            this.$refs.QuanLyLoHoSo.GAN_GO_LO_HS(1);
        },
        btnNhapMoi_Click() {
            if (!this.enable.nhapmoi) return;
            this.THAOTAC_NUT(1);
        },
        btnHuyBo_Click() {
            if (!this.enable.huybo) return;
            this.THAOTAC_NUT(3);
        },
        THAOTAC_NUT(kieu) {
            if (kieu == 1) {
                this.XOA_FORM();
                this.KHOAMO_FORM(true);
                this.visible.huybo = true;
                this.visible.capnhat = true;
                this.enable.nhapmoi = false;
                if (this.tabIndex == 2) {
                    $("#ma_kh_gd").focus();
                }
            } else if (kieu == 3) {
                this.XOA_FORM();
                this.visible.capnhat = false;
                this.visible.huybo = false;
                this.visible.xoaphieu = false;
                this.enable.nhapmoi = true;
            }
        },
        async btnCapNhat_Click() {
            if (!this.enable.capnhat) return;
            try {
                this.loading(true)
                if (!this.KIEMTRA_QUYEN(1)) return;
                await this.$refs.QuanLyLoHoSo.CAPNHAT(this.enable.nhapmoi, this.hg_id);
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
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
        async btnXoaPhieu_Click() {
            if (!this.enable.xoaphieu) return;
            try {
                if (!this.KIEMTRA_QUYEN(1)) return;
                await this.$refs.QuanLyLoHoSo.XOA_BOHOSO(this.hg_id);
            } catch (e) {
                console.log(e)
            }
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

            // if (this.$refs.QuanLyGiaoNhanHoSo.trangthai == "2" || this.$refs.QuanLyGiaoNhanHoSo.trangthai == "3") {
            //     this.enable.themfile = false;
            // } else {
            //     this.enable.themfile = true;
            // }
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
            Split(['#boxLeft','#boxRight'], {
                sizes: [70, 30],
                gutterSize: 16,
                onDragEnd: function (sizes) {

                },
            })

            this.clearGutter()
        },
        jsClose() {
            Split(['#boxLeft','#boxRight'], {
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
    },
};

window.addEventListener("load", function() {
    Split(['#boxLeft1','#boxRight1'], {
        sizes: [30, 70],
        gutterSize: 16,
        onDragEnd: function (sizes) {

        },
    })
    Split(['#boxTop', '#boxBottom'], {
        sizes: [50, 50],
        direction: 'vertical',

    });
    jQuery('#tabs a')[1].click();
});
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
