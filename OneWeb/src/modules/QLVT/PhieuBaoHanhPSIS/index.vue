<template>
  <div id="phieubh_psis">
    <!-- <breadcrumb  /> -->
    <div id="phieubh_psis_content">
      <div class="list-actions-top">
        <ul class="list">
          <li v-if="tsbtnLayTT.visible" :class="{ disabledInput: !tsbtnLayTT.enabled }" @click="tsbtnLayTT_Clicked">
            <a> <span class="icon one-search"></span>{{ tsbtnLayTT.text }}</a>
          </li>
          <li v-if="tsbtnNhapMoi.visible" :class="{ disabledInput: !tsbtnNhapMoi.enabled }" @click="tsbtnNhapMoi_Clicked">
            <a> <span class="icon one-file-plus"></span>{{ tsbtnNhapMoi.text }}</a>
          </li>
          <li v-if="tsbtnGhiLai.visible" :class="{ disabledInput: !tsbtnGhiLai.enabled }" @click="tsbtnGhiLai_Clicked">
            <a> <span class="icon one-save"></span>{{ tsbtnGhiLai.text }}</a>
          </li>
          <li v-if="tsbtnXoa.visible" :class="{ disabledInput: !tsbtnXoa.enabled }" @click="tsbtnXoa_Clicked">
            <a> <span class="icon one-trash"></span>{{ tsbtnXoa.text }}</a>
          </li>
          <li v-if="tsbtnHuy.visible" :class="{ disabledInput: !tsbtnHuy.enabled }" @click="tsbtnHuy_Clicked">
            <a> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>{{ tsbtnHuy.text }}</a>
          </li>
          <li v-if="tsbtnNghiemThuLoBH.visible" :class="{ disabledInput: !tsbtnNghiemThuLoBH.enabled }" @click="tsbtnNghiemThuLoBH_Clicked">
            <a> <span class="icon one-file-save"></span>{{ tsbtnNghiemThuLoBH.text }}</a>
          </li>
          <li v-if="tsbtnTaoPhieuBHPSIS.visible" :class="{ disabledInput: !tsbtnTaoPhieuBHPSIS.enabled }" @click="tsbtnTaoPhieuBHPSIS_Clicked">
            <a> <span class="icon icon ui-1_circle-add nc-icon-glyph"></span>{{ tsbtnTaoPhieuBHPSIS.text }}</a>
          </li>
          <li v-if="tsbtnHuyPhieuPSIS.visible" :class="{ disabledInput: !tsbtnHuyPhieuPSIS.enabled }" @click="tsbtnHuyPhieuPSIS_Clicked">
            <a> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>{{ tsbtnHuyPhieuPSIS.text }}</a>
          </li>
        </ul>
      </div>
      <div id="nhapxuatvt_v2_main" class="page-content py-1">
        <Split>
          <SplitArea :size="35">
            <div style="height: 530px;" class="box-form">
              <div class="legend-title">
                Thông tin lô hồ sơ
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">Mã phiếu</div>
                    <div class="value">
                      <input :disabled="!txtMaPhieu.enabled" type="text" class="form-control" v-model="txtMaPhieu.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">Ngày tạo</div>
                    <div class="value">
                      <vue-date :disabled="!dpNgayTao.enabled" v-model="dpNgayTao.value" format="DD/MM/YYYY" type="datetime" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w120">Kho giao</div>
                    <div class="value">
                      <SelectExt ref="cboKhoGiao" v-model="cboKhoGiao.value" :dataSource="cboKhoGiao.list" dataTextField="TEN_K_NEW" dataValueField="KHO_ID" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w120">Ghi chú</div>
                    <div class="value">
                      <textarea :disabled="!txtGhiChu.enabled" class="form-control" v-model="txtGhiChu.value" style="height: 200px;"></textarea>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </SplitArea>
          <SplitArea :size="65">
            <div class="box-form">
              <div class="legend-title">
                Thông tin
              </div>
              <div class="row" style="padding-bottom: 15px;">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Ngày YC từ<span style="color: red;"> *</span></div>
                    <div class="value">
                      <div class="value-actions">
                        <vue-date :disabled="!dpNgayTaoTu.enabled" v-model="dpNgayTaoTu.value" format="DD/MM/YYYY" type="datetime" />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Đến ngày<span style="color: red;"> *</span></div>
                    <div class="value">
                      <div class="value-actions">
                        <vue-date :disabled="!dpNgayTaoDen.enabled" v-model="dpNgayTaoDen.value" format="DD/MM/YYYY" type="datetime" />
                      </div>
                    </div>
                  </div>
                </div>
                <div>
                  <DataGrid
                    ref="gridPhieuBH"
                    panelDataHeight="285px"
                    :columns="gridPhieuBH.header"
                    :dataSource="gridPhieuBH.list"
                    :enabledSelectFirstRow="false"
                    :showFilter="true"
                    :enablePagingServer="false"
                    :showColumnCheckbox="true"
                    :selectionSettings="{ enableToggle: false, checkboxOnly: false, persistSelection: true, checkboxMode: 'ResetOnRowClick' }"
                    :allowPaging="true"
                    @rowSelected="gridPhieuBH_rowSelected"
                  />
                </div>
              </div>
            </div>
          </SplitArea>
        </Split>
        <div style="display: flex;">
          <div style="width: 48%;" class="box-form">
            <div class="legend-title">
              Danh sách serial đã gán lô
            </div>
            <DataGrid
              ref="gridSerialDaGanLo"
              panelDataHeight="285px"
              :columns="gridSerialDaGanLo.header"
              :dataSource="gridSerialDaGanLo.list"
              :enabledSelectFirstRow="false"
              :showFilter="true"
              :enablePagingServer="false"
              :showColumnCheckbox="true"
              :allowPaging="true"
              @selectedItemsChanged="gridSerialDaGanLo_selectedItemsChanged"
            />
          </div>
          <div style="width: 4%; padding: 0 20px; display: flex; flex-direction: column; justify-content: center; align-items: center;" class="actions">
            <button class="btn btn-primary" @click="tsbtnGoLo_Clicked">
              <span class="-ap icon-chevron-thin-right"></span>
            </button>
            <div style="height: 20px;"></div>
            <button class="btn btn-primary" @click="tsbtnGanLo_Clicked">
              <span class="-ap icon-chevron-thin-left"></span>
            </button>
          </div>
          <div style="width: 48%;" class="box-form">
            <div class="legend-title">
              Danh sách serial chưa có lô
            </div>
            <DataGrid
              ref="gridSerialChuaGanLo"
              panelDataHeight="285px"
              :columns="gridSerialChuaGanLo.header"
              :dataSource="gridSerialChuaGanLo.list"
              :enabledSelectFirstRow="false"
              :showFilter="true"
              :enablePagingServer="false"
              :showColumnCheckbox="true"
              :allowPaging="true"
              @selectedItemsChanged="gridSerialChuaGanLo_selectedItemsChanged"
            />
            <!-- @rowSelected="gridSerialChuaGanLo_rowSelected" -->
          </div>
        </div>
      </div>
    </div>
    <ejs-dialog
      ref="ejsDialog"
      :visible="false"
      :isModal="true"
      target="#phieubh_psis"
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
        <frmTraCuuTheoSoLoPhieuPSIS v-if="ejsDialog.name == 'frmTraCuuTheoSoLoPhieuPSIS'" ref="frmTraCuuTheoSoLoPhieuPSIS" v-bind="frmTraCuuTheoSoLoPhieuPSIS.input" />
      </div>
    </ejs-dialog>
  </div>
