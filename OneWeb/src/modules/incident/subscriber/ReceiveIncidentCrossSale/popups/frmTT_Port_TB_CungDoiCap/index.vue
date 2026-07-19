<template src="./index.html">

</template>

<script>

import config from "./config";

export default {
  name: "frmTT_Port_TB_CungDoiCap",
  data() {
    return {
      headerTitle: 'Danh sách thuê bao cùng đôi cáp',
      dataSource: [],
      cols: config.cols,
      thuebao_id: -1,
      dichvuvt_id: 0,
      ma_tb: "",
      hdtb_id: -1,
      madoicap: 0,
      ChapNhan: false,
      item: null
    }
  },
  methods: {
    handleShowModal() {
    },

    hideModal() {
      this.$refs.frmTT_Port_TB_CungDoiCap.hide()
    },

    async showModal() {
      this.$refs.frmTT_Port_TB_CungDoiCap.show();
      await this.frmTT_Port_TB_CungDoiCap_Load();
    },

    async frmTT_Port_TB_CungDoiCap_Load() {
      this.loading(true);
      try {
        let madoicap = this.madoicap;
        let rs = null
        if (madoicap !== 0)
          rs = await this.axios.get(`web-ccdv/tien_trinh_bao_hong/lay_tt_port_tb_cung_madoicap2/${madoicap}`);
        else
          rs = await this.axios.post('web-ccdv/tien_trinh_bao_hong/lay_tt_port_tb_cung_madoicap', {
            "vhdtb_id": this.hdtb_id,
            "vthuebao_id": this.thuebao_id,
            "vdichvuvt_id": this.dichvuvt_id
          });

        if (rs && rs.data && rs.data.data.length > 0) {
          this.dataSource = rs.data.data;
        } else
          this.dataSource = [];
      } catch (ex) {
        this.$toast.error('Không có thông tin');
        this.dataSource = [];
      } finally {
        this.loading(false);
      }
    },

    async gridViewDanhSach_DoubleClick(rowIndex, item) {
      this.handle(item);
    },

    selectedRowChanged(item) {
      this.item = item;
    },

    tsbtnChapNhan_Click() {
      this.handle(this.item);
    },

    handle(item) {
      if (this.dataSource.length <= 0 || item == null) {
        return;
      }

      this.ChapNhan = true;

      this.ma_tb = item.ma_tb
      this.thuebao_id = item.thuebao_id;
      this.hdtb_id = item.hdtb_id;
      this.dichvuvt_id = parseInt(item.dichvuvt_id)
      this.$emit('chapNhan', item);
      this.hideModal();
    }
  }
}
</script>

<style scoped>

</style>
