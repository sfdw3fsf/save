<template src="./index.template.html"></template>
<script>
export default {
    data() {
        return {
            gridThueBao: {
                DataSource: []
            },
            dsThueBao: [],
            thueBaoDuocChon: null
        }
    },
    methods: {
        show() {
            return this.$refs['ref-modal'].show()
        },
        hide() {
            this.$refs['ref-modal'].hide()
        },
        onModalHidden() {
            this.gridThueBao.DataSource = []
            this.dsThueBao = []
            this.thueBaoDuocChon = null
        },
        onModalShown() {
            this.gridThueBao.DataSource = this.dsThueBao
        },
        setDsThueBao(dsThueBao) {
            this.dsThueBao = dsThueBao
        },
        onSelectedRowChanged(data) {
            this.thueBaoDuocChon = data
        },
        onClickBtnChapNhan() {
            this.$emit('select', this.thueBaoDuocChon)
            this.hide()
        },
    }
}
</script>