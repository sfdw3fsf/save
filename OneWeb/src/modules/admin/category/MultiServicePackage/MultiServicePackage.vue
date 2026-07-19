<template>
  <div class="main-wrapper">
    <!-- <KBreadCrumb title="Quản lý gói đa dịch vụ" /> -->
    <breadcrumb :header="header" />
    <ActionTop :actions="actions" @onActionClick="onActionClick" />

    <div class="page-content">
      <div class="box-grid-ver" style="height: 1250px">
        <div class="grid-stack-box" id="box1">
          <div class="box-col box-form ovauto" style="width: calc(45% - 8px)">
            <div class="legend-title">
              <span class="icon fa fa-angle-up"></span> Thông tin gói đa dịch vụ
            </div>
            <div class="list-checks marb7">
              <div class="item">
                <div class="check-action">
                  <input
                    type="checkbox"
                    v-model="thongtingoidadichvu.checkCuocTronThang"
                    class="check"
                  />
                  <span class="name">Cước tròn tháng</span>
                </div>
              </div>
              <div class="item">
                <div class="check-action">
                  <input
                    type="checkbox"
                    v-model="thongtingoidadichvu.checkConHieuLuc"
                    class="check"
                  />
                  <span class="name">Còn hiệu lực</span>
                </div>
              </div>
              <div class="item">
                <div class="check-action">
                  <input
                    type="checkbox"
                    v-model="thongtingoidadichvu.checkTuDinhNghiaCuoc"
                    class="check"
                  />
                  <span class="name">Tự định nghĩa cước</span>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-8 col-12">
                <div class="info-row">
                  <div class="key w90">Công văn (*)</div>
                  <div class="value">
                    <ejs-dropdownlist
                      :allowFiltering="true"
                      v-model="congvan_selected"
                      @change="changeCongVan"
                      :filtering="onFilteringCongVan"
                      :dataSource="danhsach_congvan"
                      :fields="{ text: 'socongvan', value: 'congvan_id' }"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w90">Mã gói (*)</div>
                  <div class="value">
                    <input
                      type="text"
                      ref="inputMaGoi"
                      v-model="thongtingoidadichvu.input_magoi"
                      class="form-control highlight"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w90">Từ ngày (*)</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <k-date-picker
                        v-model="tu_ngay"
                        format="DD/MM/YYYY HH:mm:ss"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w90">Đến ngày (*)</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <k-date-picker
                        v-model="den_ngay"
                        format="DD/MM/YYYY HH:mm:ss"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w90">Tháng HL (*)</div>
                  <div class="value">
                    <input
                      type="text"
                      ref="inputThangHL"
                      v-model="thongtingoidadichvu.input_thang_hl"
                      @keypress="isNumber($event)"
                      class="form-control tright"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-8 col-12">
                <div class="info-row">
                  <div class="key w90">Tên gói (*)</div>
                  <div class="value">
                    <input
                      type="text"
                      ref="inputTenGoi"
                      v-model="thongtingoidadichvu.input_tengoi"
                      class="form-control"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w90">Tháng hủy</div>
                  <div class="value">
                    <input
                      type="text"
                      v-model="thongtingoidadichvu.input_thang_huy"
                      class="form-control tright"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w90">Tiền (*)</div>
                  <div class="value">
                    <input
                      type="text"
                      ref="inputTien"
                      v-model="cp_tien_ext"
                      @keypress="isNumber($event)"
                      class="form-control tright"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w90">Vat (*)</div>
                  <div class="value">
                    <input
                      type="text"
                      ref="inputVat"
                      v-model="cp_vat_ext"
                      @keypress="isNumber($event)"
                      class="form-control tright"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w90">Mã ghép</div>
                  <div class="value">
                    <ejs-dropdownlist
                      :allowFiltering="true"
                      v-model="manhghep_selected"
                      :dataSource="danhsach_manhghep"
                      :fields="{ text: 'maghep', value: 'maghep_id' }"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Mô tả (*)</div>
              <div class="value">
                <textarea
                  name=""
                  ref="inputMota"
                  v-model="thongtingoidadichvu.input_mota"
                  class="form-control"
                  style="height: 60px; resize: none"
                ></textarea>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Ghi chú</div>
              <div class="value">
                <input
                  type="text"
                  v-model="thongtingoidadichvu.input_ghichu"
                  class="form-control"
                />
              </div>
            </div>
          </div>
          <div class="gutter gutter-horizontal" style="width: 16px"></div>
          <div class="box-col" style="width: calc(55% - 8px)">
            <div class="nav tabs tab-over">
              <a
                href="#tab1"
                data-toggle="tab"
                @click.prevent="changeTab(1)"
                :class="{ active: currentTab == 1 }"
                class="minw0"
                >Đối tượng TB</a
              >
              <a
                href="#tab2"
                data-toggle="tab"
                @click.prevent="changeTab(2)"
                :class="{ active: currentTab == 2 }"
                class="minw0"
                >Hướng gọi</a
              >
              <a
                href="#tab3"
                data-toggle="tab"
                @click.prevent="changeTab(3)"
                :class="{ active: currentTab == 3 }"
                class="minw0"
                >Dịch vụ GT</a
              >
              <a
                href="#tab4"
                data-toggle="tab"
                @click.prevent="changeTab(4)"
                :class="{ active: currentTab == 4 }"
                class="minw0"
                >Nhóm tích hợp</a
              >
              <a
                href="#tab5"
                data-toggle="tab"
                @click.prevent="changeTab(5)"
                :class="{ active: currentTab == 5 }"
                class="minw0"
                >Trang bị</a
              >
              <a
                href="#tab6"
                data-toggle="tab"
                @click.prevent="changeTab(6)"
                :class="{ active: currentTab == 6 }"
                class="minw0"
                >Tòa nhà</a
              >
              <a
                href="#tab7"
                data-toggle="tab"
                @click.prevent="changeTab(7)"
                :class="{ active: currentTab == 7 }"
                class="minw0"
                >Loại nhà</a
              >
            </div>
            <div class="tab-content" style="height: calc(100% - 46px)">
              <div
                class="tab-pane fullh"
                :class="{ active: currentTab == 1 }"
                id="tab1"
              >
                <DoiTuongTB ref="doituong_tb" :goi_id="goi_id" />
              </div>
              <div
                class="tab-pane fullh"
                :class="{ active: currentTab == 2 }"
                id="tab2"
              >
                <HuongGoi ref="huonggoi" :rg_id="rg_id" :dvvt_id="dvvt_id" />
              </div>
              <div
                class="tab-pane fullh"
                :class="{ active: currentTab == 3 }"
                id="tab3"
              >
                <DichVuGT ref="dichvu_gt" :rg_id="rg_id" :dvvt_id="dvvt_id" />
              </div>
              <div
                class="tab-pane fullh"
                :class="{ active: currentTab == 4 }"
                id="tab4"
              >
                <NhomTichHop
                  ref="nhomtichhop"
                  :rg_id="rg_id"
                  :dvvt_id="dvvt_id"
                />
              </div>
              <div
                class="tab-pane fullh"
                :class="{ active: currentTab == 5 }"
                id="tab5"
              >
                <TrangBi ref="trangbi" :goi_id="goi_id" />
              </div>
              <div
                class="tab-pane fullh"
                :class="{ active: currentTab == 6 }"
                id="tab6"
              >
                <ToaNha ref="toanha" :goi_id="goi_id" />
              </div>
              <div
                class="tab-pane fullh"
                :class="{ active: currentTab == 7 }"
                id="tab7"
              >
                <LoaiNha ref="loainha" :goi_id="goi_id" />
              </div>
            </div>
          </div>
        </div>

        <LoaiHinhTocDoMucCuoc
          ref="loaihinh_tocdo_muccuoc"
          :goi_id="goi_id"
          @onSelectedRowGan="onSelectedRowLHTDMCGan"
          @onSelectedBtnGan="BtnGan_LHTDMC"
          @onSelectedBtnBoGan="BtnBoGan_LHTDMC"
        />

        <div class="box-form">
          <div class="legend-title">
            <span class="icon fa fa-angle-up"></span> Danh sách gói đa dịch vụ
          </div>
          <!-- <k-table
            :columns="columns"
            :dataSources="dataSources"
            @onSelectedRow="onSelectedRow"
            :rowSelectedIndex="1"
            :allowFilter="true"
          /> -->
          <DataGrid
            class="table-result table-gachle"
            :columns="columns"
            :dataSource="dataSources"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :showColumnCheckbox="false"
            ref="dsGoiDaDV"
            @selectedItemsChanged="onSelectedRow"
          >
          </DataGrid>
        </div>
      </div>
    </div>
    <Footer />
    <!-- Modal -->
    <b-modal id="popupComponents" size="huge" :title="popupComponentHeader" :hide-header="popupComponentHeader?false:true"
    hide-footer modal-class="modal-custom" body-class="modal-body p-0">
    <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr"
      isPopup="true" v-on="popupComponentEvts" @form-close="popupClosed"></component>
    </b-modal>
    <QLCongVanModal ref="ql_congvan" :goi_id="goi_id" />
    <popupThemChiTietKm ref="popupThemChiTietKm" />
    <CamKetGoiDaDichVuModal
      ref="camketgoidadichvu"
      :goi="rowSelected ? rowSelected : {}"
    />
  </div>
