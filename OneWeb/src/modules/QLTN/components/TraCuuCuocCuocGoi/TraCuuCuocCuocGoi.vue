<template src="./TraCuuCuocCuocGoi.html"></template>
<style scoped src="./TraCuuCuocCuocGoi.scss"></style>
<script type="text/javascript">
import breadcrumb from "@/components/breadcrumb";
// import InThuXacNhanCongNoSapXep from '../XacDinhCongNoTheoDanhSach/InThuXacNhanCongNoSapXep'
export default {
  components: { breadcrumb },
  name: "TraCuuCuocCuocGoi",
  mounted() {
    // this.post(this.$API_URL + 'executor/sel_lay_thang_no', {}).then(
    //   (response) => {
    //     this.sel_lay_thang_no = response.data.data
    //   }
    // )
    // this.post(this.$API_URL + 'executor/fn_chukyno_kyhoadon_sel', {
    //   namthang: '',
    // }).then((response) => {
    //   this.fn_chukyno_kyhoadon_sel = response.data.data
    // })
  },
  destroyed() {},
  computed: {
    rows() {
      return this.items.length;
    },
  },
  data() {
    return {
      header: {
        title: "TRA CỨU CƯỚC CUỘC GỌI",
        list: [
          { name: "Gạch nợ tiền", link: { name: "Ui.cards" } },
          {
            name: "Tra cứu phiếu thanh toán theo ngày",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      dateConfig: {
        altInput: true,
        dateFormat: "d/m/Y",
        altFormat: "d/m/Y",
      },
      fields: [
        {
          key: "MA_TT",
          label: "Mã TT",
        },
        {
          key: "TEN_TT",
          label: "Tên TT",
        },
        {
          key: "NHOM_KH",
          label: "Nhóm KH",
        },
        {
          key: "DIACHI_TT",
          label: "Địa chỉ TT",
        },
        {
          key: "DIACHI_CT",
          label: "Địa chỉ CT",
        },
        {
          key: "SO_DT",
          label: "Số ĐT",
        },
        {
          key: "FAX",
          label: "FAX",
        },
        {
          key: "NHOM_CONGNO",
          label: "Nhóm C.nợ",
        },
        {
          key: "TONG_NO",
          label: "Tổng nợ",
        },
        {
          key: "NO_AM",
          label: "Nợ âm",
        },
      ],
      selectedItems: [],
      items: [
        {
          MA_TT: "DNG-001",
          TEN_TT: "Nguyễn Đình Thạnh",
          NHOM_KH: "VIP",
          DIACHI_TT: "46- Trần Thị Lý",
          DIACHI_CT: "--",
          SO_DT: "0914242226",
          FAX: "-",
          NHOM_CONGNO: "",
          TONG_NO: 900000,
          NO_AM: 0,
        },
      ],
      perPage: 10,
      currentPage: 1,
      totalRows: 0,
      ngay_gui: new Date(),
      ngay_xacthuc: new Date(),
      ngay_thuhoi_thu: new Date(),
    };
  },
  methods: {
    openSapXepForm: function () {},
    alert: function () {
      alert("Hello");
    },
  },
};
$("input[name='cbLoai']").click(function () {
  var val = $(this).val();
  if (val == 0) {
    $("#tableMTB").removeClass("hidden");
    $("#tableKM").addClass("hidden");
  } else {
    $("#tableMTB").addClass("hidden");
    $("#tableKM").removeClass("hidden");
  }
});
</script>
