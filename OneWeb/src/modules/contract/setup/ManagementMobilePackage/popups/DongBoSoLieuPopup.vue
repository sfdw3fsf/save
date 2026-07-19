<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>Đồng bộ số liệu</div>
      <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide(modalId)"></div>
    </div>

    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="onClickDongBo">
            <span class="icon one-upload"></span>Đồng bộ
          </a>
        </li>
      </ul>
    </div>

    <div class="popup-body">
      <div class="d-flex flex-row align-items-center mb-3" style="width: 265px;">
        <label class="flex-shrink-0 my-0 mr-3">Loại gói cước</label>
        <b-select value-field="package_type_id" text-field="description"
                  :options="dsLoaiGoiCuocDongBo" v-model="loaiGoiCuocId" @change="loaiGoiCuocChanged"></b-select>
      </div>
      <DataGrid :columns="gridColumns" :dataSource="dsGoiCuoc"
                :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                @selectedRowChanged="gridSelectedRowChanged">
      </DataGrid>
    </div>

  </div>
</template>

<script>
import api from "../api";
import _ from "lodash";

export default {
  name: "DongBoSoLieuPopup",
  components: {},

  props: {
    modalId: { type: String },
    dsLoaiGoiCuoc: { type: Array },
  },

  mounted() {
    this.dsLoaiGoiCuocDongBo = this.dsLoaiGoiCuoc.filter(it => it.package_type_id !== 0);
    this.dsLoaiGoiCuocDongBo.unshift({
      package_type_id: null,
      description: "Chọn loại gói cước",
      disabled: true
    });
  },

  data() {
    return {
      loaiGoiCuocId: null,
      dsLoaiGoiCuocDongBo: [],

      gridColumns: [
        {fieldName: 'mobile_package_id', headerText: 'ID'},
        {fieldName: 'package_name', headerText: 'Tên gói cước'},
        {fieldName: 'package_code', headerText: 'Mã gói cước'},
        {fieldName: 'service_code', headerText: 'Mã gói cước'},
        {fieldName: 'post_dispatch_id', headerText: "Dispatch ID"},
        {fieldName: 'post_package_id', headerText: 'Package ID'},
        {fieldName: 'price', headerText: 'Giá'},
        {fieldName: 'loai', headerText: 'Loại'},
      ],
      dsGoiCuoc: [],

      selectedItem: null,
    }
  },

  methods: {
    async loaiGoiCuocChanged() {
      console.log("DongBoSoLieuPopup loaiGoiCuocChanged", this.package_type);

      try {
        this.loading(true);
        this.dsGoiCuoc = (await api.dsGoiCuoc(this.axios, this.loaiGoiCuocId)).data.data;

      } catch (e) {
        this.$toast("Có lỗi khi lấy danh sách gói cước");

      } finally {
        this.loading(false);
      }
    },

    async gridSelectedRowChanged(item) {
      console.log("DongBoSoLieuPopup gridSelectedRowChanged", JSON.parse(JSON.stringify(item)));
      this.selectedItem = item;
    },

    async onClickDongBo() {

      try {
        this.loading(true);
        const details = (await api.chiTietGoiCuoc(this.axios, this.selectedItem.mobile_package_id)).data.data[0];
        console.log("onClickDongBo", details);

        const submitParams = _.pick(details, [
          "cycle",
          "data_amount",
          "dhsx_desc",
          "external_sms_minute",
          "external_voice_minute",
          "full_desc",
          "internal_sms_minute",
          "internal_voice_minute",
          "package_code",
          "package_name",
          "package_type",
          "post_dispatch_id",
          "post_package_id",
          "price",
          "priority",
          "service_code",
          "short_desc",
          "status",
        ]);
        submitParams.phanvung_id = this.$root.token.getPhanVungID();

        await api.fn_themmoi_goicuoc_dd(this.axios, submitParams);

        this.$toast("Đồng bộ thành công");
        this.$bvModal.hide(this.modalId);

      } catch (e) {
        console.error(e);
        this.$toast("Có lỗi xảy ra khi thực hiện đồng bộ gói cước");

      } finally {
        this.loading(false);
      }
    },

  },
}
</script>
