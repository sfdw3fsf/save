
<template src='./MultipleChoiceQuestionsATVSLD.html'></template>

<style scoped src='./MultipleChoiceQuestionsATVSLD.scss'></style>

<script>
import VueElement from "./components/VueElement.vue";
import api from "./api/index.js";
import SERVER_IMAGES from "./enums/SERVER_IMAGES.js";
import collums from "./define/collums.js";
import DataGridCustom from "./components/DataGrid";
import moment from 'moment'
import chude from "./popups/chude/chude.vue";
import mangthi from "./popups/mangthi/mangthi.vue";
import dapan from "./popups/dapan/dapan.vue";
import treemenu from '@/components/Shared/treemenu'
import DataGrid2 from '@/components/Controls/DataGrid2'
import DataGrid3 from '@/components/Controls/DataGrid3'
import {
    required,
    minLength
} from 'vuelidate/lib/validators'
export default {
    components: {
        VueElement,
        chude,
        mangthi,
        dapan,
        DataGrid2,
        DataGridCustom,
        treemenu,
        DataGrid3
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
            nhanvienId: this.$root.token.getNhanVienID(),
            donviId: this.$root.token.getDonViID(),
            txtNoidung: "",
            dataSelected: {
                chude_id: 0,
                mangthi_id: 0
            },
            NdDapan: '',
            rowSelection: '',
            image: null,
            hienthi: false,
            treeFields:"",
            autoGroupColumnDef: null,
            chkHienThiCH: false,
            themmoi: false,
            previewImage:null,
            tsbtnNhapMoi: false,
            tsbtnGhiLai: false,
            tsbtnChuDe: false,
            cauhoi_check_id:0,
            tsbtnHuyBo: false,
            mt_cd_parent:0,
            tsbtnMangthi: false,
            fieldsChude:{},
            node: null,
            listImg:[],
            id_check:[],
            id_check_cauhoi:[],
            tsbtnXoa: false,
            daChupAnh : false,
            update_insert:0,
            urlImg: "",
            vcauhoi_id: 0,
            mota: "",
            cauhoi_id: 0,
            vhienthi: 0,
            kieu: 0,
            id_chuan: 0,
            mt_cd:true,
            mt_cd_id:0,
            Ketqua:0,
            json: {},
            listbox: {
                ds_mangthi:{
                    list:[],
                    value:"",
                    isEnabled: false,
                    checked: []
                },
                fields: {
                    dataSource: null,
                    id: 'id_chuan',
                    parentID: 'parentid',
                    text: 'name',
                    hasChildren: 'hasChild'
                },
                
            },
            btnimage:{
                cut:false,
                copy:false,
                palse:false,
                del:false,
                load:false,
                save:false,
            },
            tImg : [],
            ImageUrlList: "",
            selectOptions: {
                type: "Single",
                persistSelection: true,
                checkboxMode: "ResetOnRowClick",
            },
            options: {
                lstMangthi: [],
                lstTreeChude: [],
                lstChude: [],
                lstCauhoi: [],
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
        MaTB: {
            required,
            minLength: minLength(1)
        },
        DK_tungay: {
            required,
            minLength: minLength(10)
        },
    },
    created () {
    },
    methods: {
        async init() {
            try {
                await this.getTreeChude();
                this.loadTreeMangthiChude();
                this.getDsMangthi();
                this.SetButton(0);
            } catch (e) {
                console.log(e);
            }
        },
        Clear() {
            try {
                this.txtNoidung = null;
                this.chkHienThiCH = false;
                this.options.lstDapan = [];
            } catch (e) {
                this.$toast.error("Có lỗi trong quá trình làm mới dữ liệu." + e);
            }
        },
        SetButton(kieu) {
            this.tsbtnNhapMoi = false;
            this.tsbtnGhiLai = false;
            this.tsbtnChuDe = false;
            this.tsbtnHuyBo = false;
            this.tsbtnXoa = false;
            this.tsbtnMangthi = false;
            if (kieu == -1) //Bat dau
            {
                this.tsbtnGhiLai = true;
                this.tsbtnHuyBo = true;
            }

            if (kieu == 0) //Bat dau
            {
                this.tsbtnNhapMoi = true;
                this.tsbtnXoa = true;
                this.tsbtnMangthi = true;
            }

            if (kieu == 1) //Them moi
            {
                this.tsbtnChuDe = false;
                this.tsbtnGhiLai = true;
                this.tsbtnHuyBo = true;
                this.tsbtnXoa = true;
                this.tsbtnNhapMoi = false;
                this.tsbtnMangthi = true;
                this.Clear();
            }

            if (kieu == 2) //Huy
            {
                this.tsbtnNhapMoi = true;
                this.tsbtnXoa = true;
                this.tsbtnMangthi = true;
                this.Clear();
                this.themmoi = false;
            }

            if (kieu == 3) //Edit
            {
                this.tsbtnChuDe = true;
                this.tsbtnGhiLai = true;
                this.tsbtnHuyBo = true;
                this.tsbtnXoa = true;
                this.tsbtnNhapMoi = true;
            }
        },
        tsbtnNhapMoi_Click() {
            this.update_insert = 0;
            this.SetButton(1);
        },
        tsbtnGhiLai_Click() {
            this.CapNhat();
        },
        tsbtnHuyBo_Click() {
            this.SetButton(2);
            if (this.mt_cd) {
                this.getDsCauhoibyMangthi(this.mt_cd_id);
            } else {
                this.getDsCauhoibyChude(this.mt_cd_id);
            }

        },
        async onSelect (data) {
            console.log("2");
            
            if (this.isLoading) return;
            let treeObj = document.getElementById("treeview").ej2_instances[0];
            let donViId = treeObj.selectedNodes[0];
       
        },
        async tsbtnXoaCauhoi_Click() {
            this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa?', {
                title: '',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
            }).then(async v => {
                if (!v) {
                    return;
                }
                await api.Xoa_Cauhoi(this.axios, {
                    "vcauhoi_id": this.cauhoi_id
                }).then((res) => {
                    this.loading(false)
                    this.$root.$toast.success('Xoá câu hỏi thành công!')
                    this.getCauhoi(this.mt_cd,this.mt_cd_id);
                    this.hienthi = false;
                });
            });

        },
         griddataGridCellCreated:  function(args){
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
         gridCauhoidataGridCellCreated:  function(args){
             let field = args.column.field
            if(field==="hienthi"){
                let id= args.data.cauhoi_id
                let hienthi = args.data.hienthi
                let check = ""
                if (hienthi ==1){
                    check = "checked"
                }else{
                    check = ""
                }
                args.cell.innerHTML = '<input type="checkbox" class="w20 center" id="'+id+'"  name="hienthi" '+check+' class="radio-donvi check">'       
            }
        },
        async gridDsDACommand(args,data) {
            try {
              if(args == "Xóa"){
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
                            "vdapan_id": data.dapan_id
                        }).then((res) => {
                            this.loading(false)
                            this.$root.$toast.success('Xoá đáp án thành công!')
                            this.getDsDapanbyCauhoi(data.cauhoi_id);
                            
                        });
                    });
              }else {
                  this.cauhoi_id = data.cauhoi_id;
                  this.NdDapan  = data.noidung;
                  this.Ketqua  = parseInt(data.ketqua);
                  this.$bvModal.show('popup-dapan')
              }
            } catch (e) {
                this.$root.toastError("Không thành công");
            }
        },
        async CapNhat() {
            try {
                if(this.txtNoidung == null ||this.txtNoidung ==''){
                    this.$toast.warning("Bạn vui lòng nhập nội dung câu hỏi");
                }else {
                    if (!this.tsbtnNhapMoi)
                        this.vcauhoi_id = 0;
                    else
                    this.vcauhoi_id = this.cauhoi_id;
                    this.vhienthi = this.chkHienThiCH ? 1 : 0;
                    await api.Capnhat_Cauhoi(this.axios, {
                        "vchude_id": this.dataSelected.chude_id,
                        "vnoidung": this.txtNoidung,
                        "vsl_dung": 1,
                        "vhienthi": this.vhienthi,
                        "vmota": "api test",
                        "vcauhoi_id": this.vcauhoi_id,
                        "vloai_ch": 1
                    }).then((res) => {
                        if (res.data.data[0].loai == 1) {
                            this.vcauhoi_id = res.data.data[0].giatri;
                            this.$root.$toast.success("Cập nhật thành công!");

                            this.SetButton(-1);
                        }

                        this.getCauhoi(this.mt_cd,this.mt_cd_id);
                    });
                }
            } catch (e) {
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
                    this.listbox.ds_mangthi.list = res.data.data.chude2 ? res.data.data.chude2 : [];
                    
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        async nodeclick (node) {
            if (node.nodeData.hasChildren) {
                this.getDsCauhoibyMangthi(node.nodeData.id);
                
            } else {
                this.getDsCauhoibyChude(node.nodeData.id);
            }
            this.node = node;
            this.mt_cd_id = node.nodeData.id;
            this.mt_cd_parent = node.nodeData.parentID;
            this.mt_cd = node.nodeData.hasChildren;
            if(this.mt_cd) {
                this.tsbtnMangthi = true;
                this.tsbtnChuDe = false;
            }else {
                this.tsbtnChuDe = true;
                this.tsbtnMangthi = false;
            }
        },
        createTreeDonVi (dsMangthichude) {
            let tree = []
            let chudeChaArray = dsMangthichude.map(item => (item['parentid']))
            let chudeArray = dsMangthichude.map(item => (item['id_chuan']));
            dsMangthichude.forEach(item => {
                if(item['kieu'] == 1){
                        if(!chudeArray.includes(item['parentid'])){
                        delete item.parentid
                        }
                        tree.push( {...item, hasChild:true, expanded: true,})
                }else{
                        this.$set(item, 'parentid', item['parentid'].replace('MT_',''))
                        tree.push(item)
                }
            })
            return tree
        },
        async loadTreeMangthiChude () {
             try {
                    this.loading(true)
                    this.filterText = null
                    await this.getTreeChude()
                    let chudeTree = this.createTreeDonVi( this.listbox.ds_mangthi.list);
                    this.fieldsChude = { dataSource: chudeTree, id: 'id_chuan', text: 'name', parentID: 'parentid', hasChildren: 'hasChild',expanded:"expanded" }
                    
                    if (this.fieldsChude.dataSource[0].kieu == 1) {
                        this.getDsCauhoibyMangthi(this.fieldsChude.dataSource[0].id_chuan);
                    } else {
                        this.getDsCauhoibyChude(this.fieldsChude.dataSource[0].id_chuan);
                    }
                    this.mt_cd_id = this.fieldsChude.dataSource[0].id_chuan;
                    this.mt_cd = this.fieldsChude.dataSource[0].kieu == 1?true:false;
            } catch (err) {
                    console.log(err)
            } finally {
                    this.loading(false)
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
                        this.getDsChude(this.dataSelected.mangthi_id);
                    }
                });
            } catch (err) {
                this.loading(false);
                console.log(err);
            }
            this.loading(false);
        },
        //Get danh sách chủ đề
        async getDsChude(mangthi_id) {

            this.loading(true);
            try {
                await api.getDanhmuc(this.axios, {
                    "vparam1": mangthi_id,
                    "vparam2": this.nhanvienId,
                    "vkieu": 2
                }).then((res) => {
                    this.options.lstChude = res.data.data.chude1 ? res.data.data.chude1 : [];
                    if (this.options.lstChude) {
                        this.dataSelected.chude_id = this.options.lstChude[0]["chude_id"];
                        
                    }
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        //Get danh sách câu hỏi theo mảng thi
        async getDsCauhoibyMangthi(id_chuan) {
            this.Clear();
            this.loading(true);
            try {
                await api.getDsCauhoibyMangthi(this.axios, {
                    "vmangthi_id": id_chuan
                }).then((res) => {
                    this.options.lstCauhoi = res.data.data ? res.data.data : [];
                    this.getDsDapanbyCauhoi(this.options.lstCauhoi[0].cauhoi_id);
                });
               
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        //Get danh sách câu hỏi theo chủ đề
        async getDsCauhoibyChude(id_chuan) {
            this.Clear();
            this.loading(true);
            try {
                await api.getDsCauhoibyChude(this.axios, {
                    "vchude_id": id_chuan
                }).then((res) => {
                    this.options.lstCauhoi = res.data.data ? res.data.data : [];
                    this.getDsDapanbyCauhoi(this.options.lstCauhoi[0].cauhoi_id)
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        //Get đáp án theo câu hỏi
        async getDsDapanbyCauhoi(cauhoi_id) {
                 
            this.loading(true);
            try {
                await api.getDsDapanbyCauhoi(this.axios, {
                    "vcauhoi_id": cauhoi_id
                }).then((res) => {
                    this.options.lstDapan = res.data.data ? res.data.data : [];
                });
                 this.$refs.Dapan.setCurrentSelectedRow(0)
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        //Button tìm kiếm
        async getCauhoi(kieu,mt_cd_id) {
            if (kieu) {
                this.getDsCauhoibyMangthi(mt_cd_id);
            } else {
                this.getDsCauhoibyChude(mt_cd_id);
            }
        },
        //Click chọn câu hỏi trong grid
        async gridCauhoi_FocusedRowChanged(data) {
            try {
                if (data) {
                    let row = data;
                    this.getDsDapanbyCauhoi(row.cauhoi_id);
                    this.cauhoi_id = row.cauhoi_id;
                    this.dataSelected.mangthi_id = row.mangthi_id;
                    await this.getDsChude(row.mangthi_id)
                    this.dataSelected.chude_id = row.chude_id;
                    this.txtNoidung = row.noidung;
                    this.chkHienThiCH = row.hienthi;
                    this.vcauhoi_id = 1; //0 Insert > 0 update
                    this.mota = row.mota;
                    
                }else{
                    this.options.lstChude = [];
                    if (this.mt_cd){
                        this.dataSelected.mangthi_id = this.mt_cd_id;
                        await this.getDsChude(this.mt_cd_id);
                    }else{
                        this.dataSelected.mangthi_id = this.mt_cd_parent;
                        await this.getDsChude(this.mt_cd_parent);
                         this.dataSelected.chude_id = this.mt_cd_id;
                    }
                    
                }
            } catch (err) {
                console.log(err);
            }
        },
         async gridDA_FocusedRowChanged(data) {
            try {
                if (data) {
                    let row = data;
                }
            } catch (err) {
                console.log(err);
            }
        },
        onChangeMangThi: async function () {
            this.getDsChude(this.dataSelected.mangthi_id);
        },
        onChangeChude: async function () {
            //this.getDsChude(this.dataSelected.mangthi_id);
        },
        clickright(){
            this.hienthi = true;
            this.btnimage.cut = false;
            this.btnimage.copy=false;
            this.btnimage.palse=false;
            this.btnimage.del=false;
            this.btnimage.load =true;
            this.btnimage.save=false;
        },
        getAnhChup(xmlDoc){
            const parser = new DOMParser();
            const dom = parser.parseFromString(xmlDoc, "application/xml");
            var imgTag = dom.getElementsByTagName('img');
            for (var i = 0; i < imgTag.length; i++) { 
                this.listImg.push({
                url: SERVER_IMAGES.BASE_URL + this.$root.token.getMaTinh() + "/THITRACNGHIEM/"  + imgTag[i].getAttribute('url'),
                title:imgTag[i].getAttribute('title'),
                })          
            }
            
            //Kiểm tra IMG có tồn tại
            for(const i in this.listImg){              
                var checkImage = this.checkImage(this.listImg[i].url);
                if (!checkImage){
                this.daChupAnh = false;
                this.listImg[i].url = SERVER_IMAGES.NO_IMAGE_URL;
                } else{
                this.daChupAnh = true
                }        
            }
            
            if(this.listImg.length > 0){
                 this.previewImage = this.listImg[0].url;
            }else{
                 this.previewImage = SERVER_IMAGES.NO_IMAGE_URL;
            }
        },
        checkImage(url) {
            var request = new XMLHttpRequest();
            request.open("GET", url, true);
            request.send();
            if (request.status == 200) //if(statusText == OK)
            {
                return true
            } else {
                return false
            }    
        },
        Load(){
            this.getAnhChup(this.mota);
            this.btnimage.cut = true;
            this.btnimage.copy=true;
            this.btnimage.palse=false;
            this.btnimage.del=true;
            this.btnimage.load =true;
            this.btnimage.save=true;
        },
        Delete(){
            this.previewImage = "";
            this.hienthi = false;
        },
        downloadImg() {
        this.axios({
                url: ""+this.previewImage+"",
                method: 'GET',
                responseType: 'blob',
            }).then((response) => {
                var fileURL = window.URL.createObjectURL(new Blob([response.data]));
                var fileLink = document.createElement('a');

                fileLink.href = fileURL;
                fileLink.setAttribute('download', 'Img.jpg');
                document.body.appendChild(fileLink);

                fileLink.click();
            });
        },
        uploadImage(e){
                if (this.previewImage = SERVER_IMAGES.NO_IMAGE_URL)
                {
                    const image = e.target.files[0];
                    const reader = new FileReader();
                    reader.readAsDataURL(image);
                    reader.onload = e =>{
                        this.previewImage = e.target.result;
                    };
                }
            }
    },
    watch: {}
};

</script>
