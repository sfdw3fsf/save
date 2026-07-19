<template src="./index.html">

</template>

<script>
import Config from './config';

export default {
  name: "frmTraCuuThueBao",
  props: {
    dsThueBao: {
      type: Array,
      default: function () {
        return []
      }
    }
  },
  data() {
    return {
      headerTitle: 'Danh sách thuê bao',
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
      thueBao: null,
      index: -1,
      daucuoi_id: 0,
      resolvePromise: null,
      cols: Config.cols
    }
  },
  methods: {
    handleShowModal() {
      this.thueBao = null;
      this.index = -1;
      this.daucuoi_id = -1;
      this.resolvePromise = null;
    },

    hideModal() {
      this.$refs.frmTraCuuThueBao.hide()
    },

    initData() {
      try {
        this.loading(true);
        this.loadDataFromProps();
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ' + error)
      } finally {
        this.loading(false)
      }
    },

    loadDataFromProps() {
    },

    showModal() {
      this.initData()
      return new Promise((resolve, reject) => {
        this.$refs.frmTraCuuThueBao.show();

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
          index: this.index,
          thueBao: this.thueBao,
          daucuoi_id: this.daucuoi_id,
        });
      } catch (e) {
        this.$root.$toast.error(e);
      }
    },

    rowSelected: function (args) {
      this.index = parseInt(args.row.getAttribute('aria-rowindex'));
    },

    selectedRowChanged: function (thueBao) {
      this.thueBao = thueBao;
      this.daucuoi_id = thueBao.daucuoi_id;
    }
  }
}
</script>

<style scoped>

</style>
