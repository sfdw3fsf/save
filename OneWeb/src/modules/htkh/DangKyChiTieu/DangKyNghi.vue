<template>
  <div class="row">
    <!-- Thông tin tìm kiếm -->

    <div class="box-form " style="width: 50%;">
      <div class="row">
        <div class="col-6">
          <div class="info-row" >
            <div class="key w200">
              <span class="name">Từ ngày</span>
            </div>
            <div class="value" style="padding-left: 2px;">
              <date-picker format="DD/MM/YYYY" v-model="dtpFromDate" type="date"  :disabled-date="disabledFromRange">
                <template #icon-calendar>
                  <span class="icon one-calendar"></span>
                </template>
              </date-picker>
            </div>
          </div>
        </div>

        <div class="col-6">
          <div class="info-row pull-right" style="padding-left: 25px;">
            <div class="key w200">
              <span class="name">Đến ngày</span>
            </div>
            <div class="value right">
              <date-picker format="DD/MM/YYYY" v-model="dtpToDate" type="date"  :disabled-date="disabledToRangeDate">
                <template #icon-calendar>
                  <span class="icon one-calendar"></span>
                </template>
              </date-picker>
            </div>
          </div>
        </div>
      </div>

      <div class="info-row">
        <div class="key w200">Đơn vị</div>
        <div class="value">
          <div class="select-custom">
            <SelectExt dataValueField="donvi_id" dataTextField="ten_dv" v-model="cboDonVi.value" :settings="{ dropdownParent: $refs['DonViId'] }" :dataSource="cboDonVi.dataSources" />
          </div>
        </div>
      </div>

      <div class="info-row">
        <div class="key w200">Tên nhân viên</div>
        <div class="value">
          <div class="select-custom">
            <SelectExt dataValueField="nhanvien_id" dataTextField="ten_nv" v-model="cboNhanVien.value" :settings="{ dropdownParent: $refs['statusId'] }" :dataSource="cboNhanVien.dataSources" />
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w200">Lý do</div>
        <div class="value">
          <div class="select-custom">
            <SelectExt dataValueField="lydo_id" dataTextField="lydo" v-model="cboLyDo.value" :settings="{ dropdownParent: $refs['statusId'] }" :dataSource="cboLyDo.dataSources" />
          </div>
        </div>
      </div>

      <div class="info-row">
        <div class="key">Nội dung</div>
        <div class="value">
            <textarea class="form-control" placeholder="Nhập nội dung" rows="3" v-model="txtNoiDung"></textarea>
        </div>
      </div>
    </div>
    <!-- danh sach ket quả -->
    <div class="row">
      <div class="box-form">
        <div class="legend-title">
          <span>Danh sách đăng ký nghỉ</span>
        </div>
        <div class="tab-checkbox">
          <div class="table-content manual-fill">
            <DataGrid :columns="danhSachKetQua.headers" :dataSource="danhSachKetQua.dataSources" :showColumnCheckbox="false"  :enable-paging-server="false" :allowPaging="true" :showFilter="true" @selectedRowChanged="onSelectedRowChanged" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import DatePicker from 'vue2-datepicker'
import { DropDownTreePlugin } from "@syncfusion/ej2-vue-dropdowns";

