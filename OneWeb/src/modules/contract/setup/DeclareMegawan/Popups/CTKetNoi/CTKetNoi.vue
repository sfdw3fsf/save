<template src="./CTKetNoi.html"></template>
<script>
import api from "./CTKetNoi.js";
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import columns from "./columns.js";
import { Query } from '@syncfusion/ej2-data'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'

export default {
    name:'CTKetNoi',
    props:{
        Tag: {
            type: String,
            default: null
        },
        vma_tb: {
            type: String,
            default: ""
        },
        vhuong_kn: {
            type: String,
            default: ""
        },
        vhdtb_id: {
            type: Number,
            default: 0
        },
    },
    components:{
        DataGridCustom,
        SearchAccount
    },
    data() {
        return {
            ...columns,
            enable: {
                btnTimKiemKH: true,
                txtMaKH: true,
            },
            vma_tk: "",
            vkieu: 0,
            vloai: 0,
            vid: 0,
            ds_router: [],
            router_id_chinh: null,
            router_id_phu: null,
            ds_loaicap: [],
            loaicap_id_chinh: null,
            loaicap_id_phu: null,   
            dt_sc: [],
            dt_sp: [],
            txtMaKH: "",
            txtSlotPE_Chinh: "",
            txtPortPE_Chinh: "",
            txtDSLAMWan_Chinh: "",
            txtTramTB_Chinh: "",
            txtTenTbi_Chinh: "",
            txtSvLan_Chinh: "",
            txtCvLan_Chinh: "",
            txtVRF_Chinh: "",
            txtPort_Chinh: "",
            txtRoute_Chinh: "",
            txtLink_Chinh: "",
            txtSerial_Chinh: "",
            txtLAN_IP_Chinh: "",
            txtWAN_IP_Chinh: "",
            txtHopCapNgon_Chinh: "",
            txtTuCapGoc_Chinh: "",
            txtTuCapNgon_Chinh: "",
            txtTuyenCap_Chinh: "",
            txtDoiCapGoc_Chinh: "",
            txtDoiCapNgon_Chinh: "",
            txtLienTu_Chinh: "",
            txtCapGoc_Chinh: "",
            txtCapNgon_Chinh: "",
            txtSoMetDay_Chinh: "",
            txtSlotPE_Phu: "",
            txtPort_Phu: "",
            txtDSLAMWan_Phu: "",
            txtTramTB_Phu: "",
            txtTenTbi_Phu: "",
            txtSvLan_Phu: "",
            txtCvLan_Phu: "",
            txtVRF_Phu: "",
            txtPortPE_Phu: "",
            txtRoute_Phu: "",
            txtLink_Phu: "",
            txtSerial_Phu: "",
            txtLAN_IP_Phu: "",
            txtWAN_IP_Phu: "",
            txtHopCapNgon_Phu: "",
            txtTuCapGoc_Phu: "",
            txtTuCapNgon_Phu: "",
            txtTuyenCap_Phu: "",
            txtDoiCapGoc_Phu: "",
            txtDoiCapNgon_Phu: "",
            txtLienTu_Phu: "",
            txtCapGoc_Phu: "",
            txtCapNgon_Phu: "",
            txtSoMetDay_Phu: "",

        }
    },
    methods: {
        refresh() {
            this.txtMaKH = ""
            this.txtSlotPE_Chinh = ""
            this.txtPortPE_Chinh = ""
            this.txtDSLAMWan_Chinh = ""
            this.txtTramTB_Chinh = ""
            this.txtTenTbi_Chinh = ""
            this.txtSvLan_Chinh = ""
            this.txtCvLan_Chinh = ""
            this.txtVRF_Chinh = ""
            this.txtPort_Chinh = ""
            this.txtRoute_Chinh = ""
            this.txtLink_Chinh = ""
            this.txtSerial_Chinh = ""
            this.txtLAN_IP_Chinh = ""
            this.txtWAN_IP_Chinh = ""
            this.txtHopCapNgon_Chinh = ""
            this.txtTuCapGoc_Chinh = ""
            this.txtTuCapNgon_Chinh = ""
            this.txtTuyenCap_Chinh = ""
            this.txtDoiCapGoc_Chinh = ""
            this.txtDoiCapNgon_Chinh = ""
            this.txtLienTu_Chinh = ""
            this.txtCapGoc_Chinh = ""
            this.txtCapNgon_Chinh = ""
            this.txtSoMetDay_Chinh = ""
            this.txtSlotPE_Phu = ""
            this.txtPortPE_Phu = ""
            this.txtDSLAMWan_Phu = ""
            this.txtTramTB_Phu = ""
            this.txtTenTbi_Phu = ""
            this.txtSvLan_Phu = ""
            this.txtCvLan_Phu = ""
            this.txtVRF_Phu = ""
            this.txtPort_Phu = ""
            this.txtRoute_Phu = ""
            this.txtLink_Phu = ""
            this.txtSerial_Phu = ""
            this.txtLAN_IP_Phu = ""
            this.txtWAN_IP_Phu = ""
            this.txtHopCapNgon_Phu = ""
            this.txtTuCapGoc_Phu = ""
            this.txtTuCapNgon_Phu = ""
            this.txtTuyenCap_Phu = ""
            this.txtDoiCapGoc_Phu = ""
            this.txtDoiCapNgon_Phu = ""
            this.txtLienTu_Phu = ""
            this.txtCapGoc_Phu = ""
            this.txtCapNgon_Phu = ""
            this.txtSoMetDay_Phu = "" 
            this.router_id_chinh = null
            this.router_id_phu = null
            this.loaicap_id_chinh = null
            this.loaicap_id_phu = null  
        },
        async openDialog(vma_tk) { 
            this.refresh()
            try {
                this.loading(true)
                this.vma_tk = vma_tk
                await this.frmCTKetNoi_Load()
                this.$refs.dlg_ctketnoi.show()
            } catch (ex) {
                this.$toast.error(ex)
            } finally {
                this.loading(false)
            }         
        },
        async frmCTKetNoi_Load() {
            try {
                let str = "";
                if (this.Tag != null) {
                    if (this.Tag != "") {
                        str = this.Tag;
                        this.enable.btnTimKiemKH = (str == "2");
                        this.enable.txtMaKH = (str == "2");
                        this.vkieu = Number(str);
                    } else {
                        this.enable.btnTimKiemKH = false;
                        this.enable.txtMaKH = false;
                        this.vkieu = 1;
                    }
                } else {
                    this.enable.btnTimKiemKH = false;
                    this.enable.txtMaKH = false;
                    this.vkieu = 1;
                }

                this.ds_router = await this.lay_ds_router()
                if (this.ds_router.length > 0) {
                    this.router_id_chinh = this.ds_router[0].pe_id
                    this.router_id_phu = this.ds_router[0].pe_id
                }

                this.ds_loaicap = await this.lay_ds_loaicap()
                if (this.ds_loaicap.length > 0) {
                    this.loaicap_id_chinh = this.ds_loaicap[0].loaicap_id
                    this.loaicap_id_phu = this.ds_loaicap[0].loaicap_id
                }

                await this.HTDS_SoChinh();
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        async tsbtnLayTT_Click() {
            try {
                this.loading(true)
                await this.HTDS_SoChinh()
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            } finally {
                this.loading(false)
            }
        },
        async HTDS_SoChinh() {
            try {
                this.dt_sc = []
                if (this.txtMaKH.trim() != "") {
                    this.vma_tk = this.txtMaKH.trim();
                    this.vloai = 1;
                    this.dt_sc = await this.LAY_TS_MGWAN_KETNOI(this.vma_tk, this.vkieu, this.vloai, this.vid, this.vhuong_kn)
                } else {
                    this.vloai = 1;
                    this.dt_sc = await this.LAY_TS_MGWAN_KETNOI(this.vma_tk, this.vkieu, this.vloai, this.vid, this.vhuong_kn)
                }
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        async LAY_TS_MGWAN_KETNOI(ma_tk, kieu, loai, id, huong_kn) {
            let res = []
            try {
                let params = {
                    "ma_tk": ma_tk,
                    "kieu": kieu,
                    "loai": loai,
                    "id": id,
                    "huong_kn": huong_kn,
                }
                let response = await api.lay_ts_mgwan_ketnoi(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_ds_router() {
            let res = []
            try {
                let response = await api.lay_ds_router(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_ds_loaicap() {
            let res = []
            try {
                let response = await api.lay_ds_loaicap(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async gridviewSoChinh_FocusedRowChanged(data) {
            if (data.data != undefined) {
                try {
                    this.loading(true)
                    if (String(data.data.hdtb_id) != "") {
                        this.vid = Number(data.data.hdtb_id)
                    }
                        
                    this.vloai = 2;
                    if (this.vid > 0) {
                        this.HTChiTiet_SoChinh(data.data);
                    } 
                    await this.HTDS_SoPhu();
                } finally {
                    this.loading(false)
                }     
            }
        },
        gridViewSoPhu_FocusedRowChanged(data) {
            if (data.data != undefined) {
                try {
                    this.loading(true)
                    this.HTChiTiet_SoPhu(data.data)
                } finally {
                    this.loading(false)
                } 
            }
        },
        HTChiTiet_SoChinh(row) {
            try
            {
                if (String(row.pe_id) != "") {
                    this.router_id_chinh = String(row.pe_id)
                }
                if (String(row.loaicap_id) != "") {
                    this.loaicap_id_chinh = String(row.loaicap_id)
                }

                this.txtSlotPE_Chinh = row.slot_pe
                this.txtPortPE_Chinh = row.port_pe
                this.txtDSLAMWan_Chinh = row.dslam
                this.txtTramTB_Chinh = row.tram_tbi
                this.txtTenTbi_Chinh = row.ten_tbi
                this.txtSvLan_Chinh = row.svlan
                this.txtCvLan_Chinh = row.cvlan
                this.txtVRF_Chinh = row.vrf
                this.txtPort_Chinh = row.port
                this.txtRoute_Chinh = row.route
                this.txtLink_Chinh = row.link
                this.txtSerial_Chinh = row.serial
                this.txtLAN_IP_Chinh = row.lan_ip
                this.txtWAN_IP_Chinh = row.wan_ip
                this.txtHopCapNgon_Chinh = row.hopcap_p
                this.txtTuCapGoc_Chinh = row.tucap_goc
                this.txtTuCapNgon_Chinh = row.tucap_p
                this.txtTuyenCap_Chinh = row.tuyencap
                this.txtDoiCapGoc_Chinh = row.doicap_goc
                this.txtDoiCapNgon_Chinh = row.doicap_p
                this.txtLienTu_Chinh = row.lientu
                this.txtCapGoc_Chinh = row.cap_goc
                this.txtCapNgon_Chinh = row.cap_p
                this.txtSoMetDay_Chinh = row.culy
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex)
            }
        },
        async HTDS_SoPhu() {
            try {
                this.dt_sp = await this.LAY_TS_MGWAN_KETNOI(this.vma_tk, this.vkieu, this.vloai, this.vid, this.vhuong_kn)
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex)
            }
        },
        HTChiTiet_SoPhu(row) {
            try {
                if (String(row.pe_id) != "") {
                    this.router_id_phu = String(row.pe_id)
                }
                if (String(row.loaicap_id) != "") {
                    this.loaicap_id_phu = String(row.loaicap_id)
                }

                this.txtSlotPE_Phu = row.slot_pe
                this.txtPortPE_Phu = row.port_pe
                this.txtDSLAMWan_Phu = row.dslam
                this.txtTramTB_Phu = row.tram_tbi
                this.txtTenTbi_Phu = row.ten_tbi
                this.txtSvLan_Phu = row.svlan
                this.txtCvLan_Phu = row.cvlan
                this.txtVRF_Phu = row.vrf
                this.txtPort_Phu = row.port
                this.txtRoute_Phu = row.route
                this.txtLink_Phu = row.link
                this.txtSerial_Phu = row.serial
                this.txtLAN_IP_Phu = row.lan_ip
                this.txtWAN_IP_Phu = row.wan_ip
                this.txtHopCapNgon_Phu = row.hopcap_p
                this.txtTuCapGoc_Phu = row.tucap_goc
                this.txtTuCapNgon_Phu = row.tucap_p
                this.txtTuyenCap_Phu = row.tuyencap
                this.txtDoiCapGoc_Phu = row.doicap_goc
                this.txtDoiCapNgon_Phu = row.doicap_p
                this.txtLienTu_Phu = row.lientu
                this.txtCapGoc_Phu = row.cap_goc
                this.txtCapNgon_Phu = row.cap_p
                this.txtSoMetDay_Phu = row.culy
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex)
            }
        },
        onFilterRouterChinh(e) {
            var query = new Query();
            query = (e !== '') ? query.where('pe', 'contains', e, true) : query;
            e.updateData(this.ds_router, query);
        },
        onFilterRouterPhu(e) {
            var query = new Query();
            query = (e !== '') ? query.where('pe', 'contains', e, true) : query;
            e.updateData(this.ds_router, query);
        },
        onFilterLoaiCapChinh(e) {
            var query = new Query();
            query = (e !== '') ? query.where('loaicap', 'contains', e, true) : query;
            e.updateData(this.ds_loaicap, query);
        },
        onFilterLoaiCapPhu(e) {
            var query = new Query();
            query = (e !== '') ? query.where('loaicap', 'contains', e, true) : query;
            e.updateData(this.ds_loaicap, query);
        },
        btnTimKiemKH_Click() {
            this.$refs.popupSearchAccount.show()
        },
        async searchAccount_Close(args) {
            if (args.ma_tb != undefined && args.ma_tb != null && args.ma_tb != "") {
                this.txtMaKH = args.ma_tb;
                $("#txtMaKH").focus()
            }
            this.$refs.popupSearchAccount.hide()
        },
        txtMaKH_KeyPress(e) {

        }
    }
}
</script>
<style>
  .non-ative a {
    color: #d3d3d3 !important;
  }
</style>