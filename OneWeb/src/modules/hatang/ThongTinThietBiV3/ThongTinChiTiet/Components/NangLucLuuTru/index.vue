<template src="./index.template.html"></template>
<style src="./nangluc-luutru.scss" scoped></style>

<script>
import { validationMixin, ValidationRulesFactory } from '../../validation.js'
import AfterRaidForm from './components/AfterRaidForm.vue'
import BeforeRaidForm from './components/BeforeRaidForm.vue'

export default {
  name: 'NangLucLuuTru',
  mixins: [validationMixin],

  components: {
    BeforeRaidForm,
    AfterRaidForm
  },

  data() {
    return {
      validationRules: {
        mem_cache: ValidationRulesFactory.createNumberRule('Mem Cache không được để trống'),
        flash_cache: ValidationRulesFactory.createNumberRule('Flash Cache không được để trống'),
        // Trước RAID
        tong_hdd_truoc_raid: ValidationRulesFactory.createNumberRule('Tổng HDD trước RAID không được để trống'),
        tong_ssd_truoc_raid: ValidationRulesFactory.createNumberRule('Tổng SSD trước RAID không được để trống'),
        tong_nvme_truoc_raid: ValidationRulesFactory.createNumberRule('Tổng NVMe trước RAID không được để trống'),

        // Sau RAID – tổng
        hdd_tong_sau_raid: ValidationRulesFactory.createNumberRule('HDD tổng sau RAID không được để trống'),
        ssd_tong_sau_raid: ValidationRulesFactory.createNumberRule('SSD tổng sau RAID không được để trống'),
        nvme_tong_sau_raid: ValidationRulesFactory.createNumberRule('NVMe tổng sau RAID không được để trống'),
        iops_tong_sau_raid: ValidationRulesFactory.createNumberRule('IOPS tổng sau RAID không được để trống'),

        // Sau RAID – khả năng cấp
        hdd_kha_nang_cap_sau_raid: ValidationRulesFactory.createRule(
          ['number', 'conditional'],
          {
            number: 'Khả năng cấp HDD sau RAID không được để trống',
            conditional: 'Khả năng cấp HDD sau RAID không được lớn hơn tổng HDD sau RAID'
          },
          {
            condition: () => true,
            validator: (value) => {
              return Number(value) <= Number(this.currentItem.hdd_tong_sau_raid)
            }
          }
        ),

        ssd_kha_nang_cap_sau_raid: ValidationRulesFactory.createRule(
          ['number', 'conditional'],
          {
            number: 'Khả năng cấp SSD sau RAID không được để trống',
            conditional: 'Khả năng cấp SSD sau RAID không được lớn hơn tổng SSD sau RAID'
          },
          {
            condition: () => true,
            validator: (value) => {
              return Number(value) <= Number(this.currentItem.ssd_tong_sau_raid)
            }
          }
        ),

        nvme_kha_nang_cap_sau_raid: ValidationRulesFactory.createRule(
          ['number', 'conditional'],
          {
            number: 'Khả năng cấp NVME sau RAID không được để trống',
            conditional: 'Khả năng cấp NVME sau RAID không được lớn hơn tổng NVME sau RAID'
          },
          {
            condition: () => true,
            validator: (value) => {
              return Number(value) <= Number(this.currentItem.nvme_tong_sau_raid)
            }
          }
        ),

        iops_kha_nang_cap_sau_raid: ValidationRulesFactory.createRule(
          ['number', 'conditional'],
          {
            number: 'Khả năng cấp IOPS sau RAID không được để trống',
            conditional: 'Khả năng cấp IOPS sau RAID không được lớn hơn tổng IOPS sau RAID'
          },
          {
            condition: () => true,
            validator: (value) => {
              return Number(value) <= Number(this.currentItem.iops_tong_sau_raid)
            }
          }
        ),

        tong_hdd_tho_sau_raid: ValidationRulesFactory.createRule(
          ['conditional'],
          {
            conditional: 'Tổng năng lượng lưu trữ sau Raid phải nhỏ hơn Tổng dung lượng trước Raid'
          },
          {
            condition: () => true,
            validator: (value) => {
              return Number(value) <= Number(this.currentItem.tong_hdd_tho_truoc_raid)
            }
          }
        ),

        raid_level: ValidationRulesFactory.createSelectRule('RAID level không được để trống')
      },

      raidList: [
        { ID: 1, TEN: 'Raid 0' },
        { ID: 2, TEN: 'Raid 1' },
        { ID: 3, TEN: 'Raid 5' },
        { ID: 4, TEN: 'Raid 6' },
        { ID: 5, TEN: 'Raid 10' }
      ]
    }
  },

  computed: {},

  props: {
    currentItem: {
      type: Object,
      required: true
    },

    controls: {
      type: Object,
      required: true
    },

    deviceRelationHistory: {
      type: Array,
      default: () => []
    },

    deviceTypeInfo: {
      type: Object,
      default: () => ({
        phanLoai: null,
        ten: null,
        id: null,
        isServer: false,
        isNetwork: false,
        isServerBlade: false,
        isBlade: false,
        isTuDia: false,
        isBoDieuKhien: false,
        isKhayDia: false,
        isLinecard: false,
        isPowercard: false,
        isConsolecard: false
      })
    }
  },

  mounted() {},

  methods: {
    preparePayloadForNangLucLuuTru(currentItem) {
      return {
        thietbitudId: currentItem.thietbitud_id ? Number(currentItem.thietbitud_id) : null,
        thietbiId: currentItem.id ? Number(currentItem.id) : null,

        dlMemcache: currentItem.mem_cache ? Number(currentItem.mem_cache) : null,
        dlFlashcache: currentItem.flash_cache ? Number(currentItem.flash_cache) : null,
        raidlevelId: currentItem.raid_level ? Number(currentItem.raid_level) : null,

        // trước RAID
        dltrHdd: currentItem.tong_hdd_truoc_raid ? Number(currentItem.tong_hdd_truoc_raid) : null,
        dltrSsd: currentItem.tong_ssd_truoc_raid ? Number(currentItem.tong_ssd_truoc_raid) : null,
        dltrNvme: currentItem.tong_nvme_truoc_raid ? Number(currentItem.tong_nvme_truoc_raid) : null,

        // sau RAID
        dlsrHdd: currentItem.hdd_tong_sau_raid ? Number(currentItem.hdd_tong_sau_raid) : null,
        dlsrSsd: currentItem.ssd_tong_sau_raid ? Number(currentItem.ssd_tong_sau_raid) : null,
        dlsrNvme: currentItem.nvme_tong_sau_raid ? Number(currentItem.nvme_tong_sau_raid) : null,

        // dlHdd: currentItem.hdd_tong_sau_raid ? Number(currentItem.hdd_tong_sau_raid) : null,
        dlncHdd: currentItem.hdd_kha_nang_cap_sau_raid ? Number(currentItem.hdd_kha_nang_cap_sau_raid) : null,

        // dlSsd: currentItem.ssd_tong_sau_raid ? Number(currentItem.ssd_tong_sau_raid) : null,
        dlncSsd: currentItem.ssd_kha_nang_cap_sau_raid ? Number(currentItem.ssd_kha_nang_cap_sau_raid) : null,

        // dlNvme: currentItem.nvme_tong_sau_raid ? Number(currentItem.nvme_tong_sau_raid) : null,
        dlncNvme: currentItem.nvme_kha_nang_cap_sau_raid ? Number(currentItem.nvme_kha_nang_cap_sau_raid) : null,

        dlIops: currentItem.iops_tong_sau_raid ? Number(currentItem.iops_tong_sau_raid) : null,
        dlncIops: currentItem.iops_kha_nang_cap_sau_raid ? Number(currentItem.iops_kha_nang_cap_sau_raid) : null
      }
    },

    getValueForControl(controlKey) {
      const item = this.currentItem

      const valueMap = {
        mem_cache: item.mem_cache,
        flash_cache: item.flash_cache,
        tong_hdd_truoc_raid: item.tong_hdd_truoc_raid,
        tong_ssd_truoc_raid: item.tong_ssd_truoc_raid,
        tong_nvme_truoc_raid: item.tong_nvme_truoc_raid,
        raid_level: item.raid_level,
        hdd_tong_sau_raid: item.hdd_tong_sau_raid,
        ssd_tong_sau_raid: item.ssd_tong_sau_raid,
        nvme_tong_sau_raid: item.nvme_tong_sau_raid,
        iops_tong_sau_raid: item.iops_tong_sau_raid,
        hdd_kha_nang_cap_sau_raid: item.hdd_kha_nang_cap_sau_raid,
        ssd_kha_nang_cap_sau_raid: item.ssd_kha_nang_cap_sau_raid,
        nvme_kha_nang_cap_sau_raid: item.nvme_kha_nang_cap_sau_raid,
        iops_kha_nang_cap_sau_raid: item.iops_kha_nang_cap_sau_raid,
        tong_hdd_tho_sau_raid: item.tong_hdd_tho_sau_raid
      }

      return valueMap[controlKey]
    }
  }
}
</script>
