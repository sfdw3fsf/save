<template>
    <div ref="ele">
        <SelectExt :dataSource="dataSource" @change="change" :disabled="disabled" v-model="value" :dataTextField="dataTextField" :dataValueField="dataValueField"  />
    </div>
</template>
<script>
export default {
    data() {
        return {
            dataSource: [],
            value: null,
            text: "",
            dataTextField: "",
            dataValueField: "",
            disabled: this.disable,
            SelectedIndex: null
        }
    },
    props: ['disable'],
    watch: {
        SelectedIndex(value) {
            this.value = this.dataSource && this.dataSource[value] ? this.dataSource[value][this.dataValueField] : null
            console.log(this.value)
        },
        value() {
            let item = this.dataSource.find(x => x[this.dataValueField] == this.value)
            console.log(item)
            this.text = item ? item[this.dataTextField] : ""
        }
    },
    methods: {
        change() {
            this.$emit("change", this.value)
        },
        HT_COMBOBOX(dataSource, textField, valueField) {
            this.dataSource = dataSource;
            this.dataTextField = textField;
            this.dataValueField = valueField
            console.log(this.dataTextField)
            if(this.dataSource && this.dataSource.length > 0)
                this.value = this.dataSource[0][valueField]
            this.$emit('change', this.value)
        },
    }
}
</script>