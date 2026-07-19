<template>
  <div>
    <breadcrumb :header="header"/>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript: void(0)" @click="btnLayTT">
            <span class="icon one-file-attach"></span> Lấy TT
          </a>
        </li>
      </ul>
    </div>
    <frmXemThongBaoSuCo ref="frmXemThongBaoSuCo"/>
  </div>
</template>

<script>
import frmXemThongBaoSuCo from '@/modules/incident/subscriber/ReceiveIncident/popups/frmXemThongBaoSuCo';
import breadcrumb from "@/components/breadcrumb";

export default {
  name: "SearchIncidentAndChanges",
  components: {
    breadcrumb,
    frmXemThongBaoSuCo
  },
  data() {
    return {
      header: {
        title: "Tra cứu thông tin sự cố, tác động mạng lưới",
        list: []
      }
    }
  },
  mounted() {
    this.btnLayTT();
  },
  methods: {
    btnLayTT() {
      try {
        this.$refs.frmXemThongBaoSuCo.initData();
        this.$refs.frmXemThongBaoSuCo.showModal();
      } catch (e) {
        console.log(e);
      }
    }
  }
}
</script>

<style scoped>

</style>
