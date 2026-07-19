<template src='./TraCuuTonKhoTheoUser.html'></template>
<style src='./TraCuuTonKhoTheoUser.scss' scoped></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../../store/trucuutonkhotheoUser";
import moment from "moment";
import Vue from "vue";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
Vue.use(DialogPlugin);
import { Query, Predicate } from "@syncfusion/ej2-data";

export default {
  components: {},
  name: "TraCuuTonKhoTheoUser",

  created() {},
  mounted() {},
  computed: {
    ...mapState("trucuutonkhotheoUser", statePropertyName),
    ...mapGetters("trucuutonkhotheoUser", getterName),
  },
  watch: {
    
  },
  data() {
    
    return {
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',

      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        waterMark: "Chọn ngày",
      },
      kieu : 0,
      vkho_id: 0,
      DS_Xuat: [],
      _dtLuuVT : [],
      dtVatTu: [],
      _dtVT_Xuat : [],
      dataSource:{
        dsQuyen: [],
        DSKho: [],
        DSThietBi: [],
        DSVatTu: [],
        DSXuat: [],
      },
      params:{
        khoId: 0,
        denNgay: new Date(),
      },
      Enabled:{
        Tukho:true,
        DenNgay:true
      },
      Visible:{
        colSHD : false,
        colGC : false,
        colDienGiai : false,
        colMaDA : false,
        colNguoiQL : false,
        colCL : false,
        colIDNG : false,
        colNoiDUng : false,
        colCongTrinh : false,
        colIDG : false,
        colSLDD : false,
        colTrangThaiVT : false,
        colTrangThaiVTBottom : false,
        colDSTU : false,
        colMaKhoTD : false,
        colLoaiKho : false,
        colSLTonThuc : false,
        colDonGia: false,

        colNguonGoc : false,
        colNgayNhap : false,

        colChatluong_id : false,
        colHangSX : false,
        colDongtbi_id : false,
        colHethong_id : false,
        colLinhVuc : false,
        colPartN : false,
        colMotaCT : false,
        popupFilter:false,
        DSThietBi: true,
        DenNgay:true,
        DSDangKy :false,
        CheckAll: true,
      },

      thongTinNguoiDung: {
        nguoiCN: this.$root.token.getUserName(), //this.$auth.MaNguoiDung
        donViId: this.$root.token.getDonViID(), // 882
        phanVungId: this.$root.token.getPhanVungID(),
        nhanVienId: this.$root.token.getNhanVienID(),
        nguoiDungId: this.$root.token.getNguoiDungID(),
        maTinh: this.$root.token.getMaTinh(),
      },

      onFilteringKho: function (e) {
        var query = new Query();
        let predicate;

        //frame the query based on search string with filter type.
        if (e.text !== "") {
          predicate = new Predicate("tenKho", "Contains", e.text);
          query.where(predicate);
        }

        //pass the filter data source, filter query to updateData method.
        e.updateData(this.dataSource, query);
      },
    };
    
  },
  
  methods: {
    ...mapActions("trucuutonkhotheoUser", actionName),
    ...mapMutations("trucuutonkhotheoUser", mutationName),


    async openDialog(){
      this.loading(true)
      await this.frmVatTuTrongKho_Load()
      this.loading(false)
      this.$refs.TraCuuTonKhoTheoUser.show();
    },
    async closeDialog() {
      this.$refs.TraCuuTonKhoTheoUser.hide();
    },
    async frmVatTuTrongKho_Load(){
      this.Visible.colSHD = false
      this.Visible.colGC = false
      this.Visible.colDienGiai = false
      this.Visible.colMaDA = false
      this.Visible.colNguoiQL = false
      this.Visible.colCL = false
      this.Visible.colIDNG = false
      this.Visible.colNoiDUng = false
      this.Visible.colCongTrinh = false
      this.Visible.colIDG = false
      this.Visible.colSLDD = false

      if(this.thongTinNguoiDung.maTinh == "HNI"){
        this.Visible.colSHD = true
        this.Visible.colGC = true
        this.Visible.colDienGiai = true
        this.Visible.colMaDA = true
        this.Visible.colNguoiQL = true
        this.Visible.colCL = true
        this.Visible.colIDNG = true
        this.Visible.colNoiDUng = true
        this.Visible.colTrangThaiVT = true
        this.Visible.colTrangThaiVTBottom = true
        this.Visible.colDSTU = true

      }else if(this.thongTinNguoiDung.maTinh == "HCM" || this.thongTinNguoiDung.maTinh == "NET"){
        this.Visible.colMaKhoTD = true
        this.Visible.colLoaiKho = true
        this.Visible.colCongTrinh = true
        this.Visible.colGC = true
        this.Visible.colSHD = true
        this.Visible.colMaDA = true
        this.Visible.colIDG = true
        this.Visible.colSLTonThuc = true
        this.Visible.colSLDD = true
        this.Visible.colDonGia = true
        this.Visible.colNguonGoc = true
        this.Visible.colNgayNhap = true

        this.Visible.colChatluong_id = true
        this.Visible.colHangSX = true
        this.Visible.colDongtbi_id = true
        this.Visible.colHethong_id = true
        this.Visible.colLinhVuc = true
        this.Visible.colPartN = true
        this.Visible.colMotaCT = true

        this.Visible.popupFilter = this.kieu == 0
      }else if(this.thongTinNguoiDung.maTinh == "LAN"){
        this.Visible.colNgayNhap = true
      }else{
        this.Visible.colMaKhoTD = true
        this.Visible.colLoaiKho = true
      }

      var KTThamSo = await this.KiemTraThamSoMucDich("QLVT_VIEWTON_COLDG")
 
      if(KTThamSo.length > 0){
        if(KTThamSo[0] == 1){
          this.Visible.colDonGia = true
        }
      }

      // Load Quyền người dùng
      this.dataSource.dsQuyen = await this.getDSQuyenNguoiDung(this.thongTinNguoiDung.nguoiDungId)

      //Load DS kho theo nhân viên
      this.dataSource.DSKho = await this.getDSKhoTheoNhanVien(this.thongTinNguoiDung.nhanVienId)
      if(this.dataSource.DSKho.length > 0) {
        this.params.khoId = this.dataSource.DSKho[0].khoID
      }
      
      if(this.kieu == 1){
        this.dataSource.DSKho = []
        this.params.khoId = this.vkho_id
        this.Enabled.Tukho = false  
        this.Visible.DSThietBi = false
        this.Visible.DenNgay = false
        this.Visible.DSDangKy = true
      }else if( this.kieu == 2){
        this.dataSource.DSKho = await this.getDSKhoTheoNCC(this.thongTinNguoiDung.nhanVienId)
        this.params.khoId = this.vkho_id
        this.Enabled.Tukho = false  
        this.Visible.DSThietBi = false
        this.Visible.DenNgay = false
        this.Visible.DSDangKy = true
        this.Visible.CheckAll = false
      }else{
        this.dataSource.DSThietBi = await this.getDSThietBiTonKho(this.vkho_id)
        console.log(this.dataSource.DSThietBi , " = DSThietBi")
        this.Visible.CheckAll = false
        this.Visible.DenNgay = true
        this.Visible.DSDangKy = false
        this.Enabled.DenNgay = false
      }
      this.ds_vt_xuat()
    },
    
    ds_vt_xuat(){
      var _dt = this.DS_Xuat
      var _dt_2 = this.DS_Xuat

      for(const i in this._dtLuuVT){

        var _k = 0
        var _i = this._dtLuuVT[i]

        for(const j in this.dtVatTu){

          var _j = this.dtVatTu[j]

          if(_i.VATTU_ID == _j.VATTU_ID && _i.LOHANG == _j.LOHANG){

            var tien = _i.SOLUONG * _j.DONGIA
            var vat = tien/10

            if ('TRANGTHAI' in _j){

              _j.TRANGTHAI = _i.TRANGTHAI

            }else{

              _dt.push({
                CHON: _i.CHON,
                LOHANG: _j.LOHANG,
                KHO_ID: _j.KHO_ID,
                VATTU_ID: _j.VATTU_ID,
                DONGIA: _j.DONGIA,
                DVITINH_ID: _j.DVITINH_ID,
                MA_VT: _j.MA_VT,
                TEN_VT: _j.TEN_VT,
                DVI_TINH: _j.DVI_TINH,
                SL_TON: _j.SL_TON,
                LOAITBI_ID: _j.LOAITBI_ID,
                SL_TREO: _j.SL_TREO,
                LOAI_TBI: _j.LOAI_TBI,
                SOTHANG_PB: _j.SOTHANG_PB,
                SOLUONG: _j.SL_TON,
                SL_XUAT: _i.SOLUONG,
                THANHTIEN: tien,
                VAT: vat,
              })
            }

            if( ('TY_LE' in _i) && ('MAKHOTD' in _i) && ('LOAIKHO' in _i) && ('MATHETS' in _i) && ('MATHUNG' in _i)){

              _dt[i].TY_LE = _i.TY_LE
              _dt[i].MAKHOTD = _i.MAKHOTD == null || _i.MAKHOTD == "" ? 0 : _i.MAKHOTD
              _dt[i].LOAIKHO = _i.LOAIKHO == null || _i.LOAIKHO == "" ? 0 : _i.LOAIKHO
              _dt[i].MATHETS = _i.MATHETS
              _dt[i].MATHUNG = _i.MATHUNG

              _j.TY_LE = _i.TY_LE
              _j.MAKHOTD = _i.MAKHOTD == null || _i.MAKHOTD == "" ? 0 : _i.MAKHOTD
              _j.LOAIKHO = _i.LOAIKHO == null || _i.LOAIKHO == "" ? 0 : _i.LOAIKHO
              _j.MATHETS = _i.MATHETS
              _j.MATHUNG = _i.MATHUNG

            }

            _j.SL_XUAT = _i.SOLUONG
            _j.CHON = "s1"

            _k += 1 
          }
        }

        if(_k == 0){
          var tien = _i.SOLUONG * _i.DONGIA
          var vat = tien/10
          var loaikho = _i.LOAIKHO == null || _i.LOAIKHO == "" ? 0 : _i.LOAIKHO
          var makho = _i.MAKHOTD == null || _i.MAKHOTD == "" ? 0 : _i.MAKHOTD

          if ('TRANGTHAI' in _j){

            _dt_2.push({
                CHON: _i.CHON,
                LOHANG: _i.LOHANG,
                KHO_ID: this.vkho_id,
                VATTU_ID : _i.VATTU_ID,
                DONGIA : _i.DONGIA,
                DVITINH_ID : "",
                MA_VT: _i.MA_VT,
                TEN_VT: _i.TEN_VT,
                TEN_VT: _i.TEN_VT,
                DVI_TINH : _i.DVI_TINH,
                SL_TON : _i.SOLUONG,
                LOAITBI_ID : _i.LOAITBI_ID,

                SL_TREO : 0,
                LOAI_TBI : _i.LOAI_TBI,
                SOTHANG_PB : _i.SOTHANG_PB,
                SOLUONG : _i.SOLUONG,
                SL_XUAT: _i.SOLUONG,
                THANHTIEN : tien,
                VAT : vat,
                TRANGTHAI: _i.TRANGTHAI,
                LOAIKHO : loaikho,
                MAKHOTD: makho
              })

          }else{
            _dt_2.push({
                CHON: _i.CHON,
                LOHANG: _i.LOHANG,
                KHO_ID: this.vkho_id,
                VATTU_ID : _i.VATTU_ID,
                DONGIA : _i.DONGIA,
                DVITINH_ID : "",
                MA_VT: _i.MA_VT,
                TEN_VT: _i.TEN_VT,
                TEN_VT: _i.TEN_VT,
                DVI_TINH : _i.DVI_TINH,
                SL_TON : _i.SOLUONG,
                LOAITBI_ID : _i.LOAITBI_ID,
                SL_TREO : 0,
                LOAI_TBI : _i.LOAI_TBI,
                SOTHANG_PB : _i.SOTHANG_PB,
                SOLUONG : _i.SOLUONG,
                SL_XUAT: _i.SOLUONG,
                THANHTIEN : tien,
                VAT : vat,      
                LOAIKHO : loaikho,
                MAKHOTD: makho
              })

          }
          _dt_2.forEach(e =>{
            this.dtVatTu.push(e)
          })
          _dt.forEach(e =>{
            this._dtVT_Xuat.push(e)
          })

          this.dataSource.DSVatTu = this.dtVatTu
          var DSVatTuFilter = this.dtVatTu.filter(
            function (el) {
              return el.CHON == "s1";
            }
          );
          if(DSVatTuFilter.length >0){
            this.dataSource.DSXuat = DSVatTuFilter
          }
        }
      }


    }

  },
};
</script>
