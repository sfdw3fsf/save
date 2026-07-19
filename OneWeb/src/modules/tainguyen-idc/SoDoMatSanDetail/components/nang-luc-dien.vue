<template>
  <div class="nang-luc-dien card">
    <div class="card-body">
      <div class="row align-items-center">
        <!-- Donut: Tỷ lệ sử dụng điện (thực tế/định danh) -->
        <!-- <div class="col-lg-4 mb-2">
          <div class="donut-wrap">
            <div class="donut" :style="donutStyle">
              <div class="donut-center">
                <div class="donut-value">{{ usagePercent.toFixed(0) }}%</div>
              </div>
            </div>
          </div>
          <p class="donut-caption mb-0">
            Tỷ lệ sử dụng điện
          </p>
        </div> -->

        <!-- Cards -->
        <div class="col-12">
          <!-- <div class="col-md-4 mb-2">
              <div class="stat-card">
                <div class="stat-title text-muted">Công suất điện lưới</div>
                <div class="stat-value">{{ fmt(congSuatDienLuoi) }}</div>
                <div class="stat-unit">kW</div>
              </div>
            </div> -->
          <div class="row">
            <div class="col-lg-4 col-md-6 col-sm-8 mb-2">
              <div class="stat-card">
                <div class="stat-title text-muted">Tổng công suất định danh thiết bị</div>
                <div class="stat-value">{{ fmt(congSuatDinhDanh) }}</div>
                <div class="stat-unit">kW</div>
              </div>
            </div>

            <!-- <div class="col-md-4 mb-2">
              <div class="stat-card">
                <div class="stat-title text-muted">Công suất định danh có thể cấp phát</div>
                <div class="stat-value">{{ fmt(congSuatConLai) }}</div>
                <div class="stat-unit">kW</div>
              </div>
            </div>

            <div class="col-md-4 mb-2">
              <div class="stat-card">
                <div class="stat-title text-muted">Công suất định danh đã cấp phát</div>
                <div class="stat-value">{{ fmt(congSuatDinhDanhDaCapPhat) }}</div>
                <div class="stat-unit">kW</div>
              </div>
            </div>

            <div class="col-md-8 mb-2">
              <div class="stat-card">
                <div class="stat-title text-muted mb-2">Công suất thực tế đã sử dụng</div>
                <div class="d-flex align-items-end justify-content-between">
                  <div>
                    <div class="stat-value">{{ fmt(congSuatThucTeDaSuDung) }}</div>
                    <div class="stat-unit">kW</div>
                  </div>
                  <div class="text-muted small">{{ fmt(congSuatThucTeDaSuDung) }}/{{ fmt(congSuatDinhDanh) }} kW</div>
                </div>
                <div class="progress mt-2" style="height: 6px;">
                  <div class="progress-bar bg-info" role="progressbar" :style="{ width: usagePercent + '%' }"></div>
                </div>
              </div>
            </div> -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '../api.js'

export default {
  name: 'NangLucDien',
  props: {
    matSanId: { type: Number, required: true }
  },
  data() {
    return {
      // congSuatDienLuoi: 0

      // từ SP_GET_NANGLUC_DIEN_MATSAN
      congSuatDinhDanh: 0 // d.val
      // congSuatDinhDanhDaCapPhat: 0, // c.dd_cap (định danh đã cấp)
      // congSuatConLai: 0, // d.val - c.dd_cap
      // congSuatThucTeDaSuDung: 0, // c.tt_sudung
      // congSuatThucTeConLai: 0 // d.val - c.tt_sudung
    }
  },
  computed: {
    // usagePercent() {
    //   const total = Number(this.congSuatDinhDanh) || 0
    //   const used = Number(this.congSuatThucTeDaSuDung) || 0
    //   if (total <= 0) return 0
    //   return Math.max(0, Math.min(100, (used / total) * 100))
    // },
    donutStyle() {
      return {
        background: `conic-gradient(var(--primary) ${0}%, var(--track) 0)`
      }
    }
  },
  watch: {
    matSanId: {
      immediate: true,
      handler(val) {
        if (val) this.fetchNangLucDien()
      }
    }
  },
  methods: {
    async fetchNangLucDien() {
      try {
        this.$root.loading(true)
        // API backend trả ra các field như đã thống nhất ở SP_GET_NANGLUC_DIEN_MATSAN
        const res = await api.getNangLucDien(this.$root.context, this.matSanId)
        const d = res.data || {}

        // nếu có thêm "congSuatDienLuoi" từ backend thì lấy, không thì giữ 0
        // this.congSuatDienLuoi = Number(d.congSuatDienLuoi || 0)

        this.congSuatDinhDanh = Number(d.congSuatDinhDanh || 0)
        // this.congSuatDinhDanhDaCapPhat = Number(d.congSuatDinhDanhDaCapPhat || 0)
        // this.congSuatConLai = Number(
        //   d.congSuatConLai ?? Math.max(0, this.congSuatDinhDanh - this.congSuatDinhDanhDaCapPhat)
        // )
        // this.congSuatThucTeDaSuDung = Number(d.congSuatThucTeDaSuDung || 0)
        // this.congSuatThucTeConLai = Number(
        //   d.congSuatThucTeConLai ?? Math.max(0, this.congSuatDinhDanh - this.congSuatThucTeDaSuDung)
        // )
      } catch (e) {
        this.$toast.error('Lấy năng lực điện thất bại: ' + e.response.data.message || '')
      } finally {
        this.$root.loading(false)
      }
    },
    fmt(v) {
      return (v ?? 0).toLocaleString()
    }
  }
}
</script>

<style scoped>
.nang-luc-dien {
  padding: 8px;
}

/* donut */
.donut-wrap {
  display: flex;
  justify-content: center;
}
.donut {
  --size: 160px;
  --thickness: 16px;
  --primary: #2f80ed; /* phần đã dùng */
  --track: #e9edf3; /* nền vòng */
  width: var(--size);
  height: var(--size);
  border-radius: 50%;
  position: relative;
}
.donut::after {
  content: '';
  position: absolute;
  inset: calc(var(--thickness));
  background: #fff;
  border-radius: 50%;
}
.donut-center {
  position: absolute;
  inset: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  z-index: 1;
}
.donut-value {
  font-size: 28px;
  font-weight: 700;
  line-height: 1;
  color: #1f2937;
}
.donut-label {
  font-size: 11px;
  margin-top: 4px;
  color: #9ca3af;
}

/* caption under donut */
.donut-caption {
  text-align: center;
  margin-top: 8px;
  font-size: 13px;
  color: #6b7280; /* slightly stronger muted */
  display: block;
}

/* cards */
.stat-card {
  background: #f8fafc;
  border-radius: 10px;
  padding: 14px;
  height: 100%;
}
.stat-title {
  font-size: 12px;
}
.stat-value {
  font-size: 28px;
  font-weight: 700;
}
.stat-unit {
  font-size: 12px;
  margin-top: -2px;
  color: #98a2b3;
}
</style>
