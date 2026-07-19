<template src="./popupTraCuuCoSoHaTang.html"> </template>

<script>
    import ActionTop from "@/components/kylq_components/ActionTop.vue";
    import api from './api';
    import apiHelper from './api.helper';
    import moment from 'moment';
    export default {
        name: "popupTraCuuCoSoHaTang",
        components: {
            ActionTop,
            moment,
            api,
            apiHelper
        },
        data (){
            return {
                target: '.main-wrapper',
                animationSettings: { effect: 'Zoom' },
                actions: [
                    // {
                    //     id: "timkiem",
                    //     name: "Tìm kiếm",
                    //     active: true,
                    //     icon_class: "one-search"
                    // },
                    {
                        id: "chapnhan",
                        name: "Chấp nhận",
                        active: true,
                        icon_class: "one-file-detail"
                    },
                ],
                cboTinh: {
                    value: "",
                    list: []
                },
                txtMaCSHT: {
                    value: ""
                },
                dsCSHT: {
                    columns: [
                        {
                            fieldName: "building_code", headerText: "Mã CSHT", allowFiltering: true, textAlign: "center"
                        },
                        {
                            fieldName: "ma_tinh_tp", headerText: "Tỉnh", allowFiltering: true, textAlign: "center"
                        },
                        {
                            fieldName: "ten_quan_huyen", headerText: "Quận Huyện", allowFiltering: true, textAlign: "center"
                        },
                        {
                            fieldName: "ttvt", headerText: "Trung tâm VT", allowFiltering: true, textAlign: "center"
                        },
                    ],
                    data: []
                }
            }
        },
        created(){

        },
        mounted () {

        },
        watch: {
            'cboTinh.value' : {
                async handler(value){
                    await this.LayDsCSHT(value)
                }
            }
        },
        methods: {
            reset(){
                this.cboTinh.value = ""
                this.txtMaCSHT.value = ""
                this.dsCSHT.data = []
            },
            onActionClick(action){
                switch (action.id) {
                    case "timkiem":
                        this.TimKiem()
                        break;
                    case "chapnhan":
                        this.ChapNhan()
                        break;
                    default:
                        break;
                }
            },
            async dialogOpen(){
                this.loading(true)
                try{
                    Promise.all([this.LayDSTinh()])
                }
                catch(e){
                    console.log("e",e)
                }
                finally{
                    this.loading(false)
                }
            },
            async LayDsCSHT(value){
                this.loading(true)
                try{
                    let input = {
                        vtinh_id: value
                    }
                    const response = await api.Lay_ds_CSHT(input)
                    this.dsCSHT.data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                    if(this.dsCSHT.data.length > 0){
                        this.$toast.success("Tìm thấy dữ liệu")
                    }
                    else{
                        this.$toast.info("Không có dữ liệu")
                    }
                }
                catch(e){
                    console.log("e",e)
                }
                finally{
                    this.loading(false)
                }
            },
            async LayDSTinh(){
                this.loading(true)
                try{
                    const response = await api.Lay_ds_Tinh()
                    this.cboTinh.list = apiHelper.getDataFromResponseApiReturnArray(response) || []
                    this.cboTinh.list = this.cboTinh.list.map((e) => ({
                        id: e.tinh_id,
                        text: e.tentinh
                    }))
                    this.cboTinh.value = this.cboTinh.list[0].id

                }
                catch(e){
                    console.log("e",e)
                }
                finally{
                    this.loading(false)
                }
            },
            async ChapNhan(){
                this.loading(true)
                try{
                    if(this.txtMaCSHT.value.trim() == "" || this.txtMaCSHT.value == null){
                        this.$toast.error("Chưa có thông tin Mã CSHT!")
                        this.$refs.txtMaCSHT.focus()
                        return
                    }

                    this.$emit("chapnhan",this.txtMaCSHT.value)
                    this.closeModal()
                }
                catch(e){
                    console.log("e",e)
                }
                finally{
                    this.loading(false)
                }
            },
            select_selectedItemsChangedDsCSHT(selectedItem){
                if(selectedItem.length > 0){
                    this.txtMaCSHT.value = selectedItem[0].building_code
                }
            },
            openModal() {
                this.$refs.popupTraCuuCSHT.show()
            },
            closeModal () {
                this.reset()
                this.$refs.popupTraCuuCSHT.hide()
            },
        }
    }
</script>

<style scoped>

</style>