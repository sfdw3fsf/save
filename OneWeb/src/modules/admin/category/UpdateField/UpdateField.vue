<template src='./UpdateField.html'></template>
<style scoped src='./UpdateField.scss'></style>
<script>
import { mapActions } from 'vuex'
import gridview from '@/components/Shared/gridview.vue'
import UpdateFieldAPI from './UpdateFieldAPI'
import {
  GridPlugin,
  Filter,
  Page,
  Selection,
  GridComponent
} from "@syncfusion/ej2-vue-grids"
import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'

export default {
  components: {
    gridview,
    GridPlugin,
    Selection,
    GridComponent
  },
  mixins: [pagingAndFilter],
  provide: {
    grid: [Filter, Page]
  },
  name: 'UpdateField',
  data: () => ({
    isCreate: false,
    isUpdate: false,
    selectedList: [],
    selectedRow: null,
    inputNameVal: '',
    pageSettings: { pageSize: 10 },
    fieldList: [],
    config: [
      {
        fieldName: 'LINHVUC_ID',
        headerText: 'LINHVUC ID',
        allowFiltering: true,
        isPrimaryKey: true,
        visible: false
      },
      {
        fieldName: 'LINHVUC',
        headerText: 'Lĩnh vực',
        allowFiltering: true,
        headerTextAlign: 'center',
        width: "100%"
      }
    ]
  }),
  computed: {
    danhSachUpdateField () {
      return [...this.fieldList].sort((a, b) => b.LINHVUC_ID - a.LINHVUC_ID) || []
    }
  },
  mounted () {
    this.refreshList()
  },
  watch: {
  },
  methods: {
    ...mapActions('qllvCommon', [
      'getDanhSachUpdateField'
    ]),
    async refreshList () {
      UpdateFieldAPI.getList().then(rs => {
        this.fieldList = rs
      })
    },
    async onSubmit () {
    },
    checkBoxChange () {
      this.selectedList = this.$refs.linhVucGrid.getSelectedRecords()
    },
    redirectToHome () {
      this.$router.push({name: '/quantri/UploadFileHosoDienTu'})
    },
    create () {
      this.isCreate = true
      this.isUpdate = false
      this.selectedList = []
    },
    update () {
      this.isCreate = false
      this.isUpdate = true
      this.selectedList = []
    },
    cancel () {
      this.isCreate = false
      this.isUpdate = false
      this.inputNameVal = ''
      this.selectedRow = null
      this.selectedList = []
    },
    rowClick (item) {
      this.isCreate = false
      this.isUpdate = true
      this.selectedRow = item.data
      this.inputNameVal = this.selectedRow.LINHVUC

      this.selectedList = this.$refs.linhVucGrid.getSelectedRecords()
    },
    onFinish () {
      if (this.inputNameVal.trim() === '') return
      if (this.isCreate) {
        UpdateFieldAPI.create(this.inputNameVal).then(rs => {
          if (!rs.data.message_detail) {
            this.cancel()
            this.$toast.success('Cập nhật thành công')
            this.refreshList()
          } else {
            this.$toast.error('Cập nhật thất bại')
          }
        })
      } else if (this.isUpdate) {
        UpdateFieldAPI.update(this.selectedRow.LINHVUC_ID, this.inputNameVal).then(rs => {
          if (!rs.data.message_detail) {
            this.cancel()
            this.$toast.success('Cập nhật thành công')
            this.refreshList()
          } else {
            this.$toast.error('Cập nhật thất bại')
          }
        })
      }
    },
    onDelete () {
      this.$bvModal.msgBoxConfirm('Bạn có chắc muốn xóa?')
        .then(value => {
          if (value) {
            const listDelete = this.selectedList.map((item) => UpdateFieldAPI.delete(item.LINHVUC_ID))
            Promise.all(listDelete).then(r => {
              this.$toast.success('Xóa thành công')
              this.refreshList()
              this.cancel()
              this.selectedList = []
            })
          }
        })
    }
  }

}
</script>
