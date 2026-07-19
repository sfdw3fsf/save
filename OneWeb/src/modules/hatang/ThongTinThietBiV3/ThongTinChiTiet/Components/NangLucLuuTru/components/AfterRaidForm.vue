<template>
  <section class="section-wrapper">
    <div class="legend-title row align-items-center pb-3 pb-md-2">
      <div class="col-12 col-md-2 pb-2 pb-md-0">
        Tổng dung lượng sau Raid
      </div>

      <div class="col-12 col-md-4 d-flex align-items-center gap-2 sum-data">
        <label class="me-2 w-180 text-nowrap"> Tổng năng lượng sau Raid (GB) <span class="required">*</span> </label>
        <input
          :class="{ invalidBox: controls.tong_hdd_tho_sau_raid.invalid }"
          class="form-control"
          type="number"
          v-model="currentItem.tong_hdd_tho_sau_raid"
          :disabled="true"
        />
      </div>

      <div class="col-6 d-md-none"></div>
    </div>

    <div class="row g-0 content-wrapper">
      <!-- Header -->
      <div class="grid-row header-row mt-4">
        <div class="key"></div>
        <p class="legend-title">Chi tiết HDD (Hard Disk Drive)</p>
        <p class="legend-title">Chi tiết SSD (Solid State Drive)</p>
        <p class="legend-title">Chi tiết NVMe (Non-Volatile Memory Express)</p>
        <p class="legend-title">Năng lực IOPS (Input/Output Operations Per Second)</p>
      </div>

      <!-- Tổng -->
      <div class="grid-row mt-3 mt-md-0">
        <label class="key">Tổng (GB) <span class="required">*</span></label>

        <div class="value">
          <input
            :class="{ invalidBox: controls.hdd_tong_sau_raid.invalid }"
            :disabled="!controls.hdd_tong_sau_raid.enabled"
            class="form-control"
            type="number"
            v-model="currentItem.hdd_tong_sau_raid"
          />
        </div>

        <div class="value">
          <input
            :class="{ invalidBox: controls.ssd_tong_sau_raid.invalid }"
            :disabled="!controls.ssd_tong_sau_raid.enabled"
            class="form-control"
            type="number"
            v-model="currentItem.ssd_tong_sau_raid"
          />
        </div>

        <div class="value">
          <input
            :class="{ invalidBox: controls.nvme_tong_sau_raid.invalid }"
            :disabled="!controls.nvme_tong_sau_raid.enabled"
            class="form-control"
            type="number"
            v-model="currentItem.nvme_tong_sau_raid"
          />
        </div>

        <div class="value">
          <input
            :class="{ invalidBox: controls.iops_tong_sau_raid.invalid }"
            :disabled="!controls.iops_tong_sau_raid.enabled"
            class="form-control"
            type="number"
            v-model="currentItem.iops_tong_sau_raid"
          />
        </div>
      </div>

      <!-- Khả năng cấp -->
      <div class="grid-row">
        <label class="key">Khả năng cấp (GB) <span class="required">*</span></label>

        <div class="value">
          <input
            :class="{ invalidBox: controls.hdd_kha_nang_cap_sau_raid.invalid }"
            :disabled="!controls.hdd_kha_nang_cap_sau_raid.enabled"
            type="number"
            class="form-control"
            v-model="currentItem.hdd_kha_nang_cap_sau_raid"
          />
        </div>

        <div class="value">
          <input
            :class="{ invalidBox: controls.ssd_kha_nang_cap_sau_raid.invalid }"
            :disabled="!controls.ssd_kha_nang_cap_sau_raid.enabled"
            type="number"
            class="form-control"
            v-model="currentItem.ssd_kha_nang_cap_sau_raid"
          />
        </div>

        <div class="value">
          <input
            :class="{ invalidBox: controls.nvme_kha_nang_cap_sau_raid.invalid }"
            :disabled="!controls.nvme_kha_nang_cap_sau_raid.enabled"
            type="number"
            class="form-control"
            v-model="currentItem.nvme_kha_nang_cap_sau_raid"
          />
        </div>

        <div class="value">
          <input
            :class="{ invalidBox: controls.iops_kha_nang_cap_sau_raid.invalid }"
            :disabled="!controls.iops_kha_nang_cap_sau_raid.enabled"
            type="number"
            class="form-control"
            v-model="currentItem.iops_kha_nang_cap_sau_raid"
          />
        </div>
      </div>

      <!-- Đã cấp -->
      <div class="grid-row">
        <label class="key">Đã cấp (GB) <span class="required">*</span></label>

        <div class="value">
          <input :disabled="true" type="number" class="form-control" v-model="currentItem.hdd_da_cap_sau_raid" />
        </div>

        <div class="value">
          <input :disabled="true" type="number" class="form-control" v-model="currentItem.ssd_da_cap_sau_raid" />
        </div>

        <div class="value">
          <input :disabled="true" type="number" class="form-control" v-model="currentItem.nvme_da_cap_sau_raid" />
        </div>

        <div class="value">
          <input :disabled="true" type="number" class="form-control" v-model="currentItem.iops_da_cap_sau_raid" />
        </div>
      </div>

      <!-- Còn trống -->
      <div class="grid-row">
        <label class="key">Còn trống (GB) <span class="required">*</span></label>

        <div class="value">
          <input :disabled="true" type="number" class="form-control" v-model="currentItem.hdd_con_trong_sau_raid" />
        </div>

        <div class="value">
          <input :disabled="true" type="number" class="form-control" v-model="currentItem.ssd_con_trong_sau_raid" />
        </div>

        <div class="value">
          <input :disabled="true" type="number" class="form-control" v-model="currentItem.nvme_con_trong_sau_raid" />
        </div>

        <div class="value">
          <input :disabled="true" type="number" class="form-control" v-model="currentItem.iops_con_trong_sau_raid" />
        </div>
      </div>

      <!-- Mức sử dụng -->
      <div class="grid-row">
        <label class="key">Mức sử dụng (%) <span class="required">*</span></label>

        <div class="value">
          <input :disabled="true" type="number" class="form-control" v-model="currentItem.hdd_phan_tram_sau_raid" />
        </div>

        <div class="value">
          <input :disabled="true" type="number" class="form-control" v-model="currentItem.ssd_phan_tram_sau_raid" />
        </div>

        <div class="value">
          <input :disabled="true" type="number" class="form-control" v-model="currentItem.nvme_phan_tram_sau_raid" />
        </div>

        <div class="value">
          <input :disabled="true" type="number" class="form-control" v-model="currentItem.iops_phan_tram_sau_raid" />
        </div>
      </div>

      <div class="grid-row sum-raid mt-5">
        <label class="key">Tổng đã cấp (GB) <span class="required">*</span></label>

        <div class="value">
          <input :disabled="true" type="number" class="form-control" v-model="currentItem.tong_da_cap" />
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: 'AfterRaidForm',

  props: {
    currentItem: {
      type: Object,
      required: true
    },
    controls: { type: Object, required: true }
  },

  computed: {
    sumAfterRaid: function() {
      const item = this.currentItem
      return (
        Number(item.hdd_tong_sau_raid || 0) +
        Number(item.ssd_tong_sau_raid || 0) +
        Number(item.nvme_tong_sau_raid || 0) +
        Number(item.iops_tong_sau_raid || 0)
      )
    }
  },

  watch: {
    sumAfterRaid(newVal) {
      this.currentItem.tong_hdd_tho_sau_raid = newVal
    }
  }
}
</script>
