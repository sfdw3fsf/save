<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="detailCurrentMode == 'EDIT' || detailCurrentMode == 'ADD'">
          <b-link href="javascript:void(0)" @click="onSaveDetail"> <span class="icon one-save"></span>Lưu</b-link>
        </li>
        <li v-if="detailCurrentMode == 'VIEW'">
          <b-link href="javascript:void(0)" @click="onEditDetail"> <span class="icon fa fa-edit"></span>Sửa</b-link>
        </li>
        <li v-if="detailCurrentMode === 'VIEW'">
          <b-link href="javascript:void(0)" @click="onAddNew"> <span class="icon one-file-plus"></span>Nhập mới</b-link>
        </li>
        <li>
          <b-link href="javascript:void(0)" @click="onRefreshDetail" :class="{ disabled: loading }">
            <span class="icon fa one-refresh"></span>Làm mới</b-link>
        </li>
        <li>
          <b-link href="javascript:void(0)" @click="onCancel" :disabled="detailCurrentMode == 'VIEW'">
            <span class="icon ui-1_circle-remove nc-icon-glyph"></span>Huỷ</b-link>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <ChiTietThietBiMauPage 
        ref="deviceDetail" 
        :equipment-type="equipmentType"
        @mode-changed="onModeChanged"
        @saved="onDeviceSaved"
      />
    </div>
  </div>
</template>
<script>
import ChiTietThietBiMauPage from '../chitiet-thietbi-mau/index.vue'

export default {
  name: 'ThietBiMauWrapper',
  components: {
    ChiTietThietBiMauPage
  },
  props: {
    equipmentType: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      id: null,
      loading: false,
      detailCurrentMode: 'VIEW'
    }
  },
  watch: {
    '$route.params.id': {
      handler() {
        this.$nextTick(() => {
          this.handleRouteLogic()
        })
      },
      immediate: true
    }
  },
  methods: {
    async handleRouteLogic() {
      if (!this.$refs.deviceDetail || !this.$refs.deviceDetail.show) {
        this.$nextTick(() => {
          if (this.$refs.deviceDetail && this.$refs.deviceDetail.show) {
            this.handleRouteLogic()
          }
        })
        return
      }

      const deviceId = this.$route.params.id
      const idNumber = deviceId ? parseInt(deviceId, 10) : 0
      
      if (!deviceId || isNaN(idNumber) || idNumber <= 0) {
        this.detailCurrentMode = 'ADD'
        const item = {}
        this.$refs.deviceDetail.show(item, 'ADD')
        this.id = null
        return
      }
      
      this.id = idNumber
      this.detailCurrentMode = 'VIEW'
      await this.$refs.deviceDetail.show({ id: idNumber }, 'VIEW')
    },
    onModeChanged(mode) {
      this.detailCurrentMode = mode
    },
    onDeviceSaved() {
      this.detailCurrentMode = 'VIEW'
      if (this.$refs.deviceDetail && this.$refs.deviceDetail.currentItem && this.$refs.deviceDetail.currentItem.id) {
        this.$router.push({
          path: this.$route.path.replace(':id?', this.$refs.deviceDetail.currentItem.id),
          params: { id: this.$refs.deviceDetail.currentItem.id }
        })
      }
    },
    async onSaveDetail() {
      if (this.$refs.deviceDetail && this.$refs.deviceDetail.onSave) {
        this.loading = true
        try {
          await this.$refs.deviceDetail.onSave()
        } finally {
          this.loading = false
        }
      }
    },
    async onEditDetail() {
      if (this.$refs.deviceDetail && this.$refs.deviceDetail.onEditDetail) {
        await this.$refs.deviceDetail.onEditDetail()
      }
    },
    async onAddNew() {
      this.$router.push({
        path: this.$route.path.replace('/:id?', '')
      })
    },
    async onRefreshDetail() {
      if (this.$refs.deviceDetail && this.$refs.deviceDetail.onRefreshDetail) {
        await this.$refs.deviceDetail.onRefreshDetail()
      }
    },
    async onCancel() {
      if (this.$refs.deviceDetail && this.$refs.deviceDetail.onCancel) {
        await this.$refs.deviceDetail.onCancel()
        if (this.detailCurrentMode === 'ADD') {
          this.$router.push({
            path: '/ha-tang/ds-thietbi-mau'
          })
        }
      }
    }
  }
}
</script>
