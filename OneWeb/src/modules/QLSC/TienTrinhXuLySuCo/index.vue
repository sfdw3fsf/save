<template src="./index.html"></template>
<script>
import { required } from 'vuelidate/lib/validators'
import { Page, Filter, CommandColumn, Resize, Sort, Freeze } from "@syncfusion/ej2-vue-grids"
import { MultiSelectPlugin } from "@syncfusion/ej2-vue-dropdowns";
import ThemTienTrinh from './modal'
import sms from './sms'
import XLSX from "xlsx";
import { L10n } from '@syncfusion/ej2-base'
L10n.load({
  'vi-VN': {
    treegrid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị'
    }
  }
})
export default {
  components: { ThemTienTrinh, sms },
  provide: {
    grid: [Filter, Page, CommandColumn, Resize, Sort, Freeze],
    multiselect: [MultiSelectPlugin]
  },
  data: function () {
    return {
      dsDonVi: [],
      dsNguonSuCo: [],
      dsTrangThaiSuCo: [],
      dsSuCo: [],
      suCoSelected: null,
      dsTienTrinh: [],
      dsTienTrinhPhieu: [],
      fields: '',
      maSuCoInput: null,
      donviSelected: null,
      cboNguonSuCo: null,
      cboTrangThaiSuCo: null,
      isDisabledNhapMoi: false,
      showtabTienTrinh: true,
      TTObject: {
        DONVI_CN: null,
        DONVI_ID: null,
        IP_CN: null,
        MAY_CN: null,
        NGAY_CN: null,
        NGUOI_CN: null,
        NHANVIEN_ID: null,
        NOIDUNG: null,
        PHANVUNG_ID: null,
        SUCO_ID: null,
        TIENTRINHSC_ID: null
      },

      waterMark: 'Chọn ngày',
      dateVal: new Date(),
      dateFormat: 'dd/MM/yyyy',
      cboNgayBHTu: new Date(),
      cboNgayBHDen: new Date(),
      cboNgayNTTu: new Date(),
      cboNgayNTDen: new Date(),

      allowBHDate: false,
      checkBoxBH: false,
      allowNTDate: false,
      checkBoxNT: false,
      ghiChuText: '',

      checkAllSC: false,
      scChecked: [],
      showGridSC: false,
      showGridTT: false,
      filterOptions: { showFilterBarStatus: false },
      columnTemplate: function () {
        return {
          template: {
            template: '<span :id="data.index" :style="{display: display }" style="font-size: 12px;" class="col-mark-selected-row fa fa-play text-main"></span>',
            data() {
              return {
                data: {}
              }
            },
            computed: {
              display() {
                return parseInt(this.data.index) === this.$parent.getSelectedRowIndexes()[0] ? 'block' : 'none'
              }
            }
          }
        }
      },
      filterBarTemplate: {
        create: function (args) {

          let div = document.createElement('div')
          div.classList.add('input-icon-right')

          let input = document.createElement('input')
          input.classList.add('form-control')
          input.id = args.column.foreignKeyField

          let span = document.createElement('span')
          span.classList.add('icon', 'nc-icon-outline', 'ui-1_zoom')

          div.append(input)
          div.append(span)

          return div
        },
        write: function (args) {

          args.element.querySelector('input').addEventListener('input', (args) => {

            let value = args.currentTarget.value

            if (!value) {
              this.parent.removeFilteredColsByField(args.currentTarget.id);
            } else {
              this.parent.filterByColumn(args.currentTarget.id, 'contains', value)
            }
          })
        }
      },
      headerTemplateChonNhieuSC: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllSC" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {}
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              }
            }
          }
        }
      },
      columnTemplateChonNhieuSC: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.scChecked"
                                    :value="data.SUCO_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: {}
              }
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              },
            }
          }
        }
      },
      commandChucNang: [
        {
          type: "SmsTT",
          title: "Gửi tin nhắn SMS",
          buttonOption: {
            content: "sms",
            cssClass: "btn btn-second pad3 lh14 button-not-bg",
          },
        },
        {
          type: "SuaTT",
          title: "Sửa tiến trình",
          buttonOption: {
            cssClass: "btn btn-second pad3 lh14 button-not-bg fa fa-ellipsis-h",
          },
        },
        {
          type: "XoaTT",
          title: "Xóa tiến trình",
          buttonOption: {
            cssClass: "btn btn-second pad3 lh14 button-not-bg fa fa-close",
          },
        },
      ],
    }
  },
  validations: {
    tienTrinhSuCo: {
      required
    }
  },
  created: async function () {
    this.showGridSC = true
    this.showGridTT = true
    try {
      this.loading(true)
      //reset donviSelected
      this.donviSelected = null
      //get dsDonVi
      this.loadDsDonVi()
      //get DsNguonSuCo, TrangThaiSuCo
      await this.getDsNguonSuCo()
      await this.getDsTrangThaiSuCo()
    } catch (e) {
    } finally {
      this.loading(false)
    }
  },
  destroyed() {
    this.showGridSC = false
    this.showGridTT = false
  },
  methods: {
    btnLayTT: async function () {
      try {
        this.loading(true)
        let objectSearch = new Object()
        objectSearch.maSuCo = this.maSuCoInput ? this.maSuCoInput : ""
        objectSearch.donViId = this.donviSelected
        objectSearch.trangThaiSuCoID = this.cboTrangThaiSuCo
        objectSearch.nguonSuCoId = this.cboNguonSuCo
        objectSearch.tuNgayYC = this.cboNgayBHTu && this.allowBHDate ? this.convertDate(this.cboNgayBHTu) : null
        objectSearch.denNgayYC = this.cboNgayBHDen && this.allowBHDate ? this.convertDate(this.cboNgayBHDen) : null
        objectSearch.tuNgayHT = this.cboNgayNTTu && this.allowNTDate ? this.convertDate(this.cboNgayNTTu) : null
        objectSearch.denNgayHT = this.cboNgayNTDen && this.allowNTDate ? this.convertDate(this.cboNgayNTDen) : null
        if (this.validateData(objectSearch)) return
        await this.getDsSuCo(objectSearch)
      } catch (e) {
        if (e) {
          console.log(e)
          this.$root.toastError(e)
        } else {
          this.$root.toastError("Có lỗi xảy ra khi tải danh sách sự cố")
        }
      } finally {
        this.loading(false)
      }
    },
    btnXuatFile: function () {
      try {
        this.loading(true);
        let dsXuatExcel = []
        if (this.dsSuCo.length > 0) {
          dsXuatExcel = [...this.dsSuCo]
          dsXuatExcel = dsXuatExcel.map(item => ({
            "Mã sự cố": item.MA_SC,
            "Mức độ": item.MUCDO_SC,
            "Nội dung sự cố": item.NOIDUNG_SC,
            "Đơn vị yêu cầu": item.TEN_DV_YC,
            "Loại thiết bị": item.LOAI_TBI,
            "Ngày sự cố": item.NGAY_SC,
            "Kéo dài": item.THOIGIAN,
            "Ngày báo": item.NGAY_TB,
            "Người báo": item.NHANVIEN_TB,
            "Ngày tiếp nhận": item.NGAY_TN,
            "Người tiếp nhận": item.NHANVIEN_TN,
            "Ngày nghiệm thu": item.NGAY_NT,
            "Người nghiệm thu": item.NHANVIEN_NT,
            "Ghi chú xử lý": item.GHICHU_XL,
            "Ghi chú thực hiện": item.GHICHU_TH,
            "Nguồn sự cố": item.NGUON_SC,
            "Nhóm": item.NHOM_SC,
            "Nguyên nhân": item.GHICHU_HONG,
            "Hẹn sửa từ": item.HENSUATU,
            "Hẹn sửa đến": item.HENSUADEN,
            "Đơn vị nhận": item.DONVI_NHAN,
            "Trạng thái sự cố": item.TRANGTHAI_SC,
            "Mã CSHT": item.MA_CSHT,
          }))
        } else {
          // this.$root.toastError("Danh sách không có dữ liệu để xuất!")
          dsXuatExcel = [{
            "Mã sự cố": null,
            "Mức độ": null,
            "Nội dung sự cố": null,
            "Đơn vị yêu cầu": null,
            "Loại thiết bị": null,
            "Ngày sự cố": null,
            "Kéo dài": null,
            "Ngày báo": null,
            "Người báo": null,
            "Ngày tiếp nhận": null,
            "Người tiếp nhận": null,
            "Ngày nghiệm thu": null,
            "Người nghiệm thu": null,
            "Ghi chú xử lý": null,
            "Ghi chú thực hiện": null,
            "Nguồn sự cố": null,
            "Nhóm": null,
            "Nguyên nhân": null,
            "Hẹn sửa từ": null,
            "Hẹn sửa đến": null,
            "Đơn vị nhận": null,
            "Trạng thái sự cố": null,
            "Mã CSHT": null,
          }]
        }
        let ds = XLSX.utils.json_to_sheet(dsXuatExcel);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, ds, "DS_SUCO"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "Tiến trình xử lý sự cố_" + this.convertDate(new Date()) + ".xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    async loadDsDonVi() {
      try {
        this.loading(true)
        await this.getDsDonVi()
        let donViTree = this.createTreeDonVi(this.dsDonVi)
        this.fields = { dataSource: donViTree, id: 'DONVI_ID', text: 'TEN_DV', parentID: 'DONVI_CHA_ID', hasChildren: 'hasChild', tooltip: 'tooltip' }
      } catch (e) {
        this.$root.toastError(e)
      } finally {
        this.loading(false)
      }
    },
    napLaiDsDonVi() {
      this.loadDsDonVi()
    },
    napLaiDsSuCo() {
      this.btnLayTT()
    },
    napLaiDsTienTrinh() {
      if (this.suCoSelected) {
        this.loadDsTienTrinh(this.suCoSelected)
      } else {
        this.$root.toastError("Chưa chọn sự cố")
      }
    },
    btnNhapMoiTT() {
      try {
        this.loading(true)
        if (this.suCoSelected == null) {
          this.$root.toastError("Bạn chưa chọn sự cố")
          return
        }
        this.clearTTObject()
        this.TTObject.SUCO_ID = this.suCoSelected.SUCO_ID
        this.$bvModal.show('popupThemTienTrinh')
      } catch (e) {
        this.$root.toastError(e)
      } finally {
        this.loading(false)
      }
    },
    // napLaiDsTienTrinh(){
    //   this.loadDsTienTrinh()
    // },
    onDataBoundSC() {
      this.scChecked = this.dsSuCo.filter(item => item.CHON === "s1").map(item => item.SUCO_ID)

      let grid = this.$refs['gridSuCo']

      if (grid.getDataRows().length > 0) {
        grid.selectRow(0)
      } else {
        let emptyRows = grid.$el.querySelectorAll('.e-emptyrow')

        if (!emptyRows || emptyRows.length < 2) {
          return
        }

        let cell0 = emptyRows[0].cells[0]
        let cell1 = emptyRows[1].cells[0]
        cell0.style.color = 'white'
        cell1.innerHTML = cell0.innerHTML
        cell0.innerHTML = 'x'
        cell1.setAttribute('colspan', 12)
        cell1.style.setProperty('text-align', 'center', 'important')
      }
    },
    // dsSuCo_selectedRowChanged(dataItem){
    //   this.isDisabledNhapMoi = true
    //   this.suCoSelected = dataItem
    //   if (dataItem) this.loadDsTienTrinh(dataItem)
    // },
    async SCrow_selected(args) {
      // this.$refs['gridSuCo'].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
      //   item.style.display = args.rowIndex === parseInt(item.id) ? 'block' : 'none'
      // })
      this.isDisabledNhapMoi = true
      this.suCoSelected = args.data
      if (this.suCoSelected) {
        try {
          this.loading(true);
          await this.loadDsTienTrinh(this.suCoSelected);
          await this.layDsTienTrinhPhieu(this.suCoSelected.SUCO_ID);
        } catch (err) {
          console.log(err);
        } finally {
          this.loading(false);
        }
      }
    },
    async loadDsTienTrinh(dataItem) {
      try {
        this.loading(true)
        if (dataItem) {
          this.ghiChuText = dataItem.GHICHU_HONG
          await this.getDsTienTrinh(dataItem.SUCO_ID)
        }
      } catch (e) {
        this.$root.toastError("Có lỗi xảy ra khi load danh sách tiến trình")
      } finally {
        this.loading(false)
      }

    },
    //handle btn chuc nang grid tien trinh
    commandTTClick: async function (args) {
      //kiem tra nguoi dung co quyen thao tac nay
      try {
        this.loading(true)
        await this.$root.context.get("/web-suco/TienTrinhSuCo/KiemTraTienTrinh", { tienTrinhId: args.rowData.TIENTRINHSC_ID })
          .then(res => {
            if (res.error === "200") {
              if (!res.data.result) {
                this.$root.toastError("Bạn không thể thao tác trên tiến trình của người khác")
                return
              } else {
                if (args.commandColumn.type == "SmsTT") {
                  //gửi tin sms cho thuê bao
                  this.TTObject.TIENTRINHSC_ID = args.rowData.TIENTRINHSC_ID
                  this.TTObject.SUCO_ID = args.rowData.SUCO_ID
                  this.TTObject.MA_SC = this.suCoSelected.MA_SC

                  this.$bvModal.show('xxx')

                  //cho giao dien
                } else if (args.commandColumn.type == "SuaTT") {
                  this.clearTTObject()
                  this.TTObject.TIENTRINHSC_ID = args.rowData.TIENTRINHSC_ID
                  this.TTObject.NOIDUNG = args.rowData.NOIDUNG
                  this.$bvModal.show('popupThemTienTrinh')
                } else if (args.commandColumn.type == "XoaTT") {
                  this.$bvModal.msgBoxConfirm("Bạn có thực sự muốn xóa tiến trình được chọn không ?",
                    {
                      title: "Xác nhận hành động",
                      centered: true,
                      size: "sm"
                    }).then((value) => {
                      if (value) {
                        this.$root.context.post("/web-suco/TienTrinhSuCo/XoaTienTrinh", { tienTrinhSCId: args.rowData.TIENTRINHSC_ID })
                          .then(res => {
                            if (res.error === "200" && res.data.result === true) {
                              this.$root.toastSuccess("Xóa tiến trình thành công")
                              this.napLaiDsTienTrinh()
                            }
                          }).catch(e => {
                            this.$root.toastError(e.response.data.message_detail)
                          })
                      }
                      else {
                        return
                      }
                    })
                }
              }
            }
          }).catch(e => { this.$root.toastError("Có lỗi xảy ra khi kiểm tra quyền thao tác người dùng") })
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    //event grid tien trinh row selected
    TTrow_selected: function (args) {
      this.$refs['gridTT'].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
        item.style.display = args.rowIndex == parseInt(item.id) ? 'block' : 'none'
      })
      // this.tuyencot_id = args.data.TUYENCOT_ID
      // this.isClose=true;
      // this.getGianDoCot();
    },
    //event click donvi
    nodeClicked(args) {
      this.donviSelected = parseInt(args.node.dataset.uid)

    },
    getDsDonVi: async function () {
      await this.$root.context.get(
        "/web-suco/danhmuc/donvi-theo-nhanvien"
      ).then(res => {
        this.dsDonVi = res.data
      }).catch(e => { throw e.response.data.message_detail })
    },
    getDsTrangThaiSuCo: async function () {
      await this.$root.context.get(
        "/web-suco/danhmuc/trangthai-suco"
      ).then(res => {
        this.dsTrangThaiSuCo = res.data
        this.cboTrangThaiSuCo = res.data[0].TTSC_ID
      }).catch(e => {
        console.log(e)
        throw e.response.data.message_detail
      })
    },
    getDsNguonSuCo: async function () {
      await this.$root.context.get(
        "/web-suco/danhmuc/nguon-suco"
      ).then(res => {
        this.dsNguonSuCo = res.data
        this.cboNguonSuCo = res.data[0].NGUONSC_ID
      }).catch(e => {
        console.log(e)
        throw e.response.data.message_detail
      })
    },
    getDsSuCo: async function (timSuCo) {
      await this.$root.context.post(
        "/web-suco/TienTrinhSuCo/DanhSachSuCo", timSuCo
      ).then(res => {
        this.dsSuCo = res.data

      }).catch(e => {
        console.log("Lỗi ds sự có")
        throw e.response.data.message_detail
      })
    },
    getDsTienTrinh: async function (suCoId) {
      await this.$root.context.get(
        "/web-suco/TienTrinhSuCo/DanhSachTienTrinh", { suCoId: suCoId }
      ).then(res => {
        this.dsTienTrinh = res.data
      }).catch(e => {
        console.log("Lỗi ds tiến trình")
        throw e.response.data.message_detail
      })
    },
    createTreeDonVi(dsDonVi) {
      let tree = []
      let donviChaArray = dsDonVi.map(item => (item['DONVI_CHA_ID']))
      donviChaArray = new Set(donviChaArray)
      donviChaArray = Array.from(donviChaArray).sort(function (a, b) {
        return a - b
      })
      let donviArray = dsDonVi.map(item => (item['DONVI_ID'])).sort(function (a, b) {
        return a - b
      })
      dsDonVi.forEach(item => {
        //case parent node
        if (donviChaArray.includes(item['DONVI_ID'])) {
          //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id (case both child, parent)
          if (!donviArray.includes(item['DONVI_CHA_ID'])) {
            delete item.DONVI_CHA_ID // only parent
          }
          tree.push({ ...item, hasChild: true, expanded: true, tooltip: item.TEN_DV })
        } else {
          // node is only child
          if (item['DONVI_CHA_ID'] != null && donviArray.includes(item['DONVI_CHA_ID'])) tree.push(item)
          else {
            // case both child, parent
            if (!donviArray.includes(item['DONVI_CHA_ID'])) {
              delete item.DONVI_CHA_ID
            }
            tree.push({ ...item, hasChild: true, expanded: true, tooltip: item.TEN_DV }) // DONVI_CHA_ID = null, -> item is parent node
          }
        }
      })
      return tree
    },
    convertDate: function (date) {
      // return new  Date(date).toLocaleDateString("en-US", { year: 'numeric' })+ "-"+
      //   new Date(date).toLocaleDateString("en-US", { month: 'numeric' })+
      //   "-" + new  Date(date).toLocaleDateString("en-US", { day: 'numeric' })
      var date = new Date(date)
      var dd = String(date.getDate()).padStart(2, '0')
      var mm = String(date.getMonth() + 1).padStart(2, '0') //January is 0!
      var yyyy = date.getFullYear()
      return date = yyyy + '-' + mm + '-' + dd
    },
    validateData(data) {
      this.$v.$touch();
      let errorMessage = []
      if (data.donViId == null) errorMessage.push("Bạn chưa chọn đơn vị")
      if (data.nguonSuCoId == null) errorMessage.push("Bạn chưa chọn nguồn sự cố")
      if (data.trangThaiSuCoID == null) errorMessage.push("Bạn chưa chọn trạng thái sự cố")
      if (data.tuNgayYC == null && this.allowBHDate === true) errorMessage.push("Bạn chưa chọn Từ ngày yêu cầu")
      if (data.denNgayYC == null && this.allowBHDate === true) errorMessage.push("Bạn chưa chọn Đến ngày yêu cầu")
      if (data.tuNgayHT == null && this.allowNTDate === true) errorMessage.push("Bạn chưa chọn Đến ngày yêu cầu")
      if (data.denNgayHT == null && this.allowNTDate === true) errorMessage.push("Bạn chưa chọn Đến ngày yêu cầu")
      if (errorMessage.length > 0) {
        this.$root.toastError(errorMessage.join('\n'))
        return true
      } else {
        return false
      }
    },
    clearTTObject() {
      this.TTObject.DONVI_CN = null
      this.TTObject.DONVI_ID = null
      this.TTObject.IP_CN = null
      this.TTObject.MAY_CN = null
      this.TTObject.NGAY_CN = null
      this.TTObject.NHANVIEN_ID = null
      this.TTObject.NOIDUNG = null
      this.TTObject.PHANVUNG_ID = null
      this.TTObject.SUCO_ID = null
      this.TTObject.TIENTRINHSC_ID = null
    },
    queryCellInfoEventTT: function (args) {
      if (!!args.column.field) {
        args.cell.title = args.cell.innerHTML
      }
    },
    queryCellInfoEventSC: function (args) {
      if (!!args.column.field) {
        args.cell.style.setProperty('background-color', `#${args.data.MAUNEN}`, 'important')
        args.cell.style.color = `#${args.data.MAUCHU}`
        args.cell.title = args.cell.innerHTML
      }
    },
    onClickBtnExcelDsTienTrinhPhieu() {

      if (!this.dsTienTrinhPhieu || this.dsTienTrinhPhieu.length == 0) {
        this.$toast.error('Không có dữ liệu để xuất')
        return
      }

      let dsHuongGiao = this.dsTienTrinhPhieu.map(item => item.HUONGGIAO)
      dsHuongGiao = [...new Set(dsHuongGiao)]

      let exportData = []

      dsHuongGiao.forEach(element => {
        exportData.push({
          'Hướng giao': element,
          'Đơn vị giao': null,
          'Ngày giao': null,
          'Nhân viên giao': null,
          'Nội dung giao': null,
          'Đơn vị nhận': null,
          'Ngày trả': null,
          'Nội dung trả': null,
          'Ngày TH': null,
          'Nhân viên TH': null,
          'Ghi chú TH': null,
          'Trạng thái phiếu': null,
        })

        this.dsTienTrinhPhieu.forEach(item => {
          if (item.HUONGGIAO === element) {
            exportData.push({
              'Hướng giao': '',
              'Đơn vị giao': item.DV_GIAO,
              'Ngày giao': item.NGAYGIAO_F,
              'Nhân viên giao': item.NV_GIAO,
              'Nội dung giao': item.ND_GIAO,
              'Đơn vị nhận': item.DV_NHAN,
              'Ngày trả': item.NGAY_TRA_F,
              'Nội dung trả': item.ND_TRA,
              'Ngày TH': item.NGAY_TH_F,
              'Nhân viên TH': item.NV_TH,
              'Ghi chú TH': item.GHICHU_TH,
              'Trạng thái phiếu': item.TRANGTHAI_PH
            })
          }
        })
      })

      let worksheet = XLSX.utils.json_to_sheet(exportData)
      let workbook = XLSX.utils.book_new()
      XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
      XLSX.writeFile(workbook, "ds_tientrinh.xlsx")
    },
    async thucHienLayDsTienTrinhPhieu() {
      if (!this.suCoSelected) {
        return
      }
      try {
        this.loading(true)
        await this.layDsTienTrinhPhieu(this.suCoSelected.SUCO_ID)
      } catch (err) {

      } finally {
        this.loading(false)
      }
    },
    async layDsTienTrinhPhieu(suCoId) {
      try {
        let response = await this.$root.context.get(`/web-suco/tracuu/suco?suco_id=${suCoId}`)

        // console.log("51243", response);
        if (response.data != null) {
          this.dsTienTrinhPhieu = response.data;
          // console.log("123", this.dsTienTrinhPhieu );
        } else {
          this.dsTienTrinhPhieu = []
        }
      } catch (err) {
        this.dsTienTrinhPhieu = []
      }
    },
  },
  computed: {
    isDisabled: function () {
      return !this.isDisabledNhapMoi;
    }
  },
  watch: {
    "checkBoxBH": function (newBH) {
      if (newBH)
        this.allowBHDate = true
      else this.allowBHDate = false
    },
    "checkBoxNT": function (newNT) {
      if (newNT)
        this.allowNTDate = true
      else this.allowNTDate = false
    },
    "checkAllSC": function (value) {
      if (value) {
        this.scChecked = this.dsSuCo.map(item => item.SUCO_ID)
        return
      }
      if (this.scChecked.length > 0 && this.scChecked.length === this.dsSuCo.length) {
        this.scChecked = []
      }
    },
    "scChecked": function (value) {
      this.checkAllSC = value.length > 0 && value.length === this.dsSuCo.length
    },
    cboTrangThaiSuCo: function (value) {
      if (value) {
        const DA_NGHIEM_THU = 6
        if (value === DA_NGHIEM_THU) {
          this.isDisabledNhapMoi = true
        } else {
          this.isDisabledNhapMoi = false
        }
      }
    }
  },
  async mounted() {
    try {
      this.$store.dispatch('setHeader', {
        title: 'TIẾN TRÌNH XỬ LÝ Sự Cố mạng viễn thông - Công nghệ thông tin'
      })
    } catch (e) {
      console.log(e)
    }
  }
}
</script>
<style>
.tientrinh-xuly-suco .e-control.e-numerictextbox.e-lib.e-input {
  text-align: right !important;
  padding-right: 5px !important;
}

