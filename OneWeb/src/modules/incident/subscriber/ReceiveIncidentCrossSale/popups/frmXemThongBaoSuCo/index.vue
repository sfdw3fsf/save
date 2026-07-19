<template src="./index.html">

</template>

<script>

import moment from "moment";
import config from "./config";

export default {
  name: "frmXemThongBaoSuCo",
  data() {
    return {
      headerTitle: 'Thông báo sự cố trung tâm viễn thông',
      dsSuCo: [],
      wbrInformation: '',
      cboPhanLoai: {
        value: 0,
        data: []
      },
      cboTrangThai: {
        value: 0,
        data: []
      },
      cboNguonSuCo: {
        value: 0,
        data: []
      },
      txtMaSuCo: '',
      dateFrom: '',
      dateTo: '',
      cols: config.cols,
      dsDanhMucChung: []
    }
  },
  methods: {
    handleShowModal() {
    },

    hideModal() {
      this.dsSuCo = [];
      this.cboPhanLoai.value = this.cboTrangThai.value = this.cboNguonSuCo.value = 0;
      this.txtMaSuCo = '';
      this.$refs.frmXemThongBaoSuCo.hide()
    },

    async initData(data) {
      // this.dsSuCo = data;
      // this.wbrInformation = "";
      //
      // if (data != null && data.length > 0) {
      //   this.HT_THONGBAO_SUCO();
      // } else {
      //   this.wbrInformation = '<label class="w-100 font-weight-bold d-flex justify-content-center ' +
      //     'align-items-center text-uppercase text-primary" style="height: 50px; font-size: 20px">' +
      //     'Chưa có thông báo</label>'
      // }

      this.dateFrom = '01/01/' + moment().format('YYYY');
      this.dateTo = moment().format('DD/MM/YYYY');
      await this.lay_ds_danhmuc_chung();
      await this.HT_THONGBAO_SUCO_2();
    },

    showModal() {
      this.$refs.frmXemThongBaoSuCo.show();
    },

    HT_THONGBAO_SUCO() {
      try {
        this.wbrInformation = "<html><body style='font: 9.75pt Tahoma; margin: 5px'>";
        let _tbody = "<table class='table table-bordered'>" +
          "        <thead >" +
          "        <tr>" +
          "        <th class='text-center'>Stt</th>" +
          "        <th>Đơn vị thông báo</th>" +
          "        <th>Lý do</th>" +
          "        <th>Nội dung</th>" +
          "        <th>Thời gian</th>" +
          "        <th>Tiến độ xử lý</th>" +
          "        </tr>" +
          "        </thead>" +
          "        <tbody style='border: 1px solid #ccc;'>";
        let i = 1;
        for (let dr of this.dsSuCo) {
          _tbody += `<tr>
              <td class='text-center'>${i}</td>
              <td>${dr.dvi_thongbao}</td>
              <td>${dr.lydo}</td>
              <td>${dr.ghichu}</td>
              <td>${dr.ngay_th + ' - ' + dr.ngay_th_den}</td>
              <td>${dr.tiendo_xuly}</td>
            </tr>`;
        }

        _tbody += '</tbody>' +
          '        </table>' +
          '        </body></html>';

        this.wbrInformation += _tbody;
      } catch (e) {
        this.wbrInformation += "Chưa có thông báo";
      }

      this.wbrInformation += '</body></html>';
    },

    async lay_ds_danhmuc_chung() {
      try {
        let rs = await this.axios.post('/web-quantri/danhmuc/lay_ds_danhmuc_chung', {
          "vma_nhom_loai": "TIEN_DO_XLY"
        });

        if (rs.data.data) {
          let {data} = rs.data;
          let {returnds, returnds_phanloai, returnds_nguonsc} = data;
          this.cboTrangThai.data = returnds;
          this.cboPhanLoai.data = returnds_phanloai;
          this.cboNguonSuCo.data = returnds_nguonsc;
          if (returnds) {
            this.cboTrangThai.value = 2;
          }

          if (returnds_phanloai) {
            this.cboPhanLoai.value = returnds_phanloai[0].ma_loai;
          }

          if (returnds_nguonsc) {
            this.cboNguonSuCo.value = returnds_nguonsc[0].nguonsc_id;
          }
        }
      } catch (e) {
        console.log(e);
      }
    },

    async HT_THONGBAO_SUCO_2() {
      try {
        let rs = await this.axios.post('web-ccdv/tien_trinh_bao_hong/lay_ds_thongbao_suco_ttvt_popup_vsecond', {
          "vtungay": this.dateFrom,
          "vdenngay": this.dateTo,
          "vtrangthai": this.cboTrangThai.value,
          "vphanloai": this.cboPhanLoai.value,
          "vnguonsc_id": this.cboNguonSuCo.value,
          "vma_sc": this.txtMaSuCo
        });

        this.dsSuCo = rs.data.data;
      } catch (e) {
        console.log(e);
        this.dsSuCo = [];
      }
    },

    btnLayDL_Click() {
      this.HT_THONGBAO_SUCO_2();
    },

    btnExportExcel_Click() {
      if (this.dsSuCo.length <= 0) {
        this.$root.$toast.error('Chưa có dữ liệu trên lưới để xuất excel!');
        return;
      }

      //ẩn cột checkbox nếu allowCheckBox
      let cols = this.$refs.gridDanhSach.grid.getColumns();
      cols[0].visible = false;
      let excelExportProperties = {
        dataSource: this.dsSuCo,
        enableFilter: true,
        fileName: `Thông báo sự cố_${moment().format('YYYYMMDD')}.xlsx`
      };
      this.$refs.gridDanhSach.excelExport(excelExportProperties)
        .then((e) => cols[0].visible = true);//hiện lại khi export xong
    }
  }
}
</script>

<style scoped>

</style>
