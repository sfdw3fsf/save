<template src="./index.template.html"></template>
<script>
export default {
    data() {
        return {
            dsMucDoSuCo: []
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
            this.dsMucDoSuCo = []
        },
        onModalShown() {
            this.onClickBtnLamMoi()
        },
        numberFormat(number) {
            number = `${number}`
            let group = []
            
            while (number.length > 3) {
                group.push(number.substr(number.length - 3))
                number = number.substr(0, number.length - 3)
            }

            if (number.length > 0) {
                group.push(number)
            }

            return group.reverse().join()
        },
        onQueryCellInfo(args) {
            if (!!args.column.field) {
                args.cell.style.setProperty('background-color', `#${args.data.MAUNEN}`, 'important')
                args.cell.style.color = `#${args.data.MAUCHU}`
            }

            if (args.column.field == 'CANHBAO_DT') {
                args.cell.innerHTML = `≥ ${this.numberFormat(args.data.CANHBAO_DT)} đ` 
            }
        },
        async onClickBtnLamMoi() {
            try {
                this.loading(true)
                await this.layDsMucDoSuCo()
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async layDsMucDoSuCo() {
            try {
                let response = await this.$root.context.get(`/web-suco/danhmuc/mucdo-suco`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsMucDoSuCo = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsMucDoSuCo = []
            }
        },
     }
}
</script>

