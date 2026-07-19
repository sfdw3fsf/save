<template>  
    <div class="modal-content popup-box">
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a @click="btnGhiLai_Click">
                        <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span>Ghi lại
                    </a>
                </li>
            </ul>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="info-row">
                    <div class="key w90">
                        Nội dung nhắc mẫu
                    </div>
                    <div class="value">
                        <div class="select-custom">
                            <ejs-dropdownlist id="smsTemp_id"
                                locale="vi-VN" @change="cboSMSTemp_EditValueChanged"
                                v-model="smsTemp_id"
                                :dataSource="ds_SMSTemp"
                                :allowFiltering="true" :filtering="onFilterSMSTemp"
                                :fields="{ value: 'id', text: 'sms' }"
                            />
                        </div>                     
                    </div>
                </div>
                <div class="info-row">
                    <textarea class="form-control" style="height: 120px;resize: vertical;" v-model="txtNoiDung"></textarea>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { Query } from '@syncfusion/ej2-data'
import api from "@/modules/incident/subscriber/UpdateProcessIncident/UpdateProcessIncident.js"
export default {
    name:'NoiDungTT',
    components:{

    },
    props:{
        vtinh: {
            type:Object,
            default: null
        },
        vtientrinhsc_id: {
            type:Number,
            default: null
        },
        vsuco_id: {
            type:Number,
            default: null
        },
        vtientrinhbh_id: {
            type:Number,
            default: null
        },
        vnhom_id: {
            type:Number,
            default: null
        },
        vbaohong_id: {
            type:Number,
            default: null
        },
        vnoidung_goc: {
            type:String,
            default: ""
        },
        vnd_baohong: {
            type:String,
            default: ""
        },
        tag: {
            type:String,
            default: ""
        },
    },
    data() {
        return {
            ds_SMSTemp: [],
            smsTemp_id: null,
            txtNoiDung: "",
        }
    },
    methods: {
        async btnGhiLai_Click() {
            try {
                this.loading(true)
                if (this.txtNoiDung == "") {
                    this.$toast.error("Bạn phải nhập nội dung tiến trình");
                    return;
                }

                await this.capnhat_tientrinh_bh()
                this.$emit("btnGhiLai_Click")
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }   
        },
        async capnhat_tientrinh_bh() {        
            try {
                let params = {
                    "vbaohong_id": this.vbaohong_id,
                    "vnoidung": this.txtNoiDung,
                    "vsuco_id": this.vsuco_id,
                    "vtag": this.tag,
                    "vtientrinhbh_id": this.vtientrinhbh_id,
                    "vtientrinhsc_id": this.vtientrinhsc_id,
                    "vtinhthicong_id": this.vtinh != null ? this.vtinh.tinh_id : null
                }
                let response = await api.ur_41005_024_002_fn_ghilai_noidungtt(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    if (response.data.data == "1") {
                        this.$toast.success("Cập nhật thành công")
                    } else {
                        this.$toast.error(response.data.data)
                    }
                } else {
                    this.$toast.error(response.data.message)
                }            
            } catch(e) {
                console.log(e);
                this.$toast.error(e)
            }
        },
        async frmNoiDungTT_Load() {
            this.txtNoiDung = ""
            this.smsTemp_id = null
            console.log(this.vtinh)
            await this.sp_ds_nd_nhacmau()
        },
        async sp_ds_nd_nhacmau() {
            this.ds_SMSTemp = []
            try {
                let response = await api.ur_41005_024_001_sp_ds_nd_nhacmau(this.axios, {});
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_SMSTemp = response.data.data                
                }
            } catch(e) {
                console.log(e);
            }
        },
        cboSMSTemp_EditValueChanged() {
            if (this.vtientrinhbh_id != null)
                return;

            if (this.smsTemp_id == "-1") {
                this.txtNoiDung = this.ds_SMSTemp.find(x => x.id == this.smsTemp_id).sms + " " + this.vnoidung_goc
            } else {
                this.txtNoiDung = this.ds_SMSTemp.find(x => x.id == this.smsTemp_id).sms + " " + this.vnd_baohong
            }
        },
        onFilterSMSTemp() {
            var query = new Query();
            query = (e.text !== '') ? query.where('sms', 'contains', e.text, true) : query;
            e.updateData(this.ds_SMSTemp, query);
        },
    },
}
</script>
