<template src="./index.template.html"></template>
<script>
import Modal from "./../PhanKCChoNVQLBangFile/Modal";
import XLSX from "xlsx";
export default {
  components: { XLSX, Modal },
  data: function() {
    return {
      dsTTVT: [],
      dsToQL: [],
      dsTongDai: [],
      dsNhomCap: [],
      dsCapGoc: [],
      dsKetCuoiChuaPhan: [],
      dsKetCuoiDaPhan: [],
      dscheckNV: Object,
      dsNhanVien: [],
      dsNhanVienDaPhan: null,
      dataSelected: {
        TTVT: null,
        ToQL: null,
        TongDai: null,
        NhomCap: null,
        CapGoc: null,
        NhanVien: null
      },
      dsKetCuoiCP: [],
      dsKetCuoiDP: [],
      showTable: "none",
      fields: { text: "TEN_DV", value: "DONVI_ID" },
      fieldsCap: { text: "loai_cap" },
      flag: false,
      allowFiltering: true,
      isError: "",
      rowNV: null,
      page: 0,
      dataCurrent: [],
      tenCapGoc: "Chưa chọn cáp gốc"
    };
  },
  created: async function() {
    await this.getTTVT();
    await this.getNhomCap();
  },
  watch: {
    "dataSelected.NhanVien": function(val) {
      let nv = this.$refs["nhanvien"]
        .querySelector("div>span>span>span")
        .getAttribute("class");
      nv = nv.replace("form-control is-invalid ", "");
      this.$refs["nhanvien"]
        .querySelector("div>span>span>span")
        .setAttribute("class", nv);
    }
  },
  computed: {},
  methods: {
    getTTVT: async function() {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/dsttvt_theonv"
        );
        this.dsTTVT = rs.data;
      } catch (error) {
      } finally {
      }
    },
    getToQL: async function() {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/ds_to_kythuat_theonv/" +
            this.dataSelected.TTVT
        );
        this.dsToQL = rs.data;
      } catch (error) {
      } finally {
      }
    },
    getTongDai: async function() {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/ds_tram_tong_dai/" +
            this.dataSelected.ToQL
        );
        this.dsTongDai = rs.data;
      } catch (error) {
      } finally {
      }
    },
    getNhomCap: async function() {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/ds_loai_nhom_cap"
        );
        this.dsNhomCap = rs.data;
      } catch (error) {
      } finally {
      }
    },
    getCapGoc: async function() {
      try {
        let loai_cap = this.dataSelected.NhomCap == "Cáp quang" ? 1 : 0;
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/ds_cap_goc",
          { loai_nhom_cap: loai_cap, tong_dai_id: this.dataSelected.TongDai }
        );
        this.dsCapGoc = rs.data;
      } catch (error) {
      } finally {
      }
    },
    getdsketCuoiChuaPhanNVQL: async function() {
      try {
        if (this.dataSelected.CapGoc != null) {
          var rs = await this.$root.context.post(
            "/web-cabman/quanlyhatang/lietKeDanhSachKetCuoiChuaPhan",
            { id: this.dataSelected.CapGoc }
          );
          this.dsKetCuoiChuaPhan = rs.data;
        }
      } catch (error) {
      } finally {
      }
    },
    getdsketCuoiDaPhanNVQL: async function() {
      try {
        if (this.dataSelected.CapGoc != null) {
          var rs = await this.$root.context.post(
            "/web-cabman/quanlyhatang/lietKeDanhSachKetCuoiDaPhan",
            { id: this.dataSelected.CapGoc }
          );
          if (rs.data != null) {
            this.dsKetCuoiDaPhan = rs.data;
          }
        }
      } catch (error) {
      } finally {
      }
    },
    getdsNVQL: async function() {
      try {
        var rs = await this.$root.context.get(
          "/web-cabman/danhmuc/dsnhanvien-quanly/" + this.dataSelected.ToQL
        );
        this.dsNhanVien = rs.data;
      } catch (error) {
      } finally {
      }
    },
    onChangeTTVT: async function() {
      await this.getToQL();
    },
    onChangeToQL: async function() {
      await this.getTongDai();
      await this.getdsNVQL();
    },
    onChangeTongDai: async function() {
      await this.getCapGoc();
    },
    onChangeNhomCap: async function() {
      if (this.dataSelected.TongDai != null) await this.getCapGoc();
    },
    onChangeCapGoc: async function() {
      await this.getdsketCuoiChuaPhanNVQL();
      await this.getdsketCuoiDaPhanNVQL();
    },
    ganql: async function() {
      try {
        this.flag = false;
        this.$root.showLoading(true);
        if (this.checkData()) {
          if (this.dsKetCuoiCP.length == 0) {
            this.$root.toastError("Bạn chưa tick chọn kết cuối!");
            return;
          }
          for (let i = 0; i < this.dsKetCuoiCP.length; i++) {
            let data = {
              cap_id: this.dataSelected.CapGoc,
              ketcuoi_id: this.dsKetCuoiCP[i],
              nhanvien_id: this.dataSelected.NhanVien
            };
            var rs = await this.$root.context.post(
              "/web-cabman/quanlyhatang/ganNhanVienQuanLyKetCuoi/",
              data
            );
          }
          this.$toast.success("Cập nhật thành công! ");
        }
      } catch (error) {
        this.$root.toastError("Lỗi: " + error.response.data.message_detail);
      } finally {
        this.$root.showLoading(false);
        this.dsKetCuoiChuaPhan = [];
        this.dsKetCuoiDaPhan = [];
        await this.getdsketCuoiChuaPhanNVQL();
        await this.getdsketCuoiDaPhanNVQL();
      }
    },
    ganAllql: async function() {
      try {
        this.flag = false;
        this.$root.showLoading(true);
        if (this.checkData()) {
          for (let i = 0; i < this.dsKetCuoiChuaPhan.length; i++) {
            let data = new Object();
            data = {
              cap_id: this.dataSelected.CapGoc,
              ketcuoi_id: this.dsKetCuoiChuaPhan[i].KETCUOI_ID,
              nhanvien_id: this.dataSelected.NhanVien
            };
            var rs = await this.$root.context.post(
              "/web-cabman/quanlyhatang/ganNhanVienQuanLyKetCuoi/",
              data
            );
          }
          this.$toast.success("Cập nhật thành công! ");
        }
      } catch (error) {
        this.$root.toastError("Lỗi: " + error.response.data.message_detail);
      } finally {
        this.$root.showLoading(false);
        this.dsKetCuoiChuaPhan = [];
        this.dsKetCuoiDaPhan = [];
        await this.getdsketCuoiChuaPhanNVQL();
        await this.getdsketCuoiDaPhanNVQL();
      }
    },
    goql: async function() {
      try {
        this.flag = true;
        this.$root.showLoading(true);
        if (this.checkData()) {
          if (this.dsKetCuoiDP.length == 0) {
            this.$root.toastError("Bạn chưa tick chọn kết cuối!");
            return;
          }
          for (let i = 0; i < this.dsKetCuoiDP.length; i++) {
            let data = {
              id: this.dsKetCuoiDP[i]
            };
            var rs = await this.$root.context.post(
              "/web-cabman/quanlyhatang/goNhanVienQuanLyKetCuoi/",
              data
            );
          }
          this.$toast.success("Cập nhật thành công! ");
        }
      } catch (error) {
        this.$toast.error("Lỗi: " + error.response.data.message_detail);
      } finally {
        this.$root.showLoading(false);
        this.dsKetCuoiChuaPhan = [];
        this.dsKetCuoiDaPhan = [];
        await this.getdsketCuoiChuaPhanNVQL();
        await this.getdsketCuoiDaPhanNVQL();
      }
    },
    goAllql: async function() {
      try {
        this.flag = true;
        this.$root.showLoading(true);
        if (this.checkData()) {
          for (let i = 0; i < this.dsKetCuoiDaPhan.length; i++) {
            let data = {
              id: this.dsKetCuoiDaPhan[i].KETCUOI_ID
            };
            var rs = await this.$root.context.post(
              "/web-cabman/quanlyhatang/goNhanVienQuanLyKetCuoi/",
              data
            );
          }
          this.$toast.success("Cập nhật thành công! ");
        }
      } catch (error) {
        this.$toast.error("Lỗi: " + error.response.data.message_detail);
      } finally {
        this.$root.showLoading(false);
        this.dsKetCuoiChuaPhan = [];
        this.dsKetCuoiDaPhan = [];
        await this.getdsketCuoiChuaPhanNVQL();
        await this.getdsketCuoiDaPhanNVQL();
      }
    },
    async cbb_selectedChanged(dataItem) {
      if (dataItem != null) {
        this.dataSelected.CapGoc = dataItem.CAP_ID;
        this.tenCapGoc = "Cáp gốc: " + dataItem.TEN_CAP;
        this.showTable = "none";
        await this.getdsketCuoiChuaPhanNVQL();
        await this.getdsketCuoiDaPhanNVQL();
      }
    },
    checkData: function() {
      if (this.dataSelected.NhanVien == null && !this.flag) {
        this.$root.toastError("Chưa chọn nhân viên quản lý");
        let nv = this.$refs["nhanvien"]
          .querySelector("div>span>span>span")
          .getAttribute("class");
        nv = "form-control is-invalid " + nv;
        this.$refs["nhanvien"]
          .querySelector("div>span>span>span")
          .setAttribute("class", nv);
        return false;
      }
      if (this.dataSelected.CapGoc == null) {
        this.$root.toastError("Chưa chọn cáp gốc");
        return false;
      }
      return true;
    },
    selectCP_selectedItemsChanged: function(dataKeys) {
      this.dsKetCuoiCP = dataKeys;
    },
    selectDP_selectedItemsChanged: function(dataKeys) {
      this.dsKetCuoiDP = dataKeys;
    },
    onFiltering: function(e) {
      let data = e.baseEventArgs.target.ej2_instances[0];
      e.baseEventArgs.target.ej2_instances[0].listData = data.listData.filter(
        x => x[data.fields.text].includes(e.text)
      );
    },
    grid_dataGridCellCreated(e) {
      try {
        $(e.cellElement).show();
        if (e.cellFieldName == "TEN_NV") {
          let dataItems = this.$refs.grid.dataItems;
          var index = dataItems.findIndex(
            p => p.KETCUOI_ID == e.cellDataItem.KETCUOI_ID
          );
          let chk = true;
          if (index > 0) {
            if (dataItems[index - 1].TEN_NV == e.cellDataItem.TEN_NV) {
              $(e.cellElement).hide();
              //$(e.cellElement).remove();
              chk = false;
            }
          }
          if (chk) {
            let rowSpanNumber = 1;
            for (let i = index + 1; i < dataItems.length; i++) {
              const dataItem = dataItems[i];
              if (dataItem.TEN_NV == e.cellDataItem.TEN_NV) rowSpanNumber++;
              else break;
            }
            $(e.cellElement).attr("rowspan", rowSpanNumber);
          }
        }
      } catch (e) {
        console.log("Er:", e);
      }
    },
    countNV: function(arr) {
      return arr.reduce(function(a, b) {
        a[b] = a[b] + 1 || 1;
        return a;
      }, {});
    }
  }
};
</script>
<style scoped></style>
