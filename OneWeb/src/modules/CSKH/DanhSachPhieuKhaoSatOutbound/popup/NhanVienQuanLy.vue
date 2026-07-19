<template>
  <!-- <b-modal
    id="popup-nhanVienQuanLy"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @show="onShow"
  > -->
  <ejs-dialog
    :enableResize="true"
    :allowDragging="true"
    ref="NhanVienQuanLy"
    :header="'Thông tin chăm sóc KH'"
    showCloseIcon="true"
    width="90%"
    :visible="false"
    :open="dialogOpen"
    :close="closeDialog"
    :overlayClick="closeDialog"
    :isModal="true"
>
    <div class="modal-content popup-box">
      <!-- <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Thông tin chăm sóc KH
        </div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popup-nhanVienQuanLy')"></div>
      </div> -->
      <div class="popup-body">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title mart0">Thông tin khách hàng</div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">Loại HĐ</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        :value="txtLoaiHD"
                         disabled
                        :title="txtLoaiHD"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">Mã GD</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        :value="txtMaGD"
                        disabled
                        :title="txtMaGD"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">Mã KH</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        :value="txtMaKH"
                        disabled
                        :title="txtMaKH"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">Loại KH lớn</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        :value="txtLoaiKH"
                        disabled
                        :title="txtLoaiKH"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w80">Tên KH</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    :value="txtTenKH"
                    disabled
                    :title="txtTenKH"
                  />
                </div>
              </div>
              <div class="info-row">
                <div class="key w80">Địa chỉ KH</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    :value="txtDiaChiKH"
                    disabled
                    :title="txtDiaChiKH"
                  />
                </div>
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title mart0">Danh sách thuê bao</div>
              <div class="table-content">
                 <DataGrid3 :columns="fieldsDSThueBao" ref="dsThueBao"
                    :dataSource="pDsCSKH" 
                    @selectedRowChanged="onRowDSThueBaoSelected"
                    :allowPaging="true"
                    :enablePagingServer="false"
                    dataKeyField="STT" :showFilter="true">
                </DataGrid3>
                <!-- <TableWithSearch
                  :fields="fieldsDSThueBao"
                  :getDataFunc="fetchDSThueBao"
                  ref="dsThueBao"
                  :showHeader="false"
                  :pageSizeList="pageSizeListDefault"
                /> -->
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title mart0">
                Danh sách nhân viên kỹ thuật quản lý địa bàn
              </div>
              <div class="table-content">
                <!-- <TableWithSearch
                  :fields="fieldsDSNhanVienDiaBan"
                  :getDataFunc="fetDSNhanVienKyThuat"
                  ref="dsNhanVienKyThuat"
                  :showHeader="false"
                  :pageSizeList="pageSizeListDefault"
                /> -->
                <DataGrid3 :columns="fieldsDSNhanVienDiaBan" ref="gridNVKyThuat"
                    :dataSource="pDsNVKyThuat" 
                    :allowPaging="true"
                    :enablePagingServer="false"
                    dataKeyField="STT" :showFilter="true">
                </DataGrid3>
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title mart0">
                Danh sách nhân viên kinh doanh quản lý địa bàn
              </div>
              <div class="table-content">
                <DataGrid3 :columns="fieldsDSNhanVienDiaBan" ref="gridNVKinhDoanh"
                    :dataSource="pDsNVKinhDoanh"
                    :allowPaging="true"
                    :enablePagingServer="false"
                    dataKeyField="STT" :showFilter="true">
                </DataGrid3>
                <!-- <TableWithSearch
                  :fields="fieldsDSNhanVienDiaBan"
                  :getDataFunc="fetDSNhanVienKinhDoanh"
                  ref="dsNhanVienKyThuat"
                  :showHeader="false"
                  :pageSizeList="pageSizeListDefault"
                /> -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  <!-- </b-modal> -->
  </ejs-dialog>
