<template>
  <ejs-dialog
    header="Danh sách các thuê bao của khách hàng đang lập Hợp đồng/Phụ lục"
    showCloseIcon="true"
    width="60%"
    ref="frmTraCuuThueBaoDialogRef"
    :target="'.main-wrapper'"
    :position="{ X: 'center', Y: 'top' }"
    :visible="false"
    :enableResize="true"
    :allowDragging="true"
  >
    <vue-nav>
      <ul class="list">
        <li @click="onOk()">
          <a>
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="popup-body">
      <vue-card>
        <DataGrid
          :columns="gridThueBao.headers"
          :dataSource="gridThueBao.dataSource"
          :allowFiltering="true"
          :showColumnCheckbox="false"
          :enable-paging-server="false"
          :allowPaging="true"
          @selectedRowChanged="selectedRowChanged"
        />
      </vue-card>
    </div>
  </ejs-dialog>
</template>

<script>
import api from '../api'
import { TrangThaiTB } from '@/const/enums'

export default {
  name: 'frmTraCuuThueBao',
  data () {
    return {
      gridThueBao: {
        headers: [
          { fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, allowSorting: false, width: 60 },
          { fieldName: 'ma_tb', headerText: 'Account', allowFiltering: true, allowSorting: false, width: 60 },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: false, width: 60 },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', allowFiltering: true, allowSorting: false, width: 60 },
          { fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false, width: 60 },
          { fieldName: 'tinhtrang', headerText: 'Tình trạng', allowFiltering: true, allowSorting: false, width: 60 }
        ],
        dataSource: [],
        selectedItem: null
      }
    }
  },
  methods: {
    async showDialog (khachHangId, maGD, tinhTrang) {
      this.loading(true)
      try {
        this.gridThueBao.dataSource = await this.getDanhSachHDTBByKhidAndMaGD(khachHangId, 0, maGD)
        if (this.gridThueBao.dataSource.length > 0) {
          this.gridThueBao.dataSource = this.gridThueBao
            .dataSource
            .map(element => ({
              ...element,
              tinhtrang: element.thuoc_pl && element.thuoc_pl.toString() === '1' ? tinhTrang : ''
            }))
        }
      } catch (e) {
      } finally {
        this.loading(false)
        this.$refs.frmTraCuuThueBaoDialogRef.show()
      }
    },
    async getDanhSachHDTBByKhidAndMaGD (khachHangId, dichVuVTId, maGD) {
      try {
        const response = await api.getDanhSachHDTBByKhidAndMaGD(
          this.axios,
          {
            vkhachhang_id: khachHangId,
            vdichvuvt_id: dichVuVTId,
            vma_gd: maGD
          }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách hợp đồng thuê bao')
        return []
      }
    },
    selectedRowChanged (item) {
      this.gridThueBao.selectedItem = item
    },
    onOk () {
      const selected = this.gridThueBao.selectedItem
      if (+selected.trangthaitb_id !== TrangThaiTB.THUONG) {
        this.$root.$toast.warning(`Thuê bao này đang ở trạng thái ${selected.trangthai_tb}`)
        this.$emit('onOk', { maTB: '', dichVuVTId: 0 })
        this.$refs.frmTraCuuThueBaoDialogRef.hide()
        return false
      }
      if (selected && +selected.thuoc_pl !== 1) {
        this.$emit('onOk', { maTB: selected.ma_tb, dichVuVTId: +selected.dichvuvt_id })
        this.$refs.frmTraCuuThueBaoDialogRef.hide()
      }
    }
  }
}
</script>
