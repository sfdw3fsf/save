<template>
    <ejs-dialog :enableResize='true' :visible="false" ref="ppChonTinhTSL" :header='"Chọn tỉnh TSL"' 
                :allowDragging="true" :position="{X: 'center', Y: 'top'}"
                showCloseIcon=false target=".main-wrapper" width='40%' height="250">
        <div class="modal-content popup-box">
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a @click="btnChapNhan_Click">
                            <span class="icon one-save"></span> Chấp nhận
                        </a>
                    </li>
                </ul>    
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="row">
                        <div class="col-sm-12 col-12">
                            <div class="info-row">
                                <div class="key w100">Chọn tỉnh đầu</div>
                                <div class="value">
                                    <div class="select-custom">
                                        <Select2 v-model="model.cboTinh.value" :options="model.cboTinh.list" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12 col-12">
                            <div class="info-row">
                                <div class="key w100">Chọn tỉnh cuối</div>
                                <div class="value">
                                    <div class="select-custom">
                                        <Select2 v-model="model.cboTinhCuoi.value" :options="model.cboTinhCuoi.list" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>
<script>
import api from "../SetupChangeTSLSpeedCrossSale.js"
export default {
    name:'frmChonTinhTSL',
    data() {
        return {
            model: {
                cboTinh: {
                    list: [],
                    value: null
                },
                cboTinhCuoi: {
                    list: [],
                    value: null
                }
            },
            tinhdau: {},
            tinhcuoi: {}
        }
    },
    methods: {
        async openDialog() {
            try {
                this.loading(true)
                this.clear()
                this.$refs.ppChonTinhTSL.show()
                await this.frmChonTinhTSL_Load()
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        clear() {
            this.cboTinh = {
                list: [],
                value: null
            },
            this.cboTinhCuoi = {
                list: [],
                value: null
            }
            this.tinhdau = {}
            this.tinhcuoi = {}
        },
        async frmChonTinhTSL_Load() {
            let temps = await this.lay_thongtin_tinh_dm_tinh_ketnoi()
            if (temps.length > 0) {
                this.model.cboTinh.list = temps.map((x)=>({id:x.tinh_id, text:x.tentinh}))
                this.model.cboTinh.value = temps[0].tinh_id

                this.model.cboTinhCuoi.list = temps.map((x)=>({id:x.tinh_id, text:x.tentinh}))
                this.model.cboTinhCuoi.value = temps[0].tinh_id
            }
        },
        async lay_thongtin_tinh_dm_tinh_ketnoi() {
            let res = []
            try {
                let response = await api.lay_thongtin_tinh_dm_tinh_ketnoi(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_tinh(tinh_id) {
            let res = []
            try {
                let params = {
                    "tinh_id": tinh_id
                }
                let response = await api.lay_thongtin_tinh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async tinh_thicong(tinh_id) {
            let tinh = {}
            let rs = await this.lay_thongtin_tinh(tinh_id)
            if (rs.length > 0) {
                let dr = rs[0];
                tinh.user = dr.ten_truycap;
                tinh.pass = dr.mat_khau;
                tinh.nhanvien_id = Number(dr.nhanvien_id);
                tinh.donvi_id = Number(dr.donvi_id);
                tinh.matinh = dr.matinh;
                tinh.TNS = dr.tns_string;
                tinh.tinhthicong_id = Number(dr.tinh_id);
                tinh.mavung = dr.ma_vung;
                tinh.ip_dll = dr.ip_dll;
            } else {
                tinh.user = "";
                tinh.pass = "";
                tinh.nhanvien_id = 0;
                tinh.donvi_id = 0;
                tinh.matinh = "";
                tinh.TNS = "";
                tinh.tinhthicong_id = 0;
                tinh.ip_dll = "";

            }
            return tinh;
        },
        async btnChapNhan_Click() {
            this.tinhdau = await this.tinh_thicong(this.model.cboTinh.value)
            this.tinhdau.tentinh = this.model.cboTinh.list.find(item => item.id == this.model.cboTinh.value).text

            this.tinhcuoi = await this.tinh_thicong(this.model.cboTinhCuoi.value)
            this.tinhcuoi.tentinh = this.model.cboTinhCuoi.list.find(item => item.id == this.model.cboTinhCuoi.value).text

            this.$emit("chapnhan", {tinhdau: this.tinhdau, tinhcuoi: this.tinhcuoi})
            this.$refs.ppChonTinhTSL.hide()
        }
    },
}
</script>