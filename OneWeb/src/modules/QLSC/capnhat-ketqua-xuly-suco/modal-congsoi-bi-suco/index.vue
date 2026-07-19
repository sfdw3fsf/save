<template src="./index.template.html"></template>
<style>
    .capnhat-ketqua-xuly-suco .tree-plus ul li > a[is-root]:after {
        border: 0 !important;
    }
</style>
<script>
export default {
    props:{
        modalId: String,
        inputData: Object // {LOAIPTM_ID: 0, PTM_ID: 0, CONGSOI: '1,2,3'}
    },
    data() {
        return {
            kieu: 1,
            thietBi: null,
            dsCongSoi: [],
            data: null
        }
    },
    methods: {
        onModalHidden() {
            this.dsThietBi = null
            this.dsCongSoi = []
            this.data = null
        },
        async onModalShown() {

            if (!!this.inputData) {
                this.data = this.inputData
            }

            this.dsCongSoi = !this.data.CONGSOI ? [] : this.data.CONGSOI.split(',')

            try {
                this.loading(true)
                await this.layThietBi()
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        onClickBtnLamMoi() {
            this.onModalShown()
        },
        onClickThietBi(event, thietBi) {
            if (event.target.getAttribute('is-root') == '1') {
                thietBi.EXPAND = !thietBi.EXPAND
            }
        },
        setData(data) {
            this.data = data
        },
        async layThietBi() {

            let data = {
                loaitbi_id: this.data.LOAIPTM_ID,
	            thietbi_id: this.data.PTM_ID
            }

            try {
                let response = await this.$root.context.post(`/web-suco/danhmuc/cay-thietbi`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    
                    let dsThietBi = response.data

                    dsThietBi.forEach(item => {

                        let children = []

                        dsThietBi.forEach(item1 => {
                            if (item1.THIETBI_CHA_ID == item.THIETBI_ID) {
                                children.push(item1)
                            }
                        })

                        if (children.length > 0) {
                            item.EXPAND = true
                            item.CHILDREN = children
                        }
                    })

                    this.thietBi = dsThietBi.filter(item => !item.THIETBI_CHA_ID)[0]
                } else {
                    this.thietBi = null
                }
            } catch (err) {
                this.thietBi = null
            }
        }
    }
}
</script>
