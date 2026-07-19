<template src="./index.html"></template>
<style scoped>

</style>

<script>
import API from "../api/XacNhanNNKhongGiaHan";
import moment from "moment";
import { DropDownList } from '@syncfusion/ej2-dropdowns'
import { cloneArray } from '@/utils/util'

import Vue from "vue";
import { MultiSelectPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns';
MultiSelect.Inject(CheckBoxSelection);
Vue.use(MultiSelectPlugin);

let lydoElem, lydoObj
export default {
  data() {
    return {
      editSettings: { allowEditing: true, allowAdding: false, allowDeleting: false, mode: 'Normal' },
      txtTimKiem: "",
      txtGhiChu: "",
      xacNhan: "1",
      checkChuyenGD: false,
      idNVTC: [],
      dsNVTC: [],
      kyCuoc: `${moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .format("YYYYMM")}01`,
      dsThueBao: [],
      KHONG_XN_LYDO_NOCUOC_NVTC: -1,
      strttph_id: "",
      enabledChuyenGD: true,
      enabledKhongXN: true,
      enabledXacNhan: true,
      dsLyDoTra:[],
      lyDoParams: {
        create: () => {
          lydoElem = document.createElement('input')
          return lydoElem
        },
        read: () => {
          return lydoObj.value
        },
        destroy: () => {
          lydoObj.destroy()
        },
        write: () => {
          lydoObj = new DropDownList({
            dataSource: this.dsLyDoTra,
            fields: { value: 'LYDOTRA_ID', text: 'NOIDUNG' },
            enabled: true,
            placeholder: 'Chọn lý do',
            floatLabelType: 'Never',
            popupWidth: 500
          })
          lydoObj.appendTo(lydoElem)
        }
      },
    };
  },
  watch: {},
  created() {
    this.initForm();
    this.fetchNVTC();
    this.dsLyDoDropDown();
  },
  mounted() { },
  methods: {
    initForm() {
      if (this.$route.query && this.$route.query.TTPH_ID) {
        this.strttph_id = this.$route.query.TTPH_ID;
      }
      if (this.strttph_id == 4) {
        this.checkChuyenGD = false;
        this.enabledChuyenGD = false;
        this.enabledKhongXN = false;
        this.enabledXacNhan = false;
      }
    },
    DanhSach() {
      if (this.idNVTC.length <= 0) {
        this.$root.toastError("Bạn chưa chọn nhân viên thu cước!");
        return;
      }
      this.fetchDSHoaDon();
    },
    async CapNhat() {
      const dsSelected = this.$refs.gridDsThueBao.getSelectedRecords() || [];
      if (dsSelected.length <= 0) {
        this.$root.toastError("Bạn chưa chọn thuê bao!");
        return;
      }
      //   if (this.txtGhiChu.trim() == "") {
      //     this.$root.toastError("Bạn chưa nhập lý do cho hóa đơn đã chọn!");
      //     return;
      //   }
      await this.fetchCapNhat(dsSelected);
      if (this.strttph_id != 4 && !this.checkChuyenGD && this.xacNhan == "1") {
        await this.fetchDieuBienDong(dsSelected);
      }
      this.fetchDSHoaDon();
    },
    async fetchNVTC() {
      const params = {
        pNguoiDungId: this.$auth.getNguoiDungID(),
      };
      let response;
      if (this.strttph_id == 1) {
        response = await API.getDanhSachNVThuCuocAM(this.axios, params);
      } else {
        response = await API.getDanhSachNVThuCuoc(this.axios, params);
      }
      if (response.data.error_code === "BSS-00000000") {
        this.dsNVTC = response.data.data;
      }
    },
    async fetchDSHoaDon() {
      try {
        this.loading(true);
        const data = {
          nvtcId: this.idNVTC,
          ttxnId: this.strttph_id == "" ? 1 : this.strttph_id,
          nhanVienId: this.$auth.getNhanVienID(),
          donViId: this.$auth.getDonViID(),
        };
        const response = await API.getDSThueBao(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsThueBao = response.data.data;
        } else {
          this.dsThueBao = [];
          this.$root.toastError("Không có dữ liệu. Mời thử lại!");
        }
      } catch (error) {
        this.dsThueBao = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchCapNhat(dsThueBao) {
      const dsRequest = dsThueBao.map((item) => ({
        thueBaoId: item.THUEBAO_ID,
        // lyDoKGH: this.txtGhiChu.trim() != "" ? this.txtGhiChu : item.GHICHU,
        lyDoKGH: item.LYDO_KGH
      }));
      try {
        this.loading(true);
        const data = {
          kyCuoc: this.kyCuoc,
          ghiChu: this.txtGhiChu,
          chuyenGD: this.checkChuyenGD ? 1 : 0,
          xacNhan: this.xacNhan,
          ds: dsRequest,
        };
        const response = await API.capNhatXacNhanKGH(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          //   this.$root.toastSuccess("Thực hiện thành công!");
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchDieuBienDong(dsThueBao) {
      const dsRequest = dsThueBao.map((item) => item.THUEBAO_ID);
      try {
        // this.loading(true);
        const data = {
          dsThueBaoId: dsRequest,
          nhanVienId: this.$auth.getNhanVienID(),
        };
        const response = await API.dieuBienDongThueBao(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Thực hiện thành công!");
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    txtTimKiem_Keypress: function (event) {
      if (event.code == "Enter") {
        let dsThueBao = this.dsThueBao.filter((item) =>
          item.MA_TT == this.txtTimKiem
        )
        this.dsThueBao = dsThueBao;
      }
    },
    dsLyDoDropDown: async function () {
      try {
        var rs = await this.$root.context.get('/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-ly-do-tra')
        this.dsLyDoTra = rs.data
      } catch (error) {
      }
    },
    actionComplete (args) {
      if (args.action === 'edit' && args.requestType === 'save') {
        let data = cloneArray(this.dsThueBao)
        for (let i = 0; i < data.length; i++) {
          if (data[i].THUEBAO_ID === args.data.THUEBAO_ID) {
            data[i].LYDO_KGH = args.data.LYDO_KGH
            break
          }
        }
        this.dsThueBao = cloneArray(data)
      }
    },
  },
  computed: {},
};
</script>
