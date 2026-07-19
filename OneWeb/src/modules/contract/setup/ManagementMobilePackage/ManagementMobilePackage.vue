<template>
  <div class="main-wrapper">
    <breadcrumb :header="header" />

    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="onClickDongBo">
            <span class="icon one-upload"></span>Đồng bộ số liệu
          </a>
          <b-modal :id="dongBoSoLieuModalId" size="xl"
                   hide-footer hide-header hide-header-close body-class="modal-body p-0">
            <DongBoSoLieuPopup :modal-id="dongBoSoLieuModalId" :ds-loai-goi-cuoc="dsLoaiGoiCuoc"></DongBoSoLieuPopup>
          </b-modal>
        </li>
        <li>
          <a href="#" @click.prevent="onClickSuaGoiCuoc">
            Sửa gói cước
          </a>
          <b-modal :id="suaGoiCuocModalId" size="xl"
                   hide-footer hide-header hide-header-close body-class="modal-body p-0">
            <SuaGoiCuocPopup :modal-id="suaGoiCuocModalId"
                             :package-id="selectedItem && selectedItem.mobile_package_id"
                             :ds-loai-goi-cuoc="dsLoaiGoiCuoc"
            ></SuaGoiCuocPopup>
          </b-modal>
        </li>
        <li>
          <a href="#" @click.prevent="onClickXoaGoiCuoc">
            Xoá gói cước
          </a>
        </li>
      </ul>
    </div>

    <div class="page-content">
      <b-form inline class="justify-content-end px-3 py-2" @submit.prevent="doSearch">
        <label class="mr-3">Loại gói cước</label>
        <b-select class="mr-3" value-field="package_type_id" text-field="description"
                  :options="dsLoaiGoiCuoc" v-model="loaiGoiCuocId"></b-select>
        <label class="mr-3">Tên gói cước</label>
        <b-input class="mr-3" type="text" v-model="queryText"></b-input>
        <b-button type="submit" variant="outline-primary">Tìm kiếm</b-button>
      </b-form>

      <section class="box-form">
        <DataGrid :columns="gridColumns" :dataSource="dsGoiCuoc"
                  :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                  :enabled-select-first-row="true" @selectedRowChanged="gridSelectedRowChanged">
        </DataGrid>
      </section>
    </div>
  </div>
</template>

<script>
import _ from "lodash";
import breadcrumb from '@/components/breadcrumb';
import SuaGoiCuocPopup from "./popups/SuaGoiCuocPopup";
import DongBoSoLieuPopup from "./popups/DongBoSoLieuPopup";
import api from "./api";

export default {
  name: "ManagementMobilePackage",
  components: { breadcrumb, SuaGoiCuocPopup, DongBoSoLieuPopup },

  mounted() {
    this.loadInitial();
  },

  data() {
    return {
      header: {
        title: 'Quản lý gói cước di động',
        list: []
      },

      suaGoiCuocModalId: "popup-SuaGoiCuoc",
      dongBoSoLieuModalId: "popup-DongBoSoLieu",

      dsLoaiGoiCuoc: [],
      loaiGoiCuocId: 0,
      queryText: "",

      dsChuKy: [],

      gridColumns: [
        // {fieldName: 'mobile_package_id', headerText: 'ID'},
        {fieldName: 'package_name', headerText: 'Tên gói cước'},
        {fieldName: 'package_code', headerText: 'Mã gói cước'},
        {fieldName: 'service_code', headerText: 'Mã gói cước'},
        {fieldName: 'post_dispatch_id', headerText: "Dispatch ID"},
        {fieldName: 'post_package_id', headerText: 'Package ID'},
        {fieldName: 'price', headerText: 'Giá'},
        {fieldName: 'loai', headerText: 'Loại'},
        {fieldName: "actions", headerText: 'Thao tác'},
      ],
      dsGoiCuoc: [],
      selectedItem: null,
    };
  },

  methods: {
    async loadInitial() {
      try {
        this.loading(true);
        this.dsLoaiGoiCuoc = (await api.dsKieuGoiCuoc(this.axios)).data.data;
        this.dsChuKy = (await api.dsChuKy(this.axios)).data.data;
        this.dsGoiCuoc = (await api.dsGoiCuoc(this.axios, this.loaiGoiCuocId, this.queryText)).data.data;

      } finally {
        this.loading(false);
      }
    },

    async doSearch() {
      try {
        this.loading(true);
        this.dsGoiCuoc = (await api.dsGoiCuoc(this.axios, this.loaiGoiCuocId, this.queryText)).data.data;

      } catch (e) {
        this.$toast("Có lỗi khi lấy danh sách gói cước");

      } finally {
        this.loading(false);
      }
    },

    async gridSelectedRowChanged(item) {
      console.log("ManagementMobilePackage gridSelectedRowChanged", JSON.parse(JSON.stringify(item)));
      this.selectedItem = item;
    },

    onClickDongBo() {
      this.$bvModal.show(this.dongBoSoLieuModalId);
    },

    onClickSuaGoiCuoc() {
      if (!this.selectedItem) {
        alert("Vui lòng chọn gói cước");
        return;
      }

      this.$bvModal.show(this.suaGoiCuocModalId);
    },

    async onClickXoaGoiCuoc() {
      if (!this.selectedItem) {
        alert("Vui lòng chọn gói cước");
        return;
      }

      const confirm = await this.$bvModal.msgBoxConfirm(`Bạn có chắc chắn muốn xoá gói cước ${this.selectedItem.mobile_package_id} ${this.selectedItem.package_code}?`, {
        okVariant: "danger",
        okTitle: "Xoá gói cước",
        cancelTitle: "Huỷ bỏ",
      });
      console.log("ManagementMobilePackage onClickXoaGoiCuoc confirm", confirm);

      if (confirm) {
        try {
          this.loading(true);
          await api.fn_xoa_goicuoc_dd(this.axios, {
            mobile_package_id: this.selectedItem.mobile_package_id,
            nhanvien_id: this.$root.token.getNhanVienID()
          });
          this.dsGoiCuoc = this.dsGoiCuoc.filter(it => it !== this.selectedItem);
          this.$toast("Xoá gói cước thành công");

        } catch (e) {
          this.$toast("Có lỗi xảy ra khi xoá gói cước");

        } finally {
          this.loading(false);
        }
      }
    },
  }
};
</script>
