<template>
  <div>
    <b-modal
      ref="popupInPhieuChi"
      hide-header
      hide-header-close
      size="inphieuchi-size"
      hide-footer
      body-class="p-0"
      no-close-on-backdrop
      @show="onShowModal"
      @hide="onHideModal"
    >
      <div class="modal-content popup-box">
        <div class="popup-header">
          <div class="title">
            <span class="icon one-notepad"></span> In phiếu chi
          </div>
          <div class="close -ap icon-close" @click="hide"></div>
        </div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li v-for="item in actions" :key="item.id" @click="onActionClick(item)"
              :class="{disabled: !item.active}" v-show="item.visible" >
            <a style="cursor: pointer;" :class="{disabled_color: !item.active}" :ref="item.id" >
              <span class="icon" :class="item.icon_class"></span> {{ item.name }}
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body mart10">
        <div class="box-form">
          <div class="row">
            <div class="col-sm-5 col-12">
              <div class="legend-title">Thông tin tìm kiếm</div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w90">Mã giao dịch</div>
                    <div class="value">
                      <div class="input-more-button">
                        <button class="btn" @click="btnLayMaHD_Click">
                          <span class="-ap icon-more_horiz"></span>
                        </button>
                        <SearchContractModal ref="popupSearchContract" :loai_hd_id="modelSearchContract.loai_hd_id"
                                             :dichvu_id="modelSearchContract.dichvu_id"
                                             :trangthai_hd="modelSearchContract.trangthai_hd"
                                             :ngay_yc="modelSearchContract.ngay_yc" @accept="acceptSearchContract"/>
                        <input type="text" ref="txtMaGD" v-model="formProps.txtMaGD" class="form-control highlight" v-on:keyup.enter="txtMaGD_KeyUp">
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Ngày thoái trả</div>
                    <div class="value">
                      <div class="input-icon-right">
                        <k-date-picker ref="dtpNgayThoaiTra" :disabled="true" format="DD/MM/YYYY" type="date"
                                       v-model="formProps.dtpNgayThoaiTra"/>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w90">Từ ngày</div>
                    <div class="value">
                      <div class="input-icon-right">
                        <k-date-picker ref="dtpNgayBD" format="DD/MM/YYYY" type="date"
                                       v-model="formProps.dtpNgayBD"/>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Đến ngày</div>
                    <div class="value">
                      <div class="input-icon-right">
                        <k-date-picker ref="dtpNgayKT" format="DD/MM/YYYY" type="date"
                                       v-model="formProps.dtpNgayKT"/>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-7 col-12">
              <div class="legend-title">Thông tin hoá đơn</div>
              <div class="row">
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key w110">
                      <div class="check-action">
                        <input type="checkbox" ref="chkLoaiHD"
                               v-model="chkLoaiHD.checked" class="check" @change="chkLoaiHD_CheckedChanged">
                        <span class="name">Loại hoá đơn</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="select-custom">
                        <Select2 ref="cboLoaiHD" :disabled='!chkLoaiHD.checked' v-model="cboLoaiHD.value"
                                 :options="cboLoaiHD.list" :settings="settingSelect2"/>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key w65">Thu ngân</div>
                    <div class="value">
                      <div class="select-custom w-100">
                        <Select2 ref="cboThuNgan" class="select2-thungan" :disabled='!chkLoaiHD.checked' v-model="cboThuNgan.value"
                                 :options="cboThuNgan.list" :settings="settingSelect2"/>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-4 col-12">
                  <div class="info-row">
                    <div class="key w110">
                      Seri
                    </div>
                    <div class="value">
                      <div class="select-custom">
                        <Select2 ref="cboSeri" v-model="cboSeri.value" :disabled='!chkLoaiHD.checked'
                                 :options="cboSeri.list" :settings="settingSelect2"/>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-3 col-12">
                  <div class="info-row">
                    <div class="key w50 nowrap">
                      Hoá đơn
                    </div>
                    <div class="value">
                      <input type="text" ref="txtHoaDon" :disabled="!chkLoaiHD.checked" class="form-control" v-model="formProps.txtHoaDon">
                    </div>
                  </div>
                </div>
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key w65">Loại tiền</div>
                    <div class="value w80">
                      <div class="select-custom">
                        <Select2 ref="cboLoaiTien" v-model="cboLoaiTien.value" :disabled="!chkLoaiHD.checked"
                                 :options="cboLoaiTien.list" :settings="settingSelect2"/>
                      </div>
                    </div>
                    <div class="key w45 padl5 nowrap">Tỷ giá</div>
                    <div class="value">
                      <input type="text" txtTyGia class="form-control" :disabled="!chkLoaiHD.checked" v-model="formProps.txtTyGia" style="text-align: right">
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="legend-title">Danh sách phiếu thoái trả</div>
          <div class="table-content">
            <DataGrid ref="gridviewPhieuTra" class="table-result table-gachle" v-bind:columns="gridviewPhieuTra.headers"
                      v-bind:dataSource="gridviewPhieuTra.data" :showColumnCheckbox="false"
                      :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                      :selectionSettings="gridviewPhieuTra.selectionOptions"
                      @rowClicked="gridviewPhieuTra_FocusedRowChanged" @selectedRowChanged="gridviewPhieuTra_FocusedRowChanged"
                      :enabled-select-first-row="false"
            ></DataGrid>
          </div>
        </div>
      </div>
    </b-modal>
    <XemBaoCao ref="XemBaoCao" :ma_bc="ma_bc" :ds-gia-tri="dsGiaTri"></XemBaoCao>
    <PrintReport ref="printReport" :data="this.printData" :type="1"/>
    <SelectPrintType ref="refChoosePrintType"  @selectPrintType="selectPrintPhieuChi" />
  </div>
