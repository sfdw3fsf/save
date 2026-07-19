<style scoped src="./DetailTemplate.scss"></style>
<template>
  <div>
    <div class="detailRow">
      <div class="nav tabs tab-over">
        <a v-if="showNguyenNhan" v-bind:href="tab1Link" data-toggle="tab" class="active detailRowTab">Nguyên nhân hỏng</a>
        <a v-if="showTon" v-bind:href="tab2Link" data-toggle="tab" class="detailRowTab">Chi tiết tồn</a>
        <a v-if="showNhanVien" v-bind:href="tab3Link" data-toggle="tab" class="detailRowTab">Chi tiết nhân viên thực hiện</a>
      </div>
      <div class="tab-content">
        <div  v-if="showNguyenNhan" v-bind:id="tab1" class="tab-pane active detailRowTabContent">
          <DataGrid ref="refgridViewChiTiet" :showFilter="false" @dataBound="gridViewChiTiet_dataBound" v-bind:columns="gridViewChiTiet.headers" :enabledSelectFirstRow="true" v-bind:dataSource="gridViewChiTiet.datalist" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="false">
          </DataGrid>
        </div>
        <div v-if="showTon" v-bind:id="tab2" class="tab-pane detailRowTabContent">
          <DataGrid ref="refgridViewBaoTon" @dataBound="refgridViewBaoTon_dataBound" :showFilter="false" v-bind:columns="gridViewBaoTon.headers" :enabledSelectFirstRow="true" v-bind:dataSource="gridViewBaoTon.datalist" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="false">
          </DataGrid>
        </div>
        <div v-if="showNhanVien" v-bind:id="tab3" class="tab-pane detailRowTabContent">
          <DataGrid ref="refgridViewNVXL" @dataBound="refgridViewNVXL_dataBound" :showFilter="false" v-bind:columns="gridViewNVXL.headers" :enabledSelectFirstRow="true" v-bind:dataSource="gridViewNVXL.datalist" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="false">
          </DataGrid>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
// import Vue from 'vue'
import DataGrid from '@/components/Controls/DataGrid'
import moment from 'moment'
export default {
  name: 'DetailTemplate',
  data: function () {
    return {
      data: {}
    }
  },
  components: { DataGrid, moment },
  computed: {
    showNguyenNhan() {
      return this.gridViewChiTiet.datalist != null && this.gridViewChiTiet.datalist.length
    },
    showTon() {
      return this.gridViewBaoTon.datalist != null && this.gridViewBaoTon.datalist.length
    },
    showNhanVien() {
      return this.gridViewNVXL.datalist != null && this.gridViewNVXL.datalist.length
    },
    gridViewChiTiet: function () {
      return {
        headers: [
          {
            fieldName: 'loaihong',
            headerText: 'Loại hỏng',
            allowFiltering: false,
            allowSorting: false,
            width: 250
          },
          {
            fieldName: 'ct_hong',
            headerText: 'Chi tiết hỏng',
            allowFiltering: false,
            allowSorting: false
          }
        ],
        datalist: this.data.dtCTHONG != null ? this.data.dtCTHONG.filter(x=>x.baohong_id==this.data.baohong_id) : []
      }
    },
    gridViewBaoTon: function () {
      return {
        headers: [
          {
            fieldName: 'ten_dv',
            headerText: 'Đơn vị báo tồn',
            allowFiltering: true,
            allowSorting: false,
            width: 200
          },
          {
            fieldName: 'ten_nv',
            headerText: 'Nhân viên báo tồn',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_nv',
            headerText: 'Nhân viên báo tồn',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_bt',
            headerText: 'Ngày báo tồn',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nhomton',
            headerText: 'Nhóm tồn',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ct_ton',
            headerText: 'Chi tiết tồn',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ghichu',
            headerText: 'Ghi chú tồn',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        datalist: this.data.dtCTTON != null ? this.data.dtCTTON.filter(x=>x.baohong_id==this.data.baohong_id) : []
      }
    },
    gridViewNVXL: function () {
      return {
        headers: [
          {
            fieldName: 'ten_dv',
            headerText: 'Đơn vị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_nv',
            headerText: 'Nhân viên',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'so_dt',
            headerText: 'Điện thoại',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngaygiao',
            headerText: 'Ngày giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nhiemvu',
            headerText: 'Nhiệm vụ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ghichu',
            headerText: 'Ghi chú',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        datalist: this.data.dtNVXL != null ? this.data.dtNVXL.filter(x=>x.baohong_id==this.data.baohong_id) : []
      }
    },
    tab1: function () {
      try {
        return 'tab1' + this.data.rowId + moment(this.data.ngay_bh, 'DD/MM/YYYY HH:mm:ss').format('YYYYMMDDHHmmss')
      } catch (err) {
        return 'tab1' + this.data.rowId + moment().format('YYYYMMDDHHmmss')
      }
    },
    tab2: function () {
      try {
        return 'tab2' + this.data.rowId + moment(this.data.ngay_bh, 'DD/MM/YYYY HH:mm:ss').format('YYYYMMDDHHmmss')
      } catch (err) {
        return 'tab2' + this.data.rowId + moment().format('YYYYMMDDHHmmss')
      }
    },
    tab3: function () {
      try {
        return 'tab3' + this.data.rowId + moment(this.data.ngay_bh, 'DD/MM/YYYY HH:mm:ss').format('YYYYMMDDHHmmss')
      } catch (err) {
        return 'tab3' + this.data.rowId + moment().format('YYYYMMDDHHmmss')
      }
    },
    tab1Link: function () {
      try {
        return '#tab1' + this.data.rowId + moment(this.data.ngay_bh, 'DD/MM/YYYY HH:mm:ss').format('YYYYMMDDHHmmss')
      } catch (err) {
        return '#tab1' + this.data.rowId + moment().format('YYYYMMDDHHmmss')
      }
    },
    tab2Link: function () {
      try {
        return '#tab2' + this.data.rowId + moment(this.data.ngay_bh, 'DD/MM/YYYY HH:mm:ss').format('YYYYMMDDHHmmss')
      } catch (err) {
        return '#tab2' + this.data.rowId + moment().format('YYYYMMDDHHmmss')
      }
    },
    tab3Link: function () {
      try {
        return '#tab3' + this.data.rowId + moment(this.data.ngay_bh, 'DD/MM/YYYY HH:mm:ss').format('YYYYMMDDHHmmss')
      } catch (err) {
        return '#tab3' + this.data.rowId + moment().format('YYYYMMDDHHmmss')
      }
    },
    tabIndex: function () {
      return 0
    },
    showGridNguyenNhan: function () {
      return true
    },
    showGridTon: function () {
      return true
    },
    showGridNhanVien: function () {
      return true
    },
    image: function () {
      return 'https://ej2.syncfusion.com/vue/documentation/samples/grid/column/template-cs2/1.png'
    },
    altImage: function () {
      return '123'
    }
  },
  methods: {
    gridViewChiTiet_dataBound(args) {
      window.dispatchEvent(new Event('resize'))
      this.$refs.refgridViewChiTiet.grid.autoFitColumns([])
    },
    refgridViewBaoTon_dataBound(args) {
      this.$refs.refgridViewBaoTon.grid.autoFitColumns([])
    },
    refgridViewNVXL_dataBound(args) {
      this.$refs.refgridViewNVXL.grid.autoFitColumns([])
    }
  }
}
</script>