</template>
<script>
import moment from "moment";
import { mapActions, mapState, mapGetters } from "vuex";
import TableWithSearch from "../../utils/TableWithSearch.vue";
import CustomDataGrid from "../../utils/CustomDataGrid.vue";
import DanhSachPhieuKhaoSatOutboundApi from "../../api/DanhSachPhieuKhaoSatOutboundApi";
export default {
  name: "NhanVienQuanLy",
  props: ["vid", "vloai"],
  components: {
    TableWithSearch,
    datagrid: CustomDataGrid
  },
  async mounted() {
  },
  data() {
    return {
      dateConfig: {
        altInput: true,
        dateFormat: "d/m/Y",
        altFormat: "d/m/Y",
      },
      fieldsDSThueBao: [
        {
          fieldName: "MA_TB",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "MA_LT",
          headerText: "Số ảo",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "LOAIHINH_TB",
          headerText: "Loại hình",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_TB",
          headerText: "Tên thuê bao",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "DIACHI_TB",
          headerText: "Địa chỉ lắp đặt",
          allowFiltering: true,
          allowSorting: false,
        },
      ],
      fieldsDSNhanVienDiaBan: [
        {
          fieldName: "STT",
          headerText: "STT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "MA_NV",
          headerText: "Mã NV",
          allowFiltering: true,
          allowSorting: false,
        },
         {
          fieldName: "TEN_NV",
          headerText: "Tên NV",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_DV",
          headerText: "Đơn vị",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "SO_DT",
          headerText: "Số ĐT",
          allowFiltering: true,
          allowSorting: false,
        },
      ],
      pageSizeListDefault: [5, 10],
      txtMaGD: "",
      txtMaKH: "",
      txtTenKH: "",
      txtDiaChiKH: "",
      txtLoaiHD: "",
      txtLoaiKH: "",

      pDsNVKyThuat: [],
      pDsNVKinhDoanh: [],
      pDsCSKH: [],
    };
  },
  computed: {
    ...mapGetters("danhsachphieukhaosatoutbound", [
      "getdsLoaiNhomKhaoSatGetter",
      "getdsTrangThaiLuotGiaoKhaoSatGetter",
      "getdsTrangThaiPhieuGetter",
      "getdsThongTinCSKHThueBaoGetter",
      "getdsNhanVienKyThuatDiaBanGetter",
      "getdsNhanVienKinhDoanhDiaBanGetter",
    ]),
  },
  methods: {
    ...mapActions("danhsachphieukhaosatoutbound", [
      "getdsLoaiNhomKhaoSat",
      "getdsTrangThaiLuotGiaoKhaoSat",
      "getdsTrangThaiPhieu",
      "getThongTinKhachHangHopDong",
      "getdsThongTinCSKHThueBao",
      "getdsNhanVienKyThuatDiaBan",
      "getdsNhanVienKinhDoanhDiaBan"
    ]),
    async onShow() {
      await this.fetchData()
    },
    async fetchData() {
      this.loading(true);
      try {
        //@fakedata
        let postData = {
          loai: this.vloai,
          id: this.vid,
        }
        // let postData = {
        //   loai: 1,
        //   id: 843751,
        // };
        let response =
          await DanhSachPhieuKhaoSatOutboundApi.getThongTinKhachHangHopDong(
            this.axios,
            postData
          );

        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.data.data
        ) {
          let data = response.data.data.data;
          console.log(response)
          this.txtMaGD = this.vloai == 2 ? data[0].MA_GD : "";
          this.txtMaKH = data[0].MA_KH;
          this.txtTenKH = data[0].TEN_KH;
          this.txtDiaChiKH = data[0].DIACHI_KH;
          this.txtLoaiHD = this.vloai == 2 ? data[0].TEN_LOAIHD : "";
          this.txtLoaiKH = data[0].TEN_LKHL;
        }

        let dataCSKH = {
          loai: this.vloai,
          id: this.vid,
          pageNo: 1,
          pageSize: 10000,
        }

        // let dataNVKyThuat= {
        //   loai: this.vloai,
        //   id: this.vid,
        //   pageNo: 1,
        //   pageSize: 10000,
        //   loaiNvId: 51
        // }

        // let dataNVKinhDoanh = {
        //   loai: this.vloai,
        //   id: this.vid,
        //   pageNo: 1,
        //   pageSize: 10000,
        //   loaiNvId: 52
        // }

        this.pDsCSKH = await this.fetchDSCSKH(dataCSKH)
        // this.pDsNVKyThuat = await this.fetchDSNhanvien(dataNVKyThuat)
        // this.pDsNVKinhDoanh = await this.fetchDSNhanvien(dataNVKinhDoanh)
      } catch (e) {
        console.log(e)
        // this.$root.toastError(e);
      } finally {
        this.loading(false);
      }
    },
    async fetchDSThueBao(pageNo, pageSize) {
      this.loading(true);
      let response = null;
      try {
        //@fakedata
        let postData = {
          loai: this.vloai,
          id: this.vid,
          pageNo: pageNo,
          pageSize: pageSize
        }
        // let postData = {
        //   loai: 1,
        //   id: 843751,
        //   pageNo: pageNo,
        //   pageSize: pageSize,
        // };
        console.log("postData", postData);
        response =
          await DanhSachPhieuKhaoSatOutboundApi.getdsThongTinCSKHThueBao(
            this.axios,
            postData
          );
      } catch (e) {
      } finally {
        this.loading(false);
      }

      return response;
      //console.log(response)
    },
    async fetDSNhanVienKyThuat(data) {
      // return await this.fetchDSNhanVienDiaBan(pageNo, pageSize, 51);
    },
    async fetchDSNhanvien(data) {
      try {
        let rs = await this.$root.context.post('/web-cskh/api/phieu-khao-sat/nvql-dia-ban?pageNo=1&pageSize=1000', data)
        if (rs.error_code === "BSS-00000000") {
          let ds = rs.data.data || []
          ds.forEach((e, i) => e.STT = i + 1)
          return ds
        }
      }
      catch (e) {
        console.log(e)
      }
      return []
    },
    async fetchDSCSKH(data) {
      try {
        let rs = await this.$root.context.get('/web-cskh/api/phieu-khao-sat/cskh-thue-bao', data)
        if (rs.error_code === "BSS-00000000") {
          let ds = rs.data.data || []
          ds.forEach((e, i) => e.STT = i + 1)
          return ds
        }
      }
      catch (e) {
        console.log(e)
      }
      return []
    },
    async fetDSNhanVienKinhDoanh(pageNo, pageSize) {
      // return await this.fetchDSNhanVienDiaBan(pageNo, pageSize, 52);
    },
    async fetchDSNhanVienDiaBan(pageNo, pageSize, loaiNV) {
      this.loading(true);
      let response = null;
      try {
        //@fakedata
        let postData = {
          loai: this.vloai,
          id: this.vid,
        }
        // let postData = {
        //   loai: 2,
        //   id: 4180223,
        //   loaiNvId: loaiNV,
        // };
        console.log("postData", postData);
        response = await DanhSachPhieuKhaoSatOutboundApi.getdsNhanVienDiaBan(
          this.axios,
          pageNo,
          pageSize,
          postData
        );
      } catch (e) {
      } finally {
        this.loading(false);
      }

      return response;
      //console.log(response)
    },
    async onRowDSThueBaoSelected(dataItem) {
      if (dataItem) {
        try {
          console.log(dataItem)
          this.$root.showLoading(true)
          let dataNVKyThuat= {
            loai: this.vloai,
            id: this.vloai == 1 ? dataItem.THUEBAO_ID : dataItem.HDTB_ID,
            pageNo: 1,
            pageSize: 10000,
            loaiNvId: 51
          }

          let dataNVKinhDoanh = {
            loai: this.vloai,
            id: this.vloai == 1 ? dataItem.THUEBAO_ID : dataItem.HDTB_ID,
            pageNo: 1,
            pageSize: 10000,
            loaiNvId: 52
          }

          this.pDsNVKyThuat = await this.fetchDSNhanvien(dataNVKyThuat)
          this.pDsNVKinhDoanh = await this.fetchDSNhanvien(dataNVKinhDoanh)
          this.$nextTick(() => {
            this.$refs['gridNVKyThuat'].setCurrentSelectedRow(0)
            this.$refs['gridNVKinhDoanh'].setCurrentSelectedRow(0)
          })
        }
        catch (e) {
          console.log(e)
        }
        finally {
          this.$root.showLoading(false)
        }
      }
    },
    openDialog() {
        this.$refs.NhanVienQuanLy.show();
    },
    closeDialog() {
        this.$refs.NhanVienQuanLy.hide();
    },
    async dialogOpen() {
      await this.onShow()
    },
  },
};
</script>
