<template>
  <b-modal
    size="xl"
    dialog-class="dialog-content"
    body-class="body-content"
    header-class="popup-header"
    content-class="popup-box"
    v-model="isShowModal"
    hide-footer
  >
    <template #modal-header>
      <div class="title">
        <span class="icon one-notepad"></span> Đăng kí dịch vụ khác
      </div>
      <div class="close -ap icon-close" @click.prevent="show(false)"></div>
    </template>
    <div style="margin: -1rem">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="onAccept">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp
              nhận (F5)
            </a>
          </li>
          <li>
            <a @click="cancelPopup">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span>
              Thoát (F5)
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="table-content">
            <ejs-grid
              ref="tbDichVuKhac"
              :dataSource="dsLyDoHuy"
              :allowPaging="true"
              :pageSettings="pageSettings"
              :allowSorting="false"
              :allowFiltering="true"
              :allowTextWrap="true"
              gridLines="Both"
            >
              <e-columns>
                <e-column field="stt" headerText="Mã dịch vụ"></e-column>
                <e-column field="tendichvu" headerText="Tên dịch vụ"></e-column>
                <e-column field="trangthai" headerText="Trạng thái"></e-column>
                <e-column field="noidung" headerText="Nội dung"></e-column>
                <e-column
                  headerText="Đăng Ký"
                  type="checkbox"
                  width="50"
                ></e-column>
                <e-column
                  headerText="Hủy DV"
                  type="checkbox"
                  width="50"
                ></e-column>
              </e-columns>
            </ejs-grid>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
import select3 from "@/components/form/VueSelect.vue";
export default {
  components: {
    select3
  },
  data: () => ({
    pageSettings: { pageSizes: [10, 50, 100, 200], pageCount: 4, pageSize: 10 },
    isShowModal: false
  }),
  computed: {
    ...mapGetters("liquidateCommon", ["dsLyDoHuy", "dsNhomHuy"])
  },
  async created() {
    const variables = { hd_id: 155319, loaiid: 1 };
    await this.getDanhSachDichVuKhac(variables);
  },
  mounted() {
    // const variables = { hd_id: 3650260, loaiid: 1 };
    // this.getDanhSachDichVuKhac(variables);
  },
  methods: {
    ...mapActions("liquidateCommon", ["layDsNhomHuy", "layDsLyDoHuy"]),
    ...mapActions("mapChangeService", ["getDanhSachDichVuKhac"]),
    show(visible, data) {
      this.isShowModal = visible;
      if (data) {
        this.list = data;
      }
    },
    onAccept() {
      this.$emit("onAccept", this.$refs.tbDichVuKhac.getSelectedRecords());
      this.show(false);
    },
    cancelPopup() {
      this.show(false);
    }
  }
};
</script>
