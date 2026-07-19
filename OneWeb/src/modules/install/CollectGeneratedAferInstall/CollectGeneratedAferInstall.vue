<template src="./CollectGeneratedAferInstall.html"></template>
<style src="./CollectGeneratedAferInstall.scss"></style>
<script>
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";

import CollectGeneratedAferInstallAPI from './CollectGeneratedAferInstallAPI'
import GiaoPhieuNhanVien from '../../contract/setup/DeclareLandline/popup/giaophieunhanvien.vue'
// import moment from 'moment'
export default {
  components: {
    CollectGeneratedAferInstallAPI,
    GiaoPhieuNhanVien,
    DatePicker
  },
  data () {
    return {
      hoanCongEnable: false,
      dateconfig: {
        altFormat: 'd/m/Y H:i',
        altInput: true,
        dateFormat: 'd/m/Y H:i',
        allowInput: true,
        enableTime: true,
      },
      // Đéo biết là cl gì cả
      stag: '',
      tthd_id: 0,
      dsloaihd_id: '',
      dsdichvuvt_id: '',

      datetimeFormat: "DD/MM/YYYY HH:mm",

      SuccessCode: 'BSS-00000000',
      DataObj: Object,
      // Thong tin khach hang
      DichVuVT: 0,
      QuyTrinhDV: 0,
      MaGD: '',
      MaTB: '',
      KieuYC: '',
      TenKH: '',
      DiaChiKH: '',
      TenTT: '',
      DiaChiTT: '',
      // Thong tin hoan cong
      KetQuaXuLy: 0,
      isKetQuaXL: false,
      TocDo: -1,
      isTocDo: false,
      CBBKhuyenMai: -1,
      isKhuyenMai: false,
      CuocDatCoc: '',
      TienTrenThang: '',
      SoThang: '',
      TongTien: '',
      // Thong tin giao viec
      NgayGiaoViec: new Date(),
      isNgayGV: false,
      NgayHoanThanh: '',
      isNgayHT: false,
      NguoiGiaoViec: 0,
      NhanVienYeuCau: '',
      NoiDungGiao: '',
      NoiDungThucHien: '',
      GhiChu: '',
      // Biến không hiển thị trên form
      LoaiTbId: 0,
      KieuLdId: 0,
      MucCuocId: 0,
      DoiTuongId: 0,
      ThueBaoId: 0,
      HDTBId: 0,
      DVVTId: 0,
      PhieuId: 0,
      HuongGiaoId: 0,
      LuongId: 0,
      // Loại hợp đồng
      MOI: 1,
      THANH_TOAN: 2,
      DIEUHANH_THI_CONG: 3,
      DANG_THI_CONG: 4,
      DA_THI_CONG_XONG: 5,
      THU_KHAC: 17,
      DATHUCHIEN: 4,
      // DỊCH VỤ VIỄN THÔNG
      TATCA: -1,
      CO_DINH: 1,
      DI_DONG: 2,
      ADSL: 4,
      DD_TRATRUOC: 5,
      EMAIL: 6,
      METRONET: 7,
      MEGAWAN: 8,
      TSL: 9,
      GPHONE: 10,
      IMS: 11,
      MEGA_EYES: 12,
      HOINGHI_TRUYENHINH: 13,
      ANTOAN_BAOMAT_TT: 14,
      DICHVU_CNTT: 15,
      TRUNGTAM_DULIEU: 16,
      HATANG_VIENTHONG: 19,

      listbox: {
        DSDichVu: {
          DataSource: [],
          Value: null
        },
        DSQuyTrinh: {
          DataSource: [],
          Value: null
        },
        DSKQXuLy: {
          DataSource: [],
          Value: null
        },
        DSNguoiGiaoViec: {
          DataSource: [],
          Value: null
        },
        DSTocDo: {
          DataSource: [],
          Value: null
        },
        DSKhuyenMai: {
          DataSource: [],
          Value: null
        },
        DSTienKhoanMuc: {
          DataSource: [],
          Value: null
        }
      },
      ElementTocDo: [
        {
          toc_do: '',
          tocdo_id: 0
        }
      ],
      DSHopDong: {
        DataSource: [],
        header: [ {fieldName: 'hdtb_id', headerText: 'Mã GD', allowFiltering: true},
          {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true},
          {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowHtml: true},
          {fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', allowFiltering: true, allowHtml: true},
          {fieldName: 'so_dt_lh', headerText: 'Điện thoại LH', allowFiltering: true, allowHtml: true},
          {fieldName: 'ngaygiao', headerText: 'Ngày YC', allowFiltering: true, allowHtml: true},
          {fieldName: 'tien', headerText: 'Tiền', allowFiltering: true, allowHtml: true}
        ],
        value: {},
        isEnabled: true
      },
      DSNhanVienThucHien: {
        DataSource: [],
        header: [ {fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true},
          {fieldName: 'so_dt', headerText: 'Điện thoại', allowFiltering: true},
          {fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true, allowHtml: true},
          {fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true, allowHtml: true}
        ],
        value: {},
        isEnabled: true
      },
      ChiTietKhuyenMai: {
        DataSource: []
      },
      modelGiaoViec: {
        phieu_id: 0,
        nguoigv_id: 0,
        ngaygiao: '',
        ma_tb: 0,
        hdtb_id: 0,
        huonggiao_id: 0,
        dsphieuGV: []
      },
      target: '.main-wrapper',
      
    }
  },
  mounted () {
    try {
      this.stag = this.$route.query.tag;

      if (this.stag !== '') {
        
        var str = this.stag
        var words = str.split('+')
        if (words.length >= 1) { 
          this.tthd_id = parseInt(words[0]) 
           
        }
        if (words.length >= 2) {
          this.dsloaihd_id = words[1]
          if (this.dsloaihd_id.includes(";")) {
            this.dsloaihd_id = this.dsloaihd_id.Replace(';', ',')
          }
        }
        if (words.length >= 3) {
          this.dsdichvuvt_id = words[2]
          if (this.dsdichvuvt_id.includes(";")) {
            this.dsdichvuvt_id = this.dsdichvuvt_id.Replace(';', ',')
          }
        }
      } else {
        
        // this.DANG_THI_CONG = 4
        this.tthd_id = this.DANG_THI_CONG
        this.dsloaihd_id = '17'
        this.dsdichvuvt_id = '2'
      }
    } catch (exception) {
        this.dsloaihd_id = '4'
        this.dsdichvuvt_id = '1'
    }
    this.GetDSDichVu()
  },
  methods: {
    GetDSDichVu () {
      CollectGeneratedAferInstallAPI.DSDichVu(this.axios, {}).then((response) => {
        if (response.data.error_code === this.SuccessCode) {
          this.listbox.DSDichVu.DataSource = response.data.data
          let DichVuVTId = this.listbox.DSDichVu.DataSource[0].DICHVUVT_ID
          if (this.DichVuVT === 0) {
            this.DichVuVT = DichVuVTId;
          }
          this.DSDichVuChange(this.DichVuVT)
        } else this.listbox.DSDichVu.DataSource = []
      })
    },
    async DSDichVuChange (even) {
      this.GetDSQuyTrinh(even)
    },
    GetDSQuyTrinh (PDichVuId) {
      let param = {
        p_dsloaihd_id: this.dsloaihd_id,
        p_dichvuvt_id: PDichVuId,
        p_tthd_id: this.tthd_id,
        p_kieu: 0
      }
      CollectGeneratedAferInstallAPI.DSQuyTrinh(this.axios, param).then((response) => {
        if (response.data.error_code === this.SuccessCode) {
          this.listbox.DSQuyTrinh.DataSource = response.data.data
          let QuyTrinhId = this.listbox.DSQuyTrinh.DataSource[0].quytrinh_id
          this.QuyTrinhDV = QuyTrinhId
          this.GetHuongGiaoTheoQuyTrinh(QuyTrinhId)
        } else this.listbox.DSQuyTrinh.DataSource = []
      })
    },
    GetHuongGiaoTheoQuyTrinh (PquyTrinhId) {
      console.log('PquyTrinhId: ', PquyTrinhId)
      try {
        CollectGeneratedAferInstallAPI.LayHuongGiaoTheoQuyTrinh(this.axios, {
          'P_QUYTRINH_ID': PquyTrinhId,
          'P_TTHD_ID': this.tthd_id
        }).then(res => {
          console.log('res.data.data[0].huonggiao_id: ', res.data.data[0].huonggiao_id)
          let huongGiaoId = res.data.data[0].huonggiao_id
          this.HuongGiaoId = huongGiaoId
          this.LuongId = res.data.data[0].luong_id
          this.GetDSHopDong()
        })
      } catch (err) {
        this.listbox.DSKQXuLy.DataSource = []
      }
    },
    GetDSHopDong () {
      
      let param = {
        vloaihd_id: parseInt(this.dsloaihd_id),
        vhuonggiao_id: this.HuongGiaoId,
        vnhanvien_id: parseInt(this.$root.token.getNhanVienID()),
        vtthd_id: this.tthd_id,
        vphieu_id: 0,
        vhdkh_id: this.HDTBId
      }

      this.DSHopDong.DataSource = []
      
      CollectGeneratedAferInstallAPI.DSHopDong(this.axios, JSON.stringify(param)).then((response) => {
        console.log('GetDSHopDong :', response)
        if (response.data.error_code === this.SuccessCode) {
          this.DSHopDong.DataSource = response.data.data

        } else this.DSHopDong.DataSource = []
      })
    },
    GetDSKQXuLy () {
      try {
        CollectGeneratedAferInstallAPI.DSKQXuLy(this.axios, {
          'P_NHOMKQ_ID': 6,
          'P_KIEU': 0
        }).then(res => {
          if (res.data.error_code === this.SuccessCode) {
            this.listbox.DSKQXuLy.DataSource = res.data.data
          } else this.listbox.DSKQXuLy.DataSource = []
        })
      } catch (err) {
        this.listbox.DSKQXuLy.DataSource = []
      }
    },
    GetNhanVienTheoDonVi () {
      try {
        CollectGeneratedAferInstallAPI.DSNhanVienTheoDonVi(this.axios, {
          'P_DONVI_ID': this.$root.token.getDonViID()
        }).then(res => {
          this.listbox.DSNguoiGiaoViec.DataSource = res.data.data
          this.NguoiGiaoViec = this.$root.token.getNhanVienID()
        })
      } catch (err) {

      }
    },
    async GetNhanVienThucHienTheoPhieu () {
      try {
        CollectGeneratedAferInstallAPI.DSNhanVienThucHien(this.axios, {
          'VPHIEU_ID': this.PhieuId,
          'VNHANVIEN_ID': this.$root.token.getNhanVienID(),
          'VKIEU_ID': this.KieuLdId
        }).then(res => {
          if (res.data.error_code === this.SuccessCode) {
            this.DSNhanVienThucHien.DataSource = res.data.data
          } else this.DSNhanVienThucHien.DataSource = []
        })
      } catch (err) {
        this.DSNhanVienThucHien.DataSource = []
      }
    },
    async ThongTinTocDoCuaHopDong (d) {
      try {
        CollectGeneratedAferInstallAPI.thongTinTocDoCuaHopDong(this.axios, {
          'P_DICHVUVT_ID': this.DVVTId,
          'P_HDTB_ID': this.HDTBId
        }).then(res => {
          if (res.data.error_code === this.SuccessCode) {
            this.MucCuocId = res.data.data[0].muccuoc_id
            if (res.data.data[0].tocdo_id === res.data.data[0].tocdo_cu_id) {
              this.isTocDo = true
            } else {
              this.isTocDo = false
            }
          }
        })
      } catch (err) {
        this.DSNhanVienThucHien.DataSource = []
      }
    },
    async GetDSTocDo () {
      try {
        CollectGeneratedAferInstallAPI.DSTocDo(this.axios, {
          'P_DICHVUVT_ID': this.DichVuVT,
          'P_LOAITB_ID': this.LoaiTbId,
          'P_TOCDO_ID': 0
        }).then(res => {
          this.listbox.DSTocDo.DataSource = []
          if (res.data.error_code === this.SuccessCode) {
            if (res.data.data[0].toc_do === undefined && res.data.data.length > 0) {
              for (var i = 0; i < res.data.data.length; i++) {
                this.ElementTocDo[0].toc_do = res.data.data[i].tocdo
                this.ElementTocDo[0].tocdo_id = res.data.data[i].tocdo_id
                this.ElementTocDo[0] = [...this.ElementTocDo[0]]
                this.listbox.DSTocDo.DataSource.push(this.ElementTocDo[0])
              }
            } else this.listbox.DSTocDo.DataSource = res.data.data
          } else this.listbox.DSTocDo.DataSource = []
        })
      } catch (error) {
        this.listbox.DSTocDo.DataSource = []
      }
      this.TocDo = -1

    },
    async GetDsKhuyenMai () {
      try {
        CollectGeneratedAferInstallAPI.DSKhuyenMai(this.axios, {
          'P_KIEULD_ID': this.KieuLdId,
          'P_LOAITB_ID': this.LoaiTbId,
          'P_MUCCUOC_ID': this.MucCuocId,
          'P_DOITUONG_ID': this.DoiTuongId,
          'P_TOCDO_ID': this.TocDo,
          'P_THUEBAO_ID': this.ThueBaoId,
          'P_NHOM_DATCOC_ID': 1
        }).then(res => {
          if (res.data.error_code === this.SuccessCode) {
            this.listbox.DSKhuyenMai.DataSource = res.data.data
          } else this.listbox.DSKhuyenMai.DataSource = []
        })
      } catch (err) {
        this.listbox.DSKhuyenMai.DataSource = []
      }
    },
    async ListDataFocus (Obj) {

      if (!Obj) {
        return
      }

      this.DataObj = Obj
      // Thông tin khách hàng
      this.MaGD = Obj.ma_gd
      this.MaTB = Obj.ma_tb
      this.KieuYC = Obj.ten_kieuld
      this.TenKH = Obj.ten_kh
      this.DiaChiKH = Obj.diachi_ld
      this.TenTT = Obj.ten_tt
      this.DiaChiTT = Obj.diachi_tt
      // Thông tin giao việc
      this.NgayGiaoViec = this.SysDate()
      if (Obj.ngay_ht_gp) {
        this.isNgayHT = true
        this.NgayHoanThanh = Obj.ngay_ht_gp
      } else {
        this.NgayHoanThanh = this.SysDate()
      }
      this.NhanVienYeuCau = Obj.nv_lhd
      this.NoiDungGiao = Obj.nd_giao
      this.NoiDungThucHien = Obj.nd_thuchien
      this.GhiChu = Obj.ghichu
      // Gán vào biến để sử dụng cho các hàm sau
      this.LoaiTbId = Obj.loaitb_id
      this.KieuLdId = Obj.kieuld_id
      this.DoiTuongId = Obj.doituong_id
      this.ThueBaoId = Obj.thuebao_id
      this.HDTBId = Obj.hdtb_id
      this.DVVTId = Obj.dichvuvt_id
      this.PhieuId = Obj.phieu_id

      await this.GetNhanVienThucHienTheoPhieu()
      await this.ThongTinTocDoCuaHopDong()
      this.GetDSTocDo()
      await this.GetDsKhuyenMai()
      await this.LayChiTietKhuyenMai()
      this.KhuyenMai = false
      this.TocDo = -1
      this.listbox.DSKhuyenMai.DataSource = []
      await this.LayTienKhoanMucTheoLoaiHD()
      await this.LayTienKhoanMucTheoHDTB()
      this.GetNhanVienTheoDonVi()
    },
    SysDate () {
      var today = new Date()
      var dd = String(today.getDate()).padStart(2, '0')
      var mm = String(today.getMonth() + 1).padStart(2, '0')
      var yyyy = today.getFullYear()
      var hh = today.getHours()
      var mi = today.getMinutes()
      var sc = today.getSeconds()
      var type = hh > 12 ? 'PM' : 'AM'
      return dd + '/' + mm + '/' + yyyy + ' ' + hh + ':' + mi + ':' + sc
    },
    async LayChiTietKhuyenMai () {
      try {
        CollectGeneratedAferInstallAPI.LayChiTietKhuyenMai(this.axios, {
          'vhdtb_id': this.HDTBId,
          // 'vhdtb_id': 1853484,
          'vloai_km': 1
        }).then(res => {
          if (res.data.error_code === this.SuccessCode) {
            if (res.data.data[0].khuyenmai_id) {
              this.KhuyenMai = true
              this.CBBKhuyenMai = res.data.data[0].khuyenmai_id
            } else this.KhuyenMai = false
            this.ChiTietKhuyenMai.DataSource = res.data.data
            this.CuocDatCoc = res.data.data[res.data.data.length - 1].datcoc_csd
            this.TienTrenThang = res.data.data[res.data.data.length - 1].tien_td
            this.SoThang = res.data.data[res.data.data.length - 1].thang_huongdc
          } else this.KhuyenMai = false
        })
      } catch (err) {

      }
    },
    async LayTienKhoanMucTheoLoaiHD () {
      try {
        CollectGeneratedAferInstallAPI.TienKhoanMucTheoLoaiHD(this.axios, {
          // 'P_LOAIHD_ID': 10
          'P_LOAIHD_ID': this.dsloaihd_id
        }).then(res => {
          if (res.data.error_code === this.SuccessCode) {
            this.listbox.DSTienKhoanMuc.DataSource = res.data.data
          }
        })
      } catch (err) {

      }
    },
    async LayTienKhoanMucTheoHDTB () {
      try {
        CollectGeneratedAferInstallAPI.TienKhoanMucTheoHDTB(this.axios, {
          'vhdtb_id': this.HDTBId
        }).then(res => {
          if (res.data.error_code === this.SuccessCode) {
            if (res.data.data.length > 0) {
              this.listbox.DSTienKhoanMuc.DataSource = res.data.data
            }
          }
          if (this.listbox.DSTienKhoanMuc.DataSource.length > 0) {
            if (this.listbox.DSTienKhoanMuc.DataSource[0].khoanmuctt_id === 11) {
              this.CuocDatCoc = this.listbox.DSTienKhoanMuc.DataSource[this.listbox.DSTienKhoanMuc.DataSource.length - 1].tien + this.listbox.DSTienKhoanMuc.DataSource[this.listbox.DSTienKhoanMuc.DataSource.length - 1].vat
            }
          }
          var iTongTien = 0
          var iTongVAT = 0
          for (const element of this.listbox.DSTienKhoanMuc.DataSource) {
            iTongTien += element.tien
            iTongVAT += element.vat
          }
          this.TongTien = iTongTien + iTongVAT
        })
      } catch (err) {

      }
    },
    btnLayTTClicked () {
      this.GetDSDichVu()
      this.GetDSHopDong()
      if (this.DSHopDong.DataSource) {
        this.ListDataFocus(this.DSHopDong.DataSource[0])
      }
    },
    updateTHClick () {
      var Data = {}
      Data.vphieu_id = this.PhieuId
      Data.vnd_thuchien = this.NoiDungThucHien.trim()
      CollectGeneratedAferInstallAPI.CapNhatNoiDungGiaoPhieu(this.axios, Data).then((response) => {
        if (response.data.error_code === this.SuccessCode) {
          this.$toast.success('Cập nhật nội dung thực hiện thành công!')
        } else {
          this.$toast.error('Lỗi cập nhật nội dung thực hiện!')
        }
      })
    },
    btnUpdateClicked () {
      this.$confirm(
        `Bạn chắc chắn muốn cập nhật dữ liệu trạm - thu tiền phát sinh?`,
        'Cập nhật trạm -  thu tiền phát sinh',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        }
      ).then(async () => {
        try {
          var Data = {}
          Data.p_update_tocdo = 1
          Data.p_dichvuvt_id = this.DichVuVT
          Data.p_hdtb_id = this.HDTBId
          Data.p_tocdo_id = this.TocDo
          Data.p_js_datcoc_hdtb = this.JsonData('p_js_datcoc_hdtb')
          Data.p_js_ct_tienhd = this.JsonData('p_js_ct_tienhd')
          Data.p_phieu_id = this.PhieuId
          Data.p_nhanvien_gv_id = this.NguoiGiaoViec
          Data.p_nhanvien_th_id = this.DSNhanVienThucHien.DataSource[0].nhanvien_th_id
          Data.p_ngay_th = this.NgayHoanThanh
          Data.p_ngay_gv = this.NgayGiaoViec
          CollectGeneratedAferInstallAPI.CapNhat(this.axios, Data).then((response) => {
            console.log('Data: ', Data)
            console.log('btnUpdateClicked: ', response)
            if (response.data.error_code === this.SuccessCode) {
              this.$toast.success('Cập nhật dữ liệu thành công!')
              isEnableHoanCong = true;
            } else {
              this.$toast.error(response.data.message_detail)
            }
          })
        } catch (error) {
          this.$toast.error('Lỗi khi cập nhật dữ liệu!')
        }
      })
    },
    JsonData (Type) {
      let ArrResult = []
      if (Type === 'p_js_datcoc_hdtb') {
        this.ChiTietKhuyenMai.DataSource.forEach((ObjChiTietKM) => {
          let DatcocHDTB = {
            PHANVUNG_ID: ObjChiTietKM.phanvung_id,
            HDTB_ID: this.HDTBId,
            KHUYENMAI_ID: this.CBBKhuyenMai,
            CHITIETKM_ID: ObjChiTietKM.chitietkm_id,
            KHUYENMAI_ID_NEO: ObjChiTietKM.khuyenmai_id_neo,
            KIEU_YC: ObjChiTietKM.kieu_yc,
            THANG_BD: ObjChiTietKM.thang_bd,
            THANG_KT: ObjChiTietKM.thang_kt,
            THANG_HUONGKM: ObjChiTietKM.thang_huongkm,
            THANG_HUONGDC: ObjChiTietKM.thang_huongdc,
            THANG_BDDC: ObjChiTietKM.thang_bddc,
            THANG_KTDC: ObjChiTietKM.thang_ktdc,
            THANG_BDTG: ObjChiTietKM.thang_bdtg,
            THANG_KTTG: ObjChiTietKM.thang_kttg,
            TIEN_TG: ObjChiTietKM.tien_tg,
            SOTHANG_TG: ObjChiTietKM.sothang_tg,
            TYLE_KMLD: ObjChiTietKM.tyle_kmld,
            TYLE_TB: ObjChiTietKM.tyle_tb,
            TYLE_SD: ObjChiTietKM.tyle_sd,
            DATCOC_CSD: ObjChiTietKM.datcoc_csd,
            TIEN_TD: ObjChiTietKM.tien_td,
            RKM_ID: ObjChiTietKM.rkm_id,
            FKEY: ObjChiTietKM.fkey,
            TIEN_SD: ObjChiTietKM.tien_sd,
            CUOC_SD: ObjChiTietKM.cuoc_sd,
            TIEN_TB: ObjChiTietKM.tien_tb,
            CUOC_TB: ObjChiTietKM.cuoc_tb,
            NGAY_HUY: ObjChiTietKM.ngay_huy,
            NGAY_BD: ObjChiTietKM.ngay_bd,
            NGAY_KT: ObjChiTietKM.ngay_kt,
            NGAY_THOAI: ObjChiTietKM.ngay_thoai
          }
          ArrResult.push(DatcocHDTB)
        })
        return JSON.stringify(ArrResult)
      } else {
        this.ChiTietKhuyenMai.DataSource.forEach((ObjChiTietKM) => { // p_js_ct_tienhd
          let CtTienND = {
            PHANVUNG_ID: ObjChiTietKM.phanvung_id,
            CT_TIENHD_ID: ObjChiTietKM.chitietkm_id,
            TIEN: ObjChiTietKM.tien_tg,
            VAT: ObjChiTietKM.vat_kmld,
            LOAI_ID: ObjChiTietKM.loai_bdkm,
            ID: ObjChiTietKM.chitietkm_id,
            HDTB_ID: ObjChiTietKM.hdtb_id,
            KHOANMUCTT_ID: ObjChiTietKM.khoanmuctt_id,
            PHIEUTT_ID: this.PhieuId,
            TYLE_VAT: ObjChiTietKM.tyle_vat,
            TYLE_VAT_ID: ObjChiTietKM.tyle_vat_id
          }
          ArrResult.push(CtTienND)
        })
        return JSON.stringify(ArrResult)
      }
    },
    onAssignClick () {
      this.modelGiaoViec.phieu_id = this.PhieuId
      this.modelGiaoViec.nguoigv_id = this.NguoiGiaoViec
      this.modelGiaoViec.ngaygiao = this.NgayGiaoViec
      this.modelGiaoViec.ma_tb = this.MaTB
      this.modelGiaoViec.hdtb_id = this.HDTBId
      this.modelGiaoViec.huonggiao_id = this.HuongGiaoId
      this.modelGiaoViec.dsphieuGV = []
      this.$refs.formgiaphieu.init()
      this.$refs.giaoPhieuNhanVien.show()
    },
    async giaoviec_success () {
      //let dsNv = await this.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.phieu_id, Number.parseInt(this.$root.token.getNhanVienID()), 2)
      let dsNv = this.GetNhanVienThucHienTheoPhieu()
      await this.HienThiDSNV(dsNv)
    },
    onHoanCongClick () {
      if (this.ValidateHoanCong()) {
        this.$confirm(
          `Bạn thật sự muốn hoàn công không?`,
          'Thông báo',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning'
          }
        ).then(async () => {
          try {
            var Data = {}
            Data.p_nhanvien_gv_id = this.NguoiGiaoViec
            Data.p_nhanvien_th_id = this.DSNhanVienThucHien.DataSource[0].nhanvien_th_id

            Data.p_tthd_id = this.tthd_id
            Data.p_hdtb_id = this.HDTBId
            Data.p_ketqua_xl_id = this.KetQuaXuLy
            Data.p_phieu_id = this.PhieuId
            Data.p_nhanvien_gv_id = parseInt(this.NguoiGiaoViec)
            Data.p_nhanvien_th_id = this.DSNhanVienThucHien.DataSource[0].nhanvien_th_id
            // 17/07/2021 12:00:00
            Data.p_ngay_th = this.NgayHoanThanh
            Data.p_ngay_gv = this.NgayGiaoViec
            Data.p_donvi_id = parseInt(this.$root.token.getDonViID())
            Data.p_tien = this.TienTrenThang
            CollectGeneratedAferInstallAPI.HoanCong(this.axios, Data).then((response) => {
              if (response.data.error_code === this.SuccessCode) {
                this.$toast.success('Hoàn công thành công!')
              }
            })
          } catch (error) {
            this.$toast.error('Lỗi khi cập nhật dữ liệu!')
          }
        })
      }
    }, 
    ValidateHoanCong () {
      if (this.isNgayGV === false) {
        this.$toast.warning('Hãy chọn ngày giao việc!')
        return false
      }      
      if (this.isNgayHT === false) {
        this.$toast.warning('Hãy chọn ngày hoàn thành!')
        return false
      }
      if (this.NgayGiaoViec > this.SysDate()) {
        this.$toast.warning('Ngày giao việc không được lớn hơn ngày hiện tại!')
        return false
      }      
      if (this.NgayHoanThanh > this.SysDate()) {
        this.$toast.warning('Ngày hoàn thành không được lớn hơn ngày hiện tại!')
        return false
      }
      if (this.NgayGiaoViec > this.NgayHoanThanh) {
        this.$toast.warning('Ngày giao việc không được lớn hơn ngày hoàn thành!')
        return false
      }
      return true
    }
  },
  computed: {
    KetQuaXL: {
      get () { return this.isKetQuaXL },
      set (value) {
        this.isKetQuaXL = value
        if (this.isKetQuaXL) {
          this.GetDSKQXuLy()
        }
      }
    },
    DoiTocDo: {
      get () { return this.isTocDo },
      set (value) {
        this.isTocDo = value
      }
    },
    KhuyenMai: {
      get () { return this.isKhuyenMai },
      set (value) {
        this.isKhuyenMai = value
      }
    },
    ngaygv: {
      get () { return this.isNgayGV },
      set (value) {
        this.isNgayGV = value
        // this.thoihancon_nhohon = '0'
      }
    },
    ngayht: {
      get () { return this.isNgayHT },
      set (value) {
        this.isNgayHT = value
        // this.thoihancon_lonhon = '0'
      }
    }
  }
}
</script>
