<template src="./SearchVNEduData.html"></template>
<style scoped src="./SearchVNEduData.scss"></style>

<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component'
import API from './api'
import gridview from '@/components/Shared/gridview'
import DataGridNV from './components/DataGrid'

//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'

import { defineAsyncComponent } from '@vue/composition-api'

export default {
  components: {
    ThemeListEdu: defineAsyncComponent(() => import('./popup/themeListEdu/themeListEdu.vue')),
    breadcrumb,
    VueFlatPickr,
    gridview,
    API,
    DataGridNV },
  name: 'SearchVNEduData',
  created() {},

  mounted() {
    this.init()
  },

  watch: {},

  data() {
    return {
      header: {
        title: 'TRA CỨU THÔNG TIN VNEDU',
        list: [
          { name: 'Tra cứu', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu thông tin vnEdu',
            link: { name: 'Ui.buttons' }
          }
        ]
      },

      id_tinh: 0,
      id_tinh_edu: 0,
      data_edu: false,
      count_edu: 0,
      ds_truong_vnedu: {
        header: [
          {
            headerText: 'ID trường',
            fieldName: 'truong_id',
            allowSorting: false,
            width: '60',
            textAlign: 'left',
            autoFit: 'true',
            allowFiltering: true
          },
          {
            headerText: 'Tên trường',
            fieldName: 'ten_truong',
            allowSorting: false,
            width: '100',
            textAlign: 'left',
            autoFit: 'true',
            allowFiltering: true
          },
          {
            headerText: 'Quận/Huyện',
            fieldName: 'ten_huyen',
            allowSorting: false,
            width: '100',
            textAlign: 'left',
            autoFit: 'true',
            allowFiltering: true
          },
          {
            headerText: 'Cấp',
            fieldName: 'doi_tuong',
            allowSorting: false,
            width: '60',
            textAlign: 'left',
            autoFit: 'true',
            allowFiltering: true
          },
          {
            headerText: 'Mã Moet',
            fieldName: 'moet_id',
            allowSorting: false,
            width: '60',
            textAlign: 'left',
            autoFit: 'true',
            allowFiltering: true
          }
        ],
        list: []
      },
      ds_so_vnedu: {
        header: [
          {
            headerText: 'ID trường',
            fieldName: 'truong_id',
            allowSorting: false,
            width: '60',
            textAlign: 'left',
            autoFit: 'true',
            allowFiltering: true
          },
          {
            headerText: 'Tên trường',
            fieldName: 'ten_truong',
            allowSorting: false,
            width: '100',
            textAlign: 'left',
            autoFit: 'true',
            allowFiltering: true
          },
          {
            headerText: 'Quận/Huyện',
            fieldName: 'ten_huyen',
            allowSorting: false,
            width: '100',
            textAlign: 'left',
            autoFit: 'true',
            allowFiltering: true
          },
          {
            headerText: 'Cấp',
            fieldName: 'doi_tuong',
            allowSorting: false,
            width: '60',
            textAlign: 'left',
            autoFit: 'true',
            allowFiltering: true
          },
          {
            headerText: 'Mã Moet',
            fieldName: 'moet_id',
            allowSorting: false,
            width: '60',
            textAlign: 'left',
            autoFit: 'true',
            allowFiltering: true
          }
        ],
        list: []
      },

      isShowPopup: {
        ThemeListEdu: false
      }
    }
  },

  methods: {
    async init() {},
    async Timkiem_Click() {
      this.data_edu = false
      this.ds_truong_vnedu.list = []
      this.id_tinh = this.$root.token.getPhanVungID()
      var body = {
        p_tinh_id: `${this.id_tinh}`
      }
      var response = await API.sp_lay_ds_tinh_theo_tinh_id_v2(this.axios, body)
      if (response.data.error_code == 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
        var data_dv = response.data.data
        this.id_tinh_edu = data_dv[0].tinh_edu_id
      } else {
        this.$toast.error('Có lỗi khi lấy ID tỉnh VNEDU, liên hệ admin để xử lý!')
        return
      }
      // Lấy dữ liệu từ VNEDU - Đổ vào lưới
      if (this.id_tinh_edu > 0) {
        this.loading(true)
        var data_truong = await API.getListTruong(this.axios, this.id_tinh_edu)
        if (data_truong.data.errorCode == 0 && data_truong.data.data.success == true && data_truong.data.data.msg == 'Thành công' && data_truong.data.data.data.aRoot.length > 0) {
          this.loading(false)
          this.ds_truong_vnedu.list = data_truong.data.data.data.aRoot
          if (data_truong.data.data.data.iCount > 0) {
            this.data_edu = true
            this.count_edu = data_truong.data.data.data.iCount
          }
        } else {
          this.loading(false)
          this.$toast.warning('Không có dữ liệu trường từ vnEDU, liên hệ admin để xử lý!')
        }

        const data ={
          apipath: 'http://s19.zneunuaansgdmyhomework.vnedu.vn/v3/?call=app.doisoat.serv.api.getListTruong&token=75fe20c3c009a340bd3f161316f7925b&tinh_id='+this.tinh_edu_id+'&user_name=vnpt.tvtung&get_school=0',
          apimethod: 'GET',
          apibody: ''
        }
        var data_so = await API.getListSo(this.axios, data)
        console.log(data_so)
        if (data_so.data.errorCode == 0 && data_so.data.data.success == true && data_so.data.data.msg == 'Thành công' && data_so.data.data.data.aRoot.length > 0) {
          this.loading(false)
          this.ds_so_vnedu.list = data_so.data.data.data.aRoot
          if (data_so.data.data.data.iCount > 0) {
            this.data_edu = true
            this.count_edu = data_so.data.data.data.iCount
          }
        } else {
          this.loading(false)
          this.$toast.warning('Không có dữ liệu trường từ vnEDU, liên hệ admin để xử lý!')
        }
      }
    },
    emit_func()
    {
      this.$refs.dlgThemeListEdu.hide()
      this.$refs.dlgThemeListEdu.close()
    },

    async show_themeList_Edu() {
      this.isShowPopup.ThemeListEdu = true
      this.$refs.dlgThemeListEdu.show()
    },
    async closedlgThemeListEdu() {
      this.isShowPopup.ThemeListEdu = false
    },

  }
}
</script>
