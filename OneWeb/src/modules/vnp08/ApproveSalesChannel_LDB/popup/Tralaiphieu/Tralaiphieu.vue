
<template src="./Tralaiphieu.html"></template>

<style scoped src="./Tralaiphieu.scss"></style>

<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import api from "../../api/index.js";
import moment from 'moment'
export default {
    name: 'Tralaiphieugiao',
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
            nguoiduyet:"",
            ngaygiao: moment().format('DD/MM/YYYY')
        }
    },
    mounted() {
    },
    methods: {
        showModal() {
            this.$refs["TralaiModal"].show()
        },
        hideModal() {
            this.$refs["TralaiModal"].hide();
            this.lydo = "";
            this.ngayduyet = moment().format('DD/MM/YYYY'),
            this.donvi_duyet_id = 0
            this.nguoiduyet = ""
        },
        async handleShowModal() {
            await this.getDsDonviDuyet();
            this.nguoiduyet = this.$root.context.user.getTenNhanVien()
        },
        async getDsDonviDuyet() { 
            let data = this.GetDataList(await api.dsDonViDuyet(this.axios,{}));
            this.dsDonviduyet = data
            this.donvi_duyet_id = this.donvi_giao_id
        },
         async Ghilai_click() {
            try {
                this.loading(true);
                this.$bvModal.msgBoxConfirm("Bạn muốn từ chối đề xuất ký hợp đồng này không?", {
                        title: '',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Không đồng ý',
                    }).then(async v => {
                        if(v) {
                            let input = {
                                "hdkenh_id":this.hdkenh_id,
                                "phieu_id":this.phieu_id,
                                "ttph_id": 3,
                                "tthd_id":this.tthd_id,
                                "noidung_thuchien":this.noidung,
                                "nguoi_thuchien":Number(this.$auth.getNhanVienID()),
                                "donvi_thuchien":this.donvi_duyet_id,
                                "ngay_thuchien":this.ngayduyet,
                                "lydo":this.lydo

                            }
                            await api.tuChoiPhieuGiao(this.axios,input).then((res) => {
                                if(res.data.data =="ok"){ 
                                        this.$root.$toast.success("Từ chối đề xuất thành công !");
                                        this.$emit("xacnhan");
                                }else{
                                    this.$toast.error(res.data.data);
                                }
                            });
                        }
                    });
            } catch (error) {
                console.log(error)
                this.$toast.error('Từ chối đề xuất không thành công!');
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
