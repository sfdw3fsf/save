<template>
  <div id="nhapxuatvt_v2">
    <!-- <breadcrumb /> -->
    <div id="nhapxuatvt_v2_content">
      <div class="list-actions-top">
        <ul class="list">
          <li v-if="tsbtnLayTT.visible" :class="{ disabledInput: !tsbtnLayTT.enabled }" @click="tsbtnLayTT_Clicked">
            <a> <span class="icon one-search"></span>{{ tsbtnLayTT.text }}</a>
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
                <div class="key w90">Số phiếu</div>
                <div class="value">
                  <input :disabled="!txtSoPhieu.enabled" type="text" class="form-control" v-model="txtSoPhieu.value" @keyup.enter="tsbtnLayTT_Clicked" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            Danh sách
          </div>
          <div class="row">
            <DataGrid
              ref="gridPhieu"
              panelDataHeight="285px"
              v-bind:columns="gridPhieu.header"
              v-bind:dataSource="gridPhieu.list"
              :enabledSelectFirstRow="false"
              :showFilter="true"
              :enablePagingServer="false"
              :showColumnCheckbox="false"
              :selectionSettings="{ enableToggle: false, checkboxOnly: false, persistSelection: true, checkboxMode: 'ResetOnRowClick' }"
              :allowPaging="true"
              :allowExcelExport="true"
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
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import moment from 'moment'
import api from './api'
export default {
  components: {
    breadcrumb
  },
  data() {
    return {
      tsbtnLayTT: { enabled: true, visible: true, text: 'Lấy TT' },
      txtSoPhieu: { enabled: true, value: '', list: [] },
      gridPhieu: {
        header: [
          { fieldName: 'Serial', headerText: 'Serial', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SerialThayThe', headerText: 'Serial thay thế', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SoLo', headerText: 'Số Lô', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SoPhieuDHSXKD', headerText: 'Số phiếu DHSXKD', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'TrangThai', headerText: 'Trạng thái', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'TrangThaiChiTiet', headerText: 'Trạng thái chi tiết', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NgayNghiemThu', headerText: 'Ngày nghiệm thu', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NgayTiepNhan', headerText: 'Ngày tiếp nhận', textAlign: 'Right', allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: [],
        value: null
      }
    }
  },
  methods: {
    async tsbtnLayTT_Clicked() {
      if(!this.txtSoPhieu.value.length) {
        this.$toast.warning('Hãy nhập số phiếu!')
        return
      }
      try {
        this.loading(true)
        let kq = await api.baohanhtracuuthongtinphieu(this.axios, { key: this.txtSoPhieu.value })
        if (kq.data.data && kq.data.data.length) {
          this.gridPhieu.list = kq.data.data.map((e) => {
            return {
              ...e,
              NgayNghiemThu: e.NgayNghiemThu ? moment(e.NgayNghiemThu).format('DD/MM/YYYY hh:mm:ss') : null,
              NgayTiepNhan: e.NgayTiepNhan ? moment(e.NgayTiepNhan).format('DD/MM/YYYY hh:mm:ss') : null
            }
          })
        } else {
          this.gridPhieu.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
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