</template>
<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import moment from 'moment'
import api from './api'
import frmTraCuuTheoSoLoPhieuPSIS from '@/modules/QLVT/TraCuuTheoSoLoPhieuPSIS'
export default {
  components: {
    breadcrumb,
    frmTraCuuTheoSoLoPhieuPSIS
  },
  data() {
    return {
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
      frmTraCuuTheoSoLoPhieuPSIS: { visible: false, input: {} },
      vquytrinh_id: 25260,
      tsbtnLayTT: { enabled: true, visible: true, text: 'Lấy TT' },
      tsbtnNhapMoi: { enabled: true, visible: true, text: 'Nhập mới' },
      tsbtnGhiLai: { enabled: true, visible: true, text: 'Ghi lại' },
      tsbtnXoa: { enabled: true, visible: true, text: 'Xóa' },
      tsbtnHuy: { enabled: true, visible: true, text: 'Hủy' },
      tsbtnNghiemThuLoBH: { enabled: true, visible: true, text: 'Nghiệm thu lô BH' },
      tsbtnTaoPhieuBHPSIS: { enabled: true, visible: true, text: 'Tạo phiếu bảo hành PSIS' },
      tsbtnHuyPhieuPSIS: { enabled: true, visible: true, text: 'Hủy phiếu PSIS' },
      txtMaPhieu: { enabled: false, value: null },
      dpNgayTao: { enabled: true, value: null },
      cboKhoGiao: { enabled: true, value: null, list: [] },
      txtGhiChu: { enabled: true, value: null },
      dpNgayTaoTu: { enabled: true, value: null },
      dpNgayTaoDen: { enabled: true, value: null },
      ckbNguoiLap: { enabled: true, value: false },
      gridPhieuBH: {
        header: [
          { fieldName: 'MAPHIEU_BH', headerText: 'Mã phiếu', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAYTAO', headerText: 'Ngày tạo', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'MA_KHO', headerText: 'Mã kho', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'TRANGTHAI', headerText: 'Trạng thái', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGUOI_CN', headerText: 'Người CN', textAlign: 'Right', allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridSerialDaGanLo: {
        header: [
          { fieldName: 'LOAI_TBI', headerText: 'Loại thiết bị', textAlign: 'Left', allowFiltering: true, allowSorting: false },
          { fieldName: 'LOHANG', headerText: 'Lô hàng', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'DONGIA', headerText: 'Đơn giá', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'SERIAL_MOI', headerText: 'Serial mới', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SERIAL', headerText: 'Serial cũ', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SOLUONG', headerText: 'Số lượng', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'TEN_DV', headerText: 'Đơn vị TN', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_TN', headerText: 'Ngày TN', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAYGIAO', headerText: 'Ngày giao', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'TEN_KHO', headerText: 'Nhà cung cấp', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'MAPHIEU_BH', headerText: 'Mã phiếu BH', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'KHO_ID_PSIS', headerText: 'Kho đối tác', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'TT', headerText: 'Trạng thái', allowFiltering: true, textAlign: 'Left', allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridSerialChuaGanLo: {
        header: [
          { fieldName: 'LOAI_TBI', headerText: 'Loại thiết bị', textAlign: 'Left', allowFiltering: true, allowSorting: false },
          { fieldName: 'LOHANG', headerText: 'Lô hàng', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'DONGIA', headerText: 'Đơn giá', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'SERIAL_MOI', headerText: 'Serial mới', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SERIAL', headerText: 'Serial cũ', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SOLUONG', headerText: 'Số lượng', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'TEN_DV', headerText: 'Đơn vị TN', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_TN', headerText: 'Ngày TN', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAYGIAO', headerText: 'Ngày giao', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'TEN_KHO', headerText: 'Nhà cung cấp', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'MAPHIEU_BH', headerText: 'Mã phiếu BH', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'KHO_ID_PSIS', headerText: 'Kho đối tác', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'TT', headerText: 'Trạng thái', allowFiltering: true, textAlign: 'Left', allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      kieu: null
    }
  },
  watch: {
    async kieu(val) {
      this.tsbtnNhapMoi.enabled = val != 0
      this.tsbtnHuy.enabled = val == 0
    },
    async 'gridPhieuBH.value'(val) {
      await this.reloadGridGanGoLo()
    }
  },
  methods: {
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
        // position: { X: 100, Y: 100 }
        position: { X: 'center', Y: 'center' }
      }
      // this.ejsDialog.config.height = 'auto'
      // this.$refs.ejsDialog.refreshPosition()
      // this.ejsDialog.config.enableResize = false
      // this.ejsDialog.config.allowDragging = false
      // this.ejsDialog.config.position = { X: 'center', Y: 'center' }
      // this.ejsDialog.config.position = { X: 420, Y: 14 }
      // }, 3000)
    },
    ejsDialogBeforeClose() {
      this.ejsDialog.name = null
      // this.$refs.ejsDialog.refreshPosition()
      this.ejsDialog.config.width = '0'
      this.ejsDialog.config.height = '0'
      this.ejsDialog.config.position = { X: 'center', Y: 'center' }
    },
    async xuLyQueries() {},
    async tsbtnLayTT_Clicked() {
      try {
        this.loading(true)
        let kq_sp_ds_phieubh_psis = await api.sp_ds_phieubh_psis(this.axios, {
          vphanvung_id: this.$root.token.getPhanVungID(),
          vnguoidung_id: this.$root.token.getNhanVienID(),
          vtungay: this.dpNgayTaoTu.value,
          vdenngay: this.dpNgayTaoDen.value
        })
        if (kq_sp_ds_phieubh_psis.data.data && kq_sp_ds_phieubh_psis.data.data.length) {
          this.gridPhieuBH.list = kq_sp_ds_phieubh_psis.data.data
        } else {
          this.gridPhieuBH.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async tsbtnNhapMoi_Clicked() {
      this.kieu = 0
      this.txtMaPhieu.value = ''
      this.dpNgayTao.value = moment(new Date()).format('DD/MM/YYYY')
      this.cboKhoGiao.value = null
      this.txtGhiChu.value = ''
    },
    async tsbtnGhiLai_Clicked() {
      await this.sp_capnhat_phieubh_psis()
    },
    async tsbtnXoa_Clicked() {
      if (!confirm('Bạn có chắc chắn muốn xóa phiếu không?')) return
      await this.sp_capnhat_phieubh_psis()
    },
    async tsbtnHuy_Clicked() {
      this.txtMaPhieu.value = ''
      this.dpNgayTao.value = null
      this.cboKhoGiao.value = null
      this.txtGhiChu.value = ''
    },
    async tsbtnNghiemThuLoBH_Clicked() {
      this.ejsDialog.name = 'frmTraCuuTheoSoLoPhieuPSIS'
      this.ejsDialog.header = 'Tra cứu'
      this.$refs.ejsDialog.show()
    },
    async tsbtnTaoPhieuBHPSIS_Clicked() {
      let kt = this.gridSerialDaGanLo.list.filter(e => ['', null].includes(e.SERIAL))
      if(kt.length) {
        this.$toast.warning("Chưa gán serial nào, không tạo phiếu PSIS được!")
        return
      }
      let ds = this.gridSerialDaGanLo.list.map(e => {
        return {
          MaVatTu: e.MAVATTU,
          Serial: e.SERIAL
        }
      })
      let kq = await api.taophieubaohanh(this.axios, {
        ListBHChiTietBaoHanh: ds,
        SoPhieuDHSXKD: this.gridPhieuBH.value.MAPHIEU_BH,
        MaDonVi: this.$root.token.getMaTinh(),
        DoiTacId: this.gridPhieuBH.value.DOITAC_ID
      })

      await this.tsbtnLayTT_Clicked()
    },
    async tsbtnHuyPhieuPSIS_Clicked() {
      if (!confirm('Bạn có chắc chắn muốn hủy phiếu!')) return
      let kq = await api.huyphieubaohanh(this.axios, { key: this.gridPhieuBH.value.MAPHIEU_BH })
      await this.tsbtnLayTT_Clicked()
    },
    async gridPhieuBH_rowSelected(e) {
      console.log(' @rowSelected="gridSerialChuaGanLo_rowSelected"')
      console.log(e)
      if (e.data) {
        this.gridPhieuBH.value = e.data
        this.txtMaPhieu.value = this.gridPhieuBH.value.MAPHIEU_BH
        this.dpNgayTao.value = this.gridPhieuBH.value.NGAYTAO
        this.cboKhoGiao.value = this.gridPhieuBH.value.KHO_ID
        this.txtGhiChu.value = this.gridPhieuBH.value.GHICHU

        this.kieu = 1
      } else {
        this.gridPhieuBH.value = null
        this.kieu = 0
      }
    },
    async gridSerialDaGanLo_selectedItemsChanged(e) {
      this.gridSerialDaGanLo.selected = e
    },
    async gridSerialChuaGanLo_selectedItemsChanged(e) {
      this.gridSerialChuaGanLo.selected = e
    },
    async tsbtnGanLo_Clicked() {
      try {
        this.loading(false)
        let kq = await api.fn_gan_phieubh_psis(this.axios, {
          vphanvung_id: this.$root.token.getPhanVungID(),
          vphieubh_id: this.gridPhieuBH.value.PHIEUBH_ID,
          vds: JSON.stringify(this.gridSerialChuaGanLo.selected),
          p_quytrinh_id: this.vquytrinh_id
        })
        if (kq.data.data == 1) {
          this.$toast.success('Gán lô thành công')
          await this.reloadGridGanGoLo()
        } else {
          this.$toast.error('Gán lô chưa thành công')
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async tsbtnGoLo_Clicked() {
      if (!confirm('Bạn có chắc chắn muốn gỡ lô!')) return
      try {
        this.loading(false)
        let kq = await api.fn_go_phieubh_psis(this.axios, {
          vphanvung_id: this.$root.token.getPhanVungID(),
          vphieubh_id: this.gridPhieuBH.value.PHIEUBH_ID,
          vds: JSON.stringify(this.gridSerialDaGanLo.selected),
          p_quytrinh_id: this.vquytrinh_id
        })
        if (kq.data.data == 1) {
          this.$toast.success('Gỡ lô thành công')
          await this.reloadGridGanGoLo()
        } else {
          this.$toast.error('Gỡ lô chưa thành công')
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async sp_capnhat_phieubh_psis() {
      try {
        this.loading(true)
        let kq = await api.sp_capnhat_phieubh_psis(this.axios, {
          vphanvung_id: this.$root.token.getPhanVungID(),
          vkieu: this.kieu,
          vphieubh_id: this.gridPhieuBH.value ? this.gridPhieuBH.value.PHIEUBH_ID : null,
          vkho_id: this.cboKhoGiao.value,
          vnguoidung_id: this.$root.token.getNguoiDungID(),
          vmay_cn: await this.$root.token.getMachine(),
          vip_cn: await this.$root.token.getIP(),
          vghichu: this.txtGhiChu.value
        })
        console.log('sp_capnhat_phieubh_psis')
        console.log(kq)
        let message = ''
        if (this.kieu == 0) message += 'Tạo mới phiếu '
        if (this.kieu == 1) message += 'Cập nhật phiếu '
        if (this.kieu == 2) message += 'Xóa phiếu '
        if (kq.data.data == 1) {
          message += 'thành công'
          this.$toast.success(message)
        } else {
          message += 'không thành công!'
          this.$toast.error(`${message}`)
        }
        await this.tsbtnLayTT_Clicked()
      } catch (e) {
        console.error('sp_capnhat_phieubh_psis')
        console.error(e)
        this.$toast.error(`${e.data.data}`)
      } finally {
        this.loading(false)
      }
    },
    async reloadGridGanGoLo() {
      try {
        this.loading(true)
        let kq_sp_lay_ds_phieubhtb_giaoden_psis_0 = await api.sp_lay_ds_phieubhtb_giaoden_psis(this.axios, {
          p_phanvung_id: this.$root.token.getPhanVungID(),
          p_kieu: 0,
          p_phieubh_id: this.gridPhieuBH.value.PHIEUBH_ID,
          p_nhanvien_id: this.$root.token.getNhanVienID(),
          p_quytrinh_id: this.vquytrinh_id
        })
        if (kq_sp_lay_ds_phieubhtb_giaoden_psis_0.data.data && kq_sp_lay_ds_phieubhtb_giaoden_psis_0.data.data.length) {
          this.gridSerialChuaGanLo.list = kq_sp_lay_ds_phieubhtb_giaoden_psis_0.data.data
        } else {
          this.gridSerialChuaGanLo.list = []
        }

        let kq_sp_lay_ds_phieubhtb_giaoden_psis_1 = await api.sp_lay_ds_phieubhtb_giaoden_psis(this.axios, {
          p_phanvung_id: this.$root.token.getPhanVungID(),
          p_kieu: 1,
          p_phieubh_id: this.gridPhieuBH.value.PHIEUBH_ID,
          p_nhanvien_id: this.$root.token.getNhanVienID(),
          p_quytrinh_id: this.vquytrinh_id
        })
        if (kq_sp_lay_ds_phieubhtb_giaoden_psis_1.data.data && kq_sp_lay_ds_phieubhtb_giaoden_psis_1.data.data.length) {
          this.gridSerialDaGanLo.list = kq_sp_lay_ds_phieubhtb_giaoden_psis_1.data.data
        } else {
          this.gridSerialDaGanLo.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    }
  },
  async mounted() {
    this.kieu = 0
    this.dpNgayTaoTu.value = moment(new Date()).subtract(30, 'days').format('DD/MM/YYYY')
    this.dpNgayTaoDen.value = moment(new Date()).format('DD/MM/YYYY')

    let kq_sp_ds_khoncc_doitac = await api.sp_ds_khoncc_doitac(this.axios, {
      vphanvung_id: this.$root.token.getPhanVungID(),
      vnhanvien_id: this.$root.token.getNhanVienID()
    })
    if (kq_sp_ds_khoncc_doitac.data.data && kq_sp_ds_khoncc_doitac.data.data.length) {
      this.cboKhoGiao.list = kq_sp_ds_khoncc_doitac.data.data
    } else {
      this.cboKhoGiao.list = []
    }
  }
}
</script>

<style>
.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: #9999 !important;
}
.disabledInput > a {
  color: #666 !important;
}
</style>
