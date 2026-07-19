
<template src="./Ganloaisanpham.html"></template>

<style scoped src="./Ganloaisanpham.scss"></style>

<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import api from "../../api/index.js";
export default {
    name: 'Ganloaisanpham',
    props: {
        ma_kenh_ban: {
            type: String,
            default: ''
        },
        ten_kenh_ban: {
            type: String,
            default: ''
        },
    },
    components: {
        ActionTop,
    },
    data() {
        return {
            DsSanphamChuagan: [],
            DsSanphamDagan: [],
            DsQuyen: [],
            items: {},
            txtKenhban: "",
            SanphamChuagan_Selected: [],
            SanphamDagan_Selected: [],
            options: {
               DsGan:[],
               DsChuaGan:[] 
            },
        }
    },
    mounted() {
    },
    methods: {
        showModal() {
            this.$refs["ganloaisanphamModal"].show()
        },
        hideModal() {
            this.$refs["ganloaisanphamModal"].hide();
            this.DsSanphamChuagan=[]
            this.DsSanphamDagan=[]
        },
        async handleShowModal() {
            //Khi show modal
            setTimeout(() => {
                //this.initDuLieu()
            }, 500)

        },
        onLayTT(){
            this.getDsSanpham_Dagan();
            this.getDsSanpham_Chuagan();
        },
        //Lấy danh sách sản phẩm đã gán
        async getDsSanpham_Dagan() {
            let input = {
                ma_kenh_ban: this.ma_kenh_ban
            }
            let data = this.GetDataList(await api.getDsSanphamDagan(this.axios, input));
            this.DsSanphamDagan = data
        },
        //Lấy danh sách sản phẩm chưa gán
        async getDsSanpham_Chuagan() {
            let input = {
                ma_kenh_ban: this.ma_kenh_ban
            }
            let data = this.GetDataList(await api.getDsSanphamChuagan(this.axios, input));
            this.DsSanphamChuagan = data
        },
        selectedItemsDagan(data){
            this.options.DsGan = data;
        },
        selectedItemsChuagan(data){
            this.options.DsChuaGan = data;
        },
        btnGan_Click()
        {
            
            try
            {
                if (this.options.DsChuaGan.length > 0) {
                    for (let i = 0; i < this.options.DsChuaGan.length; i++) {
                        this.DeleteRow(this.options.DsChuaGan[i].ma_sp,this.DsSanphamChuagan);
                        this.AddRow(this.options.DsChuaGan[i],this.DsSanphamDagan);
                    }
                }else{
                    this.$toast.error("Bạn chưa chọn sản phẩm để bỏ gán!");
                }
                this.options.DsChuaGan= [];
            }
            catch (err)
            {
                this.$toast.error("Lỗi không thể thực hiện: " + err.message);
            }
        },
        btnBogan_Click()
        {
            try
            {
               
                if (this.options.DsGan.length > 0) {
                    for (let i = 0; i < this.options.DsGan.length; i++) {
                       
                        this.DeleteRow(this.options.DsGan[i].ma_sp,this.DsSanphamDagan);
                        this.AddRow(this.options.DsGan[i],this.DsSanphamChuagan);
                    }
                }else{
                    this.$toast.error("Bạn chưa chọn sản phẩm để gán!");
                }
                this.options.DsGan= [];
            }
            catch (err)
            {
                this.$toast.error("Lỗi không thể thực hiện: " + err.message);
            }
        },
        DeleteRow(keyDelete,data) {
                for (let i = 0; i < data.length; i++) {
                    if (data[i].ma_sp.toUpperCase() == keyDelete.toUpperCase()) {
                        data.splice(i, 1);
                    }
                }
        },
        AddRow(data,data_dich) {
                data_dich.push({
                ma_sp: data.ma_sp,
                ten_sp: data.ten_sp});
        },
        async GanSanPham(){
            let DsMaSP = [];
            for (let i = 0; i < this.DsSanphamDagan.length; i++) {
                DsMaSP.push(this.DsSanphamDagan[i].ma_sp);
            }
            console.log(DsMaSP)
            let input = {
                ma_kenh_ban: this.ma_kenh_ban,
                ma_sp: DsMaSP
            }
            let data = this.GetDataList(await api.ganSanpham(this.axios, input));
            if(data == 'OK'){
                this.$toast.success('Gán sản phẩm thành công!');
                this.onLayTT();
            }
        },
        GetDataList: function (response) {
            console.log(response)
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {} else {
                this.$root.$toast.error('Lấy dữ liệu không thành công')
            }
            return []
        },
        GetData: function (response) {
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
                return null
            } else {
                this.$root.$toast.error('Lấy dữ liệu không thành công')
            }
            return null
        },
    }
}
</script>