</template>
<script>
import Footer from "@/components/FooterStyle.vue";
import breadcrumb from "@/components/kylq_components/breadcrumb.vue";
import DataGrid from "@/components/Controls/DataGrid";
// import KTable from "@/components/kylq_components/KTable.vue";
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import QLCongVanModal from "./QLCongVanModal.vue";
import CamKetGoiDaDichVuModal from "./CamKetGoiDaDichVuModal.vue";
import LoaiHinhTocDoMucCuoc from "./LoaiHinhTocDoMucCuoc.vue";
import DoiTuongTB from "./TabContent/DoiTuongTB.vue";
import HuongGoi from "./TabContent/HuongGoi.vue";
import DichVuGT from "./TabContent/DichVuGT.vue";
import NhomTichHop from "./TabContent/NhomTichHop.vue";
import TrangBi from "./TabContent/TrangBi.vue";
import ToaNha from './TabContent/ToaNha.vue'
import LoaiNha from './TabContent/LoaiNha.vue'
import MultiServicePackageAPI from "./MultiServicePackageAPI";
import popupThemChiTietKm from "@/modules/admin/category/MultiServicePackage/popups/popupThemChiTietKm.vue"
import {
  DataManager,
  Query,
  ODataV4Adaptor,
  Predicate,
} from "@syncfusion/ej2-data";
import KDatePicker from "@/components/kylq_components/KDatePicker.vue";
import KBreadCrumb from '@/components/kylq_components/mirana/KBreadCrumb.vue'
import moment from "moment";

