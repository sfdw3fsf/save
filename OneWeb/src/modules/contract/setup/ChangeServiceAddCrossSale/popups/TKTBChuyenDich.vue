<template>
  <ejs-dialog :enableResize='true' :visible="false" ref="dialogTKTBChuyenDich"
              :header='dulieu.text ? dulieu.text :"Danh sách thuê bao của khách hàng Chuyển dịch"' :allowDragging='true'
              showCloseIcon=true target=".main-wrapper" width='60%' height="800">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript:void(0)" @click.prevent="tsbtnCapNhan_Click">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="breadcrumb-top">
        <div class="main-title">{{ dulieu.title ? dulieu.title : 'CHỌN SỐ MÁY / ACCOUNT THỰC HIỆN DI CHUYỂN' }}</div>
      </div>
      <div class="box-form">
        <div class="legend-title">Danh sách thuê bao</div>
        <div class="table-content">
          <DataGrid ref="grid" v-bind:columns="dgvThueBao.headers"
                    v-bind:dataSource="dgvThueBao.data"
                    :selection-settings="dgvThueBao.selectionOptions"
                    @recordDoubleClick="dgvThueBao_CellDoubleClick"
                    :show-column-checkbox="false"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :enabled-select-first-row="true"
                    @selectedItemsChanged="dgvThueBao_selectedItemsChanged"
                    @recordClick="dgvThueBao_SelectionChanged"
                    :showFilter="true">
          </DataGrid>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import TKTBChuyenDichAPI from './TKTBChuyenDichAPI'

export default {
  name: 'TKTBChuyenDich',
  props: ['dulieu'],
  data () {
    return {
      dichvuvt_id: 0,
      dtHDTB_KH: [],
      MaThueBao: '',
      MaThanhToan: '',
      dgvThueBao: {
        data: [],
        headers: [
          {fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true, allowSorting: true, width: 120, isPrimaryKey: true},
          {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: true, width: 130, isPrimaryKey: false},
          {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: true, isPrimaryKey: false},
          {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true, allowSorting: true, width: 130, isPrimaryKey: false},
          {fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái', allowFiltering: true, allowSorting: true, width: 160, isPrimaryKey: false},
          {fieldName: 'TINHTRANG_TB', headerText: 'Tình Trạng', allowFiltering: true, allowSorting: true, width: 160, isPrimaryKey: false}
        ],
        selected: 0,
        selectItem: null,
        checked: [],
        cellSelected: null,
        selectionOptions: { mode: 'Row' }
      }
    }
  },
  methods: {
    tsbtnCapNhan_Click () {
      this.CanhBaoTrangThaiTB()
      this.hideDialog()
    },
    async showDialog () {
      await this.HienThiDanhSachHDTBTheoKHID()
      this.$refs.dialogTKTBChuyenDich.show()
    },
    hideDialog () {
      this.$emit('closeTKTBChuyenDich', this.MaThueBao, this.dichvuvt_id)
      this.$refs.dialogTKTBChuyenDich.hide()
    },
    async HienThiDanhSachHDTBTheoKHID () {
      this.dtHDTB_KH = await this.LAY_DS_DB_HDTB_THEO_KHID_MAGD(this.dulieu.khachhang_id, this.dulieu.ma_gd)
      let selected = []
      for (let i = 0; i < this.dtHDTB_KH.length; i++) {
        let item = this.dtHDTB_KH[i]
        if (item.THUOC_PL == 1) {
          selected.push(item.MA_TB)
          if (this.dulieu.title) {
            item.TINHTRANG_TB = 'Đang thay đổi dịch vụ'
          } else {
            item.TINHTRANG_TB = 'Đang chuyển dịch'
          }
        }
      }
      this.dgvThueBao.data = this.dtHDTB_KH
      this.$refs.grid.selectRowsByRowData(selected)
    },
    async LAY_DS_DB_HDTB_THEO_KHID_MAGD (khachhangid, magd) {
      try {
        let rs = await TKTBChuyenDichAPI.lay_ds_db_hdtb_theo_khid_magd(this.axios, khachhangid, magd)
        console.log('🚀 ~ file: popupTKTBChuyenDich.vue ~ line 180 ~ LAY_DS_DB_HDTB_THEO_KHID_MAGD ~ rs.data', rs.data)
        return rs.data.data
      } catch (error) {
        return []
      }
    },
    dgvThueBao_SelectionChanged (args) {
      let item = args.rowData
      this.dgvThueBao.selectItem = args.rowData
      if (item.THUOC_PL == 1) {
        if (this.dulieu.title) {
          this.$root.$toast.warning('Thuê bao ' + item.MA_TB + ' đang thực hiện thay đổi dịch vụ, Bạn hãy chọn thuê bao khác !')
        } else {
          this.$root.$toast.warning('Thuê bao ' + item.MA_TB + ' đang thực hiện chuyển dịch, Bạn hãy chọn thuê bao khác !')
        }
      } else {
        this.MaThueBao = item.MA_TB
        this.dichvuvt_id = item.DICHVUVT_ID
      }
    },
    containsObject (o, list) {
      for (let i = 0; i < list.length; i++) {
        if (list[i] === o) {
          return true
        }
      }
      return false
    },
    dgvThueBao_selectedItemsChanged (args) {
      this.dgvThueBao.checked = args
    },
    CanhBaoTrangThaiTB () {
      if (this.dgvThueBao.selectItem.TRANGTHAITB_ID != 1) {
        this.$confirm('Thuê bao này đang ở trạng thái ' + this.dgvThueBao.selectItem.TRANGTHAI_TB + '.', 'Thông báo', {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Hủy'
        })
        return false
      }
      return true
    },
    dgvThueBao_CellDoubleClick (args) {
      this.dgvThueBao.selectItem = args.rowData
      this.CanhBaoTrangThaiTB()
      this.hideDialog()
    }
  }
}
</script>

<style scoped>

</style>
