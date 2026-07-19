<template>
  <div id="tra-cuu-theo-so-lo-phieu-psis">
    <div>
      <breadcrumb :header="header" />
      <div class="list-actions-top">
        <ul class="list">
          <li v-if="tsbtnTimKiem.visible" :class="{ disabledInput: !tsbtnTimKiem.enabled }" @click="tsbtnTimKiem_Clicked">
            <a> <span class="icon one-search"></span>{{ tsbtnTimKiem.text }}</a>
          </li>
          <li v-if="tsbtnTaoMoiChoDoiTac.visible" :class="{ disabledInput: !tsbtnTaoMoiChoDoiTac.enabled }" @click="tsbtnTaoMoiChoDoiTac_Clicked">
            <a> <span class="icon one-folder-add"></span>{{ tsbtnTaoMoiChoDoiTac.text }}</a>
          </li>
          <li v-if="tsbtnTaoMoiTuHRM.visible" :class="{ disabledInput: !tsbtnTaoMoiTuHRM.enabled }" @click="tsbtnTaoMoiTuHRM_Clicked">
            <a> <span class="icon one-folder-add"></span>{{ tsbtnTaoMoiTuHRM.text }}</a>
          </li>
          <li v-if="tsbtnChuyenDonVi.visible" :class="{ disabledInput: !tsbtnChuyenDonVi.enabled }" @click="tsbtnChuyenDonVi_Clicked">
            <a> <span class="icon one-file-arrow-right1"></span>{{ tsbtnChuyenDonVi.text }}</a>
          </li>
          <li v-if="tsbtnThemAgent.visible" :class="{ disabledInput: !tsbtnThemAgent.enabled }" @click="tsbtnThemAgent_Clicked">
            <a> <span class="icon one-folder-add"></span>{{ tsbtnThemAgent.text }}</a>
          </li>
          <li v-if="tsbtnCapNhatTrangThaiHD.visible" :class="{ disabledInput: !tsbtnCapNhatTrangThaiHD.enabled }" @click="tsbtnCapNhatTrangThaiHD_Clicked">
            <a> <span class="icon one-check"></span>{{ tsbtnCapNhatTrangThaiHD.text }}</a>
          </li>
          <li v-if="tsbtnCapNhatTrangThaiND.visible" :class="{ disabledInput: !tsbtnCapNhatTrangThaiND.enabled }" @click="tsbtnCapNhatTrangThaiND_Clicked">
            <a> <span class="icon one-check"></span>{{ tsbtnCapNhatTrangThaiND.text }}</a>
          </li>
        </ul>
      </div>
      <div class="popup-body page-content">
        <div class="box-form">
          <div class="legend-title">Thông tin nhân viên</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w140">Mã NV/Email</div>
                <div class="value">
                  <input :disabled="!txtMaNV.enabled" type="text" class="form-control highlight" v-model="txtMaNV.value" @keyup.enter="tsbtnTimKiem_Clicked" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w140">Đối tác</div>
                <div class="value">
                  <SelectExt ref="cboTaoMoiDoiTac" :disabled="!cboTaoMoiDoiTac.enabled" v-model="cboTaoMoiDoiTac.value" :dataSource="cboTaoMoiDoiTac.list" dataTextField="ten" dataValueField="id" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w140">Hình thức HĐ</div>
                <div class="value">
                  <SelectExt ref="cboHinhThucHopDong" :disabled="!cboHinhThucHopDong.enabled" v-model="cboHinhThucHopDong.value" :dataSource="cboHinhThucHopDong.list" dataTextField="hinhthuc_hd" dataValueField="hthd_id" />
                </div>
              </div>
            </div>

            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w140">Trạng thái ND</div>
                <div class="value">
                  <SelectExt ref="cboEnabled" :disabled="!cboEnabled.enabled" v-model="cboEnabled.value" :dataSource="cboEnabled.list" dataTextField="text" dataValueField="id" @select="cboEnabled_select" />
                </div>
              </div>
            </div>
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w140">Nhân viên/Đơn vị</div>
                <div class="value">
                  <textarea class="form-control" :disabled="!txtNVDV.enabled" v-model="txtNVDV.value" rows="1"></textarea>
                </div>
              </div>
            </div>
            <div class="col-sm-12 col-12" v-if="tt_userwarp">
              <!-- <div>Nhân viên: {{ tt_userwarp.ten_nv }}</div>
              <div>Đơn vị: {{ tt_userwarp.ten_dv }}</div> -->
            </div>
          </div>
        </div>
        <!-- <div class="box-form">
          <div class="legend-title">
            Thêm mới nhân viên đối tác/HRM
          </div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w140">Thông tin đăng ký HRM(Email/Mã nhân viên)</div>
                <div class="value">
                  <input :disabled="!txtThongTinDangKy.enabled" type="text" class="form-control" v-model="txtMaNV.value" @keyup.enter="txtThongTinDangKy_KeyUpEnter" />
                </div>
              </div>
            </div>
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w140">Tạo mới cho đối tác</div>
                <div class="value">
                  <SelectExt ref="cboTaoMoiDoiTac" :disabled="!cboTaoMoiDoiTac.enabled" v-model="cboTaoMoiDoiTac.value" :dataSource="cboTaoMoiDoiTac.list" dataTextField="ten" dataValueField="id" />
                </div>
              </div>
            </div>
          </div>
        </div> -->
        <div class="box-form">
          <div class="legend-title">Nhân viên tỉnh</div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w140">Đơn vị</div>
                <div class="value">
                  <!-- <SelectExt ref="cboDonVi" :disabled="!cboDonVi.enabled" v-model="cboDonVi.value" :dataSource="cboDonVi.list" dataTextField="ten_dv" dataValueField="donvi_id" /> -->
                  <ComboboxGrid ref="cboDonVi" v-bind:columns="cboDonVi.header" v-bind:dataSource="cboDonVi.list" v-model.trim="cboDonVi.value" textField="ten_dv" valueField="donvi_id" :gridStyle="{ left: 100 }" :allowPaging="true" />
                </div>
              </div>
            </div>
            <!-- :selectionSettings="{ enableToggle: false, checkboxOnly: false, persistSelection: true, checkboxMode: 'ResetOnRowClick' }" -->
            <DataGrid
              ref="gridDonVi"
              panelDataHeight="300px"
              v-bind:columns="gridDonVi.header"
              v-bind:dataSource="gridDonVi.list"
              :enabledSelectFirstRow="true"
              :showFilter="true"
              :enablePagingServer="false"
              :showColumnCheckbox="false"
              :allowPaging="true"
              @rowSelected="gridDonVi_rowSelected"
              @selectedItemsChanged="gridDonVi_selectedItemsChanged"
            />
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Ghép nối IPCC</div>
          <div class="row">
            <div class="col-sm-6 col-6">
              <div class="info-row">
                <div class="key w140">Agent</div>
                <div class="value">
                  <input :disabled="!txtAgent.enabled" type="text" class="form-control" v-model="txtAgent.value" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-6">
              <div class="info-row">
                <div class="key w140">Line</div>
                <div class="value">
                  <input :disabled="!txtLine.enabled" type="text" class="form-control" v-model="txtLine.value" />
                </div>
              </div>
            </div>
            <!-- :selectionSettings="{ enableToggle: false, checkboxOnly: false, persistSelection: true, checkboxMode: 'ResetOnRowClick' }" -->
            <DataGrid
              ref="gridIPCC"
              panelDataHeight="300px"
              v-bind:columns="gridIPCC.header"
              v-bind:dataSource="gridIPCC.list"
              :enabledSelectFirstRow="false"
              :showFilter="true"
              :enablePagingServer="false"
              :showColumnCheckbox="true"
              :allowPaging="true"
              @selectedItemsChanged="gridDonVi_selectedItemsChanged"
            />
          </div>
        </div>
      </div>
    </div>
    <ejs-dialog
      ref="ejsDialog"
      :visible="false"
      :isModal="true"
      target="#tra-cuu-theo-so-lo-phieu-psis"
      :enableResize="ejsDialog.config.enableResize"
      :resizeHandles="['All']"
      :header="ejsDialog.header"
      showCloseIcon="true"
      :width="ejsDialog.config.width"
      :height="ejsDialog.config.height"
      :minHeight="ejsDialog.config.minHeight"
      :allowDragging="ejsDialog.config.allowDragging"
      :position="ejsDialog.config.position"
      :animationSettings="ejsDialog.config.animationSettings"
      @beforeOpen="ejsDialogBeforeOpen"
      @beforeClose="ejsDialogBeforeClose"
    >
      <div v-if="ejsDialog.name" :key="uuidv4()" style="overflow-y: scroll;">
        <frmPhieuBaoHanhPSIS v-if="ejsDialog.name == 'frmPhieuBaoHanhPSIS'" ref="frmPhieuBaoHanhPSIS" v-bind="frmPhieuBaoHanhPSIS.input" />
      </div>
    </ejs-dialog>
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
      header: {
        title: 'Hoàn công IMS NET',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
      ejsDialog: {
        header: 'Tiêu đề',
        name: null,
        visible: false,
        config: {
          width: '95%',
          height: 'auto',
          minHeight: '150',
          enableResize: true,
          allowDragging: true,
          position: { X: 'center', Y: 'center' },
          animationSettings: { effect: 'Fade' }
        }
      },
      frmPhieuBaoHanhPSIS: { visible: false, input: {} },
      tsbtnTimKiem: { enabled: true, visible: true, text: 'Tìm kiếm' },
      tsbtnTaoMoiChoDoiTac: { enabled: true, visible: true, text: 'Tạo mới cho đối tác' },
      tsbtnTaoMoiTuHRM: { enabled: true, visible: true, text: 'Tạo mới từ HRM' },
      tsbtnChuyenDonVi: { enabled: true, visible: true, text: 'Chuyển đơn vị' },
      tsbtnThemAgent: { enabled: true, visible: true, text: 'Thêm agent' },
      tsbtnCapNhatTrangThaiHD: { enabled: true, visible: true, text: 'Cập nhật hình thức HĐ' },
      tsbtnCapNhatTrangThaiND: { enabled: true, visible: true, text: 'Cập nhật trạng thái ND' },
      txtMaNV: { enabled: true, value: '' },
      txtThongTinDangKy: { enabled: true, value: '' },
      cboTaoMoiDoiTac: { enabled: true, value: '', list: [] },
      cboHinhThucHopDong: { enabled: true, value: '', list: [] },
      cboEnabled: {
        enabled: true,
        value: '',
        list: []
      },
      txtNVDV: { enabled: false, value: '' },
      cboDonVi: {
        enabled: true,
        value: '',
        header: [
          // isGroupedColumn: true
          { width: 40, fieldName: 'donvi_id', headerText: 'ID', isPrimaryKey: true, allowFiltering: true },
          { width: 80, fieldName: 'ma_dv', headerText: 'Mã ĐV', allowFiltering: true },
          { width: 140, fieldName: 'ten_dv', headerText: 'Tên ĐV', allowFiltering: true },
          { width: 140, fieldName: 'ten_dv_cha', headerText: 'Tên ĐV cha', allowFiltering: true }
        ],
        list: []
      },
      txtAgent: { enabled: true, value: '' },
      txtLine: { enabled: false, value: '' },
      gridDonVi: {
        header: [
          // { fieldName: 'huonggiao', headerText: '', isGroupedColumn: true },
          { fieldName: 'tentinh', headerText: 'Tỉnh', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'ma_nd', headerText: 'Mã người dùng', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'ma_nv', headerText: 'Mã Nhân viên', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'ten_nv', headerText: 'Tên nhân viên', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'sdt', headerText: 'Số điện thoại ', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'nhanvien_id', headerText: 'ID', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'ten_dv', headerText: 'Đơn vị', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'hinhthuc_hd', headerText: 'Hình thức HĐ', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'trangthai_nd', headerText: 'Trạng thái ND', textAlign: 'Left', allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridIPCC: {
        header: [
          { fieldName: '', headerText: '', width: 50, allowFiltering: false, template: this.gridIPCCDeleteTemplate(this) },
          { fieldName: 'user_ipcc', headerText: 'Agent', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'line', headerText: 'Line', textAlign: 'Left', allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      tt_userwarp: null
    }
  },
  methods: {
    gridIPCCDeleteTemplate: function (parent) {
      return function () {
        return {
          template: {
            template: `<div style="display: flex; justify-content: center"><button class="btn btn-danger" @click="tsbtnXoa_Clicked">Xóa</button></div>`,
            data() {
              return {
                data: {}
              }
            },
            computed: {},
            async mounted() {
              console.log(parent)
            },
            methods: {
              async tsbtnXoa_Clicked() {
                console.log('tsbtnXoa_Clicked')
                console.log(this.data.user_ipcc)
                if (!confirm(`Bạn có chắc chắn muốn xóa agent ${this.data.user_ipcc} không?`)) return
                try {
                  this.loading(true)
                  let kq = await api.xoa_agent(this.axios, {
                    p_user_ipcc: this.data.user_ipcc
                  })
                  if ((kq.data.error_code = 'BSS-00000000')) {
                    this.$toast.success('Xóa thành công!')
                    await parent.lay_ds_agent()
                  } else {
                    this.$toast.error('Xóa không thành công!')
                  }
                } catch (e) {
                } finally {
                  this.loading(false)
                }
              }
            }
          }
        }
      }
    },
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    },
    ejsDialogBeforeOpen() {
      let width = null
      if (this.ejsDialog.name == 'frmNhapText') {
        width = 'auto'
      } else {
        width = '95%'
      }
      this.ejsDialog.config = {
        ...this.ejsDialog.config,
        width: width,
        height: 'auto',
        position: { X: 'center', Y: 'center' }
      }
    },
    ejsDialogBeforeClose() {
      this.ejsDialog.name = null
      this.ejsDialog.config.width = '0'
      this.ejsDialog.config.height = '0'
      this.ejsDialog.config.position = { X: 'center', Y: 'center' }
    },
    async gridDonVi_selectedItemsChanged(e) {
      this.gridDonVi.selected = e
    },
    async gridDonVi_rowSelected(e) {
      if (e.data) {
        this.gridDonVi.value = e.data
        // this.cboHinhThucHopDong.value = this.gridDonVi.value.hthd_id
        this.cboHinhThucHopDong.value = this.gridDonVi.value.hthd_id
        this.cboEnabled.value = this.gridDonVi.value.trangthai
        await this.lay_ds_agent()
      } else {
        this.gridDonVi.value = null
      }
    },
    async cboEnabled_select(e) {
      console.log('cboEnabled_select')
      console.log(e)
    },
    async tsbtnTimKiem_Clicked() {
      if (!this.txtMaNV.value.length) {
        this.$toast.warning('Vui lòng nhập mã NV/Email!')
        return
      }
      this.cboTaoMoiDoiTac.value = null
      this.tt_userwarp = null
      this.gridDonVi.list = []
      this.gridIPCC.list = []

      await this.lay_tt_userwarp()
      await this.sp_lay_hinhthuc_hd_nv()
    },
    async tsbtnTaoMoiChoDoiTac_Clicked() {
      if (!this.txtMaNV.value.length) {
        this.$toast.warning('Vui lòng nhập mã NV!')
        return
      }
      if (!this.cboTaoMoiDoiTac.value) {
        this.$toast.warning('Vui chọn đối tác!')
        return
      }
      try {
        this.loading(true)
        // let kq_xacThucNhanVien = await api.xacThucNhanVien(this.axios, { maNhanVien: this.txtMaNV.value })
        // if (kq_xacThucNhanVien.data.data) {
        // let tt = kq_xacThucNhanVien.data.data.ctHoSoNhanSu[0]
        if (!confirm('Bạn có muốn tạo mới cho đối tác không?')) return
        let kq = await api.ins_userwarp(this.axios, {
          p_ma_nv: this.txtMaNV.value,
          p_ten_nv: null,
          p_so_dt: null,
          p_gioitinh: null,
          p_ngaysinh: null,
          p_chucdanh: null,
          p_email: null,
          p_doitac: this.cboTaoMoiDoiTac.value,
          p_donvi_id: this.cboDonVi.value
        })
        if (kq.data.error_code == 'BSS-00000000') {
          this.$toast.success(`Thành công`)
          await this.tsbtnTimKiem_Clicked()
        } else {
          this.$toast.error(`${kq.data.message}`)
        }
        // } else {
        //   this.$toast.error('Chưa có thông tin nhân viên!')
        //   return
        // }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async tsbtnTaoMoiTuHRM_Clicked() {
      if (!this.txtMaNV.value.length) {
        this.$toast.error('Vui lòng nhập mã NV!')
        return
      }
      try {
        this.loading(true)
        let kq_xacthuc = null
        if (this.txtMaNV.value.includes('@')) {
          kq_xacthuc = await api.xacThucThongTin(this.axios, { email: this.txtMaNV.value })
        } else {
          kq_xacthuc = await api.xacThucNhanVien(this.axios, { maNhanVien: this.txtMaNV.value })
        }
        if (kq_xacthuc.data.data && kq_xacthuc.data.data.ctHoSoNhanSu && kq_xacthuc.data.data.ctHoSoNhanSu.length) {
          let tt = kq_xacthuc.data.data.ctHoSoNhanSu[0]
          if (!confirm('Bạn có muốn tạo mới từ HRM không?')) return
          let kq_ins_userwarp = await api.ins_userwarp(this.axios, {
            p_ma_nv: tt.maNhanVien,
            p_ten_nv: tt.tenNhanVien,
            p_so_dt: tt.sdtDiDong,
            p_gioitinh: tt.gioiTinh == 'nu' ? 0 : 1,
            p_ngaysinh: tt.ngaySinh,
            p_chucdanh: tt.tenChucDanh,
            p_email: tt.email,
            p_doitac: 10,
            p_donvi_id: this.cboDonVi.value
          })
          if (kq_ins_userwarp.data.error_code == 'BSS-00000000') {
            this.$toast.success(`Thành công`)
            await this.tsbtnTimKiem_Clicked()
          } else {
            this.$toast.error(`${kq_ins_userwarp.data.message}`)
          }
        } else {
          this.$toast.error('Chưa có thông tin nhân viên!')
          return
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async tsbtnChuyenDonVi_Clicked() {
      if (!this.gridDonVi.list.length) {
        this.$toast.warning('Chưa tìm kiếm mã nhân viên!')
        return
      }
      if (!this.cboDonVi.value) {
        this.$toast.warning('Chưa chọn đơn vị!')
        return
      }
      if (!confirm('Bạn có chắc chắn muốn chuyển đơn vị!')) return
      try {
        this.loading(true)
        let kq = await api.update_dv_userwarp(this.axios, { p_ma_nv: this.gridDonVi.list[0].ma_nv, p_dv_id: this.cboDonVi.value })
        if (kq.data.error_code == 'BSS-00000000') {
          this.$toast.success(`Thành công`)
          await this.lay_tt_userwarp()
        } else {
          this.$toast.error(`${kq.data.message}`)
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async tsbtnThemAgent_Clicked() {
      if (!this.gridDonVi.list.length) {
        this.$toast.warning('Chưa tìm kiếm mã nhân viên!')
        return
      }
      if (!this.txtAgent.value.length) {
        this.$toast.warning('Chưa nhập agent!')
        return
      }
      try {
        this.loading(true)
        let kq = await api.gan_useripcc(this.axios, { p_ma_nv: this.gridDonVi.list[0].ma_nv, p_agent: this.txtAgent.value })
        if (kq.data.error_code == 'BSS-00000000') {
          this.$toast.success(`Thành công`)
          this.txtAgent.value = ''
          this.txtLine.value = ''
          await this.lay_ds_agent()
        } else {
          this.$toast.error(`${kq.data.message}`)
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async tsbtnCapNhatTrangThaiHD_Clicked() {
      if (!confirm('Bạn có chắc chắn muốn đổi trạng thái hợp đồng!')) return

      try {
        this.loading(true)
        let kq = await api.update_hthd_nhanvien(this.axios, { p_ma_nv: this.txtMaNV.value, p_hthd_id: this.cboHinhThucHopDong.value })
        if (kq.data.error_code == 'BSS-00000000') {
          this.$toast.success(`Cập nhật thành công!`)
          await this.tsbtnTimKiem_Clicked()
        } else {
          this.$toast.error(`${kq.data.message}`)
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async tsbtnCapNhatTrangThaiND_Clicked() {
      if (!confirm('Bạn có chắc chắn muốn đổi trạng thái người dùng!')) return
      let kq = await api.lay_nv_theo_ndid(this.axios, {
        p_nguoidung_id: this.gridDonVi.value.nguoidung_id
      })
      if (kq.data.data == 'OK') {
        try {
          this.loading(true)
          let kq = await api.update_trangthai_nhanvien_dai(this.axios, { p_ma_nv: this.gridDonVi.list[0].ma_nv, p_trangthai: this.cboEnabled.value })
          if (kq.data.error_code == 'BSS-00000000') {
            this.$toast.success(`Cập nhật thành công!`)
          } else {
            this.$toast.error(`${kq.data.message}`)
          }
        } catch (e) {
        } finally {
          this.loading(false)
        }
        await this.tsbtnTimKiem_Clicked()
      } else {
        console.log('this.cboEnabled.value')
        console.log(this.cboEnabled.value)
        if (this.cboEnabled.value == 1) this.cboEnabled.value = 0
        else if (this.cboEnabled.value == 0) this.cboEnabled.value = 1
        this.$toast.error(`${kq.data.message}`)
      }
    },
    async lay_tt_userwarp() {
      try {
        this.loading(true)
        let kq = await api.lay_tt_userwarp(this.axios, { key: this.txtMaNV.value })
        if (kq.data.data && kq.data.data.length) {
          this.gridDonVi.list = kq.data.data
          this.tt_userwarp = kq.data.data[0]
          this.txtNVDV.value = `${this.tt_userwarp.ten_nv}/${this.tt_userwarp.ten_dv}`
        } else {
          this.gridDonVi.list = []
          this.tt_userwarp = null
          this.$toast.error(`${kq.data.message}`)
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async lay_ds_agent() {
      if (!this.gridDonVi.value) return
      try {
        this.loading(true)
        let kq = await api.lay_ds_agent(this.axios, { p_ma_nv: this.gridDonVi.value.ma_nv })
        if (kq.data.data && kq.data.data.length) {
          this.gridIPCC.list = kq.data.data
        } else {
          this.gridIPCC.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async sp_lay_hinhthuc_hd_nv() {
      try {
        this.loading(true)
        let kq = await api.sp_lay_hinhthuc_hd_nv(this.axios, { p_ma_nv: this.txtMaNV.value })
        if (kq.data.data && kq.data.data.length) {
          this.cboHinhThucHopDong.list = kq.data.data
        } else {
          this.cboHinhThucHopDong.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async sp_cbx_timkiem_tt_doitac_dai() {
      try {
        this.loading(true)
        let kq_sp_cbx_timkiem_tt_doitac_dai = await api.sp_cbx_timkiem_tt_doitac_dai(this.axios, {})
        if (kq_sp_cbx_timkiem_tt_doitac_dai.data.data && kq_sp_cbx_timkiem_tt_doitac_dai.data.data.length) {
          this.cboTaoMoiDoiTac.list = kq_sp_cbx_timkiem_tt_doitac_dai.data.data
        } else {
          this.cboTaoMoiDoiTac.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async lay_ds_dv_dai() {
      try {
        this.loading(true)
        let kq = await api.lay_ds_dv_dai(this.axios, {})
        if (kq.data.data && kq.data.data.length) {
          this.cboDonVi.list = kq.data.data
        } else {
          this.cboDonVi.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    }
  },
  watch: {},
  async mounted() {
    await this.sp_cbx_timkiem_tt_doitac_dai()
    await this.lay_ds_dv_dai()
    await this.lay_ds_agent()
    this.cboEnabled.list = [
      { id: 1, text: 'Mở' },
      { id: 0, text: 'Khoá' }
    ]
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
