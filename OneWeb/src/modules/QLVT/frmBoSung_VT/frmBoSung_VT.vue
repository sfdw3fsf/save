<template src='./frmBoSung_VT.html'></template>
<style scoped src='./frmBoSung_VT.scss'></style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import BoSungVatTuAPI from "../api/BoSungVatTuAPI";
import moment from "moment";

export default {
  components: { breadcrumb, moment },
  name: "BoSungVatTu",
  mounted() {},
  computed: {},
  data() {
    return {
      dateFormat: "dd/MM/yyyy",
      checked: "checkDanhBa",
      checkDanhBa: "checkDanhBa",
      checkHopDong: "checkHopDong",
      timer: null,

      visibleTenTB: false,
      visibleMaTB: false,
      visibleNhapMoi: false,
      visibleCapNhat: false,
      visibleXoa: false,
      visibleHuyBo: false,

      grvVatTuBoSung: [],

      cboVatTu: [],
      cboDonViTinh: [],
      cboDichVuVT: [],
      cboKho: [],
      cboKieuTB: [],
      cboTrangBi: [],
      cboLoHang: [],
      cboTinhTrang: [],

      txtMaTB: "",
      txtTenTB: "",
      txtSerialNumber: "",
      txtSerialGP: "",
      txtGhiChu: "",
      txtVatTuId: "",
      txtDonViTinh: "",
      txtDichVuVTId: "",
      txtKho: "",
      txtKieuTB: "",
      txtTrangBi: "",
      txtLoHang: "",
      txtTinhTrang: "",
      txtNgaySuDung: "",
      txtSoLuong: "",

      Ko_Nhap_Dung_Kho: false,
      vloai_td: null,
      vma_tb: "",
      vdichvuvt_id: "",
      vsudungvt_id: null,
      vthietbi_id: null,
      vdonvi_id: null,
      vselected: null,
      vthuebao_id: null,
      vngay_sd: "",
      vsua: "",
      vSP: 0,
      vSN: 0,

      rowSelected: null,
      checkboxSua: [],
      columnsCheckboxDayDu: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.checkboxSua"
                                    :value="data.VATTU_ID" 
                                    style="pointer-events: none;"/>
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
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
    };
  },

  created: async function () {
    await this.onCreate123();
    this.timerTick();
  },

  methods: {
    async onCreate123() {
      this.loading(true);
      let tsMacDinh = await BoSungVatTuAPI.getKO_KT_KHO_DV(this.axios);
      if (tsMacDinh.data.data != 0) {
        this.Ko_Nhap_Dung_Kho = true;
      }

      this.visibleTenTB = false;
      this.visibleMaTB = true;
      this.txtNgaySuDung = this.convertDate(new Date());
      this.setButton(0);
      this.vloai_td = -1;
      await this.HT_TT_DM();

      if (this.vma_tb) {
        this.txtDichVuVTId = this.vdichvuvt_id;
        this.HT_TT();
      }

      this.loading(false);
    },

    async HT_TT_DM() {
      let res = await BoSungVatTuAPI.layDanhMuc(this.axios);
      let danhMuc = res.data.data;
      this.cboVatTu = danhMuc.P_VATTU;
      this.cboDonViTinh = danhMuc.P_DONVITINH;
      this.cboDichVuVT = danhMuc.P_DICHVU_VT;
      if (this.cboDichVuVT.length > 0) {
        this.txtDichVuVTId = this.cboDichVuVT[0].DICHVUVT_ID;
      }
      this.cboKho = danhMuc.P_KHO;
      this.cboKieuTB = danhMuc.P_KIEU_TBI;
      this.cboTrangBi = danhMuc.P_TRANGBI;
      this.cboLoHang = danhMuc.P_LOHANG;
      this.cboTinhTrang = danhMuc.P_TINHTRANG;
    },

    async HT_TT() {
      this.loading(true);
      let loai = 1;
      if (this.checked == "checkDanhBa") {
        loai = 2;
      }

      let res = await BoSungVatTuAPI.layTTVatTuThueBao(this.axios, {
        maTB: this.txtMaTB != null ? this.txtMaTB.trim() : this.txtMaTB,
        dichVuVTId: this.txtDichVuVTId,
        loaiId: loai,
      });

      this.vselected = 0;
      let dsThueBao = res.data.data.P_THUEBAO[0];
      if (dsThueBao) {
        this.visibleTenTB = true;
        this.visibleMaTB = true;
        this.txtTenTB = dsThueBao.TEN_TB;
        this.vdonvi_id = dsThueBao.DONVI_ID;
        if (!this.Ko_Nhap_Dung_Kho) {
          this.cboKho = this.cboKho.filter((x) => x.DONVI_ID == this.vdonvi_id);
        }
        this.vthuebao_id = dsThueBao.THUEBAO_ID;
        if (dsThueBao.NGAY_SD != null) {
          this.vngay_sd = dsThueBao.NGAY_SD;
        }

        let dsVatTu = res.data.data.P_VATTU;
        this.checkboxSua = [];
        if (dsVatTu.length) {
          this.grvVatTuBoSung = dsVatTu;
          for (let e of this.grvVatTuBoSung) {
            if (e.BS_VT == 1) {
              this.checkboxSua.push(e.VATTU_ID);
            }
          }
          console.log(dsVatTu, " = dsVatTu");
          if (this.vloai_td != 1) {
            this.setButton(1);
            this.vloai_td = 2;
          }
          if (this.vselected != 1) this.HH_TT_CT();
        } else {
          this.setButton(0);
          this.vloai_td = -1;
          this.grvVatTuBoSung = [];
          this.txtSerialNumber = "";
          this.txtGhiChu = "";
          this.txtNgaySuDung = this.convertDate(new Date());
          this.vthietbi_id = 0;
          this.vsudungvt_id = 0;
          this.txtKho = "";
          this.txtVatTuId = "";
          this.txtLoHang = "";
          this.txtTrangBi = "";
          this.txtTinhTrang = "";
          this.txtKieuTB = "";
          this.txtDonViTinh = "";
        }
      } else {
        this.$toast.error("Không tìm thấy thông tin thuê bao!");
        await this.clear();
        this.grvVatTuBoSung = [];
        this.visibleTenTB = false;
        this.visibleMaTB = true;
      }
      this.loading(false);
    },

    async onClickLayTT() {
      if (this.txtMaTB.trim() == "") {
        this.$toast.error("Bạn chưa nhập số máy/account khách hàng!");
      } else {
        this.HT_TT();
      }
    },

    async onClickXoa() {
      this.loading(true);
      const dsXoa = this.$refs.danhSachDuocChon.getSelectedRecords() || [];
      if (dsXoa.length == 0) {
        this.$confirm(`Bạn muốn xóa vật tư ko?`, "Thông báo", {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
        })
          .then(async () => {
            let loai = 1;
            if (this.checked == "checkDanhBa") {
              loai = 2;
            }
            let body = {
              thueBaoId: this.vthuebao_id,
              vatTuId: this.txtVatTuId,
              khoId: this.txtKho,
              ngaySD: this.txtNgaySuDung,
              kieuTbi: this.txtKieuTB,
              donViTinh: this.txtDonViTinh,
              tinhTrangTbi: this.txtTinhTrang,
              trangBi: this.txtTrangBi,
              serialGP: this.txtSerialGP,
              ghiChu: this.txtGhiChu,
              donGia: this.txtLoHang,
              thietBiId: this.vthietbi_id,
              suDungVTId: this.vsudungvt_id,
              loaiId: loai,
              loaiTD: 3,
              serialNumber: this.txtSerialNumber != null ? this.txtSerialNumber.trim() : this.txtSerialNumber,
            };

            try {
              let res = await BoSungVatTuAPI.dayVatTuBoSung(this.axios, body);
              this.$toast.success("Xóa dữ liệu vật tư thành công!");
              this.HT_TT();
              if (this.grvVatTuBoSung.length > 0) {
                this.HH_TT_CT();
              }
            } catch (error) {
              this.$toast.error(error.data.message);
            }
          })
          .catch(() => {
            return;
          });
      } else {
        this.$confirm(
          `Bạn muốn xóa danh sách vật tư đã chọn ko?`,
          "Thông báo",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
          }
        )
          .then(async () => {     
            let loai = 1;
            if (this.checked == "checkDanhBa") {
              loai = 2;
            }
            dsXoa.forEach(async (item) => {
              let body = {
                thueBaoId: item.THUEBAO_ID,
                vatTuId: item.VATTU_ID,
                khoId: item.KHO_ID,
                ngaySD: this.vngay_sd,
                kieuTbi: item.KIEUTBI_ID,
                donViTinh: item.DVITINH_ID,
                tinhTrangTbi: item.TINHTRANGTBI_ID,
                trangBi: item.TRANGBI_ID,
                serialGP: item.SERIAL_GP,
                ghiChu: item.GHICHU,
                donGia: 0,
                thietBiId: item.THIETBI_ID,
                suDungVTId: item.SDVT_ID,
                loaiId: loai,
                loaiTD: 3,
                serialNumber: item.SERIAL,
              };
              //let res = await BoSungVatTuAPI.dayVatTuBoSung(this.axios, body);

              try {
                let res = await BoSungVatTuAPI.dayVatTuBoSung(this.axios, body);
                this.$toast.success("Xóadữ liệu vật tư " + body.vatTuId +" thành công!");
              } catch (error) {
                this.$toast.error(error.data.message);
              }

            });
            //this.$toast.success("Xóa dữ liệu vật tư thành công!");
            this.HT_TT();
            if (this.grvVatTuBoSung.length > 0) {
              this.HH_TT_CT();
            }
          })
          .catch(() => {
            return;
          });
      }
      this.loading(false);
    },

    async onClickNhapMoi() {
      this.clear();
      this.setButton(0);
      this.visibleTenTB = false;
      this.visibleMaTB = true;
      this.grvVatTuBoSung = [];
      this.vloai_td = 1;
    },

    async onEnterAccount() {
      if (this.vma_tb != "" && this.vma_tb != this.txtMaTB.trim()) {
        this.vloai_td = -1;
      }
      this.HT_TT();
      this.vma_tb = this.txtMaTB;
    },

    async onClickHuyBo() {
      if (this.grvVatTuBoSung.length > 0) {
        this.HH_TT_CT();
        this.setButton(1);
        this.vloai_td = 2;
      } else {
        this.clear();
        this.setButton(0);
        this.vloai_td = -1;
      }
    },

    async clear() {
      this.txtMaTB = "";
      this.txtTenTB = "";
      this.txtSoLuong = "";
      this.txtSerialNumber = "";
      this.txtGhiChu = "";
      this.txtNgaySuDung = "";

      this.vthietbi_id = 0;
      this.vsudungvt_id = 0;

      this.txtKho = "";
      this.txtVatTuId = "";
      this.txtLoHang = "";
      this.txtTrangBi = "";
      this.txtTinhTrang = "";
      this.txtKieuTB = "";
      this.txtDonViTinh = "";
    },

    HH_TT_CT() {
      if (this.grvVatTuBoSung.length < 0) return;
      if (this.vloai_td == 1) return;
      if (!this.rowSelected) return;

      this.vselected = 1;
      this.txtVatTuId = this.rowSelected.VATTU_ID;
      this.txtKho = this.rowSelected.KHO_ID;
      this.txtLoHang = this.rowSelected.LOHANG;
      this.txtNgaySuDung = this.rowSelected.NGAY_SD;
      this.txtKieuTB = this.rowSelected.KIEUTBI_ID;
      this.txtTinhTrang = this.rowSelected.TINHTRANGTBI_ID;
      this.txtDonViTinh = this.rowSelected.DVITINH_ID;
      this.txtTrangBi = this.rowSelected.TRANGBI_ID;
      this.txtSerialNumber = this.rowSelected.SERIAL;
      this.txtSerialGP = this.rowSelected.SERIAL_GP;
      this.vsua = this.rowSelected.BS_VT;
      this.txtGhiChu = this.rowSelected.GHICHU;
      this.vthietbi_id = this.rowSelected.THIETBI_ID;
      this.vsudungvt_id = this.rowSelected.SDVT_ID;
      this.vSP = 0;
      this.vSN = 0;
    },

    async onChangeGrvVatTuBS(data) {
      if (data) {
        this.rowSelected = data;
        this.HH_TT_CT();
      }
    },

    setButton(kieu) {
      if (kieu == 0) {
        this.visibleNhapMoi = false;
        this.visibleCapNhat = true;
        this.visibleXoa = false;
        this.visibleHuyBo = true;
      } else {
        this.visibleNhapMoi = true;
        this.visibleCapNhat = true;
        this.visibleXoa = true;
        this.visibleHuyBo = true;
      }
    },

    async KT_DL() {
      if (this.vsua == 0 && this.vloai_td == 2) {
        this.$toast.error("Bạn không được phép sửa dữ liệu này!");
        return false;
      }

      if (this.txtVatTuId == "") {
        this.$toast.error("Bạn chưa chọn vật tư!");
        return false;
      }

      if (this.txtKho == "") {
        this.$toast.error("Bạn chưa chọn kho!");
        return false;
      }

      if (this.txtDonViTinh == "") {
        this.$toast.error("Bạn chưa chọn đơn vị tính!");
        return false;
      }

      if (this.txtKieuTB == "") {
        this.$toast.error("Bạn chưa chọn kiểu thiết bị!");
        return false;
      }

      if (this.txtTrangBi == "") {
        this.$toast.error("Bạn chưa chọn trang bị!");
        return false;
      }

      if (this.txtTinhTrang == "") {
        this.$toast.error("Bạn chưa chọn tình trạng thiết bị!");
        return false;
      }
      console.log("this.txtSerialGP  == ", this.txtSerialGP);
      let res = await BoSungVatTuAPI.kiemTraVatTuBoSungV2(this.axios, {
        matKT: "kt_serial",
        thamSo:
          this.txtSerialGP != null ? this.txtSerialGP.trim() : this.txtSerialGP,
        thueBaoId: this.vthuebao_id,
        vatTuId: this.txtVatTuId,
        loaiTD: this.vloai_td,
        sdvtId: this.vsudungvt_id,
        serialNumber: this.txtSerialNumber,
      });

      if (res.data.data[0].RESULT != "ok") {
        this.$toast.error("Kiểm tra dữ liệu không thành công!");
        return false;
      }

      let ngaySDDate = moment(this.txtNgaySuDung).toDate();
      let vngaySDDate = moment(this.vngay_sd, "DD/MM/YYYY").toDate();
      console.log(new Date());
      console.log(ngaySDDate);
      if (
        !(
          ngaySDDate <= new Date() &&
          ((vngaySDDate != null && ngaySDDate >= vngaySDDate) ||
            vngaySDDate == null)
        )
      ) {
        this.$toast.error(
          "Ngày sử dụng lớn hơn ngày sử dụng của thuê bào và nhở hơn ngày hiện tại"
        );
        return false;
      }
      return true;
    },

    async onClickCapNhat() {
      this.loading(true);
      const isCheck = await this.KT_DL();
      if (!isCheck) {
        this.loading(false);
        return;
      }

      let loai = 1;
      if (this.checked == "checkDanhBa") {
        loai = 2;
      }

      var donGia = this.cboLoHang.filter(
        (x) => (x.LOHANG = this.txtLoHang)
      ).DONGIA;

      let body = {
        thueBaoId: this.vthuebao_id,
        vatTuId: this.txtVatTuId,
        khoId: this.txtKho,
        ngaySD: moment(this.txtNgaySuDung).format("DD/MM/YYYY"),
        kieuTbi: this.txtKieuTB,
        donViTinh: this.txtDonViTinh,
        tinhTrangTbi: this.txtTinhTrang,
        trangBi: this.txtTrangBi,
        serialGP: this.txtSerialGP,
        ghiChu: this.txtGhiChu,
        donGia: donGia,
        thietBiId: this.vthietbi_id,
        suDungVTId: this.vsudungvt_id,
        loaiId: loai,
        loaiTD: this.vloai_td,
        serialNumber: this.txtSerialNumber != null ? this.txtSerialNumber.trim() : this.txtSerialNumber,
      };

      try {
        let res = await BoSungVatTuAPI.dayVatTuBoSung(this.axios, body);
        this.$toast.success("Thành công!");
        this.vloai_td = 2;
        this.setButton(1);
        this.HT_TT();
      } catch (error) {
        this.$toast.error(error.data.message);
      }

      this.loading(false);
    },

    async onChangeSerialValue() {
      clearInterval(this.timer);
      this.vSN = 1;
      this.timerTick();
    },

    async timerTick() {
      try {
        this.timer = setInterval(async () => {
          if (this.vSN == 1 && this.txtSerialNumber.length > 2) {
            let res = await BoSungVatTuAPI.sntogponsnWS(
              this.axios,
              this.txtSerialNumber
            );
            this.txtSerialGP = res.data.data[0].RESULT;
          }
        }, 2000);
      } catch (error) {
        this.vSN = 0;
        clearInterval(timer);
      }
    },

    async onClickSuaVatTu() {
      try {
        if (!this.rowSelected) {
          this.$toast.error("Bạn hãy chọn một dòng muốn sửa vật tư!");
          return;
        }
        if (this.grvVatTuBoSung.length == 0) {
          this.$toast.error(
            "Danh sách vật tư bổ sung không có không thể điều chỉnh mã vật tư!"
          );
          return;
        }

        let tenVatTu = this.cboVatTu.filter(
          (x) => x.VATTU_ID == this.txtVatTuId
        );

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(
            "Bạn có chắn chắn  muốn sửa vật tư " +
              this.rowSelected.TEN_VT +
              " thành " +
              tenVatTu[0].TEN_VT +
              " không?",
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

        let body = {
          sdVtId: this.rowSelected.SDVT_ID,
          thueBaoId: this.rowSelected.THUEBAO_ID,
          serial: this.rowSelected.SERIAL,
          vatTuId: this.txtVatTuId,
          thietBiId: this.rowSelected.THIETBI_ID,
        };
        let dr_sdvt_id = this.rowSelected.SDVT_ID;
        let res = (await BoSungVatTuAPI.capNhatVatTuSuDung(this.axios, body))
          .data.data;
        console.log(res, " = res");
        if (res != "1") {
          this.$toast.error(res);
          return;
        }
        this.$toast.success("Sửa vật tư thành công!");
        this.vloai_td = 2;
        this.setButton(1);
        this.HT_TT();
        await this.FocusGridViewRow(dr_sdvt_id);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async FocusGridViewRow(value) {
      let indexs = this.grvVatTuBoSung.findIndex(
        (a) => a.SDVT_ID != null && a.SDVT_ID.toString() == value.toString()
      );
      if (indexs != -1) {
        if (
          !(
            indexs + 1 >
              this.$refs["danhSachDuocChon"].pageIndexData *
                this.$refs["danhSachDuocChon"].pageSizeData &&
            indexs + 1 <=
              (this.$refs["danhSachDuocChon"].pageIndexData + 1) *
                this.$refs["danhSachDuocChon"].pageSizeData
          )
        ) {
          this.$refs["danhSachDuocChon"].$refs.pagination.gotoPage(
            parseInt(indexs / this.$refs["danhSachDuocChon"].pageSizeData)
          );
        }
        let self = this;
        let ind = parseInt(
          indexs % this.$refs["danhSachDuocChon"].pageSizeData
        );
        setTimeout(() => {
          self.$refs["danhSachDuocChon"].grid.selectRow(ind, true);
        }, 1000);
      }
    },
    convertDate: function (date) {
      return (
        new Date(date).toLocaleDateString("en-US", { day: "2-digit" }) +
        "/" +
        new Date(date).toLocaleDateString("en-US", { month: "2-digit" }) +
        "/" +
        new Date(date).toLocaleDateString("en-US", { year: "numeric" })
      );
    },
  },
};
</script>
