<template>
  <DataGrid
    v-bind:columns="columns"
    v-bind:dataSource="danhSach"
    :showFilter="true"
    :allowPaging="true"
    :enablePagingServer="false"
    :showColumnCheckbox="true"
    @selectedItemsChanged="selectedItemsChanged"
    :enabledSelectFirstRow="false"
    :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
    @rowClicked="gridChiTiet_Selected"
    @queryCellInfo="queryCellInfo"
  >
  </DataGrid>
</template>

<script>
var property = {
  ma_tb: "Số máy/Acc",
  ma_lt: "Số ảo",
  loaihinh_tb: "Loại hình",
  dienthoai_lh: "Số liên hệ",
  dienthoai_bh: "Điện thoại báo hỏng",
  nguoi_bh: "Người báo hỏng",
  ngay_bh: "Ngày báo hỏng",
  ten_dv: "Đơn vị giao",
  nvkt_db: "Nhân viên KT",
  ten_tb: "Tên thuê bao",
  diachi_ld: "Địa chỉ LĐ",
  goi_kt: "Gói KT",
  ten_plkh: "Phân loại KH",
  kenh_tn: "Kênh tiếp nhận",
  chitieu_tg: "Chỉ tiêu thời gian",
  tgxl: "Giờ xử lý",
  gio_conlai: "Giờ còn lại",
  gio_conlai_t: "Giờ còn lại",
  gio_ck: "Giờ CK",
  mucdo: "Mức độ",
  ds_nhanvien_th: "Nhân viên TH",
  donvinhan: "Đơn vị nhận",
  tinhtrang: "Tình trạng",
  luot_bh: "Lượt BH",
  lan_pa: "KH gọi",
  nguyennhan: "Nguyên nhân",
  ngaygiao: "Ngày giao",
  nd_giao: "Nội dung giao",
  lan_ks: "Lần khảo sát",
  ngay_sd: "Ngày SD",
  ghichu_hong: "Ghi chú hỏng",
  ghichu_th: "Ghi chú TH",
  lydoton: "Lý do tồn",
  ghichuton: "Ghi chú tồn",
  ttvt_ton: "Ghi chú TTVT",
  nhanvienql: "Nhân viên QL",
  ten_cap: "Cáp gốc",
  doi_cap: "Đôi số",
  ten_kc: "Kết cuối",
  vitri: "Vị trí",
  tientrinh: "Tiến trình",
  hensuatu: "Hẹn sửa từ",
  hensuaden: "Hẹn sửa đến",
  ten_kv: "Khu vực",
  nvkd_db: "Nhân viên kinh doanh",
  nd_tra_con: "Nội dung trả",
  ngaytra_con: "Ngày trả",
  chuyenmang: "NCC khác(nhà cung cấp khác)",
  // ghichu: "Ghi chú",
  nhom_kh: "Nhóm KH"
};
export default {
  props: {
    danhSach: Array,
    collumnsInvisible: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      columns: [],
    };
  },
  created() {
    // this.handleColumns();
  },
  methods: {
    handleColumns() {
      this.columns = [];
      if (this.danhSach && this.danhSach.length) {
        const Ds = this.danhSach[0];
        let index = 0;
        for (const key in property) {
          if (
            Object.hasOwnProperty.call(Ds, key) &&
            !this.collumnsInvisible.includes(key)
          ) {
            this.columns.push({
              fieldName: key,
              headerText: property[key],
              allowFiltering: true,
            });
          }
          if (index == 6) {
            // Same key Ghi Chú Hỏng
            this.columns.push({
              fieldName: "ghichu_hong",
              headerText: "Ghi chú BH",
              allowFiltering: true,
            });
          }
          index++;
        }
      } else {
        let index = 0;
        for (const key in property) {
          this.columns.push({
            fieldName: key,
            headerText: property[key],
            allowFiltering: true,
          });
          if (index == 6) {
            // Same key Ghi Chú Hỏng
            this.columns.push({
              fieldName: "ghichu_hong",
              headerText: "Ghi chú BH",
              allowFiltering: true,
            });
          }
          index++;
        }
      }
    },
    selectedItemsChanged(items) {
      if (!this.danhSach.length) {
        return;
      }
      this.$emit("getCurrentSelectedItems", items);
    },
    gridChiTiet_Selected(i, item) {
      if (!this.danhSach.length) {
        return;
      }
      this.$emit("onFocusRowDS_BaoHong", item);
    },
    queryCellInfo(arg) {
      if (!this.danhSach.length) {
        return;
      }
      this.$emit("queryCellInfo", arg);
    },
  },
  watch: {
    danhSach() {
      this.handleColumns();
    },
  },
};
</script>

<style>
</style>