<template src='./chude.html'></template>
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
            txtChude: "",
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
                lstTreeChude: [],
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
            this.options.lstChude = [];
        },
        Clear() {
            try {
                this.txtChude = null;
                this.options.lstChude = [];
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
        async tsbtnXoaChude_Click() {
            this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa chủ đề?', {
                title: '',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
            }).then(async v => {
                if (!v) {
                    return;
                }
                await api.Xoa_Chude(this.axios, {
                    "vchude_id": this.dataSelected.chude_id
                }).then((res) => {
                    this.loading(false)
                    this.$root.$toast.success('Xoá chủ đề thành công!')
                    this.getDsChude();
                });
            });

        },
        async CapNhat() {
            try {
                await api.Them_Chude(this.axios, {
                     "vchude": this.txtChude,
                     "vmangthi_id": this.dataSelected.mangthi_id
                }).then((res) => {
                    this.$root.$toast.success("Thêm mới chủ đề thành công!");
                    this.SetButton(-1);
                    this.getDsChude();
                });
            } catch (e) {
                this.$toast.error("Lỗi: " + e);
            }

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
                    if (this.options.lstMangthi) {
                        this.dataSelected.mangthi_id = this.options.lstMangthi[0]["mangthi_id"];
                    }
                    this.getDsChude();
                });
            } catch (err) {
                this.loading(false);
                console.log(err)
            }
            this.loading(false);
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
        //Get danh sách chủ đề
        async getDsChude() {

            this.loading(true);
            try {
                await api.getDanhmuc(this.axios, {
                    "vparam1": this.dataSelected.mangthi_id,
                    "vparam2": this.nhanvienId,
                    "vkieu": 2
                }).then((res) => {
                    this.options.lstChude = res.data.data.chude1 ? res.data.data.chude1 : [];
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        //Click chọn mảng thi hoặc chủ đề
        async gridChude_FocusedRowChanged(data) {
            try {
                if (data) {
                    let row = data;
                    this.kieu = row.kieu;
                    this.txtChude = row.chude;
                    this.dataSelected.mangthi_id = row.mangthi_id;
                    this.dataSelected.chude_id = row.chude_id;
                }
            } catch (err) {
                console.log(err);
            }
        },
        onChangeMangThi: async function () {
            this.getDsChude(this.dataSelected.mangthi_id);
        },
    },
    watch: {}
};
</script>