<template>
  <ejs-dialog :enableResize='true' :allowDragging='true' :visible='false' :isModal="true"
    :animationSettings='animationSettings' ref="dlgTimNhanVienThuCuoc" :header='"Tìm kiếm nhân viên thu cước"'
    showCloseIcon=true width='90%' :target='target' :close="onDialogClose" :open="onDialogOpen"
    :beforeClose="onBeforeClose" :beforeOpen="onBeforeOpen">
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" v-on:click.prevent="doAccept()"><span class="icon -ap icon-ion-ios-checkmark"></span> Chấp
              nhận</a>
          </li>
          <li>
            <a href="#" v-on:click.prevent="doSearch()"><span class="icon one-search"></span> Tìm kiếm</a>
          </li>
          <li>
            <a href="#" v-on:click.prevent="closeDialog()">
              <span class="icon one-export"></span> Thoát
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="row">
          <div class="col-md-6 col-12">
            <div class="box-form">
              <div class="legend-title mart0">Thông tin địa danh</div>
              <div class="row">
                <div class="col-md-6 col-12">
                  <div class="info-row">
                    <div class="key w80 nowrap">
                      <div class="check-right block">
                        <b-form-checkbox v-model="params.p_chk_quan" class="check" value='1' unchecked-value='0'>
                          <span class="name">Quận</span>
                        </b-form-checkbox>
                      </div>
                    </div>
                    <div class="value">
                      <ejs-dropdownlist :change="onQuanChange" :enabled="chk_quan" :filtering='onFilteringQuan'
                        :allowFiltering="true" :dataSource='params.p_quan_options' :fields="configs.fieldsQuan"
                        v-model="params.p_quan" :placeholder="'Chọn quận'"></ejs-dropdownlist>
                    </div>
                  </div>
                </div>
                <div class="col-md-6 col-12">
                  <div class="info-row">
                    <div class="key w80 nowrap">
                      <div class="check-right block">
                        <b-form-checkbox v-model="params.p_chk_phuong" class="check" value='1' unchecked-value='0'>
                          <span class="name">Phường</span>
                        </b-form-checkbox>
                      </div>
                    </div>
                    <div class="value">
                      <div class="select-custom">
                        <ejs-dropdownlist :change="onPhuongChange" :enabled="chk_phuong" :filtering='onFilteringPhuong'
                          :allowFiltering="true" :dataSource='params.p_phuong_options' :fields="configs.fieldsPhuong"
                          v-model="params.p_phuong" :placeholder="'Chọn phường'"></ejs-dropdownlist>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-md-6 col-12">
                  <div class="info-row">
                    <div class="key w80 nowrap">
                      <div class="check-right block">
                        <b-form-checkbox v-model="params.p_chk_pho" class="check" value='1' unchecked-value='0'>
                          <span class="name">Phố</span>
                        </b-form-checkbox>
                      </div>
                    </div>
                    <div class="value">
                      <div class="select-custom">
                        <ejs-dropdownlist :change="onPhoChange" :enabled="chk_pho" :filtering='onFilteringPho' :allowFiltering="true"
                          :dataSource='params.p_pho_options' :fields="configs.fieldsPho" v-model="params.p_pho"
                          :placeholder="'Chọn phố'"></ejs-dropdownlist>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-md-6 col-12">
                  <div class="info-row">
                    <div class="key w80 nowrap">
                      <div class="check-right block">
                        <b-form-checkbox v-model="params.p_chk_ap" class="check" value='1' unchecked-value='0'>
                          <span class="name">Ấp</span>
                        </b-form-checkbox>
                      </div>
                    </div>
                    <div class="value">
                      <div class="select-custom">
                        <ejs-dropdownlist :change='onApChange' :enabled="chk_ap" :filtering='onFilteringAp' :allowFiltering="true"
                          :dataSource='params.p_ap_options' :fields="configs.fieldsAp" v-model="params.p_ap"
                          :placeholder="'Chọn ấp'"></ejs-dropdownlist>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-md-6 col-12">
                  <div class="info-row">
                    <div class="key w80 nowrap">
                      <div class="check-right block">
                        <b-form-checkbox v-model="params.p_chk_khu" class="check" value='1' unchecked-value='0'>
                          <span class="name">Khu</span>
                        </b-form-checkbox>
                      </div>
                    </div>
                    <div class="value">
                      <div class="select-custom">
                        <ejs-dropdownlist :change='onKhuChange' :enabled="chk_khu" :filtering='onFilteringKhu' :allowFiltering="true"
                          :dataSource='params.p_khu_options' :fields="configs.fieldsKhu" v-model="params.p_khu"
                          :placeholder="'Chọn khu'"></ejs-dropdownlist>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-md-6 col-12">
                  <div class="info-row">
                    <div class="key w80 nowrap">
                      <div class="check-right block">
                        <b-form-checkbox v-model="params.p_chk_dacdiem" class="check" value='1' unchecked-value='0'>
                          <span class="name">Đặc điểm</span>
                        </b-form-checkbox>
                      </div>
                    </div>
                    <div class="value">
                      <div class="select-custom">
                        <ejs-dropdownlist :enabled="chk_dacdiem" :filtering='onFilteringDacDiem' :allowFiltering="true"
                          :dataSource='params.p_dacdiem_options' :fields="configs.fieldsDacDiem"
                          v-model="params.p_dacdiem" :placeholder="'Chọn đặc điểm'"></ejs-dropdownlist>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-12">
            <div class="box-form">
              <div class="legend-title mart0">
                <div class="pull-left">
                  Thông tin nhân viên thu cước
                </div>

                <div class="clearfix"></div>
              </div>
              <div class="info-row">
                <div class="key w80 nowrap">
                  Đơn vị QL
                </div>
                <div class="value">
                  <div class="select-custom">
                    <ejs-dropdownlist :showClearButton="true" :filtering='onFilteringDonViQL' :allowFiltering="true"
                      :dataSource='params.p_donviql_options' :fields="configs.fieldsDVQL" v-model="params.p_donviql"
                      :placeholder="'Chọn đơn vị quản lý'"></ejs-dropdownlist>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-md-6 col-12">
                  <div class="info-row">
                    <div class="key w80 nowrap">
                      Mã NVTC
                    </div>
                    <div class="value">
                      <input v-model="params.p_ma_nvtc" type="text" class="form-control"
                        @keyup.enter="doSearchByMaNVTC">
                    </div>
                  </div>
                </div>
                <div class="col-md-6 col-12">
                  <div class="info-row">
                    <div class="key w80 nowrap">
                      Điện thoại
                    </div>
                    <div class="value">
                      <input v-model="params.p_dienthoai_nvtc" type="text" class="form-control">
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-md-6 col-12">
                  <div class="info-row">
                    <div class="key w80 nowrap">
                      Tên NVTC
                    </div>
                    <div class="value">
                      <input v-model="params.p_ten_nvtc" type="text" class="form-control">
                    </div>
                  </div>
                </div>
                <div class="col-md-6 col-12">
                  <div class="info-row">
                    <div class="key w80 nowrap">
                      <div class="check-right">
                        <b-form-checkbox v-model="params.p_chk_httt" class="check" value='1' unchecked-value='0'>
                          <span class="name">HTTT</span>
                        </b-form-checkbox>
                      </div>
                    </div>
                    <div class="value">
                      <ejs-dropdownlist :enabled="chk_httt" :dataSource='params.p_httt_options'
                        :fields="configs.fieldsHTTT" v-model="params.p_httt"
                        :placeholder="'Chọn HTTT'"></ejs-dropdownlist>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title mart0">
            <div class="pull-left title">
              Danh sách nhân viên thu cước
            </div>
            <div class="pull-right">
              <div class="normal">
                <b-form-checkbox v-model="params.p_chk_locno" class="check" value='1' unchecked-value='0'
                  :disabled="!configs.f_enable_chk_locno">
                  <span class="name">Lọc theo nhân viên thu nợ</span>
                </b-form-checkbox>
              </div>
            </div>
            <div class="clearfix"></div>
          </div>
          <div class="table-content" style="height: 300px">
            <DataGrid v-bind:columns="[
  { fieldName: 'MA_TUYENTHU', headerText: 'Mã tuyến', allowFiltering: true },
  { fieldName: 'TENTUYEN', headerText: 'Tên tuyến', allowFiltering: true },
  { fieldName: 'MATHE', headerText: 'Mã thẻ', allowFiltering: true },
  { fieldName: 'MANV_TC', headerText: 'Mã nhân viên', allowFiltering: true },
  { fieldName: 'TENNV_TC', headerText: 'Tên nhân viên', allowFiltering: true },
  { fieldName: 'SO_DT', headerText: 'Điện thoại', allowFiltering: true },
  { fieldName: 'DONVI_ID', headerText: 'Mã đơn vị', allowFiltering: true },
  { fieldName: 'TEN_DV', headerText: 'Tên đơn vị', allowFiltering: true },
  { fieldName: 'TUYENTHU_ID', headerText: 'ID tuyến', allowFiltering: true }
]" v-bind:dataSource="dsNVThuCuoc" :enablePagingServer="true" :showFilter="true" :allowPaging="true" :enabledSelectFirstRow="false"
              :totalRecords="pageinfo.totalElement" :pageSize="pageinfo.maxSize" :pageIndex="pageinfo.page"
              height="100%" ref=gridDSNVThuCuoc @selectedRowChanged="RowNhanVienChanged"
              @pageChanged="NVThuCuocPageChangeHandler">
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import Vue from 'vue'
import GachNoAPI from '../../api/GachNoAPI'
import CommonsAPI from '../../api/Commons'
import { Query } from '@syncfusion/ej2-data'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import moment from 'moment'
import {
  NHOM_HTTT
  // DichVuVienThong
} from '@/const/enums'
import { cloneArray } from '@/utils/util'

