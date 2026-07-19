<template src="./TraCuuTBKS.html"></template>
<script>
import api from "./TraCuuTBKS.js";
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import columns from "./columns.js";

export default {
    name:'TraCuuTBKS',
    props:{
        _matb: {
            type: String,
            default: ""
        }
    },
    components:{
        DataGridCustom
    },
    data() {
        return {
            ...columns,
            ds_thuebao: [],
            ds_chitiet: [],
            ds_donvi: [],
            fields: '',
            donviSelect: null,
            dtpTuNgay: null,
            dtpDenNgay: null,
            txtNgayKS: "",
            txtNgayYC: "",
            txtMaTB: "",
            txtNgayTH: "",
            txtGhiChu: "",
            lbTong: "",
            KHAOSAT_HDTB: false,
            ds_traloi: [],
            chtlSelect: null,
            fieldsTL: '',

        }
    },
    methods: {
        refresh() {
            this.ds_thuebao = []
            this.ds_chitiet = []
            this.donvi_id = null
            this.dtpTuNgay = null
            this.dtpDenNgay = null
            this.txtNgayKS = ""
            this.txtNgayYC = ""
            this.txtMaTB = ""
            this.txtNgayTH = ""
            this.txtGhiChu = ""
            this.lbTong = ""
        },
        async openDialog() { 
            this.refresh()
            try {
                this.loading(true)
                let ds = await this.LAY_DS_THAMSO_MD("KHAOSAT_HDTB");
                if (ds.length > 0 && Number(ds[0].ten_ts) == 1) {
                    this.KHAOSAT_HDTB = true;                  
                }

                if (this._matb != "") {
                    TimKiem(this._matb, "", "");
                }

                await this.createTreeTL()
                await this.createTree()
                this.$refs.dlg_tracuutbks.show()
            } catch (ex) {
                this.$toast.error(ex)
            } finally {
                this.loading(false)
            }
            
        },
        async TimKiem(vma_tb, vtungay, vdenngay) {
            this.TimKiem(vma_tb, vtungay, vdenngay, -1)
        },
        async TimKiem(vma_tb, vtungay, vdenngay, donvi_id) {
            try {
                if ((this.vma_tb == null || this.vma_tb == "") && donvi_id == 0)
                    return;

                let traloi_id = 0, cauhoi_id = 0;
                if (donvi_id == -1)
                    donvi_id = Number(this.donviSelect.donvi_id)

                if (document.getElementById("chkAllCH").checked) {
                    cauhoi_id = Number(this.chtlSelect.cauhoi_id)
                    traloi_id = Number(this.chtlSelect.traloi_id)
                }

                // Khong tim thay ham DB fims.LAY_DS_TB_DAKS_V3 + fims.khaosat_kh.LAY_TONG_TB_DAKS => popup nay khong co du lieu
                this.ds_thuebao = []
                // let dsList = fims.LAY_DS_TB_DAKS_V3(donvi_id, cauhoi_id, traloi_id, vtungay, vdenngay, vma_tb);
                // colBDKS.Visible = dsList.Tables[0].Columns.Contains("NGAY_BD");
                // gridDanhSach.DataSource = dsList.Tables[0];
                if (this.ds_thuebao.length > 0) {
                    // gridviewDanhSach_FocusedRowChanged(0);
                    // var dtTong = fims.LAY_TONG_TB_DAKS(donvi_id, cauhoi_id, traloi_id, dtpTuNgay.Value, dtpDenNgay.Value).Tables[0];
                    // this.lbTong = "Số lượng thuê bao không hài lòng/Tổng thuê bao khảo sát: ";
                    // foreach (DataRow item in dtTong.Rows) {
                    //     if (item["TEN_TAT"].ToString() == "TONG") {
                    //         this.lbTong += "Tổng :" + item["KHL"].ToString() + "/" + item["TONGKS"].ToString() + " ; ";
                    //     } else {
                    //         this.lbTong += item["TEN_TAT"].ToString() + " :" + item["KHL"].ToString() + "/" + item["TONGKS"].ToString() + " ; ";
                    //     }
                    // }
                } else {
                    this.ds_chitiet = []
                    this.txtNgayKS = "";
                    this.txtNgayYC = "";
                    this.txtNgayTH = "";
                    this.txtGhiChu = "";
                    this.lbTong = "";
                }
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        async createTree() {
            try {
                await this.NAP_TVW_DONVI();
                let lvtDonVi = this.createTreeDonVi(this.ds_donvi);
                lvtDonVi[0].expanded = true
                this.fields = { dataSource: lvtDonVi, id: 'donvi_id', text: 'ten_dv', parentID: 'donvi_cha_id', hasChildren: 'hasChild' }
            } catch (e){
                console.log(e)
            }
        },
        async selectDonViFocusedNodeChange (node) {
            this.donviSelect = Number(node.nodeData)
        },
        createTreeDonVi(lvtDonVi){
            let tree = []
            let lvtChaArray = lvtDonVi.map(item => (item['donvi_cha_id']))
            lvtDonVi.forEach(item => {
                if (lvtChaArray.includes(item['donvi_id'])){
                    tree.push( {...item, hasChild:true, expanded: false})
                } else {
                    tree.push(item)
                }
            })
            return tree;
        },
        async NAP_TVW_DONVI() {
            this.ds_donvi = []
            try {
                let params = {
                    "vnhanvien_id": this.$root.token.getNhanVienID()
                }
                let response = await api.ur_41005_001_nap_tvw_donvi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_donvi = response.data.data                 
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
        },
        async LAY_DS_THAMSO_MD(ma_ts) {
            let result = []
            try {
                let params = {
                    "ma_ts": ma_ts
                }
                let response = await api.ur_41003_5_001_lay_ds_thamso_md_mats(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async createTreeTL() {
            try {
                await this.sp_lay_ds_cauhoi_tcks();
                let lvtTraLoi = this.createTreeTraLoi(this.ds_traloi);
                lvtTraLoi[0].expanded = true
                this.fieldsTL = { dataSource: lvtTraLoi, id: 'id', text: 'noidung', parentID: 'parent_id', hasChildren: 'hasChild' }
            } catch (e){
                console.log(e)
            }
        },
        async selectTLFocusedNodeChange (node) {
            this.chtlSelect = Number(node.nodeData)
        },
        createTreeTraLoi(lvtTraLoi){
            let tree = []
            let lvtChaArray = lvtTraLoi.map(item => (item['parent_id']))
            lvtTraLoi.forEach(item => {
                if (lvtChaArray.includes(item['id'])){
                    tree.push( {...item, hasChild:true, expanded: false})
                } else {
                    tree.push(item)
                }
            })
            return tree;
        },
        async sp_lay_ds_cauhoi_tcks() {
            this.ds_traloi = []
            try {
                let response = await api.sp_lay_ds_cauhoi_tcks(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_traloi = response.data.data                 
                }
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
        },
        chkAllCH_CheckedChanged() {

        },
        txtMaTB_KeyPress() {

        },
        txtMaTB_ButtonClick() {
            
        }
    },
}
</script>