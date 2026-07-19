<template>
  <div id="tra-cuu-theo-so-lo-phieu-psis">
    <div>
      <div class="list-actions-top">
        <ul class="list">
          <li v-if="tsbtnGhiLai.visible" :class="{ disabledInput: !tsbtnGhiLai.enabled }" @click="tsbtnGhiLai_Clicked">
            <a> <span class="icon one-save"></span>{{ tsbtnGhiLai.text }}</a>
          </li>
        </ul>
      </div>
      <div class="popup-body page-content">
        <div class="box-move-select-table">
          <div class="box-col box-form">
            <div class="legend-title">Vị trí đã gán</div>
            <div class="grid-ext grid-ext-freeze">
              <DataGrid
                ref="gridChucDanhDaGan"
                panelDataHeight="250px"
                :columns="gridChucDanhDaGan.header"
                :dataSource="gridChucDanhDaGan.list"
                :enabledSelectFirstRow="false"
                :showFilter="true"
                :enablePagingServer="false"
                :showColumnCheckbox="true"
                :allowPaging="true"
                @rowSelected="gridChucDanhDaGan_rowSelected"
                @selectedItemsChanged="gridChucDanhDaGan_selectedItemsChanged"
              />
            </div>
          </div>
          <div class="actions">
            <button class="btn" @click="tsbtnGanGoViTri_Clicked('remove')">
              <span class="-ap icon-chevron-thin-right"></span>
            </button>
            <button class="btn" @click="tsbtnGanGoViTri_Clicked('add')">
              <span class="-ap icon-chevron-thin-left"></span>
            </button>
            <button class="btn" @click="tsbtnGanGoViTri_Clicked('remove_all')">
              <span class="fa fa-angle-double-right"></span>
            </button>
            <button class="btn" @click="tsbtnGanGoViTri_Clicked('add_all')">
              <span class="fa fa-angle-double-left"></span>
            </button>
          </div>
          <div class="box-col box-form">
            <div class="legend-title">Vị trí chưa gán</div>
            <div class="grid-ext grid-ext-freeze">
              <DataGrid
                ref="gridDonVi"
                panelDataHeight="250px"
                :columns="gridChucDanhChuaGan.header"
                :dataSource="gridChucDanhChuaGan.list"
                :enabledSelectFirstRow="false"
                :showFilter="true"
                :enablePagingServer="false"
                :showColumnCheckbox="true"
                :allowPaging="true"
                @rowSelected="gridChucDanhChuaGan_rowSelected"
                @selectedItemsChanged="gridChucDanhChuaGan_selectedItemsChanged"
              />
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách chức danh</div>
          <div class="row">
            <DataGrid ref="gridChucDanh" panelDataHeight="300px" :columns="gridChucDanh.header" :dataSource="gridChucDanh.list" :enabledSelectFirstRow="false" :showFilter="true" :enablePagingServer="false" :showColumnCheckbox="false" :allowPaging="true" @rowSelected="gridChucDanh_rowSelected" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import XLSX from 'xlsx'
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import moment from 'moment'
import api from './api'
export default {
  components: {
    breadcrumb,
    frmPhieuBaoHanhPSIS: () => import('@/modules/QLVT/PhieuBaoHanhPSIS')
  },
  data() {
    return {
      tsbtnGhiLai: { enabled: true, visible: true, text: 'Ghi lại' },
      gridChucDanhChuaGan: {
        header: [
          { width: 50, fieldName: 'vitri_id', headerText: 'ID', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'vitri', headerText: 'Vị trí', textAlign: 'Left', allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridChucDanhDaGan: {
        header: [
          { width: 50, fieldName: 'vitri_id', headerText: 'ID', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'vitri', headerText: 'Vị trí', textAlign: 'Left', allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridChucDanh: {
        header: [
          { width: 50, fieldName: 'chucdanh_id', headerText: 'ID', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'chucdanh', headerText: 'Chức danh', textAlign: 'Left', allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      tt_userwarp: null
    }
  },
  methods: {
    async gridChucDanhChuaGan_rowSelected(e) {
      // if (e.data) {
      //   this.gridChucDanh.value = e.data
      //   await this.lay_ds_agent()
      // } else {
      //   this.gridChucDanh.value = null
      // }
    },
    async gridChucDanhChuaGan_selectedItemsChanged(e) {
      this.gridChucDanhChuaGan.selected = e
    },
    async gridChucDanhDaGan_rowSelected(e) {
      // if (e.data) {
      //   this.gridChucDanh.value = e.data
      //   await this.lay_ds_agent()
      // } else {
      //   this.gridChucDanh.value = null
      // }
    },
    async gridChucDanhDaGan_selectedItemsChanged(e) {
      console.log(e)
      this.gridChucDanhDaGan.selected = e
    },
    async gridChucDanh_rowSelected(e) {
      if (e.data) {
        this.gridChucDanh.value = e.data
        await this.reloadDanhSachGanGo()
      } else {
        this.gridChucDanh.value = null
      }
    },
    async tsbtnGhiLai_Clicked() {
      let arrChuanBiGan = this.gridChucDanhDaGan.list.filter((e) => e.flag == 'chua_gan')
      for (let i = 0; i < arrChuanBiGan.length; i++) {
        let key = (
          await api.get_keys(this.axios, {
            keyname: 'VTCD_ID',
            numblocksize: 1,
            numretry: 10
          })
        ).data.data
        await this.sp_them_vitri_chucdanh({ chucdanh_id: this.gridChucDanh.value.chucdanh_id, vitri_id: arrChuanBiGan[i].vitri_id, vtcd_id: key })
      }
      let arrChuanBiBoGan = this.gridChucDanhChuaGan.list.filter((e) => e.flag == 'da_gan')
      for (let i = 0; i < arrChuanBiBoGan.length; i++) {
        await this.sp_xoa_vitri_chucdanh({ chucdanh_id: this.gridChucDanh.value.chucdanh_id, vitri_id: arrChuanBiBoGan[i].vitri_id, vtcd_id: null })
      }
      await this.reloadDanhSachGanGo()
    },
    async sp_lay_ds_chucdanh() {
      try {
        this.loading(true)
        let kq = await api.sp_lay_ds_chucdanh(this.axios, {})
        if (kq.data.data && kq.data.data.length) {
          this.gridChucDanh.list = kq.data.data
        } else {
          this.gridChucDanh.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async sp_lay_ds_vitri_chucdanh(data) {
      try {
        this.loading(true)
        let kq = await api.sp_lay_ds_vitri_chucdanh(this.axios, data)
        if (kq.data.data && kq.data.data.length) {
          return kq.data.data
        } else {
          return []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async sp_them_vitri_chucdanh(data) {
      try {
        this.loading(true)
        let kq = await api.sp_them_vitri_chucdanh(this.axios, data)
        // if (kq_sp_cbx_timkiem_tt_doitac_dai.data.data && kq_sp_cbx_timkiem_tt_doitac_dai.data.data.length) {
        //   this.cboTaoMoiDoiTac.list = kq_sp_cbx_timkiem_tt_doitac_dai.data.data
        // } else {
        //   this.cboTaoMoiDoiTac.list = []
        // }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async sp_xoa_vitri_chucdanh() {
      try {
        this.loading(true)
        let kq = await api.sp_xoa_vitri_chucdanh(this.axios, { chucdanh_id: 1 })
        // if (kq.data.data && kq.data.data.length) {
        //   this.cboDonVi.list = kq.data.data
        // } else {
        //   this.cboDonVi.list = []
        // }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async reloadDanhSachGanGo() {
      let kqChuaGan = await this.sp_lay_ds_vitri_chucdanh({ chucdanh_id: this.gridChucDanh.value.chucdanh_id, vitri_id: null, flag: 0 })
      console.log(kqChuaGan)
      this.gridChucDanhChuaGan.list = kqChuaGan.map((e) => {
        return {
          ...e,
          flag: 'chua_gan'
        }
      })
      let kqDaGan = await this.sp_lay_ds_vitri_chucdanh({ chucdanh_id: this.gridChucDanh.value.chucdanh_id, vitri_id: null, flag: 1 })
      this.gridChucDanhDaGan.list = kqDaGan.map((e) => {
        return {
          ...e,
          flag: 'da_gan'
        }
      })
    },
    async tsbtnGanGoViTri_Clicked(action) {
      switch (action) {
        case 'remove':
          console.log('remove')
          this.gridChucDanhChuaGan.list = [...this.gridChucDanhChuaGan.list, ...this.pushToViTriList('remove', this.gridChucDanhDaGan.selected)]
          if (this.gridChucDanhDaGan.selected.length) {
            this.gridChucDanhDaGan.list = this.gridChucDanhDaGan.list.filter((e) => this.gridChucDanhChuaGan.list.find((x) => x.vitri_id == e.vitri_id) == null)
          }
          this.gridChucDanhDaGan.selected = []
          break
        case 'remove_all':
          break
        case 'add':
          console.log('add')
          this.gridChucDanhDaGan.list = [...this.gridChucDanhDaGan.list, ...this.pushToViTriList('add', this.gridChucDanhChuaGan.selected)]
          if (this.gridChucDanhChuaGan.selected.length) {
            this.gridChucDanhChuaGan.list = this.gridChucDanhChuaGan.list.filter((e) => this.gridChucDanhDaGan.list.find((x) => x.vitri_id == e.vitri_id) == null)
          }
          this.gridChucDanhChuaGan.selected = []
          break
        case 'add_all':
          break
      }
    },
    pushToViTriList(action, data) {
      console.log('pushToViTriList')
      let vldata = []
      if (['add'].includes(action)) {
        vldata = data.filter((e) => this.gridChucDanhDaGan.list.find((x) => x.vitri_id == e.vitri_id) == null)
      }
      if (['remove'].includes(action)) {
        vldata = data.filter((e) => this.gridChucDanhChuaGan.list.find((x) => x.vitri_id == e.vitri_id) == null)
      }
      return vldata
    }
  },
  async mounted() {
    await this.sp_lay_ds_chucdanh()
    // let key = await api.get_keys(this.axios, {
    //   keyname: 'VTCD_ID',
    //   numblocksize: 1,
    //   numretry: 10
    // })
  }
}
</script>

<style>
#tra-cuu-theo-so-lo-phieu-psis {
  width: 100%;
  height: 100%;
}
.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: #9999 !important;
}
.disabledInput > a {
  color: #666 !important;
}
</style>
