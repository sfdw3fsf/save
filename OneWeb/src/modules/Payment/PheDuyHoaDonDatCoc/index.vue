<template>
  <div id="tra-cuu-theo-so-lo-phieu-psis">
    <breadcrumb :header="header" />
    <div>
      <div class="list-actions-top">
        <ul class="list">
          <li v-if="tsbtnLayTT.visible" :class="{ disabledInput: !tsbtnLayTT.enabled }" @click="tsbtnLayTT_Clicked">
            <a> <span class="icon one-search"></span>{{ tsbtnLayTT.text }}</a>
          </li>
          <li v-if="tsbtnGhiLai.visible" :class="{ disabledInput: !tsbtnGhiLai.enabled }" @click="tsbtnGhiLai_Clicked">
            <a> <span class="icon one-folder-add"></span>{{ tsbtnGhiLai.text }}</a>
          </li>
          <li v-if="tsbtnLayLaiOTP.visible" :class="{ disabledInput: !tsbtnLayLaiOTP.enabled }" @click="tsbtnLayLaiOTP_Clicked">
            <a> <span class="icon one-folder-add"></span>{{ tsbtnLayLaiOTP.text }}</a>
          </li>
        </ul>
      </div>
      <div class="popup-body page-content">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="box-form" style="min-height: 200px">
              <div class="legend-title">Thông tin tra cứu</div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w120">KQ duyệt</div>
                    <div class="value">
                      <SelectExt ref="cboKQDuyet1" :disabled="!cboKQDuyet1.enabled" v-model="cboKQDuyet1.value" :dataSource="cboKQDuyet1.list" dataTextField="text" dataValueField="id" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">Từ ngày</div>
                    <div class="value p-0 m-0">
                      <vue-date v-model="dpTuNgay.value" format="DD/MM/YYYY" type="datetime" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">Đến ngày</div>
                    <div class="value p-0 m-0">
                      <vue-date v-model="dpDenNgay.value" format="DD/MM/YYYY" type="datetime" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="box-form" style="min-height: 200px">
              <div class="legend-title">Cập nhật kết quả phê duyệt</div>
              <div class="row">
                <div class="col-sm-6 col-12" v-if="false">
                  <div class="info-row">
                    <div class="key w120">Mã OTP</div>
                    <div class="value">
                      <input :disabled="!txtMaOTP.enabled" type="text" class="form-control" v-model="txtMaOTP.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w120">KQ duyệt</div>
                    <div class="value">
                      <SelectExt ref="cboKQDuyet2" :disabled="!cboKQDuyet2.enabled" v-model="cboKQDuyet2.value" :dataSource="cboKQDuyet2.list" dataTextField="text" dataValueField="id" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w120">Ghi chú</div>
                    <div class="value">
                      <textarea :disabled="!txtGhiChu.enabled" ref="txtGhiChu" cols="30" rows="3" class="form-control" v-model="txtGhiChu.value" placeholder="Nhập thông tin ghi chú"></textarea>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div v-if="cboKQDuyet1.list.length" class="box-form">
          <!-- <div class="legend-title">Danh sách phiếu {{ cboKQDuyet1.list.find((e) => e.id == cboKQDuyet1.value).text.toLowerCase() }}</div> -->
          <div class="legend-title">
            <div class="pull-left">Danh sách phiếu {{ cboKQDuyet1.value != -1 ? cboKQDuyet1.list.find((e) => e.id == cboKQDuyet1.value).text.toLowerCase() : '' }}</div>
            <div class="pull-right">
              <a href="#" class="link" @click.prevent="onGridPhieuExportClicked"> <span class="icon one-excel"></span> Xuất File </a>
            </div>
            <div class="clearfix"></div>
          </div>
          <div class="row">
            <DataGrid
              ref="gridPhieu"
              panelDataHeight="300px"
              v-bind:columns="gridPhieu.header"
              v-bind:dataSource="gridPhieu.list"
              :enabledSelectFirstRow="false"
              :showFilter="true"
              :enablePagingServer="false"
              :showColumnCheckbox="cboKQDuyet1.value == 2"
              :allowPaging="true"

              :selectionSettings="gridPhieuSelectionSettings" 
              @rowSelected="gridPhieu_rowSelected"
              @selectedItemsChanged="gridPhieu_selectedItemsChanged"
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
      <div v-if="ejsDialog.name" :key="uuidv4()" style="overflow-y: scroll">
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
        title: '',
        list: [
          { name: '', link: { name: 'Ui.cards' } },
          { name: '', link: { name: 'Ui.cards' } }
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
      tsbtnLayTT: { enabled: true, visible: true, text: 'Lấy TT' },
      tsbtnGhiLai: { enabled: true, visible: true, text: 'Ghi lại' },
      tsbtnLayLaiOTP: { enabled: true, visible: false, text: 'Lấy lại OTP' },
      cboKQDuyet1: {
        enabled: true,
        value: -1,
        list: []
      },
      dpTuNgay: { enabled: true, value: '' },
      dpDenNgay: { enabled: true, value: '' },
      txtMaOTP: { enabled: true, value: '' },
      cboKQDuyet2: {
        enabled: true,
        value: -1,
        list: []
      },
      txtGhiChu: { enabled: true, value: '' },
      gridPhieu: {
        header: [
          { fieldName: 'ma_tb', headerText: 'Mã TB', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'ten_tb', headerText: 'Tên TB', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'diachi_tb', headerText: 'Địa chỉ TB', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'ma_gd', headerText: 'Mã GD', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'ma_hd', headerText: 'Mã HĐ/PL', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'ten_kh', headerText: 'Tên KH', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'so_dt', headerText: 'SĐT KH', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'tien_dc', headerText: 'Tiền ĐC', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'phi_ttn', headerText: 'Phí TTN', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          // { fieldName: 'tongtien', headerText: 'Tổng tiền trả', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_yc', headerText: 'Ngày YC', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_hen', headerText: 'Ngày hẹn TT', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'ht_tra', headerText: 'Hình thức trả', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'kenhthu', headerText: 'Kênh thu', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          // { fieldName: 'nhanvien_id', headerText: 'Trạng thái HĐ', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: '', headerText: 'Kết quả duyệt', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: '', headerText: 'Chi tiết', textAlign: 'Left', allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      }
    }
  },
  computed: {
    dpTuNgayValue() {
      return moment(this.dpTuNgay.value, 'DD/MM/YYYY').format('YYYYMMDD')
    },
    dpDenNgayValue() {
      return moment(this.dpDenNgay.value, 'DD/MM/YYYY').format('YYYYMMDD')
    },
    gridPhieuSelectionSettings() {
     return {checkboxOnly: false, type: this.cboKQDuyet1.value == 2 ?'Multiple': 'Single'}
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
    async gridPhieu_selectedItemsChanged(e) {
      this.gridPhieu.selected = e
    },
    async gridPhieu_rowSelected(e) {
      if (e.data) {
        this.gridPhieu.value = e.data
        // this.cboKQDuyet2.value = e.data.
      } else {
        this.gridPhieu.value = null
      }
    },
    async tsbtnLayTT_Clicked() {
      // if (!this.txtMaNV.value.length) {
      //   this.$toast.warning('Vui lòng nhập mã NV/Email!')
      //   return
      // }
      // await this.lay_ds_phieu_hen_thanhtoan_v2()
      await this.lay_thuebao_co_hen_phieutt()
    },
    async tsbtnGhiLai_Clicked() {
      // if (!this.txtThongTinDangKy.value.length) {
      //   this.$toast.warning('Vui lòng nhập mã NV!')
      //   return
      // }
      // try {
      //   this.loading(true)
      // let kq = await api.capnhat_kq_duyet_hen_tt(this.axios, {
      //   vphieutt_id: this.gridPhieu.value.phieutt_id,
      //   vkq_duyet: this.cboKQDuyet2.value,
      //   vnguoiduyet_id: this.$root.token.getNhanVienID(),
      //   vghichu: this.txtGhiChu.value
      // })
      // if (kq.data.error_code == 'BSS-00000000') {
      //   this.$toast.success(`Thành công`)
      // } else {
      //   this.$toast.error(`${kq.data.message}`)
      //   return
      // }
      // } catch (e) {
      // } finally {
      //   this.loading(false)
      // }
      await this.capnhat_kq_duyet_hen_theo_thuebao()
    },
    async lay_ds_phieu_hen_thanhtoan_v2() {
      try {
        this.loading(true)
        let kq = await api.lay_ds_phieu_hen_thanhtoan_v2(this.axios, { vkq_duyet: this.cboKQDuyet1.value, vtu_thang: this.dpTuNgayValue, vden_thang: this.dpDenNgayValue })
        if (kq.data.data && kq.data.data.length) {
          this.gridPhieu.list = kq.data.data
        } else {
          this.gridPhieu.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async lay_thuebao_co_hen_phieutt() {
      try {
        this.loading(true)
        let kq = await api.lay_thuebao_co_hen_phieutt(this.axios, { vkq_duyet: this.cboKQDuyet1.value, vtu_thang: this.dpTuNgayValue, vden_thang: this.dpDenNgayValue })
        if (kq.data.data && kq.data.data.length) {
          this.gridPhieu.list = kq.data.data
        } else {
          this.gridPhieu.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async capnhat_kq_duyet_hen_theo_thuebao() {
      let vdshdtb_id = this.gridPhieu.selected.map((e) => {
        return {
          hdtb_id: e.hdtb_id
        }
      })
      let kq = await api.capnhat_kq_duyet_hen_theo_thuebao(this.axios, { vdshdtb_id: JSON.stringify(vdshdtb_id), vkq_duyet: this.cboKQDuyet2.value, vghichu: this.txtGhiChu.value })

      if (kq.data.error_code == 'BSS-00000000') {
        this.$toast.success(`Thành công`)
      } else {
        this.$toast.error(`${kq.data.message}`)
        return
      }
    },
    onGridPhieuExportClicked() {
      this.exportExcelByCols(this.gridPhieu.list, this.gridPhieu.header)
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
    this.cboKQDuyet1.list = [
      { id: -1, text: 'Tất cả' },
      { id: 2, text: 'Chưa duyệt' },
      { id: 1, text: 'Đồng ý' },
      { id: 0, text: 'Không đồng ý' }
    ]
    this.cboKQDuyet2.list = [
      // { id: -1, text: 'Tất cả' },
      // { id: 2, text: 'Chưa duyệt' },
      { id: 1, text: 'Đồng ý' },
      { id: 0, text: 'Không đồng ý' }
    ]

    this.dpTuNgay.value = moment(new Date().setDate(new Date().getDate() - 7)).format('DD/MM/YYYY')
    this.dpDenNgay.value = moment().format('DD/MM/YYYY')
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
