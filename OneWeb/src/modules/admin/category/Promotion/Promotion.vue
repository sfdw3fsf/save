<template src='./Promotion.html'></template>
<style scoped src='./Promotion.scss'></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import { mapActions } from "vuex";
import gridview from "../../../../components/Shared/gridview.vue";
import InstallationType from "./components/InstallationType/InstallationType.vue";
import Area from "./components/Area/Area.vue";
import Loainha from "./components/Loainha/Loainha.vue";
import objectTB from "./components/Object/Object.vue";
import Equipment from "./components/Equipment/Equipment.vue";
import Package from "./components/Package/Package.vue";
import Service from "./components/Service/Service.vue";
import SubscribersType from "./components/SubscribersType/SubscribersType.vue";
import {
  required,
  email,
  minLength,
  sameAs,
  requiredIf,
} from "vuelidate/lib/validators";
import Vue from "vue";
import InfoPromotion from "./components/InfoPromotion/InfoPromotion.vue";
import UpdatePromotionDetail from "./components/UpdatePromotionDetail/UpdatePromotionDetail.vue";
import select3 from "@/components/form/VueSelect.vue";
import { mixin } from "./components/UpdatePromotionDetail/components/mixins";
import { Query } from "@syncfusion/ej2-data";
import { mapWatchers } from "vue";
import * as moment from "moment";
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions.js"
import api from "@/modules/admin/category/Promotion/PromotionAPI.js"
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
var headerVue = Vue.component("headerTemplate", {
  template: `
<table class="table-box" >
    <tr>
      <th style="max-width: 184px;
    min-width: 184px;
    width: 184px;" scope="col">Số CV</th>
      <th scope="col"  style="max-width: 553px;
    min-width: 553px;
    width: 553px;">Tên CV</th>
      <th scope="col" style="max-width: 95px;
    min-width: 95px;
    width: 95px;" >Năm</th>
      <th scope="col" style="max-width: 95px;
    min-width: 95px;
    width: 95px;">Hiệu lực</th>
    </tr>
</table>
 `,
  data() {
    return {
      data: {},
    };
  },
});
// congvan_id: 429
// hieuluc: 1
// nam: 2017
// socongvan: "541/TTr-ĐH ngày 27/03/2017"
// ten_cv: "Về việc đề xuất chính sách cục bộ đặc thù từng khu vực"
var itemVue = Vue.component("itemTemplate", {
  template: `
 <table class="table-box" >
    <tr  >
      <td data-label="Account" style="max-width: 200px;
    min-width: 200px;
    width: 200px;word-wrap:break-word;
white-space: normal; ">
    {{data.socongvan}}</td>
      <td data-label="Name" style="max-width: 600px;
    min-width: 600px;
    width: 600px;" >{{data.ten_cv}}</td>
      <td data-label="Amount"  style="max-width: 100px;
    min-width: 100px;
    width: 100px;">{{data.nam}}</td>
      <td data-label="Period" style="max-width: 100px;
    min-width: 100px;
    width: 100px;">{{data.hieuluc ==1 ? "Hiệu lực" : "Không hiệu lực"}}</td>
    </tr>
</table>
 `,
  data() {
    return {
      data: {},
    };
  },
});
export default {
  mixins: [mixin],
  components: {
    breadcrumb,
    select3,
    gridview,
    InstallationType,
    InfoPromotion,
    UpdatePromotionDetail,
    Area,
    objectTB,
    Equipment,
    Package,
    Service,
    SubscribersType,
    ExportDataModal,
    Loainha
  },
  name: "Promotion",
  data() {
    return {
      query: new Query()
        .from("congvan_id")
        .select(["congvan_id", "socongvan", "ten_cv", "nam", "hieuluc"]),
      fields: { text: "socongvan", value: "congvan_id" },
      headerTemplate: function (e) {
        return {
          template: headerVue,
        };
      },
      itemTemplate: function (e) {
        return {
          template: itemVue,
        };
      },
      isShowCshtPopup: false,
      state: {
        tabIndex: 0,
      },
      sortOptions: { columns: [{ field: 'chitietkm_id', direction: 'Ascending' }] },
      selected: null,
      promotionsCurrentRecord: null,
      promotionsCurrentRecordDetail: null,
      isViewDetail: false,
      tableDetailLoading: false,
      detailPromotionHeader: [
        { label: "ID", filterAble: true, key: "chitietkm_id", width: "90px" },
        {
          label: "Chi tiết KM",
          filterAble: true,
          key: "ten_ctkm",
        },
        {
          label: "Tiền KM",
          filterAble: true,
          key: "cuoc_sd",
          align: "right",
          width: "150px",
        },
        {
          label: "Tỷ lệ KMLĐ",
          filterAble: true,
          key: "tyle_kmld",
          align: "right",
          width: "150px",
        },
        {
          label: "Nhóm",
          filterAble: true,
          key: "nhom_km",
          align: "right",
          width: "150px",
        },
      ],
      tabList: [
        { id: 0, name: "Khu vực" },
        { id: 1, name: "Loại hình TB" },
        { id: 2, name: "Kiểu lắp đặt" },
        { id: 3, name: "Trang bị" },
        { id: 4, name: "Đối tượng TB" },
        { id: 5, name: "Dịch vụ kèm" },
        { id: 6, name: "Gói tham gia" },
        { id: 7, name: "Loại nhà" },
      ],
      promotionsHeader: [
        {
          fieldName: 'khuyenmai_id',
          headerText: 'ID',
          allowFiltering: true,
          allowSorting: true,
          width: 70,
        },
        {
          fieldName: 'ten_km',
          headerText: 'Tên khuyến mãi',
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: 'socongvan',
          headerText: 'Số công văn',
          allowFiltering: true,
          allowSorting: true,
        }
      ],
      cboDichVuVT: {
        text: "Dịch vụ VT",
        value: "",
        list: []
      },
      selectedTab: 0,
      infoPromotion: {
        khuyenmai_id: null,
        // serviceTransportation: null,
        dispatchNumber: null,
        namePrommotion: null,
        kindOfPromtion: null,
        promotionType: null,
        accompanyingPromotion: null,
        participationPromotion: null,
        dateFrom: moment(new Date()).format("DD/MM/YYYY"),
        dateTo: moment(new Date()).format("DD/MM/YYYY"),
        commitmentUse: null,
        content: null,
        noteContent: null,
        note: {
          isShowAll: false,
          stillValidate: true,
        },
      },
      
      table: {
        loading: false,
        data: [
        ],
        config: [
          {
            headerText: 'congvan_id',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'congvan_id'
          },
          {
            headerText: 'Số công văn',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'socongvan'
          },
          {
            headerText: 'Tên công văn',
            allowFiltering: true,
            allowSorting: true,
            isPrimaryKey: true,
            fieldName: 'ten_cv'
          },
          {
            headerText: 'khuyenmai_id',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'khuyenmai_id'
          },
          {
            headerText: 'Tên km',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'ten_km'
          },
          {
            headerText: 'Ngày bắt đầu',
            allowFiltering: true,
            allowSorting: true,
            format:"dd/MM/yyyy",
            type: 'date',
            textAlign: 'right',
            fieldName: 'ngay_bd'
          },
          {
            headerText: 'Ngày kết thúc',
            allowFiltering: true,
            allowSorting: true,
            format:"dd/MM/yyyy",
            type: 'date',
            textAlign: 'right',
            fieldName: 'ngay_kt'
          },
          {
            headerText: 'Loại',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'loai'
          },
          {
            headerText: 'chitietkm_id',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'chitietkm_id'
          },
          {
            headerText: 'Tên CTKM',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'ten_ctkm'
          },
          {
            headerText: 'Nội dung',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'noidung'
          },
          {
            headerText: 'nhom_km',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'nhom_km'
          },
          {
            headerText: 'Kiểu trả trước',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'kieu_tratruoc'
          },
          {
            headerText: 'kieu_bd_tratruoc',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'kieu_bd_tratruoc'
          },
          {
            headerText: 'thang_bd_tratruoc',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'thang_bd_tratruoc'
          },
          {
            headerText: 'Tiền trả trước',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'tien_tratruoc'
          },
          {
            headerText: 'tien_td',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'tien_td'
          },
          {
            headerText: 'so_thang_tratruoc',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'so_thang_tratruoc'
          },
          {
            headerText: 'kieu_bd_khuyenmai',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'kieu_bd_khuyenmai'
          },
          {
            headerText: 'thang_bdkm',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'thang_bdkm'
          },
          {
            headerText: 'so_thang_km',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'so_thang_km'
          },
          {
            headerText: 'tyle_sd',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'tyle_sd'
          },
          {
            headerText: 'tyle_tb',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'tyle_tb'
          },
          {
            headerText: 'tang_cuoc_sd',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'tang_cuoc_sd'
          },
          {
            headerText: 'tang_cuoc_tb',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'tang_cuoc_tb'
          },
          {
            headerText: 'tien_sd',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'tien_sd'
          },
          {
            headerText: 'tien_tb',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'tien_tb'
          },
          {
            headerText: 'tyle_kmld',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'tyle_kmld'
          },
          {
            headerText: 'tien_kmld',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'tien_kmld'
          },
          {
            headerText: 'vat_kmld',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'vat_kmld'
          },
          {
            headerText: 'mien_ip',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'mien_ip'
          },
          {
            headerText: 'tien_cam_ket',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'tien_cam_ket'
          },
          {
            headerText: 'nguon_kmld',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'nguon_kmld'
          },
        ],
      },
      dichVuKem: {
        dichVuVT: null,
        maghep_id: null,
        soLuong: 0,
      },
      infoInstallationType: {
        loading: false,
        serviceTransportation: -1,
        serviceTransportation2: null,
        contractType: null,
        subscribersType: null,
      },
      buttonStatus: {
        btnAddNew: true,
        btnSave: true,
        btnCancel: true,
        btnDelete: true,
      },
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        ngay_cn: moment(new Date()).format("DD/MM/YYYY hh:mm A"),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
          "DD/MM/YYYY hh:mm A"
        ),
        nguoi_cn: this.$root.token.getUserName(),
        ma_nd: this.$root.token.getUserName(),
        ma_tinh: this.$root.token.getMaTinh(),
      },
    };
  },
  validations() {
    if (this.infoPromotion.promotionType == 2) {
      return {
        infoPromotion: {
          // serviceTransportation: { required },
          dispatchNumber: { required },
          dispatchNumber: { required },
          namePrommotion: { required },
          kindOfPromtion: { required },
          promotionType: { required },
          accompanyingPromotion: {
            required,
          },
          participationPromotion: {
            required,
          },
          dateFrom: { required },
          dateTo: { required },
          commitmentUse: { required },
          content: { required },
          noteContent: { required },
        },
      };
    } else {
      return {
        infoPromotion: {
          // serviceTransportation: { required },
          dispatchNumber: { required },
          dispatchNumber: { required },
          namePrommotion: { required },
          kindOfPromtion: { required },
          promotionType: { required },
          dateFrom: { required },
          dateTo: { required },
          commitmentUse: { required },
          content: { required },
          noteContent: { required },
        },
      };
    }
  },
  computed: {
    detailPromotion() {
      var data = JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionCommon/danhSachChiTietKhuyenMai"]
        )
      );
      console.log('detailPromotion', data);
      // this.promotionsCurrentRecordDetail = data[0];
      if(!data || data.length == 0) return [];
      // let rowIndex = data.length - 1;
      // let gridTable = this.$refs.vueTableDetail;
      // let pageSize = gridTable.pageSizeData;
      // let pageIndex = Math.ceil(rowIndex/pageSize)
      // console.log("pageIndex", pageIndex);
      // if(pageIndex > 0){
      //   pageIndex = pageIndex - 1;
      //   this.$refs.vueTableDetail.$refs.pagination.gotoPage(pageIndex);
      // }
      // setTimeout(function(){
      //   //gridTable.selectRow(rowIndex%pageSize, false);
      //   gridTable.selectRow(rowIndex%pageSize, false);
      // }, 1000);
      return data;
    },
    listTelecomService() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionCommon/listTelecomService"]
        )
      );
    },
    listTelecomService_V2() {
      try{
        let data = this.$store.getters["promotionCommon/listTelecomService"].map((e) => ({
          id: e.ID,
          text: e.NAME
        }))
        var temp;
        
        for(let i = 0; i < data.length-1 ; i++){
          for(let j = i + 1; j < data.length ; j++){
            if(data[i].id > data[j].id){
              temp = data[i];
              data[i] = data[j];
              data[j] = temp;
            }
          }
        }
        for(let i = 1; i < data.length-1 ; i++){
          for(let j = i + 1; j < data.length ; j++){
            if(data[i].text > data[j].text){
              temp = data[i];
              data[i] = data[j];
              data[j] = temp;
            }
          }
        }
        data[0].id = -1
        return data
      }
      catch(e){
        console.log("Error: ",e);
      }
    },
    listContractType() {
      return JSON.parse(
        JSON.stringify(this.$store.getters["promotionCommon/listContractType"])
      );
    },
    listDSLoainha() {
      return JSON.parse(
        JSON.stringify(this.$store.getters["promotionCommon/listContractType"])
      );
    },
    ListSoCongVan_TimKiem() {
      const data = [
        {
          congvan_id: -1,
          hieuluc: 1,
          nam: "",
          socongvan: "",
          ten_cv: "Tất cả",
        },
        ...JSON.parse(
          JSON.stringify(
            this.$store.getters["promotionCommon/ListSoCongVan_TimKiem"]
          )
        ),
      ];
      return data;
    },
    listPromotion() {
      // this.promotionsCurrentRecord =
      //   this.$store.getters["promotionCommon/listPromotion"][0] || null;
      return JSON.parse(
        JSON.stringify(this.$store.getters["promotionCommon/listPromotion"])
      )
    },
    listContractTypeDeliver() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionCommon/listContractTypeDeliver"]
        )
      );
    },
    listContractTypeNotDeliver() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionCommon/listContractTypeNotDeliver"]
        )
      );
    },
    dsloaiHinhTB() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["lhtbCommon/danhSachLoaiHinhThueBaoGetter"]
        )
      );
    },
    danhSachDoiTuongTBDaGan() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionCommon/danhSachDoiTuongTBDaGan"]
        )
      );
    },
    listSubscribersTypeDeliver() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionCommon/listSubscribersTypeDeliver"]
        )
      );
    },
    danhSachGoiThamGiaDaGan() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionCommon/danhSachGoiThamGiaDaGan"]
        )
      );
    },
    danhSachKhuVucDaGan() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionCommon/danhSachKhuVucDaGan"]
        )
      );
    },
    danhSachLoainhaDaGan() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionCommon/danhSachLoainhaDaGan"]
        )
      );
    },
    danhSachDichVuKemDaGan() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionCommon/danhSachDichVuKemDaGan"]
        )
      );
    },
    danhSachTrangBiDaGan() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionCommon/danhSachTrangBiDaGan"]
        )
      );
    },
  },
  create() {
    this.$root.showLoading(true);
  },
  async mounted() {
    this.$root.showLoading(true);
    this.tt_nd.may_cn = await this.$root.token.getMachine();
    this.tt_nd.ip_cn = await this.$root.token.getIP();
    this.$store.dispatch("promotionCommon/getListTelecomService").then((rs) => {
      if (rs.data.data) {
        // this.infoPromotion = {
        //   ...this.infoPromotion,
        //   serviceTransportation: this.listTelecomService[0].ID,
        // };
        this.infoInstallationType.serviceTransportation2 = this.listTelecomService[0].ID;
        this.dichVuKem.dichVuVT = this.listTelecomService[0].ID;
        this.cboDichVuVT.value = this.listTelecomService_V2[0].id;
      }
    });
    // this.$store.dispatch("promotionCommon/getListDispatchNo", {
    //   list_of_cols: "*",
    //   order: "congvan_id asc", // 'hieuluc desc,nam desc, socongvan',
    //   schema: "css",
    //   table_name: "socongvan",
    //   where: "loai_cv=1",
    // });
    this.$store.dispatch("promotionCommon/getListDispatchNo", {"p_param": "*", "p_type":1});
    this.$store.dispatch("promotionCommon/getListPromotionType");
    // this.$store.dispatch("promotionCommon/getListParticipationPromotion", {
    //   list_of_cols: "khuyenmai_id,ten_km,dichvuvt_id,nhomkm_id",
    //   order: "ten_km",
    //   schema: "css",
    //   table_name: "khuyenmai",
    //   where: "ttkm=1",
    // });
    this.$store.dispatch("promotionCommon/getListParticipationPromotion", {"p_param": "", "p_type":1});
    // this.$store.dispatch("promotionCommon/getListAccompanyingPromotion", {
    //   list_of_cols: "khuyenmai_id,ten_km,nhomkm_id, dichvuvt_id",
    //   order: "ten_km",
    //   schema: "css",
    //   table_name: "khuyenmai",
    //   where: "ttkm=1",
    // });
    this.$store
      .dispatch("promotionCommon/getListContractType", "-1") //"33,34,35"
      .then((rs) => {
        if (rs.data.data) {
          this.infoInstallationType = {
            ...this.infoInstallationType,
            contractType: this.listContractType[0].ID,
          };
        }
      });
    this.$store.dispatch("lhtbCommon/getDanhSachLoaiHinhTB");
    this.$store.dispatch("promotionCommon/getDanhSachLoainha" , {
      p_khuyenmai_id: -1,
      p_kieu: 0
    });
    this.$store
      .dispatch("promotionCommon/getListSoCongVan_TimKiem", {
        ttkm: -1,
        loaicv: 1,
      })
      .then((rs) => {
        this.infoInstallationType.serviceTransportation = -1;
      });
    this.getTrangThietBi();
    // this.getDanhSachMaGhep({
    //   list_of_cols: "maghep_id,ma_ghep",
    //   order: "maghep_id",
    //   schema: "css",
    //   table_name: "ma_ghep",
    //   where: "",
    // }).then((rs) => {
    //   if (rs.data.data) {
    //     this.dichVuKem.maghep_id = rs.data.data[0].maghep_id;
    //   }
    // });
    //sửa ATBM
    this.getDanhSachMaGhep({"p_param": "", "p_type":1}).then((rs) => {
      if (rs.data.data) {
        this.dichVuKem.maghep_id = rs.data.data[0].maghep_id;
      }
    });
    console.log("store: ", this.$store);
    this.$root.showLoading(false);
  },
  created(){
    this.loadGrid();
  },
  watch: {
    isViewDetail(newValue, oldValue) {
      newValue == true ? "" : this.loadGridChiTiet();
    },
    "infoPromotion.note.isShowAll"(newValue, oldValue) {
      if (newValue) {
        if (
          this.infoInstallationType.serviceTransportation2 != null &&
          this.infoPromotion.khuyenmai_id &&
          !this.infoInstallationType.serviceTransportation2
        ) {
          this.infoPromotion.note.stillValidate = false;
          this.loadGrid();
        }
      } else {
        if (
          this.infoInstallationType.serviceTransportation2 != null &&
          this.infoPromotion.khuyenmai_id &&
          this.infoInstallationType.serviceTransportation2
        ) {
          this.infoPromotion.note.stillValidate = true;
          this.loadGrid();
        }
      }
    },
    ...["infoInstallationType.contractType"].reduce(
      (watchers, key) => ({
        ...watchers,
        [key](newVal, oldVal) {
          this.getContractType();
        },
      }),
      {}
    ),
    ...["infoPromotion.khuyenmai_id"].reduce(
      (watchers, key) => ({
        ...watchers,
        [key](newVal, oldVal) {
          this.getTrangThietBi();
          if (this.infoInstallationType.serviceTransportation2) {
            this.loadGoiThamGia();
            this.loadDanhSachTocDoTheoTB();
            this.getContractType();
            this.loadDichVuKem();
          }
          this.loadKhuVuc();
          this.loadDanhSachLoainha();
          this.loadDoiTuongTB();
          if (this.infoPromotion.khuyenmai_id) {
            this.loadGridChiTiet();
            // this.getNgayHieuLuc({
            //   list_of_cols: "khuyenmai_id,ngay_hl,ngay_cn,nguoi_cn",
            //   order: "",
            //   schema: "css",
            //   table_name: "km_ngayhl",
            //   where: `khuyenmai_id=${this.infoPromotion.khuyenmai_id}`, //9493: khuyến mại ID đang chọn trên lưới
            // });
            //SỬA ATBM
            this.getNgayHieuLuc({"p_param": this.infoPromotion.khuyenmai_id, "p_type":1});
          }
        },
      }),
      {}
    ),
    ...[
      // "infoPromotion.khuyenmai_id",
      "infoInstallationType.subscribersType",
    ].reduce(
      (watchers, key) => ({
        ...watchers,
        [key](newVal, oldVal) {
          // this.getContractType();
          if (this.infoInstallationType.serviceTransportation2) {
            if (this.infoInstallationType.subscribersType) {
              this.infoInstallationType.loading = true;
              this.loadDanhSachTocDoTheoTB();
            }
          }
        },
      }),
      {}
    ),
    ...[
      "infoInstallationType.serviceTransportation2",
      "infoPromotion.dispatchNumber",
      "infoInstallationType.serviceTransportation",
      "cboDichVuVT.value"
    ].reduce((watchers, key) => ({...watchers, [key](newVal, oldVal) {
          if(key == "infoInstallationType.serviceTransportation2"){
            this.cboDichVuVT_SelectedValueChanged();
          }
          if(key == "infoPromotion.dispatchNumber"){
            this.cboCongVan_SelectedIndexChanged();
          }
          if(key == "infoInstallationType.serviceTransportation"){
            this.cboHTTT_EditValueChanged();
          }
          if(key == "cboDichVuVT.value"){
            this.loadGrid()
          }
        },
      }),
      {}
    ),
    promotionsCurrentRecord(newValue, oldValue) {
      this.onTablePromotions(newValue);
    },
    "dichVuKem.dichVuVT"(newValue, oldValue) {
      this.getDanhSachDichVuKem({
        dichvuvt_id: newValue,
        khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        kieu: 1,
      });
      this.getDanhSachDichVuKem({
        dichvuvt_id: newValue,
        khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        kieu: 0,
      });
    },
    "infoPromotion.promotionType"() {
      if (this.infoPromotion.promotionType !== 2)
        this.infoPromotion.accompanyingPromotion = -1;
    },
  },
  methods: {
    ...mapActions("promotionCommon", [
      "getListTelecomService",
      "getListPromotion",
      "getDataContractType",
      "getDanhSachTrangBi",
      "getChiTietKhuyenMai",
      "khuyenmaiInsertOUupdate",
      "xoaKhuyenmai",
      "getDanhSachLoainha",
      "getDanhSachGoiThamGia",
      "getDanhSachDichVuKem",
      "getDanhSachDoiTuongTB",
      "getDanhSachTocDoTheoTB",
      "getDanhSachKhuVuc",
      "getDanhSachMaGhep",
      "xuatCTKM",
      "copyKhuyenMai",
      "getNgayHieuLuc",
    ]),
    valueAccess: function (field, data, column) {
      return data[field] == 1 ? "Hiệu lực" : "Không hiệu lực";
    },
    // async testClick(){
    //   console.log(this.listPromotion);
    // },
    toggleCshtPopup: function () {
      this.isShowCshtPopup = !this.isShowCshtPopup;
    },
    checkReq() {
      return (
        this.infoPromotion.promotionType &&
        this.infoPromotion.promotionType == 2
      );
    },
    rowSelected(row) {
      this.promotionsCurrentRecordDetail = row;
    },
    rowSelectedSoCongVan(row) {
      console.log("row: ", row);
      this.infoInstallationType.serviceTransportation = row.data.congvan_id;
      this.isShowCshtPopup = false;
    },
    loadKhuVuc() {
      this.getDanhSachKhuVuc({
        khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        type: 0,
      });
      this.getDanhSachKhuVuc({
        khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        type: 1,
      });
    },
    loadDanhSachLoainha() {
      this.getDanhSachLoainha({
          p_khuyenmai_id:  this.infoPromotion.khuyenmai_id || 0,
          p_kieu: 0, //0: chưa gán, 1: đã gán
        });
      this.getDanhSachLoainha({
          p_khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
          p_kieu: 1, //0: chưa gán, 1: đã gán
        });
    },
    loadDanhSachTocDoTheoTB() {
      let vloaitb_id = this.infoInstallationType.subscribersType;
      // if(this.state.tabIndex == 1)
      //   vloaitb_id = 0;
      console.log("loadDanhSachTocDoTheoTB", vloaitb_id);
      this.getDanhSachTocDoTheoTB({
        vchitietkm_id: -1,
        vdichvuvt_id: this.infoInstallationType.serviceTransportation2,
        vkhuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        vloai: 1,
        vloaitb_id: vloaitb_id,
      });
      this.getDanhSachTocDoTheoTB({
        vchitietkm_id: -1,
        vdichvuvt_id: this.infoInstallationType.serviceTransportation2,
        vkhuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        vloai: 0,
        vloaitb_id: vloaitb_id,
      }).finally(() => (this.infoInstallationType.loading = false));
    },
    async loadGridChiTiet() {
      console.log('loadGridChiTiet');
      this.tableDetailLoading = true;
      const reqChiTiet = await this.getChiTietKhuyenMai({
        khuyenmai_id: this.infoPromotion.khuyenmai_id,
      }).then((rs) => {
        this.$root.showLoading(false);
      });
      this.tableDetailLoading = false;
    },
    loadGoiThamGia() {
      this.getDanhSachGoiThamGia({
        khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        kieu: 0,
      });
      this.getDanhSachGoiThamGia({
        khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        kieu: 1,
      });
    },
    loadDoiTuongTB() {
      this.getDanhSachDoiTuongTB({
        dichvuvt_id: this.infoInstallationType.serviceTransportation2,
        khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        kieu: 1,
      });
      this.getDanhSachDoiTuongTB({
        dichvuvt_id: this.infoInstallationType.serviceTransportation2,
        khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        kieu: 0,
      });
    },
    loadDichVuKem() {
      this.getDanhSachDichVuKem({
        dichvuvt_id: this.dichVuKem.dichVuVT,
        khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        kieu: 1,
      });
      this.getDanhSachDichVuKem({
        dichvuvt_id: this.dichVuKem.dichVuVT,
        khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        kieu: 0,
      });
    },
    async loadGrid() {
      this.$root.showLoading(true);
      const grid = await this.getListPromotion({
        // phanvung_id: this.$root.token.getPhanVungID(),
        // dichvuvt_id: this.infoInstallationType.serviceTransportation2,
        dichvuvt_id: this.cboDichVuVT.value,
        congvan_id: this.infoInstallationType.serviceTransportation,
        trangthai_km: this.infoPromotion.note.isShowAll
          ? -1
          : this.infoPromotion.note.stillValidate
          ? 1
          : 0,
      });
      this.promotionsCurrentRecord = grid.data.data[0] || [];
      let rowIndex = 0;
      let gridTable = this.$refs.vueTable;
      if(this.state.selectedLastRow && this.state.selectedLastRow == true){
        this.state.selectedLastRow = false;
        rowIndex = grid.data.data.length - 1;
      }
      let pageSize = gridTable.pageSizeData;
      let pageIndex = Math.ceil(rowIndex/pageSize)
      console.log("pageIndex", pageIndex);
      if(pageIndex > 0){
        pageIndex = pageIndex - 1;
        this.$refs.vueTable.$refs.pagination.gotoPage(pageIndex);
      }
      setTimeout(function(){
        gridTable.selectRow(rowIndex%pageSize, false);
      }, 1000);
      this.$root.showLoading(false);
    },
    getContractType() {
      if (
        this.infoInstallationType.serviceTransportation2 != null &&
        this.infoInstallationType.contractType != null
      ) {
        this.getDataContractType({
          loaihd_id: this.infoInstallationType.contractType,
          khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
          dichvuvt_id: this.infoInstallationType.serviceTransportation2,
          kieu: 0,
        });
        this.getDataContractType({
          loaihd_id: this.infoInstallationType.contractType,
          khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
          dichvuvt_id: this.infoInstallationType.serviceTransportation2,
          kieu: 1,
        });
      }
    },
    getTrangThietBi() {
      this.getDanhSachTrangBi({
        khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        kieu: 0,
      });
      this.getDanhSachTrangBi({
        khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        kieu: 1,
      });
    },
    onBtnAddNewClick(e) {
      e.preventDefault();
      this.buttonStatus = {
        btnAddNew: false,
        btnSave: true,
        btnCancel: true,
        btnDelete: false,
      };
      this.resetForm();
      this.infoPromotion.dispatchNumber = null;
      this.infoPromotion.namePrommotion = "";
      this.infoPromotion.kindOfPromtion = null;
      this.infoPromotion.promotionType = null;
      this.infoPromotion.accompanyingPromotion = null;
      this.infoPromotion.participationPromotion = null;
      this.infoPromotion.dateFrom = moment(new Date()).format(
        "DD/MM/YYYY HH:mm:ss"
      );
      this.infoPromotion.dateTo = moment(new Date()).format(
        "DD/MM/YYYY HH:mm:ss"
      );
      this.infoPromotion.commitmentUse = "";
      this.infoPromotion.content = "";
      this.infoPromotion.noteContent = "";
      this.infoPromotion.khuyenmai_id = "";
    },
    async onBtnXuatCTKMClick(e) {
      e.preventDefault();
      const rs = await this.xuatCTKM({
        vcongvan_id: this.infoPromotion.dispatchNumber,
        vkhuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
      });
      this.$toast.info(rs.data.message);
      if(rs.data &&rs.data.data &&
        rs.data.error_code &&rs.data.error_code === "BSS-00000000"){
        this.table.data = rs.data.data;
        this.$refs.exportDataModal.showModal();
      }
      //this.tsTest_Click();
    },
    async tsTest_Click() {
      return new Promise(async (resolve, reject) => {
        try {
          let response = await this.axios.post(
            'web-tracuu/khuyenmai/lay_ctkm_theo_congvan',
            {
              vcongvan_id: this.infoPromotion.dispatchNumber,
              vkhuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
            },
            {responseType: 'arraybuffer'}
          )
          if (response.headers && response.headers['content-type'] == "text/plain") {
            const reader = new FileReader();
            reader.addEventListener('loadend', (e) => {
              const text = e.srcElement.result;
              this.$toast.error(text)
              this.loading(false)
              return resolve(false)
            });
            let blb = new Blob([response.data], {type: 'text/plain'})
            reader.readAsText(blb);
            return
          }
          if(response.data && response.data.size == 0) {
            this.$toast.error("Không có dữ liệu. Vui lòng kiểm tra lại")
            this.loading(false)
            return resolve(false)
          }
          const link = document.createElement('a')
          link.href = window.URL.createObjectURL(new Blob([response.data]))
          link.setAttribute('download', ("ctkm.xlsx"))
          document.body.appendChild(link)
          link.click()
        } catch(err) {
          console.log(err)
        }
        return resolve(true)
      })
  },
    async onBtnSaveClick(e) {
      e.preventDefault();
      this.$v.$touch();
      if (this.$v.$invalid) {
        this.$toast.error("Vui lòng nhập đủ các trường thông tin đánh dấu *!");
        return;
      }
      this.$root.showLoading(true);
      if (this.infoPromotion.dateFrom && this.infoPromotion.dateTo) {
        if (new Date(moment(this.infoPromotion.dateFrom, "DD/MM/YYYY HH:mm:ss")) > new Date(moment(this.infoPromotion.dateTo, "DD/MM/YYYY HH:mm:ss"))) {
          this.$toast.error("Từ ngày không được lớn hơn Đến ngày!");
          return false;
        }
      }
      const is_insert = this.buttonStatus.btnAddNew ? 0 : 1;
      const KIEULD_ID = [];
      const DOITUONG_ID = [];
      const DICHVUKEM = [];
      const GOI_ID = [];
      const KHUVUC_ID = [];
      const LOAITB_ID = [];
      const LOAINHA_ID = [];
      const TRANGBI_ID = [];
      this.listContractTypeDeliver.map((obj) => {
        KIEULD_ID.push({
          KIEULD_ID: obj.kieuld_id,
        });
      });
      this.danhSachDoiTuongTBDaGan.map((obj) => {
        DOITUONG_ID.push({
          DOITUONG_ID: obj.doituong_id,
        });
      });
      this.danhSachDichVuKemDaGan.map((obj) => {
        DICHVUKEM.push({
          LOAITB_ID: obj.loaitb_id,
          MAGHEP_ID: this.dichVuKem.maghep_id,
          SOLUONG: this.dichVuKem.soLuong,
        });
      });
      this.danhSachGoiThamGiaDaGan.map((obj) => {
        GOI_ID.push({
          GOI_ID: obj.goi_id,
        });
      });
      this.danhSachKhuVucDaGan.map((obj) => {
        KHUVUC_ID.push({
          KHUVUC_ID: obj.khuvuc_id,
        });
      });
      if(this.danhSachLoainhaDaGan&&this.danhSachLoainhaDaGan.length >0){
         this.danhSachLoainhaDaGan.map((obj) => {
            LOAINHA_ID.push({
              LOAINHA_ID: obj.loainha_id,
            });
          });
      }else{
        LOAINHA_ID.push({
            LOAINHA_ID: 0,
        });
      }
     
      this.listSubscribersTypeDeliver.map((obj) => {
          LOAITB_ID.push({
            LOAITB_ID: obj.loaitb_id,
            MUCCUOC_ID: this.state.tabIndex == 1 ? 0 : obj.muccuoc_id,
            TOCDO_ID: this.state.tabIndex == 1 ? 0 : obj.tocdo_id,
          });
      });
      this.danhSachTrangBiDaGan.map((obj) => {
        TRANGBI_ID.push({
          TRANGBI_ID: obj.trangbi_id,
        });
      });
      if (TRANGBI_ID.length == 0) {
        this.$toast.error("Bạn phải gán trang bị!");
        this.selectedTab = 3;
        this.loading(false);
        return false;
      }
      const data = {
        is_insert: is_insert,
        json_doituong: DOITUONG_ID,
        json_dvgt: null,
        json_dvkem: DICHVUKEM,
        json_goi_dadv: GOI_ID,
        json_khl: null,
        json_khuvuc: KHUVUC_ID,
        p_json_loainha: LOAINHA_ID,
        json_kieuld: KIEULD_ID,
        json_km: [
          {
            CONGVAN_ID: this.infoPromotion.dispatchNumber,
            TEN_KM: this.infoPromotion.namePrommotion,
            DICHVUVT_ID: this.infoInstallationType.serviceTransportation2,
            GHICHU: this.infoPromotion.noteContent,
            HIENTHI_TC: 1,
            KHUVUC_ID: null,
            KHUYENMAI_CHA_ID: null,
            KIEUKM_ID: this.infoPromotion.promotionType,
            KMTG_ID: this.infoPromotion.participationPromotion || 0,
            LOAI_KM: this.infoPromotion.kindOfPromtion,
            MA_KM: "",
            NGAY_BD: this.infoPromotion.dateFrom.split(" ")[0],
            NGAY_KT: this.infoPromotion.dateTo.split(" ")[0],
            NHOMKM_ID: this.infoPromotion.accompanyingPromotion,
            NOIDUNG_KM: this.infoPromotion.content,
            SOTHANG_SD: this.infoPromotion.commitmentUse,
            THANG_DC: null,
            TTKM: this.infoPromotion.note.stillValidate ? 1 : 0,
            MAY_CN: this.tt_nd.may_cn,
            NGAY_CN: this.tt_nd.ngay_cn,
            NGUOI_CN: this.tt_nd.nguoi_cn,
            IP_CN: this.tt_nd.ip_cn,
          },
        ],
        json_loaihinh_tb: LOAITB_ID,
        json_trangbi: TRANGBI_ID,
        khuyenmai_id: this.infoPromotion.khuyenmai_id || 0,
        congvan_id: this.infoPromotion.dispatchNumber,
      };
      console.log(JSON.stringify(data));
      let thanhcong = false;
      try{
        this.loading(true)
        await this.khuyenmaiInsertOUupdate(data)
        .then((rs) => {
          if (rs.data.message == "Success") {
            is_insert == 0
              ? this.$toast.success("Cập nhật thành công")
              : this.$toast.success("Thêm mới thành công");
            thanhcong = true;
          } else {
            this.$toast.error(rs.data.message);
          }
        })
        .catch((ex) => {
          commonFn.showException(this, ex);
        })
        .finally(() => this.$root.showLoading(false));
        if(thanhcong == true){
          if(is_insert == 1) this.state.selectedLastRow = true;
          if(this.infoInstallationType.serviceTransportation != this.infoPromotion.dispatchNumber)
            this.infoInstallationType.serviceTransportation = this.infoPromotion.dispatchNumber;
          else{
            this.HienThiDanhSach(-1);
          }
        }
      }catch(ex){
         commonFn.showException(this, ex);
      }
      this.loading(false)
    },
    
    onBtnCancelClick(e) {
      e.preventDefault();
      this.buttonStatus = {
        btnAddNew: true,
        btnSave: false,
        btnCancel: false,
        btnDelete: false,
      };
      this.infoPromotion.namePrommotion = "";
      this.infoPromotion.kindOfPromtion = -1;
      this.infoPromotion.promotionType = -1;
      this.infoPromotion.accompanyingPromotion = -1;
      this.infoPromotion.participationPromotion = -1;
      this.infoPromotion.commitmentUse = 0;
      this.infoPromotion.content = "";
      this.infoPromotion.noteContent = "";
      this.infoPromotion.khuyenmai_id = -1;
      // this.infoInstallationType.serviceTransportation = -1;
      // this.infoPromotion.serviceTransportation =
      //   this.listTelecomService.length > 0 ? this.listTelecomService[0].ID : 14;
      // this.infoPromotion.note.stillValidate = true;
      // this.loadGrid();
    },
    
    async onBtnCopyClick(e) {
      try{
        const isConfirm = await this.$bvModal.msgBoxConfirm(
          `Bạn có thực sự muốn copy khuyến mại không?`,
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
          await this.copyKhuyenMai({
            vkhuyenmai_id_cu: this.infoPromotion.khuyenmai_id || 0,
          })
            .then((rs) => {
              if (rs.data.message == "Success") {
                this.$toast.success("Copy khuyến mại thành công");
                this.loadGrid();
              }
            })
            .catch((e) => {
              commonFn.showException(this, e);
            })
            .finally(() => {
              this.$root.showLoading(false);
            });
        }
      }catch(ex){
         commonFn.showException(this, ex);
      }
      this.loading(false)
    },
    async onBtnDeleteClick(e) {
      e.preventDefault();
      const isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn có thực sự muốn xóa khuyến mại không?`,
        {
          title: "",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
          centered: true,
        }
      );
      if (isConfirm) {
        try{
          this.loading(true)
          await this.xoaKhuyenmai({
            khuyenmai_id: this.infoPromotion.khuyenmai_id,
          })
            .then((rs) => {
              if (rs.data.data == "OK") {
                this.$toast.success("Xóa khuyến mại thành công");
                this.loadGrid();
              }else{
                this.$toast.error(rs.data.data);
              }
            })
            .catch((e) => {
              commonFn.showException(this, e);
            });
        }catch(ex){
          commonFn.showException(this, ex);
        }
        this.loading(false)
      }
    },
    onTablePromotions(row) {
      if (row) {
        this.buttonStatus = {
          btnAddNew: true,
          btnSave: true,
          btnCancel: true,
          btnDelete: true,
        };
        this.infoPromotion.dispatchNumber = row.congvan_id;
        this.infoPromotion.namePrommotion = row.ten_km;
        this.infoPromotion.kindOfPromtion = row.loai_km;
        this.infoPromotion.promotionType = row.kieukm_id;
        this.infoPromotion.accompanyingPromotion = row.nhomkm_id;
        this.infoPromotion.participationPromotion = row.kmtg_id;
        if(row.ngay_bd && row.ngay_bd.length > 0){
          this.infoPromotion.dateFrom = moment(row.ngay_bd, "DD/MM/YYYY HH:mm:ss").format(
            "DD/MM/YYYY HH:mm:ss"
          );
        }else this.infoPromotion.dateFrom = null;
        if(row.ngay_kt && row.ngay_kt.length > 0){
          this.infoPromotion.dateTo = moment(row.ngay_kt, "DD/MM/YYYY HH:mm:ss").format(
            "DD/MM/YYYY HH:mm:ss"
          );
        }
        else this.infoPromotion.dateTo = null;
        // row.kmtg_id
        this.infoPromotion.commitmentUse = row.sothang_sd;
        this.infoPromotion.content = row.noidung_km;
        this.infoPromotion.noteContent = row.ghichu;
        this.infoPromotion.khuyenmai_id = row.khuyenmai_id;
      } else {
        this.resetForm();
        this.getContractType();
        this.$v.$reset();
        this.buttonStatus = {
          btnAddNew: false,
          btnSave: true,
          btnCancel: true,
          btnDelete: false,
        };
        if (this.infoInstallationType.serviceTransportation) {
          // const congvan = this.ListSoCongVan_TimKiem.find(
          //   (e) =>
          //     e.congvan_id == this.infoInstallationType.serviceTransportation
          // );
          // if (congvan) {
          //   this.infoPromotion.dispatchNumber =
          //     this.infoInstallationType.serviceTransportation;
          //   this.infoPromotion.namePrommotion =
          //     congvan.socongvan + "-" + congvan.ten_cv;
          // }
        }
      }
    },
    async cboCongVan_SelectedIndexChanged(){
      this.loadkmKem();
    },
    async grvDanhSachKM_FocusedRowChanged(e){
      console.log("grvDanhSachKM_FocusedRowChanged", e);
      this.promotionsCurrentRecord = e;
    },
    async loadkmKem(){
      console.log('cboCongVan_SelectedIndexChanged loadkmKem');
      let congvan_id = this.infoPromotion.dispatchNumber;
      let kieuKM = this.infoPromotion.promotionType;
      // if(congvan_id && congvan_id > 0 && kieuKM == 2){
      // }
      // else
      //   congvan_id = "-1";
      // this.$store.dispatch("promotionCommon/getListAccompanyingPromotion", {
      //   list_of_cols: "khuyenmai_id,ten_km,nhomkm_id, dichvuvt_id",
      //   order: "ten_km",
      //   schema: "css",
      //   table_name: "khuyenmai",
      //   where: `ttkm=1 and khuyenmai_id <> 1 and congvan_id= ${congvan_id}`,
      // });
    },
    async cboDichVuVT_SelectedValueChanged(){
      console.log("cboDichVuVT_SelectedValueChanged", this.infoInstallationType.serviceTransportation2);
      let vdichvuvt_id = this.infoInstallationType.serviceTransportation2;
      let req = {
        vdichvuvt_id: vdichvuvt_id
      }
      let data = commonFn.GetData(await api.lay_ds_lhtb_km(this.axios, req));
      if(data && data.length > 0){
        this.infoInstallationType.subscribersType = data[0].loaitb_id;
        this.state.tabIndex = 0;
        this.tabList[1].name = "Tốc độ"
      }else{
        this.state.tabIndex = 1;
        this.tabList[1].name = "Loại hình TB"
      }
      // if(vdichvuvt_id == 1) //cố định
      // {
      //   this.state.tabIndex = 1;
      // }
      this.HienThiDanhSach(this.infoInstallationType.serviceTransportation);
    },
    async cboHTTT_EditValueChanged(){
      this.HienThiDanhSach(-1);
      this.loadGrid();
    },
    async HienThiDanhSach(congvan_id){
      // this.loadGrid();
      this.getContractType();
      if (this.infoInstallationType.serviceTransportation2) {
        this.infoInstallationType.serviceTransportationName =
          this.ListSoCongVan_TimKiem.find(
            (e) =>
              e.congvan_id ==
              this.infoInstallationType.serviceTransportation
          ).ten_cv;
        this.loadDichVuKem();
        this.loadDoiTuongTB();
        if (this.infoInstallationType.subscribersType) {
          this.infoInstallationType.loading = true;
          this.loadDanhSachTocDoTheoTB();
        }
      }
    },
    resetForm() {
      this.infoPromotion.khuyenmai_id = 0;
      this.infoPromotion.dispatchNumber = null;
      this.infoPromotion.namePrommotion = "";
      this.infoPromotion.kindOfPromtion = null;
      this.infoPromotion.promotionType = null;
      this.infoPromotion.accompanyingPromotion = null;
      this.infoPromotion.participationPromotion = null;
      this.infoPromotion.dateFrom = moment(new Date()).format(
        "DD/MM/YYYY HH:mm:ss"
      );
      this.infoPromotion.dateTo = moment(new Date()).format(
        "DD/MM/YYYY HH:mm:ss"
      );
      this.infoPromotion.commitmentUse = "";
      this.infoPromotion.content = "";
      this.infoPromotion.noteContent = "";
      this.$store.commit("promotionCommon/setDanhSachChiTietKhuyenMai", []);
    },
    reloadGridDSKM() {
      this.getListPromotion({
        
        // phanvung_id: this.$root.token.getPhanVungID(),
        // dichvuvt_id: this.infoInstallationType.serviceTransportation2,
        dichvuvt_id: this.cboDichVuVT.value,
        congvan_id: this.infoInstallationType.serviceTransportation,
        trangthai_km: this.infoPromotion.note.isShowAll
          ? -1
          : this.infoPromotion.note.stillValidate
          ? 1
          : 0,
      });
    },
  },
};
</script>
<style >
/* .table-box {
  display: grid;
  grid-template-columns: 2fr 3fr 1fr 1fr;
}
.table-box > div {
  margin: 2px;
  background: powderblue;
  padding: 5px;
}
.table-box > div:nth-child(3n + 2) {
  background: lemonchiffon;
}
.table-box > div:nth-child(3n + 3) {
  background: palegreen;
} */
.table-box {
  border-collapse: collapse;
  margin: 0;
  padding: 0;
  width: 100%;
  table-layout: fixed;
}
.table-box tr {
  background-color: #fff;
  border-bottom: 1px solid #ddd;
}
.table-box th {
  padding: 0.625em;
  border: 1px solid #ddd;
  text-align: center;
}
.e-list-parent {
  padding: 0px !important;
}
.e-list-parent .e-active {
  background-color: #f9e1a9 !important;
  color: #333;
}
.e-list-item {
  padding: 0 !important;
  overflow: visible;
  width: 500px;
}
.e-active .table-box tr {
  background-color: #f9e1a9 !important;
  color: #333;
}
.table-box tr td {
  border: 1px solid #ddd;
  padding: 0.625em;
}
.e-list-parent .e-active {
  background-color: #f9e1a9 !important;
  color: #333;
}
.table-box tr:hover {
  background-color: #fff9eb !important;
}
</style>