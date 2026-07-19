
<template src="./Giaophieu.html"></template>

<style scoped src="./Giaophieu.scss"></style>

<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import api from "../../api/index.js";
import moment from 'moment'
export default {
    name: 'Giaophieu',
    props: {
        ma_yc: {
            type: String,
            default: ''
        },
        tthd_id: {
            type: Number,
            default: 0
        },
        hdkenh_id: {
            type: Number,
            default: 0
        },
    },
    components: {
        ActionTop,
    },
    data() {
        return {
            huonggiao: 0,
            dsHuonggiao: [],
            dsDonvigiao: [],
            dsNhanviengiao: [],
            dsNhanviennhan:[],
            dsDonvinhan: [],
            donvi_giao_id: null,
            donvi_nhan_id: null,
            nhanvien_giao_id: null,
            nhanvien_nhan_id: null,
            items: {},
            noidung: "",
            ngaygiao: moment().format('DD/MM/YYYY')
        }
    },
    mounted() {
    },
    methods: {
        showModal() {
            this.$refs["GiaophieuModal"].show()
        },
        hideModal() {
            this.$refs["GiaophieuModal"].hide();
            this.dsHuonggiao=[]
            this.dsDonvigiao=[]
            this.dsDonvinhan=[]
            this.dsNhanviengiao=[]
            this.dsNhanviengiao=[]
            this.noidung = ""
        },
        async handleShowModal() {
            await this.getDsHuonggiao();
            await this.getDsDonvigiao();
            await this.getDsDonvinhan();
        },
        //Lấy danh sách huonggiao
        async getDsHuonggiao() { 
            let input = {
                tthd_id: this.tthd_id
            }
            let data = this.GetDataList(await api.dsHuongGiao(this.axios, input));
            this.dsHuonggiao = data
            this.huonggiao = data[0].huonggiao_id
        },
        //Lấy danh sách đơn vị giao
        async getDsDonvigiao() { 
            let data = this.GetDataList(await api.dsDonViGiao(this.axios,{}));
            this.dsDonvigiao = this.list2treeNew(data)
            this.donvi_giao_id = this.dsDonvigiao[0].donvi_id
            this.getDsNhanviengiao()
        }, 
        //Lấy danh sách đơn vị nhận
        async getDsDonvinhan() { 
            let data = this.GetDataList(await api.dsDonViNhan(this.axios,{})); 
            //this.dsDonvinhan = data
            this.dsDonvinhan = this.list2treeNew(data)
            this.donvi_nhan_id = this.dsDonvinhan[0].donvi_id
            this.getDsNhanviennhan()
        },
        list2treeNew(list) {
            let rootList = list.filter(x => (x.donvi_cha_id == null));
            let openList = [];
            rootList.forEach(item => {
                item.id = item.donvi_id;
                item.label = item.ten_dv ? item.ten_dv : "Null";
                item.level = 0;
                openList.push(item);
                let children = list.filter(x => x.donvi_cha_id == item.donvi_id);
                if (children.length > 0) {
                    item.children = children;
                    children.forEach(childItem => {
                        childItem.level = item.level + 1;
                        childItem.id = childItem.donvi_id;
                        childItem.label = childItem.ten_dv ? childItem.ten_dv : "Null";
                        openList.push(childItem);
                        let grandchildren = list.filter(x => x.donvi_cha_id == childItem.donvi_id);
                        if (grandchildren.length > 0) {
                            childItem.children = grandchildren;
                            grandchildren.forEach(grandchildItem => {
                                grandchildItem.level = childItem.level + 1;
                                grandchildItem.id = grandchildItem.donvi_id;
                                grandchildItem.label = grandchildItem.ten_dv ? grandchildItem.ten_dv : "Null";
                                openList.push(grandchildItem);
                                let greatGrandchildren = list.filter(x => x.donvi_cha_id == grandchildItem.donvi_id);
                                if (greatGrandchildren.length > 0) {
                                    grandchildItem.children = greatGrandchildren;
                                    greatGrandchildren.forEach(greatGrandchildItem => {
                                        greatGrandchildItem.level = grandchildItem.level + 1;
                                        greatGrandchildItem.id = greatGrandchildItem.donvi_id;
                                        greatGrandchildItem.label = greatGrandchildItem.ten_dv ? greatGrandchildItem.ten_dv : "Null";
                                        openList.push(greatGrandchildItem);
                                    });
                                }
                            });
                        }
                    });
                }
            });
            let rootList_new = openList.filter(x => (x.donvi_cha_id == null && x.level < 4));
            return rootList_new;
        },
        //Lấy danh sách đơn vị giao
        async getDsNhanviengiao() { 
            let input = {
                donvi_id: this.donvi_giao_id
            }
            let data = this.GetDataList(await api.dsNhanVienGiao(this.axios,input));
            this.dsNhanviengiao = data
            this.nhanvien_giao_id = data[0].nhanvien_id
        },
        //Lấy danh sách đơn vị nhận
        async getDsNhanviennhan() { 
            let input = {
                donvi_id: this.donvi_nhan_id
            }
            let data = this.GetDataList(await api.dsNhanVienNhan(this.axios,input));
            this.dsNhanviennhan = data
            this.nhanvien_nhan_id = data[0].nhanvien_id
        },
         async Ghilai_click() {
            try {
                this.loading(true);
                let input = {
                    "hdkenh_id":this.hdkenh_id,
                    "huonggiao_id":this.huonggiao,
                    "nguoigiao":this.nhanvien_giao_id,
                    "donvigiao":this.donvi_giao_id,
                    "nguoinhan":this.nhanvien_nhan_id,
                    "donvinhan":this.donvi_nhan_id,
                    "ma_yc":this.ma_yc,
                    "noidung":this.noidung,
                    "tthd_id":this.tthd_id
                }
                await api.themMoiPhieuGiao(this.axios,input).then((res) => {
                    if(res.data.data =="ok"){
                            this.$root.$toast.success("Giao phiếu thành công !");
                            this.$emit("ghilai");
                    }else{
                        this.$toast.error(res.data.data);
                    }
                });
            } catch (error) {
                console.log(error)
                this.$toast.error('Giao phiếu không thành công!');
                return false;
            } finally {
                this.loading(false);
            }
        },
        onChangeDonvigiao(item){
            console.log(item)
            this.donvi_giao_id = item.donvi_id
            this.getDsNhanviengiao()
        },
        onChangeDonvinhan(item){
            this.donvi_nhan_id = item.donvi_id
            this.getDsNhanviennhan()
        },
        onChangeNhanviengiao(){
        },
        onChangeNhanviennhan(){
        },
        changeNgaygiao(date){
          this.ngaygiao =  moment(this.ngaygiao).format("DD/MM/YYYY");
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
