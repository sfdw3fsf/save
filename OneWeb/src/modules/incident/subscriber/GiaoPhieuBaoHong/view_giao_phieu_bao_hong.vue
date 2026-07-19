<template>
  <div>
    <div class="breadcrumb-top">
      <div class="main-title">Giao phiếu báo hỏng NEW</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
        <li class="breadcrumb-item active">Giao phiếu báo hỏng</li>
      </ul>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li :class="{ '-disabled': !tsbtnTimKiem.enabled }">
          <a @click="onSeach"> <span class="icon one-search"></span>Tìm kiếm</a>
        </li>
        <li :class="{ '-disabled': !tsbtnGiaoPhieu.enabled }">
          <a @click="onGiaoPhieu"> <span class="icon one-file-arrow-right1"></span>Giao phiếu</a>
        </li>
        <li :class="{ '-disabled': !tsbtnHuyGiao.enabled }">
          <a @click="onHuyGiao"> <span class="icon one-cancel"></span>Hủy giao</a>
        </li>
        <li :class="{ '-disabled': !btnCapNhatVT.enabled }">
          <a @click="onCapNhatDv"> <span class="icon one-reload1"></span>Cập nhật ĐV</a>
        </li>
        <li>
          <a href="#"> <span class="icon one-circle-question"></span>Trợ giúp </a>
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
              <div class="key w100">
                <div class="check-action">
                  <input v-model="dichvuvt.enabled" type="checkbox" class="check" />
                  <span class="name">Dịch vụ</span>
                </div>
              </div>
              <div class="value">
                <SelectExt :disabled="!dichvuvt.enabled" v-model="dichvuvt.value" :dataSource="dichvuvt.list" dataTextField="TEN_DVVT" dataValueField="DICHVUVT_ID"></SelectExt>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w100">
                Số máy/Acc
              </div>
              <div class="value">
                <input :disabled="!txtMaTB.enabled" type="text" class="form-control highlight" ref="txtMaTB" v-model="txtMaTB.value" v-on:keyup="onInputSubcriberCode" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">
                Ngày giao
              </div>
              <div class="value">
                <vue-date :disabled="!ngayGiaoTK.enabled" v-model="ngayGiaoTK.value" format="DD/MM/YYYY" type="datetime"></vue-date>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">
                <div class="check-action">
                  <input :disabled="false" v-model="quytrinh.enabled" type="checkbox" class="check" />
                  <span class="name">Quy Trình</span>
                </div>
              </div>
              <div class="value">
                <SelectExt :disabled="!quytrinh.enabled" v-model="quytrinh.value" :dataSource="quytrinh.list" dataTextField="quytrinh" dataValueField="quytrinh_id"></SelectExt>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">
                <div class="check-action">
                  <input :disabled="false" v-model="trangThai.enabled" type="checkbox" class="check" />
                  <span class="name">Trạng thái</span>
                </div>
              </div>
              <div class="value">
                <SelectExt :disabled="!trangThai.enabled" v-model="trangThai.value" :dataSource="trangThai.list" dataTextField="NAME" dataValueField="ID"></SelectExt>
              </div>
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
              <div class="key w100">
                <div class="check-action">
                  <input :disabled="false" v-model="huonggiao.enabled" type="checkbox" class="check" />
                  <span class="name">Hướng giao</span>
                </div>
              </div>
              <div class="value">
                <SelectExt :disabled="!huonggiao.enabled" v-model="huonggiao.value" :dataSource="huonggiao.list" dataTextField="huonggiao" dataValueField="huonggiao_id"></SelectExt>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w100">
                Số máy/Acc
              </div>
              <div class="value">
                <input type="text" class="form-control" v-model="txtMaTB.value" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w100">
                Ngày giao
              </div>
              <div class="value">
                <vue-date :disabled="!ngay_cn.enabled" v-model="ngay_cn.value" format="DD/MM/YYYY" type="datetime"></vue-date>
              </div>
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
                <input :disabled="!noiDungGiaoTxt.enabled" type="text" class="form-control" v-model="noiDungGiaoTxt.value" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w100">
                Đơn vị nhận
              </div>
              <div class="value">
                <SelectExt :disabled="!donViNhan.enabled" v-model="donViNhan.value" :dataSource="donViNhan.list" dataTextField="TEN_DV" dataValueField="DONVI_ID"></SelectExt>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w100">
                Mức độ
              </div>
              <div class="value">
                <SelectExt :disabled="!mucdoXL.enabled" v-model="mucdoXL.value" :dataSource="mucdoXL.list" dataTextField="MUCDO" dataValueField="MUCDO_ID"></SelectExt>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">
          Danh sách phiếu yêu cầu
        </div>
        <DataGrid ref="grcDanhSach" :columns="columns" :dataSource="grcDanhSach" :enable-paging-server="false" :showColumnCheckbox="true" :allowPaging="true" :loading="true" :showFilter="true" @selectedItemsChanged="grid_selectedRowChanged" @rowSelected="rowSelected"> </DataGrid>
      </div>
    </div>
  </div>
