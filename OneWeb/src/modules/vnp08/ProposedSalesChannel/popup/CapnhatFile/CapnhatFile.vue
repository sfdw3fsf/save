
<template src="./CapnhatFile.html"></template>

<style scoped src="./CapnhatFile.scss"></style>

<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import api from "../../api/index.js";
export default {
    name: 'CapnhatFile',
    props: {
        hdkenh_id: {
            type: Number,
            default: ''
        },
        ten_kenh_ban: {
            type: String,
            default: ''
        },
        dsFile: {
            type: Array,
            default: ''
        },
    },
    components: {
        ActionTop,
    },
    data() {
        return {
            dsLoaiFile: [],
            items: {file_upload:""},
            txtKenhban: "",
        }
    },
    mounted() {
    },
    methods: {
        showModal() {
            this.$refs["capnhatFileModal"].show();
            this.getDanhsach_LoaiFile();
            console.log(this.items)
        },
        hideModal() {
            this.$refs["capnhatFileModal"].hide();
            this.items.file_upload= "";
        },
        async handleShowModal() {
            //Khi show modal
            setTimeout(() => {
                //this.initDuLieu()
            }, 500)

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
                this.items.file_upload = response.data.data.join('|');
                this.$refs.files_upload.value=null;
                }
                }).catch(function(){
                console.log('File upload failed!');
            });
            console.log(this.items.file_upload)
        },
         async CapnhatFile(){
            let dsFile_New = {}
            dsFile_New.loaifile_id = this.items.loaifile_id;
            dsFile_New.url = this.items.file_upload;
            dsFile_New.ghichu = this.items.ghichu
            this.dsFile.push(dsFile_New);
            let input = {
                    hdkenh_id: this.hdkenh_id,
                    files: this.dsFile
            }
            console.log(this.dsFile)
            let data = this.GetDataList(await api.capNhatFileHDK(this.axios,input));
            if(data=="OK"){
                    this.$toast.success('Cập nhật thành công!');
                    this.$emit("xacnhan", {...this.items.file_upload});
                    this.hideModal()
            }
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
        Clear(){
            this.items.file_upload = "";
            this.items.ghichu = "";
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
