<template>
    <div>
        <div class="breadcrumb-top">
            <div class="main-title">Giao phiếu báo hỏng</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
                <li class="breadcrumb-item active">Giao phiếu báo hỏng</li>
            </ul>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li :class="{ '-disabled' : !tsbtnTimKiem.enabled }">
                    <a @click="onSeach">
                        <span class="icon one-search"></span>{{tsbtnTimKiem.text}}
                    </a>
                </li>
                <li :class="{ '-disabled' : !tsbtnGiaoPhieu.enabled }">
                    <a @click="onGiaoPhieu">
                        <span class="icon one-file-arrow-right1"></span>{{tsbtnGiaoPhieu.text}}
                    </a>
                </li>
                <li :class="{ '-disabled' : !tsbtnHuyGiao.enabled }">
                    <a @click="onHuyGiao">
                        <span class="icon one-cancel"></span>{{tsbtnHuyGiao.text}}
                    </a>
                </li>
                <li :class="{ '-disabled' : !btnCapNhatVT.enabled }">
                    <a @click="onCapNhatDv">
                        <span class="icon one-reload1"></span>{{btnCapNhatVT.text}}
                    </a>
                </li>
                <li>
                  <a href="#" v-on:click.prevent='tsbtnHenKH_Click'>
                    <span class="dropdown-icon one-calendar-check"></span> Hẹn KH
                  </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon one-circle-question"></span>Trợ giúp
                    </a>
                </li>
            </ul>
        </div>
        <div class="page-content">
            <div class="box-form box-gn-tt">
                <div class="legend-title">
                    <div class="title">Thông tin tìm kiếm</div>
                </div>
                <div class="row">
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <vue-select
                                ref="dsDichVuVt"
                                v-model="selectedServiceId"
                                :required="false"
                                :labelWidth="'90'"
                                :disable="false"
                                :options="dsDichVuVt"
                                :valueField="'DICHVUVT_ID'"
                                :labelField="'TEN_DVVT'"
                                label="Dịch vụ"
                                :checkbox="true"
                                v-on:input="onSelectedService"
                            ></vue-select>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w90">
                                Số máy/Acc
                            </div>
                            <div class="value">
                                <input type="text" class="form-control highlight"
                                    ref="txtMaTB"
                                    v-model="txtMaTB"
                                    v-on:keyup="onInputSubcriberCode">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <vue-date
                            ref="dtpNgayGiaoTK"
                            type="date"
                            format="DD/MM/YYYY"
                            v-model="dtpNgayGiaoTK"
                            :labelWidth="'90'"
                            :required="false"
                            label="Ngày giao"
                            :checkbox="false"
                            @input="onDtpNgayGiaoTKChange"
                            ></vue-date>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <vue-select
                                ref="dsQuyTrinh"
                                v-model="selectedQuyTrinhId"
                                :required="false"
                                :labelWidth="'90'"
                                :disable="false"
                                :options="dsQuyTrinh"
                                :valueField="'quytrinh_id'"
                                :labelField="'quytrinh'"
                                label="Quy trình"
                                :checkbox="true"
                                v-on:input="onSelectedQuyTrinh"
                            ></vue-select>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <vue-select
                                ref="dsTrangThai"
                                v-model="selectedStatusId"
                                :required="false"
                                :labelWidth="'90'"
                                :disable="false"
                                :options="dsTrangThai"
                                :valueField="'ID'"
                                :labelField="'NAME'"
                                label="Trạng thái"
                                :checkbox="true"
                                v-on:input="onSelectedStatus"
                            ></vue-select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form box-gn-tt">
                <div class="legend-title">
                    <div class="title">Thông tin giao phiếu</div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <vue-select
                                v-model="selectedHuongGiaoId"
                                :required="false"
                                :labelWidth="'100'"
                                :disable="false"
                                :options="dsHuongGiao"
                                :valueField="'HUONGGIAO_ID'"
                                :labelField="'HUONGGIAO'"
                                label="Hướng giao"
                                :checkbox="false"
                                v-on:input="onSelectedHuongGiao"
                            ></vue-select>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w90">
                                Số máy/Acc
                            </div>
                            <div class="value">
                                <input type="text" class="form-control"
                                    v-model="txtMaTBChon">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <vue-date
                            ref="dtpNgayGiao"
                            :class="{ 'error-input' : isDtpNgayGiaoError }"
                            v-model="dtpNgayGiao"
                            :labelWidth="'90'"
                            :required="false"
                            label="Ngày giao"
                            :checkbox="false"
                            ></vue-date>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w100">
                                Nội dung giao
                            </div>
                            <div class="value">
                                <input type="text" class="form-control"
                                    v-model="noiDungGiao">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <vue-select
                                v-model="selectedDonViNhanId"
                                :required="false"
                                :labelWidth="'90'"
                                :disable="!cboTramTC.enabled"
                                :options="dsDonViNhan"
                                :valueField="'DONVI_ID'"
                                :labelField="'TEN_DV'"
                                :label="cboTramTC.text"
                                :checkbox="false"
                                v-on:input="onSelectedDonVi"
                            ></vue-select>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <vue-select
                                :class="{ 'error-input' : isMucdoError }"
                                v-model="selectedMucdoId"
                                :required="false"
                                :labelWidth="'90'"
                                :disable="false"
                                :options="dsMucDoXL"
                                :valueField="'MUCDO_ID'"
                                :labelField="'MUCDO'"
                                label="Mức độ"
                                :checkbox="false"
                                v-on:input="onSelectedMucDo"
                            ></vue-select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">
                   Danh sách phiếu yêu cầu
                </div>
                <DataGrid
                    ref="grcDanhSach"
                    :columns="columns"
                    :dataSource="grcDanhSach"
                    :enable-paging-server="false"
                    :showColumnCheckbox="true"
                    :allowPaging="true"
                    :loading="true"
                    :showFilter="true"
                    @selectedItemsChanged="grid_selectedRowChanged"
                    @rowSelected="rowSelected"
                >
                </DataGrid>
            </div>
        </div>
        <frmHenKH ref="reffrmHenKH" :params="frmHenKHInput" @closeHenKH="closeHenKH"></frmHenKH>
        <frmHenKHV2 ref="reffrmHenKHV2" :params="frmHenKHV2Input" @closeHenKH="closeHenKH"></frmHenKHV2>
    </div>    