.tientrinh-xuly-suco .e-grid {
  font-family: "Roboto", Arial !important;
}

.tientrinh-xuly-suco .e-grid .e-filterbarcell {
  padding: 5px 7px !important;
}

.tientrinh-xuly-suco .e-grid th.e-grouptopleftcell,
.tientrinh-xuly-suco .e-grid th.e-headercell,
.tientrinh-xuly-suco .e-grid th.e-filterbarcell {
  background-color: #BAE7FF !important;
  border-color: white !important;
}

.tientrinh-xuly-suco .e-grid th.e-filterbarcell {
  border-width: 1px 0 0 1px !important;
}

.tientrinh-xuly-suco .e-grid td.e-active {
  background: #F9E1A9 !important;
}

.tientrinh-xuly-suco .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.tientrinh-xuly-suco .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.tientrinh-xuly-suco .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.tientrinh-xuly-suco .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.tientrinh-xuly-suco .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
  background-color: #FFF9EB !important;
}

.tientrinh-xuly-suco .e-grid td.e-rowcell {
  line-height: 20px !important;
  padding: 5px 7px !important;
}

.tientrinh-xuly-suco .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
  border: 0 !important;
}

.tientrinh-xuly-suco .e-grid .e-filtertext.e-input.e-disable {
  display: none !important;
}

.tientrinh-xuly-suco .e-grid tr.e-emptyrow td {
  text-align: center !important;
}

.tientrinh-xuly-suco .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
.tientrinh-xuly-suco .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"]>div,
.tientrinh-xuly-suco .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="2"],
.tientrinh-xuly-suco .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="2"]>div,
.tientrinh-xuly-suco .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="3"],
.tientrinh-xuly-suco .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="3"]>div {
  padding: 0 !important;
}

.info-row .value.calender-tientrinhsuco {
  display: flex;
  align-items: center;
  flex-wrap: nowrap;
}

.calender-tientrinhsuco .value {
  display: flex;
  align-items: center;
  flex-wrap: nowrap;
}

.calender-tientrinhsuco .tille-denngay {
  padding: 0px 5px;
  display: block;
  width: max-content;
}

.calender-tientrinhsuco .tille-denngay .key {
  display: block;
  width: max-content;
  text-align: center;
}

a.refresh.link {
    position: absolute;
    right: 2%;
    bottom: 90%;
}

#tabPhieu, #tabTienTrinh {
    position: relative;
}

.tab-content {
    height: 365px;
}

</style>
