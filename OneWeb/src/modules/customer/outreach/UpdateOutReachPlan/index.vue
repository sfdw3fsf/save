<template>
  <div id="tra-cuu-theo-so-lo-phieu-psis">
    <div>
      <breadcrumb :header="header" />
      <div class="list-actions-top">
        <ul class="list">
          <!-- <li v-if="tsbtnTest.visible" :class="{ disabledInput: !tsbtnTest.enabled }" @click="tsbtnTest_Clicked">
              <a> <span class="icon one-reload-next"></span>{{ tsbtnTest.text }}</a>
            </li> -->
          <li v-if="tsbtnLayDS.visible" :class="{ disabledInput: !tsbtnLayDS.enabled }" @click="tsbtnLayDS_Clicked">
            <a> <span class="icon one-refresh"></span>{{ tsbtnLayDS.text }}</a>
          </li>
          <li v-if="tsbtnGhiLai.visible" :class="{ disabledInput: !tsbtnGhiLai.enabled }" @click="tsbtnGhiLai_Clicked">
            <a> <span class="icon one-save1"></span>{{ tsbtnGhiLai.text }}</a>
          </li>
          <li v-if="tsbtnHoanThanh.visible" :class="{ disabledInput: !tsbtnHoanThanh.enabled }" @click="tsbtnHoanThanh_Clicked">
            <a> <span class="icon one-check"></span>{{ tsbtnHoanThanh.text }}</a>
          </li>
          <li v-if="tsbtnTaoBaoGia.visible" :class="{ disabledInput: !tsbtnTaoBaoGia.enabled }" @click="tsbtnTaoBaoGia_Clicked">
            <a> <span class="icon one-coin-add"></span>{{ tsbtnTaoBaoGia.text }}</a>
          </li>
          <li v-if="tsbtnTaoKhaoSat.visible" :class="{ disabledInput: !tsbtnTaoKhaoSat.enabled }" @click="tsbtnTaoKhaoSat_Clicked">
            <a> <span class="icon one-folder-add"></span>{{ tsbtnTaoKhaoSat.text }}</a>
          </li>
          <li v-if="tsbtnTaoDonHang.visible" :class="{ disabledInput: !tsbtnTaoDonHang.enabled }" @click="tsbtnTaoDonHang_Clicked">
            <a> <span class="icon one-folder-add"></span>{{ tsbtnTaoDonHang.text }}</a>
          </li>
          <li v-if="tsbtnTaoHopDong.visible" :class="{ disabledInput: !tsbtnTaoHopDong.enabled }" @click="tsbtnTaoHopDong_Clicked">
            <a> <span class="icon one-folder-add"></span>{{ tsbtnTaoHopDong.text }}</a>
          </li>
        </ul>
      </div>
      <div class="popup-body page-content">
        <div class="row" style="min-height: 650px;">
          <div class="col-sm-6 col-12" >
            <div class="box-form" style="height: 100%;">
              <div class="legend-title">
                Thông tin tiếp cân
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Mã kế hoạch(*)</div>
                    <div class="value">
                      <input :disabled="!txtMaKeHoach.enabled" type="text" class="form-control" v-model="txtMaKeHoach.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Mã nhiệm vụ(*)</div>
                    <div class="value">
                      <input :disabled="!txtMaNhiemVu.enabled" type="text" class="form-control" v-model="txtMaNhiemVu.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Loại nhiệm vụ(*)</div>
                    <div class="value">
                      <SelectExt ref="cboLoaiNhiemVu" v-model="cboLoaiNhiemVu.value" :dataSource="cboLoaiNhiemVu.list" dataTextField="MA_CT" dataValueField="DUAN_ID" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Chương trình HĐ(*)</div>
                    <div class="value">
                      <input :disabled="!txtChuongTrinhHD.enabled" type="text" class="form-control" v-model="txtChuongTrinhHD.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Hình thức(*)</div>
                    <div class="value">
                      <SelectExt ref="cboHinhThuc" v-model="cboHinhThuc.value" :dataSource="cboHinhThuc.list" dataTextField="MA_CT" dataValueField="DUAN_ID" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Ngày thực hiện(*)</div>
                    <div class="value m-0 p-0">
                      <vue-date :disabled="!dpNgayThucHien.enabled" v-model="dpNgayThucHien.value" format="DD/MM/YYYY" type="datetime" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Dịch vụ(*)</div>
                    <div class="value">
                      <SelectExt ref="cboDichVu" v-model="cboDichVu.value" :dataSource="cboDichVu.list" dataTextField="MA_CT" dataValueField="DUAN_ID" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Loại hình(*)</div>
                    <div class="value">
                      <SelectExt ref="cboLoaiHinh" v-model="cboLoaiHinh.value" :dataSource="cboLoaiHinh.list" dataTextField="MA_CT" dataValueField="DUAN_ID" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w130">Nội dung(*)</div>
                    <div class="value">
                      <textarea class="form-control" v-model="txtNoiDung.value" style="height: 80px;"></textarea>
                    </div>
                  </div>
                </div>
              </div>
              <div class="legend-title mt-2">
                Danh sách vật phẩm CSKH
              </div>
              <DataGrid
                ref="gridVPCSKH"
                panelDataHeight="120px"
                v-bind:columns="gridVPCSKH.header"
                v-bind:dataSource="gridVPCSKH.list"
                :enabledSelectFirstRow="false"
                :showFilter="true"
                :enablePagingServer="false"
                :showColumnCheckbox="true"
                :allowPaging="true"
                @rowSelected="gridVPCSKH_rowSelected"
                @selectedItemsChanged="gridVPCSKH_selectedItemsChanged"
              />
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="box-form" style="height: 100%;">
              <div class="legend-title">
                Thông tin khách hàng
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Mã KH</div>
                    <div class="value">
                      <input :disabled="!txtMaKH.enabled" type="text" class="form-control" v-model="txtMaKH.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Tên KH</div>
                    <div class="value">
                      <input :disabled="!txtTenKH.enabled" type="text" class="form-control" v-model="txtTenKH.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Mã số thuế</div>
                    <div class="value">
                      <input :disabled="!txtMaSoThue.enabled" type="text" class="form-control" v-model="txtMaSoThue.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Số giầy tờ</div>
                    <div class="value">
                      <input :disabled="!txtSoGiayTo.enabled" type="text" class="form-control" v-model="txtSoGiayTo.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w130">Địa chỉ</div>
                    <div class="value">
                      <input :disabled="!txtDiaChi.enabled" type="text" class="form-control" v-model="txtDiaChi.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Người tiếp cận(*)</div>
                    <div class="value">
                      <input :disabled="!txtNguoiTiepCan.enabled" type="text" class="form-control" v-model="txtNguoiTiepCan.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Chức vụ(*)</div>
                    <div class="value">
                      <input :disabled="!txtChucVu.enabled" type="text" class="form-control" v-model="txtChucVu.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Số điện thoại</div>
                    <div class="value">
                      <input :disabled="!txtSoDienThoai.enabled" type="text" class="form-control" v-model="txtSoDienThoai.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Email</div>
                    <div class="value">
                      <input :disabled="!txtEmail.enabled" type="text" class="form-control" v-model="txtEmail.value" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="legend-title" style="margin-top: 62px">
                Kết quả tiếp cận
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Kết quả(*)</div>
                    <div class="value">
                      <SelectExt ref="cboKetQua" v-model="cboKetQua.value" :dataSource="cboKetQua.list" dataTextField="MA_CT" dataValueField="DUAN_ID" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">Thông tin khác</div>
                    <div class="value">
                      <input :disabled="!txtThongTinKhac.enabled" type="text" class="form-control" v-model="txtThongTinKhac.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">DT dự kiến tăng</div>
                    <div class="value">
                      <input :disabled="!txtDTDKTang.enabled" type="text" class="form-control" v-model="txtDTDKTang.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w130">DT dự kiến giảm</div>
                    <div class="value">
                      <input :disabled="!txtDTDKGiam.enabled" type="text" class="form-control" v-model="txtDTDKGiam.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w130">TT đối thủ</div>
                    <div class="value">
                      <input :disabled="!txtTTDoiThu.enabled" type="text" class="form-control" v-model="txtTTDoiThu.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w130">Đề xuất</div>
                    <div class="value">
                      <input :disabled="!txtDeXuat.enabled" type="text" class="form-control" v-model="txtDeXuat.value" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form mt-3">
          <div class="legend-title">
            Danh sách kế hoạch tiếp cận
          </div>
          <div class="row">
            <!-- :selectionSettings="{ enableToggle: false, checkboxOnly: false, persistSelection: true, checkboxMode: 'ResetOnRowClick' }" -->
            <DataGrid
              ref="gridKeHoach"
              panelDataHeight="285px"
              v-bind:columns="gridKeHoach.header"
              v-bind:dataSource="gridKeHoach.list"
              :enabledSelectFirstRow="false"
              :showFilter="true"
              :enablePagingServer="false"
              :showColumnCheckbox="true"
              :allowPaging="true"
              @rowSelected="gridKeHoach_rowSelected"
              @selectedItemsChanged="gridKeHoach_selectedItemsChanged"
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
import Vue from 'vue'
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
      header: {
        title: 'Báo cáo kế hoạch tiếp cận khách hàng',
        list: []
      },
      tsbtnLayDS: { enabled: true, visible: true, text: 'Lấy DS' },
      tsbtnGhiLai: { enabled: true, visible: true, text: 'Ghi lại' },
      tsbtnHoanThanh: { enabled: true, visible: true, text: 'Hoàn thành' },
      tsbtnTaoBaoGia: { enabled: true, visible: true, text: 'Tạo báo giá' },
      tsbtnTaoKhaoSat: { enabled: true, visible: true, text: 'Tạo khảo sát' },
      tsbtnTaoDonHang: { enabled: true, visible: true, text: 'Tạo đơn hàng' },
      tsbtnTaoHopDong: { enabled: true, visible: true, text: 'Tạo hợp đồng' },
      txtMaKeHoach: { enabled: true, value: '' },
      txtMaNhiemVu: { enabled: true, value: '' },
      cboLoaiNhiemVu: { enabled: true, value: '', list: [] },
      txtChuongTrinhHD: { enabled: true, value: '' },
      cboHinhThuc: { enabled: true, value: '', list: [] },
      dpNgayThucHien: { enabled: true, value: '' },
      cboDichVu: { enabled: true, value: '', list: [] },
      cboLoaiHinh: { enabled: true, value: '', list: [] },
      txtNoiDung: { enabled: true, value: '' },
      txtMaKH: { enabled: true, value: '' },
      txtTenKH: { enabled: true, value: '' },
      txtMaSoThue: { enabled: true, value: '' },
      txtSoGiayTo: { enabled: true, value: '' },
      txtDiaChi: { enabled: true, value: '' },
      txtNguoiTiepCan: { enabled: true, value: '' },
      txtChucVu: { enabled: true, value: '' },
      txtSoDienThoai: { enabled: true, value: '' },
      txtEmail: { enabled: true, value: '' },
      cboKetQua: { enabled: true, value: '', list: [] },
      txtThongTinKhac: { enabled: true, value: '' },
      txtDTDKTang: { enabled: true, value: '' },
      txtDTDKGiam: { enabled: true, value: '' },
      txtTTDoiThu: { enabled: true, value: '' },
      txtDeXuat: { enabled: true, value: '' },
      gridVPCSKH: {
        header: [
          { fieldName: 'a', headerText: 'Mã vật phẩm', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'b', headerText: 'Tên vật phẩm', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'thao_tac', headerText: 'Thao tác', allowFiltering: false, allowSorting: false, template: this.gridVPCSKH_template_ThaoTac(this) }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridKeHoach: {
        header: [
          { fieldName: 'Serial', headerText: 'Mã kế hoạch', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SerialThayThe', headerText: 'Mã nhiệm vụ', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SoLo', headerText: 'Loại nhiệm vụ', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SoPhieuDHSXKD', headerText: 'Ngày giao', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'TrangThai', headerText: 'Mã khách hàng', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'TrangThaiChiTiet', headerText: 'Tên khách hàng', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NgayNghiemThu', headerText: 'Ngày tiếp cận', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NgayGiao', headerText: 'Dịch vụ', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'trangthai_onebss', headerText: 'Loại hình', textAlign: 'Right', allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      }
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
        position: { X: 'center', Y: 'center' }
      }
    },
    ejsDialogBeforeClose() {
      this.ejsDialog.name = null
      this.ejsDialog.config.width = '0'
      this.ejsDialog.config.height = '0'
      this.ejsDialog.config.position = { X: 'center', Y: 'center' }
    },
    gridVPCSKH_template_ThaoTac(parent) {
      return function () {
        return {
          template: {
            template: `<div style="display: flex; justify-content: center"><button>1</button><button>2</button></div>`,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    async gridVPCSKH_selectedItemsChanged(e) {
      console.log('gridVPCSKH_selectedItemsChanged')
      console.log(e)
      this.gridVPCSKH.selected = e
    },
    async gridVPCSKH_rowSelected(e) {
      this.gridVPCSKH.value = null
      if(e.data) {
        this.gridVPCSKH.value = e.data
      }
    },
    async gridKeHoach_selectedItemsChanged(e) {
      console.log('gridKeHoach_selectedItemsChanged')
      console.log(e)
      this.gridKeHoach.selected = e
    },
    async gridKeHoach_rowSelected(e) {
      this.gridKeHoach.value = null
      if(e.data) {
        this.gridKeHoach.value = e.data
      }
    },
    async txtSoLo_KeyUpEnter() {
      if (!this.txtSoLo.value.length) {
        this.$toast.warning('Số lô không được trống!')
        return
      }
      try {
        this.loading(true)
        let kq = await api.tracuubaohanhthongtinlo(this.axios, { vphanvung_id: this.$root.token.getPhanVungID(), vlo_bh: this.txtSoLo.value })
        if (kq.data.data && kq.data.data.length) {
          this.gridKeHoach.list = kq.data.data.map((e) => {
            return {
              ...e,
              NgayGiao: e.NgayGiao ? moment(e.NgayGiao).format('DD/MM/YYYY hh:mm:ss') : null,
              NgayNghiemThu: e.NgayNghiemThu ? moment(e.NgayNghiemThu).format('DD/MM/YYYY hh:mm:ss') : null
            }
          })
        } else {
          this.gridKeHoach.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async tsbtnLayDS_Clicked() {
      // this.ejsDialog.name = 'frmPhieuBaoHanhPSIS'
      // this.ejsDialog.header = 'Tra cứu'
      // this.$refs.ejsDialog.show()
    },
    async tsbtnGhiLai_Clicked() {
      // if (!this.gridKeHoach.list.length) {
      //   this.$toast.warning('Chưa có dữ liệu!')
      //   return
      // }
      // try {
      //   this.loading(true)
      //   let ds = this.gridKeHoach.list
      //   for (let i = 0; i < ds.length; i++) {
      //     ds[i] = this.ConvertKeysToUpperCase(ds[i])
      //   }
      //   let vds = JSON.stringify(ds)
      //   let kq = await api.fn_tudonggiao_phieubh_psis(this.axios, {
      //     vphanvung_id: this.$root.token.getPhanVungID(),
      //     vds: vds,
      //     vnhanvien_id: this.$root.token.getNhanVienID(),
      //     vmay_cn: await this.$root.token.getMachine(),
      //     vnguoi_cn: this.$root.token.getUserName(),
      //     vip_cn: await this.$root.token.getIP()
      //   })
      //   if (kq.data.data == 1) {
      //     this.$toast.success(`${kq.data.message}`)
      //   } else {
      //     this.$toast.error(`${kq.data.message}`)
      //   }
      // } catch (e) {
      //   this.$toast.error(`${e}`)
      //   return
      // } finally {
      //   this.loading(false)
      // }
    },
    async tsbtnHoanThanh_Clicked() {
      // this.exportExcelByCols(this.gridKeHoach.selected, this.gridKeHoach.header)
    },
    async tsbtnTaoBaoGia_Clicked() {
      // try {
      //   this.loading(true)
      //   if (!this.gridKeHoach.list.length) return
      //   let solo = this.gridKeHoach.list[0]
      //   let kq = await api.nghiemthulobaohanh(this.axios, { key: `NGHIEMTHU|${solo}`, MaDonVi: `${this.$root.token.getMaTinh()}` })
      //   if (kq.data.data && kq.data.data.length) {
      //     this.gridKeHoach.list = kq.data.data
      //   } else {
      //     this.gridKeHoach.list = []
      //   }
      // } catch (e) {
      //   this.$toast.error(`${e}`)
      //   return
      // } finally {
      //   this.loading(false)
      // }
    },
    async tsbtnTaoKhaoSat_Clicked() {},
    async tsbtnTaoDonHang_Clicked() {},
    async tsbtnTaoHopDong_Clicked() {},
    ConvertKeysToUpperCase(obj) {
      console.log(obj)
      var output = {}
      for (let i in obj) {
        if (Object.prototype.toString.apply(obj[i]) === '[object Object]') {
          output[i.toUpperCase()] = ConvertKeysToUpperCase(obj[i])
        } else if (Object.prototype.toString.apply(obj[i]) === '[object Array]') {
          output[i.toUpperCase()] = []
          output[i.toUpperCase()].push(ConvertKeysToUpperCase(obj[i][0]))
        } else {
          output[i.toUpperCase()] = obj[i]
        }
      }
      console.log(output)
      return output
    },
    exportExcelByCols(inputArray, arrayFields) {
      let exportData = []
      exportData = inputArray.map((e) => {
        let exportEntity = {}
        arrayFields.map((x) => {
          exportEntity[x.fieldName] = e[x.fieldName]
        })
        return exportEntity
      })
      if (exportData.length > 0) {
        let worksheet = XLSX.utils.json_to_sheet(exportData)
        let workbook = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(workbook, worksheet, 'Dữ liệu')
        XLSX.writeFile(workbook, 'export.xlsx')
      } else {
        this.$toast.warning('Không có dữ liệu để xuất file!')
      }
    }
  },
  async mounted() {
    this.gridVPCSKH.list = [
      { a: '1', b: '2' },
      { a: '1', b: '2' },
      { a: '1', b: '2' }
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
