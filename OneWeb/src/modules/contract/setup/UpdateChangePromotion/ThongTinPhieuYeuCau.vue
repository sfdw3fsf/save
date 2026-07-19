<template>
  <div class="col-sm-6 col-12">
    <div class="box-form">
      <div class="legend-title">Thông tin phiếu yêu cầu</div>
      <div class="row">
        <div class="col-sm-8 col-12">
          <div class="info-row">
            <div class="key w100">Mã giao dịch</div>
            <div class="value">
              <div class="input-more-button">
                <button class="btn" @click="openTraCuuHopDong">
                  <span class="-ap icon-more_horiz"></span>
                </button>
                <input type="text" class="form-control highlight" @keyup.enter="onEnterMaGD" :value="input_magd" @change="e=>input_magd=e.target.value"/>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w80">Ngày YC</div>
            <div class="value">
              <div class="input-icon-right">
                <k-date-picker v-model="ngay_yc"/>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-8 col-12">
          <div class="info-row">
            <div class="key w100">Dich vụ</div>
            <div class="value">
              <div class="select-custom">
                <select v-model="dichvu_selected" @change="changeDichVu" :disabled="!enabledDichVuVT" class="form-control">
                  <option v-for="item in ds_dichvu" :key="item.dichvuvt_id" :value="item.dichvuvt_id">
                    {{item.ten_dvvt}}
                  </option>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w80">Loại hình</div>
            <div class="value">
              <div class="select-custom">
                <select v-model="loaihinh_selected" @change="changeLoaiHinhTBCu" :disabled="disableLoaiHinhTB" class="form-control">
                  <option v-for="item in ds_loaihinh_theo_dv" :key="item.LOAITB_ID" :value="item.LOAITB_ID">
                    {{item.LOAIHINH_TB}}
                  </option>
                </select>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-8 col-12">
          <div class="info-row">
            <div class="key w100">Kiểu yêu cầu</div>
            <div class="value">
              <div class="select-custom">
                <select v-model="kieu_yc_selected" @change="changeKieuYC" :disabled="disableKieuLD" class="form-control">
                  <option v-for="item in ds_kieu_yc" :key="item.kieuld_id" :value="item.kieuld_id">
                    {{item.ten_kieuld}}
                  </option>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w80">Số ảo</div>
            <div class="value">
              <input type="text" class="form-control highlight" :value="input_soao" @change="e=>input_soao=e.target.value"/>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-8 col-12">
          <div class="info-row">
            <div class="key w100">Mức cước TB</div>
            <div class="value">
              <div class="select-custom">
                <select v-model="muoccuoc_tb_selected" :disabled="disableMucCuocTB" @change="changeMucCuocTB" class="form-control">
                  <option v-for="item in ds_muccuoc_tb" :key="item.muccuoctb_id" :value="item.muccuoctb_id">
                    {{item.tenmuccuoc}}
                  </option>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w80">Mã CNG</div>
            <div class="value">
              <input type="text" class="form-control" :value="input_ma_cng" @change="e=>input_ma_cng=e.target.value"/>
            </div>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w100">
          <div class="check-action">
            <input type="checkbox" class="check" @change="changeCheckKhuyenMai" :disabled="khuyenmai.disableCheckKhuyenMai" v-model="khuyenmai.checkKhuyenMai"/>
            <span class="name">K.Mại</span>
          </div>
        </div>
        <div class="value">
          <div class="input-more-button">
            <button class="btn" :class="{disabledButton:!khuyenmai.btnChon_KhuyenMai}"  :disabled="!khuyenmai.btnChon_KhuyenMai" @click="clickChonKhuyenMai(1)">
                  <span class="-ap icon-more_horiz"></span>
            </button>
            <div class="select-custom">
              <select ref="cboKhuyenMai" v-model="khuyenmai.khuyenmai_selected" class="form-control" :disabled="!khuyenmai.checkKhuyenMai||khuyenmai.disableCheckKhuyenMai">
                <option v-for="item in khuyenmai.ds_khuyenmai" :key="item.khuyenmai_id" :value="item.khuyenmai_id">
                  {{item.ten_km}}
                </option>
              </select>
            </div>
          </div>

        </div>
        <div class="value w30 nowrap">
          <button class="btn btn-second" :disabled="!khuyenmai.btnChiTietEnabled" @click="chiTietKM">
            <span class="one-eye f20 inline vcenter"></span> Chi tiết
          </button>
        </div>
      </div>
      <div class="info-row">
        <div class="key w100">
          <div class="check-action">
            <input type="checkbox" class="check" @change="changeCheckTraTruoc" :disabled="tratruoc.disableCheckTraTruoc" v-model="tratruoc.checkTraTruoc" />
            <span class="name">Trả trước</span>
          </div>
        </div>
        <div class="value">
          <div class="input-more-button">
            <button class="btn" :class="{disabledButton:!tratruoc.btnChon_TraTruoc}" :disabled="!tratruoc.btnChon_TraTruoc" @click="clickChonKhuyenMai(2)">
                  <span class="-ap icon-more_horiz"></span>
            </button>
            <div class="select-custom">
              <select v-model="tratruoc.tratruoc_selected" :disabled="!tratruoc.checkTraTruoc||tratruoc.disableCheckTraTruoc" class="form-control">
                <option v-for="item in tratruoc.ds_tratruoc" :key="item.khuyenmai_id" :value="item.khuyenmai_id">
                  {{item.ten_km}}
                </option>
              </select>
            </div>
          </div>
        </div>
        <div class="value w30 nowrap">
          <button class="btn btn-second" :disabled="!tratruoc.btnChiTietEnabled" @click="chiTietTraTruoc">
            <span class="one-eye f20 inline vcenter"></span> Chi tiết
          </button>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-8 col-12">
          <div class="info-row">
            <div class="key w100">Ghi chú</div>
            <div class="value">
              <input type="text" class="form-control" :value="input_ghichu" @change="e=>input_ghichu=e.target.value"/>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="info-row">
            <div class="key w80">Gói KT</div>
            <div class="value">
              <div class="select-custom">
                <select v-model="goikt_selected" class="form-control">
                  <option v-for="item in ds_goikt" :key="item.goikt_id" :value="item.goikt_id">
                    {{item.ten_goi}}
                  </option>
                </select>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal -->
    <SearchContractModal ref="popupSearchContract"
      :loai_hd_id="6"
      :trangthai_hd="1"
      @accept="acceptSearchContract"/>
    <ChonKhuyenMaiModal ref="chonKhuyenMaiModal"
      :loai="loai"
      :data="ds_km_tt"
      @accept="acceptChonKhuyenMai"
    />
    <DangKyChiTietKhuyenMaiModal ref="dangKyChiTietKhuyenMaiModal"
      :dulieu="dulieu"
      @form-close="formCloseChiTietKM"
    />
  </div>
