<template>

  <div class="box-move-select-table mart20">
    <div class="box-col">
      <div class="legend-title">Loại đơn vị đã gán</div>
      <DataGrid ref="gridViewLoaiDVDaGan" v-bind:columns="gridViewLoaiDVDaGan.headers"
                v-bind:dataSource="gridViewLoaiDVDaGan.data" :showColumnCheckbox="true"
                @actionComplete="gridDaganActionComplete"
                :enable-paging-server="false" :allowPaging="true" :showFilter="true" @selectedItemsChanged="gridViewLoaiDVDaGanChecked"
                :enabled-select-first-row="false"
      ></DataGrid>
    </div>
    <div class="actions">
      <button class="btn" @click="boGan">
        <span class="fa fa-angle-right"></span>
      </button>
      <button class="btn" @click="boGanAll">
        <span class="fa fa-angle-double-right"></span>
      </button>
      <button class="btn" @click="gan">
        <span class="fa fa-angle-left"></span>
      </button>
      <button class="btn" @click="ganAll">
        <span class="fa fa-angle-double-left"></span>
      </button>
    </div>
    <div class="box-col">
      <div class="legend-title">Loại đơn vị chưa gán</div>
      <DataGrid ref="gridLoaiDVChuaGan" v-bind:columns="gridLoaiDVChuaGan.headers"
                v-bind:dataSource="gridLoaiDVChuaGan.data" :showColumnCheckbox="true"
                :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                @actionComplete="gridChuaGanActionComplete"
                :enabled-select-first-row="false" @selectedItemsChanged="gridLoaiDVChuaGanChecked"
      ></DataGrid>
    </div>
  </div>
</template>

<script>
import KTable from '@/components/kylq_components/KTable.vue'
import DepartmentAPI from '../API'

