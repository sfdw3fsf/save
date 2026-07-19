<template src="./index.template.html"></template>
<script>
import { Edit, Sort, Filter, ForeignKey, Resize, Freeze, Page, Group } from "@syncfusion/ej2-vue-grids"
import { L10n } from '@syncfusion/ej2-base'

L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})

export default {
    provide: {
        grid: [Edit, Sort, Filter, ForeignKey, Resize, Freeze, Page, Group ]
    },
    props:{
        modalId: String,
        data: Object
    },
    data() {
        return {
            dsThueBao: [],
            thueBaoDuocChon: null,
            keyThueBaoDuocChon: null
        }
    },
    computed: {
        enableBtnChapNhan() {
            return this.dsThueBao && this.dsThueBao.length > 0
        }
    },
    watch: {
        keyThueBaoDuocChon(newVal) {
            this.thueBaoDuocChon = !newVal ? null : this.dsThueBao.find(item => item.KEY == newVal)
        },
        dsThueBao(newVal) {
            if (!!newVal && newVal.length > 0) {
                this.keyThueBaoDuocChon = newVal[0].KEY
            }
        }
    },
    methods: {
        onHiddenModal() {
            this.dsThueBao = [],
            this.thueBaoDuocChon = null
            this.keyThueBaoDuocChon = null
        },
        async onShownModal() {
            let data = {
                maThueBao: this.data.maThueBao,
                dichVuID: this.data.dichVuId == -1 ? 0 : this.data.dichVuId
            }
            
            try {
                this.loading(true)
                await this.layDsThueBao(data)
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsThueBao.length == 0) {
                this.$toast.error(`Không tìm thấy thông tin thuê bao ${this.data.maThueBao}`)
            }
        },
        getColumnTemplateChon(parent) {
            return function () {
                return { 
                    template: {
                        template: `
                            <div class="check-action">
                                <input 
                                    type="radio" 
                                    class="check"
                                    v-model="parent.keyThueBaoDuocChon"
                                    :value="data.KEY"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return {
                                parent: parent,
                                data: { }
                            }
                        }
                    }
                }
            }
        },
        onClickBtnChapNhan() {

            if (!this.thueBaoDuocChon) {
                this.$toast.error('Chưa chọn thuê bao.')
                return
            }

            this.$emit('select', this.thueBaoDuocChon)
            this.$bvModal.hide(this.modalId)
        },
        async layDsThueBao(data) {

            data.kieu = 0

            try {
                let response = await this.$root.context.post('/web-cabman/danhmuc/layDsThueBaoTheoMaTBVaDichVu', data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach((item, index) => item.KEY = index + 1)
                    this.dsThueBao = response.data
                } else {
                    this.dsThueBao = []
                }
            } catch (err) {
                this.dsThueBao = []
            }
        },
    }
}
</script>
<style>
    .chon-thuebao-dauchuyen .e-grid {
        font-family: "Roboto", Arial !important;
    }

    .chon-thuebao-dauchuyen .e-grid .e-filterbarcell {
        padding: 5px 7px !important;
        border-width: 1px 0 0 1px !important;
    }

    .chon-thuebao-dauchuyen .e-grid th.e-headercell,
    .chon-thuebao-dauchuyen .e-grid .e-gridcontent td.e-rowcell {
        padding: 5px 7px !important;
        height: 30px !important;
    }

    .chon-thuebao-dauchuyen .e-grid th.e-grouptopleftcell,
    .chon-thuebao-dauchuyen .e-grid th.e-headercell, 
    .chon-thuebao-dauchuyen .e-grid th.e-filterbarcell {
        background-color: #BAE7FF !important;
        border-color: white !important;
    }

    .chon-thuebao-dauchuyen .e-grid th.e-filterbarcell > div input.form-control {
        padding-bottom: 0 !important;
        padding-top: 0 !important;
        height: 26px !important;
    }

    .chon-thuebao-dauchuyen .e-grid td.e-active {
        background: #F9E1A9 !important;
    }

    .chon-thuebao-dauchuyen .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .chon-thuebao-dauchuyen .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .chon-thuebao-dauchuyen .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .chon-thuebao-dauchuyen .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .chon-thuebao-dauchuyen .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
        background-color: #FFF9EB !important;
    }

    .chon-thuebao-dauchuyen .e-grid td.e-rowcell { 
        line-height: 20px !important;
        padding: 5px 7px !important;
    }

    .chon-thuebao-dauchuyen .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
        border: 0 !important;
    }

    .chon-thuebao-dauchuyen .e-grid .e-filtertext.e-input.e-disable {
        display: none !important;
    }

    .chon-thuebao-dauchuyen .e-grid tr.e-emptyrow td {
        text-align: center !important;
    }

    .chon-thuebao-dauchuyen .e-grid .e-pagerexternalmsg,
    .chon-thuebao-dauchuyen .e-grid .e-sortnumber {
        display: none !important;
    }

    .chon-thuebao-dauchuyen .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="0"],
    .chon-thuebao-dauchuyen .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="0"] > div {
        padding: 0 !important;
    }

    .chon-thuebao-dauchuyen .disable-btn:hover {
        background-color: white !important;
    }

    .chon-thuebao-dauchuyen .disable-btn a {
        color: #6c757d !important;
    }

    .chon-thuebao-dauchuyen .disable-btn a:hover {
        cursor: default;
        color: #6c757d;
    }
</style>