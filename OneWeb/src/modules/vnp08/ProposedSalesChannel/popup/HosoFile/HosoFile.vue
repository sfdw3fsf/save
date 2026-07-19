
<template src="./HosoFile.html"></template>

<style scoped src="./HosoFile.scss"></style>

<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import api from "../../api/index.js";
import moment from 'moment'
import CapnhatFile from "../CapnhatFile/CapnhatFile.vue";
export default {
    name: 'HosoFile',
    props: {
        hdkenh_id: {
            type: Number,
            default: 0
        },
    },
    components: {
        ActionTop,
        CapnhatFile
    },
    data() {
        return {
            dsLoaiFile: [],
            dsFile: [],
            themmoi: true,
            dsFileKenhBan: [],
            items: {url: ""},
            noidung: "",
            ngaygiao: moment().format('DD/MM/YYYY')
        }
    },
    mounted() {
    },
    methods: {
        showModal() {
            this.$refs["HosoFileModal"].show()
        },
        hideModal() {
            this.$refs["HosoFileModal"].hide();
            this.dsFileKenhBan=[]
            this.dsLoaiFile=[]
            this.items.url = "";
            this.items.ghichu = "";
        },
        async handleShowModal() {
            this.getDanhsach_FileKenhBan();
            this.getDanhsach_LoaiFile();
            
        },
        
        //Lấy danh sách huonggiao
         async getDanhsach_FileKenhBan(){
              let input = {
                    hdkenh_id: this.hdkenh_id
                }
            let data = this.GetDataList(await api.dsFileHDK(this.axios,input));
            this.dsFileKenhBan = data; 
        },
        CapnhatFile_Click(index, rowData) {
            this.$refs.capnhatFileModal.showModal();
        },
         //Lấy danh sách loại giấy tờ
        async getDanhsach_LoaiFile(){
            let input = {"hdkenh_id": this.hdkenh_id} 
            let data = this.GetDataList(await api.dsLoaiFile(this.axios,input));
            this.dsLoaiFile = data;
            this.items.loaifile_id = data[0].loaifile_id;
        },
        async changeFile() {
            this.loading(true);
            let formData = new FormData();
            for(let file of this.$refs.files_upload.files) {
                formData.append('files', file); 
            }
            
            await api.upload_file(this.axios,formData,{ headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
                
                if(response.data.error_code === 'BSS-00000000') {
                    this.items.url = response.data.data.join('|');
                    console.log(this.items.url)
                    this.$refs.files_upload.value=null;
                }
                }).catch(function(){
                    console.log('File upload failed!');
                })
                .finally(() => {
                    this.loading(false); 
                }); 
        }, 
        onNhapMoi() {
            this.themmoi = true,
            this.items = {url: "",ghichu: "",loaifile_id:this.dsLoaiFile[0].loaifile_id,file_id: 0}
        },
        async onFileSelected(args){
            this.items = args.data
        }, 
        grid_selectedRowChanged(){
        }, 
         async CapnhatFile(){
            this.loading(true);
            if(this.themmoi){
                let dsFile_New = {}
                dsFile_New.loaifile_id = this.items.loaifile_id;
                dsFile_New.url = this.items.url;
                dsFile_New.ghichu = this.items.ghichu
                this.dsFileKenhBan.push(dsFile_New);
            }else{
                this.item = this.dsFileKenhBan.find(item => item.file_id === this.items.file_id);
                for (let i = 0; i < this.dsFileKenhBan.length; i++) {
                    if (this.dsFileKenhBan[i].file_id === this.items.file_id) {
                        this.dsFileKenhBan[i].loaifile_id = this.items.loaifile_id;
                        this.dsFileKenhBan[i].url = this.items.url;
                        this.dsFileKenhBan[i].ghichu = this.items.ghichu;
                        break; 
                    }
                }
            }
            let input = {
                hdkenh_id: this.hdkenh_id,
                files: this.dsFileKenhBan
            }
            let data = this.GetDataList(await api.capNhatFileHDK(this.axios,input));
            if(data=="OK"){
                     console.log(888888)
                     console.log(this.themmoi)
                     if(this.themmoi){
                        this.$toast.success('Thêm mới file thành công!');
                     }else{
                        this.$toast.success('Cập nhật file thành công!');
                     }
                    this.getDanhsach_FileKenhBan();
                    this.themmoi = false
                    this.loading(false);
                    
            }
        },
        gridDsFileCommand(args,data_file) {
            if(args == 'Xóa'){
                this.$bvModal.msgBoxConfirm("Bạn có muốn xóa file "+data_file.loai_file+" này không?", {
                        title: '',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Không đồng ý',
                    }).then(async v => {
                        if(v) {
                            this.loading(true);
                            let fileArray = this.dsFileKenhBan.filter(item => item.file_id !== data_file.file_id);                          
                            let input = {
                                    hdkenh_id: this.hdkenh_id,
                                    files: fileArray
                            }
                            let data = this.GetDataList(await api.capNhatFileHDK(this.axios,input));
                            if(data=="OK"){
                                    this.$toast.success('Xóa file thành công!');
                                    this.getDanhsach_FileKenhBan();
                                    this.loading(false);
                            }
                        }
                    });
            }else{
                this.checkFileType(data_file.url)
            }

         },
         downloadFile(url) {
            const link = document.createElement('a');
            link.href = url;
            link.target = "_blank"
            link.click();
         },
         async checkFileType(event) {
            let file = event
            if (file.endsWith('.jpg') || file.endsWith('.jpeg') || file.endsWith('.png')) {
                this.viewFile(file);
                this.onClickXemAnh();
            } else {
                await this.viewFile(file);
                this.downloadFile(this.linkview_file)
            }
        },
        async viewFile(url){
            let input ={
                list_file: url
            }
            let data = this.GetDataList(await api.view_file(this.axios,input));
            this.linkview_file = data[0].file_url;
        },
        onClickXemAnh() {
            this.$bvModal.show("PopUp");
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
