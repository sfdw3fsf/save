<template src="./index.template.html"></template>

<script>
import { validationMixin } from '../../validation.js'
import api from './api'
import moment from 'moment'

export default {
  name: 'ThongTinKhachHang',
  mixins: [validationMixin],
  data() {
    return {
      dataItems: []
    }
  },
  props: {
    currentItem: {
      type: Object,
      required: true
    },
    controls: {
      type: Object,
      required: true
    },
    IDCList: {
      type: Array,
      default: () => []
    },
    alterBuildingList: {
      type: Array,
      default: () => []
    },
    alterFloorList: {
      type: Array,
      default: () => []
    },
    alterZoneList: {
      type: Array,
      default: () => []
    },
    alterRackList: {
      type: Array,
      default: () => []
    },
    UpperUList: {
      type: Array,
      default: () => []
    },
    LowerUList: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    'currentItem.idc_id': function(val) {
      if (val) {
        this.$emit('idc-changed', val)
      }
    },
    'currentItem.building_id': function(val) {
      if (val) {
        this.$emit('building-changed', val)
      }
    },
    'currentItem.floor_id': function(val) {
      if (val) {
        this.$emit('floor-changed', val)
      }
    },
    'currentItem.zone_id': function(val) {
      if (val) {
        this.$emit('zone-changed', val)
      }
    }
  },
  mounted: async function() {
    await this.loadData()
  },
  methods: {
    onIDCChange() {
      this.currentItem.building_id = null
      this.currentItem.floor_id = null
      this.currentItem.zone_id = null
      this.currentItem.rack_id = null
    },
    onBuildingChange() {
      this.currentItem.floor_id = null
      this.currentItem.zone_id = null
      this.currentItem.rack_id = null
    },
    onFloorChange() {
      this.currentItem.zone_id = null
      this.currentItem.rack_id = null
    },
    onZoneChange() {
      this.currentItem.rack_id = null
    },
    getValueForControl(controlKey) {
      const valueMap = {
        // Map các control keys với giá trị từ currentItem nếu cần validate
      }
      
      return valueMap[controlKey]
    },
    actionColumn(parent) {
      return function () {
        return {
          template: {
            template: `<div class="action-buttons gap-1">
                <button class="btn btn-sm btn-outline-primary" title="Xem chi tiết" @click="viewItem()">
                  Xem chi tiết HĐ
                </button>
              </div>`,
            computed: {
            },
            methods: {
              viewItem() {
                parent.loadHDDetail(this.data);
              }
            },
          },
        };
      };
    },
    loadHDDetail: async function(data) {
      try {
        this.$root.loading(true)
        const res = await api.getHDDetail(this.axios, data.HDTB_ID)
        console.log(res)

        if (res.data.error == 200) {
          // this.dataItems = res.data.data || []
        } else {
          this.$toast.error('Lỗi khi tải dữ liệu')
        }
      } catch (error) {
        console.log('Lỗi khi tải dữ liệu', error)
      } finally {
        this.$root.loading(false)
      }
    },
    loadData: async function() {
      try {
        if(!this.currentItem.id) return
        const res = await api.getList(this.axios, this.currentItem.id)
        if (res.data.error == 200) {
          this.dataItems = res.data.data || []
        } else {
          this.$toast.error('Lỗi khi tải dữ liệu')
        }
      } catch (error) {
        console.log('Lỗi khi tải dữ liệu', error)
      }
    },
  }
}
</script>
