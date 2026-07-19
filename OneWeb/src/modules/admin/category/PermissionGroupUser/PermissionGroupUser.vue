<template>
  <div class="main-wrapper">
    <breadcrumb :header="header" />
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="btnPhanQuyenSMCA" :class="{disabled: false}"> <span
            class="icon one-file-plus"></span>Phân quyền duyệt SMCA</a>
        </li>
      </ul>
    </div>


    <div class="page-content">
      <div class="box-form">
        <div class="info-row marb0">
          <div class="key w80">Người dùng</div>
          <div class="value">
            <ejs-dropdownlist
              :allowFiltering="true"
              v-model="nguoidung_selected"
              @change="changeNguoiDung"
              :dataSource="danhsach_nguoidung"
              :filtering="filterSelect"
              :fields="{ text: 'ten_nd', value: 'nguoidung_id' }" />
          </div>
        </div>
      </div>
      <div class="box-move-select-table">
        <div class="box-col box-form">
          <KDataGrid
            :columns="columnsChuaGan"
            :dataSource="dataSourcesChuaGan"
            :showColumnCheckbox="true"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            ref="gridChuaGan"
            :enabledSelectFirstRow="false"
            panelDataHeight="360px" />
        </div>
        <div class="actions">
          <button class="btn btn-main" @click="ganQuyen">
            <span class="-ap icon-plus3"></span>
          </button>
          <button class="btn btn-main" @click="goQuyen">
            <span class="-ap icon-minus3"></span>
          </button>
        </div>
        <div class="box-col box-form">
          <KDataGrid
            :columns="columnsGan"
            :dataSource="dataSourcesGan"
            :showColumnCheckbox="true"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            ref="gridDaGan"
            :enabledSelectFirstRow="false"
            panelDataHeight="360px" />
        </div>
      </div>
    </div>

    <ejs-dialog
      :enableResize='true'
      :allowDragging='true'
      :visible='false'
      ref="dlgPhanQuyenDuyetSMCA"
      :header='dataDLGPhanQuyenDuyetSMCA.header'
      showCloseIcon=true width='50%'
      @close="close_ppPhanquyenDuyetSMCA"
      :isModal="true"
    >
      <template v-if="dataDLGPhanQuyenDuyetSMCA.isShow">
        <ppPhanQuyenDuyetSMCA ref='popPQDuyetSMCA' :danhsach_nguoidung="danhsach_nguoidung"/>
      </template>
    </ejs-dialog>

  </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import PermissionGroupUserAPI from './PermissionGroupUserAPI'
