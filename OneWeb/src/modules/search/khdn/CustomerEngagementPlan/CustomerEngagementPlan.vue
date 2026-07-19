<template src="./template.html"> </template>

<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import moment from 'moment'
import {Icon} from '@iconify/vue2'
import api from './API.js'
import Vue from 'vue'

const baseCol = [
          {fieldName: 'ma_kh', headerText: 'Mã KH', width: '150px'},
          {fieldName: 'ten_kh', headerText: 'Tên khách hàng', width: '200px'},
          {fieldName: 'dia_chi', headerText: 'Địa chỉ khách hang', width: '250px'},
          {fieldName: 'so_dt', headerText: 'SĐT liên hệ', width: '150px'},
          {fieldName: 'mst', headerText: 'Mã số thuế', width: '150px'},
          {fieldName: 'ma_gt', headerText: 'Số giấy tờ', width: '150px'},
          {fieldName: 'khoi_kh', headerText: 'Khối KH', width: '100px'},
          {fieldName: 'loai_kh', headerText: 'Phân loại KH', width: '130px'},
          {fieldName: 'so_ctbh', headerText: 'Số lượng CTBH', width: '140px', textAlign: 'left'},
        ];
export default {
  components: {
    breadcrumb,
    Icon
  },
  data() {
    return {
      DateFormat: 'dd/MM/yyyy',
      txtMaNV: {
        enabled: false,
        value: ''
      },
      cbbLoaiNV: {
        enabled: false,
        value: null,
        options: []
      },
      dateNgayGiao:{
        enabled: false,
        value: new Date(),
      },
      txtHanThucHien: {
        enabled: false,
        value: ''
      },
      cbbDichVu: {
        enabled: false,
        value: null,
        options: []
      },
      cbbLoaiHinh: {
        enabled: false,
        value: null,
        options: []
      },
      txtNDTiepCan: {
        enabled: false,
        value: ''
      },
      tbKHChuaCoKH:{
        data: [],
        columns: [
            ...baseCol,
            {fieldName: 'so_cthd', headerText: 'Chi tiết CTBH',
          template: this.colChiTietCTBH(this),
          width: '130px', textAlign: 'center'},
            {fieldName: 'so_cthd', headerText: 'Kế hoạch',
          template: this.colKeHoach(this),
          width: '130px', textAlign: 'center'},
        ]
      },
      tbKHCoKH: {
        data: [],
        columns: [
          ...baseCol,
          {fieldName: 'so_cthd', headerText: 'Chi tiết CTBH',
          template: this.colChiTietCTBH(this),
          width: '130px', textAlign: 'center'},
        ]
      },
      tbNhiemVu:{
        data: [],
        columns: [
          {fieldName: 'ngay_giao', headerText: 'Ngày giao', width: '150px'}, 
          {fieldName: 'han_th', headerText: 'Hạn thực hiện', width: '150px'},
          {fieldName: 'ma_nv', headerText: 'Mã nhiệm vụ', width: '150px'},
          {fieldName: 'nhanvien', headerText: 'Nhân viên TC', width: '150px'},
          {fieldName: 'sdt', headerText: 'Số điện thoại', width: '150px'},
          {fieldName: 'loai_nv', headerText: 'Loại nhiệm vụ', width: '150px'},
          {fieldName: 'dichvu', headerText: 'Dịch vụ', width: '150px'},
          {fieldName: 'loaihinh', headerText: 'Loại hình', width: '150px'},
        ]
      }
    }
  },
  methods: {
    async LAY_DS(){
      //using mock data for design
      this.tbKHChuaCoKH.data = [
        {
          ma_kh: 'HNI-20028767',
          ten_kh: 'Công ty A',
          dia_chi: 'Lô 1 khu A 21 Điện Biên Phủ Q 3, HCM',
          so_dt: '0123456789',
          mst: '0303490096',
          ma_gt: '123456789',
          khoi_kh: 'GOV',
          loai_kh: 'A1',
          so_ctbh: '2',
          so_cthd: '1',
          so_cttt: '1'
        },
      ]
    },
    colChiTietCTBH(parent){
      return function(){
        return {
          template: {
            template: '<div class="table-button"><Icon icon="akar-icons:info" width="24" height="24"/><Icon class="table-button__effect" icon="akar-icons:info" width="24" height="24"/></div>',
            components: {
              Icon
            },
          }
        }
      }
    },
    colKeHoach(parent){
      return function(){
        return {
          template: {
            template: '<div class="table-button"><Icon icon="gridicons:create" width="24" height="24" /><Icon class="table-button__effect" icon="gridicons:create" width="24" height="24" /></div>',
            components: {
              Icon
            },
          }
        }
      }
    }
  },
  async mounted() {
    await this.LAY_DS()
  }
}
</script>

<style lang="scss" src="./style.scss"></style>
