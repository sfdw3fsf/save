<template>
  <div id="nhapxuatvt_v2">
    <!-- <breadcrumb /> -->
    <div id="nhapxuatvt_v2_content">
      <div class="list-actions-top">
        <ul class="list">
          <li v-if="tsbtnTuDongGiao.visible" :class="{ disabledInput: !tsbtnTuDongGiao.enabled }" @click="tsbtnTuDongGiao_Clicked">
            <a> <span class="icon one-reload-next"></span>{{ tsbtnTuDongGiao.text }}</a>
          </li>
          <li v-if="tsbtnXuatExcel.visible" :class="{ disabledInput: !tsbtnXuatExcel.enabled }" @click="tsbtnXuatExcel_Clicked">
            <a> <span class="icon one-excel"></span>{{ tsbtnXuatExcel.text }}</a>
          </li>
          <li v-if="tsbtnNghiemThuLoBH.visible" :class="{ disabledInput: !tsbtnNghiemThuLoBH.enabled }" @click="tsbtnNghiemThuLoBH_Clicked">
            <a> <span class="icon one-save1"></span>{{ tsbtnNghiemThuLoBH.text }}</a>
          </li>
        </ul>
      </div>
      <div id="nhapxuatvt_v2_main" class="page-content py-1">
        <div class="box-form">
          <div class="legend-title">
            Thông tin tra cứu
          </div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w90">Số lô</div>
                <div class="value">
                  <input :disabled="!txtSoLo.enabled" type="text" class="form-control" v-model="txtSoLo.value" @keyup.enter="txtSoLo_KeyUpEnter" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            Danh sách Serial
          </div>
          <div class="row">
            <!-- :selectionSettings="{ enableToggle: false, checkboxOnly: false, persistSelection: true, checkboxMode: 'ResetOnRowClick' }" -->
            <DataGrid
              ref="gridSerial"
              panelDataHeight="285px"
              v-bind:columns="gridSerial.header"
              v-bind:dataSource="gridSerial.list"
              :enabledSelectFirstRow="false"
              :showFilter="true"
              :enablePagingServer="false"
              :showColumnCheckbox="true"
              :allowPaging="true"
              @selectedItemsChanged="gridSerial_selectedItemsChanged"
            />
          </div>
        </div>
      </div>
    </div>
    <!-- <ejs-dialog
      ref="ejsDialog"
      :visible="false"
      :isModal="true"
      target="#nhapxuatvt_v2"
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
        <frmChungTu_TBao v-if="ejsDialog.name == 'frmChungTu_TBao'" ref="frmChungTu_TBao" v-bind="frmChungTu_TBao.input" />
      </div>
    </ejs-dialog> -->
  </div>
</template>
<script>
import XLSX from 'xlsx'
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import moment from 'moment'
import api from './api'
export default {
  components: {
    breadcrumb
  },
  data() {
    return {
      tsbtnTuDongGiao: { enabled: true, visible: true, text: 'Tự động giao' },
      tsbtnXuatExcel: { enabled: true, visible: true, text: 'Xuất Excel' },
      tsbtnNghiemThuLoBH: { enabled: true, visible: true, text: 'Nghiệm thu lô BH' },
      txtSoLo: { enabled: true, value: '', list: [] },
      gridSerial: {
        header: [
          { fieldName: 'Serial', headerText: 'Serial', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SerialThayThe', headerText: 'Serial thay thế', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SoLo', headerText: 'Số Lô', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SoPhieuDHSXKD', headerText: 'Số phiếu DHSXKD', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'TrangThai', headerText: 'Trạng thái', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'TrangThaiChiTiet', headerText: 'Trạng thái chi tiết', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NgayNghiemThu', headerText: 'Ngày nghiệm thu', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NgayGiao', headerText: 'Ngày tiếp nhận', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'trangthai_onebss', headerText: 'Trạng thái Onebss', textAlign: 'Right', allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      }
    }
  },
  methods: {
    async gridSerial_selectedItemsChanged(e) {
      console.log('gridSerial_selectedItemsChanged')
      console.log(e)
      this.gridSerial.selected = e
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
          this.gridSerial.list = kq.data.data.map((e) => {
            return {
              ...e,
              NgayGiao: e.NgayGiao ? moment(e.NgayGiao).format('DD/MM/YYYY hh:mm:ss') : null,
              NgayNghiemThu: e.NgayNghiemThu ? moment(e.NgayNghiemThu).format('DD/MM/YYYY hh:mm:ss') : null
            }
          })
        } else {
          this.gridSerial.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async tsbtnTuDongGiao_Clicked() {
      console.log('tsbtnLayTT_Clicked')
      // try {
      //   this.loading(true)
      let ds = this.gridSerial.list
      for (let i = 0; i < ds.length; i++) {
        ds[i] = this.ConvertKeysToUpperCase(ds[i])
      }
      let vds = JSON.stringify(ds)
      let kq = await api.fn_tudonggiao_phieubh_psis(this.axios, {
        vphanvung_id: this.$root.token.getPhanVungID(),
        vds: vds,
        vnhanvien_id: this.$root.token.getNhanVienID(),
        vmay_cn: await this.$root.token.getMachine(),
        vnguoi_cn: this.$root.token.getUserName(),
        vip_cn: await this.$root.token.getIP()
      })
      if (kq.data.data == 1) {
        this.$toast.success(`${kq.data.message}`)
      } else {
        this.$toast.error(`${kq.data.message}`)
      }
      // } catch (e) {
      // } finally {
      //   this.loading(false)
      // }
    },
    async tsbtnXuatExcel_Clicked() {
      this.exportExcelByCols(this.gridSerial.selected, this.gridSerial.header)
    },
    async tsbtnNghiemThuLoBH_Clicked() {
      // try {
      //   this.loading(true)
      if(!this.gridSerial.list.length) return
      let solo = this.gridSerial.list[0]
      let kq = await api.nghiemthulobaohanh(this.axios, { key: `NGHIEMTHU|${solo}`, MaDonVi: `${this.$root.token.getMaTinh()}` })
      if (kq.data.data && kq.data.data.length) {
        this.gridSerial.list = kq.data.data
      } else {
        this.gridSerial.list = []
      }
      // } catch (e) {
      // } finally {
      //   this.loading(false)
      // }

    },
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
  }
}
</script>

<style >
.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: #9999 !important;
}
.disabledInput > a {
  color: #666 !important;
}
</style>
