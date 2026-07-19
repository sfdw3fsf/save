<template src="./index.template.html"></template>
<script>
import ModalInPhieu from '../modal-in-phieu/index.vue'
import { Group, Resize } from "@syncfusion/ej2-vue-grids"
export default {
    provide: {
        grid: [ Group, Resize ]
    },
    components: { ModalInPhieu },
    data() {
        return {
            dsPhieu: [],
            kieuNhomPhieu: 1,
            groupSettingsGridPhieu: { 
                showDropArea: false, 
                columns: [], 
                captionTemplate: '${if(field == "PHIEU_ID")} ${if(!key)} &nbsp&nbspPhiếu ${else} &nbsp&nbspPhiếu ${key} ${/if} ${else} &nbsp&nbsp${key} ${/if}' 
            },
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
            this.dsPhieu = [],
            this.kieuNhomPhieu = 1
        },
        onModalShown() {
            let grid = this.$refs['grid-preview']
            grid.groupColumn(parseInt(this.kieuNhomPhieu) == 1 ? 'PHIEU_ID' : 'TINHTRANG')
        },
        setData(data) {

            this.kieuNhomPhieu = data.kieuNhomPhieu

            this.dsPhieu = data.dsPhieu.map(item => {
                let obj = {...item}
                obj.NGAYGIAO = !obj.NGAYGIAO ? '' : obj.NGAYGIAO.split(' ').join('\n')
                return obj
            })
        },
        onClickBtnInPhieu() {
            let modal = this.$refs['ref-modal-in-phieu']
            modal.setData({
                dsPhieu: this.dsPhieu, 
                kieuNhomPhieu: this.kieuNhomPhieu,
                columnModel: this.$refs['grid-preview'].ej2Instances.columnModel
            })
            modal.show()
        }
     }
}
</script>
<style>  
    .in-phieu-suco-preview .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th,
    .in-phieu-suco-preview .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th > div {
        padding: 0 !important;
    }

    .in-phieu-suco-preview .e-grid .e-groupcaption {
        font-size: 10px !important;
    }

    .in-phieu-suco-preview .e-grid .e-rowcell {
        white-space: break-spaces !important;
        font-size: 10px !important;
    }

    .in-phieu-suco-preview .e-grid .e-headercelldiv {
        height: auto !important;
        white-space: break-spaces !important;
        font-size: 10px !important;
    }
</style>

