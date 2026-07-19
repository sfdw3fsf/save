<template src="./frmTC_DBTB_KH.html"></template>
<script>
import api from "./frmTC_DBTB_KH.js";

export default {
    name:'frmTC_DBTB_KH',
    props:{
        khachhang_id: {
            type: Number,
            default: 0
        },
        thuebao_id: {
            type: Number,
            default: 0
        },
        hdkh_id: {
            type: Number,
            default: 0
        },
        loaitb_id: {
            type: Number,
            default: 0
        },
        vkieu: {
            type: Number,
            default: 0
        }
    },
    data() {
        return {
            ds_matb: "",
            grvDS_TB: {
                headers: [
                    { fieldName: 'ma_tb', headerText: 'Số máy/Account', allowFiltering: true, allowSorting: false, width: 100 },
                    { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: false, autoFit: true },
                    { fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', allowFiltering: true, allowSorting: false, autoFit: true },
                ],
                data: [],
                checked: []
            },
        }
    },
    methods: {
        async openDialog() { 
            this.$refs.dlg_TC_DBTB_KH.show()
            await this.frmTC_DBTB_KH_Load()
        },
        async frmTC_DBTB_KH_Load() {
            try {
                this.loading(true)
                let ds = []
                if (this.vkieu == 0)
                    ds = await this.Lay_dstb_theo_khid_loaitb_V2(this.hdkh_id, this.khachhang_id, this.loaitb_id);
                else
                    ds = await this.lay_dstb_theo_khid(this.khachhang_id);

                if (ds.length > 0)
                    this.grvDS_TB.data = ds.slice()
                else
                    this.grvDS_TB.data = [];
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        async Lay_dstb_theo_khid_loaitb_V2(vhdkh_id, vkhachhang_id, vloaitb_id) {
            let result = []
            try {
                let params = {
                    "vhdkh_id": vhdkh_id,
                    "vkhachhang_id": vkhachhang_id,
                    "vloaitb_id": vloaitb_id
                }
                let response = await api.lay_dstb_theo_khid_loaitb_v2(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async lay_dstb_theo_khid(vkhachhang_id) {
            let result = []
            try {
                let params = {
                    "vkhachhang_id": vkhachhang_id
                }
                let response = await api.lay_dstb_theo_khid(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        grvDS_TB_selectedItemsChanged: function (obj) {
            this.grvDS_TB.checked = obj
        },
        async tsbtnChapNhan_Click() {
            try {
                this.loading(true)
                this.ds_matb = "";
                if (this.vkieu == 0) {
                    this.grvDS_TB.checked.forEach(item => {
                        this.ds_matb += item.thuebao_id + ";"
                    })
                } else {
                    this.grvDS_TB.checked.forEach(item => {
                        this.ds_matb += item.ma_tb + ";"
                    })
                }
                
                if (this.ds_matb.length > 0) {
                    this.ds_matb = this.ds_matb.substring(0, this.ds_matb.length - 1)
                }
                this.$emit("chapnhan", this.ds_matb);
                this.$refs.dlg_TC_DBTB_KH.hide()
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        async tsbtnLayDL_Click() {
            await this.frmTC_DBTB_KH_Load()
        }
    },
}
</script>