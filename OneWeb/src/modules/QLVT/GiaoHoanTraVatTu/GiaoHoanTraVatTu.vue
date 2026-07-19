<template src='./GiaoHoanTraVatTu.html'></template>
<style scoped src='./GiaoHoanTraVatTu.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import { TreeViewPlugin } from "@syncfusion/ej2-vue-navigations";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/giaohoantravattu";
import {
  Freeze,
  Edit,
  Group,
  Page,
  Filter,
  Sort,
  Resize,
} from "@syncfusion/ej2-vue-grids";
import moment from "moment";
import Vue from "vue";
import { L10n } from "@syncfusion/ej2-base";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import { currency } from "@/filters/currency";
import EventBus from "@/utils/eventBus";
import { previewPrint } from "../../../utils/util";
import { Query, Predicate } from "@syncfusion/ej2-data";

Vue.use(DatePickerPlugin);
L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
    },
  },
});
Vue.use(TreeViewPlugin);

export default {
  components: { breadcrumb },
  name: "GiaoHoanTraVatTu",

  provide: {
    grid: [Freeze, Edit, Group, Page, Filter, Sort, Resize],
  },
  data() {
    return {
      header: {
        title: "GIAO CHỨNG TỪ HOÀN TRẢ",
        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Nhập/Xuất/Điều chuyển vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Giao chứng từ hoàn trả",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        formatDayTime: "dd/MM/y HH:mm",
        waterMark: "Chọn ngày",
      },

      xn_phanloai_nhapkho: false,
      params: {
        DSQuyen: [],
        tagFrm: "",
        donViGiaoNhanId: "",
        trangThaiId: 1,
        loaiPhieuId: 1,
        locPhieuId: "",
        tuNgay: new Date(),
        denNgay: new Date(),
        khoId: "",
        donViNhanId: "",

        ghiChu: "",
        khoGiao: "",
        ngayCT: "",
        ngayXuat: "",
        soPhieu: "",
        lenhVB: "",
        ngayVB: new Date(),
        listCheckboxChungTu: [],
        noiDungTra: "",
        noiDungNhapKho: "",
        ngayNhapKho: new Date(),
      },
      showFile: "none",
      Text: {
        Kho: "Kho đến",
      },
      Checkbox: {
        isCheckNgay: true,
      },
      Focus: {
        chungTu: {},
        daChia: {},
      },
      thongTinNguoiDung: {
        nguoiCN: this.$root.token.getUserName(), //this.$root.token.getUserName()
        mayCN: "DESKTOP-IDG67LD", //this.$auth.MAYCN
        ipCN: "10.59.90.128", //this.$auth.MAYCN
        donViId: this.$root.token.getDonViID(), //this.$root.token.getDonViID(),
        phanVungId: this.$root.token.getPhanVungID(),
        nhanVienId: this.$root.token.getNhanVienID(),
        nguoiDungId: this.$root.token.getNguoiDungID(),
        maTinh: this.$root.token.getMaTinh(),
      },
      dataResponse: {
        DSChiTietVatTu: [],
        DSThietBi: [],
        DSFileCV: [],
        txtFileCV: "",
        DSXuatDaChia: [],
        DSDonViGiaoNhan: [],
        DSTrangThai: [
          {
            ID: 1,
            TRANGTHAI: "Mới",
          },
          {
            ID: 2,
            TRANGTHAI: "Đã giao",
          },
          {
            ID: 3,
            TRANGTHAI: "Trả lại",
          },
        ],
        DSLoaiPhieu: [
          {
            ID: 1,
            TRANGTHAI: "Chứng từ giao",
          },
          {
            ID: 2,
            TRANGTHAI: "Chứng từ đến",
          },
        ],
        DSLocPhieu: [],
        DSKho: [],
        DSDonViNhan: [],
        DSHoanTraCT: [],
        DSLoaiKho: [],
        DSKhoTTVT: [],
        DSKhoDen: [],
      },
      firstLoad: true,
      Enabled: {
        locPhieu: true,
        tabDSDaChia: true,
        loaiPhieu: false,
        colKhoDen: true,
        colLoaiKho: true,
        colMaKho: true,
      },
      Visible: {
        giaoPhieu: true,
        nhapKho: true,
        traLai: true,
        huyBo: true,
        xacNhan: false,
        boxSearch: true,
        colKhoDen: true,
        colLoaiKho: true,
        colMaKho: true,
      },
      classIconHiddenBox: "icon fa fa-angle-up",

      customTemplate: function () {
        return {
          template: Vue.component("footerTemplate", {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data() {
              return { data: { data: {} } };
            },
          }),
        };
      },
      downloadTemplate: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>
                          <button class="btn btn-primary" @click="onDownloadFile" title="Tải xuống">
                            <span class="icon one-search"></span>
                          </button>
                      </div>`,
            data() {
              return { data: {} };
            },
            methods: {
              onDownloadFile() {
                EventBus.$emit("onDownloadFile", this.data);
              },
            },
          }),
        };
      },
      khoDenTemplate: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>     
                        <ejs-dropdownlist class="form-control" ref="" locale='vi'
                            v-model="khoDenId" :dataSource='parent.dataResponse.DSKhoDen'
                            :fields="{value:'khoId', text: 'tenKho'}" :allowFiltering="true" popupWidth="500px"
                            :filtering='parent.onFilteringKhoDen' :enabled='parent.Enabled.colKhoDen' @change="onChangeKhoDen">
                        </ejs-dropdownlist>                            
                      </div>`,
            data() {
              return {
                data: {},
                khoDenId: 0,
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
            created: async function () {
              this.khoDenId = this.data.KHO_ID == null || this.data.KHO_ID == "" ? 0 : parseInt(this.data.KHO_ID);
            },
            methods: {
              onChangeKhoDen() {
                this.data.KHO_ID = this.khoDenId;
                EventBus.$emit("onChangeKhoDen", this.data);
              },
            },           
          }),
        };
      },
      onFilteringKhoDen: function (e) {
        var query = new Query();
        let predicate;

        //frame the query based on search string with filter type.
        if (e.text !== "") {
          predicate = new Predicate("tenKho", "contains", e.text.trim(), true);
          query.where(predicate);
        }

        //pass the filter data source, filter query to updateData method.
        e.updateData(this.dataSource, query);
      },
      onFilteringLoaiKho: function (e) {
        var query = new Query();
        let predicate;

        //frame the query based on search string with filter type.
        if (e.text !== "") {
          predicate = new Predicate("tenLoaiKho", "contains", e.text.trim(), true);
          query.where(predicate);
        }

        //pass the filter data source, filter query to updateData method.
        e.updateData(this.dataSource, query);
      },
      loaiKhoTemplate: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>   
                          <ejs-dropdownlist class="form-control" ref="" locale='vi'
                            v-model="loaiKhoId" :dataSource='parent.dataResponse.DSLoaiKho'
                            :fields="{value:'loaiKhoTdId', text: 'tenLoaiKho'}" :allowFiltering="true" popupWidth="500px"
                            :filtering='parent.onFilteringLoaiKho' :enabled='parent.Enabled.colLoaiKho' @change="onChangeLoaiKho">
                          </ejs-dropdownlist>                       
                          
                      </div>`,
            data() {
              return {
                data: {},
                loaiKhoId: 0,
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
            created: async function () {
              this.loaiKhoId = this.data.LOAIKHO == null || this.data.LOAIKHO == "" ? 0 : parseInt(this.data.LOAIKHO);
            },
            methods: {
              onChangeLoaiKho() {
                this.data.LOAIKHO = this.loaiKhoId;
                EventBus.$emit("onChangeLoaiKho", this.data);
              },
            },
          }),
        };
      },

      maKhoKTTemplate: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>                         
                          <select class="form-control"  v-model="maKhoKTId" @change="onChangeMaKhoKT" :disabled="!parent.Enabled.colMaKho">
                              <option v-for="maKho in DSMaKhoKT" v-bind:value="maKho.khoTDId">
                                  {{maKho.maKhoTd}}</option>
                          </select>
                      </div>`,
            data() {
              return {
                data: {},
                maKhoKTId: 0,
                DSMaKhoKT: [],
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
            created: async function () {
              this.maKhoKTId = this.data.MAKHOTD;
              if (this.data.LOAIKHO != null && this.data.LOAIKHO != "") {
                var rs = await this.$root.context.get(
                  `/web-qlvt/api/tra-cuu-ton-kho/danh-sach-vat-tu/${this.data.LOAIKHO}`
                );
                this.DSMaKhoKT = rs.data;
              } else {
                this.DSMaKhoKT = [];
              }
            },
            methods: {
              onChangeMaKhoKT() {
                this.data.MAKHOTD = this.maKhoKTId;
                EventBus.$emit("onChangeMaKhoKT", this.data);
              },
            },
          }),
        };
      },

      onFilteringKho: function (e) {
        var query = new Query();
        let predicate;

        //frame the query based on search string with filter type.
        if (e.text !== "") {
          predicate = new Predicate("tenKho", "contains", e.text.trim(), true);
          query.where(predicate);
        }

        //pass the filter data source, filter query to updateData method.
        e.updateData(this.dataSource, query);
      },

      onFilteringDVGiao: function (e) {
        var query = new Query();
        let predicate;

        //frame the query based on search string with filter type.
        if (e.text !== "") {
          predicate = new Predicate("TEN_DV", "contains", e.text.trim(), true);
          query.where(predicate);
        }

        //pass the filter data source, filter query to updateData method.
        e.updateData(this.dataSource, query);
      },
    };
  },

  mounted: async function () {
    document.addEventListener("click", (event) => {
      if (typeof this.$refs["title-file"] == "undefined") {
        return;
      }
      if (this.$refs["title-file"].contains(event.target)) {
        this.showFile = this.showFile == "none" ? "block" : "none";
      } else if (this.$refs["table-file"].contains(event.target)) {
        if (event.target.tagName.toLowerCase() == "td") {
          this.showFile = "none";
        } else {
          this.showFile = "block";
        }
      } else {
        this.showFile = "none";
      }

      if (this.showFile == "block") {
        this.popper = new Popper(
          this.$refs["title-file"],
          this.$refs["table-file"],
          {
            placement: "bottom-start",
            modifiers: [
              {
                name: "offset",
                options: {
                  offset: [0, 50],
                },
              },
            ],
          }
        );
      } else {
        if (this.popper) {
          this.popper.destroy();
        }
        this.popper = null;
      }
    });
  },
  watch: {},
  computed: {
    ...mapState("giaohoantravattu", statePropertyName),
    ...mapGetters("giaohoantravattu", getterName),
    PTAggregatesColumns() {
      return [
        {
          field: "TIEN",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.customAggregate,
        },
      ];
    },
  },

  methods: {
    ...mapActions("giaohoantravattu", actionName),
    ...mapMutations("giaohoantravattu", mutationName),

    chooseFile() {
      this.$refs["ref-choose-file"].$el.querySelector("input").click();
      console.log("chooseFile");
    },
    async onChangeFile() {
      console.log("onChangeFile");
      let fileChooser = this.$refs["ref-choose-file"];
      let choosedFiles = fileChooser.$el.querySelector("input").files;
      if (choosedFiles.length == 0) {
        fileChooser.reset();
        return;
      }
      choosedFiles = [...choosedFiles];
      fileChooser.reset();

      this.loading(true);
      let formData = new FormData();
      // var paths = []
      for (let i = 0; i < choosedFiles.length; i++) {
        let item = choosedFiles[i];
        formData.append("file", item);
        // paths.push(path)
      }
      let paths = await this.uploadFile(formData);

      if (paths.length == 0) {
        this.$toast.error("Upload file thất bại!");
        this.loading(false);
        return;
      }
      this.dataResponse.DSFileCV = []
      for (let i = 0; i < paths.length; i++) {
        if (paths[i].status) {
          var urlSplit = paths[i].message.relativeUrl.split("/");
          this.dataResponse.DSFileCV.push({
            linkFile: paths[i].message.relativeUrl,
            nameFile: urlSplit[urlSplit.length - 1],
          });
        }
      }
     
      this.dataResponse.txtFileCV = "";
      this.dataResponse.DSFileCV.forEach((e) => {
        this.dataResponse.txtFileCV += e.linkFile;
      });
      this.$refs.gridFile.grid.refreshColumns();
      this.$toast.success("Upload file thành công!");
      this.loading(false);
    },
    customAggregate() {
      var TongTien = 0;
      this.dataResponse.DSChiTietVatTu.forEach((e) => {
        TongTien += e.TIEN;
      });
      return currency(TongTien);
    },
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },
    // convertDateTime_DDMMYYYY_to_YYYYMMDD(datetime){
    //   datetime = datetime.split(" ")
    //   var thisDate = datetime[0].split('-');
    //   var newDate = [thisDate[2],thisDate[1],thisDate[0]].join("/");
    //   return newDate
    // },
    convertDateTime_DDMMYYYY_to_YYYYMMDD(datetime) {
      datetime = datetime.split(" ");
      var thisDate = datetime[0].split("/");
      var newDate = [thisDate[2], thisDate[1], thisDate[0]].join("/");
      return newDate;
    },
    async gridDSChungTuSelected(row) {
      try{
        this.loading(true)
        if (row) {
          this.Focus.chungTu = row;
          this.CLEAR();

          this.params.ghiChu = row.GHICHU;
          this.params.khoGiao = row.KHO_GIAO;
          this.params.ngayCT = row.NGAY_CT;
          this.params.ngayXuat = row.NGAYGUI_TT;
          this.params.soPhieu = row.SOPHIEU;
          this.params.lenhVB = row.SO_VB;
          this.params.ngayVB =
            row.NGAY_VB != null && row.NGAY_VB != ""
              ? new Date(this.convertDateTime_DDMMYYYY_to_YYYYMMDD(row.NGAY_VB))
              : new Date();
          var ctht_id = row.VANBAN_ID;

          if (ctht_id != null && ctht_id != "") {
            var DSFileCV = await this.getDSFileCongVan(ctht_id);
            if (DSFileCV != null && DSFileCV.length > 0 && DSFileCV[0] != null) {

              this.dataResponse.txtFileCV = DSFileCV[0];
              var listFileCV = DSFileCV[0].split("|");
              listFileCV.forEach((element) => {
                if (element != "") {
                  var nameFileSplit = element.split("/");
                  this.dataResponse.DSFileCV.push({
                    linkFile: element,
                    nameFile: nameFileSplit[nameFileSplit.length - 1],
                  });
                }
              });                
              
            }
          }
          this.$refs.gridFile.grid.refreshColumns();
          await this.NAP_DS_CHITIET(2);
          await this.NAP_DS_THIETBI(2);
        } else {
          this.dataResponse.DSXuatDaChia = [];
          this.dataResponse.DSThietBi = [];
          this.Focus.chungTu = {};
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async gridDSXuatDaChiaSelected(row) {
      if (row) {
        this.Focus.daChia = row;
        await this.focusXuat(0);
      } else {
        await this.focusXuat(-1);
      }
    },
    async NAP_DS_CHITIET(kieu) {
      var DSChiTietVatTu = [];
      if (kieu == 1) {
        if (!("CHUNGTU_ID" in this.Focus.daChia)) {
          return;
        }
        DSChiTietVatTu = await this.getDSChiTietVatTuDaChia(
          this.Focus.daChia.CHUNGTU_ID
        );
      } else if (kieu == 2) {
        if (!("CHUNGTU_ID" in this.Focus.chungTu)) {
          return;
        }
        DSChiTietVatTu = await this.getDSThietBi({
          chungTuId: this.Focus.chungTu.CHUNGTU_ID,
          kieu: 1,
          tag: this.params.tagFrm,
        });
        DSChiTietVatTu.forEach((element) => {
          if (!("KHO_ID" in element)) {
            element.KHO_ID = 0;
          }
        });
      }

      DSChiTietVatTu.forEach((e) => {
        e.SOLUONG = e.SOLUONG == "" || e.SOLUONG == null ? 0 : parseFloat(e.SOLUONG);
        e.DONGIA = e.DONGIA == "" || e.DONGIA == null ? 0 : parseFloat(e.DONGIA);
        e.TIEN = e.TIEN == "" || e.TIEN == null ? 0 : parseFloat(e.TIEN);
      });
      this.dataResponse.DSChiTietVatTu = DSChiTietVatTu;
    },
    async NAP_DS_THIETBI(kieu) {
      var DSThietBi = [];
      if (kieu == 1) {
        if (!("CHUNGTU_ID" in this.Focus.daChia)) {
          return;
        }
        DSThietBi = await this.getDSThietBiDaChia(this.Focus.daChia.CHUNGTU_ID);
      } else if (kieu == 2) {
        if (!("CHUNGTU_ID" in this.Focus.chungTu)) {
          return;
        }
        DSThietBi = await this.getDSThietBi({
          chungTuId: this.Focus.chungTu.CHUNGTU_ID,
          kieu: 2,
          tag: this.params.tagFrm,
        });
      }

      DSThietBi.forEach((e) => {
        e.SOLUONG =
          e.SOLUONG == "" || e.SOLUONG == null ? 0 : parseFloat(e.SOLUONG);
        e.DONGIA = e.DONGIA == "" || e.DONGIA == null ? 0 : parseFloat(e.DONGIA);
        e.TIEN = e.TIEN == "" || e.TIEN == null ? 0 : parseFloat(e.TIEN);
      });
      this.dataResponse.DSThietBi = DSThietBi;
    },
    async onClickDSChungTu() {
      this.loading(true);
      await this.NAP_DS_CHITIET(2);
      await this.NAP_DS_THIETBI(2);
      this.loading(false);
    },
    async onClickVTDaChia() {
      this.loading(true);
      var chungTuId =
        "CHUNGTU_ID" in this.Focus.chungTu ? this.Focus.chungTu.CHUNGTU_ID : 0;
      var DSXuatDaChia = await this.getDSXuatDaChia(chungTuId);
      DSXuatDaChia.forEach((e) => {
        e.NGAY_CT = e.NGAY_CT.replaceAll("-", "/");
        e.TIEN = e.TIEN == "" || e.TIEN == null ? 0 : parseFloat(e.TIEN);
        e.KHO_NHAN = e.KHO_NHAN == null || e.KHO_NHAN == "" ? "-" : e.KHO_NHAN;
        e.DONVI_N = e.DONVI_N == null || e.DONVI_N == "" ? "-" : e.DONVI_N;
        e.KHO_GIAO = e.KHO_GIAO == null || e.KHO_GIAO == "" ? "-" : e.KHO_GIAO;
        e.DONVI_G = e.DONVI_G == null || e.DONVI_G == "" ? "-" : e.DONVI_G;
      });
      this.dataResponse.DSXuatDaChia = DSXuatDaChia;
      this.focusXuat(0);
      this.loading(false);
    },
    async focusXuat(kieu) {
      if (kieu >= 0) {
        this.dataResponse.DSChiTietVatTu = [];
        this.dataResponse.DSThietBi = [];
        await this.NAP_DS_CHITIET(1);
        await this.NAP_DS_THIETBI(1);
      } else {
        this.dataResponse.DSChiTietVatTu = [];
        this.dataResponse.DSThietBi = [];
      }
    },
    CLEAR() {
      this.params.ghiChu = "";
      this.params.khoGiao = "";
      this.params.ngayCT = "";
      this.params.ngayXuat = "";
      this.params.soPhieu = "";
      this.params.lenhVB = "";
      this.params.ngayVB = new Date();
      this.dataResponse.DSChiTietVatTu = [];
      this.dataResponse.DSThietBi = [];
      this.dataResponse.DSFileCV = [];
    },
    gridDSChungTuCheckBox: function (dataItem) {
      this.params.listCheckboxChungTu = dataItem;
      console.log(
        this.params.listCheckboxChungTu,
        " = this.params.listCheckboxChungTu "
      );
    },
    gridDSXuatDaChiaCheckBox: function (dataItem) {
      console.log(dataItem, " = dataItem");
    },

    async onClickLayTT() {
      try {
        this.loading(true);
        await this.TIMKIEM();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async onClickTraPhieu() {
      if (!this.KIEMTRA_PHIEU("TRAPHIEU")) {
        return;
      }

      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn có muốn chắc chắn muốn trả chứng từ không ?", {
          title: "Xác nhận hành động",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        return;
      }

      this.$bvModal.show("popupNoiDungTra");
    },

    async onClickXacNhan() {
      try {
        this.loading(true);
        this.$bvModal.hide("popupNoiDungTra");

        var listChungTu = [];
        this.params.listCheckboxChungTu.forEach((element) => {
          listChungTu.push({ CHUNGTU_ID: element });
        });

        var ketQua = "";
        var k_p = "";

        if (
          this.thongTinNguoiDung.phanVungId == 28 ||
          this.thongTinNguoiDung.phanVungId == 67
        ) {
          if (this.params.DSQuyen.includes(2509) && this.xn_phanloai_nhapkho) {
            k_p = "3";
          } else if (
            this.params.DSQuyen.includes(2515) &&
            this.xn_phanloai_nhapkho
          ) {
            k_p = this.params.locPhieuId == "1" ? "3" : "5";
          } else {
            k_p = this.thongTinNguoiDung.phanVungId == 28 ? "3" : "5";
          }

          ketQua = await this.giaoPhieuHoanTraChungTu({
            kieuPhieu: this.params.loaiPhieuId,
            ttPhId: this.params.trangThaiId,
            khoDenId: this.params.khoId,
            tagFrm: this.params.tagFrm,
            kieuTh: k_p,
            ghiChu: this.params.noiDungTra,
            donViDenId: 0,
            ngayVb: this.f_DateToString(this.params.ngayVB),
            soVb: this.params.soPhieu,
            fileCv: this.dataResponse.txtFileCV,
            ds: listChungTu,
            dsCt: [{}],
            dsTbi: [{}],
            nguoiCn: this.thongTinNguoiDung.nguoiCN,
            mayCn: this.thongTinNguoiDung.mayCN,
            ipCn: this.thongTinNguoiDung.ipCN,
          });
        } else {
          ketQua = await this.giaoPhieuHoanTraChungTu({
            kieuPhieu: this.params.loaiPhieuId,
            ttPhId: this.params.trangThaiId,
            khoDenId: this.params.khoId,
            tagFrm: this.params.tagFrm,
            kieuTh: "3",
            ghiChu: this.params.noiDungTra,
            donViDenId: 0,
            ngayVb: this.f_DateToString(this.params.ngayVB),
            soVb: this.params.soPhieu,
            fileCv: this.dataResponse.txtFileCV,
            ds: listChungTu,
            dsCt: [{}],
            dsTbi: [{}],
            nguoiCn: this.thongTinNguoiDung.nguoiCN,
            mayCn: this.thongTinNguoiDung.mayCN,
            ipCn: this.thongTinNguoiDung.ipCN,
          });
        }
        if (ketQua == "1") {
          this.$toast.success("Thực hiện trả chứng từ thành công");
          this.TIMKIEM();
        } else {
          this.$toast.error(ketQua);
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onClickHuyBo() {
      try {
        if (this.params.loaiPhieuId == "1") {
          this.HUYPHIEU();
        } else {
          this.XOA_NHAPKHO();
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async HUYPHIEU() {
      if (!this.KIEMTRA_PHIEU("HUYPHIEU")) {
        return;
      }

      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn có muốn chắc chắn muốn hủy giao chứng từ không ?", {
          title: "Xác nhận hành động",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        return;
      }
      this.loading(true);
      var listChungTu = [];
      this.params.listCheckboxChungTu.forEach((element) => {
        listChungTu.push({ CHUNGTU_ID: element });
      });
      var ketQua = "";
      ketQua = await this.giaoPhieuHoanTraChungTu({
        kieuPhieu: this.params.loaiPhieuId,
        ttPhId: this.params.trangThaiId,
        khoDenId: 0,
        tagFrm: this.params.tagFrm,
        kieuTh: "0",
        ghiChu: "",
        donViDenId: 0,
        ngayVb: this.f_DateToString(this.params.ngayVB),
        soVb: this.params.soPhieu,
        fileCv: this.dataResponse.txtFileCV,
        ds: listChungTu,
        dsCt: [{}],
        dsTbi: [{}],
        nguoiCn: this.thongTinNguoiDung.nguoiCN,
        mayCn: this.thongTinNguoiDung.mayCN,
        ipCn: this.thongTinNguoiDung.ipCN,
      });

      if (ketQua == "1") {
        this.$toast.success("Thực hiện hủy giao phiếu thành công");
        this.TIMKIEM();
      } else {
        this.$toast.error(ketQua);
      }
      this.loading(false);
    },

    async onClickNhapKho() {
      if (!this.KIEMTRA_PHIEU("NHAPKHO")) {
        return;
      }
      var khoId = this.params.khoId;
      var dataKho = this.dataResponse.DSKho.filter(function (el) {
        return el.khoID == khoId;
      });
      var tenKho = dataKho.length > 0 ? dataKho[0].tenKho : "";
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm(
          "Bạn có muốn chắc chắn muốn nhập vào kho " + tenKho + " không ?",
          {
            title: "Xác nhận hành động",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        )
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        return;
      }

      if (
        this.thongTinNguoiDung.phanVungId == 28 ||
        this.thongTinNguoiDung.phanVungId == 67
      ) {
        for (const i in this.dataResponse.DSChiTietVatTu) {
          if (this.dataResponse.DSChiTietVatTu[i].KHO_ID == 0) {
            this.$toast.error("Bạn phải chọn đủ kho trước!");
            return;
          }
        }
      }

      var chungTuCheckBoxId = this.params.listCheckboxChungTu[0];
      var dataChungTuCheckBox = this.dataResponse.DSHoanTraCT.filter(function (
        el
      ) {
        return el.CHUNGTU_ID == chungTuCheckBoxId;
      });

      this.params.noiDungNhapKho =
        this.thongTinNguoiDung.phanVungId == 67
          ? this.Focus.chungTu.ND_GIAO
          : "Xuất hoàn trả vật tư số phiếu " + dataChungTuCheckBox[0].SOPHIEU;
      this.$bvModal.show("popupChuyenKho");
    },
    async onClickXacNhanNhapKho() {
      try {
        if (!this.KIEMTRA_PHIEU("XACNHAN")) {
          return;
        }
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(
            "Bạn có muốn chắc chắn muốn phân loại VT chứng từ không ?",
            {
              title: "Xác nhận hành động",
              centered: true,
              size: "md",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

        if (!isBoxConfirm) {
          return;
        }
        this.loading(true);
        var listCheckboxChungTu = this.params.listCheckboxChungTu;
        var DSChungTu = this.dataResponse.DSHoanTraCT.filter(function (el) {
          return listCheckboxChungTu.includes(el.CHUNGTU_ID);
        });

        if (this.dataResponse.DSChiTietVatTu.length == 0) {
          this.$toast.error(
            "Không có thông tin trả về khi thực hiện giao phiếu"
          );

          return;
        }

        var DSChungTuKT = [];
        this.dataResponse.DSChiTietVatTu.forEach((e) => {
          DSChungTuKT.push({
            CTCT_ID: e.CTCT_ID,
            LOAIKHO: e.LOAIKHO,
            MAKHOTD: e.MAKHOTD,
            KHO_ID: e.KHO_ID,
          });
        });

        var ketQua = await this.giaoPhieuHoanTraChungTu({
          kieuPhieu: this.params.loaiPhieuId,
          ttPhId: this.params.trangThaiId,
          khoDenId: 0,
          tagFrm: this.params.tagFrm,
          kieuTh: "4",
          ghiChu: "",
          donViDenId: this.params.donViGiaoNhanId,
          ngayVb: this.f_DateToString(this.params.ngayVB),
          soVb: this.params.lenhVB,
          fileCv: this.dataResponse.txtFileCV,
          ds: DSChungTu,
          dsCt: DSChungTuKT,
          dsTbi: [{}],
          nguoiCn: this.thongTinNguoiDung.nguoiCN,
          mayCn: this.thongTinNguoiDung.mayCN,
          ipCn: this.thongTinNguoiDung.ipCN,
        });
        if (ketQua == "1") {
          this.$toast.success("Thực hiện nhập kho thành công");
          await this.TIMKIEM();
        } else {
          this.$toast.error(ketQua);
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async onClickXacNhanChuyenKho() {
      try {
        this.$bvModal.hide("popupChuyenKho");

        this.loading(true);
        var listChungTu = [];
        var dem = 0;
        if (this.params.tagFrm == "2") {
          var listCheckboxChungTu = this.params.listCheckboxChungTu;
          var DSChungTu = this.dataResponse.DSHoanTraCT.filter(function (el) {
            return listCheckboxChungTu.includes(el.CHUNGTU_ID);
          });
          var DSChungTuKT = [];
          if (this.dataResponse.DSChiTietVatTu.length == 0) {
            this.$toast.error(
              "Không có thông tin trả về khi thực hiện giao phiếu"
            );
            this.loading(false);
            return;
          }
          for (const i in this.dataResponse.DSChiTietVatTu) {
            DSChungTuKT.push({
              CTCT_ID: this.dataResponse.DSChiTietVatTu[i].CTCT_ID,
              LOHANG: this.dataResponse.DSChiTietVatTu[i].LOHANG,
              VATTU_ID: this.dataResponse.DSChiTietVatTu[i].VATTU_ID,
              KHO_ID: this.dataResponse.DSChiTietVatTu[i].KHO_ID,
            });
          }
          for(let e of DSChungTu){
            e.NGAY_TEMP = this.f_DateTimeToString(this.params.ngayNhapKho)
            e.NOIDUNG = this.params.noiDungNhapKho
          }

          var ketQua = await this.giaoPhieuHoanTraChungTu({
            kieuPhieu: this.params.loaiPhieuId,
            ttPhId: this.params.trangThaiId,
            khoDenId: this.params.khoId,
            tagFrm: this.params.tagFrm,
            kieuTh: "0",
            ghiChu: this.thongTinNguoiDung.nhanVienId,
            donViDenId: this.thongTinNguoiDung.donViId,
            ngayVb: this.f_DateToString(this.params.ngayVB),
            soVb: this.params.soPhieu,
            fileCv: this.dataResponse.txtFileCV,
            ds: DSChungTu,
            dsCt: DSChungTuKT,
            dsTbi: [{}],
            nguoiCn: this.thongTinNguoiDung.nguoiCN,
            mayCn: this.thongTinNguoiDung.mayCN,
            ipCn: this.thongTinNguoiDung.ipCN,
          });
          
        } else {
          this.params.listCheckboxChungTu.forEach((element) => {
            listChungTu.push({ CHUNGTU_ID: element });
          });
          var ketQua = await this.giaoPhieuHoanTraChungTu({
            kieuPhieu: this.params.loaiPhieuId,
            ttPhId: this.params.trangThaiId,
            khoDenId: this.params.khoId,
            tagFrm: this.params.tagFrm,
            kieuTh: "0",
            ghiChu: "",
            donViDenId: this.thongTinNguoiDung.donViId,
            ngayVb: this.f_DateToString(this.params.ngayVB),
            soVb: this.params.soPhieu,
            fileCv: this.dataResponse.txtFileCV,
            ds: listChungTu,
            dsCt: [{}],
            dsTbi: [{}],
            nguoiCn: this.thongTinNguoiDung.nguoiCN,
            mayCn: this.thongTinNguoiDung.mayCN,
            ipCn: this.thongTinNguoiDung.ipCN,
          });
          console.log(ketQua, " = ketQua");
        }

        if (ketQua != "1") {
          this.$toast.error(ketQua);
          this.loading(false);
          return;
        } else {
          if (this.params.tagFrm != "2") {
            var listCheckboxChungTu = this.params.listCheckboxChungTu;
            var DSChungTu = this.dataResponse.DSHoanTraCT.filter(function (el) {
              return listCheckboxChungTu.includes(el.CHUNGTU_ID);
            });
            console.log(DSChungTu, " = DSChungTu");
            for (const i in DSChungTu) {
              var KTHanChungTu = await this.kiemTraHanChungTu({
                chungTuId: DSChungTu[i].CHUNGTU_ID,
                khoGiaoId: DSChungTu[i].KHO_GIAO_ID,
                khoNhanId: this.params.khoId,
                ngayCt: DSChungTu[i].NGAY_CT.slice(0, 10).replaceAll("-", "/"),
              });

              if (KTHanChungTu[0] != "1") {
                dem = dem + 1;
                continue;
              }

              var KTHoanThien = await this.kiemTraHoanThienChungTu(
                DSChungTu[i].CHUNGTU_ID
              );
              console.log(KTHoanThien, " = KTHoanThien");
              if (KTHoanThien != "1") {
                dem = dem + 1;
                continue;
              }

              var hoanThien = await this.hoanThienChungTu({
                chungTuId: DSChungTu[i].CHUNGTU_ID,
                nhanVienId: this.thongTinNguoiDung.nhanVienId,
                nguoiCN: this.thongTinNguoiDung.nguoiCN,
                mayCN: this.thongTinNguoiDung.mayCN,
                ipCN: this.thongTinNguoiDung.ipCN,
              });
              console.log(hoanThien, " = hoanThien");
              if (hoanThien != "1") {
                dem = dem + 1;
                continue;
              }
            }
          }
        }
        if (dem == 0 && ketQua == "1") {
          this.$toast.success("Thực hiện nhập kho thành công");
          this.TIMKIEM();
        } else {
          this.$toast.error("Có lỗi xảy ra không thể nhập kho!");
        }
        this.loading(false);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async XOA_NHAPKHO() {
      if (!this.KIEMTRA_PHIEU("XOANHAPKHO")) {
        return;
      }

      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn có muốn chắc chắn muốn hủy chứng từ không ?", {
          title: "Xác nhận hành động",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        return;
      }
      this.loading(true);

      var listCheckboxChungTu = this.params.listCheckboxChungTu;
      var DSChungTu = this.dataResponse.DSHoanTraCT.filter(function (el) {
        return listCheckboxChungTu.includes(el.CHUNGTU_ID);
      });
      console.log(DSChungTu, " = DSChungTu");
      var dem = 0;
      for (const i in DSChungTu) {
        var KTHanChungTu = await this.kiemTraHanChungTu({
          chungTuId: DSChungTu[i].CHUNGTU_ID,
          khoGiaoId: DSChungTu[i].KHO_GIAO_ID,
          khoNhanId: DSChungTu[i].KHO_NHAN_ID,
          ngayCt: DSChungTu[i].NGAY_CT.slice(0, 10).replaceAll("-", "/"),
        });

        if (KTHanChungTu[0] != "1") {
          dem = dem + 1;
          continue;
        }

        var KTXoaChungTu = await this.kiemTraDaXoaPhieu(
          DSChungTu[i].CHUNGTU_ID
        );
        console.log(KTXoaChungTu, " = KTXoaChungTu");
        if (KTXoaChungTu.length > 0 && KTXoaChungTu[0] != "1") {
          dem = dem + 1;
          continue;
        }

        var xoaPhieu = await this.xoaPhieuDaHoanThanh({
          chungTuId: DSChungTu[i].CHUNGTU_ID,
          nguoiCn: this.thongTinNguoiDung.nguoiCN,
          mayCn: this.thongTinNguoiDung.mayCN,
          ipCn: this.thongTinNguoiDung.ipCN,
        });
        console.log(xoaPhieu, " = xoaPhieu");
        if (xoaPhieu != "1") {
          dem = dem + 1;
          continue;
        }
      }

      if (dem == 0) {
        this.$toast.success("Thực hiện hủy chứng từ thành công");
        this.TIMKIEM();
      } else {
        this.$toast.error("Không thể hủy chứng từ");
      }
      this.loading(false);
    },
    async onClickGiaoPhieu() {
      try {
        if (!this.KIEMTRA_PHIEU("GIAOPHIEU")) {
          return;
        }

        if (
          this.thongTinNguoiDung.phanVungId == 28 ||
          this.thongTinNguoiDung.phanVungId == 67
        ) {
          if (this.params.lenhVB == "") {
            this.$toast.error("Chưa nhập số VB !");
            return;
          }
          if (this.dataResponse.DSFileCV.length == 0) {
            this.$toast.error("Chưa nhập file VB !");
            return;
          }
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có muốn chắc chắn muốn giao chứng từ không ?", {
            title: "Xác nhận hành động",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

        if (!isBoxConfirm) {
          return;
        }
        this.loading(true);
        var listChungTu = [];
        this.params.listCheckboxChungTu.forEach((element) => {
          listChungTu.push({ CHUNGTU_ID: element });
        });

        var ketQua = "";

        var dsFile = "";
        this.dataResponse.DSFileCV.forEach((element) => {
          dsFile += element.linkFile + "|";
        });
        console.log(dsFile, " = dsFile");
        ketQua = await this.giaoPhieuHoanTraChungTu({
          kieuPhieu: this.params.loaiPhieuId,
          ttPhId: this.params.trangThaiId,
          khoDenId: 0,
          tagFrm: this.params.tagFrm,
          kieuTh: "0",
          ghiChu: this.params.ghiChu,
          donViDenId: this.params.donViGiaoNhanId,
          ngayVb: this.f_DateToString(this.params.ngayVB),
          soVb: this.params.lenhVB,
          fileCv: dsFile,
          ds: listChungTu,
          dsCt: [{}],
          dsTbi: [{}],
          nguoiCn: this.thongTinNguoiDung.nguoiCN,
          mayCn: this.thongTinNguoiDung.mayCN,
          ipCn: this.thongTinNguoiDung.ipCN,
        });

        if (ketQua == "1") {
          this.$toast.success("Thực hiện giao phiếu thành công");
          this.TIMKIEM();
        } else {
          this.$toast.error(ketQua);
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    KIEMTRA_PHIEU(button) {
      if (this.dataResponse.DSHoanTraCT.length == 0) {
        this.$toast.error("Không có danh sách phiếu để thực hiện");
        return false;
      }

      if (this.params.listCheckboxChungTu.length == 0) {
        this.$toast.error("Bạn chưa chọn phiếu để thực hiện");
        return false;
      }

      if (this.params.loaiPhieuId == "2") {
        if (this.params.khoId == "" || this.params.khoId == "0") {
          this.$toast.error("Bạn chưa chọn kho đến");
          return false;
        }
      } else {
        if (this.params.tagFrm == "3") {
          if (
            (this.dataResponse.DSDonViNhan == "" ||
              this.dataResponse.DSDonViNhan == "-1") &&
            this.params.trangThaiId != "-2" &&
            button == "GIAOPHIEU"
          ) {
            this.$toast.error("Bạn chưa chọn đơn vị nhận");
            return false;
          }
        }
      }
      return true;
    },
    async loadGiaoNhanChungTu() {
      var DSThamSoMacDinh = await this.getThamSoMacDinh(0);

      if (DSThamSoMacDinh.length > 0) {
        var DSThamSo = DSThamSoMacDinh.filter(function (el) {
          return el.maTS == "XN_PHANLOAI_NHAPKHO_HOANTRA";
        });

        if (DSThamSo.length > 0) {
          if (DSThamSo[0].tenTS.trim() == "1") {
            this.xn_phanloai_nhapkho = true;
          }
        }
      }

      this.params.DSQuyen = await this.getDSQuyen(
        this.thongTinNguoiDung.nguoiDungId
      );

      if (this.params.tagFrm == "2") {
        this.Text.Kho = "Kho chung";
        this.dataResponse.DSXuatDaChia = [];
        this.Enabled.locPhieu = !this.params.DSQuyen.includes(2515)
          ? false
          : true;
      }
      if (
        this.thongTinNguoiDung.phanVungId != 28 &&
        this.thongTinNguoiDung.phanVungId != 67
      ) {
        this.Enabled.tabDSDaChia = false;
        this.Visible.colKhoDen = false;
        this.Visible.colLoaiKho = false;
        this.Visible.colMaKho = false;
      }

      await this.NAP_DS_COMBO();

      // await this.TIMKIEM() // Rem tìm kiếm do trùng sự kiện thì change đơn vị
      this.firstLoad = false;
    },

    async TIMKIEM() {
      var vtu = this.Checkbox.isCheckNgay
        ? this.f_DateToString(this.params.tuNgay)
        : "0";
      var vden = this.Checkbox.isCheckNgay
        ? this.f_DateToString(this.params.denNgay)
        : "0";
      var vdvi = this.params.donViGiaoNhanId;
      var vtt = "";
      if (
        this.params.tagFrm == "2" &&
        this.params.DSQuyen.includes(2509) &&
        this.xn_phanloai_nhapkho
      ) {
        vtt = this.params.trangThaiId == "2" ? "1" : this.params.trangThaiId;
      } else if (
        this.params.tagFrm == "2" &&
        this.params.DSQuyen.includes(2515) &&
        this.xn_phanloai_nhapkho
      ) {
        if (this.params.locPhieuId == "1") {
          vtt = this.params.trangThaiId == "2" ? "1" : this.params.trangThaiId;
        } else {
          vtt = this.params.trangThaiId;
        }
      } else {
        vtt = this.params.trangThaiId;
      }
      var vlph = this.params.loaiPhieuId;
      var ds = [
        {
          vtu: vtu,
          vden: vden,
          vdvi: vdvi,
          vtt: vtt,
          vlph: vlph,
        },
      ];

      var DSHoanTraCT = await this.getDSHoanTraChungTu({
        ds: ds,
        nguoiCn: this.thongTinNguoiDung.nguoiCN, //this.thongTinNguoiDung.nguoiCN,
        kieuGoi: this.params.tagFrm,
      });

      if (this.params.tagFrm == "2") {
        if (this.params.DSQuyen.includes(2509) && this.xn_phanloai_nhapkho) {
          var ttph =
            this.params.trangThaiId == "1"
              ? "10"
              : this.params.trangThaiId == "3"
              ? "6"
              : "2";
          DSHoanTraCT = DSHoanTraCT.filter(function (el) {
            return el.TTPH_ID == ttph;
          });
        } else if (
          this.params.DSQuyen.includes(2515) &&
          this.xn_phanloai_nhapkho
        ) {
          if (this.params.locPhieuId == "1") {
            var ttph =
              this.params.trangThaiId == "1"
                ? "10"
                : this.params.trangThaiId == "3"
                ? "6"
                : "2";
            DSHoanTraCT = DSHoanTraCT.filter(function (el) {
              return el.TTPH_ID == ttph;
            });
          } else {
            DSHoanTraCT = DSHoanTraCT.filter(function (el) {
              return el.TTPH_ID != "6" && el.TTPH_ID != "10";
            });
          }
        } else {
          DSHoanTraCT = DSHoanTraCT.filter(function (el) {
            return el.TTPH_ID != "6" && el.TTPH_ID != "10";
          });
        }
      } else {
        if (this.params.trangThaiId != 2) {
          DSHoanTraCT = DSHoanTraCT.filter(function (el) {
            return el.TTPH_ID != "10";
          });
        }
      }
      DSHoanTraCT.forEach((e) => {
        e.NGAY_VB = e.NGAY_VB.replaceAll("-", "/").slice(0, 10);
        e.NGAYGUI_TT = e.NGAYGUI_TT.replaceAll("-", "/").slice(0, 10);
        e.NGAY_CT = e.NGAY_CT.replaceAll("-", "/");
        e.KHO_NHAN = e.KHO_NHAN == null || e.KHO_NHAN == "" ? "-" : e.KHO_NHAN;
        e.DONVI_N = e.DONVI_N == null || e.DONVI_N == "" ? "-" : e.DONVI_N;
        e.KHO_GIAO = e.KHO_GIAO == null || e.KHO_GIAO == "" ? "-" : e.KHO_GIAO;
        e.DONVI_G = e.DONVI_G == null || e.DONVI_G == "" ? "-" : e.DONVI_G;
        e.TIEN = e.TIEN == "" || e.TIEN == null ? 0 : parseInt(e.TIEN);
      });
      this.dataResponse.DSHoanTraCT = DSHoanTraCT;
      if (this.dataResponse.DSHoanTraCT.length == 0) {
        this.dataResponse.DSChiTietVatTu = [];
        this.dataResponse.DSXuatDaChia = [];
        this.dataResponse.DSThietBi = [];
        this.Focus.chungTu = {};
      }
    },

    async NAP_DS_COMBO() {
      this.params.trangThaiId = 1;

      this.dataResponse.DSLoaiKho = await this.getDSLoaiKho();

      // this.dataResponse.DSKhoTTVT = await this.getDSKhoTTVT()

      this.dataResponse.DSKhoDen = await this.getDSKhoDen(
        this.thongTinNguoiDung.nhanVienId
      );
      console.log(this.params.tagFrm, " = this.params.tagFrm");
      if (this.params.tagFrm == "2" || this.params.tagFrm == "4") {
        this.params.loaiPhieuId = 2;
        if (this.params.DSQuyen.includes(2515)) {
          this.dataResponse.DSLocPhieu = [
            {
              ID: 1,
              TRANGTHAI: "Xác nhận",
            },
            {
              ID: 2,
              TRANGTHAI: "chuyển kho",
            },
          ];
          this.params.locPhieuId = 1;
        }
      } else if (this.params.tagFrm == "1" || this.params.tagFrm == "3") {
        this.params.loaiPhieuId = 1;
      }

      this.params.tuNgay = new Date();
      this.params.denNgay = new Date();
      var duration = 15; //In Days
      this.params.tuNgay.setTime(
        this.params.tuNgay.getTime() - duration * 24 * 60 * 60 * 1000
      );

      this.HT_NUT();

      if (this.params.tagFrm == "2") {
        this.header.title = "NHẬN CHỨNG TỪ HOÀN TRẢ";
        this.header.list[2].name = "Nhận chứng từ hoàn trả";
        this.dataResponse.DSKho = await this.getDSKhoNhanChungTuHCM();
        if (this.dataResponse.DSKho.length > 0) {
          this.params.khoId = this.dataResponse.DSKho[0].khoID;
        }
      } else if (this.params.tagFrm == "1") {
        this.header.title = "GIAO/NHẬN CHỨNG TỪ HOÀN TRẢ";
        this.header.list[2].name = "Giao chứng từ hoàn trả";
        this.dataResponse.DSKho = await this.getDSKhoGiaoChungTuHCM(
          this.thongTinNguoiDung.nhanVienId
        );
        if (this.dataResponse.DSKho.length > 0) {
          this.params.khoId = this.dataResponse.DSKho[0].khoID;
        }
      } else if (this.params.tagFrm == "3") {
        this.header.title = "DUYỆT CHỨNG TỪ HOÀN TRẢ";
        this.header.list[1].name = "Duyệt chứng từ hoàn trả";
        this.dataResponse.DSDonViNhan = await this.getDSDonViDuyet();
        if (this.dataResponse.DSDonViNhan.length > 0) {
          this.params.donViNhanId = this.dataResponse.DSDonViNhan[0].donViId;
        }
      } else if (this.params.tagFrm == "4") {
        this.header.title = "NHẬN CHỨNG TỪ HOÀN TRẢ";
        this.header.list[1].name = "Nhận chứng từ hoàn trả";
        this.dataResponse.DSKho = await this.getDSKhoNhanChungTuHaNoi(
          this.thongTinNguoiDung.nhanVienId
        );
        if (this.dataResponse.DSKho.length > 0) {
          this.params.khoId = this.dataResponse.DSKho[0].khoID;
        }
      }
      this.dataResponse.DSDonViGiaoNhan = await this.getDSDonViGiaoNhanChungTu({
        nguoiDungId: this.thongTinNguoiDung.nguoiDungId,
        donViId: this.thongTinNguoiDung.donViId,
        donViDlId: this.$root.context.user.getProperty("donvi_dl_id"),
      });

      if (this.dataResponse.DSDonViGiaoNhan.length > 0) {
        this.params.donViGiaoNhanId =
          this.dataResponse.DSDonViGiaoNhan[0].DONVI_ID;
      }
    },

    async onChangeDonViGiao() {
      try {
        this.loading(true);
        await this.TIMKIEM();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onChangeTrangThai() {
      try {
        this.loading(true);
        await this.TIMKIEM();
        this.HT_NUT();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onChangeLoaiPhieu() {
      try {
        this.loading(true);
        await this.TIMKIEM();
        this.HT_NUT();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onChangeLocPhieu() {
      try {
        this.loading(true);
        await this.TIMKIEM();
        this.HT_NUT();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    HT_NUT() {
      this.Visible.giaoPhieu = false;
      this.Visible.nhapKho = false;
      this.Visible.traLai = false;
      this.Visible.huyBo = false;
      if (this.params.loaiPhieuId == "1") {
        this.Visible.giaoPhieu = true;
      } else {
        if (this.params.DSQuyen.includes(2509) && this.xn_phanloai_nhapkho) {
          this.Visible.xacNhan = this.params.trangThaiId != 2;
          this.Enabled.colKhoDen = true;
          this.Enabled.colLoaiKho = true;
          this.Enabled.colMaKho = true;
        } else {
          this.Visible.nhapKho = true;
          this.Visible.traLai = false;
          if (this.thongTinNguoiDung.phanVungId == 67) {
            this.Enabled.colKhoDen = false;
          } else {
            this.Enabled.colKhoDen = true;
          }
          this.Enabled.colLoaiKho = true;
          this.Enabled.colMaKho = true;
        }
      }

      if (this.params.trangThaiId == "2") {
        this.Visible.giaoPhieu = false;
        this.Visible.nhapKho = false;
        this.Visible.huyBo = !(
          this.params.tagFrm == "2" &&
          this.params.DSQuyen.includes(2509) &&
          this.xn_phanloai_nhapkho
        );

        if (this.params.DSQuyen.includes(2515)) {
          this.Visible.xacNhan = false;
        }
      } else if (
        this.params.trangThaiId == "1" ||
        this.params.trangThaiId == "3"
      ) {
        if (this.params.tagFrm != "1") {
          this.Visible.traLai = true;
        }
        if (this.params.tagFrm != "1" && this.params.DSQuyen.includes(2515)) {
          if (this.params.locPhieuId == "1") {
            this.Visible.xacNhan = true;
            this.Visible.nhapKho = false;
            this.Enabled.colKhoDen = true;
            this.Enabled.colLoaiKho = true;
            this.Enabled.colMaKho = true;
          } else {
            this.Visible.xacNhan = false;
            this.Visible.nhapKho = true;
            this.Enabled.colKhoDen = false;
            this.Enabled.colLoaiKho = false;
            this.Enabled.colMaKho = false;
          }
        }
      }
    },
    onClickHiddenBox() {
      this.classIconHiddenBox = this.Visible.boxSearch
        ? "icon fa fa-angle-down"
        : "icon fa fa-angle-up";
      this.Visible.boxSearch = this.Visible.boxSearch ? false : true;
    },
    async onDownloadFileHandler(item) {
      // var checkFile = await this.checkFileExist({ fileSource: item.linkFile });
      // if (checkFile.message == "Success") {
      //   if (!checkFile.data.data) {
      //     this.$toast.error("File không còn tồn tại!");
      //     return;
      //   }
      // }
      // await this.onClickDownFile(item.linkFile);

      var checkFile = await this.checkFileExist({fileSource : item.linkFile})        
      var linkFile = ""
      if(checkFile.message == "Success" && checkFile.data.data){
        let getLink = await this.getLinkFile({fileSource : item.linkFile}) 
        linkFile = getLink.data.name                 
      }else{  
        linkFile  = this.URL + item.linkFile     
      }
      const downloadLink = document.createElement("a");      
      downloadLink.href = linkFile;      
      downloadLink.download = item.nameFile;    
      downloadLink.target = "_blank";
      downloadLink.click();   
    },
    

    async onClickDownFile(value) {
      if (value) {
        const data = await this.downloadFile(value);
        if (data) {
          const filePath = value.split("/");
          const linkSource = `data:application/octet-stream;base64,${data.data.body.data}`;
          const downloadLink = document.createElement("a");
          const fileName = filePath[filePath.length - 1];
          downloadLink.href = linkSource;
          downloadLink.download = fileName;
          downloadLink.click();
        }
      }
    },

    async onClickInPhieu(){
      
      // let test = await this.getParamsBaoCao("HTVTTT")
      // console.log(test , " = test")     
      // return 
      try{
        if (this.params.listCheckboxChungTu.toString() == "") {
          this.$toast.error("Không có danh sách phiếu để thực hiện");
          return;
        }
        this.loading(true);
        let ds_tieuchi = [
          { 
            name: 'P_PHANVUNG_ID',
            value: this.$auth.getPhanVungID()
          },
          {
            name: 'p_chungtu_id',
            value: this.params.listCheckboxChungTu.toString()
          },          
        ]       
     
        const baseUrl = process.env.API;
        await this.axios({
          url:
            `${baseUrl}/web-qlvt/api/in-phieu/getPhieuIn?extend=pdf`,
          method: "POST",
          responseType: "blob",
          data: {
            ma_bc: 'HTVTTT',
            ds_tieuchi: ds_tieuchi
          }                
        }).then((response) => {
          previewPrint(response.data);
        });
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    // async onClickInPhieu() {
    //   try {
    //     if (this.params.listCheckboxChungTu.toString() == "") {
    //       this.$toast.error("Không có danh sách phiếu để thực hiện");
    //       return;
    //     }
    //     this.loading(true);
    //     const baseUrl = process.env.API;
    //     await this.axios({
    //       url: `${baseUrl}/web-qlvt/api/bao-cao/de-nghi-hoan-tra-vat-tu?extend=pdf`,
    //       method: "POST",
    //       responseType: "blob",
    //       data: {
    //         chungTuId: this.params.listCheckboxChungTu.toString(),
    //       },
    //     }).then((response) => {
    //       previewPrint(response.data);
    //     });
    //     this.loading(false);
    //   } catch (e) {
    //     this.$toast.error(e.data.message);
    //   } finally {
    //     this.loading(false);
    //   }
    // },
    onChangeKhoDenHandler(value) {      
      this.dataResponse.DSChiTietVatTu.forEach((element) => {
        if (element.CTCT_ID == value.CTCT_ID && element.LOHANG == value.LOHANG) {
          element.KHO_ID = value.KHO_ID;
        }
      });
    },
    onChangeLoaiKhoHandler(value) {
      this.dataResponse.DSChiTietVatTu.forEach((element) => {
        if (element.CTCT_ID == value.CTCT_ID && element.LOHANG == value.LOHANG) {
          element.LOAIKHO = value.LOAIKHO;
        }
      });
      this.$refs.gridDSChiTiet.grid.refreshColumns();
    },
    onChangeMaKhoKTHandler(value) {
      this.dataResponse.DSChiTietVatTu.forEach((element) => {
        if (element.CTCT_ID == value.CTCT_ID && element.LOHANG == value.LOHANG) {
          element.MAKHOTD = value.MAKHOTD;
        }
      });
    },
  },
  destroyed() {
    EventBus.$off("onDownloadFile", this.onDownloadFileHandler);
    EventBus.$off("onChangeKhoDen", this.onChangeKhoDenHandler);
    EventBus.$off("onChangeLoaiKho", this.onChangeLoaiKhoHandler);
    EventBus.$off("onChangeMaKhoKT", this.onChangeMaKhoKTHandler);
  },
  created: async function () {
    try {
      this.loading(true);
      this.params.tagFrm = this.$route.query.tagFrm != "2" ? "1" : "2";
      EventBus.$on("onDownloadFile", this.onDownloadFileHandler);
      EventBus.$on("onChangeKhoDen", this.onChangeKhoDenHandler);
      EventBus.$on("onChangeLoaiKho", this.onChangeLoaiKhoHandler);
      EventBus.$on("onChangeMaKhoKT", this.onChangeMaKhoKTHandler);
      await this.loadGiaoNhanChungTu();
    } catch (e) {
      this.$toast.error(e.data.message);
    } finally {
      this.loading(false);
    }
  },
};
</script>
