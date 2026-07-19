<template>
  <div class="modal-content popup-box">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="onSave">
            <span class="icon one-save"></span>Ghi lại(F9)
          </a>
        </li>
        <li>
          <a @click="onClose">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">Danh sách nhân viên giao việc</div>
        <div class="info-row">
          <div class="key w60">ND giao</div>
          <div class="value">
            <textarea class="form-control" style="height: 100px;resize: none;" v-model="noiDungGiao"></textarea>
          </div>
        </div>
        <div class="table-content">
          <DataGrid
            :columns="dataGrid.nhanVienGiaoViec.headers"
            :dataSource="dataGrid.nhanVienGiaoViec.dataSources"
            :showColumnCheckbox="false"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
          />
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">Danh sách nhân viên đã giao nhiệm vụ</div>
        <div class="table-content">
          <DataGrid
            :columns="dataGrid.nhanVienDaGiaoNV.headers"
            :dataSource="dataGrid.nhanVienDaGiaoNV.dataSources"
            :showColumnCheckbox="false"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            @selectedRowChanged="onSelectedRowChanged"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '../api'
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns'
MultiSelect.Inject(CheckBoxSelection)

let dsNhiemVu = []
let dsKetQua = []

export default {
  name: 'PopupGiaoViec',
  props: ['phieuId', 'donViId', 'nhanVienId', 'ngayGiao'],
  data () {
    return {
      noiDungGiao: '',
      ketQuaXuLyOptions: [],
      dataGrid: {
        nhanVienGiaoViec: {
          headers: [
            { fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true },
            { fieldName: 'ma_nv', headerText: 'Mã nhân viên', allowFiltering: true },
            { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true },
            { fieldName: 'so_dt', headerText: 'Điện thoại', allowFiltering: true, width: 150 },
            { fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true, width: 350, template: this.getColumnNhiemVu(this) },
            { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true, template: this.getColumnGhiChu(this) }
          ],
          dataSources: [],
          selectedRows: []
        },
        nhanVienDaGiaoNV: {
          headers: [
            { fieldName: 'ma_nv', headerText: 'Mã nhân viên', allowFiltering: true },
            { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true },
            { fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true },
            { fieldName: 'so_dt', headerText: 'Điện thoại', allowFiltering: true },
            { fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true },
            { fieldName: 'ketqua_xl', headerText: 'Kết quả XL', allowFiltering: true, template: this.getColumnKetQua(this) },
            { fieldName: 'trangthai', headerText: 'Trạng thái', allowFiltering: true },
            { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true },
            { fieldName: '', headerText: '', allowFiltering: true, template: this.getColumnXoa(this) }
          ],
          dataSources: [],
          selectedRow: {}
        },
        vkieu_nv_macdinh: 0
      }
    }
  },

  methods: {
    async init (kieuNhanVienMacDinh) {
      this.resetFilter()
      this.vkieu_nv_macdinh = kieuNhanVienMacDinh
      this.loading(true)
      try {
        await Promise.all([
          this.getDanhSachNhiemVuGiao(),
          this.getDanhSachKetQuaXuLy(),
          this.getDanhSachNhanVienGiaoViec(),
          this.getDanhSachNhanVienDaGiaoViec()
        ])
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    resetFilter () {
      this.noiDungGiao = ''
      this.dataGrid.nhanVienGiaoViec.selectedRows = []
      dsKetQua = []
      dsNhiemVu = []
    },
    async getDanhSachNhiemVuGiao () {
      try {
        const response = await api.getDanhSachNhiemVuGiao(this.axios, { nhiemvu_id: 21 })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          dsNhiemVu = data.data
        } else {
          dsNhiemVu = []
        }
      } catch (e) {
        this.$root.$toast.error('Lấy danh sách nhiệm vụ không thành công')
        dsNhiemVu = []
      }
    },
    async getDanhSachKetQuaXuLy () {
      try {
        const response = await api.getDanhSachKetQuaXuLy(this.axios, { nhomkq_id: 3 })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          dsKetQua = data.data
        } else {
          dsKetQua = []
        }
      } catch (e) {
        this.$root.$toast.error('Lấy danh sách kết quả xử lý không thành công')
        dsKetQua = []
      }
    },
    async getDanhSachNhanVienGiaoViec () {
      try {
        const response = await api.getDanhSachNhanVienGiaoViec(this.axios, {
          'kieu_id': 1,
          'phieu_id': this.phieuId,
          'kieugiao': 0,
          'kieu_nv_macdinh': this.vkieu_nv_macdinh
        })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200' && data.data.length > 0) {
          this.dataGrid.nhanVienGiaoViec.dataSources = data.data.filter(elm => !elm.nhiemvu_id || elm.nhiemvu_id !== 20)
        } else {
          this.$root.$toast.warning('Không có nhân viên nào!')
          this.dataGrid.nhanVienGiaoViec.dataSources = []
        }
      } catch (e) {
        this.$root.$toast.error('Lấy danh sách nhân viên giao việc không thành công')
        this.dataGrid.nhanVienGiaoViec.dataSources = []
      }
    },
    async getDanhSachNhanVienDaGiaoViec () {
      try {
        const response = await api.getDanhSachNhanVienDaGiaoViec(this.axios, {
          'kieu_id': 3,
          'phieu_id': this.phieuId
        })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200' && data.data.length > 0) {
          const dataSources = data.data.filter(elm => +elm.nhiemvu_id === 21)
          this.dataGrid.nhanVienDaGiaoNV.dataSources = dataSources
          if (dataSources.length > 0) this.noiDungGiao = dataSources[0].nhiemvu
        } else {
          this.dataGrid.nhanVienDaGiaoNV.dataSources = []
        }
      } catch (e) {
        this.$root.$toast.error('Lấy danh sách nhân viên đã giao việc không thành công')
        this.dataGrid.nhanVienDaGiaoNV.dataSources = []
      }
    },
    async onSave () {
      const selectedRows = this.dataGrid.nhanVienGiaoViec.selectedRows
      if (selectedRows.length === 0) {
        this.$root.$toast.warning('Hãy nhập nhiệm vụ cho nhân viên!')
        return false
      }
      if (selectedRows.length > 1) {
        this.$root.$toast.warning('Chỉ được chọn 1 nhân viên làm nhiệm vụ "Nhân viên thu cước tiếp nhận yêu cầu từ vấn phát triển hợp đồng"!')
        return false
      }
      if (selectedRows.some(nhanVien => nhanVien.soDt && nhanVien.soDt.length > 15)) {
        this.$root.$toast.warning('Số điện thoại không được quá 15 ký tự !')
        return false
      }
      const nhanVien = selectedRows[0]
      this.loading(true)
      try {
        const response = await api.giaoViecNhanVien(
          this.axios,
          {
            nhanvien_nhan_id: nhanVien.nhanVienId,
            nd_giao: this.noiDungGiao,
            nhiemvu_id: nhanVien.nhiemvu[0],
            phieu_id: this.phieuId,
            so_dt: nhanVien.soDt,
            nhanvien_giao_id: this.nhanVienId
          }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.$root.$toast.success('Cập nhật dữ liệu thành công!')
          this.dataGrid
            .nhanVienGiaoViec
            .dataSources
            .forEach(elm => { elm.nhiemvu = null })
          await this.getDanhSachNhanVienDaGiaoViec()
        } else {
          this.$root.$toast.error(data.message)
        }
      } catch (e) {
        this.$root.$toast.error('Có lỗi trong quá trình giao việc nhân viên')
      } finally {
        this.loading(false)
      }
    },
    onClose () {
      this.noiDungGiao = ''
      this.$emit('onClose')
    },
    onSelectedRowChanged (data) {
      if (data) this.dataGrid.nhanVienDaGiaoNV.selectedRow = { ...data }
    },
    getColumnNhiemVu (parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="select-custom">
                <ejs-multiselect
                  mode="CheckBox"
                  selectAllText="Chọn tất cả"
                  unSelectAllText="Bỏ chọn tất cả"
                  locale="vi-VN"
                  width="150%"
                  v-model="data.nhiemvu"
                  :showSelectAll="true"
                  :allowFiltering="true"
                  :dataSource="dataSources"
                  :fields="{ text: 'nhiemvu', value: 'nhiemvu_id' }"
                ></ejs-multiselect>
              </div>
            `,
            data () {
              return {
                parent: parent,
                dataSources: dsNhiemVu,
                data: {}
              }
            },
            watch: {
              'data.nhiemvu' (newVal) {
                const dataSources = this.parent
                  .dataGrid
                  .nhanVienGiaoViec
                  .dataSources
                const nhanVien = dataSources.find(item => item.nhanvien_th_id === this.data.nhanvien_th_id)
                let selectedRows = this.parent.dataGrid.nhanVienGiaoViec.selectedRows
                if (selectedRows.findIndex(element => element.nhanVienId === this.data.nhanvien_th_id) === -1) {
                  selectedRows.push({ nhanVienId: this.data.nhanvien_th_id, soDt: nhanVien.so_dt, nhiemvu: newVal })
                } else {
                  selectedRows = selectedRows.filter(element => element.nhanVienId !== this.data.nhanvien_th_id)
                }
                this.parent.dataGrid.nhanVienGiaoViec.selectedRows = [...selectedRows]
              }
            }
          }
        }
      }
    },
    getColumnGhiChu (parent) {
      return function () {
        return {
          template: {
            template: `<input type="text" class="form-control" v-model="data.ghichu" />`,
            data () {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    getColumnKetQua (parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="select-custom" ref="ketQuaXuLyRef">
                <SelectExt
                  v-model="data.kqxl_id"
                  dataValueField="kqxl_id"
                  dataTextField="ketqua_xl"
                  :settings="{ dropdownParent: $refs['ketQuaXuLyRef'] }"
                  :dataSource="dataSources"
                />
              </div>
            `,
            data () {
              return {
                parent: parent,
                dataSources: [],
                data: {}
              }
            },
            mounted () {
              this.dataSources = dsKetQua
            }
          }
        }
      }
    },
    getColumnXoa (parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="center">
                <span title="Xóa giao việc">
                  <button class="btn btn-sm btn-danger" @click="parent.onDelete(data)">
                    <span class="-ap icon-close"></span>
                  </button>
                </span>
              </div>
            `,
            data () {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    async onDelete (data) {
      this.loading(true)
      try {
        const response = await api.xoaGiaoViecNhanVien(
          this.axios,
          {
            nhanvien_th_id: data.nhanvien_th_id,
            phieu_id: this.phieuId,
            nhiemvu_id: data.nhiemvu_id
          }
        )
        const result = await response.data
        if (result.error_code === 'BSS-00000000' && result.error === '200') {
          this.$root.$toast.success('Xóa giao việc thành công')
          await this.getDanhSachNhanVienDaGiaoViec()
        } else {
          this.$root.$toast.error('Xóa giao việc không thành công')
        }
      } catch (e) {
        this.$root.$toast.error('Không thể xóa giao việc')
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>

<style>
.e-multi-select-wrapper {
  text-align: left;
}
</style>
