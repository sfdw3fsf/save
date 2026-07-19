<template src='./QuanLyPhieuIn.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import { Group, Page, Filter, Sort, Resize } from "@syncfusion/ej2-vue-grids";
import api from "../api/QuanLyPhieuInAPI";
import { previewPrint } from "../../../utils/util";
import EventBus from "@/utils/eventBus";
import Vue from "vue";

var itemVue = Vue.component("itemTemplate", {
  template: `<div class="check-action" style="vertical-align: middle;">
                                        <input type="checkbox" class="check" @change="onChanged" v-model="data.CHON"/>
                                        <span class="name"></span>
                                    </div>`,
  data() {
    return {
      data: {},
    };
  },
  methods: {
    onChanged() {
      EventBus.$emit("onTBCheckboxChanged", this.data);
    },
  },
});

export default {
  components: { breadcrumb },
  name: "QuanLyPhieuIn",
  provide: {
    grid: [Group, Page, Filter, Sort, Resize],
  },
  mounted() { },
  computed: {},
  data() {
    return {
      dateFormat: "dd/MM/yyyy",

      cboVatTu: [],
      cboTrangThaiIn: [],
      cboKieuIn: [],
      cboLoaiChungTu: [],
      checkBoxVatTu: false,
      checkBoxNguoiLap: false,
      checkBoxCuonCap: false,
      checkBoxSoCV: false,
      checkBoxLoHang: false,
      txtCuonCap: "",
      txtLoHang: "",
      txtSoCV: "",
      txtKieuIn: 0,
      txtLoaiChungTu: 0,
      txtTrangThaiIn: 0,
      txtVatTu: "",

      ds: [],
      ds_vattu: [],
      //slectedDSChungTu.getSelectedRecords(): [],
      selectedDSVatTu: [],
      vchungtu_id: 0,
      dsChungTu: [],
      dsChiTiet: [],
      dt_lct: [],

      txtDenNgay: "",
      txtTuNgay: "",

      txtNgayPXK: "",
      txtSoPXK: "",
      txtSLVatTuIn: 0,
      childGrid: {
        dataSource: this.dsChiTiet,
        queryString: "CHUNGTU_ID",
        allowResizing: true,
        columns: [
          {
            headerText: "Chọn",
            textAlign: "Center",
            template: function () {
              return {
                template: itemVue,
              };
            },
            width: 75,
          },
          {
            field: "LOHANG",
            headerText: "Lô hàng",
            textAlign: "Left",
            headerTextAlign: "Center",
          },
          {
            field: "MA_VT",
            headerText: "Mã vật tư",
            textAlign: "Left",
            headerTextAlign: "Center",
          },
          {
            field: "TEN_VT",
            headerText: "Tên vật tư",
            textAlign: "Left",
            headerTextAlign: "Center",
          },
          {
            field: "DVI_TINH",
            headerText: "ĐVT",
            textAlign: "Left",
            headerTextAlign: "Center",
          },
          {
            field: "SOLUONG",
            headerText: "Số lượng",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "DONGIA",
            headerText: "Đơn giá",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "TIEN",
            headerText: "Thành tiền",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "LOAI_TBI",
            headerText: "Loại thiết bị",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "CHATLUONG",
            headerText: "Chất lượng",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "MA_HD",
            headerText: "Số HĐ",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "MA_DA",
            headerText: "Mã DA",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "ID_NHAP_GOC",
            headerText: "Id nhập gốc",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "NGUOI_QL",
            headerText: "Người QL",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "DIENGIAI",
            headerText: "Diễn giải",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "NOIDUNG",
            headerText: "Nội dung",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "GHICHU",
            headerText: "Ghi chú",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "NGAYIN_PXK",
            headerText: "Ngày PXK",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "SOPHIEU_IN",
            headerText: "Số phiếu XK",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "SOLAN_IN",
            headerText: "Số lần in",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
          {
            field: "TRANGTHAI_DV",
            headerText: "Trạng thái",
            textAlign: "Right",
            headerTextAlign: "Center",
          },
        ],
      },
    };
  },
  methods: {
    async LOAD_COMBO() {
      let res = await api.layDSLoaiChungTu(this.axios);
      this.dt_lct = res.data.data;
      this.cboTrangThaiIn = [
        {
          TEN: "Chưa in",
          ID: 0,
        },
        {
          TEN: "Đã in",
          ID: 1,
        },
      ];
      this.cboKieuIn = [
        {
          TEN: "In Hóa Đơn",
          ID: 1,
        },
        {
          TEN: "In A4",
          ID: 0,
        },
      ];
      this.changeValueKieuIn();
      this.txtDenNgay = new Date();
      this.txtTuNgay = new Date(Date.now() - 7 * 24 * 60 * 60 * 1000);
    },

    async LOAD_GRID() {
      let json_list = [{}];
      this.dsChungTu = [];
      let dt1 = [];
      let dt2 = [];
      let vlohang_tk = "-1";
      let vcuoncap_goc_tk = "-1";
      let vsocv_tk = "-1";

      if (this.checkBoxVatTu) {
        if (this.cboVatTu.length > 0) {
          if (this.$refs.selectedDSVatTu.selectedItems == 0) {
            json_list = [{}];
          } else {
            let tempVatTuId = [];
            this.$refs.selectedDSVatTu.selectedItems.forEach((item) => {
              console.log(item.vatTuID);
              tempVatTuId.push({ VATTU_ID: item.vatTuID });
            });
            json_list = tempVatTuId;
          }
        }
      }
      if (this.checkBoxCuonCap) {
        vcuoncap_goc_tk = this.txtCuonCap;
      }
      if (this.checkBoxLoHang) {
        vlohang_tk = this.txtLoHang;
      }
      if (this.checkBoxSoCV) {
        vsocv_tk = this.txtSoCV;
      }

      var body = {
        tuNgay: moment(this.txtTuNgay).format("DD/MM/YYYY"),
        denNgay: moment(this.txtDenNgay).format("DD/MM/YYYY"),
        kieuIn: this.txtKieuIn,
        trangThaiIn: this.txtTrangThaiIn,
        kieu: 1,
        loaiCtId: this.txtLoaiChungTu,
        dsVatTu: json_list,
        cuonCapTk: vcuoncap_goc_tk,
        loHangTk: vlohang_tk,
        soCvTk: vsocv_tk,
        chungTuId: -1,
        isCheckNguoiCn : this.checkBoxNguoiLap ?  1 : 0
      };

      // var body = {
      //   tuNgay: "01/01/2017",
      //   denNgay: "30/06/2017",
      //   kieuIn: "1",
      //   trangThaiIn: 0,
      //   kieu: 1,
      //   loaiCtId: 0,
      //   dsVatTu: [
      //     {
      //       VATTU_ID: 6925,
      //     },
      //   ],
      //   cuonCapTk: "-1",
      //   loHangTk: "-1",
      //   soCvTk: "-1",
      // };

      let res = await api.lapDSChungTuInV3(this.axios, body);
      body.kieu = 0;
      let res2 = await api.lapDSChungTuInV3(this.axios, body);

      dt1 = res.data.data;
      this.dsChungTu = dt1;
      dt2 = res2.data.data;
      this.dsChiTiet = dt2;
    },
    async LOAD_POPUP() {
      let res = await api.layDSVatTu(this.axios);
      this.cboVatTu = res.data.data;
    },

    async onClickTimKiem() {
      this.loading(true);
      await this.LOAD_GRID();
      this.loading(false);
    },
    onClickLamMoi() {
      this.txtNgayPXK = "";
      this.txtSoPXK = "";
      this.txtSLVatTuIn = 7; //vi sao 7??
    },
    async onClickCapNhat() {
      if (this.dsChungTu.length == 0) {
        this.$toast.error("Danh sách bạn chưa chọn không thể cập nhật");
        return;
      }
      if (this.$refs.slectedDSChungTu.getSelectedRecords().length == 0) {
        this.$toast.error("Danh sách bạn chưa chọn không thể cập nhật");
        return;
      }
      if (this.txtSoPXK == "") {
        this.$toast.error("Bạn chưa điền số phiếu xuất");
        return;
      }

      let ngayPXK = moment(this.txtNgayPXK).format("DD/MM/YYYY");

      if (!this.txtNgayPXK || !ngayPXK || ngayPXK == "") {
        this.$toast.error("Bạn chưa nhập ngày phiếu xuất");
        return;
      }

      let temps = this.$refs.slectedDSChungTu
        .getSelectedRecords()
        .filter((x) => x.NGUOI_CN != this.$auth.getUserName());
      if (temps.length > 0) {
        this.$toast.error("Bạn không được cập nhật phiếu của người khác");
        return;
      }

      this.$confirm(
        "Bạn có muốn cập nhật danh sách các chứng từ theo số phiếu xuất là " +
        this.txtSoPXK +
        "  và ngày xuất là " +
        ngayPXK +
        " không ?",
        "Thông báo",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
        }
      )
        .then(async () => {
          var dt_kt = [];
          this.$refs.slectedDSChungTu.getSelectedRecords().forEach((item) => {
            dt_kt.push({ CHUNGTU_ID: item.CHUNGTU_ID });
          });

          try {
            var res = await api.capNhatNgayInPXK(this.axios, {
              ds: dt_kt,
              ngayIn: ngayPXK,
              soPhieu: this.txtSoPXK,
            });
            if (res.data.error_code.includes("BSS-00000000")) {
              this.$toast.success("Cập nhật thành công!");
              this.LOAD_GRID();
            } else {
              this.$toast.error(res.data.data);
            }
          } catch (error) {
            this.$toast.error(error.data.message);
          }
        })
        .catch(() => {
          return;
        });
    },

    changeValueKieuIn() {
      this.txtLoaiChungTu = 0;
      if (this.txtKieuIn == 1) {
        this.cboLoaiChungTu = this.dt_lct.filter((x) => x.loaiCtId == 2);
      } else if (this.txtKieuIn == 0) {
        this.cboLoaiChungTu = this.dt_lct.filter((x) => x.loaiCtId != 0);
      }
    },

    async In_Phieu() {
      if (this.dsChungTu.length == 0) {
        this.$toast.error("Không có dữ liệu để in");
        return;
      }
      if (this.$refs.slectedDSChungTu.getSelectedRecords().length == 0) {
        this.$toast.error("Bạn chưa chọn vật tư cần in");
        return;
      }
      let trangThaiTemp = this.$refs.slectedDSChungTu
        .getSelectedRecords()
        .filter((x) => x.TRANGTHAI == 0);
      if (trangThaiTemp.length > 0) {
        this.$toast.error(
          "Có vật tư đang có trạng thái là chưa có đầu vào, không thể in phiếu! Bạn hãy chuyển trạng thái ở [Nhập/xuất/điều chuyển vật tư]!"
        );
        return;
      }

      this.$confirm(
        `Nếu chứng từ đã có ngày in, số phiếu xuất kho bạn có muốn thay thế không?`,
        "Thông báo",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }
      )
        .then(() => {
          let tempSoPhieu = this.$refs.slectedDSChungTu
            .getSelectedRecords()
            .filter((x) => x.SOPHIEU_IN == null || x.SOPHIEU_IN == undefined);
          if (tempSoPhieu.length > 0) {
            if (!this.txtSoPXK || this.txtSoPXK.trim == "") {
              this.$toast.error("Bạn chưa nhập số phiếu");
              this.loading(false);
              return;
            }
            if (!this.txtNgayPXK || this.txtNgayPXK.trim == "") {
              this.$toast.error("Bạn chưa nhập ngày phiếu xuất kho");
              this.loading(false);
              return;
            }
          }
          this.processInPhieu(1);
        })
        .catch((e) => {
          this.processInPhieu(0);
        });
    },

    async processInPhieu(replace_text) {
      let spx = "";
      let sopx_kho = -1;
      try {
        sopx_kho = parseInt(this.txtSoPXK);
      } catch (e) {
        spx = this.txtSoPXK;
      }

      let distinctValues = this.distinceValues(
        this.$refs.slectedDSChungTu.getSelectedRecords()
      );
      let xdoc_in = [];
      for (let i = 0; i < distinctValues.length; i++) {
        let ds_b = this.dsChiTiet.filter(
          (x) => (x.CHUNGTU_ID = distinctValues[i].CHUNGTU_ID)
        );
        let j = 0;
        for (j = 0; j < ds_b.length; j++) {
          xdoc_in.push({
            CTCT_ID: ds_b[j].CTCT_ID,
            NGAY_PX:
              replace_text == 1
                ? moment(this.txtNgayPXK).format("DD/MM/YYYY")
                : null,
            SO_PX: sopx_kho == -1 ? spx : sopx_kho,
          });
        }
        if ((j + 1) % parseInt(this.txtSLVatTuIn) == 0) {
          let res = await api.xuLyNoInKim(this.axios, {
            chungTuId: distinctValues[i].CHUNGTU_ID,
            soPhieuIn: null,
            ngayIn:
              replace_text == 1
                ? moment(this.txtNgayPXK).format("DD/MM/YYYY")
                : null,
            ds: xdoc_in,
            replace: replace_text,
          });
          if (sopx_kho != -1) {
            sopx_kho += 1;
            this.txtSoPXK = sopx_kho;
          }
        } else if (j == ds_b.length - 1) {
          let res = await api.xuLyNoInKim(this.axios, {
            chungTuId: this.vchungtu_id,
            soPhieuIn: this.txtSoPXK,
            ngayIn: this.txtNgayPXK,
            ds: xdoc_in,
            replace: replace_text,
          });
          if (sopx_kho != -1) {
            sopx_kho += 1;
            this.txtSoPXK = sopx_kho;
          }
        }
      }

      this.loading(true);
      try {
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-qlvt/api/quan-ly-phieu-in/in-phieu-in-kim-nx?extend=pdf`,
          method: "POST",
          responseType: "blob",
          data: {
            chungTuId: this.vchungtu_id,
            soPhieuIn: this.txtSoPXK,
            ngayIn:
              replace_text == 1
                ? moment(this.txtNgayPXK).format("DD/MM/YYYY")
                : null,
            ds: xdoc_in,
            replace: replace_text,
          },
        }).then((response) => {
          previewPrint(response.data);
        });
      } catch (error) {
        this.$toast.error("Có lỗi xảy ra trong quá trình in phiếu!");
      } finally {
        await this.onClickTimKiem();
        this.loading(false);
      }
    },

    distinceValues(data) {
      let temp = [];
      let result = [];
      data.forEach((item) => {
        temp = result.filter(
          (x) => x.CHUNGTU_ID == item.CHUNGTU_ID && x.SOPHIEU == item.SOPHIEU
        );
        if (temp.length == 0) {
          result.push(item);
        }
      });
      return result;
    },

    async In_A4() {
      if (this.dsChungTu.length == 0) {
        this.$toast.error("Không có dữ liệu để in");
        return;
      }

      if (!this.txtLoaiChungTu || this.txtLoaiChungTu.trim == "") {
        this.$toast.error("Chọn loại chứng từ để in A4");
        return;
      }

      if (this.vchungtu_id == 0) {
        return;
      }

      let maBC = "";
      if (this.txtLoaiChungTu == 1) {
        maBC = "PNK";
      } else if (this.txtLoaiChungTu == 2) {
        maBC = this.$auth.getPhanVungID() == 21 ? "PXK_HNI" : "PXK";       
      } else if (this.txtLoaiChungTu == 3) {
        maBC = this.$auth.getPhanVungID() == 21 ? "PCK_HNI" : "PCK";        
      }

      let tmpChungTu = "";
      this.$refs.slectedDSChungTu.getSelectedRecords().forEach((item) => { tmpChungTu += "," + item.CHUNGTU_ID });
      tmpChungTu = tmpChungTu.slice(1);

      try {
        this.loading(true);
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-qlvt/api/in-phieu/getPhieuIn?extend=pdf`,
          method: "POST",
          responseType: "blob",
          data: {
            ma_bc: maBC,
            ds_tieuchi: [
              {
                name: 'vphanvung_id',
                value: this.$auth.getPhanVungID()
              },
              {
                name: 'vchungtu_id',
                value: tmpChungTu
              },
              {
                name: 'vnhanvien',
                value: this.$auth.getNhanVienID()
              },
              {
                name: 'vdonvi',
                value: this.$auth.getDonViID()
              }
            ]
          },
        }).then((response) => {
          previewPrint(response.data);
        });
        this.loading(false);
      } catch (error) {
        this.$toast.error("Có lỗi xảy ra trong quá trình In A4!");
        this.loading(false);
        return;
      }

      this.$confirm("Bạn đã tiến hành in phiếu chưa?", "Thông báo", {
        confirmButtonText: "Có",
        cancelButtonText: "Không",
      })
        .then(async () => {
          this.$refs.slectedDSChungTu.getSelectedRecords().forEach(async (item) => {
            await api.capNhatSoLanIn(this.axios, item.CHUNGTU_ID);
          });
          this.$toast.success("Đã thực hiện cập nhập phiếu in thành công!");
        })
        .catch(() => {
          return;
        });
    },
    onClickInPhieu() {
      if (this.txtKieuIn == 1) {
        this.In_Phieu();
      } else {
        this.In_A4();
      }
    },

    onDetailDataBound(args) {
      args.detailElement.querySelector(".e-grid").ej2_instances[0].dataSource =
        this.dsChiTiet; // assign data source for child grid.
    },

    rowDataBound(args) {
      let CHUNGTU_ID = args.data.CHUNGTU_ID;
      var childrecord = this.dsChiTiet.filter(function (el) {
        return el.CHUNGTU_ID == CHUNGTU_ID;
      });

      if (childrecord.length == 0) {
        args.row.querySelector("td").innerHTML = " ";
        args.row.querySelector("td").className = "e-customizedExpandcell";
      }
    },

    async onTBCheckboxChangedHandler(args) {
      console.log(args);
    },

    selectedRowChanged(data) {
      if (data) {
        this.vchungtu_id = data.CHUNGTU_ID;
      }
    },
  },

  created: async function () {
    EventBus.$on("onTBCheckboxChanged", this.onTBCheckboxChangedHandler);
    this.loading(true);
    await this.LOAD_COMBO();
    await this.LOAD_GRID();
    await this.LOAD_POPUP();
    this.loading(false);
  },
  watch: {},
  destroyed() {
    EventBus.$off("onTBCheckboxChanged", this.onTBCheckboxChangedHandler);
  },
};
</script>
