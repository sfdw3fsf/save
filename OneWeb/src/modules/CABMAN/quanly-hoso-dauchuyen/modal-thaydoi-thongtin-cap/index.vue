<template src="./index.template.html"></template>
<script>
import ModalDauNoiThueBao from '../modal-daunoi-thuebao/index.vue'
export default {
    components: { ModalDauNoiThueBao },
    data() {
        return {
            loaiChuyen: 1,
            thueBao: null,
            tramTbiId: null,
            hasChange: false
        }
    },
    methods: {
        show() {
            return this.$refs['ref-modal'].show()
        },
        hide() {
            this.$refs['ref-modal'].hide()
        },
        onModalHidden() {
            if (this.hasChange) {
                this.$emit('change', {...this.thueBao})
            }
            this.loaiChuyen = 1
            this.thueBao = null
            this.tramTbiId = null
            this.hasChange = false
        },
        setThueBao(thueBao) {
            this.thueBao = thueBao
        },
        setTramTbiId(tramTbiId) {
            this.tramTbiId = tramTbiId
        },
        async onClickBtnXacNhan() {

            let choPhepDoiTramTbi = true
            let tramTbiId = -1

            if (this.loaiChuyen == 1) {
                choPhepDoiTramTbi = false
                tramTbiId = this.tramTbiId
            }

            let thongTinDauNoi = {
                choPhepDoiTramTbi: choPhepDoiTramTbi,
                tramTbiId: tramTbiId,
                chonTramTbiTheoTB: false,
                thueBaoId: this.thueBao.THUEBAO_ID,
                kieu: 0
            }
            
            let modal = this.$refs['modal-daunoi-thuebao']
            modal.setData(thongTinDauNoi)
            modal.show()
        },
        onChange() {
            this.hasChange = true
        }
    }
}
</script>
