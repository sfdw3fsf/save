<template src='./index.html'>
</template>

<script>
import { Filter, Edit, Page } from "@syncfusion/ej2-vue-grids";
export default {
    data() {
        return {
            dsChuKy: [],
            arr: [],
            editSettings: { allowEditing: true, mode: "Batch" },
            selectOptions: { type: 'Single', allowColumnSelection: true, enableToggle: false },
            mucdo: { required: true },
            cap1: { required: true, number: true },
            cap2: { required: true, number: true },
            cap3: { required: true, number: true },
            editparams: { params: { popupHeight: '300px' }},
            pageSettings: {pageCount: 5, pageSize: 10},
            cellIndex: {},
            dsChuKyChuanHoa: [],
            columnTemplate: function () {
                return { 
                    template: {
                        template: '<span :id="data.index" :style="{display: display }" style="font-size: 12px;" class="col-mark-selected-row fa fa-play text-main"></span>',
                        data() {
                            return { 
                                data: { } 
                            }
                        },
                        computed: {
                            display() {
                            return parseInt(this.data.index) == this.$parent.getSelectedRowIndexes()[0] ? 'block' : 'none'
                            }
                        }
                    }
                }
            },
            filterBarTemplate: {
                create: function (args) {

                    let div = document.createElement('div')
                    div.classList.add('input-icon-right')
                    
                    let input = document.createElement('input')
                    input.classList.add('form-control')
                    input.id = args.column.foreignKeyField

                    let span = document.createElement('span')
                    span.classList.add('icon', 'nc-icon-outline', 'ui-1_zoom')

                    div.append(input)
                    div.append(span)

                    return div
                },
                write: function (args) {
                    
                    args.element.querySelector('input').addEventListener('input', (args) => {
                        let value = args.currentTarget.value

                        if (!value) {
                            this.parent.removeFilteredColsByField(args.currentTarget.id);
                        } else {
                            this.parent.filterByColumn(args.currentTarget.id, 'contains', value)
                        }
                    })
                }
            },
        }
    },
    provide: {
        grid: [Filter, Edit, Page]
    },
    methods: {
        clickTab: function(data) {
            this.$emit('clickTab', data);
        },
        layDsChuKy: async function() {
            try {
                var rs = await this.$root.context.get('/web-suco/danhmuc/dieuhanhtudong/chuky-nt');
                this.dsChuKy = rs.data;
                this.arr = JSON.parse(JSON.stringify(this.dsChuKy));
            } catch (error) {
                
            }
        },
        gridDsChuKy_selectedRowChanged: function() {

        },
        lamMoi: async function() {
            try {
                this.loading(true);
                await this.layDsChuKy();
            } catch (error) {
                this.$toast.error('Xảy ra lỗi');
            } finally {
                this.loading(false);
            }
        },
        ghiLai: async function() {
            if(this.dsChuKyChuanHoa.length > 0) {
                try {
                    this.loading(true);
                    var rs = await this.$root.context.post('/web-suco/danhmuc/dieuhanhtudong/capnhat-chuky-nt', this.dsChuKyChuanHoa);
                    if(rs.data.result) {
                        this.$toast.success('Cập nhật thành công');
                    }
                } catch (error) {
                    this.$toast.error('Cập nhật thất bại');
                } finally {
                    this.loading(false);
                }
            }
            else {
                this.$toast.error('Chưa có thông tin thay đổi');
            }
        },
        selected_ChuKy: function(args) {
            this.$refs['grid'].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
                item.style.display = args.rowIndex == parseInt(item.id) ? 'block' : 'none'
            })
        },
        onCellSavedChuKy: function() { 
            let chuKy = this.$refs['grid']
            let changes = chuKy.getBatchChanges()
            this.chuanHoaChuKy(changes.changedRecords);
        },
        chuanHoaChuKy: function(data) {
            this.dsChuKyChuanHoa = [];
            for(let i = 0; i < data.length; i++){
                let cap_1 = {
                    chuky_id: data[i].CAP_1_ID,
                    mucdo_id: data[i].MUCDO_ID,
                    chuky_dh: data[i].CAP_1_DH
                }
                this.dsChuKyChuanHoa.push(cap_1);

                let cap_2 = {
                    chuky_id: data[i].CAP_2_ID,
                    mucdo_id: data[i].MUCDO_ID,
                    chuky_dh: data[i].CAP_2_DH
                }
                this.dsChuKyChuanHoa.push(cap_2);
                
                let cap_3 = {
                    chuky_id: data[i].CAP_3_ID,
                    mucdo_id: data[i].MUCDO_ID,
                    chuky_dh: data[i].CAP_3_DH
                }
                this.dsChuKyChuanHoa.push(cap_3);
            }
        }
    },
    async mounted() {
        try {
            this.loading(true);
            await this.layDsChuKy();
        } catch (error) {
            this.$toast.error('Xảy ra lỗi');
        } finally {
            this.loading(false);
        }
    }
}
</script>
