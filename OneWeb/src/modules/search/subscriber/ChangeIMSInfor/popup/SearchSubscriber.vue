<template>
    <b-modal ref="popupSearchSubscriber" size="xl" body-class="p-0" hide-header
        hide-header-close @show="handleShowModal" hide-footer>
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title"><span class="icon one-notepad"></span>Danh sách các thuê bao của khách hàng đang lập Hợp đồng/Phụ lục</div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li v-if="isPopup">
                        <a style="cursor: pointer;" @click.prevent="closeForm">
                            <span class="icon nc-icon-glyph ui-1_circle-add"></span>Cập nhật
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
        dichvuvt_id:{
            type: Number,
            default: 11
        },
        loaihd_id:{
            type: Number,
            default: 21
        },
        ma_gd:{
            type: String,
            default: ''
        },
        ma_tb:{
            type: String,
            default: ''
        }
    },
    components: {
        KTable
    },
    data() {
        return {
            isPopup: false,
            columns: [
                {
                    field: "ma_tt",
                    label: "Mã TT",
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
                    label: "Loại hình TB",
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
                this.isPopup = true;
            } else {
                this.isPopup = false;
            }
        },
        showModal() {
            this.$refs['popupSearchSubscriber'].show()
        },
        hideModal() {
            this.$refs['popupSearchSubscriber'].hide()
        },
        handleShowModal(){
            //time out 500ms chờ binding view
            setTimeout(async ()=>{
                await this.khoiTaoDuLieu();
            },500)
        },
        hienThiDanhSachHDTBTheoKHID: async function() {
            let ds = [];
            let self = this;
            await api.getDB_HDTBTheoKHId_MaGD(this.axios, {
                "vkhachhang_id": this.khachhang_id,
                "vdichvuvt_id": this.dichvuvt_id,
                "vma_gd": this.ma_gd
            }).then(res => {
                ds = res.data.data ? res.data.data : [];
            }).catch(err => {
                self.$toast.error("Có lỗi: " + err.data.message);
            });
            return ds;
        },
        khoiTaoDuLieu: async function() {
            this.dataSources = [];
            try {
                this.loading(true);
                this.dataSources = await this.hienThiDanhSachHDTBTheoKHID();
                if (this.dataSources.length > 0) {
                    //this.khachhang_id = this.dataSources[0].khachhang_id;
                } else {
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
        },
        closeForm: async function() {
            if(!this.itemSelected){
                return
            } else {
                this.$emit('accept', this.itemSelected)
                this.hideModal();
            }
        }
    }
}
</script>
