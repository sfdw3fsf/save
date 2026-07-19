<template src="./frmNhapDieuChinh.html"></template>

<script>
import moment from "moment";
import api from "../api/frmNhapDieuChinhAPI";
import xlsx from "xlsx";

export default {
  components: {},
  data() {
    return {
      columnTab1: [],
      columnTab1Normal: [
        { fieldName: "LOHANG", headerText: "Mã lô hàng", allowFiltering: true },
        { fieldName: "MA_VT", headerText: "Mã vật tư", allowFiltering: true },
        { fieldName: "DVI_TINH", headerText: "ĐVT", allowFiltering: true },
        { fieldName: "SOLUONG", headerText: "Số lượng", allowFiltering: true },
        {
          fieldName: "TONGSL",
          headerText: "Tổng SL",
          allowFiltering: true,
          editType: "numbericedit",
        },
        { fieldName: "DONGIA", headerText: "Đơn giá", allowFiltering: true },
        { fieldName: "TIEN", headerText: "Thành tiền", allowFiltering: true },
        { fieldName: "VAT", headerText: "Thuế", allowFiltering: true },
        {
          fieldName: "LOAI_TBI",
          headerText: "Loại thiết bị",
          allowFiltering: true,
        },
        {
          fieldName: "SPCHIAVT",
          headerText: "Chia số lượng",
          allowFiltering: true,
          allowEditing: true,
          type: "number",
        },
        {
          fieldName: "CTCT_ID",
          headerText: "",
          visible: false,
          isPrimaryKey: true,
        },
      ],
      columnTab1Hide: [
        { fieldName: "LOHANG", headerText: "Mã lô hàng", allowFiltering: true },
        { fieldName: "MA_VT", headerText: "Mã vật tư", allowFiltering: true },
        { fieldName: "DVI_TINH", headerText: "ĐVT", allowFiltering: true },
        { fieldName: "SOLUONG", headerText: "Số lượng", allowFiltering: true },
        {
          fieldName: "TONGSL",
          headerText: "Tổng SL",
          allowFiltering: true,
          allowEditing: true,
          editType: "numbericedit",
        },
        { fieldName: "DONGIA", headerText: "Đơn giá", allowFiltering: true },
        { fieldName: "TIEN", headerText: "Thành tiền", allowFiltering: true },
        { fieldName: "VAT", headerText: "Thuế", allowFiltering: true },
        {
          fieldName: "LOAI_TBI",
          headerText: "Loại thiết bị",
          allowFiltering: true,
        },
        {
          fieldName: "SPCHIAVT",
          headerText: "Chia số lượng",
          allowFiltering: true,
        },
      ],
      columnTab2: [],
      columnTab2Normal: [
        {
          fieldName: "LOHANG",
          headerText: "Mã lô hàng",
          allowFiltering: true,
        },
        {
          fieldName: "MA_VT",
          headerText: "Mã vật tư",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_VT",
          headerText: "Tên vật tư",
          allowFiltering: true,
        },
        {
          fieldName: "DVI_TINH",
          headerText: "ĐVT",
          allowFiltering: true,
        },
        {
          fieldName: "SOLUONG",
          headerText: "Số lượng",
          allowFiltering: true,
          allowEditing: true,
          type: "number",
        },
        {
          fieldName: "DONGIA",
          headerText: "Đơn giá",
          allowFiltering: true,
          allowEditing: true,
          type: "number",
        },
        {
          fieldName: "TINHTRANGTBI_ID",
          headerText: "Tình trạng",
          textAlign: "Left",
          width: 130,
          allowFiltering: false,
          template: this.getCBOTinhTrangTbi(this),
        },
        { fieldName: "TIEN", headerText: "Thành tiền", allowFiltering: true },
        {
          fieldName: "LOAI_TBI",
          headerText: "Loại thiết bị",
          allowFiltering: true,
        },
        {
          fieldName: "",
          headerText: "Xóa",
          allowFiltering: false,
          width: 70,
          commands: [
            {
              type: "Xóa",
              buttonOption: {
                cssClass: "btn btn-second button-not-bg lh14 pad4",
                iconCss: "-ap icon-close",
              },
            },
          ],
          cssClass: "text-center",
        },
        {
          fieldName: "CTCT_ID",
          headerText: "",
          visible: false,
          isPrimaryKey: true,
        },
      ],
      columnTab2Hide: [
        {
          fieldName: "LOHANG",
          headerText: "Mã lô hàng",
          allowFiltering: true,
          allowEditing: true,
        },
        {
          fieldName: "MA_VT",
          headerText: "Mã vật tư",
          allowFiltering: true,
          allowEditing: true,
        },
        {
          fieldName: "TEN_VT",
          headerText: "Tên vật tư",
          allowFiltering: true,
          allowEditing: true,
        },
        {
          fieldName: "DVI_TINH",
          headerText: "ĐVT",
          allowFiltering: true,
          allowEditing: true,
        },
        {
          fieldName: "SOLUONG",
          headerText: "Số lượng",
          allowFiltering: true,
        },
        {
          fieldName: "DONGIA",
          headerText: "Đơn giá",
          allowFiltering: true,
        },
        { fieldName: "TIEN", headerText: "Thành tiền", allowFiltering: true },
        {
          fieldName: "LOAI_TBI",
          headerText: "Loại thiết bị",
          allowFiltering: true,
          allowEditing: true,
        },
        {
          fieldName: "THAOTAC",
          headerText: "Xóa",
          allowFiltering: false,
          width: 70,
          commands: [
            {
              type: "Xóa",
              buttonOption: {
                cssClass: "btn btn-second button-not-bg lh14 pad4",
                iconCss: "-ap icon-close",
              },
            },
          ],
          cssClass: "text-center",
        },
      ],

      cboTinhTrangTbi: [],
      dt: [],
      grdChiTietDieuChinh: [],
      grdChiTietVT: [],
      grdChungTu: [],
      grdChiTietVT_2: [],
      cboDenKho: [],
      vchungtu_id: 0,
      vchungtu_id_v2: 0,
      vchungtu_gan: 0,
      vctct_id: 0,
      kho_nhan: 0,
      kho_giao: 0,
      xacnhan: false,
      sophieu: "",
      vma_md: "",
      tag: 3, //TODO: sửa lại
      xacnhan: null,

      selectedChungTu: null,
      selectedChiTietVT: null,
      selectedChiTietDieuChinh: null,
      selectedChiTietVT_V2: null,

      isTab1: true,
      isTab2: false,
      isTab3: false,
      visibleTab1: true,
      visibleTab2: true,
      visibleTab3: true,
      visibleNhapExcel: true,
      visibleFileMau: true,
      visibleGoChungTu: false,

      dateFormat: "dd/MM/yyyy",
      txtDenKho: "",
      txtNoiDungChungTu: "",
      txtSoChungTu: "",
      txtMaLoHang: "",
      txtTuNgay: "",
      txtDenNgay: "",
      cbTuNgay: false,
      cbDenNgay: false,
      animationSettings: { effect: "Zoom" },
      target: ".main-wrapper",
      editSettings: { allowEditing: true, mode: "Batch" },
    };
  },
  validations: {},
  watch: {},
  created: async function () {
    // Đức rem move sang openNhapDieuChinh
    // this.loading(true);
    // this.frmNhapDieuChinh_Load();
    // this.loading(false);
  },
  mounted() { },
  methods: {
    tsbtnFileMau() {
      if (this.grdChiTietDieuChinh.length == 0) {
        this.$toast.error("Không có dữ liệu để xuất excel !");
        return;
      }
      this.loading(true);

      var NameFile = "fileDSNhapDieuChinh.xls";
      this.grdChiTietDieuChinh.forEach((e) => {
        ColExcel.push({
          CTCT_ID: e.CTCT_ID,
          LOHANG: e.LOHANG,
          LOHANG_CHA: e.LOHANG_CHA,
          MA_VT: e.MA_VT,
          TEN_VT: e.TEN_VT,
          SOLUONG: e.SOLUONG,
          DONGIA: e.DONGIA,
          TIEN: e.TIEN,
          LOAI_TBI: e.LOAI_TBI,
          DVI_TINH: e.DVI_TINH,
          LOAITBI_ID: e.LOAITBI_ID,
          TINHTRANG: e.TINHTRANG,
          VATTU_ID: e.VATTU_ID,
          MAKHOTD: e.MAKHOTD,
          LOAIKHO: e.LOAIKHO,
          CHATLUONG_ID: e.CHATLUONG_ID,
          DONGTBI_ID: e.DONGTBI_ID,
          HETHONG_ID: e.HETHONG_ID,
        });
      });
      var dataExcel = xlsx.utils.json_to_sheet(ColExcel);
      var wb = xlsx.utils.book_new();
      xlsx.utils.book_append_sheet(wb, dataExcel, "Sheet1");
      xlsx.writeFile(wb, NameFile);
      this.loading(false);
    },
    async openNhapDieuChinh() {
      this.loading(true);
      this.frmNhapDieuChinh_Load();
      this.$refs.popupNhapDieuChinh.show();
      this.loading(false);
    },
    LAMMOI() {
      this.grdChungTu = [];
      this.grdChiTietVT = [];
      this.grdChiTietDieuChinh = [];
      this.grdChiTietVT_2 = [];
      this.txtSoChungTu = "";
      this.txtMaLoHang = "";
    },

    async Load_ds_chungtu() {
      let kieu = null;
      if (this.tag == 1) {
        kieu = this.txtMaLoHang;
      }

      let from = null;
      if (this.cbTuNgay) {
        from = this.txtTuNgay;
      }

      let to = null;
      if (this.cbDenNgay) {
        to = this.txtDenNgay;
      }

      let res = await api.layDSChungTuV3(this.axios, {
        soChungTu: this.txtSoChungTu,
        maLoHang: kieu,
        tuNgay: from,
        denNgay: to,
      });
      let ds = res.data.data;
      if (this.vchungtu_id_v2 > 0) {
        this.grdChungTu = ds.filter((x) => x.CHUNGTU_ID == this.vchungtu_id);
      } else {
        this.grdChungTu = ds;
      }

      this.LAY_DS_VTCT_SERIAL();

      if (ds.length == 0) {
        this.$toast.error("Không tìm thấy chứng từ nào phù hợp");
      }
    },

    async frmNhapDieuChinh_Load() {
      this.loading(true);
      console.log(this.columnTab2Nomarl);
      this.columnTab1 = this.columnTab1Normal;
      this.columnTab2 = this.columnTab2Normal;

      this.txtDenNgay = new Date();
      this.txtTuNgay = new Date(Date.now() - 7 * 24 * 60 * 60 * 1000);
      if (this.tag == 2) {
        let res = await api.dsTinhTrangThietBi(this.axios);
        this.cboTinhTrangTbi = res.data.data;
      } else if (this.tag == 1) {
        this.visibleTab1 = false;
        this.visibleTab2 = false;
        this.visibleTab3 = false;
        this.visibleGoChungTu = true;
      } else if (this.tag == 3) {
        this.visibleTab3 = false;
        if (this.vma_md == "NV_KHAC_BQLDA" || this.vma_md == "NV_DAUTU_BQLDA") {
          this.columnTab1 = this.columnTab1Hide;
          this.columnTab2 = this.columnTab2Hide;
          this.visibleFileMau = false;
          this.visibleNhapExcel = false;
          this.txtMaLoHang = this.vma_md;
        }
        let res = await api.dsTinhTrangThietBi(this.axios);
        this.cboTinhTrangTbi = res.data.data;
      } else if (this.tag == 4) {
        this.visibleTab3 = false;
        this.visibleTab2 = false;
        this.txtMaLoHang = this.vma_md;
        let req = await api.getDSKhoTheoNV(this.axios, { nhanVienId: this.$auth.getNhanVienID(), donViId: this.$auth.getDonViID() });
        let res = req.data.data;
        let dsKho = res.filter(item => item.TRANGTHAI.toString() == "1" && item.LOAIKHO_ID.toString() == "5");
        this.cboDenKho = dsKho.length > 0 ? dsKho : [];
        this.onClickTimKiem();
      }
      this.loading(false);
    },

    async LAY_DS_VTCT_SERIAL() {
      let res = await api.layDSVatTuChungTuSerial(this.axios, this.vchungtu_id);
      let ds = res.data.data;
      if (ds.length > 0) {
        if (this.tag == 1) {
          this.grdChiTietVT_2 = ds;
        } else if (this.tag == 2) {
          this.grdChiTietVT = ds;
        } else if (this.tag == 3) {
          this.grdChiTietVT = ds;
          this.grdChiTietVT.forEach((element) => {
            element.SPCHIAVT = 1;
          });
        }
      }
    },

    async LAY_DS_CHITIET_CT(index) {
      let ldr = {};
      let dr_v = {};
      let ds_dk = this.grdChiTietVT;
      let bltrung = false;
      let blcheck = false;
      let ds = this.grdChiTietDieuChinh;

      if (this.ds.length == 0) {
        ds = ds_dk;
      }
      var soluong = this.selectedChiTietVT.SPCHIAVT;
      var soluong_goc = this.selectedChiTietVT.SOLUONG;
      var chia_het = soluong_goc / soluong;
      var chia_du = soluong_goc % soluong;
      for (let i = 0; i < soluong; i++) {
        dr_v = this.selectedChungTu;
        ldr.CTCT_ID = dr_v.CTCT_ID;
        ldr.LOHANG_CHA = dr_v.LOHANG;
        ldr.MA_VT = dr_v.MA_VT;
        ldr.TEN_VT = dr_v.TEN_VT;
        ldr.DVI_TINH = dr_v.DVI_TINH;
        ldr.LOAI_TBI = dr_v.LOAI_TBI;
        ldr.VATTU_ID = dr_v.VATTU_ID;
        if (this.vma_md == "N.DCDG" || this.vma_md == "SC_BH_DONGIA") {
          ldr.SOLUONG = 0;
          ldr.DONGIA = 0;
          ldr.TIEN = 0;
        } else if (this.vma_md == "N.TTCL") {
          ldr.DONGIA = dr_v.DONGIA;
          if (i == 0) ldr.SOLUONG = chia_het + chia_du;
          else ldr.SOLUONG = chia_het;
          ldr.TIEN = Convert.ToInt32(ldr.SOLUONG * dr_v.DONGIA);
        } else if (
          this.vma_md == "NV_DAUTU_BQLDA" ||
          this.vma_md == "NV_KHAC_BQLDA"
        ) {
          ldr.SOLUONG = soluong_goc;
          ldr.DONGIA = dr_v.DONGIA;
          ldr.TIEN = ldr.SOLUONG * ldr.DONGIA;
        }
        ldr.CHUNGTU_ID = dr_v.CHUNGTU_ID;
        ldr.LOAITBI_ID = dr_v.LOAITBI_ID;
        ldr.MAKHOTD = dr_v.MAKHOTD;
        ldr.LOAIKHO = dr_v.LOAIKHO;
        ldr.CHATLUONG_ID =
          dr_v.CHATLUONG_ID == "" || dr_v.CHATLUONG_ID == null
            ? 0
            : dr_v.CHATLUONG_ID;
        ldr.HETHONG_ID =
          dr_v.HETHONG_ID == "" || dr_v.HETHONG_ID == null
            ? 0
            : dr_v.HETHONG_ID;
        ldr.DONGTBI_ID =
          dr_v.DONGTBI_ID == "" || dr_v.DONGTBI_ID == null
            ? 0
            : dr_v.DONGTBI_ID;
        for (const i in ds) {
          if (ds[i].CHUNGTU_ID != ldr.CHUNGTU_ID) {
            bltrung = true;
            break;
          }
        }
        if (bltrung) {
          this.$toast.error("Chỉ cho phép điều chỉnh 1 chứng từ xuất kho!");
          return;
        }
        // code C# chuối vl, ds = this.grdChiTietDieuChinh
        ds.push(ldr);
        this.grdChiTietDieuChinh = ds;
      }
    },

    onChangeSelectedChungTu(data) {
      this.selectedChungTu = data;
      if (data != null) {
        this.vchungtu_id = data.CHUNGTU_ID;
        this.txtSoChungTu = data.SOPHGIEU;
        this.LAY_DS_VTCT_SERIAL();
        this.kho_giao = data.KHO_GIAO_ID;
        this.kho_nhan = data.KHO_NHAN_ID;
      } else {
        this.txtSoChungTu = "";
        this.grdChiTietVT = [];
        this.grdChiTietDieuChinh = [];
        this.grdChiTietVT_2 = [];
        this.vchungtu_id = 0;
      }
    },

    onChangeSelectedChiTietVT(data) {
      this.selectedChiTietVT = data;
      if (data != null) {
        this.vctct_id = data.CTCT_ID;
      }
    },

    soChungTuEnter() {
      this.Load_ds_chungtu();
    },

    onClickTimKiem() {
      this.Load_ds_chungtu();
    },

    onClickLamMoi() {
      this.LAMMOI();
    },

    maLoHangEnter() {
      this.Load_ds_chungtu();
    },

    onClickCBDenNgay() {
      this.cbDenNgay = !this.cbDenNgay;
    },
    onClickCBTuNgayNgay() {
      this.cbTuNgay = !this.cbTuNgay;
    },

    async onClickGoChungTu() {
      this.$confirm(
        `Bạn có muốn gỡ chứng từ xuất ra khỏi chứng từ nhập bạn đang chọn không?`,
        "Thông báo",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
        }
      )
        .then(async () => {
          let res = await api.goChungTu(this.axios, this.vchungtu_gan);
          if (res.data.data.includes("OK")) {
            this.$toast.success("Gỡ chứng từ thành công!");
            this.txtSoChungTu = "";
            this.Load_ds_chungtu();
            this.xacnhan = true;
            this.$parent.XacNhan_frmNhapDieuChinh()
          } else {
            this.$toast.error(res.data.data);
          }
        })
        .catch(() => {
          return;
        });
    },

    async onClickXacNhan() {
      console.log(this.tag)
      if (this.tag == 1) {
        let res = await api.layNgayCT(this.axios, this.vchungtu_gan);
        if (res.data.data.length == 0) {
          this.$toast.error("KHông lây được ngày chứng từ của chứng từ nhập");
          return;
        }
        let ngaynhap = res.data.data.ngayCT;
        if (
          moment(this.selectedChungTu.ngayCT, "dd/MM/yyyy") >
          moment(ngaynhap, "dd/MM/yyyy hh:mm:ss")
        ) {
          this.$toast.error(
            "Chứng từ nhập phải có ngày chứng từ lớn hơn chứng từ xuất"
          );
          return;
        }
        await api.capNhatChungTuCha(this.axios, {
          chungTuGanId: this.selectedChungTu.CHUNGTU_ID,
          chungTuId: this.vchungtu_gan,
        });
      } else if (this.tag == 2) {
        let gr_chitiet = this.grdChiTietVT;
        let gr_chia = this.grdChiTietDieuChinh;
        let sum = 0;
        let sl = 0;
        let ctct_id = 0;
        let vt = "";
        gr_chitiet.forEach((i) => {
          if (i.SPCHIAVT > 0) {
            gr_chia.forEach((j) => {
              if (i.CTCT_ID == j.CTCT_ID) {
                if (j.SOLUONG < 1) {
                  this.$toast.error("Số lượng phải lớn hơn 0");
                  return;
                }
                sum += j.SOLUONG;
                ctct_id = j.CTCT_ID;
              }
            });
            if (ctct_id == i.CTCT_ID) {
              if (sum != i.SOLUONG) {
                vt += i.TEN_VT;
                sl += i.SOLUONG;
                this.$toast.error(
                  "[" +
                  vt +
                  "] đã chia ra: [" +
                  sum +
                  "] phải bằng số lượng cũ: [" +
                  sl +
                  "]"
                );
                sum = 0;
                return;
              }
              sum = 0;
            }
          } else {
            this.$toast.error("Số lượng chia phải lớn hơn 0");
            return;
          }
        });
        if (this.vma_md == "N.TTCL") {
          let tt_check = (this.gr_chia = ds.filter((x) => x.TINHTRANG == null));
          if (tt_check.length > 0) {
            this.$toast.error("Bạn chưa chọn hết tình trạng cho vật tư !");
            return;
          }
        }
        this.dt = this.grdChiTietDieuChinh;
      } else if (this.tag == 3) {
        this.dt = this.grdChiTietDieuChinh;
        this.sophieu = this.txtSoChungTu;
        this.xacnhan = true;
        this.$parent.XacNhan_frmNhapDieuChinh()
      } else if (this.tag == 4) {
        if (this.txtDenKho.length == 0) {
          this.$toast.error("Chưa chọn kho đến !");
          return;
        }

        this.$confirm(
          `Bạn thực sự muốn nhập vật tư thay đổi về kho` + this.txtDenKho + `?`,
          "Thông báo",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
          }
        )
          .then(async () => {
            let dsTemp = [];
            this.grdChiTietVT_2.forEach((e) => {
              dsTemp.push({
                CTCT_ID: e.CTCT_ID,
                MA_VT: e.MA_VT
              });
            });

            let data = {
              ds: dsTemp,
              chungTuId: this.vchungtu_id,
              chungTuDaXoa: this.vchungtu_luu_tam,
              khoNhanId: this.txtDenKho,
              noiDung: this.txtNoiDungChungTu
            }
            let res = await api.sinhChungTuDieuChinhPSIS(this.axios, data);
            if (res.data.data.includes("OK")) {
              this.$toast.success("Cập nhật thành công!!");
              this.xacnhan = true;
              this.sophieu = thí.txtSoChungTu;
            } else {
              this.$toast.error(res.data.data);
            }
          })
          .catch(() => {
            return;
          });
      }
    },

    getCBOTinhTrangTbi(parent) {
      return function () {
        return {
          template: {
            components: {},
            template: `
                           <ejs-combobox                           
                            :dataSource="data.TINHTRANGTBI"                        
                            :fields="{value:'tinhTrangTBiId', text: 'tinhTrangTBi'}"                   
                            :change="valueChange"
                            v-model="value"
                        ></ejs-combobox>
                        `,
            data() {
              return {
                parent: parent, //this
                data: {}, //rowData
                value: "",
              };
            },
            computed: {},
            mounted() {
              this.value = `${this.data.TINHTRANGTBI_ID}`;
            },
            methods: {
              valueChange(e) { },
            },
            watch: {
              value(newVal) {
                let obj = this.parent.gridChiTietVT.dtChiTietVT.find(
                  (item) => item.STT == this.data.STT
                );
                obj.TINHTRANGTBI_ID = this.value;
              },
            },
          },
        };
      };
    },
    async editGridChiTietVT_v2(arg) {
      let grvChiTietVT_v2 = this.$refs.grvChiTietVT_v2;
      let changes = grvChiTietVT_v2.getBatchChanges();

      if (changes.changedRecords.length > 0) {
        return;
      }
      if (arg.columnName == "SOLUONG") {
        if (changes.changedRecords[0].SOLUONG <= 0) {
          changes.changedRecords[0].SOLUONG = 0;
          this.$toast.error("Số lượng phải lớn hơn 0 !");
          return;
        } else {
          changes.changedRecords[0].TIEN =
            changes.changedRecords[0].SOLUONG *
            changes.changedRecords[0].DONGIA;
        }
      } else if (arg.columnName == "MA_VT") {
        if (
          changes.changedRecords[0].MA_VT == null ||
          changes.changedRecords[0].MA_VT == ""
        ) {
          this.$toast.error("Mã vật tư không được để trống");
          return;
        } else {
          let res = await api.layDSChungTuV3(
            this.axios,
            changes.changedRecords[0].MA_VT
          );
          let ds = res.data.data;
          if (ds.length == 0) {
            this.$toast.error("Mã vật tư không tồn tại !");
            return;
          }
          changes.changedRecords[0].TEN_VT = ds[0].tenVatTu;
        }
      }

      grvChiTietVT_v2.batchUpdate(changes);
      this.$refs.grvChiTietVT_v2.grid.refreshColumns();
    },
    async editGridChiTietDieuChinh(arg) {
      let grvChiTietDieuChinh = this.$refs.grvChiTietDieuChinh;
      let changes = grvChiTietDieuChinh.getBatchChanges();
      var CTCT_ID = this.vctct_id;
      if (changes.changedRecords.length > 0) {
        return;
      }
      if (arg.columnName == "SOLUONG") {
        if (changes.changedRecords[0].SOLUONG < 0) {
          changes.changedRecords[0].SOLUONG = 0;
          changes.changedRecords[0].TIEN = 0;
          changes.changedRecords[0].DONGIA = 0;
        } else {
          var sum = 0;
          this.grdChiTietDieuChinh.forEach((e) => {
            if (e.CTCT_ID == CTCT_ID) {
              sum += e.SOLUONG;
            }
          });
          changes.changedRecords[0].TONGSL =
            this.selectedChiTietVT.SOLUONG - sum;
          changes.changedRecords[0].TIEN =
            this.selectedChiTietVT.SOLUONG * this.selectedChiTietVT.DONGIA;
        }
      } else if (arg.columnName == "DONGIA") {
        if (changes.changedRecords[0].DONGIA < 0) {
          changes.changedRecords[0].TIEN = 0;
          changes.changedRecords[0].DONGIA = 0;
        } else {
          changes.changedRecords[0].TIEN =
            this.selectedChiTietVT.SOLUONG * this.selectedChiTietVT.DONGIA;
        }
      }
      grvChiTietDieuChinh.batchUpdate(changes);
      this.$refs.grvChiTietDieuChinh.grid.refreshColumns();
    },
    async editGridChiTietVT(arg) {
      let grvChiTietVT = this.$refs.grvChiTietVT;
      let changes = grvChiTietDieuChinh.getBatchChanges();
      var ctct_id_temp = this.vctct_id;
      if (changes.changedRecords.length > 0) {
        return;
      }
      var is;
      if (arg.columnName == "SPCHIAVT") {
        if (
          changes.changedRecords[0].SPCHIAVT > 0 &&
          changes.changedRecords[0].SOLUONG
        ) {
          var gr_chia = this.grdChiTietDieuChinh.filter(function (el) {
            return el.CTCT_ID != ctct_id_temp;
          });
          if (gr_chia.length > 0) {
            this.grdChiTietDieuChinh = this.gr_chia;
          } else {
            this.grdChiTietDieuChinh = [];
          }
          grvChiTietVT.batchUpdate(changes);
          this.LAY_DS_CHITIET_CT();
        } else {
          changes.changedRecords[0].SPCHIAVT = 0;
          grvChiTietVT.batchUpdate(changes);
        }
      }
    },
    async onChangeFile(e) {
      const files = e.target.files;
      this.duongDan = e.target.files[0].name;
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError("File excel không đúng định dạng!");
      }
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          console.log(ws);
          var res = await api.kiemTraFileDieuChinh(this.axios, {
            chungTuId: this.vchungtu_id,
            lyDoTraId: ws,
          });
          if (!res.data.data.includes("OK")) {
            this.$toast.error(res.data.data);
            return;
          }

          this.grdChiTietDieuChinh = [];
          this.ws.forEach((element) => {
            if (element.SOLUONG != null && element[DONGIA] != null) {
              element.TIEN = element.SOLUONG * element[DONGIA];
            }
          });
          this.grdChiTietDieuChinh = ws;
          this.changeTab(1);
        } catch (error) {
          return this.$root.toastError("Kiểm tra dữ liệu file đầu vào!");
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    onGridDeleteClicked(arg) {
      this.grdChiTietDieuChinh = this.grdChiTietDieuChinh.filter(
        (item) => item.CTCT_ID !== arg.rowData.CTCT_ID
      );
    },
    changeTab(data) {
      if (data == 1) {
        this.isTab1 = true;
        this.isTab2 = false;
        this.isTab3 = false;
      } else if (data == 2) {
        this.isTab1 = false;
        this.isTab2 = true;
        this.isTab3 = false;
      } else if (data == 3) {
        this.isTab1 = false;
        this.isTab2 = false;
        this.isTab3 = true;
      }
    },
  },
  computed: {},
};
</script>
