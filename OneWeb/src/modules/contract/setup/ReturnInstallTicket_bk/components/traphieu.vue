<template src="./template.html"></template>
<script>
import VueElement from "../../DeclareLandline/components/VueElement.vue";
import SearchContractModal from "../../HandoverTicket/components/SearchContractModal";
import api from "../api/index.js";
import moment from "moment";
export default {
  components: {
    VueElement,
    SearchContractModal,
  },
  data() {
    return {
      collumns: [
        {
          fieldName: "id",
          headerText: "Stt",
          allowFiltering: true,
          allowSorting: false,
          width: 70,
        },
        {
          fieldName: "ma_tb",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          allowSorting: false,
          width: 140,
        },
        {
          fieldName: "ten_tb",
          headerText: "Tên thuê bao",
          allowFiltering: true,
          allowSorting: false,
          width: 140,
        },
        {
          fieldName: "loaihinh_tb",
          headerText: "Loại hình",
          allowFiltering: true,
          allowSorting: false,
          width: 100,
        },
        {
          fieldName: "ngay_giao",
          headerText: "Ngày giao",
          allowFiltering: true,
          allowSorting: false,
          width: 140,
        },
        {
          fieldName: "donvi_giao",
          headerText: "Đơn vị giao",
          allowFiltering: true,
          allowSorting: false,
          width: 140,
        },
        {
          fieldName: "ten_loaihd",
          headerText: "Loại HĐ",
          allowFiltering: true,
          allowSorting: false,
          width: 140,
        },
        {
          fieldName: "nhanvien_lhd",
          headerText: "Nhân viên LHĐ",
          allowFiltering: true,
          allowSorting: false,
          width: 140,
        },
      ],
      options: {
        loaihopdong: [],
        dichvu: [],
        kieutra: [],
        danhsachphieu: [],
      },
      model: {
        ma_gd: this.ma_gd,
        ma_tb: this.ma_tb,
        loaihd_id: this.loaihd_id,
        dichvuvt_id: this.dichvuvt_id,
        ngaygiao: this.ngaygiao,
        ngaytra: this.ngaytra,
      },
      traphieu: {
        ngay_giao: new Date().toLocaleDateString("en-GB"),
        kieutra: null,
        ghichu: "",
        nd_tra: "",
      },
    };
  },
  mounted() {
    this.getDsLoaiHD();
    this.getDsDichvu();
    this.getDsKieutra();
    if (this.getDanhsach) {
      this.getDsPhieugiao();
    }
  },
  props: [
    "ma_gd",
    "ma_tb",
    "loaihd_id",
    "dichvuvt_id",
    "ngaygiao",
    "ngaytra",
    "kieu",
    "nhanvien_id",
  ],
  computed: {
    ngaygiao_format: {
      get() {
        return moment(this.model.ngaygiao).format("DD/MM/YYYY hh:mm:ss");
      },
      set(value) {
        this.model.ngaygiao = value;
      },
    },
    getDanhsach() {
      if (this.model.loaihd_id && this.model.dichvuvt_id && this.model.ma_gd) {
        return true;
      } else {
        return false;
      }
    },
  },
  watch: {
    "model.loaihd_id"(val) {
      if (this.getDanhsach) this.getDsPhieugiao();
    },
    "model.dichvuvt_id"(val) {
      if (this.getDanhsach) this.getDsPhieugiao();
    },
  },
  methods: {
    async getDsLoaiHD() {
      let data = await api.getDsLoaiHD(this.axios, {});
      this.options.loaihopdong = data.data.data ? data.data.data : [];
    },
    async getDsDichvu() {
      let data = await api.getDsDichvu(this.axios);
      this.options.dichvu = data.data.data ? data.data.data : [];
    },
    async getDsKieutra() {
      let data = await api.getDsKieutra(this.axios);
      this.options.kieutra = data.data.data ? data.data.data : [];
    },
    async getDsPhieugiao() {
      let data = await api.getDsPhieugiao(this.axios, {
        ma_gd: "",
        loaihd_id: this.model.loaihd_id,
        dichvuvt_id: this.model.dichvuvt_id,
        donvi_id: 0,
        ngay_giao: "0",
        ngaytra: "0",
        kieu: this.kieu,
        nhanvien_id: this.nhanvien_id,
      });
      this.options.danhsachphieu = data.data ? data.data.data : [];
    },
    focusItem(data) {
      if (data.data) {
        for (let index in data.data) {
          this.$set(this.model, index, data.data[index]);
        }
      }
    },
    async traphieu_click() {
      try {
        this.$bvModal
          .msgBoxConfirm("Bạn thật sự muốn trả phiếu này không?", {
            title: "",
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then(async (v) => {
            if (v) {
              let data = {
                PHIEU_ID: this.model.phieu_id,
                NHANVIEN_ID: this.$root.token.getNhanVienID(),
                STATUS: "1",
                LOAITB_ID: this.model.loaitb_id,
                DICHVUVT_ID: this.model.dichvuvt_id,
                LOAIHD_ID: this.model.loaihd_id,
                QUYTRINH_ID: this.model.quytrinh_id,
                TTHD_ID: this.model.tthd_id,
                HDTB_ID: this.model.hdtb_id,
                HDTB_CHA_ID: this.model.hdtb_cha_id,
                NGAYGIAO: this.model.ngay_giao,
                NGAYTRA: new Date().toLocaleDateString(),
                KIEUGOI_ID: this.model.kieutra_id,
                PHIEU_CHA_ID: this.model.phieu_cha_id,
                ND_TRA: this.traphieu.nd_tra,
                LOAIPT_ID: "1",
                GHICHU: this.traphieu.ghichu,
                LYDOTRA_ID: this.traphieu.kieutra_id,
                DTP_NGAYTRA: new Date().toLocaleDateString(),
              };

              await this.axios.post(
                "web-hopdong/traphieuthicong/fn_traphieu_traphieutc",
                { ds: JSON.stringify(data) }
              );
              this.$emit("update_success", true);
              this.$toast.success("Trả phiếu thành công!");
            }
          });
      } catch (e) {
        console.log(e);
        this.$toast.error("Trả phiếu thất bại!");
      }
    },
    btnLayMaHD_Click() {
      console.log("vào đây");
      this.$refs.popupSearchContract.showModal();
    },
    acceptSearchContract(item) {
      this.model.ma_gd = item.ma_gd;
    },
    async txtMaGD_KeyUp() {
      try {
        let ngaygiao = "";
        if (!this.$refs.chkNgayGiao.checked) ngaygiao = "0";
        else ngaygiao = ngaygiao_format;
        if (!chkNgayTra.Checked) ngaytra = "";
        else ngaytra = dtpNgayTra.Text.ToString();
        ma_gd = txtMaGD.Text.Trim();
        let data = await api.getDsPhieugiao(this.axios, {
          ma_gd: this.model.ma_gd,
          loaihd_id: this.model.loaihd_id,
          dichvuvt_id: this.model.dichvuvt_id,
          donvi_id: 0,
          ngay_giao: "0",
          ngaytra: "0",
          kieu: this.kieu,
          nhanvien_id: this.nhanvien_id,
        });

        this.options.danhsachphieu = data.data ? data.data.data : [];
      } catch (err) {
        console.log(err)
      }
    },
  },
};
</script>
<style>
.ghichu-traphieu .form-control {
  height: 62px !important;
}

.noidung-traphieu .form-control {
  height: 140px !important;
}
</style>
