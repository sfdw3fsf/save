<template src='./UpdatePromotionDetail.html'></template>
<style scoped src='./UpdatePromotionDetail.scss'></style>
<script>
import { mapActions } from "vuex";
import gridview from "../../../../../../components/Shared/gridview.vue";
import LaborSource from "../LaborSource/LaborSource.vue";
import PromotionQuota from "../PromotionQuota/PromotionQuota.vue";
import Deposit from "./components/Deposit.vue";
import ServiceGT from "./components/ServiceGT.vue";
import EquipmentPromotion from "./components/EquipmentPromotion.vue";
import PromotionType from "./components/PromotionType.vue";
import Toanha from "./components/Toanha.vue";
import Loainha from "./components/Loainha.vue";
import Trangbi from "./components/Trangbi.vue";
import DSKhuyenmai from "./components/DSKhuyenmai.vue";
import Package from "./components/Package.vue";
import Installment from "./components/Installment.vue";
import { Query } from "@syncfusion/ej2-data";
import { mixin } from "./components/mixins";
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions"; 
import popupThemChiTietKm from "./components/popupThemChiTietKm.vue"
import {
  required,
  email,
  minLength,
  sameAs,
  minValue,
} from "vuelidate/lib/validators";
import * as moment from "moment";
export default {
  mixins: [mixin],
  components: {
    gridview,
    LaborSource,
    PromotionQuota,
    Deposit,
    ServiceGT,
    EquipmentPromotion,
    PromotionType,
    Toanha,
    Loainha,
    Package,
    Trangbi,
    DSKhuyenmai,
    Installment,
    popupThemChiTietKm
  },
  name: "UpdatePromotionDetail",
  props: ["khuyenmai_id", "dichvuvt_id", "index"],
  data: () => ({
    idx: 0,
    ds_nguonkmld: [],
    ds_ctkmlhtb: [],
    dsgoidadv: [],
    dstgtbi: [],
    dskmtbi: [],
    dstoanha: [],
    dstrangbi: [],
    dskhuyenmai: [],
    dsloainha: [],
    dsdvgt: [],
    rowdata: {},
    selectedTab: 0,
    state: {},
    promotionsCurrentRecordDetail: null,
    tableLoading: true, 
    detailPromotionHeader: [
      { label: "ID", filterAble: true, key: "chitietkm_id", width: "90px" },
      {
        label: "Tên tiết KM",
        filterAble: true,
        key: "ten_ctkm",
      },
      {
        label: "Hiển thị MĐ",
        filterAble: true,
        key: "cuoc_sd",
        align: "right",
      },
      {
        label: "Nhóm KM",
        filterAble: true,
        key: "tyle_kmld",
        align: "right",
      },
    ],
    selectionOptionsDetails: { checkboxMode: "ResetOnRowClick" },
    buttonStatus: {
      btnAddNew: true,
      btnSave: true,
      btnCancel: true,
      btnDelete: true,
      btnCopy: true,
      btnDinhMuc: true,
      btnCTKhoanmuc: true,
    },
    isShowLaborSource: false,
    isShowPromotionQuota: false,
    chitietkm_id: 0,
    isChange: true,
    km_theo_tien_tyle: 0,
    is_nhom_datcoc: false,
    form: {
      ma_km: "",
      sua_km_cuoc: false,
      sua_kmld: false,
      ten_km: "",
      tien_kmld: 0,
      vat_km: 0,
      tyle_vat: false,
      nguon_ld: "",
      nguonkm_id: 0,
      phan_bo: false,
      kieu_batdau: "",
      nhom_km: 0,
      thang_bdkm: 0,
      huong_km: 0,
      cuoc_sd: 0,
      cuoc_tb: 0,
      tyle_cuoctb: false,
      tyleKMLD: 0,
      tien_sd: 0,
      tien_tb: 0,
      ghi_chu: "",
      noi_dung: "",
      tu_ncc: false,
      hien_thi: false,
      cuoctb_maximun: 9999999999,
      cuoctb_decimal: 0,
    },
    fromTraGopThietBi: {
      nhomtb_id: null,
      kieu_batdau: 1,
      sothang_tg: 0,
      thang_bdtg: 0,
    },
    fromKuyenMaiThietBi: {
      nhomtb_id: null,
      nguonkm_id: null,
      tang_truoc: 0,
      tien_km: 0,
      vat_km: 0,
      sl_thang: 0,
      thang_db: 0,
      tyle: false,
      tyleKMTB: 0,
    },
    formTabDeposit: {
      nhom_datcoc_id: null,
      kieu_batdau: null,
      thang_bddc: 0,
      so_thang: 0,
      dat_coc: 0,
      tien_trudan: 0,
      cho_phep_gdv: false,
      nguonkm_id: null,
      radio_ip: 0,
      mien_ip: 0,
      mien_block: 0,
      tien_ck: 0,
      tyle_cuoc_ip: 0,
      kieu_cam_ket: 0,
      thang_bdck: 0,
      thang_ktck: 0,
    },
    isReset: false,
    formTabServiceGT: {
      tyle_giamcuoc_sd: 0,
      vat_giamcuoc_sd: 0,
      ngay_cn: moment(new Date()).format("DD/MM/YYYY"),
      tien_giamcuoc_sd: 0,
      tyle_giamcuoc_ld: 0,
      kieu_km_dvgt: false,
      isNgay: false,
    },
  }),
  validations: {
    form: {
      ma_km: { required },
      ten_km: { required },
      nguon_km: { required },
      kieu_batdau: { required },
      nhom_km: { required },
      thang_bdkm: { required },
      huong_km: { required },
      cuoc_sd: { required },
      cuoc_tb: { required },
      tien_sd: { required },
      tien_tb: { required },
      tyle_sd: { required },
      tyle_tb: { required },
      ghi_chu: { required },
      noi_dung: { required },
    },
  },
  computed: {
    danhSachNhomTB() {
      return JSON.parse(
        JSON.stringify(this.$store.getters["promotionDetail/danhSachNhomTB"])
      );
    },
    danhSachGoiThamGiaChiTietDaGan() {
      return this.$store.getters[
        "promotionDetail/danhSachGoiThamGiaChiTietDaGan"
      ];
    },
    danhSachTraGopThietBiChiTietDaGan() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters[
            "promotionDetail/danhSachTraGopThietBiChiTietDaGan"
          ]
        )
      );
    },
    
    danhSachTrangBiDaGan() {
      return this.$store.getters["promotionCommon/danhSachTrangBiDaGan"]; // Tổng
    },
    danhSachLoaiHinhTBDaGan() {
      return this.$store.getters["promotionDetail/danhSachLoaiHinhTBDaGan"];
    },
    danhSachToaNhaDaGan() {
      return this.$store.getters["promotionDetail/danhSachToaNhaDaGan"];
    },

    danhSachTrangbiDaGan() {
      return this.$store.getters["promotionDetail/danhSachTrangbiDaGan"]; // Chi tiết khuyến mại
    },

    danhSachKhuyenmaiDaGan() {
      return this.$store.getters["promotionDetail/danhSachKhuyenmaiDaGan"];
    },
    danhSachLoainhaDaGan() {
      return this.$store.getters["promotionDetail/danhSachLoainhaDaGan"];
    },
    danhSachKMTBDaGan() {
      return JSON.parse(
        JSON.stringify(this.$store.getters["promotionDetail/danhSachKMTBDaGan"])
      );
    },
    danhSachDichVuGTDaGan() {
      return this.$store.getters["promotionDetail/danhSachDichVuGTDaGan"];
    },
    danhSachNguonKMDaGan() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionDetail/danhSachNguonKMDaGan"]
        )
      );
    },
    danhSachNhomKM() {
      return this.$store.getters["promotionDetail/danhSachNhomKM"];
    },
    danhSachKieubatdau() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionDetail/danhSachKieubatdau"]
        )
      );
    },
    danhSachNguonKM() {
      return JSON.parse(
        JSON.stringify(this.$store.getters["promotionDetail/danhSachNguonKM"])
      );
    },
    danhSachNhomDatCoc() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionDetail/danhSachNhomDatCoc"]
        )
      );
    },
    danhSachChiTiet() {
      this.$root.showLoading(true);
      // function compare(a, b) {
      //   if (a.chitietkm_id > b.chitietkm_id) return -1;
      //   if (a.chitietkm_id < b.chitietkm_id) return 1;
      //   return 0;
      // }
      
      let data = JSON.parse( 
        JSON.stringify(this.$store.getters["promotionDetail/danhSachChiTiet"])
      );  
      this.$root.showLoading(false);  
      return data;  
      //return data ? data.sort(compare) : data;
    },
  },
  async created() {
    this.$root.showLoading(true);
    // this.getNhomKM({
    //   list_of_cols: "ma_ts, ten_ts, macdinh",
    //   order: "ten_ts",
    //   schema: "ADMIN",
    //   table_name: "thamso_md",
    //   where: "ma_ts='NHOM_KM'",
    // });
    this.getNhomKM({"p_param": "NHOM_KM", "p_type":2});
 
    this.getNguonKM();
    // this.getDanhSachNhomDatCoc({
    //   list_of_cols: "nhom_datcoc_id, ten_nhom",
    //   order: "nhom_datcoc_id",
    //   schema: "css",
    //   table_name: "nhom_datcoc",
    //   where: "",
    // });
    //SỬA ATBM
    this.getDanhSachNhomDatCoc({ 
        "param": 1,
        "type": 1
    });
    
    this.getDanhSachNhomTB().then((e) => {
      if (e.data.data && e.data.data[0]) {
        this.fromTraGopThietBi.nhomtb_id = e.data.data[0].NHOM_TBI_ID;
        this.fromKuyenMaiThietBi.nhomtb_id = e.data.data[0].NHOM_TBI_ID;
      }
    });
    this.loadDanhSachLoaiHinhTB();
    if (this.khuyenmai_id) {
      console.log("danhSachLoaiHinhTBDaGan");
      this.resetForm();
      await this.loadDanhSachChiTiet();
    }
  
    this.$root.showLoading(false);
  },
  async mounted() {},
  watch: {
    async km_theo_tien_tyle(newValue, oldValue) {
      if (newValue == 0) {
        this.form.tien_sd = 0;
        this.form.tien_tb = 0;
      } else {
        this.form.cuoc_sd = 0;
        this.form.cuoc_tb = 0;
        this.form.tyle_sd = 0;
        this.form.tyle_tb = 0;
        this.form.tyle_cuoctb = false;
      }
    },
    "formTabDeposit.nhom_datcoc_id"(newValue, oldValue) {
      this.cboNhomDC_SelectedValueChanged();
    },
    "formTabDeposit.radio_ip"(newValue, oldValue) {
      if (newValue == 1) {
        this.formTabDeposit.mien_ip = 0;
        this.formTabDeposit.mien_block = 0;
      } else {
        this.formTabDeposit.tyle_cuoc_ip = 0;
      }
    },
    "form.tyle_cuoctb"(newValue, oldValue) {
      if (newValue) {
        this.form.cuoctb_decimal = 0;//3
        this.form.cuoctb_maximun = 100;
      } else {
        this.form.cuoctb_decimal = 0;
        this.form.cuoctb_maximun = 99999999999;
      }
    },
    "fromKuyenMaiThietBi.tyle"(newValue, oldValue) {
      if (newValue) {
        this.fromKuyenMaiThietBi.tyleKMTB = 0;
        this.fromKuyenMaiThietBi.tien_km = 0;
        this.fromKuyenMaiThietBi.vat_km = 0;
      } else {
        this.fromKuyenMaiThietBi.tyleKMTB = 0;
      }
    },
    async promotionsCurrentRecordDetail(newVal, oldValue) {
      this.$root.showLoading(true);
      if (newVal && newVal["chitietkm_id"]) {
        this.setButtom(3);
        this.chitietkm_id = newVal.chitietkm_id;
        this.form.ma_km = newVal.ma_km;
        this.form.ten_km = newVal.ten_ctkm;
        this.form.tien_kmld = newVal.tien_kmld;
        this.form.sua_km_cuoc = Boolean(newVal.kieu_km);
        this.form.sua_kmld = Boolean(newVal.kieu_kmld);
        this.form.nguon_ld = newVal.nguonkmld;
        this.form.tyleKMLD = newVal.tyle_kmld;
        this.form.noi_dung = newVal.noidung;
        this.form.vat_km = newVal.vat_kmld;
        this.form.nguonkm_id = (newVal.nguonkm_id && newVal.nguonkm_id > 0) ? newVal.nguonkm_id : 0;
        this.form.phan_bo = (newVal.phan_bo == 0);
        this.form.tyle_vat = Boolean(newVal.tyle_kmld);
        this.form.kieu_batdau = newVal.loai_bdkm; //?
        this.form.nhom_km = (newVal.nhom_km ? newVal.nhom_km*1 : 0); 
        this.form.thang_bdkm = newVal.thang_bdkm;
        this.form.huong_km = newVal.huong_km;
        this.form.cho_phep_gdv = (newVal.kieu_tratruoc == 1)
        this.form.ghi_chu = newVal.ghichu;
        // this.form.nguon_km = newVal.nguonkmld == null ? 0 : newVal.nguonkmld;
        this.form.tu_ncc = Boolean(newVal.chuyenmang);
        this.form.hien_thi = Boolean(newVal.hienthi_md);
        if (newVal.tien_tb == 0 && newVal.tien_sd == 0) {
          this.km_theo_tien_tyle = 0;
          if (
            (newVal.tyle_sd != 0 && newVal.tyle_sd != "") ||
            (newVal.tyle_tb != 0 && newVal.tyle_tb != "")
          ) {
            this.form.tyle_cuoctb = true;
          } else {
            this.form.tyle_cuoctb = false;
          }
        } else {
          this.km_theo_tien_tyle = 1;
        }
        this.form.cuoc_sd = newVal.cuoc_sd;
        this.form.cuoc_tb = newVal.cuoc_tb;
        this.form.tyle_sd = newVal.tyle_sd;
        this.form.tyle_tb = newVal.tyle_tb;
        this.form.tien_sd = newVal.tien_sd;
        this.form.tien_tb = newVal.tien_tb;
        //form DV
        // this.formTabServiceGT.kieu_km_dvgt =
        //   this.promotionsCurrentRecordDetail.kieu_km_dvgt;
        await this.loadDanhSachLoaiHinhTB(newVal.chitietkm_id);
        await this.loadDanhSachGoiThamGia(newVal.chitietkm_id);
        await this.loadDanhSachDichVuGT();
        console.log("fromKuyenMaiThietBi.nhomtb_id", this.fromKuyenMaiThietBi.nhomtb_id)
        if (this.fromKuyenMaiThietBi.nhomtb_id) {
          await this.loadDanhSachKhuyenMaiTB();
        }
        console.log("fromTraGopThietBi.nhomtb_id", this.fromTraGopThietBi.nhomtb_id)
        if (this.fromTraGopThietBi.nhomtb_id) {
          await this.loadDanhSachTraGopTB();
        }
        // form deposit
        this.formTabDeposit.nhom_datcoc_id = newVal.nhom_datcoc_id;
        this.formTabDeposit.thang_bddc = newVal.thang_bddc;
        this.formTabDeposit.thang_bdck = newVal.thang_bdck;
        this.formTabDeposit.so_thang = newVal.huong_dc;
        this.formTabDeposit.dat_coc = newVal.datcoc_csd;
        this.formTabDeposit.radio_ip = newVal.tyle_cuoc_ip > 0 ? 1 : 0;
        this.formTabDeposit.mien_ip = newVal.mien_ip;
        this.formTabDeposit.mien_block = newVal.mien_block_ip;
        this.formTabDeposit.nguonkm_id = newVal.nguonkm_ip_id ? newVal.nguonkm_ip_id : 0;
        this.formTabDeposit.tyle_cuoc_ip = newVal.tyle_cuoc_ip;
        this.formTabDeposit.tien_ck = newVal.tien_ck;
        // this.formTabDeposit.thang_bdck = newVal.thang_bdck;
        this.formTabDeposit.thang_ktck = newVal.thang_ktck;
        // this.formTabDeposit.thang_ktck = newVal.thang_bdck;
        this.formTabDeposit.tien_trudan = newVal.tien_td;
        this.formTabDeposit.kieu_cam_ket = newVal.kieu_ck == 0 ? 0 : 1;
        this.formTabDeposit.cho_phep_gdv = (newVal.kieu_tratruoc == 1);
        this.formTabDeposit.kieu_batdau = newVal.loai_bddc; //?
        await this.$root.showLoading(false);
      } else {
        this.setButtom(1);
        await this.$root.showLoading(false);
      }
    },
    // ...["formTabDeposit.tien_trudan", "formTabDeposit.so_thang"].reduce(
    //   (watchers, key) => ({
    //     ...watchers,
    //     [key](newVal, oldVal) {
    //       this.formTabDeposit.dat_coc =
    //         this.formTabDeposit.so_thang > 0
    //           ? this.formTabDeposit.tien_trudan * this.formTabDeposit.so_thang
    //           : this.formTabDeposit.tien_trudan;
    //     },
    //   }),
    //   {}
    // ),
    ...["fromKuyenMaiThietBi.nhomtb_id"].reduce(
      (watchers, key) => ({
        ...watchers,
        [key](newVal, oldVal) {
          if (this.fromKuyenMaiThietBi.nhomtb_id) {
            this.loadDanhSachKhuyenMaiTB();
          }
        },
      }),
      {}
    ),
    "fromTraGopThietBi.nhomtb_id"(newVal, oldValue) {
      if (this.fromTraGopThietBi.nhomtb_id) {
        this.loadDanhSachTraGopTB();
      }
    },
  },
  methods: {
    ...mapActions("promotionDetail", [
      "getNhomKM",
      "getNguonKM",
      "getDanhSachChiTiet",
      "getDanhSachNhomDatCoc",
      "getDanhSachNhomTB",
      "getDanhSachLoaiHinhTB",
      "getDanhSachToaNha",
      "getDanhSachLoainha",
      "getDanhSachTrangbi",
      "getDanhSachKhuyenmai",
      "getDanhSachGoiThamGiaChiTiet",
      "getDanhSachTraGopThietBi",
      "getDanhSachKMTB",
      "insertOrUpdateDetail",
      "getDanhSachDichVuGT",
      "copyChiTietKhuyenMai",
      "deleteChiTietKhuyenMai",
    ]),
     gridCommand_Thaotac: async function (type, dataItem) {
      console.log(dataItem)
            this.$refs.popupThemChiTietKm.vctkm_id = dataItem.chitietkm_id
            this.$refs.popupThemChiTietKm.vkhuyenmai_id = dataItem.khuyenmai_id
            this.$refs.popupThemChiTietKm.showDialog()
       },
    async loadDanhSachChiTiet(ctkm_id) {
      this.tableLoading = true;
      await this.getDanhSachChiTiet({"p_param": this.khuyenmai_id, "p_type":1}).finally(() => {  
        console.log("sp_tt_ct_khuyenmai", this.danhSachChiTiet ); 
        this.tableLoading = false;
        if (this.danhSachChiTiet && this.danhSachChiTiet.length == 0) {
          this.setButtom(1);
        }
        let gridTable = this.$refs.vueTableDetail;
        let rowIndex = 0;
        let pageSize = gridTable.pageSizeData;
        if(this.state.themmoi_ctiet && this.state.themmoi_ctiet == true && this.danhSachChiTiet.length > 0){
          rowIndex = this.danhSachChiTiet.length - 1;
           
          let pageIndex = Math.ceil(rowIndex/pageSize)
          
          console.log("pageIndex", pageIndex);
          if(pageIndex > 0){
            pageIndex = pageIndex - 1;
            this.$refs.vueTableDetail.$refs.pagination.gotoPage(pageIndex); 
          }   
        }
        let that = this;
        if(ctkm_id && this.danhSachChiTiet){
          let item = this.danhSachChiTiet.find((x) => x.chitietkm_id == ctkm_id)
          if(item)
          {
            rowIndex = this.danhSachChiTiet.indexOf(item);
            if(rowIndex < 0) rowIndex = 0;
          }
        } 
        setTimeout(function(){
          gridTable.selectRow(rowIndex%pageSize, false);
          that.state.themmoi_ctiet = false;
        }, 1000); 
      });
    },
    onChangVat(a, b, form) {
      if (this.isChange == true) this[form][a] = this[form][b] / (0.1 * 100);
    },
    loadDanhSachDichVuGT() {
      this.getDanhSachDichVuGT({
        khuyenmai_id: this.khuyenmai_id,
        chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        kieu: 1, //0: chưa gán, 1: đã gán
      });
      this.getDanhSachDichVuGT({
        khuyenmai_id: this.khuyenmai_id,
        chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        kieu: 0, //0: chưa gán, 1: đã gán
      });
    },
    loadDanhSachKhuyenMaiTB() { 
      this.getDanhSachKMTB({
        khuyenmai_id: this.khuyenmai_id,
        chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        nhom_tbi_id: this.fromKuyenMaiThietBi.nhomtb_id,
        kieu: 1, //0: chưa gán, 1: đã gán
      });
      this.getDanhSachKMTB({
        khuyenmai_id: this.khuyenmai_id,
        chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        nhom_tbi_id: this.fromKuyenMaiThietBi.nhomtb_id,
        kieu: 0, //0: chưa gán, 1: đã gán
      });
    },
    loadDanhSachLoaiHinhTB() {
      this.getDanhSachLoaiHinhTB({
        vchitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 1,
        vdichvuvt_id: this.dichvuvt_id,
        vkhuyenmai_id: this.khuyenmai_id,
        vloai: 0, //0: chưa gán, 1: đã gán
        vloaitb_id: "0",
      });
      this.getDanhSachLoaiHinhTB({
        vchitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 1,
        vdichvuvt_id: this.dichvuvt_id,
        vkhuyenmai_id: this.khuyenmai_id,
        vloai: 1, //0: chưa gán, 1: đã gán
        vloaitb_id: "0",
      });
    },
    //Start thitv.hue add 25/07/2023
    loadDanhSachToaNha() {
    this.getDanhSachToaNha({
        p_chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        p_kieu: 0, //0: chưa gán, 1: đã gán
      });
    this.getDanhSachToaNha({
        p_chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        p_kieu: 1, //0: chưa gán, 1: đã gán
      });
    },
    loadDanhSachTrangbi() {
    this.getDanhSachTrangbi({
       p_khuyenmai_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.khuyenmai_id
          : 0,
        p_chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        p_kieu: 0, //0: chưa gán, 1: đã gán
      });
    this.getDanhSachTrangbi({
       p_khuyenmai_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.khuyenmai_id
          : 0,
        p_chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        p_kieu: 1, //0: chưa gán, 1: đã gán
      });
    },
    loadDanhSachKhuyenmai() {
    this.getDanhSachKhuyenmai({
       p_khuyenmai_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.khuyenmai_id
          : 0,
        p_chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        p_kieu: 0, //0: chưa gán, 1: đã gán
      });
    this.getDanhSachKhuyenmai({
       p_khuyenmai_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.khuyenmai_id
          : 0,
        p_chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        p_kieu: 1, //0: chưa gán, 1: đã gán
      });
    },
    loadDanhSachLoainha() {
    this.getDanhSachLoainha({
        p_chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        p_kieu: 0, //0: chưa gán, 1: đã gán
      });
    this.getDanhSachLoainha({
        p_chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        p_kieu: 1, //0: chưa gán, 1: đã gán
      });
    },
     //End thitv.hue add 25/07/2023
    loadDanhSachTraGopTB() {
      this.getDanhSachTraGopThietBi({
        chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        khuyenmai_id: this.khuyenmai_id,
        nhom_tbi_id: this.fromTraGopThietBi.nhomtb_id,
        kieu: 1, //0: chưa gán, 1: đã gán
      });
      this.getDanhSachTraGopThietBi({
        chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        khuyenmai_id: this.khuyenmai_id,
        nhom_tbi_id: this.fromTraGopThietBi.nhomtb_id,
        kieu: 0, //0: chưa gán, 1: đã gán
      });
    },
    loadDanhSachGoiThamGia(value) {
      this.getDanhSachGoiThamGiaChiTiet({
        chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        khuyenmai_id: this.khuyenmai_id,
        kieu: 0, //0: chưa gán, 1: đã gán
      });
      this.getDanhSachGoiThamGiaChiTiet({
        chitietkm_id: this.promotionsCurrentRecordDetail
          ? this.promotionsCurrentRecordDetail.chitietkm_id
          : 0,
        khuyenmai_id: this.khuyenmai_id,  
        kieu: 1, //0: chưa gán, 1: đã gán
      });
    },
     rowSelected(row) { 
      console.log('%cThis is a colored message!', 'color: red; font-weight: bold;', row);
      this.promotionsCurrentRecordDetail = row;
      // this.formTabServiceGT  = row;   (DTĐ fix bug 28/09/2024: undefined tyle_giamcuoc_ld)
      this.danhSachTrangbiDaGan = [];
      this.loadDanhSachToaNha();
      this.loadDanhSachLoainha();
      this.loadDanhSachTrangbi();
      this.loadDanhSachKhuyenmai();
    },
    async btnGhiLaiOnClick(e) {
      e.preventDefault();
      
      try{ 
        this.loading(true)   
        if (this.buttonStatus.btnAddNew == false) {
          if (!this.KiemtraDL(true)) return this.$root.showLoading(false);
          await this.TaoDuLieu(true);
           
          const data = {
            chitietkm_id: this.chitietkm_id,
            is_insert: this.buttonStatus.btnAddNew ? 0 : 1,
            json_ctkm: [this.rowdata],
            json_ctkmlhtb: this.ds_ctkmlhtb,
            // json_ctkmlhtb: [],
            json_ds_nguonkmld: this.ds_nguonkmld,
            json_dsdvgt: this.dsdvgt,
            json_dskmtbi: this.dskmtbi,
            json_dstgtbi: this.dstgtbi,
            json_goi_dadv: this.dsgoidadv,
            p_json_loainha : this.dsloainha,
            khuyenmai_id: this.khuyenmai_id,
            ma_km: this.form.ma_km,
            p_json_toanha: this.dstoanha,
            p_json_trangbi: this.dstrangbi,
            p_json_khuyenmai: this.dskhuyenmai
          };
          await this.insertOrUpdateDetail(data)
            .then((rs) => {
              if (rs.data.message == "Success") {
                this.$toast.success("Thêm mới chi tiết khuyến mại thành công");
                this.state.themmoi_ctiet = true;
                this.loadDanhSachChiTiet(this.chitietkm_id).then(() => { 
                  // let ctKM = this.danhSachChiTiet.filter((x) => x.chitietkm_id = this.chitietkm_id);
                  // if(ctKM && ctKM.length > 0){
                  //   this.promotionsCurrentRecordDetail = Object.assign({}, ctKM[0]);
                  // } 
                });
              }
            })
            .catch((ex) => {
              commonFn.showException(this, ex);
            })
            .finally(() => {
              this.$root.showLoading(false);
            });
        } else {
          if (!this.KiemtraDL(false)) return this.$root.showLoading(false);
          await this.TaoDuLieu(false); 
          const data = {
            chitietkm_id: this.chitietkm_id,
            is_insert: this.buttonStatus.btnAddNew ? 0 : 1,
            json_ctkm: [this.rowdata],
            json_ctkmlhtb: this.ds_ctkmlhtb,
            json_ds_nguonkmld: this.ds_nguonkmld,
            json_dsdvgt: this.dsdvgt,
            json_dskmtbi: this.dskmtbi,
            json_dstgtbi: this.dstgtbi,
            json_goi_dadv: this.dsgoidadv,
            khuyenmai_id: this.khuyenmai_id,
            ma_km: this.form.ma_km,
            p_json_toanha: this.dstoanha,
            p_json_loainha : this.dsloainha,
            p_json_trangbi: this.dstrangbi,
            p_json_khuyenmai: this.dskhuyenmai
          };
          await this.insertOrUpdateDetail(data).then((rs) => {
            if (rs.data.message == "Success") {
              this.$toast.success("Cập nhập chi tiết khuyến mại thành công");
              this.loadDanhSachChiTiet(this.chitietkm_id)
                .then(() => {
                    // let ctKM = this.danhSachChiTiet.filter((x) => x.chitietkm_id = this.chitietkm_id);
                    // if(ctKM && ctKM.length > 0){
                    //   this.promotionsCurrentRecordDetail = Object.assign({}, ctKM[0]);
                    // } 
                })
                .catch((e) => {
                    commonFn.showException(this, e);
                })
                .finally(() => {
                  this.$root.showLoading(false);
                });
            }
          });
        }
      }catch(ex){
         commonFn.showException(this, ex);
      }
      this.loading(false)
    },
    btnNhapMoiOnClick(e) {
      e.preventDefault();
      this.resetForm();
      this.setButtom(1);
    },
    btnHuyOnClick(e) {
      e.preventDefault();
      this.setButtom(0);
      this.loadDanhSachChiTiet().then(() => {
        this.promotionsCurrentRecordDetail =
          this.danhSachChiTiet.length > 0 ? this.danhSachChiTiet[0] : null;
      });
    },
    async btnCopyOnClick(e) {
      const isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn có thực sự muốn copy chi tiết khuyến mại không?`,
        {
          title: "",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
          centered: true,
        }
      );
      if (isConfirm) {
        this.$root.showLoading(true);
        await this.copyChiTietKhuyenMai({
          vchitietkm_id_cu: this.chitietkm_id,
        })
          .then((rs) => {
            if (rs.data.message == "Success") {
              this.$toast.success("Copy chi tiết khuyến mại thành công");
              this.loadDanhSachChiTiet().then(() => {
                this.promotionsCurrentRecordDetail =
                  this.danhSachChiTiet.length > 0
                    ? this.danhSachChiTiet[0]
                    : null;
              });
            }
          })
          .catch((e) => {
                 commonFn.showException(this, e);
          });
        this.$root.showLoading(false);
      }
    },
    KiemtraMaCTKM(txtMaKM) {
      if(!this.danhSachChiTiet) return undefined;
      return this.danhSachChiTiet.find(function (value) {
        return value.ma_km == txtMaKM;
      });
    },
    KiemtraDL(themmoi) {
      // bỏ check điều kiện theo bug: https://cntt.vnpt.vn/browse/BSS-92140
      //Đã xác nhận là bắt not null
      if (!this.form.ma_km) {
        this.$toast.error("Hãy nhập mã chi tiết khuyến mại!");
        this.$refs.maKm.focusIn();
        return false;
      }
      // if (themmoi == true) {
      //   if (this.form.ma_km && this.KiemtraMaCTKM(this.form.ma_km) !== undefined) {
      //     this.$toast.error(
      //       "Mã chi tiết khuyến mại đã tồn tại trong hệ thống!"
      //     );
      //     this.$refs.maKm.focusIn();
      //     return false;
      //   }
      // }
      
      if (!this.form.ten_km) {
        this.$toast.error("Hãy nhập tên chi tiết khuyến mại!");
        this.$refs.tenKm.focusIn();
        return false;
      }
      if (!this.form.noi_dung) {
        this.$toast.error("Hãy nhập nội dung chi tiết khuyến mại!");
        this.$refs.noiDung.focusIn();
        return false;
      }
      if (!this.form.nguonkm_id || this.form.nguonkm_id <= 0) {
        // this.$toast.error("Hãy chọn Nguồn khuyến mại!");
        // this.$refs.nguonKM.focusIn();
        // return false;
        this.form.nguonkm_id = 0;
      }
      if (!this.form.ghi_chu) {
        // this.$toast.error("Hãy nhập ghi chú chi tiết khuyến mại!");
        // this.$refs.ghiChu.focusIn();
        // return false;
        this.form.ghi_chu = ""
      }
      if (
        this.formTabDeposit.nhom_datcoc_id > 0 &&
        !this.formTabDeposit.tien_trudan
      ) {
        // this.$toast.error("Hãy nhập tiền trừ dần hàng tháng!");
        // this.$refs.Deposit.$refs.TienTruDan.focusIn();
        // return false;
        this.formTabDeposit.tien_trudan = 0;
      }
      if(!this.form.cuoc_sd){
        this.form.cuoc_sd = 0;
      }
      // if (
      //   !this.form.cuoc_sd &&
      //   this.form.tyle_cuoctb == false &&
      //   this.km_theo_tien_tyle == 0
      // ) {
      //   this.$toast.error(
      //     "Hãy nhập tiền khuyến mại cước sử dụng cho khách hàng!"
      //   );
      //   this.$refs.CuocSD.focusIn();
      //   return false;
      // }
      if (
        !this.formTabDeposit.tien_ck &&
        this.formTabDeposit.nguonkm_id !== 0
      ) {
        this.formTabDeposit.tien_ck = 0;
        // this.$toast.error("Hãy nhập tiền cam kết!");
        // this.$refs.Deposit.$refs.TienCK.focusIn();
        // return false;
      }
      if (!this.form.huong_km) {
        this.form.huong_km = 0;
        // this.$toast.error("Hãy nhập số tháng hưởng khuyến mại!");
        // this.$refs.HuongKM.focusIn();
        // return false;
      }
      if (this.form.tyle_cuoctb) { 
        if (this.form.tyle_sd == null) {
          this.$toast.error("Bạn phải nhập Tỷ lệ cước sử dụng !");
          this.$refs.CuocTyleSD.focusIn();
          return false;
        }
        if(this.form.tyle_tb == null){
          this.$toast.error("Bạn phải nhập Tỷ lệ cước TB !");
          this.$refs.CuocTyleTB.focusIn();
          return false;
        }
        if ((this.form.tyle_tb < 0 || this.form.tyle_tb > 100)) {
          this.$toast.error("Tỷ lệ cước sử dụng phải >= 0 và < 100 !");
          this.$refs.CuocTyleTB.focusIn();
          return false;
        }
      }else{
        if(this.form.tyle_sd == null) this.form.tyle_sd = 0;
        if(this.form.tyle_tb == null) this.form.tyle_tb = 0;      
        if(this.form.cuoc_tb == null) this.form.cuoc_tb = 0;   
      }
      if (this.formTabDeposit.nhom_datcoc_id > 0) {
        if (!this.formTabDeposit.dat_coc && this.formTabDeposit.cho_phep_gdv != 1) {
          this.$toast.error("Hãy nhập tiền đặt cọc cước sử dụng!");
          this.$refs.Deposit.$refs.DatCoc.focusIn();
          return false;
        }
        if (this.formTabDeposit.dat_coc < 0) {
          this.$toast.error("Hãy nhập tiền đặt cọc cước sử dụng!");
          this.$refs.Deposit.$refs.DatCoc.focusIn();
          return false;
        }
      }
      if (this.formTabDeposit.nguonkm_id > 0) {
        if (this.formTabDeposit.radio_ip == 0) {
          if (!this.formTabDeposit.mien_ip) {
            this.$toast.error("Hãy nhập số lượng IP tĩnh được miễn phí");
            this.$refs.Deposit.$refs.MienIP.focusIn();
            return false;
          }
          // if (!this.formTabDeposit.mien_block) {
          //   this.$toast.error("Hãy nhập số lượng Block IP tĩnh được miễn phí");
          //   this.$refs.Deposit.$refs.BlockIP.focusIn();
          //   return false;
          // }
          if (
            this.formTabDeposit.mien_ip <= 0 &&
            this.formTabDeposit.mien_block <= 0
          ) {
            this.$toast.error(
              "Số lượng IP tĩnh hoặc block IP được miễn phí phải lớn hơn 0"
            );
            this.$refs.Deposit.$refs.MienIP.focusIn();
            return false;
          }
        } else {
          if (!this.formTabDeposit.tyle_cuoc_ip) {
            this.$toast.error("Hãy nhập tỷ lệ giảm cước IP");
            this.$refs.Deposit.$refs.TyleCuocIp.focusIn();
            return false;
          }
          if (this.formTabDeposit.tyle_cuoc_ip <= 0) {
            this.$toast.error("Tỷ lệ giảm cước IP phải lớn hơn 0");
            this.$refs.Deposit.$refs.TyleCuocIp.focusIn();
            return false;
          }
        }
      }
      if (this.danhSachLoaiHinhTBDaGan.length == 0) {
        this.$toast.error("Bạn phải gán loại hình thuê bao!");
        this.selectedTab = 3;
        return false;
      }
      if (
        this.formTabServiceGT.kieu_km_dvgt &&
        this.danhSachDichVuGTDaGan.length != 1
      ) {
        this.$toast.error("Bạn chỉ được chọn 1 và chỉ 1 dịch vụ gia tăng");
        return false;
      }
      return true;
    },
    async btnXoaOnClick(e) {
      e.preventDefault();
      const isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn có thực sự muốn xóa chi tiết khuyến mại không?`,
        {
          title: "",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
          centered: true,
        }
      );
      if (isConfirm) {
        this.$root.showLoading(true);
        await this.deleteChiTietKhuyenMai({
          khuyenmai_id: this.khuyenmai_id,
          chitietkm_id: this.chitietkm_id,
        })
          .then((rs) => {
            if (rs.data.message == "Success") {
              this.$toast.success("Xóa chi tiết khuyến mại thành công");
              this.loadDanhSachChiTiet().then(() => {
                this.promotionsCurrentRecordDetail =
                  this.danhSachChiTiet.length > 0
                    ? this.danhSachChiTiet[0]
                    : null;
              });
            }
          })
          .catch((ex) => {
              commonFn.showException(this, ex); 
          });
        this.$root.showLoading(false);
      }
    },
    TaoDuLieu(themmoi) {
        if(themmoi == false && this.chitietkm_id == 0)
          throw "Bạn chưa chọn chi tiết khuyến mại" 
        this.ds_nguonkmld = [];
        this.ds_ctkmlhtb = [];
        this.dsgoidadv = [];
        this.dstgtbi = [];
        this.dstrangbi = [];
        this.dskhuyenmai = [];
        this.dstoanha = [];
        this.dsloainha = [];
        this.dskmtbi = [];
        this.dsdvgt = [];
        this.rowdata = {};
        this.rowdata.SO_MB = 0;
        this.rowdata.KHOANMUCTT_ID = 0;
        this.rowdata.THANG_KTKM = 0;
        this.rowdata.THANG_KT_EMAIL = 0;
        this.rowdata.CHUKY_ID_NEO = 0;
        this.rowdata.THANG_BD_EMAIL = 0;
        this.rowdata.THANG_KTDC = 0;
        this.rowdata.TYLE_VAT_ID = 0;
        this.rowdata.CUOC_FIX_MB = 0;
        this.rowdata.TYLE_GIAM_EMAIL = 0;
        this.rowdata.CHITIETKM_ID = themmoi == false ? 0 : this.chitietkm_id;
        this.rowdata.KHUYENMAI_ID = this.khuyenmai_id;
        this.rowdata.MA_KM = this.form.ma_km;
        this.rowdata.TEN_CTKM = this.form.ten_km;
        this.rowdata.NOIDUNG = this.form.noi_dung;
        this.rowdata.KIEU_KM = this.form.sua_km_cuoc ? 1 : 0;
        this.rowdata.KIEU_KMLD = this.form.sua_kmld ? 1 : 0;
        this.rowdata.CHUYENMANG = this.form.tu_ncc ? 1 : 0;
        this.rowdata.KIEU_TRATRUOC = this.formTabDeposit.cho_phep_gdv ? 1 : 0;
        this.rowdata.KIEU_KM_DVGT = this.formTabServiceGT.kieu_km_dvgt ? 1 : 0;
        this.rowdata.TYLE_CUOC_IP = 0;
        if (!this.form.tyle_vat) {
          this.rowdata.TYLE_VAT = Number(this.form.tyle_vat);
          this.rowdata.TIEN_KMLD = this.form.tien_kmld;
          this.rowdata.VAT_KMLD = this.form.vat_km;
          this.rowdata.TYLE_KMLD = 0;
        } else {
          this.rowdata.TYLE_VAT = Number(this.form.tyle_vat);
          this.rowdata.TIEN_KMLD = 0;
          this.rowdata.VAT_KMLD = 0;
          this.rowdata.TYLE_KMLD = this.form.tyleKMLD;
        }
        
        if (this.km_theo_tien_tyle == 0) {
          if (this.form.tyle_cuoctb == true) {
            this.rowdata.CUOC_SD = 0;
            this.rowdata.CUOC_TB = 0;
            this.rowdata.TYLE_SD = this.form.tyle_sd;
            this.rowdata.TYLE_TB = this.form.tyle_tb;
          } else {
            this.rowdata.CUOC_SD = this.form.cuoc_sd;
            this.rowdata.CUOC_TB = this.form.cuoc_tb ? this.form.cuoc_tb : 0;
            this.rowdata.TYLE_SD = 0;
            this.rowdata.TYLE_TB = 0;
          }
          this.rowdata.TIEN_TB = 0;
          this.rowdata.TIEN_SD = 0;
        } else {
          this.rowdata.CUOC_SD = 0;
          this.rowdata.CUOC_TB = 0;
          this.rowdata.TYLE_SD = 0;
          this.rowdata.TYLE_TB = 0;
          this.rowdata.TIEN_TB = this.form.tien_tb;
          this.rowdata.TIEN_SD = this.form.tien_sd;
        }
        this.rowdata.HUONG_KM = this.form.huong_km;
        if (this.form.hien_thi) this.rowdata.HIENTHI_MD = 1;
        else this.rowdata.HIENTHI_MD = 0;
        if (this.form.nhom_km == 0 || this.form.nhom_km == null)
          this.rowdata.NHOM_KM = null;
        else this.rowdata.NHOM_KM = this.form.nhom_km;
        this.rowdata.TIEN_CK = this.formTabDeposit.tien_ck;
        if(!this.rowdata.TIEN_CK)
          this.rowdata.TIEN_CK = 0;
        if (this.formTabDeposit.kieu_cam_ket == 0) this.rowdata.KIEU_CK = 0;
        else this.rowdata.KIEU_CK = 1;
        this.rowdata.THANG_BDCK = this.formTabDeposit.thang_bdck;
        this.rowdata.THANG_KTCK = this.formTabDeposit.thang_ktck;
        //Thông tin đặt cọc
        if (this.formTabDeposit.nhom_datcoc_id > 0) {
          this.rowdata.THANG_BDDC = this.formTabDeposit.thang_bddc;
          this.rowdata.NHOM_DATCOC_ID = this.formTabDeposit.nhom_datcoc_id;
          this.rowdata.DATCOC_CSD = this.formTabDeposit.dat_coc;
          this.rowdata.TIEN_TD = this.formTabDeposit.tien_trudan;
          this.rowdata.HUONG_DC = this.formTabDeposit.so_thang;
          if (this.formTabDeposit.kieu_batdau)
            this.rowdata.LOAI_BDDC = this.formTabDeposit.kieu_batdau;
        } else {
          this.rowdata.THANG_BDDC = 0;
          this.rowdata.NHOM_DATCOC_ID = 0;
          this.rowdata.DATCOC_CSD = 0;
          this.rowdata.TIEN_TD = 0;
          this.rowdata.HUONG_DC = 0;
          this.rowdata.LOAI_BDDC = 1;
        }
        this.rowdata.THANG_BDKM = this.form.thang_bdkm;
        this.rowdata.LOAI_BDKM = this.form.kieu_batdau;
        //end longdx
        if (this.formTabDeposit.nguonkm_id <= 0) {
          this.rowdata.MIEN_IP = 0;
          this.rowdata.NGUONKM_IP_ID = 0;
          this.rowdata.TYLE_CUOC_IP = 0;
          this.rowdata.MIEN_BLOCK_IP = 0;
        } else {
          if (this.formTabDeposit.radio_ip == 0) {
            if (this.formTabDeposit.mien_ip != "")
              this.rowdata.MIEN_IP = this.formTabDeposit.mien_ip;
            else this.rowdata.MIEN_IP = 0;
            if (
              this.formTabDeposit.mien_block != "" &&
              this.formTabDeposit.mien_block != null
            ) {
              this.rowdata.MIEN_BLOCK_IP = this.formTabDeposit.mien_block;
              this.rowdata.TYLE_CUOC_IP = 0;
            } else {
              this.rowdata.MIEN_BLOCK_IP = 0;
            }
          } else {
            if (this.formTabDeposit.tyle_cuoc_ip != "") {
              this.rowdata.MIEN_IP = 0;
              this.rowdata.MIEN_BLOCK_IP = 0;
              this.rowdata.TYLE_CUOC_IP = this.formTabDeposit.tyle_cuoc_ip;
            } else this.rowdata.TYLE_CUOC_IP = 0;
          }
          this.rowdata.NGUONKM_IP_ID = this.formTabDeposit.nguonkm_id;
        }
        this.rowdata.NGUONKM_LD_ID = 0;
        this.rowdata.NGUONKM_ID = this.form.nguonkm_id;
        this.rowdata.GHICHU = this.form.ghi_chu;
        if (this.form.phan_bo) 
          this.rowdata.PHAN_BO = 0;
        else 
          this.rowdata.PHAN_BO = 1;
        this.danhSachDichVuGTDaGan.map((e) =>
          this.dsdvgt.push({
            DICHVUGT_ID: e.dichvugt_id,
            TYLE_GIAMCUOC_LD: e?.tyle_giamcuoc_ld,
            TIEN_GIAMCUOC_SD: e?.tien_giamcuoc_sd,
            TYLE_GIAMCUOC_SD: e?.tyle_giamcuoc_sd,
            VAT_GIAMCUOC_SD: e?.vat_giamcuoc_sd,
            DENNGAY: e?.ngay_cn,
            NGUOI_CN: "string",
            MAY_CN: "string",
          })
        );
        this.danhSachKMTBDaGan.map((e) =>
          this.dskmtbi.push({
            LOAITBI_ID: e.loaitbi_id,
            TIEN_KM: e.tien_km,
            VAT_KM: e.vat_km,
            TYLE_KM: e.tyle_km,
            NGUONKM_ID: e.nguonkm_id,
            TANG_TRUOC: e.tang_truoc,
            THANG_BD_TT: e.thang_bd_tt,
            HUONG_TT: e.huong_tt,
          })
        );
        //Start thitv.hue add 25/07/2023
        this.danhSachToaNhaDaGan.map((e) =>
          this.dstoanha.push({
            TOANHA_ID: e.toanha_id,
          })
        );
        
         this.danhSachTrangbiDaGan.map((e) =>
          this.dstrangbi.push({
            TRANGBI_ID: e.trangbi_id,
          })
        );
        if(this.danhSachKhuyenmaiDaGan){
           this.danhSachKhuyenmaiDaGan.map((e) =>
              this.dskhuyenmai.push({
                KHUYENMAI_ID: e.khuyenmai_id,
              })
            );
        }
         //Start thitv.hue add 25/07/2023
        this.danhSachLoainhaDaGan.map((e) =>
            this.dsloainha.push({
              LOAINHA_ID: e.loainha_id,
            })
        );
        
         //End thitv.hue add 25/07/2023
        this.danhSachTraGopThietBiChiTietDaGan.map((e) =>
          this.dstgtbi.push({
            LOAITBI_ID: e.loaitbi_id,
            LOAI_BDTG: e.loai_bdtg,
            THANG_BDTG: e.thang_bdtg,
            SOTHANG_TG: e.sothang_tg,
          })
        );
        this.danhSachLoaiHinhTBDaGan.map((e) =>
          this.ds_ctkmlhtb.push({
            LOAITB_ID: e.loaitb_id,
            TOCDO_ID: e.tocdo_id,
            MUCCUOC_ID: e.muccuoc_id,
          })
        );
        this.danhSachGoiThamGiaChiTietDaGan.map((e) =>
          this.dsgoidadv.push({
            GOI_ID: e.goi_id,
          })
        );
        var muc = 0;
        this.danhSachNguonKMDaGan.map((e) => {
          this.ds_nguonkmld.push({
            CHITIETKM_ID: this.chitietkm_id,
            MUC: (muc += 1),
            NGUONKM_LD_ID: e.nguonkm_id,
            TIEN_KMLD: e.tien_kmld || 0,
            TYLE_KMLD: e.tyle_kmld || 0,
            VAT_KMLD: e.vat_kmld || 0,
          });
        });
    },
    onFilteringDropDownList(e, dataSource, name) {
      console.log(e);
      var query = new Query();
      query = e.text !== "" ? query.where(name, "contains", e.text, true) : query; 
      e.updateData(dataSource, query); 
    }, 
    resetForm() {
      this.promotionsCurrentRecordDetail = null;
      this.loadDanhSachDichVuGT();
      this.loadDanhSachLoaiHinhTB();
      if (this.fromKuyenMaiThietBi.nhomtb_id) {
        this.loadDanhSachKhuyenMaiTB();
      }
      if (this.fromTraGopThietBi.nhomtb_id) {
        this.loadDanhSachTraGopTB();
      }
      this.loadDanhSachGoiThamGia();
      this.chitietkm_id = 0;
      this.form.ma_km = "";
      this.form.ten_km = "";
      this.form.tien_kmld = 0;
      this.form.noi_dung = "";
      this.form.vat_km = 0;
      this.form.sua_km_cuoc = 0;
      this.form.sua_kmld = 0;
      this.form.nguonkm_id = this.danhSachNguonKM.length
        ? this.danhSachNguonKM[0].nguonkm_id
        : null;
      this.form.phan_bo = false;
      this.form.kieu_batdau = this.danhSachKieubatdau.length
        ? this.danhSachKieubatdau[0].id
        : null;
      this.form.nhom_km = this.danhSachNhomKM.length
        ? this.danhSachNhomKM[0].ma_ts
        : null;
      this.form.thang_bdkm = 0;
      this.form.huong_km = 0;
      this.form.cuoc_sd = 0;
      this.form.cuoc_tb = 0;
      this.form.tien_sd = 0;
      this.form.tien_tb = 0;
      this.form.ghi_chu = "";
      this.form.tu_ncc = false;
      this.form.hien_thi = false;
      this.formTabDeposit.kieu_batdau = this.danhSachKieubatdau.length
        ? this.danhSachKieubatdau[0].id
        : null;
      this.formTabDeposit.nhom_datcoc_id = this.danhSachNhomDatCoc.length
        ? this.danhSachNhomDatCoc[0].nhom_datcoc_id
        : null;
      this.formTabDeposit.nguonkm_id = this.danhSachNguonKM.length
        ? this.danhSachNguonKM[0].nguonkm_id
        : null;
      this.formTabDeposit.so_thang = 0;
      this.formTabDeposit.dat_coc = 0;
      this.formTabDeposit.tien_trudan = 0;
      this.formTabDeposit.thang_bddc = 0;
      this.formTabDeposit.cho_phep_gdv = true;
      this.formTabDeposit.radio_ip = 0;
      this.formTabDeposit.mien_ip = 0;
      this.formTabDeposit.mien_block = 0;
      this.formTabDeposit.tien_ck = 0;
      this.formTabDeposit.tyle_cuoc_ip = 0;
      this.formTabDeposit.kieu_cam_ket = 0;
      this.formTabDeposit.thang_bdck = 0;
      this.formTabDeposit.thang_ktck = 0;
      this.formTabServiceGT.tyle_giamcuoc_sd = 0;
      this.formTabServiceGT.vat_giamcuoc_sd = 0;
      this.formTabServiceGT.ngay_cn = moment(new Date()).format("DD/MM/YYYY");
      this.formTabServiceGT.tien_giamcuoc_sd = 0;
      this.formTabServiceGT.tyle_giamcuoc_ld = 0;
      this.formTabServiceGT.kieu_km_dvgt = false;
      this.formTabServiceGT.isNgay = false;
      //    fromTraGopThietBi: {
      //   nhomtb_id: null,
      //   kieu_batdau: 1,
      //   sothang_tg: 0,
      //   thang_bdtg: 0,
      // },
      this.fromTraGopThietBi.nhomtb_id = this.danhSachNhomTB.length
        ? this.danhSachNhomTB[0].NHOM_TBI_ID
        : null;
      this.fromTraGopThietBi.vat_giamcuoc_sd = 0;
      this.fromTraGopThietBi.kieu_batdau = 1;
      this.fromTraGopThietBi.sothang_tg = 0;
      this.fromTraGopThietBi.thang_bdtg = 0;
      // fromKuyenMaiThietBi: {
      //   nhomtb_id: null,
      //   nguonkm_id: null,
      //   tang_truoc: 0,
      //   tien_km: 0,
      //   vat_km: 0,
      //   sl_thang: 0,
      //   thang_db: 0,
      //   tyle: false,
      //   tyleKMTB: 0,
      // },
      this.fromKuyenMaiThietBi.nhomtb_id = this.danhSachNhomTB.length
        ? this.danhSachNhomTB[0].NHOM_TBI_ID
        : null;
      this.fromKuyenMaiThietBi.nguonkm_id = this.danhSachNguonKM.length
        ? this.danhSachNguonKM[0].nguonkm_id
        : null;
      this.fromKuyenMaiThietBi.tang_truoc = 0;
      this.fromKuyenMaiThietBi.tien_km = 0;
      this.fromKuyenMaiThietBi.vat_km = 0;
      this.fromKuyenMaiThietBi.sl_thang = 0;
      this.fromKuyenMaiThietBi.thang_db = 0;
      this.fromKuyenMaiThietBi.tyle = false;
      this.fromKuyenMaiThietBi.tyleKMTB = 0;
    },
    setButtom(kieu) {
      this.buttonStatus = {
        btnAddNew: false,
        btnSave: false,
        btnCancel: true,
        btnDelete: false,
        btnCopy: false,
        btnDinhMuc: true,
        btnDinhMuc: true
      };
      if (kieu == -1) {
        //Bat dau
        this.buttonStatus = {
          btnSave: true,
          btnCancel: true,
        };
      }
      if (kieu == 0) {
        //Bat dau
        this.buttonStatus = {
          btnAddNew: true,
        };
      }
      if (kieu == 1) {
        //Them moi
        this.buttonStatus = {
          btnSave: true,
          btnAddNew: false,
          btnCancel: true,
          btnCopy: false,
          btnDinhMuc: false,
          btnCTKhoanmuc: false
        };
      }
      if (kieu == 2) {
        //Huy
        this.buttonStatus = {
          btnAddNew: true,
          btnDelete: true,
        };
      }
      if (kieu == 3) {
        //Edit
        this.buttonStatus = {
          btnAddNew: true,
          btnSave: true,
          btnCancel: true,
          btnCopy: true,
          btnDelete: true,
          btnDinhMuc: true,
          btnCTKhoanmuc: true
        };
      }
      if (kieu == 4) {
        //Edit
        this.buttonStatus = {
          btnAddNew: false,
          btnSave: false,
          btnCancel: false,
        };
      }
    },
    async onChapNhapNguon(isTyle) {
      this.form.tyle_vat = isTyle;
      this.isChange = false;
      console.log("onChapNhapNguon danhSachNguonKMDaGan", this.danhSachNguonKMDaGan)
      if (!isTyle) {
        const tienKMLD = this.danhSachNguonKMDaGan.reduce(function (s, a) {
          return s + a.tien_kmld;
        }, 0);
        const vatKMLD = this.danhSachNguonKMDaGan.reduce(function (s, a) {
          return s + a.vat_kmld;
        }, 0);
        var nguonLD = [];
        this.danhSachNguonKMDaGan.map((e) => {
          nguonLD.push(
            `${e.ten_nguon}: ${e.tien_kmld || 0} - ${e.vat_kmld || 0}`
          );
        });
        this.form.nguon_ld = nguonLD.toString();
        this.form.tien_kmld = tienKMLD;
        this.form.vat_km = vatKMLD;
        this.isShowLaborSource = false;
        await this.$nextTick(() => {});
        this.isChange = true;
      } else {
        const tyleKMLD = this.danhSachNguonKMDaGan.reduce(function (s, a) {
          return s + a.tyle_kmld;
        }, 0);
        var nguonLD = [];
        this.danhSachNguonKMDaGan.map((e) => {
          nguonLD.push(`${e.ten_nguon}: ${e.tyle_kmld}%`);
        });
        this.form.nguon_ld = nguonLD.toString();
        this.form.tyleKMLD = tyleKMLD;
        this.isShowLaborSource = false;
      }
    },
    handShowPromotionQuota() {
      if (this.danhSachChiTiet.length <= 0) {
        this.$toast.error("Không có dữ liệu nhập định mức khuyến mại!");
        return;
      }
      this.isShowPromotionQuota = true;
    },
    handShowPromotionCTKhoanmuc() {
      
      this.$refs.popupThemChiTietKm.vctkm_id = this.promotionsCurrentRecordDetail.chitietkm_id
      this.$refs.popupThemChiTietKm.vkhuyenmai_id = this.promotionsCurrentRecordDetail.khuyenmai_id
      this.$refs.popupThemChiTietKm.vdichvuvt_id = this.dichvuvt_id
      this.$refs.popupThemChiTietKm.vloaitb_id = this.rowSelected.loaitb_id
      this.$refs.popupThemChiTietKm.showDialog()
    },
    cboNhomDC_SelectedValueChanged() {
      if (this.formTabDeposit.nhom_datcoc_id <= 0) {
        this.formTabDeposit.cho_phep_gdv = false;
        this.formTabDeposit.so_thang = 0;
        this.formTabDeposit.dat_coc = 0;
        this.formTabDeposit.tien_trudan = 0;
        this.formTabDeposit.thang_bddc = 0;
        this.is_nhom_datcoc = false;
      } else this.is_nhom_datcoc = true;
    },
  },
};
</script>