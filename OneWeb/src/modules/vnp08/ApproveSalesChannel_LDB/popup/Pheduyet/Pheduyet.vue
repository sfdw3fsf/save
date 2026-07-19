
<template src="./Pheduyet.html"></template>

<style scoped src="./Pheduyet.scss"></style>

<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import api from "../../api/index.js";
import moment from 'moment'
export default {
    name: 'Pheduyetphieugiao',
    props: {
        phieu_id: {
            type: Number,
            default: 0
        },
        tthd_id: {
            type: Number,
            default: 0
        },
        hdkenh_id: {
            type: Number,
            default: 0
        },
        donvi_giao_id: {
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
            noidung: "",
            lydo: "",
            items: {},
            dsDonviduyet: [],
            donvi_duyet_id: 0,
            ngayduyet: moment().format('DD/MM/YYYY'),
            noidung: "",
            ngaygiao: moment().format('DD/MM/YYYY'),
            nguoiduyet: ""
        }
    },
    mounted() {
    },
    methods: {
        showModal() {
            this.$refs["PheDuyetModal"].show()
        },
        hideModal() {
            this.$refs["PheDuyetModal"].hide();
            this.noidung = "";
            this.ngayduyet = moment().format('DD/MM/YYYY'),
            this.donvi_duyet_id = 0;
            this.nguoiduyet  = ""
        },
        async handleShowModal() {
            await this.getDsDonviDuyet();
            this.nguoiduyet = this.$root.context.user.getTenNhanVien()
        },
        async getDsDonviDuyet() { 
            let data = this.GetDataList(await api.dsDonViDuyet(this.axios,{}));
            //this.dsDonviduyet = data
            this.dsDonviduyet = this.list2treeNew(data)
            this.donvi_duyet_id = this.donvi_giao_id
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
         async Ghilai_click() {
            try {
                this.loading(true);
                 this.$bvModal.msgBoxConfirm("Bạn muốn phê duyệt đề xuất ký hợp đồng này không?", {
                        title: '',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Không đồng ý',
                    }).then(async v => {
                        if(v) {
                                let input = {
                                    "hdkenh_id":this.hdkenh_id,
                                    "phieu_id":this.phieu_id,
                                    "ttph_id": 4,
                                    "tthd_id":this.tthd_id,
                                    "noidung_thuchien":this.noidung,
                                    "nguoi_thuchien":this.$auth.getNhanVienID(),
                                    "donvi_thuchien":this.donvi_duyet_id,
                                    "ngay_thuchien":this.ngayduyet,
                                }
                                await api.duyetPhieuGiao(this.axios,input).then((res) => {
                                    if(res.data.data =="ok"){
                                            this.$root.$toast.success("Duyệt đề xuất thành công !");
                                            this.$emit("xacnhan");
                                    }else{
                                        this.$toast.error(res.data.data);
                                    }
                                });
                             }
                    });
            } catch (error) {
                console.log(error)
                this.$toast.error('Duyệt đề xuất không thành công!');
                return false;
            } finally {
                this.loading(false);
            }
        },
        onChangeDonvigiao(){
            this.getDsNhanviengiao()
        },
        changeNgayduyet(date){
          this.ngayduyet =  moment(this.ngayduyet).format("DD/MM/YYYY");
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
