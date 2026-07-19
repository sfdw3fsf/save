<template src="./DotTongHop.html"></template>
<style scoped src="./DotTongHop.scss"></style>
<script>
import Vue from "vue";
import moment from "moment";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/dottonghop";
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import { previewPrint } from "../../../utils/util";
import EventBus from "@/utils/eventBus";

export default Vue.extend({
  name: "DotTongHop",
  components: { breadcrumb },
  data() {
    return {
      header: {
        title: "ĐỢT TỔNG HỢP",

        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Đợt tổng hợp",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        formatMonth: "MM/yyyy",
        waterMark: "Chọn ngày",
      },

      params: {
        Thang: new Date(),
        ngayDuyet: "",
        ngayDuyetConfirm: new Date(),
        ngayVB: "",
        tagFrom: "",
        dsDK: "",
        vdonvi_timkiem: "-1",
        view_all_thdk: "0",
        tongHopId: -1,
        dotCu: -1,
        Dot: -1,
        ghiChu: "",
        maTH: "",
        fileCV: "",
        tuThang: new Date(new Date().getTime() - 30 * 24 * 60 * 60 * 1000), //ngày yêu cầu từ
        denThang: new Date(),
        loaiTH: "",
      },
      Visible: {
        colTenDV: true,
      },
      isKHDN: false,
      cboLoaiTongHop: [
        {
          loaiTHId : "",
          TenTongHop: "Tổng hợp mua sắm tại đơn vị"      
        }, 
        {
          loaiTHId : "3",
          TenTongHop: "Tổng hợp mua sắm tập trung"      
        },
      ],
      showFile: "none",
      themMoi: false,
      thongTinNguoiDung: {
        nhanVienId: this.$root.token.getNhanVienID(),
        nguoiCN: this.$root.token.getUserName(), //this.$auth.MaNguoiDung
        mayCN: "DESKTOP-IDG67LD", //this.$auth.MAYCN
        ipCN: "10.59.90.128", //this.$auth.IPCN
        donViId: this.$root.token.getDonViID(), //this.$auth.getDonViID(),
        maTinh: this.$root.token.getMaTinh(),
      },
      dataSource: {
        DSDotTongHop: [],
        DSDotDK: [],
        DSDKDaGan: [],
        DSDKChuaGan: [],
        DSFile: [],
      },
      Enabled: {
        Duyet: true,
        huyDuyet: true,
        ghiLai: true,
        Huy: true,
        Xoa: true,
        FileCV: true,
        nhapMoi: true,
      },
      dotTongHopFocus: [],
      dotTongHopChecked: [],
      daGanChecked: [],
      chuaGanChecked: [],
      rowFocus: {},
      animationSettings: { effect: "Zoom" },
      target: ".main-wrapper",
      Visible: {
        Duyet: true,
        huyDuyet: true,
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

      columnsCheckbox: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.dotTongHopChecked"
                                    :value="data.TONGHOP_ID"/>
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
      checkAllDotTH: false,
      columnsCheckboxAll: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllDotTH" class="uncheck"/>
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
    };
  },

  watch: {
    dotTongHopChecked: function (value) {
      this.dotTongHopChecked = value;
      if (this.dotTongHopChecked.length == 0) {
        this.checkAllDotTH = false;
      }
      this.BAT_TAT_DUYET_TH();
    },
    checkAllDotTH: function (value) {
      this.dotTongHopChecked = [];
      if (value) {
        for (let e of this.dataSource.DSDotTongHop) {
          this.dotTongHopChecked.push(e.TONGHOP_ID);
        }
      }
      this.BAT_TAT_DUYET_TH();
    },
  },
  computed: {
    ...mapState("dottonghop", ["DSTongHopThang"]),
    ...mapGetters("dottonghop", getterName),
  },
  methods: {
    ...mapActions("dottonghop", actionName),
    ...mapMutations("dottonghop", mutationName),

    f_MonthToString: function (value, format = "YYYYMM") {
      return moment(value).format(format);
    },
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },

    async gridDotTongHop_FocusedRowChanged(row) {
      if (row) {
        this.rowFocus = row;
        this.setParamsFocus(row);
      }
    },

    gridDotTongHop_selected: function (dataItem) {
      // this.dotTongHopChecked = dataItem;
      // this.BAT_TAT_DUYET_TH();
    },

    onChangeThang() {
      this.loading(true);
      this.NAP_DS_TONGHOP();
      this.loading(false);
    },

    // async CAPNHAT_FILE_CV(urlFile,kieu){
    //   if(kieu == 1){
    //     await this.capNhapFileCV({
    //       tongHopId: this.params.tongHopId,
    //       fileCv: urlFile,
    //       ngayCn: this.f_DateTimeToString(new Date()),
    //       nguoiCn:this.thongTinNguoiDung.nguoiCN,
    //       mayCn: this.thongTinNguoiDung.mayCN,
    //       ipCn: this.thongTinNguoiDung.ipCN,
    //       kieu: kieu
    //     })
    //     this.NAP_DS_FILE_CV()
    //   }else if(kieu == 2){
    //     await this.capNhapFileCV({
    //       tongHopId: this.params.tongHopId,
    //       fileCv: urlFile,
    //       ngayCn: this.f_DateTimeToString(new Date()),
    //       nguoiCn:this.thongTinNguoiDung.nguoiCN,
    //       mayCn: this.thongTinNguoiDung.mayCN,
    //       ipCn: this.thongTinNguoiDung.ipCN,
    //       kieu: kieu
    //     })
    //     this.NAP_DS_FILE_CV()
    //     this.XOA_FILE(urlFile)
    //   }
    //   else if(kieu == 3){
    //     await this.capNhapFileCV({
    //       tongHopId: this.params.tongHopId,
    //       fileCv: urlFile,
    //       ngayCn: this.f_DateTimeToString(new Date()),
    //       nguoiCn:this.thongTinNguoiDung.nguoiCN,
    //       mayCn: this.thongTinNguoiDung.mayCN,
    //       ipCn: this.thongTinNguoiDung.ipCN,
    //       kieu: kieu
    //     })
    //     this.NAP_DS_FILE_CV()
    //   }
    // },
    async NAP_DS_TONGHOP() {
      let DSTongHopThang = await this.getDSTongHopThang({
        dataRequest: {
          thangDk: this.f_MonthToString(this.params.Thang), //this.f_MonthToString(this.params.Thang),
          tagFrm: this.params.tagFrom,
          kieuTh: this.params.dsDK,
          donViId: this.thongTinNguoiDung.donViId,
          viewAll: this.params.view_all_thdk,
        },
        pageNo: 0,
        pageSize: 100,
      });

      this.dataSource.DSDotTongHop = DSTongHopThang.data;
      this.dotTongHopChecked = [];
      this.checkAllDotTH = false;
      if (this.dataSource.DSDotTongHop.length > 0) {
        this.rowFocus = this.dataSource.DSDotTongHop[0];
        this.setParamsFocus(this.rowFocus);
      } else {
        this.Enabled.ghiLai = false;
        this.Enabled.Huy = false;
        this.Enabled.Xoa = false;

        this.params.Dot = -1;
        this.params.ghiChu = "";
        this.params.ngayDuyet = "";
        this.params.ngayVB = "";
        this.params.maTH = "";

        (this.dataSource.DSDKDaGan = []), (this.dataSource.DSDKChuaGan = []);
      }
      this.themMoi = false;
      this.Enabled.nhapMoi = true;

      this.BAT_TAT_DUYET_TH();
    },

    showPopupNhapNgayDuyet() {
      this.$bvModal.show("popupNhapNgayDuyet");
    },
    async onClickDuyet() {
      this.$bvModal.hide("popupNhapNgayDuyet");

      var listDuyetId = [];
      var listIndex = [];
      try {
        this.loading(true);
        for (const j in this.dataSource.DSDotTongHop) {
          if (
            this.dotTongHopChecked.includes(
              this.dataSource.DSDotTongHop[j].TONGHOP_ID
            ) &&
            (this.dataSource.DSDotTongHop[j].NGAYDUYET == "" ||
              this.dataSource.DSDotTongHop[j].NGAYDUYET == null)
          ) {
            listDuyetId.push(this.dataSource.DSDotTongHop[j].TONGHOP_ID);
            listIndex.push(parseInt(j));
          }
        }

        if (listDuyetId.length == 0) {
          this.$toast.error("Hãy chọn danh sách tổng hợp muốn duyệt");
          this.loading(false);
          return;
        }
        let KTDotTongHop = await this.kiemTraDotTongHop({
          tongHopIdArr: listDuyetId,
        });
        if (
          KTDotTongHop.length > 0 &&
          KTDotTongHop[0] != null &&
          KTDotTongHop[0].trim() != 0
        ) {
          this.$toast.error(
            "Đợt" + KTDotTongHop[0] + " chưa tổng hợp, không thể duyệt"
          );
          this.loading(false);
          return;
        }

        let duyetTongHop = await this.duyetDotTongHop({
          tongHopIdArr: listDuyetId,
          ngayDuyet: this.f_DateTimeToString(this.params.ngayDuyetConfirm),
          ngayCn: this.f_DateTimeToString(new Date()),
          nguoiCn: this.thongTinNguoiDung.nguoiCN,
          mayCn: this.thongTinNguoiDung.mayCN,
          ipCn: this.thongTinNguoiDung.ipCN,
        });

        if (duyetTongHop > 0) {
          await this.NAP_DS_TONGHOP();
          this.$refs.gridDotTongHop.flagSelectedRowIndexes = listIndex;
          this.$toast.success("Duyệt thành công");
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async onClickHuyDuyet() {
      // Xác Thực
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm(
          "Bạn có thực sự muốn hủy duyệt đợt tổng hợp đang chọn không ?",
          {
            title: "Xác nhận hành động",
            centered: true,
            size: "md",
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
      try {
        this.loading(true);
        var listHuyDuyetId = [];
        var listIndex = [];
        for (const tongHopId of this.dotTongHopChecked) {
          for (const j in this.dataSource.DSDotTongHop) {
            if (
              this.dataSource.DSDotTongHop[j].TONGHOP_ID == tongHopId &&
              this.dataSource.DSDotTongHop[j].NGAYDUYET != null &&
              this.dataSource.DSDotTongHop[j].NGAYDUYET != ""
            ) {
              listHuyDuyetId.push(tongHopId);
              listIndex.push(parseInt(j));
            }
          }
        }
        if (listHuyDuyetId.length == 0) {
          this.$toast.error("Hãy chọn danh sách tổng hợp muốn hủy duyệt");
          this.loading(false);
          return;
        }

        var KTHopDongDaLap = await this.kiemTraHopDongDaLap(
          this.params.tongHopId
        );
        if (KTHopDongDaLap > 0) {
          this.loading(false);
          this.$toast.error("Bạn đã lập hợp đồng không thể hủy duyệt");
          return;
        }

        var KTChungTuDaLap = await this.kiemTraChungTuDaLap(
          this.params.tongHopId
        );
        if (KTChungTuDaLap.length > 0) {
          this.loading(false);
          this.$toast.error("Bạn đã lập chứng từ không thể hủy duyệt ");
          return;
        }

        let duyetTongHop = await this.duyetDotTongHop({
          tongHopIdArr: listHuyDuyetId,
          ngayDuyet: "",
          ngayCn: this.f_DateTimeToString(new Date()),
          nguoiCn: this.thongTinNguoiDung.nguoiCN,
          mayCn: this.thongTinNguoiDung.mayCN,
          ipCn: this.thongTinNguoiDung.ipCN,
        });
        if (duyetTongHop > 0) {
          await this.NAP_DS_TONGHOP();
          this.$refs.gridDotTongHop.flagSelectedRowIndexes = listIndex;
          this.$toast.success("Hủy duyệt thành công");
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async NAP_DS_DKTH() {
      this.dataSource.DSDKDaGan = [];
      this.dataSource.DSDKChuaGan = [];
      if (this.params.tongHopId != -1) {
        this.dataSource.DSDKChuaGan = await this.getDSDangKyTongHop({
          tuThangDk: this.f_DateToString(this.params.tuThang, "MM/YYYY"),
          denThangDk: this.f_DateToString(this.params.denThang, "MM/YYYY"),
          ttDkId: 1,
          donViId: this.params.vdonvi_timkiem,
          nhanVienId: this.thongTinNguoiDung.nhanVienId,
          tuNgay: "0",
          denNgay: "0",
          tagfrm: this.params.dsDK,
          tongHopId: this.params.tongHopId,
        });

        this.dataSource.DSDKDaGan = await this.getDSDangKyTongHop({
          tuThangDk: this.f_DateToString(this.params.tuThang, "MM/YYYY"),
          denThangDk: this.f_DateToString(this.params.denThang, "MM/YYYY"),
          ttDkId: 2,
          donViId: -1,
          nhanVienId: this.thongTinNguoiDung.nhanVienId,
          tuNgay: "0",
          denNgay: "0",
          tagfrm: this.params.dsDK,
          tongHopId: this.params.tongHopId,
        });
      }
    },
    async XOA_FILE(fileCV) {
      fileCV = fileCV.replace("http://10.70.52.164:8081/", "");
      try {
        var linkDeleteFileCV =
          "http://10.70.52.164:8081/FILE/UploadFile.aspx?user=dauxanh&pass=rauma&action=delete&filePath=" +
          fileCV;
        var request = new XMLHttpRequest();
        request.open("GET", linkDeleteFileCV, true);
        request.send();
      } catch (error) {}
    },
    async NAP_DS_FILE_CV() {
      this.dataSource.DSFile = [];
      var DSFile = await this.getDSFileCVTongHop(this.params.tongHopId);
      if (DSFile != null && DSFile.length > 0) {
        if (DSFile[0] != null && DSFile[0] != "") {
          var listFileCV = DSFile[0].split("|");
          listFileCV.forEach((element) => {
            if (element != "") {
              var urlSplit = element.split("/");
              this.dataSource.DSFile.push({
                linkFile: element,
                nameFile: urlSplit[urlSplit.length - 1],
              });
            }
          });
        }
      }
      this.$refs.gridFile.grid.refreshColumns();
    },
    async onClickXoa() {
      // Xác Thực
      try {
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(
            "Bạn có thực sự muốn xóa đợt tổng hợp đang chọn không ?",
            {
              title: "Xác nhận hành động",
              centered: true,
              size: "md",
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

        var kiemTraVTTongHop = await this.kiemTraVatTuTongHop(
          this.params.tongHopId
        );
        if (kiemTraVTTongHop != 0) {
          this.$toast.error(
            "Đợt tổng hợp đang có vật tư liên quan, không thể xóa"
          );
          return;
        }

        await this.NAP_DS_FILE_CV();

        var listFile = [];
        for (const i in this.dataSource.DSFile) {
          var deleteFile = await this.deleteFile({
            fileSource: this.dataSource.DSFile[i].linkFile,
          });
        }

        var ngayDuyet =
          this.params.ghiChu != "" ? this.f_DateTimeToString(new Date()) : "";
        var ngayVB =
          this.params.ngayVB != ""
            ? this.f_DateTimeToString(this.params.ngayVB)
            : "";
        var kiemTraCapNhat = await this.capNhapDotTongHop({
          kieuId: 3,
          tongHopId: this.params.tongHopId,
          ngayDuyet: ngayDuyet,
          dot: this.params.Dot,
          thangTh: this.f_MonthToString(this.params.Thang),
          ghiChu: this.params.ghiChu,
          fileCv: "",
          nhanVienId: this.thongTinNguoiDung.nhanVienId,
          donViId: this.thongTinNguoiDung.donViId,
          ngayCn: this.f_DateTimeToString(new Date()),
          nguoiCn: this.thongTinNguoiDung.nguoiCN,
          mayCn: this.thongTinNguoiDung.mayCN,
          ipCn: this.thongTinNguoiDung.ipCN,
          ngayVb: ngayVB,
          tagFrm: this.params.tagFrom,
          dsDk: this.params.dsDK,
        });
        
        if (kiemTraCapNhat == 1) {
          this.$toast.success("Xóa thành công");
          await this.NAP_DS_TONGHOP();
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async onClickNhapMoi() {
      this.themMoi = true;
      this.Enabled.nhapMoi = false;
      this.Enabled.ghiLai = true;
      this.Enabled.Huy = true;
      this.Enabled.Xoa = false;
      this.Enabled.Duyet = false;
      this.Enabled.huyDuyet = false;
      this.Enabled.FileCV = false;

      this.params.Dot = -1;
      this.params.ghiChu = "";
      this.params.ngayDuyet = "";
      this.params.ngayVB = "";
      this.params.maTH = "";

      (this.dataSource.DSFile = []),
        (this.dataSource.DSDKDaGan = []),
        (this.dataSource.DSDKChuaGan = []);

      if (this.params.tagFrom != "") {
        var DotTH = await this.getMaxDotTongHop({
          thangTh: this.f_MonthToString(this.params.Thang),
          tag: this.params.tagFrom,
          kieuTh: this.params.dsDK,
          donViId: this.thongTinNguoiDung.donViId,
        });
        if (DotTH > 0) {
          this.params.Dot = DotTH;
        }
      }
    },
    async onClickHuy() {
      this.Enabled.nhapMoi = true;
      this.Enabled.ghiLai = false;
      this.Enabled.Huy = false;
      this.Enabled.Xoa = false;
      this.themMoi = false;
      this.Enabled.nhapMoi = true;
      this.setParamsFocus(this.rowFocus);
    },
    async onClickGhiLai() {
      try {
        this.loading(true);
        var kiemTra = await this.KIEMTRA();
        if (kiemTra) {
          var tongHopId = this.params.tongHopId;
          if (this.themMoi) {
            tongHopId = await this.getKey({
              keyName: "TONGHOP_ID",
              numBlockSize: 0,
              numRetry: 0,
            });
          }
          var ngayDuyet =
            this.params.ngayDuyet != "" && this.params.ngayDuyet != null
              ? this.f_DateTimeToString(this.params.ngayDuyet)
              : "";
          var ngayVB =
            this.params.ngayVB != "" && this.params.ngayVB != null
              ? this.f_DateTimeToString(this.params.ngayVB)
              : "";
          var kiemTraCapNhat = await this.capNhapDotTongHop({
            kieuId: this.themMoi ? 1 : 2,
            tongHopId: tongHopId,
            ngayDuyet: ngayDuyet,
            dot: this.params.Dot,
            thangTh: this.f_MonthToString(this.params.Thang),
            ghiChu: this.params.ghiChu,
            fileCv: "",
            nhanVienId: this.thongTinNguoiDung.nhanVienId,
            donViId: this.thongTinNguoiDung.donViId,
            ngayCn: this.f_DateTimeToString(new Date()),
            nguoiCn: this.thongTinNguoiDung.nguoiCN,
            mayCn: this.thongTinNguoiDung.mayCN,
            ipCn: this.thongTinNguoiDung.ipCN,
            ngayVb: ngayVB,
            tagFrm: this.params.loaiTH == "3" ? "3" : this.params.tagFrom,
            dsDk: this.params.loaiTH == "3" ? "3" : this.params.dsDK,
          });
          if (kiemTraCapNhat == 1) {
            this.$toast.success("Cập nhật thành công");
            await this.NAP_DS_TONGHOP();
            await this.FocusGridViewRow(this.params.Dot);
          }
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async FocusGridViewRow(value) {
      let indexs = this.dataSource.DSDotTongHop.findIndex(
        (a) => a.DENGHI_ID != null && a.DENGHI_ID.toString() == value.toString()
      );
      if (indexs != -1) {
        if (
          !(
            indexs + 1 >
              this.$refs["gridDotTongHop"].pageIndexData *
                this.$refs["gridDotTongHop"].pageSizeData &&
            indexs + 1 <=
              (this.$refs["gridDotTongHop"].pageIndexData + 1) *
                this.$refs["gridDotTongHop"].pageSizeData
          )
        ) {
          this.$refs["gridDotTongHop"].$refs.pagination.gotoPage(
            parseInt(indexs / this.$refs["gridDotTongHop"].pageSizeData)
          );
        }
        let self = this;
        let ind = parseInt(indexs % this.$refs["gridDotTongHop"].pageSizeData);
        setTimeout(() => {
          self.$refs["gridDotTongHop"].grid.selectRow(ind, true);
        }, 1000);
      }
    },
    async KIEMTRA() {
      if (this.params.Dot == -1) {
        this.$toast.error("Chưa chọn đợt tổng hợp");
        this.loading(false);
        return false;
      }

      let KTDotTongHop = await this.getDotTongHop({
        dot: this.params.Dot,
        thangTh: this.f_MonthToString(this.params.Thang),
        tag: this.params.loaiTH == "3" ? "3" : this.params.tagFrom,
        kieuTh: this.params.loaiTH == "3" ? "3" : this.params.dsDK,
        donViId: this.thongTinNguoiDung.donViId,
      });

      if (this.themMoi) {
        if (KTDotTongHop.length > 0) {
          this.$toast.error("Đợt tổng hợp có rồi");
          this.loading(false);
          return false;
        }
      } else {
        if (KTDotTongHop.length > 0 && this.params.Dot != this.params.dotCu) {
          this.$toast.error("Đợt tổng hợp có rồi");
          this.loading(false);
          return false;
        }
      }
      if (this.params.ngayDuyet != "" && this.params.ngayDuyet != null) {
        this.$toast.error("Đợt tổng hợp đã đc duyệt bạn không được sửa");
        this.loading(false);
        return false;
      }
      return true;
    },
    async setParamsFocus(row) {
      this.dotTongHopFocus = row;
      this.params.tongHopId = row.TONGHOP_ID;
      this.params.Dot = row.DOT;
      this.params.ghiChu = row.GHICHU;
      this.params.dotCu = row.DOT;
      this.params.maTH = row.MA_TH;
      this.params.ngayDuyet =  row.NGAYDUYET != "" ? new Date(row.NGAYDUYET) : "";
      this.params.ngayVB = row.NGAY_VB != "" ? new Date(row.NGAY_VB) : "";
      this.Enabled.ghiLai = true;
      this.Enabled.Huy = true;
      this.Enabled.FileCV = true;
      this.Enabled.Xoa = row.NGAYDUYET == "" ? true : false;
      if(this.$auth.getPhanVungID() == 97){
        this.params.loaiTH = row.KIEU_TH
      }
      if (this.params.tagFrom != "") {
        await this.NAP_DS_DKTH();
      }
    },

    BAT_TAT_DUYET_TH() {
      var checkEnableHuyDuyet = [];
      for (const tongHopId of this.dotTongHopChecked) {
        checkEnableHuyDuyet = this.dataSource.DSDotTongHop.filter(
          (row) =>
            row.TONGHOP_ID == tongHopId &&
            row.NGAYDUYET != null &&
            row.NGAYDUYET != ""
        );
      }
      this.Enabled.Duyet = this.dotTongHopChecked.length > 0;
      this.Enabled.huyDuyet = checkEnableHuyDuyet.length != 0;
    },

    async loadDataDotTongHop() {
      this.dataSource.DSDotDK = await this.getDSDotDk();
      await this.NAP_DS_TONGHOP();
      await this.NAP_DS_FILE_CV();
    },
    async dialogOpen() {
      try {
        this.loading(true);
        this.isKHDN = this.$auth.getPhanVungID() == 97
        EventBus.$on("onDownloadFile", this.onDownloadFileHandler);
        EventBus.$on("onDeleteFile", this.onDeleteFileHandler);
        await this.loadDataDotTongHop();
        if (this.params.view_all_thdk == "1") {
          this.Visible.colTenDV = true;
        }
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
      this.$refs.popupDotTongHop.show();
    },
    async Close() {
      await this.$parent.closeDotTongHop();
      this.$refs.popupDotTongHop.hide();
    },
    async onClickInsert() {
      if (this.dataSource.DSDKChuaGan.length == 0) {
        return;
      }

      if (this.chuaGanChecked.length == 0) {
        this.$toast.error("Bạn chưa chọn phiếu đăng ký");
        return;
      }
      this.loading(true);
      var listPhieu = [];
      for (const i in this.chuaGanChecked) {
        listPhieu.push({ dk_id: this.chuaGanChecked[i] });
      }

      var KiemTra = await this.kiemTraDangKyDotTH({
        listPhieu: listPhieu,
        tongHopId: 0,
      });

      if (KiemTra.length > 0) {
        if (KiemTra[0].TRUNG_MA != "") {
          this.$toast.error(
            "Đăng ký " +
              KiemTra[0].TRUNG_MA +
              " đã được gán vào tổng hợp khác ! Hãy chọn đăng ký khác !"
          );
          return;
        }
      }
      var ketQua = await this.taoDanhSachTongHopDangKy({
        listPhieu: listPhieu,
        tongHopId: this.params.tongHopId,
      });
      this.$toast.success("Gán thành công");

      await this.NAP_DS_DKTH();

      this.loading(false);
    },
    async onClickOut() {
      if (this.dataSource.DSDKDaGan.length == 0) {
        return;
      }

      if (this.daGanChecked.length == 0) {
        this.$toast.error("Chưa chọn danh sách đăng ký để gỡ");
        return;
      }

      this.loading(true);
      var listPhieu = [];
      for (const i in this.daGanChecked) {
        listPhieu.push({ dk_id: this.daGanChecked[i] });
      }

      var ketQua = await this.xoaDanhSachTongHop({
        listPhieu: listPhieu,
        tongHopId: this.params.tongHopId,
      });

      this.$toast.success("Gỡ thành công");
      await this.NAP_DS_DKTH();
      this.loading(false);
    },
    gridDaGan_selected: function (dataItem) {
      this.daGanChecked = dataItem;
    },
    gridChuaGan_selected: function (dataItem) {
      this.chuaGanChecked = dataItem;
    },

    chooseFile() {
      this.$refs["ref-choose-file"].$el.querySelector("input").click();
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
      for (let i = 0; i < choosedFiles.length; i++) {
        let item = choosedFiles[i];
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
          this.dataSource.DSFile.push({
            linkFile: paths[i].message.relativeUrl,
            nameFile: urlSplit[urlSplit.length - 1],
          });
        }
      }
      var listFile = [];
      this.dataSource.DSFile.forEach((e) => {
        listFile.push(e.linkFile);
      });

      if (listFile.join("|").length > 300) {
        this.$toast.error("Tên file quá dài hoặc quá nhiều, không thể lưu!");
        await this.NAP_DS_FILE_CV();
        this.loading(false);
        return;
      }

      await this.capNhapFileCV({
        tongHopId: this.params.tongHopId,
        fileCv: listFile.join("|"),
        ngayCn: this.f_DateTimeToString(new Date()),
        nguoiCn: this.thongTinNguoiDung.nguoiCN,
        mayCn: this.thongTinNguoiDung.mayCN,
        ipCn: this.thongTinNguoiDung.ipCN,
        kieu: 1,
      });

      await this.NAP_DS_FILE_CV();
      this.$refs.gridFile.grid.refreshColumns();
      this.$toast.success("Upload file thành công!");
      this.loading(false);
    },

    async onDownloadFileHandler(item) {
      var checkFile = await this.checkFileExist({ fileSource: item.linkFile });
      if (checkFile.message == "Success") {
        if (!checkFile.data.data) {
          this.$toast.error("File không còn tồn tại!");
          return;
        }
      }
      await this.onClickDownFile(item.linkFile);
    },
    async onClickDownFile(value) {
      if (value) {
        const data = await this.downloadFile(value);
        console.log(data, " = data");
        if (data) {
          const filePath = value.split("/");
          const linkSource = `data:application/octet-stream;base64,${data.body.data}`;
          const downloadLink = document.createElement("a");
          const fileName = filePath[filePath.length - 1];
          downloadLink.href = linkSource;
          downloadLink.download = fileName;
          downloadLink.click();
        }
      }
    },

    async onDeleteFileHandler(item) {
      this.loading(true);
      var deleteFile = await this.deleteFile({ fileSource: item.linkFile });
      var listFile = [];
      this.dataSource.DSFile.forEach((e) => {
        if (item.linkFile != e.linkFile) {
          listFile.push(e.linkFile);
        }
      });

      await this.capNhapFileCV({
        tongHopId: this.params.tongHopId,
        fileCv: listFile.join("|"),
        ngayCn: this.f_DateTimeToString(new Date()),
        nguoiCn: this.thongTinNguoiDung.nguoiCN,
        mayCn: this.thongTinNguoiDung.mayCN,
        ipCn: this.thongTinNguoiDung.ipCN,
        kieu: 1,
      });

      await this.NAP_DS_FILE_CV();
      this.$refs.gridFile.grid.refreshColumns();
      this.$toast.success("Xóa file thành công!");
      this.loading(false);
    },

    async onClickInPhieu() {
      try {
        if (this.dataSource.DSDKDaGan.length == 0) {
          this.$toast.error("Không có dữ liệu để in phiếu!");
          return;
        }
        this.loading(true);
        var listDangKyID = [];
        if (this.daGanChecked.length != 0) {
          listDangKyID = this.daGanChecked;
        } else {
          this.dataSource.DSDKDaGan.forEach((e) => {
            listDangKyID.push(e.DANGKY_ID);
          });
        }
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-qlvt/api/bao-cao/de-nghi-cap-vat-tu?extend=pdf`,
          method: "POST",
          responseType: "blob",
          data: {
            dangKyId: listDangKyID.toString(),
          },
        }).then((response) => {
          previewPrint(response.data);
        });
        this.loading(false);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
  },

  async created() {},
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
  destroyed() {},
  computed: {},
});
</script>
