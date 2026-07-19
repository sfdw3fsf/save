<template src="./TraCuuLichSuPhanAnh.html"></template>
<style src="./TraCuuLichSuPhanAnh.scss">
</style>
<script>
import XLSX from "xlsx";
import TraCuuAPI from "../api/TraCuuAPI";
import TraCuuDanhBa from "../TraCuuDanhBa/";
import { mapActions, mapState, mapGetters } from "vuex";
import CustomDataGrid from "../utils/CustomDataGrid.vue";
import moment from "moment";
import downloadexcel from "vue-json-excel";
import { Workbook } from "@syncfusion/ej2-excel-export/index";
import SearchAccount from "@/modules/search/subscriber/SearchAccount";
import CSKHGrid from "../utils/CSKHGrid"
// import { Page, Filter, Sort } from "@syncfusion/ej2-vue-treegrid";
import {
  Edit,
  Sort,
  Filter,
  ForeignKey,
  Resize,
  Freeze,
  Page,
  Group,
} from "@syncfusion/ej2-vue-grids";
import { L10n } from "@syncfusion/ej2-base";
var initial = true;
L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
    },
  },
});
export default {
  props: ['maKhachHang'],
  provide: {
    grid: [Edit, Sort, Filter, ForeignKey, Resize, Freeze, Page, Group],
  },
  components: { TraCuuDanhBa, downloadexcel, CustomDataGrid, SearchAccount, CSKHGrid},
  name: "TraCuuLichSuPhanAnh",
  destroyed() {},
  data() {
    return {
      header: {
        title: "TRA CỨU LỊCH SỬ PHẢN ÁNH",
        list: [
          { name: "Chăm sóc khách hàng", link: "/cskh" },
          {
            name: "Tra cứu",
            link: "/cskh/hometracuu",
          },
          {
            name: "Tra cứu lịch sử phản ánh",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      dsPhanAnh: [],
      dateConfig: {
        altInput: true,
        dateFormat: "d/m/Y",
        altFormat: "d/m/Y",
      },
      from_date: new Date(),
      to_date: new Date(),
      today: new Date(),
      chk_from_date: false,
      chk_to_date: false,
      pageNo: 1,
      pageSize: 10,
      ma_kh: "",
      pageInfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
      },
      getSelectedRow: 0,
      pageSizeList: [10, 20],
      treeGridData: [],
      fields2: [
        {
          fieldName: "NOIDUNGPA",
          headerText: "Nội dung phản ánh",
          allowFiltering: true,
          allowSorting: false,
          isGroupedColumn: true,
        },
        {
          fieldName: "MA_KH",
          headerText: "Mã KH",
          allowFiltering: true,
          allowSorting: false,
          cellCssClass: "font-weight-bold",
        },
        {
          fieldName: "NGAYTIEPNHAN",
          headerText: "Ngày tiếp nhận",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGAY_GQ",
          headerText: "Ngày giải quyết",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "GHICHU",
          headerText: "Ghi chú",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TRANGTHAI_PA",
          headerText: "Trạng thái",
          allowFiltering: true,
          allowSorting: false,
        },
      ],
      items: [],
      dataItems: [],
      dataItemsGetter: [],
      arraySearchInput: [
        { key: "MA_KH", value: "" },
        { key: "NGAYTIEPNHAN", value: "" },
        { key: "NGAY_GQ", value: "" },
        { key: "GHICHU", value: "" },
        { key: "TRANGTHAI_PA", value: "" },
      ],
      columnTemplate: function () {
        return {
          template: {
            template:
              '<span :id="data.index" :style="{display: display }" style="font-size: 12px;" class="col-mark-selected-row fa fa-play text-main"></span>',
            data() {
              return {
                data: {},
              };
            },
            computed: {
              display() {
                return parseInt(this.data.index) ==
                  this.$parent.getSelectedRowIndexes()[0]
                  ? "block"
                  : "none";
              },
            },
          },
        };
      },
      filterBarTemplate: {
        create: function (args) {
          let div = document.createElement("div");
          div.classList.add("input-icon-right");

          let input = document.createElement("input");
          input.classList.add("form-control");
          input.id = args.column.foreignKeyField;

          let span = document.createElement("span");
          span.classList.add("icon", "nc-icon-outline", "ui-1_zoom");

          div.append(input);
          div.append(span);

          return div;
        },
        write: function (args) {
          args.element
            .querySelector("input")
            .addEventListener("input", (args) => {
              let value = args.currentTarget.value;
              value = !!value ? value.trim() : value;

              if (!value) {
                this.parent.removeFilteredColsByField(args.currentTarget.id);
              } else {
                this.parent.filterByColumn(
                  args.currentTarget.id,
                  "contains",
                  value
                );
              }
            });
        },
      },
      filterValues: [],
      popupComponent: null,
    };
  },
  async mounted() {
    // this.pageNo = 1
    // this.pageSize = 50
    // await this.doSearch()
    this.focusInput();
    await this.doSearch()
    // this.$nextTick(async () => await this.doSearch())
  },
  computed: {
    ...mapState("tracuulichsuphananh", ["dsLichSuPhanAnh"]),
    ...mapGetters("tracuulichsuphananh", [
      "dsLichSuPhanAnhGetter",
      "totalItemsGetter",
    ]),
    dataExcel() {
      return Object.values(this.dsLichSuPhanAnhGetter);
    },
  },
  created() {
    this.clearData();
    if(this.maKhachHang){
      this.ma_kh = this.maKhachHang || "";
    }
  },
  methods: {
    ...mapActions("tracuulichsuphananh", [
      "getLichSuPhanAnh",
      "clearData",
      "getObjSearch",
    ]),
    onRowSelected(items) {
    },
    onChangeText(txt, key) {
      let data = {
        key: key || "",
        value: txt || "",
      };
      this.getObjSearch(data);
    },
    formatData(input) {
      let output = [];

      var tmp = input.reduce(function (r, a) {
        r[a.NOIDUNGPA] = r[a.NOIDUNGPA] || [];
        r[a.NOIDUNGPA].push(a);
        return r;
      }, Object.create(null));

      output = Object.keys(tmp).map(function (key) {
        return {
          NOIDUNGPA: key,
          Childs: tmp[key],
          isOpen: true,
          isSelected: false,
        };
      });
      return output;
    },
    onRowSelected(args) {
      let refValue = args.row.closest(".e-grid").getAttribute("ref-value");
      this.$refs[refValue].$el
        .querySelectorAll(".col-mark-selected-row")
        .forEach((item) => {
          item.style.display =
            args.rowIndex == parseInt(item.id) ? "block" : "none";
        });
    },
    onDataBoundTienTrinh() {
      this.$refs["grid-dsphananh"].$refs.grid.groupCollapseAll()
    },
    onActionCompleteTienTrinh(args) {
      this.onActionComplete(args);

      this.$refs["grid-dsphananh"].$el
        .querySelectorAll(".e-indentcell")
        .forEach((item) => {
          item.classList.add("tree-first", "bg-white");

          let div1 = document.createElement("div");
          div1.classList.add("tree-line");

          let div2 = document.createElement("div");
          div2.classList.add("tree-indent");

          item.append(div1);
          item.append(div2);
        });
    },
    onActionComplete(args) {
      if (args.requestType == "filtering") {
        let filterObj = args.currentFilterObject;
        let grid = document
          .querySelector(`[e-mappinguid="${filterObj.uid}"]`)
          .closest(".e-grid");
        let filterInput = grid.querySelector(`#${filterObj.field}`);
        filterInput.click();
        filterInput.focus();
      }
    },
    // async fetchData(pageNo, pageSize) {
    //   this.loading(true);
    //   let response = null;
    //   try {
    //     this.dataItems = [];
    //     let postData = {
    //       ngayTiepNhan: this.$data.chk_from_date
    //         ? moment(this.from_date).format("DD/MM/YYYY")
    //         : "",
    //       denNgay: this.$data.chk_to_date
    //         ? moment(this.to_date).format("DD/MM/YYYY")
    //         : "",
    //       maKh: this.$data.ma_kh,
    //     };
    //     response = await TraCuuAPI.getTraCuuLichSuPhanAnh(
    //       this.axios,
    //       pageNo,
    //       10000,
    //       postData
    //     )
    //       .then((result) => {
    //         if (result.data.error_code === "BSS-00000000") {
    //           if (
    //             result.data.data !== undefined &&
    //             result.data.data.data !== undefined
    //           ) {
    //             if (result.data.data.data.length > 0) {
    //               this.items = result.data.data.data;
    //               this.items.forEach((element, index) => {
    //                 element.isSelected = false;
    //                 element.id = "a" + index;
    //               });

    //               this.dataItems = this.formatData(this.items);
    //               this.dataItems[0].isSelected = true;
    //               this.dataItemsGetter = this.dataItems;

    //               let newPageInfo = {
    //                 page: result.data.data.page,
    //                 maxSize: this.pageInfo.maxSize,
    //                 totalElement: this.dataItems.length,
    //                 totalPages: this.dataItems.length / this.pageInfo.maxSize,
    //                 sort: result.data.data.sort,
    //                 propertiesSort: result.data.data.propertiesSort,
    //               };
    //               this.pageInfo = newPageInfo;
    //             }
    //             else {
    //               this.$toast.warning("Không tìm thấy thông tin khách hàng.")
    //               this.focusInput()
    //             }
    //           } else {
    //             this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
    //           }
    //         } else {
    //           this.$root.toastError(respone.data.message);
    //         }
    //       })
    //       .catch((reject) => {
    //         console.log(reject);
    //         if (
    //           reject.response &&
    //           reject.response.data &&
    //           reject.response.data.message
    //         )
    //           this.$root.toastError(reject.response.data.message);
    //         else this.$root.toastError("Có lỗi xảy ra!");
    //       });
    //   } catch (e) {
    //     console.log(e);
    //   } finally {
    //     this.loading(false);
    //   }
    // },
    onUnselected() {
      this.dataItems.forEach((element) => {
        element.isSelected = false;
        element.Childs.forEach((child) => {
          if (child.isSelected) {
            child.isSelected = false;
          }
        });
      });
    },
    async fetchData() {
      let response = null;

      if (
        this.chk_from_date &&
        this.chk_to_date &&
        moment(this.to_date).diff(this.from_date, "days") < 0)
      {
        this.$root.toastError("Ngày tiếp nhận không được lớn hơn Đến ngày!!");
        return;
      }
      this.loading(true);
      try {
        if (this.chk_from_date) {
          if (!this.from_date) {
            this.$root.toastError("Trường từ ngày không được để trống")
            return
          }

          if (!moment(this.from_date, "DD/MM/YYYY").isValid()) {
            this.$root.toastError("Trường từ ngày không đúng định dạng DD/MM/YYYY")
            return
          }
        }

        if (this.chk_to_date) {
          if (!this.to_date) {
            this.$root.toastError("Trường đến ngày không được để trống")
            return
          }

          if (!moment(this.to_date, "DD/MM/YYYY").isValid()) {
            this.$root.toastError("Trường đến ngày không đúng định dạng DD/MM/YYYY")
            return
          }
        }

        this.items = [];
        this.dsPhanAnh = [];
        let postData = {
          ngayTiepNhan: this.chk_from_date
            ? moment(this.from_date).format("DD/MM/YYYY")
            : "",
          denNgay: this.chk_to_date
            ? moment(this.to_date).format("DD/MM/YYYY")
            : "",
          maKh: this.ma_kh.trim(),
          pageNo: 1,
          pageSize: 1000,
        };
        let response = await TraCuuAPI.traCuuLichSuPhanAnh(
          this.axios,
          1,
          10000,
          postData
        );
        if (response.data.error_code === "BSS-00000000") {
          initial = true
          this.dsPhanAnh = response.data.data.data || [];
          this.dsPhanAnh.sort((a, b) => a.NOIDUNGPA.localeCompare(b.NOIDUNGPA));
          // this.dsPhanAnh.forEach((element, index) => {
          //   element.NGAYTIEPNHAN = moment
          //     .utc(element.NGAYTIEPNHAN)
          //     .format("DD/MM/YYYY HH:mm:ss");
          //   element.NGAY_GQ = moment
          //     .utc(element.NGAY_GQ)
          //     .format("DD/MM/YYYY HH:mm:ss");
          // });
        }
        // await this.getLichSuPhanAnh(postData).then(() => {
        //   if (
        //     !this.dsLichSuPhanAnhGetter ||
        //     this.dsLichSuPhanAnhGetter.length == 0
        //   )
        //     this.$root.toastError("Không tìm thấy thông tin khách hàng!");
        // });
        // this.treeGridData = this.formatData(this.dsLichSuPhanAnhGetter)
      } catch (e) {
        if (e.data && e.data.message) {
          this.$root.toastError(e.data.message)
        }
        else {
          this.$root.toastError("Không tìm thấy thông tin khách hàng!");
        }

      } finally {
        this.loading(false);
      }
      return response;
    },
    async doSearch() {
      await this.fetchData();
      // await this.fetchData(this.pageNo, this.pageSize);
      // await this.$refs.dsLichSuPhanAnh.fetchData();
    },
    exportXLS() {},
    focusInput() {
      this.$refs.maKH.select();
    },
    firstPage() {},
    lastPage() {},
    nextPage() {},
    previousPage() {},
    doSelectNearby() {},
    setPageSize(value) {
      this.pageInfo.maxSize = value;
    },
    onFatherRowClick(item) {
      this.onUnselected();
      item.isOpen = !item.isOpen;
      item.isSelected = true;
    },
    onChildRowClick(item) {
      this.onUnselected();
      item.isSelected = true;
    },
    filterChange() {
      let datatmp = this.items;
      this.fields.forEach((field) => {
        let search = this.filterValues[field.key];
        if (search) {
          datatmp = datatmp.filter((x) => {
            const itemValue = x[field.key] + "" || "";
            return itemValue.toLowerCase().indexOf(search.toLowerCase()) > -1;
          });
        }
      });
      this.dataItemsGetter = this.formatData(datatmp);
    },
    async onMaKHSearch(event) {
      if (event.keyCode == 13) {
        await this.fetchData();
      }
    },
    onClickBtnExcelDsPhanAnh() {
      if (!this.dsPhanAnh || this.dsPhanAnh.length == 0) {
        this.$toast.error("Không có dữ liệu để xuất");
        return;
      }

      let propName = {
        MA_KH: "Mã KH",
        NGAYTIEPNHAN: "Ngày tiếp nhận",
        NGAYGQ: "Ngày giải quyết",
        GHICHU: "Ghi chú",
        TRANGTHAI_PA: "Trạng thái",
      };

      let columns = [
        { index: 1, width: 50 },
        { index: 2, width: 130 },
        { index: 3, width: 130 },
        { index: 4, width: 130 },
        { index: 5, width: 130 },
        { index: 6, width: 130 },
      ];

      let rows = [];
      let headerRow = {
        index: 1,
        cells: [
          {
            index: 1,
            value: "Mã KH",
            colSpan: 2,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
            },
          },
          {
            index: 3,
            value: "Ngày tiếp nhận",
            colSpan: 1,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
            },
          },
          {
            index: 4,
            value: "Ngày giải quyết",
            colSpan: 1,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
            },
          },
          {
            index: 5,
            value: "Ghi chú",
            colSpan: 1,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
            },
          },
          {
            index: 6,
            value: "Trạng thái",
            colSpan: 1,
            style: {
              borders: {
                color: "#00000000",
                lineStyle: "thin",
              },
            },
          },
        ],
      };

      rows.push(headerRow);
      let dsPhanAnh = this.dsPhanAnh.map((item) => item.NOIDUNGPA);
      dsPhanAnh = [...new Set(dsPhanAnh)];
      let rowIndex = 2
      dsPhanAnh.forEach((element, index) => {
        let row = {
          index: rowIndex++,
          cells: [],
        };
        row.cells.push({
          index: 1,
          colSpan: 6,
          value: 'Nội dung phản ánh: ' + element,
          style: {
            borders: {
              color: "#00000000",
              lineStyle: "thin",
            },
          },
        });
        rows.push(row);
        this.dsPhanAnh.forEach((element2, index2) => {
          if (element2.NOIDUNGPA == element) {
            let row2 = {
              index: rowIndex++,
              cells: [
                {index: 1, value: '', style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
                {index: 2, value: element2.MA_KH, style: { bold: true, borders: { color: "#00000000", lineStyle: "thin", }, }},
                {index: 3, value: element2.NGAYTIEPNHAN, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
                {index: 4, value: element2.NGAY_GQ, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
                {index: 5, value: element2.GHICHU, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
                {index: 6, value: element2.TRANGTHAI_PA, style: { bold: false, borders: { color: "#00000000", lineStyle: "thin", }, }},
              ]
            }
            rows.push(row2)
          }
        })
      });
      let worksheets = [{ columns, rows }];
      worksheets[0].name = "export"
      let workbook = new Workbook({ worksheets }, "xlsx");
      workbook.save("Export.xlsx");
      // if (!this.dsPhanAnh || this.dsPhanAnh.length == 0) {
      //     this.$toast.error('Không có dữ liệu để xuất')
      //     return
      // }

      // let dsPhanAnh = this.dsPhanAnh.map(item => item.NOIDUNGPA)
      // dsPhanAnh = [...new Set(dsPhanAnh)]

      // let exportData = []

      // dsPhanAnh.forEach(element => {
      //     exportData.push({
      //         'Stt': null,
      //         'Nội dung phản ánh': element,
      //         'Mã KH': null,
      //         'Ngày tiếp nhận': null,
      //         'Ngày giải quyết': null,
      //         'Ghi chú': null,
      //         'Trạng thái': null,
      //     })

      //     this.dsPhanAnh.forEach(item => {
      //         if (item.NOIDUNGPA === element) {
      //             exportData.push({
      //                 'Stt': item.STT,
      //                 'Nội dung phản ánh': item.NOIDUNGPA,
      //                 'Mã KH': item.MA_KH,
      //                 'Ngày tiếp nhận': item.NGAYTIEPNHAN,
      //                 'Ngày giải quyết': item.NGAYGQ,
      //                 'Ghi chú': item.GHICHU,
      //                 'Trạng thái': item.TRANGTHAI_PA,
      //             })
      //         }
      //     })
      // })

      // let worksheet = XLSX.utils.json_to_sheet(exportData)
      // let workbook = XLSX.utils.book_new()
      // XLSX.utils.book_append_sheet(workbook, worksheet, "export")
      // XLSX.writeFile(workbook, "export.xlsx")
    },
    ShowSearchAccount() {
      this.popupComponent = SearchAccount;
    //   SearchAccount.data.isPopup = true;
      this.Popup("popupComponents");
      //this.$refs.SearchAccountComponent.show();
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
      // // Xu ly form popup
      // $('#'+modalId).modal();
      // //appending modal background inside the bigform-content
      // $('.modal-backdrop').appendTo('#ChangeADSLSpeed');
      // //removing body classes to enable click events
      // $('body > .modal-backdrop').remove();
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      // $('#'+modalId).modal('hide');
      // $('#ChangeADSLSpeed > .modal-backdrop').remove();
    },
    searchAccountClosed(val) {
      if(val){
        this.ma_kh = val.ma_kh;
      }
      this.ClosePopup("popupComponents");
    },
  },

  watch: {
    pageNo: {
      handler: async function (value) {
        await this.fetchData();
      },
    },
  },
};
</script>
