<template src="./DeleteLiquidationSubscription.html"></template>
<style scoped src="./DeleteLiquidationSubscription.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from "./DeleteLiquidationSubscription.js";
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import collums from "./define/collums.js";
import {Query} from '@syncfusion/ej2-data'

import Vue from 'vue';
import { DropDownListPlugin } from "@/components/duybn_components/Dropdowns/ej2-vue-dropdowns";
Vue.use(DropDownListPlugin);

export default {
    name:'DeleteLiquidationSubscription',
    components:{
        breadcrumb,
        DataGridCustom,
    },
    data() {
        return {
            header: {
                title: 'Xóa thuê bao thanh lý',
                list: [
                    { name: 'Quản trị hệ thống', link: { name: 'Ui.cards' } },
                    { name: 'Xóa thuê bao thanh lý', link: { name: 'Ui.cards' } },
                ]
            },
            ...collums,
            capnhat_bd_thongtin_cap_tb: false,
            dichvuvt: null,
            lstDichVuVT: [],
            trangthaitb: null,
            lstTrangThaiTB: [],
            enable: {
                btnXoaTB: false
            },
            txtMaTB: "",
            txtMaKH: "",
            txtTenKH: "",
            txtTenTB: "",
            txtDCTB: "",
            txtThangcat: "6",
            thuebao_id: null,
            ds_thuebao: [],
            ds_thuebao_selected: [],
            somay: "",
            loaitb_id: 0,
            tttb_id: null,
            dichvuvt_id: null,
            popupComponent: null,
            popupComponentData: null,
            popupComponentName: '',
            popupComponentEvents: {
                'form-close':'popupClosed',
                'acceptChangeCTV': 'popupClosed',
                'acceptChangeNGT': 'popupClosed'
            },
            totalRecords: 0,
            isClick: false,
        }
    },
    methods: {
        onFilterDichVuVT: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('TEN_DVVT', 'contains', e.text, true) : query;
            e.updateData(this.lstDichVuVT, query);
        },
        onFilterTrangThaiTB: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('TRANGTHAI_TB', 'contains', e.text, true) : query;
            e.updateData(this.lstTrangThaiTB, query);
        },
        cboDichVuVT_Changed() {
            this.txtMaTB = "";
            this.Clear();
            this.enable.btnXoaTB = false;
        },
        cboTrangThaiTB_Changed() {
            this.txtMaTB = "";
            this.Clear();
            this.enable.btnXoaTB = false;
        },
        Clear() {
            this.txtMaKH = "";
            this.txtTenKH = "";
            this.txtTenTB = "";
            this.txtDCTB = "";
            this.thuebao_id = 0;
            this.ds_thuebao = [];
        },
        gridDanhSachTB_FocusedRowChanged(data) {
            let row = data.data
            if (row != undefined) {
                this.somay = String(row.ma_tb)
                this.loaitb_id = Number(row.loaitb_id)
                this.txtMaKH = row.ma_kh
                this.txtTenKH = row.ten_kh
                this.txtMaTB = row.ma_tb
                this.txtTenTB = row.ten_tb
                this.txtDCTB = row.diachi_tb
                this.thuebao_id = Number(row.thuebao_id)
                this.enable.btnXoaTB = true
            }
        },
        txtThangcat_TextChanged() {
            try {
                if (this.txtThangcat == "") {
                    this.$toast.error("Nhập tháng cắt")
                    $("#txtThangcat").focus()
                    return;
                }
                this.Clear();
                this.enable.btnXoaTB = false;
            } catch (exp) {
                this.$toast.error(exp)
            }
        },
        checkNumber(event) {
            if ((event.keyCode >= 96 && event.keyCode <= 105) || (event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode == 8 || event.keyCode == 46) {
                return true;
            } else {
                event.preventDefault();    
            }
        },
        async txtMaTB_KeyUp(event) {
            if (event.keyCode == 13) {
                this.Clear();
                await this.HT_TT_DS_THANHLY();
            }
        },
        async LAY_DS_DBTB_THANHLY_THEO_DVVT(ma_tb, thang, trangthai_tb_id, dvvt_id, pageNum, pageRec, totalRow) {
            let lstDSTB = []
            let params = {
                "ma_tb": ma_tb != undefined && ma_tb.trim() != "" ? ma_tb.trim() : "0",
                "thang": thang,
                "trangthai_tb_id": trangthai_tb_id,
                "dvvt_id": dvvt_id,
                "pageNum": pageNum,
                "pageRec": pageRec,
                "totalRow": totalRow
            }
            try {
                this.loading(true)
                let response = await api.ur_15001_003_sp_lay_ds_dbtb_thanhly_theo_dvvt(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    lstDSTB = response.data.data
                    if (totalRow == 1) {
                        this.totalRecords = response.data.page_info.record_count
                    }
                }            
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
            return lstDSTB
        },
        async gridDanhSachTB_pageChanged() {
            if (this.isClick) {
                this.isClick = false
                return
            }

            this.isClick = false
            this.ds_thuebao = []
            try {
                let pIndex = this.$refs.ds_thuebao.$refs.pagination.pageIndexData
                let pSize = this.$refs.ds_thuebao.$refs.pagination.pageSizeData
                this.ds_thuebao = await this.LAY_DS_DBTB_THANHLY_THEO_DVVT("", Number(this.txtThangcat), this.tttb_id, this.dichvuvt_id, pIndex+1, pSize, 0)
            } catch (exp) {
                this.$toast.error(exp)
            }
        },
        async HT_TT_DS_THANHLY() {
            if (this.dichvuvt == null) return;
            if (this.trangthaitb == null) return;

            this.tttb_id = Number(this.trangthaitb);
            this.dichvuvt_id = Number(this.dichvuvt);
            this.ds_thuebao = []
            try {
                let pSize = this.$refs.ds_thuebao.$refs.pagination.pageSizeData
                this.ds_thuebao = await this.LAY_DS_DBTB_THANHLY_THEO_DVVT(this.txtMaTB, Number(this.txtThangcat), this.tttb_id, this.dichvuvt_id, 1, pSize, 1)
            } catch (exp) {
                this.$toast.error(exp)
            }
        },
        async btnTimKiem_Click() {
            this.Clear();
            this.isClick = true
            await this.HT_TT_DS_THANHLY();
            if (this.ds_thuebao.length > 0) {
                this.enable.btnXoaTB = true;
            } 
        },
        async btnTraCuuMaTB_Click() {
            this.popupComponent = ()=>import('@/modules/search/subscriber/SearchAccount');
            this.popupComponentName = "SearchAccount";
            try {
                this.popupComponent.data.isPopup = true;
            } catch(ex) {}
            this.Popup('popupComponents');
        },
        async popupClosed(val) {
            console.log(val)
            try {
                this.loading(true)
                if (this.popupComponentName == "SearchAccount") {
                    if (val.ma_tb) {
                        this.txtMaTB = val.ma_tb;
                        await this.HT_TT_DS_THANHLY();
                    }  
                }
            } catch(e) {
                console.log(e);
            } finally {
                this.ClosePopup('popupComponents');
                this.loading(false);
            }    
        },
        async Popup(modalId) { 
            this.$bvModal.show(modalId); 
        }, 
        async ClosePopup(modalId) { 
            this.$bvModal.hide(modalId);
        },
        gridDanhSachTB_SelectRow (val) {
            this.ds_thuebao_selected = [];
            for (let index in val) {
                for (let ix in this.ds_thuebao) {
                    if (val[index].thuebao_id == this.ds_thuebao[ix].thuebao_id) {
                        this.ds_thuebao_selected.push(val[index]);
                    }
                }
            }
        },
        async xoa_thuebao(dsObj) {
            let chkVuotLoi = document.getElementById("chkVuotLoi")
            let params = {
                "chkVuotLoi": chkVuotLoi.checked,
                "dsObj": dsObj
            }

            try {
                this.loading(true)
                let response = await api.ur_15001_006_xoa_thuebao(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000') {                  
                    this.$toast.success("Đã xóa khỏi danh bạ thuê bao");
                } else {
                    this.$toast.error(response.data.message)
                }

                this.txtMaTB = "";
                this.Clear();
                await this.HT_TT_DS_THANHLY();
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        async btnXoaTB_Click() {
            if (!this.enable.btnXoaTB) return
            try {
                if (this.ds_thuebao_selected.length == 0) {
                    this.$toast.error("Bạn chưa chọn thuê bao !");
                    return;
                }

                this.$bvModal.msgBoxConfirm("Bạn thật sự muốn xóa các thuê bao đã chọn không ?", {
                        title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                    }).then(async v => {
                        if (v) {
                            let dsObj = []
                            for (let i = 0; i < this.ds_thuebao_selected.length; i++) {
                                let temp = {}
                                temp.thuebao_id = Number(this.ds_thuebao_selected[i].thuebao_id);
                                temp.ma_tb = String(this.ds_thuebao_selected[i].ma_tb);
                                temp.loaitb_id = Number(this.ds_thuebao_selected[i].loaitb_id);
                                temp.dichvuvt_id = Number(this.ds_thuebao_selected[i].dichvuvt_id);
                                dsObj.push(temp)
                            }

                            await this.xoa_thuebao(dsObj)           
                        }
                    });           
            } catch (exp) {
                console.log(exp)
            }
        },
        async init() {
            try {
                this.loading(true);
                await this.get_dichvu_vt()
                await this.get_trangthai_tb()
                let lstThamSoMD = await this.sp_tt_thamso_md()
                if (lstThamSoMD.length > 0) {
                    let det = []
                    let str_vtemp = "CAPNHAT_BD_THONGTIN_CAP_TB";
                    for (let i=0; i<lstThamSoMD.length; i++) {
                        if (lstThamSoMD[i].ma_ts == str_vtemp) {
                            det.push(lstThamSoMD[i])
                        }
                    }

                    if (det.length > 0) {
                        let row = det[0];
                        if (row.ten_ts.trim() == "1") {
                            this.capnhat_bd_thongtin_cap_tb = true;
                        }                   
                    }
                }
            } catch (e) {
                this.$toast.error("Khởi tạo thất bại " + e);
            } finally {
                this.loading(false);
            }        
        },
        async sp_tt_thamso_md() {
            let lstThamSoMD = []
            let params = {
                "p_param": "",
                "p_type": 1
            }
            try {
                let response = await api.sp_tt_thamso_md(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    lstThamSoMD = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return lstThamSoMD
        },
        async get_dichvu_vt() {
            this.dichvuvt = null
            this.lstDichVuVT = []
            try {
                let response = await api.ur_15001_001_get_dichvu_vt(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.lstDichVuVT = response.data.data
                    if (this.lstDichVuVT.length > 0) {
                        this.dichvuvt = this.lstDichVuVT[0].DICHVUVT_ID
                    }
                }            
            } catch (e) {
                console.log(e)
            }
        },
        async get_trangthai_tb() {
            this.trangthaitb = null
            this.lstTrangThaiTB = []
            try {
                let response = await api.ur_15001_002_get_trangthai_tb(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    let lst = response.data.data
                    lst.forEach((item) => {
                        if (item.TRANGTHAITB_ID == 7 || item.TRANGTHAITB_ID == 8 || item.TRANGTHAITB_ID == 9) {
                            this.lstTrangThaiTB.push(item)
                        }
                    })
                    
                    if (this.lstTrangThaiTB.length > 0) {
                        this.trangthaitb = this.lstTrangThaiTB[0].TRANGTHAITB_ID
                    }
                }            
            } catch (e) {
                console.log(e)
            }
        }
    },
    mounted() {
        this.init();
    },
}
</script>