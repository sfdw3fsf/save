<template src="./ChuDeKhuyenMai.html">
</template>

<script>
import { mapActions, mapState } from 'vuex'
import ThongTinChuDeKhuyenMai from './part/ThongTinChuDeKhuyenMai.vue'
import DanhSachChuDeKhuyenMai from './part/DanhSachChuDeKhuyenMai.vue'
export default {
  components: {
    ThongTinChuDeKhuyenMai,
    DanhSachChuDeKhuyenMai
  },
  data () {
    return {
      formSumbit: false,
      checkResetForm: false,
      submitted: false,
      checkResetFormActivated: false,
      checkRecord: false,
      checkDelete: false,
      checkCancel: false,
      checkNewEntry: false,
      param: {
        pageNum: 1,
        pageRec: 10,
        totalRow: 1
      },
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },

    }
  },
  computed: {
    ...mapState('complaintTopic', ['danhSachKhuyenMai', 'isLoading', 'dataItem'])
  },
  // created () {
  //   this.layDanhSachKhuyenMai({
  //     'pageNum': 1,
  //     'pageRec': 10,
  //     'totalRow': 1
  //   })
  // },
  watch: {
    param: {
      deep: true,
      immediate: true,
      handler () {
        this.getDanhSachKhuyenMai()
      }
    }
  },
  methods: {
    ...mapActions('complaintTopic', ['layDanhSachKhuyenMai']),
    handlenewEntry () {
      this.checkNewEntry = !this.checkNewEntry
      this.$store.commit('complaintTopic/SET_DATA_ITEM', { loai_id: this.danhSachKhuyenMai.loai[0].loai_id, nhom_id: this.danhSachKhuyenMai.nhom[0].nhom_id})
    },
    handlePromotionalParam (item) {
      this.param.pageNum = item.pageIndex + 1
      this.param.pageRec = item.pageSize
      this.param.totalRow = 0
    },
    getDanhSachKhuyenMaiUpdate (id) {
      this.param.totalRow = id == 1 ? 0 : 1
      this.getDanhSachKhuyenMai()
    },
    getDanhSachKhuyenMai () {
      this.layDanhSachKhuyenMai(this.param)
    },
    handleCancel () {
      this.checkCancel = !this.checkCancel
    },
    summitForm () {
      this.formSumbit = !this.formSumbit
    },
    handleRecord () {
      this.checkRecord = !this.checkRecord
    },
    handleDelete () {
      this.checkDelete = !this.checkDelete
    },
    resetForm () {
      this.checkResetForm = !this.checkResetForm
    },
    summitFormActivated () {
      this.checkResetFormActivated = !this.checkResetFormActivated
    },
    closeDialog: function () {
      this.$emit('close', true)
    }
  }
}
</script>

<style scoped>
.disabled {
  /* background: #ccc; */
  cursor: inherit !important;
}
.list-actions-top .list li {
  cursor: pointer;
}
>>> .modal .modal-huge {
  max-width: 922px;
  width: 922px;
}
>>> .modal-body {
  padding: 0 !important;
}

.popup-header {
  width: 100%;
  font-size: 20px;
}
>>> .modal-header {
  padding: 10px 1rem !important;
}
>>> .popup-header {
  /* padding: 10px 15px; */
  font-size: 20px;
  background-color: #fff;
}
>>> .popup-header .title .icon {
  margin-right: 10px;
  color: #666;
  font-size: 24px;
  vertical-align: middle;
}
</style>

</template>
