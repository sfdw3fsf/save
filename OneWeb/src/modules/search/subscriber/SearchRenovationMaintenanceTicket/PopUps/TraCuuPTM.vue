<template>
  <ejs-dialog
    :enableResize="true"
    :visible="false"
    ref="popupTraCuuPTM"
    :header="'Chọn phần tử mạng'"
    showCloseIcon="true"
    target=".main-wrapper"
    width="80%"
    height="800"
  >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a> <span class="icon one-reload1"></span> Tìm kiếm </a>
          </li>
          <li>
            <a> <span class="icon one-notepad"></span> Chấp nhận </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="box-form">
              <div class="legend-title">Chọn đơn vị</div>
              <div class="tree-plus tree-icon ovauto" style="height: 225px">
                <ejs-treeview
                  id="treeview"
                  ref="treeObj"
                  @nodeSelected="selectDonViFocusedNodeChange"
                  :fields="this.fields"
                  :showCheckBox="false"
                ></ejs-treeview>
              </div>
            </div>
          </div>
          <div class="col-sm-9 col-12">
            <div class="legend-title">Tiêu chí tìm kiếm</div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="box-form" style="height: 96% !important">
                  <div class="info-row">
                    <div class="key w100">Tên thiết bị</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        v-model="txtTenThietBi"
                      />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w100">Loại thiết bị</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        v-model="txtLoaiThietBi"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="box-form" style="height: 96% !important">
                  <div class="info-row">
                    <div class="key w100">Nhóm thiết bị</div>
                    <div class="value">
                      <ejs-dropdownlist
                        class="combobox"
                        :dataSource="[]"
                        :allowFiltering="true"
                      />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w100">Kiểu thiết bị</div>
                    <div class="value">
                      <ejs-dropdownlist
                        class="combobox"
                        :dataSource="[]"
                        :allowFiltering="true"
                      />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w100">Dung lượng</div>
                    <div class="value">
                      <ejs-dropdownlist
                        class="combobox"
                        :dataSource="[]"
                        :allowFiltering="true"
                      />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w100">Số lượng</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        v-model="txtTenThietBi"
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="box-move-select-table">
              <div class="box-col box-form">
                <div class="legend-title">
                  <div class="pull-left">Thiết bị đã gán</div>
                  <div class="clearfix"></div>
                </div>
                <div class="table-content" style="height: 200px">
                  <DataGrid
                    v-bind:columns="thietbiDG.headers"
                    v-bind:dataSource="thietbiDG.data"
                    :enable-paging-server="false"
                    :showColumnCheckbox="true"
                    :allowPaging="true"
                    :showFilter="true"
                    ref="DSHDDaGan"
                  >
                  </DataGrid>
                </div>
              </div>
              <div class="actions w50">
                <button class="btn">
                  <span class="fa fa-angle-right"></span>
                </button>
                <button class="btn">
                  <span class="fa fa-angle-left"></span>
                </button>
              </div>
              <div class="box-col box-form">
                <div class="legend-title">
                  <div class="pull-left">Danh sách thiết bị</div>
                  <div class="clearfix"></div>
                </div>
                <div class="table-content" style="height: 200px">
                  <DataGrid
                    v-bind:columns="DSThietBi.headers"
                    v-bind:dataSource="DSThietBi.data"
                    :enable-paging-server="false"
                    :showColumnCheckbox="true"
                    :allowPaging="true"
                    :showFilter="true"
                  >
                  </DataGrid>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            Danh sách thuê bao được khảo sát --------
          </div>
          {{ lbTong }}
          <div class="table-content mart10">
            <DataGridCustom
              ref="ds_thuebao"
              v-bind:columns="columnsDSTB"
              v-bind:dataSource="ds_thuebao"
              :showColumnCheckbox="false"
              :showFilter="true"
              :enabledSelectFirstRow="true"
              :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
              panelDataHeight="270"
              :allowPaging="true"
              :enablePagingServer="false"
            ></DataGridCustom>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Chi tiết</div>
          <div class="table-content mart10">
            <DataGridCustom
              ref="ds_chitiet"
              v-bind:columns="columnsDSCT"
              v-bind:dataSource="ds_chitiet"
              :showColumnCheckbox="false"
              :showFilter="true"
              :enabledSelectFirstRow="true"
              :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
              panelDataHeight="270"
              :allowPaging="true"
              :enablePagingServer="false"
            ></DataGridCustom>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
export default {
  data: () => ({
    txtThietBi: "",
    txtLoaiThietBi: "",
    thietbiDG: {
      headers: [
        {
          fieldName: "",
          headerText: "Tên thiết bị",
        },
        {
          fieldName: "",
          headerText: "Ngày bắt đầu",
        },
        {
          fieldName: "",
          headerText: "Ngày kết thúc",
        },
      ],
      data: [],
    },
    DSThietBi: {
      headers: [
        {
          fieldName: "",
          headerText: "Tên thiết bị",
        },
        {
          fieldName: "",
          headerText: "Loại thiết bị",
        },
        {
          fieldName: "",
          headerText: "Kiểu thiết bị",
        },
      ],
      data: [],
    },
  }),
  methods: {
    showDialog() {
      this.$refs.popupTraCuuPTM.show();
    },
    hideDialog() {
      this.$refs.popupTraCuuPTM.hide();
    },
  },
};
</script>

<style>
</style>