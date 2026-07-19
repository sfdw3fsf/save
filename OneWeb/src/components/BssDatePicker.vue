<template>
    <flat-pickr :config="dateConfig" :value="value" @on-change="onChange" />
</template>
<script>
    export default {
        name: 'BssFlatPicker',
        props: {
            value: String,
            config: Object
        },
        methods: {
            onChange(selectedDates, dateStr, instance) {
                this.$emit('input', dateStr)
            }
        },
        data () {

            let dateConfig = {}

            if (this.config) {
                dateConfig = this.config
            }

            if (!dateConfig.altInput) {
                dateConfig.altInput = true
            }

            if (!dateConfig.dateFormat) {
                dateConfig.dateFormat = 'd/m/Y'
            }

            if (!dateConfig.altFormat) {
                dateConfig.altFormat = 'd/m/Y'
            }

            return {
                dateConfig: dateConfig
            }
        }
    }
</script>
