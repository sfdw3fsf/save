<template src="./TraCuuMDF.html"></template>
<script>
import api from "./TraCuuMDF.js";
import { Query } from '@syncfusion/ej2-data'
import Vue from 'vue';
import { DropDownListPlugin } from "@/components/duybn_components/Dropdowns/ej2-vue-dropdowns";
Vue.use(DropDownListPlugin);
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import columns from "./columns.js";
import {LOAI_DV} from '@/const/enums'

export default {
    name:'TraCuuMDF',
    components:{
        DataGridCustom
    },
    data() {
        return {
            ...columns,
            enable: {
                tsbtnTuyenCap: false
            },
            tc_type: "rdoMDF",
            txtMaTB: "",
            ds_tongdai: [],
            tongdai_id: null,
            txtKyHieuPD: "",
            txtShJumper: "",
            ds_tracuu: [],
            rowSelected: null,
            ds_dandocap: [],
            vkieu: null,
            vma_tb: "",
            vtongdai: null,
            vkyhieupd: "",
            vjumper: "",
            ds: [],
            dstangbo: [],
            dsdando: [],
            dstongdai: [],
            dstonghop: [],

        }
    },
    methods: {
        async frmTraCuuMDF_Load(ma_tb) {
            try {
                this.loading(true)

                // loadCbo
                this.ds_tongdai = await this.HT_DonViCon_loaidv_Combobox(0, LOAI_DV.TRAM_PORT + "," + LOAI_DV.TRAM_VETINH)
                if (this.ds_tongdai.length > 0) {
                    this.tongdai_id = this.ds_tongdai[0].donvi_id
                }

                // Tracuu
                this.txtMaTB = ma_tb
                await this.TraCuu()
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        },
        async HT_DonViCon_loaidv_Combobox(donvi_id, loaidv_id) {
            let result = []
            try {
                let params = {
                    "donvi_id": donvi_id,
                    "loaidv_id": loaidv_id,
                    "phanvung_id": this.$root.token.getPhanVungID()
                }
                let response = await api.ur_41003_06_001_ht_donvicon_loaidv_combobox(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DB_THEO_MATB_1(in_ma_tb, in_donvi_dl_id) {
            let result = []
            try {
                let params = {
                    "in_ma_tb": in_ma_tb,
                    "in_donvi_dl_id": in_donvi_dl_id
                }
                let response = await api.ur_41003_06_003_lay_db_theo_matb_one(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_THONGTIN_MDF_PD(vkieu, vthuebao_id, vtongdai, vkyhieupd, vjumper) {
            let result = []
            try {
                let params = {
                    "vkieu": vkieu,
                    "vthuebao_id" : vthuebao_id,
                    "vtongdai": vtongdai,
                    "vkyhieupd": vkyhieupd,
                    "vjumper": vjumper
                }
                let response = await api.ur_41003_06_002_lay_thongtin_mdf_pd(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async TraCuu() {
            try {
                this.enable.tsbtnTuyenCap = false
                let vthuebao_id = 0;
                if (this.tc_type == "rdoMDF") {
                    if (this.txtMaTB.trim() == "") {
                        this.$toast.error("Bạn chưa chọn mã thuê bao cần tra cứu !");
                        return;
                    }

                    this.vkieu = 1
                    this.vma_tb = this.txtMaTB.trim()
                    this.ds = await this.LAY_DB_THEO_MATB_1(this.vma_tb, 0)

                    if (this.ds.length > 0) {
                        if (this.ds.length == 1) {
                            vthuebao_id = Number(this.ds[0].thuebao_id)
                        } else {
                            // TODO mo form UR4.1.001_frmTraCuu_ThueBao
                            // frmTraCuu_ThueBao frm = new frmTraCuu_ThueBao(ds);
                            // frm.ShowDialog();
                            // if (frm.chapnhan) {
                            //     int idex = frm.index
                            //     vthuebao_id = Convert.ToInt32(ds.Tables[0].Rows[idex]["thuebao_id"].ToString());
                            // }
                            // else
                            //     return;
                        }
                    } else {
                        this.$toast.error("Không tìm thấy thông tin thuê bao!");
                        return;
                    }
                }

                if (this.tc_type == "rdoPD") {
                    if (this.txtKyHieuPD.trim() == "" || this.txtShJumper.trim() == "" || this.tongdai_id == null) {
                        this.$toast.error("Bạn nhập thiếu thông tin tra cứu !");
                        return;
                    }
                    this.vkieu = 2;
                    this.vtongdai = String(this.tongdai_id)
                    this.vkyhieupd = this.txtKyHieuPD.toUpperCase().trim()
                    this.vjumper = this.txtShJumper.trim()
                }

                this.ds = await this.LAY_THONGTIN_MDF_PD(this.vkieu, vthuebao_id, this.vtongdai, this.vkyhieupd, this.vjumper);

                if (this.ds.length > 0) {
                    this.ds_tracuu = this.ds.slice()
                    this.enable.tsbtnTuyenCap = true
                } else {
                    if (this.tc_type == "rdoMDF") {
                        this.$toast.error("Không tìm thấy thông tin tra cứu MDF theo mã thuê bao!");
                    } else {
                        this.$toast.error("Không tìm thấy thông tin tra cứu thuê bao theo phiến dọc !");
                    }

                    this.ds_tracuu = []
                }
            } catch (ex) {
                this.$toast.error("Có lỗi trong quá trình tìm kiếm !" + ex);
            }
        },
        async tsbtnTraCuu_Click() {
            try {
                this.loading(true)
                await this.TraCuu()
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        },
        gridTraCuu_rowSelected(row) {
            this.rowSelected = {}
            if (row.data != undefined) {
                this.rowSelected = row.data
            }
        },
        async LAY_TT_CAP_THEO_MATB(vma_tb) {
            let result = []
            try {
                let params = {
                    "vma_tb": vma_tb
                }
                let response = await api.ur_41003_06_005_lay_tt_cap_theo_matb(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_GIANDO_THEODAUNOI(vketcuoi_id, vvitri) {
            let result = []
            try {
                let params = {
                    "vketcuoi_id": vketcuoi_id,
                    "vvitri": vvitri
                }
                let response = await api.ur_41003_06_005_lay_giando_theodaunoi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_TT_TONGDAI_THEO_CAP(vcap_id, vdoidayvao, vkieu) {
            let result = []
            try {
                let params = {
                    "vcap_id": vcap_id,
                    "vdoidayvao": vdoidayvao,
                    "vkieu": vkieu
                }
                let response = await api.ur_41003_06_005_lay_tt_tongdai_theo_cap(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async tsbtnTuyenCap_Click() {
            if (!this.enable.tsbtnTuyenCap) return
            try {
                this.loading(true)
                let matb = "", soi = "";
                let ketcuoi_id = 0;
                let vitri = 0;
                if (this.ds_tracuu.length > 0) {
                    matb = this.rowSelected.ma_tb
                    soi = this.rowSelected.soi

                    this.dsdando = []
                    let dtCap = await this.LAY_TT_CAP_THEO_MATB(matb)           
                    if (dtCap.length > 0) {
                        ketcuoi_id = Number(dtCap[0].ketcuoi_id)
                        vitri = (soi == "1" ? Number(dtCap[0].vitri) : Number(dtCap[0].vitri_2))
                        this.dsdando = await this.LAY_GIANDO_THEODAUNOI(ketcuoi_id, vitri);
                    }

                    if (this.dsdando.length > 0) {
                        let ktra = false;
                        let capvaoid = 0;
                        for (let i = 0; i < this.dsdando.length; i++) {
                            if (String(this.dsdando[i].lopmc_id) == "1") {
                                capvaoid = Number(this.dsdando[i].capvao_id)
                                ktra = true;
                                break;
                            }
                            ktra = false;
                        }

                        if (ktra) {
                            let ddv = Number(this.dsdando[0].doidayvao);
                            this.dstongdai = []
                            this.dstongdai = await this.LAY_TT_TONGDAI_THEO_CAP(capvaoid, ddv, 1);
                        } else {
                            let ddv = Number(this.dsdando[0].doidayvao);
                            dstongdai = []
                        }

                        this.dstonghop = []
                        this.ds_dandocap = this.dsdando.slice()
                    }

                    // TODO chua biet lam form nay nhu nao
                    // frmDanDoCapTB f = new frmDanDoCapTB(dstonghop, matb);
                    // f.ShowDialog();
                } else {
                    this.$toast.error("Không có dữ liệu để xem tuyến cáp !");
                    return;
                }
            } catch (e) {
                console.log(e)
                this.$toast.error("Có lỗi: " + e)
            } finally {
                this.loading(false)
            }
        },
        cboTongDai_onFilter(e) {
            var query = new Query();
            query = (e.text !== '') ? query.where('ten_dv', 'contains', e.text, true) : query;
            e.updateData(this.ds_tongdai, query);
        }
    },
}
</script>
<style>
    .non-ative a {
        color: #d3d3d3 !important;
    }
</style>