export default {
  name: "MultiServicePackage",
  components: {
    Footer,
    breadcrumb,
    DataGrid,
    // KTable,
    ActionTop,
    QLCongVanModal,
    CamKetGoiDaDichVuModal,
    LoaiHinhTocDoMucCuoc,
    DoiTuongTB,
    HuongGoi,
    TrangBi,
    DichVuGT,
    NhomTichHop,
    KDatePicker,
    KBreadCrumb,
    ToaNha,
    LoaiNha,
    popupThemChiTietKm
  },
  computed: {
    goi_id() {
      if (this.rowSelected == null) {
        return 0;
      }
      return this.rowSelected.goi_id;
    },
    rg_id() {
      if (this.rowSelectedLHTDMC == null) {
        return 0;
      }
      return this.rowSelectedLHTDMC.rg_id;
    },
    dvvt_id() {
      if (this.rowSelectedLHTDMC == null) {
        return 0;
      }
      return this.rowSelectedLHTDMC.dichvuvt_id;
    },
    cp_tien_ext: {
      get() {
        return this.numberWithCommas(this.thongtingoidadichvu.input_tien);
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.thongtingoidadichvu.input_tien = Number(replaced);
      },
    },
    cp_vat_ext: {
      get() {
        return this.numberWithCommas(this.thongtingoidadichvu.input_vat);
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.thongtingoidadichvu.input_vat = Number(replaced);
      },
    },
  },
  data() {
    return {
      popupComponentName: '',
      popupComponentHeader: '',
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {},
      dulieu:'',
      tt_nd:'',
      phanvung_id:'',
      header: {
        title: 'Quản lý gói đa dịch vụ',
        list: [
          { name: 'Dữ liệu', link: { name: 'Ui.cards' } },
          { name: 'Phát triển thuê bao', link: { name: 'Ui.cards' } },
          { name: 'Bảng giá - Khuyến mại - Gói cước', link: { name: 'Ui.cards' } },
          { name: 'Gói đa dịch vụ', link: { name: 'Ui.cards' } },
        ]
      },
      actions: [
        {
          id: "nhapmoi",
          name: "Nhập mới (F5)",
          active: true,
          icon_class: "one-file-plus",
        },
        {
          id: "huy",
          name: "Huỷ",
          active: true,
          icon_class: "nc-icon-glyph ui-1_circle-remove",
        },
        {
          id: "ghilai",
          name: "Ghi lại (F9)",
          active: true,
          icon_class: "one-save",
        },
        {
          id: "xoa",
          name: "Xoá",
          active: true,
          icon_class: "one-trash",
        },
        {
          id: "congvan",
          name: "Công văn",
          active: true,
          icon_class: "one-clipart-1",
        },
        {
          id: "camket",
          name: "Cam kết",
          active: true,
          icon_class: "nc-icon-glyph files_copy",
        },
        {
          id: "ctkm",
          name: "CT khoản mục",
          active: true,
          icon_class: "one-file-check",
        },
        {
          id: "copygoidaDV",
          name: "Copy gói đa DV",
          active: true,
          icon_class: "icon one-copy",
        },
        {
          id: "copygoidaDVtinh",
          name: "Copy gói đa DV cho các tỉnh",
          active: true,
          icon_class: "icon one-copy",
        },
      ],
      columns: [
        {
          fieldName: "goi_id",
          headerText: "ID",
          allowFiltering: true,
        },
        {
          fieldName: "ten_goi",
          headerText: "Tên gói",
          allowFiltering: true,
        },
        {
          fieldName: "socongvan",
          headerText: "Số công văn",
          allowFiltering: true,
        },
        {
          fieldName: "tien",
          headerText: "Tiền",
          allowFiltering: true,
          type: "Number",
          format: "N0",
        },
        {
          fieldName: "vat",
          headerText: "Vat",
          allowFiltering: true,
          type: "Number",
          format: "N0",
        },
        {
          fieldName: "trangthai",
          headerText: "Trạng thái",
          allowFiltering: true,
          customAttributes: { class: "red" },
        },
        {
          fieldName: "goi_vnp_id",
          headerText: "Gói VNP",
          allowFiltering: true,
        },
      ],
      dataSources: [],
      currentTab: 1,
      danhsach_congvan: [],
      congvan_selected: null,
      danhsach_manhghep: [],
      manhghep_selected: null,
      tu_ngay: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
      den_ngay: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
      isFirstChangeCongVan: true,
      thongtingoidadichvu: {
        checkCuocTronThang: false,
        checkConHieuLuc: false,
        checkTuDinhNghiaCuoc: false,
        input_magoi: "",
        input_tengoi: "",
        input_thang_hl: "",
        input_thang_huy: "",
        input_tien: "",
        input_vat: "",
        input_mota: "",
        input_ghichu: "",
        goi_vnp_id: 0,
      },
      rowSelected: null,
      rowSelected_callback: null,
      rowSelectedLHTDMC: null,
      nhapmoi: false,
      ds_huonggoi_all: [],
      ds_dichvugt_all: [],
      ds_nhomth_all: [],
    };
  },
  methods: {
    numberWithCommas(n) {
      var parts = n.toString().split(".");
      return (
        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
        (parts[1] ? "." + parts[1] : "")
      );
    },
    async onActionClick(action) {
      if (action.id == "nhapmoi") {
        this.nhapmoi = true;
        this.rowSelected = null;
        this.setActiveActions(1);
      } else if (action.id == "huy") {
        this.nhapmoi = false;
        // this.rowSelected = this.rowSelected_callback;
        if (this.rowSelected != null) {
          //init đối tượng tb
          await this.$refs.doituong_tb.initDulieu();
          //init tab trang bị
          await this.$refs.trangbi.initDulieu();
          //hienthi_loaihinh LoaiHinhTocDoMucCuoc
          await this.$refs.loaihinh_tocdo_muccuoc.hienthi_loaihinh();
          await this.onSelectedRow(this.rowSelected);
          await this.$refs.huonggoi.initDulieu();
          await this.$refs.dichvu_gt.initDulieu();
          await this.$refs.nhomtichhop.initDulieu();
        } else {
          if (this.dataSources.length > 0) {
            this.rowSelected = this.rowSelected_callback;
            await this.onSelectedRow(this.rowSelected);
          } else {
            this.setActiveActions(0);
          }
        }
      } else if (action.id == "ghilai") {
        await this.ghiDuLieu();
      } else if (action.id == "xoa") {
        if (this.rowSelected == null) {
          this.$alert("Hãy chọn gói để thực hiện thao tác xoá!", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "warning",
          });
          return;
        }
        this.$confirm("Bạn thật sự muốn xóa dữ liệu không?", "Thông báo", {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Huỷ",
        }).then(() => {
          this.sp_del_goidadv(this.rowSelected.goi_id);
        });
      } else if (action.id == "congvan") {
        if (this.rowSelected == null) {
          this.$alert("Hãy chọn gói!", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "warning",
          });
          return;
        }
        this.$refs.ql_congvan.showModal();
      } else if (action.id == "camket") {
        if (this.rowSelected == null) {
          this.$alert("Hãy chọn gói!", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "warning",
          });
          return;
        }
        this.openFormCamKet();
        //this.$refs.camketgoidadichvu.showModal()
      } else if (action.id == "ctkm") {
        if (this.rowSelected == null) {
          this.$alert("Hãy chọn gói!", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "warning",
          });
          return;
        }
        this.$refs.popupThemChiTietKm.vgoi_id = this.goi_id
        this.$refs.popupThemChiTietKm.vrg_id = this.rg_id
        this.$refs.popupThemChiTietKm.vdichvuvt_id = this.dvvt_id
        this.$refs.popupThemChiTietKm.vloaitb_id = this.rowSelected.loaitb_id
        this.$refs.popupThemChiTietKm.tu_ngay = this.tu_ngay
        this.$refs.popupThemChiTietKm.den_ngay = this.den_ngay
        this.$refs.popupThemChiTietKm.showDialog()
        //this.$refs.camketgoidadichvu.showModal()
      }else if (action.id == "copygoidaDV") {
        console.log("đã nhấn")
        if (this.rowSelected == null) {
          this.$alert("Hãy chọn gói!", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "warning",
          });
          return;
        }
        await this.click_copygoidaDV_1()
      }else if (action.id == "copygoidaDVtinh") {
        console.log("đã nhấn")
        if (this.rowSelected == null) {
          this.$alert("Hãy chọn gói!", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "warning",
          });
          return;
        }
        await this.click_copygoidaDVtinh()
      }
    },
    async click_copygoidaDV(){
      console.log('Thiện check nhấn copy',this.rowSelected.goi_id)
      try{
        let params = {
          vphanvung_id:0,
          vgoi_id:this.rowSelected.goi_id
        }
        const response = await MultiServicePackageAPI.nhanban_goi_dadv(this.axios, JSON.stringify(params));
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          const resultData = response.data.data && response.data.data[0];
          if (resultData && resultData.ketqua === 0) {
            this.$toast.success('Copy gói đa dịch vụ thành công');
            // hiển thị lại dữ liệu
            await this.sp_grv_goida_dv();
          } else {
            this.$toast.error(resultData ? resultData.error : 'Có lỗi xảy ra');
          }
        } else {
          this.$toast.error('Có lỗi xảy ra trong quá trình thực hiện');
        }
      }catch(error){
        this.$toast.error('Xảy ra lỗi khi copy gói đa dịch vụ')
      }finally{
        this.loading(false);
      }
    },
    async click_copygoidaDV_1(index){
      this.$bvModal
        .msgBoxConfirm("Bạn có chắc chắn copy gói đa dịch vụ này ko này không?", {
          title: "Xác nhận",
          size: "sm",
          okTitle: "Có",
          cancelTitle: "Không",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            await this.click_copygoidaDV()
            this.loading(false);
          }
        })
        .catch((e) => {
          console.log(e);
          this.loading(false);
        });
    },
    async click_copygoidaDVtinh(){
      console.log('Thiện check nhấn copy tinh',this.rowSelected.goi_id)
      this.showPopupchonTinh()

    },
    showPopupchonTinh : function () {
      this.popupComponent = () =>
        import(
          "@/modules/admin/category/MultiServicePackage/popups/chonTinh.vue"
        );
      this.popupComponentName = "chonTinh";
      this.popupComponentHeader = 'Chọn tỉnh copy gói đa dịch vụ ';
      this.popupComponentData = {
        isPopup: true,
        goiid: this.rowSelected.goi_id,
      }; 
      this.Popup("popupComponents");
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
   async ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async popupClosed(val) {
      this.loading(true);
      try {
        switch (this.popupComponentName) {
          case "chonTinh": 
            console.log("Thiện check, popupClosed called with:", val);
            this.loading(false);
          break;
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail) {
          this.$toast.error("" + ex.data.message_detail);
        } else {
          this.$toast.error("" + ex);
        }
      } finally {
        this.ClosePopup("popupComponents");
        this.loading(false);
      }
    },
    setActiveActions(kieu) {
      this.actions.forEach((item) => {
        if (item.id != "congvan" && item.id != "camket" && item.id != "ctkm" && item.id != "copygoidaDV") {
          item.active = false;
        }
        if (this.phanvung_id == 21 && item.id == "copygoidaDVtinh") {
          item.active = true;
        }
      });
      if (kieu == -1) {
        //bắt đầu
        this.actions[
          this.actions.findIndex((x) => x.id == "ghilai")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "huy")
        ].active = true;
      } else if (kieu == 0) {
        //bắt đầu
        this.actions[
          this.actions.findIndex((x) => x.id == "nhapmoi")
        ].active = true;
        this.clear();
      } else if (kieu == 1) {
        //thêm mới
        this.actions[
          this.actions.findIndex((x) => x.id == "ghilai")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "huy")
        ].active = true;
        this.clear();
      } else if (kieu == 2) {
        //Huỷ
        this.actions[
          this.actions.findIndex((x) => x.id == "nhapmoi")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "xoa")
        ].active = true;
        this.clear();
      } else if (kieu == 3) {
        //edit
        this.actions[
          this.actions.findIndex((x) => x.id == "nhapmoi")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "ghilai")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "huy")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "xoa")
        ].active = true;
      }
    },
    async clear() {
      this.ds_huonggoi_all = [];
      this.ds_dichvugt_all = [];
      this.ds_nhomth_all = [];
      this.congvan_selected =
        this.danhsach_congvan.length > 0
          ? this.danhsach_congvan[0].congvan_id
          : 0;

      this.thongtingoidadichvu.input_thang_hl = "";
      this.thongtingoidadichvu.input_thang_huy = "";
      // rg_id_xoa = "";
      this.thongtingoidadichvu.input_magoi = "";
      this.thongtingoidadichvu.input_tengoi = "";
      this.thongtingoidadichvu.input_tien = "";
      this.thongtingoidadichvu.input_vat = "";
      this.thongtingoidadichvu.input_mota = "";
      this.thongtingoidadichvu.input_ghichu = "";
      this.thongtingoidadichvu.checkTuDinhNghiaCuoc = false;
      this.tu_ngay = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
      this.den_ngay = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");

      // goi_id = 0;
      // rg_id = 0;
      // chkTuDN.Checked = false;

      //clear tab, init tab value 0
      this.rowSelectedLHTDMC = null;
      await this.$refs.doituong_tb.clearDuLieu();
      await this.$refs.trangbi.clearDuLieu();
      //loaihinh, tocdo, muccuoc
      await this.$refs.loaihinh_tocdo_muccuoc.clearDuLieu();
      await this.$refs.huonggoi.clearDuLieu();
      await this.$refs.nhomtichhop.clearDuLieu();
      await this.$refs.dichvu_gt.clearDuLieu();
    },
    changeTab(tab) {
      if (this.currentTab == tab) {
        return;
      }
      this.currentTab = tab;
      // if(this.currentTab==1){
      //     this.$refs.doituong_tb.initDulieu()
      // }else if(this.currentTab==2){
      //     this.$refs.huonggoi.initDulieu()
      // }else if(this.currentTab==3){
      //     this.$refs.dichvu_gt.initDulieu()
      // }else if(this.currentTab==4){
      //     this.$refs.nhomtichhop.initDulieu()
      // }else if(this.currentTab==5){
      //     this.$refs.trangbi.initDulieu()
      // }
    },
    changeCongVan() {
      if (this.congvan_selected == null) {
        return;
      }
      if (this.isFirstChangeCongVan) {
        this.isFirstChangeCongVan = false;
        return;
      }
      this.$refs.loaihinh_tocdo_muccuoc.sp_cbb_goivnp(this.congvan_selected);
    },
    ghiDuLieu() {
      let is_insert = this.actions[
        this.actions.findIndex((x) => x.id == "nhapmoi")
      ].active
        ? false
        : true;
      //Kiểm tra dữ liệu
      if (this.$refs.loaihinh_tocdo_muccuoc.getDsLoaiHinhGan().length == 0) {
        this.$alert("Chưa có loại hình thuê bao nào được gán!", "Thông báo", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "info",
        });
        return;
      }
      if (!this.kiemtra_dulieu()) {
        return;
      }
      if (is_insert) {
        this.taodulieu(true);
      } else {
        this.taodulieu(false);
      }

      //thực thi
      this.nhapmoi = false;
    },
    taodulieu: async function (nhapmoi) {
      try {      
        // var i_goi_id = 0;
        // var in_rg_id = 0;
        // var rg_id_xoa = "";
        // if (nhapmoi) {
        //   var promise_goi_id = this.getkey("GOI_ID");
        //   // var promise_rg_id = this.getkey("RG_ID");
        //   i_goi_id = await promise_goi_id;
        //   // in_rg_id = await promise_rg_id;
        // }

        //Tạo dữ liệu
        var input = {
          is_themmoi: nhapmoi ? 0 : 1,
          congvan_id: this.congvan_selected.toString(),
          goi_id: nhapmoi ? 0 : this.goi_id.toString(),
          rg_id: this.rg_id.toString(),
          rg_id_xoa: await this.getDsRgIDXoa(),
          json_dadv_lhtb: await this.getDLLoaiHinhTDMC(nhapmoi),
          json_doituong: await this.getDLDoiTuong(nhapmoi),
          json_dvgt: await this.getDLDichVuGT(),
          json_goidadv: await this.getDLGoiDaDV(nhapmoi),
          json_goidadv_tcdv: await this.getDLNhomTH(),
          json_goidadv_trangbi: await this.getDLTrangBi(nhapmoi),
          json_huonggoi: await this.getDLHuongGoi(),
          json_toanha: await this.getDLToaNha(),
          json_loainha: await this.getDLLoaiNha(),
        };
        // Thay đổi theo yêu cầu jira https://cntt.vnpt.vn/browse/BSS-96626
        // 27/12/2023: https://cntt.vnpt.vn/browse/BSS-109346
        //await this.sp_upd_goitichhop(input);
       await this.sp_upd_goitichhop_v2(input);
      } catch (e) {
        
      }
    },
    getDLGoiDaDV: async function (nhapmoi) {
      try {
        var dsGoiDaDV = [];
        var rowdata = {};
        rowdata.THANG_HUONG = this.thongtingoidadichvu.input_thang_hl;
        rowdata.THANG_HUY = this.thongtingoidadichvu.input_thang_huy;
        rowdata.GOI_ID = nhapmoi ? 0 : this.goi_id;
        rowdata.MA_GOI = this.thongtingoidadichvu.input_magoi;
        rowdata.TEN_GOI = this.thongtingoidadichvu.input_tengoi;
        rowdata.TIEN = this.thongtingoidadichvu.input_tien;
        rowdata.VAT = this.thongtingoidadichvu.input_vat;
        rowdata.MOTA = this.thongtingoidadichvu.input_mota;
        rowdata.NGAY_BD = this.tu_ngay;
        rowdata.NGAY_KT = this.den_ngay;
        rowdata.TRANGTHAI = this.thongtingoidadichvu.checkConHieuLuc ? 1 : 0;
        rowdata.CONGVAN_ID = this.congvan_selected;
        rowdata.GHICHU = this.thongtingoidadichvu.input_ghichu;
        rowdata.TINHCUOC = this.thongtingoidadichvu.checkTuDinhNghiaCuoc ? 1 : 0;
        rowdata.MA_GHEP = this.manhghep_selected;
        rowdata.MAY_CN = await this.$root.token.getMachine();
        rowdata.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
        rowdata.NGUOI_CN = await this.$root.token.getNguoiDungID();
        rowdata.CUOC_TRONTHANG = this.thongtingoidadichvu.checkCuocTronThang
          ? 1
          : 0;
        rowdata.GOI_VNP_ID = this.$refs.loaihinh_tocdo_muccuoc.getGoiVNPID()
          ? Number(this.$refs.loaihinh_tocdo_muccuoc.getGoiVNPID())
          : 0;
        dsGoiDaDV.push(rowdata);
        return JSON.stringify(dsGoiDaDV);
      } catch (e) {}
    },
    getDLDoiTuong(nhapmoi) {
      try {
        let dsDoiTuong = [];
        let dt = this.$refs.doituong_tb.getDsDoiTuongTBGan();
        if (dt && dt.length > 0) {
          dt.forEach((item, index) => {
            dsDoiTuong.push({
              DOITUONG_ID: item.doituong_id,
              GOI_ID: nhapmoi ? 0 : item.goi_id,
            });
          });
          return JSON.stringify(dsDoiTuong);
        } else {
          return "";
        }
      } catch (e) {}
    },
    getDLHuongGoi: async function () {
      try {
        // let dt = this.$refs.huonggoi.getDsHuongGoiAll();
        var dsHuongGoi = [];
        let dt = this.$refs.huonggoi.getDsHuongGoiGan();
        if (dt.length > 0) {
          var nguoidung_id = await this.$root.token.getNguoiDungID();
          var machine = await this.$root.token.getMachine();
          dsHuongGoi = dt.map((e) => ({
            RG_ID: e.rg_id,
            NHOMDT_ID: e.nhomdt_id ? Number(e.nhomdt_id) : 0,
            PHUT_GIAM: e.phut_giam ? Number(e.phut_giam) : 0,
            TIEN_GIAM: e.tien_giam ? Number(e.tien_giam) : 0,
            PHUT_DAU: e.phut_dau ? Number(e.phut_dau) : 0,
            TYLE_GIAM: e.tyle_giam ? Number(e.tyle_giam) : 0,
            NGAY_CN: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
            NGUOI_CN: nguoidung_id ? nguoidung_id : "",
            MAY_CN: machine ? machine : "",
          }));
          return JSON.stringify(dsHuongGoi);
        } else {
          return "";
        }
      } catch (e) {}
    },
    getDLDichVuGT: async function () {
      try {
        // let dt = this.$refs.dichvu_gt.getDsDichVuGTAll();
        let dt = this.$refs.dichvu_gt.getDsDichVuGTGan();
        var dsdvgt = [];
        if (dt.length > 0) {
          var nguoidung_id = await this.$root.token.getNguoiDungID();
          var machine = await this.$root.token.getMachine();
          dsdvgt = dt.map((e) => ({
            RG_ID: e.rg_id,
            DICHVUGT_ID: e.dichvugt_id ? Number(e.dichvugt_id) : 0,
            TYLE_GIAMCUOC_LD: e.tyle_giamcuoc_ld ? Number(e.tyle_giamcuoc_ld) : 0,
            TYLE_GIAMCUOC_SD: e.tyle_giamcuoc_sd ? Number(e.tyle_giamcuoc_sd) : 0,
            TIEN_GIAMCUOC_SD: e.tien_giamcuoc_sd ? Number(e.tien_giamcuoc_sd) : 0,
            VAT_GIAMCUOC_SD: e.vat_giamcuoc_sd ? Number(e.vat_giamcuoc_sd) : 0,
            DENNGAY: e.denngay && e.denngay.toString() != "" ? e.denngay : "",
            NGAY_CN: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
            NGUOI_CN: nguoidung_id ? nguoidung_id : "",
            MAY_CN: machine ? machine : "",
          }));
          return JSON.stringify(dsdvgt);
        } else {
          return "";
        }
      } catch (e) {}
    },
    getDLNhomTH: async function () {
      try {
        // let dt = this.$refs.nhomtichhop.getDsNhomTHAll();
        let dt = this.$refs.nhomtichhop.getDsNhomTHGan();
        var dsNhomTH = [];
        if (dt.length > 0) {
          var nguoidung_id = await this.$root.token.getNguoiDungID();
          var machine = await this.$root.token.getMachine();
          var ip_cn = await this.$root.token.getIP();
          dsNhomTH = dt.map((e) => ({
            RG_ID: e.rg_id,
            DICHVUVT_ID: e.dichvuvt_id,
            PHUT_GIAM: e.phut_giam ? Number(e.phut_giam) : 0,
            TIEN_GIAM: e.tien_giam ? Number(e.tien_giam) : 0,
            PHUT_DAU: e.phut_dau ? Number(e.phut_dau) : 0,
            TYLE_GIAM: e.tyle_giam ? Number(e.tyle_giam) : 0,
            KIEU: e.kieu ? Number(e.kieu) : 0,
            SOLUONG_TU: e.soluong_tu ? Number(e.soluong_tu) : 0,
            SOLUONG_DEN: e.soluong_den ? Number(e.soluong_den) : 0,
            NGAY_CN: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
            NGUOI_CN: nguoidung_id ? nguoidung_id : "",
            MAY_CN: machine ? machine : "",
            IP_CN: ip_cn ? ip_cn : "",
          }));
          return JSON.stringify(dsNhomTH);
        } else {
          return "";
        }
      } catch (e) {}
    },
    getDLTrangBi(nhapmoi) {
      try {
        let dt = this.$refs.trangbi.getDsTrangBiGan();
        var dsDLTrangBi = [];
        if (dt.length > 0) {
          dsDLTrangBi = dt.map((e) => ({
            GOI_ID: nhapmoi ? 0 : e.goi_id,
            TRANGBI_ID: e.trangbi_id ? Number(e.trangbi_id) : 0,
          }));
          return JSON.stringify(dsDLTrangBi);
        } else {
          return "";
        }
      } catch (e) {}
    },
    getDLToaNha(){
      let dt = this.$refs.toanha.getDsToaNhaGan();
      let dsToaNha = dt && dt.length > 0 ? dt.map(x=>{
        return {
          TOANHA_ID: x.toanha_id
        }
      }) : []
      return JSON.stringify(dsToaNha)
    },
    getDLLoaiNha(){
      let dt = this.$refs.loainha.getDsLoaiNhaGan();
      let dsLoaiNha = dt && dt.length > 0 ? dt.map(x=>{
        return {
          LOAINHA_ID: x.loainha_id
        }
      }) : []
      return JSON.stringify(dsLoaiNha)
    },
    getDLLoaiHinhTDMC: function (nhapmoi) {
      try {
        let dt = this.$refs.loaihinh_tocdo_muccuoc.getDsLoaiHinhGan();
        console.log(" đây là test", dt)
        var dsLoaiHinhTB = [];
        if (dt.length > 0) {
          dsLoaiHinhTB = dt.map((e) => ({
            RG_ID: e.rg_id,
            GOI_ID: nhapmoi ? 0 : e.goi_id,
            SL_TU: e.sl_tu ? Number(e.sl_tu) : 0,
            SL_DEN: e.sl_den ? Number(e.sl_den) : 0,
            TG_TU: e.tg_tu ? Number(e.tg_tu) : 0,
            TG_DEN: e.tg_den ? Number(e.tg_den) : 0,
            GIAMCUOC_TB: e.giamcuoc_tb ? Number(e.giamcuoc_tb.toString().replace(",","")) : 0,
            GIAMCUOC_SD: e.giamcuoc_sd ? Number(e.giamcuoc_sd.toString().replace(",","")) : 0,
            CHUNHOM: e.chunhom ? 1 : 0,
            DONGBO_DL: e.dongbo_dl ? 1 : 0,
            LOAITB_ID: e.loaitb_id ? Number(e.loaitb_id) : 0,
            TOCDO_ID: e.tocdo_id ? Number(e.tocdo_id) : 0,
            MUCCUOC_ID: e.muccuoc_id ? Number(e.muccuoc_id) : 0,
            TIEN_GOI: e.tien_goi ? Number(e.tien_goi.toString().replace(/,/g,'')) : 0,
            VAT_GOI: e.vat_goi ? Number(e.vat_goi.toString().replace(/,/g,'')) : 0,
            CUOC_TB: e.cuoc_tb ? Number(e.cuoc_tb.toString().replace(",","")) : 0,
            CUOC_SD: e.cuoc_sd ? Number(e.cuoc_sd.toString().replace(",","")) : 0,
            GOI_ID_DATG: e.goi_id_datg ? Number(e.goi_id_datg) : 0,
          }));
          return JSON.stringify(dsLoaiHinhTB);
        } else {
          return "";
        }
      } catch (e) {}
    },
    getDsRgIDXoa: function (nhapmoi) {
      try {
        if (!nhapmoi) {
          // var dsgan_bandau = this.$refs.loaihinh_tocdo_muccuoc.dsGan_init;
          var dsgan_hientai =
            this.$refs.loaihinh_tocdo_muccuoc.getDsLoaiHinhGan();
          // var ds_rgid_xoa = dsgan_bandau
          //   .filter((x) => !dsgan_hientai.includes(x))
          //   .map((x) => ({ rg_id: x.rg_id }));
          var ds_rgid_xoa = dsgan_hientai.map((x) => ({ rg_id: x.rg_id }));
          var str = "";
          if (ds_rgid_xoa.length == 1) {
            str = ds_rgid_xoa[0].rg_id.toString();
          } else if (ds_rgid_xoa.length > 1) {
            str = ds_rgid_xoa[0].rg_id.toString();
            ds_rgid_xoa.forEach((x, index) => {
              if (index > 0) {
                str += "," + x.rg_id;
              }
            });
          } else str = "0";

          return str;
        } else {
          return "0";
        }
      } catch (e) {}
    },
    kiemtra_dulieu() {
      if (new Date(moment(this.tu_ngay, "DD/MM/YYYY HH:mm:ss")) > new Date(moment(this.den_ngay, "DD/MM/YYYY HH:mm:ss"))) {
        this.$toast.error("Từ ngày không được lớn hơn Đến ngày!");
        return false;
      }
      if (this.thongtingoidadichvu.input_magoi == null || this.thongtingoidadichvu.input_magoi.trim() == "") {
        this.$alert("Bạn chưa nhập mã gói!", "Thông báo", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "info",
        });
        this.$refs.inputMaGoi.focus();
        return false;
      }
      if (this.thongtingoidadichvu.input_tengoi == null || this.thongtingoidadichvu.input_tengoi.trim() == "") {
        this.$alert("Bạn chưa nhập tên gói!", "Thông báo", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "warning",
        });
        this.$refs.inputTenGoi.focus();
        return false;
      }
      if (this.thongtingoidadichvu.input_tien == null || this.thongtingoidadichvu.input_tien.toString().trim() == "") {
        this.$alert("Bạn chưa nhập tiền gói!", "Thông báo", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "warning",
        });
        this.$refs.inputTien.focus();
        return false;
      }
      if (this.thongtingoidadichvu.input_vat == null || this.thongtingoidadichvu.input_vat.toString().trim() == "") {
        this.$alert("Bạn chưa nhập Vat gói!", "Thông báo", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "warning",
        });
        this.$refs.inputVat.focus();
        return false;
      }
      if (this.thongtingoidadichvu.input_mota == null || this.thongtingoidadichvu.input_mota.trim() == "") {
        this.$alert("Bạn chưa nhập mô tả gói!", "Thông báo", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "warning",
        });
        this.$refs.inputMota.focus();
        return false;
      }
      if (this.thongtingoidadichvu.input_thang_hl == null || this.thongtingoidadichvu.input_thang_hl.trim() == "") {
        this.$alert("Bạn chưa nhập tháng hiệu lực!", "Thông báo", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "warning",
        });
        this.$refs.inputThangHL.focus();
        return false;
      }
      if (this.congvan_selected == null || this.congvan_selected == 0) {
        this.$alert("Hãy chọn công văn hưởng khuyến mại!", "Thông báo", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "warning",
        });
        return false;
      }

      // if (themmoi)
      // {
      //     int hl = bangts.kiemtra_hieuluc_cv(Convert.ToInt32(cboCongVan.EditValue));
      //     if (hl == 0)
      //     {
      //         Message_Box.ShowWarning("Công văn hiện tại đang không còn hiệu lực.");
      //         cboCongVan.Focus();
      //         return false;
      //     }
      // }

      return true;
    },
    async openFormCamKet() {
      //lấy danh sách loại hình thuê bao theo gói, thực hiện check
      try {
        this.loading(true);
        let response = await MultiServicePackageAPI.sp_cbb_loaihinh_tb(
          this.axios,
          this.rowSelected.goi_id
        );
        this.loading(false);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          if (response.data.data.length == 0) {
            this.$alert(
              "Gói này chưa được gán loại hình thuê bao nào!",
              "Thông báo",
              {
                dangerouslyUseHTMLString: true,
                confirmButtonText: "OK",
                type: "info",
              }
            );
            return;
          }
          this.$nextTick(() => {
            this.$refs.camketgoidadichvu.showModal();
          });
        } else {
          if (response && response.data && response.data.message_detail) {
            this.$alert(response.data.message_detail, "", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            });
          } else {
            this.$alert(
              "Đã xảy ra lỗi, thực hiện thao tác không thành công",
              "",
              {
                dangerouslyUseHTMLString: true,
                confirmButtonText: "OK",
                type: "error",
              }
            );
          }
        }
      } catch (e) {
        this.loading(false);
        if (e.data && e.data.message_detail) {
          this.$alert(e.data.message_detail, "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        } else if (
          e.response &&
          e.response.data &&
          e.response.data.message_detail
        ) {
          this.$alert(e.response.data.message_detail, "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        } else {
          this.$alert(
            "Đã xảy ra lỗi, thực hiện thao tác không thành công",
            "",
            {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            }
          );
        }
      }
    },
    async onSelectedRow(item) {
      this.nhapmoi = false;
      this.ds_huonggoi_all = [];
      this.ds_dichvugt_all = [];
      this.ds_nhomth_all = [];
      if (item.length > 0) {
        this.rowSelected = item[0];
        this.rowSelected_callback = item[0];
        //hiển thị thông tin
        this.thongtingoidadichvu.input_magoi =
          item[0].ma_goi != null ? item[0].ma_goi : "";
        this.thongtingoidadichvu.input_tengoi =
          item[0].ten_goi != null ? item[0].ten_goi : "";
        this.thongtingoidadichvu.input_tien =
          item[0].tien != null ? item[0].tien : "";
        this.thongtingoidadichvu.input_vat =
          item[0].vat != null ? item[0].vat : "";
        this.thongtingoidadichvu.input_mota =
          item[0].mota != null ? item[0].mota : "";
        this.thongtingoidadichvu.input_ghichu =
          item[0].ghichu != null ? item[0].ghichu : "";
        this.thongtingoidadichvu.goi_vnp_id =
          item[0].goi_vnp_id != null ? item[0].goi_vnp_id : 0;

        if (item[0].tinhcuoc != null && item[0].tinhcuoc == 1) {
          this.thongtingoidadichvu.checkTuDinhNghiaCuoc = true;
        } else {
          this.thongtingoidadichvu.checkTuDinhNghiaCuoc = false;
        }

        if (item[0].cuoc_tronthang != null && item[0].cuoc_tronthang == 1) {
          this.thongtingoidadichvu.checkCuocTronThang = true;
        } else {
          this.thongtingoidadichvu.checkCuocTronThang = false;
        }

        if (item[0].trangthai != null && item[0].trangthai == 1) {
          this.thongtingoidadichvu.checkConHieuLuc = true;
        } else {
          this.thongtingoidadichvu.checkConHieuLuc = false;
        }

        this.thongtingoidadichvu.input_thang_hl =
          item[0].thang_huong != null ? item[0].thang_huong.toString() : "";
        this.thongtingoidadichvu.input_thang_huy =
          item[0].thang_huy != null ? item[0].thang_huy.toString() : "";
        //"ngay_bd": "2018-04-26 08:10:58",
        this.tu_ngay =
          item[0].ngay_bd != null
            ? moment(item[0].ngay_bd, "YYYY-MM-DD HH:mm:ss").format(
                "DD/MM/YYYY HH:mm:ss"
              )
            : "";
        this.den_ngay =
          item[0].ngay_kt != null
            ? moment(item[0].ngay_kt, "YYYY-MM-DD HH:mm:ss").format(
                "DD/MM/YYYY HH:mm:ss"
              )
            : "";

        if (item[0].congvan_id != null && item[0].congvan_id != "") {
          this.congvan_selected = item[0].congvan_id;
        } else {
          this.congvan_selected = 0;
        }

        if (item[0].ma_ghep != null && item[0].ma_ghep != "") {
          this.manhghep_selected = item[0].ma_ghep;
        } else {
          this.manhghep_selected = null;
        }

        //init đối tượng tb
        // await this.$refs.doituong_tb.initDulieu();
        //init tab trang bị
        // await this.$refs.trangbi.initDulieu();

        //hienthi_loaihinh LoaiHinhTocDoMucCuoc
        // await this.$refs.loaihinh_tocdo_muccuoc.hienthi_loaihinh();
      }

      this.setActiveActions(3);
    },
    //callback form LoaiHinhTocDoMucCuoc
    onSelectedRowLHTDMCGan(item) {
      //trigger call form tab
      this.rowSelectedLHTDMC = item;
      //tab 1 bỏ qua
      // await this.$refs.huonggoi.initDulieu();
      // await this.$refs.dichvu_gt.initDulieu();
      // await this.$refs.nhomtichhop.initDulieu();

      // this.getDsHuongGoiAll();
      // this.getDsDichVuGTAll();
      // this.getDsTichHopAll();
    },
    BtnGan_LHTDMC(item) {
      this.onSelectedRowLHTDMCGan(item.dataSourcesGan[0]);
    },
    BtnBoGan_LHTDMC(item) {
      if (item.dataSourcesGan.length > 0) {
        //Kiểm tra xem RG ID xem đã có hướng gọi gán vào.
        this.onSelectedRowLHTDMCGan(item.dataSourcesGan[0]);
      }
    },
    getDsHuongGoiAll() {
      var huonggoi_tmp = this.$refs.huonggoi.getDsHuongGoiGan();
      if (this.ds_huonggoi_all.length > 0) {
        if (huonggoi_tmp.length > 0) {
          huonggoi_tmp.forEach((item) => {
            const index = this.ds_huonggoi_all.findIndex(
              (x) => x.nhomdt_id == item.nhomdt_id && x.rg_id == item.rg_id
            );
            if (index == -1) {
              this.ds_huonggoi_all.push(item);
            }
          });
        }
      } else {
        this.ds_huonggoi_all = huonggoi_tmp;
      }
    },
    getDsDichVuGTAll() {
      var dichvugt_tmp = this.$refs.dichvu_gt.getDsDichVuGTGan();
      if (this.ds_dichvugt_all.length > 0) {
        if (dichvugt_tmp.length > 0) {
          dichvugt_tmp.forEach((item) => {
            const index = this.ds_dichvugt_all.findIndex(
              (x) => x.dichvugt_id == item.dichvugt_id && x.rg_id == item.rg_id
            );
            if (index == -1) {
              this.ds_dichvugt_all.push(item);
            }
          });
        }
      } else {
        this.ds_dichvugt_all = dichvugt_tmp;
      }
    },
    getDsTichHopAll() {
      var nhomth_tmp = this.$refs.nhomtichhop.getDsNhomTHGan();
      if (this.ds_nhomth_all.length > 0) {
        if (nhomth_tmp.length > 0) {
          nhomth_tmp.forEach((item) => {
            const index = this.ds_nhomth_all.findIndex(
              (x) => x.dichvuvt_id == item.dichvuvt_id && x.rg_id == item.rg_id
            );
            if (index == -1) {
              this.ds_nhomth_all.push(item);
            }
          });
        }
      } else {
        this.ds_nhomth_all = nhomth_tmp;
      }
    },
    onFilteringCongVan(e) {
      var query = new Query();
      let predicate;
      //frame the query based on search string with filter type.
      if (e.text !== "") {
        predicate = new Predicate("socongvan", "contains", e.text, true);
        //predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate);
      }
      //pass the filter data source, filter query to updateData method.
      e.updateData(this.danhsach_congvan, query);
    },
    isNumber(evt) {
      evt = evt ? evt : window.event;
      var charCode = evt.which ? evt.which : evt.keyCode;
      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        evt.preventDefault();
      } else {
        return true;
      }
    },
    async sp_cbb_congvan() {
      try {
        this.loading(true);
        this.danhsach_congvan = [];
        let response = await MultiServicePackageAPI.sp_cbb_congvan(this.axios);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          this.danhsach_congvan = response.data.data;
          if (this.danhsach_congvan.length > 0) {
            this.congvan_selected = this.danhsach_congvan[0].congvan_id;
          }
        } else {
          this.danhsach_congvan = [];
        }
      } catch (e) {
        this.$toast.error("Không load được danh sách công văn");
      } finally {
        this.loading(false);
      }
    },
    async sp_cbb_maghep() {
      try {
        this.loading(true);
        this.danhsach_manhghep = [];
        let response = await MultiServicePackageAPI.sp_cbb_maghep(this.axios);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          this.danhsach_manhghep = response.data.data;
          if (this.danhsach_manhghep.length > 0) {
            this.manhghep_selected = this.danhsach_manhghep[0].maghep_id;
          }
        } else {
          this.danhsach_manhghep = [];
        }
      } catch (e) {
        this.$toast.error("Không load được danh sách mảnh ghép");
      } finally {
        this.loading(false);
      }
    },
    async sp_grv_goida_dv() {
      try {
        this.loading(true);
        this.dataSources = [];
        let response = await MultiServicePackageAPI.sp_grv_goida_dv(this.axios);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          this.dataSources = response.data.data;
          if (this.dataSources.length > 0) {
            this.onSelectedRow(this.dataSources[0]);
          } else {
            this.setActiveActions(1);
          }
        } else {
          this.dataSources = [];
        }
      } catch (e) {
        this.$toast.error("Không load được danh sách gói đa dịch vụ");
      } finally {
        this.loading(false);
      }
    },
    async sp_upd_goitichhop(input) {
      try {
        this.loading(true);
        let response = await MultiServicePackageAPI.sp_upd_goitichhop(
          this.axios,
          input
        );
        this.loading(false);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.$alert("Thành công", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          });
          // hiển thị lại dữ liệu
          await this.sp_grv_goida_dv();
        } else {
          if (response && response.data && response.data.message_detail) {
            this.$alert(response.data.message_detail, "", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            });
          } else {
            this.$alert("Đã xảy ra lỗi, thao tác không thành công!", "", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            });
          }
        }
      } catch (e) {
        //console.log(e)
        this.loading(false);
        if (e.data && e.data.message_detail) {
          this.$alert(e.data.message_detail, "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        } else if (
          e.response &&
          e.response.data &&
          e.response.data.message_detail
        ) {
          this.$alert(e.response.data.message_detail, "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        } else {
          this.$alert("Đã xảy ra lỗi, thao tác không thành công!", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        }
      }
    },
    async sp_upd_goitichhop_v2(input) {
      try {
        this.loading(true);
        let response = await MultiServicePackageAPI.sp_upd_goitichhop_v2(
          this.axios,
          input
        );
        this.loading(false);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.$alert("Thành công", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          });
          // hiển thị lại dữ liệu
          await this.sp_grv_goida_dv();
        } else {
          if (response && response.data && response.data.message_detail) {
            this.$alert(response.data.message_detail, "", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            });
          } else {
            this.$alert("Đã xảy ra lỗi, thao tác không thành công!", "", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            });
          }
        }
      } catch (e) {
        //console.log(e)
        this.loading(false);
        if (e.data && e.data.message_detail) {
          this.$alert(e.data.message_detail, "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        } else if (
          e.response &&
          e.response.data &&
          e.response.data.message_detail
        ) {
          this.$alert(e.response.data.message_detail, "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        } else {
          this.$alert("Đã xảy ra lỗi, thao tác không thành công!", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        }
      }
    },
    async sp_del_goidadv(goi_id) {
      try {
        this.loading(true);
        let response = await MultiServicePackageAPI.sp_del_goidadv(
          this.axios,
          goi_id
        );
        this.loading(false);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.$alert("Xóa dữ liệu thành công !", "Thông báo", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          });
          //load gói đa dịch vụ
          await this.sp_grv_goida_dv();
        } else {
          if (response && response.data && response.data.message_detail) {
            this.$alert(response.data.message_detail, "", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            });
          } else {
            this.$alert("Đã xảy ra lỗi, xoá gói không thành công!", "", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            });
          }
        }
      } catch (e) {
        //console.log(e)
        this.loading(false);
        if (e.data && e.data.message_detail) {
          this.$alert(e.data.message_detail, "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        } else if (
          e.response &&
          e.response.data &&
          e.response.data.message_detail
        ) {
          this.$alert(e.response.data.message_detail, "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        } else {
          this.$alert("Đã xảy ra lỗi, xoá gói không thành công!", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        }
      }
    },
    getkey: async function (key) {
      var input = {
        keyname: key,
      };
      let response = await MultiServicePackageAPI.getKey(this.axios, input);
      if (
        response &&
        response.data &&
        response.data.error_code &&
        response.data.error_code == "BSS-00000000" &&
        response.data.data
      ) {
        return Number(response.data.data);
      } else {
        this.$toast.error("Có lỗi trong quá trình tạo key mới");
        return -1;
      }
    },
    async initDuLieu() {
      this.tt_nd = await this.$root.token.getThongTinNguoiDung()
      this.phanvung_id = this.tt_nd.phanvung_id
      console.log('Thiện check phanvung_id', this.phanvung_id)
      await this.sp_cbb_congvan();
      await this.sp_cbb_maghep();
      if (this.congvan_selected != null) {
        await this.$refs.loaihinh_tocdo_muccuoc.sp_cbb_goivnp(
          this.congvan_selected
        );
      }
      await this.$refs.loaihinh_tocdo_muccuoc.initDuLieu();
      await this.sp_grv_goida_dv();
    },
  },
  mounted() {
    setTimeout(() => {
      this.setActiveActions(-1);
      this.initDuLieu();
    }, 200);

  },
  watch: {
    "thongtingoidadichvu.input_tien"(val) {
      this.thongtingoidadichvu.input_vat = Math.round((Number(val) * 10) / 100);
    },
  },
};
</script>
