<template src="./index.template.html"> </template>
<script>
import moment from "moment";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import Vue from 'vue';
import { Query } from '@syncfusion/ej2-data';
import XLSX from 'xlsx';

const defaultItem = {
  id: 0,
  name: '',
  classify: null,
  code: '',
  note: null,
  validity: 1
}

export default {
  components: {
    DatePickerPlugin,
    DatePicker,
  },
  data() {
    return {
      dsSpDichVu: [],
      dataSource: [],
      spdvId: null,
      spdvIds: [],
      monthFormat: "MM/YYYY",
      thangTimKiem: { value: moment(new Date()).format("MM/YYYY"), Enabled: true },
      controls: {
        selectSpdv: {
          invalid: true,
        }
      }
    }
  },
  computed: {
    // get component from ref which has value "gridItems"
    gridItems() {
      return this.$refs.gridItems
    }
  },
  watch: {
    'currentItem.id': function (value) {
      this.buttons.btnSua.enabled = value > 0
      this.buttons.btnXoa.enabled = value > 0
    },

  },
  created: async function () {
    // await this.load();
    this.dsSpDichVu = await this.getDmSanPhamDichVu();
  },
  methods: {
    handleErrorResponse(error) {
      if (!(error.response == null || error.response == undefined)) {
        if (!(error.response.data == null || error.response.data == undefined)) {
          this.$root.toastError(error.response.data.message_detail)
        }
      }
    },
    getHieuLucText(value) {
      return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
    },
    onChangeThangTimKiem(value_) {
      if (!moment(value_, "MM/YYYY").isValid()) {
        this.thangTimKiem.value = "01/2000";
      } else {
        this.thangTimKiem.value = value_;
      }
    },

    async load() {
      try {
        this.$root.showLoading(true)
        this.dataSource = await this.getDsYeuCauThuHoi();
      } finally {
        this.$root.showLoading(false)
      }
    },
    async getDsYeuCauThuHoi() {
      let result = [];
      // Kiểm tra nếu spdvId không hợp lệ
      if (this.spdvIds.length == 0) {
        this.$root.toastError("Vui lòng chọn ít nhất một sản phẩm dịch vụ!");
        return result;
      }
      try {
        const rs = await this.$root.context.post('/web-ecms/toiuu-tainguyen/lay-ds-yeucau-toiuu', {
          p_thang: this.thangTimKiem.value,
          p_spdv_ids: this.spdvIds.toString(),
        });
        result = rs.data;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    xuatExcel: async function () {
      try {
        if (this.dataSource == []) {
          this.$toast.warning("Dữ liệu trống!");
        } else {
          let data = []
          this.dataSource.forEach(item => {
            data.push({
              'STT': item.STT,
              'Tên máy chủ': item.TEN_MAYCHU,
              'IP': item.IP,
              'Sản phẩm dịch vụ': item.SPDV_ID,
              'Đơn vị chủ quản': item.DONVI_CHUQUAN,
              'Đơn vị vận hành': item.DONVI_VANHANH,
              'Quản trị viên': item.TEN_QUANTRIVIEN,
              'vCPU': item.VCPU,
              'RAM': item.VRAM,
              'CPU USE MAX (%)': item.VCPU_USE_MAX,
              'RAM USE MAX (%)': item.VRAM_USE_MAX,
            })
          })

          let worksheet = XLSX.utils.json_to_sheet(data)
          let workbook = XLSX.utils.book_new()
          XLSX.utils.book_append_sheet(workbook, worksheet, "Danh sách hiệu năng tháng")
          XLSX.writeFile(workbook, "Báo cáo hiệu năng tháng.xlsx")
        }
      } catch (ex) {
        this.$toast.error("Có lỗi trong quá trình xuất file Excel !" + ex)
      }
    },
    async addItem(args) {
      let result = false
      try {
        let rs = await this.$root.context.post('/web-ecms/danh-muc-loai-thiet-bi/create', {
          ten: args.name,
          phanLoai: args.classify,
          ma: args.code,
          ghiChu: args.note,
          hieuLuc: args.validity
        })
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    async updateItem(args) {
      let result = false
      try {
        let rs = await this.$root.context.post('/web-ecms/danh-muc-loai-thiet-bi/update', {
          id: args.id,
          ten: args.name,
          phanLoai: args.classify,
          ma: args.code,
          ghiChu: args.note,
          hieuLuc: args.validity
        })
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },

    validateControls() {
      let msg = ''
      if (this.controls.txtName.invalid) {
        msg = 'Tên loại thiết bị không được để trống.'
      }
      if (this.controls.comboboxClassify.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'Phân loại loại thiết bị không được để trống.'
      }
      if (this.controls.txtCode.invalid) {
        if (msg != '') msg += '\n\r'
        msg += 'Mã thiết bị không được để trống.'
      }
      if (msg != '') this.$root.toastError(msg)
      return msg == ''
    },
    rowSelected(args) {
      this.setCurrentItem(args.data)
    },
    rowUnselected(args) {
      // get all selected records from component has computed ref - DataGrid
      const items = this.gridItems.getSelectedRecords()
      if (!(items == null || items.length == 0)) {
        // if there are many records fill form with first item
        this.setCurrentItem(items[0])
      } else {
        this.setDefaultItem()
      }
    },
    async btnSaveClick(args) {
      if (this.validateControls()) {
        let flag = await this.$confirm('Có chắc bạn muốn lưu lại loại cổng đang chọn?', 'Xác nhận thực hiện', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'info',
          dangerouslyUseHTMLString: true
        })
          .then((s) => true)
          .catch((e) => false)
        if (flag) {
          try {
            this.$root.showLoading(true)
            if (this.currentItem.id == 0) {
              let rs = await this.addItem(this.currentItem)
              if (rs) {
                this.$root.toastSuccess('Thêm loại thiết bị thành công')
              } else {
                this.$root.toastError('Thêm loại thiết bị thất bại')
              }
              this.enableControls(!rs)
            } else {
              let rs = await this.updateItem(this.currentItem)
              if (rs) {
                this.$root.toastSuccess('Cập nhật loại thiết bị thành công')
              } else {
                this.$root.toastError('Cập nhật loại thiết bị thất bại')
              }
              this.enableControls(!rs)
            }
          } finally {
            this.$root.showLoading(false)
            this.enableControls(false)
            await this.load()
          }
        }
      }
    },
    btnNewClick() {
      this.load();
      // this.setDefaultItem()
    },

    getDmSanPhamDichVu: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/spdv')
        result = rs.data;
                result.forEach(element => {
                  element.id = element.ID;
                  element.label = element.TEN;
                });
      } catch (error) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },

  }
}
</script>
<style scoped></style>