</template>
<script>
import KDatePicker from "@/components/kylq_components/KDatePicker.vue"
import moment from "moment"
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
// import SearchContractModal from "../ChangeInstallUnit/popup/SearchContract/SearchContractModal.vue"
import ChangeSubsTypeAPI from "./ChangeSubsTypeAPI"
import {LoaiHinhTB, LoaiHopDong, DichVuVienThong} from "./ThamSo"
import ChonKhuyenMaiModal from "./popups/ChonKhuyenMaiModal.vue"
import DangKyChiTietKhuyenMaiModal from "./popups/DangKyChiTietKhuyenMaiModal.vue"
export default {
  name: "ThongTinPhieuYeuCau",
  props: {
    dsKM_HDTB: {
      type: Array,
      default: () => []
    },
    dsDC_HDTB: {
      type: Array,
      default: () => []
    },
    loaihinhtb_id: {
      type: [Number, String],
      default: 0
    },
    hdtb_id: {
      type: Number,
      default: 0
    }
  },
  components: {
    KDatePicker,
    SearchContractModal,
    ChonKhuyenMaiModal,
    DangKyChiTietKhuyenMaiModal
  },
  data () {
    return {
      input_magd: "",
      ngay_yc: moment(new Date()).format("DD/MM/YYYY"),
      input_soao: "",
      input_ma_cng: "",
      khuyenmai: {
        disableCheckKhuyenMai: true,
        checkKhuyenMai: false,
        ds_khuyenmai: [],
        khuyenmai_selected: null,
        btnChiTietEnabled: false,
        btnChon_KhuyenMai: false
      },
      tratruoc: {
        disableCheckTraTruoc: true,
        checkTraTruoc: false,
        ds_tratruoc: [],
        tratruoc_selected: null,
        btnChiTietEnabled: false,
        btnChon_TraTruoc: false
      },
      ds_dichvu: [
        {
          dichvuvt_id: 1,
          ten_dvvt: "Cố định"
        },
        {
          dichvuvt_id: 2,
          ten_dvvt: "Di động"
        },
        {
          dichvuvt_id: 4,
          ten_dvvt: "Băng rộng cố định"
        },
        {
          dichvuvt_id: 7,
          ten_dvvt: "Metronet"
        },
        {
          dichvuvt_id: 8,
          ten_dvvt: "MegaWan"
        },
        {
          dichvuvt_id: 9,
          ten_dvvt: "Kênh thuê riêng (điểm-điểm)"
        },
        {
          dichvuvt_id: 11,
          ten_dvvt: "IMS"
        }
        // {
        //   dichvuvt_id: 14,
        //   ten_dvvt: "An toàn bảo mật thông tin"
        // },

      ],
      dichvu_selected: 1,
      ds_loaihinh: [],
      loaihinh_selected: null,
      ds_kieu_yc: [],
      kieu_yc_selected: null,
      ds_muccuoc_tb: [],
      muoccuoc_tb_selected: null,
      disableMucCuocTB: false,
      disableLoaiHinhTB: false,
      disableKieuLD: false,
      ds_goikt: [],
      goikt_selected: null,
      input_ghichu: "",
      loai: 1, // 1:Khuyến mãi/ 2:Trả trước,
      loaiCT: 1, // 1:Khuyến mãi/ 2:Trả trước,

      dulieuCTKM: {},
      dulieuCTTC: {},
      enabledDichVuVT: true

    }
  },
  computed: {
    ds_loaihinh_theo_dv () {
      return this.ds_loaihinh.filter(x => x.DICHVUVT_ID == this.dichvu_selected)
    },
    ds_km_tt () {
      return this.loai == 1 ? this.khuyenmai.ds_khuyenmai : this.tratruoc.ds_tratruoc
    },
    dulieu () {
      return this.loaiCT == 1 ? this.dulieuCTKM : this.dulieuCTTC
    }
  },
  methods: {
    chiTietKM () {
      this.loaiCT = 1
      this.$emit("btnChiTietKMClick")
    },
    chiTietTraTruoc () {
      this.loaiCT = 2
      //
      this.$emit("btnChiTietTCClick")
    },
    async openDialogChiTietDKKhuyenMai (dulieu) {
      this.dulieuCTKM.khuyenmai_id = this.khuyenmai.khuyenmai_selected != null ? this.khuyenmai.khuyenmai_selected : 0
      this.dulieuCTKM.loaihinh_tb = this.loaihinhtb_id
      this.dulieuCTKM.vdichvuvt_id = this.dichvu_selected != null ? this.dichvu_selected : 0
      this.dulieuCTKM.kieu_km = 0
      Object.assign(this.dulieuCTKM, dulieu)

      // test
      // hard tạm
      // this.dulieuCTKM.khuyenmai_id=11099
      // this.dulieuCTKM.loaihinh_tb=58
      // this.dulieuCTKM.vdichvuvt_id=this.dichvu_selected!=null?this.dichvu_selected:0
      // this.dulieuCTKM.vtocdotn_id=9270
      // this.dulieuCTKM.vmuccuoctn_id=0
      // this.dulieuCTKM.kieu_km=0

      //
      setTimeout(() => {
        console.log("dulieuCTKM", this.dulieuCTKM)
        this.$refs.dangKyChiTietKhuyenMaiModal.showModal()
      }, 500)
    },
    async openDialogChiTietTraTruoc (dulieu) {
      this.dulieuCTTC.khuyenmai_id = this.tratruoc.tratruoc_selected != null ? this.tratruoc.tratruoc_selected : 0
      this.dulieuCTTC.loaihinh_tb = this.loaihinhtb_id
      this.dulieuCTTC.vdichvuvt_id = this.dichvu_selected != null ? this.dichvu_selected : 0
      this.dulieuCTKM.kieu_km = 1
      Object.assign(this.dulieuCTTC, dulieu)
      //

      //
      // this.dulieuCTTC.khuyenmai_id=11099
      // this.dulieuCTTC.loaihinh_tb=58
      // this.dulieuCTTC.vdichvuvt_id=this.dichvu_selected!=null?this.dichvu_selected:0
      // this.dulieuCTTC.vtocdotn_id=9270
      // this.dulieuCTTC.vmuccuoctn_id=0
      // this.dulieuCTTC.kieu_km=1

      setTimeout(() => {
        console.log("dulieuCTTC", this.dulieuCTTC)
        this.$refs.dangKyChiTietKhuyenMaiModal.showModal()
      }, 500)
    },
    openTraCuuHopDong () {
      this.$refs.popupSearchContract.showModal()
    },
    acceptSearchContract (item) {
      this.input_magd = item.ma_gd
      this.onEnterMaGD()
      // handled
    },
    formCloseChiTietKM (ds) {
      if (ds == null) {
        return
      }
      this.$emit("formCloseChiTietKM", {
        loai: this.loaiCT,
        ds: ds
      })
    },
    async changeDichVu () {
      this.$emit("changeDichVu", this.dichvu_selected)
      // handle
      if (this.ds_loaihinh_theo_dv.length > 0) {
        if (this.dichvu_selected == DichVuVienThong.CO_DINH) {
          this.loaihinh_selected = 1
        } else if (this.dichvu_selected == DichVuVienThong.ADSL) {
          this.loaihinh_selected = 58
        } else {
          this.loaihinh_selected = this.ds_loaihinh_theo_dv[0].LOAITB_ID
        }
        await this.changeLoaiHinhTBCu()
      }
    },
    async changeLoaiHinhTBCu () {
      let loaihinhtbcu_id = Number(this.loaihinh_selected)
      if (loaihinhtbcu_id == LoaiHinhTB.DIENTHOAI_CD) {
        this.disableMucCuocTB = true
      } else {
        this.disableMucCuocTB = false
      }
      if (loaihinhtbcu_id == LoaiHinhTB.FSECURE) {
        this.disableLoaiHinhTB = true
        this.disableKieuLD = true
      } else {
        this.disableLoaiHinhTB = false
        this.disableKieuLD = false
      }

      this.kieu_yc_selected = null

      this.$emit("changeLoaiHinhTBCu", loaihinhtbcu_id)
    },
    async changeKieuYC () {
      this.$emit("changeKieuYC", this.kieu_yc_selected)
    },
    async changeMucCuocTB () {
      this.$emit("changeMucCuocTB", this.muoccuoc_tb_selected)
    },
    async changeCheckKhuyenMai () {
      this.khuyenmai.btnChiTietEnabled = this.khuyenmai.checkKhuyenMai
      this.khuyenmai.btnChon_KhuyenMai = this.khuyenmai.checkKhuyenMai
      // handle xử lý sau
      this.$emit("changeCheckKhuyenMai", {
        isChecked: this.khuyenmai.checkKhuyenMai,
        khuyenmai_selected: this.khuyenmai.khuyenmai_selected,
        lenght_ds_khuyenmai: this.khuyenmai.ds_khuyenmai.length
      })
    },
    async changeCheckTraTruoc () {
      this.tratruoc.btnChiTietEnabled = this.tratruoc.checkTraTruoc
      this.tratruoc.btnChon_TraTruoc = this.tratruoc.checkTraTruoc
      // handle xử lý sau
      this.$emit("changeCheckTraTruoc", {
        isChecked: this.tratruoc.checkTraTruoc,
        tratruoc_selected: this.tratruoc.tratruoc_selected,
        lenght_ds_tratruoc: this.tratruoc.ds_tratruoc.length
      })
    },
    clickChonKhuyenMai (loai) {
      this.loai = loai
      this.$refs.chonKhuyenMaiModal.showModal()
    },
    acceptChonKhuyenMai (khuyenmai_id) {
      if (this.loai == 1) {
        this.khuyenmai.khuyenmai_selected = khuyenmai_id
      } else if (this.loai == 2) {
        this.tratruoc.tratruoc_selected = khuyenmai_id
      }
    },
    //
    async setLoaiHinhTbCu (loaihinh_tb_id) {
      this.loaihinh_selected = loaihinh_tb_id
      let loaihinhtbcu_id = Number(this.loaihinh_selected)
      if (loaihinhtbcu_id == LoaiHinhTB.DIENTHOAI_CD) {
        this.disableMucCuocTB = true
      } else {
        this.disableMucCuocTB = false
      }
      if (loaihinhtbcu_id == LoaiHinhTB.FSECURE) {
        this.disableLoaiHinhTB = true
        this.disableKieuLD = true
      } else {
        this.disableLoaiHinhTB = false
        this.disableKieuLD = false
      }
    },
    async setKieuLD (ds) {
      this.ds_kieu_yc = ds
      if (this.ds_kieu_yc.length > 0) {
        this.kieu_yc_selected = this.ds_kieu_yc[0].kieuld_id
      } else {
        this.kieu_yc_selected = null
      }
      // await this.changeKieuYC()
    },
    async getNgayYC () {
      return this.ngay_yc != null ? this.ngay_yc : ""
    },
    focusKhuyenMai () {
      this.$refs.cboKhuyenMai.focus()
    },
    async DS_MucCuoc_TB_V2 (ds) {
      console.log("DS_MucCuoc_TB_V2", ds)
      if (ds.length > 0) {
        this.ds_muccuoc_tb = ds
        this.muoccuoc_tb_selected = this.ds_muccuoc_tb[0].muccuoctb_id
        await this.changeMucCuocTB()
      } else {
        this.ds_muccuoc_tb = []
      }
    },

    async HienThiTTHopDongTB (item) {
      // khởi tạo dịch vụ
      if (this.dichvu_selected != item.dichvuvt_id_map) {
        this.dichvu_selected = item.dichvuvt_id_map
        if (this.ds_loaihinh_theo_dv.length > 0) {
          this.loaihinh_selected = this.ds_loaihinh_theo_dv[0].LOAITB_ID
          await this.changeLoaiHinhTBCu()
        }
      } else {

      }
      //
    },

    async HienThiTT_DanhBaV2 (item) {
      this.ngay_yc = moment(new Date()).format("DD/MM/YYYY")
      this.input_soao = ""
      if (item.loaitb_id != null && this.loaihinh_selected != item.loaitb_id) {
        this.loaihinh_selected = item.loaitb_id
        await this.changeLoaiHinhTBCu()
      }
    },
    async HienThiTTHopDongTB_B2 (item, result) {
      this.kieu_yc_selected = item.KIEULD_ID != null ? item.KIEULD_ID.toString() : "0"

      // await this.changeKieuYC()
      this.muoccuoc_tb_selected = item.MUCUOCTB_ID != null ? item.MUCUOCTB_ID : -1
      // await this.changeMucCuocTB()
      // txtGhiChu.Text = CommonFunction.ChuanHoaTen(dst.Rows[rowIndex]["ghichu"].ToString());
      this.input_ghichu = item.GHICHU != null ? item.GHICHU : ""

      if (item.hasOwnProperty("GOIKT_ID") && item.GOIKT_ID != null) {
        this.goikt_selected = item.GOIKT_ID.toString()
      } else {
        this.goikt_selected = this.ds_goikt.length > 0 ? this.ds_goikt[0].goikt_id : 0
      }
      this.input_soao = ""
    },
    async khoiTaoDsKhuyenMai (dsKhuyenMai) {
      this.khuyenmai.ds_khuyenmai = dsKhuyenMai
      if (this.khuyenmai.ds_khuyenmai.length > 0) {
        this.khuyenmai.disableCheckKhuyenMai = false
        this.khuyenmai.checkKhuyenMai = true
        this.changeCheckKhuyenMai()
      } else {
        this.khuyenmai.disableCheckKhuyenMai = true
        this.khuyenmai.checkKhuyenMai = false
        this.changeCheckKhuyenMai()
      }
    },
    async khoiTaoDsTraTruoc (dsTraTruoc) {
      this.tratruoc.ds_tratruoc = dsTraTruoc
      if (this.tratruoc.ds_tratruoc.length > 0) {
        this.tratruoc.disableCheckTraTruoc = false
        this.tratruoc.checkTraTruoc = true
        this.changeCheckTraTruoc()
      } else {
        this.tratruoc.disableCheckTraTruoc = true
        this.tratruoc.checkTraTruoc = false
        this.changeCheckTraTruoc()
      }
    },
    async HienThiTTHopDongKH (item) {
      this.input_magd = item.ma_gd != null ? item.ma_gd : ""
    },
    async lay_danhmuc_cdlh_tt_chung (chuoi) {
      try {
        let response = await ChangeSubsTypeAPI.lay_danhmuc_cdlh_tt_chung(this.axios, chuoi)
        if (response && response.data && response.data.error_code && response.data.error_code == "BSS-00000000" && response.data.data) {
          return response.data.data
        } else {
          return null
        }
      } catch (e) {
        return null
      }
    },
    async getThongTinPhieu () {
      return {
        ds_kieu_yc: this.ds_kieu_yc,
        kieu_yc_selected: this.kieu_yc_selected,
        ds_muccuoc_tb: this.ds_muccuoc_tb,
        muoccuoc_tb_selected: this.muoccuoc_tb_selected

      }
    },
    async getMaGD () {
      return this.input_magd.trim()
    },
    async getKieuID () {
      return this.kieu_yc_selected != null ? this.kieu_yc_selected : 0
    },
    getDichVu () {
      let dichvu = this.ds_dichvu.find(x => x.dichvuvt_id == this.dichvu_selected)
      return dichvu != undefined ? dichvu.ten_dvvt : ""
    },
    getLoaiHinhTB () {
      const loaihinh = this.ds_loaihinh.find(x => x.LOAITB_ID == this.loaihinh_selected)
      return loaihinh != undefined ? loaihinh.loaihinh_tb : ""
    },
    onEnterMaGD () {
      this.$emit("onEnterMaGD")
    },
    async initDuLieu (ds_loaihinh_tb, ds_goi_kt) {
      this.ds_loaihinh = ds_loaihinh_tb
      if (this.ds_loaihinh_theo_dv.length > 0) {
        if (this.dichvu_selected == DichVuVienThong.CO_DINH) {
          // Điện thoại cố định
          this.loaihinh_selected = 1
        } else if (this.dichvu_selected == DichVuVienThong.ADSL) {
          // Fiber
          this.loaihinh_selected = 58
        } else {
          this.loaihinh_selected = this.ds_loaihinh_theo_dv[0].LOAITB_ID
        }
        // call ngoài sao khi khởi tạo form biến động loại hình
        // await this.changeLoaiHinhTBCu()
      }
      // Gói KT
      this.ds_goikt = ds_goi_kt
      if (this.ds_goikt.length > 0) {
        this.goikt_selected = this.ds_goikt[0].goikt_id
      }
    },

    async clearDulieu () {
      this.input_magd = ""
      this.ngay_yc = moment(new Date()).format("DD/MM/YYYY")
      this.input_soao = ""
      this.input_ma_cng = ""
      this.input_ghichu = ""
    }

  },
  watch: {
    dsKM_HDTB (val) {
      this.dulieuCTKM.dsKM_HDTB = []
      val.forEach(item => {
        this.dulieuCTKM.dsKM_HDTB.push(Object.assign({}, item))
      })
      // upper case key data source
      for (var i = 0; i < this.dulieuCTKM.dsKM_HDTB.length; i++) {
        for (var key in this.dulieuCTKM.dsKM_HDTB[i]) {
          if (key.toLowerCase() !== key) {
            this.dulieuCTKM.dsKM_HDTB[i][key.toLowerCase()] = this.dulieuCTKM.dsKM_HDTB[i][key]
            delete this.dulieuCTKM.dsKM_HDTB[i][key]
          }
        }
      }
      console.log("this.dulieuCTKM.dsKM_HDTB", this.dulieuCTKM.dsKM_HDTB)
    },
    dsDC_HDTB (val) {
      this.dulieuCTTC.dsDC_HDTB = []
      val.forEach(item => {
        this.dulieuCTTC.dsDC_HDTB.push(Object.assign({}, item))
      })
      for (var i = 0; i < this.dulieuCTTC.dsDC_HDTB.length; i++) {
        for (var key in this.dulieuCTTC.dsDC_HDTB[i]) {
          if (key.toLowerCase() !== key) {
            this.dulieuCTTC.dsDC_HDTB[i][key.toLowerCase()] = this.dulieuCTTC.dsDC_HDTB[i][key]
            delete this.dulieuCTTC.dsDC_HDTB[i][key]
          }
        }
      }
      console.log("this.dulieuCTTC.dsDC_HDTB", this.dulieuCTTC.dsDC_HDTB)
    }
  }
}
</script>
<style>
.disabledButton{
  border-color: gray !important;
  color: gray !important;
}
.btn-second {
    background-color: #fff;
    border-color: #0176FF;
    color: #0176FF;
    font-weight: 600;
}
.btn-second:hover {
    background-color: #0176FF;
    border-color: #0176FF;
    color: #fff;
    font-weight: 600;
}
.btn-outline-secondary {
    background-color: #fff;
    color: #6c757d;
    border-color: #6c757d;
}
.btn-outline-secondary:hover {
    background-color: #6c757d;
    color: #fff;
    border-color: #6c757d;
}
</style>
