<template src="./ResetCellGphone.html"></template>

<script>
import moment from "moment";
import api from "./ResetCellGphoneAPI";
// import { DatabaseConstants } from "@/const/enums/index.js";
import { DONGBO_GPHONE } from "@/const/enums/index.js";
import ActionTop from "@/components/kylq_components/ActionTop.vue";

export default {
  components: {
    ActionTop
  },
  data() {
    return {
      dateFormat: "DD/MM/YYYY",
      typeFormat: "date",
      tuNgay: "",
      denNgay: "",
      txtGhiChu: "",
      txtSoTB_Reset: "",
      txtSoTB: "",
      vma_tinh: "",
      userGP: "",
      columns: [
        {
          fieldName: "STT",
          headerText: "STT",
          allowFiltering: true,
          allowSorting: true,
          width: 60
        },
        {
          fieldName: "MA_TB",
          headerText: "Số GPhone",
          allowFiltering: true,
          allowSorting: true,
          width: 60
        },
        {
          fieldName: "NGAYTH",
          headerText: "Ngày thực hiện",
          allowFiltering: true,
          allowSorting: true,
          width: 60
        },
        {
          fieldName: "NGUOITH",
          headerText: "Người dùng",
          allowFiltering: true,
          allowSorting: true,
          width: 60
        },
        {
          fieldName: "CHANNEL",
          headerText: "Kênh đặt lệnh",
          allowFiltering: true,
          allowSorting: true,
          width: 60
        },
        {
          fieldName: "MAYTH",
          headerText: "Máy đặt lệnh",
          allowFiltering: true,
          allowSorting: true,
          width: 60
        },
        {
          fieldName: "TRANGTHAI",
          headerText: "Trạng thái",
          allowFiltering: false,
          allowSorting: false,
          width: 60
        },
        {
          fieldName: "GHICHU",
          headerText: "Ghi chú",
          allowFiltering: false,
          allowSorting: false,
          width: 60
        }
      ],
      dataSource: [],
      tt_nd: {
        nguoidung_id: "",
        tinh_id: ""
      },
      actions: [
        {
          id: "timkiem",
          name: "Tìm kiếm",
          active: true,
          icon_class: "icon one-search"
        },
        {
          id: "chonfile",
          name: "Chọn file",
          active: true,
          icon_class: "icon nc-icon-glyph ui-1_circle-add"
        },
        {
          id: "thuchien",
          name: "Thực hiện",
          active: this.enableBtnThucHien,
          icon_class: "icon fa fa-check"
        }
      ]
    };
  },
  mounted() {
    this.tt_nd.nguoidung_id = this.$root.token.getNguoiDungID();
    this.tt_nd.tinh_id = this.$root.token.getPhanVungID();

    this.loadData();
  },
  computed: {
    enableBtnThucHien() {
      this.actions.forEach(i => {
        if (i.id == "thuchien") {
          i.active = this.txtSoTB_Reset ? true : false;
        }
      });
    }
  },
  methods: {
    async loadData() {
      this.vma_tinh = "";
      this.userGP = "";
      this.tuNgay = moment()
        .startOf("month")
        .format("DD/MM/YYYY");
      this.denNgay = moment(new Date()).format("DD/MM/YYYY");
      this.vma_tinh = await this.fn_map_id(
        DatabaseConstants.DB2 + ".tinh",
        "tentat_ccbs",
        "where tinh_id =" + this.tt_nd.tinh_id + ""
      );
      this.userGP = await this.fn_map_id(
        DatabaseConstants.DB1 + ".nguoidung",
        "user_gphone",
        "where nguoidung_id = " + this.tt_nd.nguoidung_id
      );
    },
    async fn_map_id(table, id_neo, dk) {
      const res = await api.fn_map_id(this.axios, {
        vin_dk: dk,
        vin_id_neo: id_neo,
        vin_table: table
      });
      if (res.data) {
        return res.data.data;
      }
    },
    onActionClick(action) {
      if (action.id == "timkiem") {
        this.btnTimKiemClick();
      } else if (action.id == "ghilai") {
        this.btnThucHienClick();
      } else if (action.id == "chonfile") {
        this.btnChonFileClick();
      }
    },
    async timKiem(tuNgay, denNgay, soTB) {
      const res = await api.gphone_reset_cell_list(this.axios, {
        tu_ngay: tuNgay,
        den_ngay: denNgay,
        so_tb: soTB
      });
      if (res.data.error_code == "BSS-00000000") {
        this.dataSource = res.data.data;
      } else {
        this.$toast.error(res.data.message);
      }
    },
    async btnThucHienClick() {
      if (!DONGBO_GPHONE.DONGBO) {
        this.$toast.warning(
          "Đơn vị chưa được mở đồng bộ trực tiếp tới hệ thống GPhone !"
        );
        return;
      }
      if (this.userGP == "-1") {
        this.$toast.warning(
          "Người dùng chưa được gán user kết nối hệ thống gphone. Liên hệ Admin để gán quyền."
        );
        return;
      }
      if (!this.txtSoTB_Reset) {
        this.$toast.warning(
          "DS số GPhone không được để trống."
        );
        return;
      }
      const res = await api.gphone_reset_cell(this.axios, {
        ghi_chu: this.txtGhiChu,
        reset_type: this.userGP,
        so_tb: this.txtSoTB_Reset
      });
      if (res.data.error_code == "BSS-00000000") {
        this.$toast.success("Reset Cell thành công.", "");
      } else {
        this.$toast.error(res.data.message);
      }
    },
    btnTimKiemClick() {
      this.timKiem(
        this.tuNgay,
        this.denNgay,
        this.txtSoTB ? "84" + this.txtSoTB : ""
      );
    },
    handleKeyUp() {
      this.btnTimKiemClick();
    },
    btnChonFileClick() {
      var file = this.$refs.file.files[0];
      const reader = new FileReader();
      if (file.name.includes(".txt")) {
        reader.onload = res => {
          this.txtSoTB_Reset = res.target.result.replace(/\r\n/g, ",");
        };
        reader.onerror = err => console.log(err);
        reader.readAsText(file);
      } else {
      }
    }
  }
};
</script>

<style></style>
