<template src="./template.html"></template>
<style scoped src="./style.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueNav from '@/modules/search/subscriber/components/elements/VueNav'
import FormTimKiem from './part/FormTimKiem'
import api from './api'
export default {
  components: {
    breadcrumb,
    FormTimKiem
  },
  data () {
    return {
      form: {
        ma_gd: '',
        ma_tb: '',
        ma_kh: '',
        ten_kh: '',
        diachi_kh: '',
        ten_tb: '',
        loaitb_id: '',
        phieu_id: '',
        ngay_th: ''
      },
      update: {
        donvi_dl_id: 11,
        hdtb_id: '1461637',
        dichvuvt_id: '10',
        ngay_ht: '23/07/2021 14:20:20',
      },
      header: {
        title: 'CẬP NHẬT KẾT QUẢ CHUYỂN ĐỔI LOẠI HÌNH GPHONE',
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
            label: 'Số máy/Acc',
            filterAble: true,
            key: 'ma_tb'
          },
          {
            label: 'Tên thuê bao',
            filterAble: true,
            key: 'ten_tb'
          },
          {
            label: 'Địa chỉ LĐ',
            filterAble: true,
            key: 'ma_kh'
          },
          {
            label: 'Loại hình TB',
            filterAble: true,
            key: 'loaihinh_tb'
          },
          {
            label: 'Ngày HT',
            filterAble: true,
            key: 'ngay_ht'
          }
        ],
        all_data: []
      }
    }
  },
  methods: {
    capnhap() {
      this.table.loading = true
      api.traCuuPhatTrien(this.axios, this.form).then(response => {
        if(response.data.err_code && response.data.err_code == "BSS-00000200") {
          this.table.options = response.data.data
        }
      }).finally(() => {
        this.table.loading = false
      })
    }
  }
}
</script>
