<template>
  <div class="box-move-select-table mart20">
    <div class="box-col">
      <div class="legend-title">Hướng giao đã gán</div>
      <DataGrid ref="gridViewHuongGiaoDaGan" v-bind:columns="gridViewHuongGiaoDaGan.headers"
                v-bind:dataSource="gridViewHuongGiaoDaGan.data" :showColumnCheckbox="true"
                @actionComplete="gridDaganActionComplete"
                :enable-paging-server="false" :allowPaging="true" :showFilter="true" @selectedItemsChanged="gridViewHuongGiaoDaGanChecked"
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
      <div class="legend-title">Hướng giao chưa gán</div>
      <DataGrid ref="gridViewHuongGiaoChuaGan" v-bind:columns="gridViewHuongGiaoChuaGan.headers"
                v-bind:dataSource="gridViewHuongGiaoChuaGan.data" :showColumnCheckbox="true"
                :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                @actionComplete="gridChuaGanActionComplete"
                :enabled-select-first-row="false" @selectedItemsChanged="gridViewHuongGiaoChuaGanChecked"
      ></DataGrid>
    </div>
  </div>
</template>

<script>

export default {
  name: 'DanhSachHuongGiao',
  data () {
    return {
      gridViewHuongGiaoDaGan: {
        data: [],
        headers: [
          {
            fieldName: 'huonggiao_id',
            headerText: 'ID',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'huonggiao',
            headerText: 'Hướng giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_tinh',
            headerText: 'Danh sách tỉnh',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selected: [],
        checked: [],
        selectItem: null
      },
      gridViewHuongGiaoChuaGan: {
        data: [],
        headers: [
          {
            fieldName: 'huonggiao_id',
            headerText: 'ID',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'huonggiao',
            headerText: 'Hướng giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_tinh',
            headerText: 'Danh sách tỉnh',
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
  methods: {
    clearSelected () {
      this.gridViewHuongGiaoDaGan.selected = []
      this.gridViewHuongGiaoChuaGan.selected = []
    },
    setHuongGiaoDaGan (items) {
      this.gridViewHuongGiaoDaGan.data = items
    },
    setHuongGiaoChuaGan (items) {
      this.gridViewHuongGiaoChuaGan.data = items
    },
    getHuongGiaoDaGan() {
      return this.gridViewHuongGiaoDaGan.data
    },
    gridDaganActionComplete (args) {
      if (this.gridViewHuongGiaoDaGan.selected.length > 0) {
        let indexs = []
        for (let i = 0; i < this.gridViewHuongGiaoDaGan.selected.length; i++) {
          let element = this.gridViewHuongGiaoDaGan.selected[i]
          let index = this.gridViewHuongGiaoDaGan.data.findIndex(item => item.huonggiao == element.huonggiao)
          indexs.push(index)
        }
        this.$refs.gridViewHuongGiaoDaGan.selectRows(indexs)
      }
    },
    gridChuaGanActionComplete (args) {
      if (this.gridViewHuongGiaoChuaGan.selected.length > 0) {
        let indexs = []
        for (let i = 0; i < this.gridViewHuongGiaoChuaGan.selected.length; i++) {
          let element = this.gridViewHuongGiaoChuaGan.selected[i]
          let index = this.gridViewHuongGiaoChuaGan.data.findIndex(item => item.huonggiao == element.huonggiao)
          indexs.push(index)
        }

        this.$refs.gridViewHuongGiaoChuaGan.selectRows(indexs)
      }
    },
    gan () {
      if (this.gridViewHuongGiaoChuaGan.checked.length === 0) {
        return
      }
      this.gridViewHuongGiaoChuaGan.checked.forEach((item) => {
        this.gridViewHuongGiaoDaGan.data.unshift(item)
        const index = this.gridViewHuongGiaoChuaGan.data.findIndex(x => x.huonggiao === item.huonggiao)
        if (index > -1) {
          this.gridViewHuongGiaoChuaGan.data.splice(index, 1)
        }
      })
      this.gridViewHuongGiaoChuaGan.checked = []
    },
    boGan () {
      if (this.gridViewHuongGiaoDaGan.checked.length === 0) {
        return
      }
      this.gridViewHuongGiaoDaGan.checked.forEach((item) => {
        this.gridViewHuongGiaoChuaGan.data.unshift(item)
        const index = this.gridViewHuongGiaoDaGan.data.findIndex(x => x.huonggiao === item.huonggiao)
        if (index > -1) {
          this.gridViewHuongGiaoDaGan.data.splice(index, 1)
        }
      })
      this.gridViewHuongGiaoDaGan.checked = []
    },
    ganAll () {
      if (this.gridViewHuongGiaoChuaGan.data.length === 0) {
        return
      }
      this.gridViewHuongGiaoChuaGan.data.forEach(item => {
        this.gridViewHuongGiaoDaGan.data.unshift(item)
      })
      this.gridViewHuongGiaoChuaGan.data = []
      this.gridViewHuongGiaoChuaGan.checked = []
    },
    boGanAll () {
      if (this.gridViewHuongGiaoDaGan.data.length === 0) {
        return
      }
      this.gridViewHuongGiaoDaGan.data.forEach(item => {
        this.gridViewHuongGiaoChuaGan.data.unshift(item)
      })
      this.gridViewHuongGiaoDaGan.data = []
      this.gridViewHuongGiaoDaGan.checked = []
    },
    gridViewHuongGiaoDaGanChecked (items) {
      this.gridViewHuongGiaoDaGan.checked = items
    },
    gridViewHuongGiaoChuaGanChecked (items) {
      this.gridViewHuongGiaoChuaGan.checked = items
    },
    async clearData () {
    }
  }
}
</script>

<style scoped>
</style>
