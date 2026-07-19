<template src="./NhanPhieu3.html"></template>
<style src="./NhanPhieu3.scss" scoped></style>

<script>

import Vue from 'vue'

import MultiSelectDemo from "../NhanPhieu/components/multi-select";
import xlsx from "xlsx";
import moment from "moment";
import NhanPhieuAPI from '../api/NhanPhieu3.js'
import GRID_COLUMS from "./constant";
import ModalExport from "../NhanPhieu/components/modal-export";
import NhatKy from "../NhanPhieu/components/nhat-ky";
import KyCuoc from '@/components/KyCuoc2'
import { Query } from '@syncfusion/ej2-data'

import { MultiSelectPlugin, CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
import { CheckBoxPlugin } from "@syncfusion/ej2-vue-buttons"
import { currency } from '@/filters/currency'

Vue.use(MultiSelectPlugin)
Vue.use(CheckBoxPlugin)

export default {
  components: {
    appKyCuoc: KyCuoc,
    "multi-select-demo": MultiSelectDemo,
    ModalExport,
    NhatKy,
  },
  provide: {
    multiselect: [CheckBoxSelection]
  },
  data: function () {
    return {
      GRID_COLUMS: GRID_COLUMS,
      configs: {
        nvtc: {
          fields: { text: 'TEN_HIENTHI', value: 'NHANVIEN_ID' },
          mode: 'CheckBox',
          showSelectAll: true,
          showDropDownIcon: true,
          filterBarPlaceholder: 'Tìm kiếm nhanh',
          allowFiltering: true
        },
        selectionSettings: { checkboxOnly: true, type: 'Multiple' },
      },
      sumTemplate: function () {
        return {
          template: Vue.component('sumTemplate', {
            template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
            data: function () { return { data: { data: {} } }; }
          })
        }
      },
      countSelectedRows: function () {
        return {
          template: Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" :value="data.Custom" :disabled="true" style="font-weight:bold" />`,
            data() { return { data: { data: {} } }; }
          })
        }
      },
      sumSelectedRows: function () {
        return {
          template: Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="font-weight:bold" />`,
            data() { return { data: { data: {} } }; }
          })
        }
      },
      passParam: {
        maTT: "",
        kyHoaDon: "",
        trangThaiHDId: "",
      },
      countSelectedRowsData: {
        1: 0,
        2: 0,
        3: 0,
        4: 0,
        5: 0,
        6: 0,
        7: 0,
        8: 0,
      },
      columnAggs: {
        1: ['TIEN', 'TIEN_HD', 'TIENNO_QL'],
        2: ['TIEN', 'TIEN_HD', 'TIENNO_QL'],
        3: ['TIEN', 'TIEN_HD', 'TIENNO_QL'],
        4: ['TIEN', 'TIEN_HD', 'TIENNO_QL'],
        5: ['TIEN', 'TIEN_HD', 'TIENNO_QL'],
        6: ['TIENTRA'],
        7: ['TIEN', 'TIEN_HD', 'NO_PS', 'NO_PS_2', 'NO_PS_3', 'NO_PS_4', 'NO_DK'],
        8: ['TIEN', 'TIEN_HD', 'NO_PS', 'NO_PS_2', 'NO_PS_3', 'NO_PS_4', 'NO_DK', 'TIENNO_QL'],
      },
      sumRows: {
        1: {
          TIEN: {
            selected: 0,
            total: 0
          },
          TIEN_HD: {
            selected: 0,
            total: 0
          },
          TIENNO_QL: {
            selected: 0,
            total: 0
          }
        },
        2: {
          TIEN: {
            selected: 0,
            total: 0
          },
          TIEN_HD: {
            selected: 0,
            total: 0
          },
          TIENNO_QL: {
            selected: 0,
            total: 0
          }
        },
        3: {
          TIEN: {
            selected: 0,
            total: 0
          },
          TIEN_HD: {
            selected: 0,
            total: 0
          },
          TIENNO_QL: {
            selected: 0,
            total: 0
          }
        },
        4: {
          TIEN: {
            selected: 0,
            total: 0
          },
          TIEN_HD: {
            selected: 0,
            total: 0
          },
          TIENNO_QL: {
            selected: 0,
            total: 0
          }
        },
        5: {
          TIEN: {
            selected: 0,
            total: 0
          },
          TIEN_HD: {
            selected: 0,
            total: 0
          },
          TIENNO_QL: {
            selected: 0,
            total: 0
          }
        },
        6: {
          TIEN: {
            selected: 0,
            total: 0
          },
        },
        7: {
          TIEN: {
            selected: 0,
            total: 0
          },
          TIEN_HD: {
            selected: 0,
            total: 0
          },
          NO_PS: {
            selected: 0,
            total: 0
          },
          NO_PS_2: {
            selected: 0,
            total: 0
          },
          NO_PS_3: {
            selected: 0,
            total: 0
          },
          NO_PS_4: {
            selected: 0,
            total: 0
          },
          NO_DK: {
            selected: 0,
            total: 0
          },
        },
        8: {
          TIEN: {
            selected: 0,
            total: 0
          },
          TIEN_HD: {
            selected: 0,
            total: 0
          },
          NO_PS: {
            selected: 0,
            total: 0
          },
          NO_PS_2: {
            selected: 0,
            total: 0
          },
          NO_PS_3: {
            selected: 0,
            total: 0
          },
          NO_PS_4: {
            selected: 0,
            total: 0
          },
          NO_DK: {
            selected: 0,
            total: 0
          },
          TIENNO_QL: {
            selected: 0,
            total: 0
          }
        },
      },
      /*
      sumRows :{
        1: {
          selected: 0,
          total: 0
        },
        2: {
          selected: 0,
          total: 0
        },
        3: {
          selected: 0,
          total: 0
        },
        4: {
          selected: 0,
          total: 0
        },
        5: {
          selected: 0,
          total: 0
        },
        6: {
          selected: 0,
          total: 0
        },
        7: {
          selected: 0,
          total: 0
        },
        8: {
          selected: 0,
          total: 0
        },
        
      },
      */
      tabData: {
        1: [],
        2: [],
        3: [],
        4: [],
        5: [],
        6: [],
        7: [],
        8: []
      },
      tabPageInfo: {
        1: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        2: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        3: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        4: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        5: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        6: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        7: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
        8: {
          pageIndex: 0,
          pageSize: 10,
          totalElement: 0
        },
      },
      tabKey: {
        1: 0,
        2: 0,
        3: 0,
        4: 0,
        5: 0,
        6: 0,
        7: 0,
        8: 0,
      },
      dataExport: [],
      columnsExport: [],
      dsLoaiPhieu: [],
      dsLyDoTra: [],
      dsLuotGiao: [],
      dsNVTC: [
        {
          NHANVIEN_ID: "3789",
          MA_NV: "ABI3",
          TEN_NV: "Vũ Thị Sơn",
          TEN_HIENTHI: "ABI3 - Vũ Thị Sơn",
          TIEN_DC: "0",
          DONVI_ID: "1004",
        },
      ],
      dsPhieuChuaThu: [],
      dsTraHoaDon: [],
      dsTraTronBo: [],
      dsNVTCId: [],
      searchForm: {
        searchType: "1",
        ckbNgayHenTra: false,
        ckbNgayNhanGiayBao: false,
        ngayHenTra: new Date(),
        ngayNhanGiayBao: new Date(),
        idLoaiPhieu: "",
        idLuotGiao: "",
        idNVTC: "",
        txtTimKiem: "",
        p_loai_chuyen: "0"
      },
      updateForm: {
        ngayDen: new Date(),
        idLyDo: "",
        ghiChu: "",
      },
      indexFocus: 0,
      waterMark: "Chọn ngày",
      dateFormat: "dd/MM/yyyy",
      showTable: true,
      hideNVTC: true,
      DS_NHANPHIEU_THEO_AM: -1,
      currentTab: 1,
      maTTChosen: [],
      files: [],
      dsLoiExcel: [],
      rowChosen: null,
      kyHoaDon: null, //20210301//,
      showSearch: true,
    };
  },
  validations: {},
  watch: {},
  created: async function () {
    this.dsLoaiPhieuDropDown();
    this.dsLyDoDropDown();
    this.getTSMD();
    this.getDsNVTC();
    // console.log('ndid', this.$root.token.getNguoiDungID());
    // console.log(this.$auth.getUserName())
  },
  async mounted() { },
  async updated() { },
  methods: {

    countSelectedRowsAgg() {
      return currency(this.countSelectedRowsData[this.currentTab]) + '/' + currency(this.tabPageInfo[this.currentTab].totalElement)
    },
    sumTienAgg() {
      return currency(this.sumRows[this.currentTab]['TIEN'].selected) + '/' + currency(this.sumRows[this.currentTab]['TIEN'].total)
    },
    sumTienHDAgg() {
      return currency(this.sumRows[this.currentTab]['TIEN_HD'].selected) + '/' + currency(this.sumRows[this.currentTab]['TIEN_HD'].total)
    },
    sumTienNoQLAgg() {
      if (this.sumRows[this.currentTab]['TIENNO_QL'] !== undefined) {
        return currency(this.sumRows[this.currentTab]['TIENNO_QL'].selected) + '/' + currency(this.sumRows[this.currentTab]['TIENNO_QL'].total)
      }
    },
    sumTienPSAgg() {
      return currency(this.sumRows[this.currentTab]['NO_PS'].selected) + '/' + currency(this.sumRows[this.currentTab]['NO_PS'].total)
    },
    sumTienPS2Agg() {
      return currency(this.sumRows[this.currentTab]['NO_PS_2'].selected) + '/' + currency(this.sumRows[this.currentTab]['NO_PS_2'].total)
    },
    sumTienPS3Agg() {
      return currency(this.sumRows[this.currentTab]['NO_PS_3'].selected) + '/' + currency(this.sumRows[this.currentTab]['NO_PS_3'].total)
    },
    sumTienPS4Agg() {
      return currency(this.sumRows[this.currentTab]['NO_PS_4'].selected) + '/' + currency(this.sumRows[this.currentTab]['NO_PS_4'].total)
    },
    sumTienNoDKAgg() {
      return currency(this.sumRows[this.currentTab]['NO_DK'].selected) + '/' + currency(this.sumRows[this.currentTab]['NO_DK'].total)
    },
    onSelectRowCheckbox(value) {
      if (value.length > 0) {
        this.searchForm.idLuotGiao = value.join();
      } else {
        this.searchForm.idLuotGiao = "";
      }
      //this.$refs.table.rowClick(1, null);
    },
    onSelectRowGrid(value) {
      this.rowChosen = value;
      /*
      let selectedRows = this.$refs['grid' + this.currentTab].getSelectedRecords()
      console.log(selectedRows)

      this.countSelectedRowsData[this.currentTab] = selectedRows.length

      this.sumRows[this.currentTab].selected = selectedRows.reduce((acc, curr) => {
        return acc = Number(curr.TIEN) + acc
      }, 0)
      this.$refs['grid' + this.currentTab].$refs.grid.ej2Instances.aggregateModule.refresh()
      */
    },
    onRowSelected(args) {
      this.refreshAgg(args)
    },
    onRowDeselected(args) {
      this.refreshAgg(args)
    },
    refreshAgg(args) {
      let selectedRows = this.$refs['grid' + this.currentTab].getSelectedRecords()
      //      console.log(selectedRows)

      this.countSelectedRowsData[this.currentTab] = selectedRows.length

      this.columnAggs[this.currentTab].forEach(x => {
        this.sumRows[this.currentTab][x].selected = selectedRows.reduce((acc, curr) => {
          return acc = Number(curr[x]) + acc
        }, 0)
      })


      this.$refs['grid' + this.currentTab].$refs.grid.ej2Instances.aggregateModule.refresh()
      /*
      let selectedRows = this.$refs.gridDSPhieuTT.getSelectedRecords()

      if(selectedRows.length > 0)
        this.selectedTotal = selectedRows.reduce((acc, curr) => {
          return acc = Number(curr.TIEN_TRA) + acc
        }, 0)
      else
        this.selectedTotal = 0
      this.$refs.gridDSPhieuTT.$refs.grid.ej2Instances.aggregateModule.refresh()
      */
    },
    onChangeLuotGiao() {
      this.showTable = !this.showTable;
    },
    onChangeNVTC() {
      this.hideNVTC = !this.hideNVTC;
    },
    onOk(value) {
      this.hideNVTC = true;
      const arrName = [];
      this.dsNVTC.forEach((element) => {
        if (value.includes(element.NHANVIEN_ID)) {
          arrName.push(element.TEN_HIENTHI);
        }
      });
      this.searchForm.idNVTC = arrName.join();
      this.dsNVTCId = value.length > 0 ? value.join() : "";
      this.dsLuotGiaoDropDown();
    },
    onCancel(value) {
      this.hideNVTC = true;
    },
    clearResult() {
      for (let i = 1; i <= 8; i++)
        this.tabData[i] = []
    },
    onClickDanhSach: async function () {
      this.maTTChosen = [];
      // this.updateForm.ghiChu="";
      //console.log("this.DS_NHANPHIEU_THEO_AM", this.DS_NHANPHIEU_THEO_AM);
      /*
      if (this.DS_NHANPHIEU_THEO_AM == 1) {
        await this.getDataV1();
      } else {
        if (this.searchForm.idLuotGiao) {
          await this.getDataV3();
        } else {
          await this.getDataV2();
        }
      }
      */
      if (this.DS_NHANPHIEU_THEO_AM !== 1) {
        if (this.searchForm.idLuotGiao === null) {
          this.clearResult()
          return
        }

        if (this.searchForm.idLoaiPhieu === null)
          return
      }

      this.getDataV3();

      if (this.DS_NHANPHIEU_THEO_AM === 1) {
        //await this.getDataV3();
      } else {
        if (this.searchForm.idLuotGiao === '') {

        } else {

        }
      }
    },
    switchTab(tab) {
      this.currentTab = tab
      if (tab === 7 || tab === 8) {
        if (this.tabData[tab].length > 0) return
      }
      this.onClickDanhSach();
    },
    onChangeLoaiPhieu() {
      // this.dsLyDoDropDown(); v2
      this.onClickDanhSach();
    },
    checkedGrid01(value) {
      this.maTTChosen = value;
    },
    onClickTraGiayBao() {
      /*
      if (this.validateForm()) {
        const { body, haveHoaDon } = this.makeTraGiayBaoBody(
          this.maTTChosen,
          3,
          this.tabData[this.currentTab]
        );
        if (haveHoaDon) {
          this.$toast.error(`Trả giấy báo không áp dụng cho hóa đơn !`);
        } else {
          this.traGiayBao(body);
        }
      }
      */
      const { body, haveHoaDon } = this.makeTraGiayBaoBody(
        this.maTTChosen,
        3,
        this.tabData[this.currentTab]
      );
      if (haveHoaDon) {
        this.$toast.error(`Trả giấy báo không áp dụng cho hóa đơn !`);
      } else {
        this.traGiayBao(body);
      }
    },
    onClickTraHoaDon: async function () {
      /*
      if (this.validateForm()) {
        const { body } = this.makeTraGiayBaoBody(
          this.maTTChosen,
          1,
          this.tabData[this.currentTab]
        );
        const bodySeri = this.makeHuySeriBody(
          this.maTTChosen,
          this.tabData[this.currentTab]
        );
        await this.traHoaDon(body);
        await this.huySeri(bodySeri);
      }
      */
      const { body } = this.makeTraGiayBaoBody(
        this.maTTChosen,
        1,
        this.tabData[this.currentTab]
      );
      const bodySeri = this.makeHuySeriBody(
        this.maTTChosen,
        this.tabData[this.currentTab]
      );
      await this.traHoaDon(body);
      await this.huySeri(bodySeri);

    },
    onClickHuyTraHoaDon() {
      const body = this.makeHuyTraHDBody(
        this.maTTChosen,
        3,
        this.tabData[this.currentTab]
      );
      this.huyTraHoaDon(body);
    },
    onClickHuyTraTronBo() {
      const body = this.makeHuyTraHDBody(
        this.maTTChosen,
        5,
        this.tabData[this.currentTab]
      );
      this.huyTraTronBo(body);
    },
    onClickCapNhat() {
      if (this.validateCapNhat()) {
        const body = this.makeCapNhatBody(
          this.maTTChosen,
          this.tabData[this.currentTab]
        );
        const urlObject = {
          1: "/web-thuno/api/thu-no/nhan-phieu/cap-nhat-phieu-chua-thu",
          2: "/web-thuno/api/thu-no/nhan-phieu/cap-nhat-phieu-tra-hoa-don",
          3: "/web-thuno/api/thu-no/nhan-phieu/cap-nhat-phieu-tra-tron-bo",
          4: "/web-thuno/api/thu-no/nhan-phieu/cap-nhat-phieu-tra-phieu-hen",
          5: "/web-thuno/api/thu-no/nhan-phieu/cap-nhat-phieu-tra-giay-bao",
          7: "/web-thuno/api/thu-no/nhan-phieu/cap-nhat-phieu-hoa-don-loai",
        };
        this.capNhatData(body, urlObject[this.currentTab]);
      }
    },
    onClickTraTronBo: async function () {
      /*
      if (this.validateForm()) {
        const body = this.makeTraTronBoBody(
          this.maTTChosen,
          this.tabData[this.currentTab]
        );
        await this.traTronBo(body);
        const bodySeri = this.makeHuySeriBody(
          this.maTTChosen,
          this.tabData[this.currentTab]
        );
        await this.huySeri(bodySeri);
      }
      */
      const body = this.makeTraTronBoBody(
        this.maTTChosen,
        this.tabData[this.currentTab]
      );
      await this.traTronBo(body);
      const bodySeri = this.makeHuySeriBody(
        this.maTTChosen,
        this.tabData[this.currentTab]
      );
      await this.huySeri(bodySeri);
    },
    onClickNhapExcel() {
      /*
      if (this.tabData[this.currentTab].length == 0) {
        this.$toast.error("Danh sách phiếu không được để trống!");
      } else {
        this.$refs.fileInput.click();
      }
      */
      if (this.currentTab === 1 && this.tabData[1].length <= 0)  // phiếu chưa thu
      {
        this.$toast.error("Danh sách phiếu không được để trống!");
        return
      } else if (this.currentTab === 7 && this.tabData[7].length <= 0) // hóa đơn loại
      {
        this.$toast.error("Danh sách phiếu không được để trống!");
        return
      }

      this.$refs.fileInput.click();
    },
    onClickExport() {
      this.columnsExport = this.GRID_COLUMS[this.currentTab];
      if (this.dataExport.length > 0) {
        this.$bvModal.show("modal-export");
      } else {
        this.$toast.error("Không có dữ liệu để xuất excel");
      }
    },
    conClickNhatKy() {
      if (this.tabData[this.currentTab].length > 0 && this.rowChosen) {
        this.passParam.maTT = this.rowChosen.MA_TT;
        this.passParam.kyHoaDon = this.kyHoaDon;
        this.passParam.trangThaiHDId = this.rowChosen.TRANGTHAI_HD_ID;
        // console.log(this.passParam)
        this.$bvModal.show("modal-nhatky");
      } else {
        this.$toast.error("Chưa có dữ liệu để cập nhật nhật ký");
      }
    },
    onCloseModal() {
      this.$bvModal.hide("modal-nhatky");
    },
    async onChangeFile(e) {
      this.files = e.target.files;
      if (this.files.length > 0) {
        await this.importExcel();
      }
    },
    onToggleSearchForm() {
      this.showSearch = !this.showSearch;
    },
    async importExcel() {
      this.validData = [];
      const files = this.files;
      const fileReader = new FileReader();
      fileReader.readAsBinaryString(files[0]);
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          // console.log(ws);
          if (this.currentTab == 1) {
            let res = this.checkFormatData(ws)
            if (res.length > 0) {
              this.changeDataGrid(res);
            }
          } else {
            let res = this.checkReadFile(ws)
            if (res.length > 0) {
              this.changeDataReadFile(res);
            }
          }
          document.getElementById('upload').value = null;
        } catch (e) {
          document.getElementById('upload').value = null;
          return alert("Read failure!" + e);
        }
      };
    },
    changeDataGrid(data) {
      this.tabData[this.currentTab].forEach((el) => {
        data.forEach((item) => {
          if (el.MA_TT == item.MA_TT) {
            el.GHICHU = `<input type="text" id="ghichu${item.MA_TT}" class="form-control" value="${item.GHICHU}">`;
            let opt = `<select id="combo${item.MA_TT}" class="form-control">`;
            this.dsLyDoTra.forEach((el1) => {
              if (el1.LYDOTRA_ID == item.LYDO_ID) {
                opt += `<option selected="selected" value="${el1.LYDOTRA_ID}">${el1.NOIDUNG}</option>`;
              } else {
                opt += `<option value="${el1.LYDOTRA_ID}">${el1.NOIDUNG}</option>`;
              }
            });
            opt += `</select>`;
            el.LYDOTRA_ID = opt;
          }
        });
      });
      let selected = []
      data.forEach((item) => {
        let index = this.tabData[this.currentTab].findIndex(x => x.MA_TT === item.MA_TT);
        if (index >= 0)
          selected.push(index)
      });
      if (selected.length > 0)
        this.$refs['grid' + this.currentTab].$refs.grid.selectRows(selected)
    },
    checkFormatData(data) {
      const dsLoi = []
      let rsOK = []
      if (data && data.length > 0) {
        let keys = Object.keys(data[0]);
        data.forEach((el) => {
          let key = Object.keys(el);
          keys = [...new Set(keys.concat(key))];
        });
        if (
          keys.length < 3 ||
          keys[0] != "MA_TT" ||
          keys[1] != "LYDO_ID" ||
          keys[2] != "GHICHU"
        ) {
          this.$toast.error(
            "File Excel nhập vào phải đúng định dạng!\n - Chỉ có các trường theo thứ tự MA_TT, LYDO_ID, GHICHU"
          );
          document.getElementById('upload').value = null;
          return false;
        }
        //console.log(data)

        data.forEach((el) => {
          let item = { ...el }  // clone object
          if (item.MA_TT !== undefined && item.MA_TT !== null)
            item.MA_TT = item.MA_TT.trim()
          if (item.LYDO_ID !== undefined && item.LYDO_ID !== null && typeof item.LYDO_ID === "string")
            item.LYDO_ID = item.LYDO_ID.trim()


          if (this.isExistMaTT(item.MA_TT)) {
            if (
              !item.LYDO_ID ||
              (typeof item.LYDO_ID == "string" && item.LYDO_ID.trim() == "")
            ) {
              dsLoi.push({
                MA_TT: item.MA_TT,
                LOI: "Lý do ID bị null",
              });
            } else {
              if (!this.isNumeric(item.LYDO_ID)) {
                dsLoi.push({
                  MA_TT: item.MA_TT,
                  LOI: "Lý do ID phải là kiểu số",
                });
              } else if (!this.isExistLyDoId(item.LYDO_ID)) {
                dsLoi.push({
                  MA_TT: item.MA_TT,
                  LOI: "Không tồn tại lý do ID trong dữ liệu",
                });
              } else {
                rsOK.push(item)
              }
            }
          } else {
            dsLoi.push({
              MA_TT: item.MA_TT,
              LOI: "Không có trong danh sách",
            });
          }
        });

      } else {
        this.$toast.error('Không tìm thấy dữ liệu trong file.')
        return []
      }

      if (dsLoi.length > 0) {
        this.dsLoiExcel = dsLoi;
        this.$bvModal.show("modal-error");
        document.getElementById('upload').value = null;
        return [];
      }
      return rsOK
    },
    isExistLyDoId(lyDoId) {
      return this.dsLyDoTra.some(function (el) {
        return el.LYDOTRA_ID === lyDoId;
      });
    },
    isExistMaTT(maTT) {
      return this.tabData[this.currentTab].some(function (el) {
        return el.MA_TT === maTT;
      });
    },
    isNumeric(str) {
      if (!isNaN(str)) return true;
      if (typeof str != "string") return false;
      return !isNaN(str) && !isNaN(parseFloat(str));
    },
    checkReadFile(data) {
      const dsLoi = [];
      let rsOK = []
      if (data && data.length > 0) {
        let keys = Object.keys(data[0]);
        data.forEach((el) => {
          let key = Object.keys(el);
          keys = [...new Set(keys.concat(key))];
        });
        if (keys.length < 2 || keys[0] != "MA_TT" || keys[1] != "NVQL_XL") {
          this.$toast.error(
            "File Excel nhập vào phải đúng định dạng!\n - Chỉ có các trường theo thứ tự MA_TT, NVQL_XL"
          );
          document.getElementById('upload').value = null;
          return false;
        }
        data.forEach((el) => {
          let item = { ...el }
          if (item.MA_TT !== undefined && item.MA_TT !== null)
            item.MA_TT = item.MA_TT.trim()
          if (!this.isExistMaTT(item.MA_TT)) {
            dsLoi.push({
              MA_TT: item.MA_TT,
              LOI: "Không có trong danh sách",
            });
          } else {
            rsOK.push(item)
          }
        });
      } else {
        this.$toast.error("Không tìm thấy dữ liệu từ file Excel!");
        return []
      }
      if (dsLoi.length > 0) {
        this.dsLoiExcel = dsLoi;
        this.$bvModal.show("modal-error");
        return [];
      }
      return rsOK
    },
    changeDataReadFile(data) {
      this.tabData[this.currentTab].forEach((el) => {
        data.forEach((item) => {
          if (el.MA_TT == item.MA_TT) {
            el.ND_THUCHIEN = `<input type="text" id="noidung${item.MA_TT}" class="form-control" value="${item.NVQL_XL}">`;
          }
        });
      });
      /*
      data.forEach((item) => {
        this.$refs['grid' + this.currentTab].doSelectItem(item);
      });
      */
      let selected = []
      data.forEach((item) => {
        let index = this.tabData[this.currentTab].findIndex(x => x.MA_TT === item.MA_TT);
        if (index >= 0)
          selected.push(index)
      });
      if (selected.length > 0)
        this.$refs['grid' + this.currentTab].$refs.grid.selectRows(selected)
    },
    validateForm() {
      if (this.maTTChosen.length == 0) {
        this.$toast.error(`Bạn chưa chọn phiếu !`);
        return false;
      }
      if (!this.updateForm.ghiChu.trim()) {
        this.$toast.error(`Mã ${this.maTTChosen[0]} chưa nhập ghi chú`);
        return false;
      }
      if (!this.updateForm.idLyDo) {
        this.$toast.error(`Mã ${this.maTTChosen[0]} chưa nhập lý do trả`);
        return false;
      }

      return true;
    },
    validateCapNhat() {
      if (this.maTTChosen.length == 0) {
        this.$toast.error(`Bạn chưa chọn phiếu !`);
        return false;
      }
      if (
        (this.currentTab == 2 ||
          this.currentTab == 3 ||
          this.currentTab == 5) &&
        this.searchForm.ckbNgayHenTra &&
        this.searchForm.ngayHenTra
      ) {
        this.$toast.error(
          `Bạn chỉ được quyền xóa ngày hẹn chứ không được quyền sửa ! `
        );
        return false;
      }

      if (
        this.searchForm.ckbNgayHenTra &&
        this.searchForm.ngayHenTra &&
        this.searchForm.ngayHenTra < new Date().setHours(0, 0, 0, 0)
      ) {
        this.$toast.error(`Ngày hẹn trả không được nhỏ hơn ngày hiện tại`);
        return false;
      }
      if (!this.updateForm.idLyDo) {
        this.$toast.error(`Bạn chưa nhập lý do cho hóa đơn đã chọn!`);
        return false;
      }
      return true;
    },
    makeHuyTraHDBody(dsMaTT, pkieu, tabData) {
      const body = {
        pPhanVungId: this.$root.token.getPhanVungID(),
        pKyHoaDon: this.kyHoaDon,
        pTrangThaiHDId: pkieu,
        pData: [],
      };
      tabData.forEach((item) => {
        if (dsMaTT.includes(item.MA_TT)) {
          body.pData.push({
            giaophieu_id: item.GIAOPHIEU_ID,
            thanhtoan_id: item.HOADON_ID,
          });
        }
      });
      return body;
    },
    makeHuySeriBody(dsMaTT, tabData) {
      const body = {
        //pPhanVungId: this.$root.token.getPhanVungID(),
        pMaND: this.$auth.getMaNhanVien(),
        pKyHoaDon: this.kyHoaDon,
        pMayCN: "-",
        pIPCN: "-",
        pDSCNSeri: [],
      };
      /*
      tabData.forEach((item) => {
        if (dsMaTT.includes(item.MA_TT)) {
          body.pDSCNSeri.push({
            ma_tt: item.MA_TT,
            seri: item.SERI,
            so_seri: item.SO_SERI,
            so_thung: item.SO_THUNG,
            tien_hd: item.TIEN_HD,
          });
        }
      });
      */
      let selectedRows = this.$refs['grid' + this.currentTab].getSelectedRecords()

      if (tabData.length <= 0) {
        this.$toast.error('Không có phiếu để trả.')
        return false
      }

      if (selectedRows.length <= 0) {
        this.$toast.error('Chưa chọn phiếu để trả.')
        return false
      }

      selectedRows.forEach((item) => {
        body.pDSCNSeri.push({
          ma_tt: item.MA_TT,
          seri: item.SERI,
          so_seri: item.SO_SERI,
          so_thung: item.SO_THUNG,
          tien_hd: item.TIEN_HD,
        });
      })
      return body;
    },
    makeTraGiayBaoBody(dsMaTT, pkieu, tabData) {
      const body = {
        //pPhanVungId: this.$root.token.getPhanVungID(),
        pKyHoaDon: this.kyHoaDon,
        pKieu: pkieu,
        pDSPhieu: [],
      };
      let haveHoaDon = false;
      let selectedRows = this.$refs['grid' + this.currentTab].getSelectedRecords()

      if (tabData.length <= 0) {
        this.$toast.error('Không có phiếu để trả.')
        return false
      }

      if (selectedRows.length <= 0) {
        this.$toast.error('Chưa chọn phiếu để trả.')
        return false
      }

      selectedRows.forEach((item) => {
        const liDoTbDOM = document.getElementById(`combo${item.MA_TT}`);
        const ghiChuDOM = document.getElementById(`ghichu${item.MA_TT}`);
        const liDoTb = liDoTbDOM ? liDoTbDOM.value : null;
        const ghiChu = ghiChuDOM ? ghiChuDOM.value : null;
        console.log(item)
        body.pDSPhieu.push({
          giaophieu_id: item.GIAOPHIEU_ID,
          hoadon_id: item.HOADON_ID,
          lydo_id: liDoTb,
          ghichu: ghiChu,
        });
        if (item.LOAIPHIEU_ID == 1) {
          haveHoaDon = true;
        }
      })

      /*

      tabData.forEach((item) => {
        if (dsMaTT.includes(item.MA_TT)) {
          body.pDSPhieu.push({
            giaophieu_id: item.GIAOPHIEU_ID,
            hoadon_id: item.HOADON_ID,
            lydo_id: this.updateForm.idLyDo,
            ghichu: this.updateForm.ghiChu,
          });
          if (item.LOAIPHIEU_ID == 1) {
            haveHoaDon = true;
          }
        }
      });
      */
      return { body, haveHoaDon };
    },
    makeCapNhatBody(dsMaTT, tabData) {
      const ngayDen = this.updateForm.ngayDen
        ? moment(this.updateForm.ngayDen).format("DD/MM/YYYY")
        : null;
      const pNgayNhanGB = this.searchForm.ngayNhanGiayBao
        ? moment(this.searchForm.ngayNhanGiayBao).format("DD/MM/YYYY")
        : null;
      const pNgayHen = this.searchForm.ngayHenTra
        ? moment(this.searchForm.ngayHenTra).format("DD/MM/YYYY")
        : null;

      if (this.currentTab == 1999) {
        let body = {
          pPhanVungId: this.$root.token.getPhanVungID(),
          pKyHoaDon: this.kyHoaDon,
          pNgayNhanGB: this.searchForm.ckbNgayNhanGiayBao ? pNgayNhanGB : null,
          pNgayHen: this.searchForm.ckbNgayHenTra ? pNgayHen : null,
          pNgayDen: ngayDen,
          pLyDoId: null, // this.updateForm.idLyDo,
          pGhiChu: null, //this.updateForm.ghiChu.trim(),
          pDSCapNhat: [],
          pNguoiCN: this.$auth.getUserName(),
          pMayCN: "test",
          pIPCN: "192.168.15.15",
        };
        tabData.forEach((item) => {
          if (dsMaTT.includes(item.MA_TT)) {
            const ghiChuTb = document.getElementById(
              `ghichu${item.MA_TT}`
            ).value;
            const liDoTb = document.getElementById(`combo${item.MA_TT}`).value;
            body.pDSCapNhat.push({
              hoadon_id: item.HOADON_ID,
              nd_thuchien: item.ND_THUCHIEN,
              pLyDoId: liDoTb ? liDoTb : this.updateForm.idLyDo,
              pGhiChu: ghiChuTb ? ghiChuTb : this.updateForm.ghiChu.trim(),
            });
          }
        });
        return body;
      } else if (this.currentTab == 1) {
        // let body = {
        //   pPhanVungId: this.$root.token.getPhanVungID(),
        //   pKyHoaDon: this.kyHoaDon,
        //   pNgayNhanGB: this.searchForm.ckbNgayNhanGiayBao ? pNgayNhanGB : null,
        //   pNgayHen: this.searchForm.ckbNgayHenTra ? pNgayHen : null,
        //   pNgayDen: ngayDen,
        //   pLyDoId: this.updateForm.idLyDo,
        //   pGhiChu: this.updateForm.ghiChu.trim(),
        //   pDSHoaDonId: "",
        //   pNguoiCN: this.$auth.getUserName(),
        //   pMayCN: "test",
        //   pIPCN: "192.168.15.15"
        // };
        // let hoaDonArr=[];
        // tabData.forEach(item => {
        //   if (dsMaTT.includes(item.MA_TT)) {
        //     hoaDonArr.push(item.HOADON_ID);
        //   }
        // });
        // body.pDSHoaDonId = hoaDonArr.join();
        // return body;
        let body = {
          pPhanVungId: this.$root.token.getPhanVungID(),
          pKyHoaDon: this.kyHoaDon,
          pNgayNhanGB: this.searchForm.ckbNgayNhanGiayBao ? pNgayNhanGB : null,
          pNgayHen: this.searchForm.ckbNgayHenTra ? pNgayHen : null,
          pNgayDen: ngayDen,
          pLyDoId: this.updateForm.idLyDo,
          pGhiChu: this.updateForm.ghiChu.trim(),
          pDSHoaDon: [],
          pNguoiCN: this.$auth.getUserName(),
          pMayCN: "-",
          pIPCN: "-",
        };
        /*
        tabData.forEach((item) => {
          if (dsMaTT.includes(item.MA_TT)) {
            const noiDungDOM = document.getElementById(`noidung${item.MA_TT}`);
            const liDoTbDOM = document.getElementById(`combo${item.MA_TT}`);
            const ghiChuDOM = document.getElementById(`ghichu${item.MA_TT}`);
            const noiDung = noiDungDOM ? noiDungDOM.value : "";
            const liDoTb = liDoTbDOM ? liDoTbDOM.value : null;
            const ghiChu = ghiChuDOM ? ghiChuDOM.value : null;
            body.pDSHoaDon.push({
              hoadon_id: item.HOADON_ID,
              nd_thuchien: noiDung ? noiDung.trim() : "",
              lydo_id: liDoTb,//this.updateForm.idLyDo,
              ghichu:
                this.updateForm.ghiChu.trim() != ""
                  ? this.updateForm.ghiChu.trim()
                  : ghiChu.trim(),
            });
          }
        });
        */
        let selectedRows = this.$refs.grid1.$refs.grid.getSelectedRecords()
        if (selectedRows.length <= 0) {
          this.$toast.error('Chưa chọn danh sách để cập nhật.')

        } else {
          selectedRows.forEach((item) => {
            const noiDungDOM = document.getElementById(`noidung${item.MA_TT}`);
            const liDoTbDOM = document.getElementById(`combo${item.MA_TT}`);
            const ghiChuDOM = document.getElementById(`ghichu${item.MA_TT}`);
            const noiDung = noiDungDOM ? noiDungDOM.value : "";
            const liDoTb = liDoTbDOM ? liDoTbDOM.value : null;
            const ghiChu = ghiChuDOM ? ghiChuDOM.value : null;
            body.pDSHoaDon.push({
              hoadon_id: item.HOADON_ID,
              nd_thuchien: noiDung ? noiDung.trim() : "",
              lydo_id: liDoTb,//this.updateForm.idLyDo,
              ghichu:
                this.updateForm.ghiChu.trim() != ""
                  ? this.updateForm.ghiChu.trim()
                  : ghiChu.trim(),
            });
          })
        }


        return body;
      } else {
        let body = {
          pPhanVungId: this.$root.token.getPhanVungID(),
          pKyHoaDon: this.kyHoaDon,
          pNgayNhanGB: this.searchForm.ckbNgayNhanGiayBao ? pNgayNhanGB : null,
          pNgayHen: this.searchForm.ckbNgayHenTra ? pNgayHen : null,
          pNgayDen: ngayDen,
          pLyDoId: this.updateForm.idLyDo,
          pGhiChu: this.updateForm.ghiChu.trim(),
          pDSCapNhat: [],
          pNguoiCN: this.$auth.getUserName(),
          pMayCN: "-",
          pIPCN: "-",
        };
        /*
        tabData.forEach((item) => {
          if (dsMaTT.includes(item.MA_TT)) {
            const noiDungDOM = document.getElementById(`noidung${item.MA_TT}`);
            const liDoTbDOM = document.getElementById(`combo${item.MA_TT}`);
            const ghiChuDOM = document.getElementById(`ghichu${item.MA_TT}`);
            const noiDung = noiDungDOM ? noiDungDOM.value : "";
            const liDoTb = liDoTbDOM ? liDoTbDOM.value : null;
            const ghiChu = ghiChuDOM ? ghiChuDOM.value : null;
            body.pDSCapNhat.push({
              hoadon_id: item.HOADON_ID,
              nd_thuchien: noiDung ? noiDung.trim() : "",
              pLyDoId: this.updateForm.idLyDo,
              pGhiChu:
                this.updateForm.ghiChu.trim() != ""
                  ? this.updateForm.ghiChu.trim()
                  : ghiChu.trim(),
            });
          }
        });
        */
        let selectedRows = this.$refs['grid' + this.currentTab].$refs.grid.getSelectedRecords()
        if (selectedRows.length <= 0) {
          this.$toast.error('Chưa chọn danh sách để cập nhật.')

        } else {
          selectedRows.forEach((item) => {
            const noiDungDOM = document.getElementById(`noidung${item.MA_TT}`);
            const liDoTbDOM = document.getElementById(`combo${item.MA_TT}`);
            const ghiChuDOM = document.getElementById(`ghichu${item.MA_TT}`);
            const noiDung = noiDungDOM ? noiDungDOM.value : "";
            const liDoTb = liDoTbDOM ? liDoTbDOM.value : null;
            const ghiChu = ghiChuDOM ? ghiChuDOM.value : null;
            body.pDSCapNhat.push({
              hoadon_id: item.HOADON_ID,
              nd_thuchien: noiDung ? noiDung.trim() : "",
              lydo_id: liDoTb,//this.updateForm.idLyDo,
              ghichu:
                this.updateForm.ghiChu.trim() != ""
                  ? this.updateForm.ghiChu.trim()
                  : ghiChu.trim(),
            });
          })
        }
        return body;
      }
    },
    makeTraTronBoBody(dsMaTT, tabData) {
      let body = {
        pPhanVungId: this.$root.token.getPhanVungID(),
        pKyHoaDon: this.kyHoaDon,
        pKieu: 2,
        pDSPhieu: [],
      };
      tabData.forEach((item) => {
        if (dsMaTT.includes(item.MA_TT)) {
          const noiDungDOM = document.getElementById(`noidung${item.MA_TT}`);
          const liDoTbDOM = document.getElementById(`combo${item.MA_TT}`);
          const ghiChuDOM = document.getElementById(`ghichu${item.MA_TT}`);
          const noiDung = noiDungDOM ? noiDungDOM.value : "";
          const liDoTb = liDoTbDOM ? liDoTbDOM.value : null;
          const ghiChu = ghiChuDOM ? ghiChuDOM.value : null;
          body.pDSPhieu.push({
            hoadon_id: item.HOADON_ID,
            giaophieu_id: item.GIAOPHIEU_ID,
            lydo_id: liDoTb ? liDoTb : this.updateForm.idLyDo,
            ghichu: ghiChu ? ghiChu.trim() : this.updateForm.ghiChu.trim(),
          });
        }
      });
      return body;
    },
    dsLoaiPhieuDropDown: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/nguyen-nhan-no/lay-danh-sach-loai-phieu"
        );
        this.dsLoaiPhieu = rs.data;
        if (rs.data) {
          this.searchForm.idLoaiPhieu = rs.data[0].LOAIPHIEU_ID;
        }
      } catch (error) {
      } finally {
      }
    },
    dsLyDoDropDown: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-ly-do-tra"
        );
        this.dsLyDoTra = rs.data;
        if (rs.data) {
          this.updateForm.idLyDo = rs.data[0].LYDOTRA_ID;
        }
      } catch (error) {
      } finally {
      }
    },
    dsLuotGiaoDropDown: async function () {
      const body = {
        pChuKyNo: this.kyHoaDon, //20210301,
        pNVTCId: this.dsNVTCId.join(','), //"3789,12901",
        pKieu: this.$root.token.getPhanVungID() == 26 ? 5 : 1, // HP 5 else 1
        pLanTaoId: null,
      };
      // console.log(body)
      try {
        var rs = await this.$root.context.post(
          "/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-luot-giao",
          body
        );
        // console.log(rs);
        this.dsLuotGiao = rs.data;
      } catch (error) {
      } finally {
      }
    },
    getTSMD: async function () {
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-tham-so-mac-dinh",
          {
            pPhanVungid: this.$root.token.getPhanVungID(),
            pMaThamSo: "DS_NHANPHIEU_THEO_AM",
          }
        );
        if (rs.data && rs.data.length > 0) {
          this.DS_NHANPHIEU_THEO_AM = rs.data[0].TEN_TS;
        }
      } catch (error) {
      } finally {
      }
    },
    getDataV1: async function () {
      //Lay_ds_hoadon_giao_nvtc_v3
      // const param = {
      //   pPhanVungId: this.$root.token.getPhanVungID(),
      //   pKyHoaDon: "20210301",
      //   pLoaiPhieuId: this.searchForm.idLoaiPhieu,
      //   pKieu: this.currentTab, //1,//tabNo
      //   pNhanVienId: 38853,
      //   pGiaoPhieuId: this.searchForm.idLuotGiao
      // };
      const body = {
        // "pPhanVungId":28,
        pKyHoaDon: this.kyHoaDon,
        pLoaiPhieuId: this.searchForm.idLoaiPhieu,
        pKieu: this.currentTab, //2,
        pNhanVienId: this.$auth.getNhanVienID(), //993,
        pGiaoPhieuId: this.searchForm.idLuotGiao, //"1362436,1368732"
      };
      console.log(body);
      try {
        var rs = await this.$root.context.post(
          "/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-hoa-don-giao-nhan-vien-thu-cuoc-v2",
          body
        );
        console.log(rs);
        this.dataExport = rs.data.data;
        this.handleData(rs.data.data);
      } catch (error) {
      } finally {
      }
    },
    handleData(srcData) {

      const dataParse = srcData.map((item) => {
        let opt = `<select id="combo${item.MA_TT}" class="form-control">`;
        this.dsLyDoTra.forEach((el) => {
          if (el.LYDOTRA_ID == item.LYDOTRA_ID) {
            opt += `<option selected="selected" value="${el.LYDOTRA_ID}">${el.NOIDUNG}</option>`;
          } else {
            opt += `<option value="${el.LYDOTRA_ID}">${el.NOIDUNG}</option>`;
          }
        });
        opt += `</select>`;
        return {
          ...item,
          LYDOTRA_ID: opt,
          GHICHU: `<input type="text" id="ghichu${item.MA_TT}" class="form-control" value="${item.GHICHU}">`,
          ND_THUCHIEN: `<input type="text" id="noidung${item.MA_TT}" class="form-control" value="${item.ND_THUCHIEN}">`,
          NGAYHEN: item.NGAYHEN, //item.NGAYHEN.split(" ")[0].split("-").reverse().join("/"),
          NGAYNHAN_HD: item.NGAYNHAN_HD //item.NGAYNHAN_HD.split(" ")[0].split("-").reverse().join("/"),
        };
      });
      if (this.currentTab !== 7 && this.currentTab !== 8) {
        if (!this.searchForm.txtTimKiem) {
          this.columnAggs[this.currentTab].forEach(x => {
            this.sumRows[this.currentTab][x].total = dataParse.reduce((acc, curr) => {
              return acc = Number(curr[x]) + acc
            }, 0)
          })
          /*
          
          */
          this.tabData[this.currentTab] = dataParse;
          this.tabPageInfo[this.currentTab].totalElement = dataParse.length
          this.tabKey[this.currentTab]++
        } else {
          this.tabData[this.currentTab] = dataParse.filter(
            (ele) => ele.MA_TT.indexOf(this.searchForm.txtTimKiem.trim()) > -1
          );
        }
      } else {
        let dt = dataParse.filter(x => x.TIEN <= 0) // đã trả
        let ct = dataParse.filter(x => x.TIEN > 0)  // chưa trả
        if (!this.searchForm.txtTimKiem) {
          this.columnAggs[7].forEach(x => {
            this.sumRows[7][x].total = ct.reduce((acc, curr) => {
              return acc = Number(curr[x]) + acc
            }, 0)
          })
          this.columnAggs[8].forEach(x => {
            this.sumRows[8][x].total = dt.reduce((acc, curr) => {
              return acc = Number(curr[x]) + acc
            }, 0)
          })
          /*
          
          */
          this.tabData[7] = [...ct]
          this.tabPageInfo[7].totalElement = ct.length
          this.tabKey[7]++

          // console.log(dt)

          this.tabData[8] = [...dt]
          this.tabPageInfo[8].totalElement = dt.length
          this.tabKey[8]++
        } else {
          this.tabData[7] = ct.filter(
            (ele) => ele.MA_TT.indexOf(this.searchForm.txtTimKiem.trim()) > -1
          );
          this.tabData[8] = dt.filter(
            (ele) => ele.MA_TT.indexOf(this.searchForm.txtTimKiem.trim()) > -1
          );
        }
      }

    },
    getLyDo(id) {
      let nd = id;
      this.dsLyDoTra.forEach((item) => {
        if (item.LYDOTRA_ID == id) {
          nd = item.NOIDUNG;
        }
      });
      return nd;
    },
    getDataV2: async function () {
      //LAY_DS_HOADON_GIAO_NVTC_NV
      const param = {
        pPhanVungId: this.$root.token.getPhanVungID(),
        pKyHoaDon: this.kyHoaDon,
        pLoaiPhieuId: this.searchForm.idLoaiPhieu,
        pKieu: this.currentTab, //1,//tabNo
        pNhanVienId: this.$auth.getNhanVienID(), //38853
      };
      console.log(param);
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-hoa-don-giao-nhan-vien-thu-cuoc-nv",
          param
        );
        this.dataExport = rs.data.data;
        this.handleData(rs.data.data);
      } catch (error) { }
    },
    getDataV3: async function () {
      //LAY_DS_HOADON_GIAO_NVTC_2
      // const param = {
      //   pPhanVungId: this.$root.token.getPhanVungID(),
      //   pKyHoaDon: this.kyHoaDon,
      //   pLoaiPhieuId: this.searchForm.idLoaiPhieu,
      //   pKieu: this.currentTab, // 1,//tabNo
      //   pGiaoPhieuId: this.searchForm.idLuotGiao
      // };
      // console.log(param);

      let vdu_3kq = 0

      const body = {
        //pPhanVungId: 26,
        kyCuoc: this.kyHoaDon,
        loaiPhieuId: this.searchForm.idLoaiPhieu,
        kieu: this.currentTab,
        giaoPhieuId: this.searchForm.idLuotGiao, //"675239,675231,673020,672995,672567,671999,671561,671134,670883,670697,670215,669946,669693"
        nhanVienId: this.dsNVTCId.join(','),// this.$auth.getNhanVienID(),
        du3KQ: vdu_3kq
      };
      //console.log(body);
      //this.loading(true)
      /*
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/quan-ly-dai-ly/ds-hoa-don-giao-nvtc-v3",
          body
        );
        /// this.dsPhieuChuaThu = rs.data.data;
        this.dataExport = rs.data.data;
        this.handleData(rs.data.data);
      } catch (error) {
        console.log(error)
      }
      */
      this.loading(true)
      await NhanPhieuAPI.layDSHoaDonGiaoNVTC3(this.axios, body)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000') {
            if (this.currentTab === 7 || this.currentTab === 8) {
              let t = res.data.data.filter(x => x.TIEN <= 0)
              let k = res.data.data.filter(x => x.TIEN > 0)
              // console.log(t, k)
              this.dataExport = this.currentTab === 7 ? k : t
              this.handleData(res.data.data)
            } else {
              this.dataExport = res.data.data
              this.handleData(res.data.data)
            }
          }
        })
        .catch(error => {
          //this.$toast.error(error.data.message_detail)
          console.log(error)
          return false
        })
        .finally(() => { this.loading(false) })
    },
    traGiayBao: async function (body) {
      try {
        const { data } = await this.axios.put(
          "/web-thuno/api/thu-no/nhan-phieu/tra-giay-bao",
          body
        );
        if (data && data.error == "200") {
          this.onClickDanhSach();
        }
      } catch (error) { }
    },
    traHoaDon: async function (body) {
      console.log(body);
      try {
        const { data } = await this.axios.put(
          "/web-thuno/api/thu-no/nhan-phieu/tra-hoa-don",
          body
        );
        // console.log(data);
        if (data && data.error == "200") {
          this.$toast.success('Cập nhật trả hóa đơn thành công.')
          this.onClickDanhSach();
        }
      } catch (error) { }
    },
    huySeri: async function (body) {
      console.log(body);
      try {
        const { data } = await this.axios.put(
          "/web-thuno/api/thu-no/nhan-phieu/cap-nhat-huy-seri",
          body
        );
        if (data && data.error == "200") {
          this.$toast.success('Cập nhật hủy seri thành công.')
        }
        // console.log(data);
      } catch (error) { }
    },
    huyTraHoaDon: async function (body) {
      console.log(body);
      try {
        const { data } = await this.axios.put(
          "/web-thuno/api/thu-no/nhan-phieu/huy-tra-phieu-hoa-don",
          body
        );
        console.log(data);
        if (data && data.error == "200") {
          this.onClickDanhSach();
        }
      } catch (error) {
        //console.log(error)
        this.$toast.error(error.data.message_detail)
      }
    },
    capNhatData: async function (body, url) {
      console.log(body);
      try {
        const { data } = await this.axios.put(url, body);
        if (data && data.error == "200") {
          this.$toast.success(`Cập nhật dữ liệu thành công`);
          this.onClickDanhSach();
        }
      } catch (error) {
        this.$toast.error(`Cập nhật dữ liệu thất bại`);
      }
    },
    traTronBo: async function (body) {
      console.log(body);
      try {
        const { data } = await this.axios.put(
          "/web-thuno/api/thu-no/nhan-phieu/tra-tron-bo",
          body
        );
        if (data && data.error == "200") {
          this.$toast.success(`Cập nhật dữ liệu thành công`);
          this.onClickDanhSach();
        }
      } catch (error) { }
    },
    huyTraTronBo: async function (body) {
      //console.log(body);
      try {
        const { data } = await this.axios.put(
          "/web-thuno/api/thu-no/nhan-phieu/huy-tra-phieu-tron-bo",
          body
        );
        console.log(data)
        if (data && data.error == "200") {
          this.onClickDanhSach();
        }
      } catch (error) {
        console.log(error)
      }
    },
    getDsNVTC: async function () {
      try {
        const { data } = await this.$root.context.get(
          "/web-thuno/api/thu-no/quan-ly-dai-ly/ds-quanly-diaban"
        );
        this.dsNVTC = data.data;
      } catch (error) { }
    },

    onNVTCFiltering: function (e) {
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_HIENTHI', 'contains', e.text, true) : query;
      e.updateData(this.dsNVTC, query);

    },

    chukycuocChangeHandler(e) {
      if (e.p_chuky_no !== null) {
        this.kyHoaDon = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
      }
      else
        this.kyHoaDon = null
    },
    onNVTCChange(args) {
      //console.log(args)
      this.dsLuotGiaoDropDown()
    },
    async getDSHoaDonGiaoNVTC3(postData) {
      this.loading(true)
      await NhanPhieuAPI.layDSHoaDonGiaoNVTC3(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000')
            return res.data.data
          return null
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
          return false
        })
        .finally(() => { this.loading(false) })
    },
  },
};
</script>
