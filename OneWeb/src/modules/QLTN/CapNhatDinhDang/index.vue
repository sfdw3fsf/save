<template src="./index.template.html"></template>
<script>
import Vue from "vue"
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import { AccordionPlugin } from '@syncfusion/ej2-vue-navigations';

Vue.use(DialogPlugin)
Vue.use(AccordionPlugin)

import { mapActions, mapGetters } from 'vuex'
import API from '@/modules/QLTN/api/CapNhatDinhDangAPI.js'
import FormatGrid from './components/FormatGrid.vue'
import moment from 'moment'
import xlsx from "xlsx"

export default Vue.extend({
    components: { FormatGrid },
    name: 'CapNhatDinhDang',   
    
    data() {
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            params: {
                //Print Type: true: Đồ họa; false: Ký tự
                printType: true,
                column_Show: true,
                IDSetting_Show: true
            },            
            format_grid: {
                items: [],
                selected: {}
            },            
            formarTypes: [
                { id: '0', text: 'Page_Level'},
                { id: '1', text: 'Invoice_Level'},
                { id: '2', text: 'Sub'},
                { id: '3', text: 'Detail'}                
            ]
        }
    },
    
    methods: {
        async dialogOpen() {
            await this.getDSDinhDang();
        },

        dialogClose() {        
        },

        openDialog () {
            this.$refs.dlgCapNhatDinhDang.show();
        },

        close() {
            this.format_grid.items = [];
            this.$refs.dlgCapNhatDinhDang.hide();
        },

        formatSelected(e) {
            let index = parseInt(e);
            let format = this.format_grid.items[index];
            if (format !== undefined) {
                this.format_grid.selected = format;
            }
        },

        getNewID() {            
            let lastId = Number(this.format_grid.items[this.format_grid.items.length - 1].FORMAT_ID);
            for (let i = 1; i <= lastId; i++)
            {
                let idx = this.format_grid.items.findIndex(e => e.FORMAT_ID === i.toString());
                if (idx !== -1)
                    continue;                    
                else 
                    return i;
            }
            return 0;
        },

        onAdd() {
            let id = this.getNewID();
            if (id === 0)
                id = Number(this.format_grid.items[this.format_grid.items.length - 1].FORMAT_ID) + 1;
            let format = {
                FORMAT_ID: id,
                FORMAT_TYPE: '1',
                FORMATTYPE: 'Invoice_Level',
                FORMAT_NAME: 'Format 0',
                PRINT_TYPE: this.params.printType ? 1 : 0,
                ORDER_ID: 0
            }
            this.format_grid.items.push(format);
            this.format_grid.items = [...this.format_grid.items];   //Refresh grid
        },

        async onDelete() {
            if (this.format_grid.items.length <= 0)
                return this.$toast.error('Chưa có dữ liệu định dạng để xóa!');

            let formatChecked = this.$refs.formatTable.getCheckedRecords();
            if (formatChecked.length <= 0)
                return this.$toast.error('Bạn chưa chọn định dạng để xóa!');
            
            try {                
                await this.$bvModal.msgBoxConfirm('Bạn muốn xoá định dạng này khỏi cơ sở dữ liệu không?', {
                "title": 'Thông báo',
                "size": 'lg',
                "buttonSize": 'lg',
                "okVariant": 'primary',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Hủy bỏ',
                "hideHeaderClose": false,
                "centered": true,
                "modal-class": ["f18","text-center"],
                })
                .then(async(value) => {
                    if (value){
                        for (let item of formatChecked) {
                            let rs = await this.deleteDinhDang(item.FORMAT_ID);
                            if (!rs) {
                                await this.loadFormat(this.params.printType);
                                return;
                            }
                        }
                        this.$toast.success('Xóa định dạng thành công!');
                        await this.loadFormat(this.params.printType);
                    }
                })
                .catch(err => {
                    // An error occurred
                })
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            }
        },

        async onSave() {
            try {                
                await this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật định dạng này không?', {
                "title": 'Thông báo',
                "size": 'lg',
                "buttonSize": 'lg',
                "okVariant": 'primary',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Hủy bỏ',
                "hideHeaderClose": false,
                "centered": true,
                "modal-class": ["f18","text-center"],
                })
                .then(async(value) => {
                    if (value){
                        let rs = await this.updateDinhDang(this.format_grid.selected);
                        if (rs)
                            this.$toast.success('Cập nhật thành công');
                    }
                })
                .catch(err => {
                    // An error occurred
                })
            } catch (error) {
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            }
        },

        onDesign() {
            if (this.format_grid.items.length <= 0)
                this.$toast.error("Chưa có định dạng để thiết kế!");
            else if (this.format_grid.selected === null || this.format_grid.selected === {})
                this.$toast.error("Chưa chọn định dạng để thiết kế!")
            else 
                this.$router.push({ name: 'ThietKeDinhDang' });
        },

        async loadFormat(printType) {
            await this.getDSDinhDang();
            if (printType)
                this.format_grid.items = this.format_grid.items.filter(e => e.PRINT_TYPE === '1');
            else 
                this.format_grid.items = this.format_grid.items.filter(e => e.PRINT_TYPE === '0');
        },

        async getDSDinhDang() {
            try {
                this.loading(true);
                this.format_grid.items = [];
                let response = await API.getDSDinhDang(this.axios);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    this.format_grid.items = response.data.data.map((item) => ({
                        ...item,
                        FORMATTYPE: this.formarTypes.find(e => e.id === item.FORMAT_TYPE).text
                    }));
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async updateDinhDang(item) {
            try {
                this.loading(true);
                let apiBody = {
                    columnCount: item.COLUMNCOUNT,
                    columnSpacing: item.COLUMNSPACING,
                    columnWidth: item.COLUMNWIDTH,
                    formatId: item.FORMAT_ID,
                    formatName: item.FORMAT_NAME,
                    formatType: item.FORMAT_TYPE,
                    newPage: item.NEWPAGE,
                    note: item.NOTE,
                    orderId: item.ORDER_ID,
                    printType: item.PRINT_TYPE
                };
                let response = await API.updateDinhDang(this.axios, apiBody);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    return true;
                } else return false
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async deleteDinhDang(id) {
            try {
                this.loading(true);                
                let response = await API.deleteDinhDang(this.axios, id);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    return true;
                } else return false
            }
            catch (error) {
                this.$toast.error(error.data.message_detail);
                return false;
            }
            finally {
                this.loading(false);
            }
        }
    },

    watch: {
        'params.printType': async function(newVal, oldVal) {
            await this.loadFormat(newVal);
        }
    }
})
</script>
