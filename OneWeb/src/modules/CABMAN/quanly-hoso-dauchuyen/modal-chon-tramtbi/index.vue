<template src="./index.template.html"></template>
<style>
    .chon-tram-tbi .show-detail {
        white-space: normal !important;
    }
</style>
<script>
export default {
    props:{
        modalId: String,
        valTtvtId: Number
    },
    data() {
        return {
            dsTtvt: [],
            dsTo: [],
            dsTramTbi: [],
            ttvtId: null,
            toId: null,
            tramTbiDuocChon: null,
            showDetail: false,
        }
    },
    watch: {
        async ttvtId(newVal) {

            if (!newVal) {
                this.dsTo = []
                this.toId = null
                return
            }

            try {
                this.loading(true)
                await this.layDsTo(newVal)
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsTo && this.dsTo.length > 0) {
                this.toId = this.dsTo[0].DONVI_ID
            } else {
                this.toId = null
            }
        },
        async toId(newVal) {

            if (!newVal) {
                this.dsTramTbi = []
                this.tramTbiDuocChon = null
                return
            }

            try {
                this.loading(true)
                await this.layDsTramThietBi(newVal)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
    },
    methods: {
        onModalHidden() {
            this.dsTtvt = []
            this.dsTo = []
            this.dsTramTbi = []
            this.ttvtId = null
            this.toId = null
            this.tramTbiDuocChon = null
            this.showDetail = false
        },
        async onModalShown() {
            try {
                this.loading(true)
                await this.layDsTtvt()
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsTtvt && this.dsTtvt.length > 0) {
                this.ttvtId = this.valTtvtId ? this.valTtvtId : this.dsTtvt[0].DONVI_ID
            }
        },
        onDoubleClickRow(rowIndex, dataItem) {
            this.tramTbiDuocChon = dataItem
            this.onClickBtnChon()
        },
        onClickBtnChon() {

            if (!this.tramTbiDuocChon) {
                this.$toast.error('Chưa chọn trạm thiết bị.')
                return
            }

            this.$emit('select', this.tramTbiDuocChon)
            this.$bvModal.hide(this.modalId)
        },
        onClickRowTable(data) {
            this.tramTbiDuocChon = data
        },
        async layDsTtvt() {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/layDsTTVTTrongToken')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTtvt = response.data
                } else {
                    this.dsTtvt = []
                }
            } catch (err) {
                this.dsTtvt = []
            }
        },
        async layDsTo(ttvtId) {
            try {
                let response = await this.$root.context.post(`/web-ecms/danhmuc/layDsToKTTheoNVTrongTokenVaDonVi`, {id: ttvtId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTo = response.data
                } else {
                    this.dsTo = []
                }
            } catch (err) {
                this.dsTo = []
            }
        },
        async layDsTramThietBi(toId) {
            try {
                let response = await this.$root.context.get(`/web-cabman/hoso-dauchuyen/danhsach-tram-thietbi?donvi_id=${toId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach(element => {
                        element.THONGTIN_BS = element.THONGTIN_BS.replace('\r\n', '. ')
                    })
                    this.dsTramTbi = response.data
                } else {
                    this.dsTramTbi = []
                }
            } catch (err) {
                this.dsTramTbi = []
            }
        },
    }
}
</script>
