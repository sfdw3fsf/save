<template src="./LichSu_HenBH.html"></template>
<script>
import api from "./LichSu_HenBH.js";
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import columns from "./columns.js";

export default {
    name:'LichSu_HenBH',
    props:{
        phieu_id: {
            type: Number,
            default: 0
        },
        baohong_id: {
            type: Number,
            default: 0
        },
        hdtb_id: {
            type: Number,
            default: null
        },
        kieu: {
            type: Number,
            default: null
        },
    },
    components:{
        DataGridCustom
    },
    data() {
        return {
            ...columns,
            tsbCapNhat: false,
            ds_lichsuhen: []
        }
    },
    methods: {
        refresh() {
            this.ds_lichsuhen = []
        },
        async openDialog() { 
            this.refresh()          
            await this.frmLichSu_HenBH_Load()
            this.$refs.dlg_lichsu_henbh.show()
        },
        async frmLichSu_HenBH_Load() {
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
            this.ds_lichsuhen = []
            if (this.kieu == 2) {
                if (this.baohong_id != 0) {
                    let ds = await this.Lay_lichsu_hen_baohong(this.baohong_id);
                    if (ds != null && ds.length > 0) {
                        this.ds_lichsuhen = ds.slice()
                    }
                }                 
            } else {
                if (this.hdtb_id != 0) {
                    let ds = await this.Lay_lichsu_hen_thicong(this.hdtb_id);
                    if (ds != null && ds.length > 0) {
                        this.ds_lichsuhen = ds.slice()
                    }
                }
            }
        },
        async Lay_lichsu_hen_baohong(baohong_id) {
            let result = []
            try {
                let params = {
                    "baohong_id": baohong_id
                }
                let response = await api.ur_41003_09_001_lay_lichsu_hen_baohong(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async Lay_lichsu_hen_thicong(hdtb_id) {
            let result = []
            try {
                let params = {
                    "hdtb_id": hdtb_id
                }
                let response = await api.ur_41003_09_001_lay_lichsu_hen_thicong(this.axios, params);
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
        }
    },
}
</script>