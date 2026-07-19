/* eslint-disable no-console */

<template src="./RegisStudentPackage.html" />
<style scoped src=./RegisStudentPackage.css ></style>
<script>
import moment from "moment"
// eslint-disable-next-line no-unused-vars
import axios from "axios"
import api from "./api"
import service from "./service"
import serviceTest from "./service/serviceTest"
import KDatePicker from "@/components/kylq_components/KDatePicker.vue"
// import SearchDsHopDongTb from "@/modules/search/subscriber/CompleteTransferRightsBC/ChonHopDong/index.vue"
// "./popup/SearchDSHopDongTb.vue"
import SearchDsHopDongTb from "./popup/ChonHopDong/index.vue"
import BssErrorMarker from "@/components/BssErrorMarker"
import {LoaiHopDong, LoaiGiayToNeo, KieuLapDat, TrangThaiHD, KieuLay, PivotStr, FormState, ValidOrder} from "./Enum"
import { required, requiredIf, helpers, maxLength } from "vuelidate/lib/validators"
const khoaHocConstraintFormat = (khoaHoc) => khoaHoc.match(/^20\d{2}-20\d{2}$/) != null
const khoaHocConstraintDuration = (khoaHoc) => {
  var startYear = parseInt(khoaHoc.split("-")[0])
  var endYear = parseInt(khoaHoc.split("-")[1])
  if (endYear - startYear > 8) {
    return false
  }
  if (startYear > endYear) {
    return false
  }
  return true
}
const ComboxSelectedIdConstraint = (itemSelectedId) => itemSelectedId && parseInt(itemSelectedId) > 0
const ComboxSelectedIdConstraintBaoHo = (param) =>
  helpers.withParams(
    { type: "ComboxSelectedIdConstraintBaoHo", value: param },
    (value) => !param || parseInt(value) > 0
  )
