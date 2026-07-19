<template>
    <b-modal 
        :id="modalId"
        size="xl"
        no-close-on-backdrop
        hide-footer 
        hide-header 
        hide-header-close 
        body-class="modal-body p-0"
        @shown="frmLoad">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title"><span class="icon one-notepad"></span> Danh sách thuê bao đính kèm</div>
                <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide(modalId)">
                </div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="#" @click.prevent="chapNhan">
                            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận
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
                <div class="table-content">
                    <DataGrid v-bind:columns="[
                        {
                            fieldName: 'chon',
                            headerText: 'Chọn',
                            allowFiltering: false,
                            allowSorting: false,
                            freeze: 'left',
                            width: 80,
                            textAlign: 'Center',
                            template: cbDangKyTemplate(this)
                        },
                        {
                            fieldName: 'ma_tt',
                            headerText: 'Mã TT',
                            allowFiltering: true,
                            allowSorting: false,
                            freeze: 'left',
                            width: 150
                        },
                        {
                            fieldName: 'ma_tb',
                            headerText: 'Số máy/Acc',
                            allowFiltering: true,
                            allowSorting: false,
                            freeze: 'left',
                            width: 150
                        },
                        {
                            fieldName: 'ten_tb',
                            headerText: 'Tên thuê bao',
                            allowFiltering: true,
                            allowSorting: false
                        },
                        {
                            fieldName: 'diachi_tb',
                            headerText: 'Địa chỉ thuê bao',
                            allowFiltering: true,
                            allowSorting: false,
                            cssClass: 'font-weight-bold'
                        },
                        {
                            fieldName: 'diachi_ld',
                            headerText: 'Địa chỉ lắp đặt',
                            allowFiltering: true,
                            allowSorting: false
                        },
                        {
                            fieldName: 'loaihinh_tb',
                            headerText: 'Loại hình',
                            allowFiltering: true,
                            allowSorting: false,
                            width: 150
                        },
                        {
                            fieldName: 'ngay_sd',
                            headerText: 'Ngày SD',
                            allowFiltering: true,
                            allowSorting: false,
                            width: 100
                        },
                        {
                            fieldName: 'trangthai_tb',
                            headerText: 'Trạng thái',
                            allowFiltering: true,
                            allowSorting: false,
                            width: 150
                        },
                        {
                            fieldName: 'doanhthu',
                            headerText: 'Doanh thu',
                            allowFiltering: true,
                            allowSorting: false,
                            type: 'number',
                            format:'N0',
                            width: 150
                        },
                        {
                            fieldName: 'goi_dk',
                            headerText: 'Gói nâng cấp',
                            clipMode: 'Clip',
                            allowFiltering: false,
                            allowSorting: false,
                            template: cbGoiNangCapTemplate(this)
                        },
                        {
                            fieldName: 'tengoi_ht',
                            headerText: 'Gói hiện tại',
                            allowFiltering: true,
                            allowSorting: false
                        },
                        {
                            fieldName: 'doanhthu_dk',
                            headerText: 'Doanh thu dự kiến',
                            allowFiltering: true,
                            allowSorting: false,
                            type: 'number',
                            format:'N0',
                            width: 150,
                            template: txtDoanhThuDkTemplate(this)
                        },
                    ]" v-bind:dataSource="dsThueBao" :showFilter="true" :allowPaging="true"
                        dataKeyField="ma_tb" :enablePagingServer="false"
                        ref="gridCTThueBao" :showColumnCheckbox="false">
                    </DataGrid>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
import moment from 'moment'
import { L10n, setCulture } from '@syncfusion/ej2-base'
import ComboboxGrid from "@/components/Controls/ComboboxGrid"
import { Checkbox } from 'element-ui'
import API from './../API.js'
import EventBus from '@/utils/eventBus'
import Vue from 'vue'

L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị ",
      Item: "Bản ghi",
      Items: "Bản ghi",
      FilterbarTitle: "",
    },
  },
});

