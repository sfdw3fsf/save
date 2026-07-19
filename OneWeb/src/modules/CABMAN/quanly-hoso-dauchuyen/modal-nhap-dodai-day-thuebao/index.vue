<template src="./index.template.html"></template>
<script>
export default {
    props:{
        modalId: String,
        doDaiCu: Number
    },
    data() {
        return {
            doDaiMoi: 0
        }
    },
    computed: {
        inputNhapDoDai() {
            return this.$refs['ref-nhap-do-dai']
        }
    },
    methods: {
        onModalShown() {
            if (!!this.doDaiCu) {
                this.doDaiMoi = this.doDaiCu
            }

            this.inputNhapDoDai.defaultUnformat = this.inputNhapDoDai.unformat
            this.inputNhapDoDai.unformat = function(value) {
                if (isNaN(value)) {
                    value = this.emptyValue
                }
                return this.defaultUnformat(value)
            }
        },
        onModalHidden() {
            this.inputNhapDoDai.unformat = this.inputNhapDoDai.defaultUnformat
            this.doDaiMoi = 0
        },
        onClickBtnXacNhan() {
            this.$emit('ok', this.doDaiMoi)
        }
    }
}
</script>
