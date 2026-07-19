<template src="./LichSu_BaoHong.html"></template>
<script>
import api from "./LichSu_BaoHong.js";
import columns from "./columns.js";
import frmTraCuuPhieuBH from "../../../SearchIncidentUpdateAppointment"

export default {
    name:'LichSu_BaoHong',
    props:{
        phieu_id: {
            type: Number,
            default: 0
        },
        baohong_id: {
            type: Number,
            default: 0
        },
        thuebao_id: {
            type: Number,
            default: 0
        },
    },
    components:{
        frmTraCuuPhieuBH
    },
    data() {
        return {
            ...columns,
            tsbCapNhat: false,
            ds_lichsubaohong: []
        }
    },
    methods: {
        refresh() {
            this.ds_lichsubaohong = []
        },
        async openDialog() { 
            this.refresh()          
            await this.frmLichSu_BaoHong_Load()
            this.$refs.dlg_lichsu_baohong.show()
        },
        async frmLichSu_BaoHong_Load() {
            try {
                this.loading(true)
                await this.HienThiDanhSach()
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        },
        async HienThiDanhSach() {
            this.ds_lichsubaohong = []
            if (this.thuebao_id != 0) {
                this.ds_lichsubaohong = await this.LAY_LICHSU_BAOHONG_THUEBAO(this.thuebao_id);
            }
        },
        async LAY_LICHSU_BAOHONG_THUEBAO(thuebao_id) {
            let result = []
            try {
                let params = {
                    "thuebao_id": thuebao_id
                }
                let response = await api.Lay_lichsu_baohong_thuebao(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async tsbCapNhat_Click() {
            // Button nay khong hien thi
        },
        async DoRowDoubleClick(index, item) {
            if (item != undefined && item != null) {
                try {
                    this.loading(true)
                    let vma_tb = String(item.ma_tb)
                    let baohong_id = Number(item.baohong_id);
                    this.$refs.dlg_tracuuphieubh.show()
                    await this.$refs.frmTraCuuPhieuBH.HienThi_LichSu_BaoHong(vma_tb, 0, "", baohong_id)
                } finally {
                    this.loading(false)
                }
            } 
        }
    },
}
</script>
<style>
    .popup-ttpbh .page-content {
        position: inherit;
        top: 20px !important;
    }

    .popup-ttpbh .breadcrumb-top {
        display: none;
    }

    .popup-ttpbh .main-wrapper {
        position: relative !important;
        top: 0px !important;
    }
</style>