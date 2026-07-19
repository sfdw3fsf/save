<template src='./TraCuuHopDongVaDonHang.html'></template>

<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import Vue from "vue";
// import breadcrumb from '@/components/breadcrumb'
import TraCuuHopDongVaDonHangAPI from "../api/TraCuuHopDongVaDonHangAPI";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/tracuuhopdongvadonhang";
import select2 from "@/components/Select2.vue";
import {
  Group,
  Page,
  Filter,
  Sort,
  Resize,
  ForeignKey,
} from "@syncfusion/ej2-vue-grids";
import { Query, Predicate } from "@syncfusion/ej2-data";
import { L10n } from "@syncfusion/ej2-base";
import XLSX from "xlsx";
import frmGiaHanHD from "../frmGiaHanHD";
import { timeout } from "@syncfusion/ej2-maps";

L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
    },
  },
});

var defaultParams = {
  p_ckb_tracuuhopdong: false, // ckbHDTimKiem
  p_ma_hd: "", //txtMaHD
  p_ckb_trangthai_hd: false, //ckbTTHD
  p_trangthai_hd: "", //cboTrangThaiHD
  p_ckb_kieu_hd: false, //ckbKieuHD
  p_kieu_hd: "", //cboKieuHD
  p_ckb_mucdich: false, //ckbMucDich
  p_mucdich: "", //cbo_MucDich
  p_ckb_kho_nhathau: false, //ckbNhaThau
  p_kho_nhathau: "", //cbo_KhoNhaThau
  p_ckb_donvi_dk: false, //ckbDonVi
  p_donvi_dk: "", //cboDviDK

  p_ckb_tungay_dk: false, //ckb_TuNgay
  p_tungay_dk: new Date(), //dtpTuNgayDK
  p_ckb_denngay_dk: false, //ckb_DenNgay
  p_denngay_dk: new Date(), //dtpDenNgayDK
  p_ckb_giatri_hd: false, //ckbGiaTriHD
  p_giatri_hd: 1, //spGiaTriHD
  p_ckb_ncl_lonhon: false, //ckb_NgayHH_LonHon
  p_ngay_conlai_lonhon: 1, //spNgay_CL_LonHon
  p_ckb_ncl_nhohon: false, //ckb_NgayHH_NhoHon
  p_ngay_conlai_nhohon: 1, //spNgay_CL_NhoHon
  p_ckb_donhang: false, //ckb_SLDonHang
  p_ckb_hd_ao: false, //ckbHDAo
  p_ckb_hd_dat: false, //ckbHD_Dat

  p_ckb_tracuudonhang: false, //ckbDH_TimKiem
  p_ma_donhang: "", //txtDonHang
  p_ckb_trangthai_dh: false, //ckbTrangThaiDH
  p_trangthai_dh: "", //cboTrangThaiDH
  p_ckb_hopdong: false, //ckbHopDong
  p_hopdong: 0, //cboHopDong
  p_ckb_donhangcha: false, //ckbDonHangCha
  p_donhangcha: "", //cboDonHangCha
  p_ckb_ngaynh_tu: false, //ckbNgayNH_Tu
  p_ngaynh_tu: new Date(), //dtpNgayNH_Tu
  p_ckb_ngaynh_den: false, //ckbNgayNH_Den
  p_ngaynh_den: new Date(), //dtpNgayNH_Den
  p_ckb_nghiemthu_tu: false, //ckbNghiemThu_Tu
  p_nghiemthu_tu: new Date(), //dtpNghiemThu_Tu
  p_ckb_nghiemthu_den: false, //ckbNghiemThu_Den
  p_nghiemthu_den: new Date(), //dtpNghiemThu_Den

  p_ckb_NhanVienNT: false, // ckbNhanVienNT bổ sung nhưng ko show trên form
  p_DonVi_DH: null, // ckbNhanVienNT bổ sung nhưng ko show trên form
  p_ckb_DonVi_DH: false, // ckbDonVi_DH
  p_NhanVienNT: null,
};