export default {
  name: 'LoaiDonVi',
  props: {
    p_donvi_id: {
      type: Number,
      default: -1
    }
  },
  components: {
    KTable
  },
  data () {
    return {
      columns: [
        {
          field: 'TEN_LOAIDV',
          label: 'Loại đơn vị',
          allowFilter: true
        }
      ],
      gridViewLoaiDVDaGan: {
        data: [],
        headers: [
          {
            fieldName: 'TEN_LOAIDV',
            headerText: 'Loại đơn vị',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selected: [],
        checked: [],
        selectItem: null
      },
      gridLoaiDVChuaGan: {
        data: [],
        headers: [
          {
            fieldName: 'TEN_LOAIDV',
            headerText: 'Loại đơn vị',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selected: [],
        checked: [],
        selectItem: null
      }
    }
  },
  watch: {
    p_donvi_id (val) {
      this.p_donvi_id = val
    }
  },
  methods: {
    clearSelected () {
      this.gridViewLoaiDVDaGan.selected = []
      this.gridLoaiDVChuaGan.selected = []
    },
    gridDaganActionComplete (args) {
      if (this.gridViewLoaiDVDaGan.selected.length > 0) {
        let indexs = []
        for (let i = 0; i < this.gridViewLoaiDVDaGan.selected.length; i++) {
          let element = this.gridViewLoaiDVDaGan.selected[i]
          let index = this.gridViewLoaiDVDaGan.data.findIndex(item => item.LOAIDV_ID == element.LOAIDV_ID)
          indexs.push(index)
        }
        this.$refs.gridViewLoaiDVDaGan.selectRows(indexs)
      }
    },
    gridChuaGanActionComplete (args) {
      if (this.gridLoaiDVChuaGan.selected.length > 0) {
        let indexs = []
        for (let i = 0; i < this.gridLoaiDVChuaGan.selected.length; i++) {
          let element = this.gridLoaiDVChuaGan.selected[i]
          let index = this.gridLoaiDVChuaGan.data.findIndex(item => item.LOAIDV_ID == element.LOAIDV_ID)
          indexs.push(index)
        }
        console.log(indexs)
        this.$refs.gridLoaiDVChuaGan.selectRows(indexs)
      }
    },
    gan () {
      if (this.gridLoaiDVChuaGan.checked.length === 0) {
        return
      }
      this.gridLoaiDVChuaGan.checked.forEach((item) => {
        this.gridViewLoaiDVDaGan.data.unshift(item)
        this.gridViewLoaiDVDaGan.selected.push(item)
        const index = this.gridLoaiDVChuaGan.data.findIndex(x => x.TEN_LOAIDV === item.TEN_LOAIDV)
        if (index > -1) {
          this.gridLoaiDVChuaGan.data.splice(index, 1)
        }
      })
      this.gridLoaiDVChuaGan.checked = []
    },
    boGan () {
      if (this.gridViewLoaiDVDaGan.checked.length === 0) {
        return
      }
      this.gridViewLoaiDVDaGan.checked.forEach((item) => {
        this.gridLoaiDVChuaGan.data.unshift(item)
        this.gridLoaiDVChuaGan.selected.push(item)
        const index = this.gridViewLoaiDVDaGan.data.findIndex(x => x.TEN_LOAIDV === item.TEN_LOAIDV)
        if (index > -1) {
          this.gridViewLoaiDVDaGan.data.splice(index, 1)
        }
      })
      this.gridViewLoaiDVDaGan.checked = []
    },
    ganAll () {
      if (this.gridLoaiDVChuaGan.data.length === 0) {
        return
      }
      this.gridLoaiDVChuaGan.data.forEach(item => {
        this.gridViewLoaiDVDaGan.selected.push(item)
        this.gridViewLoaiDVDaGan.data.unshift(item)
      })
      this.gridLoaiDVChuaGan.data = []
      this.gridLoaiDVChuaGan.checked = []
    },
    boGanAll () {
      if (this.gridViewLoaiDVDaGan.data.length === 0) {
        return
      }
      this.gridViewLoaiDVDaGan.data.forEach(item => {
        this.gridLoaiDVChuaGan.selected.push(item)
        this.gridLoaiDVChuaGan.data.unshift(item)
      })
      this.gridViewLoaiDVDaGan.data = []
      this.gridViewLoaiDVDaGan.checked = []
    },
    gridViewLoaiDVDaGanChecked (items) {
      this.gridViewLoaiDVDaGan.checked = items
    },
    gridLoaiDVChuaGanChecked (items) {
      this.gridLoaiDVChuaGan.checked = items
    },
    async clearData () {
    },
    async hienthi_ds_loaidv (donviId) {
      if (donviId === -1) {
        return
      }
      // loai don vi da gan
      await this.sp_ds_loaidv(donviId, 1)
      // loai don vi chua gan
      await this.sp_ds_loaidv(donviId, 0)
    },
    async sp_ds_loaidv (donviId, type) {
      try {
        this.loading(true)
        if (type === 1) {
          this.gridViewLoaiDVDaGan.data = []
        } else {
          this.gridLoaiDVChuaGan.data = []
        }
        const params = {p_donvi_id: donviId, p_kieu: type}
        let response = await DepartmentAPI.get_loai_don_vi(this.axios, params)
        if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
          if (type === 1) {
            this.gridViewLoaiDVDaGan.data = response.data.data
          } else {
            this.gridLoaiDVChuaGan.data = response.data.data
          }
        } else {
          if (type === 1) {
            this.gridViewLoaiDVDaGan.data = []
          } else {
            this.gridLoaiDVChuaGan.data = []
          }
        }
      } catch (e) {
        this.$toast.error('Không load được danh sách loại đơn vị')
      } finally {
        this.loading(false)
      }
    },
    async CAPNHAT_LOG_DONVI_LDV_JSON (donviid, maycn, ipcn) {
      let datas = this.gridViewLoaiDVDaGan.data.map(item => {
        return {
          LOAIDV_ID: item.LOAIDV_ID,
          DONVI_ID: donviid
        }
      })
      let request = {
        vdata: JSON.stringify(datas),
        vdonvi_id: donviid,
        vnguoi_td: this.$root.token.getUserName(),
        vmay_td: maycn,
        vip_td: ipcn
      }
      await DepartmentAPI.CAPNHAT_LOG_DONVI_LDV_JSON(this.axios, request).then(response => {
        console.log(response)
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          console.log('Cập nhật thành công log đơn vị loại đơn vị')
        } else {
          console.log('Cập nhật log đơn vị loại đơn vị thất bại: ' + response.data.message_detail)
        }
      }).catch(error => {
        console.log(error)
        console.log('Cập nhật log đơn vị loại đơn vị thất bại: ' + error.data.message_detail)
      })
    }
  }
}
</script>

<style scoped>
</style>
