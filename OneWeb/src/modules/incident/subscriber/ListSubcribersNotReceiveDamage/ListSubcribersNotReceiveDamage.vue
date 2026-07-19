<template>
    <div>
        <div class="breadcrumb-top">
            <div class="main-title">danh sách thuê bao không nhận hỏng</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
                <li class="breadcrumb-item active">Danh sách thuê bao không nhận hỏng</li>
            </ul>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a @click="onClickLoadData">
                        <span class="icon one-file-attach"></span>Lấy DS
                    </a>
                </li>
                <li :class="{'-disabled' : !isEnableAddNew}">
                    <a @click="onClickAddNew">
                        <span class="icon one-file-plus"></span>Nhập mới
                    </a>
                </li>
                <li :class="{'-disabled' : !isEnableSave}">
                    <a @click="onClickSave">
                        <span class="icon one-save"></span>Ghi lại
                    </a>
                </li>
                <li :class="{'-disabled' : !isEnableCancel}">
                    <a @click="onClickCancel">
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Huỷ
                    </a>
                </li>
                <li :class="{'-disabled' : !isEnableDelete}">
                    <a @click="onDelete">
                        <span class="icon one-trash"></span>Xóa
                    </a>
                </li>
                <li :class="{ '-disabled' : !isEnableReadFile }">
                    <a @click="readFile">
                        <span class="icon one-book"></span>Đọc file thêm
                    </a>
                </li>
                <li>
                    <a @click="exportFile">
                        <span class="icon one-download"></span>Xuất File
                    </a>
                </li>
            </ul>
            <div class="more dropdown">
                <a data-toggle="dropdown">
                    <span class="icon -ap  icon-dots-three-vertical"></span>
                </a>
            </div>
        </div>
        <div class="page-content">
            <div class="box-form padt8 padb5">
                <div class="pull-right">
                    <div class="inline">
                        <div class="info-row marb0">
                            <div class="key w80">
                              <span class="name">Kỳ hóa đơn</span>
                            </div>
                            <div class="value">
                              <input type="text" class="form-control"
                                    v-model="kyHoaDonDate"
                                    :disabled="true">
                            </div>
                        </div>
                    </div>
                    <div class="inline marl10">
                        <div class="info-row marb0">
                            <vue-select
                                v-model="cboChuKy"
                                :labelWidth="'50'"
                                :options="getDsChuKyNo"
                                :valueField="'chuky'"
                                :labelField="'chuky'"
                                label="Chu kỳ"
                                :checkbox="false"
                                :disable="true"
                            ></vue-select>
                        </div>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="row">
                <div class="col-sm-4 col-12">
                    <div class="box-form">
                        <div class="legend-title">Thông tin tra cứu</div>
                        <div class="info-row">
                            <vue-select
                                v-model="selectedType"
                                :required="false"
                                :labelWidth="'90'"
                                :disable="false"
                                :options="typeList"
                                :valueField="'id'"
                                :labelField="'text'"
                                label="Loại DS"
                                :checkbox="false"
                                v-on:input="onSelectedType"
                            ></vue-select>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Số máy/Acc</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="onClickSearchAccount">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" class="form-control highlight"
                                        :class="{ 'error-input' : isErrorSubcriberCode }"
                                        ref="subcriberCode"
                                        v-model="subcriberCode"
                                        v-on:keyup="onInputSubcriberCode">
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Tên TB</div>
                            <div class="value">
                                <input type="text" class="form-control"
                                    v-model="subcriberName">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <vue-date
                                    ref="fromDate"
                                    v-model="fromDate"
                                    :labelWidth="'90'"
                                    :required="false"
                                    label="Ngày TH"
                                    :checkbox="true"
                                    ></vue-date>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <vue-date
                                    ref="toDate"
                                    v-model="toDate"
                                    :labelWidth="'90'"
                                    :required="false"
                                    label="Đến ngày"
                                    :checkbox="true"
                                    ></vue-date>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">
                              <span class="name">Lý do</span>
                              <span class="required">*</span>
                            </div>
                            <div class="value">
                                <input type="text" class="form-control"
                                    :class="{ 'error-input' : isErrorReason }"
                                    ref="reason"
                                    v-model="reason">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">
                              <span class="name">Nội dung</span>
                              <span class="required">*</span>
                            </div>
                            <div class="value">
                                <textarea name="" class="form-control" style="height:80px;resize:none;" id="" cols="30" rows="10"
                                    :class="{ 'error-input' : isErrorNote }"
                                    ref="note"
                                    v-model="note"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-8 col-12">
                    <div class="box-form">
                        <div class="legend-title">Kết quả tìm kiếm</div>
                        <DataGrid ref="findSubcribersTable"
                            :columns="findSubcribersTable.columns"
                            :dataSource="findSubcribersTable.dataSource"
                            :enable-paging-server="false"
                            :allowPaging="true"
                            :loading="findSubcribersTable.isLoading"
                            :showFilter="true"
                            :showColumnCheckbox="true"
                            :enabledSelectFirstRow="false"
                            @rowClicked="recordChangeSubcriberFindSubcribersTable"
                            @selectedItemsChanged="selectedItemsChangedFindSubcribersTable"
                        ></DataGrid>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">
                    <div class="title ">{{text}}</div>
                </div>
                <DataGrid
                    ref="inforSubcribers"
                    :columns="infoTable.columns"
                    :dataSource="infoTable.dataSource"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :loading="infoTable.isLoading"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    @rowClicked="recordChangeSubcriberInfoTable"
                    @selectedItemsChanged="selectedItemsChangedInfoTable"
                ></DataGrid>
            </div>
        </div>
      <b-modal id="popup-search-account" size="xl" hide-footer hide-header>
        <div style="min-height: 800px;">
          <SearchAccount :isPopup="true" v-on:form-close="onSelectAccount"/>
        </div>
      </b-modal>
      <LayDuLieuBC ref="popupLayDuLieuBC"
      :data="infoTable.dataSource"></LayDuLieuBC>
      <DocFileThem ref="popupDocFileThem"
        :input="inputDocFileThem"
        :acceptChange="readFileAcceptChange"></DocFileThem>
    </div>
