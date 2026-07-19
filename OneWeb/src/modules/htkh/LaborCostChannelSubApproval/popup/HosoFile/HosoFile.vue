
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
        ma_tap: {
            type: String,
            default: ""
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
            dsFileVB: [],
            items: {file_url:""},
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
            this.dsFileVB=[]
        },
        async handleShowModal() {
            this.getDanhsach_FileVB();
        },
        
        //Lấy danh sách huonggiao
         async getDanhsach_FileVB(){
              let input = {
                    p_ma_tap: this.ma_tap
                }
            let data = this.GetDataList(await api.sp_lay_ds_file_tinhcuoc_nhancong(this.axios,input));
            this.dsFileVB = data; 
        },
        async onFileSelected(args){
            this.items = args.data
        }, 
        changeFile() {
            let formData = new FormData();
            for(let file of this.$refs.files_upload.files) {
                formData.append('files', file);
            }
            console.log(this.$refs.files_upload.files)
            api.upload_file(this.axios,formData,{ headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
                if(response.data.error_code === 'BSS-00000000') {
                this.items.file_url = response.data.data.join('|');

                this.$refs.files_upload.value=null;
                }
                }).catch(function(){
                console.log('File upload failed!');
            });
            console.log(111111111)
            console.log(this.items.file_url)
        },
         async CapnhatFile(){
            let input = {
                    p_ma_tap: this.ma_tap,
                    p_file_url: this.items.file_url,
                    p_file_name: "Test"
            }
            let data = this.GetDataList(await api.sp_upload_file_tinhcuoc_nhancong(this.axios,input));
            if(data=="OK"){
                    this.$toast.success('Cập nhật File thành công!');
                    this.getDanhsach_FileVB()
            }
        },
        tsbtnNhapMoi_Click(){
            this.items.file_url = ""
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
                            let fileArray = this.dsFileVB.filter(item => item.file_id !== data_file.file_id);                          
                            let input = {
                                    p_ma_tap: this.ma_tap,
                                    p_file_id: this.items.file_id
                            }
                            let data = this.GetDataList(await api.sp_xoa_file_tinhcuoc_nhancong(this.axios,input));
                            if(data=="OK"){
                                    this.$toast.success('Xóa file thành công!');
                                    this.getDanhsach_FileVB()
                            }
                        }
                    });
            }else{
                this.checkFileType(data_file.file_url)
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