import moment from 'moment'
import API from './api'
import apiHelper from './api.helper'
// import Utils from './utils'
import _ from 'lodash'
export default {
    name: 'DangKyNghi',
    components: {  DatePicker, DropDownTreePlugin},
    props: {
    isNew: {
      type: Boolean,
      default: true
    }
  },
    data() {
        return {
            cboDonVi: {
                dataSources: [],
                value: 0
            },
            cboNhanVien: {
                dataSources: [],
                value: 0
            },
            cboLyDo: {
                dataSources: [],
                value: 0
            },
            txtNoiDung: "",

            dtpFromDate: new Date(moment().add(1, 'days')),
            dtpToDate:   new Date(moment().add(1, 'days')), // new Date(moment().add(1, 'days')),
            
            danhSachKetQua: {
            headers: [
                { fieldName: 'ngay_dk', headerText: 'Ngày ĐK', allowFiltering: true, visible: true },
                { fieldName: 'lydo', headerText: 'Lý do', allowFiltering: true, visible: true }, // thieu
                { fieldName: 'ngay_cn', headerText: 'Ngày cập nhật', allowFiltering: true, visible: true },
                { fieldName: 'ten_nv', headerText: 'Tên Nhân Viên', allowFiltering: true, visible: true },
                { fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true, visible: true }, // thieu
            ],
            dataSources: [],
            selectedRow: null
        },

        }
    },
    computed: {
        phanVungId() {
            return +this.$root.token.getPhanVungID()
        },
        donViId() {
            return +this.$root.token.getDonViID()
        },
        nhanVienId() {
            return +this.$root.token.getNhanVienID()
        },



    },
    async created() {

    },
    async mounted() {
        await Promise.all([this.getCombDonvi(), this.getCombLyDoNghi(), this.getDanhSachDKNghi()])
    },
    methods: {

        async getCombDonvi() {
            const response = await API.getDonVi(this.axios)
            this.cboDonVi.dataSources = apiHelper.getDataFromResponseApiReturnArray(response) || []
            if (this.cboDonVi.dataSources.length > 0)  this.cboDonVi.value = this.cboDonVi.dataSources[0].donvi_id
                
            
        },

       async getCombNhanVien(donViId){
            this.cboNhanVien.value = 0
            const response = await API.getNhanVien(this.axios, donViId)
            this.cboNhanVien.dataSources = apiHelper.getDataFromResponseApiReturnArray(response) || []
            // if (this.cboNhanVien.dataSources.length > 0)  this.cboNhanVien.value = this.cboNhanVien.dataSources[0].nhanvien_id
           
        },

        async getCombLyDoNghi(){
            const response = await API.getLyDoNghi(this.axios)
            this.cboLyDo.dataSources = apiHelper.getDataFromResponseApiReturnArray(response) || []
        },
        async onSelectedRowChanged(item) {
            if(!item) return
            console.log(item);
            this.cboDonVi.value = item.donvi_id
            this.cboNhanVien.value = item.nhanvien_id
            this.cboLyDo.value = item.lydo_id
            this.txtNoiDung = item.noidung
            this.danhSachKetQua.selectedRow = item;

            this.dtpFromDate = new Date(moment(item.ngay_dk, "DD/MM/YYYY"))
            this.dtpToDate = new Date(moment(item.ngay_dk, "DD/MM/YYYY"))
            this.$emit('isNew-change', false)
        },

        async getDanhSachDKNghi(){

            const response = await API.getDanhSachDkNghi(this.axios, this.$root.token.getUserName())
            this.danhSachKetQua.dataSources = apiHelper.getDataFromResponseApiReturnArray(response) || []
        },

        async saveClick(){
          if(this.isNew) return this.save()
           
        },


        async deleteClick(){
            if(!this.danhSachKetQua.selectedRow) return  this.$toast.warning('Vui lòng chọn dòng cần xóa')
            const { rid, ngay_dk}     = this.danhSachKetQua.selectedRow;
            if(new Date(moment(ngay_dk, "DD/MM/YYYY")) < new Date(moment().subtract(1, 'days'))) return this.$toast.warning('Không thể xóa dữ liệu cũ')
         
            try {
                const response = await API.deleteDKNghi(this.axios, rid)
                if(apiHelper.isResponseApiSuccess(response)){
                    this.$toast.success('Xóa thành công')
                    await this.getDanhSachDKNghi()
                }   
            } catch (error) {
                console.log(error);
                return this.$toast.error('Có lỗi xảy ra')
            }
        },

        async save(){
          if(!this.cboDonVi.value) return this.$toast.warning('Vui lòng chọn đơn vị');
            if(!this.cboNhanVien.value) return this.$toast.warning('Vui lòng chọn nhân viên');
            if(!this.cboLyDo.value) return this.$toast.warning('Vui lòng chọn lý do');
            if(this.dtpFromDate > this.dtpToDate) return this.$toast.warning('Ngày bắt đầu không được lớn hơn ngày kết thúc');


            const days = apiHelper.calculateData(this.dtpToDate, new Date() );
         
            if(days >30 ) return this.$toast.warning('Bạn không được chọn quá 30 ngày')
        
            try {
                this.loading(true)
                const input = this.getInputDataDangKyNghi()
        
                const response = await API.saveDKNghi(this.axios, JSON.stringify(input))
                this.loading(false)
                const strError = apiHelper.getDataFromResponseApiReturnRaise(response)
                if(strError == 'OK'){ 
                    this.$toast.success('Đăng ký nghỉ thành công')
                    await this.getDanhSachDKNghi()
                }
                else this.$toast.warning(strError)
            } catch (error) {
                console.log(error);
                this.loading(false)
                return this.$toast.error('Có lỗi xảy ra')
            }
        },

        clear(){
            this.dtpFromDate =new Date(moment().add(1, 'days'));
            this.dtpToDate = new Date(moment().add(1, 'days'));
            this.txtNoiDung = "";
            this.cboLyDo.value = 0;
            this.cboDonVi.value = this.cboDonVi.dataSources[0]?.donvi_id || 0;
            this.cboNhanVien.value = 0;
        },

        getInputDataDangKyNghi(){
            const days = apiHelper.calculateData(this.dtpFromDate, this.dtpToDate)  
    
            let data = []
            for (let i = 0; i < days + 1; i++) {
                const date = moment(this.dtpFromDate).add(i, 'days').format('DD/MM/YYYY')
                const pram = {
                    ngay_dk: date,
                    donvi_id: this.cboDonVi.value,
                    nhanvien_id: this.cboNhanVien.value,
                    lydo_id: this.cboLyDo.value,
                    noidung: this.txtNoiDung
                }
                data.push(pram)
             
            }
            return  data 
        },
        disabledToRangeDate(date) {
            const currentDate = new Date(moment(this.dtpFromDate));
            const thirtyDaysFromNow = new Date(moment().add(30, 'days'));

            return date <=  currentDate|| date > thirtyDaysFromNow;
        },
        disabledFromRange(date) {

            const currentDate = new Date(moment());
            const thirtyDaysFromNow = new Date(moment().add(30, 'days'));

            return date <= currentDate || date > thirtyDaysFromNow;
        },
    },

    watch: {
        'cboDonVi.value': function (val) {
            this.getCombNhanVien(val)
        },
       

    }
}

</script>
