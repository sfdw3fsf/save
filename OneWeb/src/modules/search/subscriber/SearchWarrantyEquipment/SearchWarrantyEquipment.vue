<template src="./SearchWarrantyEquipment.html"></template>
<style scoped src="./SearchWarrantyEquipment.scss"></style>

<script>
import breadcrumb from "@/components/breadcrumb"
import VueFlatPickr from "vue-flatpickr-component"
import API from "./api"
import gridview from "@/components/Shared/gridview"
import DataGridNV from "./components/DataGrid"
import DataGridCustom from "./components/DataGridCustom"
import KDatePicker from "@/components/kylq_components/KDatePicker.vue"
import XLSX from "xlsx"
import moment from 'moment'

// CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css"
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css"
// JS
import "@/assets/vendor/jquery/split"
import "@/assets/vendor/bootstrap-table/bootstrap-table.min"
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min"

import Vue from "vue"
export default {
  components: { breadcrumb, VueFlatPickr, gridview, API, DataGridNV, DataGridCustom, KDatePicker },
  name: "SearchWarrantyEquipment",
  created () {},

  mounted () {
    this.init()
    this.NgayTN = this.currentDate()
    this.Tungay = this.currentDate()
    this.Denngay = this.currentDate()
  },

  watch: {
  },

  data () {
    return {
      header: {
        title: "TRA CỨU THIẾT BỊ BẢO HÀNH",
        list: [
          { name: "Tra cứu", link: { name: "Ui.cards" } },
          {
            name: "Tra cứu thiết bị bảo hành",
            link: { name: "Ui.buttons" }
          }
        ]
      },
      ctkn: [
        {
          fieldName: "thietbi_id",
          headerText: "",
          textAlign: "center",
          template: function () {
            return {
              template: Vue.component("TemplateEdit", {
                template: `<a class="btn btn-main pad4 lh14"
                                style="background-color: #0176FF !important; width: 100%; color: white !important"
                                @click="onClickGridButton">
                                <span class="-ap icon-add"></span></a>`,
                data () {
                  return {data: {}}
                },
                computed: {
                  parent () {
                    return this.$parent.$parent.$parent
                  }
                },
                methods: {
                  onClickGridButton () {
                    this.parent.HamDeGoi(this.data)
                  }
                }
              })
            }
          },
          allowFiltering: false,
          allowSorting: false,
          width: 40
        },
        {
          headerText: "Serial",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: "serial"
        },
        {
          headerText: "Serial mới",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: "serial_moi"
        },
        {
          headerText: "Trạng thái",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: "trangthai_bh"
        },
        {
          headerText: "Ngày tiếp nhận",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: "ngay_yc",
          type: 'string' ,
        },
        {
          headerText: "Đơn vị tiếp nhận",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: "ten_dv"
        },
        {
          headerText: "Người tiếp nhận",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: "ten_nv"
        },
        {
          headerText: "Nhóm thiết bị",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: "nhom_tbi"
        },
        {
          headerText: "Loại thiết bị",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: "loai_tbi"
        },
        {
          headerText: "Kiểu bảo hành",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: "kieu_bh"
        },
        {
          headerText: "Tình trạng",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: "tinhtrang"
        },
        {
          headerText: "Phụ kiện",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: "phukien"
        },
        {
          headerText: "Kết quả",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: ""
        },
        {
          headerText: "Số máy/Acc",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: "ma_tb"
        },
        {
          headerText: "Tên TB",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: "ten_tb"
        },
        {
          headerText: "Địa chỉ lắp đặt",
          autoFit: true,
          allowSorting: false,
          allowFiltering: true,
          fieldName: "diachi_ld"
        }
      ],
      data_phieu: [],
      data_phieu_hienthi: [],
      dateconfig: {
        altFormat: "DD/MM/YYYY",
        altInput: true,
        dateFormat: "DD/MM/YYYY",
        allowInput: true
      },

      ctxl: [
        {
          headerText: "Trạng thái báo hành",
          autoFit: true,
          allowSorting: false,
          fieldName: "trangthai_bh",
        },
        {
          headerText: "Ngày giao",
          autoFit: true,
          allowSorting: false,
          fieldName: "ngaygiao",
        },
        {
          headerText: "Đơn vị giao",
          autoFit: true,
          allowSorting: false,
          fieldName: "donvi_giao",
        },
        {
          headerText: "Nhân viên giao",
          autoFit: true,
          allowSorting: false,
          fieldName: "nhanvien_giao",
        },
        {
          headerText: "Nội dung giao",
          autoFit: true,
          allowSorting: false,
          fieldName: "nd_giao",
        },
        {
          headerText: "Trạng thái phiếu",
          autoFit: true,
          allowSorting: false,
          fieldName: "trangthai_ph",
        },
        {
          headerText: "Đơn vị nhận",
          autoFit: true,
          allowSorting: false,
          fieldName: "donvi_nhan",
        },
        {
          headerText: "Nhân viên thực hiện",
          autoFit: true,
          allowSorting: false,
          fieldName: "nhanvien_th",
        },
        {
          headerText: "Nội dung thực hiện",
          autoFit: true,
          allowSorting: false,
          fieldName: "nd_thuchien",
        },
        {
          headerText: "Ngày TH",
          autoFit: true,
          allowSorting: false,
          fieldName: "ngay_th",
        },
          {
          headerText: " ",
          fieldName: "huonggiao",
          isGroupedColumn: true,
        },
      ],
      thongtintb: "",
      Serial:"",
      NgayTN: "",
      Tungay: "",
      Denngay: "",
      Ghichu: "",
      displayStylePopup:"none",
      baohong_id:0,
      hdtb_id:0,
      baohanh_id : 0,
    }
  },

  methods: {
    async init () {
    },
    onSelectedRowChange_PHIEU(items) {
      this.thongtintb = ''
      this.baohong_id = 0
      this.hdtb_id = 0
      if (this.data_phieu_hienthi.length > 0) {
        this.Serial = this.data_phieu_hienthi[items.rowIndex].serial
        this.Ghichu = this.data_phieu_hienthi[items.rowIndex].ghichu
        this.NgayTN = this.data_phieu_hienthi[items.rowIndex].ngay_yc

        if (this.data_phieu_hienthi[items.rowIndex].hdtb_id > 0)
        this.hdtb_id = this.data_phieu_hienthi[items.rowIndex].hdtb_id;
        if (this.data_phieu_hienthi[items.rowIndex].baohong_id > 0)
        this.baohong_id = this.data_phieu_hienthi[items.rowIndex].baohong_id;
        if (this.baohong_id > 0 || this.hdtb_id > 0)
        this.Thongtinthuebao(this.hdtb_id,this.baohong_id)
      }
    },

    async Thongtinthuebao(v_hdtb,v_baohong)
    {
        this.loading(true)
      // Tra cứu thông tin hđ - bh
      var body_tt =
      {
        "vhdtb_id": v_hdtb,   //1474337
        "vbaohong_id": v_baohong
      }

      var response_tt = await API.lay_tt_tb_baohanh_tbi(this.axios, body_tt)
      if (
        response_tt.data.error_code == "BSS-00000000" &&
          response_tt.data.data && response_tt.data.data.length > 0) {
        this.loading(false)
        var data_tt = response_tt.data.data
        this.thongtintb = response_tt.data.data
      }
      this.loading(false)
    },

    async Timkiem_Click()
    {
      this.loading(true)
      this.data_phieu = []
      // Tra cứu danh sách phiếu bảo hành:
      var body_phieu =
      {
        "vserial": this.Serial.toString(),
        "vtungay": this.Tungay.toString(),
        "vdenngay": this.Denngay.toString()
      }
      var response_phieu = await API.lay_ds_tracuu_bh_v2(this.axios, body_phieu)
      if (
        response_phieu.data.error_code == "BSS-00000000" &&
        response_phieu.data.data && response_phieu.data.data.length > 0) {
        this.loading(false)
        this.data_phieu = response_phieu.data.data

        // Xử lý ngay thang
        this.data_phieu.forEach((element) => {
            if (
              element.ngay_yc != "" && element.ngay_yc.length > 0
            ) {
                element.ngay_yc = moment(element.ngay_yc, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY HH:mm:ss")
            } 
          }
        )
      }
      else
      {
        this.loading(false)
        this.$toast.warning("Không tìm thông tin thiết bị bảo hành!");
        this.clear()
      }
      this.loading(false)
    },

    currentDate () {
      const dateObj = new Date()
      const formatted = ("0" + dateObj.getDate()).slice(-2) + "/" +
             ("0" + (dateObj.getMonth() + 1)).slice(-2) + "/" +
             dateObj.getFullYear()
      return formatted
    },

    async HamDeGoi (baohanh) {
      this.popup()
      // Tra cứu chi tiết phiếu:
      this.loading(true)
      var body_phieuct =
      {
        "vserial": baohanh.serial.toString(),
        "vtungay": this.Tungay.toString(),
        "vdenngay": this.Denngay.toString()
      }
      var response_phieuct = await API.lay_ds_tracuu_bh_ct_v2(this.axios, body_phieuct)
      if (
        response_phieuct.data.error_code == "BSS-00000000" &&
        response_phieuct.data.data && response_phieuct.data.data.length > 0) {
        var data_phieuct = response_phieuct.data.data.filter(
          (item) => item.baohanh_id == baohanh.baohanh_id
        );
        this.$refs.dgvChiTiet_xuly.dataSource = data_phieuct
      }
      this.loading(false)
    },

    popup() {
        this.displayStylePopup = "block";
        this.showPopup = true;
    },
    closepopup() {
      this.displayStylePopup = "none";
    },
    actionCompleteHandle (args) {
      // Xu ly danh sach page
      if (args.rows && args.rows.length > 0) {
        this.data_phieu_hienthi = []
        args.rows.forEach(row => {
          this.data_phieu_hienthi.push(row.data)
        })
        if (this.data_phieu_hienthi.length > 0) {
          this.thongtintb = ''
          this.baohong_id = 0
          this.hdtb_id = 0
          this.Serial = this.data_phieu_hienthi[0].serial
          this.Ghichu = this.data_phieu_hienthi[0].ghichu
          this.NgayTN = this.data_phieu_hienthi[0].ngay_yc

          if (this.data_phieu_hienthi[0].hdtb_id > 0)
          this.hdtb_id = this.data_phieu_hienthi[0].hdtb_id;
          if (this.data_phieu_hienthi[0].baohong_id > 0)
          this.baohong_id = this.data_phieu_hienthi[0].baohong_id;
          if (this.baohong_id > 0 || this.hdtb_id > 0)
          this.Thongtinthuebao(this.hdtb_id,this.baohong_id)
        }
      }
    },

    // Bổ sung chức năng xuất Excel
    btnExportExcel(){
      if (this.data_phieu.length > 0) {
        let data = XLSX.utils.json_to_sheet(this.data_phieu)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'BaoHanh') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'BaoHanhThietBi.xlsx')
      } else this.$root.$toast('Không có dữ liệu!')
    },
    
    clear()
    {
      this.data_phieu = []
      this.NgayTN = this.currentDate()
      this.Ghichu = ''
      this.thongtintb = ''
    }
  }
}
</script>
