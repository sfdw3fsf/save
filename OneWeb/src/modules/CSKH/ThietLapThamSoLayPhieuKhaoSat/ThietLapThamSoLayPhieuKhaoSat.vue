<template>
  <b-modal
    id="popup-thietLapThamSoLayPhieuKS"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    no-close-on-backdrop
    @show="onShow"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Thiết lập thời gian lấy phiếu
        </div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popup-thietLapThamSoLayPhieuKS')"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript: void(0)" @click="fetchData" data-toggle="tooltip" title="Lấy TT">
              <span class="icon one-file-attach"></span> Lấy TT
            </a>
          </li>
          <li>
            <a href="javascript: void(0)" @click="capNhap" data-toggle="tooltip" title="Cập nhật">
              <span class="icon one-reload1"></span> Cập nhật
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="table-content">
            <div class="grid">
              <div class="table-content">
                <table class="table-result table-gachle">
                  <thead class="center">
                    <tr>
                      <th class="w20"></th>
                      <th class="w60 center">Xóa</th>
                      <th class="w60 center">Bật/Tắt</th>
                      <th class="center">Từ giờ</th>
                      <th class="center">Đến giờ</th>
                    </tr>
                    <tr>
                      <th class="w20"></th>
                      <th>
                        <div class="input-icon-right">
                          <input type="text" class="form-control" />
                          <span class="icon nc-icon-outline ui-1_zoom"></span>
                        </div>
                      </th>
                      <th>
                        <div class="input-icon-right">
                          <input
                            type="text"
                            class="form-control"
                            @change="filterChange()"
                            v-model="filterValues['ON_OFF']"
                          />
                          <span class="icon nc-icon-outline ui-1_zoom"></span>
                        </div>
                      </th>
                      <th>
                        <div class="input-icon-right">
                          <input
                            type="text"
                            class="form-control"
                            @change="filterChange()"
                            v-model="filterValues['TU_GIO']"
                          />
                          <span class="icon nc-icon-outline ui-1_zoom"></span>
                        </div>
                      </th>
                      <th>
                        <div class="input-icon-right">
                          <input
                            type="text"
                            class="form-control"
                            @change="filterChange()"
                            v-model="filterValues['DEN_GIO']"
                          />
                          <span class="icon nc-icon-outline ui-1_zoom"></span>
                        </div>
                      </th>
                    </tr>
                    <tr>
                      <th class="w20"></th>
                      <th class="center w20" />
                      <th>
                        <b-form-checkbox
                              class="check"
                              value="1"
                              unchecked-value="0"
                              v-model="newRecord.ON_OFF"
                            />
                        <!-- <div class="check-action">

                          <input
                            type="checkbox"
                            class="check"
                            v-model="newRecord.ON_OFF"
                          />
                          <span class="name"></span>
                        </div> -->
                      </th>
                      <th>
                        <div>
                          <input
                            type="text"
                            class="form-control time-input"
                            v-model="newRecord.TU_GIO"
                            placeholder="Nhập mới tại dòng này (Định dạng HH:mm vd 13:11)"
                          />
                          <span
                            v-if="newRecord.TU_GIO_ERR_MSG !== ''"
                            :title="newRecord.TU_GIO_ERR_MSG"
                            class="
                              icon
                              nc-icon-glyph
                              ui-1_circle-remove
                              red
                              time-input
                            "
                          ></span>
                        </div>
                      </th>
                      <th>
                        <div>
                          <input
                            type="text"
                            class="form-control time-input"
                            v-model="newRecord.DEN_GIO"
                            placeholder="Nhập mới tại dòng này (Định dạng HH:mm vd 13:11)"
                          />
                          <span
                            v-if="newRecord.DEN_GIO_ERR_MSG != ''"
                            :title="newRecord.DEN_GIO_ERR_MSG"
                            class="
                              icon
                              nc-icon-glyph
                              ui-1_circle-remove
                              red
                              time-input
                            "
                          ></span>
                        </div>
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <template v-for="(thietLap, index) in dataItems">
                        <template>
                            <tr
                            :key="index" @click="rowClick(index, thietLap)"
                            :class="currentSelectedRow == index ? 'highlight1' : ''"
                            >
                            <td class="w20 center">
                            <span
                                class="fa fa-play text-main"
                                v-if="currentSelectedRow == index"
                            ></span>
                            </td>
                            <td class="w60 center">
                            <a
                                href="javascript: void(0)"
                                @click="xoa(thietLap)"
                                class="btn btn-main pad4 lh14"
                                data-toggle="tooltip" title="Xóa"
                            >
                                <span
                                class="nc-icon-glyph ui-1_circle-remove"
                                ></span>
                            </a>
                            </td>
                            <td class="w60 center">
                            <b-form-checkbox
                                class="check"
                                value="1"
                                unchecked-value="0"
                                v-model="thietLap.ON_OFF"
                                />
                            </td>
                            <td>
                            <div>
                                <input
                                type="text"
                                class="form-control"
                                v-model="thietLap.TU_GIO"
                                @change="validateThietLap(thietLap)"
                                />
                                <span
                                v-if="thietLap.TU_GIO_ERR_MSG"
                                :title="thietLap.TU_GIO_ERR_MSG"
                                class="
                                    icon
                                    nc-icon-glyph
                                    ui-1_circle-remove
                                    red
                                    time-input
                                "
                                ></span>
                            </div>
                            </td>
                            <td>
                            <div>
                                <input
                                type="text"
                                class="form-control"
                                v-model="thietLap.DEN_GIO"
                                @change="validateThietLap(thietLap)"
                                />
                                <span
                                v-if="thietLap.DEN_GIO_ERR_MSG"
                                :title="thietLap.DEN_GIO_ERR_MSG"
                                class="
                                    icon
                                    nc-icon-glyph
                                    ui-1_circle-remove
                                    red
                                    time-input
                                "
                                ></span>
                            </div>
                            </td>
                            </tr>
                        </template>
                    </template>
                  </tbody>
                </table>
              </div>
              <div
                class="tc-pagination"
              >
                <button
                  @click="firstPage()"
                  :disabled="currentPageIndex == 0"
                  class="btn one-arrow-left-forward"
                ></button>
                <button
                  @click="previousPage()"
                  :disabled="currentPageIndex == 0"
                  class="btn fa fa-angle-double-left"
                ></button>
                <button
                  @click="doSelectNearby(false)"
                  class="btn fa fa-angle-left"
                ></button>
                <span class="text">Bản ghi</span>
                <div class="dropdown inline">
                  <div class="fw6 input-number" data-toggle="dropdown">
                    <span class="text-main">{{ getSelectedRow }}</span
                    >/{{ rowCount }}
                  </div>
                  <div class="dropdown-menu">
                    <button @click="setPageSize(10)" class="dropdown-item">
                      10
                    </button>
                    <button @click="setPageSize(20)" class="dropdown-item">
                      20
                    </button>
                    <button @click="setPageSize(50)" class="dropdown-item">
                      50
                    </button>
                    <button @click="setPageSize(100)" class="dropdown-item">
                      100
                    </button>
                  </div>
                </div>
                <button
                  @click="doSelectNearby(true)"
                  class="btn fa fa-angle-right"
                ></button>
                <button
                  @click="nextPage()"
                  :disabled="currentPageIndex >= pageCount - 1"
                  class="btn fa fa-angle-double-right"
                ></button>
                <button
                  @click="lastPage()"
                  :disabled="currentPageIndex >= pageCount - 1"
                  class="btn one-arrow-right-forward"
                ></button>
                <button @click="them()" class="btn one-check" ref='btnThemMoi' :disabled="!(newRecord.TU_GIO || newRecord.DEN_GIO)" data-toggle="tooltip" title="Thêm mới"></button>
              </div>
            </div>
            <!-- <datagrid
              :columns="fields"
              ref="gridThietLapThoiGian"
              :dataSource="dsThietLap"
              :showColumnCheckbox="false"
              dataKeyField="ID"
              :showFilter="false"
              :childFunc="gridFunc"
              :showAddNewRow="true"
              @deleteItem="xoa"
              @addItem="them"
            >
            </datagrid> -->
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<style scoped>
input.time-input {
  display: inline;
}
span.time-input {
  position: relative;
  left: -12%;
}
</style>
<script>
import { mapState, mapGetters, mapActions } from "vuex";
import CustomDataGrid from "./components/CustomDataGrid.vue";
import ThietLapThamSoLayPhieuKhaoSatApi from "../api/ThietLapThamSoLayPhieuKhaoSatApi";
import moment from "moment";
export default {
  components: { datagrid: CustomDataGrid },
  data() {
    return {
      filterValues: [],
      currentSelectedRow: 0,
      currentPageIndex: 0,
      currentPageSize: 10,
      gridFunc: [this.xoa],
      dsThietLap: [],
      dsThietLapBackup: [],
      newRecord: {
        ID: "",
        TU_GIO: "",
        DEN_GIO: "",
        TU_GIO_ERR_MSG: "",
        DEN_GIO_ERR_MSG: "",
        ON_OFF: 0,
      },
      lstXoa: [],
      fields: [
        {
          headerText: "Xóa",
          allowFiltering: true,
          allowSorting: false,
          cellCssClass: "w60 center ",
          isDeleteAction: true,
        },
        {
          fieldName: "ON_OFF",
          headerText: "Bật/Tắt",
          allowFiltering: true,
          allowSorting: false,
          isCheckbox: true,
          cellCssClass: "w20 center ",
          isNewRecord: true,
          dataType: "checkbox",
          cssInputClass: "check",
        },
        {
          fieldName: "TU_GIO",
          headerText: "Từ giờ",
          allowFiltering: true,
          allowSorting: false,
          isNewRecord: true,
          dataType: "time",
          cssInputClass: "form-control",
          isRequired: true,
          errMsg: "",
        },
        {
          fieldName: "DEN_GIO",
          headerText: "Đến giờ",
          allowFiltering: true,
          allowSorting: false,
          isNewRecord: true,
          dataType: "time",
          cssInputClass: "form-control",
          isRequired: true,
          errMsg: "",
        },
      ],
    };
  },
  computed: {
    ...mapGetters("thietlapthamso", ["dsThietLapThoiGianGetter"]),
    getSelectedRow() {
      return this.currentSelectedRow > -1
        ? this.currentPageIndex * this.currentPageSize +
            this.currentSelectedRow +
            1
        : 0;
    },
    counteSelectedRows: function () {
      return this.selectedValues.length + "/" + this.dsThietLap.length;
    },
    rowCount: function () {
      if (this.dsThietLap != null) return this.dsThietLap.length;
      return 0;
    },
    pageCount: function () {
      if (this.rowCount > 0)
        return Math.ceil(this.rowCount / this.currentPageSize);
      return 0;
    },
    pageRowCount: function () {
      if (this.dataItems != null) return this.dataItems.length;
      return 0;
    },
    dataItems: function () {
      if (this.dsThietLap != null) {
        return this.dsThietLap
          .filter(x => x.IS_XOA != true)
          .filter(this.filterData)
          .slice(
            this.currentPageIndex * this.currentPageSize,
            this.currentPageIndex * this.currentPageSize + this.currentPageSize
          );
      }

      return [];
    },
  },
  methods: {
    ...mapActions("thietlapthamso", [
      "getDsThietLapThoiGian",
      "clearData",
      "addThietLapThoiGian",
      "removeThietLayThoiGianById",
    ]),
    reset() {
      this.newRecord = {
        ID: "",
        TU_GIO: "",
        DEN_GIO: "",
        TU_GIO_ERR_MSG: "",
        DEN_GIO_ERR_MSG: "",
        ON_OFF: "0",
      },
      this.lstXoa = [];
    },
    async onShow() {
      //this.clearData()
      await this.fetchData();
    },
    async fetchData() {
      this.reset()
      let data = {
        pageNo: 1,
        pageSize: 10000,
      };
      // await this.getDsThietLapThoiGian(data)
      let response =
        await ThietLapThamSoLayPhieuKhaoSatApi.getDsThietLapThoiGian(
          this.axios,
          data.pageNo,
          data.pageSize,
          {}
        );
      let ds = [];
      this.dsThietLap = [];
      this.dsThietLapBackup = []
      console.log(response);
      if (response.data.error_code === "BSS-00000000") {
        ds = response.data.data.data || [];
        ds.forEach((element) => {
          element.TU_GIO = moment(element.TU_GIO, "DD/MM/YYYY HH:mm:ss").format("HH:mm");
          element.DEN_GIO = moment(element.DEN_GIO, "DD/MM/YYYY HH:mm:ss").format("HH:mm");
          element.IS_UPDATE = false;
          element.TU_GIO_ERR_MSG = "";
          element.DEN_GIO_ERR_MSG = "";
          element.IS_XOA = false
          element.TU_GIO_OLD = element.TU_GIO
          element.DEN_GIO_OLD = element.DEN_GIO
          element.ON_OFF_OLD = element.ON_OFF
        });
        this.dsThietLap = [...ds];
        this.dsThietLapBackup = [...ds];
      } else {
        this._vm.$root.toastError(respone.data.message);
      }
    },
    isNeedUpdate() {
      let needUpdate = false
      for (let i = 0; i < this.dsThietLap.length; i++) {
        if (this.dsThietLap[i].ID.includes('tmp_') && this.dsThietLap[i].IS_XOA != true)
        {
          needUpdate = true
        }
        else if (!this.dsThietLap[i].ID.includes('tmp_') && this.dsThietLap[i].IS_XOA == true)
        {
          needUpdate = true
        }
        else if (!this.dsThietLap[i].ID.includes('tmp_')) {
          console.log('b', i)
          let thietlap = this.dsThietLap[i]
          console.log(thietlap)
          if (thietlap.TU_GIO != thietlap.TU_GIO_OLD || thietlap.DEN_GIO != thietlap.DEN_GIO_OLD || thietlap.ON_OFF != thietlap.ON_OFF_OLD) {
            thietlap.IS_UPDATE = true
            needUpdate = true
          }
        }
      }
      console.log('need update', this.dsThietLap)
      return needUpdate
      // if (this.lstXoa && this.lstXoa.length > 0) return true
      // let needUpdate = false
      // this.dsThietLapBackup.forEach(backup => {
      //   if (needUpdate) return
      //   let tl = this.dsThietLap.find(x => x.ID == backup.ID)
      //   if (!tl) needUpdate = true
      //   console.log(tl.TU_GIO != backup.TU_GIO)
      //   console.log(tl.DEN_GIO != backup.DEN_GIO)
      //   console.log(tl.ON_OFF != backup.ON_OFF)
      //   if (tl.TU_GIO != backup.TU_GIO || tl.DEN_GIO != backup.DEN_GIO || tl.ON_OFF != backup.ON_OFF)
      //     needUpdate = true
      // })
      // return needUpdate
    },
    async xoaRecord(ID) {
      try {
        if (ID.includes("tmp_")) return
        let response = await ThietLapThamSoLayPhieuKhaoSatApi.xoaThietLapThoiGian(this.axios, {
          id: ID,
        })
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess('Xóa thành công' )
        }
        else {
          this.$root.toastError('Có lỗi xảy ra: ' + response.data.message)
        }
      }
      catch (e) {
        console.log(e)
        this.$root.toastError('Có lỗi xảy ra: ' + e.data.message)
      }
    },
    async capNhap() {
        if (!this.isNeedUpdate()) {
            this.$root.toastError('Không có sự thay đổi, không thể cập nhập')
            return
        }
        let promises = [];
        let dsThanhCong = []
        let dsThatBai = []

        for await (var thietLap of this.dsThietLap)
        {
            if (thietLap.IS_XOA == true && thietLap.ID)
            {
                try {
                    if (thietLap.ID.includes("tmp_")) continue
                    let response = await ThietLapThamSoLayPhieuKhaoSatApi.xoaThietLapThoiGian(this.axios, { id: thietLap.ID, })
                    if (response.data.error_code === "BSS-00000000") {
                        dsThanhCong.push({thietLap: thietLap, isXoa: true})
                    }
                    else {
                        dsThatBai.push({thietLap: thietLap, message: response.data.message, isXoa: true})
                    }
                }
                catch (e) {
                    console.log(e)
                    dsThatBai.push({thietLap: thietLap, message: response.data.message, isXoa: true})
                }
                // await this.xoaRecord(e.ID)
            }
            else if (thietLap.IS_UPDATE == true)
            {
                try
                {
                    let response = await ThietLapThamSoLayPhieuKhaoSatApi.capNhapThietLapThoiGian(
                        this.axios,
                        {
                            trangThai: thietLap.ON_OFF,
                            tuGio: thietLap.TU_GIO,
                            denGio: thietLap.DEN_GIO,
                            id: thietLap.hasOwnProperty("ID") && thietLap.ID.includes("tmp_") ? "" : thietLap.ID,
                        }
                    )

                    if (response.data.error_code === "BSS-00000000")
                    {
                        dsThanhCong.push({thietLap: thietLap, isXoa: false})
                    }
                    else
                    {
                        dsThatBai.push({thietLap: thietLap, message: response.data.message, isXoa: false})
                    }
                }
                catch (e)
                {
                    console.log(e)
                    dsThatBai.push({thietLap: thietLap, message: e.response.data.message, isXoa: false})
                }

            }
        }

        if (dsThanhCong.length == 0 && dsThatBai.length == 0)
        {
            this.$root.toastError('Không có sự thay đổi, không thể cập nhập')
        }

        if (dsThanhCong.length > 0)
        {
            let message = dsThanhCong.map(x => " - " + (x.isXoa == true ? " Xóa: " : "" ) + "Từ giờ: " + x.thietLap.TU_GIO + " đến giờ " + x.thietLap.DEN_GIO ).join("\n")
            this.$root.toastSuccess("Cập nhập thành công: \n" + message)
        }

        if (dsThatBai.length > 0)
        {
            let message = dsThatBai.map(x => " - " + (x.isXoa == true ? " Xóa: " : "" ) + "Từ giờ: " + x.thietLap.TU_GIO + " đến giờ " + x.thietLap.DEN_GIO + " - " + x.message).join("\n")
            this.$root.toastError("Cập nhập thất bại: \n" + message)
        }
        await this.fetchData()

    },
    xoa(dataItem) {
      console.log(dataItem)
      if (dataItem) {
        // this.removeThietLayThoiGianById(dataItem.ID)
        if (!dataItem.ID.includes("tmp_")) {
          this.lstXoa.push(dataItem.ID);
        }
        let item = this.dsThietLap.find(x => x.ID == dataItem.ID)
        let index = this.dsThietLap.indexOf(item)
        item.IS_XOA = true
        this.$set(this.dsThietLap, index, item)
        // dataItem.IS_XOA = true
        // this.$refs.gridThietLapThoiGian.setCurrentSelectedRow(0);
        this.dsThietLap = this.dsThietLap.filter((x) => x.ID != dataItem.ID);
        // if (this.dsThietLap.length > 0) {
        //   this.$refs.gridThietLapThoiGian.setCurrentSelectedRow(0);
        // }
      }

    },
    them() {
      let ds = this.dsThietLap;

      if (!this.validateThietLap(this.newRecord)) return;

      if (!this.newRecord.hasOwnProperty("ID") || !this.newRecord.ID) {
        this.newRecord.ID = "tmp_" + (this.dsThietLap.length + 1);
        this.newRecord.IS_UPDATE = true;
        ds.push(this.newRecord);
        this.dsThietLap = ds;
        this.newRecord = {
          ID: "",
          TU_GIO: "",
          DEN_GIO: "",
          TU_GIO_ERR_MSG: "",
          DEN_GIO_ERR_MSG: "",
          ON_OFF: "0",
        }
      }
      // this.addThietLapThoiGian(dataItem)
    },
    validateHhMm(inputField) {
      var isValid = /^([0-1]?[0-9]|2[0-4]):([0-5][0-9])(:[0-5][0-9])?$/.test(inputField);
      return isValid;
    },
    validateThietLap(thietLap) {
      let checkValidate = true;

      if (!thietLap.TU_GIO) {
        thietLap.TU_GIO_ERR_MSG = "Bạn phải nhập từ giờ";
        checkValidate = false;
      } else {
        thietLap.TU_GIO_ERR_MSG = "";
      }

      if (!thietLap.DEN_GIO) {
        thietLap.DEN_GIO_ERR_MSG = "Bạn phải nhập đến giờ";
        checkValidate = false;
      } else {
        thietLap.DEN_GIO_ERR_MSG = "";
      }

      if (!this.validateHhMm(thietLap.TU_GIO)) {
        thietLap.TU_GIO_ERR_MSG = "Trường Từ giờ ko đúng định dạng 'HH:mm'";
        checkValidate = false;
      }
      if (!this.validateHhMm(thietLap.DEN_GIO)) {
        thietLap.DEN_GIO_ERR_MSG = "Trường Từ giờ ko đúng định dạng 'HH:mm'";
        checkValidate = false;
      }

      if ( moment(thietLap.TU_GIO, 'HH:mm').isAfter( moment(thietLap.DEN_GIO, 'HH:mm'))) {
        thietLap.TU_GIO_ERR_MSG = "Từ giờ phải nhỏ hơn đến giờ";
        checkValidate = false;
      }
      return checkValidate
    },
    filterData: function (dataItem) {
      let result = true;
      this.fields.forEach((column) => {
        if (column.allowFiltering) {
          if (this.filterValues[column.fieldName] != null) {
            if (result) {
              if (dataItem[column.fieldName] != null)
                result = dataItem[column.fieldName]
                  .toString()
                  .toLowerCase()
                  .includes(this.filterValues[column.fieldName].toLowerCase());
              else {
                if (this.filterValues[column.fieldName].toString().trim() == "")
                  result = true;
                else result = false;
              }
            }
          }

          // if(dataItem[column.fieldName] != null)
          //   {
          //       if(this.filterValues[column.fieldName] != null)
          //           if(result)
          //               result = dataItem[column.fieldName].toString().toLowerCase().includes(this.filterValues[column.fieldName].toLowerCase());
          //   }
        }
      });
      return result;
    },
    setCurrentSelectedRow: function (value) {
      if (this.currentSelectedRow == value) {
        if (value > this.pageRowCount - 1) value = this.pageRowCount - 1;
      } else {
        if (value > this.pageRowCount - 1 || value < 0) {
          if (this.currentSelectedRow == -1) {
            if (value > this.currentSelectedRow) value = 0;
            else value = this.pageRowCount - 1;
          } else value = -1;
        }
      }
      this.currentSelectedRow = value;
    },
    setPageSize: function (value) {
      this.currentPageSize = value;
      if (this.currentSelectedRow > -1)
        this.setCurrentSelectedRow(this.currentSelectedRow);
    },
    nextPage: function () {
      this.currentPageIndex += 1;
      if (this.currentSelectedRow > -1)
        this.setCurrentSelectedRow(this.currentSelectedRow);
    },
    lastPage: function () {
      this.currentPageIndex = this.pageCount - 1;
      if (this.currentSelectedRow > -1)
        this.setCurrentSelectedRow(this.currentSelectedRow);
    },
    previousPage: function () {
      this.currentPageIndex -= 1;
      if (this.currentSelectedRow > -1)
        this.setCurrentSelectedRow(this.currentSelectedRow);
    },
    firstPage: function () {
      this.currentPageIndex = 0;
      if (this.currentSelectedRow > -1)
        this.setCurrentSelectedRow(this.currentSelectedRow);
    },
    doSelectNearby: function (next) {
      this.setCurrentSelectedRow(
        next ? this.currentSelectedRow + 1 : this.currentSelectedRow - 1
      );
    },
    doSelectItem: function (dataItem) {
      this.selectedValues.push(dataItem);
    },
    filterChange: function () {
      if (this.currentSelectedRow > -1) this.setCurrentSelectedRow(-1);
      if (this.currentPageIndex > this.pageCount - 1)
        this.currentPageIndex =
          this.pageCount - 1 >= 0 ? this.pageCount - 1 : 0;
    },
    rowClick: function (rowIndex, dataItem) {
      this.setCurrentSelectedRow(rowIndex);
    },
  },
  watch: {
    dsThietLap: function (newValue) {
      this.setCurrentSelectedRow(this.currentSelectedRow);
    },
  },
};
</script>
