<template>
  <ComboboxGrid
    :valueField="dataValueField"
    :textField="dataTextField"
    :value="value"
    :columns="columns"
    panelDataHeight="150"
    :enabled="!disabled"
    :enabledSelectFirstRow="false"
    :gridStyle="{right: 0}"
    :dataSource="dataSource"
    @selectedChanged="change"
  >
  </ComboboxGrid>
</template>
<script>
export default {
    props: ['columns'],
    data() { return {
            dataValueField: "",
            dataTextField: "",
            value: "",
            disabled: false,
            dataSource: []
        }
    },
    methods: {
        change(item) {
            this.value = item[this.dataValueField]
            this.$emit("change", item)
        }
    }
}
</script>