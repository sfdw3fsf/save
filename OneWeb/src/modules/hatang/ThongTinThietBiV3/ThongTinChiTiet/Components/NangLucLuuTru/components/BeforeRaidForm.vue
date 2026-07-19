<template>
  <section class="section-wrapper">
    <!-- TITLE -->
    <div class="legend-title row pb-3 pb-md-2 align-items-center">
      <div class="col-12 col-md-2 pb-2 pb-md-0">
        Tổng dung lượng trước Raid
      </div>

      <div class="col-12 col-md-4 gap-2 d-flex align-items-center sum-data">
        <label class="me-2 w200 text-nowrap"> Tổng dung lượng thô (GB) <span class="required">*</span> </label>
        <div class="value">
          <input class="form-control" type="text" v-model="currentItem.tong_hdd_tho_truoc_raid" disabled />
        </div>
      </div>

      <div class="col-6 d-md-none"></div>
    </div>

    <!-- CONTENT -->
    <div class="row content-wrapper g-0">
      <!-- HEADER -->
      <div class="grid-row header-row mt-4">
        <div class="key"></div>
        <p class="col-title legend-title">Chi tiết HDD (Hard Disk Drive)</p>
        <p class="col-title legend-title">Chi tiết SSD (Solid State Drive)</p>
        <p class="col-title legend-title">Chi tiết NVMe (Non-Volatile Memory Express)</p>
      </div>

      <!-- Tổng -->
      <div class="grid-row mt-3 mt-md-0">
        <label class="key">Tổng (GB) <span class="required">*</span></label>

        <div class="value">
          <input
            :class="{ invalidBox: controls.tong_hdd_truoc_raid.invalid }"
            :disabled="!controls.tong_hdd_truoc_raid.enabled"
            type="number"
            class="form-control"
            v-model="currentItem.tong_hdd_truoc_raid"
          />
        </div>

        <div class="value">
          <input
            :class="{ invalidBox: controls.tong_ssd_truoc_raid.invalid }"
            :disabled="!controls.tong_ssd_truoc_raid.enabled"
            type="number"
            class="form-control"
            v-model="currentItem.tong_ssd_truoc_raid"
          />
        </div>

        <div class="value">
          <input
            :class="{ invalidBox: controls.tong_nvme_truoc_raid.invalid }"
            :disabled="!controls.tong_nvme_truoc_raid.enabled"
            type="number"
            class="form-control"
            v-model="currentItem.tong_nvme_truoc_raid"
          />
        </div>
      </div>

      <!-- Khả năng cấp -->
      <div class="grid-row">
        <label class="key">Khả năng cấp (GB) <span class="required">*</span></label>

        <div class="value">
          <input type="number" class="form-control" v-model="currentItem.hdd_kha_nang_cap_truoc_raid" disabled />
        </div>

        <div class="value">
          <input type="number" class="form-control" v-model="currentItem.ssd_kha_nang_cap_truoc_raid" disabled />
        </div>

        <div class="value">
          <input type="number" class="form-control" v-model="currentItem.nvme_kha_nang_cap_truoc_raid" disabled />
        </div>
      </div>

      <!-- Đã cấp -->
      <div class="grid-row">
        <label class="key">Đã cấp (GB) <span class="required">*</span></label>

        <div class="value">
          <input type="number" class="form-control" v-model="currentItem.hdd_da_cap_truoc_raid" disabled />
        </div>

        <div class="value">
          <input type="number" class="form-control" v-model="currentItem.ssd_da_cap_truoc_raid" disabled />
        </div>

        <div class="value">
          <input type="number" class="form-control" v-model="currentItem.nvme_da_cap_truoc_raid" disabled />
        </div>
      </div>

      <!-- Còn trống -->
      <div class="grid-row">
        <label class="key">Còn trống (GB) <span class="required">*</span></label>

        <div class="value">
          <input type="number" class="form-control" v-model="currentItem.hdd_con_trong_truoc_raid" disabled />
        </div>

        <div class="value">
          <input type="number" class="form-control" v-model="currentItem.ssd_con_trong_truoc_raid" disabled />
        </div>

        <div class="value">
          <input type="number" class="form-control" v-model="currentItem.nvme_con_trong_truoc_raid" disabled />
        </div>
      </div>

      <!-- Mức sử dụng -->
      <div class="grid-row">
        <label class="key">Mức sử dụng (%) <span class="required">*</span></label>

        <div class="value">
          <input type="number" class="form-control" v-model="currentItem.hdd_phan_tram_truoc_raid" disabled />
        </div>

        <div class="value">
          <input type="number" class="form-control" v-model="currentItem.ssd_phan_tram_truoc_raid" disabled />
        </div>

        <div class="value">
          <input type="number" class="form-control" v-model="currentItem.nvme_phan_tram_truoc_raid" disabled />
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: 'BeforeRaidForm',

  props: {
    currentItem: { type: Object, required: true },
    controls: { type: Object, required: true }
  },

  computed: {
    sumBeforeRaid() {
      const item = this.currentItem
      return (
        Number(item.tong_hdd_truoc_raid || 0) +
        Number(item.tong_ssd_truoc_raid || 0) +
        Number(item.tong_nvme_truoc_raid || 0)
      )
    }
  },

  watch: {
    sumBeforeRaid(newVal) {
      this.currentItem.tong_hdd_tho_truoc_raid = newVal
    }
  }
}
</script>
