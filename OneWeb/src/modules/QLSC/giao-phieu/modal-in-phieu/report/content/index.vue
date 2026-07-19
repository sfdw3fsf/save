<template src="./index.template.html"></template>
<script>
import { Group } from "@syncfusion/ej2-vue-grids"
export default {
    provide: {
        grid: [ Group ]
    },
    data() {
        return {
            columnWidth: {
                PHIEU_ID: 0,
                TINHTRANG: 0,
                DONVI_NHAN: 0,
                NGAYGIAO: 0,
                ND_GIAO: 0,
                KHAN_HIENTHI: 0,
                CHITIEU_TG: 0,
                CONLAI: 0,
                TRANGTHAI_PH: 0,
                NGAY_TRA: 0,
                LYDOTRA: 0,
                ND_TRA: 0,
                NGAY_TH: 0,
                NGUOI_TH: 0,
                GHICHU_TH: 0
            },
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
        setData(data) {

            Object.keys(this.columnWidth).forEach(item => {
                let column = data.columnModel.find(element => element.field == item)
                this.columnWidth[item] = column.width
            })

            this.kieuNhomPhieu = data.kieuNhomPhieu

            this.dsPhieu = data.dsPhieu.map(item => {
                let obj = {...item}
                obj.NGAYGIAO = !obj.NGAYGIAO ? '' : obj.NGAYGIAO.split(' ').join('\n')
                return obj
            })

            this.$refs['grid-phieu'].groupColumn(parseInt(data.kieuNhomPhieu) == 1 ? 'PHIEU_ID' : 'TINHTRANG')
        }
    }
}
</script>
<style>  
    .in-phieu-suco .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th,
    .in-phieu-suco .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th > div {
        padding: 0 !important;
    }

    .in-phieu-suco .e-grid .e-groupcaption {
        font-size: 10px !important;
    }

    .in-phieu-suco .e-grid .e-rowcell {
        white-space: break-spaces !important;
        font-size: 10px !important;
    }

    .in-phieu-suco .e-grid .e-headercelldiv {
        height: auto !important;
        white-space: break-spaces !important;
        font-size: 10px !important;
    }
</style>

