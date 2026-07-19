<template>
    <b-modal ref="popupThemDSThueBao_TDTS_IMS" size="xl" body-class="p-0" hide-header
        hide-header-close @show="handleShowModal" hide-footer>
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title"><span class="icon one-notepad"></span>Bổ sung danh sách thuê bao IMS vào hợp đồng/phụ lục</div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a style="cursor: pointer;">
                            <span class="icon one-refresh"></span>Lấy TT
                        </a>
                        <a style="cursor: pointer;">
                            <span class="icon one-mobile-plus"></span>Thêm TB
                        </a>
                        <a style="cursor: pointer;">
                            <span class="icon one-phone-remove"></span>Xóa TB
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <k-table :columns="columns" :dataSources="dataSources"
                        @onSelectedRow="onSelectedRow" :rowSelectedIndex="1" :allowFilter="true" />
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
import KTable from '@/components/kylq_components/mirana/KTable.vue'
import api from '../api'
export default {
    props: {
        khachhang_id:{
            type: Number,
            default: 0
        },
        dsdichvuvt_id:{
            type: String,
            default: ''
        },
        dsloaihd_id:{
            type: String,
            default: ''
        },
        hdkh_id:{
            type: Number,
            default: 0
        }
    },
    components: {
        KTable
    },
    data() {
        return {
            columns: [
                {
                    field: "ma_gd",
                    label: "Mã giao dịch",
                    allowFilter: true
                },
                {
                    field: "ma_tb",
                    label: "Account",
                    allowFilter: true
                },
                {
                    field: "ten_tb",
                    label: "Tên thuê bao",
                    allowFilter: true
                },
                {
                    field: "loaihinh_tb",
                    label: "Loại hình",
                    allowFilter: true
                },
                {
                    field: "trangthai_tb",
                    label: "Trạng thái",
                    allowFilter: true
                },
                {
                    field: "tinhtrang",
                    label: "Tình trạng",
                    allowFilter: true
                }
            ],
            dataSources: [],
            itemSelected: null
        }
    },
    methods: {
        onSelectedRow(item){
            if (item) {
                this.itemSelected = Object.assign({},item);
            } 
        },
        showModal() {
            this.$refs['popupThemDSThueBao_TDTS_IMS'].show()
        },
        hideModal() {
            this.$refs['popupThemDSThueBao_TDTS_IMS'].hide()
        },
        handleShowModal(){
            //time out 500ms chờ binding view
            setTimeout(async ()=>{
                await this.khoiTaoDuLieu();
            },500)
        },
        hienThiDanhSachHDTBTheoKHID: async function() {
            let ds = [];
            // let self = this;
            // await api.getDB_HDTBTheoKHId_MaGD(this.axios, {
            //     "vkhachhang_id": this.khachhang_id,
            //     "vdichvuvt_id": this.dichvuvt_id,
            //     "vma_gd": this.ma_gd
            // }).then(res => {
            //     ds = res.data.data ? res.data.data : [];
            // }).catch(err => {
            //     self.$toast.error("Có lỗi: " + err.data.message);
            // });
            return ds;
        },
        khoiTaoDuLieu: async function() {
            this.dataSources = [];
            try {
                this.loading(true);
                this.dataSources = await this.hienThiDanhSachHDTBTheoKHID();
                if (this.dataSources.length == 0) {
                    let str = "Chưa có thông tin về khách hàng thanh lý có thuê bao: " + this.ma_tb + "\nKiểm tra lại !";
                    this.$toast.error(str);
                }
            } catch(e) {
                this.$alert('Đã xảy ra lỗi', '', {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: 'OK',
                    type: 'error'
                })
            } finally {
                this.loading(false)
            }
        }
    }
}
</script>
