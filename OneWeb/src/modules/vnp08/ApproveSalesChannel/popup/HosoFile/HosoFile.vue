
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
            dsFileKenhBan: [],
            items: {url:""},
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
        },
        async handleShowModal() {
            this.getDanhsach_FileKenhBan();
            this.getDanhsach_LoaiFile()
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
        changeFile() {
            let formData = new FormData();
            for(let file of this.$refs.files_upload.files) {
                formData.append('files', file);
            }
            api.upload_file(this.axios,formData,{ headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
                if(response.data.error_code === 'BSS-00000000') {
                this.items.url = response.data.data.join('|');
                this.$refs.files_upload.value=null;
                }
                }).catch(function(){
                console.log('File upload failed!');
            });
            console.log(this.items.url)
        },
         async CapnhatFile(){
            let dsFile_New = {}
            dsFile_New.loaifile_id = this.items.loaifile_id;
            dsFile_New.url = this.items.url;
            dsFile_New.ghichu = this.items.ghichu
            this.dsFile.push(dsFile_New);
            let input = {
                    hdkenh_id: this.hdkenh_id,
                    files: this.dsFile
            }
            console.log(this.dsFile)
            let data = this.GetDataList(await api.capNhatFileHDK(this.axios,input));
            if(data=="OK"){
                    this.$toast.success('Cập nhật File thành công!');
            }
        },
         async onFileSelected(args){
            this.items = args.data
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
                            let fileArray = this.dsFileKenhBan.filter(item => item.file_id !== data_file.file_id);                          
                            let input = {
                                    hdkenh_id: this.items.hdkenh_id,
                                    files: fileArray
                            }
                            let data = this.GetDataList(await api.capNhatFileHDK(this.axios,input));
                            if(data=="OK"){
                                    this.$toast.success('Xóa file thành công!');
                                    this.getDanhsach_FileKenhBan()
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
