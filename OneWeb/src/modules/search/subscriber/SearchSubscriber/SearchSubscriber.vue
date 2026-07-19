<template src="./template.html"></template>
<style scoped src="./style.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueNav from '../components/elements/VueNav'
import FormTimKiem from './part/FormTimKiem'
import moment from "moment";
import api from './api'
import ExportDataModal from "@/modules/contract/profile/ExportData/ExportDataModal.vue"
export default {
  components: {
    breadcrumb,
    FormTimKiem,
    ExportDataModal
  },
  data () {
    return {
      ds: [],
      ckDonvi: 0,
      form: {
        denNgay: moment(new Date()).format("DD/MM/YYYY"),
        dichVuVTId: '0',
        donViId: '0',
        loaiHDId: '0',
        tuNgay: moment(new Date()).format("DD/MM/YYYY"),
        userCN: null,
        ckDonvi: 0
      },
      header: {
        title: 'Tra cứu phát triển',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      table: {
        loading: false,
        options: [],
        config: [
          {
            headerText: 'Số máy/Acc',
            allowFiltering: true,width: '100',
            fieldName: 'ma_tb'
          },
          {
            headerText: 'Loại hình',
            allowFiltering: true,width: '100',
            fieldName: 'loaihinh_tb'
          },
          {
            headerText: 'Mã KH',
            allowFiltering: true,width: '100',
            fieldName: 'ma_kh'
          },
          {
            headerText: 'Tên thuê bao',
            allowFiltering: true,width: '100',
            fieldName: 'ten_tb'
          },
          {
            headerText: 'Ngày hoàn thành',
            allowFiltering: true,width: '100',
            fieldName: 'ngay_ht'
          },
          {
            headerText: 'Ngày yêu cầu',
            allowFiltering: true,width: '100',
            fieldName: 'ngay_yc'
          },
          {
            headerText: 'Địa chỉ LĐ',
            allowFiltering: true,width: '100',
            fieldName: 'diachi_ld'
          },
          {
            headerText: 'Trạm VT',
            allowFiltering: true,width: '100',
            fieldName: 'tram_vt'
          },
          {
            headerText: 'Người CN',
            allowFiltering: true,width: '100',
            fieldName: 'nguoi_cn'
          },
          {
            headerText: 'NV phát triển',
            allowFiltering: true,width: '100',
            fieldName: 'nhanvien_pt'
          },
          {
            headerText: 'ĐV phát triển',
            allowFiltering: true,width: '100',
            fieldName: 'donvi_pt'
          }
        ],
        all_data: []
      }
    }
  },
  methods: {
    timkiem() {
      this.traCuuPhatTrien()
    },
    xuatfile() {
      this.$refs.ExportDataModal.showModal()
    },
    traCuuPhatTrien () {
      this.loading(true)
      console.log(this.form)
      if(this.form.ckDonvi == 0) {
        this.form.donViId = null
      }
      api.traCuuPhatTrien(this.axios, this.form).then(response => {
        this.table.options = response.data.data
      }).finally(() => {
        this.loading(false)
      })
    }
  }
}
</script>
