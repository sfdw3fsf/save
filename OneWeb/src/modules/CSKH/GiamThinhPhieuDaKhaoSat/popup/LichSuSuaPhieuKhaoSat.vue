<template>
  <b-modal
    id="popup-lichSuSuaPhieu"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @show="fetchData"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>LỊCH SỬ SỬA PHIẾU KHẢO SÁT
        </div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popup-lichSuSuaPhieu')"></div>
      </div>

      <div class="popup-body">
        <div class="box-form">
          <div class="table-content">
            <DataGrid
              :columns="fields"
              :dataSource="dataSource"
              :showColumnCheckbox="false"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
            ></DataGrid>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import TableWithSearch from "../../utils/TableWithSearch.vue";
import GiamThinhPhieuKhaoSatApi from "../../api/GiamThinhPhieuKhaoSatApi";
export default {
  components: { TableWithSearch },
  props: ["vbtlId"],
  data() {
    return {
      dataSource: [],
      fields: [
        {
          fieldName: "TEN_NV",
          headerText: "Người thực hiện",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NOI_DUNG",
          headerText: "Nội dung",
          allowFiltering: true,
          allowSorting: false,
        },

        {
          fieldName: "NGAY_TH",
          headerText: "Ngày cập nhật",
          allowFiltering: true,
          allowSorting: false,
        },
      ],
    };
  },
  methods: {
    async fetchData() {
      this.loading(true);
      let response = null;
      console.log("aaaaaaa", this.$attrs);
      try {
        let postData = {
          pageNo: 1,
          pageSize: 1000,
          tbtlId: this.vbtlId,
        };
        // let postData = {
        //   pageNo: pageNo,
        //   pageSize: pageSize,
        //   tbtlId: 60381
        // }
        response = await GiamThinhPhieuKhaoSatApi.getLishSuSuaPhieu(
          this.axios,
          postData
        );
        this.dataSource = []
        if (response.data.error_code == 'BSS-00000000') {
          this.dataSource = response.data.data.data || []
        }

      } catch (e) {
      } finally {
        this.loading(false);
      }

      return response;
    },
  },
};
</script>
