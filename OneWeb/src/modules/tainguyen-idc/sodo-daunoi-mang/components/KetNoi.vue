<template>
  <div class="ketnoi-container">
    <div class="legend-title">Kết nối</div>
    <div class="row">
      <div class="ketnoi-row">
        <span class="ketnoi-label">Upstream:</span>
        <span class="ketnoi-value ketnoi-upstream">{{ upstream }}</span>
      </div>
      <div class="ketnoi-row">
        <span class="ketnoi-label">Downstream:</span>
        <span class="ketnoi-value ketnoi-downstream">{{ downstream }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'KetNoi',
  props: {
    upstream: {
      type: [Number, String],
      default: 0
    },
    downstream: {
      type: [Number, String],
      default: 0
    }
  }
}
</script>

<style scoped>
.ketnoi-row {
  display: flex;
  align-items: center;
  font-size: 14px;
}

.ketnoi-label {
  color: #666;
  width: 100px;
  text-align: left;
}

.ketnoi-value {
  font-weight: 600;
  text-align: right;
  min-width: 40px;
}

.ketnoi-upstream {
  color: #0066ff;
}

.ketnoi-downstream {
  color: #28a745;
}
</style>
