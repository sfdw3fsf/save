<template >
    <div class="row">
        <div class="col-sm-6 col-12">
            <div class="info-row">
                <div class="key w100">Kết quả XL</div>
                <div class="value">
                    <div class="select-custom">
                        <ejs-dropdownlist id="kqxl_id" locale="vi-VN" v-model="kqXlId" enable="enable.cboKQXL"
                            :dataSource="cboKqXl" :allowFiltering="true" :fields="{ value: 'ID', text: 'NAME' }" />
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w100">Nội Dung</div>
                <div class="value">
                    <input type="text" class="form-control" v-model="txtNoiDung" />
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-12">
            <div class="info-row">
                <div class="key w100" :style="cboNhomNguyenNhan.visible ? '' : 'display: none'">Nhóm N.N</div>
                <div class="value" :style="cboNhomNguyenNhan.visible ? '' : 'display: none'">
                    <div class="select-custom">
                        <ejs-dropdownlist id="nhomnn_id" locale="vi-VN" v-model="cboNhomNguyenNhan.value"
                            :dataSource="cboNhomNguyenNhan.dataSource" :allowFiltering="true" @change="cboNhomNN_Changed"
                            :fields="{ value: 'loaihong_id', text: 'loaihong' }" />
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w100" :style="cboNguyenNhan.visible ? '' : 'display: none'">Nguyên Nhân</div>
                <div class="value" :style="cboNguyenNhan.visible ? '' : 'display: none'">
                    <div class="select-custom">
                        <ejs-multiselect v-model="cboNguyenNhan.value"
                               :dataSource='cboNguyenNhan.dataSource'
                               mode="CheckBox"
                          
                               :showSelectAll='true'
                               selectAllText="Chọn tất cả"
                               unSelectAllText="Bỏ chọn tất cả"
                               :fields="{ text: 'ct_hong', value: 'cthong_id' }" />
                        <!-- <ejs-dropdownlist locale="vi-VN" v-model="cboNguyenNhan.value"
                            :dataSource="cboNguyenNhan.dataSource" :allowFiltering="true"
                            :fields="{ value: 'cthong_id', text: 'ct_hong' }" /> -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>

import Vue from 'vue'
import api from '../../api.js'
import apiHelper from '../../api.helper.js'
export default {
    name: 'ThongTinXuLy',
    props: {
        dichVuVTid: {
            type: String | Number,
            required: true,
            default: 0
        },
        baoHongItem: null

    },
    components: {
    },

    data() {
        return {

            txtNoiDung: "",
            cboKqXl: [
                { "ID": "0", "NAME": "Đã xử lý xong" },
                { "ID": "1", "NAME": "Không xử lý được" },
                { "ID": "2", "NAME": "Đang chờ xử lý" },
            ],
            kqXlId: '0',
            cboNhomNguyenNhan: {
                dataSource: [],
                value: null,
                visible: true,
                enable: true
            },
            cboNguyenNhan: {
                dataSource: [],
                value: [],
                visible: true,
                enable: true
            },
        }
    },
    methods: {

        async getNhomNguyenNhan(vdichvuvt_id) {

            if (!vdichvuvt_id) return;

            try {
                const response = await api.getNhomNguyenNhan(this.axios, vdichvuvt_id);
                const data = apiHelper.getDataFromResponseApiReturnArray(response);

                this.cboNhomNguyenNhan.dataSource = data;
                
                this.cboNhomNguyenNhan.value = this.cboNhomNguyenNhan.dataSource[0]?.loaihong_id
                
            }
            catch (err) {
                console.log("err:", err)
            }
        },

        async cboNhomNN_Changed(e) {
            const groupNNId = e.value;
            if (!groupNNId) return;

            if (!this.baoHongItem) return;

            api.getNguyenNhanNet(this.axios, { vphieu_id: this.baoHongItem.phieu_id, vnhomnn_id: groupNNId, vloaitb_id: this.baoHongItem.loaitb_id }).then(response => {
                const data = apiHelper.getDataFromResponseApiReturnArray(response);
                this.cboNguyenNhan.dataSource = data;
                this.cboNguyenNhan.value = [];
            }).catch(err => {
                console.log(err)
            })
        },

        clearData(){
            this.cboNguyenNhan.value = [];
            this.txtNoiDung = "";
            this.kqXlId = '0';
        }


    },
    watch: {
        baoHongItem(item){
            this.clearData();
            if(!item) return;
            this.txtNoiDung = item.ghichu_th || "";
        },

        async dichVuVTid(id) {
            if (id) {
                await this.getNhomNguyenNhan(id);
            }
        },
        txtNoiDung(val) {
            if (val) {
                this.$emit('update-event', {
                    noiDung: val,
                    kqXlId: this.kqXlId,
                    // nhomNNId: this.cboNhomNguyenNhan.value,
                    nguyenNhan: this.cboNguyenNhan.value
                });
            }

        },
        kqXlId(value) {
            this.$emit('update-event', {
                noiDung: this.txtNoiDung,
                kqXlId: this.kqXlId,
                // nhomNNId: this.cboNhomNguyenNhan.value,
                nguyenNhan: this.cboNguyenNhan.value
            });
        },
        // cboNhomNguyenNhan: {
        //     handler: function (value) {
        //         this.$emit('update-event', {
        //             noiDung: this.txtNoiDung,
        //             kqXlId: this.kqXlId,
        //             nhomNNId: this.cboNhomNguyenNhan.value,
        //             nguyenNhanId: this.cboNguyenNhan.value
        //         });
        //     },
        //     deep: true
        // },
        cboNguyenNhan: {
            handler: function (value) {
                // console.log(value)
                this.$emit('update-event', {
                    noiDung: this.txtNoiDung,
                    kqXlId: this.kqXlId,
                    // nhomNNId: this.cboNhomNguyenNhan.value,
                    nguyenNhan: this.cboNguyenNhan.value
                });
            },
            deep: true
        },

    },
    mounted() {
        // this.kqXlId = '0';
    }


}
</script>
<style scoped></style>