import { DataManager, Query, ODataV4Adaptor, Predicate } from '@syncfusion/ej2-data'
import { defineAsyncComponent } from '@vue/composition-api'
//  "nguoidung_id": "12520",
// #             "ten_nd": "Phạm Ngọc Bích",
// #             "ma_nd": " pnbich.hcm",
export default {
  name: 'PermissionGroupUser',
  components: {
    breadcrumb,
    KDataGrid,
    ppPhanQuyenDuyetSMCA: defineAsyncComponent(() => import('./popup/ppPhanQuyenDuyetSMCA.vue'))
  },
  data() {
    return {
      header: {
        title: 'Phân Quyền người dùng',
        list: [
          {
            name: 'Quản lý người dùng',
            link: { name: 'Ui.cards' },
            active: false
          },
          {
            name: 'Phân quyền ND',
            link: { name: 'Ui.buttons' },
            active: true
          }
        ]
      },
      columnsGan: [
        {
          fieldName: 'ten_quyen',
          headerText: 'Tên quyền đã gán',
          allowFiltering: true
        }
      ],
      dataSourcesGan: [],
      columnsChuaGan: [
        {
          fieldName: 'ten_quyen',
          headerText: 'Tên quyền chưa gán',
          allowFiltering: true
        }
      ],
      dataSourcesChuaGan: [],
      danhsach_nguoidung: [],
      danhsach_nguoidung_SMCA: [],
      nguoidung_selected: null,

      dataDLGPhanQuyenDuyetSMCA: {
        header: 'Phân quyền duyệt SmartCA',
        isShow: false,
        bindData: {},
      }
    }

  },
  methods: {
    ganQuyen() {
      if (this.dataSourcesChuaGan.length == 0 || this.nguoidung_selected == null) {
        return
      }
      let dataCheckedsChuaGan = this.$refs.gridChuaGan.getSelectedRecords()
      if (dataCheckedsChuaGan.length == 0) {
        return
      }
      let data = dataCheckedsChuaGan.map((item) => {
        return {
          NGUOIDUNG_ID: this.nguoidung_selected,
          QUYEN_ID: item.quyen_id
        }
      })
      this.sp_ins_gan_quyen_nd(data)
    },
    goQuyen() {
      if (this.dataSourcesGan.length == 0 || this.nguoidung_selected == null) {
        return
      }
      let dataCheckedsGan = this.$refs.gridDaGan.getSelectedRecords()
      if (dataCheckedsGan.length == 0) {
        return
      }
      let data = dataCheckedsGan.map((item) => {
        return {
          NGUOIDUNG_ID: this.nguoidung_selected,
          QUYEN_ID: item.quyen_id
        }
      })
      this.sp_del_gan_quyen_nd(data)
    },
    changeNguoiDung() {
      if (this.nguoidung_selected != null) {
        this.sp_lay_ds_da_chua_gan_quyen_nd(this.nguoidung_selected)
      } else {
        //clear
        this.dataSourcesGan = []
        this.dataSourcesChuaGan = []
      }
    },
    filterSelect(e) {
      // return result
      var query = new Query()
      let predicate
      //frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('ten_nd', 'contains', e.text.trim(), true)
        //predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate)
      }
      //pass the filter data source, filter query to updateData method.
      e.updateData(this.danhsach_nguoidung, query)
    },
    async lay_danhsach_nguoidung() {
      try {
        this.loading(true)
        let response = await PermissionGroupUserAPI.lay_danhsach_nguoidung(this.axios)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data.map((item) => Object.assign(item, {
            ten_nd: item.ma_nd + ' - ' + item.ten_nd,
            text: item.ma_nd + ' - ' + item.ten_nd,
            id: item.nguoidung_id,
          }))
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async sp_lay_ds_da_chua_gan_quyen_nd(nguoidung_id) {
      try {
        this.loading(true)
        // this.dataSourcesGan=[]
        // this.dataSourcesChuaGan=[]
        let response = await PermissionGroupUserAPI.sp_lay_ds_da_chua_gan_quyen_nd(this.axios, nguoidung_id)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          let result = response.data.data
          if (result.ds) {
            this.dataSourcesGan = result.ds
          } else {
            this.dataSourcesGan = []
          }
          if (result.ds1) {
            this.dataSourcesChuaGan = result.ds1
          } else {
            this.dataSourcesChuaGan = []
          }
        } else {
          this.dataSourcesGan = []
          this.dataSourcesChuaGan = []
        }
      } catch (error) {
        this.loading(false)
        if (error.data && error.data.message) {
          this.$toast.error(error.data.message)
        } else if (error.response && error.response.data && error.response.data.message) {
          this.$toast.error(error.response.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi khi lấy danh sách')
        }
      }
    },
    async sp_ins_gan_quyen_nd(data) {
      try {
        this.loading(true)
        let response = await PermissionGroupUserAPI.sp_ins_gan_quyen_nd(this.axios, data)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          if (response.data.data == '0') {
            this.$toast.success('Gán quyền người dùng thành công')
            this.sp_lay_ds_da_chua_gan_quyen_nd(this.nguoidung_selected)
          } else {
            this.$toast.info(response.data.data)
          }
        } else {
          if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
          } else {
            this.$toast.error('Đã xảy ra lỗi, gán quyền không thành công')
          }

        }
      } catch (error) {
        this.loading(false)
        if (error.data && error.data.message) {
          this.$toast.error(error.data.message)
        } else if (error.response && error.response.data && error.response.data.message) {
          this.$toast.error(error.response.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi, gán quyền không thành công')
        }
      }
    },
    async sp_del_gan_quyen_nd(data) {
      try {
        this.loading(true)
        let response = await PermissionGroupUserAPI.sp_del_gan_quyen_nd(this.axios, data)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          if (response.data.data == '0') {
            this.$toast.success('Gỡ quyền người dùng thành công!')
            this.sp_lay_ds_da_chua_gan_quyen_nd(this.nguoidung_selected)
          } else {
            this.$toast.error(response.data.data)
          }
        } else {
          if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
          } else {
            this.$toast.error('Đã xảy ra lỗi, gỡ quyền không thành công')
          }

        }
      } catch (error) {
        this.loading(false)
        if (error.data && error.data.message) {
          this.$toast.error(error.data.message)
        } else if (error.response && error.response.data && error.response.data.message) {
          this.$toast.error(error.response.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi, gỡ quyền không thành công')
        }
      }
    },
    async initDuLieu() {
      this.danhsach_nguoidung = await this.lay_danhsach_nguoidung()
    },

    btnPhanQuyenSMCA() {
      // if (this.nguoidung_selected === null || this.nguoidung_selected === undefined || this.nguoidung_selected === '')
      // {
      //   return this.$toast.error('Hãy chọn nhân viên trước!')
      // }
      // this.dataDLGPhanQuyenDuyetSMCA.bindData = this.danhsach_nguoidung.find(obj => obj.nguoidung_id == this.nguoidung_selected)

      // console.log(this.dataDLGPhanQuyenDuyetSMCA)
      this.dataDLGPhanQuyenDuyetSMCA.isShow = true
      this.$refs.dlgPhanQuyenDuyetSMCA.show()
    },

    close_ppPhanquyenDuyetSMCA(){
      this.dataDLGPhanQuyenDuyetSMCA.isShow = false
      this.$refs.dlgPhanQuyenDuyetSMCA.hide()
    }

  },
  mounted() {
    setTimeout(() => {
      this.initDuLieu()
    }, 500)
  }
}
</script>
