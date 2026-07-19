<template>
    <div>
        <breadcrumb />
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a @click="layThongTin">
                        <span class="icon one-file-attach"></span> Lấy TT (F5)
                    </a>
                </li>
                <li :class="{ '-disabled': !isEnableSave }">
                    <a @click="save">
                        <span class="icon one-reload1"></span> Cập nhật (F9)
                    </a>
                </li>
                <!-- <li>
                    <a @click="onGiaoPhieu">
                        <span class="icon one-file-arrow-right1"></span> Giao phiếu
                    </a>
                </li> -->
                <!-- <li :class="{ '-disabled': !isEnableKhoiPhucSo }">
                    <a href="#">
                        <span class="icon nc-icon-glyph arrows-1_refresh-68"></span> Khôi phục số
                    </a>
                </li> -->
                <!-- <li :class="{ '-disabled': !isEnableKichHoat }">
                    <a href="#">
                        <span class="icon one-file-arrow-up"></span> Kích hoạt
                    </a>
                </li> -->
                <!-- <li :class="{ '-disabled': !isEnableHuyKichHoat }">
                    <a href="#">
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy kích hoạt
                    </a>
                </li> -->
                <!-- <li :class="{ '-disabled': !isEnableDauNoi }">
                    <a href="#">
                        <span class="icon one-connect"></span>Đầu nối
                    </a>
                </li> -->
                <!-- <li :class="{ '-disabled': !isEnableXuatPhieu }">
                    <a href="#">
                        <span class="icon one-xlsx-import"></span>Xuất phiếu
                    </a>
                </li> -->
                <li>
                    <a @click="onInBB">
                        <span class="icon one-change"></span>In BB
                    </a>
                </li>
                <li>
                    <a @click="onChuyenTo">
                        <span class="icon one-change"></span>Chuyển tổ
                    </a>
                </li>
                <li :class="{ '-disabled': !isEnableTraPhieu }">
                    <a @click="returnTicket">
                        <span class="icon one-file-back"></span>Trả phiếu
                    </a>
                </li>
                <!--<li :class="{ '-disabled': !isEnableCompleteHoSo }">
                    <a href="#">
                        <span class="icon one-file-back"></span>Hoàn thiện hồ sơ
                    </a>
                </li> -->
            </ul>
            <div class="more dropdown">
                <a data-toggle="dropdown">
                    <span class="icon -ap  icon-dots-three-vertical"></span>
                </a>
                <div class="dropdown-menu ovauto" style="max-height: 300px;">
                    <!-- <a href="#" :class="{ '-disabled': !isEnableDoiCong }">
                        <span class="dropdown-icon one-sync"></span> Đổi cổng
                    </a> -->
                    <a @click="onClickVatTu" :class="{ '-disabled': !isEnableMaterial }">
                        <span class="dropdown-icon one-vt"></span> Vật tư
                    </a>
                    <!-- <a href="#" :class="{ '-disabled': !isEnableExportFile }">
                        <span class="dropdown-icon one-download"></span> Xuất file
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableHenLD }">
                        <span class="dropdown-icon one-henld"></span> Hẹn LĐ
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableThongSoKyThuat }">
                        <span class="dropdown-icon nc-icon-glyph ui-1_settings"></span> thông số KT
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableGanKV }">
                        <span class="dropdown-icon -ap icon-pin"></span> Gán KV
                    </a> -->
                    <a @click="onTicketInventory">
                        <span class="dropdown-icon one-calendar-number"></span> Tồn phiếu
                    </a>
                    <a @click="giveWork" :class="{ '-disabled': !isEnableGiveWork }">
                        <span class="dropdown-icon one-file-edit2"></span> Giao việc
                    </a>
                    <!-- <a href="#" :class="{ '-disabled': !isEnableIpLanWan }">
                        <span class="dropdown-icon one-location-ip"></span> IP LAN, WAN
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableHuongKN }">
                        <span class="dropdown-icon one-location-ip"></span> Hướng KN
                    </a> -->
                    <!-- <a @click="onGiaoPhieu">
                        <span class="dropdown-icon one-file-arrow-right1"></span> Giao phiếu
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableDuAn }">
                        <span class="dropdown-icon one-clipart-edit"></span> Dự án
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableShowPort }">
                        <span class="dropdown-icon one-clipart-arrow-right"></span> Show Port
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableTestPort }">
                        <span class="dropdown-icon nc-icon-glyph arrows-1_simple-up"></span> Test port
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableCheckVisa }">
                        <span class="dropdown-icon one-file-visa"></span> Tra cứu VISA
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableCheckOneMesh }">
                        <span class="dropdown-icon ui-1_lock nc-icon-glyph"></span> Tra cứu OneMesh
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableXemMK }">
                        <span class="dropdown-icon ui-1_lock nc-icon-glyph"></span> Xem MK
                    </a> -->
                    <a @click="onMeasureAndCheck">
                        <span class="dropdown-icon nc-icon-glyph design_design"></span> Đo kiểm
                    </a>
                    <!-- <a href="#" :class="{ '-disabled': !isEnableSuaKenhThu }">
                        <span class="dropdown-icon nc-icon-glyph ui-1_edit-74"></span> Sửa kênh thu
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableToaNha }">
                        <span class="dropdown-icon one-building"></span> Toà nhà
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableChuyenVMP }">
                        <span class="dropdown-icon one-file-arrow-right"></span> Chuyển VMP
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnablePhoiHopThiCong }">
                        <span class="dropdown-icon one-file-half"></span> Phối hợp thi công
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableCSHT }">
                        <span class="dropdown-icon one-thicong"></span> Cơ sở hạ tầng
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableNapTienTrinh }">
                        <span class="dropdown-icon one-clipart-edit"></span> Nạp tiến trình
                    </a> -->
                    <!-- <a href="#" :class="{ '-disabled': !isEnableComplete }">
                        <span class="dropdown-icon one-file-check"></span> Hoàn thiện
                    </a> -->
                    <a :class="{ '-disabled': !isEnableHoanCong }"
                        @click="OnComplete">
                        <span class="dropdown-icon one-hoancong"></span> Hoàn công
                    </a>
                </div>

            </div>
        </div>
        <div class="page-content">
            <subscriber-info ref="subcriberInfor"
                :inputServices="getServices"
                :inputProcedures="getProcedures"
                :inputPersonGiveWorks="getPersonGiveWorks"
                :inputEmployees="getEmployees"
                :inputSelectedSubcriber="getSelectedSubcriber"
                :inputDataControls="onInputDataControls"
                :dsloaihd_id="dsloaihd_id"
                :tthd_id="tthd_id.toString()"
                @timKiemMaGiaoDich="timKiemMaGiaoDich"
                @finishedMounted="initialize"></subscriber-info>
            <subcribers-cable-change
                ref="subcribersCableChange"
                :inputChangeWireSubcribers="getChangeWireSubcribers"></subcribers-cable-change>
        </div>
      <ejs-dialog :enableResize='true' :visible="false" ref="giaophieunhanvien"
          :header='"Giao phiếu nhân viên"'
          showCloseIcon=true
          :target="target"
          width='60%'
          height="800">
          <div>
            <giaophieunhanvien @giaoviec_success="giveWorkSuccess()"
                ref="formgiaphieu"
                :ngaygiao="getSelectedDateGiveWork"
                :nguoigv_id="getSelectedPersonGiveWorkId"
                :phieu_id="getPhieuId" />
          </div>
      </ejs-dialog>
      <DoKiemModal ref="doKiem"
        :hdtb_id="getSelectedSubcriber.hdtb_id"/>
      <b-modal id="form-tra-phieu"
        v-model="showModalTraphieu"
        size="lg"
        title="Trả phiếu">
        <div style="height:900px">
          <traphieu :ma_gd="getSelectedSubcriber.ma_gd"
          :ma_tb="getSelectedSubcriber.ma_tb"
          :loaihd_id="getSelectedSubcriber.loaihd_id"
          :dichvuvt_id="getSelectedSubcriber.dichvuvt_id"
          :ngaygiao="getSelectedSubcriber.ngaygiao"
          :ngaytra="returnDate"
          :kieu="getSelectedSubcriber.kieuld_id"
          :nhanvien_id="nhanvienId"/>
        </div>
      </b-modal>
      <TonPhieuGiaoModal ref="tonPhieuGiaoModal"
        :phieu_id="getSelectedSubcriber.phieu_id"
        :tthd_id="tthd_id"/>
      <ChuyenTo ref="popupChuyenTo"></ChuyenTo>
      <!-- <VatTuThueBao ref="popupVatTuThueBao" :input-data="modelVatTu"/> -->
      <CapVatTuTB ref="popupVatTuTB" :input-data="modelVatTu"/>
      <InBienBan ref="popupInBB" :params="inBBModel" />
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import api from './api/index'
import axios from 'axios'
import Vue from 'vue'
import * as moment from 'moment'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import breadcrumb from '@/components/breadcrumb'
import InBienBan from "@/modules/print/InBienBan/InBienBan.vue"
Vue.use(DialogPlugin)

