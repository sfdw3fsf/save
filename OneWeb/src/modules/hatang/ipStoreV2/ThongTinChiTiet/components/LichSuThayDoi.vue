<template>
  <DataGrid
    :allowPaging="true"
    :enablePagingServer="false"
    :showColumnCheckbox="false"
    :dataSource="dataSource"
    :columns="columns"
  ></DataGrid>
</template>

<script>
export default {
  name: 'LichSuThayDoiTab',
  props: {
    dataSource: {
      type: Array,
      default: () => [],
    },
    columns: {
      type: Array,
      default: () => [],
    },
  },
};
</script>

