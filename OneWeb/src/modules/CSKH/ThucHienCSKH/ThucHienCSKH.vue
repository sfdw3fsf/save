<template src="./ThucHienCSKH.html"></template>
<style src="./ThucHienCSKH.scss"></style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import select2 from "@/components/Select2.vue";
import YesNoModal from "../utils/YesNoModal.vue";
import BssRequiredMarker from "@/components/BssRequiredMarker";
import { required, maxLength } from "vuelidate/lib/validators";
import ModalChonDiaChi from "../../ECMS/tracuu-bando-mangcap/ChonDiaChi/Modal";

export default {
  name: "ThucHienCSKH",
  components: {
    appSelect2: select2,
    confirmPopup: YesNoModal,
    BssRequiredMarker,
    ModalChonDiaChi
  },
  data() {
    return {
      chuongTrinhCSKHSelected: "",
      loaiDoiTuongSelected: "",
      dsTrangThai: [
        // {
        // 	"id": 0,
        // 	"text": "Chọn trạng thái"
        // },
        {
          id: 1,
          text: "Chưa chăm sóc",
        },
        {
          id: 2,
          text: "Đang chăm sóc",
        },
        {
          id: 3,
          text: "Đã chăm sóc",
        },
        {
          id: 4,
          text: "Không thực hiện được",
        },
      ],
      trangThaiSelected: 1,
      dsChamSocTheoNV: [],
      dsChamSocTheoNVFields: [
        {
          fieldName: "MA_KH",
          headerText: "Mã khách hàng",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_KH",
          headerText: "Tên khách hàng",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "DIACHI_KH",
          headerText: "Địa chỉ khách hàng",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "SO_DT",
          headerText: "Số điện thoại",
          allowFiltering: true,
          allowSorting: false,
        },
      ],
      dsCongViecTheoCT: [
        {
          id: 0,
          text: "Chọn công việc",
        },
      ],
      congViecSelected: 0,

      enableGhiLai: true,
      enableXoa: false,
      enableHoanThanh: false,
      enableBaoTon: false,
      txtChucVu: "",
      txtDiaChi: "",
      txtGhiChu: "",
      txtQuaTang: "",
      txtTenDT: "",
      txtSoDT: "",
      thucHienId: 0,
      khachHangId: 0,
      kyHoaDon: 0,
    };
  },

  async created() {
    this.$nextTick(() => this.dsTrangThai = [...this.dsTrangThai]);
  },
  async mounted() {
    //combo chương trình
    await this.getDsChuongTrinhCSKH({
      nhanVienId: this.$auth.getNhanVienID(),
    });
    await this.getDsLoaiDoiTuong();
    await this.getDsChamSocTheoNV();
  },
  computed: {
    ...mapState("thuchiencskh", [
      "dsChuongTrinhCSKH",
      "dsLoaiDoiTuong",
      // "dsChamSocTheoNV"
    ]),

    ...mapGetters("thuchiencskh", [
      "dsChuongTrinhCSKHGetter",
      "dsLoaiDoiTuongGetter",
      // "dsChamSocTheoNV"
    ]),
  },
  watch: {
    chuongTrinhCSKHSelected: {
      handler: async function (value) {
        await this.getCongViecTheoCT();
        await this.getDsChamSocTheoNV();
      },
    },
    trangThaiSelected: {
      handler: async function (value) {
        await this.getDsChamSocTheoNV();
        // if (this.trangThaiSelected == 1) {
        // 	this.enableButtonChuaChamSoc();
        // } else if (this.{}
        switch (this.trangThaiSelected) {
          case "1":
            this.enableButtonChuaChamSoc();
            break;
          case "2":
            this.enableButtonDangChamSoc();
            break;
          case "3":
            this.enableButtonDaChamSoc();
            break;
          case "4":
            this.enableButtonDaChamSoc();
            break;
          default:
            this.enableButtonChuaChamSoc();
            break;
        }
      },
    },
  },
  validations: {
    txtSoDT: {
      required,
    },
    txtDiaChi: {
      required,
    },
    txtQuaTang: {
      required,
    },
  },
  methods: {
    ...mapActions("thuchiencskh", [
      "getDsChuongTrinhCSKH",
      "getDsLoaiDoiTuong",
      // "getDsChamSocTheoNV"
    ]),

    async getDsChamSocTheoNV() {
      this.khachHangId = 0;
      let csTheoNVInput = {
        nhanVienId: this.$auth.getNhanVienID(),
        trangThaiId: this.trangThaiSelected,
        chuongTrinhId: this.chuongTrinhCSKHSelected,
        // "nhanVienId": 13933,
        // "trangThaiId": 1,
        // "chuongTrinhId": 4
      };
      try {
        this.loading(true);
        let response = await this.$root.context.post("/web-cskh/api/thuc-hien-cskh/ds-cs-theo-nv",csTheoNVInput);
        if (response.error_code === "BSS-00000000") {
            this.dsChamSocTheoNV = response.data || [];
            if (this.dsChamSocTheoNV == []) {
                this.disableAll();
            }
        }
      } catch (error) {

      } finally {
        this.loading(false);
      }
    },
    async getCongViecTheoCT() {
      //  try {
      let congViecTheoCTInput = {
        ctcsKhId: this.chuongTrinhCSKHSelected,
        trangThai: 2,
      };
      let response = await this.$root.context.post(
        "/web-cskh/api/thuc-hien-cskh/ds-cv-theo-ct",
        congViecTheoCTInput
      );
      // let response = await ThucHienCSKHApi.getDsCongViecTheoCT(this._vm.axios)
      let ds = response.data.data || [];
      this.dsCongViecTheoCT = [
        {
          id: "",
          text: "Chọn công việc",
        },
      ];

      if (ds.length > 0) {
        ds.forEach((element) => {
          this.dsCongViecTheoCT.push({
            id: element.CONGVIEC_ID,
            text: element.NOIDUNG,
          });
        });
      }

      // return response;
      // } catch (error) {

      // }
    },

    async onGhiLai() {
      if (this.khachHangId == 0) {
        this.$root.toastError("Bạn chưa chọn khách hàng !");
        return;
      }
      if (!this.validateData()) return;

      if (!this.kiemTraDuLieu()) return;
      // chưa chăm sóc
      if (this.trangThaiSelected == 1) {
        this.taoDL(0);
        // this.getDsChamSocTheoNV()
      } else if (this.trangThaiSelected == 2) {
        // đang chăm sóc
        this.taoDL(0);
        // this.getDsChamSocTheoNV()
      }
      // await this.getDsChamSocTheoNV()
    },

    kiemTraDuLieu() {
      if (!this.txtSoDT.match(/(0[1-9])+(\d{8})/g)) {
        this.$root.toastError("Số điện thoại chưa đúng định dạng!");
        this.$refs.txtSoDT.focus();
        return false;
      } else if (this.congViecSelected == 0) {
        this.$root.toastError("Bạn chưa chọn công việc!");
        return false;
      }
      return true;
    },

    async taoDL(kieu) {
      try {
        this.loading(true);
        let taoDLInput = {
          kieu: kieu,
          thucHienId: this.thucHienId,
          khachHangId: this.khachHangId,
          congViecId: this.congViecSelected,
          ghiChu: this.txtGhiChu,
          kyHoaDon: this.kyHoaDon,
          doiTuongCsId: this.loaiDoiTuongSelected,
          tenDoiTuong: this.txtTenDT,
          diaChi: this.txtDiaChi,
          dienThoai: this.txtSoDT,
          chucVu: this.txtChucVu,
          quaTang: this.txtQuaTang,
          // mayCn: "TEST-PC",
          // nguoiCn: "test@vnpt.vn"
        };
        // let response = await API.ganDVChamSocKHL(this.axios, data);
        let response = await this.$root.context.post(
          "/web-cskh/api/thuc-hien-cskh/cap-nhat",
          taoDLInput
        );

        if (response.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Cập nhật dữ liệu thành công!");
          this.getDsChamSocTheoNV();
          this.$v.$reset();
        } else {
          this.$root.toastError(
            "Có lỗi: " + response.message || response.error
          );
        }
      } catch (e) {
        this.$root.toastError("Không thành công!");
      } finally {
        this.loading(false);
      }
    },

    async gridDsChamSocTheoNV_selectedRowChanged(dataItem) {
      this.loading(true);
      try {
        if (dataItem != null) {
          // this.ctcskhIdSelected = dataItem.CTCSKH_ID;
          // await this.hienThiDsDvChuaGan(this.ctcskhIdSelected);
          // await this.hienThiDsDvDaGan(this.ctcskhIdSelected);
          // if(this.trangThaiSelected != 1) {
          // 			if (Convert.ToInt32(cboTrangThai.EditValue) != 1)
          // {
          //     CONGVIEC_ID = Convert.ToInt32(gridviewChamSoc_KHL.GetRowCellValue(index, "CONGVIEC_ID"));
          //     string chuongtrinh_id = new CheckDataFacade().MAP_ID("ctcskh_id", DatabaseConstants.DB3 + ".congviec_cskh", "where congviec_id = " + CONGVIEC_ID);
          //     if (chuongtrinh_id != "-1")
          //     {
          //         //cboChuongTrinh.ItemIndex = cboChuongTrinh.Properties.GetDataSourceRowIndex("CTCSKH_ID", chuongtrinh_id);
          //         cboCongViec.ItemIndex = cboCongViec.Properties.GetDataSourceRowIndex("CONGVIEC_ID", CONGVIEC_ID);
          //     }
          //     else
          //     {
          //         //cboChuongTrinh.ItemIndex = 0;
          //         cboCongViec.ItemIndex = 0;
          //     }
          // }
          // else
          // {
          //     //cboChuongTrinh.ItemIndex = 0;
          //     cboCongViec.ItemIndex = 0;
          // }

          // if (gridviewChamSoc_KHL.GetRowCellValue(index, "DOITUONGCS_ID").ToString() != "")
          //     cboLoaiDT.ItemIndex = cboLoaiDT.Properties.GetDataSourceRowIndex("DOITUONGCS_ID", gridviewChamSoc_KHL.GetRowCellValue(index, "DOITUONGCS_ID").ToString());
          // else
          //     cboLoaiDT.ItemIndex = 0;
          if (this.trangThaiSelected != 1) {
            this.congViecSelected = dataItem.CONGVIEC_ID;
          }

          if (dataItem.DOITUONGCS_ID != null) {
            this.loaiDoiTuongSelected = dataItem.DOITUONGCS_ID;
          }
          this.txtTenDT = dataItem.TENDOITUONG;
          this.txtSoDT = dataItem.DIENTHOAI;
          this.txtDiaChi = dataItem.DIACHI;
          this.txtChucVu = dataItem.CHUCVU;
          this.txtGhiChu = dataItem.GHICHU;
          this.txtQuaTang = dataItem.QUATANG;
          this.thucHienId = dataItem.THUCHIEN_ID;
          this.khachHangId = dataItem.KHACHHANG_ID;
          this.kyHoaDon = dataItem.KYHOADON;
        } else {
          this.$v.$reset();
        }
      } catch (ex) {
        this.$root.toastError("Có lỗi");
      } finally {
        this.loading(false);
      }
    },

    async onHoanThanh() {
      if (this.khachHangId == 0) {
        this.$root.toastError("Bạn chưa chọn khách hàng !");
        return;
      }
      if (!this.validateData()) return;
      if (this.txtTenDT == "") {
        this.$root.toastError("Bạn chưa nhập Tên đối tượng !");
        // txtTenDT.Focus();
        return;
      }
      if (this.txtSoDT == "") {
        this.$root.toastError("Bạn chưa nhập Số điện thoại !");
        // txtSoDT.Focus();
        return;
      }
      this.$bvModal.show("hoanThanhConfirm");
    },

    async onHoanThanhConfirm() {
      // long thuchien_id = Convert.ToInt64(gridviewChamSoc_KHL.GetRowCellValue
      // (gridviewChamSoc_KHL.FocusedRowHandle, "THUCHIEN_ID").ToString());
      // let thucHienId

      try {
        this.loading(true);
        let hoanThanhInput = {
          thucHienId: this.thucHienId,
          trangThai: 5,
        };
        let response = await this.$root.context.post(
          "/web-cskh/api/thuc-hien-cskh/hoan-thanh",
          hoanThanhInput
        );
        if (response.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Hoàn thành thực hiện CSKH thành công!");
          this.getDsChamSocTheoNV();
        } else {
          this.$root.toastError(
            "Có lỗi: " + response.message || response.error
          );
        }
      } catch (e) {
        this.$root.toastError("Không thành công!");
      } finally {
        this.loading(false);
      }

      // this.getDsChamSocTheoNV();
    },

    async onXoa() {
      if (this.khachHangId == 0) {
        this.$root.toastError("Bạn chưa chọn khách hàng !");
        return;
      }
      this.$bvModal.show("xoaConfirm");
    },

    async onXoaConfirm() {
      // confirm xóa
      try {
        let thucHienId = this.thucHienId;
        let response = await this.$root.context.get(
          "/web-cskh/api/thuc-hien-cskh/xoa",
          { thucHienId }
        );
        if (response.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Xóa thực hiện CSKH thành công!");
          this.getDsChamSocTheoNV();
        }
      } catch (e) {
        this.$root.toastError(
          "Có lỗi: " + e.response.data.message || e.response.data.error
        );
      } finally {
        this.loading(false);
      }
    },

    async onBaoTon() {
      if (this.khachHangId == 0) {
        this.$root.toastError("Bạn chưa chọn khách hàng !");
        return;
      }
      if (!this.validateData()) return;
      if (this.txtGhiChu == "") {
        this.$root.toastError("Bạn chưa nhập Ghi chú !");
        // txtGhiChu.Focus();
        return;
      }
      await this.$bvModal.show("baoTonConfirm");
    },
    async onBaoTonConfirm() {
      // confirm bảo tồn

      // long thuchien_id = Convert.ToInt64(gridviewChamSoc_KHL.GetRowCellValue
      // (gridviewChamSoc_KHL.FocusedRowHandle, "THUCHIEN_ID").ToString());
      try {
        let hoanThanhInput = {
          thucHienId: this.thucHienId,
          trangThai: 2,
        };
        let response = await this.$root.context.post(
          "/web-cskh/api/thuc-hien-cskh/hoan-thanh",
          hoanThanhInput
        );
        if (response.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Báo tồn thực hiện CSKH thành công!");
          this.loading(false);
          await this.getDsChamSocTheoNV();
        } else {
          this.$root.toastError(
            "Có lỗi: " + response.data.message || response.data.error
          );
        }
      } catch (e) {
        this.$root.toastError(
          "Có lỗi: " + e.response.data.message || e.response.data.error
        );
      } finally {
        this.loading(false);
      }
    },

    enableButtonChuaChamSoc() {
      (this.enableGhiLai = true),
        (this.enableXoa = false),
        (this.enableHoanThanh = false),
        (this.enableBaoTon = false);
    },

    enableButtonDangChamSoc() {
      (this.enableGhiLai = true),
        (this.enableXoa = true),
        (this.enableHoanThanh = true),
        (this.enableBaoTon = true);
    },

    enableButtonDaChamSoc() {
      (this.enableGhiLai = false),
        (this.enableXoa = false),
        (this.enableHoanThanh = false),
        (this.enableBaoTon = false);
    },

    disableAll() {
      (this.enableGhiLai = false),
        (this.enableXoa = false),
        (this.enableHoanThanh = false),
        (this.enableBaoTon = false);
    },

    validateData() {
      this.$v.$touch();
      let errorMessage = [];

      if (!this.$v.txtSoDT.required) {
        errorMessage.push("Bạn chưa nhập số điện thoại");
        this.$refs.txtSoDT.focus();
      } else if (!this.$v.txtDiaChi.required) {
        errorMessage.push("Bạn chưa nhập địa chỉ");
        this.$refs.txtDiaChi.focus();
      } else if (!this.$v.txtQuaTang.required) {
        errorMessage.push("Bạn chưa nhập quà tặng");
        this.$refs.txtQuaTang.focus();
      }
      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join("\n"));
        return false;
      } else return true;
    },
    getDiaChiKH(data){
       this.txtDiaChi = data.diachimoi || "";
    }
  },
};
</script>
