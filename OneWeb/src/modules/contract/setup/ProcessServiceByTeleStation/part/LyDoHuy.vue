<template>
  <div>
    <div class="legend-title mart20">Lý do huỷ</div>
        <div class="table-content" style="height: 120px;">
            <vue-table ref="TTGV_DSNV"
                :config="tableConfig"
                :unique="'nhanvien_th_id'"
                :multiple="false">
            </vue-table>
        </div>
    </div>
</template>

<script>
export default {
  data: () => ({
    options: [{id: 1, name: 'dat 1'}, {id: 2, name: 'dat 2'}, {id: 3, name: 'dat 3'}],
    tableConfig: [
      {
        label: 'Lý do huỷ',
        filterAble: true,
        key: 'ten_nv'
      },
      {
        label: 'Kiểu',
        filterAble: true,
        key: 'so_dt'
      },
      {
        label: 'Người CN',
        filterAble: true,
        key: 'nhiemvu'
      }
    ]
  })
}
</script>

<style scoped>

</style>
