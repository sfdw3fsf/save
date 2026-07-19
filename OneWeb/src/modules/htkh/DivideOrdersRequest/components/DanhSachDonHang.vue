<template>
  <div class="box-form">
    <div class="legend-title">Danh sách đơn hàng</div>
    <div class="tab-checkbox" :class="{ 'hidden': hidden.isHdnTabOne }" id="tab1">
      <div class="table-content manual-fill">
        <DataGrid :columns="danhSachDonHang.headers" ref="danhSachDonHang" :dataSource="danhSachDonHang.dataSources" :showColumnCheckbox="true" :enabledSelectFirstRow="true"
          :enable-paging-server="false" :allowPaging="true" :showFilter="true"
          @selectedRowChanged="onSelectedRowChanged" />
      </div>
    </div>
   
  </div>
</template>

<script>
import api from '../api.js'
import apiHelper from '../api.helper.js'
const PHIEU_MOI = 1
const PHIEU_TRA_LAI = 2
const CHUYEN_TC = 3
export default {

  name: 'DanhSachDonHang',
  props: {
    trangThaiPhieu: {
      type: Number,
      default: 0,
    },
    ungDungId: {
      type: Number | String,
      default: 0,
    },
    
  },
  data() {
    return {
      danhSachDonHang: {
        headers: [
          { fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, visible: true },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true, visible: true }, // thieu
          { fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true, visible: true },
          { fieldName: 'ten_kh', headerText: 'Tên KH', allowFiltering: true, visible: true },
          { fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', allowFiltering: true, visible: true },
          { fieldName: 'so_dt', headerText: 'Số ĐT', allowFiltering: true, visible: true },
          { fieldName: 'ma_gd_tc', headerText: 'Mã GD (Thi công)', allowFiltering: true, visible: true }, // thieu
          { fieldName: 'ten_kieuld', headerText: 'Kiểu YC', allowFiltering: true, visible: true },
          { fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, visible: true },
          { fieldName: 'nhanvien_th', headerText: 'NV địa bàn', allowFiltering: true, visible: true },
          { fieldName: 'giaoviec', headerText: 'Giao việc', allowFiltering: true, visible: true },
          // Grid Phieu tra lai
          { fieldName: 'lydotra', headerText: 'Lý do trả', allowFiltering: true, visible: false },
          { fieldName: 'nd_tra', headerText: 'Nội dung trả', allowFiltering: true, visible: false },
          //Grid Xu ly don hang
          { fieldName: 'nv_giamsat', headerText: 'Nhân viên giám sát', allowFiltering: true, visible: true },
          { fieldName: 'xacnhan_xldh', headerText: 'Xác nhận XLDH', allowFiltering: true, visible: true },
          { fieldName: 'thoigian_xldh', headerText: 'Thời gian xác nhận XLDH', allowFiltering: true, visible: true },
          { fieldName: 'nhanvien_th', headerText: 'User xác nhận XLDH', allowFiltering: true, visible: true },
          { fieldName: 'ten_goikt', headerText: 'SLA giao hàng', allowFiltering: true, visible: true },
          // End grid Phieu tra lai, grid phieu TC
          { fieldName: 'phieucskh_id', headerText: 'ID', allowFiltering: true, visible: false },
          { fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true, visible: false },
          { fieldName: 'nhom_lnc', headerText: 'Nhóm NC', allowFiltering: true, visible: false },
          { fieldName: 'phanloai', headerText: 'Phân loại', allowFiltering: true, visible: false },
          { fieldName: 'ungdung', headerText: 'Ứng dụng', allowFiltering: true, visible: false }
        ],
        dataSources: [],
        selectedRow: {}
      },
      hidden: {
        isHdnTabOne: false,
        isHdnTabTwo: true,
        isHdnTabThree: true
      },

    }
  },
  computed: {



  },

  methods: {

    async onInitComponent(params) {
      console.log("onInitComponent DanhSachDonHang", params);
      this.loading(true)
      const response = await api.getDanhSachDonHang(this.axios, params);
      this.danhSachDonHang.dataSources = apiHelper.getDataFromResponseApiReturnArray(response);
      if(this.danhSachDonHang.dataSources.length == 0){
        this.$toast.warning("Không tìm thấy đơn hàng!")
        this.$emit('on-select-row', null);
      }
      this.loading(false)
    },
    onSelectedRowChanged(val) {
      this.$emit('on-select-row', val);
    },
    onSetColumnVisible (fieldName, isVisible) {
      this.danhSachDonHang.headers.find(header => header.fieldName === fieldName).visible = isVisible
    },
    onSetColumnName (fieldName, name) {
      this.danhSachDonHang.headers.find(header => header.fieldName === fieldName).headerText = name
    },
    getSelectedRecords() {
      return this.$refs.danhSachDonHang.getSelectedRecords()
    }
  },
  watch: {
    trangThaiPhieu:{
      handler: function (val, oldVal) {
        if (val === PHIEU_MOI) {
          this.onSetColumnVisible('nd_tra', false)
          this.onSetColumnVisible('lydotra', false)
        }
        if (val === PHIEU_TRA_LAI){
          this.onSetColumnVisible('nd_tra', true)
          this.onSetColumnVisible('lydotra', true)
        }
        if (val === CHUYEN_TC){
          this.onSetColumnVisible('nd_tra', false)
          this.onSetColumnVisible('lydotra', false)
        }

      },
      deep: true
    },
    ungDungId:{
      handler: function (val, oldVal) {
        if (val === 5) {
          this.onSetColumnVisible('nd_tra', false)
          this.onSetColumnVisible('lydotra', false)
          this.onSetColumnVisible('nhanvien_th', false)
          this.onSetColumnVisible('ghichu', false)
          this.onSetColumnVisible('giaoviec', false)
          this.onSetColumnVisible('phieucskh_id', true)
          this.onSetColumnVisible('ma_tb', true)
          this.onSetColumnVisible('nhom_lnc', true)
          this.onSetColumnVisible('phanloai', true)
          this.onSetColumnVisible('ungdung', true)
          this.onSetColumnName('ngay_yc', 'Ngày CS')
        }
        else{
          this.onSetColumnVisible('phieucskh_id', false)
          this.onSetColumnVisible('ma_tb', false)
          this.onSetColumnVisible('nhom_lnc', false)
          this.onSetColumnVisible('phanloai', false)
          this.onSetColumnVisible('ungdung', false)

          if (this.trangThaiPhieu === CHUYEN_TC) {
 
            this.onSetColumnVisible('nd_tra', false)
            this.onSetColumnVisible('lydotra', false)
          } else {
     
            this.onSetColumnVisible('nd_tra', true)
            this.onSetColumnVisible('lydotra', true)
          }
   
          this.onSetColumnVisible('nhanvien_th', true)
          this.onSetColumnVisible('ghichu', true)
          this.onSetColumnVisible('giaoviec', true)
   
          this.onSetColumnName('ngay_yc', 'Ngày YC')
        }

      },
      deep: true
    }
  }


}
</script>