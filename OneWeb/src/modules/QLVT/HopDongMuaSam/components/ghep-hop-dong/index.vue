<template src="./index.html"></template>
<style scoped src="./style.scss">
</style>
<script>
import moment from "moment";
import ClickOutside from "vue-click-outside";
import EventBus from "@/utils/eventBus";
import Vue from "vue";
import VTCustomDropdown from "../custom-dropdown";

const nest2 = (items, donViId = 1, link = "donViChaId") =>
  items
    .filter((item) => item[link] == donViId)
    .map((item) => ({ ...item, nodes: nest2(items, item.donViId) }));

export default {
  name: "GhepHopDong",
  components: { "vt-custom-dropdown": VTCustomDropdown },
  props: ["data"],
  directives: {
    ClickOutside,
  },
  data: function () {
    return {
      form: {
        maHd: "",
        trangThaiHdCkb: false,
        trangThaiId: 0,
        mucDichMsCkb: false,
        mucDichId: 0,
        nhaThauCkb: false,
        khoId: 0,
        donViCkb: false,
        donViId: [],
        ngayTuCkb: false,
        ngayTu: new Date(),
        ngayDenCkb: false,
        ngayDen: new Date(),
        ngayClTuCkb: false,
        ngayClTu: 0,
        ngayClDenCkb: false,
        ngayClDen: 0,
        chuaCoDonCkb: false,
        duDkCkb: false,
      },
      trangThaiHDLst: [],
      mucDichMsLst: [],
      nhaThauLst: [],
      donViLst: [],
      fields: {
        dataSource: [],
        value: "donViId",
        text: "tenDv",
        child: "nodes",
      },
      hopDongLst: {
        data: [],
        totalItems: 0,
      },
      hopDongChon: [],
    };
  },
  validations: {},
  watch: {},
  created: async function () {
    this.dsTrangThaiHD();
    this.dsMucDichMs();
    this.dsNhaThau();
    this.dsDonVi();
  },
  destroyed() {},
  async mounted() {},
  methods: {
    rowSelectTrangThaiHD(value) {
      if (value) this.form.trangThaiId = value.TTHD_ID;
    },
    rowSelectMucDichMs(value) {
      if (value) this.form.mucDichId = value.MUCDICH_ID;
    },
    rowSelectNhaThau(value) {
      if (value) {
        this.form.khoId = value.KHO_ID;
      }
    },
    onCheckGridItem(value) {
      this.hopDongChon = value;
    },
    onClickGhiLai() {
      if (this.hopDongChon.length == 0) {
        this.$toast.error("Hãy chọn các hợp đồng cần ghép");
      } else {
        const body = {
          hdMsId: this.data.HDMS_ID,
          dsHdAoId: this.hopDongChon,
          nguoiDungId: this.$auth.getNguoiDungID(),
          ngayCn: moment(new Date()).format("DD/MM/YYYY"),
          nguoiCn: this.$auth.getUserName(),
          mayCn: "",
          ipCn: "",
        };
        this.ghepHD(body);
      }
    },
    getCbNhaThau(parent) {
      return function () {
        return {
          template: {
            components: {},
            template: `
                           <ejs-dropdownlist                           
                            :dataSource="parent.nhaThauLst"                        
                            :fields="{value:'KHO_ID', text: 'TEN_KHO'}"                                             
                            v-model="value"
                            :enabled="false"
                        ></ejs-dropdownlist>
                        `,
            data() {
              return {
                parent: parent, //this
                data: {}, //rowData
                value: "",
              };
            },
            created() {
              this.value = this.data.KHO_ID;
            },
          },
        };
      };
    },
    getCbMucDich(parent) {
      return function () {
        return {
          template: {
            components: {},
            template: `
                           <ejs-dropdownlist                           
                            :dataSource="parent.mucDichMsLst"                        
                            :fields="{value:'MUCDICH_ID', text: 'MUCDICH'}"                                             
                            v-model="value"
                            :enabled="false"
                        ></ejs-dropdownlist>
                        `,
            data() {
              return {
                parent: parent, //this
                data: {}, //rowData
                value: "",
              };
            },
            created() {
              this.value = this.data.MUCDICH_ID;
            },
          },
        };
      };
    },
    getCbTrangThai(parent) {
      return function () {
        return {
          template: {
            components: {},
            template: `
                           <ejs-dropdownlist                           
                            :dataSource="parent.trangThaiHDLst"                        
                            :fields="{value:'TTHD_ID', text: 'TRANGTHAI_HD'}"                                             
                            v-model="value"
                            :enabled="false"
                        ></ejs-dropdownlist>
                        `,
            data() {
              return {
                parent: parent, //this
                data: {}, //rowData
                value: "",
              };
            },
            created() {
              this.value = this.data.TTHD_ID;
            },
          },
        };
      };
    },
    onClickTraCuu() {
      const body = {
        ttHdId: this.form.trangThaiHdCkb ? this.form.trangThaiId : 0,
        mucDichId: this.form.mucDichMsCkb ? this.form.mucDichId : 0,
        khoId: this.form.nhaThauCkb ? this.form.khoId : 0,
        donViId:
          this.form.donViCkb && this.form.donViId.length > 0
            ? this.form.donViId[0]
            : -1,
        tuNgayYc: this.form.ngayTuCkb
          ? moment(this.form.ngayTu).format("DD/MM/YYYY")
          : "",
        denNgayYc: this.form.ngayDenCkb
          ? moment(this.form.ngayDen).format("DD/MM/YYYY")
          : "",
        soNgayClLh: this.form.ngayClTu,
        soNgayClNh: this.form.ngayClDen,
        slDh: this.form.chuaCoDonCkb ? 1 : 0,
        dat: this.form.duDkCkb ? 1 : 0,
      };
      this.dsHDChuaGhep(body);
    },
    onClickHuyChon() {
      document
        .querySelectorAll(".e-checkselect")
        .forEach((el) => el.checked && el.click());
    },
    dsTrangThaiHD: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/tra-cuu-hop-dong-don-hang/trang-thai-hop-dong/-1`
        );
        this.trangThaiHDLst = rs.data.filter((item) => item.TTHD_ID);
      } catch (error) {
      } finally {
      }
    },
    dsMucDichMs: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-qlvt/api/tra-cuu-hop-dong-don-hang/muc-dich-mua-sam/1"
        );
        this.mucDichMsLst = rs.data;
      } catch (error) {
      } finally {
      }
    },
    dsNhaThau: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-qlvt/api/tra-cuu-hop-dong-don-hang/kho-nha-cung-cap/1"
        );
        this.nhaThauLst = rs.data;
      } catch (error) {
      } finally {
      }
    },
    dsDonVi: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-qlvt/api/ghep-hop-dong/danh-muc-don-vi-hd"
        );
        this.donViLst = rs.data;
        const lstRoot = this.donViLst
          .filter((item) => item.donViChaId == 0)
          .map((item2) => {
            return {
              ...item2,
              nodes: JSON.parse(
                JSON.stringify(nest2(this.donViLst, Number(item2.donViId)))
              ),
            };
          });
        this.fields = {
          dataSource: lstRoot,
          value: "donViId",
          text: "tenDv",
          child: "nodes",
        };
      } catch (error) {
      } finally {
      }
    },
    dsHDChuaGhep: async function (body) {
      console.log(body);
      try {
        var rs = await this.$root.context.post(
          "/web-qlvt/api/ghep-hop-dong/ds-hd-ao-chua-ghep",
          body
        );
        this.hopDongLst.data = rs.data;
        this.hopDongLst.totalItems = rs.data.length;
      } catch (error) {
      } finally {
      }
    },
    ghepHD: async function (body) {
      console.log(body);
      try {
        this.$root.loading(true);
        var rs = await this.$root.context.post(
          "/web-qlvt/api/ghep-hop-dong/ds-ghep-hd-vao-hd-that",
          body
        );
        if (rs.data == "1")
          return this.$toast.success("Ghép hợp đồng thành công");
        return this.$toast.error("Ghép hợp đồng thất bại " + rs.data);
      } catch (error) {
        console.log(error);
        if (error.response.data.data) {
          this.$toast.error(error.response.data.data);
        } else if (error.response.data.error_code) {
          this.$toast.error(error.response.data.error_code);
        } else {
          this.$toast.error("Ghép hợp đồng thất bại");
        }
      } finally {
        this.$root.loading(false);
      }
    },
  },
};
</script>
