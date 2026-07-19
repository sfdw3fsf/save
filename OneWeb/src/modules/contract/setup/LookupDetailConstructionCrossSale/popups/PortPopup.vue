<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>Thông tin port của thuê bao</div>
      <div
        class="close -ap icon-close"
        data-dismiss="modal"
        @click="$bvModal.hide('popup-port')"
      ></div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="">
            <span class="icon one-file-plus"></span> Xuất Excel
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <DataGrid v-bind:columns="gridHeaders" :show-filter="false"
                v-bind:dataSource="gridData" :showColumnCheckbox="false"></DataGrid>
    </div>
  </div>
</template>

<script>
import api from "../api";

// Tham chiếu UR2.8.004 Mục 1.1.9
export default {
  name: "PortPopup",
  components: {

  },

  props: {
    hdtb_id: { type: Number },
  },

  mounted () {
    this.loadDsPort();
  },

  data() {
    return {
      gridData: [],
      gridHeaders: [
        {fieldName: 'slot', headerText: 'SlotT'},
        {fieldName: 'port', headerText: 'Port'},
        {fieldName: 'vpi', headerText: 'Vpi/Svlan'},
        {fieldName: 'vci', headerText: 'Vci/Cvlan'},
        {fieldName: 'rack', headerText: 'Rack'},
        {fieldName: 'shelf', headerText: 'Shelf'},
        {fieldName: 'system', headerText: 'System'},
        {fieldName: 'tendslam', headerText: 'Dslam'},
        {fieldName: 'vitri', headerText: 'Vị trí Port'},
        {fieldName: 'ten_card', headerText: 'Card'},
        {fieldName: 'selfid01', headerText: 'SelfID'},
        {fieldName: 'selfid02', headerText: 'OLT Slot'},
        {fieldName: 'selfid03', headerText: 'OLT Port'},
        {fieldName: 'selfid04', headerText: 'ONU ID'},
        {fieldName: 'adsl_port', headerText: 'Adsl port'},
        {fieldName: 'port_mdf', headerText: 'Port MDF'},
        {fieldName: 'ketcuoi', headerText: 'Spliter'}
      ],
    };
  },

  methods: {
    async loadDsPort() {
      let hdtb_id = this.hdtb_id;
      if (!hdtb_id) {
        hdtb_id = 1475169;
        this.$toast(`hdtb_id test ${hdtb_id}`);
      }

      try {
        this.loading(true);
        const ret = await api.lay_ds_port_hdtb(this.axios, {hdtb_id});
        this.gridData = ret.data.data;

      } catch (e) {
        this.$toast("Có lỗi khi lấy danh sách port");

      } finally {
        this.loading(false);
      }
    }
  },
}
</script>
