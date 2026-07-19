<template src="./index.html">

</template>

<script>
import Const from "./const";
import moment from "moment";

export default {
  name: "DsHoaDon",
  data() {
    return {
      dsCols: Const.dsCols,
      errorCols: Const.errorCols,
    }
  },
  props: {
    value: {
      type: Object,
      required: true
    },
    dsKhachHang: {
      type: Array,
      required: true
    }
  },
  computed: {
    hoaDon: {
      get() {
        return this.value;
      },
      set(hoaDon) {
        this.$emit('input', hoaDon);
      }
    }
  },
  methods: {
    setActiveTab(tabIndex) {
      this.hoaDon.currentTab = tabIndex;
    },

    queryCellInfo(args) {
      switch (args.column.field) {
        case 'lydo':
          args.cell.style.color = 'red'
          break;
        case 'tongtien':
          args.cell.style.color = 'green';
          args.cell.innerText = this.numberWithCommas(args.data.tongtien)
          break;
        case 'tongtien_vat':
          args.cell.style.color = 'blue';
          args.cell.innerText = this.numberWithCommas(args.data.tongtien_vat)
          break;
        case 'dongia':
          args.cell.innerText = this.numberWithCommas(args.data.dongia)
          break;
        case 'soluong':
          args.cell.innerText = this.numberWithCommas(args.data.soluong)
          break;
        case 'phathanh_hd':
          if (args.data.phathanh_hd === 's1') {
            args.cell.innerHTML = '<div class="text-center"><i class="fa fa-check text-success"></i></div>';
          } else {
            args.cell.innerText = '';
          }
          break;
        case 'thanhtoan_hd':
          if (args.data.thanhtoan_hd === 's1') {
            args.cell.innerHTML = '<div class="text-center"><i class="fa fa-check text-success"></i></div>';
          } else {
            args.cell.innerText = '';
          }
          break;
      }
    },

    numberWithCommas(n) {
      try {
        let parts = n.toString().split(".");
        return (parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") + (parts[1] ? "." + parts[1] : ""));
      } catch (e) {
        return n;
      }

    },

    openDialog() {
      console.log('DSHOADONDIALOG');
      if (this.dsKhachHang == null || this.dsKhachHang.length <= 0) {
        this.$root.$toast.error('Chưa có thông tin khách hàng');
        return;
      }

      $('#dsHDFileDialog').click();
    },

    onChangeFile(event) {
      let files = event.target.files;
      if (files.length > 0) {
        let file = files[0];
        $('#dsHDFileDialog').val('');
        this.readExcel(file);
      } else {
        this.hoaDon.dsHDFilePath = '';
      }
    },

    readExcel(file) {
      this.$emit('readExcel', {
        file,
        type: 2
      });
    },

    actionComplete() {
      let data = this.hoaDon.dsHoaDon;
      if (data != null) {
        let selected = [];
        data.forEach((item, index) => {
          if (item.chon === 's1') {
            selected.push(index);
          }
        });

        this.$refs.dsHoaDon.selectRows(selected);
      }
    },

    rowClicked(rowIndex, rowData) {
      let {ma_kh, stt_hd, phathanh_hd, thanhtoan_hd} = rowData;
      let data = this.hoaDon.dsHoaDon;

      if (phathanh_hd === "s1" && thanhtoan_hd == "s1") {
        this.hoaDon.dsHoaDon = data.map((item, index) => {
          if (rowIndex === index) {
            item.chon = 's0';
          }

          return item;
        });

        this.$toast.error(
          'Hóa đơn điện tử đã được phát hành và thanh toán!',
          'Thông báo',
          {confirmButtonText: 'Đóng'}
        );

        return;
      }

      this.hoaDon.dsHoaDon = data.map((item, index) => {
        if (ma_kh === item.ma_kh && stt_hd === item.stt_hd) {
          item.chon = item.chon === 's1' ? 's0' : 's1';
        }

        return item;
      });
    },

    exportExcel() {
      if (this.hoaDon.dsHoaDon.length <= 0) {
        this.$root.$toast.error('Không có dữ liệu hóa đơn để xuất Excel!');
        return;
      }

      //ẩn cột checkbox nếu allowCheckBox
      let cols = this.$refs.dsHoaDon.grid.getColumns();
      // cols[0].visible = false;
      let excelExportProperties = {
        dataSource: this.hoaDon.dsHoaDon,
        enableFilter: true,
        fileName: `HoaDon_${moment().format('DDMMYYYY')}.xlsx`
      };

      this.$refs.dsHoaDon.excelExport(excelExportProperties)
        .then((e) => cols[0].visible = true);//hiện lại khi export xong
    }
  }
}
</script>

<style scoped>
.btn-second {
  background-color: #fff;
  border-color: #0176FF;
  color: #0176FF;
  font-weight: 600;
}

.btn-second:hover {
  background: #0176FF;
  color: #fff;
}
</style>
<style>
.grid-hoadon .e-grid .e-rowcell.e-active {
  font-weight: bold !important;
}
</style>
