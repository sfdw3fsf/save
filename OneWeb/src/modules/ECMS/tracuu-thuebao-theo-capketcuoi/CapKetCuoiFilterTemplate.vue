<template>
  <div class="d-flex autocomplete-item">
    <div class="autocomplete-item-left align-self-center">
      <div class="autocomplete-item-image"><span :class="data.LOAI == 1 ? 'fa fa-dot-circle-o' : 'fa fa-expand'"></span></div>
    </div>
    <div class="autocomplete-item-right flex-grow-1">
      <div class="autocomplete-item-text">{{ data.OBJ_NAME }}</div>
      <div class="autocomplete-item-info">{{ data.NOTE }}</div>
    </div>
  </div>
</template>
<script>
export default {
  data: function () {},
};
</script>