const TrangThaiHD = {
  MOI: 1,
  THANH_TOAN: 2,
  DIEUHANH_THI_CONG: 3,
  DANG_THI_CONG: 4,
  DA_THI_CONG_XONG: 5,
  HOAN_THANH: 6,
  THOAITRA: 7,
  DA_LAY_DU_LIEU: 8,
  KHAIBAO_TONGDAI: 9,
  DANGCHO_HOANCONG: 10,
  DOISOAT_HOSO: 11,
  TTVT_XACMINH_NO: 14,
  THOAITRA_TMP: 17,
  TTVT_HOANCONG_DICHVU: 23,
  HTHS_BANCHEO: 26,
  NVKD_XACMINH: 27
}

export default {
  data () {
    return {
      target: '.main-wrapper',
      isEnableSave: false,
      isEnableHoanCong: false,
      isEnableGiveWork: true,
      isEnableMaterial: true,
      isEnableTraPhieu: false,
      // isEnableCompleteHoSo: false,
      nhanvienId: this.$root.token.getNhanVienID(),
      returnDate: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
      showModalTraphieu: false,
      isEnableKhoiPhucSo: false,
      isEnableKichHoat: false,
      isEnableHuyKichHoat: false,
      isEnableDauNoi: false,
      isEnableXuatPhieu: false,
      isEnableDoiCong: false,
      isEnableExportFile: false,
      isEnableHenLD: false,
      isEnableThongSoKyThuat: false,
      isEnableGanKV: false,
      isEnableIpLanWan: false,
      isEnableHuongKN: false,
      isEnableDuAn: false,
      isEnableShowPort: false,
      isEnableTestPort: false,
      isEnableCheckVisa: false,
      isEnableCheckOneMesh: false,
      isEnableXemMK: false,
      isEnableSuaKenhThu: false,
      isEnableToaNha: false,
      isEnableChuyenVMP: false,
      isEnablePhoiHopThiCong: false,
      isEnableCSHT: false,
      isEnableNapTienTrinh: false,
      tag: '',
      tthd_id: 0,
      dsloaihd_id: 0,
      dsdichvuvt_id: 0,
      modelVatTu: {},
      inBBModel: {
        n_hdkh_id: '',
        nvth_id: -1,
        cv_thuchien: '',
        n_phieu_id: '',
        n_hdtb_id: '',
        huonggiao_id: 0,
        tentram_tb: ''
      }
    }
  },
  components: {
    'subscriber-info': () => import('./parts/SubcriberInfor.vue'),
    'subcribers-cable-change': () => import('./parts/SubcribersCableChange.vue'),
    'giaophieunhanvien': () => import('../DeclareLandline/popup/giaophieunhanvien.vue'),
    'DoKiemModal': () => import('../DeclareServiceSpeedITSecure/popups/DoKiem/DoKiemModal.vue'),
    'traphieu': () => import('../ReturnInstallTicket/components/traphieu.vue'),
    'TonPhieuGiaoModal': () => import('../DeclareServiceSpeedITSecure/popups/TonPhieuGiao/TonPhieuGiaoModal.vue'),
    'ChuyenTo': () => import('../../profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue'),
    'VatTuThueBao': () => import('../ChangeServiceOMCVIPCM/popups/VatTuThueBao.vue'),
    'CapVatTuTB': () => import('../../complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue'),
    breadcrumb,
    InBienBan
  },
  methods: {
    ...mapActions('installReplaceRecallWifiCommon', [
      'loadSubcriberTypes',
      'loadProcedures',
      'loadPersonGiveWorks',
      'loadEmployees',
      'loadChangeWireSubcribers',
      'setServices'
    ]),
    initialize: async function () {
      this.loading(true)
      try {
        if (this.$route.query.tag) {
          this.tag = this.$route.query.tag
          this.tag = this.tag.replaceAll(' ', '+')
        } else {
          this.tag = '4+13+1;4;8;9;10;11;12'
        }

        if (this.tag) {
          const str = this.tag
          const words = str.split('+')
          if (words.length >= 1) { this.tthd_id = parseInt(words[0]) }
          if (words.length >= 2) {
            this.dsloaihd_id = words[1]
            this.dsloaihd_id = this.dsloaihd_id.replaceAll(';', ',')
          }
          if (words.length >= 3) {
            this.dsdichvuvt_id = words[2]
            this.dsdichvuvt_id = this.dsdichvuvt_id.replaceAll(';', ',')
          }
        } else {
          this.tthd_id = TrangThaiHD.DANG_THI_CONG
          this.dsloaihd_id = '1'
          this.dsdichvuvt_id = '1'
        }

        // services
        const resService = await api.getServices(axios)
        if (resService.data.data) {
          const services = resService.data.data.filter(i => this.dsdichvuvt_id.split(',').includes(i.DICHVUVT_ID.toString()))
          this.setServices(services)
        }

        await this.loadSubcriberTypes()
        await this.loadPersonGiveWorks(this.$root.token.getNhanVienID())
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.loading(false)
    },
    giveWork: async function () {
      if (this.checkGiveWork()) {
        this.$refs.formgiaphieu.init()
        this.$refs.giaophieunhanvien.show()
      }
    },
    giveWorkSuccess: async function (e) {
      console.log('Giao việc emit : ', e)
      this.$root.showLoading(true)
      try {
        await this.loadEmployees({
          kieu_id: this.getSelectedSubcriber.kieuld_id,
          nhanvien_id: this.$root.token.getNhanVienID(),
          phanvung_id: this.$root.token.getPhanVungID(),
          phieu_id: this.getSelectedSubcriber.phieu_id
        })
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    save: async function () {
      this.$root.showLoading(true)
      try {
        if (await this.checkDataSave()) {
          const dsNhanvienGiaoViec = this.getEmployees.map(item => {
            return {
              PHIEU_ID: item.phieu_id,
              NHANVIEN_GIAO_ID: item.nhanvien_giao_id,
              NGAYGIAO: item.ngaygiao,
              NHANVIEN_TH_ID: item.nhanvien_th_id,
              NHIEMVU: item.nhiemvu,
              GHICHU: item.ghichu,
              SO_DT: item.so_dt,
              STATUS: item.trangthai,
              NHIEMVU_ID: item.nhiemvu_id,
              KQXL_ID: item.kqxl_id
            }
          })
          const bodyData = {
            NHANVIEN_ID: this.$root.token.getNhanVienID(),
            PHIEU_ID: this.getSelectedSubcriber.phieu_id,
            NHANVIEN_GIAO_ID: this.$refs.subcriberInfor.selectedPersonGiveWorkId,
            GIAOVIEC_NHANVIEN: dsNhanvienGiaoViec
          }
          await api.update(axios, {
            parameters: JSON.stringify(bodyData)
          })
          this.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công.')

          await this.loadChangedWireSubcriberList()
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    checkGiveWork: function () {
      if (!this.$refs.subcriberInfor.$refs.dateGiveWork.checkbox_value || !this.$refs.subcriberInfor.selectedDateGiveWork) {
        this.$toast.error('Vui lòng chọn ngày giao việc!')
        return false
      }

      if (this.getChangeWireSubcribers.length === 0) {
        this.$toast.error('Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại!')
        return false
      }

      if (!this.$refs.subcriberInfor.selectedPersonGiveWorkId) {
        this.$toast.error('Hãy nhập nhân viên giao việc!')
        return false
      }

      return true
    },
    checkDataSave: async function () {
      if (!this.$refs.subcriberInfor.$refs.dateGiveWork.checkbox_value || !this.$refs.subcriberInfor.selectedDateGiveWork) {
        this.$toast.error('Hãy chọn ngày giao việc !')
        return false
      }

      if (await this.checkHaveToDo('GIAOVIEC')) {
        if (this.getEmployees.length === 0) {
          this.$toast.error('Hãy nhập nhân viên thực hiện  !')
          return false
        }
      }

      if (!this.$refs.subcriberInfor.$refs.dateTH.checkbox_value || !this.$refs.subcriberInfor.selectedDateTH) {
        this.$toast.error('Hãy nhập ngày thực hiện!')
        return false
      }

      if (moment(this.$refs.subcriberInfor.selectedDateGiveWork, 'DD/MM/YYYY HH:mm:ss').toDate().getTime() > moment(this.$refs.subcriberInfor.selectedDateTH, 'DD/MM/YYYY HH:mm:ss').toDate().getTime()) {
        this.$toast.error('Ngày giao việc không được lớn hơn ngày hoàn thành!')
        return false
      }

      if (moment(this.$refs.subcriberInfor.selectedDateGiveWork, 'DD/MM/YYYY HH:mm:ss').toDate().getTime() > (new Date()).getTime()) {
        this.$toast.error('Bạn không được phép chọn ngày giao việc lớn hơn ngày hiện tại')
        return false
      }

      return true
    },
    checkHaveToDo: async function (code) {
      const res = await api.haveToDo(axios, {
        code: code,
        luong_id: this.getDirections.length > 0 ? this.getDirections[0].luong_id : 0
      })

      if (res.data.data === '1') {
        return true
      }
      return false
    },
    onInputDataControls: function (controls) {
      controls.forEach(item => {
        if (item.code === 'TUDONG_HTHS') {

        } else if (item.code === 'CAPNHAT_CAP') {
          this.isEnableSave = item.enable === 1
        } else if (item.code === 'HOANCONG') {
          this.isEnableHoanCong = item.enable === 1
        } else if (item.code === 'GIAOVIEC') {
          this.isEnableGiveWork = item.enable === 1
        } else if (item.code === 'VAT_TU_MOI') {

        } else if (item.code === 'TRAPHIEU_BH') {
          this.isEnableTraPhieu = item.enable === 1
        } 
        // else if (item.code === 'HOANTHIENHOSO') {
        //   this.isEnableCompleteHoSo = item.enable === 1
        // } 
      })
    },
    loadInfor: async function () {
      this.$root.showLoading(true)
      try {
        await this.loadChangedWireSubcriberList()
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    loadChangedWireSubcriberList: async function () {
      let loaiId = 0
      if (!this.getIsNewCard) {
        loaiId = 1
      }

      await this.loadChangeWireSubcribers({
        vdichvuvt_id: this.$refs.subcriberInfor.selectedServiceId === null ? 0 : this.$refs.subcriberInfor.selectedServiceId,
        vhuonggiao_id: 0,
        vkieu_id: 0,
        vloai_id: loaiId,
        vma_gd: this.$refs.subcriberInfor.transactionCode === null ? 0 : this.$refs.subcriberInfor.transactionCode,
        vnhanvien_id: this.$root.token.getNhanVienID(),
        vtthd_id: this.tthd_id
      })
    },
    timKiemMaGiaoDich(ma_gd) {
      this.loadInfor()
    },
    layThongTin() {
      this.$refs.subcriberInfor.transactionCode = null
      this.loadInfor()
    },
    OnComplete: async function () {
      const isConfirm = await this.$bvModal
        .msgBoxConfirm(
          `Bạn thật sự muốn hoàn công không ?`,
          {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          }
        )

      if (isConfirm && await this.checkDataSave()) {
        this.$root.showLoading(true)
        try {
          const bodyData = {
            LUONG_ID: this.getDirections.length > 0 ? this.getDirections[0].luong_id : 0,
            THUEBAO_ID: this.getSelectedSubcriber.thuebao_id,
            HDTB_ID: this.getSelectedSubcriber.hdtb_id,
            VATTU_MOI: 1,
            PHIEU_ID: this.getSelectedSubcriber.phieu_id,
            HUONGGIAO_ID: this.getDirections.length > 0 ? this.getDirections[0].huonggiao_id : 0,
            TTND_DONVI_ID: this.$root.token.getDonViID(),
            TTND_NHANVIEN_ID: this.$root.token.getNhanVienID(),
            TTND_MA_ND: this.$root.token.getUserName(),
            TTND_IP: '10.0.0.1',
            NGAY_TH: this.$refs.subcriberInfor.selectedDateTH
          }

          const res = await api.complete(axios, {
            parameters: JSON.stringify(bodyData)
          })

          if (res.data.data && res.data.data.toString() === 'ok') {
            this.$toast.success('Hoàn công thành công')
            await this.loadChangedWireSubcriberList()
          } else {
            console.log(res.data)
            this.$toast.error(res.data.message)
          }
        } catch (error) {
          console.log(error)
          this.$toast.error(error.toString())
        }
        this.$root.showLoading(false)
      }
    },
    onGiaoPhieu: async function () {
      try {
        if (this.getChangeWireSubcribers.length === 0) {
          this.$toast.error('Danh sách đang trống !')
          return
        }

        const huonggiaoId = this.getDirections.length > 0 ? this.getDirections[0].huonggiao_id : 0
        let loaiId = 0
        if (!this.getIsNewCard) {
          loaiId = 1
        }

        window.open(`/#/contract/HandoverTicket?vquytrinh=${this.$refs.subcriberInfor.selectedProcedureId}&vhuonggiao_id=${huonggiaoId}&vdichvuvt_id=${this.$root.token.getDonViID()}&vloai_hd=${loaiId}`)
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra')
      }
    },
    onMeasureAndCheck: function () {
      this.$refs.doKiem.showModal()
    },
    returnTicket: function () {
      this.$bvModal.show('form-tra-phieu')
      this.showModalTraphieu = true
    },
    onTicketInventory: function () {
      this.$refs.tonPhieuGiaoModal.showModal()
    },
    onInBB: async function () {
      try {
        const lstChecked = this.$refs.subcribersCableChange.selectedItems
        if (lstChecked.length === 0) {
          this.$toast.warning("Hãy chọn phiếu!")
          return
        }
        let nhdkhid = []
        let nhdtbid = []
        let nphieuid = []
        nhdkhid = lstChecked.map(item => item.hdkh_id)
        nhdtbid = lstChecked.map(item => item.hdtb_id)
        nphieuid = lstChecked.map(item => item.phieu_id)

        this.inBBModel.n_hdkh_id = nhdkhid.join(",")
        this.inBBModel.nvth_id = this.getEmployees.length > 0 ? this.getEmployees[0].nhanvien_th_id : -1
        this.inBBModel.cv_thuchien = this.getEmployees.length > 0 ? this.getEmployees[0].nhiemvu : ''
        this.inBBModel.n_phieu_id = nphieuid.join(",")
        this.inBBModel.n_hdtb_id = nhdtbid.join(",")
        this.inBBModel.huonggiao_id = this.getDirections.length > 0 ? this.getDirections[0].huonggiao_id : 0
        this.inBBModel.tentram_tb = ''
        this.$nextTick(() => {
          this.$refs.popupInBB.showModal()
        })
      } catch (error) {
        console.log(error)
        this.$toast.error("Có lỗi khi in phiếu: " + error.message)
      }
    },
    onChuyenTo: function () {
      this.$refs.popupChuyenTo.openDialog(this.getSelectedSubcriber.phieu_id, this.getSelectedSubcriber.hdtb_id)
    },
    onClickVatTu: function () {
      if (this.getPhieuId) {
        this.modelVatTu.phieu_id = this.getPhieuId
        this.modelVatTu.loaihd_id = this.getSelectedSubcriber.loaihd_id
        this.modelVatTu.hdtb_id = this.getSelectedSubcriber.hdtb_id
        this.modelVatTu.thuebao_id = this.getSelectedSubcriber.thuebao_id

        this.modelVatTu.vdvvt_id = this.$refs.subcriberInfor.selectedServiceId === null ? 0 : this.$refs.subcriberInfor.selectedServiceId
        this.modelVatTu.loaitb_id = this.getSelectedSubcriber.loaitb_id
        // this.modelVatTu.vma_tbcur = this.formThongTinThueBao.txtMaTB.trim()
        this.modelVatTu.baohong_id = 0
        this.$refs.popupVatTuTB.openDialog()
      } else {
        this.$toast.error('Bạn chưa chọn phiếu!')
      }
    }
  },
  computed: {
    ...mapGetters('installReplaceRecallWifiCommon', [
      'getProcedures',
      'getPersonGiveWorks',
      'getChangeWireSubcribers',
      'getEmployees',
      'getSelectedSubcriber',
      'getPhieuId',
      'getSelectedPersonGiveWorkId',
      'getSelectedDateGiveWork',
      'getIsNewCard',
      'getDirections',
      'getServices'
    ])
  },
  watch: {
    getSelectedSubcriber: function () {
      if (this.getSelectedSubcriber.thuebao_id) {
        this.isEnableSave = true
        this.isEnableTraPhieu = true
        // this.isEnableCompleteHoSo = true
      }

      if (this.getSelectedSubcriber.ngay_ht_gp) {
        this.isEnableHoanCong = true
      } else {
        this.isEnableHoanCong = false
      }
    },
    getChangeWireSubcribers: function () {
      if (this.getChangeWireSubcribers.length === 0) {
        this.isEnableHoanCong = false
      }
    },
    showModalTraphieu: async function () {
      this.$root.showLoading(true)
      if (!this.showModalTraphieu) {
        await this.loadChangedWireSubcriberList()
      }
      this.$root.showLoading(false)
    }
  }
}
</script>
<style scoped>
a:hover {
  cursor: pointer;
}

.-disabled{
    pointer-events: none;
    cursor: default;
    color: #d3d3d3 !important;
}

.-disabled a{
    color: #d3d3d3 !important;
}

.-disabled:hover{
    background: unset;
    color: #d3d3d3 !important;
}

.e-dialog {
    overflow: auto;
    overflow-x: hidden;
}

.traphieu .page-content {
  position: unset;
  height: 500px;
}

.traphieu-content {
  width: 1200px;
}

.popup-box {
  background: #fafafa38;
}

.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  min-width: 720px !important;
}

.none-padding {
  padding: 0 !important;
}
</style>
