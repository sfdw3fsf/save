<template>
<div class="form-group">
    <div class="row">
        <div class="row">
            <div class="legend-title fw-bold">Thông số chi tiết</div>
        </div>

        <div class="col-4 center-card">
            <PieProgressChart
                :used="stats.tongRackDaLapDat"
                :total="stats.tongRackThietKe"
                label="Tỷ lệ sử dụng rack"
                color="#3b82f6"
            />
        </div>
        <div class="col-8">
            <!-- Thông tin Rack -->
            <div class="stats-grid-3col" style="margin-top: 48px;">
                <div class="stat-card">
                    <div class="stat-value">{{ stats.tongRackThietKe }}</div>
                    <div class="stat-label">Rack thiết kế</div>
                </div>
                <div class="stat-card stat-card-success">
                    <div class="stat-value">{{ stats.tongRackDaLapDat }}</div>
                    <div class="stat-label">Rack đã lắp đặt</div>
                </div>
                <div class="stat-card stat-card-info">
                    <div class="stat-value">{{ stats.tongRackCon }}</div>
                    <div class="stat-label">Rack có thể lắp đặt</div>
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<script>
import PieProgressChart from './PieProgressChart.vue';

export default {
    name: 'NangLucKhongGian',
    components: {
        PieProgressChart
    },
    props: {
        stats: {
            type: Object,
            default: () => ({
                tongRackThietKe: 0,
                tongRackDaLapDat: 0,
                tongRackCon: 0
            })
        },
    }
}
</script>
