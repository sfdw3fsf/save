<template src="./index.html"></template>
<style scoped>
.list-actions-top .list > li.disabled {
  cursor: not-allowed;
  pointer-events: none;
}
.list-actions-top .list > li.disabled > a {
  color: #6c757d !important;
}
li.pointer {
  cursor: pointer;
}
.modal-wrapper {
  min-height: 500px;
}

.list-actions-top .list {
  overflow: unset;
}

.input-group {
  display: table !important;
}

.vattu_box {
  position: relative;
}

.vattu_table {
  position: absolute;
  z-index: 1;
  max-height: 300px;
  background: aliceblue;
  overflow-y: scroll;
  width: 100%;
}
</style>
<script>
import moment from "moment";
import ClickOutside from "vue-click-outside";
import EventBus from "@/utils/eventBus";
import Vue from "vue";

export default {
  name: "NVThau",
  components: {},
  props: ["data"],
  directives: {
    ClickOutside,
  },
  data: function () {
    return {
      columns: [
        {
          fieldName: "tenDv",
          headerText: "",
          allowFiltering: true,
          allowSorting: false,
          // isGroupedColumn: true,
        },
        {
          fieldName: "maNv",
          headerText: "Mã NV",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "tenNv",
          headerText: "Tên NV",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "",
          headerText: "Vai trò",
          width: 120,
          allowFiltering: false,
          template: this.vaiTroTemplate,
        },
      ],
      editSettings: { allowEditing: true, mode: "Batch" },
      nvThauLst: {
        data: [],
        totalItems: 0,
      },
      timKiem: {
        id: "",
        text: "",
      },
      showTK: false,
      nvDonViLst: [],
      vaiTroLst: [],
      nvThauSelected: [],
      timKiemChonId: "",
      vaiTroTemplate: function () {
        return {
          template: {
            template: `<div class="select-custom">
                            <select class="form-control" v-model="vtId" @change="onChangeVaiTro">
                                <option v-for="dv in parent.vaiTroLst" v-bind:value="dv.vaiTroId">{{dv.vaiTro}}
																</option>
                            </select>
                        </div>`,
            data() {
              return {
                data: {},
                vtId: "",
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
            created: async function () {
              // //console.log(this.parent.vaiTroLst);
            },
            methods: {
              onChangeVaiTro() {
                //console.log(this.vtId);
                this.data.vaiTroId = this.vtId;
                // EventBus.$emit("onChangeVaiTro", {...this.data. this.vtId});
                EventBus.$emit("onChangeVaiTro", this.data);
              },
            },
          },
        };
      },
    };
  },
  validations: {},
  watch: {},
  created: async function () {
    this.getDsNV();
    this.getDsNVT();
    this.getDsVaiTro();
    EventBus.$on("onChangeVaiTro", this.onChangeVaiTroHandle);
  },
  destroyed() {
    EventBus.$off("onChangeVaiTro", this.onChangeVaiTroHandle);
  },
  async mounted() {},
  methods: {
    toggleTimKiem() {
      this.showTK = !this.showTK;
    },
    hideTimKiem() {
      this.showTK = false;
    },
    onSelectRowTK(value) {
      //console.log(value);
      if (value) {
        this.timKiem.id = value.donViId;
        this.timKiem.text = value.tenDv;
        this.showTK = false;
        this.timKiemChonId = value.donViId;
        this.getDsNVT();
      }
    },
    onChangeVaiTroHandle(value) {
      // //console.log(value);
      this.nvThauLst.data.forEach((element) => {
        if (element.nhanVienId == value.nhanVienId) {
          element.vaiTroId = value.vaiTroId;
        }
      });
      //  //console.log(this.nvThauLst.data.filter(item=>item.nhanVienId == value.nhanVienId));
    },
    selectItemsNvThau(value) {
      //console.log(value);
      this.nvThauSelected = value;
    },
    onReload() {
      this.getDsNVT();
    },
    async onSave() {
      const nvtChon = this.nvThauLst.data.filter((item) =>
        this.nvThauSelected.includes(item.nhanVienId)
      );
      if (!nvtChon.length)
        return this.$toast.error("Chưa chọn nhân viên thầu!");
      const errMsg = [];
      nvtChon.forEach((element) => {
        if (element.vaiTroId == null || element.vaiTroId == -1) {
          if (errMsg.length == 0) {
            errMsg.push(`Nhân viên ${element.tenNv} chưa gán vai trò`);
          }
        }
      });
      if (errMsg.length > 0) {
        this.$toast.error(errMsg[0]);
      }
      //  //console.log(nvtChon);
      const dsInsert = nvtChon.map((item) => ({
        hdMsId: this.data.HDMS_ID,
        nhanVienId: item.nhanVienId,
        donViId: item.donViId,
        vaiTroId: item.vaiTroId,
        ghiChu: "",
      }));
      //console.log(dsInsert);
      this.ganToThau(dsInsert);
      //  dsInsert.forEach(async item=> {
      // 	 await this.ganToThau(item);
      //  })
      //  //console.log('OK');
      //  this.$emit("")
    },
    getDsNV: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/hop-dong-mua-sam/ds-nv-dvi`
        );
        //console.log(rs);
        this.nvDonViLst = rs.data;
      } catch (error) {
      } finally {
      }
    },
    getDsVaiTro: async function () {
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/hop-dong-mua-sam/ds-vai-tro/-1`
        );
        //console.log("getDsVaiTro", rs);
        this.vaiTroLst = rs.data;
      } catch (error) {
      } finally {
      }
    },
    ganToThau: async function (bodyLst) {
      try {
        // for (let index = 0; index < bodyLst.length; index++) {
        // 		const element = bodyLst[index];
        // 		var rs = await this.$root.context.post(
        // 		`/web-qlvt/api/hop-dong-mua-sam/tao-hd-to-thau`,
        // 		element
        // 	);
        // }
        // //console.log('Gan Ok');
        var rs = await this.$root.context.post(
          `/web-qlvt/api/hop-dong-mua-sam/tao-hd-to-thau`,
          bodyLst
        );
        //console.log("ganToThau", rs);
        if (rs.error == "200") {
          this.$toast.success("Gán thành công!");
          this.getDsNVT();
        }
      } catch (error) {
      } finally {
      }
    },
    getDsNVT: async function () {
      // //console.log(this.data.HDMS_ID);
      this.$root.showLoading(true);
      try {
        var rs = await this.$root.context.get(
          `/web-qlvt/api/hop-dong-mua-sam/ds-nv-chua-gan/${this.data.HDMS_ID}`
        );
        //console.log(rs);
        this.nvThauLst.data = rs.data.map((item) => ({
          ...item,
          tenDv: "Đơn vị: " + item.tenDv,
        }));
        if (this.timKiemChonId) {
          this.nvThauLst.data = this.nvThauLst.data.filter(
            (item) => item.donViId == this.timKiemChonId
          );
        }
        this.nvThauLst.totalItems = rs.data.length;
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
  },
};
</script>
