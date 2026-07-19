<template src="./index.html"></template>

<script>
export default {
  data: function () {
    return {
      isAllowCompleteButton: false,
      soPhieu: "",
      soCuThuHoi: "",
      trangThaiYeuCau: "",
      sanPhamDichVu: "",
      donViYeuCau: "",
      tenSPDV: "",
      nguoiGui: "",
      cumDichVu: "",
      ngayGui: "",
      ngayHoanThanh: "",
      ngayBatDauThuHoi: "",
      ghiChu: "",
      yKienXacNhan: "",
      ngatTaiNguyen: "",
      yKienThuHoi: "",
      dsThongTinYeuCau: [],
      DSThuHoiBackup: [],
      thuhoi_id: null,
      id: null,
      filePublicLink: null,
      fileName: '',
    };
  },
  computed: {
  },
  created: async function () {
    this.DSThuHoiBackup = await this.apiDanhSachThuHoiBackUp();
  },
  methods: {
    async onComplete() {
      if (!this.yKienThuHoi || this.yKienThuHoi.trim().length === 0) {
        this.$root.toastError("Vui lòng nhập ý kiến thực hiện thu hồi backup.");
        return;
      } else {
        let flag = await this.$confirm("Bạn có muốn hoàn thành thu hồi backup này không?", "Xác nhận",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "warning",
            dangerouslyUseHTMLString: true,
          }
        )
          .then((s) => true)
          .catch((e) => {
            return false;  
          });
        if (flag) {
          try {
            this.$root.showLoading(true);
            let rs = await this.apiHoanThanh();
            this.DSThuHoiBackup = await this.apiDanhSachThuHoiBackUp();
            this.$root.toastSuccess("Hoàn thành thu hồi backup thành công!");
          }
          catch (e) {
            this.$root.toastError(e.response.data.message_detail);
          }
          finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    setCurrentItem(data) {
      this.id = data.ID
      this.soPhieu = data.SOPHIEU
      this.soCuThuHoi = data.SOCU_THUHOI
      this.trangThaiYeuCau = data.TRANGTHAI_YC
      this.sanPhamDichVu = data.SPDA
      this.donViYeuCau = data.DONVI_YC
      this.tenSPDV = data.TEN_SPDA
      this.nguoiGui = data.NGUOI_GUI
      this.cumDichVu = data.CUM_DV
      this.ngayGui = data.NGAY_GUI
      this.ngayHoanThanh = data.NGAY_HT
      this.ngayBatDauThuHoi = data.NGAY_THUHOI
      this.ghiChu = data.GHICHU
      this.yKienXacNhan = data.YK_XACNHAN
      this.ngatTaiNguyen = data.NGAT_TAINGUYEN
      this.yKienThuHoi = data.YK_THUHOI
      this.fileName = data.NAME_FILE

      this.isAllowCompleteButton = data.is_allow_compelete_button == 1 ? true : false;
    },
    grdYeuCau_RowSelected: async function (args) {

    },
    grdThuHoiBackup_RowSelected: async function (args) {
      try {
        this.$root.showLoading(true);
        this.setCurrentItem(args.data);
        this.filePublicLink = await this.apiDownFile(args.data.PATH_FILE);
        await this.loadDsThongTinYeuCauThuHoi();
      }
      finally {
        this.$root.showLoading(false);
      }

    },
    grdThuHoiBackup_RowDeselected: async function (args) {
      // let items = this.$refs.grdThuHoiBackup.getSelectedRecords();
    },

    apiDanhSachThuHoiBackUp: async function () {
      let result = [];
      try {
        const rs = await this.$root.context.post(`/web-ecms/hatang/thu-hoi-backup/danhsach-thuhoi-backup`);
        result = rs.data;
      } catch (e) {
        this.$root.toastError(e.response.data.message_detail)
      }
      return result;
    },
    loadDsThongTinYeuCauThuHoi: async function () {
      this.dsThongTinYeuCau = await this.apiDsThongTinYeuCauThuHoi();
    },
    apiDsThongTinYeuCauThuHoi: async function () {
      let result = [];
      try {
        const rs = await this.$root.context.get(`/web-ecms/hatang/thu-hoi-backup/danhsach-yeucau/${this.id}`);
        result = rs.data;
      } catch (e) {
        this.$root.toastError(e.response.data.message_detail)
      }
      return result;
    },
    apiHoanThanh: async function () {
      let result = null;
      try {
        const rs = await this.$root.context.post(`/web-ecms/hatang/thu-hoi-backup/hoanthanh-thuhoi-backup/${this.id}`,
          { ykien: this.yKienThuHoi }
        );
        result = rs.data;
      } catch (e) {
        throw e;
      }
      return result;
    },
    apiDownFile: async function (path_file) {
      let result = null;
      try {
        let rs = await this.$root.context.post("/web-ecms/storage/getPresignedUrl", { fileSource: path_file });
        result = rs.data.result;
      } catch (e) {
        // this.$root.toastError(e.response.data.message_detail)
      }
      return result;
    },

  },
};
</script>
<style>
.info-row .key {
  width: 150px !important;
}
</style>