export default {
    name: "ModalCTThueBao",
    components: { L10n, setCulture },
    props: ["modalId", "khachHangId", "danhSach"],
    data() {
        return {
            dsThueBao: [],
            dsThueBaoTmp: [],
            thueBaoChon: [],
        }
    },
    methods: {
        cbDangKyTemplate(parent) {
            return function () {
                return { 
                    template : {
                        template: `<div class="check-action"><input type="checkbox" class="check" v-model="value"
                        @change="rowCheckedChanged"><span class="name"></span></div>`,                        
                        data () { 
                            return { 
                                data: {},
                                parent: parent,
                                value: null
                            }
                        },
                        methods: {
                            rowCheckedChanged() {  
                                let rowData = this.parent.dsThueBao.find(item => item.thuebao_id == this.data.thuebao_id)
                                rowData.chon = this.value ? '1' : '0'   
                                EventBus.$emit('rowCheckedChanged', this.data);
                            }
                        },
                        mounted() {
                            this.value = this.data.chon == '1' ? true : false
                        }
                    }
                }
            }
        },
        cbGoiNangCapTemplate(parent) {
            return function () {
                return { 
                    template : {
                        template: `<div class="select-custom">
                            <SelectExt
                                v-model="value"
                                :settings="{dropdownParent: parent.$refs['ref-modal-content']}"
                                placeholder="[Chưa chọn giá trị]"
                                :dataSource="dataSource"
                                dataValueField="goi_id"
                                dataTextField="tengoi2" />
                        </div>`,                        
                        data () { 
                            return { 
                                data: {},
                                value: null,
                                parent: parent,
                                dataSource: []
                            }
                        },
                        methods: {
                            chonGoiNangCap() {    
                                // this.data.goidk_id = this.value             
                                EventBus.$emit('chonGoiNangCap', this.data);
                            }
                        },
                        watch: {
                            value(newVal) {
                                let rowData = this.parent.dsThueBao.find(item => item.thuebao_id == this.data.thuebao_id)
                                rowData.goidk_id = !newVal ? null : parseInt(newVal)
                                rowData.goi_dk = !newVal ? null : this.data.ds_goi_cuoc.find(item => item.goi_id == rowData.goidk_id).tengoi
                            }
                        },
                        mounted() {
                            this.dataSource = this.data.ds_goi_cuoc.map(item => { return {...item} })
                            this.value = this.data.goidk_id
                        }
                    }
                }
            }
        },
        txtDoanhThuDkTemplate(parent) {
            return function () {
                return { 
                    template : {
                        template: `<div class="value">
                            <input v-model="value" type="text" class="form-control text-right" @change="doanhThuDkChanged">
                        </div>`,                        
                        data () { 
                            return { 
                                data: {},
                                parent: parent,
                                value: null
                            }
                        },
                        methods: {
                            doanhThuDkChanged() {    
                                //this.data.doanhthu_dk = this.value   
                                let rowData = this.parent.dsThueBao.find(item => item.thuebao_id == this.data.thuebao_id)
                                rowData.doanhthu_dk = this.value        
                                EventBus.$emit('doanhThuDkChanged', this.data);
                            }
                        },
                        watch: {
                            value(newVal) {
                                this.value = !newVal ? 0 : parseInt(newVal)
                            }
                        },
                        mounted() {
                            this.value = this.data.doanhthu_dk
                        }
                    }
                }
            }
        },
        async frmLoad(){
            this.loading(true)
            try{
                await API.lay_ds_dbtb_theo_khid_v3(this.axios, {
                    "in_khachhang_id": this.khachHangId
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        this.dsThueBaoTmp = response.data.data
                        this.dsThueBaoTmp.forEach(ele => {
                            ele.ngay_sd = ele.ngay_sd != null ? moment(ele.ngay_sd, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY') : null
                        })

                        if(this.danhSach && this.danhSach.length>0) {
                            var ds = this.danhSach
                            this.dsThueBaoTmp.forEach(function (item) {
                                let rec = ds.filter(x => x.thuebao_id === item.thuebao_id)
                                if (rec.length > 0) {
                                    item.chon = '1'
                                    item.goidk_id = rec[0].goidk_id
                                    item.doanhthu_dk = rec[0].doanhthu_dk
                                } else {
                                    item.chon = '0'
                                }
                            });
                        } 
                    }
                    else{
                        this.dsThueBaoTmp = []
                    }
                })

                for(var i = 0; i < this.dsThueBaoTmp.length; i ++){
                    const ele = this.dsThueBaoTmp[i]
                    const ds = await this.layDsGoiCuoc(ele.loaitb_id, ele.thuebao_id)
                    this.dsThueBaoTmp[i].ds_goi_cuoc = ds.map(item => ({
                        ...item,
                        tengoi2: item.goi_id + ' - ' + item.tengoi
                    }))
                }
                this.dsThueBao = this.dsThueBaoTmp
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
            finally{
                this.loading(false)
            }
        },
        async layDsGoiCuoc(loaiTbId, thueBaoId){
            this.loading(true)
            try{
                return await API.lay_goi_cuoc_ban_tbi(this.axios, {
                    "vloaihinhtb_id": loaiTbId,
                    "vthuebao_id": thueBaoId
                }).then(response => {
                    if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
                        return response.data.data
                    }
                    else{
                        return []
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
            }
            finally{
                this.loading(false)
            }
        },
        chapNhan(){
            this.thueBaoChon = this.dsThueBao.filter(x=> x.chon=='1')
            this.$emit('form-close', this.thueBaoChon)
        },
        thoat(){
            this.$bvModal.hide(this.modalId)
        },
    }
}
</script>