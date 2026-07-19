<template>
    <b-modal 
        :id="modalId"
        size="xl"
        no-close-on-backdrop
        hide-footer 
        hide-header 
        hide-header-close 
        body-class="modal-body p-0"
        @hidden="onModalHidden"
        @shown="frmLoad">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title"><span class="icon one-notepad"></span> Sửa đổi thông tin cho các khách hàng trùng số giấy tờ</div>
                <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide(modalId)">
                </div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="#" @click="capNhat">
                            <span class="icon one-reload1"></span>Cập nhật (F5)
                        </a>
                    </li>
                    <li>
                        <a href="#" @click.prevent="thoat">
                            <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Thoát
                        </a>
                    </li>                    
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="legend-title">Thông tin khách hàng</div>
                    <div class="info-row">
                        <div class="key">Mã khách hàng</div>
                        <div class="value">
                            <input v-model="dsKhachHang.value.MA_KH" type="text" class="form-control" readonly>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key">Tên khách hàng</div>
                        <div class="value">
                            <input v-model="dsKhachHang.value.TEN_KH" type="text" class="form-control" readonly>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key">Địa chỉ KH</div>
                        <div class="value">
                            <input v-model="dsKhachHang.value.DIACHI_KH" type="text" class="form-control" readonly>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-12">
                            <div class="info-row">
                                <div class="key">Loại giấy tờ (*)</div>
                                <div class="value">
                                    <div class="select-custom">
                                        <select v-model="dsKhachHang.value.LOAIGT_ID" class="form-control" :class="{ 'is-invalid': $v.dsKhachHang.value.LOAI_GT.$error}">
                                            <option v-for="gt in dsLoaiGt" :key="gt.loaigt_id" :value="gt.loaigt_id">{{gt.loai_gt}}</option>
                                        </select>
                                    </div>
                                    
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-8 col-12">
                            <div class="info-row">
                                <div class="key">Số giấy tờ (*)</div>
                                <div class="value">
                                    <input v-model.trim="$v.dsKhachHang.value.SO_GT.$model" type="text" class="form-control" :class="{ 'is-invalid': $v.dsKhachHang.value.SO_GT.$error}">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-12">
                            <div class="info-row">
                                <div class="key">Ngày cấp (*)</div>
                                <div class="value">
                                    <date-picker :format="dateFormat" :value-type="'format'" v-model="$v.dsKhachHang.value.NGAYCAP.$model" :input-class="{ 'form-control': true, 'is-invalid': $v.dsKhachHang.value.NGAYCAP.$error}"></date-picker>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-8 col-12">
                            <div class="info-row">
                                <div class="key">Nơi cấp</div>
                                <div class="value">
                                    <input v-model="dsKhachHang.value.NOICAP" type="text" class="form-control">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <div class="legend-title">
                        <div class="pull-left">
                            Danh sách khách hàng trùng số giấy tờ
                        </div>
                        <div class="pull-right red">
                            Tổng số: {{tongKhachHang}}
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="table-content">
                        <DataGrid2 v-bind:columns="dsKhachHang.header" v-bind:dataSource="dsKhachHang.list"
                            :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                            ref="gridKhachHang" :enabledSelectFirstRow="true" dataKeyField="khachhang_id"
                            @selectedRowChanged="rowChanged" :pageSize="5">
                        </DataGrid2>
                    </div>
                </div>
                <div class="box-form">
                    <div class="legend-title">
                        <div class="pull-left">
                            Danh sách thuê bao
                        </div>
                        <div class="pull-right red">
                            Tổng số: {{tongThueBao}}
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="table-content">
                        <DataGrid2 v-bind:columns="dsThueBao.header" v-bind:dataSource="dsThueBao.list"
                            :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                            ref="gridThueBao" :enabledSelectFirstRow="true" :pageSize="5">
                        </DataGrid2>
                    </div>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<style scoped>
    .mx-datepicker { 
        width:100%; 
    }
