<template src="./index.template.html"></template>
<script>
import BssErrorMarker from "@/components/BssErrorMarker";
import BssRequiredMarker from "@/components/BssRequiredMarker";
import xlsx from "xlsx";

export default {
  name: "ModalDocFile",
  props: { modalID: null, parent: null },
  components: {
    BssErrorMarker,
    BssRequiredMarker,
  },
  created: async function () {},
  data: function () {
    return {
      passATTT: true,
      isLoading: true,
      tab1: true,
      tab2: false,
      tab3: false,
      dsKhieuNai: [],
      dsLoi: [],
      dsDB: [],
      fileExcel: null,
      lblKetQua: null,
      dsChuDe_KN: [],
      lblThoiGianTao: null,
      listHuongGiao: [],
      THEM_KHDB_KHI_KHIEUNAI: -1,
      nDs1: 1,
      nDs2: 2,
      nDs3: 3,
    };
  },
  watch: {
    async fileExcel(val) {
      console.log("🚀 ~ file: index.vue ~ line 36 ~ fileExcel ~ val", val);
      this.passATTT = true;
      // if (val !== null) {
      //   try {
      //     this.loading(true)
      //     await this.ReadFile3(val)
      //   } catch (error) {
      //     console.log('🚀 ~ file: index.vue ~ line 41 ~ fileExcel ~ error', error)
      //   } finally {
      //     console.log('🚀 ~ file: index.vue ~ line 41 ~ fileExcel ~ finally')
      //     this.loading(false)
      //   }
      // }
    },
  },
  computed: {},
  methods: {
    async tsbtnXuatExcel_Click() {
      if (this.tab1) {
        if (this.dsKhieuNai.length === 0) {
          await this.$alert("Không có dữ liệu để xuất Excel !", "Thông báo", {
            confirmButtonText: "Đóng",
            type: "warning",
          });
          return;
        }
        try {
          let exportProperties = {
            dataSource: this.dsKhieuNai,
          };
          this.$refs.gridDsKhieuNai.excelExport(exportProperties);
        } catch (error) {
          console.log(
            "🚀 ~ file: index.vue ~ line 53 ~ tsbtnXuatExcel_Click ~ error",
            error
          );
        }
      } else if (this.tab2) {
        if (this.dsLoi.length === 0) {
          await this.$alert("Không có dữ liệu để xuất Excel !", "Thông báo", {
            confirmButtonText: "Đóng",
            type: "warning",
          });
          return;
        }
        try {
          let exportProperties = {
            dataSource: this.dsLoi,
          };
          this.$refs.gridDsLoi.excelExport(exportProperties);
        } catch (error) {
          console.log(
            "🚀 ~ file: index.vue ~ line 53 ~ tsbtnXuatExcel_Click ~ error",
            error
          );
        }
      } else {
        if (this.dsDB.length === 0) {
          await this.$alert("Không có dữ liệu để xuất Excel !", "Thông báo", {
            confirmButtonText: "Đóng",
            type: "warning",
          });
          return;
        }
        try {
          let exportProperties = {
            dataSource: this.dsDB,
          };
          this.$refs.gridDsDB.excelExport(exportProperties);
        } catch (error) {
          console.log(
            "🚀 ~ file: index.vue ~ line 53 ~ tsbtnXuatExcel_Click ~ error",
            error
          );
        }
      }
    },
    hiddenModal() {},
    async shownModal() {
      await this.loadData();
      this.dsKhieuNai = [];
      this.dsLoi = [];
      this.dsDB = [];
      this.fileExcel = null;
      this.lblKetQua = null;
    },
    closeModal() {
      this.resolvePromise({ ok: false, data: null });
      this.$bvModal.hide(this.modalID);
    },
    show(data) {
      return new Promise((resolve, reject) => {
        this.data = data;
        this.$bvModal.show(this.modalID);
        this.resolvePromise = resolve;
        this.rejectPromise = reject;
      });
    },
    clickTab(index) {
      if (index === 1) {
        this.tab1 = true;
        this.tab2 = false;
        this.tab3 = false;
      }
      if (index === 2) {
        this.tab1 = false;
        this.tab2 = true;
        this.tab3 = false;
      }
      if (index === 3) {
        this.tab1 = false;
        this.tab2 = false;
        this.tab3 = true;
      }
    },
    async tsbtnCheckATTT_Click() {
      try {
        this.loading(true);
        await this.checkATTT(this.fileExcel);
        this.loading(false);
      } catch (error) {
        console.log("🚀 ~ file: index.vue:150 ~ tsbtnCheckATTT_Click ~ error:", error);
        this.loading(false);
      }
    },
    async tsbtnXem_Click() {
      try {
        this.loading(true);
        await this.ReadFile3(this.fileExcel);
        this.loading(false);
      } catch (error) {
        console.log("🚀 ~ file: index.vue:160 ~ tsbtnXem_Click ~ error:", error);
        this.loading(false);
      }
    },
    async loadData() {
      try {
        if (this.dsChuDe_KN.length > 0) return;
        this.$root.showLoading(true);
        let rs = await this.$root.context.get("/web-cabman/ban-do-mang-cap/danh-muc", {
          loai: "CHUDE_KN",
          option: "",
        });
        this.dsChuDe_KN = rs.data;
        console.log(
          "🚀 ~ file: index.vue ~ line 78 ~ loadData ~  this.dsChuDe_KN",
          this.dsChuDe_KN
        );
        this.$root.showLoading(false);
      } catch (error) {
        this.dsChuDe_KN = [];
        this.$root.showLoading(false);
      }
    },
    async TaoDL() {
      try {
        this.loading(true);
        this.listHuongGiao = [];

        var huonggiao_tn = 0;
        var quytrinh_id = 0;
        var khieunai_id = 0;
        var ma_kn = "";

        for (let index = 0; index < this.dsKhieuNai.length; index++) {
          const kn = this.dsKhieuNai[index];
          huonggiao_tn = 0;
          quytrinh_id = 0;
          khieunai_id = 0;
          ma_kn = "";

          var data = {
            CHUDEKN_ID: kn.CHUDEKN_ID,
          };
          let dset = await this.$root.context.post(
            "/web-gqkn/tiep-nhan-khieu-nai/layhuonggiao-tiepnhan-xlkn",
            data
          );

          huonggiao_tn = dset.data[0].HUONGGIAO_ID;
          quytrinh_id = dset.data[0].QUYTRINH_ID;

          var row = {};
          var dtTB = await this.$root.context.get(
            "/web-cabman/ban-do-mang-cap/danh-muc",
            { loai: "DB_THUEBAO", option: kn.MA_TB.trim() }
          );
          console.log("🚀 ~ file: index.vue ~ line 130 ~ TaoDL ~ dtTB", dtTB);
          if (dtTB.data.length === 0) {
            this.dsKhieuNai[index].TRANGTHAI =
              "Lỗi: Không tìm thấy thông tin danh bạ thuê bao";
            continue;
          }
          ma_kn = await this.TaoMaKN();
          row.MA_TB = kn.MA_TB.toString();
          row.CONGVAN_XL = "";
          row.MA_KN = ma_kn;
          row.THUEBAO_ID = dtTB.data[0].THUEBAO_ID;
          row.THANHTOAN_ID = dtTB.data[0].THANHTOAN_ID;
          row.DICHVUVT_ID = dtTB.data[0].DICHVUVT_ID;
          row.LOAITB_ID = dtTB.data[0].LOAITB_ID;
          row.DIENTHOAI_LH = kn.DIENTHOAI_LH;

          if (row.DIENTHOAI_LH === "" || row.DIENTHOAI_LH === null) {
            var sp = await this.$root.context.get(
              "/web-cabman/ban-do-mang-cap/danh-muc",
              { loai: "DB_THANHTOAN", option: kn.MA_TT }
            );
            if (sp.data && sp.data.length > 0) {
              row.DIENTHOAI_LH = sp.data[0].SO_DT;
            }
          }
          row.TTKN_ID = 1;
          row.DONVI_ID = this.$root.token.getDonViID();
          row.NHANVIEN_ID = this.$root.token.getNhanVienID();
          row.CHUDEKN_ID = kn.CHUDEKN_ID;
          row.GHICHU_KN = "";
          row.ND_KHIEUNAI = kn.NOIDUNG_KN;
          row.QUYTRINH_ID = quytrinh_id;
          row.NOIDUNG_GQ = "";
          var sp2 = await this.$root.context.get("/web-cabman/ban-do-mang-cap/danh-muc", {
            loai: "DB_THANHTOAN2",
            option: kn.MA_TT,
          });
          var vkhachhang_id = 0;
          if (sp2.data.length > 0) {
            vkhachhang_id = sp2.data[0].KHACHHANG_ID;
          }
          let dskh = await this.$root.context.get(
            "/web-gqkn/tiep-nhan-khieu-nai/getDanhBaKHTheoKHID?KHACHHANG_ID=" +
              vkhachhang_id
          );
          console.log("🚀 ~ file: index.vue ~ line 174 ~ TaoDL ~ dskh", dskh);
          if (dskh.data.length > 0) {
            if (dskh.data[0].LOAIGT_ID !== null) {
              if (dskh.data[0].LOAIGT_ID === 1) {
                row.CMT = dskh.data[0].SO_GT;
                var ngaycap = dskh.data[0].NGAYCAP;
                if (ngaycap !== null) {
                  row.NGAYCAP = ngaycap;
                }
                row.NOICAP = dskh.data[0].NOICAP;
              }
            }
            row.DIACHI_LH = dskh.data[0].DIACHI_KH;
            row.NGUOI_KN = dskh.data[0].TEN_KH;
          }
          row.HTKN_ID = 2;
          console.log("🚀 ~ file: index.vue ~ line 190 ~ TaoDL ~ row", row);
          khieunai_id = await this.TaoKN(row);
          console.log("🚀 ~ file: index.vue:235 ~ TaoDL ~ khieunai_id:", khieunai_id);
          if (khieunai_id !== null) {
            await this.TaoDuLieu_HDTBDV(quytrinh_id, kn.MA_TT, khieunai_id);
            this.listHuongGiao.push(huonggiao_tn);
            await this.CHUYEN_DL_XLKN_SANG_GIAOPHIEU(khieunai_id, huonggiao_tn);
            await this.Update_LyDo_KhongXL_Duoc_File(khieunai_id, "");
            if (this.THEM_KHDB_KHI_KHIEUNAI === 1) {
              await this.Capnhat_khdb_theo_khieunai();
            }
            this.dsKhieuNai[index].TRANGTHAI = "Thành công";
          } else {
            this.dsKhieuNai[index].TRANGTHAI = "Lỗi: Tiếp nhận khiếu nại lỗi";
          }
        }
        console.log(
          "🚀 ~ file: index.vue:247 ~ TaoDL ~ this.dsKhieuNai:",
          this.dsKhieuNai
        );
        await this.$refs.gridDsKhieuNai.refresh();
        this.loading(false);
        this.$alert("Đã thực hiện nhập tiếp nhận khiếu nại từ file!", "Thông báo", {
          confirmButtonText: "Đóng",
          type: "success",
        });
        await this.parent.getTB_KHIEUNAI();
      } catch (error) {
        this.loading(false);
        console.log(
          "🚀 ~ file: index.vue ~ line 99 ~ TaoDL ~ error",
          error.response.data
        );
        this.$alert("Tiếp nhận khiếu nại lỗi", "Thông báo", {
          confirmButtonText: "Đóng",
          type: "error",
        });
      }
    },
    async TaoDLV2() {
      try {
        this.loading(true);
        this.listHuongGiao = [];
        var huonggiao_tn = 0;
        var quytrinh_id = 0;
        var khieunai_id = 0;
        var ma_kn = "";
        var myArr = [];
        let myJSON = JSON.stringify(this.dsKhieuNai);
        console.log("🚀 ~ file: index.vue:363 ~ TaoDLV2 ~ myJSON:", myJSON);
        let kq = await this.TaoKNV2(myJSON);
        this.dsKhieuNai = [];
        this.dsLoi = [];
        this.loading(false);
        if (kq === 1) {
          this.$alert("Tiếp nhận khiếu nại thành công !", "Thông báo", {
            confirmButtonText: "Đóng",
            type: "success",
          });
        } else {
          this.$alert("Tiếp nhận khiếu nại lỗi", "Thông báo", {
            confirmButtonText: "Đóng",
            type: "error",
          });
        }
        await this.parent.getTB_KHIEUNAI();
      } catch (error) {
        this.loading(false);
        console.log(
          "🚀 ~ file: index.vue ~ line 99 ~ TaoDL ~ error",
          error.response.data
        );
        this.$alert("Tiếp nhận khiếu nại lỗi", "Thông báo", {
          confirmButtonText: "Đóng",
          type: "error",
        });
      }
    },
    async Capnhat_khdb_theo_khieunai() {
      try {
        let rps = await this.$root.context.post(
          "/web-gqkn/tiep-nhan-khieu-nai/capnhat_khdb_theo_khieunai"
        );
      } catch (error) {
        console.log(
          "🚀 ~ file: index.vue:272 ~ Capnhat_khdb_theo_khieunai ~ error:",
          error
        );
      }
    },
    async Update_LyDo_KhongXL_Duoc_File(khieunai_id, lydo) {
      try {
        let data = {
          PHIEUKN_ID: 0,
          KHIEUNAI_ID: khieunai_id,
          LYDO_TON: lydo,
        };
        let rps = await this.$root.context.post(
          "/web-gqkn/tiep-nhan-khieu-nai/update_lydo_khongxl_duoc",
          data
        );
        console.log(
          "🚀 ~ file: index.vue ~ line 216 ~ Update_LyDo_KhongXL_Duoc_File ~ rps",
          rps
        );
      } catch (error) {
        console.log(
          "🚀 ~ file: index.vue ~ line 216 ~ Update_LyDo_KhongXL_Duoc_File ~ error",
          error.response
        );
      }
    },
    async CHUYEN_DL_XLKN_SANG_GIAOPHIEU(khieunai_id, huonggiao_tn) {
      try {
        let data = {
          VKHIEUNAI_ID: khieunai_id,
          VHUONGGIAO_ID: huonggiao_tn,
        };
        let rps = await this.$root.context.post(
          "/web-gqkn/tiep-nhan-khieu-nai/chuyen_dl_xlkn_sang_giaophieu",
          data
        );
        console.log(
          "🚀 ~ file: index.vue ~ line 214 ~ CHUYEN_DL_XLKN_SANG_GIAOPHIEU ~ rps",
          rps
        );
      } catch (error) {
        console.log(
          "🚀 ~ file: index.vue ~ line 216 ~ CHUYEN_DL_XLKN_SANG_GIAOPHIEU ~ error",
          error.response
        );
      }
    },
    async TaoDuLieu_HDTBDV(quytrinh_id, ma_tt, khieunai_id) {
      try {
        var dt_tmp = await this.$root.context.get(
          "/web-cabman/ban-do-mang-cap/danh-muc",
          { loai: "HUONGGIAO_LDV", option: quytrinh_id }
        );
        console.log(
          "🚀 ~ file: index.vue ~ line 207 ~ TaoDuLieu_HDTBDV ~ dt_tmp",
          dt_tmp
        );
        var inloaidv_id = 0;
        var indonvi_id = 0;
        for (let index = 0; index < dt_tmp.data.length; index++) {
          const element = dt_tmp.data[index];
          inloaidv_id = parseInt(element.LOAIDV_DICH_ID);
          if (inloaidv_id === 45) {
            var daily_id = 0;
            var dt = await this.$root.context.get(
              "/web-cabman/ban-do-mang-cap/danh-muc",
              { loai: "DAILY_ID", option: ma_tt }
            );
            if (dt.data.length > 0) {
              daily_id = dt.data[0].DAILY_ID;

              let data = {
                VTENBANG: "NHANVIEN_TC",
                VGIATRI: daily_id,
                VLOAIDV_ID: inloaidv_id,
                VLOAINV_ID: 40,
              };
              let ds_donvi = await this.$root.context.post(
                "/web-gqkn/tiep-nhan-khieu-nai/lay_donvi_nguoidung_theobang",
                data
              );
              console.log(
                "🚀 ~ file: index.vue ~ line 232 ~ TaoDuLieu_HDTBDV ~ ds_donvi",
                ds_donvi
              );
              if (ds_donvi.data.length > 0) {
                indonvi_id = ds_donvi.data[0].DONVI_ID;
              }
            }
          } else {
            var dt = await this.$root.context.get(
              "/web-cabman/ban-do-mang-cap/danh-muc",
              { loai: "LOAIDV_ID", option: inloaidv_id }
            );
            console.log("🚀 ~ file: index.vue ~ line 241 ~ TaoDuLieu_HDTBDV ~ dt", dt);
            if (dt.data.length > 0) {
              indonvi_id = dt.data[0].DONVI_ID;
            }
          }
          var row = {};
          row.KHIEUNAI_ID = khieunai_id;
          row.LOAIDV_ID = inloaidv_id;
          row.DONVI_ID = indonvi_id;
          await this.AddKHIEUNAI_DV(row);
        }
      } catch (error) {
        console.log("🚀 ~ file: index.vue:339 ~ TaoDuLieu_HDTBDV ~ error:", error);
      }
    },
    async AddKHIEUNAI_DV(row) {
      try {
        let data = {
          P_KHIEUNAI_ID: row.KHIEUNAI_ID,
          P_LOAIDV_ID: row.LOAIDV_ID,
          P_DONVI_ID: row.DONVI_ID,
        };
        let rps = await this.$root.context.post(
          "/web-gqkn/tiep-nhan-khieu-nai/insert_khieunai_dv",
          data
        );
        console.log("🚀 ~ file: index.vue ~ line 268 ~ AddKHIEUNAI_DV ~ rps", rps);
      } catch (error) {
        console.log("🚀 ~ file: index.vue:354 ~ AddKHIEUNAI_DV ~ error:", error);
      }
    },
    async TaoKN(row) {
      try {
        var data = {
          P_CHUDEKN_ID: row.CHUDEKN_ID,
          P_CMT: row.CMT ? row.CMT : null,
          P_CONGVAN_XL: row.CONGVAN_XL,
          P_DIACHI_LH: row.DIACHI_LH,
          P_DICHVUVT_ID: row.DICHVUVT_ID,
          P_DIENTHOAI_LH: row.DIENTHOAI_LH,
          P_DONVI_ID: row.DONVI_ID,
          P_GHICHU_KN: row.GHICHU_KN,
          P_HTKN_ID: row.HTKN_ID,
          P_LOAITB_ID: row.LOAITB_ID,
          P_MA_KN: row.MA_KN,
          P_MA_TB: row.MA_TB,
          P_ND_KHIEUNAI: row.ND_KHIEUNAI,
          P_NGAYCAP: row.NGAYCAP ? row.NGAYCAP : null,
          P_NGUOI_KN: row.NGUOI_KN,
          P_NHANVIEN_ID: row.NHANVIEN_ID,
          P_NOICAP: row.NOICAP ? row.NOICAP : null,
          P_NOIDUNG_GQ: row.NOIDUNG_GQ,
          P_QUYTRINH_ID: row.QUYTRINH_ID,
          P_THANHTOAN_ID: row.THANHTOAN_ID,
          P_THUEBAO_ID: row.THUEBAO_ID,
          P_TTKN_ID: row.TTKN_ID,
          P_NGUOI_CN: this.$root.token.getUserName(),
        };
        console.log("🚀 ~ file: index.vue ~ line 232 ~ TaoKN ~ data", data);
        let ds = await this.$root.context.post(
          "/web-gqkn/tiep-nhan-khieu-nai/insert_khieunai",
          data
        );
        console.log("🚀 ~ file: index.vue ~ line 236 ~ TaoKN ~ ds.data", ds.data);
        if (ds.data.length > 0) {
          return ds.data[0].KHIEUNAI_ID;
        } else return null;
      } catch (error) {
        console.log(
          "🚀 ~ file: index.vue ~ line 239 ~ TaoKN ~ error",
          error.response.data
        );
        return null;
      }
    },
    async TaoKNV2(P_JSON) {
      try {
        var data = {
          P_JSON,
          P_DONVI_ID: this.$root.token.getDonViID(),
          P_NHANVIEN_ID: this.$root.token.getNhanVienID(),
          P_NGUOI_CN: this.$root.token.getUserName(),
        };
        console.log("🚀 ~ file: index.vue:532 ~ TaoKNV2 ~ data:", data);
        let ds = await this.$root.context.post(
          "/web-gqkn/tiep-nhan-khieu-nai/insert_khieunai_v2",
          data
        );
        if (ds.data.length > 0) {
          return Number(ds.data[0].KQ);
        } else return 0;
      } catch (error) {
        console.log("🚀 ~ file: index.vue:538 ~ TaoKNV2 ~ error:", error);
        return 0;
      }
    },
    async getNam() {
      try {
        let rps = await this.$root.context.get(
          "/web-gqkn/tiep-nhan-khieu-nai/getsysdate"
        );
        if (rps.data.length > 0) {
          return rps.data[0].NAM_SYS;
        } else return null;
      } catch (error) {
        return null;
      }
    },
    async LAY_DS_THAMSO_MD2(data) {
      try {
        var rps = await this.$root.context.post(
          "/web-gqkn/tiep-nhan-khieu-nai/lay_ds_thamso_md2",
          data
        );
        return rps.data;
      } catch (error) {
        return [];
      }
    },
    async LAY_DS_THAMSO_MD() {
      try {
        var rps = await this.$root.context.get(
          "/web-gqkn/tiep-nhan-khieu-nai/lay_ds_thamso_md"
        );
        return rps.data;
      } catch (error) {
        return [];
      }
    },
    async TaoMaKN() {
      var data = {
        NAM: "",
      };
      var rps = await this.$root.context.post(
        "/web-gqkn/tiep-nhan-khieu-nai/taomakn",
        data
      );
      if (rps.data.length > 0) {
        return rps.data[0].MA_KN;
      } else {
        return null;
      }
    },
    async tsbtnChapNhan_Click() {
      try {
        if (this.dsLoi.length > 0) {
          this.$root.$toast.error("Có thông tin lỗi. Bạn hãy kiểm tra lại!");
          return;
        }
        if (this.dsDB.length > 0) {
          let confirm = await this.$confirm(
            "Có thuê bao trong danh sách thuê bao đặc biệt. Bạn có muốn tiếp tục thực hiện không?",
            "Thông báo",
            {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
              type: "warning",
            }
          );
          if (confirm !== "confirm") {
            return;
          }
        }
        let confirm = await this.$confirm(
          "Bạn chắc chắn muốn tiếp nhận khiếu nại không ?",
          "Thông báo",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "warning",
          }
        );
        if (confirm !== "confirm") {
          return;
        }
        await this.TaoDL();
      } catch (error) {
        console.log(
          "🚀 ~ file: index.vue ~ line 99 ~ tsbtnChapNhan_Click ~ error",
          error
        );
      }
    },
    async ReadFile(fileExcel) {
      this.loading(true);
      console.log("🚀 ~ file: index.vue ~ line 70 ~ ReadFile ~ fileExcel", fileExcel);
      try {
        if (this.fileExcel === null) return;
        this.dsKhieuNai = [];
        this.dsLoi = [];
        this.dsDB = [];
        this.lblKetQua = null;
        let s = this.fileExcel.name.toLowerCase();
        let dsTmp = [];
        if (s.endsWith(".xlsx") || s.endsWith(".xls")) {
          let reader = new FileReader();
          reader.onload = async (e) => {
            const data = e.target.result;
            const workBook = xlsx.read(data, { type: "binary" });
            if (workBook != null) {
              for (let i = 0; i < workBook.SheetNames.length; i++) {
                const sheetName = workBook.SheetNames[i];
                dsTmp = xlsx.utils.sheet_to_json(workBook.Sheets[sheetName], {
                  header: "A",
                  defval: "",
                });
                dsTmp.shift();
                console.log(
                  "🚀 ~ file: index.vue ~ line 86 ~ reader.onload= ~ dsTmp",
                  dsTmp
                );
              }
            }
            console.log("🚀 ~ file: index.vue ~ line 120 ~ ReadFile ~ dsTmp", dsTmp);
            if (dsTmp.length > 0) {
              let l1 = dsTmp.filter((m) => {
                return m.A === "" || m.A === null;
              });
              if (l1.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = l1.map((m) => {
                  return {
                    CHUDEKN_ID: m.A,
                    MA_TT: m.B.toString().trim(),
                    MA_TB: m.C.toString().trim(),
                    TRANGTHAI: "Thiếu Chủ đề khiếu nại",
                  };
                });
                return false;
              }

              let l2 = dsTmp.filter((m) => {
                return m.A !== "" && m.A !== null;
              });
              let l3 = l2.filter((m) =>
                this.dsChuDe_KN.every((fd) => fd.CHUDEKN_ID !== m.A.toString())
              );

              console.log("🚀 ~ file: index.vue ~ line 124 ~ reader.onload= ~ l3", l3);

              if (l3.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l3.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B.toString().trim(),
                      MA_TB: m.C.toString().trim(),
                      TRANGTHAI: "Không có chủ đề khiếu nại này",
                    };
                  })
                );
                return false;
              }

              let l4 = dsTmp.filter((m) => {
                return m.B === "" || m.B === null;
              });
              if (l4.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l4.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B.toString().trim(),
                      MA_TB: m.C.toString().trim(),
                      TRANGTHAI: "Thiếu Mã thanh toán",
                    };
                  })
                );
                return false;
              }

              let l5 = dsTmp.filter((m) => {
                return m.C === "" || m.C === null;
              });
              if (l5.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l5.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B.toString().trim(),
                      MA_TB: m.C.toString().trim(),
                      TRANGTHAI: "Thiếu Mã thuê bao",
                    };
                  })
                );
                return false;
              }

              let l6 = dsTmp.filter((m) => {
                return m.D === "" || m.D === null;
              });
              if (l6.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l6.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B.toString().trim(),
                      MA_TB: m.C.toString().trim(),
                      TRANGTHAI: "Thiếu Điện thoại liên hệ",
                    };
                  })
                );
                return false;
              }

              let l7 = dsTmp.filter((m) => {
                return m.E === "" || m.E === null;
              });
              if (l7.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l7.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B.toString().trim(),
                      MA_TB: m.C.toString().trim(),
                      TRANGTHAI: "Thiếu Nội dung khiếu nại",
                    };
                  })
                );
                return false;
              }
              var startTime = Date.now();
              try {
                var rs = await this.$root.context.post(
                  "/web-gqkn/tiep-nhan-khieu-nai/delete_khieunai_tmp",
                  { VNGUOIDUNG_ID: this.$root.token.getNguoiDungID() }
                );
                for (let index = 0; index < dsTmp.length; index++) {
                  const element = dsTmp[index];
                  let data = {
                    VNGUOIDUNG_ID: this.$root.token.getNguoiDungID(),
                    VCHUDEKN_ID: element.A,
                    VMA_TT: element.B,
                    VMA_TB: element.C,
                    VDIENTHOAI_LH: element.D,
                    VNOIDUNG_KN: element.E,
                  };
                  console.log(
                    "🚀 ~ file: index.vue ~ line 219 ~ reader.onload= ~ data",
                    data
                  );
                  let rs = await this.$root.context.post(
                    "/web-gqkn/tiep-nhan-khieu-nai/insert_khieunai_tmp",
                    data
                  );
                  console.log(
                    "🚀 ~ file: index.vue ~ line 223 ~ reader.onload= ~ rs",
                    rs
                  );
                }
                var endTime = Date.now();

                rs = await this.$root.context.post(
                  "/web-gqkn/tiep-nhan-khieu-nai/lay_khieunai_file_tmp",
                  {
                    VNGUOIDUNG_ID: this.$root.token.getNguoiDungID(),
                  }
                );
                console.log("🚀 ~ file: index.vue ~ line 234 ~ reader.onload= ~ rs", rs);
                if (rs.data.length > 0) {
                  this.dsLoi = rs.data;
                  this.lblKetQua = "Có thông tin lỗi";
                  this.$root.$toast.error("Có thông tin lỗi");
                  // return
                }

                rs = await this.$root.context.post(
                  "/web-gqkn/tiep-nhan-khieu-nai/lay_tb_dacbiet_khieunai",
                  {
                    VNGUOIDUNG_ID: this.$root.token.getNguoiDungID(),
                  }
                );
                console.log("🚀 ~ file: index.vue:618 ~ reader.onload= ~ rs:", rs);
                if (rs.data.length > 0) {
                  this.dsDB = rs.data;
                  this.lblKetQua = "Có thuê bao đặc biệt";
                  // return
                }

                this.dsKhieuNai = dsTmp.map((m) => {
                  return {
                    CHUDEKN_ID: m.A,
                    MA_TT: m.B.toString().trim(),
                    MA_TB: m.C.toString().trim(),
                    DIENTHOAI_LH: m.D.toString().trim(),
                    NOIDUNG_KN: m.E.toString().trim(),
                    TINHTRANG: 0,
                  };
                });
                console.log(
                  "🚀 ~ file: index.vue ~ line 242 ~ reader.onload= ~ this.dsKhieuNai",
                  this.dsKhieuNai
                );
              } catch (error) {
                console.log(
                  "🚀 ~ file: index.vue ~ line 208 ~ reader.onload= ~ error",
                  error
                );
              }
            }
          };
          reader.readAsBinaryString(this.fileExcel);
        }
      } catch (error) {
        console.log("🚀 ~ file: index.vue ~ line 74 ~ ReadFile ~ error", error);
      } finally {
        console.log("🚀 ~ file: index.vue ~ line 174 ~ ReadFile ~ finally");
        this.loading(false);
      }
    },
    async ReadFile2(fileExcel) {
      if (this.fileExcel === null) return;
      this.dsKhieuNai = [];
      this.dsLoi = [];
      this.dsDB = [];
      this.lblKetQua = null;
      let s = this.fileExcel.name.toLowerCase();
      if (s.endsWith(".xlsx") || s.endsWith(".xls")) {
        const reader = new FileReader();
        reader.readAsBinaryString(this.fileExcel);
        const result = await new Promise((resolve, reject) => {
          reader.onload = async (e) => {
            let dsTmp = [];
            const data = e.target.result;
            const workBook = xlsx.read(data, { type: "binary" });
            if (workBook != null) {
              for (let i = 0; i < workBook.SheetNames.length; i++) {
                const sheetName = workBook.SheetNames[i];
                dsTmp = xlsx.utils.sheet_to_json(workBook.Sheets[sheetName], {
                  header: "A",
                  defval: "",
                });
                dsTmp.shift();
              }
            }
            console.log("🚀 ~ file: index.vue ~ line 120 ~ ReadFile ~ dsTmp", dsTmp);
            if (dsTmp.length > 0) {
              let l1 = dsTmp.filter((m) => {
                return m.A === "" || m.A === null;
              });
              if (l1.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = l1.map((m) => {
                  return {
                    CHUDEKN_ID: m.A,
                    MA_TT: m.B,
                    MA_TB: m.C,
                    TRANGTHAI: "Thiếu Chủ đề khiếu nại",
                  };
                });
                resolve(false);
              }

              let l2 = dsTmp.filter((m) => {
                return m.A !== "" && m.A !== null;
              });
              let l3 = l2.filter((m) =>
                this.dsChuDe_KN.every((fd) => fd.CHUDEKN_ID !== m.A.toString())
              );

              console.log("🚀 ~ file: index.vue ~ line 124 ~ reader.onload= ~ l3", l3);

              if (l3.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l3.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B,
                      MA_TB: m.C,
                      TRANGTHAI: "Không có chủ đề khiếu nại này",
                    };
                  })
                );
                resolve(false);
              }

              let l4 = dsTmp.filter((m) => {
                return m.B === "" || m.B === null;
              });
              if (l4.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l4.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B,
                      MA_TB: m.C,
                      TRANGTHAI: "Thiếu Mã thanh toán",
                    };
                  })
                );
                resolve(false);
              }

              let l5 = dsTmp.filter((m) => {
                return m.C === "" || m.C === null;
              });
              if (l5.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l5.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B,
                      MA_TB: m.C,
                      TRANGTHAI: "Thiếu Mã thuê bao",
                    };
                  })
                );
                resolve(false);
              }

              let l6 = dsTmp.filter((m) => {
                return m.D === "" || m.D === null;
              });
              if (l6.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l6.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B,
                      MA_TB: m.C,
                      TRANGTHAI: "Thiếu Điện thoại liên hệ",
                    };
                  })
                );
                resolve(false);
              }

              let l7 = dsTmp.filter((m) => {
                return m.E === "" || m.E === null;
              });
              if (l7.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l7.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B,
                      MA_TB: m.C,
                      TRANGTHAI: "Thiếu Nội dung khiếu nại",
                    };
                  })
                );
                resolve(false);
              }
              try {
                var rs = await this.$root.context.post(
                  "/web-gqkn/tiep-nhan-khieu-nai/delete_khieunai_tmp",
                  { VNGUOIDUNG_ID: this.$root.token.getNguoiDungID() }
                );
                for (let index = 0; index < dsTmp.length; index++) {
                  // let progress = parseInt(String((index + 1 / dsTmp.length) * 100))
                  // console.log('🚀 ~ file: index.vue:791 ~ reader.onload= ~ progress:', progress)
                  const element = dsTmp[index];
                  try {
                    let data = {
                      VNGUOIDUNG_ID: this.$root.token.getNguoiDungID(),
                      VCHUDEKN_ID: element.A,
                      VMA_TT: element.B,
                      VMA_TB: element.C,
                      VDIENTHOAI_LH: element.D,
                      VNOIDUNG_KN: element.E,
                    };
                    console.log(
                      "🚀 ~ file: index.vue ~ line 219 ~ reader.onload= ~ data",
                      data
                    );
                    let rs = await this.$root.context.post(
                      "/web-gqkn/tiep-nhan-khieu-nai/insert_khieunai_tmp",
                      data
                    );
                    console.log(
                      "🚀 ~ file: index.vue ~ line 223 ~ reader.onload= ~ rs",
                      rs
                    );
                  } catch (error) {
                    this.dsLoi.push({
                      CHUDEKN_ID: element.A,
                      MA_TT: element.B,
                      MA_TB: element.C,
                      TRANGTHAI: error.response.data.message_detail,
                    });
                    console.log(
                      "🚀 ~ file: index.vue ~ line 208 ~ reader.onload= ~ error",
                      error
                    );
                    this.lblKetQua =
                      "Có thông tin lỗi: " + error.response.data.message_detail;
                    // this.$root.$toast.error('Lỗi load dữ liệu!!!' + '\r\n' + error.response.data.message_detail)
                  }
                }
                rs = await this.$root.context.post(
                  "/web-gqkn/tiep-nhan-khieu-nai/lay_khieunai_file_tmp",
                  {
                    VNGUOIDUNG_ID: this.$root.token.getNguoiDungID(),
                  }
                );
                console.log("🚀 ~ file: index.vue ~ line 234 ~ reader.onload= ~ rs", rs);
                if (rs.data.length > 0) {
                  this.dsLoi = [...this.dsLoi, ...rs.data];
                  this.lblKetQua = "Có thông tin lỗi";
                  this.$root.$toast.error("Có thông tin lỗi");
                }
                rs = await this.$root.context.post(
                  "/web-gqkn/tiep-nhan-khieu-nai/lay_tb_dacbiet_khieunai",
                  {
                    VNGUOIDUNG_ID: this.$root.token.getNguoiDungID(),
                  }
                );
                console.log("🚀 ~ file: index.vue:618 ~ reader.onload= ~ rs:", rs);
                if (rs.data.length > 0) {
                  this.dsDB = rs.data;
                  this.lblKetQua = "Có thuê bao đặc biệt";
                }
                this.dsKhieuNai = dsTmp.map((m) => {
                  return {
                    CHUDEKN_ID: m.A,
                    MA_TT: m.B,
                    MA_TB: m.C,
                    DIENTHOAI_LH: m.D,
                    NOIDUNG_KN: m.E,
                  };
                });
                console.log(
                  "🚀 ~ file: index.vue ~ line 242 ~ reader.onload= ~ this.dsKhieuNai",
                  this.dsKhieuNai
                );
              } catch (error) {
                console.log(
                  "🚀 ~ file: index.vue ~ line 208 ~ reader.onload= ~ error",
                  error
                );
                this.lblKetQua = "Có thông tin lỗi: " + error.message;
                this.$root.$toast.error("Lỗi load dữ liệu!!!" + "\r\n" + error.message);
                resolve(false);
              }
            }
            resolve(true);
          };
        });
        console.log(result);
      }
    },
    async checkATTT(fileExcel) {
      if (this.fileExcel === null) return;
      this.dsKhieuNai = [];
      this.dsLoi = [];
      this.dsDB = [];
      this.lblKetQua = null;
      let s = this.fileExcel.name.toLowerCase();
      if (s.endsWith(".xlsx") || s.endsWith(".xls")) {
        const reader = new FileReader();
        reader.readAsBinaryString(this.fileExcel);
        const result = await new Promise((resolve, reject) => {
          reader.onload = async (e) => {
            let dsTmp = [];
            const data = e.target.result;
            const workBook = xlsx.read(data, { type: "binary" });
            if (workBook != null) {
              for (let i = 0; i < workBook.SheetNames.length; i++) {
                const sheetName = workBook.SheetNames[i];
                dsTmp = xlsx.utils.sheet_to_json(workBook.Sheets[sheetName], {
                  header: "A",
                  defval: "",
                });
                dsTmp.shift();
              }
            }
            console.log("🚀 ~ file: index.vue ~ line 120 ~ ReadFile ~ dsTmp", dsTmp);
            if (dsTmp.length > 0) {
              try {
                for (let index = 0; index < dsTmp.length; index++) {
                  const element = dsTmp[index];
                  try {
                    let data = {
                      VCHUDEKN_ID: element.A,
                      VMA_TT: element.B,
                      VMA_TB: element.C,
                      VDIENTHOAI_LH: element.D,
                      VNOIDUNG_KN: element.E,
                    };
                    let rs = await this.$root.context.post(
                      "/web-gqkn/tiep-nhan-khieu-nai/check_attt",
                      data
                    );
                    console.log("🚀 ~ file: index.vue:974 ~ reader.onload= ~ rs:", rs);
                  } catch (error) {
                    this.dsLoi.push({
                      CHUDEKN_ID: element.A,
                      MA_TT: element.B,
                      MA_TB: element.C,
                      TRANGTHAI: error.response.data.message_detail,
                    });
                    console.log(
                      "🚀 ~ file: index.vue ~ line 208 ~ reader.onload= ~ error",
                      error
                    );
                    this.lblKetQua =
                      "Có thông tin lỗi: " + error.response.data.message_detail;
                  }
                }
                if (this.dsLoi.length > 0) {
                  this.passATTT = false;
                  this.$root.$toast.error(
                    "File excel có chứa thông tin vi phạm ATTT, đề nghị kiểm tra lại hay liên hệ Admin để được hỗ trợ!!!!"
                  );
                } else {
                  this.passATTT = true;
                }
              } catch (error) {
                console.log(
                  "🚀 ~ file: index.vue ~ line 208 ~ reader.onload= ~ error",
                  error
                );
                this.lblKetQua = "Có thông tin lỗi: " + error.message;
                this.$root.$toast.error("Lỗi load dữ liệu!!!" + "\r\n" + error.message);
                resolve(false);
              }
            }
            resolve(true);
          };
        });
        console.log(result);
      }
    },
    async ReadFile3(fileExcel) {
      if (this.fileExcel === null) return;
      this.dsKhieuNai = [];
      this.dsLoi = [];
      this.dsDB = [];
      this.lblKetQua = null;
      let s = this.fileExcel.name.toLowerCase();
      if (s.endsWith(".xlsx") || s.endsWith(".xls")) {
        const reader = new FileReader();
        reader.readAsBinaryString(this.fileExcel);
        const result = await new Promise((resolve, reject) => {
          reader.onload = async (e) => {
            let dsTmp = [];
            const data = e.target.result;
            
            const workBook = xlsx.read(data, { type: "binary" });
            

            if (workBook != null) {
              //for (let i = 0; i < workBook.SheetNames.length; i++) {                
                const sheetName = workBook.SheetNames[0];
                dsTmp = xlsx.utils.sheet_to_json(workBook.Sheets[sheetName], {
                  header: "A",
                  defval: "",
                });                
                dsTmp.shift();
              //}
            }
            console.log("🚀 ~ file: index.vue ~ line 120 ~ ReadFile ~ dsTmp", dsTmp);
            if (dsTmp.length > 0) {
              let l1 = dsTmp.filter((m) => {
                return m.A === "" || m.A === null;
              });
              if (l1.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = l1.map((m) => {
                  return {
                    CHUDEKN_ID: m.A,
                    MA_TT: m.B,
                    MA_TB: m.C,
                    TRANGTHAI: "Thiếu Chủ đề khiếu nại",
                  };
                });
                resolve(false);
                return;
              }

              let l2 = dsTmp.filter((m) => {
                return m.A !== "" && m.A !== null;
              });
              let l3 = l2.filter((m) =>
                this.dsChuDe_KN.every((fd) => fd.CHUDEKN_ID !== m.A.toString())
              );

              console.log("🚀 ~ file: index.vue ~ line 124 ~ reader.onload= ~ l3", l3);

              if (l3.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l3.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B,
                      MA_TB: m.C,
                      TRANGTHAI: "Không có chủ đề khiếu nại này",
                    };
                  })
                );
                resolve(false);
                return;
              }

              let l4 = dsTmp.filter((m) => {
                return m.B === "" || m.B === null;
              });
              if (l4.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l4.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B,
                      MA_TB: m.C,
                      TRANGTHAI: "Thiếu Mã thanh toán",
                    };
                  })
                );
                resolve(false);
                return;
              }

              let l5 = dsTmp.filter((m) => {
                return m.C === "" || m.C === null;
              });
              if (l5.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l5.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B,
                      MA_TB: m.C,
                      TRANGTHAI: "Thiếu Mã thuê bao",
                    };
                  })
                );
                resolve(false);
                return;
              }

              let l6 = dsTmp.filter((m) => {
                return m.D === "" || m.D === null;
              });
              if (l6.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l6.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B,
                      MA_TB: m.C,
                      TRANGTHAI: "Thiếu Điện thoại liên hệ",
                    };
                  })
                );
                resolve(false);
                return;
              }

              let l7 = dsTmp.filter((m) => {
                return m.E === "" || m.E === null;
              });
              if (l7.length > 0) {
                this.lblKetQua = "Có thông tin lỗi";
                this.dsLoi = this.dsLoi.concat(
                  l7.map((m) => {
                    return {
                      CHUDEKN_ID: m.A,
                      MA_TT: m.B,
                      MA_TB: m.C,
                      TRANGTHAI: "Thiếu Nội dung khiếu nại",
                    };
                  })
                );
                resolve(false);
                return;
              }
              try {
                var rs = await this.$root.context.post(
                  "/web-gqkn/tiep-nhan-khieu-nai/delete_khieunai_tmp",
                  { VNGUOIDUNG_ID: this.$root.token.getNguoiDungID() }
                );
                let myArr = [];
                for (let index = 0; index < dsTmp.length; index++) {
                  let progress = Number(String(((index + 1) / dsTmp.length) * 100));
                  console.log(
                    "🚀 ~ file: index.vue:791 ~ reader.onload= ~ progress:",
                    progress
                  );
                  const element = dsTmp[index];
                  try {
                    let data = {
                      VNGUOIDUNG_ID: this.$root.token.getNguoiDungID(),
                      VCHUDEKN_ID: element.A,
                      VMA_TT: element.B,
                      VMA_TB: element.C,
                      VDIENTHOAI_LH: element.D,
                      VNOIDUNG_KN: element.E,
                    };
                    myArr.push(data);
                  } catch (error) {
                    this.dsLoi.push({
                      CHUDEKN_ID: element.A,
                      MA_TT: element.B,
                      MA_TB: element.C,
                      TRANGTHAI: error.response.data.message_detail,
                    });
                    console.log(
                      "🚀 ~ file: index.vue ~ line 208 ~ reader.onload= ~ error",
                      error
                    );
                    this.lblKetQua =
                      "Có thông tin lỗi: " + error.response.data.message_detail;
                  }
                }
                let myJSON = JSON.stringify(myArr);
                console.log(
                  "🚀 ~ file: index.vue:1012 ~ reader.onload= ~ myJSON:",
                  myJSON
                );
                rs = await this.$root.context.post(
                  "/web-gqkn/tiep-nhan-khieu-nai/insert_khieunai_tmp_v2",
                  { myJSON }
                );
                console.log("🚀 ~ file: index.vue:1018 ~ reader.onload= ~ rs:", rs);
                rs = await this.$root.context.post(
                  "/web-gqkn/tiep-nhan-khieu-nai/lay_khieunai_file_tmp",
                  { VNGUOIDUNG_ID: this.$root.token.getNguoiDungID() }
                );
                console.log("🚀 ~ file: index.vue ~ line 234 ~ reader.onload= ~ rs", rs);
                if (rs.data.length > 0) {
                  this.dsLoi = [...this.dsLoi, ...rs.data];
                  this.lblKetQua = "Có thông tin lỗi";
                  this.$root.$toast.error("Có thông tin lỗi");
                }
                rs = await this.$root.context.post(
                  "/web-gqkn/tiep-nhan-khieu-nai/lay_tb_dacbiet_khieunai",
                  { VNGUOIDUNG_ID: this.$root.token.getNguoiDungID() }
                );
                console.log("🚀 ~ file: index.vue:618 ~ reader.onload= ~ rs:", rs);
                if (rs.data.length > 0) {
                  this.dsDB = rs.data;
                  this.lblKetQua = "Có thuê bao đặc biệt";
                }
                this.dsKhieuNai = dsTmp.map((m) => {
                  return {
                    CHUDEKN_ID: m.A,
                    MA_TT: m.B,
                    MA_TB: m.C,
                    DIENTHOAI_LH: m.D,
                    NOIDUNG_KN: m.E,
                    TRANGTHAI: "Chưa thực hiện",
                  };
                });
                console.log(
                  "🚀 ~ file: index.vue ~ line 242 ~ reader.onload= ~ this.dsKhieuNai",
                  this.dsKhieuNai
                );
              } catch (error) {
                console.log(
                  "🚀 ~ file: index.vue ~ line 208 ~ reader.onload= ~ error",
                  error
                );
                this.lblKetQua = "Có thông tin lỗi: " + error.message;
                this.$root.$toast.error("Lỗi load dữ liệu!!!" + "\r\n" + error.message);
                resolve(false);
                return;
              }
            }
            resolve(true);
          };
        });
        console.log(result);
      }
    },
  },
  mounted: async function () {
    var dt = await this.LAY_DS_THAMSO_MD2({ P_THAMSO: "THEM_KHDB_KHI_KHIEUNAI" });
    if (dt.length > 0) {
      let strvtemp = "THEM_KHDB_KHI_KHIEUNAI";
      let o = dt.find((e) => e.MA_TS === strvtemp);
      if (o !== undefined) {
        if (o.TEN_TS.toString() === "1") {
          this.THEM_KHDB_KHI_KHIEUNAI = 1;
        }
      }
    } else {
      this.THEM_KHDB_KHI_KHIEUNAI = 0;
    }
  },
};
</script>
<style scoped src="../../index.scss"></style>