</template>

<script>
import api from './api/index'
import moment from 'moment'

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
  props: {
    type: {
      type: String,
      default: 'view'
    },
    vma_tb: {
      type: String,
      required: false,
      default: null
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
    vquytrinh_id: {
      type: [String, Number],
      required: false,
      default: null
    },
    vtrangthaiphieu: {
      type: [String, Number],
      required: false,
      default: null
    }
  },
  data() {
    return {
      dichvuvt: {
        enabled: false,
        list: [],
        value: null
      },
      ngayGiaoTK: {
        enabled: false,
        value: null
      },
      quytrinh: {
        enabled: false,
        list: [],
        value: null
      },
      trangThai: {
        enabled: true,
        list: [],
        value: null
      },
      huonggiao: {
        enabled: false,
        list: [],
        value: null
      },
      ngay_cn: {
        enabled: true,
        value: null
      },
      donViNhan: {
        enabled: false,
        list: [],
        value: null
      },
      mucdoXL: {
        enabled: true,
        list: [],
        value: null
      },
      dsHuongGiaoFormLoad: [],
      isMucdoError: false,
      isDtpNgayGiaoError: false,
      txtMaTB: {
        enabled: true,
        value: null
      },
      noiDungGiaoTxt: {
        enabled: true,
        value: null
      },
      btnCapNhatVT: {
        enabled: false
      },
      tsbtnHuyGiao: {
        enabled: false
      },
      tsbtnTimKiem: {
        enabled: true
      },
      tsbtnGiaoPhieu: {
        enabled: true
      },
      columns: [
        {
          fieldName: 'ma_tb',
          headerText: 'Số máy/Acc'
        },
        {
          fieldName: 'ten_tb',
          headerText: 'Tên thuê bao'
        },
        {
          fieldName: 'diachi_ld',
          headerText: 'Địa chỉ lắp đặt'
        },
        {
          fieldName: 'donvinhan',
          headerText: 'Đơn vị nhận'
        }
      ],
      grcDanhSach: [],
      selectedGrcDanhSach: [],
      kieu_id: 0,
      hg_id: 0,
      baohong_id: 0
    }
  },
  props: ['dataSource'],
  watch: {
    async dataSource(newValue, oldValue) {},
    async 'dichvuvt.value'(val) {
      await this.on_dichvuvt_changed()
    },
    async 'ngayGiaoTK.value'(val) {
      await this.on_ngayGiaoTK_changed()
    },
    async 'quytrinh.value'(val) {
      await this.on_quytrinh_changed()
    },
    async 'trangThai.value'(val) {
      await this.on_trangthai_changed()
    },
    async 'huonggiao.value'(val) {
      await this.on_huonggiao_changed()
    }
  },
  async mounted() {
    this.initData()
    const data = {
      NGUOIDUNG_ID: 1,
      QUYTRINH_ID: this.huonggiao.value,
      DICHVUVT_ID: this.dichvuvt.value
    }
    const resFormLoad = await api.fn_frmgiaophieubh_load(this.axios, {
      ds_para: JSON.stringify(data)
    })
    if (resFormLoad.data.message_detail) {
      throw resFormLoad.data.message_detail
    } else {
      const data = JSON.parse(resFormLoad.data.data)
      this.dichvuvt.list = data.RESULT.DICHVUVT
      this.huonggiao.listFormLoad = data.RESULT.HUONGGIAO

      this.mucdoXL.list = data.RESULT.MUCDO_XL
      if (data.RESULT.MUCDO_XL.length > 0) {
        this.mucdoXL.value = this.mucdoXL.list[0].MUCDO_ID
      }

      this.donViNhan.list = JSON.parse(data.RESULT.DTTRAMTC)
      if (data.RESULT.DTTRAMTC.length > 0) {
        this.donViNhan.value = this.donViNhan.list[0].DONVI_ID
      }

      const statusData = await api.trangthai_phieu(this.axios)
      if (statusData.data.data && statusData.data.data.length > 0) {
        this.trangThai.list = statusData.data.data
        this.trangThai.value = this.trangThai.list[0].ID
      } else {
        this.trangThai.list = []
      }
    }
  },
  computed: {},
  methods: {
    initData() {
      this.initView()
    },
    initView() {
      var { vloaitb_id, vdichvuvt_id, vquytrinh_id, vhuonggiao_id, vma_tb } = this.$route.query
      if (vloaitb_id != null) {
        this.loaitb_id = vloaitb_id
      }
      if (vdichvuvt_id != null) {
        this.dichvuvt.enabled = true
        this.dichvuvt.value = vdichvuvt_id
      }
      if (vquytrinh_id != null) {
        this.quytrinh.enabled = true
        this.quytrinh.value = vquytrinh_id
      }
      if (vhuonggiao_id != null) {
        this.huonggiao.enabled = true
        this.huonggiao.value = vhuonggiao_id
      }
      if (vma_tb != null) {
        this.txtMaTB.value = vma_tb
      }
      this.ngayGiaoTK.value = this.$root.token.getNgayCapNhat()
      this.ngay_cn.value = this.$root.token.getNgayCapNhat()
    },
    initModal() {
      if (this.vloaitb_id != null) {
        this.loaitb_id = this.vloaitb_id
      }
      if (this.vdichvuvt_id != null) {
        this.dichvuvt.enabled = true
        this.dichvuvt.value = this.vdichvuvt_id
      }
      if (this.vquytrinh_id != null) {
        this.quytrinh.enabled = true
        this.quytrinh.value = this.vquytrinh_id
      }
      if (this.vhuonggiao_id != null) {
        this.huonggiao.enabled = true
        this.huonggiao.value = this.vhuonggiao_id
      }
      if (this.vma_tb != null) {
        this.txtMaTB.value = this.vma_tb
      }
      this.ngayGiaoTK.value = this.$root.token.getNgayCapNhat()
      this.ngay_cn.value = this.$root.token.getNgayCapNhat()
    },
    async HienThi_GiaoDien() {
      if (this.kieu_id == 1) {
        this.trangThai.value = this.hg_id
        await this.HienThi_DanhSach()
      }
    },
    async HienThi_DanhSach() {
      if (this.trangThai.value === '1') {
        this.btnCapNhatVT.enabled = true
        this.donViNhan.enabled = true

        this.tsbtnHuyGiao.enabled = false
        this.tsbtnTimKiem.enabled = true
        this.tsbtnGiaoPhieu.enabled = true
      } else {
        this.tsbtnHuyGiao.enabled = true
        this.tsbtnTimKiem.enabled = true
        this.tsbtnGiaoPhieu.enabled = false
        this.btnCapNhatVT.enabled = false
      }

      if (this.trangThai.list.length === 0 || !this.trangThai.value) return

      const postData = {
        huongGiaoId: this.huonggiao.value,
        ngayGiao: this.ngayGiaoTK.value,
        trangThai: this.trangThai.value,
        maTB: this.txtMaTB.value ? this.txtMaTB.value : ''
      }
      const resHienThiDanhSach = await api.getDsBaoHong(this.axios, postData)

      if (resHienThiDanhSach.data.message_detail) {
        throw resHienThiDanhSach.data.message_detail
      }

      this.selectedGrcDanhSach = []
      this.grcDanhSach = resHienThiDanhSach.data.data

      return resHienThiDanhSach.data.data
    },
    async LAY_DS_HUONGGIAO() {
      this.loading(true)
      const data = await api.fn_lay_ds_huonggiao_frmgiaophieubh(this.axios, {
        nguoidung_id: this.$auth.getNguoiDungID(),
        quytrinh_id: this.quytrinh.value,
        dichvuvt_id: this.dichvuvt.value
      })
      if (data.data.data && data.data.data.length > 0) {
        this.huonggiao.list = data.data.data
      } else {
        this.huonggiao.list = []
      }
      this.loading(false)
    },
    async on_dichvuvt_changed() {
      this.loading(true)
      try {
        const data = await api.sp_giaophieubh_layds_quytrinh(this.axios, {
          nhom_qt_id: NHOM_QT.BAOHONG,
          vdichvuvt_id: this.dichvuvt.value,
          vloaihd_id: 0,
          vphanvung_id: 0
        })
        if (data.data.data && data.data.data.length > 0) {
          this.quytrinh.list = data.data.data
        } else {
          this.quytrinh.list = []
        }
      } catch (error) {
        this.$toast.error(`${error}`)
      }
      this.loading(false)
    },
    async on_quytrinh_changed() {
      this.loading(true)
      try {
        await this.LAY_DS_HUONGGIAO()
      } catch (e) {
        this.$toast.error(`${e}`)
      }
      this.loading(false)
    },
    async on_trangthai_changed() {
      await this.HienThi_DanhSach()
    },
    async on_huonggiao_changed() {
      this.loading(true)
      try {
        const resDsDonVi = await api.getDsDonViTheoHuongGiao(this.axios, {
          vhuonggiao_id: this.huonggiao.value,
          vphanvung_id: 0
        })
        if (resDsDonVi.data.message_detail) {
          throw resDsDonVi.data.message_detail
        }
        this.donViNhan.list = resDsDonVi.data.data
        await this.HienThi_DanhSach()
      } catch (e) {
        this.$toast.error(`${e}`)
      }
      this.loading(false)
    },
    onSelectedMucDo() {},
    onSelectedDonVi() {},
    grid_selectedRowChanged(e) {
      this.selectedGrcDanhSach = e
    },
    rowSelected(e) {
      if (e.data) {
        this.ma_tb = e.data.ma_tb
        this.noiDungGiaoTxt.value = e.data.nd_giao
        this.donViNhan.value = e.data.donvi_id
      }
    },
    async onSeach() {
      this.loading(true)
      try {
        await this.HienThi_DanhSach()
      } catch (e) {
        this.$toast.error(`${e}`)
      }
      this.loading(false)
    },
    async onInputSubcriberCode(e) {
      if (e.keyCode === 13) {
        this.loading(true)
        try {
          await this.onSeach()
        } catch (error) {
          this.$toast.error(`${e}`)
        }
        this.loading(false)
      }
    },
    async on_ngayGiaoTK_changed() {
      this.loading(true)
      try {
        await this.HienThi_DanhSach()
      } catch (e) {
        this.$toast.error(`${e}`)
      }
      this.loading(false)
    },
    async onHuyGiao() {
      this.loading(true)
      try {
        if (this.selectedGrcDanhSach.length === 0) {
          const textError = 'Bạn chưa chọn phiếu để hủy'
          throw textError
        }
        for (let i = 0; i < this.selectedGrcDanhSach.length; i++) {
          if (this.selectedGrcDanhSach[i].ttph_id && (this.selectedGrcDanhSach[i].ttph_id.toString().trim() === '4' || this.selectedGrcDanhSach[i].ttph_id.toString().trim() === '2')) {
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
        const resHuyGiao = await api.huyGiaoPhieu(this.axios, {
          ds_para: JSON.stringify(postData)
        })
        if (resHuyGiao.data.message_detail) {
          throw resHuyGiao.data.message_detail
        }
        this.$toast.success('Đã hủy giao phiếu thành công!')
        this.ma_tb = ''
        this.noiDungGiaoTxt.value = ''
        this.ngay_cn.value = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
        await this.HienThi_DanhSach()
      } catch (e) {
        this.$toast.error(`${e}`)
      }
      this.loading(false)
    },
    async onCapNhatDv() {
      this.loading(true)
      try {
        if (this.selectedGrcDanhSach.length === 0) {
          const textError = 'Bạn chưa chọn phiếu!'
          throw textError
        }
        for (let i = 0; i < this.selectedGrcDanhSach.length; i++) {
          const resCapNhat = await api.capNhatDV(this.axios, {
            vbaohong_id: this.selectedGrcDanhSach[i].baohong_id,
            vdonvi_id: this.donViNhan.value,
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
        this.$toast.error(`${e}`)
      }
      this.loading(false)
    },
    async onGiaoPhieu() {
      this.loading(true)
      try {
        if (!this.mucdoXL.value) {
          const errorText = 'Bạn chưa chọn mức độ xử lý'
          this.isMucdoError = true
          throw errorText
        } else this.isMucdoError = false
        const kt_dambao_ckcl_kh = true
        const camket_cl = true
        if (kt_dambao_ckcl_kh && !camket_cl && this.mucdoXL.value.toString() === '3') {
          const errorText = 'Mức độ "Khẩn - KH yêu cầu bồi thường CKCL" chỉ áp dụng cho thuê bao có cam kết chất lượng!'
          throw errorText
        }
        const toDay = moment(new Date().setHours(23, 59, 59, 999))
        if (moment(this.ngay_cn.value) > toDay) {
          const errorText = 'Ngày giao không được lớn hơn ngày hiện tại'
          this.isDtpNgayGiaoError = true
          throw errorText
        } else this.isDtpNgayGiaoError = false
        if (this.selectedGrcDanhSach.length === 0) {
          const errorText = 'Bạn chưa chọn phiếu giao'
          throw errorText
        }
        const resKeyName = await api.getKey(this.axios, {
          keyname: 'PHIEU_ID'
        })
        if (resKeyName.data.message_detail) {
          throw resKeyName.data.message_detail
        }
        const phieu_id = resKeyName.data.data
        const ds_phieu_chon = []
        const ds_phieu_taodl = []
        const ds_sodt_donvi = []
        const huongGiao = this.huonggiao.listFormLoad.find((hg) => hg.HUONGGIAO_ID.toString() === this.huonggiao.value.toString())
        const ds_para = {
          NHANVIEN_ID: this.$auth.getNhanVienID(),
          THUEBAO_ID: this.$auth.getNguoiDungID(),
          MUCDO_ID: this.mucdoXL.value,
          NGAYGIAO: this.ngay_cn.value,
          GIAOVIEC_TD: huongGiao !== null && huongGiao.SMS === 1 ? 1 : 0,
          HUONGGIAO_ID: this.huonggiao.value
        }
        for (let i = 0; i < this.selectedGrcDanhSach.length; i++) {
          const selectedDonVi = this.donViNhan.list.find((dv) => dv.DONVI_ID.toString() === this.selectedGrcDanhSach[i].donvi_id.toString())
          ds_phieu_chon.push({
            THUEBAO_ID: this.selectedGrcDanhSach[i].thuebao_id,
            MA_TB: this.selectedGrcDanhSach[i].ma_tb,
            NOIDUNG: this.noiDungGiaoTxt.value,
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
            HUONGGIAO_ID: this.huonggiao.value,
            PHIEU_ID: phieu_id,
            NHANVIEN_GIAO_ID: this.$auth.getNhanVienID(),
            TINHTRANG: this.selectedGrcDanhSach[i].tinhtrang,
            NGAYGIAO: moment(this.selectedGrcDanhSach[i].ngaygiao, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'),
            ND_GIAO: this.noiDungGiaoTxt.value,
            TTPH_ID: this.selectedGrcDanhSach[i].ttbh_id,
            NGUOI_CN: this.$auth.getNhanVienID(),
            MAY_CN: `${await this.$root.token.getIP()}`,
            DAUCUOI_ID: this.selectedGrcDanhSach[i].kieudv_id,
            DONVI_GIAO_ID: this.$auth.getDonViID()
          })
          ds_sodt_donvi.push({
            THUEBAO_ID: this.selectedGrcDanhSach[i].thuebao_id,
            MA_TB: this.selectedGrcDanhSach[i].ma_tb,
            NOIDUNG: this.noiDungGiaoTxt.value,
            SO_DT: selectedDonVi !== undefined && selectedDonVi.so_dt !== null ? selectedDonVi.so_dt : '',
            DONVI_NHAN_ID: this.selectedGrcDanhSach[i].donvi_id
          })
        }
        const resGiaoPhieu = await api.giaoPhieu(this.axios, {
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
        this.$emit('onFinishedGiaoPhieu', true)
      } catch (e) {
        this.$toast.error(`${e}`)
        this.$emit('onFinishedGiaoPhieu', false)
      }
      this.loading(false)
    }
  }
}
</script>
<style scoped>
.-disabled {
  pointer-events: none;
  cursor: not-allowed;
}
.-disabled:hover {
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
</style>
