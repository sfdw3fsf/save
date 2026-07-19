<template src="./ParameterConfiguration.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import moment from 'moment';
import api from './api'
import apiHelper from "./api.helper"
import updatePopup from "./updatePopup.vue"
export default {
    name: "ParameterConfiguration",
    components: {
        api,
        ActionTop,
        apiHelper,
        moment,
        breadcrumb,
        updatePopup
    },
    data(){
        return {
            header: {
                title: 'Cấu hình tham số mặc định',
                list: [
                    { name: 'Cấu hình tham số mặc định', link: { name: 'Ui.cards' } },
                    {
                        name: 'Cấu hình tham số mặc định',
                        link: { name: 'Ui.buttons' }
                    }
                ]
            },
            actions: [
                {
                    id: "laydanhsach",
                    name: "Lấy danh sách",
                    active: true,
                    icon_class: "one-save"
                }
            ],
            Loading: false,
            DanhSachThamSo: {
                columns: [
                    {
                        fieldName: "ma_ts", headerText: "Mã tham số", allowFiltering: true, textAlign: 'left'
                    },
                    {
                        fieldName: "ten_ts", headerText: "Tên tham số", allowFiltering: true , textAlign: 'left'
                    },
                    {
                        fieldName: "ghichu", headerText: "Ghi chú", allowFiltering: true , textAlign: 'left'
                    },
                ],
                data: [],
                value: "",
                selectedItem: [],
            },
            click: 0,
            timer: null

        }
    },
    watch: {
        Loading: function(val) {
            this.$root.showLoading(val)
        }
            
    },
    created(){
        this.getDSThamSoMacDinh()
    },
    methods: {
        onActionClick(action){
            switch(action.id){
                case "laydanhsach":
                    this.LayDanhSach()
                    break;
                default:
                    break;
            }
        },
        LayDanhSach(){
            this.getDSThamSoMacDinh()
        },
        async getDSThamSoMacDinh(){
            this.Loading = true
            try{
                let input = {
                    p_phanvung_id: this.$root.token.getPhanVungID(),
                    p_ma_ts: ""
                }
                const response = await api.lay_danh_sach_tham_so(this.axios,input)
                this.DanhSachThamSo.data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                if(this.DanhSachThamSo.data.length > 0){
                    this.$toast.success("Lấy danh sách thành công")
                }
                else{
                    this.$toast.info("Không có dữ liệu")
                }
                this.Loading = false

            }
            catch(e){
                console.log("Error",e);
                this.Loading = false
            }
        },
        select_selectedItemsChangedDanhSachThamSo(selectedItem){
            var self = this
            this.click ++
            if(this.click == 1){
                this.timer = setTimeout(function() {
                    self.click = 0
                }, 200); 
            }
            else{
                this.$refs['UpdatePopup'].txtMaTS = selectedItem[0].ma_ts
                this.$refs['UpdatePopup'].txtTenTS = selectedItem[0].ten_ts
                this.$refs['UpdatePopup'].txtGhiChu = selectedItem[0].ghichu
                this.$refs['UpdatePopup'].showModal()
                clearTimeout(this.timer);
                self.click = 0;
            }
            
        },
        reloadDS(value){
            if(value == "Success"){
                this.getDSThamSoMacDinh()
            }
        }
    }
}
</script>

<style scoped>

</style>
