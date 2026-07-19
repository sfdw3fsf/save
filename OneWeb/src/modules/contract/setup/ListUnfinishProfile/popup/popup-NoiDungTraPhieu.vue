<template>
    <div class="modal-content popup-box" style="background: #fff; width: 99.5% !important; margin-left: 1px">
        <div class="popup-body mart10">
            <div class="info-row">
                <div class="key" style="width: 160px;">
                    Lý do trả
                </div>
                <div class="value">
                    <div class="select-custom">
                        <Select2 v-model="lydo_tra" :options="lstLyDoTra" />
                        <!-- <ejs-dropdownlist
                            locale="vi-VN"
                            v-model="lydo_tra"
                            :dataSource="lstLyDoTra"
                            :allowFiltering="true" :filtering="onFilterLyDoTra"
                            :fields="{ value: 'vattu_id', text: 'ten_vt' }"
                        /> -->
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key" style="width: 160px;">
                    Nhập nội dung trả phiếu
                </div>
                <div class="value">
                    <input type="text" class="form-control" v-model="noidung_tra">
                </div>
            </div>
            <div class="group-buttons -bottom tright">
                <button class="btn btn-second" @click="tra_phieu">Trả phiếu</button>
                <button class="btn btn-second" @click="huy_bo">Huỷ bỏ</button>
            </div>
        </div>
    </div>
</template>
<script>
import api from "@/modules/contract/setup/ListUnfinishProfile/api/index.js";
import { Query } from '@syncfusion/ej2-data'
export default {
    name:'popup-NoiDungTraPhieu',
    components:{
        
    },
    data() {
        return {
            lstLyDoTra: [],
            lydo_tra: "",
            noidung_tra: "",
        }
    },
    computed: {

    },
    methods: {
        // onFilterLyDoTra: function(e){
        //     var query = new Query();
        //     query = (e.text !== '') ? query.where('ten_vt', 'contains', e.text, true) : query;
        //     e.updateData(this.lstLyDoTra, query);
        // },
        tra_phieu() {
            let nd = {
                lydo: this.lydo_tra,
                noidung: this.noidung_tra,
            }
            this.$emit("tra_phieu", nd);
        },
        huy_bo() {
            this.$emit("huy_bo");
        },
        async init() {
            this.noidung_tra = "";
            this.lydo_tra = "";
            this.lstLyDoTra = [],
            this.loadCboLyDoTra();
        },
        async loadCboLyDoTra() {
            try {
                let response = await api.ur_005_ds_lydotra_tc(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
                    this.lstLyDoTra = response.data.data.map((x)=>({id:x.vattu_id, text:x.ten_vt}))
                    this.lydo_tra = this.lstLyDoTra[0].id;
                }
            } catch(e) {
                console.log(e);           
            }
        }  
    },
    mounted() {
        
    },
}
</script>