export default {
  components: {
    KDatePicker, SearchDsHopDongTb, BssErrorMarker
  },
  data () {
    return {
      dateconfig: {
        altFormat: "DD-MM-YYYY",
        altInput: true,
        dateFormat: "DD-MM-YYYY",
        allowInput: true
      },
      currentFormState: 0,
      buttonState: {
        isDisableNhapMoi: false,
        isDisableGhiLai: false,
        isDisableXoaHD: false,
        isDisableGuiMaXT: false,
        isDisableDangKy: false
      },
      loaiHDDKHSSV: 101,
      maTbGoc: "",
      maHD: "",
      ngay_yc: null,
      maGD: "",
      thuebao_id: 0,
      khachhang_id: 0,
      daDangKyHS: false,
      hdKhId: 0,
      hdtbId: 0,
      hdtbDDId: 0,
      danhBa: null,
      danhBaDD: null,
      hopDong: null,
      hopDongDD: null,
      danhBaSVDD: null,
      dsTruongHoc: [],
      dsQuocTich: [],
      dsLoaiGiayTo: [],
      dsLoaiGiayToBaoHo: [],
      dsTinh: [],
      dsTinhBaoHo: [],
      dsLoaiKH: [],
      dsDoiTuongSD: [],
      dsTinhTrangSV: [],
      dsGioiTinh: [],
      options: {
        huyGoi: false,
        isBaoHo: false,
        truongHocId: 0,
        quocTichId: 0,
        quocTichIdNeo: 0,
        loaiGiayToId: null,
        loaiGiayToIdNeo: 0,
        tinhNeo: 0,
        loaiKHId: 0,
        doiTuongSDId: 0,
        tinhTrangSVId: 0,
        maTb: "",
        soSim: "",
        tenGoiCuoc: "",
        tenHSSV: "",
        ngaySinh: null,
        ngayCapGiayTo: null,
        noiCapGiayTo: null,
        soGiayTo: "",
        soCMND: "",
        gioiTinhId: null,
        khoaHoc: "",
        soThe: "",
        ngayCapThe: null,
        lop: "",
        diachiTb: "",
        ghiChu: "",
        ngayYeuCau: null,
        maXacThuc: "",
        ghiChuDD: "",
        nguoiBaoHo: {
          ten: "",
          loaiGiayToId: null,
          loaiGiayToIdNeo: null,
          soGiayTo: null,
          noiCapGiayTo: null,
          ngayCapGiayTo: null,
          diachi: null,
          ngaySinh: null,
          soCMND: ""

        }
      },
      tab: {
        isActiveTabLSDangKyHS: true,
        isActiveTabLSCapNhatTT: false
      },
      dsLichSuDKHSSV: {
        header: [
          { headerText: "Tên TB", fieldName: "TEN_TB" },
          { headerText: "Loại GT", fieldName: "TENLOAI" },
          { headerText: "Số GT", fieldName: "SO_GT" },
          { headerText: "Nơi cấp", fieldName: "NOI_CAP" },
          { headerText: "Ngày cấp", fieldName: "NGAY_CAP_GT" },
          { headerText: "Ngày sinh", fieldName: "NGAY_SINH" },
          { headerText: "Địa chỉ", fieldName: "DIACHI_TB" },
          { headerText: "Tình trạng", fieldName: "NAME" },
          { headerText: "Số thẻ", fieldName: "SO_THE" },
          { headerText: "Lớp", fieldName: "LOP" },
          { headerText: "Ngày cấp thẻ", fieldName: "NGAY_CAP_THE" },
          { headerText: "Người thực hiện", fieldName: "NGUOI_TH" },
          { headerText: "Ngày cập nhật", fieldName: "NGAY_CN" },
          { headerText: "Người sửa đổi", fieldName: "NGUOI_SUA_DOI" },
          { headerText: "Ghi chú", fieldName: "GHI_CHU" }
        ],
        list: []
      },
      dsLichSuCapNhat: {
        header: [
          { headerText: "Ngày CN", fieldName: "REGISTERDATE" },
          { headerText: "Tên TB", fieldName: "FULLNAME" },
          { headerText: "Giới tính", fieldName: "GENDER" },
          { headerText: "Loại GT", fieldName: "TENLOAI" },
          { headerText: "Số GT", fieldName: "IDNUMBER" },
          { headerText: "Nơi cấp", fieldName: "PLACEOFISSUE" },
          { headerText: "Ngày cấp", fieldName: "PLACEDATE" },
          { headerText: "Ngày sinh", fieldName: "BIRTHDAY" },
          { headerText: "Nơi DKHKTT", fieldName: "ADDRESS" },
          { headerText: "Người CN", fieldName: "AGENTID" }
        ],
        list: []
      },
      thongtin_anh: [],
      anh_upload: {
        anh1: {base64: null, path: null, name: "Ảnh 1", file: null, url_minio: null},
        anh2: {base64: null, path: null, name: "Ảnh 2", file: null, url_minio: null},
        anh3: {base64: null, path: null, name: "Ảnh 3", file: null, url_minio: null},
        anh4: {base64: null, path: null, name: "Ảnh 4", file: null, url_minio: null}
      },
      displayStylePopup: "none",
      displayStyleImage: "none",
      anhDaChon: null,
      anhDaChonLocal: null,
      displayStyleImageLocal: "none",
      ds_valid: []
    }
  },
  validations: {
    options: {
      doiTuongSDId: { ComboxSelectedIdConstraint },
      gioiTinhId: { required },
      loaiKHId: { ComboxSelectedIdConstraint },
      truongHocId: { ComboxSelectedIdConstraint },
      tinhTrangSVId: { ComboxSelectedIdConstraint },
      quocTichId: { ComboxSelectedIdConstraint },
      loaiGiayToId: {required: requiredIf(function () {
        return !this.options.isBaoHo
      })},
      noiCapGiayTo: {required: requiredIf(function () {
        return !this.options.isBaoHo
      })},
      ngayCapGiayTo: {required: requiredIf(function () {
        return !this.options.isBaoHo
      })},
      ngayCapThe: {required: requiredIf(function () {
        return !this.options.isBaoHo
      })},
      ngaySinh: {required: requiredIf(function () {
        return !this.options.isBaoHo
      })},
      tenHSSV: {
        required,
        // minLength: minLength(5),
        maxLength: maxLength(100)
      },
      maTb: {
        required,
        // minLength: minLength(8),
        maxLength: maxLength(20)
      },
      soGiayTo: {
        required: requiredIf(function () {
          return !this.options.isBaoHo
        }),
        maxLength: maxLength(100)
      },
      soCMND: {
        required: requiredIf(function () {
          return !this.options.isBaoHo && this.options.loaiGiayToId == 3 // ho chieu
        }),
        maxLength: maxLength(100)
      },

      khoaHoc: {
        required,
        // minLength: minLength(3),
        maxLength: maxLength(32),
        khoaHocConstraintFormat,
        khoaHocConstraintDuration
      },
      soThe: {required, // minLength: minLength(5),
        maxLength: maxLength(32)},

      lop: {
        required,
        maxLength: maxLength(32)
      },
      diachiTb: {
        required,
        maxLength: maxLength(500)
      },
      ghiChu: {
        maxLength: maxLength(500)
      },

      nguoiBaoHo: {
        loaiGiayToId: {required: requiredIf(function () {
          return this.options.isBaoHo
        })},
        noiCapGiayTo: { required: requiredIf(function () { return this.options.isBaoHo }) },
        ngayCapGiayTo: { required: requiredIf(function () { return this.options.isBaoHo }) },
        ngaySinh: {required: requiredIf(function () {
          return this.options.isBaoHo
        })},
        ten: {
          required: requiredIf(function () {
            // eslint-disable-next-line no-console
            console.log("required tenNguoiBaoHO + ", this.options.isBaoHo)
            return this.options.isBaoHo
          }),
          maxLength: maxLength(50)
        },
        soGiayTo: {
          required: requiredIf(function () {
            // eslint-disable-next-line no-console
            console.log("required soGiayToBaoHO + ", this.options.isBaoHo)
            return this.options.isBaoHo
          }),
          maxLength: maxLength(50)
        },
        soCMND: {
          required: requiredIf(function () {
            // eslint-disable-next-line no-console
            console.log("required soCMNDBaoHO + ", this.options.isBaoHo)
            return this.options.isBaoHo && this.options.nguoiBaoHo.loaiGiayToId == 3
          }),
          maxLength: maxLength(50)
        },

        diachi: {
          required: requiredIf(function () {
            console.log("required diachiBaoHO + ", this.options.isBaoHo)
            return this.options.isBaoHo
          }),
          maxLength: maxLength(500)
        }

      }
    }
  },
  watch: {
    currentFormState: function (newState, oldState) {
      this.buttonState.isDisableNhapMoi = this.currentFormState == FormState.NhapMoi
      this.buttonState.isDisableXoaHD = this.currentFormState != FormState.CapNhat
    },
    "options.loaiGiayToId": function (newState, oldState) {
      if (this.options.loaiGiayToId > 0) {
        this.options.loaiGiayToIdNeo = this.dsLoaiGiayTo.filter(item => item.loaigt_id == this.options.loaiGiayToId)[0].loaigt_id_neo
      }
      if (this.options.loaiGiayToId != 3) {
        this.options.soCMND = ""
      }
    },
    "options.nguoiBaoHo.loaiGiayToId": function (newState, oldState) {
      if (this.options.nguoiBaoHo.loaiGiayToId > 0) {
        this.options.nguoiBaoHo.loaiGiayToIdNeo = this.dsLoaiGiayToBaoHo.filter(item => item.loaigt_id == this.options.nguoiBaoHo.loaiGiayToId)[0].loaigt_id_neo
      }
      if (this.options.nguoiBaoHo.loaiGiayToId != 3) {
        this.options.nguoiBaoHo.soCMND = ""
      }
    },
    "options.quocTichId": function (newState, oldState) {
      if (this.options.quocTichId > 0) {
        this.options.quocTichIdNeo = this.dsQuocTich.filter(item => item.quoctich_id == this.options.quocTichId)[0].quoctich_neo_id
      }
    },
    "options.loaiKHId": async function (newState, oldState) {
      if (newState > 0) {
        this.loading(true)
        // this.options.tinhTrangSVId = 0
        // this.options.truongHocId = 0
        await Promise.all([
          this.layDsTinhTrangSV(this.options.loaiKHId),
          this.layDsTruongHoc(this.options.loaiKHId)
        ])
        if (!this.dsTruongHoc || this.dsTruongHoc.length == 0 || !this.dsTruongHoc.some(item => item.TRUONGHOC_ID == this.options.truongHocId)) {
          this.options.truongHocId = 0
        }
        if (!this.dsTinhTrangSV || this.dsTinhTrangSV.length == 0 || !this.dsTinhTrangSV.some(item => item.TT_HSSV_ID == this.options.tinhTrangSVId)) {
          this.options.tinhTrangSVId = 0
        }
        this.loading(false)
      }
    },
    "options.isBaoHo": function (newState, oldState) {
      console.log(`isBaoHo changed newState = ${newState} oldState = ${oldState}`)
    },
    "tab.isActiveTabLSDangKyHS": function (newState, oldState) {
      this.tab.isActiveTabLSCapNhatTT = !this.tab.isActiveTabLSDangKyHS
    }

  },
  async created () {
    this.loading(true)
    try {
      this.clearForm()
      await this.layDanhMucDKGoiSV()
      this.layDSGioiTinh()
      this.thongtin_anh = []
      this.khoitao_anh()
    } catch (ex) {
      console.log(ex.message)
    }
    this.loading(false)
  },
  mounted () {},
  methods: {
    ganTenAnh (loai) {
      if (loai == 1) { return "Ảnh CMT mặt trước" } else if (loai == 2) { return "Ảnh CMT mặt sau" } else if (loai == 3) { return "Ảnh người đăng ký " } else if (loai == 4) { return "Ảnh phiếu đang ký" }
    },
    khoitao_anh () {
      this.thongtin_anh = []
      for (var i = 1; i <= 4; i++) {
        var anh = {}
        anh.image_base = null
        anh.image_name = this.ganTenAnh(i)
        anh.image_file = null //
        anh.type = i
        this.thongtin_anh.push(anh)
      }
    },
    layDSGioiTinh () {
      this.dsGioiTinh.push({gioitinh_id: 0, gioitinh: "Nữ"})
      this.dsGioiTinh.push({gioitinh_id: 1, gioitinh: "Nam"})
    },
    getData (response) {
      if (
        response.data.error === 200 ||
        response.data.error_code === "BSS-00000000"
      ) {
        return response.data.data
      } else {
        this.$toast.error("Không lấy được dữ liệu")
        console.log(response.data.error_code)
      }
      return []
    },
    async layDsTruongHoc (loaiSVId) {
      try {
        var response = await api.lay_danhmuc_tr_tt_dk_goi_hssv(this.axios, {
          ID: loaiSVId
        })
        this.dsTruongHoc = this.getData(response)
        console.log("dsTruongHoc len = " + this.dsTruongHoc.length)
      } catch (ex) {
        this.$toast.error(
          "Có lỗi xảy ra khi lấy danh mục trường: " + ex.message
        )
      }
    },
    async layDsTinhTrangSV (loaiSVId) {
      try {
        var response = await api.lay_danhmuc_tr_tt_dk_goi_hssv_tinhtrang(
          this.axios,
          { ID: loaiSVId }
        )
        this.dsTinhTrangSV = this.getData(response)
        console.log("dsTinhTrangHS len = " + this.dsTinhTrangSV.length)
      } catch (ex) {
        this.$toast.error(
          "Có lỗi xảy ra khi lấy danh mục tinh trang sv: " + ex.message
        )
      }
    },
    async layDanhMucDKGoiSV () {
      try {
        var response = await api.lay_danhmuc_tt_dk_goi_hssv(this.axios)
        var danhMuc = this.getData(response)
        this.dsQuocTich = danhMuc.quoctich
        // this.dsLoaiGiayTo = danhMuc.loai_gt
        for (var i = 0; i < danhMuc.loai_gt.length; i++) {
          var currentItem = danhMuc.loai_gt[i]
          var vLoaiGTID = await this.layLoaiGiayToId(currentItem.loaigt_id_neo)
          this.dsLoaiGiayTo.push({ "loaigt_id": vLoaiGTID, "loaigt_id_neo": currentItem.loaigt_id_neo, "tenloai_gt": currentItem.tenloai_gt })
          // this.dsLoaiGiayToBaoHo.push({ "loaigt_id": vLoaiGTID, "loaigt_id_neo": currentItem.loaigt_id_neo, "tenloai_gt": currentItem.tenloai_gt })
        }
        console.log("dsLoaiGiayToBaoHo", this.dsLoaiGiayToBaoHo)

        this.dsTinh = danhMuc.tinh_neo
        this.dsLoaiKH = danhMuc.loai_kh
        danhMuc.doituong_sd.forEach(item => {
          if (!this.dsDoiTuongSD.some(dt => dt.doituongsd_id == item.doituongsd_id)) {
            this.dsDoiTuongSD.push(item)
          }
        })
        this.dsTinhBaoHo = structuredClone(this.dsTinh)
        this.dsLoaiGiayToBaoHo = structuredClone(this.dsLoaiGiayTo)
      } catch (ex) {
        this.$toast.error(
          "Có lỗi xảy ra khi lấy danh mục trường: " + ex.message
        )
        console.log(ex)
      }
    },
    async ThongBao (msg, title) {
      if (!title) title = "Lỗi dữ liệu"
      this.$alert(msg
        , title,
        {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",

          type: "info"

        }
      )
    },

    async kiemtra_thuebao_lap_hopdong_khac () {
      var result = await service.kiemtra_thuebao_lap_hopdong_khac(this.options.maTb, LoaiHopDong.DD_HSSV)
      if (!result.isValid) { this.ThongBao(result.message, "Không được phép") }
      return result.isValid
    },
    async layLoaiGiayToId (vLoaiGiayToIDNeo) {
      try {
        var body = {
          param: vLoaiGiayToIDNeo,
          type: 1
        }
        var response = await api.fn_tt_loai_gt(this.axios, body)
        return parseInt(this.getData(response))
      } catch (e) {
        console.log(e)
        this.$toast.error("Co loi xay ra: " + e.message)
        throw e
      }
    },
    onTruongHocSelectedChanged () {
      console.log(
        "onTruongHocSelectedChanged: truongHocId = " + this.options.truongHocId
      )
    },
    // async onLoaiSVChanged () {
    //   console.log("loaiSVId = " + this.options.loaiKHId)
    //   var task1 = this.layDsTinhTrangSV(this.options.loaiKHId)
    //   var task2 = this.layDsTruongHoc(this.options.loaiKHId)
    //   await Promise.all([task1, task2])
    // },
    async onNhapMoiClicked () {
      console.log("onNhapMoiClicked")
      this.currentFormState = 1
      this.options.maTb = ""
      this.clearForm()
    },
    validAll () {
      this.ds_valid = []
      this.$v.$touch()
      this.validateFormData()
      this.validCombox()

      if (this.ds_valid.length > 0) {
        this.ds_valid.sort((validA, validB) => { return validA.order - validB.order })
        console.log("valid sorted: ", this.ds_valid)
        var isAlreadyFocus = false
        this.ds_valid.forEach(item => {
          this.$toast.error(item.msg)
          if (item.ref && !isAlreadyFocus) {
            console.log("focused : " + item.msg)
            item.ref.focus()
            isAlreadyFocus = true
          }
        }
        )
        return false
      }
      return true
    },
    validateFormData () {
      var vmsg = null
      var vref = null

      if (!this.$v.options.ghiChu.maxLength) {
        this.ds_valid.push({msg: "Ghi chú không được quá 500 ký tự",
          ref: this.$refs.ghiChu,
          order: ValidOrder.GhiChu
        })
      }
      if (!this.$v.options.diachiTb.required) {
        this.ds_valid.push({msg: "Địa chỉ thuê bao không được để trống",
          ref: this.$refs.diachiTb,
          order: ValidOrder.DiaChiTb
        })
      } else if (!this.$v.options.diachiTb.maxLength) {
        vmsg = "Địa chỉ thuê bao không được quá 500 ký tự"
        vref = this.$refs.diachiTb
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.DiaChiTb})
      }
      if (!this.$v.options.lop.required) {
        vmsg = "Lớp không được để trống"
        vref = this.$refs.lop
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.Lop})
      } else if (!this.$v.options.lop.maxLength) {
        vmsg = "Lớp không được quá 32 ký tự"
        vref = this.$refs.lop
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.Lop})
      }
      if (!this.$v.options.soThe.required) {
        vmsg = "Số thẻ không được để trống"
        vref = this.$refs.soThe
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.SoThe})
      } else if (!this.$v.options.soThe.maxLength) {
        vmsg = "Số thẻ không được quá 32 ký tự"
        vref = this.$refs.soThe
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.SoThe})
      }
      if (!this.$v.options.khoaHoc.required) {
        vmsg = "Khoá học không được để trống"
        vref = this.$refs.khoaHoc
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.KhoaHoc})
      } else if (!this.$v.options.khoaHoc.maxLength) {
        vmsg = "Khoá học không được quá 32 ký tự"
        vref = this.$refs.khoaHoc
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.KhoaHoc})
      } else if (!this.$v.options.khoaHoc.khoaHocConstraintFormat) {
        vmsg = "Khoá học phải có dạng 20yy-20yy ví dụ 2007-2012"
        vref = this.$refs.khoaHoc
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.KhoaHoc})
      } else if (!this.$v.options.khoaHoc.khoaHocConstraintDuration) {
        var startYear = parseInt(this.options.khoaHoc.split("-")[0])
        var endYear = parseInt(this.options.khoaHoc.split("-")[1])
        if (endYear - startYear > 8) {
          vmsg = "Khóa học kéo da tối đa 8 năm "
          vref = this.$refs.khoaHoc
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.KhoaHoc})
        } else if (startYear > endYear) {
          vmsg = "Khoá học: Thời gian kết thúc phải lớn hơn thời gian bắt đầu"
          vref = this.$refs.khoaHoc
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.KhoaHoc})
        }
      }

      if (!this.$v.options.tenHSSV.required) {
        vmsg = "Tên HSSV không được để trống"
        vref = this.$refs.tenHSSV
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.TenHSSV})
      } else if (!this.$v.options.tenHSSV.maxLength) {
        vmsg = "Tên HSSV không được quá 50 ký tự"
        vref = this.$refs.tenHSSV
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.TenHSSV})
      }
      if (!this.$v.options.maTb.required) {
        vref = this.$refs.maTb
        vmsg = "Số máy không được để trống"
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.SoTb})
      } else if (!this.$v.options.maTb.maxLength) {
        vmsg = "Số máy không được quá 50 ký tự"
        vref = this.$refs.maTb
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.SoTb})
      }

      if (this.options.isBaoHo) {
        if (!this.$v.options.nguoiBaoHo.ten.required) {
          vmsg = "Tên người bảo hộ không được để trống"
          vref = this.$refs.tenNguoiBaoHo
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.HoTenNguoiBaoHo})
        } else if (!this.$v.options.nguoiBaoHo.ten.maxLength) {
          vmsg = "Tên người bảo hộ không được quá 50 ký tự"
          vref = this.$refs.tenNguoiBaoHo
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.HoTenNguoiBaoHo})
        }

        if (!this.$v.options.nguoiBaoHo.soGiayTo.required) {
          vmsg = "Số giấy tờ người bảo hộ không được để trống"
          vref = this.$refs.soGiayToNguoiBaoHo
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.SoGiayToNguoiBaoHo})
        } else if (!this.$v.options.nguoiBaoHo.soGiayTo.maxLength) {
          vmsg = "Số giấy tờ người bảo hộ không được quá 100 ký tự"
          vref = this.$refs.soGiayToNguoiBaoHo
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.SoGiayToNguoiBaoHo})
        }

        if (!this.$v.options.nguoiBaoHo.soCMND.required) {
          vmsg = "Số CMND người bảo hộ không được để trống"
          vref = this.$refs.soCMNDNguoiBaoHo
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.SoCMNDNguoiBaoHo})
        } else if (!this.$v.options.nguoiBaoHo.soCMND.maxLength) {
          vmsg = "Số CMND người bảo hộ không được quá 100 ký tự"
          vref = this.$refs.soCMNDNguoiBaoHo
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.SoCMNDNguoiBaoHo})
        }

        if (!this.$v.options.nguoiBaoHo.diachi.required) {
          vmsg = "Địa chỉ người bảo hộ không được để trống"
          vref = this.$refs.diachiNguoiBaoHo
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.diachiNguoiBaoHo})
        } else if (!this.$v.options.nguoiBaoHo.soCMND.maxLength) {
          vmsg = "Địa chỉ người bảo hộ không được quá 500 ký tự"
          vref = this.$refs.diachiNguoiBaoHo
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.diachiNguoiBaoHo})
        }
      } else {
        if (!this.$v.options.soGiayTo.required) {
          vmsg = "Số giấy tờ không được để trống"
          vref = this.$refs.soGiayTo
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.SoGiayTo})
        } else if (!this.$v.options.soGiayTo.maxLength) {
          vmsg = "Số giấy tờ không được quá 100 ký tự"
          vref = this.$refs.soGiayTo
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.SoGiayTo})
        }

        if (!this.$v.options.soCMND.required) {
          vmsg = "Số CMND không được để trống"
          vref = this.$refs.soCMND
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.SoCMND})
        } else if (!this.$v.options.soCMND.maxLength) {
          vmsg = "Số CMND không được quá 100 ký tự"
          vref = this.$refs.soCMND
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.SoCMND})
        }
      }
    },
    async onGhiLaiClicked () {
      try {
        if (this.currentFormState == FormState.CapNhat && this.maTbGoc != this.options.maTb) {
          this.ThongBao("Số điện thoại đã nhập khác thông tin thuê bao tìm kiếm", "Có lỗi")
          return false
        }
        if (!this.validAll()) {
          return false
        }

        this.loading(true)
        if (this.currentFormState == FormState.NhapMoi) {
          var isChuaCoHopDong = await this.kiemtra_thuebao_lap_hopdong_khac()
          if (!isChuaCoHopDong) { return }
          var resultThemMoi = await this.themmoi_hopdong_dd()
          if (resultThemMoi.isSuccess) {
            this.currentFormState = FormState.CapNhat
            this.$toast.success("Đã thêm mới hợp đồng thành công")
            await this.onMaGDEnter()
          } else {
            this.$toast.error("Có lỗi thêm mới hợp đồng " + resultThemMoi.error_msg)
          }
        } else {
          var resultCapNhat = await this.capnhat_hopdong_dd()
          if (resultCapNhat.isSuccess) {
            this.$toast.success("Đã cập nhật hợp đồng thành công")
            return true
          } else {
            this.$toast.error("Có lỗi cập nhật hợp đồng " + resultCapNhat.error_msg)
            return false
          }
        }
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra " + e.message)
        return false
      } finally {
        this.loading(false)
      }
    },

    async lay_danhba_theo_matb () {
      try {
        var body = {
          "dichVuVtId": 2,
          "donViDlId": 0,
          "maTb": this.options.maTb
        }
        var response = await api.lay_danhba_theo_matb(this.axios, body)
        return this.getData(response)
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra " + e.message)
        console.error(e)
        throw e
      }
    },
    async lay_ds_db_dd_theo_tbid (vThuebaoId) {
      try {
        var body = {
          "ID": vThuebaoId
        }
        var response = await api.lay_ds_db_dd_theo_tbid(this.axios, body)
        return this.getData(response)
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra " + e.message)
        console.error(e)
        throw e
      }
    },

    hienThiThongTinHopDong () {
      try {
        this.hienThiThongTinHopDongKhachHang()
        this.decodeThongTinHSSV(this.hopDongDD)
      } catch (e) {
        throw e
      }
    },
    hienThiThongTinHopDongKhachHang () {
      if (this.hopDong.kieuld_id == 957) { this.daDangKyHS = true } else { this.daDangKyHS = false }
      this.maTbGoc = this.hopDong.ma_tb
      this.options.maTb = this.hopDong.ma_tb
      this.maGD = this.hopDong.ma_gd
      this.maHD = this.hopDong.ma_hd
      this.hdKhId = this.hopDong.hdkh_id
      this.hdtbId = this.hopDong.hdtb_id
      this.thuebao_id = this.hopDong.thuebao_id
      this.options.ghiChu = this.hopDong.ghichu
      this.options.diachiTb = this.hopDong.diachi_tb
      this.options.tenHSSV = this.hopDong.ten_tb
      this.options.noiCapGiayTo = this.hopDong.noicap
      this.options.soGiayTo = this.hopDong.so_gt
      this.options.loaiGiayToId = this.hopDong.loaigt_id
      if (this.hopDong.ngaycap) {
        this.options.ngayCapGiayTo = moment(this.hopDong.ngaycap, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY")
      }
      if (this.hopDong.ngay_sn) {
        this.options.ngaySinh = moment(this.hopDong.ngay_sn, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY")
      }
      if (this.hopDong.ngay_yc) {
        this.ngay_yc = moment(this.hopDong.ngay_yc, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY")
      }
      this.options.gioiTinhId = this.hopDong.gioitinh
      this.options.quocTichId = this.hopDong.quoctich_id
      this.options.soSim = this.danhBaDD.soSim
      this.options.tenGoiCuoc = this.danhBaDD.tengoicuoc
    },

    async hienThiThongTinDanhBa () {
      try {
        // var dataCCBS = await serviceTest.tt_7_dk_hssv_lay_thongtin(this.options.maTb.trim())
        // if (!dataCCBS.error_msg || dataCCBS.data.length == 0) {
        //   console.log("Không lấy được thông tin thuê bao từ CCBS, error_msg = " + dataCCBS.error_msg)
        // } else {
        //   dataCCBS = dataCCBS.data[0]
        //   this.daDangKyHS = parseInt(dataCCBS.ID_PKG) > 0
        //   if (this.daDangKyHS) {
        //     this.options.khoaHoc = dataCCBS.KHOA_HOC
        //     this.options.soThe = dataCCBS.SO_THE
        //     this.options.ngayCapThe = dataCCBS.NGAY_CAP_THE
        //     this.options.loaiKHId = dataCCBS.CUSTOMER_USE_ID
        //     this.options.tinhTrangSVId = dataCCBS.ID_TINHTRANG
        //     this.options.truongHocId = dataCCBS.ID_TRUONG
        //     this.options.lop = dataCCBS.LOP
        //   }
        // }

        this.danhBa = await this.lay_danhba_theo_matb()
        if (!this.danhBa) {
          this.$toast.error("Không lấy được thông tin số máy " + this.options.maTb + " trong danh bạ, Hãy liên hệ admin để xử lý")
          this.loading(false)
          return
        }
        // eslint-disable-next-line no-console
        console.log("danhBa=", this.danhBa)
        this.maHD = this.danhBa.ma_hd
        this.maTbGoc = this.danhBa.ma_tb
        this.options.tenHSSV = this.danhBa.ten_tb
        this.options.diachiTb = this.danhBa.diachi_tb
        this.thuebao_id = this.danhBa.thuebao_id
        this.khachhang_id = this.danhBa.khachhang_id
        this.options.loaiGiayToId = this.danhBa.loaigt_id
        this.options.noiCapGiayTo = this.danhBa.noicap
        this.options.soGiayTo = this.danhBa.so_gt
        if (this.danhBa.ngaycap) {
          this.options.ngayCapGiayTo = moment(this.danhBa.ngaycap, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY")
        }
        this.options.quocTichId = this.danhBa.quoctich_id
        this.options.gioiTinhId = this.danhBa.gioitinh
        this.options.tenGoiCuoc = this.danhBa.tengoi_cuoc
        if (this.danhBa.ngay_sn) {
          this.options.ngaySinh = moment(this.danhBa.ngay_sn, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY")
        }
        var danhBaDDtmp = await this.lay_ds_db_dd_theo_tbid(this.thuebao_id)
        this.danhBaDD = danhBaDDtmp[0]
        // eslint-disable-next-line no-console
        console.log("this.danhBaDD:, thuebao_id", this.danhBaDD, this.danhBa.thuebao_id)
        if (!this.danhBaDD) return
        this.options.soSim = this.danhBaDD.simcard
        this.options.tenGoiCuoc = this.danhBaDD.tengoicuoc
        this.options.ghiChuDD = this.danhBaDD.ghichu

        var responseTTSV = await service.lay_thongtin_sinhvien_dd(KieuLay.DANHBA, this.danhBa.thuebao_id)
        if (!responseTTSV.error_msg) {
          this.danhBaSVDD = responseTTSV.data[0]
          if (this.danhBaSVDD.loaisv_id_neo > 0) {
            this.daDangKyHS = true
            this.decodeThongTinHSSV(this.danhBaSVDD)
          }
        }
      } catch (e) {
        console.error(e)
        throw e
      }
    },
    async onMaGDEnter () {
      try {
        if (!this.maGD || this.maGD.length < 3) {
          return
        }

        this.loading(true)
        this.currentFormState = FormState.CapNhat
        var responseHopdong = await service.lay_dshd_theo_ma_gd(this.maGD)
        if (responseHopdong.error_msg) {
          this.$toast.warning("Không tìm thấy hợp đồng tương ứng với mã GD đã chọn")
          return
        }
        this.clearForm()
        this.hopDong = responseHopdong.data[0]
        console.log("hopdong.thuebao_id = ", this.hopDong.thuebao_id)

        var responseHopdongDD = await service.lay_thongtin_sinhvien_dd(KieuLay.HOPDONG, this.hopDong.hdtb_id)
        if (responseHopdongDD.error_msg) {
          this.$toast.error("Co loi xay ra khi lay thong tin hop dong di dong" + responseHopdongDD.error_msg)
          return
        }
        this.hopDongDD = responseHopdongDD.data[0]

        var dsDanhBaDD = await this.lay_ds_db_dd_theo_tbid(this.hopDong.thuebao_id)
        this.danhBaDD = dsDanhBaDD[0]

        this.hienThiThongTinHopDong()
      } catch (ex) {
        this.$toast.error("Có lỗi xảy ra " + ex.message)
      } finally {
        this.loading(false)
      }
    },
    async onMaTbEnter () {
      try {
        this.loading(true)
        console.log("ma_tb = ", this.options.maTb, "loaiGTID =", this.options.loaiGiayToId)
        var isValid = await this.kiemtra_thuebao_lap_hopdong_khac()
        if (!isValid) return
        this.clearForm()
        this.currentFormState = FormState.NhapMoi
        this.hienThiThongTinDanhBa()
      } catch (e) {
        console.error(e)
        this.$toast.error("Có lỗi xảy ra trong quá trình lấy dữ liệu " + e.message)
      } finally {
        this.loading(false)
      }
    },
    async onXoaClicked () {
      try {
        console.log("hdKhId xoa = ", this.hdKhId)
        if (this.hdKhId <= 0) {
          this.$toast.warning("Không có hợp đồng để xoá")
          return
        }
        var isConfirm = await this.$bvModal.msgBoxConfirm("Bạn thật sự muốn xoá hợp đồng này không?",
          {
            title: "",
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy"
          })
        if (!isConfirm) return
        this.loading(true)
        var xoaResult = await service.xoa_hopdong_khachhang(this.hdKhId, LoaiHopDong.DD_HSSV)
        if (xoaResult.error_msg) {
          this.$toast.error("Có lỗi xoá hợp đồng " + xoaResult.error_msg)
          return
        }
        this.$toast.success("Đã xoá thành công hợp đồng")
        this.options.maTb = ""
        this.clearForm()
        this.FormState = FormState.CapNhat
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra " + e.message)
      } finally {
        this.loading(false)
      }
    },
    async onLayLichSuDKHSSVClicked () {
      if (!this.options.maTb || this.options.maTb.length < 9) return
      this.tab.isActiveTabLSDangKyHS = true
      try {
        this.loading(true)
        var response = await service.tt_7_dk_hssv_lskm(this.options.maTb)
        this.dsLichSuDKHSSV.list = []
        this.dsLichSuCapNhat.list = []
        if (!response.error_msg) {
          this.dsLichSuDKHSSV.list = response.data
        } else {
          this.$toast.error("Không lấy được Lịch sử DKHSSV: " + response.error_msg)
        }
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra khi lấy Lịch sử DKHSSV" + e.message)
      } finally {
        this.loading(false)
      }
    },
    async onLichSuDKTabClicked () {
      this.tab.isActiveTabLSDangKyHS = true
      if (!this.options.maTb || this.options.maTb.length < 9) return

      if (!this.dsLichSuDKHSSV.list) {
        try {
          this.loading(true)
          var responseLS = await service.tt_7_dk_hssv_lskm(this.options.maTb)
          if (!responseLS.error_msg) {
            this.dsLichSuDKHSSV.list = responseLS.data
          } else {
            this.$toast.error("Không lấy được Lịch sử DKHSSV: " + responseLS.error_msg)
          }
        } catch (e) {
          this.$toast.error("Có lỗi lấy Lịch sử DKHSSV: " + e.message)
        } finally {
          this.loading(false)
        }
      }
    },
    async onLichSuCapNhatTabClicked () {
      this.tab.isActiveTabLSDangKyHS = false
      if (!this.options.maTb || this.options.maTb.length < 9) { return }
      if (!this.dsLichSuCapNhat.list || this.dsLichSuCapNhat.list.length == 0) {
        try {
          this.loading(true)
          var responseLS = await service.ts_lichsu_capnhat_thuebao_tratruoc(this.options.maTb)
          if (!responseLS.error_msg) {
            this.dsLichSuCapNhat.list = responseLS.data
          } else {
            this.$toast.error("Không lấy được Lịch sử Cập nhật: " + responseLS.error_msg)
          }
        } catch (e) {
          this.$toast.error("Không lấy được Lịch sử Cập nhật: " + e.message)
        } finally {
          this.loading(false)
        }
      }
    },
    async themmoi_hopdong_dd () {
      try {
        var responseHdkh = await service.get_keys({"keyname": "hd_khachhang"})
        if (!responseHdkh.error_msg && responseHdkh.data > 0) {
          this.hdKhId = responseHdkh.data
        } else {
          this.$toast.error("Có lỗi sinh key hdkh " + responseHdkh.error_msg)
          return
        }
        var responseHdTb = await service.get_keys({"keyname": "hd_thuebao"})
        if (!responseHdTb.error_msg && responseHdTb.data > 0) {
          this.hdtbId = responseHdTb.data
        } else {
          this.$toast.error("Có lỗi sinh key hdtb " + responseHdTb.error_msg)
          return
        }

        var responseMagd = await service.sinh_magd_v2({ "vdonvi_id": 0, "vloaihd_id": LoaiHopDong.DD_HSSV })
        if (!responseMagd.error_msg && responseMagd.data) {
          this.maGD = responseMagd.data
        } else {
          this.$toast.error("Có lỗi xảy ra trong khi sinh key, hãy liên hệ admin để xử lý")
          return
        }
        this.ngay_yc = moment(new Date()).format("DD/MM/YYYY")
        var body = {
          "hopdong_khachhangs": [
            {
              "BOSUNGTB_ID": 0,
              "CHUCDANH_DD": null,
              "CTV_ID": 0,
              "DACTRUNG": this.danhBa.dactrung,
              "DANTOC_ID": this.danhBa.dantoc_id,
              "DIACHI_KH": this.danhBa.diachi_kh,
              "DIACHI_NDD": this.danhBa.diahci_ndd,
              "DONVI_ID": this.danhBa.donvi_id,
              "EMAIL": this.danhBa.email,
              "GHICHU": this.options.ghiChu,
              "GIOITINH": this.options.gioiTinhId,
              "HDKH_CHA_ID": 0,
              "HDKH_ID": this.hdKhId,
              "HOKHAU": this.danhBa.hokhau,
              "HTTTHI_ID": this.danhBa.httt_id,
              "IP_CN": null,
              "KENHTN_ID": 0,
              "KHACHHANG_ID": this.danhBa.khachhang_id,
              "KHLON_ID": this.danhBa.khlon_id,
              "KH_KT": this.danhBa.kh_kt3,
              "KIEUHD_ID": 0,
              "LOAIGT_ID": this.options.isBaoHo ? this.options.nguoiBaoHo.loaiGiayToId : this.options.loaiGiayToId,
              "LOAIHD_ID": LoaiHopDong.DD_HSSV,
              "LOAIKH_ID": this.danhBa.loaikh_id,
              "MAY_CN": this.$root.token.getUserName(),
              "MA_DUAN": null,
              "MA_GD": this.maGD,
              "MA_HD": this.danhBa.ma_hd,
              "MA_KH": this.danhBa.ma_kh,
              "MST": this.danhBa.mst,
              "MST_KH": null,
              "NGANHANG_ID": this.danhBa.nganhang_id,
              "NGANHNGHE_ID": this.danhBa.nganhnghe_id,
              "NGAYCAP": this.options.ngayCapGiayTo,
              "NGAYHETHAN_GT": null,
              "NGAYLAP_HD": moment(this.danhBa.ngaylap_hd, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY"),
              "NGAY_CN": moment(new Date()).format("DD/MM/YYYY"),
              "NGAY_HK": null,
              "NGAY_SN": this.options.isBaoHo ? this.options.nguoiBaoHo.ngaySinh : this.options.ngaySinh,
              "NGAY_YC": this.ngay_yc,
              "NGUOI_CN": this.$root.token.getUserName(),
              "NGUOI_DD": this.danhBa.nguoi_dd,
              "NHANVIENGT_ID": 0,
              "NHANVIEN_ID": this.danhBa.nhanvien_id,
              "NOICAP": this.options.isBaoHo ? this.options.nguoiBaoHo.noiCapGiayTo : this.options.noiCapGiayTo,
              "NOICAP_HK": this.danhBa.noicap_hk,
              "PHANLOAIKH_ID": this.danhBa.phanloaikh_id,
              "PHIEUCSKH_ID": 0,
              "QUOCTICH_ID": this.options.quocTichId,
              "SO_DT": this.danhBa.so_dt,
              "SO_FAX": null,
              "SO_GT": this.options.isBaoHo ? this.options.nguoiBaoHo.soGiayTo : this.options.soGiayTo,
              "STK": this.danhBa.stk,
              "TEN_KH": this.danhBa.ten_kh,
              "TNC1": this.danhBa.tnc1_id,
              "TNC2": this.danhBa.tnc2_id,
              "TNC3": this.danhBa.tnc3_id,
              "TNC4": this.danhBa.tnc4_id,
              "WEBSITE": this.danhBa.website
            }
          ],
          "hopdong_thuebao_dds": [
            {
              "CHUQUAN_ID": this.danhBa.chuquan_id,
              "DATCOC_CS": 0,
              "DOITUONGSD_ID_NEO": this.options.doiTuongSDId,
              "DOITUONG_ID_NEO": this.danhBaDD.doituong_id_neo,
              "GHICHU": this.encodeThongTinHSSV(),
              "GOICUOC_ID": this.danhBaDD.goicuoc_id,
              "HDTB_ID": this.hdtbId,
              "KIEU_TL": null,
              "LOAISV_ID_NEO": this.options.loaiKHId,
              "MA_GD_NEO": null,
              "SIMCARD": this.danhBaDD.simcard,
              "SOMAY_NTK": null,
              "TIEN_DTK": null,
              "TINHKHAC": 0,
              "TRANGBI_ID": this.danhBaDD.trangbi_id,
              "TRUONGHOC_ID_NEO": this.options.truongHocId
            }
          ],
          "hopdong_thuebaos": [
            {
              "CHUYENMANG": 0,
              "DIACHI_LD": this.danhBa.diachi_ld,
              "DIACHI_TB": this.options.diachiTb,
              "DICHVUVT_ID": this.danhBa.dichvuvt_id,
              "DOITUONG_CT_ID": 0,
              "DOITUONG_ID": this.danhBa.doituong_id,
              "DONVI_CUOI_ID": 0,
              "DONVI_DAU_ID": 0,
              "DONVI_ID": this.danhBa.donvi_id,
              "GHICHU": this.options.ghiChu,
              "GOIKT_ID": 0,
              "HDKH_ID": this.hdKhId,
              "HDTB_CHA_ID": 0,
              "HDTB_ID": this.hdtbId,
              "HDTT_ID": 0,
              "HTCM_ID": 0,
              "IP_CN": null,
              "KIEULD_ID": this.daDangKyHS ? KieuLapDat.HUYGOI_HSSSV : KieuLapDat.THAYDOI_TT_THUEBAO,
              "KIEU_TB": null,
              "LOAITB_ID": this.danhBa.loaitb_id,
              "MAY_CN": this.$root.token.getUserName(),
              "MA_TB": this.danhBa.ma_tb,
              "MUCUOCTB_ID": this.danhBa.mucuoctb_id,
              "MUCUOC_TB": this.danhBa.mucuoc_tb,
              "NGAY_CN": moment(new Date()).format("DD/MM/YYYY"),
              "NGAY_HH_TD": null,
              "NGAY_HT": null,
              "NGAY_SN": this.options.ngaySinh,
              "NGUOI_CN": this.$root.token.getUserName(),
              "NHACC_CU_ID": 0,
              "QUYTRINH_ID": 0,
              "SOLUONG": 0,
              "SOTHANG_CK": 0,
              "STATUS": 0,
              "TBHD_PL": 0,
              "TEN_TB": this.options.tenHSSV,
              "TG_THUE_DEN": null,
              "TG_THUE_TU": null,
              "THUEBAO_CHA_ID": 0,
              "THUEBAO_ID": this.danhBa.thuebao_id,
              "TRANGTHAIYC_ID": 0,
              "TTHD_ID": TrangThaiHD.MOI
            }
          ],
          "huonggiao_id": 5600,
          "tinhkhac": 0
        }
        var isSuccess = await service.themmoi_hopdong_dd(body)
        return isSuccess
      } catch (e) {
        console.error(e)
        throw e
      }
    },

    async capnhat_hopdong_dd () {
      try {
        var body = {
          "hopdong_khachhangs": [
            {
              "BOSUNGTB_ID": 0,
              "CHUCDANH_DD": null,
              "CTV_ID": 0,
              "DACTRUNG": this.hopDong.dactrung,
              "DANTOC_ID": this.hopDong.dantoc_id,
              "DIACHI_KH": this.hopDong.diachi_kh,
              "DIACHI_NDD": this.hopDong.diahci_ndd,
              "DONVI_ID": this.hopDong.donvi_id,
              "EMAIL": this.hopDong.email,
              "GHICHU": this.options.ghiChu,
              "GIOITINH": this.options.gioiTinhId,
              "HDKH_CHA_ID": 0,
              "HDKH_ID": this.hdKhId,
              "HOKHAU": this.hopDong.hokhau,
              "HTTTHI_ID": this.hopDong.httt_id,
              "IP_CN": null,
              "KENHTN_ID": 0,
              "KHACHHANG_ID": this.hopDong.khachhang_id,
              "KHLON_ID": this.hopDong.khlon_id,
              "KH_KT": this.hopDong.kh_kt3,
              "KIEUHD_ID": 0,
              "LOAIGT_ID": this.options.isBaoHo ? this.options.nguoiBaoHo.loaiGiayToId : this.options.loaiGiayToId,
              "LOAIHD_ID": LoaiHopDong.DD_HSSV,
              "LOAIKH_ID": this.hopDong.loaikh_id,
              "MAY_CN": this.$root.token.getUserName(),
              "MA_DUAN": null,
              "MA_GD": this.maGD,
              "MA_HD": this.hopDong.ma_hd,
              "MA_KH": this.hopDong.ma_kh,
              "MST": this.hopDong.mst,
              "MST_KH": null,
              "NGANHANG_ID": this.hopDong.nganhang_id,
              "NGANHNGHE_ID": this.hopDong.nganhnghe_id,
              "NGAYCAP": this.options.ngayCapGiayTo,
              "NGAYHETHAN_GT": null,
              "NGAYLAP_HD": moment(this.hopDong.ngaylap_hd, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY"),
              "NGAY_CN": moment(new Date()).format("DD/MM/YYYY"),
              "NGAY_HK": null,
              "NGAY_SN": this.options.isBaoHo ? this.options.nguoiBaoHo.ngaySinh : this.options.ngaySinh,
              "NGAY_YC": this.ngay_yc,
              "NGUOI_CN": this.$root.token.getUserName(),
              "NGUOI_DD": this.hopDong.nguoi_dd,
              "NHANVIENGT_ID": 0,
              "NHANVIEN_ID": this.hopDong.nhanvien_id,
              "NOICAP": this.options.isBaoHo ? this.options.nguoiBaoHo.noiCapGiayTo : this.options.noiCapGiayTo,
              "NOICAP_HK": this.hopDong.noicap_hk,
              "PHANLOAIKH_ID": this.hopDong.phanloaikh_id,
              "PHIEUCSKH_ID": 0,
              "QUOCTICH_ID": this.options.quocTichId,
              "SO_DT": this.hopDong.so_dt,
              "SO_FAX": null,
              "SO_GT": this.options.isBaoHo ? this.options.nguoiBaoHo.soGiayTo : this.options.soGiayTo,
              "STK": this.hopDong.stk,
              "TEN_KH": this.hopDong.ten_kh,
              "TNC1": this.hopDong.tnc1_id,
              "TNC2": this.hopDong.tnc2_id,
              "TNC3": this.hopDong.tnc3_id,
              "TNC4": this.hopDong.tnc4_id,
              "WEBSITE": this.hopDong.website
            }
          ],
          "hopdong_thuebao_dds": [
            {
              "CHUQUAN_ID": this.hopDong.chuquan_id,
              "DATCOC_CS": 0,
              "DOITUONGSD_ID_NEO": this.options.doiTuongSDId,
              "DOITUONG_ID_NEO": this.danhBaDD.doituong_id_neo,
              "GHICHU": this.encodeThongTinHSSV(),
              "GOICUOC_ID": this.danhBaDD.goicuoc_id,
              "HDTB_ID": this.hdtbId,
              "KIEU_TL": null,
              "LOAISV_ID_NEO": this.options.loaiKHId,
              "MA_GD_NEO": null,
              "SIMCARD": this.danhBaDD.simcard,
              "SOMAY_NTK": null,
              "TIEN_DTK": null,
              "TINHKHAC": 0,
              "TRANGBI_ID": this.danhBaDD.trangbi_id,
              "TRUONGHOC_ID_NEO": this.options.truongHocId
            }
          ],
          "hopdong_thuebaos": [
            {
              "CHUYENMANG": 0,
              "DIACHI_LD": this.hopDong.diachi_ld,
              "DIACHI_TB": this.options.diachiTb,
              "DICHVUVT_ID": this.hopDong.dichvuvt_id,
              "DOITUONG_CT_ID": 0,
              "DOITUONG_ID": this.hopDong.doituong_id,
              "DONVI_CUOI_ID": 0,
              "DONVI_DAU_ID": 0,
              "DONVI_ID": this.hopDong.donvi_id,
              "GHICHU": this.options.ghiChu,
              "GOIKT_ID": 0,
              "HDKH_ID": this.hdKhId,
              "HDTB_CHA_ID": 0,
              "HDTB_ID": this.hdtbId,
              "HDTT_ID": 0,
              "HTCM_ID": 0,
              "IP_CN": null,
              "KIEULD_ID": this.daDangKyHS ? KieuLapDat.HUYGOI_HSSSV : KieuLapDat.THAYDOI_TT_THUEBAO,
              "KIEU_TB": null,
              "LOAITB_ID": this.hopDong.loaitb_id,
              "MAY_CN": this.$root.token.getUserName(),
              "MA_TB": this.hopDong.ma_tb,
              "MUCUOCTB_ID": this.hopDong.mucuoctb_id,
              "MUCUOC_TB": this.hopDong.mucuoc_tb,
              "NGAY_CN": moment(new Date()).format("DD/MM/YYYY"),
              "NGAY_HH_TD": null,
              "NGAY_HT": null,
              "NGAY_SN": this.options.ngaySinh,
              "NGUOI_CN": this.$root.token.getUserName(),
              "NHACC_CU_ID": 0,
              "QUYTRINH_ID": 0,
              "SOLUONG": 0,
              "SOTHANG_CK": 0,
              "STATUS": 0,
              "TBHD_PL": 0,
              "TEN_TB": this.options.tenHSSV,
              "TG_THUE_DEN": null,
              "TG_THUE_TU": null,
              "THUEBAO_CHA_ID": 0,
              "THUEBAO_ID": this.hopDong.thuebao_id,
              "TRANGTHAIYC_ID": 0,
              "TTHD_ID": TrangThaiHD.MOI
            }
          ],
          "huonggiao_id": 5600,
          "tinhkhac": 0
        }
        var resultCapNhat = await service.capnhat_hopdong_dd(body)
        return resultCapNhat
      } catch (e) {
        console.error(e)
        throw e
      }
    },

    validCombox () {
      var vmsg = null
      var vref = null
      if (!this.options.quocTichId) {
        // this.$refs.quocTich.focus()
        vmsg = "Bạn phải chọn quốc tịch"
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.QuocTich})
      }
      if (this.options.gioiTinhId == null) {
        // this.$refs.gioiTinh.focus()
        vmsg = "Bạn phải chọn giới tính"
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.GioiTinh})
      }
      if (this.options.loaiKHId == 0) {
        // this.$refs.loaiKH.focus()
        vmsg = "Bạn phải chọn loại khách hàng"
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.LoaiKH})
      }
      if (!this.$v.options.doiTuongSDId.ComboxSelectedIdConstraint) {
        // this.$refs.doiTuongSD.focus()
        vmsg = "Bạn phải chọn đối tượng sử dụng"
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.DoituongSD})
      }
      if (this.options.truongHocId == 0) {
        vmsg = "Bạn phải chọn trường học"
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.TenTruong})
      }
      if (this.options.tinhTrangSVId == 0) {
        // this.$refs.tinhTrangSV.focus()
        vmsg = "Bạn phải chọn tình trạng sinh viên"
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.TinhTrangSV})
      }
      if (!this.options.ngayCapThe) {
        // this.$refs.ngayCapThe.focus()
        vmsg = "Bạn phải chọn ngày cấp thẻ"
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.NgayCap})
      }
      if (this.options.isBaoHo) {
        if (!this.options.nguoiBaoHo.loaiGiayToId) {
          // this.$refs.loaiGiayToNguoiBaoHo.focus()
          vmsg = "Bạn phải chọn loại giấy tờ người bảo hộ"
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.LoaiGiayToNguoiBaoHo})
        }
        if (!this.options.nguoiBaoHo.noiCapGiayTo) {
          // this.$refs.noiCapGiayToNguoiBaoHo.focus()
          vmsg = "Bạn phải chọn nơi cấp giấy tờ người bảo hộ"
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.NoiCapGiayToNguoiBaoHo})
        }
        if (!this.options.nguoiBaoHo.ngaySinh) {
          // this.$refs.ngaySinhNguoiBaoHo.focus()
          vmsg = "Bạn phải chọn ngày sinh người bảo hộ"
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.NgaySinhNguoiBaoHo})
        }
        if (!this.options.nguoiBaoHo.ngayCapGiayTo) {
          // this.$refs.ngaySinhNguoiBaoHo.focus()
          vmsg = "Bạn phải chọn ngày cấp giấy tờ người bảo hộ"
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.NgayCapGiayToNguoiBaoHo})
        }
      } else {
        if (!this.options.loaiGiayToId) {
          // this.$refs.loaiGiayTo.focus()
          vmsg = "Bạn phải chọn loại giấy tờ "
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.LoaiGiayToNguoiBaoHo})
        }
        if (!this.options.noiCapGiayTo) {
          // this.$refs.noiCapGiayTo.focus()
          vmsg = "Bạn phải chọn nơi cấp giấy tờ "
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.NoiCapGiayTo})
        }
        if (!this.options.ngaySinh) {
          // this.$refs.ngaySinh.focus()
          vmsg = "Bạn phải chọn ngày sinh"
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.NgaySinh})
        }
        if (!this.options.ngayCapGiayTo) {
          // this.$refs.ngaySinh.focus()
          vmsg = "Bạn phải chọn ngày cấp giấy tờ"
          this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.NgayCapGiayTo})
        }
      }
    },
    clearForm () {
      this.dsLichSuDKHSSV.list = []
      this.hopDong = null
      this.hopDongDD = null
      this.danhBa = null
      this.danhBaDD = null
      this.danhBaSVDD = null
      this.hdtbId = 0
      this.hdKhId = 0
      this.thuebao_id = 0
      this.hdtbDDId = 0
      this.options.tenHSSV = ""
      this.options.diachiTb = ""
      this.options.ghiChu = ""
      this.ngay_yc = null
      this.options.isBaoHo = false
      this.options.ngaySinh = null
      this.options.ngayCapGiayTo = null
      this.options.nguoiBaoHo.ngaySinh = null
      this.options.nguoiBaoHo.ngayCapGiayTo = null
      this.options.maXacThuc = ""
      this.maHD = ""
      this.maGD = ""
      this.thuebao_id = 0
      this.options.doiTuongSDId = 0
      this.options.loaiGiayToId = null
      this.options.noiCapGiayTo = null
      this.options.soGiayTo = ""
      this.options.soCMND = ""
      this.options.ngayCapGiayTo = null
      this.options.quocTichId = 0
      this.options.gioiTinhId = null
      this.options.tenGoiCuoc = ""
      this.options.soSim = ""
      this.options.tenGoiCuoc = ""
      this.options.ghiChuDD = ""
      this.options.loaiKHId = 0
      this.options.tinhTrangSVId = 0
      this.options.truongHocId = 0
      this.options.soThe = ""
      this.options.khoaHoc = ""
      this.options.ngayCapThe = null
      this.options.lop = ""
      this.options.ngaySinh = null
      this.options.nguoiBaoHo.ten = ""
      this.options.nguoiBaoHo.loaiGiayToId = null
      this.options.nguoiBaoHo.soGiayTo = null
      this.options.nguoiBaoHo.ngaySinh = null
      this.options.nguoiBaoHo.noiCapGiayTo = null
      this.options.nguoiBaoHo.diachi = ""
      this.options.nguoiBaoHo.soCMND = ""

      this.anh_upload.anh1.base64 = null
      this.anh_upload.anh2.base64 = null
      this.anh_upload.anh3.base64 = null
      this.anh_upload.anh4.base64 = null
      this.anhDaChonLocal = null

      this.khoitao_anh()
    },
    encodeThongTinHSSV () {
      var result = ""
      if (this.options.isBaoHo) {
        result = `sv${PivotStr}${this.options.tinhTrangSVId}${PivotStr}${this.options.soThe}${PivotStr}${this.options.khoaHoc}${PivotStr}${this.options.ngayCapThe}${PivotStr}${this.options.lop}${PivotStr}${this.options.nguoiBaoHo.soCMND}${PivotStr}1${PivotStr}${this.options.nguoiBaoHo.ten}${PivotStr}${this.options.nguoiBaoHo.ngaySinh}${PivotStr}${this.options.nguoiBaoHo.diachi}`
      } else {
        result = `sv${PivotStr}${this.options.tinhTrangSVId}${PivotStr}${this.options.soThe}${PivotStr}${this.options.khoaHoc}${PivotStr}${this.options.ngayCapThe}${PivotStr}${this.options.lop}${PivotStr}${this.options.soCMND}${PivotStr}0`
      }
      // eslint-disable-next-line no-console
      console.log("encodeThongTinHSSV :", result)
      return result
    },
    decodeThongTinHSSV (dataSV) {
      this.options.loaiKHId = dataSV.loaisv_id_neo
      this.options.truongHocId = dataSV.truonghoc_id_neo
      this.options.doiTuongSDId = dataSV.doituongsd_id_neo

      var data = dataSV.ghichu
      var decodeResult = ""
      if (!data.startsWith("sv")) {
        return decodeResult
      }
      var items = data.split(PivotStr)
      console.log("decodeThongTinHSSV", items)
      this.options.tinhTrangSVId = items[1]
      this.options.soThe = items[2]
      this.options.khoaHoc = items[3]
      try {
        console.log(" this.options.ngayCapThe", items[4])
        this.options.ngayCapThe = moment(items[4], "DD/MM/YYYY").format("DD/MM/YYYY")
      } catch (e) {
        console.log(" this.options.ngayCapThe", this.options.ngayCapThe)
        console.log("ngaycapthe", e)
      }
      this.options.lop = items[5]
      this.options.isBaoHo = items[7] == "1"
      if (this.options.isBaoHo) {
        this.options.nguoiBaoHo.loaiGiayToId = this.options.loaiGiayToId
        this.options.nguoiBaoHo.soGiayTo = this.options.soGiayTo
        this.options.nguoiBaoHo.ngayCapGiayTo = this.options.ngayCapGiayTo
        this.options.nguoiBaoHo.noiCapGiayTo = this.options.noiCapGiayTo
        this.options.nguoiBaoHo.ten = items[8]
        this.options.nguoiBaoHo.ngaySinh = items[9]
        this.options.nguoiBaoHo.diachi = items[10]
        this.options.nguoiBaoHo.soCMND = items[6]
      } else {
        this.options.soCMND = items[6]
      }
    },
    async openPopupXemAnh () {
      if (!this.options.maTb || this.options.maTb.length < 9) {
        return
      }
      this.displayStylePopup = "block"
      try {
        this.loading(true)
        var response = await service.app_tb_image_dktt(this.options.maTb)
        if (response.error_msg) {
          this.$toast.error("Có lỗi lấy ảnh từ CCBS " + response.error_msg)
        } else if (response.data.length == 0) {
          this.$toast.success("Không có ảnh để hiển thị")
        } else {
          this.thongtin_anh.forEach(anh => {
            var item = response.data.filter(anhCCBS => anhCCBS.type == anh.type)[0]
            anh.image_base = item.image_base
          })
        }
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra " + e.message)
      } finally {
        this.loading(false)
      }
    },
    setAnhCCBS () {

    },
    closePopup () {
      this.displayStylePopup = "none"
    },
    imgClick (item) {
      if (item && item.image_base) {
        this.anhDaChon = item

        // this.showPopup = false
        this.displayStyleImage = "block"
        this.displayStylePopup = "none"
      }
    },
    selectRowsChonDLichSuSV () {
      // eslint-disable-next-line no-console
      console.log("selectRowsChonDLichSuSV")
      let selectedRows = []
      for (var i = 0; i < this.dsLichSuDKHSSV.list.length; i++) {
        if (this.dsLichSuDKHSSV.list[i].GHI_CHU == "1") {
          selectedRows.push(i)
        }
      }
      this.$refs.refDsLichSuDKHSSV.$refs.grid.selectRows(selectedRows)
      console.log(selectedRows)
    },
    async chonAnh (args) {
      console.log(args)
      if (args.target.files.length == 0) { return }
      var result = await this.readFileBase64Async(args.target.files[0])
      if (args.target.id == "file1") {
        this.anh_upload.anh1.base64 = result
        this.anh_upload.anh1.file = structuredClone(args.target.files[0])
        this.$toast.success("Đã chọn ảnh 1")
      } else if (args.target.id == "file2") {
        this.anh_upload.anh2.base64 = result
        this.anh_upload.anh2.file = structuredClone(args.target.files[0])
        this.$toast.success("Đã chọn ảnh 2")
      } else if (args.target.id == "file3") {
        this.anh_upload.anh3.base64 = result
        this.anh_upload.anh3.file = structuredClone(args.target.files[0])
        this.$toast.success("Đã chọn ảnh 3")
      } else if (args.target.id == "file4") {
        this.anh_upload.anh4.base64 = result
        this.anh_upload.anh4.file = structuredClone(args.target.files[0])
        this.$toast.success("Đã chọn ảnh 4")
      } else if (args.target.id == "AnhChonLai") {
        this.anhDaChonLocal.base64 = result
      }
    },
    readFileBase64Async (file) {
      return new Promise((resolve, reject) => {
        let reader = new FileReader()

        reader.onload = () => {
          resolve(reader.result)
        }

        reader.onerror = reject

        reader.readAsDataURL(file)
      })
    },
    xemAnhDaChonLocal (args) {
      console.log("xemAnhDaChonLocal,args", args)
      this.displayStyleImageLocal = "block"
      if (args.currentTarget.id == "xemAnh1") {
        this.anhDaChonLocal = this.anh_upload.anh1
      } else if (args.currentTarget.id == "xemAnh2") {
        this.anhDaChonLocal = this.anh_upload.anh2
      } else if (args.currentTarget.id == "xemAnh3") {
        this.anhDaChonLocal = this.anh_upload.anh3
      } else if (args.currentTarget.id == "xemAnh4") {
        this.anhDaChonLocal = this.anh_upload.anh4
      }
    },
    async uploadAnhMimo () {
      for (const key in this.anh_upload) {
        if (this.anh_upload[key].file) {
          let formData = new FormData()
          formData.append("files", this.anh_upload[key].file)
          var response = await service.upload_file(formData)
          if (response.error_msg) {
            return {"error_msg": response.error_msg}
          } else if (response.data && response.data.length > 0) {
            this.anh_upload[key].url_minio = response.data[0]
          } else {
            return {"error_msg": "Không lấy được link url minIO "}
          }
        }
      }
      return {"error_msg": null}
    },
    async uploadAnhCCBS () {
      if (this.hdtbId == 0) {
        this.$toast.warning("Bạn phải tạo hợp đồng trước lúc upload ảnh")
        return
      }
      if (!this.anh_upload.anh1.base64) {
        this.$toast.warning("Bạn chưa chọn ảnh 1")
        return
      }
      if (!this.anh_upload.anh2.base64) {
        this.$toast.warning("Bạn chưa chọn ảnh 2")
        return
      }
      if (!this.anh_upload.anh3.base64) {
        this.$toast.warning("Bạn chưa chọn ảnh 3")
        return
      }
      if (!this.anh_upload.anh4.base64) {
        this.$toast.warning("Bạn chưa chọn ảnh 4")
        return
      }
      this.loading(true)
      try {
        var uploadMinIOResult = await this.uploadAnhMimo()
        if (uploadMinIOResult.error_msg) {
          this.$toast.console.error("Có lỗi upload ảnh lên server minIO: " + uploadMinIOResult.error_msg)
          return
        }
        var result = await service.upload_anh(this.options.maTb, this.anh_upload.anh1.url_minio
          , this.anh_upload.anh2.url_minio, this.anh_upload.anh3.url_minio, this.anh_upload.anh4.url_minio
          , "")
        if (result.error_msg) {
          this.$toast.error("Có lỗi xảy ra " + result.error_msg)
        } else {
          this.$toast.success("Đã cập nhật ảnh thành công ")
        }
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra " + e.message)
      } finally {
        this.loading(false)
      }
    },
    async guiMaXacThuc () {
      if (this.hdtbId == 0) {
        this.$toast.warning("Bạn phải tạo hợp đồng trước khi gửi mã xác thực ")
        return
      }
      this.loading(true)
      try {
        var maNvCCbs = this.$root.token.getProperty("ma_nhanvien_ccbs")
        console.log("ma_nhanvien_ccbs =", maNvCCbs)
        var result = await service.tt_18_ma_xac_thuc(this.options.maTb, maNvCCbs)
        if (result.error_msg) {
          this.$toast.error("Có lỗi xảy ra " + result.error_msg, "Có lỗi xảy ra")
        } else {
          this.$toast.success("Đã gửi mã xác thực thành công đến thuê bao " + this.options.maTb)
        }
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra " + e.message, "Có lỗi xảy ra")
      } finally {
        this.loading(false)
      }
    },
    getPassPortId () {
      if (this.options.isBaoHo) {
        if (this.options.nguoiBaoHo.loaiGiayToIdNeo == LoaiGiayToNeo.HoChieu) { return this.options.nguoiBaoHo.soGiayTo } else { return "" }
      } else {
        if (this.options.loaiGiayToIdNeo == LoaiGiayToNeo.HoChieu) { return this.options.soGiayTo } else { return "" }
      }
    },
    getSoGiayToBaoHo () {
      if (this.options.isBaoHo) {
        if (this.options.nguoiBaoHo.loaiGiayToIdNeo == LoaiGiayToNeo.HoChieu) { return this.options.nguoiBaoHo.soCMND } else { return this.options.nguoiBaoHo.soGiayTo }
      }
      return ""
    },

    getSoGiayToSV () {
      if (!this.options.isBaoHo) {
        if (this.options.loaiGiayToIdNeo == LoaiGiayToNeo.HoChieu) { return this.options.soCMND } else { return this.options.soGiayTo }
      }
      return ""
    },

    async kichHoatCCBS () {
      try {
        var body = {
          "so_tb": "84" + this.options.maTb,
          "ten_khach_hang": this.options.tenHSSV,
          "ngay_sinh": this.options.ngaySinh,
          "gioi_tinh": this.options.gioiTinhId == 1 ? "male" : "female",
          "loai_giayto": this.options.isBaoHo ? "" : this.options.loaiGiayToIdNeo,
          "so_giayto": this.getSoGiayToSV(),
          "ngay_cap": this.options.isBaoHo ? "" : this.options.ngayCapGiayTo,
          "noi_cap": this.options.isBaoHo ? "" : this.options.noiCapGiayTo,
          "loai_khach_hang": this.options.loaiKHId,
          "ma_truong": this.options.truongHocId,
          "tinh_trang_hs_sv": this.options.tinhTrangSVId,
          "so_the_hs_sv": this.options.soThe,
          "ten_lop_hoc": this.options.lop,
          "ngay_cap_the": this.options.ngayCapThe,
          "dia_chi": this.options.diachiTb,
          // "userid": this.$root.token.getProperty("ma_nhanvien_ccbs"),
          "ghi_chu": this.options.ghiChu,
          "bao_ho": this.options.isBaoHo ? 1 : 0,
          "khoa_hoc": this.options.khoaHoc,
          "ma_xac_thuc": this.options.maXacThuc,
          "kieu_thuc_hien": "",
          // "ip": this.$root.token.getUserName(),
          "ten_nguoi_bao_ho": this.options.isBaoHo ? this.options.nguoiBaoHo.ten : "",
          "ngay_sinh_nguoi_bao_ho": this.options.isBaoHo ? this.options.nguoiBaoHo.ngaySinh : "",
          "loai_giayto_bao_ho": this.options.isBaoHo ? this.options.nguoiBaoHo.loaiGiayToIdNeo : "",
          "ngay_cap_giayto_bao_ho": this.options.isBaoHo ? this.options.nguoiBaoHo.ngayCapGiayTo : "",
          "noi_cap_giayto_bao_ho": this.options.isBaoHo ? this.options.nguoiBaoHo.noiCapGiayTo : "",
          "so_giay_to_bao_ho": this.getSoGiayToBaoHo(),
          "dia_chi_nguoi_bao_ho": this.options.isBaoHo ? this.options.nguoiBaoHo.diachi : "",
          "quoc_tich": this.options.quocTichIdNeo,
          "doi_tuong_su_dung": this.options.doiTuongSDId,
          "passportid": this.getPassPortId(),
          "diachi_phong_giao_dich": "",
          "dienthoai_lh_phong_giao_dich": ""
          // "ma_tinh": this.$root.token.getMaTinh()
        }

        console.log("body kichHoat = ", body)
        var result = await service.ts_dk_hssv_dk(body)
        if (result.error_msg) {
          this.$toast.error("Có lỗi xảy ra khi kích hoạt trên CCBS " + result.error_msg)
          return false
        } else {
          this.$toast.success("Thành công")
          return true
        }
      } catch (e) {
        throw e
      }
    },
    async dangKyGoiCuoc () {
      try {
        if (this.options.maXacThuc.length == 0) {
          this.$toast.error("Bạn phải lấy mã xác thực trước khi hoàn thiện")
          this.$refs.maXacThuc.focus()
          return
        }
        var isConfirm = await this.confirm("Xác nhận đăng ký gói HSSV ?")
        console.log("isconfim = " + isConfirm)
        if (!isConfirm) return

        this.loading(true)
        var ghilaiResult = await this.onGhiLaiClicked()
        if (!ghilaiResult) {
          return
        }

        var isKichHoatSuccess = await this.kichHoatCCBS()
        if (!isKichHoatSuccess) return
        var hoanCongResult = await service.hths_dk_goicuoc_sv(this.hdtbId)
        if (hoanCongResult.error_msg) {
          this.$toast.error("Có lỗi hoản thiện trên Điều hành sản xuất")
        } else {
          this.ThongBao("Đăng ký gói HSSV thành công", "Thành công")
          this.clearForm()
          this.options.maTb = ""
          this.currentFormState = FormState.NhapMoi
        }
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra " + e.message)
      } finally {
        this.loading(false)
      }
    },
    async confirm (msg) {
      try {
        var result = await this.$confirm(msg, "Xác nhận", {
          confirmButtonText: "Có",
          cancelButtonText: "Không"
        })
        return result
      } catch (e) {
        return null
      }
    },
    async huyGoiCuoc () {
      try {
        var isConfirm = await this.confirm("Xác nhận huỷ gói HSSV?")
        if (!isConfirm) return
        this.loading(true)
        var result = await service.ts_dk_hssv_huy(this.options.maTb)
        if (result.error_msg) {
          this.$toast.error("Có lỗi xảy ra khi huỷ trên ccbs: " + result.error_msg)
        } else {
          this.$toast.success("Huỷ gói cước thành công trên CCBS ")
          var hoanCongResult = await service.hths_dk_goicuoc_sv(this.hdtbId)
          if (hoanCongResult.error_msg) {
            this.$toast.error("Có lỗi hoản thiện trên Điều hành sản xuất")
          } else {
            this.ThongBao("Đã huỷ gói HSSV thành công", "Thành công")
            this.clearForm()
            this.options.maTb = ""
            this.currentFormState = FormState.NhapMoi
          }
        }
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra " + e.message)
      } finally {
        this.loading(false)
      }
    },

    ShowPopupSearchHopDong () {
      console.log("ShowPopupSearchHopDong")
      this.$refs.popupSearchHD.onShownModal()
    },
    async selectedMagdFromPopup (vMagd) {
      console.log("selectedMagdFromPopup: vmgd = ", vMagd)
      this.maGD = vMagd
      await this.onMaGDEnter()
    }
  }

}
</script>
