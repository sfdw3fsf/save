<template>
    <div class="modal-content popup-box" style="background: #fff; width: 99.5% !important; margin-left: 1px">
        <div class="popup-body mart10">
            <div class="info-row">
                <div class="key" style="width: 160px;">
                    Đối tác
                </div>
                <div class="value">
                    <div class="select-custom">
                        <ejs-dropdownlist
                            locale="vi-VN"
                            v-model="doitac"
                            :dataSource="lstDoiTac"
                            :allowFiltering="true" :filtering="onFilterDoiTac"
                            :fields="{ value: 'donvi_id', text: 'ten_dv' }"
                        />
                    </div>
                </div>
            </div>
            <div class="group-buttons -bottom tright">
                <button class="btn btn-main" @click="xac_nhan">Xác nhận</button>
            </div>
        </div>
    </div>
</template>
<script>
import api from "@/modules/incident/subscriber/HandleAcceptanceIncidentReports/HandleAcceptanceIncidentReports.js";
import { Query } from '@syncfusion/ej2-data'
export default {
    name:'DoiTac',
    components:{
        
    },
    data() {
        return {
            lstDoiTac: [],
            doitac: "",
        }
    },
    methods: {
        onFilterDoiTac: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('ten_dv', 'contains', e.text, true) : query;
            e.updateData(this.lstDoiTac, query);
        },
        xac_nhan() {
            if (this.doitac == null || this.doitac == "") {
                this.$toast.error("Chưa chọn đối tác cần giao phiếu!");
                return;
            }
            this.$emit("xac_nhan", this.doitac);
        },
        async init() {
            this.doitac = "";
            this.lstDoiTac = [],
            this.loadCboDoiTac();
        },
        async loadCboDoiTac() {
            try {
                let params = {
                    "p_type": 3,
                    "p_param": ""
                }
                let response = await api.sp_tt_donvi(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.lstDoiTac = response.data.data;
                    if (this.lstDoiTac.length > 0) {
                        this.doitac = this.lstDoiTac[0].donvi_id;
                    }
                }
            } catch(e) {
                console.log(e);           
            }
        }  
    },
}
</script>