export default {
  components: { XLSX, appSelect2: select2, frmGiaHanHD },
  name: "TraCuuHopDongVaDonHang",
  mounted() {},
  provide: {
    grid: [Group, Page, Filter, Sort, Resize, ForeignKey],
  },
  computed: {
    ...mapState("tracuuhopdongvadonhang", statePropertyName),
    ...mapGetters("tracuuhopdongvadonhang", getterName),
  },
  props: {
    isPopup: false,
  },
  data() {
    return {
      header: {
        title: "TRA CỨU HỢP ĐỒNG VÀ ĐƠN HÀNG",
        list: [
          { name: "Quản lý vật tư", link: { name: "Tra cứu" } },
          {
            name: "Tra cứu hợp đồng và đơn hàng",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      params: Object.assign({}, defaultParams),
      DSMucDichDK: [],

      DSMucDichMuaSam: [],
      cboMucDichFilter: [],

      DSTrangThaiHD: [],
      cboTrangThaiFilter: [],

      DSKhoNhaThau: [],
      cboKhoNhaThauFilter: [],

      DSDonVi: [],
      cboDonViFilter: [],
      isFirstLoadDSHopDong: true,
      DSTrangThaiDonHang: [],
      DSHopDongDK: [],
      DSDonHangCha: [],
      // DSDonHangTheoHD: [],

      GridDanhSachHD: [],
      GridDanhSachDonHang: [],
      GridDSVatTu_CTHD: [],
      GridDS_VT_DonHang: [],

      cboDviTinhFilter: [],
      cboTrangThaiDKFilter: [],
      cboVatTuFilter: [],
      cboKieuHD_Filter: [],
      cboDvi_DH: [],

      // cboNhanVienNTFilter: [],
      // cboNhanVienNT: [],

      thietlap: 1, // Nếu thiết lập =0 là Form Tra cứu cho tìm cả 2, Nếu thiết lập =1 là chỉ tìm theo hợp đồng Thieets lapj =2 cho don hang
      tl: 0, // tl = 0 là theo hợp đồng , tl =1 là theo đơn hàng , tl=-1 là cả 2
      chapnhan: false,
      _owner: false, // frmHDMuaSam
      _owner_TT: false, //frmThanhToanDH
      _owner_dh: false, //frmDonHang
      MaxValue: 2147483647,
      DSKieuHD: [
        { KIEU_HD: "Theo số lượng", KIEUHD_ID: 1 },
        { KIEU_HD: "Theo giá trị", KIEUHD_ID: 2 },
      ],

      dh_nv_nt: null, // bổ sung biến nhưng 0 show trên form
      vdvi_id: null, // bổ sung biến nhưng 0 show trên form

      accept: 0, // = 0 khong hien thi nut gia han, = 1 truyen tu form hd_muasam => hien thi nut gia han
      visible: {
        tsbtnGiaHan: true,
        tsbtnDongY: true,
        tsbtnThanhToan: false,
      },
      readonly: {
        ckbHopDong: false,
        ckbDH_TimKiem: false,
        ckbHDTimKiem: false,
        txtMaHD: false,
        txtDonHang: false,
        spNgay_CL_LonHon: false,
        spNgay_CL_NhoHon: false,
        spGiaTriHD: false,
        ckbDonVi: false,
      },
      enable: {
        tsbtnGiaHan: false,
        cboDviDK: true,
        tsbtnDongY: false,
      },
      dh_temp_id: "",
      tt_temp_id: "",
      temp_hd_id: "",
      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,
      },
      format: { format: "N3", useGrouping: true },
      templateOptions: {
        create: function (args) {
          if (["STT", "btnXoaVT", "btnEditTbi"].includes(args.column.field)) {
            return '<span class="fa input-icon-right"></span>';
          }

          return (
            '<div class="input-icon-right"><input id="' +
            args.column.field +
            'FilterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>'
          );
        },
        write: function (args) {},
      },
      cRowSelected: function () {
        return {
          template: Vue.component("columntemplate", {
            template: '<span class="fa"></span>',
          }), // end of template
        };
      },
      iTemplate: function (e) {
        return {
          template: Vue.component("itemTemplate", {
            template: ` <div class="row">
                                    <div class="col-sm-2 border">
                                      <span>{{data.MA_MD}}</span>
                                    </div>
                                    <div class="col-sm-10 border">
                                      <span>{{data.MUCDICH}}</span>
                                    </div>
                                  </div>`,
            data() {
              return {
                data: {},
              };
            },
          }),
        };
      },
      onFiltering: function (e) {
        var query = new Query();
        let predicate;

        //frame the query based on search string with filter type.
        if (e.text !== "") {
          predicate = new Predicate("MA_MD", "Contains", e.text);
          predicate = predicate.or("MUCDICH", "contains", e.text);
          query.where(predicate);
        }

        //pass the filter data source, filter query to updateData method.
        e.updateData(this.dataSource, query);
      },
      showTraCuuHD: true,
      showTraCuuDH: true,
      pageSettings: {
        pageSizes: ["10", "20", "50", "100", "All"],
        pageSize: 10,
      },
      grvDanhSachHDPage: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0,
      },
      GridDSVatTu_CTHDPage: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0,
      },
      GridDanhSachDonHangPage: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0,
      },
      GridDS_VT_DonHangPage: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0,
      },
      hdms_id: 0,
      grcToThau: [],
    };
  },
  methods: {
    ...mapActions("tracuuhopdongvadonhang", actionName),
    ...mapMutations("tracuuhopdongvadonhang", mutationName),

    // chuyển Date thành type string
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_StringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    grvDanhSachHDPageHandler(e) {
      this.grvDanhSachHDPage.page = e.pageIndex;
      this.grvDanhSachHDPage.maxSize = e.pageSize;
    },
    GridDSVatTu_CTHDPageHandler(e) {
      this.GridDSVatTu_CTHDPage.page = e.pageIndex;
      this.GridDSVatTu_CTHDPage.maxSize = e.pageSize;
    },
    GridDanhSachDonHangPageHandler(e) {
      this.GridDanhSachDonHangPage.page = e.pageIndex;
      this.GridDanhSachDonHangPage.maxSize = e.pageSize;
    },
    GridDS_VT_DonHangPageHandler(e) {
      this.GridDS_VT_DonHangPage.page = e.pageIndex;
      this.GridDS_VT_DonHangPage.maxSize = e.pageSize;
    },

    async frmTraCuuHopDong_FormLoad() {
      if (this._owner_TT) {
        console.log(this._owner_TT, " = _owner_TT");
        this.Ckb_TimKiem();
        await this.Load_Cbo_HD();
        await this.Load_Cbo_DonHang();
        this.thietlap = 0;
        // this.params.cboDvi_DH = this.$auth.getDonViID() // không tìm thấy cboDvi_DH
        this.visible.tsbtnThanhToan = false;
      } else if (this._owner) {
        console.log(this._owner, " = _owner");
        this.Ckb_TimKiem();
        await this.Load_Cbo_HD();
        await this.Load_Cbo_DonHang();
        this.thietlap = 1;
        this.params.p_trangthai_hd = 1;
        this.params.p_donvi_dk = this.$auth.getDonViID();
        this.enable.tsbtnGiaHan = false;
      } else if (this._owner_dh) {
        console.log(this._owner_dh, " = _owner_dh");
        this.Ckb_TimKiem();
        await this.Load_Cbo_HD();
        await this.Load_Cbo_DonHang();
        this.thietlap = 1;
        this.visible.tsbtnGiaHan = false;
        this.visible.tsbtnThanhToan = false;
        this.params.p_trangthai_hd = 1;
        this.params.p_donvi_dk = this.$auth.getDonViID();
      }

      if (this._owner) {
        await this.TimKiem();
        let indexData = 0;
        // console.log("this.GridDanhSachHD",this.GridDanhSachHD,this.temp_hd_id);
        let itemConfirm = this.GridDanhSachHD.find((x, index) => {
          if (x.HDMS_ID == this.temp_hd_id) {
            indexData = index;
            return x;
          }
        });
        // console.log(indexData);
        // this.$refs["TableHopDong"].grid.selectRow(indexData);
        // Focus_Row_HD(temp_hd_id);
        await this.FocusGridViewRow("TableHopDong", indexData);
      }
      if (this._owner_dh) {
        await this.TimKiem();
      }
      if (this._owner_TT) {
        await this.TimKiem();
      }
    },
    async Load_Cbo_HD() {
      // try {
      // khai báo kiểu HĐ
      let dt_dvitinh = await this.getDSDonViTinh();
      let dt_mucdich = await this.getDSMucDichDK({ DK: 1 }); // DHSX đang hardcode 1
      let dt_dvi = await this.getDSDonVi();
      let dt_vattu = await this.getDSVatTu();

      this.params.p_denngay_dk = new Date();
      this.params.p_tungay_dk = new Date();
      this.params.p_donvi_dk = this.$auth.getDonViID();

      this.DSKhoNhaThau = await this.getDSKhoNhaCC({ DK: 1 }); // DHSX đang hardcode 1
      this.DSKhoNhaThau.length
        ? (this.params.p_kho_nhathau = this.DSKhoNhaThau[0].KHO_ID)
        : "";
      this.cboKhoNhaThauFilter = await this.getDSKhoNhaCC({ DK: -1 });

      this.DSTrangThaiHD = await this.getDSTrangThaiHD({ DK: 1 }); // DHSX đang hardcode 1
      this.DSTrangThaiHD.length
        ? (this.params.p_trangthai_hd = this.DSTrangThaiHD[0].TTHD_ID)
        : "";
      this.cboTrangThaiFilter = await this.getDSTrangThaiHD({ DK: -1 });

      this.cboTrangThaiDKFilter = await this.getDSTrangThaiDK();

      this.cboDviTinhFilter = dt_dvitinh;

      this.DSMucDichMuaSam = await this.getDSMucDichMuaSam({ DK: 1 }); // DHSX đang hardcode 1
      this.DSMucDichMuaSam.length
        ? (this.params.p_mucdich = this.DSMucDichMuaSam[0].MUCDICH_ID)
        : "";

      this.cboVatTuFilter = dt_vattu;
      this.cboMucDichFilter = await this.getDSMucDichMuaSam({ DK: -1 });

      this.DSDonVi = dt_dvi;
      this.cboDonViFilter = dt_dvi;
      this.cboKieuHD_Filter = this.DSKieuHD;
      this.params.p_kieu_hd = 1;
      // } catch (error) {
      //     this.$toast.error("Lỗi khi Load_Cbo_HD")
      // }
    },
    async Load_Cbo_DonHang() {
      // try {
      this.params.p_ngaynh_tu = new Date();
      this.params.p_ngaynh_den = new Date();
      this.params.p_nghiemthu_den = new Date();
      this.params.p_nghiemthu_tu = new Date();
      this.DSTrangThaiDonHang = await this.getDSTrangThaiDonHang();
      this.DSTrangThaiDonHang.push({
        ttdhid: 10,
        trangThaiDH: "Đã nhập kho hết",
      });
      this.DSTrangThaiDonHang.length
        ? (this.params.p_trangthai_dh = this.DSTrangThaiDonHang[0].ttdhid)
        : "";
      this.cboTrangThaiDHFilter = this.DSTrangThaiDonHang;

      if (this.thietlap == 0) {
        this.DSHopDongDK = await this.getDSHopDongDK({ donViId: -1 }); // DHSX đang hardcode -1
        this.DSHopDongDK.length
          ? (this.params.p_hopdong = this.DSHopDongDK[0].HDMS_ID)
          : "";
      } else {
        this.DSHopDongDK = await this.getDSHopDongDK({
          donViId: this.$auth.getDonViID(),
        });
        this.DSHopDongDK.length
          ? (this.params.p_hopdong = this.DSHopDongDK[0].HDMS_ID)
          : "";
      }
      // let nv_nt = await this.getDSNhanVienNghiemThu()
      // this.cboNhanVienNT = nv_nt
      // this.cboNhanVienNTFilter = nv_nt

      this.DSDonHangCha = await this.getDSDonHangDK({ donHangChaId: -1 }); // DHSX đang hardcode -1

      this.cboDvi_DH = await this.getDSDonVi();

      // } catch (error) {
      //   this.$toast.error("Lỗi khi Load_Cbo_DonHang")
      // }
    },
    async Lay_DS_DonHang_TheoHD(hdms_id) {
      // try {
      let GridDanhSachDonHang = await this.getDSDonHangTheoHD({
        maHopDong: hdms_id,
      }); // 0 là mã đơn vị người dùng
      await this.showData_GridDanhSachDonHang(GridDanhSachDonHang);

      // } catch (error) {
      //   this.$toast.error("Lỗi khi Lay_DS_DonHang_TheoHD")
      // }
    },
    Ckb_TimKiem() {
      if (
        this.thietlap != 0 ||
        !this.params.p_ckb_tracuuhopdong ||
        !this.params.p_ckb_tracuudonhang
      ) {
        this.readonly.ckbHopDong = false;
      } else {
        this.params.p_ckb_hopdong = false;
        this.readonly.ckbHopDong = true;
      }
      this.visible.tsbtnGiaHan =
        this.params.p_ckb_tracuuhopdong && this.accept == 1;
      if (!this._owner_TT) {
        this.visible.tsbtnThanhToan = this.params.p_ckb_tracuudonhang;
      }
      this.enable.tsbtnGiaHan = this.params.p_hopdong != 0; // hdms_id
      if (this.dh_temp_id != 0 && this.tt_temp_id > 1) {
        this.enable.tsbtnThanhToan = true;
      } else {
        this.enable.tsbtnThanhToan = false;
      }
      this.params.p_ckb_tracuuhopdong ? (this.readonly.txtMaHD = false) : "";
      this.params.p_ckb_tracuudonhang ? (this.readonly.txtDonHang = false) : "";
    },
    async TraCuuHopDongV1() {
      let GridDanhSachHD = await this.getTraCuuHopDong({
        tthdId: this.params.p_ckb_trangthai_hd
          ? parseInt(this.params.p_trangthai_hd)
          : 0,
        mucDichId: this.params.p_ckb_mucdich
          ? parseInt(this.params.p_mucdich)
          : 0,
        khoId: this.params.p_ckb_kho_nhathau
          ? parseInt(this.params.p_kho_nhathau)
          : 0,
        donViId: this.params.p_ckb_donvi_dk
          ? parseInt(this.params.p_donvi_dk)
          : -1,
        tuNgayYc: this.params.p_ckb_tungay_dk
          ? this.f_DateToString(this.params.p_tungay_dk)
          : "",
        denNgayYc: this.params.p_ckb_denngay_dk
          ? this.f_DateToString(this.params.p_denngay_dk)
          : "",
        soNgayClLh: this.params.p_ckb_ncl_lonhon
          ? parseInt(this.params.p_ngay_conlai_lonhon)
          : 0,
        soNgayClNh: this.params.p_ckb_ncl_nhohon
          ? parseInt(this.params.p_ngay_conlai_nhohon)
          : 0,
        loai: this.params.p_ckb_hd_ao ? 1 : 0,
        sldh: this.params.p_ckb_donhang ? 1 : 0,
        dat: this.params.dk_hd ? 1 : 0,
        kieuHDId: this.params.p_ckb_kieu_hd
          ? parseInt(this.params.p_kieu_hd)
          : 0,
        giaTriHD: this.params.p_ckb_giatri_hd
          ? parseInt(this.params.p_giatri_hd)
          : 0,
      });
      await this.showData_GridDanhSachHD(GridDanhSachHD);
    },
    async TraCuuHopDongV2() {
      let GridDanhSachHD = await this.getTraCuuHopDong({
        tthdId: this.params.p_ckb_trangthai_hd
          ? parseInt(this.params.p_trangthai_hd)
          : 0,
        mucDichId: this.params.p_ckb_mucdich
          ? parseInt(this.params.p_mucdich)
          : 0,
        khoId: this.params.p_ckb_kho_nhathau
          ? parseInt(this.params.p_kho_nhathau)
          : 0,
        donViId: this.params.p_ckb_donvi_dk
          ? parseInt(this.params.p_donvi_dk)
          : -1,
        tuNgayYc: this.params.p_ckb_tungay_dk
          ? this.f_DateToString(this.params.p_tungay_dk)
          : "",
        denNgayYc: this.params.p_ckb_denngay_dk
          ? this.f_DateToString(this.params.p_denngay_dk)
          : "",
        soNgayClLh: this.params.p_ckb_ncl_lonhon
          ? parseInt(this.params.p_ckb_ncl_lonhon)
          : 0,
        soNgayClNh: this.params.p_ckb_ncl_nhohon
          ? parseInt(this.p_ckb_ncl_nhohon)
          : 0,
        loai: this.params.p_ckb_hd_ao ? 1 : 0,
        sldh: this.params.p_ckb_donhang ? 1 : 0,
        dat: this.params.dk_hd ? 1 : 0,
        kieuHDId: 0,
        giaTriHD: 0,
      });
      await this.showData_GridDanhSachHD(GridDanhSachHD);
    },
    async TraCuuDonHang() {
      let GridDanhSachDonHang = await this.getTraCuuDonHang({
        tthdId: this.params.p_ckb_trangthai_dh
          ? parseInt(this.params.p_trangthai_dh)
          : 0,
        hdmsId: this.params.p_ckb_hopdong ? parseInt(this.params.p_hopdong) : 0,
        dhChaId: this.params.p_ckb_donhangcha
          ? parseInt(this.params.p_donhangcha)
          : 0,
        nvnt: this.params.p_ckb_NhanVienNT
          ? parseInt(this.params.p_NhanVienNT)
          : 0, //cboNhanVienNT
        dvId: this.params.p_ckb_DonVi_DH ? parseInt(this.params.p_DonVi_DH) : 0, //ckbDonVi_DH
        nhanDhTu: this.params.p_ckb_ngaynh_tu
          ? this.f_DateToString(this.params.p_ngaynh_tu)
          : "",
        nhanDhDen: this.params.p_ckb_ngaynh_den
          ? this.f_DateToString(this.params.p_ngaynh_den)
          : "",
        ntDhTu: this.params.p_ckb_nghiemthu_tu
          ? this.f_DateToString(this.params.p_nghiemthu_tu)
          : "",
        ntDhDen: this.params.p_ckb_nghiemthu_den
          ? this.f_DateToString(this.params.p_nghiemthu_den)
          : "",
        loaiDH: -1, // ckbDonHangAo
      });
      await this.showData_GridDanhSachDonHang(GridDanhSachDonHang);
    },
    async ClearGrid() {
      this.GridDanhSachHD = [];
      this.GridDSVatTu_CTHD = [];
      this.GridDanhSachDonHang = [];
      this.GridDS_VT_DonHang = [];
    },
    async TimKiem() {
      await this.ClearGrid();
      // Nếu tìm theo hợp đồng ko tìm theo đơn hàng
      if (this.params.p_ckb_tracuuhopdong && !this.params.p_ckb_tracuudonhang) {
        // try {
        if (this.params.p_kieu_hd != 0 || this.params.p_giatri_hd != 0) {
          await this.TraCuuHopDongV1();
        } else {
          await this.TraCuuHopDongV2();
        }
        // } catch (error) {
        //   this.$toast.error("Lỗi khi danh sách hợp đồng !")
        // }
      }

      // Nếu check đơn hàng ko check hợp đồng
      if (!this.params.p_ckb_tracuuhopdong && this.params.p_ckb_tracuudonhang) {
        // try {
        this.hdms_id = 0;
        await this.TraCuuDonHang();

        // } catch (error) {
        //   this.$toast.error("Lỗi khi danh sách đơn hàng !")
        // }
      }

      // Nếu check tìm kiếm theo cả 2
      if (this.params.p_ckb_tracuuhopdong && this.params.p_ckb_tracuudonhang) {
        this.hdms_id = 0;
        // try {
        if (this.params.p_kieu_hd != 0 || this.params.p_giatri_hd) {
          await this.TraCuuHopDongV1();
        } else {
          await this.TraCuuHopDongV2();
        }
        await this.TraCuuDonHang();
        // } catch (error) {
        //   this.$toast.error("Lỗi khi danh sách đơn hàng !")
        // }
      }
    },
    Focus_Row_HD(temp_id) {
      if (temp_id != 0) {
        row = this.GridDanhSachHD.findIndex(
          (a) => a.HDMS_ID.toString() == temp_id.toString()
        );
        this.$refs.TableHopDong.selectRow(row, true);
      }
    },
    async frmTraCuuHopDong() {
      // quy edit (button DongY Hien thi khi goi popup)
      if (this._owner || this._owner_TT || this._owner_dh) {
        this.visible.tsbtnDongY = true;
      } else {
        this.visible.tsbtnDongY = false;
      }

      this.Ckb_TimKiem();
      this.thietlap = 0;
      await this.Load_Cbo_HD();
      await this.Load_Cbo_DonHang();
    },
    // frmTraCuuHopDong(owner){ // Form thanh toasn
    //   this._owner_TT = owner
    // },
    // async frmTraCuuHopDong(owner){ // Form Hợp đồng gọi
    //   await this.CapNhatHDHetHan({})
    //   this._owner = owner
    // },
    // frmTraCuuHopDong(owner){ //frmDonHang
    //   this._owner_dh = owner
    // },
    async tsbtnTimKiem_Click() {
      await this.TimKiem();
    },
    grvDanhSachHD_RowStyle(row) {
      if (row && row.HDMS_ID.toString != "0") {
        this.enable.tsbtnGiaHan = true;
      } else {
        this.enable.tsbtnGiaHan = false;
      }
    },
    async tsbtnGiaHan_Click() {
      var f = this.$refs.frmGiaHanHD;
      f.hdms_id = this.hdms_id;
      var temp = this.hdms_id;
      await f.HienThiLS_GiaHan();
      await f.ShowDialog();
    },
    async Close_tsbtnGiaHan() {
      await this.CapNhatHDHetHan();
      // await this.CAPNHAT_HD_HETHAN()
      await this.TimKiem();
      let indexData = 0;
      let itemConfirm = this.GridDanhSachHD.find((x, index) => {
        if (x.HDMS_ID == temp) {
          indexData = index;
          return x;
        }
      });
      await this.FocusGridViewRow("TableHopDong", indexData);
      // await this.Focus_Row_HD()
    },
    ckbHDTimKiem_CheckedChanged() {
      this.Ckb_TimKiem();
    },
    ckbDH_TimKiem_CheckedChanged() {
      this.Ckb_TimKiem();
    },
    ckbTTHD_CheckedChanged() {
      this.enable.cboTrangThaiHD = this.params.p_trangthai_hd;
    },
    async grvDanhSachHD_FocusedRowChanged(row) {
      if (this.isFirstLoadDSHopDong) {
        // tránh lặp lần đầu
        this.isFirstLoadDSHopDong = false;
        return;
      }
      if (!row) {
        this.enable.tsbtnDongY = false;
        this.params.p_hopdong = 0;
        this.params.p_ma_hd = "";
        this.enable.tsbtnGiaHan = false;
        this.hdms_id = 0;
        return;
      }
      this.enable.tsbtnDongY = row.TTHD_ID.toString() != "6";
      this.hdms_id = row.HDMS_ID || 0;

      this.params.p_ma_hd = row.MA_HD.toString();
      this.enable.tsbtnGiaHan = true;
      if (
        this.thietlap == 1 ||
        (this.thietlap == 0 && (this.tl == 1 || this.tl == 0))
      ) {
        await this.Lay_DS_DonHang_TheoHD(parseInt(row.HDMS_ID));
        await this.HienThiDS_VT_CTHD(parseInt(row.HDMS_ID));
      }
      // if (this.filter){

      // }
      this.grcToThau = await this.getDS_NV_TG_V2({ hdms_id: this.hdms_id });
    },
    async HienThiDS_VT_CTHD(hdms_id) {
      // try {
      this.GridDSVatTu_CTHD = await this.getDSVatTuTheoHD({
        maHopDong: hdms_id,
      });
      await this.showData_GridDSVatTu_CTHD();
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi lấy danh sách vật tư của hợp đồng")
      // }
    },
    async grvDS_DonHang_FocusedRowChanged(args) {
      if (!args) {
        this.params.p_ma_donhang = "";
        this.enable.tsbtnThanhToan = false;
        this.GridDS_VT_DonHang = [];
        return;
      }
      let row = args.data;
      this.dh_temp_id = parseInt(row.DONHANG_ID);
      if (
        this.thietlap == 1 ||
        (this.thietlap == 0 && (this.tl == 1 || this.tl == -1))
      ) {
        // khác 1 tức là hợp đồng và nếu =0 tức  là dang ở fomr tk
        this.params.p_ma_donhang = row.MA_DH.toString();
      }
      if (!this._owner && !this._owner_dh) {
        if (row.TTDH_ID.toString() != "1" && row.TTDH_ID.toString() != "6") {
          this.enable.tsbtnThanhToan = true;
          this.enable.tsbtnDongY = true;
        } else {
          this.enable.tsbtnThanhToan = false;
          this.enable.tsbtnDongY = false;
        }
      }
      this.GridDS_VT_DonHang = await this.getVatTuChiTietTheoDH({
        maHopDong: this.dh_temp_id,
      });
      await this.showData_GridDS_VT_DonHang();
    },
    // FilterContextToolStripMenuItem_Click(){
    //   this.filter = true
    //   this.enable.FilterContextToolStripMenuItem = false
    //   this.enable.All_ToolStripMenuItem = true
    // }
    All_ToolStripMenuItem_Click() {
      this.filter = false;
      this.enable.FilterContextToolStripMenuItem = true;
      this.enable.All_ToolStripMenuItem = false;
    },
    tsbtnThanhToan_Click() {
      // DHSX đang comment
    },
    // tim_theo_ma
    async tim_theo_ma_donhang() {
      if (this.params.p_ma_donhang) {
        if (this.params.p_ckb_tracuudonhang) {
          let GridDanhSachDonHang = await this.TraCuuHDTheoDonHang({
            ma: this.params.p_ma_donhang,
            kieu: 1,
          });
          await this.showData_GridDanhSachDonHang(GridDanhSachDonHang);
        }
      }
    },
    async tim_theo_ma_hopdong() {
      this.ClearGrid();
      if (this.params.p_ckb_tracuuhopdong) {
        if (this.params.p_ma_hd) {
          let GridDanhSachHD = await this.TraCuuHDTheoDonHang({
            ma: this.params.p_ma_hd,
            kieu: 0,
          });
          await this.showData_GridDanhSachHD(GridDanhSachHD);
        }
      }
    },
    async showData_GridDanhSachHD(data) {
      console.log("showData_GridDanhSachHD showData_GridDanhSachHD");
      var self = this;
      data.forEach((a) => {
        a.NGAY_YC
          ? (a.NGAY_YC = self
              .f_StringToDate(a.NGAY_YC, "DD/MM/YYYY hh:mm:ss")
              .toISOString())
          : "";
        a.NGAY_KY
          ? (a.NGAY_KY = self
              .f_StringToDate(a.NGAY_KY, "DD/MM/YYYY hh:mm:ss")
              .toISOString())
          : "";
        a.NGAY_HH
          ? (a.NGAY_HH = self
              .f_StringToDate(a.NGAY_HH, "DD/MM/YYYY hh:mm:ss")
              .toISOString())
          : "";
        a.NGAY_KH
          ? (a.NGAY_KH = self
              .f_StringToDate(a.NGAY_KH, "DD/MM/YYYY hh:mm:ss")
              .toISOString())
          : "";
        a.NGAY_NT
          ? (a.NGAY_NT = self
              .f_StringToDate(a.NGAY_NT, "DD/MM/YYYY hh:mm:ss")
              .toISOString())
          : "";

        a.KHO_ID
          ? (a.NHATHAU =
              self.cboKhoNhaThauFilter.find(
                (b) => b.KHO_ID.toString() == a.KHO_ID.toString()
              ).TEN_KHO || "")
          : "";
        a.MUCDICH_ID
          ? (a.MUCDICH =
              self.cboMucDichFilter.find(
                (b) => b.MUCDICH_ID.toString() == a.MUCDICH_ID.toString()
              ).MUCDICH || "")
          : "";
        a.TTHD_ID
          ? (a.TRANGTHAI_HD =
              self.cboTrangThaiFilter.find(
                (b) => b.TTHD_ID.toString() == a.TTHD_ID.toString()
              ).TRANGTHAI_HD || "")
          : "";
        if (a.hasOwnProperty("KIEUHD_ID")) {
          let kieuHD_Obj = self.DSKieuHD.find(
            (b) => b.KIEUHD_ID.toString() == a.KIEUHD_ID.toString()
          );
          kieuHD_Obj
            ? (a.KIEU_HD = kieuHD_Obj.KIEU_HD)
            : (a.KIEU_HD = "[Chưa chọn giá trị]");
        } else {
          a.KIEU_HD = "[Chưa chọn giá trị]";
        }
        // a.hasOwnProperty('KIEUHD_ID') ? a.KIEU_HD = self.DSKieuHD.find(b=> b.KIEUHD_ID.toString() == a.KIEUHD_ID.toString()).KIEU_HD || "" : ""
        if (a.DONVI_ID.toString()) {
          let dvi = self.cboDonViFilter.find(
            (b) => b.dviId.toString() == a.DONVI_ID.toString()
          );
          dvi ? (a.DONVI = dvi.tenDV) : "";
        }

        a.CONLAI = parseInt(a.CONLAI || 0) || 0;
        a.TIEN_HD = parseInt(a.TIEN_HD || 0) || 0;
        a.TIEN_CONLAI = parseInt(a.TIEN_CONLAI || 0) || 0;
        a.TYLE = parseInt(a.TYLE || 0) || 0;
      });
      this.GridDanhSachHD = data;
    },
    async showData_GridDSVatTu_CTHD() {
      var self = this;
      this.GridDSVatTu_CTHD.forEach((a) => {
        a.DVITINH_ID
          ? (a.DVI_TINH =
              self.cboDviTinhFilter.find(
                (b) => b.dviTinhId.toString() == a.DVITINH_ID.toString()
              ).dviTinh || "")
          : "";

        a.SL_KD = parseInt(a.SL_KD || 0) || 0;
        a.SOLUONG = parseInt(a.SOLUONG || 0) || 0;
        a.TIEN = parseInt(a.TIEN || 0) || 0;
        a.VAT = parseInt(a.VAT || 0) || 0;
        a.DONGIA = parseInt(a.DONGIA || 0) || 0;
      });
    },
    async showData_GridDanhSachDonHang(data) {
      data.forEach((a) => {
        a.NGAY_NH
          ? (a.NGAY_NH = this.f_StringToDate(
              a.NGAY_NH,
              "DD/MM/YYYY hh:mm:ss"
            ).toISOString())
          : "";
        a.NGAY_CN
          ? (a.NGAY_CN = this.f_StringToDate(
              a.NGAY_CN,
              "DD/MM/YYYY hh:mm:ss"
            ).toISOString())
          : "";
        a.NGAY_NK
          ? (a.NGAY_NK = this.f_StringToDate(
              a.NGAY_NK,
              "DD/MM/YYYY hh:mm:ss"
            ).toISOString())
          : "";
        a.NGAY_NT
          ? (a.NGAY_NT = this.f_StringToDate(
              a.NGAY_NT,
              "DD/MM/YYYY hh:mm:ss"
            ).toISOString())
          : "";
        a.NGAY_TT
          ? (a.NGAY_TT = this.f_StringToDate(
              a.NGAY_TT,
              "DD/MM/YYYY hh:mm:ss"
            ).toISOString())
          : "";
        a.NGAY_YC
          ? (a.NGAY_YC = this.f_StringToDate(
              a.NGAY_YC,
              "DD/MM/YYYY hh:mm:ss"
            ).toISOString())
          : "";

        a.TTDH_ID != null && a.TTDH_ID != ""
          ? (a.TRANGTHAI_DH =
              this.cboTrangThaiDHFilter.find(
                (b) => b.ttdhid.toString() == a.TTDH_ID.toString()
              ).trangThaiDH || "")
          : "";
        a.TONGTIEN = parseInt(a.TONGTIEN || 0) || 0;
      });
      this.GridDanhSachDonHang = data;
      console.log(this.GridDanhSachDonHang);
    },
    async showData_GridDS_VT_DonHang() {
      console.log("GridDS_VT_DonHang ", this.GridDS_VT_DonHang);
      var self = this;
      this.GridDS_VT_DonHang.forEach((a) => {
        a.DVITINH_ID
          ? (a.DVI_TINH =
              self.cboDviTinhFilter.find(
                (b) => b.dviTinhId.toString() == a.DVITINH_ID.toString()
              ).dviTinh || "")
          : "";

        a.TONGTIEN = parseInt(a.TONGTIEN || 0) || 0;
        a.SOLUONG = parseInt(a.SOLUONG || 0) || 0;
        a.SL_NT = parseInt(a.SL_NT || 0) || 0;
        a.SL_HONG = parseInt(a.SL_HONG || 0) || 0;
        a.SL_CHUAN = parseInt(a.SL_CHUAN || 0) || 0;
        a.DONGIA = parseInt(a.DONGIA || 0) || 0;
        a.TIEN = parseInt(a.TIEN || 0) || 0;
        a.VAT = parseInt(a.VAT || 0) || 0;
      });
    },
    // DHSX thiếu, tự thêm vào
    async tsbtnHuyBo_Click() {
      this.params = Object.assign({}, defaultParams);
      this.GridDanhSachHD = [];
      this.GridDanhSachDonHang = [];
      this.GridDSVatTu_CTHD = [];
      this.GridDS_VT_DonHang = [];
      this.DSKhoNhaThau.length
        ? (this.params.p_kho_nhathau = this.DSKhoNhaThau[0].KHO_ID)
        : "";
      this.DSTrangThaiHD.length
        ? (this.params.p_trangthai_hd = this.DSTrangThaiHD[0].TTHD_ID)
        : "";
      this.DSMucDichMuaSam.length
        ? (this.params.p_mucdich = this.DSMucDichMuaSam[0].MUCDICH_ID)
        : "";
      this.DSTrangThaiDonHang.length
        ? (this.params.p_trangthai_dh = this.DSTrangThaiDonHang[0].ttdhid)
        : "";
      this.DSHopDongDK.length
        ? (this.params.p_hopdong = this.DSHopDongDK[0].HDMS_ID)
        : "";
    },
    tsbtnDongY_Click() {
      if (this._owner) {
        console.log("ok vo dung form");
        this.chapnhan = true;
        // this._owner.tracuu_id = this.hdms_id;
        this.$emit("onXacNhan", this.hdms_id);
        // this.$parent.$parent.$parent.tracuu_id = this.hdms_id;
        // this.$parent.hide()
      }
      if (this._owner_dh) {
        this.chapnhan = true;
        this.$parent.$parent.$parent.hd_tracuu_id = this.hdms_id;
        // this._owner_dh.hd_tracuu_id = this.hdms_id;
        this.$parent.hide();
      }
      if (this._owner_TT) {
        this.chapnhan = true;
        // frmThanhToanDH
        this.$parent.$parent.$parent.vdh_tracuu_id = this.dh_temp_id;
        // _owner_TT.vdh_tracuu_id = this.dh_temp_id;
        this.$parent.hide();
      }
    },
    async tsbtnExcelHopDong_Click() {
      if (this.GridDanhSachHD.length) {
        let dshd_temp = [];
        this.GridDanhSachHD.map((a) => dshd_temp.push({ HDMS_ID: a.HDMS_ID }));
        let temp_vt = await this.XuatDuLieu({
          ds: JSON.stringify(dshd_temp),
          kieu: 0,
        });
        let data1 = XLSX.utils.json_to_sheet(this.GridDanhSachHD);
        let data2 = XLSX.utils.json_to_sheet(temp_vt);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data1, "Sheet1"); // sheetAName is name of Worksheet
        XLSX.utils.book_append_sheet(wb, data2, "Sheet2");
        // export Excel file
        XLSX.writeFile(wb, "DS_HOPDONG-.xlsx");
      }
    },
    async tsbtnExcelVatTuHopDong_Click() {
      if (this.GridDSVatTu_CTHD.length) {
       
        let data1 = XLSX.utils.json_to_sheet(this.GridDSVatTu_CTHD);       
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data1, "VatTuHopDong"); // sheetAName is name of Worksheet        
        // export Excel file
        XLSX.writeFile(wb, "DS_VatTu_HopDong-.xlsx");
      }else{
        this.$toast.error("Không có dữ liệu vật tư của hợp đồng này!")
      }
    },
    async tsbtnExcelDonHang_Click() {
      if (this.GridDanhSachDonHang.length) {
        let dshd_temp = [];
        this.GridDanhSachDonHang.map((a) =>
          dshd_temp.push({ DONHANG_ID: a.DONHANG_ID })
        );
        let temp_vt = await this.XuatDuLieu({
          ds: JSON.stringify(dshd_temp),
          kieu: 1,
        });
        let data1 = XLSX.utils.json_to_sheet(this.GridDanhSachDonHang);
        let data2 = XLSX.utils.json_to_sheet(temp_vt);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data1, "Sheet1"); // sheetAName is name of Worksheet
        XLSX.utils.book_append_sheet(wb, data2, "Sheet2");
        // export Excel file
        XLSX.writeFile(wb, "DS_DONHANG-.xlsx");
      }
    },
    resetParentForm() {
      this._owner = false;
      this._owner_TT = false;
      this._owner_dh = false;
    },
    async FocusGridViewRow(grid, indexData) {
      if (grid == "TableHopDong") {
        let indexs = indexData;
        if (indexs != -1) {
          if (
            !(
              indexs + 1 >
                this.$refs[grid].pageIndexData *
                  this.$refs[grid].pageSizeData &&
              indexs + 1 <=
                (this.$refs[grid].pageIndexData + 1) *
                  this.$refs[grid].pageSizeData
            )
          ) {
            this.$refs[grid].$refs.pagination.gotoPage(
              parseInt(indexs / this.$refs[grid].pageSizeData)
            );
          }
          let self = this;
          let ind = parseInt(indexs % this.$refs[grid].pageSizeData);
          setTimeout(() => {
            self.$refs[grid].grid.selectRow(ind, true);
          }, 1000);
        }
      }
    },
    async dialogOpen() {
      try {
        this.$root.showLoading(true);
        this.resetParentForm();
        await this.frmTraCuuHopDong();
        await this.frmTraCuuHopDong_FormLoad();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.$root.showLoading(false);
      }
    },
  },
  created: async function () {
    // this.clearData();
    // neu call tu menu thi chay func created
    if (
      !(
        typeof this.$parent === "object" &&
        this.$parent.hasOwnProperty("ej2Instances")
      )
    ) {
      this.$root.showLoading(true);
      this.resetParentForm();
      await this.frmTraCuuHopDong();
      await this.frmTraCuuHopDong_FormLoad();
      this.$root.showLoading(false);
    }
  },
  watch: {
    GridDanhSachHD() {
      if (this._owner) {
        setTimeout(() => {
          let indexData = 0;
          console.log(
            "this.GridDanhSachHD",
            this.GridDanhSachHD,
            this.temp_hd_id
          );
          let itemConfirm = this.GridDanhSachHD.find((x, index) => {
            if (x.HDMS_ID == this.temp_hd_id) {
              indexData = index;
              return x;
            }
          });
          console.log(indexData);
          this.$refs["TableHopDong"].grid.selectRow(indexData);
        }, 1000);
      }
    },
  },
  destroyed() {},
};
</script>
<style>
.tracuu-hopdongvadonhang .e-grid tr.e-emptyrow td {
  text-align: center !important;
}
.disabled a {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

.disabled a:hover {
  cursor: default;
  color: #6c757d;
}
</style>