</template>

<script>
import api from './api/index'
import axios from 'axios'
import moment from 'moment'
import frmHenKH from '@/modules/incident/subscriber/SearchIncidentUpdateAppointment/components/frmHenKH.vue'
import frmHenKHV2 from '@/modules/incident/subscriber/SearchIncidentUpdateAppointment/components/frmHenKH_V2.vue'

const NHOM_QT = {
  DIEUHANH_THICONG: 1,
  BAOHONG: 2,
  GIAIQUYET_KN: 3,
  BAOHANH_TBI: 4,
  SLSC_CNTT: 5,
  LUANCHUYEN_HOSO: 6,
  QUANLY_VATTU: 7,
  CAITAO_BAODUONG: 8
}

export default {
  components: {    
    frmHenKH,
    frmHenKHV2
  },
  data () {
    return {
      dsDichVuVt: [],
      selectedServiceId: 0,
      dsHuongGiaoFormLoad: [],
      dsHuongGiao: [],
      selectedHuongGiaoId: 1,
      dsMucDoXL: [],
      selectedMucdoId: 0,
      isMucdoError: false,
      dsDonViNhan: [],
      selectedDonViNhanId: 0,
      dsTrangThai: [],
      selectedStatusId: 0,
      dsQuyTrinh: [],
      selectedQuyTrinhId: 0,
      dtpNgayGiao: '',
      isDtpNgayGiaoError: false,
      dtpNgayGiaoTK: '',
      txtMaTB: '',
      txtMaTBChon: '',
      noiDungGiao: '',
      btnCapNhatVT: {
        enabled: false,
        text: 'Cập nhật ĐV'
      },
      cboTramTC: {
        enabled: false,
        text: 'Đơn vị nhận'
      },
      tsbtnHuyGiao: {
        enabled: false,
        text: 'Hủy giao'
      },
      tsbtnTimKiem: {
        enabled: true,
        text: 'Tìm kiếm'
      },
      tsbtnGiaoPhieu: {
        enabled: true,
        text: 'Giao phiếu'
      },
      columns: [{
        fieldName: 'ma_tb',
        headerText: 'Số máy/Acc'
      }, {
        fieldName: 'ten_tb',
        headerText: 'Tên thuê bao'
      }, {
        fieldName: 'diachi_ld',
        headerText: 'Địa chỉ lắp đặt'
      }, {
        fieldName: 'donvinhan',
        headerText: 'Đơn vị nhận'
      }],
      grcDanhSach: [],
      selectedGrcDanhSach: [],
      kieu_id: 0,
      hg_id: 0,
      baohong_id: 0,
      kt_thoigian_hen_baohong: false,
      XACMINH_HEN_BAOHONG: -1,
      frmHenKHInput: {},
      frmHenKHV2Input: {},
      Quyen_Duoc_hen: 0,
    }
  },
  props: ['dataSource'],
  watch: {
    dataSource: async function (newValue, oldValue) {
      console.log('dataSource', newValue)

      this.loading(true)
      try {
        // assign data
        this.selectedServiceId = newValue.inputServiceId !== null && newValue.inputServiceId !== undefined ? newValue.inputServiceId : this.selectedServiceId
        this.selectedQuyTrinhId = newValue.inputQuyTrinhId !== null && newValue.inputQuyTrinhId !== undefined ? newValue.inputQuyTrinhId : this.selectedQuyTrinhId
        this.selectedHuongGiaoId = newValue.inputHuongGiaoId !== null && newValue.inputHuongGiaoId !== undefined ? newValue.inputHuongGiaoId : this.selectedHuongGiaoId
        this.selectedStatusId = newValue.inputStatusId !== null && newValue.inputStatusId !== undefined ? newValue.inputStatusId : this.selectedStatusId
        this.dtpNgayGiaoTK = newValue.inputDtpNgayGiaoTK

        // ds quy trinh
        const resDsQuyTrinh = await api.getDsQuyTrinh(axios, {
          nhom_qt_id: NHOM_QT.BAOHONG,
          vdichvuvt_id: this.selectedServiceId,
          vloaihd_id: 0,
          vphanvung_id: 0
        })
        if (resDsQuyTrinh.data.message_detail) {
          throw resDsQuyTrinh.data.message_detail
        }
        this.dsQuyTrinh = resDsQuyTrinh.data.data

        // ds huong giao
        const resDsHuongGiao = await api.getDsHuongGiao(axios, {
          nguoidung_id: this.$auth.getNguoiDungID(),
          quytrinh_id: this.selectedQuyTrinhId,
          dichvuvt_id: this.selectedServiceId
        })
        if (resDsHuongGiao.data.message_detail) {
          throw resDsHuongGiao.data.message_detail
        } else {
          this.dsHuongGiao = this.upperCaseKeyInlist(Array.from(new Set(resDsHuongGiao.data.data.map(a => a.huonggiao_id)))
            .map(id => {
              return resDsHuongGiao.data.data.find(a => a.huonggiao_id === id)
            }))
        }

        // foreach to search
        const resultSearch = []
        let distinct_arr = []
        if (newValue.inputTxtMaTBList && newValue.inputTxtMaTBList.length > 0) {
          for (let i = 0; i < newValue.inputTxtMaTBList.length; i++) {
            if(distinct_arr.length>0){
              if (distinct_arr.indexOf(newValue.inputTxtMaTBList[i])>=0){
                continue
              }
            }
            distinct_arr.push(newValue.inputTxtMaTBList[i])            
            this.txtMaTB = newValue.inputTxtMaTBList[i]
            const result = await this.HienThi_DanhSach()
            if (result && result.length && result.length > 0) {
              resultSearch.push(...result)
            }
          }
        }

        // reset data
        this.txtMaTB = newValue.inputTxtMaTBList.length > 1 ? '' : this.txtMaTB
        this.txtMaTBChon = this.txtMaTB
        this.selectedGrcDanhSach = []

        // assign search data
        console.log('resultSearch')
        console.log(resultSearch)
        this.grcDanhSach = resultSearch
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.loading(false)
    },
    dsDichVuVt: function (newValue, oldValue) {
      if (!this.dataSource) {
        this.selectedServiceId = newValue.length > 0 ? newValue[0].DICHVUVT_ID : 0
      }
    },
    dsQuyTrinh: function (newValue, oldValue) {
      if (!this.dataSource) {
        this.selectedQuyTrinhId = newValue.length > 0 ? newValue[0].quytrinh_id : 0
      }
    },
    dsHuongGiao: function (newValue, oldValue) {
      if (!this.dataSource) {
        this.selectedHuongGiaoId = newValue.length > 0 ? newValue[0].HUONGGIAO_ID : 0
      }
    },
    dsMucDoXL: function (newValue, oldValue) {
      this.selectedMucdoId = newValue.length > 0 ? newValue[0].MUCDO_ID : 0
    },
    dsDonViNhan: function (newValue, oldValue) {
      this.selectedDonViNhanId = newValue.length > 0 ? newValue[0].DONVI_ID : 0
    },
    dsTrangThai: function (newValue, oldValue) {
      if (!this.dataSource) {
        this.selectedStatusId = newValue.length > 0 ? newValue[0].ID : 1
      }
    }
  },
  mounted: async function () {
    this.loading(true)
    try {
      // default checkboxes are true
      this.$refs.dsDichVuVt.checkbox_value = true
      this.$refs.dsQuyTrinh.checkbox_value = true
      this.$refs.dsTrangThai.checkbox_value = true

      this.kieu_id = this.$router.history.current.query.kieu_id
      this.hg_id = this.$router.history.current.query.huonggiao_id
      this.txtMaTB = this.$router.history.current.query.ma_tb
      this.selectedStatusId = this.$router.history.current.query.ttph
      if (this.$router.history.current.query.ngaygiao && this.$router.history.current.query.ngaygiao !== '0') {
        this.dtpNgayGiao = this.$router.history.current.query.ngaygiao
      } else this.dtpNgayGiao = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      this.baohong_id = this.$router.history.current.query.baohong_id

      const data = {
        NGUOIDUNG_ID: 1,
        QUYTRINH_ID: 2,
        DICHVUVT_ID: 3
      }
      const resFormLoad = await api.getFormLoad(axios, {
        ds_para: JSON.stringify(data)
      })
      if (resFormLoad.data.message_detail) {
        throw resFormLoad.data.message_detail
      } else {
        const data = JSON.parse(resFormLoad.data.data)
        console.log('resFormLoad data', data)
        this.dsDichVuVt = data.RESULT.DICHVUVT
        this.dsHuongGiaoFormLoad = data.RESULT.HUONGGIAO
        this.dsMucDoXL = data.RESULT.MUCDO_XL
        this.dsDonViNhan = JSON.parse(data.RESULT.DTTRAMTC)

        const resStatuses = await api.getStatuses(axios)
        if (resStatuses.data.message_detail) {
          throw resStatuses.data.message_detail
        } else {
          this.dsTrangThai = resStatuses.data.data
        }
      }

      this.dtpNgayGiaoTK = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      this.btnCapNhatVT.enabled = true
      
      let ds_kt1 = await this.lay_ds_thamso_macdinh(0) 
      if (ds_kt1 != null && ds_kt1.length > 0) {
        console.log('lay_ds_thamso_macdinh')
        console.log(ds_kt1)
        let str_vtemp = 'XACMINH_HEN_BAOHONG' //Hẹn báo hỏng có xác minh
        let det = ds_kt1.filter((x) => x.ma_ts == str_vtemp)        
        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'] == '1') this.XACMINH_HEN_BAOHONG = 1
        }
        str_vtemp = 'KT_THOIGIAN_HEN_BAOHONG' //kt thời gian hẹn
        det = ds_kt1.filter((x) => x.ma_ts == str_vtemp)

        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'] == '1') this.kt_thoigian_hen_baohong = true
        }
      }
      let donviId = this.$root.token.getDonViID()
      let ds = await this.lay_ds_thamso_macdinh_dv()
      if (ds.length > 0) {
        let str_vtemp = 'TTKD_CAPNHAT_HEN'
        let det1 = ds.filter((x) => x.ma_ts == str_vtemp && x.donvi_id == donviId)        
        if (det1.length > 0) {
          let row = det1[0]
          if (row['ten_ts'] == '1') this.Quyen_Duoc_hen = 1
        }
      }

      this.HienThi_GiaoDien()
    } catch (error) {
      console.log(error)
      this.$toast.error(error.toString())
    }
    this.$refs.txtMaTB.focus()
    this.$emit('onFinishedMounted', true)
    this.loading(false)
  },
  computed: {
  },
  methods: {
    HienThi_GiaoDien: async function () {
      if (this.kieu_id == 1) {
        // this.$refs.dsDichVuVt.checkbox_value = true
        // this.selectedServiceId = 0
        // this.$refs.dsQuyTrinh.checkbox_value = true
        // this.selectedQuyTrinhId = 0
        this.selectedStatusId = this.hg_id
        await this.HienThi_DanhSach()
      }
    },
    HienThi_DanhSach: async function () {
      console.log('HienThi_DanhSach')
      console.log(this.selectedStatusId)
      if (this.selectedStatusId.toString() === '1') {
        this.btnCapNhatVT.enabled = true
        this.cboTramTC.enabled = true

        this.tsbtnHuyGiao.enabled = false
        this.tsbtnTimKiem.enabled = true
        this.tsbtnGiaoPhieu.enabled = true
      } else {
        this.tsbtnHuyGiao.enabled = true
        this.tsbtnTimKiem.enabled = true
        this.tsbtnGiaoPhieu.enabled = false
        this.btnCapNhatVT.enabled = false
      }

      if (this.dsTrangThai.length === 0 || !this.selectedStatusId) return

      const postData = {
        huongGiaoId: this.selectedHuongGiaoId,
        ngayGiao: moment(this.dtpNgayGiaoTK, 'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY'),
        trangThai: this.selectedStatusId,
        maTB: this.txtMaTB !== undefined ? this.txtMaTB.trim() : ''
      }
      const resHienThiDanhSach = await api.getDsBaoHong(axios, postData)

      if (resHienThiDanhSach.data.message_detail) {
        throw resHienThiDanhSach.data.message_detail
      }

      this.selectedGrcDanhSach = []
      console.log('HienThi_DanhSach this.selectedGrcDanhSach', this.selectedGrcDanhSach)
      this.grcDanhSach = resHienThiDanhSach.data.data
      console.log('this.grcDanhSach')
      console.log(this.grcDanhSach)
      
      

      return resHienThiDanhSach.data.data
    },
    LAY_DS_HUONGGIAO: async function () {
      const resDsHuongGiao = await api.getDsHuongGiao(axios, {
        nguoidung_id: this.$auth.getNguoiDungID(),
        quytrinh_id: this.selectedQuyTrinhId,
        dichvuvt_id: this.selectedServiceId
      })
      if (resDsHuongGiao.data.message_detail) {
        throw resDsHuongGiao.data.message_detail
      } else {
        // this.dsHuongGiao = this.upperCaseKeyInlist(resDsHuongGiao.data.data.filter(i => i.huonggiao_id.toString() === '1315'))
        this.dsHuongGiao = this.upperCaseKeyInlist(Array.from(new Set(resDsHuongGiao.data.data.map(a => a.huonggiao_id)))
          .map(id => {
            return resDsHuongGiao.data.data.find(a => a.huonggiao_id === id)
          }))
      }
    },
    upperCaseKeyInlist: function (list) {
      for (var i = 0; i < list.length; i++) {
        const a = list[i]
        for (var key in a) {
          if (a.hasOwnProperty(key)) {
            a[key.toUpperCase()] = a[key]
            delete a[key]
          }
        }
        list[i] = a
      }

      return list
    },
    onSelectedService: async function (e) {
      if (!e) return

      this.loading(true)
      try {
        const resDsQuyTrinh = await api.getDsQuyTrinh(axios, {
          nhom_qt_id: NHOM_QT.BAOHONG,
          vdichvuvt_id: this.selectedServiceId,
          vloaihd_id: 0,
          vphanvung_id: 0
        })
        if (resDsQuyTrinh.data.message_detail) {
          throw resDsQuyTrinh.data.message_detail
        }
        this.dsQuyTrinh = resDsQuyTrinh.data.data
        this.selectedQuyTrinhId = this.dsQuyTrinh.length > 0 ? this.dsQuyTrinh[0].quytrinh_id : 0

        await this.LAY_DS_HUONGGIAO()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.loading(false)
    },
    onSelectedStatus: async function (e) {
      if (!e || !this.$refs.dsTrangThai.checkbox_value) return

      this.loading(true)
      try {
        const resDsDonVi = await api.getDsDonViTheoHuongGiao(axios, {
          vhuonggiao_id: this.selectedHuongGiaoId,
          vphanvung_id: 0
        })

        if (resDsDonVi.data.message_detail) {
          throw resDsDonVi.data.message_detail
        }

        this.dsDonViNhan = this.upperCaseKeyInlist(resDsDonVi.data.data)
        console.log(this.dsDonViNhan)

        await this.HienThi_DanhSach()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.loading(false)
    },
    onSelectedHuongGiao: async function (selectedHuongGiaoId) {
      this.loading(true)
      try {
        const resDsDonVi = await api.getDsDonViTheoHuongGiao(axios, {
          vhuonggiao_id: selectedHuongGiaoId,
          vphanvung_id: 0
        })

        if (resDsDonVi.data.message_detail) {
          throw resDsDonVi.data.message_detail
        }

        this.dsDonViNhan = this.upperCaseKeyInlist(resDsDonVi.data.data)

        await this.HienThi_DanhSach()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.loading(false)
    },
    onSelectedMucDo: function () {

    },
    onSelectedDonVi: function () {},
    onSelectedQuyTrinh: async function (e) {
      if (!e || !this.$refs.dsQuyTrinh.checkbox_value) return

      this.loading(true)
      try {
        await this.LAY_DS_HUONGGIAO()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.loading(false)
    },
    grid_selectedRowChanged: function (e) {
      this.selectedGrcDanhSach = e
    },
    rowSelected: function (e) {
      if (e.data) {
        this.txtMaTBChon = e.data.ma_tb
        this.noiDungGiao = e.data.nd_giao
        this.selectedDonViNhanId = e.data.donvi_id
      }
    },
    onSeach: async function () {
      this.loading(true)
      try {
        await this.HienThi_DanhSach()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.loading(false)
    },
    onInputSubcriberCode: async function (e) {
      if (e.keyCode === 13) {
        this.loading(true)
        try {
          await this.onSeach()
        } catch (error) {
          console.log(error)
          this.$toast.error(error.toString())
        }
        this.loading(false)
      }
    },
    onDtpNgayGiaoTKChange: async function () {
      this.loading(true)
      try {
        await this.HienThi_DanhSach()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.loading(false)
    },
    onHuyGiao: async function () {
      this.loading(true)
      try {
        if (this.selectedGrcDanhSach.length === 0) {
          const textError = 'Bạn chưa chọn phiếu để hủy'
          throw textError
        }

        for (let i = 0; i < this.selectedGrcDanhSach.length; i++) {
          if (this.selectedGrcDanhSach[i].ttph_id &&
          (this.selectedGrcDanhSach[i].ttph_id.toString().trim() === '4' || this.selectedGrcDanhSach[i].ttph_id.toString().trim() === '2')) {
            const textError = 'Phiếu đang được xử lý. Không được hủy phiếu này'
            throw textError
          }
        }

        const postData = []
        for (let i = 0; i < this.selectedGrcDanhSach.length; i++) {
          postData.push({
            PHIEU_ID: this.selectedGrcDanhSach[i].phieu_id,
            BAOHONG_ID: this.selectedGrcDanhSach[i].baohong_id,
            HUONGGIAO_ID: this.selectedGrcDanhSach[i].huonggiao_id,
            PHIEU_CHA_ID: this.selectedGrcDanhSach[i].phieu_cha_id,
            TTBH_ID: this.selectedGrcDanhSach[i].ttbh_id,
            TTBH_ID_PHIEU: this.selectedGrcDanhSach[i].ttbh_id_phieu
          })
        }

        const resHuyGiao = await api.huyGiaoPhieu(axios, {
          ds_para: JSON.stringify(postData)
        })

        if (resHuyGiao.data.message_detail) {
          throw resHuyGiao.data.message_detail
        }

        this.$toast.success('Đã hủy giao phiếu thành công!')
        this.txtMaTBChon = ''
        this.noiDungGiao = ''
        this.dtpNgayGiao = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')

        await this.HienThi_DanhSach()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.loading(false)
    },
    onCapNhatDv: async function () {
      this.loading(true)
      try {
        if (this.selectedGrcDanhSach.length === 0) {
          const textError = 'Bạn chưa chọn phiếu!'
          throw textError
        }

        // TODO: Chờ sửa lại api
        for (let i = 0; i < this.selectedGrcDanhSach.length; i++) {
          const resCapNhat = await api.capNhatDV(axios, {
            vbaohong_id: this.selectedGrcDanhSach[i].baohong_id,
            vdonvi_id: this.selectedDonViNhanId,
            vloaidv_id: this.selectedGrcDanhSach[i].loaidv_id,
            vkieudv_id: this.selectedGrcDanhSach[i].kieudv_id
          })

          if (resCapNhat.data.message_detail) {
            throw resCapNhat.data.message_detail
          }
        }

        this.$toast.success('Đã cập nhật đơn vị !')
        await this.HienThi_DanhSach()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.loading(false)
    },
    async tsbtnHenKH_Click() {
      try {
        if (this.selectedGrcDanhSach.length <= 0) {
          this.$toast.error('Bạn chưa chọn bản ghi cần cập nhật giờ hẹn!')
          return
        }
        let khoaphieu = 0
        let phieu_id = this.selectedGrcDanhSach[0].phieu_id 
        console.log('tsbtnHenKH_Click')
        console.log(this.selectedGrcDanhSach)
        if (this.kt_thoigian_hen_baohong) khoaphieu = this.this.selectedGrcDanhSach[0].khoaphieu 
        if (this.XACMINH_HEN_BAOHONG == -1) {          
          this.frmHenKHInput.baohong_id = this.selectedGrcDanhSach[0].baohong_id
          this.frmHenKHInput.phieu_id = phieu_id
          this.frmHenKHInput.Quyen_Duoc_hen = this.Quyen_Duoc_hen
          this.frmHenKHInput.khoaphieu = khoaphieu
          this.frmHenKHInput.kt_thoigian_hen_baohong = this.kt_thoigian_hen_baohong
          console.log('this.frmHenKHInput')
          console.log(this.frmHenKHInput)
          this.$refs.reffrmHenKH.openDialog()
        } else {     
          
          this.frmHenKHV2Input.baohong_id = this.selectedGrcDanhSach[0].baohong_id
          this.frmHenKHV2Input.phieu_id = phieu_id
          this.frmHenKHV2Input.Quyen_Duoc_hen = this.Quyen_Duoc_hen
          console.log(this.frmHenKHV2Input)    
          console.log('this.frmHenKHV2Input') 
          this.$refs.reffrmHenKHV2.openDialog()
        }
      } catch (err) {
        this.$toast.error(`${err.message}`)
      }
    },
    async closeHenKH(thuchien) {
      
    },
    onGiaoPhieu: async function () {
      this.loading(true)
      try {
        if (!this.selectedMucdoId) {
          const errorText = 'Bạn chưa chọn mức độ xử lý'
          this.isMucdoError = true
          throw errorText
        } else this.isMucdoError = false

        const kt_dambao_ckcl_kh = true
        const camket_cl = true
        if (kt_dambao_ckcl_kh && !camket_cl && this.selectedMucdoId.toString() === '3') {
          const errorText = 'Mức độ "Khẩn - KH yêu cầu bồi thường CKCL" chỉ áp dụng cho thuê bao có cam kết chất lượng!'
          throw errorText
        }

        const toDay = moment((new Date()).setHours(23, 59, 59, 999))
        if (moment(this.dtpNgayGiao,'DD/MM/yyyy HH:mm:ss') > toDay) {
          const errorText = 'Ngày giao không được lớn hơn ngày hiện tại'
          this.isDtpNgayGiaoError = true
          throw errorText
        } else this.isDtpNgayGiaoError = false

        console.log('onGiaoPhieu this.selectedGrcDanhSach', this.selectedGrcDanhSach)
        if (this.selectedGrcDanhSach.length === 0) {
          const errorText = 'Bạn chưa chọn phiếu giao'
          throw errorText
        }

        const resKeyName = await api.getKey(axios, {
          keyname: 'PHIEU_ID'
        })

        if (resKeyName.data.message_detail) {
          throw resKeyName.data.message_detail
        }

        const phieu_id = resKeyName.data.data
        const ds_phieu_chon = []
        const ds_phieu_taodl = []
        let ds_sodt_donvi = []

        const huongGiao = this.dsHuongGiaoFormLoad.find(hg => hg.HUONGGIAO_ID.toString() === this.selectedHuongGiaoId.toString())
        console.log('huongGiao')
        console.log(huongGiao)
        const ds_para = {
          NHANVIEN_ID: this.$auth.getNhanVienID(),
          THUEBAO_ID: this.$auth.getNguoiDungID(),
          MUCDO_ID: this.selectedMucdoId,
          NGAYGIAO: this.dtpNgayGiao,
          GIAOVIEC_TD: huongGiao !== null && huongGiao.SMS === 1 ? 1 : 0,
          SENDSMS: huongGiao !== null && huongGiao.GIAOVIEC === 1 ? 1 : 0,
          HUONGGIAO_ID: this.selectedHuongGiaoId
        }
        console.log(ds_para)
        //return


        for (let i = 0; i < this.selectedGrcDanhSach.length; i++) {
          const selectedDonVi = this.dsDonViNhan.find(dv => dv.DONVI_ID.toString() === this.selectedGrcDanhSach[i].donvi_id.toString())

          ds_phieu_chon.push({
            THUEBAO_ID: this.selectedGrcDanhSach[i].thuebao_id,
            MA_TB: this.selectedGrcDanhSach[i].ma_tb,
            NOIDUNG: this.noiDungGiao,
            PHIEU_ID: this.selectedGrcDanhSach[i].phieu_id,
            DICHVUVT_ID: this.selectedGrcDanhSach[i].dichvuvt_id,
            DONVI_NHAN_ID: this.selectedGrcDanhSach[i].donvi_id,
            DONVI_ID: this.$auth.getDonViID(),
            BAOHONG_ID: this.selectedGrcDanhSach[i].baohong_id,
            SO_DT: selectedDonVi !== undefined && selectedDonVi.so_dt !== null ? selectedDonVi.so_dt : ''
          })

          

          ds_phieu_taodl.push({
            THUEBAO_ID: this.selectedGrcDanhSach[i].thuebao_id,
            DONVI_NHAN_ID: this.selectedGrcDanhSach[i].donvi_id,
            BAOHONG_ID: this.selectedGrcDanhSach[i].baohong_id,
            PHIEU_CHA_ID: this.selectedGrcDanhSach[i].phieu_id,
            TTBH_ID: this.selectedGrcDanhSach[i].ttbh_id,
            MA_TB: this.selectedGrcDanhSach[i].ma_tb,
            KIEUDV_ID: this.selectedGrcDanhSach[i].kieudv_id,
            HUONGGIAO_ID: this.selectedHuongGiaoId,
            PHIEU_ID: phieu_id,
            NHANVIEN_GIAO_ID: this.$auth.getNhanVienID(),
            TINHTRANG: this.selectedGrcDanhSach[i].tinhtrang,
            NGAYGIAO: moment(this.selectedGrcDanhSach[i].ngaygiao, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'),
            ND_GIAO: this.noiDungGiao,
            TTPH_ID: this.selectedGrcDanhSach[i].ttbh_id,
            NGUOI_CN: this.$auth.getNhanVienID(),
            MAY_CN: "1.2.3.4",
            DAUCUOI_ID: this.selectedGrcDanhSach[i].kieudv_id,
            DONVI_GIAO_ID: this.$auth.getDonViID()
          })

          if (parseInt(this.selectedServiceId) == 9){ // thêm phiếu giao đơn vị tsl đầu còn lại nếu có
            for (let j = 0; j< this.grcDanhSach.length; j++){
              if (    this.grcDanhSach[j].thuebao_id == this.selectedGrcDanhSach[i].thuebao_id
                   && this.grcDanhSach[j].baohong_id == this.selectedGrcDanhSach[i].baohong_id
                   && this.grcDanhSach[j].donvi_id != this.selectedGrcDanhSach[i].donvi_id
                  ){
                    let items = ds_phieu_taodl.filter(x=>x.THUEBAO_ID==this.grcDanhSach[j].thuebao_id 
                                                        && x.BAOHONG_ID==this.grcDanhSach[j].baohong_id 
                                                        && x.DONVI_NHAN_ID==this.grcDanhSach[j].donvi_id  )
                    if (items.length == 0) {
                      
                      const resKeyName1 = await api.getKey(axios, {
                        keyname: 'PHIEU_ID'
                      })

                      if (resKeyName1.data.message_detail) {
                        throw resKeyName1.data.message_detail
                      }

                      const phieu_id1 = resKeyName1.data.data
                      ds_phieu_taodl.push({
                        THUEBAO_ID: this.grcDanhSach[j].thuebao_id,
                        DONVI_NHAN_ID: this.grcDanhSach[j].donvi_id,
                        BAOHONG_ID: this.grcDanhSach[j].baohong_id,
                        PHIEU_CHA_ID: this.grcDanhSach[j].phieu_id,
                        TTBH_ID: this.grcDanhSach[j].ttbh_id,
                        MA_TB: this.grcDanhSach[j].ma_tb,
                        KIEUDV_ID: this.grcDanhSach[j].kieudv_id,
                        HUONGGIAO_ID: this.selectedHuongGiaoId,
                        PHIEU_ID: phieu_id1,
                        NHANVIEN_GIAO_ID: this.$auth.getNhanVienID(),
                        TINHTRANG: this.grcDanhSach[j].tinhtrang,
                        NGAYGIAO: moment(this.grcDanhSach[j].ngaygiao, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'),
                        ND_GIAO: this.noiDungGiao,
                        TTPH_ID: this.grcDanhSach[j].ttbh_id,
                        NGUOI_CN: this.$auth.getNhanVienID(),
                        MAY_CN: "1.2.3.4",
                        DAUCUOI_ID: this.grcDanhSach[j].kieudv_id,
                        DONVI_GIAO_ID: this.$auth.getDonViID()
                      })
                    }
                  }
            }
            
          }
          let ds_sdt = await this._LayDS_SDT_DonViNhan( this.selectedGrcDanhSach[i].donvi_id, this.selectedGrcDanhSach[i].thuebao_id, this.selectedGrcDanhSach[i].ma_tb, this.noiDungGiao, this.selectedGrcDanhSach[i].phieu_id)
          console.log('ds_sdt')
          console.log(ds_sdt)
          ds_sodt_donvi = ds_sodt_donvi.concat(ds_sdt)
          // ds_sodt_donvi.push({
          //   THUEBAO_ID: this.selectedGrcDanhSach[i].thuebao_id,
          //   MA_TB: this.selectedGrcDanhSach[i].ma_tb,
          //   NOIDUNG: this.noiDungGiao,
          //   SO_DT: selectedDonVi !== undefined && selectedDonVi.so_dt !== null ? selectedDonVi.so_dt : '',
          //   DONVI_NHAN_ID: this.selectedGrcDanhSach[i].donvi_id
          // })
        }
        console.log('dữ liệu giao phiếu')
        console.log(ds_phieu_chon)
        console.log(ds_phieu_taodl)
        console.log(ds_sodt_donvi)
        console.log(ds_para)

        
        const resGiaoPhieu = await api.giaoPhieu(axios, {
          ds_phieu_chon: JSON.stringify(ds_phieu_chon),
          ds_phieu_taodl: JSON.stringify(ds_phieu_taodl),
          ds_sodt_donvi: JSON.stringify(ds_sodt_donvi),
          ds_para: JSON.stringify(ds_para)
        })
        

        if (resGiaoPhieu.data.message_detail) {
          throw resGiaoPhieu.data.message_detail
        }

        this.$toast.success('Đã giao phiếu thành công')

        this.HienThi_DanhSach()

        this.resetErrorInput()

        this.$emit('onFinishedGiaoPhieu', true)
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
        this.$emit('onFinishedGiaoPhieu', false)
      }
      this.loading(false)
    },
    async _LayDS_SDT_DonViNhan( vdonvi_nhan_id, _vthuebao_id, _matb, _vnoidung, _vphieuid)
    {
      let _ds_sodt_donvi = []
      try {
        let _StringsoDT = "";
        let _StringsoDT_nv = "";
        _StringsoDT = await api.fn_tt_donvi(axios, { type:6, param : vdonvi_nhan_id }); //(checkdata.MAP_ID("so_dt", DatabaseConstants.DB1 + ".donvi", "where donvi_id =" + vdonvi_nhan_id), @"\s+", "");

        let dset = await api.lay_sodt_nhanvien_khoan_tn(axios,_vthuebao_id)
        console.log('_LayDS_SDT_DonViNhan1')
        console.log(dset)
        //nhapt sửa 09092016      
        if (dset.length > 0) {         
            for (let j = 0;j < dset.length; j++)
            {
                _StringsoDT_nv = _StringsoDT_nv + dset[j]["so_dt"] + ",";
                j++;
                if (j >= dset.length) break;
            }
        }
        console.log('_LayDS_SDT_DonViNhan2')
        if (_StringsoDT == "-1" || _StringsoDT == "")
        {
            if (_StringsoDT_nv != "")
                _StringsoDT = _StringsoDT_nv;
        }
        else
            _StringsoDT = _StringsoDT + "," + _StringsoDT_nv;

        console.log('_LayDS_SDT_DonViNhan3')
        if (_StringsoDT!= null && _StringsoDT!="" && _StringsoDT != "-1")
        {
            //todo
            if (_StringsoDT.endsWith(","))
                _StringsoDT = _StringsoDT.substring(0,_StringsoDT.length - 1);
            let _soDT = _StringsoDT.split(',');
            
            
            for (let i=0;i<_soDT.length;i++)
            {
              let _dr = {
                THUEBAO_ID: _vthuebao_id,
                MA_TB: _matb,
                NOIDUNG: _vnoidung,
                SO_DT: _soDT[i],
                DONVI_NHAN_ID: vdonvi_nhan_id,
                PHIEU_ID:_vphieuid
              }
              _ds_sodt_donvi.push(_dr)
            }
        }    
        console.log('_LayDS_SDT_DonViNhan4')    
        console.log(_ds_sodt_donvi)
      }
      catch(ex){
        return []
      }
      return _ds_sodt_donvi;
    },
    resetErrorInput: function () {
      this.isDtpNgayGiaoError = false
      this.isMucdoError = false
    },    
    async lay_ds_thamso_macdinh_dv() {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let input = {
        vdonvi_id: donviId,
        vkieu_id: 4
      }
      let data = this.GetData(await api.lay_ds_thamso_macdinh_dv(this.axios, input))
      return data
    },
    async lay_ds_thamso_macdinh(kieu) {
      let input = {
        kieu_id: kieu
      }
      let data = this.GetData(await api.lay_ds_thamso_macdinh(this.axios, input))
      return data
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
  }
}
</script>

<style>
.-disabled{
    pointer-events: none;
    cursor: default;
}
.-disabled:hover{
    background: unset;
}
.-disabled > a {
    color: gray !important;
}
.-disabled > a > span {
    color: gray;
}

a:hover {
  cursor: pointer;
}

.error-input input.mx-input {
  border: 1px solid red !important;
  box-shadow: 0px 0px 5px 1px red !important;
}

.error-input input {
  border: 1px solid #f57f6c;
  animation-name: bounce;
  animation-duration: 0.5s;
  animation-delay: 0.25s;
}

input.error-input {
  border: 1px solid #f57f6c;
  animation-name: bounce;
  animation-duration: 0.5s;
  animation-delay: 0.25s;
}

@keyframes bounce {
  0% {
    transform: translateX(0px);
    timing-function: ease-in;
  }
  37% {
    transform: translateX(5px);
    timing-function: ease-out;
  }
  55% {
    transform: translateX(-5px);
    timing-function: ease-in;
  }
  73% {
    transform: translateX(4px);
    timing-function: ease-out;
  }
  82% {
    transform: translateX(-4px);
    timing-function: ease-in;
  }
  91% {
    transform: translateX(2px);
    timing-function: ease-out;
  }
  96% {
    transform: translateX(-2px);
    timing-function: ease-in;
  }
  100% {
    transform: translateX(0px);
    timing-function: ease-in;
  }
}
</style>
