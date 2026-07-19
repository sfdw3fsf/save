<template src='./QuanLyNhaTram.html'></template>
<style scoped src='./QuanLyNhaTram.scss'></style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import ClickOutside from "vue-click-outside";
import Vue from "vue";
import {currency} from '@/filters/currency';

export default {
  components: { breadcrumb },
  name: "QuanLyNhaTram",
  data() {
    return {
      header: {
        title: "CẬP NHẬT DANH SÁCH MÃ TÀI SẢN",
        list: [
          {
            name: "Quản lý vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Nhập/Xuất/Điều chuyển vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Quản lý nhà trạm – phụ trợ  BTS",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      listLoaiVatTu: [],
      loaiVatTuSelected_value: "",
      loaiVatTuSelected_text: "",
      showLoaiVatTu: false,
      listMaVatTu: [],
      loaiMaTuSelected_value: "",
      loaiMaTuSelected_text: "",
      showMaVatTu: false,
      listMaVatTuSelected: [],
      listMaVatTuShown: [],
      maTaiSanSelected: "",
      chk_DangChinhSua: false,
      listTaiSanTB: [],
      vtbi_id: 0,
      rowChoseData: {},
      customTemplate: function () {
        return {
          template: Vue.component("footerTemplate", {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data() {
              return { data: { data: {} } };
            },
          }),
        };
      },
    };
  },
  computed: {
    PTAggregatesColumns() {
      return [
        {
          field: "TIEN_PB",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: () => {
            let tong = this.listTaiSanTB.reduce((a, b) => +a + +b.TIEN_PB, 0);
            if(!tong) tong = 0;
            return currency(tong);
          },
        },
      ];
    },
  },
  async created() {
    await this.fetchDSLoaiVatTu();
    await this.fetchDSMaVatTu(this.loaiVatTuSelected_value);
    this.fetchDSTaiSanTB();
  },
  mounted() {
    this.$refs.maTS.focus();
  },
  watch: {
    listMaVatTuShown: function (data) {
      this.loaiMaTuSelected_text = "";
      data.forEach((element) => {
        this.loaiMaTuSelected_text += `, ${element}`;
      });
      this.loaiMaTuSelected_text = this.loaiMaTuSelected_text.substring(2);
    },
    loaiVatTuSelected_value: function (data) {
      this.fetchDSMaVatTu(data);
    },
  },
  directives: {
    ClickOutside,
  },
  methods: {
    ...mapActions("quanlynhatram", [
      "getDSLoaiVatTu",
      "getDSMaVatTu",
      "getDSTaiSanTB",
    ]),
    hide: function () {
      this.showMaVatTu = false;
    },
    hideLoaiVT: function () {
      this.showLoaiVatTu = false;
    },
    async onClickGhiLai() {
      const updateLst = this.listTaiSanTB.filter(item=> item.ISUPDATE == 1);
      if(updateLst.length > 0) {
        for (let index = 0; index < updateLst.length; index++) {
          const element = updateLst[index];
          const param = {
            maTbi: element.MA_TBI,
            thietBiId: element.THIETBI_ID
          }
          await this.capNhatMaTB(param);
        }
        this.fetchDSTaiSanTB();
      } else {
        this.$toast.error("Dữ liệu không có sự thay đổi");
      }
    },
    async fetchDSLoaiVatTu() {
      this.loading(true);
      const response = await this.getDSLoaiVatTu();
      // console.log("getDSLoaiVatTu", response);
      if (response.data.error_code === "BSS-00000000") {
        this.listLoaiVatTu = response.data.data;
        this.loaiVatTuSelected_value = response.data.data[0].loaiVtId;
        this.loaiVatTuSelected_text = response.data.data[0].maLoaiVt;
      }
      this.loading(false);
    },
    gridLoaiVatTu_selectedRowChanged(data) {
      this.loaiVatTuSelected_value = data.loaiVtId;
      this.loaiVatTuSelected_text = data.maLoaiVt;
    },
    async fetchDSMaVatTu(_loadVatTu) {
      if(_loadVatTu) {
        this.loading(true);
        const response = await this.getDSMaVatTu({
          loaiVt: _loadVatTu,
        });
        if (response.data.error_code === "BSS-00000000") {
          this.listMaVatTu = response.data.data;
          this.loaiMaTuSelected_value = response.data.data[0].vatTuId;
          this.loaiMaTuSelected_text = response.data.data[0].tenVT;
        }
        this.loading(false);
      }
    },
    gridMaVatTu_selectedData_checkbox(data) {
      this.listMaVatTuSelected = data;
      if (data.length < this.listMaVatTu.length) {
        $(".check-action").find("input").prop("checked", false);
      }
      this.listMaVatTuShown = [];
      this.listMaVatTuSelected.forEach((el) => {
        this.listMaVatTu.forEach((temp) => {
          if (el === temp.vatTuId) {
            this.listMaVatTuShown.push(temp.maVT + " - " + temp.tenVT);
          }
        });
      });
    },
    async fetchDSTaiSanTB() {
      this.loading(true);
      let data = {
        maVt: this.listMaVatTuSelected, //[10944, 10949],
        pageNum: 1,
        pageSize: 1000,
      };
      const response = await this.getDSTaiSanTB(data);
      // console.log("getDSTaiSanTB", response);
      if (response.data.error_code === "BSS-00000000") {
        this.listTaiSanTB = response.data.data;
      }
      this.loading(false);
    },
    gridDSTaiSanTB_selectedRowChanged(data) {
      // console.log(data);
      this.rowChoseData = data;
      if(data) {
          this.maTaiSanSelected = data.MA_TBI;
      try {
        this.vtbi_id = 0;
        if (data) {
          this.maTaiSanSelected = data.MA_TBI;
          this.vtbi_id = 0;
        } else {
          this.maTaiSanSelected = "";
          this.vtbi_id = 0;
        }
      } catch (ex) {
        this.maTaiSanSelected = "";
        this.vtbi_id = 0;
      }
      }
    },
    capNhatMaTB: async function (param) {
      try {
        const { data } = await this.axios.put(
          "/web-qlvt/api/quan-ly-nha-tram/cap-nhat-ma-tbi",
          null,
          {
            params: param
          }
        );
        if (data && data.error == "200") {
          this.$toast.success("Cập nhật thiết bị thành công !");
        }
      } catch (error) {
        this.$toast.error('Lỗi khi cập nhật');
      }
    },
    editGrid(arg) {
      if(arg.columnName == 'MA_TBI') {
          this.listTaiSanTB = this.listTaiSanTB.map((item) => {
          const isEqual = item.THIETBI_ID == arg.rowData.THIETBI_ID;
          return {
            ...item,
            MA_TBI: isEqual ? arg.value : item.MA_TBI,
            ISUPDATE: isEqual ? 1 : item.ISUPDATE
          };
        });
      }
    }
  },
};
</script>
