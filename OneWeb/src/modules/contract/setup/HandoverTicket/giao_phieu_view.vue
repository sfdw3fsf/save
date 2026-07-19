<template>
  <div :class="{ 'modal-content popup-box': type == 'modal' }">
    <breadcrumb v-if="type == 'view'" :header="header" />
    <div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="btnTimKiem" :class="{ disabled: !tsbtnTimKiem }"> <span class="icon one-search"></span>Tìm kiếm </a>
          </li>
          <li>
            <a href="#" @click.prevent="btnGiaoPhieu" :class="{ disabled: !tsbtnGiaoPhieu }"> <span class="icon one-file-arrow-right1"></span>Giao phiếu </a>
          </li>
          <li>
            <a href="#" @click.prevent="btnHuyGiao" :class="{ disabled: !tsbtnHuyGiao }"> <span class="icon one-cancel"></span>Hủy giao </a>
          </li>
          <li>
            <a href="#" @click.prevent="btnCapNhatDV" :class="{ disabled: !tsbtnCapNhatDV }"> <span class="icon one-reload1"></span>Cập nhật ĐV </a>
          </li>
          <li>
            <a href="#" @click.prevent="btnXuatFile"> <span class="icon one-download"></span>Xuất file </a>
          </li>
        </ul>
      </div>
      <!-- class="page-content"  -->
      <div :class="{ 'page-content': type == 'view', 'form-content': type == 'modal' }">
        <div class="box-form">
          <div class="legend-title">Thông tin tìm kiếm</div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w100">Mã giao dịch</div>
                <div class="value">
                  <div class="input-more-button">
                    <button class="btn" @click="showPopupSearchContract">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <SearchContractModal ref="popupSearchContract" @accept="acceptSearchContract" />
                    <input type="text" class="form-control " v-model="maGDTK.value" v-on:keyup.enter="onMaGDTKEnterred" />
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w80">Ngày giao</div>
                <div class="value" width="100%">
                  <div class="input-icon-right" width="100%">
                    <vue-date :disabled="!ngayGiaoTK.enabled" v-model="ngayGiaoTK.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">Trạng thái phiếu</div>
                <div class="value">
                  <SelectExt :disabled="!trangThai.enabled" v-model="trangThai.value" :dataSource="trangThai.list" dataTextField="text" dataValueField="id" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w100">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="loaiHD.enabled" />
                    <span class="name">Loại HĐ</span>
                  </div>
                </div>
                <div class="value">
                  <SelectExt :disabled="!loaiHD.enabled" v-model="loaiHD.value" :dataSource="loaiHD.list" dataTextField="text" dataValueField="id"></SelectExt>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w80">
                  <div class="check-action">
                    <!-- :disabled="!dichVu.enabled" -->
                    <input type="checkbox" class="check" v-model="dichVu.enabled" />
                    <span class="name">Dịch vụ</span>
                  </div>
                </div>
                <div class="value">
                  <SelectExt :disabled="!dichVu.enabled" v-model="dichVu.value" :dataSource="dichVu.list" dataTextField="text" dataValueField="id"></SelectExt>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="quyTrinh.enabled" :disabled="!dichVu.enabled || !loaiHD.enabled" />
                    <span class="name">Quy trình:</span>
                  </div>
                </div>
                <div class="value">
                  <SelectExt :disabled="!quyTrinh.enabled" v-model="quyTrinh.value" :dataSource="filtedQuyTrinhList" dataTextField="quytrinh" dataValueField="quytrinh_id"></SelectExt>
                </div>
              </div>
            </div>
          </div>
          <div class="legend-title mart20">Thông tin giao phiếu</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Hướng giao</div>
                <div class="value">
                  <SelectExt :disabled="!huongGiao.enabled" v-model="huongGiao.value" :dataSource="huongGiao.list" dataTextField="text" dataValueField="id"></SelectExt>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key">Mã giao dịch</div>
                <div class="value">
                  <input :disabled="!maGD.enabled" type="text" class="form-control" v-model="maGD.value" readonly="true" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w70">Ngày giao</div>
                <div class="value">
                  <div class="input-icon-right">
                    <vue-date :disabled="!ngayGiao.enabled" v-model="ngayGiao.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w100">Số máy/Acc</div>
                <div class="value">
                  <input :disabled="!maTB.enabled" type="text" class="form-control highlight" v-model="maTB.value" readonly="true" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w80">Số QĐ</div>
                <div class="value">
                  <input :disabled="!soPhieu.enabled" type="text" class="form-control" v-model="soPhieu.value" ref="soPhieu" />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-6">
              <div class="info-row">
                <div class="key">Đơn vị nhận</div>
                <div class="value">
                  <SelectExt :disabled="!donViNhan.enabled" v-model="donViNhan.value" :dataSource="donViNhan.list" dataTextField="text" dataValueField="id" />
                </div>
              </div>
            </div>


            <div class="col-sm-2 col-3">
              <div class="info-row">
                <div class="key">Đơn vị phối hợp</div>
                <div class="select-custom">
                  <!-- <SelectExt :options="listbox.donviphoihop.list" v-model="listbox.donviphoihop.value.donvi_id" ref = "donviphoihop"> 
                      <option disabled value="0">Select one</option> 
                  </SelectExt>  -->

                  <div class="value select-custom">
                      <ejs-multiselect
                        v-model="donviphoihop.value"
                        id="ds_donviphoihop"
                        :dataSource="donviphoihop.list"
                        placeholder="Chọn đơn vị phối hợp"
                        selectAllText="Chọn tất cả"
                        :showSelectAll="true"
                        locale="vi-VN"
                        :showDropDownIcon="true"
                        unSelectAllText="Bỏ chọn tất cả"
                        mode="CheckBox"
                        :fields="{ text: 'ten_dv', value: 'donvi_id' }"
                      >
                      </ejs-multiselect>
                    </div>
              </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Nội dung giao</div>
                <div class="value">
                  <input :disabled="!noiDungGiao.enabled" type="text" class="form-control" v-model="noiDungGiao.value" ref="noiDungGiao" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">Địa chỉ TC</div>
                <div class="value">
                  <input :disabled="!diaChiTC.enabled" type="text" class="form-control" v-model="diaChiTC.value" readonly="true" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="legend-title mart20">
          <div class="pull-left">
            Danh sách phiếu yêu cầu
          </div>
          <div class="pull-right red"></div>
          <div class="clearfix"></div>
        </div>
        <div class="table-content">
          <table class="table-result table-gachle">
            <div>
              <DataGrid
                v-bind:columns="gridPhieuYeuCau.header"
                v-bind:dataSource="gridPhieuYeuCau.list"
                :enable-paging-server="false"
                :allowPaging="true"
                :showFilter="true"
                :showColumnCheckbox="true"
                ref="nvdaGan"
                @selectedRowChanged="(object) => gridPhieuYeuCauOnItemClicked('selectedRowChanged', object)"
                @selectedItemsChanged="selectedItems_click"
                @rowClicked="(index, object) => gridPhieuYeuCauOnItemClicked('rowClicked', object)"

              >
              </DataGrid>
            </div>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import VueFlatPickr from 'vue-flatpickr-component'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import breadcrumb from '@/components/breadcrumb'
