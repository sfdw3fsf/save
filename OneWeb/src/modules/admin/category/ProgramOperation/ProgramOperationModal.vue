
<template src='./ProgramOperation.html'></template>

<style scoped src='./ProgramOperation.scss'></style>

<script>
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import collums from "./define/collums.js";
import DataGridCustom from "./components/DataGrid";
import DataGrid2 from '@/components/Controls/DataGrid2'

import moment from 'moment'
import {
    required,
    minLength
} from 'vuelidate/lib/validators'
export default {
    name:'ProgramOperationModal',
    components: {
        VueElement,
        DataGridCustom,
        DataGrid2
    },
    data() {
        return {
            dateconfig: {
                altFormat: 'd/m/Y H:i:S',
                altInput: true,
                dateFormat: 'd/m/Y H:i:S',
                allowInput: true,
            },
            dateFormat: "DD/MM/YYYY",
            ...collums,
            header: {
                title: "câu hỏi trắc nghiệm Atvslđ",
                list: [{
                        name: "Lập hợp đồng",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Lắp đặt mới",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            txtGhiChu: "",
            txtThaoTac: "",
            txtCode: "",
            thaotac_id: 0,
            txtControlName:"",
            warning: false,
            dsTHAOTAC:{},
            dsTHAOTACCONTROL:{},
            dsTHAOTACs:[],
            dsTHAOTACCONTROLs:[],
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            Fn: {
                NhapMoi: false,
                GhiLai: false,
                HuyBo: false,
                Xoa: false,
            },
            nhanvienId: this.$root.token.getNhanVienID(),
            donviId: this.$root.token.getDonViID(),
            dataSelected: {
              
            },
            options: {
               DanhsachAll: [],
               ds_control_chuagan:[],
               ds_control_dagan:[],
               DsGan:[],
               DsChuaGan:[] ,
               dsthaotac_update:[]
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
            try {
               this.HienThiDanhSach();
               this.SetButton(0);
            } catch (e) {
                console.log(e);
            }
        },
        openDialog () {
            this.$refs.capnhatthaotac.show();
        },
         showModal() {
            this.$refs["capnhatthaotac"].show()
        },
        hideModal() {
            this.$refs["capnhatthaotac"].hide()
        },
        close() {
            try {
                this.dsthaotac_update = [];
                this.dsTHAOTAC = {};
                this.dsTHAOTACCONTROL = {};
                this.dsTHAOTACCONTROLs = [];
                this.dsTHAOTACs = [];
                this.$refs.capnhatthaotac.hide();
            } catch (e) {
                console.log(e);
            }
        },
        handleShowModal(){
            try {
               this.HienThiDanhSach();
               this.dsthaotac_update = [];
               this.options.DsChuaGan = [];
               this.options.DsGan = [];
               this.dsTHAOTAC = {};
                this.dsTHAOTACCONTROL = {};
                this.dsTHAOTACCONTROLs = [];
                this.dsTHAOTACs = [];
               this.SetButton(0);
            } catch (e) {
                console.log(e);
            }
        },
        async HienThiDanhSach() {
            this.loading(true);
            try {
                await api.getDanhsach(this.axios, {
                    "thaotac_id":0
                }).then((res) => {
                    this.options.DanhsachAll = res.data.data ? res.data.data : [];
                    this.txtGhiChu = this.options.DanhsachAll[0].ghichu;
                    this.txtThaoTac = this.options.DanhsachAll[0].thaotac;
                    this.txtCode = this.options.DanhsachAll[0].code;
                    this.thaotac_id = this.options.DanhsachAll[0].thaotac_id;
                    this.HienThi_Control(this.thaotac_id);
                    
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        }, 
        async getKeys (data) {
            let response = await api.getKeys(this.axios, data)
            try {
                return response.data.data
            } catch (error) {
                throw error
            }
        },
        TaoDuLieu(themmoi)
        {       
                    this.dsTHAOTAC.THAOTAC_ID = parseInt(this.thaotac_id);
                    this.dsTHAOTAC.THAOTAC = this.txtThaoTac;
                    this.dsTHAOTAC.CODE = this.txtCode.toUpperCase();
                    this.dsTHAOTAC.GHICHU = this.txtGhiChu;
                    this.dsTHAOTACs.push(this.dsTHAOTAC);
                
                this.options.ds_control_dagan.forEach(item =>{
                this.dsTHAOTACCONTROL.PHANVUNG_ID = parseInt(this.$root.token.getPhanVungID());
                this.dsTHAOTACCONTROL.THAOTAC_ID = parseInt(this.thaotac_id);
                this.dsTHAOTACCONTROL.CONTROL_NAME = item.control_name;
                this.dsTHAOTACCONTROLs.push(this.dsTHAOTACCONTROL);
                this.dsTHAOTACCONTROL = {};
                });
                if (this.txtControlName != '')
                {
                    this.dsTHAOTACCONTROL.PHANVUNG_ID = parseInt(this.$root.token.getPhanVungID());
                    this.dsTHAOTACCONTROL.THAOTAC_ID = parseInt(this.thaotac_id);
                    this.dsTHAOTACCONTROL.CONTROL_NAME = this.txtControlName.trim();
                    this.dsTHAOTACCONTROLs.push(this.dsTHAOTACCONTROL);
                }
        },
         //Khi click dòng trong grid danh sách bi3n lai mới
        async gridDS_FocusedRowChanged(data) {
               this.dsthaotac_update = [];
               this.options.DsChuaGan = [];
               this.options.DsGan = [];
               this.dsTHAOTAC = {};
               this.dsTHAOTACCONTROL = {};
               this.dsTHAOTACCONTROLs = [];
               this.dsTHAOTACs = [];
            try
            {
                console.log(this.thaotac_id);
                if (data.rowIndex < 0)
                {
                    this.SetButton(1);
                    return;
                }
                if (data) {
                    let update = {}
                    let row = data.data;
                    this.thaotac_id = row.thaotac_id;
                    this.txtThaoTac = row.thaotac;
                    this.txtCode = row.code;
                    this.txtGhiChu =  row.ghichu;
                    this.HienThi_Control(this.thaotac_id);
                    this.SetButton(3);
                    
                }
            }
            catch (err)
            {
                console.log(err);
            }
        },
        async HienThi_Control_Dagan(thaotac_id) {
            this.loading(true);
            try {
                await api.danhsach_control(this.axios, {
                    "p_thaotac_id":thaotac_id,
                    "p_kieu":1//Đã gán
                }).then((res) => {
                    this.options.ds_control_dagan = res.data.data ? res.data.data : [];
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        }, 
        async HienThi_Control_Chuagan(thaotac_id) {
            this.loading(true);
            try {
                console.log({
                    "p_thaotac_id":thaotac_id,
                    "p_kieu":0 //Chưa gán
                })
                await api.danhsach_control(this.axios, {
                    "p_thaotac_id":thaotac_id,
                    "p_kieu":0 //Chưa gán
                }).then((res) => {
                    this.options.ds_control_chuagan = res.data.data ? res.data.data : []; 
                    for(let i = 0;i <= this.options.ds_control_dagan.length;++i){
                        this.options.ds_control_chuagan.splice(this.options.ds_control_chuagan.findIndex(v => v.control_name === this.options.ds_control_dagan[i].control_name), 1); 
                    }
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        }, 
        async HienThi_Control(thaotac_id)
        {
            this.HienThi_Control_Dagan(thaotac_id);
            this.HienThi_Control_Chuagan(thaotac_id);
            
        },
        async gridDSDagan_FocusedRowChanged(data) {
            try
            {
                if (data) {
                     if(data.isHeaderCheckboxClicked){
                        this.options.DsGan = data.data
                    }else{
                        let row = data.data;
                        this.options.DsGan.push(row);
                        let ids = [...new Set(this.options.DsGan.map(x => x.control_name))]; 
                        let ds_control_dagan = ids.map(name => this.options.DsGan.find(x => x.control_name === name));
                        this.options.DsGan = ds_control_dagan;
                    }
                  
                }
            }
            catch (err)
            {
                console.log(err);
            }
        },
        async gridDSChuagan_FocusedRowChanged(data) {
            try
            {               
                if (data) {
                    if(data.isHeaderCheckboxClicked){
                        this.options.DsChuaGan = data.data
                    }else{
                        let row = data.data;
                        this.options.DsChuaGan.push(row);
                        let ids = [...new Set(this.options.DsChuaGan.map(x => x.control_name))]; 
                        let ds_control_chuagan = ids.map(name => this.options.DsChuaGan.find(x => x.control_name === name));
                        this.options.DsChuaGan = ds_control_chuagan;
                    }
                }
            }
            catch (err)
            {
                console.log(err);
            }
        },
        async gridDSChuagan_RowDeselected(data) {
            try
            {
                console.log(data)
                if (data) {
                    let row = data.data;
                    this.options.DsChuaGan.splice(this.options.DsChuaGan.indexOf(data.data), 1);
                }
            }
            catch (err)
            {
                console.log(err);
            }
        },
         async gridDSDagan_RowDeselected(data) {
            
            try
            {
                if (data) {
                    let row = data.data;
                    this.options.DsGan.splice(this.options.DsGan.indexOf(data.data), 1);
                }
            }
            catch (err)
            {
                console.log(err);
            }
        },
        btnNext_Click()
        {
            
            try
            {
                if (this.options.DsGan.length > 0) {
                    for (let i = 0; i < this.options.DsGan.length; i++) {
                        this.DeleteRow(this.options.DsGan[i].control_name,this.options.ds_control_dagan);
                        this.AddRow(this.options.DsGan[i],this.options.ds_control_chuagan);
                    }
                }else{
                    this.$toast.error("Bạn chưa chọn Control để bỏ gán!");
                }
                this.options.DsGan= [];
            }
            catch (err)
            {
                this.$toast.error("Lỗi không thể thực hiện: " + err.message);
            }
        },
        btnPrevious_Click()
        {
            try
            {
               
                if (this.options.DsChuaGan.length > 0) {
                    for (let i = 0; i < this.options.DsChuaGan.length; i++) {
                       
                        this.DeleteRow(this.options.DsChuaGan[i].control_name,this.options.ds_control_chuagan);
                        this.AddRow(this.options.DsChuaGan[i],this.options.ds_control_dagan);
                    }
                }else{
                    this.$toast.error("Bạn chưa chọn Control để gán!");
                }
                this.options.DsChuaGan= [];
            }
            catch (err)
            {
                this.$toast.error("Lỗi không thể thực hiện: " + err.message);
            }
        },
        DeleteRow(keyDelete,data) {
            console.log(keyDelete)
             console.log(data)
                for (let i = 0; i < data.length; i++) {
                    if (data[i].control_name.toUpperCase() == keyDelete.toUpperCase()) {
                        data.splice(i, 1);
                    }
                }
        },
        AddRow(data,data_dich) {
                data_dich.push({
                thaotac_id: data.thaotac_id,
                control_name: data.control_name});
        },
        tsbtnNhapMoi_Click()
        {
            this.options.ds_control_dagan= [];
               this.options.DsGan = [];
               this.dsTHAOTAC = {};
               this.dsTHAOTACCONTROL = {};
               this.dsTHAOTACCONTROLs = [];
               this.dsTHAOTACs = [];
            this.thaotac_id = 0;
            this.SetButton(1);
        },
        tsbtnGhiLai_Click()
        {
            this.CapNhat();
        },
        async Insert(){
            try {
                console.log({
                    "thaotac_id": this.thaotac_id,
                    "kieu": 1,
                    "json_thaotac": JSON.stringify(this.dsTHAOTACs),
                    "json_thaotac_ct": JSON.stringify(this.dsTHAOTACCONTROLs)
                })
                await api.capnhat_thaotac(this.axios, {
                    "thaotac_id": this.thaotac_id,
                    "kieu": 1,
                    "json_thaotac": JSON.stringify(this.dsTHAOTACs),
                    "json_thaotac_ct": JSON.stringify(this.dsTHAOTACCONTROLs)
                }).then((res) => {
                    this.loading(false)
                    if(res.error_code ="BSS-00000000")
                    {
                        this.$root.$toast.success("Thêm mới thao tác thành công !");
                        
                        this.HienThiDanhSach();
                        this.SetButton(3);
                    }
                    else
                    {
                        this.$toast.error("Lỗi không thể thực hiện: " + res.message);
                    } 
                });
            } catch (err) {
                this.$toast.error("Lỗi không thể thực hiện: " + err.message);
            }
        },
         async Update(){
            try {
                console.log({
                    "thaotac_id": this.thaotac_id,
                    "kieu": 2,
                    "json_thaotac": JSON.stringify(this.dsTHAOTACs),
                    "json_thaotac_ct": JSON.stringify(this.dsTHAOTACCONTROLs)
                })
                await api.capnhat_thaotac(this.axios, {
                    "thaotac_id": this.thaotac_id,
                    "kieu": 2,
                    "json_thaotac": JSON.stringify(this.dsTHAOTACs),
                    "json_thaotac_ct": JSON.stringify(this.dsTHAOTACCONTROLs)
                }).then((res) => {
                   
                    this.loading(false)
                    if(res.error_code ="BSS-00000000")
                    {
                        this.$root.$toast.success("Cập nhật thao tác thành công !");
                        
                        this.HienThiDanhSach();
                        this.SetButton(3);
                    }
                    else
                    {
                        this.$toast.error("Lỗi không thể thực hiện: " + res.message);
                    } 
                });
               
            } catch (err) {
                this.$toast.error("Lỗi không thể thực hiện: " + err.message);
            }
        },
        async CapNhat()
        {
            this.KiemTra_DuLieu();
            try
            {
              
                     if (this.Fn.NhapMoi == false)
                    {
                        this.thaotac_id = await this.getKeys({
                            "keyname": "THAOTAC_ID",
                            "numblocksize": 1,
                            "numretry": 10
                        });
                        this.TaoDuLieu(true);
                        this.Insert();
                    }
                    else
                    {
                        this.TaoDuLieu(false);
                        this.Update();
                    }
                    
            }
            catch (err)
            {
                this.$toast.error("Lỗi không thể thực hiện: " + err.message);
            }
        },
        tsbtnHuyBo_Click()
        {
            this.SetButton(2);
            this.HienThiDanhSach();

        },
        tsbtnXoa_Click()
        {
            try
            {
                this.$bvModal.msgBoxConfirm('Bạn muốn xóa thao tác này không ?', {
                title: '',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
                }).then(async v => {
                    await api.xoa_thaotac(this.axios, {
                    "thaotac_id": this.thaotac_id
                    }).then((res) => {
                        this.$root.$toast.success("Xóa thành công!");
                        this.HienThiDanhSach();
                    });
                });
            }
            catch (err)
            {
                this.$toast.error("Lỗi không thể thực hiện: " + err.message);
            }
        },
        KiemTra_DuLieu()
        {
            if (this.txtThaoTac == "")
            {
                this.$toast.error("Tên thao tác không được để trống !");
                this.txtThaoTac.focus();
                this.warning = true;
            }
            if (this.txtCode == "")
            {
                this.$toast.error("Mã thao tác không được để trống !");
                this.txtCode.focus();
                this.warning = true;
            }
            if (this.txtControlName != "")
            {
                this.options.ds_control_dagan.forEach((item)=>{
                    if (item.control_name.toString().toUpperCase() == this.txtControlName.trim().toUpperCase())
                    {
                        this.$toast.error("Control này đã có trong dữ liệu !");
                        this.warning = true;
                    }
                });
                this.options.ds_control_chuagan.forEach((item)=>{
                    if (item.control_name.toString().toUpperCase() == this.txtControlName.trim().toUpperCase())
                    {
                        this.$toast.error("Control này đã có trong dữ liệu !");
                        this.warning = true;
                    }
                })      
            }
        },
        Clear()
        {
            this.txtGhiChu = "";
            this.txtThaoTac = "";
            this.txtCode = "";
            this.thaotac_id = 0;
            this.ds_control_chuagan = [];
            this.ds_control_dagan = []
        },
        SetButton(kieu)
        {
            this.Fn.NhapMoi = false;
            this.Fn.GhiLai = false;
            this.Fn.Xoa = false;
            this.Fn.HuyBo = false;
            this.txtControlName = "";
            if (kieu == -1)//Bat dau
            {
                this.Fn.GhiLai = true;
                this.Fn.HuyBo = true;
            }
            if (kieu == 0)//Bat dau
            {
                this.Fn.NhapMoi = true;
                this.Clear();
            }
            if (kieu == 1)//Them moi
            {
                this.Fn.GhiLai = true;
                this.Fn.HuyBo = true;
                this.Clear();
            }
            if (kieu == 2)//Huy
            {
                this.Fn.NhapMoi = true;
                this.Fn.Xoa = true;
                this.Clear();
            }
            if (kieu == 3)//Edit
            {
                this.Fn.NhapMoi = true;
                this.Fn.Xoa = true;
                this.Fn.GhiLai = true;
                this.Fn.HuyBo = true;
            }
        }
        
    },
    watch: {}
};
</script>
