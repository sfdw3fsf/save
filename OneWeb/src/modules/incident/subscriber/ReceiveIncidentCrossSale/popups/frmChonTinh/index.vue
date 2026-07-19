<template src="./index.html">

</template>

<script>

import API from "../../API";

export default {
  name: "frmChonTinh",
  data() {
    return {
      headerTitle: 'Chọn tỉnh thi công',
      actions: [
        {
          id: 'tsbtnChapNhan',
          name: 'Chấp nhận (F5)',
          active: true,
          icon_class: 'one-check',
          visible: true
        },
        {
          id: 'tsbtnThoat',
          name: 'Thoát',
          active: true,
          icon_class: 'one-cancel',
          visible: true
        }
      ],
      tinhthicong_id: -1,
      cboTinh_data: [],
      resolvePromise: null,
    }
  },
  methods: {
    handleShowModal() {
      this.tinhthicong_id = -1;
      this.cboTinh_data = [];
      this.resolvePromise = null;
    },

    hideModal() {
      this.$refs.frmChonTinh.hide()
    },

    async initData() {
      try {
        this.loading(true);
        let rs = await API.load_tinh_thi_cong(this.axios, {tinhthicong_id: this.$root.token.getPhanVungID()});
        let {data} = rs.data;
        this.cboTinh_data = data || [];
        if (this.cboTinh_data.length > 0) {
          this.tinhthicong_id = this.cboTinh_data[0].tinh_id;
        }
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ' + error)
      } finally {
        this.loading(false)
      }
    },

    showModal() {
      this.initData()
      return new Promise((resolve, reject) => {
        this.$refs.frmChonTinh.show();

        this.resolvePromise = resolve;
        this.rejectPromise = reject;
      });
    },

    onActionClick(action) {
      if (action.id === 'tsbtnChapNhan') {
        this.ChapNhan();
      }

      this.hideModal();
    },

    async ChapNhan() {
      try {
        this.resolvePromise({
          tinhthicong_id: this.tinhthicong_id
        });
      } catch (e) {
        this.$root.$toast.error(e);
      }
    },
  }
}
</script>

<style scoped>

</style>
