<template>
  <div class="box-move-select-table mart20">
    <div class="box-col">
      <div class="legend-title">Danh sách tỉnh đã gán</div>
      <DataGrid ref="gridViewTinhDaGan" v-bind:columns="gridViewTinhDaGan.headers"
                v-bind:dataSource="gridViewTinhDaGan.data" :showColumnCheckbox="true"
                @actionComplete="gridDaganActionComplete"
                :enable-paging-server="false" :allowPaging="true" :showFilter="true" @selectedItemsChanged="gridViewTinhDaGanChecked"
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
      <div class="legend-title">Danh sách tỉnh chưa gán</div>
      <DataGrid ref="gridViewTinhChuaGan" v-bind:columns="gridViewTinhChuaGan.headers"
                v-bind:dataSource="gridViewTinhChuaGan.data" :showColumnCheckbox="true"
                :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                @actionComplete="gridChuaGanActionComplete"
                :enabled-select-first-row="false" @selectedItemsChanged="gridViewTinhChuaGanChecked"
      ></DataGrid>
    </div>
  </div>
</template>

<script>

export default {
  name: 'DanhSachTinh',
  props: {
    dsTinhDaGan : {
      type: Array,
      default: function () {
        return []
      }
    },
    dsTinhChuaGan : {
      type: Array,
      default: function () {
        return []
      }
    }
  },
  data () {
    return {
      gridViewTinhDaGan: {
        data: [],
        headers: [
          {
            fieldName: 'tentinh',
            headerText: 'Danh sách tỉnh đã gán',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selected: [],
        checked: [],
        selectItem: null
      },
      gridViewTinhChuaGan: {
        data: [],
        headers: [
          {
            fieldName: 'tentinh',
            headerText: 'Danh sách tỉnh chưa gán',
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
    dsTinhDaGan: {
      handler: function (val) {
        this.gridViewTinhDaGan.data = val
      },
      deep: true
    },
    dsTinhChuaGan: {
      handler: function (val) {
        this.gridViewTinhChuaGan.data = val
      },
      deep: true
    }
  },
  methods: {
    clearSelected () {
      this.gridViewTinhDaGan.selected = []
      this.gridViewTinhChuaGan.selected = []
    },
    setTinhDaGan (items) {
      this.gridViewTinhDaGan.data = items
    },
    setTinhChuaGan (items) {
      this.gridViewTinhChuaGan.data = items
    },
    getTinhDaGan() {
      return this.gridViewTinhDaGan.data
    },
    gridDaganActionComplete (args) {
      if (this.gridViewTinhDaGan.selected.length > 0) {
        let indexs = []
        for (let i = 0; i < this.gridViewTinhDaGan.selected.length; i++) {
          let element = this.gridViewTinhDaGan.selected[i]
          let index = this.gridViewTinhDaGan.data.findIndex(item => item.tinh_id == element.tinh_id)
          indexs.push(index)
        }
        this.$refs.gridViewTinhDaGan.selectRows(indexs)
      }
    },
    gridChuaGanActionComplete (args) {
      if (this.gridViewTinhChuaGan.selected.length > 0) {
        let indexs = []
        for (let i = 0; i < this.gridViewTinhChuaGan.selected.length; i++) {
          let element = this.gridViewTinhChuaGan.selected[i]
          let index = this.gridViewTinhChuaGan.data.findIndex(item => item.tinh_id == element.tinh_id)
          indexs.push(index)
        }
        console.log(indexs)
        this.$refs.gridViewTinhChuaGan.selectRows(indexs)
      }
    },
    gan () {
      if (this.gridViewTinhChuaGan.checked.length === 0) {
        return
      }
      this.gridViewTinhChuaGan.checked.forEach((item) => {
        this.gridViewTinhDaGan.data.unshift(item)
        const index = this.gridViewTinhChuaGan.data.findIndex(x => x.tinh_id === item.tinh_id)
        if (index > -1) {
          this.gridViewTinhChuaGan.data.splice(index, 1)
        }
      })
      this.gridViewTinhChuaGan.checked = []
    },
    boGan () {
      if (this.gridViewTinhDaGan.checked.length === 0) {
        return
      }
      this.gridViewTinhDaGan.checked.forEach((item) => {
        this.gridViewTinhChuaGan.data.unshift(item)
        const index = this.gridViewTinhDaGan.data.findIndex(x => x.tentinh === item.tentinh)
        if (index > -1) {
          this.gridViewTinhDaGan.data.splice(index, 1)
        }
      })
      this.gridViewTinhDaGan.checked = []
    },
    ganAll () {
      if (this.gridViewTinhChuaGan.data.length === 0) {
        return
      }
      this.gridViewTinhChuaGan.data.forEach(item => {
        this.gridViewTinhDaGan.data.unshift(item)
      })
      this.gridViewTinhChuaGan.data = []
      this.gridViewTinhChuaGan.checked = []
    },
    boGanAll () {
      if (this.gridViewTinhDaGan.data.length === 0) {
        return
      }
      this.gridViewTinhDaGan.data.forEach(item => {
        this.gridViewTinhChuaGan.data.unshift(item)
      })
      this.gridViewTinhDaGan.data = []
      this.gridViewTinhDaGan.checked = []
    },
    gridViewTinhDaGanChecked (items) {
      this.gridViewTinhDaGan.checked = items
    },
    gridViewTinhChuaGanChecked (items) {
      this.gridViewTinhChuaGan.checked = items
    },
    async clearData () {
    },
    GetDataList: function (response) {
      console.log(response)
      if (response && response.data && response.data.error_code === 'BSS-00000000') {
        return response.data.data == null ? [] : response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return null
    }
  }
}
</script>

<style scoped>
</style>
