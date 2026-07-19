<template src="./MenuGroup.html"></template>
<style  src="./MenuGroup.scss"></style>
<script>
import gridView from "@/components/Shared/gridview.vue";
import breadcrumb from "@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue";
import "flatpickr/dist/plugins/monthSelect/style.css";
import { required, minLength } from "vuelidate/lib/validators";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import bssFlatPicker from "@/components/BssDatePicker";
import moment from "moment";
import api from "./API";
//src/modules/admin/category/MenuGroup Replace code của module hiện tại từ nhánh kypc_dev.
export default {
  components: {
    breadcrumb,
    gridView,
    bssFlatPicker,
    DatePicker,
    moment,
  },
  props: {
    tag_prop: {
      type: String,
      default: "NHOM_MENU", //UR1.1.104:NHOM_MENU - UR1.2.011:NHOM_ND - UR1.1.108:NHOMHAM - UR1.1.102:NHOM_CN - UR1.1.109:DS_HAM
    },
  },
  name: "MenuGroup",
  data() {
    return {
      testAPI: {},
      header: {
        title: "Nhập dữ liệu từ điển",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          { name: "Lắp đặt mới", link: { name: "Ui.cards" } },
        ],
      },
      date: new Date(),
      dateFormat: "DD/MM/YYYY",
      typeFormat: "date",
      dateConfig: {
        dateFormat: "d/m/Y",
        altFormat: "d/m/Y",
      },
      // khai bao
      schema: "",
      where: "",
      cot_sapxep: "",
      socot_ht: 0,
      kieuld_id: 0,
      pnloaihinhtbStatus: true,
      DichVuVTStatus: true,
      focus_input: "",
      dsCot: [],
      bangts_id: "",
      DmBangTS: [],
      dichvuvt_id: 0,
      DmDichVuVT: [],
      cbb_id: 0,
      DmCombobox: [],
      checkRequire : {
        NHOM_MENU_ID: "Nhóm Menu ID là bắt buộc!",
        NHOM_MENU: "Nhóm Menu là bắt buộc!",
        TEN_NHOM: "Tên nhóm là bắt buộc!",
        NHOMHAM_ID: "Nhóm hàm ID là bắt buộc!",
        NHOMHAM: "Nhóm hàm là bắt buộc!",
        NHOM_ND_ID: "Nhóm ND ID là bắt buộc!",
        NHOM_ND: "Nhóm hàm là bắt buộc!",
        NHOM_CN_ID: "Nhóm ID là bắt buộc!",
        IS_BSC: "IS_BSC là bắt buộc!",
        QUYEN_ID: "Quyền ID là bắt buộc!",
        TEN_QUYEN: "Tên quyền là bắt buộc!"
      },

      isNumber: ['NHOM_MENU_ID', 'NHOMHAM_ID', 'NHOM_CN_ID', 'NHOM_ND_ID', 'QUYEN_ID'],

      checkLength : {
        TEN_NHOM: {
          leng: 50,
          message: "Tên nhóm không được vượt quá 50 ký tự!"
        },
        GHICHU: {
          leng: 255,
          message: "Ghi chú không được vượt quá 255 ký tự!"
        },
        QUYEN_ID: {
          leng: 9,
          message: "Quyen ID không được vượt quá 9 ký tự!"
        }
      },

      btnNhapMoiStatus: false,
      btnGhiLaiStatus: false,
      btnXoaStatus: false,
      btnHuyStatus: false,
      isNhapMoi: 0,
      rowSelected: {},
      pageChange: false,
      lastPage: 0,
      last_nhomham_id: null,
      loadDsCot: false,
      DsDuLieu: {
        headers: [
          {
            fieldName: "ham_id",
            headerText: "Hàm ID",
            allowFiltering: true,
            allowSorting: false,
            width: 50,
          },
          {
            fieldName: "ma_ham",
            headerText: "Mã hàm",
            allowFiltering: true,
            allowSorting: false,
            width: 50,
          },
          {
            fieldName: "ten_ham",
            headerText: "Tên hàm",
            allowFiltering: true,
            allowSorting: false,
            width: 50,
          },
          {
            fieldName: "ghichu",
            headerText: "Ghi chú",
            allowFiltering: true,
            allowSorting: false,
            width: 50,
          },
        ],
        data: [],
        selected: 0,
        checked: [],
      },
      DsLoaiTBCG: {
        headers: [
          {
            fieldName: "loaihinh_tb",
            headerText: "Loại TB",
            allowFiltering: true,
            width: 150,
          },
        ],
        data: [],
        selected: 0,
        checked: [],
      },

      DsLoaiTBDG: {
        headers: [
          {
            fieldName: "loaihinh_tb",
            headerText: "Loại TB",
            allowFiltering: true,
            width: 150,
          },
        ],
        data: [],
        selected: 0,
        checked: [],
      },
    };
  },

  computed: {
    tag() {
      if (this.$route.query.tag) {
        return this.$route.query.tag;
      } else if (this.tag_prop) {
        return this.tag_prop;
      } else {
        return "NHOM_MENU";
      }
    },

    list_bangts: {
      get() {
        var listbangts = [...new Set(this.DmBangTS)].map((x) => ({
          id: x.TENBANG,
          text: x.TEN_HIENTHI,
        }));
        return listbangts;
      },
    },
  },
  watch: {
    bangts_id: async function (val) {
      this.schema = this.DmBangTS.filter((x) => x.TENBANG == val)[0].TEN_SCHEMA;
      this.cot_sapxep = this.DmBangTS.filter(
        (x) => x.TENBANG == val
      )[0].COT_XAPXEP;
      this.socot_ht = this.DmBangTS.filter((x) => x.TENBANG == val)[0].SOCOT_HT;

      // lay dsCot
      this.dsCot = await this.getDS_Cot_TB();
      await this.CreateDynamicControl();

      // focus
      if (this.tag == "NHOMHAM") {
        this.header.title = "Nhóm hàm";
        this.$refs["NHOMHAM_ID"][0].focus();
      }

      //check theo dieu kiem bang ts
      if (val == "KIEU_LD") {
        this.pnloaihinhtbStatus = true;
        this.DichVuVTStatus = true;
      } else {
        this.pnloaihinhtbStatus = false;
        this.DichVuVTStatus = false;
      }
      this.LoadDuLieu(0);
    },

    dichvuvt_id: async function () {
      if (this.bangts_id == "KIEU_LD") {
        this.HienThiLoaiHinhTB();
      }
    },

    Loading: function (val, oldval) {
      this.$root.showLoading(val);
    }
  },
  async created() {
    this.Loading = true;
    try {
      Promise.all([this.getParam_load(), this.SetButtom(0)]);
    } catch (ex) {
      this.Loading = false;
    }
    this.Loading = false;
  },
  validations: {
    // LoaiNhanVienId: {
    //   required,
    //   minLength: minLength(1)
    // },
    // PopUpNhanVienQL: {
    //   manhanvien:
    //   {
    //     required,
    //     minLength: minLength(3)
    //   }
    // }
  },
  methods: {
    showAPI() {
      console.log(JSON.parse(JSON.stringify(this.DsDuLieu)))
    },
    getParam_load: async function () {
      var pdata = {};
      api.getDsBangTS(this.axios, pdata).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          var dataApi = response.data.data;
          if (this.tag && this.tag != "") {
            this.DmBangTS = dataApi.filter((x) => x.TENBANG == this.tag);
            this.bangts_id = dataApi.filter(
              (x) => x.TENBANG == this.tag
            )[0].TENBANG;
          } else {
            this.DmBangTS = dataApi;
            this.bangts_id = dataApi[0].TENBANG;
          }
        } else {
          this.DmBangTS = [];
          this.bangts_id = "";
        }
      });
      // Hiển thị cbo dịch vụ vt
      var pdata_dv = {};
      api.getDsDichVuVT(this.axios, pdata_dv).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          var dataApi = response.data.data;
          this.DmDichVuVT = dataApi.map((x) => ({
            id: x.DICHVUVT_ID,
            text: x.TEN_DVVT,
          }));
          this.dichvuvt_id = dataApi[0].DICHVUVT_ID;
        } else {
          this.DmDichVuVT = [];
          this.dichvuvt_id = 0;
        }
      });
    },

    getDS_Cot_TB: async function () {
      var pdata_dl = {};
      this.loadDsCot = false
      let data = await api.getDsCotTB(this.axios, pdata_dl);
      this.loadDsCot = true
      if (
        data &&
        data.data &&
        data.data.data &&
        data.data.error_code &&
        data.data.error_code === "BSS-00000000"
      ) {
        return data.data.data.filter((x) => x.TENBANG == this.tag);
      } else {
        return [];
      }
    },

    LoadDuLieu: async function (kieu) {
      this.loading(true);
      try {
        if (kieu == 0) {
          // load du lieu DsDuLieu
          var header_ds = [];
          for (var i of this.dsCot.filter((x) => x.HIENTHI == 1)) {
            var row_header = {};
            row_header.fieldName = "" + i.TENCOT.toLowerCase() + "";
            row_header.headerText = "" + i.NHAN_COT + "";
            row_header.allowFiltering = true;
            row_header.allowSorting = false;

            header_ds.push(row_header);
          }
          // bind dong header vao grid
          this.DsDuLieu.headers = header_ds;
        }

        if (kieu == 2) {
          this.where = "";
          this.dsCot.forEach((item) => {
            if (
              this.$refs[item.TENCOT][0].value != undefined &&
              this.$refs[item.TENCOT][0].value != null &&
              this.$refs[item.TENCOT][0].value.trim() != ""
            ) {
              this.where +=
                item.TENCOT +
                " = '" +
                this.$refs[item.TENCOT][0].value.trim() +
                "' AND ";
            }
          });
          if (this.where.length > 4) {
            this.where =
              "WHERE " + this.where.substring(0, this.where.length - 4);
          }
        }

        var pdata_dl = {
          vschema: "ADMIN",
          vtenbang: this.tag,
          vwhere: this.where,
          vcot_xapxep: this.cot_sapxep,
        };
        await api.getDsNhomMenu(this.axios, pdata_dl).then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            var dataApi = response.data.data;
            // bind dong data vao grid
            this.DsDuLieu.data = dataApi;
          } else {
            this.DsDuLieu.data = [];
          }
        });
      } catch (ex) {}
      this.loading(false);
    },

    CreateDynamicControl: async function () {
      if (this.dsCot && this.dsCot.length > 0) {
        this.dsCot.forEach((item) => {
          this.$set(item, "value", null);
          if (item.KIEU_RB == 2) {
            var pdata_dl = {
              vtencot_rb: item.TENCOT_RB,
              vtencot_htrb: item.TENCOT_HTRB,
              vschema_rb: item.SCHEMA_RB.replace("_HPG", ""),
              vtenbang_rb: item.TENBANG_RB,
            };
            api.getDsTenCotTRB(this.axios, pdata_dl).then((response) => {
              if (
                response &&
                response.data &&
                response.data.data &&
                response.data.error_code &&
                response.data.error_code === "BSS-00000000"
              ) {
                var dataApi = response.data.data;
                this.DmCombobox = dataApi.map((x) => ({
                  id: x.code,
                  text: x.name,
                }));
                this.cbb_id = dataApi[0].code;
              } else {
                this.DmCombobox = [];
                this.cbb_id = null;
              }
            });
          }
        });
      }
    },

    gridDSDuLieuSelected: async function (args) {
      var data = JSON.parse(JSON.stringify(args.data));
      if (args) {
        var data_val;
        for (var item of this.dsCot) {
          data_val = data["" + item.TENCOT.toLowerCase() + ""];
          if (item.KIEU_RB == 2) {
            this.cbb_id = data_val;
          } else {
            item.value = data_val;
          }
        }

        this.clearValidate()
        this.SetButtom(3);
        this.rowSelected = data;
      }
    },

    SetButtom: async function (kieu) {
      let enableNhomham = true;
      if (kieu == -1) {
        // Bat dau
        this.btnGhiLaiStatus = true;
        this.btnHuyStatus = true;
        this.isNhapMoi = 1;
        enableNhomham = true;
      }
      if (kieu == 0) {
        // Bat dau
        this.btnNhapMoiStatus = true;
        this.btnGhiLaiStatus = false;
        this.btnXoaStatus = false;
        this.btnHuyStatus = false;
        this.clear();
        this.isNhapMoi = 1;
        enableNhomham = true;
      }
      if (kieu == 1) {
        // Them moi
        this.btnNhapMoiStatus = false;
        this.btnGhiLaiStatus = true;
        this.btnHuyStatus = true;
        this.btnXoaStatus = false;
        this.clear();
        this.isNhapMoi = 0;
        enableNhomham = true;
      }
      if (kieu == 2) {
        // Huy
        this.btnNhapMoiStatus = true;
        this.btnXoaStatus = false;
        this.btnGhiLaiStatus = false;
        this.btnHuyStatus = false;
        this.clear();
        this.isNhapMoi = 1;
        enableNhomham = true;
      }
      if (kieu == 3) {
        // Edit
        this.btnNhapMoiStatus = true;
        this.btnGhiLaiStatus = true;
        this.btnXoaStatus = true;
        this.btnHuyStatus = true;
        this.isNhapMoi = 1;
        enableNhomham = false;
      }

      if (this.tag == "NHOMHAM" && this.$refs["NHOMHAM_ID"] != undefined) {
        this.$refs["NHOMHAM_ID"][0].classList.remove("invalid_in");
        this.$refs["TEN_NHOM"][0].classList.remove("invalid_in");
        this.$refs["GHICHU"][0].classList.remove("invalid_in");

        this.$refs["NHOMHAM_ID"][0].disabled = !enableNhomham;
      }
    },

    clear: async function () {
      for (var item of this.dsCot) {
        if (item.KIEU_RB != 2) {
          item.value = "";
        }
      }
    },

    HienThiLoaiHinhTB: async function () {
      // DS loai thue bao chua gan
      var pdata_cg = {
        vkieuld_id: this.kieuld_id,
        vdichvuvt_id: this.dichvuvt_id,
        vkieu: 0,
      };
      api.getLoaiHinhTB(this.axios, pdata_cg).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          var dataApi = response.data.data;
          this.DsLoaiTBCG.data = dataApi;
        } else {
          this.DsLoaiTBCG.data = [];
        }
      });

      // DS loai thue bao da gan
      var pdata_dg = {
        vkieuld_id: this.kieuld_id,
        vdichvuvt_id: this.dichvuvt_id,
        vkieu: 1,
      };
      api.getLoaiHinhTB(this.axios, pdata_dg).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          var dataApi = response.data.data;
          this.DsLoaiTBDG.data = dataApi;
        } else {
          this.DsLoaiTBDG.data = [];
        }
      });
    },
    btnNhapMoi_OnClick: async function () {
      this.SetButtom(1);
      if (this.dsCot.length > 0 && this.$refs[this.dsCot[0].TENCOT].length > 0)
        this.$refs[this.dsCot[0].TENCOT][0].focus();
    },
    clearValidate() {
      for( let key in this.$refs) {
        if(this.$refs[key] && this.$refs[key][0])
          this.$refs[key][0].classList.remove("invalid_in");
      }
    },
    validateData(kieu, data) {


      let check = true
      for( let key in this.$refs) {
        console.log(key)
        if(this.$refs[key] && this.$refs[key][0])
          this.$refs[key][0].classList.remove("invalid_in");
        if(this.checkRequire.hasOwnProperty(key)) {
          if(this.$refs[key][0].value == "") {
            this.$refs[key][0].focus();
            this.$refs[key][0].classList.add("invalid_in");
            this.$toast.error(this.checkRequire[key])
            check = false
          }
        }

        if(this.checkLength.hasOwnProperty(key)) {
          if(this.$refs[key][0].value.length > this.checkLength[key].leng) {
            this.$refs[key][0].focus();
            this.$refs[key][0].classList.add("invalid_in");
            this.$toast.error(this.checkLength[key].message)
            check = false
          }
        }
      }

      return check;
    },
    btnGhiLai_OnClick: async function () {
      var vds = [];
      for (var item of this.dsCot) {
        var dr = {};
        if (item.KIEU_RB == 2) {
          dr["" + item.TENCOT + ""] = this.cbb_id;
        } else {
          dr[item.TENCOT] = item.value;
        }

        vds.push(dr);
      }
      var kieu = 0;
      var msg = "";
      if (this.isNhapMoi == 0) {
        kieu = 0;
        msg = "Thêm mới thông tin thành công!";
      } else {
        kieu = 1;
        msg = "Cập nhật thông tin thành công!";
      }

      // validate
      if (!this.validateData(kieu, vds)) return;

      var pdata_cg = {
        vkieu: kieu,
        vschema: this.schema.replace("_HPG", ""),
        vtenbang: this.tag,
        vds: JSON.stringify(vds).replaceAll("},{", ","),
      };
      try {
        let response = await api.postDLTuDien(this.axios, pdata_cg);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.$toast.success(msg);
          this.SetButtom(3);
          this.LoadDuLieu(1);
        } else {
          this.$toast.error("Thất bại! ");
        }
      } catch (e) {
        console.log(e);
        if (
          e.data &&
          e.data.message_detail &&
          e.data.message_detail.includes("unique constraint (ADMIN.PK_NHOMHAM)")
        ) {
          this.$toast.error("Nhóm hàm ID đã tồn tại");
          this.$refs["NHOMHAM_ID"][0].focus();
          this.$refs["NHOMHAM_ID"][0].classList.add("invalid_in");
        } else if(e.data &&
          e.data.message_detail &&
          e.data.message_detail.includes("unique constraint (ADMIN.PK_NHOM_CN)")) {
            this.$toast.error("Nhóm ID đã tồn tại");
            this.$refs["NHOM_CN_ID"][0].focus();
            this.$refs["NHOM_CN_ID"][0].classList.add("invalid_in");
        } else if (
          e.data &&
          e.data.message_detail &&
          e.data.message_detail.includes("unique constraint (ADMIN.PK_NHOM_MENU)")
        ) {
          this.$toast.error("Nhóm Menu ID đã tồn tại");
          this.$refs["NHOM_MENU_ID"][0].focus();
          this.$refs["NHOM_MENU_ID"][0].classList.add("invalid_in");
        } else {
          this.$toast.error(
            (kieu == 0 ? "Thêm mới " : "Cập nhật ") + "thất bại"
          );
        }
      }
    },
    gridDSDuLieuPageChanged(e) {
      if (e.pageIndex != this.lastPage) {
        this.lastPage = e.pageIndex;
        this.pageChange = true;
      } else {
        this.pageChange = false;
      }
    },
    gridDSDuLieuComplete() {
      if (this.pageChange) {
        this.pageChange = false;

        if (this.tag == "NHOMHAM") {
          let isExist = false;
          if (
            this.last_nhomham_id != undefined &&
            this.last_nhomham_id != null
          ) {
            let pageIndex =
              this.$refs.gridDsDuLieu.$refs.pagination.pageIndexData;
            for (let i = 0; i < this.DsDuLieu.data.length; i++) {
              if (this.DsDuLieu.data[i].nhomham_id == this.last_nhomham_id) {
                let pageRecord = Math.floor(
                  i / this.$refs.gridDsDuLieu.$refs.pagination.pageSizeData
                );
                if (pageRecord == pageIndex) {
                  let iRecord =
                    i % this.$refs.gridDsDuLieu.$refs.pagination.pageSizeData;
                  this.$refs.gridDsDuLieu.selectRow(iRecord);
                }

                isExist = true;
                break;
              }
            }
            this.last_nhomham_id = null;
          }

          if (!isExist) {
            this.$refs.gridDsDuLieu.selectRow(0);
          }
        } else {
          this.$refs.gridDsDuLieu.selectRow(0);
        }
        return;
      }

      if (this.tag == "NHOMHAM") {
        let isExist = false;
        if (this.last_nhomham_id != undefined && this.last_nhomham_id != null) {
          let pageIndex =
            this.$refs.gridDsDuLieu.$refs.pagination.pageIndexData;
          for (let i = 0; i < this.DsDuLieu.data.length; i++) {
            if (this.DsDuLieu.data[i].nhomham_id == this.last_nhomham_id) {
              let pageRecord = Math.floor(
                i / this.$refs.gridDsDuLieu.$refs.pagination.pageSizeData
              );
              if (pageRecord == pageIndex) {
                let iRecord =
                  i % this.$refs.gridDsDuLieu.$refs.pagination.pageSizeData;
                this.$refs.gridDsDuLieu.selectRow(iRecord);
              } else {
                this.$refs.gridDsDuLieu.$refs.pagination.pageIndexData =
                  pageRecord;
              }

              isExist = true;
              break;
            }
          }
        }

        if (!isExist) {
          this.$refs.gridDsDuLieu.selectRow(0);
        }
      } else {
        this.$refs.gridDsDuLieu.selectRow(0);
      }
    },
    btnXoa_OnClick: async function () {
      this.$confirm(
        "Bạn có thực sự muốn xóa thông tin không?",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }
      )
        .then(async (v) => {
          if (v) {
            this.$root.$loading = true;
            try {
              var vds = [];
              for (var item of this.dsCot) {
                var dr = {};
                if (item.KIEU_RB == 2) {
                  dr["" + item.TENCOT + ""] = this.cbb_id;
                } else {
                  dr["" + item.TENCOT + ""] = item.value;
                }

                vds.push(dr);
              }

              var pdata_cg = {
                vkieu: 2,
                vschema: this.schema.replace("_HPG", ""),
                vtenbang: this.tag,
                vds: JSON.stringify(vds).replaceAll("},{", ","),
              };
              api.postDLTuDien(this.axios, pdata_cg).then((response) => {
                if (
                  response &&
                  response.data &&
                  response.data.error_code &&
                  response.data.error_code === "BSS-00000000"
                ) {
                  this.$toast.success("Xóa thông tin thành công!");
                  this.SetButtom(3);
                } else {
                  this.$toast.success("Xóa thông tin thất bại!");
                }
                this.$root.$loading = false;
                this.LoadDuLieu(1);
              }).catch(err => {
                console.log(err)
                if(err.data.message.includes("child record")) {
                  this.$toast.warning("Nhóm đang có người dùng được gán vào. Hãy gỡ gán trước khi xóa!");
                } else {
                  this.$toast.error(err.data.message)
                }
              });
            } catch (err) {
              console.log(err)
              if(message.includes("child record")) {
                this.$toast.warning("Nhóm đang có người dùng được gán vào. Hãy gỡ gán trước khi xóa!");
              } else {
                this.$toast.error(err.message)
              }
            }
          }
        });
    },
    btnHuy_OnClick: async function () {
      this.SetButtom(2);
      this.DsDuLieu.data = [...this.DsDuLieu.data];
      this.$refs.gridDsDuLieu.$refs.pagination.gotoFirstPage();
    },
    btnTimKiem_OnClick: async function () {
      this.LoadDuLieu(2);
    },
    select_selectedItemsChangedCG: function (dataKeys) {
      this.DsLoaiTBCG.checked = dataKeys;
      this.DsLoaiTBCG.data.forEach(function (item) {
        if (dataKeys.filter((x) => x === item.mucdich_id).length > 0) {
          item.ischecked = true;
        } else {
          item.ischecked = false;
        }
      });
    },
    select_selectedItemsChangedDG: function (dataKeys) {

      this.DsLoaiTBDG.checked = dataKeys;
      this.DsLoaiTBDG.data.forEach(function (item) {
        if (dataKeys.filter((x) => x === item.mucdich_id).length > 0) {
          item.ischecked = true;
        } else {
          item.ischecked = false;
        }
      });
    },
    // chuyen ds muc dich da gan, chua gan
    MoveRight: function () {
      var tempData = this.DsLoaiTBDG.data;
      this.DsLoaiTBDG.data = [];
      var data = this.DsLoaiTBCG.data.filter((x) => x.ischecked);
      if (data.length > 0) {
        data.forEach((item) => {
          tempData.push(item);
        });
      }
      this.DsLoaiTBDG.data = tempData.slice(0);
      this.DsLoaiTBCG.data = this.DsLoaiTBCG.data.filter((x) => !x.ischecked);
    },
    MoveRightAll: function () {
      var tempData = this.DsLoaiTBDG.data;
      var data = this.DsLoaiTBCG.data;
      if (data.length > 0) {
        data.forEach((item) => {
          tempData.push(item);
        });
        this.DsLoaiTBDG.data = tempData.slice(0);
      }
      this.DsLoaiTBCG.data = [];
    },
    MoveLeft: function () {
      var tempData = this.DsLoaiTBCG.data;
      var data = this.DsLoaiTBDG.data.filter((x) => x.ischecked);
      if (data.length > 0) {
        data.forEach((item) => {
          tempData.push(item);
        });
        this.DsLoaiTBCG.data = tempData.slice(0);
      } else {
        this.$root.toastError("Bạn chưa chọn thông tin để gán!");
      }
      this.DsLoaiTBDG.data = this.DsLoaiTBDG.data.filter((x) => !x.ischecked);
    },
    MoveLeftAll: function () {
      var tempData = this.DsLoaiTBCG.data;
      var data = this.DsLoaiTBDG.data;
      if (data.length > 0) {
        data.forEach((item) => {
          tempData.push(item);
        });
        this.DsLoaiTBCG.data = tempData.slice(0);
      }
      this.DsLoaiTBDG.data = [];
    },

    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        if (response.data.data) {
          return response.data.data;
        }
        console.log(response.data.error_code);
      }
      return [];
    },
  },
};
</script>
