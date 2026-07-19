<template>
    <div class="form-group">
        <div class="info-section">
            <div class="stats-grid-3col">
                <div class="stat-card stat-card-info">
                    <div class="stat-value">{{ stats.congSuatDinhDanh }}</div>
                    <div class="stat-label">Công suất định danh</div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: 'NangLucDien',
    props: {
        stats: {
            type: Object,
            default: () => ({
                congSuatDinhDanh: 0
            })
        }
    }
}
</script>