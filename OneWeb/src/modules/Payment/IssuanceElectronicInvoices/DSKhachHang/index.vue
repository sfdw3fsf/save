<template src="./index.html">

</template>

<script>
import Const from './const';
import API from "../API";
import moment from "moment";

export default {
  name: "DsKhachHang",
  data() {
    return {
      dsCols: Const.dsCols,
      errorCols: Const.errorCols,
      cboPattern: {
        value: 0,
        data: []
      },
      cboSerial: {
        value: 0,
        data: []
      },
    }
  },
  props: {
    value: {
      type: Object,
      required: true
    }
  },
  watch: {
    'cboPattern.value': function (newVal) {
      this.loadCboSerial();
    },
  },
  computed: {
    khachHang: {
      get() {
        return this.value;
      },
      set(khachHang) {
        this.$emit('input', khachHang);
      }
    }
  },
  mounted() {
    this.loadForm();
  },
  methods: {
    setActiveTab(tabIndex) {
      this.khachHang.currentTab = tabIndex;
    },

    queryCellInfo(args) {
      switch (args.column.type) {
        case 'checkbox':
          args.cell.classList.add('disabled');
          break;
      }

      switch (args.column.field) {
        case 'lydo':
          args.cell.style.color = 'red'
          break;
        case 'trangthai':
          if (args.data.trangthai === 's1') {
            args.cell.innerHTML = '<div class="text-center"><i class="fa fa-check text-success"></i></div>';
          } else {
            args.cell.innerText = '';
          }
          break;
      }
    },

    openDialog() {
      $('#dsKHFileDialog').click();
    },

    onChangeFile(event) {
      let files = event.target.files;
      if (files.length > 0) {
        let file = files[0];
        $('#dsKHFileDialog').val('');
        this.readExcel(file);
      } else {
        this.khachHang.dsKHFilePath = '';
      }
    },

    readExcel(file) {
      this.$emit('readExcel', {
        file,
        type: 1
      });
    },

    loadForm() {
      try {
        this.loading(true);

        let cboPattern = this.loadCboPattern()

        Promise.all([cboPattern])
          .then(() => {
            this.loading(false);
          })
          .catch(err => {
            this.loading(false);
          });
      } catch (e) {

      }
    },

    async loadCboPattern() {
      try {
        let rs = await API.loadCboPattern(this.axios);
        let js_returnds = JSON.parse(rs.data.data.js_returnds);
        let {DS_LOAI_HOADON} = js_returnds;
        this.cboPattern = {
          value: DS_LOAI_HOADON.length > 0 ? DS_LOAI_HOADON[0].LOAIHOADON_ID : 0,
          data: DS_LOAI_HOADON,
        }
      } catch (e) {
        this.cboPattern = {
          data: [],
          value: 0
        }
      }
    },

    async loadCboSerial() {
      try {
        let rs = await API.loadCboSerial(this.axios, {
            "kieu": 1,
            "loaihd_id": parseInt(this.cboPattern.value),
          }
        );

        let {data} = rs.data;
        this.cboSerial = {
          value: data.length > 0 ? data[0].seri_id : 0,
          data: data
        }
      } catch (e) {
        this.cboSerial = {
          data: [],
          value: 0
        }
      }
    },

    getParams() {
      return {
        cboPattern: this.cboPattern.value,
        cboSerial: this.cboSerial.value
      }
    },

    getCboPatternValue() {
      return this.cboPattern.value;
    },

    getCboPatternText() {
      for (const pattern of this.cboPattern.data) {
        if (pattern.LOAIHOADON_ID == this.cboPattern.value) {
          return pattern.TEN_LOAIHD;
        }
      }

      return this.cboPattern.value;
    },

    getCboSerialValue() {
      return this.cboSerial.value;
    },

    getCboSerialText() {
      for (const pattern of this.cboSerial.data) {
        if (pattern.seri_id == this.cboSerial.value) {
          return pattern.seri;
        }
      }

      return this.cboSerial.value;
    },

    actionComplete() {
      let data = this.khachHang.dsKhachHang;
      if (data != null) {
        let selected = [];
        data.forEach((item, index) => {
          if (item.chon === 's1') {
            selected.push(index);
          }
        });

        this.$refs.dsKhachHang.selectRows(selected);
      }

      $('.dsKhachHang .column-check-selected').addClass('disabled');
    },

    exportExcel() {
      if (this.khachHang.dsKhachHang.length <= 0) {
        this.$root.$toast.error('Không có dữ liệu khách hàng để xuất Excel!');
        return;
      }

      //ẩn cột checkbox nếu allowCheckBox
      let cols = this.$refs.dsKhachHang.grid.getColumns();
      // cols[0].visible = false;
      let excelExportProperties = {
        dataSource: this.khachHang.dsKhachHang,
        enableFilter: true,
        fileName: `KhachHang_${moment().format('DDMMYYYY')}.xlsx`
      };
      this.$refs.dsKhachHang.excelExport(excelExportProperties)
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
.grid-khachhang .e-grid .e-rowcell.e-active {
  font-weight: bold !important;
}
</style>
