<template src="./AssignLocationEmployee.html"></template>
<style src="./AssignLocationEmployee.scss" scoped></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import DataGrid from "@/components/Controls/DataGrid";
import SelectExt from "@/components/Controls/SelectExt";
import api from "./API.js";

export default {
  components: {
    breadcrumb,
    DataGrid,
    SelectExt,
  },
  name: "AssignLocationEmployee",
  data() {
    return {
      NHOMPHO: {
        DUONGPHO: 1,
        AP: 2,
        KHU: 3,
      },
      button: {
        btnPhoRight: false,
        btnPhoLeft: false,
        btnApRight: false,
        btnApLeft: false,
        btnKhuRight: false,
        btnKhuLeft: false,
        btnDDRight: false,
        btnDDLeft: false,
      },
      header: {
       title: "Gán địa danh – Nhân viên",
        list: [
          //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: "Quản lý nhân viên", link: { name: "Ui.cards" } },
          {
            name: "Gán địa danh – Nhân viên",
            link: { name: "Ui.cards" },
            active: true,
          },
        ],
      },
      input: { nhanvien_id: 0, loainv_id: 0, js_nhanvien_px: "" },
      ds_nhanvien: [],
      ds_loainv: [],
      ds_quan: [],
      ds_phuong: [],
      nhanvien_id: 0,
      loainv_id: 0,
      quan_id: 0,
      phuong_id: 0,
      ds_phodagan_selected: [],
      ds_phochuagan_selected: [],
      ds_apdagan_selected: [],
      ds_apchuagan_selected: [],
      ds_khudagan_selected: [],
      ds_khuchuagan_selected: [],
      ds_dacdiemdagan_selected: [],
      ds_dacdiemchuagan_selected: [],
      ds_nap_selected: [],
      ds_dagan: {
        list: [],
        header: [
          {
            fieldName: "ten_quan",
            headerText: "Quận",
            allowFiltering: true,
          },
          {
            fieldName: "ten_phuong",
            headerText: "Phường",
            allowFiltering: true,
          },
          {
            fieldName: "ten_pho",
            headerText: "Phố",
            allowFiltering: true,
          },
          {
            fieldName: "ten_ap",
            headerText: "Ấp",
            allowFiltering: true,
          },
          {
            fieldName: "ten_khu",
            headerText: "Khu",
            allowFiltering: true,
          },
          {
            fieldName: "dacdiem",
            headerText: "Đặc điểm",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      ds_pho_dagan: {
        list: [],
        header: [
          {
            fieldName: "TEN_PHO",
            headerText: "Phố đã gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      ds_ap_dagan: {
        list: [],
        header: [
          {
            fieldName: "TEN_PHO",
            headerText: "Ấp đã gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      ds_khu_dagan: {
        list: [],
        header: [
          {
            fieldName: "TEN_PHO",
            headerText: "Khu đã gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      ds_dacdiem_dagan: {
        list: [],
        header: [
          {
            fieldName: "DACDIEM",
            headerText: "Đặc điểm",
            allowFiltering: true,
          },
          {
            fieldName: "TEN_PHO",
            headerText: "Phố/Ấp/Khu",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      ds_pho_chuagan: {
        list: [],
        header: [
          {
            fieldName: "TEN_PHO",
            headerText: "Phố chưa gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      ds_ap_chuagan: {
        list: [],
        header: [
          {
            fieldName: "TEN_PHO",
            headerText: "Ấp chưa gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      ds_khu_chuagan: {
        list: [],
        header: [
          {
            fieldName: "TEN_PHO",
            headerText: "Khu chưa gán",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      ds_dacdiem_chuagan: {
        list: [],
        header: [
          {
            fieldName: "DACDIEM",
            headerText: "Đặc điểm",
            allowFiltering: true,
          },
          {
            fieldName: "TEN_PHO",
            headerText: "Phố/Ấp/Khu",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
    };
  },
  created() {
    this.loading(true);
    try {
      Promise.all([this.getFormLoad()]);
      this.loading(false);
    } catch (ex) {
      this.loading(false);
    }
    this.loading(false);
  },
  computed: {
    cp_dsNhanvien: {
      get() {
        return this.ds_nhanvien;
      },
      set(value) {
        return (this.ds_nhanvien = value);
      },
    },
    cp_loainv: {
      get() {
        return this.ds_loainv;
      },
      set(value) {
        return (this.ds_loainv = value);
      },
    },
    cp_dsQuan: {
      get() {
        return this.ds_quan;
      },
      set(value) {
        return (this.ds_quan = value);
      },
    },
    cp_dsPhuong: {
      get() {
        return this.ds_phuong;
      },
      set(value) {
        return (this.ds_phuong = value);
      },
    },
    cp_dsDagan: {
      get() {
        return this.ds_dagan;
      },
      set(value) {
        return (this.ds_dagan = value);
      },
    },
    cp_pho_dagan: {
      get() {
        return this.ds_pho_dagan;
      },
      set(value) {
        return (this.ds_pho_dagan = value);
      },
    },
    cp_ap_dagan: {
      get() {
        return this.ds_ap_dagan;
      },
      set(value) {
        return (this.ds_ap_dagan = value);
      },
    },
    cp_khu_dagan: {
      get() {
        return this.ds_khu_dagan;
      },
      set(value) {
        return (this.ds_khu_dagan = value);
      },
    },
    cp_dacdiem_dagan: {
      get() {
        return this.ds_dacdiem_dagan;
      },
      set(value) {
        return (this.ds_dacdiem_dagan = value);
      },
    },
    cp_pho_chuagan: {
      get() {
        return this.ds_pho_chuagan;
      },
      set(value) {
        return (this.ds_pho_chuagan = value);
      },
    },
    cp_ap_chuagan: {
      get() {
        return this.ds_ap_chuagan;
      },
      set(value) {
        return (this.ds_ap_chuagan = value);
      },
    },
    cp_khu_chuagan: {
      get() {
        return this.ds_khu_chuagan;
      },
      set(value) {
        return (this.ds_khu_chuagan = value);
      },
    },
    cp_dacdiem_chuagan: {
      get() {
        return this.ds_dacdiem_chuagan;
      },
      set(value) {
        return (this.ds_dacdiem_chuagan = value);
      },
    },
  },
  methods: {
    getFormLoad: async function () {
      var ds_nhanvien = [];
      var ds_quan = [];
      let data = this.GetData(await api.formLoad(this.axios));
      if (data.kq != "1") {
        this.ShowError("Có lỗi khi load dữ liệu fn_nhanvien_diadanh_load");
        return;
      }
      if (JSON.parse(data.returns).ERROR_CODE != 1) {
        this.ShowError(
          "Có lỗi khi load dữ liệu fn_nhanvien_diadanh_load" +
            JSON.parse(data.returns).MESSAGE
        );
        return;
      }
      JSON.parse(data.returns).DS_NHANVIEN.forEach((e) => {
        var temp = {
          id: e.NHANVIEN_ID,
          text: e.MA_NV + " - " + e.TEN_NV + " - " + e.TEN_DV,
        };
        ds_nhanvien.push(temp);
      });

      JSON.parse(data.returns).DS_QUAN.forEach((e) => {
        var temp = {
          id: e.QUAN_ID,
          text: e.TEN_QUAN,
        };
        ds_quan.push(temp);
      });

      this.ds_nhanvien = ds_nhanvien;
      this.ds_quan = ds_quan;
      if (ds_nhanvien[0].id) {
        this.nhanvien_id = ds_nhanvien[0].id;
        this.getDsLoaiNhanVien(ds_nhanvien[0].id);
      }
      if (ds_quan[0].id) {
        this.quan_id = ds_quan[0].id;
        await this.getDsPhuong(ds_quan[0].id);
        await this.initRightForm();
      }
    },
    initRightForm: async function () {
      this.getDsChuaGan(this.NHOMPHO.DUONGPHO);
      this.getDsChuaGan(this.NHOMPHO.AP);
      this.getDsChuaGan(this.NHOMPHO.KHU);
      this.ds_pho_dagan.list = [];
      this.ds_ap_dagan.list = [];
      this.ds_khu_dagan.list = [];
      this.ds_dacdiem_dagan.list = [];
      this.ds_phodagan_selected = [];
      this.ds_phochuagan_selected = [];
      this.ds_apdagan_selected = [];
      this.ds_apchuagan_selected = [];
      this.ds_khudagan_selected = [];
      this.ds_khuchuagan_selected = [];
      this.ds_dacdiemdagan_selected = [];
      this.ds_dacdiemchuagan_selected = [];
      this.button.btnPhoRight = false;
      this.button.btnPhoLeft = false;
      this.button.btnApRight = false;
      this.button.btnApLeft = false;
      this.button.btnKhuRight = false;
      this.button.btnKhuLeft = false;
      this.button.btnDDRight = false;
      this.button.btnDDLeft = false;
    },
    getDsLoaiNhanVien: async function (nhanvien_id) {
      try {
        var ds_loainv = [];
        var input = {
          p_nhanvien_id: nhanvien_id,
          p_kieu: 1,
        };
        let response = await api.ds_loaiNhanVien(this.axios, input);
        if (response.data.error === 200 || response.data.error === "200") {
          var data = response.data.data;
          if (data && data.length > 0) {
            data.forEach((e) => {
              var temp = {
                id: e.loainv_id,
                text: e.ten,
              };
              ds_loainv.push(temp);
            });
            if (ds_loainv[0].id) {
              this.loainv_id = ds_loainv[0].id;
              this.getDsDaGan(this.nhanvien_id, ds_loainv[0].id);
            }
          } else {
            ds_loainv = [];
            this.loainv_id = 0;
            this.ShowError(
              "Không có loại nhân viên gán với nhân viên ID " + nhanvien_id
            );
          }
          this.ds_loainv = ds_loainv;
        } else if (
          response.data.error === 204 ||
          response.data.error === "204"
        ) {
          this.loainv_id = 0;
          this.ds_loainv = [];
          // console.log(response.data.message);
          this.ShowError(
            "Không có loại nhân viên gán với nhân viên ID " + nhanvien_id
          );
        } else {
          this.loainv_id = 0;
          this.ds_loainv = [];
          this.ShowError(
            "Có lỗi trong quá trình lấy loại nhân viên theo nhân viên ID " +
              response.data.message
          );
        }
      } catch (e) {
        this.loainv_id = 0;
        this.ds_loainv = [];
        if (e.data && e.data.message)
          this.ShowError(
            "Có lỗi trong quá trình lấy loại nhân viên theo nhân viên ID " +
              e.data.message
          );
        else
          this.ShowError(
            "Có lỗi trong quá trình lấy loại nhân viên theo nhân viên ID "
          );
      }
    },
    getDsDaGan: async function (nhanvien_id, loainv_id) {
      var input = {
        vnhanvien_id: nhanvien_id, //CTV032842
        vloainv_id: loainv_id,
      };
      let data = this.GetData(await api.ds_daGan(this.axios, input));
      this.ds_dagan.list = data;
    },
    getDsPhuong: async function (quan_id) {
      var ds_phuong = [];
      var input = {
        p_quan_id: quan_id,
      };
      let data = this.GetData(await api.ds_phuong(this.axios, input));
      if (data.length > 0) {
        data.forEach((e) => {
          var temp = {
            id: e.PHUONG_ID,
            text: e.TEN_PHUONG,
          };
          ds_phuong.push(temp);
        });
        this.phuong_id = ds_phuong[0].id;
      }
      this.ds_phuong = ds_phuong;
    },
    getDsChuaGan: async function (value) {
      var param = {};
      if (value == 1) {
        param = {
          nhompho_id: this.NHOMPHO.DUONGPHO,
          phuong_id: this.phuong_id,
        };
      }
      if (value == 2) {
        param = {
          nhompho_id: this.NHOMPHO.AP,
          phuong_id: this.phuong_id,
        };
      }
      if (value == 3) {
        param = {
          nhompho_id: this.NHOMPHO.KHU,
          phuong_id: this.phuong_id,
        };
      }

      var input = {
        v_ds_para: JSON.stringify(param),
      };
      let data = this.GetData(await api.ds_chuaGan(this.axios, input));
      if (value == 1) {
        this.ds_pho_chuagan.list = JSON.parse(data.returns).DS_PHO;
      }
      if (value == 2) {
        this.ds_ap_chuagan.list = JSON.parse(data.returns).DS_PHO;
      }
      if (value == 3) {
        this.ds_khu_chuagan.list = JSON.parse(data.returns).DS_PHO;
      }
    },
    getDsDacDiem: async function (phuong_id, pho_id, ten_pho, type) {
      let input = {
        v_ds_para: JSON.stringify({ phuong_id: phuong_id, pho_id: pho_id }),
      };
      let data = this.GetData(await api.ds_dacdiem(this.axios, input));
      let param = JSON.parse(data.returns).DS_DACDIEM;
      param.forEach((e) => {
        e.TEN_PHO = ten_pho;
        e.TYPE = type;
        this.ds_dacdiem_chuagan.list.push(e);
      });
    },
    updateDsDaGan: async function (input) {
      let response = await api.capnhat(this.axios, input);
      if (response.data.error === 200 || response.data.error === "200") {
        this.ShowSuccess("Thành công!");
      } else {
        this.ShowError(response.data.message);
      }
    },
    selectNhanVien: async function (value) {
      this.loading(true);
      if (value) {
        await this.getDsLoaiNhanVien(Number(value.id));
        await this.initRightForm();
      }
      this.loading(false);
    },
    selectLoaiNV: async function (value) {
      this.loading(true);
      if (value) {
        await this.getDsDaGan(this.nhanvien_id, Number(value.id));
        await this.initRightForm();
      }
      this.loading(false);
    },
    selectQuan: async function (value) {
      this.loading(true);
      if (value) {
        this.quan_id = Number(value.id);
        await this.getDsPhuong(Number(value.id));
        await this.initRightForm();
      }
      this.loading(false);
    },
    selectPhuong: async function (value) {
      this.loading(true);
      if (value) {
        this.phuong_id = Number(value.id);
      } else this.phuong_id = 0;
      await this.initRightForm();
      this.loading(false);
    },
    selectedDsNap_click: function (value) {
      this.ds_nap_selected = value;
    },
    selectedPhoDaGan_click: function (data) {
      if (data && data.length > 0) {
        this.ds_phodagan_selected = data;
        return data;
      } else {
        this.ds_phodagan_selected = [];
        return [];
      }
    },
    selectedApDaGan_click: function (data) {
      if (data && data.length > 0) {
        this.ds_apdagan_selected = data;
        return data;
      } else {
        this.ds_apdagan_selected = [];
        return [];
      }
    },
    selectedKhuDaGan_click: function (data) {
      if (data && data.length > 0) {
        this.ds_khudagan_selected = data;
        return data;
      } else {
        this.ds_khudagan_selected = [];
        return [];
      }
    },
    selectedDacDiemDaGan_click: function (data) {
      if (data && data.length > 0) {
        this.ds_dacdiemdagan_selected = data;
        return data;
      } else {
        this.ds_dacdiemdagan_selected = [];
        return [];
      }
    },
    selectedPhoChuaGan_click: function (data) {
      if (data && data.length > 0) {
        this.ds_phochuagan_selected = data;
        return data;
      } else {
        this.ds_phochuagan_selected = [];
        return [];
      }
    },
    selectedApChuaGan_click: function (data) {
      if (data && data.length > 0) {
        this.ds_apchuagan_selected = data;
        return data;
      } else {
        this.ds_apchuagan_selected = [];
        return [];
      }
    },
    selectedKhuChuaGan_click: function (data) {
      if (data && data.length > 0) {
        this.ds_khuchuagan_selected = data;
        return data;
      } else {
        this.ds_khuchuagan_selected = [];
        return [];
      }
    },
    selectedDacDiemChuaGan_click: function (data) {
      if (data && data.length > 0) {
        this.ds_dacdiemchuagan_selected = data;
        return data;
      } else {
        this.ds_dacdiemchuagan_selected = [];
        return [];
      }
    },
    handleBtnPhoRight: function () {
      this.loading(true);
      this.button.btnPhoRight = true;
      this.button.btnPhoLeft = false;
      var dagan = this.ds_pho_dagan.list;
      var chuagan = this.ds_pho_chuagan.list;
      var selected = this.ds_phodagan_selected;
      if (selected.length == 0) {
        this.loading(false);
        return;
      }
      selected.forEach((item) => {
        chuagan.push(item);
        const index = dagan.findIndex((x) => x == item);
        if (index > -1) {
          dagan.splice(index, 1);
        }
      });
      chuagan = chuagan.sort(
        (a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID)
      );
      dagan = dagan.sort((a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID));
      this.ds_pho_dagan.list = dagan;
      this.ds_pho_chuagan.list = chuagan;
      setTimeout(() => {
        this.ds_phodagan_selected = [];
        if (dagan.length > 0) this.ds_phodagan_selected = dagan;
        this.ds_phochuagan_selected = [];

        this.ds_dacdiem_chuagan.list = [];
        if (dagan.length > 0) {
          dagan.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "pho")
          );
        }
        if (this.ds_ap_dagan.list.length > 0) {
          this.ds_ap_dagan.list.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "ap")
          );
        }
        if (this.ds_khu_dagan.list.length > 0) {
          this.ds_khu_dagan.list.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "khu")
          );
        }
        selected.forEach((e) => {
          this.ds_dacdiem_dagan.list = this.ds_dacdiem_dagan.list.filter(
            (item) => item.PHO_ID != e.PHO_ID
          );
        });
        this.loading(false);
      }, 200);
    },
    handleBtnPhoLeft: function () {
      this.loading(true);
      this.button.btnPhoLeft = true;
      this.button.btnPhoRight = false;
      var dagan = this.ds_pho_dagan.list;
      var chuagan = this.ds_pho_chuagan.list;
      var selected = this.ds_phochuagan_selected;
      if (selected.length == 0) {
        this.loading(false);
        return;
      }
      selected.forEach((item) => {
        dagan.push(item);
        const index = chuagan.findIndex((x) => x == item);
        if (index > -1) {
          chuagan.splice(index, 1);
        }
      });
      chuagan = chuagan.sort(
        (a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID)
      );
      dagan = dagan.sort((a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID));
      this.ds_pho_dagan.list = dagan;
      this.ds_pho_chuagan.list = chuagan;
      setTimeout(() => {
        // this.ds_phodagan_selected = [];
        this.ds_phochuagan_selected = [];

        this.ds_dacdiem_chuagan.list = [];
        dagan.forEach((e) =>
          this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "pho")
        );
        if (this.ds_ap_dagan.list.length > 0) {
          this.ds_ap_dagan.list.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "ap")
          );
        }
        if (this.ds_khu_dagan.list.length > 0) {
          this.ds_khu_dagan.list.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "khu")
          );
        }
        this.loading(false);
      }, 200);
    },
    handleBtnApRight: function () {
      this.loading(true);
      this.button.btnApRight = true;
      this.button.btnApLeft = false;
      var dagan = this.ds_ap_dagan.list;
      var chuagan = this.ds_ap_chuagan.list;
      var selected = this.ds_apdagan_selected;
      if (selected.length == 0) {
        this.loading(false);
        return;
      }
      selected.forEach((item) => {
        chuagan.push(item);
        const index = dagan.findIndex((x) => x == item);
        if (index > -1) {
          dagan.splice(index, 1);
        }
      });
      chuagan = chuagan.sort(
        (a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID)
      );
      dagan = dagan.sort((a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID));
      this.ds_ap_dagan.list = dagan;
      this.ds_ap_chuagan.list = chuagan;
      setTimeout(() => {
        this.ds_apdagan_selected = [];
        if (dagan.length > 0) this.ds_apdagan_selected = dagan;
        this.ds_apchuagan_selected = [];

        this.ds_dacdiem_chuagan.list = [];
        if (dagan.length > 0) {
          dagan.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "ap")
          );
        }
        if (this.ds_pho_dagan.list.length > 0) {
          this.ds_pho_dagan.list.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "pho")
          );
        }
        if (this.ds_khu_dagan.list.length > 0) {
          this.ds_khu_dagan.list.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "khu")
          );
        }
        selected.forEach((e) => {
          this.ds_dacdiem_dagan.list = this.ds_dacdiem_dagan.list.filter(
            (item) => item.PHO_ID != e.PHO_ID
          );
        });
        this.loading(false);
      }, 200);
    },
    handleBtnApLeft: function () {
      this.loading(true);
      this.button.btnApLeft = true;
      this.button.btnApRight = false;
      var dagan = this.ds_ap_dagan.list;
      var chuagan = this.ds_ap_chuagan.list;
      var selected = this.ds_apchuagan_selected;
      if (selected.length == 0) {
        this.loading(false);
        return;
      }

      selected.forEach((item) => {
        dagan.push(item);
        const index = chuagan.findIndex((x) => x == item);
        if (index > -1) {
          chuagan.splice(index, 1);
        }
      });
      chuagan = chuagan.sort(
        (a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID)
      );
      dagan = dagan.sort((a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID));
      this.ds_ap_dagan.list = dagan;
      this.ds_ap_chuagan.list = chuagan;
      setTimeout(() => {
        // this.ds_apdagan_selected = [];
        this.ds_apchuagan_selected = [];

        this.ds_dacdiem_chuagan.list = [];
        dagan.forEach((e) =>
          this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "ap")
        );
        if (this.ds_pho_dagan.list.length > 0) {
          this.ds_pho_dagan.list.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "pho")
          );
        }
        if (this.ds_khu_dagan.list.length > 0) {
          this.ds_khu_dagan.list.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "khu")
          );
        }
        this.loading(false);
      }, 200);
    },
    handleBtnKhuRight: function () {
      this.loading(true);
      this.button.btnKhuRight = true;
      this.button.btnKhuLeft = false;
      var dagan = this.ds_khu_dagan.list;
      var chuagan = this.ds_khu_chuagan.list;
      var selected = this.ds_khudagan_selected;
      if (selected.length == 0) {
        this.loading(false);
        return;
      }
      selected.forEach((item) => {
        chuagan.push(item);
        const index = dagan.findIndex((x) => x == item);
        if (index > -1) {
          dagan.splice(index, 1);
        }
      });
      chuagan = chuagan.sort(
        (a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID)
      );
      dagan = dagan.sort((a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID));
      this.ds_khu_dagan.list = dagan;
      this.ds_khu_chuagan.list = chuagan;
      setTimeout(() => {
        this.ds_khudagan_selected = [];
        if (dagan.length > 0) this.ds_khudagan_selected = dagan;
        this.ds_khuchuagan_selected = [];

        this.ds_dacdiem_chuagan.list = [];
        if (dagan.length > 0) {
          dagan.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "khu")
          );
        }
        if (this.ds_pho_dagan.list.length > 0) {
          this.ds_pho_dagan.list.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "pho")
          );
        }
        if (this.ds_ap_dagan.list.length > 0) {
          this.ds_ap_dagan.list.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "ap")
          );
        }
        selected.forEach((e) => {
          this.ds_dacdiem_dagan.list = this.ds_dacdiem_dagan.list.filter(
            (item) => item.PHO_ID != e.PHO_ID
          );
        });
        this.loading(false);
      }, 200);
    },
    handleBtnKhuLeft: function () {
      this.loading(true);
      this.button.btnKhuLeft = true;
      this.button.btnKhuRight = false;
      var dagan = this.ds_khu_dagan.list;
      var chuagan = this.ds_khu_chuagan.list;
      var selected = this.ds_khuchuagan_selected;
      if (selected.length == 0) {
        this.loading(false);
        return;
      }
      selected.forEach((item) => {
        dagan.push(item);
        const index = chuagan.findIndex((x) => x == item);
        if (index > -1) {
          chuagan.splice(index, 1);
        }
      });
      chuagan = chuagan.sort(
        (a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID)
      );
      dagan = dagan.sort((a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID));
      this.ds_khu_dagan.list = dagan;
      this.ds_khu_chuagan.list = chuagan;
      setTimeout(() => {
        // this.ds_khudagan_selected = [];
        this.ds_khuchuagan_selected = [];

        this.ds_dacdiem_chuagan.list = [];
        dagan.forEach((e) =>
          this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "khu")
        );
        if (this.ds_pho_dagan.list.length > 0) {
          this.ds_pho_dagan.list.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "pho")
          );
        }
        if (this.ds_ap_dagan.list.length > 0) {
          this.ds_ap_dagan.list.forEach((e) =>
            this.getDsDacDiem(this.phuong_id, e.PHO_ID, e.TEN_PHO, "ap")
          );
        }
        this.loading(false);
      }, 200);
    },
    handleBtnDDRight: function () {
      this.loading(true);
      this.button.btnDDRight = true;
      this.button.btnDDLeft = false;
      var dagan = this.ds_dacdiem_dagan.list;
      var chuagan = this.ds_dacdiem_chuagan.list;
      var selected = this.ds_dacdiemdagan_selected;
      if (selected.length == 0) {
        this.loading(false);
        return;
      }
      selected.forEach((item) => {
        chuagan.push(item);
        const index = dagan.findIndex((x) => x == item);
        if (index > -1) {
          dagan.splice(index, 1);
        }
      });
      chuagan = chuagan.sort(
        (a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID)
      );
      dagan = dagan.sort((a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID));
      this.ds_dacdiem_dagan.list = dagan;
      this.ds_dacdiem_chuagan.list = chuagan;
      setTimeout(() => {
        this.ds_dacdiemdagan_selected = [];
        if (dagan.length > 0) this.ds_dacdiemdagan_selected = dagan;
        this.ds_dacdiemchuagan_selected = [];
        this.loading(false);
      }, 200);
    },
    handleBtnDDLeft: function () {
      this.loading(true);
      this.button.btnDDLeft = true;
      this.button.btnDDRight = false;
      var dagan = this.ds_dacdiem_dagan.list;
      var chuagan = this.ds_dacdiem_chuagan.list;
      var selected = this.ds_dacdiemchuagan_selected;
      if (selected.length == 0) {
        this.loading(false);
        return;
      }
      selected.forEach((item) => {
        dagan.push(item);
        const index = chuagan.findIndex((x) => x == item);
        if (index > -1) {
          chuagan.splice(index, 1);
        }
      });
      chuagan = chuagan.sort(
        (a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID)
      );
      dagan = dagan.sort((a, b) => parseFloat(b.PHO_ID) - parseFloat(a.PHO_ID));
      this.ds_dacdiem_dagan.list = dagan;
      this.ds_dacdiem_chuagan.list = chuagan;
      setTimeout(() => {
        //  this.ds_dacdiemdagan_selected = [];
        this.ds_dacdiemchuagan_selected = [];
        this.loading(false);
      }, 200);
    },
    handleBtnSave: async function () {
      this.loading(true);
      var check = await this.validate();
      if (check) {
        let js_nhanvien_px = this.taoDuLieu(this.ds_dagan.list);
        let ds_para = {
          nhanvien_id: this.nhanvien_id ? this.nhanvien_id : 0,
          loainv_id: this.loainv_id ? this.loainv_id : 0,
          js_nhanvien_px: js_nhanvien_px,
        };
        let input = {
          ds_para: JSON.stringify(ds_para),
        };
        await this.updateDsDaGan(input);
        await this.getDsDaGan(this.nhanvien_id, this.loainv_id);
      }
      this.loading(false);
    },
    taoDuLieu(ds_dagan) {
      let data = "[";
      ds_dagan.forEach((item, index) => {
        var row =
          "{'NHANVIENPX_ID': 0, 'NHANVIEN_ID' :#1, 'LOAINV_ID':#2, 'PHUONG_ID':#3, 'PHO_ID':#4, 'AP_ID' :#5, 'KHU_ID':#6, 'DACDIEM_ID':#7, 'QUAN_ID':#8, 'KHUVUC_ID':#9}";
        row = row.replace("#1", this.nhanvien_id);
        row = row.replace("#2", this.loainv_id);
        row = row.replace("#3", item.phuong_id != null ? item.phuong_id : 0);
        row = row.replace("#4", item.pho_id != null ? item.pho_id : 0);
        row = row.replace("#5", item.ap_id != null ? item.ap_id : 0);
        row = row.replace("#6", item.khu_id != null ? item.khu_id : 0);
        row = row.replace("#7", item.dacdiem_id != null ? item.dacdiem_id : 0);
        row = row.replace("#8", item.quan_id != null ? item.quan_id : 0);
        row = row.replace("#9", item.khuvuc_id != null ? item.khuvuc_id : 0);
        if (index == ds_dagan.length - 1) {
          data = data.concat(row);
        } else {
          data = data.concat(row).concat(", ");
        }
      });
      data = data.concat("]");
      return data;
    },
    ganPho: function () {
      var dagan_pho = [];
      var ds_dacdiem = this.ds_dacdiem_dagan.list;
      if (this.ds_pho_dagan.list.length > 0) {
        this.ds_pho_dagan.list.forEach((e) => {
          var dacdiem = ds_dacdiem.filter(
            (i) => i.TYPE == "pho" && i.PHO_ID == e.PHO_ID
          );
          var temp = {
            selected: 0,
            quan_id: this.ds_quan.filter((e) => (e.QUAN_ID = this.quan_id))[0]
              .id,
            phuong_id: e.PHUONG_ID,
            pho_id: e.PHO_ID,
            ap_id: 0,
            khu_id: 0,
            ten_phuong: e.TEN_PHUONG,
            ten_pho: e.TEN_PHO,
            ten_ap: "",
            ten_khu: "",
            ten_quan: this.ds_quan.filter((e) => (e.QUAN_ID = this.quan_id))[0]
              .text,
            dacdiem_id: 0,
            dacdiem: "",
          };
          if (dacdiem.length > 0) {
            dacdiem.forEach((i) => {
              temp.dacdiem_id = i.DACDIEM_ID;
              temp.dacdiem = i.DACDIEM;
              dagan_pho.push(temp);
            });
          } else dagan_pho.push(temp);
        });
      }
      return dagan_pho;
    },
    ganAp: function (dagan_pho) {
      var dagan_ap = [];
      var ds_dacdiem = this.ds_dacdiem_dagan.list;
      if (this.ds_ap_dagan.list.length > 0) {
        if (dagan_pho.length > 0) {
          dagan_pho.forEach((x) => {
            this.ds_ap_dagan.list.forEach((e) => {
              var dacdiem = ds_dacdiem.filter(
                (i) => i.TYPE == "ap" && i.PHO_ID == e.PHO_ID
              );
              var temp = {
                selected: x.selected,
                quan_id: x.quan_id,
                phuong_id: x.phuong_id,
                pho_id: x.pho_id,
                ap_id: e.PHO_ID,
                khu_id: 0,
                ten_phuong: x.ten_phuong,
                ten_pho: x.ten_pho,
                ten_ap: e.TEN_PHO,
                ten_khu: "",
                ten_quan: x.ten_quan,
                dacdiem_id: x.dacdiem_id,
                dacdiem: x.dacdiem,
              };
              if (x.dacdiem_id == 0)
                if (dacdiem.length > 0) {
                  dacdiem.forEach((i) => {
                    temp.dacdiem_id = i.DACDIEM_ID;
                    temp.dacdiem = i.DACDIEM;
                    dagan_ap.push(temp);
                  });
                } else dagan_ap.push(temp);
              else dagan_ap.push(temp);
            });
          });
        } else {
          this.ds_ap_dagan.list.forEach((e) => {
            var dacdiem = ds_dacdiem.filter(
              (i) => i.TYPE == "ap" && i.PHO_ID == e.PHO_ID
            );
            var temp = {
              selected: 0,
              quan_id: this.ds_quan.filter((e) => (e.QUAN_ID = this.quan_id))[0]
                .id,
              phuong_id: e.PHUONG_ID,
              pho_id: 0,
              ap_id: e.PHO_ID,
              khu_id: 0,
              ten_phuong: e.TEN_PHUONG,
              ten_pho: "",
              ten_ap: e.TEN_PHO,
              ten_khu: "",
              ten_quan: this.ds_quan.filter(
                (e) => (e.QUAN_ID = this.quan_id)
              )[0].text,
              dacdiem_id: 0,
              dacdiem: "",
            };
            if (dacdiem.length > 0) {
              dacdiem.forEach((i) => {
                temp.dacdiem_id = i.DACDIEM_ID;
                temp.dacdiem = i.DACDIEM;
                dagan_ap.push(temp);
              });
            } else dagan_ap.push(temp);
          });
        }
      } else {
        if (dagan_pho.length > 0) {
          dagan_ap = dagan_pho;
        }
      }
      return dagan_ap;
    },
    ganKhu: function (dagan_ap) {
      var dagan_khu = [];
      var ds_dacdiem = this.ds_dacdiem_dagan.list;
      if (this.ds_khu_dagan.list.length > 0) {
        if (dagan_ap.length > 0) {
          dagan_ap.forEach((x) => {
            this.ds_khu_dagan.list.forEach((e) => {
              var dacdiem = ds_dacdiem.filter(
                (i) => i.TYPE == "khu" && i.PHO_ID == e.PHO_ID
              );
              var temp = {
                selected: x.selected,
                quan_id: x.quan_id,
                phuong_id: x.phuong_id,
                pho_id: x.pho_id,
                ap_id: x.ap_id,
                khu_id: e.PHO_ID,
                ten_phuong: x.ten_phuong,
                ten_pho: x.ten_pho,
                ten_ap: x.ten_ap,
                ten_khu: e.TEN_PHO,
                ten_quan: x.ten_quan,
                dacdiem_id: x.dacdiem_id,
                dacdiem: x.dacdiem,
              };
              if (x.dacdiem_id == 0)
                if (dacdiem.length > 0) {
                  dacdiem.forEach((i) => {
                    temp.dacdiem_id = i.DACDIEM_ID;
                    temp.dacdiem = i.DACDIEM;
                    dagan_khu.push(temp);
                  });
                } else dagan_khu.push(temp);
              else dagan_khu.push(temp);
            });
          });
        } else {
          this.ds_khu_dagan.list.forEach((e) => {
            var dacdiem = ds_dacdiem.filter(
              (i) => i.TYPE == "khu" && i.PHO_ID == e.PHO_ID
            );
            var temp = {
              selected: 0,
              quan_id: this.ds_quan.filter((e) => (e.QUAN_ID = this.quan_id))[0]
                .id,
              phuong_id: e.PHUONG_ID,
              pho_id: 0,
              ap_id: 0,
              khu_id: e.PHO_ID,
              ten_phuong: e.TEN_PHUONG,
              ten_pho: "",
              ten_ap: "",
              ten_khu: e.TEN_PHO,
              ten_quan: this.ds_quan.filter(
                (e) => (e.QUAN_ID = this.quan_id)
              )[0].text,
              dacdiem_id: 0,
              dacdiem: "",
            };
            if (dacdiem.length > 0) {
              dacdiem.forEach((i) => {
                temp.dacdiem_id = i.DACDIEM_ID;
                temp.dacdiem = i.DACDIEM;
                dagan_khu.push(temp);
              });
            } else dagan_khu.push(temp);
          });
        }
      } else if (dagan_ap.length > 0) {
        dagan_khu = dagan_ap;
      }

      return dagan_khu;
    },
    handleNap: function () {
      var ds_nap = this.ds_dagan.list;
      var dagan = [];
      var dagan_pho = this.ganPho();
      var dagan_ap = this.ganAp(dagan_pho);
      dagan = this.ganKhu(dagan_ap);
      for (var i = 0; i < dagan.length; i++) {
        var index = ds_nap.findIndex(
          (n) =>
            n.quan_id == dagan[i].quan_id &&
            n.phuong_id == dagan[i].phuong_id &&
            n.pho_id == dagan[i].pho_id &&
            n.ap_id == dagan[i].ap_id &&
            n.khu_id == dagan[i].khu_id &&
            n.dacdiem_id == dagan[i].dacdiem_id
        );
        if (index == -1) ds_nap.push(dagan[i]);
        else {
          var quan = dagan[i].ten_quan ? dagan[i].ten_quan + ", " : " ";
          var phuong = dagan[i].ten_phuong ? dagan[i].ten_phuong + ", " : " ";
          var pho = dagan[i].ten_pho ? dagan[i].ten_pho + ", " : " ";
          var ap = dagan[i].ten_ap ? dagan[i].ten_ap + ", " : " ";
          var khu = dagan[i].ten_khu ? dagan[i].ten_khu + ", " : " ";
          var dacdiem = dagan[i].dacdiem ? dagan[i].dacdiem + ", " : " ";
          this.ShowError(
            "Dữ liệu nạp bị trùng: " + quan + phuong + pho + ap + khu + dacdiem
          );
        }
      }
      this.ds_dagan.list = ds_nap;
    },
    handleHuyNap: async function () {
      // await this.getDsDaGan(this.nhanvien_id, this.loainv_id);
      if (this.ds_nap_selected && this.ds_nap_selected.length > 0) {
        for (var i = 0; i < this.ds_nap_selected.length; i++) {
          const index = this.ds_dagan.list.findIndex(
            (x) => x == this.ds_nap_selected[i]
          );
          if (index > -1) {
            this.ds_dagan.list.splice(index, 1);
          }
        }
      }
    },
    validate: function () {
      if (!this.loainv_id) {
        this.ShowError("Nhân viên chưa được gán loại nhân viên!");
        return false;
      }
      return true;
    },
    GetData: function (response) {
      if (
        response.data.error === 200 ||
        response.data.error === "200" ||
        response.data.error === 204 ||
        response.data.error === "204"
      ) {
        return response.data.data;
      } else {
        // console.log(response.data.message);
        // this.ShowError(response.data.message);
      }
      return [];
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
    resetPR(value) {
      this.button.btnPhoRight = false;
    },
    resetPL(value) {
      this.button.btnPhoLeft = false;
    },
    resetAR(value) {
      this.button.btnApRight = false;
    },
    resetAL(value) {
      this.button.btnApLeft = false;
    },
    resetKR(value) {
      this.button.btnKhuRight = false;
    },
    resetKL(value) {
      this.button.btnKhuLeft = false;
    },
    resetDDR(value) {
      this.button.btnDDRight = false;
    },
    resetDDL(value) {
      this.button.btnDDLeft = false;
    },
  },
};
</script>
