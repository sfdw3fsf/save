<template src='./dapan.html'></template>
<script>
import VueElement from "../../components/VueElement.vue";
import api from "../../api/index.js";
import collums from "../../define/collums.js";
import DataGridCustom from "../../components/DataGrid";
import DataGrid2 from '@/components/Controls/DataGrid2'
import moment from 'moment'
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
    props: {
        idCauhoi: [String, Number], NdCauhoi: [String, String], NdDapan:[String, String] ,Ketqua:[String, Number]
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
            noidungcauhoi:"",
            txtThuTu: "",
            dapan_id: 0,
            Ins_Up: false,
            txtNoiDung: "",
            noidungdapan:"",
            checkbox_value: false,
            dataSelected: {
                chude_id: 0,
                mangthi_id: 0
            },
            tsbtnNhapMoi: false,
            tsbtnGhiLai: false,
            tsbtnXoa: false,
            json: {},
            options: {
                lstDapan: [],
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
            this.noidungcauhoi = this.NdCauhoi;
            this.checkbox_value = this.Ketqua;
            this.getDsDapanbyCauhoi(this.idCauhoi);
            this.SetButton(0);
            this.txtNoiDung = this.NdDapan;
        },
        onModalHidden() {
            this.options.lstMangthi = [];
        },
        Clear() {
            try {
                this.txtMangThi = null;
                this.options.lstMangthi = [];
                this.checkbox_value = 0;
                this.txtNoiDung = "";
                this.txtThuTu = "";

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
                this.tsbtnNhapMoi = true;
                this.tsbtnGhiLai = true;
                this.tsbtnXoa = true;
                this.Clear();
            }

            if (kieu == 1) //Them moi
            {
                this.Clear();
                this.tsbtnGhiLai = true;
                this.tsbtnXoa = true;
            }
        },
        tsbtnNhapMoi_Click() {
            this.Ins_Up = true;
            this.SetButton(1);
        },
        tsbtnGhiLai_Click() {
            this.CapNhat();
        },
        async tsbtnXoaDapan_Click() {
            this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa đáp án?', {
                title: '',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
            }).then(async v => {
                if (!v) {
                    return;
                }
                await api.Xoa_Dapan(this.axios, {
                    "vdapan_id": this.dapan_id
                }).then((res) => {
                    this.loading(false)
                    this.$root.$toast.success('Xoá đáp án thành công!')
                    this.getDsDapanbyCauhoi(this.idCauhoi);
                });
            });

        },
        async CapNhat() {
            try {
                if(this.Ins_Up==true){
                    await api.Them_Dapan(this.axios, {
                    "vketqua": this.checkbox_value?1:0,
                    "vnoidung": this.txtNoiDung,
                    "vcauhoi_id": this.idCauhoi,
                    "vstt": this.txtThuTu
                    }).then((res) => {
                        console.log(this.idCauhoi);
                        console.log(res)
                        this.$root.$toast.success("Thêm mới đáp án thành công!");
                        this.SetButton(-1);
                        this.Clear();
                        this.getDsDapanbyCauhoi(this.idCauhoi);
                    });
                }else{
                     await api.Sua_Dapan(this.axios, {
                    "vketqua": this.checkbox_value?1:0,
                    "vnoidung": this.txtNoiDung,
                    "vcauhoi_id": this.NdCauhoi,
                    "vstt": this.txtThuTu,
                    "vdapan_id": this.dapan_id
                    }).then((res) => {
                        
                        this.$root.$toast.success("Cập nhật đáp án thành công!");
                        this.SetButton(-1);
                        this.getDsDapanbyCauhoi(this.idCauhoi);
                    });
                }
            } catch (e) {
                this.$toast.error("Lỗi: " + e);
            }

        },
        //Get danh sách mảng thi
        async getDsDapanbyCauhoi(cauhoi_id) {
            this.loading(true);
            try {
                await api.getDsDapanbyCauhoi(this.axios, {
                    "vcauhoi_id": cauhoi_id
                }).then((res) => {
                    this.options.lstDapan = res.data.data ? res.data.data : [];
                    console.log(this.options.lstDapan)
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        //Click chọn mảng thi hoặc chủ đề
        async gridDapan_FocusedRowChanged(data) {
            try {
                if (data) {
                    let row = data;
                    this.kieu = row.kieu;
                    this.txtNoiDung =row.noidung;
                    this.txtThuTu = row.stt;
                    this.dapan_id = row.dapan_id;
                    this.checkbox_value = (row.ketqua==1?true:false);
                    this.Ins_Up = false;
                }
            } catch (err) {
                console.log(err);
            }
        },
        gridDapandataGridCellCreated:  function(args){
             let field = args.column.field
            if(field==="ketqua"){
                let id= args.data.dapan_id
                let ketqua = args.data.ketqua
                let check = ""
                if (ketqua ==1){
                    check = "checked"
                }else{
                    check = ""
                }
                args.cell.innerHTML = '<input type="checkbox" class="w20 center" id="'+id+'"  name="hienthi" '+check+' class="radio-donvi check">'       
            }
        },
    },
    watch: {}
};
</script>