Vue.use(DialogPlugin)

export default Vue.extend({
  components: {
  },
  props: {
    vkieu: {
      type: Number,
      default: 1
    },
    vdonvi: {
      type: Number,
      default: -1
    },
    vnvtc: {
      type: String,
      default: null
    },
    vtinh_id: {
      type: Number,
      default: 0
    },
    vquan_id: {
      type: Number,
      default: 0
    },
    vphuong_id: {
      type: Number,
      default: 0
    },
    vpho_id: {
      type: Number,
      default: 0
    },
    vap_id: {
      type: Number,
      default: 0
    },
    vdacdiem_id: {
      type: Number,
      default: 0
    },
    vkhu_id: {
      type: Number,
      default: 0
    },
    vkt_kylai: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    currentRow () {
      return this.pageinfo.currentRow
    },
    chk_quan () {
      return this.params.p_chk_quan === '1'
    },
    chk_phuong () {
      return this.params.p_chk_phuong === '1'
    },
    chk_pho () {
      return this.params.p_chk_pho === '1'
    },
    chk_ap () {
      return this.params.p_chk_ap === '1'
    },
    chk_khu () {
      return this.params.p_chk_khu === '1'
    },
    chk_dacdiem () {
      return this.params.p_chk_dacdiem === '1'
    },
    chk_httt () {
      return this.params.p_chk_httt === '1'
    },
    chk_locno () {
      return this.params.p_chk_locno === '1'
    }
  },
  async created () {

  },
  destroyed () {
  },
  data () {
    return {
      selectionOptions: { persistSelection: true, type: 'Single' },
      configs: {
        fieldsQuan: { text: 'TEN_QUAN', value: 'QUAN_ID' },
        fieldsPhuong: { text: 'TEN_PHUONG', value: 'PHUONG_ID' },
        fieldsPho: { text: 'TEN', value: 'ID' },
        fieldsAp: { text: 'TEN', value: 'ID' },
        fieldsKhu: { text: 'TEN', value: 'ID' },
        fieldsDacDiem: { text: 'DACDIEM', value: 'DACDIEM_ID' },
        fieldsDVQL: { text: 'TEN_DV', value: 'DONVI_ID' },
        fieldsHTTT: { text: 'HTTT', value: 'HTTT_ID' },
        f_enable_chk_locno: false
      },
      params: {
        p_chk_quan: '0',
        p_tinh: null,
        p_quan_options: [],
        p_quan: null,
        p_chk_phuong: '0',
        p_phuong_options: [],
        p_phuong: null,
        p_chk_pho: '0',
        p_pho_options: [],
        p_pho: null,
        p_chk_ap: '0',
        p_ap_options: [],
        p_ap: null,
        p_chk_khu: '0',
        p_khu_options: [],
        p_khu: null,
        p_chk_dacdiem: '0',
        p_dacdiem_options: [],
        p_dacdiem: null,
        p_donviql_options: [],
        p_donviql: null,
        p_ten_nvtc: null,
        p_ma_nvtc: null,
        p_dienthoai_nvtc: null,
        p_chk_httt: '0',
        p_chk_locno: '0',

        p_httt_options: [],
        p_httt: null,
        p_vkieu: 1 // 1: Mặc định tìm kiếm theo đại lý, 2: cho phép tìm theo nhân viên thu nợ (NV Viễn thông).
      },
      searchForm: {
        pPhanVungId: null,
        pMaNVTC: null,
        pTenNVTC: null,
        pDonViId: null,
        pQuanId: null,
        pPhuongId: null,
        pPhoId: null,
        pApId: null,
        pKhuId: null,
        pDacDiemId: null,
        pHTTTId: null,
        pageNo: 0,
        pageSize: 10
      },
      dsNVThuCuoc: [],
      pageinfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      currentInfo: null,
      f_first: true
    }
  },
  methods: {
    validateData () {
      let errors = []

      if (this.params.p_donviql === null) errors.push('Đơn vị quản lý bắt buộc nhập')
      if (this.params.p_chk_quan === '1' && this.params.p_quan === null) errors.push('Quận huyện bắt buộc nhập')
      if (this.params.p_chk_phuong === '1' && this.params.p_phuong === null) errors.push('Phường xã bắt buộc nhập')
      if (this.params.p_chk_pho === '1' && this.params.p_pho === null) errors.push('Phố bắt buộc nhập')
      if (this.params.p_chk_ap === '1' && this.params.p_ap === null) errors.push('Ấp bắt buộc nhập')
      if (this.params.p_chk_khu === '1' && this.params.p_khu === null) errors.push('Khu bắt buộc nhập')
      if (this.params.p_chk_dacdiem === '1' && this.params.p_dacdiem === null) errors.push('Đặc điểm bắt buộc nhập')

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      return true
    },
    async doSearch () {
      if (!this.validateData()) return false

      this.searchForm = {
        // pPhanVungId: this.$auth.getPhanVungID(),
        pMaNVTC: '', // this.params.p_ma_nvtc !== null && this.params.p_ma_nvtc.trim() !== '' ? this.params.p_ma_nvtc.trim() : null,
        pTenNVTC: '', // this.params.p_ten_nvtc === null || this.params.p_ten_nvtc.trim() === '' ? null : this.params.p_ten_nvtc.trim(),
        pDonViId: this.params.p_donviql,
        pQuanId: this.params.p_chk_quan === '1' ? this.params.p_quan : null,
        pPhuongId: this.params.p_chk_phuong === '1' ? this.params.p_phuong : null,
        pPhoId: this.params.p_chk_pho === '1' ? this.params.p_pho : null,
        pApId: this.params.p_chk_ap === '1' ? this.params.p_ap : null,
        pKhuId: this.params.p_chk_khu === '1' ? this.params.p_khu : null,
        pDacDiemId: this.params.p_chk_dacdiem === '1' ? this.params.p_dacdiem : null,
        pHTTTId: this.params.p_chk_httt === '1' ? this.params.p_httt : null,
        pageNo: this.pageinfo.page,
        pageSize: this.pageinfo.pageSize
      }

      this.loading(true)
      this.clearResult()
      await this.loadNVTC()

      this.loading(false)
    },

    async loadNVTC () {
      let res = []

      if (this.params.p_chk_locno === '0') {
        if (this.params.p_chk_httt === '1' && this.params.httt_id !== null) {
          res = await this.HT_DanhSach_NV_ThuCuoc2(this.searchForm)
        } else {
          res = await this.HT_DanhSach_NV_ThuCuoc1(this.searchForm)
        }
      } else {
        if (this.params.p_chk_httt === '1' && this.params.httt_id !== null) {
          res = await this.HT_DanhSach_NV_ThuNo2(this.searchForm)
        } else {
          res = await this.HT_DanhSach_NV_ThuNo1(this.searchForm)
        }
      }
      // console.log(res)
      if (res !== null && res.data !== undefined && res.data.length > 0) {
        this.dsNVThuCuoc = cloneArray(res.data)
        this.pageinfo.page = res.page
        this.pageinfo.totalElement = res.totalElement
      }
    },
    async doSearchByMaNVTC () {
      if (this.params.p_ma_nvtc === null || this.params.p_ma_nvtc.trim() === '') {
        this.$toast.error('Chưa nhập Mã NVTC')
        return
      }

      this.searchForm = {
        pPhanVungId: this.$auth.getPhanVungID(),
        pMaNVTC: this.params.p_ma_nvtc.trim(),
        pTenNVTC: null,
        pDonViId: null,
        pQuanId: null,
        pPhuongId: null,
        pPhoId: null,
        pApId: null,
        pKhuId: null,
        pDacDiemId: null,
        pHTTTId: null,
        pageNo: this.pageinfo.page,
        pageSize: this.pageinfo.pageSize
      }

      this.loading(true)
      this.clearResult()
      await this.loadNVTC()
      this.loading(false)
    },
    HT_DanhSach_NVTC (postData) {
      this.loading(true)
      this.clearResult()

      GachNoAPI.getDSNVThuCuoc1(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.length > 0
            ) {
              this.dsNVThuCuoc = response.data.data
              this.pageinfo.page = 0
              this.pageinfo.totalElement = response.data.data.length
            } else {
              this.$toast.error('Không có dữ liệu, vui lòng thử lại.')
            }
          })
        .catch(error => {
          // console.log(error)
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    HT_DanhSach_NVTC2 (postData) {
      this.loading(true)
      this.clearResult()

      GachNoAPI.getDSNVThuCuoc2(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.length > 0
            ) {
              this.dsNVThuCuoc = response.data.data
              this.pageinfo.page = 0
              this.pageinfo.totalElement = response.data.data.length
            } else {
              this.$toast.error('Không có dữ liệu, vui lòng thử lại.')
            }
          })
        .catch(error => {
          // console.log(error)
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    async HT_DanhSach_NV_ThuCuoc1 (postData) {
      try {
        const res = await GachNoAPI.getDSNVThuCuoc1(this.axios, postData)
        const data = await res.data
        // console.log(data)
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.data !== undefined ? data.data : []
      } catch (error) {
        if (error.data !== undefined) this.$toast.error(error.data.message_detail)
        else this.$toast.error(error.data)
        return []
      }
    },
    async HT_DanhSach_NV_ThuCuoc2 (postData) {
      try {
        const res = await GachNoAPI.getDSNVThuCuoc2(this.axios, postData)
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : null
      } catch (error) {
        if (error.data !== undefined) this.$toast.error(error.data.message_detail)
        else this.$toast.error(error.data)
        return null
      }
    },
    async HT_DanhSach_NV_ThuNo1 (postData) {
      try {
        const res = await GachNoAPI.getDSNVThuNo1(this.axios, postData)
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : null
      } catch (error) {
        if (error.data !== undefined) this.$toast.error(error.data.message_detail)
        else this.$toast.error(error.data)
        return null
      }
    },
    async HT_DanhSach_NV_ThuNo2 (postData) {
      try {
        const res = await GachNoAPI.getDSNVThuNo2(this.axios, postData)
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : null
      } catch (error) {
        if (error.data !== undefined) this.$toast.error(error.data.message_detail)
        else this.$toast.error(error.data)
        return null
      }
    },
    doAccept () {
      if (this.currentInfo === null) {
        this.$toast.error('Vui lòng chọn nhân viên thu cước')
        return
      }
      // EventBus.$emit('chonNVTC', this.currentInfo)
      this.$emit('chonNVTC', this.currentInfo)
      this.$refs.dlgTimNhanVienThuCuoc.hide()
    },
    clearForm () {
      this.params.p_chk_quan = '0'
      this.params.p_quan_options = []
      this.params.p_quan = null
      this.params.p_chk_phuong = '0'
      // this.params.p_phuong_options= []
      // this.params.p_phuong= null
      this.params.p_chk_pho = '0'
      // this.params.p_pho_options= []
      // this.params.p_pho= null
      this.params.p_chk_ap = '0'
      // this.params.p_ap_options= []
      // this.params.p_ap= null
      this.params.p_chk_khu = '0'
      // this.params.p_khu_options= []
      // this.params.p_khu= null
      this.params.p_chk_dacdiem = '0'
      // this.params.p_dacdiem_options= []
      // this.params.p_dacdiem= null
      this.params.p_donviql_options = []
      this.params.p_donviql = null
      this.params.p_ten_nvtc = null
      this.params.p_ma_nvtc = null
      this.params.p_dienthoai_nvtc = null
      this.params.p_chk_httt = '0'
      this.params.p_httt_options = []
      this.params.p_httt = null
    },
    clearResult () {
      this.currentInfo = null
      this.dsNVThuCuoc = []
      this.pageinfo.page = 1
      this.pageinfo.totalElement = 0
      this.pageinfo.totalPages = 0
      this.pageinfo.currentRow = 0
      // this.params.p_ma_nvtc = null
      // this.params.p_ten_nvtc = null
      // this.params.p_dienthoai_nvtc = null
    },
    openDialog () {
      this.$refs.dlgTimNhanVienThuCuoc.show()
    },
    closeDialog () {
      this.$refs.dlgTimNhanVienThuCuoc.hide()
    },
    onDialogClose () {
      this.clearForm()
      this.clearResult()
    },
    async onDialogOpen () {
      this.loading(true)
      this.f_first = true

      if (this.vtinh_id !== 0) {
        this.params.p_tinh = this.vtinh_id
      } else {
        this.params.p_tinh = this.$auth.getPhanVungID()
      }
      
      const res = await Promise.all(
        [this.getQuanHuyen(), this.getDonVi(), this.getDSHTTT()]
      )
      this.loading(false)
      this.params.p_quan_options = res[0]
      this.params.p_donviql_options = res[1]

      if (this.vkieu !== undefined) this.params.p_vkieu = this.vkieu
      if (this.vdonvi !== 0) this.params.p_donviql = this.vdonvi
      
      if (this.vquan_id !== 0) {
        this.params.p_quan = this.vquan_id
        this.params.p_chk_quan = '1'
      }
      if (this.vphuong_id !== 0) {
        // this.params.p_phuong = this.vphuong_id
        this.params.p_chk_phuong = '1'
      }

      if (this.vpho_id !== 0) {
        this.params.p_pho = this.vpho_id
        this.params.p_chk_pho = '1'
      }

      if (this.vap_id !== 0) {
        this.params.p_ap = this.vap_id
        this.params.p_chk_ap = '1'
      }

      if (this.vdacdiem_id !== 0) {
        this.params.p_dacdiem = this.vdacdiem_id
        this.params.p_chk_dacdiem = '1'
      }

      if (this.vkhu_id !== 0) {
        this.params.p_khu = this.vkhu_id
        this.params.p_chk_khu = '1'
      }

      if (this.vnvtc !== null) this.params.p_ma_nvtc = this.vnvtc

      if (this.params.p_vkieu === 2) this.configs.f_enable_chk_locno = true

      if (this.params.p_donviql === 0) this.params.p_donviql = this.params.p_donviql_options[0].DONVI_ID

      if (this.params.p_ma_nvtc !== null && this.vquan_id === 0) {
        this.doSearchByMaNVTC()
      }
    },
    clickTab (index) {
      this.params.p_type = index
    },
    dlgKyCuocChangeHandler (e) {
      if (e.p_chuky_no !== null) this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
      else this.params.p_kycuoc = null
    },
    /* callback */
    onFilteringQuan: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_QUAN', 'contains', e.text, true) : query
      e.updateData(this.params.p_quan_options, query)
    },
    onFilteringPhuong: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_PHUONG', 'contains', e.text, true) : query
      e.updateData(this.params.p_phuong_options, query)
    },
    onFilteringPho: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.params.p_pho_options, query)
    },
    onFilteringAp: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.params.p_ap_options, query)
    },
    onFilteringKhu: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.params.p_khu_options, query)
    },
    onFilteringDacDiem: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.params.p_dacdiem_options, query)
    },
    onFilteringDonViQL: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_DV', 'contains', e.text, true) : query
      e.updateData(this.params.p_donviql_options, query)
    },
    /* DANH MỤC */
    async getQuanHuyen () {
      try {
        let rs = await this.$root.context.get('/web-cabman/bando_tuyencot/lay_ds_quan_huyen/' + this.params.p_tinh)
        // this.dsQuan = rs.data;
        // const results = await CommonsAPI.getDanhMucQuanHuyen(this.axios)
        // const data = await results.data
        // return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
        return rs.data
      } catch (err) {
        console.log(err)
        return []
      }
    },
    async getPhuongXa (QuanId) {
      try {
        const results = await CommonsAPI.getDanhMucPhuongTheoQuan(this.axios, { pQuanId: QuanId })
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        console.log(err)
        return []
      }
    },
    async getPho (PhuongId) {
      try {
        const results = await CommonsAPI.getDanhMucPhoApKhuTheoPhuong(this.axios, { pPhuongId: PhuongId, pNhomPhoId: 1 })
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        console.log(err)
        return []
      }
    },
    async getAp (PhuongId) {
      try {
        const results = await CommonsAPI.getDanhMucPhoApKhuTheoPhuong(this.axios, { pPhuongId: PhuongId, pNhomPhoId: 2 })
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        console.log(err)
        return []
      }
    },
    async getKhu (PhuongId) {
      try {
        const results = await CommonsAPI.getDanhMucPhoApKhuTheoPhuong(this.axios, { pPhuongId: PhuongId, pNhomPhoId: 3 })
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        console.log(err)
        return []
      }
    },
    async getDonVi () {
      try {
        // const results = await CommonsAPI.getDanhSachDonViQL(this.axios, {pNhanVienId: this.$auth.getNhanVienID()});
        const results = await CommonsAPI.getDanhSachDonViQL(this.axios)
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        console.log(err)
        return []
      }
    },
    async getDacDiem () {
      try {
        // const results = await CommonsAPI.getDanhSachDonViQL(this.axios, {pNhanVienId: this.$auth.getNhanVienID()});
        let postData = {
          pPhuongId: this.params.p_phuong,
          pPhoId: this.params.p_chk_pho ? this.params.p_pho : null,
          pApId: this.params.p_chk_ap ? this.params.p_ap : null,
          pKhuId: this.params.p_chk_khu ? this.params.p_khu : null
        }
        const results = await CommonsAPI.getDanhMucDacDiem(this.axios, postData)
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        console.log(err)
        return []
      }
    },
    async getDSHTTT () {
      try {
        await CommonsAPI.getDanhSachHinhThucThanhToan(this.axios, { kieu: 1, nhomHTTT: NHOM_HTTT.GACHNO_DAILY }).then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
                res.data.data !== undefined &&
                res.data.data.length > 0) {
              this.params.p_httt_options = res.data.data
              this.params.p_httt = res.data.data[0].HTTT_ID
            } else {
              this.params.p_httt_options = []
              this.params.p_httt = null
            }
          }
        )
      } catch (error) {
        // this.$toast.error(error.data.message_detail)
        console.log(error)
      }
    },
    async onQuanChange (args) {
      // console.log(args)
      if (args.value === null) {
        this.params.p_phuong = null
        this.params.p_phuong_options = []
        return
      }
      const res = await this.getPhuongXa(args.value)
      this.params.p_phuong_options = res

      if (this.vphuong_id !== 0) {
        this.params.p_phuong = this.vphuong_id
      }
    },
    async onPhoChange (args) {
      this.params.p_dacdiem = null
      if (args.value === null) {
        this.params.p_dacdiem_options = []
      }
      const res = await this.getDacDiem()
      this.params.p_dacdiem_options = res
    },
    async onKhuChange (args) {
      this.params.p_dacdiem = null
      if (args.value === null) {
        this.params.p_dacdiem_options = []
      }
      const res = await this.getDacDiem()
      this.params.p_dacdiem_options = res
    },
    async onApChange (args) {
      this.params.p_dacdiem = null
      if (args.value === null) {
        this.params.p_dacdiem_options = []
      }
    },
    async onPhuongChange (args) {
      this.params.p_pho = null
      this.params.p_ap = null
      this.params.p_khu = null
      this.params.p_dacdiem = null

      if (args.value === null) {
        this.params.p_pho_options = []
        this.params.p_ap_options = []
        this.params.p_khu_options = []
        this.params.p_dacdiem_options = []
        return
      }

      const res = await Promise.all([
        this.getPho(args.value),
        this.getAp(args.value),
        this.getKhu(args.value)
      ])

      this.params.p_pho_options = res[0]
      this.params.p_ap_options = res[1]
      this.params.p_khu_options = res[2]

      if (this.vpho_id !== 0) {
        this.params.p_pho = this.vpho_id
      }
      if (this.vap_id !== 0) {
        this.params.p_ap = this.vap_id
      }
      if (this.vkhu_id !== 0) {
        this.params.p_khu = this.vkhu_id
      }
      const ret = await this.getDacDiem()
      this.params.p_dacdiem_options = ret

      if (this.vdacdiem_id !== 0) {
        this.params.p_dacdiem = this.vdacdiem_id
      }

      if (this.f_first) {
        await this.doSearch()
        if (this.vnvtc !== null) {
          let index = this.dsNVThuCuoc.findIndex(x => x.MANV_TC === this.vnvtc)
          console.log(index)
          let obj = this.$refs.gridDSNVThuCuoc.$refs.grid.ej2Instances
          obj.selectRow(index)
        }
        this.f_first = false
      }
    },
    async NVThuCuocPageChangeHandler (e) {
      if (this.pageinfo.page === e.pageIndex && this.pageinfo.maxSize === e.pageSize) return
      // console.log(e)

      this.pageinfo.page = e.pageIndex
      this.pageinfo.maxSize = e.pageSize

      this.searchForm.pageNo = this.pageinfo.page + 1
      this.searchForm.pageSize = this.pageinfo.maxSize

      this.loading(true)

      await this.loadNVTC()

      this.loading(false)
    },
    RowNhanVienChanged (e) {
      if (e === null) {
        this.currentInfo = null
        this.params.p_ma_nvtc = null
        this.params.p_ten_nvtc = null
        this.params.p_dienthoai_nvtc = null
        return
      }
      this.currentInfo = e
      this.params.p_ma_nvtc = e.MANV_TC
      this.params.p_ten_nvtc = e.TENNV_TC
      this.params.p_dienthoai_nvtc = e.SO_DT
    },
    onBeforeOpen (args) {
      this.$emit('beforeOpen', args)
    },
    onBeforeClose (args) {
      this.$emit('beforeClose', args)
    }
  },
  watch: {
  }
})
</script>
