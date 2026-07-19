<template src='./NghiemThuDonHang.html'></template>
<style scoped src='./NghiemThuDonHang.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/nghiemthudonhang";
import moment from "moment";
import Vue from "vue";
import {
  GridPlugin,
  Group,
  Page,
  Filter,
  Sort,
  Resize,
} from "@syncfusion/ej2-vue-grids";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import { currency } from "@/filters/currency";
import EventBus from "@/utils/eventBus";

Vue.use(DatePickerPlugin);

Vue.use(GridPlugin);

export default {
  components: { breadcrumb },
  name: "NghiemThuDonHang",

  data() {
    return {
      header: {
        title: "NGHIỆM THU ĐƠN HÀNG",

        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Nghiệm thu đơn hàng",
            link: { name: "Ui.buttons" },
          },
        ],
      },

      params: {
        donHangId: "",
        maDonHang: "",
        hdms_id: "",
        ngayNhanHang: "",
        ngayNhapKho: "",
        soHoaDon: "",
        trangThaiDonHang: 0,
        dsQuyen: [],
        ngayNghiemThuGoc: "",
        ngayNghiemThu: "",
        trangThaiDHHT: "",
        ngayNTBanGiao: null,
        fileCV: "",
        isHanBH: false,
        isNgay: true,
        ngayBH: "",
        textTime: "Số ngày",
        valueTime: "",
      },
      rowCheckedCTDH: [],
      checkAllVT: false,
      showPopupHanBH: "none",
      classNgay: "btn-main",
      classThang: "btn-second",
      dataResponse: {
        DSVatTu: [],
        DSFileCV: [],
      },
      thongTinNguoiDung: {
        ngayCN: new Date(), //this.$auth.ngayCN
        nguoiCN: this.$root.token.getUserName(), //this.$auth.MaNguoiDung
        mayCN: "DESKTOP-IDG67LD", //this.$auth.MAYCN
        ipCN: "10.59.90.128", //this.$auth.IPCN
        nhanVienId: this.$root.token.getNhanVienID(),
        maTinh: this.$root.token.getMaTinh(),
      },
      ghi: false,
      boqua_ngay_nghiemthu: false,
      showFile: "none",
      Enabled: {
        Xoa: true,
        nghiemThu: true,
      },
      NGHIEM_THU_BAN_GIAO: true, // Debug nghiệm thu bàn giao
      editSettings: { allowEditing: true, mode: "Batch" },
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        waterMark: "Chọn ngày",
      },
      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,
      },
      animationSettings: { effect: "Zoom" },
      target: ".main-wrapper",
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
      columnsCheckbox: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.rowCheckedCTDH"
                                    :value="data.CTDH_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      columnsCheckboxAll: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllVT" class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent.$parent;
              },
            },
          },
        };
      },

      downloadTemplate: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>
                          <button class="btn btn-success btn-sm" @click="onDownloadFile" title="Tải xuống">
                            <span class="-ap icon-download"></span>
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
      deleteTemplate: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div>
                          <button class="btn btn-danger lh14 pad3" @click="onDeleteFile" title="Xóa">
                            <span class="-ap icon-close"></span>
                          </button>
                      </div>`,
            data() {
              return { data: {} };
            },
            methods: {
              onDeleteFile() {                
                EventBus.$emit("onDeleteFile", this.data);
              },
            },
          }),
        };
      },
    };
  },

  computed: {
    ...mapState("nghiemthudonhang", statePropertyName),
    ...mapGetters("nghiemthudonhang", getterName),
    PTAggregatesColumns() {
      return [
        {
          field: "SOLUONG",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalSL,
        },
        {
          field: "SL_NT",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalSLNT,
        },
        {
          field: "SL_HONG",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalSLHONG,
        },
        {
          field: "SL_CHUAN",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalSLCHUAN,
        },
      ];
    },
  },
  watch: {
    rowCheckedCTDH: function (value) {
      this.rowCheckedCTDH = value;
      this.dataResponse.DSVatTu.forEach((e) => {
        if (e.SL_NT == 0 && value.includes(e.CTDH_ID)) {
          e.SL_NT = e.SOLUONG;
          e.SL_CHUAN = e.SOLUONG;
        }else if(!value.includes(e.CTDH_ID)){
          e.SL_NT = 0
          e.SL_CHUAN = 0
        }
        
      });
      this.$refs.gridCTDonHang.grid.refreshColumns();
    },
    checkAllVT: function (value) {
      if (value) {
        for (const e of this.dataResponse.DSVatTu) {
          if(!this.rowCheckedCTDH.includes(e.CTDH_ID)){
            this.rowCheckedCTDH.push(e.CTDH_ID);        
            e.SL_NT = e.SOLUONG;
            e.SL_CHUAN = e.SOLUONG;
          }          
        }
      } else {
        for (const e of this.dataResponse.DSVatTu) {
          e.SL_NT = 0;
          e.SL_CHUAN = 0;
        }
        this.rowCheckedCTDH = [];
      }
      this.$refs.gridCTDonHang.grid.refreshColumns();
    },
  },
  mounted: async function () {
    document.addEventListener("click", (event) => {
      if (typeof this.$refs["title-han-bh"] == "undefined") {
        return;
      }
      if (this.$refs["title-han-bh"].contains(event.target)) {
        this.showPopupHanBH = this.showPopupHanBH == "none" ? "block" : "none";
      } else if (this.$refs["table-han-bh"].contains(event.target)) {
        if (event.target.tagName.toLowerCase() == "td") {
          this.showPopupHanBH = "none";
        } else {
          this.showPopupHanBH = "block";
        }
      } else {
        this.showPopupHanBH = "none";
      }

      if (this.showPopupHanBH == "block") {
        this.popper = new Popper(
          this.$refs["title-han-bh"],
          this.$refs["table-han-bh"],
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

  methods: {
    ...mapActions("nghiemthudonhang", actionName),
    ...mapMutations("nghiemthudonhang", mutationName),

    async onClickNgay() {
      this.params.isNgay = true;
      this.params.textTime = "Số ngày";
      await this.onChangeTime();
    },
    async onClickThang() {
      this.params.isNgay = false;
      this.params.textTime = "Số tháng";
      await this.onChangeTime();
    },
    onChangeTime() {
      if (this.params.valueTime < 0) {
        this.$toast.error("Chỉ được nhập số  >= 0");
      }
      if (
        this.params.ngayNghiemThu != null &&
        this.params.ngayNghiemThu != "" &&
        this.params.valueTime != ""
      ) {
        var ngayNghiemThu = new Date(this.params.ngayNghiemThu);
        this.params.ngayBH = this.params.isNgay
          ? new Date(
              ngayNghiemThu.getTime() +
                this.params.valueTime * 24 * 60 * 60 * 1000
            )
          : ngayNghiemThu.setMonth(
              ngayNghiemThu.getMonth() + parseInt(this.params.valueTime)
            );
        this.params.ngayBH = this.f_DateToString(this.params.ngayBH);
      } else {
        this.params.ngayBH = "";
      }
    },
    onChangeCheckboxHanBH() {
      if (this.params.ngayBH == "" || this.rowCheckedCTDH.length == 0) return;
      this.dataResponse.DSVatTu.forEach((e) => {
        if (this.rowCheckedCTDH.includes(e.CTDH_ID)) {
          e.HAN_BH = this.params.isHanBH ? this.params.ngayBH : "";
        }
      });
      this.$refs.gridCTDonHang.grid.refreshColumns();
    },
    removeElementInArray(array,element){
      const index = array.indexOf(element);
      if (index > -1) {
        array.splice(index, 1);
      }
      return array
    },
    editGridDonHang(arg) {
      let gridCTDonHang = this.$refs.gridCTDonHang;
      let changes = gridCTDonHang.getBatchChanges();

      if (changes.changedRecords.length == 0) {
        return;
      }
      if (arg.columnName == "SL_NT") {
        console.log(changes.changedRecords[0].SL_NT )
        if (changes.changedRecords[0].SL_NT > changes.changedRecords[0].SOLUONG){         
          changes.changedRecords[0].SL_NT = changes.changedRecords[0].SOLUONG;
          changes.changedRecords[0].SL_CHUAN =
            changes.changedRecords[0].SL_NT - changes.changedRecords[0].SL_HONG;
          this.$toast.error(
            "Yêu cầu: Số lượng nghiệm thu <= Số lượng đơn hàng"
          );
          if(!this.rowCheckedCTDH.includes(changes.changedRecords[0].CTDH_ID)){
            this.rowCheckedCTDH.push(changes.changedRecords[0].CTDH_ID)
          }
          
        }  else if (changes.changedRecords[0].SL_NT <= 0) {       
          changes.changedRecords[0].SL_NT = 0;
          changes.changedRecords[0].SL_HONG = 0;
          changes.changedRecords[0].SL_CHUAN = 0;
          this.rowCheckedCTDH = this.removeElementInArray(this.rowCheckedCTDH , changes.changedRecords[0].CTDH_ID)
        } else {         
          changes.changedRecords[0].SL_CHUAN = changes.changedRecords[0].SL_NT - changes.changedRecords[0].SL_HONG;
          if(!this.rowCheckedCTDH.includes(changes.changedRecords[0].CTDH_ID)){
            this.rowCheckedCTDH.push(changes.changedRecords[0].CTDH_ID)
          }
        }
      } else if (arg.columnName == "SL_HONG") {
        if (
          changes.changedRecords[0].SL_HONG > changes.changedRecords[0].SL_NT
        ) {
          changes.changedRecords[0].SL_HONG = 0;
          changes.changedRecords[0].SL_CHUAN =
            changes.changedRecords[0].SL_NT - changes.changedRecords[0].SL_HONG;
          this.$toast.error(
            "số lượng hỏng không được lớn hơn số lượng nghiệm thu"
          );
        } else if (changes.changedRecords[0].SL_HONG < 0) {
          changes.changedRecords[0].SL_HONG = 0;
          changes.changedRecords[0].SL_CHUAN = changes.changedRecords[0].SL_NT - changes.changedRecords[0].SL_HONG;
          this.$toast.error("Số lượng hỏng không được âm!");
        } else {
          changes.changedRecords[0].SL_CHUAN = changes.changedRecords[0].SL_NT - changes.changedRecords[0].SL_HONG;
        }
      } else if (arg.columnName == "HAN_BH") {
        console.log(changes.changedRecords[0].HAN_BH, " = HAN_BH");
        if (this.dateIsValid(changes.changedRecords[0].HAN_BH) == false) {
          this.$toast.error("Hạn bảo hành không đúng định dạng DD/MM/YYYY!");
          changes.changedRecords[0].HAN_BH = "";
        } else {
          gridCTDonHang.batchUpdate(changes);
          return;
        }
      }
      gridCTDonHang.batchUpdate(changes);
      this.$refs.gridCTDonHang.grid.refreshColumns();
    },
    dateIsValid(dateStr) {
      const regex = /^\d{2}\/\d{2}\/\d{4}$/;

      if (dateStr.match(regex) === null) {
        return false;
      }

      const [day, month, year] = dateStr.split("/");

      // 👇️ format Date string as `yyyy-mm-dd`
      const isoFormattedStr = `${year}-${month}-${day}`;

      const date = new Date(isoFormattedStr);

      const timestamp = date.getTime();

      if (typeof timestamp !== "number" || Number.isNaN(timestamp)) {
        return false;
      }

      return date.toISOString().startsWith(isoFormattedStr);
    },

    getTotalSL() {
      var Tong = 0;
      this.dataResponse.DSVatTu.forEach((e) => {
        e.SOLUONG =
          e.SOLUONG == "" || e.SOLUONG == null ? 0 : parseFloat(e.SOLUONG);
        Tong += e.SOLUONG;
      });
      return currency(Tong ,"",3);
    },
    getTotalSLNT() {
      var Tong = 0;
      this.dataResponse.DSVatTu.forEach((e) => {
        e.SL_NT = e.SL_NT == "" || e.SL_NT == null ? 0 : parseFloat(e.SL_NT);
        Tong += e.SL_NT;
      });
      return currency(Tong,"",3);
    },
    getTotalSLHONG() {
      var Tong = 0;
      this.dataResponse.DSVatTu.forEach((e) => {
        e.SL_HONG =
          e.SL_HONG == "" || e.SL_HONG == null ? 0 : parseFloat(e.SL_HONG);
        Tong += e.SL_HONG;
      });
      return currency(Tong,"",3);
    },
    getTotalSLCHUAN() {
      var Tong = 0;
      this.dataResponse.DSVatTu.forEach((e) => {
        e.SL_CHUAN =
          e.SL_CHUAN == "" || e.SL_CHUAN == null ? 0 : parseFloat(e.SL_CHUAN);
        Tong += e.SL_CHUAN;
      });
      return currency(Tong,"",3);
    },

    async Load_cbo() {
      this.clear();
    },
    async Load_DS_VATTU() {
      var DSDonViTinh = await this.getDSDonViTinh();
      var objDonViTinh = {};
      DSDonViTinh.forEach((e) => {
        objDonViTinh[e.dviTinhId] = e.dviTinh;
      });
      var DSVatTu = await this.getDSVatTuTuDonHang(this.params.donHangId);
      DSVatTu.forEach((e) => {
        e.SOLUONG =
          e.SOLUONG == "" || e.SOLUONG == null ? 0 : parseFloat(e.SOLUONG);
        e.SL_NT = e.SL_NT == "" || e.SL_NT == null ? 0 : parseFloat(e.SL_NT);
        e.SL_HONG =
          e.SL_HONG == "" || e.SL_HONG == null ? 0 : parseFloat(e.SL_HONG);
        e.SL_CHUAN =
          e.SL_CHUAN == "" || e.SL_CHUAN == null ? 0 : parseFloat(e.SL_CHUAN);
        e.HAN_BH = e.HAN_BH.replaceAll("-", "/").slice(0, 10);
        e.DVITINH = objDonViTinh[e.DVITINH_ID];
      });
      this.dataResponse.DSVatTu = DSVatTu;
      this.rowCheckedCTDH = []; // reset checkbox
      let DateTimeNTBG = await this.getNgayNghiemThuBanGiao(this.params.donHangId)   
      this.params.ngayNTBanGiao = DateTimeNTBG == "" || DateTimeNTBG == null ? null : DateTimeNTBG.split(" ")[0] // không lấy giờ
      await this.getDSFile();
    },
    async getDSFile() {
      this.dataResponse.DSFileCV = [];
      var DSFileCV = await this.getDSFileNghiemThu(this.params.donHangId);    
      if (DSFileCV != null && DSFileCV.length > 0 && DSFileCV[0] != null && DSFileCV[0] != "") {              
        var listFileCV = DSFileCV[0].split(",");
       
        listFileCV.forEach((element) => {
          if (element != "") {
            var urlSplit = element.split("/");        
            this.dataResponse.DSFileCV.push({
              linkFile: element,
              nameFile: urlSplit[urlSplit.length - 1],
            });            
          }
        });              
      }
      console.log(this.dataResponse.DSFileCV  , " = DSFileCV")
      this.$refs.gridFile.grid.refreshColumns()  
    },
    async onClickLamMoi() {
      this.clear();
    },

    async onClickXoa() {
      try {
        var KTDonHangDaCoChungTu = await this.KTDonHangDaCoChungTu(
          this.params.donHangId
        );
        if (KTDonHangDaCoChungTu[0] > 0) {
          this.$toast.error("Đơn hàng đã có chứng từ không thể xóa");
          return;
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có muốn xóa dữ liệu không ?", {
            title: "Thông báo",
            centered: true,
            size: "md",
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

        await this.xoaNghiemThu(this.params.donHangId);
        await this.capNhatHopDong({
          hdmsId: this.params.hdms_id,
          kieu: this.params.donHangId,
        });
        await this.capNhatTTNghiemThuDonHang({
          hdmsId: this.params.hdms_id,
          donHangId: this.params.donHangId,
          nhanVienId: this.thongTinNguoiDung.nhanVienId,
        });
        this.$toast.success("Xóa phiếu nghiệm thu thành công");
        this.ghi = true;
        // đóng popup
        await this.Close();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async clear() {
      if (this.params.ngayNghiemThuGoc != "1753-01-01 12:00:00") {
        this.Enabled.Xoa = true;
        this.params.ngayNghiemThu = this.params.ngayNghiemThuGoc;
      } else {
        this.Enabled.Xoa = false;
        this.params.ngayNghiemThu = "";
        this.params.ngayNhapKho = "";
      }
      if (
        this.params.trangThaiDHHT == "2" ||
        this.params.trangThaiDHHT == "6"
      ) {
        this.Enabled.nghiemThu = false;
        this.Enabled.Xoa = false;
      }
      await this.Load_DS_VATTU();
    },
    async capNhatNTBanGiao(kieu){
      try{        
        if(kieu == 1 && (this.params.ngayNTBanGiao == null || this.params.ngayNTBanGiao == "")){
          this.$toast.error("Chưa chọn ngày nghiệm thu bàn giao!")
          return
        }else if(kieu == 0){
          var isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm("Bạn có muốn xóa ngày nghiệm thu bàn giao không ?", {
              title: "Thông báo",
              centered: true,
              size: "md",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            })
            .then((value) => {
              if (value) {
                isBoxConfirm = true;
              } 
            });

          if (!isBoxConfirm) {
            return;
          }
        }      
        this.loading(true)
        let ketQua = await this.capNhatNgayNghiemThuBanGiao({
          donHangId: this.params.donHangId,
          ngayNtKt: this.params.ngayNTBanGiao == null ? "" : this.f_DateToString(this.params.ngayNTBanGiao),
          kieu: kieu
        })
        if(ketQua == 1){
          this.$toast.success("Cập nhật ngày nghiệm thu bàn giao thành công!")
          this.params.ngayNTBanGiao = await this.getNgayNghiemThuBanGiao(this.params.donHangId)  
        }else{
          this.$toast.error(ketQua)
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async onClickNghiemThu() {
      try {
        var KiemTra = await this.KiemTra_DuLieu();
        if (!KiemTra) {
          return;
        }
        var currentDateTime = this.f_DateTimeToString(new Date());
        var listDateTime = currentDateTime.split(" ");
        var nt =
          this.f_DateToString(this.params.ngayNghiemThu) +
          " " +
          listDateTime[1];
        var nkho =
          this.f_DateToString(this.params.ngayNhapKho) + " " + listDateTime[1];
        var dem = 0;
        var dem_ = 0;
        var i_d = 0;

        this.dataResponse.DSVatTu.forEach((e) => {
          var HAN_BH = e.HAN_BH;
          if (
            (HAN_BH == null || HAN_BH == "") &&
            !(e.MA_VT.startsWith("10") || e.MA_VT.startsWith("16.01"))
          ) {
            dem += 1;
          } else {
            if (
              HAN_BH != null &&
              this.params.ngayNghiemThu != null &&
              this.params.ngayNghiemThu != "" &&
              new Date(HAN_BH) < this.params.ngayNghiemThu
            ) {
              dem_ += 1;
            }
          }
        });
        var mess = "";
        if (dem != 0) {
          this.$toast.error(
            "Có " + dem + " vật tư chưa nhập hạn bảo hành \t \n"
          );
        }
        if (dem_ != 0) {
          this.$toast.error(
            "Có " + dem + " vật tư hạn bảo hành nhỏ hơn ngày nghiệm thu \t \n"
          );
        }
        let chiTietList = [];
        if (dem == 0 && dem_ == 0) {
          if (this.rowCheckedCTDH.length != 0) {
            var listCTDHId = this.rowCheckedCTDH;
            var DSChecked = this.dataResponse.DSVatTu.filter(function (el) {
              return listCTDHId.includes(el.CTDH_ID);
            });
            
            DSChecked.forEach((e) => {
              chiTietList.push({
                soLuongNt: e.SL_NT,
                soLuongHong: e.SL_HONG,
                soLuongChuan: e.SL_CHUAN,
                donGia: e.DONGIA,
                hanBh: e.HAN_BH.replaceAll("-", "/"),
                chiTietDhId: e.CTDH_ID,
              });
            });

            await this.insertBackupDonHang({
              kieu: 1,
              donHangId: this.params.donHangId,
              ngayNt: nt,
              ngayNhapKho: nkho,
              soHd: this.params.soHoaDon,
              chiTietList: chiTietList,
            });
          } else {
            
            this.dataResponse.DSVatTu.forEach((e) => {
              chiTietList.push({
                soLuongNt: e.SL_NT,
                soLuongHong: e.SL_HONG,
                soLuongChuan: e.SL_CHUAN,
                donGia: e.DONGIA,
                hanBh: e.HAN_BH.replaceAll("-", "/"),
                chiTietDhId: e.CTDH_ID,
              });
            });
            await this.insertBackupDonHang({
              kieu: 0,
              donHangId: this.params.donHangId,
              ngayNt: this.f_DateTimeToString(this.params.ngayNghiemThu),
              ngayNhapKho: this.f_DateTimeToString(this.params.ngayNhapKho),
              soHd: this.params.soHoaDon,
              chiTietList: chiTietList,
            });
          }

          await this.capNhatHopDong({
            hdmsId: this.params.hdms_id,
            kieu: 0,
          });

          var DSVatTuNghiemThu = await this.getDSVatTuNghiemThu(
            this.params.hdms_id
          );
          var vkieuhd_id = -1;
          var vgiatri_hd_conlai = 0;
          var tien = 0;
          if (this.thongTinNguoiDung.maTinh == "HCM") {
            var DSTTHD = await this.getTTHopDongDonHang({
              hdmsId: this.params.hdms_id,
              donHangId: this.params.donHangId,
            });

            if (DSTTHD.length > 0) {
              vkieuhd_id = DSTTHD[0].KIEUHD_ID;
              vgiatri_hd_conlai = DSTTHD[0].TIEN_CL;
            }
          }

          for (const i in DSVatTuNghiemThu) {
            if (
              (vkieuhd_id == -1 || vkieuhd_id == 1) &&
              DSVatTuNghiemThu[i].SOLUONG - DSVatTuNghiemThu[i].SLChuan < 0
            ) {
              await this.xoaNghiemThu(this.params.donHangId);
              await this.capNhatHopDong({
                hdmsId: this.params.hdms_id,
                donHangId: this.params.donHangId,
              });
              this.$toast.error(
                "Đơn hàng nghiệm thu số lượng không đúng " +
                  this.params.hdms_id +
                  " " +
                  this.params.donHangId
              );
              return;
            }
          }
          if (vkieuhd_id == 2) {
            var DSChiTietDonHang = await this.getDSChiTietDonHang(
              this.params.donHangId
            );
            var tien = 0;
            for (const i in DSChiTietDonHang) {
              tien += DSChiTietDonHang[i].slChuan + DSChiTietDonHang[i].donGia;
              if (tien > vgiatri_hd_conlai) {
                await this.xoaNghiemThu(this.params.donHangId);
                await this.capNhatHopDong({
                  hdmsId: this.params.hdms_id,
                  donHangId: this.params.donHangId,
                });
                this.$toast.error(
                  "Đơn hàng nghiệm thu giá trị không đúng " +
                    tien +
                    " / " +
                    vgiatri_hd_conlai +
                    " " +
                    this.params.hdms_id +
                    " " +
                    this.params.donHangId
                );
                return;
              }
            }
          }

          await this.capNhatTTNghiemThuDonHang({
            hdmsId: this.params.hdms_id,
            donHangId: this.params.donHangId,
            nhanVienId: this.thongTinNguoiDung.nhanVienId,
          });
          this.$toast.success("Nghiệm thu đơn hàng thành công");
          this.ghi = true;
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async KiemTra_DuLieu() {
      var msg = "";

      if (
        this.params.ngayNghiemThu == "" ||
        this.params.ngayNghiemThu == null
      ) {
        msg += "Vui lòng nhập ngày nghiệm thu \t\n ";
      }
      if (this.params.ngayNhapKho == "" || this.params.ngayNhapKho == null) {
        msg += "Vui lòng nhập ngày nhập kho \t\n ";
      }
      if (
        this.params.ngayNghiemThu < this.params.ngayNhanHang &&
        !this.boqua_ngay_nghiemthu
      ) {
        msg +=
          "Ngày nghiệm thu phải lớn hơn ngày nhận hàng: " +
          this.f_DateToString(this.params.ngayNhanHang) +
          " \t\n ";
      }

      if (this.params.ngayNhapKho < this.params.ngayNghiemThu) {
        msg +=
          "Ngày đề nghị nhập kho phải lớn hơn hoặc bằng ngày nghiệm thu \t\n ";
      }
      var soLuongNghiemThu = 0;
      var soLuongHong = 0;
      this.dataResponse.DSVatTu.forEach((e) => {
        soLuongNghiemThu += e.SL_NT;
        soLuongHong += e.SL_HONG;
      });

      if (soLuongNghiemThu == 0 && soLuongHong == 0) {
        msg += "Chưa điền số lượng nghiệm thu \t\n ";
      }

      var listCTDHId = this.rowCheckedCTDH;
      var DSChecked = this.dataResponse.DSVatTu.filter(function (el) {
        return listCTDHId.includes(el.CTDH_ID) && el.SL_NT == 0;
      });

      if (DSChecked.length > 0) {
        msg += "Có số lượng nghiệm thu bằng 0 không thể nghiệm thu \t\n ";
      }

      var KTDonHangDaCoChungTu = await this.KTDonHangDaCoChungTu(
        this.params.donHangId
      );
      if (KTDonHangDaCoChungTu[0] > 0 && !this.params.dsQuyen.includes(2512)) {
        msg += "Đơn hàng đã có chứng từ không thể nghiệm thu \t\n ";
      }

      if (msg != "") {
        this.$toast.error(msg);
        return false;
      }
      return true;
    },
    rowsCheckboxCTDH: function (dataItem) {
      // this.rowCheckedCTDH = dataItem
    },
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },

    async dialogOpen() {
      try {
        this.loading(true);

        EventBus.$on("onDownloadFile", this.onDownloadFileHandler);
        EventBus.$on("onDeleteFile", this.onDeleteFileHandler);
        var TSMacDinh = await this.getTSMacDinh("BOQUA_NGAY_NGHIEMTHU");
        if (TSMacDinh != 0) {
          this.boqua_ngay_nghiemthu = true; // HCM đang dùng
        }
        if (!this.boqua_ngay_nghiemthu) {
          this.params.ngayNghiemThu = this.params.ngayNhanHang;
        }
        this.Load_cbo();        
      
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    onClickThoat() {
      this.Close();
    },
    Open() {
      this.$refs.popupNghiemThuDH.show();
    },
    async Close() {
      await this.$parent.closeNghiemThuDH();
      this.$refs.popupNghiemThuDH.hide();
    },
    onClickHanBH() {
      this.$bvModal.show("popupHanBH");
    },

    chooseFile() {
      this.$refs["ref-choose-file"].$el.querySelector("input").click();
    },
    async onChangeFile() {
      try{
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
        for (let i = 0; i < choosedFiles.length; i++) {
          
          let item = choosedFiles[i];
          let CheckFile = this.dataResponse.DSFileCV.filter(e=> e.nameFile.toLowerCase() == item.name.toLowerCase())
          if(CheckFile.length > 0){
            this.$toast.error("Tên file đã tồn tại trong đơn hàng này!")
            return
          }          
          formData.append("file", item);
        }        
        let paths = await this.uploadFile(formData);

        if (paths.length == 0) {
          this.$toast.error("Upload file thất bại!");
          this.loading(false);
          return;
        }

        for (let i = 0; i < paths.length; i++) {
          if (paths[i].status) {
            var urlSplit = paths[i].message.relativeUrl.split("/");
            this.dataResponse.DSFileCV.push({
              linkFile: paths[i].message.relativeUrl,
              nameFile: urlSplit[urlSplit.length - 1],
            });
          }
        }
        var listFile = [];
        this.dataResponse.DSFileCV.forEach((e) => {
          listFile.push(e.linkFile);
        });
        var KTSaveFile = await this.capNhatFile({
          fileVb: listFile.join(","),
          donHangId: this.params.donHangId,
        });

        await this.getDSFile();
        this.$refs.gridFile.grid.refreshColumns();
        this.$toast.success("Upload file thành công!");        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false);
      }
    },
    async onDeleteFileHandler(item) {
      try{
       
        this.loading(true);
        var deleteFile = await this.deleteFile({ fileSource: item.linkFile });
        var listFile = [];
        this.dataResponse.DSFileCV.forEach((e) => {
          if (item.linkFile != e.linkFile) {
            listFile.push(e.linkFile);
          }
        });
        var KTSaveFile = await this.capNhatFile({
          fileVb: listFile.join(","),
          donHangId: this.params.donHangId,
        });
        await this.getDSFile();
        this.$toast.success("Xóa file thành công!");
        this.$refs.gridFile.grid.refreshColumns();
        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false);
      }
      
    },
    async onDownloadFileHandler(item) {
      var checkFile = await this.checkFileExist({fileSource : item.linkFile})        
      var linkFile = item.linkFile
      if(checkFile.message == "Success" && checkFile.data.data){
        let getLink = await this.getLinkFile({fileSource : item.linkFile}) 
        linkFile = getLink.data.name                 
      }
      const downloadLink = document.createElement("a");      
      downloadLink.href = linkFile;
      const filePath = item.linkFile.split('/');
      const fileName = filePath[filePath.length - 1];
      downloadLink.download = fileName;    
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
  },

  created: async function () {
    // this.loading(true)
    // EventBus.$on('onDownloadFile', this.onDownloadFileHandler);
    // var TSMacDinh = await this.getTSMacDinh("BOQUA_NGAY_NGHIEMTHU")
    // if(TSMacDinh.length != 0){
    //   this.boqua_ngay_nghiemthu = true// HCM đang dùng
    // }
    // if(!this.boqua_ngay_nghiemthu){
    //   this.params.ngayNghiemThu = this.params.ngayNhanHang
    // }
    // //  this.Load_cbo()
    // this.loading(false)
  },
  provide: {
    grid: [Group, Page, Filter, Sort, Resize],
  },
};
</script>