import API from './API'
import XLSX from 'xlsx'
import SearchContractModal from './components/SearchContractModal.vue'
import moment from 'moment'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'

export default {
  components: { VueFlatPickr, API, XLSX, SearchContractModal, DatePicker, breadcrumb },
  name: 'GiaoPhieuView',
  emits: ['onGiaoPhieuSuccessListener'],
  props: {
    type: {
      type: String,
      default: 'view'
    },
    vhuonggiao_id: {
      type: [String, Number],
      required: false,
      default: null
    },
    vloai_hd: {
      type: [String, Number],
      required: false,
      default: null
    },
    vdichvuvt_id: {
      type: [String, Number],
      required: false,
      default: null
    },
    vma_gd: {
      type: [String, Number],
      required: false,
      default: null
    },
    vquytrinh: {
      type: [String, Number],
      required: false,
      default: null
    },
    vtrangthaiphieu: {
      type: [String, Number],
      required: false,
      default: null
    }, 
   
  },
  async mounted() {
    await this.init()
  },
  data() {
    return {
      header: {
        title: 'GIAO PHIẾU ĐIỀU HÀNH THI CÔNG',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Giao phiếu thi công',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      maGDTK: {
        enabled: true,
        value: null
      },
      ngayGiaoTK: {
        enabled: true,
        value: null
      },
      trangThai: {
        enabled: true,
        list: [],
        value: null
      },
      loaiHD: {
        enabled: false,
        list: [],
        value: 0
      },
      dichVu: {
        enabled: false,
        list: [],
        value: 0
      },
      quyTrinh: {
        enabled: false,
        list: [],
        value: 0
      },
      huongGiao: {
        enabled: true,
        list: [],
        value: null
      },
      maGD: {
        enabled: true,
        value: null
      },
      ngayGiao: {
        enabled: true,
        value: null
      },
      maTB: {
        enabled: true,
        value: null
      },
      soPhieu: {
        enabled: true,
        value: null
      },
      donViNhan: {
        enabled: true,
        list: [],
        value: null
      },
      noiDungGiao: {
        enabled: true,
        value: null
      },
      diaChiTC: {
        enabled: true,
        list: [],
        value: null
      },
      SelectedRow:[],
      check_tsl:false,
      hg_id: '',
      tsbtnHuyGiao: true,
      tsbtnCapNhatDV: true,
      tsbtnTimKiem: true,
      tsbtnGiaoPhieu: true,
      ctmsGiaoPhieu: true,
      ctmsHuyPhieu: true,
      isloaded: false,
      gridPhieuYeuCau: {
        header: [   
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 150}, 
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, width: 350 },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, allowHtml: true, width: 400 },
          { fieldName: 'ttyc', headerText: 'Thông tin yêu cầu', allowFiltering: true, allowHtml: true, width: 300 },
          { fieldName: 'sophieu', headerText: 'Số QĐ', allowFiltering: true, allowHtml: true, width: 150 },
          { fieldName: 'donvinhan', headerText: 'Đơn vị nhận', allowFiltering: true, allowHtml: true },
          { fieldName: '', headerText: 'Nội dung trả', allowFiltering: true, allowHtml: true }
        ],
        list: [],
        checked: []
      },
      gridThamSo: {
        list: []
      },
      success_code: 'BSS-00000000',
      NHANTIN_NHANVIEN_PHOIHOP: -1,
      SEND_SMS_NHANTIN_NV: -1,
      NHANTIN_THEO_HG: -1,
      ts_kt_tthdkhdn: 0, // Kiểm tra cho phép thêm thông tin khách hàng DN  đối với PTTB KHDN mới xài
      kt_phieutra_gdv: false,
      _pSendSMS: false,
      _Giaoviec_td: false,
      _Giaoviec_nvkd: false,
      DV_BTSL: 9, // Mã dịch vụ truyền số liệu
      loaidv_nhan_id: '-1',
      donvi_id: '',
      nhanvien_id: '',
      ma_nd: '',
      may_cn: '',
      huonggiao_id: '',
      kt_daucuoi: '',
      _Giaoviec_td: false, // Giao việc tự động cho nhân viên kỹ thuật
      _Giaoviec_nvkd: false, // Giao việc tự động cho nhân viên kinh doanh
      DIEUHANH_THICONG: 1,
      HUONG_LAPMOI_CD: 202,
      HUONG_LAPMOI_FTTH: 206,
      HUONG_LAPMOI_MYTV: 207,
      DICHVU: 1, // 1:CĐ, 4: BRCD, 10:MYTV
      donviphoihop: {
        list: []
      }
    }
  },
  watch: {
    async 'loaiHD.value'(val) {
      await this.onLoaiHDChanged()
      await this.LoadQuyTrinh()
    },
    async 'loaiHD.enabled'(val) {
      if (!val) {
        this.loaiHD.value = null
        this.quyTrinh.enabled = false
      }
    },
    async 'dichVu.value'(val) {
      await this.onDichVuChanged()
      await this.LoadQuyTrinh()
    },
    async 'dichVu.enabled'(val) {
      if (!val) {
        this.dichVu.value = null
        this.quyTrinh.enabled = false
      }
    },
    async 'quyTrinh.value'(val) {
      await this.onQuyTrinhChanged()
    },
    async 'quyTrinh.enabled'(val) {
      if (!val) {
        this.quyTrinh.value = null
      }
    },
    async 'huongGiao.value'(val) {
      await this.onHuongGiaoChanged()
    },
    async 'ngayGiaoTK.value'(val) {
      await this.onNgayGiaoChanged()
    },
    async 'trangThai.value'(val) {
      await this.onTrangThaiChanged()
    }
  },
  computed: {
    filtedQuyTrinhList() {
      let list = this.quyTrinh.list.filter((e) => (this.loaiHD.value != 0 ? this.loaiHD.value == e.loaihd_id : true) && (this.dichVu.value != 0 ? this.dichVu.value == e.dichvuvt_id : true))
      return list
    }
  },
  methods: {
    async init() {
      this.ngayGiaoTK.value = this.$root.token.getNgayCapNhat()
      this.ngayGiao.value = this.$root.token.getNgayCapNhat()
      setTimeout(() => {
        this.trangThai.list = [
          { id: 1, text: 'Chưa giao' },
          { id: 2, text: 'Đã giao' }
        ]
      }, 1000)
      if (this.type == 'view') {
        this.initView()
      }
      if (this.type == 'modal') {
        this.initModal()
      }
    },
    async initView() {
      if (this.$route.query.vhuonggiao_id && this.$route.query.vhuonggiao_id != '') {
        this.huongGiao.value = this.$route.query.vhuonggiao_id
      }else if (this.$route.query.huonggiao && this.$route.query.huonggiao != '') {
        this.huongGiao.value = this.$route.query.huonggiao
      }
      await this.LoadCombobox()
      this.trangThai.value = 1
      if (this.$route.query.vtrangthaiphieu) {
        this.trangThai.value = this.$route.query.vtrangthaiphieu
      }
      if (this.$route.query.vloai_hd && this.$route.query.vloai_hd != '') {
        this.loaiHD.enabled = true
        this.loaiHD.value = this.$route.query.vloai_hd
      }
      this.DICHVU = 0
      if (this.$route.query.vdichvuvt_id && this.$route.query.vdichvuvt_id != '') {
        this.dichVu.enabled = true
        this.DICHVU = this.$route.query.vdichvuvt_id
        this.dichVu.value = this.$route.query.vdichvuvt_id
      }
      await this.LoadQuyTrinh()
      if (this.$route.query.vquytrinh && this.$route.query.vquytrinh != '') {
        this.quyTrinh.enabled = true
        this.quyTrinh.value = this.$route.query.vquytrinh
      }
      await this.LoadHuongGiao()

      
      if (this.$route.query.vma_gd) {
        this.maGDTK.value = this.$route.query.vma_gd
      }
      if (this.pkieu != '') {
        this.kieu_id = this.pkieu
      }
      if (this.maGDTK.value != '') {
        await this.onMaGDTKEnterred()
      }
      this.isloaded = true
    },
    async initModal() {
      var data = await API.list_all_quytrinh(this.axios, {})
      if (data.data.data && data.data.data.length > 0) {
        this.quyTrinh.list = data.data.data.map((x) => ({ id: x.quytrinh_id, text: x.quytrinh }))
      }
      await this.LoadCombobox()
      this.trangThai.value = 1
      if (this.vtrangthaiphieu) {
        this.trangThai.value = this.vtrangthaiphieu
      }
      if (this.vloai_hd && this.vloai_hd !== '') {
        this.loaiHD.enabled = true
        this.loaiHD.value = this.vloai_hd
      }
      this.DICHVU = 0
      if (this.vdichvuvt_id != null && this.vdichvuvt_id !== '') {
        this.dichVu.enabled = true
        this.DICHVU = this.vdichvuvt_id
        this.dichVu.value = this.vdichvuvt_id
      }
      if (this.vquytrinh && this.vquytrinh !== '') {
        this.quyTrinh.enabled = true
        this.quyTrinh.value = this.vquytrinh
      }
      await this.LoadHuongGiao()
      if (this.vhuonggiao_id && this.vhuonggiao_id !== '') {
        this.huongGiao.value = this.vhuonggiao_id
      }
      if (this.vma_gd) {
        this.maGDTK.value = this.vma_gd
        if (this.maGDTK.value !== '') {
          await this.onMaGDTKEnterred()
        }
      }
    },
    showPopupSearchContract() {
      this.$refs.popupSearchContract.showModal()
    },
    acceptSearchContract(item) {
      this.maGDTK.value = item.ma_gd
      if (this.maGDTK.value && this.maGDTK.value.trim() != '') this.onMaGDTKEnterred()
    },
    gridPhieuYeuCauOnItemChanged(dataKeys) {
      this.gridPhieuYeuCau.checked = dataKeys
      this.gridPhieuYeuCau.list.forEach(function(item) {
        if (dataKeys.filter((x) => x === item.phieu_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },
    gridPhieuYeuCauOnItemClicked(event, obj) {
      if (!obj) {
        return
      }
      this.SelectedRow=obj
      console.log("Tú kiểm tra obj", obj)
      this.maTB.value = obj.ma_tb
      this.maGD.value = obj.ma_gd
      this.soPhieu.value = obj.sophieu
      this.noiDungGiao.value = obj.nd_tra_con
      this.diaChiTC.value = obj.diachi_ld
      this.donViNhan.value = obj.donvinhan_id
      if (obj.dichvuvt_id == this.DV_BTSL || obj.dichvuvt_id == 25) {
        if (obj.danhdau == '3') {
          this.kt_daucuoi = 1
        } else {
          this.kt_daucuoi = 0
        }
      } else {
        this.kt_daucuoi = 1
      }
    },
    async LoadCombobox() {
      var data = await API.lay_ds_thamso_md_mats(this.axios, {})
      if (data.data.data && data.data.data.length > 0) {
        this.gridThamSo.list = data.data.data
        if (this.gridThamSo.list.length > 0) {
          var temp = this.gridThamSo.list.filter((x) => x.ma_ts === 'NHANTIN_NHANVIEN_PHOIHOP')
          if (temp.length > 0) {
            if (temp[0]['ten_ts'].toString() == '1') {
              this.NHANTIN_NHANVIEN_PHOIHOP = 1
            }
          }
          var temp1 = this.gridThamSo.list.filter((x) => x.ma_ts === 'THEM_TT_HDKHDN')
          if (temp1.length > 0) {
            if (temp1[0]['ten_ts'].toString() == '1') {
              this.ts_kt_tthdkhdn = 1
            }
          }
          var temp2 = this.gridThamSo.list.filter((x) => x.ma_ts === 'KT_PHIEUTRA_GDV')
          if (temp2.length > 0) {
            if (temp2[0]['ten_ts'].toString() == '1') {
              this.kt_phieutra_gdv = true
            }
          }
          var temp3 = this.gridThamSo.list.filter((x) => x.ma_ts === 'SEND_SMS_NHANTIN_NV')
          if (temp3.length > 0) {
            if (temp3[0]['ten_ts'].toString() == '1') {
              this.SEND_SMS_NHANTIN_NV = 1
            }
          }
          var temp4 = this.gridThamSo.list.filter((x) => x.ma_ts === 'NHANTIN_THEO_HG')
          if (temp4.length > 0) {
            if (temp4[0]['ten_ts'].toString() == '1') {
              this.NHANTIN_THEO_HG = 1
            }
          }
        }
      } else {
        this.$toast.error('Không tìm thấy danh sách dịch tham số dịch vụ')
      }
      try {
        var loadHDData = await API.CSS_LOAI_HD(this.axios, {})
        if (loadHDData.data.data && loadHDData.data.data.length > 0) {
          this.loaiHD.list = loadHDData.data.data.map((x) => ({ id: x.LOAIHD_ID, text: x.TEN_LOAIHD }))
        } else {
          this.loaiHD.list = []
        }
      } catch (e) {
        this.loading(false)
      }
      try {
        var dichVuData = await API.CSS_DICHVU_VT(this.axios, {})
        if (dichVuData.data.data && dichVuData.data.data.length > 0) {
          this.dichVu.list = dichVuData.data.data.map((x) => ({ id: x.DICHVUVT_ID, text: x.TEN_DVVT }))
        } else {
          this.dichVu.list = []
        }
      } catch (e) {
        this.loading(false)
      }
      this.HienThi_DanhSach()
    },
    async LoadQuyTrinh() {
      this.loading(true)
      try {
        var input = {}
        var data = await API.list_all_quytrinh(this.axios, input)
        if (data.data.data && data.data.data.length > 0) {
          this.quyTrinh.list = data.data.data
          if (this.quyTrinh.value == null) {
            this.quyTrinh.value = this.quyTrinh.list[0].quytrinh_id
          }
        } else {
          this.quyTrinh.list = []
        }
      } catch (e) {
        this.$toast.error('Lỗi lấy thông tin quy trình!')
      }
      this.loading(false)
    },
    async LoadHuongGiao() {
      this.loading(true)
      try {
        var input = {}
        input.nguoidung_id = this.$root.token.getNguoiDungID()
        input.quytrinh_id = this.quyTrinh.enabled ? this.quyTrinh.value : 0
        input.loaihd_id = this.loaiHD.enabled ? this.loaiHD.value : 0
        input.dichvuvt_id = this.dichVu.enabled ? this.dichVu.value : 0
        input.nhom_qt_id = this.DIEUHANH_THICONG
        if (this.loaiHD.enabled) {
          input.loaihd_id = this.loaiHD.value == null ? 0 : this.loaiHD.value
        }
        if (this.dichVu.enabled) {
          input.dichvuvt_id = this.dichVu.value == null ? 0 : this.dichVu.value
        }
        if (this.quyTrinh.enabled) {
          input.nhom_qt_id = this.quyTrinh.nhom_qt_id == null ? 1 : this.quyTrinh.nhom_qt_id
          input.quytrinh_id = this.quyTrinh.value == null ? 0 : this.quyTrinh.value
        }
        var data = await API.sp_lay_ds_huonggiao(this.axios, input)
        if (data.data.data && data.data.data.length > 0) {
          this.huongGiao.list = data.data.data.map((x) => ({ id: x.huonggiao_id, text: x.huonggiao }))
          // if(this.huong)
          if (this.huongGiao.value == null) this.huongGiao.value = this.huongGiao.list[0].id
          if (this.isloaded) await this.onHuongGiaoChanged()
        } else {
          this.huongGiao.list = []
        }
      } catch (e) {
        this.$toast.error(`Lỗi lấy danh sách hướng giao - line653 - ${e}`)
      }
      this.loading(false)
    },
    async HienThi_DanhSach() {
      try {
        this.gridPhieuYeuCau.checked = []
        if (this.trangThai.value == 1) {
          this.tsbtnHuyGiao = false
          this.tsbtnTimKiem = true
          this.tsbtnGiaoPhieu = true
          this.ctmsGiaoPhieu = true
          this.ctmsHuyPhieu = false
          this.tsbtnCapNhatDV = true
        } else if (this.trangThai.value == 2) {
          var KHONG_HUY_GIAOPHIEU = -1
          var data = await API.lay_ds_thamso_md_mats(this.axios, {})
          if (data.data.data && data.data.data.length > 0) {
            this.gridThamSo.list = data.data.data
            if (this.gridThamSo.list.length > 0) {
              var temp = this.gridThamSo.list.filter((x) => x.ma_ts === 'KHONG_HUY_GIAOPHIEU')
              if (temp.length > 0) {
                KHONG_HUY_GIAOPHIEU = parseInt(temp[0]['ten_ts'])
              }
            }
          } else {
            this.gridThamSo.list = []
            this.$toast.error(`${data.data.message}`)
          }
          if (KHONG_HUY_GIAOPHIEU == 1) this.tsbtnHuyGiao = false
          else this.tsbtnHuyGiao = true
          this.tsbtnTimKiem = false
          this.tsbtnGiaoPhieu = false
          this.ctmsGiaoPhieu = false
          this.ctmsHuyPhieu = true
          this.tsbtnCapNhatDV = false
        }
        if (this.trangThai.value == 1) {
          try {
            let input = {}
            input.dichvuvt_id = this.dichVu.enabled ? this.dichVu.value : 0
            input.loaihd_id = this.loaiHD.enabled ? this.loaiHD.value : 0
            input.huonggiao_id = this.huongGiao.value == null ? 0 : this.huongGiao.value
            input.donvi_id = this.$root.token.getDonViID()
            input.ma_gd = (!this.maGDTK.value || this.maGDTK.value == '') ? '0' : this.maGDTK.value
            input.nhanvien_id = this.$root.token.getNhanVienID()
            this.loading(true)
            var data = await API.sp_lay_ds_hdtb_chuagiao(this.axios, input)
            this.loading(false)
            if (data.data.error_code && data.data.error_code === this.success_code) {
              this.gridPhieuYeuCau.list = data.data.data
              if (this.gridPhieuYeuCau.list.length == 0) {
                this.maTB.value = ''
                this.maGD.value = ''
                this.soPhieu.value = ''
                this.noiDungGiao.value = ''
                this.diaChiTC.value = ''
              }
              this.gridPhieuYeuCau.header = [
                { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 150 },
                { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, width: 350 },
                { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, allowHtml: true, width: 400 },
                { fieldName: 'ttyc', headerText: 'Thông tin yêu cầu', allowFiltering: true, allowHtml: true, width: 300 },
                { fieldName: 'sophieu', headerText: 'Số QĐ', allowFiltering: true, allowHtml: true, width: 150 },
                { fieldName: 'donvinhan', headerText: 'Đơn vị nhận', allowFiltering: true, allowHtml: true },
                { fieldName: 'nd_tra_con', headerText: 'Nội dung trả', allowFiltering: true, allowHtml: true }
              ]
            } else {
              this.gridPhieuYeuCau.list = []
              this.maTB.value = ''
              this.maGD.value = ''
              this.soPhieu.value = ''
              this.noiDungGiao.value = ''
              this.diaChiTC.value = ''
            }
          } catch (e) {
            this.$toast.error(`Có lỗi lấy danh sách phiếu chưa giao - line757 - ${e}`)
            this.loading(false)
          }
        } else if (this.trangThai.value == 2) {
          try {
            let input = {}
            input.dichvuvt_id = this.dichVu.enabled ? this.dichVu.value : 0
            input.loaihd_id = this.loaiHD.enabled ? this.loaiHD.value : 0
            input.huonggiao_id = this.huongGiao.value == null ? 0 : this.huongGiao.value
            input.donvi_id = this.$root.token.getDonViID()
            input.loaipt_id = 0
            input.ngay_giao = this.ngayGiaoTK.value == null ? 0 : this.ngayGiaoTK.value
            input.ma_gd = (!this.maGDTK.value || this.maGDTK.value == '') ? '0' : this.maGDTK.value
            input.nhanvien_id = this.$root.token.getNhanVienID()
            this.loading(true)
            var data = await API.lay_ds_hdtb_dagiao(this.axios, input)
            this.loading(false)
            if (data.data.error_code && data.data.error_code === this.success_code) {
              this.gridPhieuYeuCau.list = data.data.data
              if (this.gridPhieuYeuCau.list.length == 0) {
                this.maTB.value = ''
                this.maGD.value = ''
                this.soPhieu.value = ''
                this.noiDungGiao.value = ''
                this.diaChiTC.value = ''
              }
              this.gridPhieuYeuCau.header = [                
                { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 150 },
                { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, width: 350 },
                { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, allowHtml: true, width: 400 },
                { fieldName: 'ttyc', headerText: 'Thông tin yêu cầu', allowFiltering: true, allowHtml: true, width: 300 },
                { fieldName: 'sophieu', headerText: 'Số QĐ', allowFiltering: true, allowHtml: true, width: 150 },
                { fieldName: 'donvinhan', headerText: 'Đơn vị nhận', allowFiltering: true, allowHtml: true },
                { fieldName: 'nd_giao', headerText: 'Nội dung giao', allowFiltering: true, allowHtml: true }
              ]
            } else {
              this.gridPhieuYeuCau.list = []
              this.maTB.value = ''
              this.maGD.value = ''
              this.soPhieu.value = ''
              this.noiDungGiao.value = ''
              this.diaChiTC.value = ''
            }
          } catch (e) {
            this.$toast.error(`Có lỗi lấy danh sách phiếu đã giao - line805 - ${e}`)
            this.loading(false)
          }
        }
      } catch (e) {
        this.$toast.error(`${e}`)
      }
    },
    GetDSDonViNhan() {
      try {
        var input = {}
        input.huonggiao_id = this.huongGiao.value
        API.fn_lay_loaidv_dich_theo_hg(this.axios, input).then((response) => {
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.loaidv_nhan_id = response.data.data
            if (this.loaidv_nhan_id != '-1') {
              var input1 = {}
              input1.huonggiao_id = this.huongGiao.value
              input1.loaidv_id = this.loaidv_nhan_id
              API.sp_ht_tatca_vetinh_combobox_v2(this.axios, input1).then((response) => {
                if (response.data.error_code && response.data.error_code === this.success_code) {
                  this.donViNhan.list = response.data.data.map((x) => ({ id: x.donvi_id, text: x.ten_dv }))
                } else {
                  this.donViNhan.list = []
                  // if (this.isloaded) {
                  //   this.$toast.error('Có lỗi lấy danh sách đơn vị nhận !')
                  // }
                }
              })
            }
          } else {
            this.donViNhan.list = []
          }
        })
      } catch (e) {
        this.$toast.error(`Lỗi lấy danh sách loại hợp đồng - line837 - ${e}`)
        this.loading(false)
      }
    },
    btnTimKiem() {
      this.HienThi_DanhSach()
    },
    async btnGiaoPhieu() {
      this.handlerGiaoPhieuSuccess()
      if (this.huongGiao.value == null) {
        this.$toast.error('Bạn chưa chọn hướng giao!')
        return
      }
      // if (this.noiDungGiao.value == '' || this.noiDungGiao.value == null) {
      //   this.$toast.error('Bạn chưa nhập nội dung giao!')
      //   this.$refs.noiDungGiao.focus()
      //   return
      // }
      var listphieuchuyen = []
      let outResponse = {}
      console.log('btnGiaoPhieu', this.gridPhieuYeuCau.checked)
      if (this.gridPhieuYeuCau.checked.length > 0) {
        listphieuchuyen = this.gridPhieuYeuCau.checked//this.gridPhieuYeuCau.checked
        listphieuchuyen = listphieuchuyen.map(function(item) {
          for (var key in item) {
            var upper = key.toUpperCase()
            if (upper !== key) {
              item[upper] = item[key]
              delete item[key]
            }
          }
          return item
        })
      } else {
        this.$toast.error('Bạn chưa chọn phiếu để chuyển')
        return
      }
      // Tú thêm check KTR
      if (this.check_tsl){
        this.$toast.error('KTR cần giao đủ 02 phiếu, vui lòng kiểm tra lại')
        return
      }
      // hết thêm
      console.log('btnGiaoPhieu listphieuchuyen', listphieuchuyen)
      try {
        this.donvi_id = this.$root.token.getDonViID()
        this.nhanvien_id = this.$root.token.getNhanVienID()
        this.ma_nd = this.$root.token.getUserName()
        this.may_cn = ''
        this.huonggiao_id = this.huongGiao.value

        var input = {}
        input.json_dsphieu = JSON.stringify(listphieuchuyen)
        input.donvi_id = this.donvi_id
        input.nhanvien_id = this.nhanvien_id
        input.ngay_cn = moment(await this.$root.token.getNgayCapNhat(), 'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
        input.nguoi_cn = this.ma_nd
        input.may_cn = this.may_cn
        input.ip_cn = await this.$root.token.getIP()
        input.huonggiao_id = this.huonggiao_id
        input.sophieu = this.soPhieu.value == null ? '' : this.soPhieu.value
        input.noidung = this.noiDungGiao.value
        input.kt_daucuoi = this.kt_daucuoi
        input.taoduyet = 0
        input.nt_phoihop = this.NHANTIN_NHANVIEN_PHOIHOP
        input.nt_nhanvien = this.SEND_SMS_NHANTIN_NV
        input.nt_huonggiao = this.NHANTIN_THEO_HG
        this.loading(true)
        var data = await API.fn_giaophieu(this.axios, input)
        this.loading(false)
        if (data.data.error_code && data.data.error_code === this.success_code) {
          this.$toast.success('Giao phiếu thành công!')
          this.HienThi_DanhSach()
        } else {
          this.$toast.error(`giao_phieu_view - line887 - ${data.data.message} - ${data.data.message_detail}`)
        }
      } catch (e) {
        this.$toast.error(`giao_phieu_view - line890 - ${e}`)
        this.loading(false)
      }
    },
    btnHuyGiao() {
      if (this.huongGiao.value == null) {
        this.$toast.error('Bạn chưa chọn hướng trả')
        return
      }
      var listphieuchuyen = []
      if (this.gridPhieuYeuCau.checked.length > 0) {
        listphieuchuyen = this.gridPhieuYeuCau.checked
        listphieuchuyen = listphieuchuyen.map(function(item) {
          for (var key in item) {
            var upper = key.toUpperCase()
            if (upper !== key) {
              item[upper] = item[key]
              delete item[key]
            }
          }
          return item
        })
      }
      var temp = []
      temp = listphieuchuyen.map((x) => ({ CHON: 1, PHIEU_ID: x.PHIEU_ID, HDTB_ID: x.HDTB_ID, MA_TB: x.MA_TB, TTPH_ID: x.TTPH_ID, LOAIPT_ID: x.LOAIPT_ID, STATUS: x.STATUS, PHIEU_CHA_ID: x.PHIEU_CHA_ID }))
      try {
        var input = {}
        input.json_ds_phieu = JSON.stringify(temp)
        input.huonggiao_id = this.huongGiao.value
        this.loading(true)
        API.fn_huyphieu(this.axios, input).then((response) => {
          this.loading(false)
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.$toast.success(`${response.data.data}`)
            this.HienThi_DanhSach()
          } else {
            this.$toast.error(`Trả phiếu không thành công ${response.data.data}`)
          }
        })
      } catch (e) {
        this.$toast.error('Lỗi trả phiếu không thành công')
        this.loading(false)
      }
    },
    async btnCapNhatDV() {
      if (this.donViNhan.value == null || this.donViNhan.value == '') {
        this.$toast.error('Bạn chưa chọn đơn vị nhận')
        return
      }
      var listphieuchuyen = []
      if (this.gridPhieuYeuCau.checked.length > 0) {
        listphieuchuyen = this.gridPhieuYeuCau.checked
        listphieuchuyen = listphieuchuyen.map(function(item) {
          for (var key in item) {
            var upper = key.toUpperCase()
            if (upper !== key) {
              item[upper] = item[key]
              delete item[key]
            }
          }
          return item
        })
      } else {
        this.$toast.error('Bạn chưa chọn phiếu để cập nhật đơn vị nhận')
        return
      }
      var input = {}
      input.huonggiao_id = this.huongGiao.value
      API.fn_lay_loaidv_dich_theo_hg(this.axios, input).then((response) => {
        if (response.data.error_code && response.data.error_code === this.success_code) {
          this.loaidv_nhan_id = response.data.data
        }
      })
      for (var item of listphieuchuyen) {
        if (this.loaidv_nhan_id != '-1') {
          try {
            var input1 = {}
            input1.hdtb_id = item['HDTB_ID']
            input1.donvi_id = this.donViNhan.value
            input1.loaidv_id = this.loaidv_nhan_id
            if (this.dichVu.value != this.DV_BTSL && this.dichVu.value != 25) {
              input1.kieudv_id = 2
            } else {
              input1.kieudv_id = 1
            }
            this.loading(true)
            await API.capnhat_donvinhan(this.axios, input1).then((response) => {
              this.loading(false)
              if (response.data.error_code && response.data.error_code === this.success_code) {
                this.$toast.success('Cập nhật đơn vị nhận thành công!')
              } else {
                this.$toast.error('Cập nhật đơn vị nhận không thành công!')
              }
            })
          } catch (e) {
            this.$toast.error('Lỗi cập nhật đơn vị nhận không thành công!')
            this.loading(false)
          }
        }
      }
      this.HienThi_DanhSach()
    },
    btnXuatFile() {
      if (this.gridPhieuYeuCau.list != null && this.gridPhieuYeuCau.list.length > 0) {
        let data = XLSX.utils.json_to_sheet(this.gridPhieuYeuCau.list)
        let wb = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(wb, data, 'DsPorts')
        XLSX.writeFile(wb, 'ds_phieuyeucau.xlsx')
      } else this.$toast.error('Không có dữ liệu!')
    },
    async onMaGDTKEnterred() {
      await this.HienThi_DanhSach()
    },
    async onLoaiHDChanged() {
      await this.LoadHuongGiao()
    },
    async onDichVuChanged() {
      await this.LoadHuongGiao()
    },
    async onQuyTrinhChanged() {
      await this.LoadHuongGiao()
    },
    async onHuongGiaoChanged() {
      await this.GetDSDonViNhan()
      await this.HienThi_DanhSach()
    },
    async onNgayGiaoChanged() {
      await this.HienThi_DanhSach()
    },
    async onTrangThaiChanged() {
      await this.HienThi_DanhSach()
    },
    async handlerGiaoPhieuSuccess() {
      this.$emit('onGiaoPhieuSuccessListener', { success: true })
    },        
    selectedItems_click: function (dataKeys) {
      // Tú thêm check KTR
      console.log('selectedItems 35', dataKeys)
      var item_row = this.SelectedRow
      let isItemInList = dataKeys.findIndex(element => {
        return element.chon === item_row.chon && 
               element.diachi_ld === item_row.diachi_ld &&
               element.diachi_tb === item_row.diachi_tb &&
               element.dichvuvt_id === item_row.dichvuvt_id &&
               element.donvinhan === item_row.donvinhan &&
               element.donvinhan_id === item_row.donvinhan_id &&
               element.hdtb_id === item_row.hdtb_id &&
               element.ma_gd === item_row.ma_gd &&
               element.ma_tb === item_row.ma_tb &&
               element.nd_giao === item_row.nd_giao &&
               element.nd_tra_con === item_row.nd_tra_con &&
               element.ngaygiao === item_row.ngaygiao &&
               element.phieu_id === item_row.phieu_id &&
               element.sophieu === item_row.sophieu &&
               element.ten_tb === item_row.ten_tb &&
               element.ten_ttyc === item_row.ten_ttyc &&
               element.tthd_id === item_row.tthd_id &&
               element.ttph_id === item_row.ttph_id &&
               element.ttyc === item_row.ttyc  
      }) !== -1;
      var ds_data = this.gridPhieuYeuCau.list
      if (isItemInList){
        console.log("đã chọn cái này ", item_row)
        if (item_row.dichvuvt_id == 9){
          console.log("đây là dịch vụ 9")
          var check_tsl = ds_data.filter(item => item.ma_tb == item_row.ma_tb)
          if (check_tsl.length>=2){
            var check_tsl_selection = dataKeys.filter(item => item.ma_tb == item_row.ma_tb)
            if(check_tsl_selection.length<2){
              this.$toast.success('Với dịch vụ KTR cần giao đủ 02 phiếu, vui lòng chọn đủ')
              this.check_tsl=true
            }else {
              this.check_tsl=false
            }
          }else {
              this.check_tsl=false
            }
        }else {
          this.check_tsl=false
        }
      }else {
        console.log("đã bỏ chọn cái này ", item_row)
        this.check_tsl=false
      }
      // hết thêm
      let checked = dataKeys && dataKeys.length > 0 ? dataKeys.map(e => ({...e, ischecked: true, chon: 1})) : []
      this.gridPhieuYeuCau.checked = checked
      console.log('selectedItems_click', checked)
    },
  }
}
</script>
<style>
a.disabled {
  color: lightgray !important;
}
a.disabled:hover {
  color: lightgray !important;
}
.disabled {
  pointer-events: none;
}
.btn {
  padding: 5px 10px;
  border-radius: 4px;
}
.form-content {
  padding: 10px;
}
.e-headerchkcelldiv .e-checkbox-wrapper,
.e-headerchkcelldiv .e-checkselectall {
  display: block !important;
}
</style>