</style>
<script>
import DataGrid2 from '@/components/Controls/DataGrid2'
import { required, minValue, maxValue, maxLength } from 'vuelidate/lib/validators'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import API from './../API.js'
export default {
    name: "ModalSuaGiayTo",
    components: { DataGrid2, DatePicker },
    props: ["modalId", "danhSach", "chuyenForm"],
    validations: {
        dsKhachHang: {
            value: {
                LOAI_GT: {
                    required
                },
                SO_GT: {
                    required,
                    maxlength: maxLength(30)
                },
                NGAYCAP: {
                    required
                },
            }
        }
    },
    data() {
        return {
            dateFormat: "DD/MM/YYYY",
            dsLoaiGt: [],
            isDlChuyenForm: true,
            dsKhachHang: {
                list: [],
                header: [ 
                    {fieldName: 'MA_KH', headerText: 'Mã khách hàng', allowFiltering: true}, 
                    {fieldName: 'TEN_KH', headerText: 'Tên khách hàng', allowFiltering: true}, 
                    {fieldName: 'DIACHI_KH', headerText: 'Địa chỉ khách hàng', allowFiltering: true}, 
                    {fieldName: 'LOAI_GT', headerText: 'Loại giấy tờ', allowFiltering: true}, 
                    {fieldName: 'SO_GT', headerText: 'Số giấy tờ', allowFiltering: true}, 
                    {fieldName: 'NGAYCAP', headerText: 'Ngày cấp', allowFiltering: true}, 
                    {fieldName: 'NOICAP', headerText: 'Nơi cấp', allowFiltering: true}, 
                ],
                value: {}
            },
            dsThueBao: {
                list: [],
                header: [
                    {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true}, 
                    {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true}, 
                    {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true}, 
                    {fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: true}, 
                ],
                value: {}
            }
        }
    },
    computed: {
        tongKhachHang(){
            return this.dsKhachHang.list.length
        },
        tongThueBao(){
            return this.dsThueBao.list.length
        },
        maTinh(){
        return this.$root.token.getMaTinh()
        }
    },
    watch: {
        'dsKhachHang.list': function(newVal){
            this.dsKhachHang.list.forEach(ele => {
                if(this.isDlChuyenForm)
                    ele.NGAYCAP = moment(ele.NGAYCAP).format('DD/MM/YYYY')
                else
                    ele.NGAYCAP = moment(ele.NGAYCAP, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY')
                    // ele.NGAYCAP = moment(ele.NGAYCAP, 'ddd MMM DD HH:mm:ss Z YYYY').format('DD/MM/YYYY')
                ele.LOAI_GT = this.mapLoaiGt(ele.LOAIGT_ID)
            });
        },
        'dsKhachHang.value.LOAIGT_ID': function(newVal){
            this.dsKhachHang.value.LOAI_GT = this.mapLoaiGt(newVal)
        }
    },
    methods: {
        onModalHidden(){
            if(this.$parent.daDongBo)
                this.$parent.hienThiDanhSachDaDongBo()
            else
                this.$parent.hienThiDanhSach()
        },
        async frmLoad(){
            this.loading(true)
            try{
                await API.load_capnhat_giayto(this.axios, {

                }).then(response => {
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
                        this.dsLoaiGt = response.data.data
                    }
                })
                
                if(this.chuyenForm)
                    this.dsKhachHang.list = this.danhSach
                else 
                    await this.layDlKhachHang()
                this.loading(false)

            } catch(e){
                this.$toast.error(e.data.message_detail)
                this.loading(false)
            }
        },
        async layDlKhachHang(){
            this.loading(true)
            try {
                await API.lay_ds_dbkh_chuanhoa_sogt(this.axios, {
                    "vmatinh": this.maTinh
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
                        this.dsKhachHang.list = response.data.data
                        this.isDlChuyenForm = false
                    }
                    else
                        this.dsKhachHang.list = []
                })
            } catch(e) {
                this.loading(false)
                throw e.data.message_detail
            }
        },
        mapLoaiGt(loaigt_id){
            let giayTo = this.dsLoaiGt.filter(obj => {
                return obj.loaigt_id == loaigt_id
            })
            return giayTo.length>0 ? giayTo[0].loai_gt : null
        },
        // upperObjectKeys(obj){
        //     obj
        // },
        async layDlThueBao(khachhang_id){
            this.loading(true)
            try{
                await API.load_danhsach_thuebao(this.axios, {
                    "vkhachhang_id": khachhang_id
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
                        this.dsThueBao.list = response.data.data                       
                    }
                    else 
                        this.dsThueBao.list = []
                })

            } catch(e){
                this.$toast.error(e.data.message_detail)
                this.loading(false)
            }
        },
        async capNhat(){
            const invalid = await this.validateForm()
            if(invalid) {
                return
            }

            this.loading(true)
            try{
                let ds_para = {
                    LOAIGT_ID: this.dsKhachHang.value.LOAIGT_ID,
                    NGAYCAP: this.dsKhachHang.value.NGAYCAP,
                    NOICAP: this.dsKhachHang.value.NOICAP,
                    SO_GT: this.dsKhachHang.value.SO_GT,
                    NGUOI_CN: this.$root.token.getUserName(),
                    KHACHHANG_ID: this.dsKhachHang.value.KHACHHANG_ID
                }
                await API.fn_capnhat_sogt(this.axios, {
                    "ds_para": JSON.stringify(ds_para)
                }).then(async response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data !== null) {
                        let res = JSON.parse(response.data.data)
                        if (res.ERROR_CODE === 1){
                            this.$toast.success(`Cập nhật số giấy tờ: ${this.dsKhachHang.value.SO_GT} thành công !`)
                            await this.layDlKhachHang()
                            if(this.dsKhachHang.list.length == 0)
                                this.$bvModal.hide(this.modalId)
                        }
                        else
                            this.toast.error(res.MESSAGE)
                    }  
                })

            } catch(e){
                this.$toast.error(e.data.message_detail)
                this.loading(false)
            }
        },
        async validateForm(){
            let errorMessage = []
            this.$v.$touch()
            if(!this.$v.dsKhachHang.value.LOAI_GT.required){
                errorMessage.push('Loại giấy tờ không được trống')
            }
            if(!this.$v.dsKhachHang.value.SO_GT.required){
                errorMessage.push('Số giấy tờ không được trống')
            }
            if(!this.$v.dsKhachHang.value.NGAYCAP.required){
                errorMessage.push('Ngày cấp không được trống')
            }
            
            if (errorMessage.length > 0)
                this.$toast.error(errorMessage.join('\n'))
            return this.$v.$invalid
        },
        thoat(){
            this.$bvModal.hide(this.modalId)
        },
        async rowChanged(obj) {
            if(!obj) { 
                return; 
            }
            this.dsKhachHang.value = {...obj}
            await this.layDlThueBao(obj.KHACHHANG_ID)
        },
    }
}
</script>