</template>

<script>
import moment from 'moment'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
// import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import API from './API'
import {TrangThaiHD} from '../../../utils/Enum'
import LoaiHopDong from '../../../const/enums/LoaiHopDong'
import XemBaoCao from '../XemBaoCao/XemBaoCao'
import PrintReport from './PrintReport'
import SelectPrintType from "./SelectPrintType"

export default {
  name: 'InPhieuChi',
  components: {
    KDatePicker,
    SearchContractModal,
    XemBaoCao,
    PrintReport,
    SelectPrintType
  },
  data () {
    return {
      animationSettings: {
        effect: 'Zoom'
      },
      position: {X: 'center', Y: 'top'},
      targetTop: '#app',
      settingSelect2: {
        language: 'vi'
      },
      dsGiaTri: [],
      ma_bc: '',
      actions: [
        {
          id: 'btnTK',
          name: 'Tìm kiếm',
          active: true,
          icon_class: 'one-search',
          visible: true
        },
        {
          id: 'tsbtnInHD_Kim',
          name: 'Mẫu in kim',
          active: true,
          icon_class: 'one-print',
          visible: true
        },
        {
          id: 'tsbtnInHD_Laser',
          name: 'Mẫu in thường',
          active: true,
          icon_class: 'one-print',
          visible: true
        },
        {
          id: 'tsbtnInPhieu',
          name: 'Phiếu chi',
          active: true,
          icon_class: 'one-print',
          visible: false
        }
      ],
      formProps: {
        txtMaGD: '',
        dtpNgayBD: this.formatDate(new Date(), null),
        dtpNgayThoaiTra: this.formatDate(new Date(), null),
        dtpNgayKT: this.formatDate(new Date(), null),
        txtTyGia: 1,
        txtHoaDon: ''
      },
      chkLoaiHD: {
        checked: true
      },
      cboLoaiHD: {
        list: [],
        disable: false,
        value: 0
      },
      cboThuNgan: {
        list: [],
        disable: false,
        value: 0
      },
      cboSeri: {
        list: [],
        disable: false,
        value: 0
      },
      cboLoaiTien: {
        list: [],
        disable: false,
        value: 0
      },
      gridviewPhieuTra: {
        data: [],
        headers: [
          {
            fieldName: 'ma_gd',
            headerText: 'Mã giao dịch',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ten_kh',
            headerText: 'Tên khách hàng',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'diachi_kh',
            headerText: 'Địa chỉ',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ht_tra',
            headerText: 'Hình thức trả',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'tongtien',
            headerText: 'Tổng tiền',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            type: 'Number',
            format: 'N0'
          },
          {
            fieldName: 'ngay_tt',
            headerText: 'Ngày thoái',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          }
        ],
        selected: 0,
        selectItem: null,
        checked: [],
        selectionOptions: {
          checkboxMode: 'ResetOnRowClick',
          enableToggle: true
        }
      },
      modelSearchContract: {
        loai_hd_id: 0,
        dichvu_id: 0,
        ngay_yc: '',
        ngay_kt: '',
        trangthai_hd: TrangThaiHD.MOI
      },
      hdkh_id: 0,
      kt_load: true,
      nhanvienId: Number.parseInt(this.$root.token.getNhanVienID()),
      phanvungId: Number.parseInt(this.$root.token.getPhanVungID()),
      donviId: Number.parseInt(this.$root.token.getDonViID()),
      vpage: 0,
      printData: []
    }
  },
  watch: {
    'cboLoaiHD.value': function (newValue) {
      this.HienThi_SoHD()
    },
    'cboSeri.value': function (newValue) {
      this.HienThi_SoHD()
    }
  },
  methods: {
    async onShowModal () {
      try {
        this.loading(true)
        await this.HT_LoaiTien_Combobox()
        await this.HT_Loai_HoaDon_Combobox()
        await this.HT_Seri_Combobox()
        this.kt_load = false
        await this.HienThi_SoHD()
        await this.HT_ThuNgan_Combobox()
        await this.HienThiDanhSachPhieuTra()
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    onHideModal () {
    },
    showModal () {
      this.$refs.popupInPhieuChi.show()
    },
    hide () {
      this.$refs.popupInPhieuChi.hide()
    },
    async HT_LoaiTien_Combobox () {
      let dsLoaiTien = this.GetDataList(await API.sp_ht_loaitien(this.axios))
      if (dsLoaiTien.length > 0) {
        this.cboLoaiTien.list = dsLoaiTien.map(item => {
          return {
            id: item.loaitien_id,
            text: item.ma_loaitien
          }
        })
        this.cboLoaiTien.value = this.cboLoaiTien.list[0].id
      } else {
        this.cboLoaiTien.list = []
        this.cboLoaiTien.value = 0
      }
    },
    async HT_Loai_HoaDon_Combobox () {
      let dsLoaiHD = this.GetDataList(await API.sp_ht_loai_hoadon(this.axios, {
        kieu: 1
      }))
      if (dsLoaiHD.length > 0) {
        this.cboLoaiHD.list = dsLoaiHD.map(item => {
          return {
            id: item.loaihoadon_id,
            text: item.tentat
          }
        })
        this.cboLoaiHD.value = this.cboLoaiHD.list[0].id
      } else {
        this.cboLoaiHD.list = []
        this.cboLoaiHD.value = 0
      }
    },
    async HT_Seri_Combobox () {
      let dsSeri = this.GetDataList(await API.sp_ht_seri(this.axios, {
        kieu: 1
      }))
      if (dsSeri.length > 0) {
        this.cboSeri.list = dsSeri.map(item => {
          return {
            id: item.seri_id,
            text: item.seri
          }
        })
        this.cboSeri.value = this.cboSeri.list[0].id
      } else {
        this.cboSeri.list = []
        this.cboSeri.value = 0
      }
    },
    async HT_ThuNgan_Combobox () {
      let dsThuNgan = this.GetDataList(await API.sp_hienthi_thungan_phieuchi(this.axios, {
        'nhanvien_id': this.nhanvienId,
        'phanvung_id': this.phanvungId
      }))
      if (dsThuNgan.length > 0) {
        this.cboThuNgan.list = dsThuNgan.map(item => {
          return {
            id: item.nhanvien_id,
            text: item.ten_nv
          }
        })
        this.cboThuNgan.value = this.nhanvienId
      } else {
        this.cboThuNgan.list = []
        this.cboThuNgan.value = 0
      }
    },
    async HienThi_SoHD () {
      if (this.kt_load === false) {
        let vloaihdid = this.cboLoaiHD.value
        let vseriid = this.cboSeri.value
        let ds = await this.sp_lay_max_hoadon(vloaihdid, vseriid, this.donviId)
        if (ds.length > 0 && ds[0].soseri) {
          this.formProps.txtHoaDon = Number(ds[0].soseri) + 1
        } else {
          this.formProps.txtHoaDon = ''
        }
      }
    },
    btnLayMaHD_Click () {
      this.modelSearchContract.loai_hd_id = Number(LoaiHopDong.DAT_MOI)
      this.modelSearchContract.trangthai_hd = Number(TrangThaiHD.THOAITRA)
      this.modelSearchContract.dichvu_id = 0
      this.modelSearchContract.ngay_yc = moment(new Date()).format('DD/MM/YYYY')
      this.$refs.popupSearchContract.showModal()
    },
    acceptSearchContract (item) {
      if (item != null) {
        this.formProps.txtMaGD = item.ma_gd
        if (this.formProps.txtMaGD && this.formProps.txtMaGD.trim() !== '') {
          this.txtMaGD_KeyUp()
        }
      }
    },
    gridviewPhieuTra_FocusedRowChanged (item) {
      if (item != null) {
        this.gridviewPhieuTra.selectItem = item
      }
    },
    txtMaGD_KeyUp () {
      this.HienThiDanhSachPhieuTra()
    },
    async HienThiDanhSachPhieuTra () {
      let ds = await this.lay_ds_thoaitra(this.nhanvienId, this.formProps.dtpNgayBD, this.formProps.dtpNgayKT, this.formProps.txtMaGD)
      if (ds.length > 0) {
        this.gridviewPhieuTra.data = ds
      } else {
        this.gridviewPhieuTra.data = []
      }
    },
    async lay_ds_thoaitra (nhanvienid, dtpNgayBD, dtpNgayKT, magd) {
      let req = {
        'ma_gd': magd,
        'ngay_bd': dtpNgayBD,
        'ngay_kt': dtpNgayKT,
        'nhanvien_id': nhanvienid,
        'phanvung_id': this.phanvungId
      }
      return this.GetDataList(await API.lay_ds_thoaitra(this.axios, req))
    },
    async sp_lay_max_hoadon (loaihdid, seriid, donviid) {
      let req = {
        'donvi_id': donviid,
        'loaihoadon_id': loaihdid,
        'phanvung_id': this.phanvungId,
        'seri_id': seriid
      }
      return this.GetDataList(await API.sp_lay_max_hoadon(this.axios, req))
    },
    formatDate (value, format) {
      if (format) {
        return moment(value).format(format)
      } else {
        return moment(value).format('DD/MM/YYYY')
      }
    },
    chkLoaiHD_CheckedChanged () {

    },
    async INPHIEUCHI (hdkhId) {
      let req = {
        in_hdkh_id: hdkhId
      }
      return this.GetDataList(await API.INPHIEUCHI(this.axios, req))
    },
    async LAY_GT_THAMSO_MD_MATS (mats) {
      return this.GetData(await API.lay_gt_thamso_md_mats(this.axios, mats))
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return null
    },
    async selectPrintPhieuChi(type) {
      let nhanvienId = this.$root.token.getNhanVienID()
      this.dsGiaTri.push(this.$root.token.getPhanVungID())
      this.dsGiaTri.push(this.hdkh_id)
      this.dsGiaTri.push('0')
      this.dsGiaTri.push(nhanvienId)
      this.dsGiaTri.push(type)
      this.ma_bc = 'PHIEUCHI_THOAITRA_A5'
      await this.$refs.XemBaoCao.XemNgay()
    },
    async tsbtnInHD_Laser_Click () {
      if (this.gridviewPhieuTra.selectItem == null) {
        this.$root.$toast.warning('Chưa có thông tin phiếu!')
        return
      }
      try {
        this.hdkh_id = Number(this.gridviewPhieuTra.selectItem.hdkh_id)
        let in_bbn_tt = await this.LAY_GT_THAMSO_MD_MATS('IN_BIENNHAN_TT')
        console.log('🚀 ~ file: InPhieuChi.vue ~ line 567 ~ tsbtnInHD_Laser_Click ~ this.in_bbn_tt', in_bbn_tt)
        if (in_bbn_tt == '1') {
          this.$refs.refChoosePrintType.openDialog(this.PhieuTtIdIn)
        } else {
          this.dsGiaTri.push(this.hdkh_id)
          this.ma_bc = 'PHIEUCHI_TTR'
          await this.$refs.XemBaoCao.XemNgay()
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async tsbtnInHD_Kim_Click () {
      this.hdkh_id = Number(this.gridviewPhieuTra.selectItem.hdkh_id)
      let data = await this.INPHIEUCHI(this.hdkh_id)
      if (data.length <= 0) {
        this.$root.$toast.error('Chưa có dữ liệu để in !')
        return
      }
      this.printData = data
      this.$refs.printReport.startPrint(data)
    },
    onActionClick (action) {
      if (action.id === 'btnTK') {
        this.HienThiDanhSachPhieuTra()
      } else if (action.id === 'tsbtnInHD_Laser') {
        this.tsbtnInHD_Laser_Click()
      } else if (action.id === 'tsbtnInHD_Kim') {
        this.tsbtnInHD_Kim_Click()
      }
    }
  }
}
</script>

<style>
.modal .modal-inphieuchi-size {
  max-width: 85% !important;
}
.select2-selection { overflow: hidden; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all;
}
</style>
