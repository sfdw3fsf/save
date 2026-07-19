<template src='./mangthi.html'></template>
<script>
import VueElement from "../../components/VueElement.vue";
import api from "../../api/index.js";
import collums from "../../define/collums.js";
import DataGridCustom from "../../components/DataGrid";
import moment from 'moment'
import DataGrid2 from '@/components/Controls/DataGrid2'
import {
    required,
    minLength
} from 'vuelidate/lib/validators'
export default {
    components: {
        VueElement,
        DataGridCustom,
        DataGrid2
    },
    data() {
        return {
            ...collums,
            dateconfig: {
                altFormat: 'd/m/Y H:i:S',
                altInput: true,
                dateFormat: 'd/m/Y H:i:S',
                allowInput: true,
            },
            dateFormat: "DD/MM/YYYY",
            nhanvienId: this.$root.token.getNhanVienID(),
            donviId: this.$root.token.getDonViID(),
            txtMangThi: "",
            txtGhiChu: "",
            dataSelected: {
                chude_id: 0,
                mangthi_id: 0
            },
            tsbtnNhapMoi: false,
            tsbtnGhiLai: false,
            tsbtnXoa: false,
            json: {},
            options: {
                lstMangthi: [],
                lstChude: [],
                lstTreeChude: []
            },
        };
    },
    mounted() {
        this.init();
    },
    computed: {
    },
    validations: {
    },
    methods: {
        async init() {
           
        },
        onModalShown() {
            this.getDsMangthi();
            this.SetButton(0);
        },
        onModalHidden() {
            this.getTreeChude();
            this.options.lstMangthi = [];
        },
        Clear() {
            try {
                this.txtMangThi = null;
                this.options.lstMangthi = [];
            } catch (e) {
                this.$toast.error("Có lỗi trong quá trình làm mới dữ liệu." + e);
            }
        },
        SetButton(kieu) {
            this.tsbtnNhapMoi = false;
            this.tsbtnGhiLai = false;
            this.tsbtnXoa = false;

            if (kieu == -1) //Bat dau
            {
                this.tsbtnGhiLai = true;
                this.tsbtnXoa = true;
                this.tsbtnNhapMoi = false;
            }

            if (kieu == 0) //Bat dau
            {
                this.tsbtnNhapMoi = false;
                this.tsbtnGhiLai = false;
                this.tsbtnXoa = false;
                this.Clear();
            }

            if (kieu == 1) //Them moi
            {
                this.Clear();
                this.tsbtnNhapMoi = true;
            }
        },
        tsbtnNhapMoi_Click() {
            this.SetButton(1);
        },
        tsbtnGhiLai_Click() {
            this.CapNhat();
        },
        async tsbtnXoaMangThi_Click() {
            console.log(this.dataSelected.mangthi_id);
            this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa mảng thi?', {
                title: '',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
            }).then(async v => {
                await api.Xoa_MangThi(this.axios, {
                     "vmangthi_id": this.dataSelected.mangthi_id
                }).then((res) => {
                    this.loading(false)
                    this.$root.$toast.success('Xoá mảng thi thành công!')
                    this.getDsMangthi();
                });
            });

        },
        async CapNhat() {
            try {
                await api.Them_mangthi(this.axios, {
                    "vmangthi": this.txtMangThi,
                }).then((res) => {
                        this.$root.$toast.success("Thêm mảng thi thành công!");
                        this.SetButton(-1);
                        this.getDsMangthi();
                });
            } catch (e) {
                console.log(e);
                this.$toast.error("Lỗi: " + e);
            }

        },
         async getTreeChude() {
            this.loading(true);
            try {
                await api.getDanhmuc(this.axios, {
                    "vparam1": 0,
                    "vparam2": this.nhanvienId,
                    "vkieu": 3
                }).then((res) => {
                    this.options.lstTreeChude = res.data.data.chude2 ? res.data.data.chude2 : [];
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        //Get danh sách mảng thi
        async getDsMangthi() {
            this.loading(true);
            try {
                await api.getDanhmuc(this.axios, {
                    "vparam1": this.donviId,
                    "vparam2": this.nhanvienId,
                    "vkieu": 1
                }).then((res) => {
                    this.options.lstMangthi = res.data.data.mangthi ? res.data.data.mangthi : [];
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        //Click chọn mảng thi hoặc chủ đề
        async gridMangThi_FocusedRowChanged(data) {
            try {
                if (data) {
                    let row = data;
                    this.kieu = row.kieu;
                    this.txtMangThi = row.mangthi;
                    this.dataSelected.mangthi_id = row.mangthi_id;
                }
            } catch (err) {
                console.log(err);
            }
        },
    },
    watch: {}
};
</script>