</template>

<script>
import api from './api/index'
import axios from 'axios'
import moment from 'moment'
import { mapActions, mapGetters } from 'vuex'
import gridView from '@/components/Shared/gridview.vue'
import SearchAccount from '../../../search/subscriber/SearchAccount'

const DS_KHONG_XULY = {
  THUEBAO_KHONGCHE: 0,
  THUEBAO_KHONG_KHOA: 1,
  THUEBAO_KHONG_NHACNO: 2,
  THUEBAO_KHONG_HUY: 3,
  THUEBAO_KHONG_NHANHONG: 5,
  THUEBAO_KHONG_OB_LM: 6,
  THUEBAO_KHONG_OB_BH: 7,
  THONGBAO_SUCO_TTVT: 12
}

export default {
  components: {
    gridView,
    SearchAccount,
    'LayDuLieuBC': () => import('../../../contract/profile/ExportData/ExportDataModal.vue'),
    'DocFileThem': () => import('../../../QLTN/components/PopupDocFile/PopupDocFileDSKhoaMo.vue')
  },
  data () {
    return {
      tag: null,
      kyHoaDonDate: '08/2014',
      cboChuKy: null,
      subcriberCode: '',
      subcriberName: '',
      reason: '',
      note: '',
      isEnableReadFile: true,
      isEnableAddNew: false,
      isEnableSave: true,
      isEnableDelete: false,
      isEnableCancel: true,
      selectedType: '0',
      typeList: [],
      fromDate: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
      toDate: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
      findSubcribersTable: {
        dataSource: [],
        isLoading: true,
        perpage: 10,
        columns: [{
          fieldName: 'ma_tb',
          headerText: 'Số máy/Acc'
        }, {
          fieldName: 'trangthai_tb',
          headerText: 'Trạng thái'
        }, {
          fieldName: 'ten_dvvt',
          headerText: 'Dịch vụ'
        }, {
          fieldName: 'loaihinh_tb',
          headerText: 'Loại hình'
        }, {
          fieldName: 'ten_tb',
          headerText: 'Tên thuê bao'
        }, {
          fieldName: 'thangno',
          headerText: 'Tháng nợ'
        }, {
          fieldName: 'tongno',
          headerText: 'Tổng nợ'
        }]
      },
      infoTable: {
        dataSource: [],
        isLoading: true,
        perpage: 10,
        columns: [{
          fieldName: 'ma_tb',
          headerText: 'Số máy/Acc'
        }, {
          fieldName: 'trangthai',
          headerText: 'Trạng thái'
        }, {
          fieldName: 'ten_dvvt',
          headerText: 'Dịch vụ'
        }, {
          fieldName: 'loaihinh_tb',
          headerText: 'Loại hình'
        }, {
          fieldName: 'ten_tb',
          headerText: 'Tên thuê bao'
        }, {
          fieldName: 'lydo',
          headerText: 'Lý do'
        }, {
          fieldName: 'ten_dvql',
          headerText: 'Đơn vị QL'
        }, {
          fieldName: 'nguoi_cn',
          headerText: 'Người CN'
        }, {
          fieldName: 'ten_nhanvien',
          headerText: 'Nhân viên'
        }, {
          fieldName: 'ten_dvvt',
          headerText: 'Đơn vị'
        }, {
          fieldName: 'ngay_th',
          headerText: 'Ngày thực hiện'
        }, {
          fieldName: 'ngay_th_den',
          headerText: 'Ngày TH đến'
        }]
      },
      selectedInfoTableRow: {},
      kiemTraQuyen: false,
      inputDocFileThem: {
        loaiDSId: '',
        lyDo: '',
        tuNgay: '',
        denNgay_Checked: false,
        denNgay: '',
        kyHoaDon: ''
      },
      selectedInforSubcribers: [],
      selectedGridSubcribers: [],
      text: '',
      isErrorSubcriberCode: false,
      isErrorReason: false,
      isErrorNote: false,
      thuebao_id: 0,
      thanhtoan_id: 0
    }
  },
  props: ['tagFrm'],
  mounted: async function () {
    this.$root.showLoading(true)
    try {
      this.tag = this.$router.history.current.query.tag

      this.$refs.fromDate.checkbox_value = true

      const resKyHoaDon = await api.getKyHoaDon(axios)
      if (resKyHoaDon.data.data && resKyHoaDon.data.data.result) {
        this.kyHoaDonDate = moment(resKyHoaDon.data.data.result, 'YYYYMMDD').format('MM/YYYY')
      }

      const resDsChuKyNo = await api.getDsChuKyNo(axios, {
        namthang: this.kyHoaDonDate ? moment(this.kyHoaDonDate, 'MM/YYYY').format('YYYYMM') : ''
      })

      if (resDsChuKyNo.data.data) {
        this.setChuKyNo(resDsChuKyNo.data.data.ckn ? resDsChuKyNo.data.data.ckn : [])
      }

      const resThamSoMd = await api.getThamSoMd(axios, {
        kieu_id: 0
      })

      if (resThamSoMd.data.data) {
        this.setDsThamSoMd(resThamSoMd.data.data)

        if (resThamSoMd.data.data.length > 0) {
          const str_vtemp = 'DOCFILE_DSTB_KHONGNHANHONG'
          const firstRow = resThamSoMd.data.data.find(i => i.ma_ts === str_vtemp)
          if (firstRow && firstRow.ten_ts === '1') {
            this.kiemTraQuyen = true
          }
        }
      }

      const dsloaiid = this.tag ? this.tag : ''
      if (dsloaiid === DS_KHONG_XULY.THUEBAO_KHONG_NHANHONG.toString()) {
        this.typeList.push({ id: DS_KHONG_XULY.THUEBAO_KHONG_NHANHONG.toString(), text: 'Thuê bao không nhận hỏng' })
        // label29.Text = "DANH SÁCH THUÊ BAO KHÔNG NHẬN HỎNG"
        this.text = "Danh sách thuê bao không nhận hỏng"
        this.isEnableReadFile = true
        // tsbtnDocFileXoaTBDB.Visible = false
        // label5.Text = "Nội dung"

        // An hien thong tin cap
        // gridColumn21.Visible = true
        // gridColumn21.VisibleIndex = 13
        // gridColumn22.Visible = true
        // gridColumn22.VisibleIndex = 14
        // gridColumn23.Visible = true
        // gridColumn23.VisibleIndex = 15
        // gridColumn24.Visible = true
        // gridColumn24.VisibleIndex = 16
        // gridColumn25.Visible = true
        // gridColumn25.VisibleIndex = 17
        // gridColumn26.Visible = true
        // gridColumn26.VisibleIndex = 18
      } else if (dsloaiid.indexOf(";") > -1 &&
            (dsloaiid.Split(';').indexOf(DS_KHONG_XULY.THUEBAO_KHONG_OB_LM.toString()) ||
            dsloaiid.Split(';').indexOf(DS_KHONG_XULY.THUEBAO_KHONG_OB_BH.toString()))) {
        this.typeList.push({ id: DS_KHONG_XULY.THUEBAO_KHONG_OB_LM.toString(), text: 'Thuê bao không khảo sát Lắp mới' })
        this.typeList.push({ id: DS_KHONG_XULY.THUEBAO_KHONG_OB_BH.toString(), text: 'Thuê bao không khảo sát Báo hỏng' })
        // label29.Text = "DANH SÁCH THUÊ BAO KHÔNG KHẢO SÁT"
        this.text = "Danh sách thuê bao không khảo sát"
        this.isEnableReadFile = true
        // tsbtnDocFileXoaTBDB.Visible = false
        // label5.Text = "Nội dung"
      } else {
        this.typeList.push({ id: DS_KHONG_XULY.THUEBAO_KHONGCHE.toString(), text: 'Thuê bao khống chế' })
        this.typeList.push({ id: DS_KHONG_XULY.THUEBAO_KHONG_HUY.toString(), text: 'Thuê bao không hủy' })
        if (dsloaiid == "") {
          this.typeList.push({ id: DS_KHONG_XULY.THUEBAO_KHONG_KHOA.toString(), text: 'Thuê bao không khóa' })
          this.typeList.push({ id: DS_KHONG_XULY.THUEBAO_KHONG_NHACNO.toString(), text: 'Thuê bao không nhắc nợ' })
        } else {
          this.isEnableAddNew = false
          this.isEnableSave = false
          this.isEnableReadFile = false
        }
      }

      this.selectedType = this.typeList[0].id

      await this.loadInforSubcriber()
    } catch (error) {
      console.log(error)
      this.$toast.error(error.toString())
    }
    this.$root.showLoading(false)
  },
  computed: {
    ...mapGetters('listSubcribersNotReceiveDamageCommon', [
      'getSubcribers',
      'getDsChuKyNo'
    ])
  },
  watch: {
    getDsChuKyNo: function () {
      if (this.getDsChuKyNo && this.getDsChuKyNo.length > 0)
        this.cboChuKy = this.getDsChuKyNo[0].chuky
    },
    selectedInforSubcribers: function (newList, oldList) {
      // if (!this.newList || this.newList.length === 0) {
      //   this.setButton(1)
      //   this.note = ''
      //   this.subcriberName = ''
      // }
    },
    getSubcribers: function (newList, oldList) {
      if (newList.length > 0) {
        this.recordChangeSubcriberInfoTable(1, newList[0])
      }
    }
  },
  methods: {
    ...mapActions('listSubcribersNotReceiveDamageCommon', [
      'setChuKyNo',
      'setDsThamSoMd',
      'setInforSubcribers',
      'setGridSubcribers',
      'setSubcriberSearched'
    ]),
    loadInforSubcriber: async function () {
      const toDate = moment(this.toDate, 'DD/MM/YYYY HH:mm:ss')
      const fromDate = moment(this.fromDate, 'DD/MM/YYYY HH:mm:ss')
      const resSubcriberInfo = await api.getInforSubcribers(axios, {
        kyhoadon: this.kyHoaDonDate ? moment(this.kyHoaDonDate, 'MM/YYYY').format('DDMMYYYY') : '',
        loaids: this.selectedType,
        ma_tb: this.subcriberCode,
        tungay: fromDate.isValid() ? fromDate.format('DD/MM/YYYY') : moment(new Date()).format('DD/MM/YYYY'),
        denngay: toDate.isValid() ? toDate.format('DD/MM/YYYY') : moment(new Date()).format('DD/MM/YYYY')
      })

      if (resSubcriberInfo.data.data) {
        this.setInforSubcribers(resSubcriberInfo.data.data)
      }
    },
    onSelectedType: function (e) {
      try {
        if (this.selectedType.toString() !== '0') {
          this.setGridSubcribers([])
          this.Clear()
          const dsloaiid = this.tag
          if (dsloaiid.indexOf(";") &&
          (dsloaiid.split(';').includes(DS_KHONG_XULY.THUEBAO_KHONG_OB_LM.toString()) ||
          dsloaiid.split(';').includes(DS_KHONG_XULY.THUEBAO_KHONG_OB_BH.toString()))) { } else {
            if (!this.cboChuKy) {
              this.$toast.error(`Kỳ hóa đơn : ${this.kyHoaDonDate}  không có chu kỳ tính cước ! Bạn hãy xem lại`)
              return
            }
          }

          this.onClickLoadData()
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
    },
    recordChangeSubcriberInfoTable: async function (i, e) {
      try {
        if (!e) { return }

        this.selectedInfoTableRow = e
        await this.searchSubcribers(this.selectedInfoTableRow.thanhtoan_id)

        this.subcriberCode = e.ma_tb
        this.subcriberName = e.ten_tb
        this.reason = e.lydo
        this.note = e.ghichu
        this.fromDate = e.ngay_th
        this.toDate = moment(e.ngay_cn, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')

        // this.$refs.subcriberCode.focus()
        this.setButton(3)
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
    },
    selectedItemsChangedInfoTable: function (e) {
      this.selectedInforSubcribers = e
    },
    recordChangeSubcriberFindSubcribersTable: function (i, e) {
    },
    selectedItemsChangedFindSubcribersTable: function (e) {
      this.selectedGridSubcribers = e
    },
    searchSubcribers: async function (thanhtoan_id) {
      const resGridSubcribers = await api.getGridSubcribers(axios, {
        kyhoadon: this.kyHoaDonDate ? moment(this.kyHoaDonDate, 'MM/YYYY').format('DDMMYYYY') : '',
        thanhtoan_id: thanhtoan_id || '0',
        loaids_id: this.selectedType
      })

      if (resGridSubcribers.data.data) {
        this.setGridSubcribers(resGridSubcribers.data.data)
        this.findSubcribersTable.dataSource = resGridSubcribers.data.data
      }
    },
    onInputSubcriberCode: async function (e) {
      if (e.keyCode === 13) {
        this.loading(true)
        try {
          if (this.subcriberCode.trim() !== '') {
            if (!this.cboChuKy) {
              const errorText = `Kỳ hóa đơn : ${this.kyHoaDonDate} không có chu kỳ tính cước ! Bạn hãy xem lại`
              throw errorText
            }

            const toDate = moment(this.toDate, 'DD/MM/YYYY HH:mm:ss')
            const fromDate = moment(this.fromDate, 'DD/MM/YYYY HH:mm:ss')
            const resSubcriberInfo = await api.getInforSubcribers(axios, {
              kyhoadon: this.kyHoaDonDate ? this.cboChuKy + moment(this.kyHoaDonDate, 'MM/YYYY').format('MMYYYY') : '',
              loaids: this.selectedType,
              ma_tb: this.subcriberCode,
              tungay: fromDate.isValid() ? fromDate.format('DD/MM/YYYY') : moment(new Date()).format('DD/MM/YYYY'),
              denngay: toDate.isValid() ? toDate.format('DD/MM/YYYY') : moment(new Date()).format('DD/MM/YYYY')
            })

            if (resSubcriberInfo.data.data) {
              this.infoTable.dataSource = resSubcriberInfo.data.data
              if (this.infoTable.dataSource.length > 0) {
                const errorText = `Thuê bao ${this.subcriberCode} đã tồn tại trong danh sách`
                this.$toast.error(errorText)
              } else {
                this.thuebao_id = 0
                this.thanhtoan_id = 0
                this.note = ''
                this.subcriberName = ''
              }
            }

            await this.HienThiThongTin(this.subcriberCode.trim())
          }
        } catch (error) {
          console.log(error)
          this.$toast.error(error.toString())
        }
        this.loading(false)
      }
    },
    HienThiThongTin: async function (ma_tb) {
      const resSubcriber = await api.getSubcriberByCode(axios, {
        ma_tb: ma_tb
      })
      this.HienThi_TT(resSubcriber.data.data || [])
      await this.Lay_TB_No(this.thanhtoan_id)
    },
    HienThi_TT: function (ds) {
      if (ds.length > 0) {
        if (ds.length === 1) {
          this.thuebao_id = ds[0].thuebao_id
          this.thanhtoan_id = ds[0].thanhtoan_id
          this.subcriberName = ds[0].ten_tb
        } else {

        }
      } else {
        this.thuebao_id = 0
        this.thanhtoan_id = 0
        this.subcriberName = ''
      }
    },
    Lay_TB_No: async function (thanhtoan_id) {
      const resGridSubcribers = await api.getGridSubcribers(axios, {
        kyhoadon: this.kyHoaDonDate ? this.cboChuKy + moment(this.kyHoaDonDate, 'MM/YYYY').format('MMYYYY') : '',
        thanhtoan_id: thanhtoan_id || '0',
        loaids_id: this.selectedType
      })
      console.log(resGridSubcribers.data.data)
      console.log('resGridSubcribers.data.data')
      if (resGridSubcribers.data.data) {
        this.findSubcribersTable.dataSource = resGridSubcribers.data.data

        if (this.findSubcribersTable.dataSource.length === 0) {
          this.$toast.error(`Không tìm thấy thông tin!`)
        }
      }

      let timeout = setTimeout(() => {
        for (let i = 0; i < this.findSubcribersTable.dataSource.length; i++) {
          if (this.findSubcribersTable.dataSource[i].selected !== '1') {
            this.$refs.findSubcribersTable.grid.selectRow(i)
          }
        }

        clearTimeout(timeout)
      }, 1000)
    },
    onClickLoadData: async function () {
      this.loading(true)
      try {
        // if (this.subcriberCode.trim() !== '') {
        //   if (!this.cboChuKy) {
        //     const errorText = `Kỳ hóa đơn : ${this.kyHoaDonDate} không có chu kỳ tính cước ! Bạn hãy xem lại`
        //     throw errorText
        //   }

        //   await this.HienThiThongTin(this.subcriberCode.trim())

        //   this.infoTable.dataSource = []
        //   for (let i = 0; i < this.findSubcribersTable.dataSource.length; i++) {
        //     const toDate = this.$refs.toDate.checkbox_value ? moment(this.toDate, 'DD/MM/YYYY HH:mm:ss') : moment(new Date())
        //     const fromDate = this.$refs.fromDate.checkbox_value ? moment(this.fromDate, 'DD/MM/YYYY HH:mm:ss') : moment(new Date())
        //     const resSubcriberInfo = await api.getInforSubcribers(axios, {
        //       kyhoadon: this.kyHoaDonDate ? this.cboChuKy + moment(this.kyHoaDonDate, 'MM/YYYY').format('MMYYYY') : '',
        //       loaids: this.selectedType,
        //       ma_tb: this.findSubcribersTable.dataSource[i].ma_tb,
        //       tungay: fromDate.isValid() ? fromDate.format('DD/MM/YYYY') : moment(new Date()).format('DD/MM/YYYY'),
        //       denngay: toDate.isValid() ? toDate.format('DD/MM/YYYY') : moment(new Date()).format('DD/MM/YYYY')
        //     })

        //     if (resSubcriberInfo.data.data) {
        //       this.infoTable.dataSource.push(...resSubcriberInfo.data.data)
        //     }
        //   }

        //   if (this.infoTable.dataSource.length > 0) {
        //     const errorText = `Thuê bao ${this.subcriberCode} đã tồn tại trong danh sách`
        //     this.$toast.error(errorText)
        //   } else {
        //     this.thuebao_id = 0
        //     this.thanhtoan_id = 0
        //     this.note = ''
        //     this.subcriberName = ''
        //   }
        // }

        this.infoTable.dataSource = []
        const toDate = this.$refs.toDate.checkbox_value ? moment(this.toDate, 'DD/MM/YYYY HH:mm:ss') : moment(new Date())
        const fromDate = this.$refs.fromDate.checkbox_value ? moment(this.fromDate, 'DD/MM/YYYY HH:mm:ss') : moment(new Date())
        const resSubcriberInfo = await api.getInforSubcribers(axios, {
          kyhoadon: this.kyHoaDonDate ? this.cboChuKy + moment(this.kyHoaDonDate, 'MM/YYYY').format('MMYYYY') : '',
          loaids: this.selectedType,
          ma_tb: '',//this.subcriberCode, tuanlv commented 04/04/2023
          tungay: fromDate.isValid() ? fromDate.format('DD/MM/YYYY') : moment(new Date()).format('DD/MM/YYYY'),
          denngay: toDate.isValid() ? toDate.format('DD/MM/YYYY') : moment(new Date()).format('DD/MM/YYYY')
        })

        if (resSubcriberInfo.data.data) {
          this.infoTable.dataSource = resSubcriberInfo.data.data
        }
        this.loading(false)  


      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
        this.loading(false)
      }
      this.loading(false)
    },
    onClickAddNew: function () {
      this.setButton(1)
    },
    onClickCancel: function () {
      this.Clear()
    },
    onClickSave: async function () {
      try {
        const isConfirm = await this.$bvModal
          .msgBoxConfirm(
            `Bạn chắc chắn muốn cập nhật dữ liệu ?`,
            {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
              centered: true
            }
          )
        this.$root.showLoading(true)
        if (isConfirm && this.isValidateDataBeforeSave()) {
          const json_ds_khongxuly = []
          console.log(moment(this.fromDate,'DD/MM/YYYY HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'),)
          console.log('this.fromDate')
          if (this.isEnableAddNew) {
            // Cập nhật
            for (let i = 0; i < this.selectedInforSubcribers.length; i++) {
              const selectedRow = this.selectedInforSubcribers[i]
              json_ds_khongxuly.push(
                {
                  PHANVUNG_ID: this.$root.token.getPhanVungID(),
                  RDS_ID: selectedRow.rds_id,
                  THUEBAO_ID: selectedRow.thuebao_id,
                  THANHTOAN_ID: selectedRow.thanhtoan_id,
                  DICHVUVT_ID: selectedRow.dichvuvt_id,
                  LOAITB_ID: selectedRow.loaitb_id,
                  NGAY_TH: this.fromDate,
                  LOAIDS: this.selectedType,
                  LYDO: this.reason,
                  GHICHU: this.note,
                  NHANVIEN_ID: this.$root.token.getNhanVienID(),
                  NGAY_CN: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                  MAY_CN: 'don',
                  NGUOI_CN: this.$auth.getUserName(),
                  IP_CN: null,
                  NGAY_TH_DEN: this.$refs.toDate.checkbox_value ? this.toDate : null,
                  MA_TB: selectedRow.ma_tb}
              )
            }

            const resSave = await api.saveData(axios, {
              json_ds_khongxuly: JSON.stringify(json_ds_khongxuly)
            })

            if (resSave.data.message_detail) {
              this.$toast.error(resSave.data.message_detail.toString())
            } else {
              this.$toast.success('Cập nhật dữ liệu viễn thông tỉnh thành công!')
            }
          } else {
            // Thêm mới
            // const selectedGridSubcribers = this.$refs.gridSubcribers.fullDataList.filter(i => i.ischecked)
            for (let i = 0; i < this.selectedGridSubcribers.length; i++) {
              const selectedRow = this.selectedGridSubcribers[i]
              const resGetKey = await api.getKey(axios, { keyname: 'RDS_ID' })
              json_ds_khongxuly.push(
                {
                  PHANVUNG_ID: this.$root.token.getPhanVungID(),
                  RDS_ID: resGetKey.data.data,
                  THUEBAO_ID: selectedRow.thuebao_id,
                  THANHTOAN_ID: selectedRow.thanhtoan_id,
                  DICHVUVT_ID: selectedRow.dichvuvt_id,
                  LOAITB_ID: selectedRow.loaitb_id,
                  NGAY_TH: this.fromDate,
                  LOAIDS: this.selectedType,
                  LYDO: this.reason,
                  GHICHU: this.note,
                  NHANVIEN_ID: this.$root.token.getNhanVienID(),
                  NGAY_CN: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
                  MAY_CN: 'localhost',
                  NGUOI_CN: this.$auth.getUserName(),
                  IP_CN: null,
                  NGAY_TH_DEN: this.$refs.toDate.checkbox_value ? this.toDate : null,
                  MA_TB: selectedRow.ma_tb}
              )
            }

            const resInsert = await api.insertData(axios, {
              json_ds_khongxuly: JSON.stringify(json_ds_khongxuly)
            })

            if (resInsert.data.message_detail) {
              this.$toast.error(resInsert.data.message_detail.toString())
            } else {
              this.$toast.success('Thêm mới dữ liệu viễn thông tỉnh thành công!')
            }
          }
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)

      // load data again
      this.onClickLoadData()
    },
    isValidateDataBeforeSave: function () {
      if (!this.subcriberCode) {
        this.$toast.error('Không có thông tin thuê bao!')
        this.$refs.subcriberCode.focus()
        this.isErrorSubcriberCode = true
        return false
      } else this.isErrorSubcriberCode = false

      if (!this.reason || this.reason=='') {
        this.$toast.error('Hãy nhập lý do')
        this.$refs.reason.focus()
        this.isErrorReason = true
        return false
      } else this.isErrorReason = false

      if (this.reason.length > 200) {
        this.$toast.error('Lý do không được vượt quá 200 ký tự!')
        this.$refs.reason.focus()
        this.isErrorReason = true
        return false
      } else this.isErrorReason = false

      if (!this.note || this.note=='') {
        this.$toast.error('Hãy nhập nội dung')
        this.$refs.note.focus()
        this.isErrorNote = true
        return false
      } else this.isErrorNote = false

      if (this.note.length > 4000) {
        this.$toast.error('Nội dung không được vượt quá 4000 ký tự!')
        this.$refs.note.focus()
        this.isErrorNote = true
        return false
      } else this.isErrorNote = false

      if (this.kiemTraQuyen && !this.$refs.toDate.checkbox_value && !this.toDate && this.tag === DS_KHONG_XULY.THUEBAO_KHONG_NHANHONG.ToString()) {
        this.$toast.error('Hãy chọn thông tin đến ngày!')
        this.$refs.toDate.focus()
        return false
      }

      if (Date.parse(this.toDate) < Date.parse(this.fromDate)) {
        this.$toast.error('Đến ngày phải lớn hơn ngày TH !')
        return false
      }

      if (this.isEnableAddNew) {
        // Cập nhật
        if (this.selectedInforSubcribers.length === 0) {
          this.$toast.error('Bạn chưa chọn thuê bao.')
          return false
        }
      } else {
        // Thêm mới
        if (this.selectedType !== DS_KHONG_XULY.THUEBAO_KHONG_OB_LM.toString() &&
            this.selectedType !== DS_KHONG_XULY.THUEBAO_KHONG_OB_BH.toString()) {
          if (this.findSubcribersTable.dataSource.length === 0) {
            this.$toast.error('Chưa có dữ liệu. Xin kiểm tra lại!')
            return false
          }
          if (this.thuebao_id === 0 || this.thanhtoan_id === 0) {
            this.$toast.error('Chưa có dữ liệu. Xin kiểm tra lại!')
            return false
          }
        }

        if (this.selectedType === '0') {
          if (this.selectedGridSubcribers.length === 0) {
            this.$toast.error('Bạn chưa chọn thuê bao.')
            return false
          }

          const selectedInforSubcribersUnable = this.selectedGridSubcribers.filter(i => i.trangthaitb_id === '1')
          if (selectedInforSubcribersUnable.length > 0) {
            this.$toast.error(`Thuê bao ${selectedInforSubcribersUnable[0].ma_tb} đang hoạt động bình thường. Bạn không được phép thêm thuê bao này vào danh sách khống chế.`)
            return false
          }
        }
      }

      return true
    },
    onDelete: async function () {
      try {
        const selectedType = this.typeList.find(i => i.id === this.selectedType)
        const isConfirm = await this.$bvModal
          .msgBoxConfirm(
            `Bạn muốn xóa thuê bao này khỏi danh sách ${selectedType.text} không?`,
            {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
              centered: true
            }
          )
        this.$root.showLoading(true)
        if (isConfirm && this.isValidBeforeDelete()) {
          const rds_idData = []
          this.selectedInforSubcribers.map(i => i.rds_id).forEach(id => { rds_idData.push({ RDS_ID: id }) })
          const bodyData = {
            rds_id: JSON.stringify(rds_idData),
            may_cn: "a",
            nguoi_cn: this.$auth.getUserName(),
            ip_cn: "a",
            json_dsthuebao: JSON.stringify(this.selectedInforSubcribers.map(i => i.thuebao_id))
          }

          const resDelete = await api.deleteSubcriber(axios, bodyData)
          if (resDelete.data.message_detail) {
            this.$toast.error(resDelete.data.message_detail.toString())
          } else {
            this.$toast.success('Xóa dữ liệu trên viễn thông tỉnh thành công!')
          }
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)

      // load data again
      this.onClickLoadData()
    },
    isValidBeforeDelete: function () {
      if (this.selectedInforSubcribers.length === 0) {
        this.$toast.error('Bạn chưa chọn thuê bao.')
        return false
      }

      return true
    },
    setButton: function (kieu) {
      this.isEnableAddNew = false
      this.isEnableSave = false
      this.isEnableDelete = false
      this.isEnableCancel = false
      if (kieu === -1) {
        // Bat dau
        this.isEnableSave = true
        this.isEnableCancel = true
      } else if (kieu === 0) {
        // Bat dau
        this.isEnableAddNew = true
        // Clear();
      } else if (kieu === 1) {
        // Them moi
        this.isEnableSave = true
        this.isEnableCancel = true
        this.Clear()
      } else if (kieu === 2) {
        // Huy
        this.isEnableAddNew = true
        this.isEnableDelete = true
      } else if (kieu === 3) {
        // Edit
        this.isEnableAddNew = true
        this.isEnableDelete = true
        this.isEnableSave = true
        this.isEnableCancel = true
      }
    },
    Clear: function () {
      this.subcriberCode = ""
      this.subcriberName = ""
      this.note = ""
      this.reason = ""
      this.setGridSubcribers([])
    },
    onClickSearchAccount: function () {
      this.$bvModal.show('popup-search-account')
    },
    onSelectAccount: async function (e) {
      this.$bvModal.hide('popup-search-account')
      console.log('onSelectAccount', e)
      this.subcriberCode = e.ma_tb
      await this.onInputSubcriberCode({keyCode: 13})
    },
    exportFile: function () {
      this.$refs.popupLayDuLieuBC.showModal()
    },
    readFile: function () {
      this.inputDocFileThem.loaiDSId = this.selectedType
      this.inputDocFileThem.lyDo = this.reason
      this.inputDocFileThem.kyHoaDon = moment('01/' + this.kyHoaDonDate, 'DD/MM/YYYY').format('YYYYMMDD')
      this.inputDocFileThem.tuNgay = moment(this.fromDate,'DD/MM/YYYY HH:mm:ss').format('YYYY/MM/DD HH:mm:ss')
      this.inputDocFileThem.denNgay = moment(this.toDate,'DD/MM/YYYY HH:mm:ss').format('YYYY/MM/DD HH:mm:ss')
      this.inputDocFileThem.denNgay_Checked = this.$refs.toDate.checkbox_value

      if (this.inputDocFileThem.loaiDSId === DS_KHONG_XULY.THUEBAO_KHONG_NHANHONG.toString()) {
        if (!this.reason || !this.reason.trim()) {
          this.$toast.error('Hãy nhập lý do')
          return
        }
      }

      if (this.kiemTraQuyen && !this.$refs.toDate.checkbox_value) {
        this.$toast.error('Hãy chọn thông tin đến ngày!')
        return
      }

      this.$refs.popupDocFileThem.openDialog()
    },
    readFileAcceptChange: function (e) {
      if (e) {

      }
    }
  }
}
</script>

<style scoped>
/* >>>.modal-dialog {
  height: 100%;
}
>>>.modal-content {
  height: 100%;
} */
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
a:hover{
  cursor: pointer;
}

.error-input {
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
