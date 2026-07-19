<template>
    <b-modal
        ref="dlgUploadFile"
        size="lg"
        body-class="p-0"
        title="Upload File"
        hide-footer
    >
        <div class="list-actions-top">
            <ul class="list">
            <li>
                <a href="javascript: void(0)" @click="onAdd" title="Thêm File">
                    <span class="icon icon ui-1_circle-add nc-icon-glyph"></span> Thêm File
                </a>
            </li>
            <li>
                <a href="javascript: void(0)" @click="onDelete" title="Xóa File">
                    <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Xóa File
                </a>
            </li>
            <li>
                <a href="javascript: void(0)" @click="hideModal" title="Đóng">
                    <span class="icon one-export"></span> Đóng
                </a>
            </li>
            </ul>
        </div>
        <div class="popup-body">
                <div class="box-form">
                    <div class="info-row">
                        <div class="key w80">Đường dẫn</div>
                        <div class="value">
                        <div class="input-more-button -right">                           
                            <label for="upload" class="btn">
                                <span class="-ap icon-more_horiz"></span>
                                <input ref="fileInput" type="file" id="upload" style="display: none" @change="onChangeFile"
                                    @click="$refs.fileInput.value=null"/>                                    
                            </label>
                            <input type="text" class="form-control highlight" v-model="txt_filePath" readonly @change="onInputText($event.target.value)">
                        </div>
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <DataGrid v-bind:columns="[
                        {fieldName: 'ten_file', headerText: 'Tên file', allowFiltering: true, allowSorting: false, width: 200},
                        {fieldName: 'nguoi_cn', headerText: 'Người upload', allowFiltering: true, allowSorting: false, width: 100},
                        {fieldName: 'ngay_cn1', headerText: 'Ngày upload', allowFiltering: true, allowSorting: false, width: 100},
                        {fieldName: '', headerText: 'Xem/Tải về', allowFiltering: true, allowSorting: false, width: 100, template: downloadTemplate, cssClass: 'text-center'}
                    ]"
                        v-bind:dataSource="grid_File.items"
                        dataKeyField="file_id"
                        :showFilter="true"                    
                        :allowPaging="true"
                        :enabledSelectFirstRow = "false"
                        :enablePagingServer="false"
                        :showColumnCheckbox="true"
                        @selectedItemsChanged="onFileChecked"
                        ref="fileGrid"                        
                    >
                    </DataGrid>
                </div>
            </div>
    </b-modal>
</template>
<script>
import Vue from 'vue';
import EventBus from '@/utils/eventBus'
import axios from 'axios'
import token from "@/plugins/token"
export default {
    name:'PopupUploadFile',

    props: {        
    },

    created () {
        EventBus.$on('onDownload', this.onDownloadHandler);
        EventBus.$on('onView', this.onViewHandler);
    },

    destroyed () {      
        EventBus.$off('onDownload', this.onDownloadHandler);
        EventBus.$off('onView', this.onViewHandler);
    },

    data() {
        return {
            vloai_hd: 0,
            vhopdong_id: 0,
            vServerName: '',
            txt_filePath: '',
            files: null,
            vurl: '',
            vfile_id: 0,
            grid_File: {
                items: [],
                checked: []
            },
            downloadTemplate: function () {
                return { template : Vue.component('columnTemplate', {
                    template: `<div>
                                    <button class="btn btn-success lh14 pad3" @click="onView" title="Xem file">
                                        <span class="icon nc-icon-outline ui-1_eye-17"></span>
                                    </button>
                                    <button class="btn btn-danger lh14 pad3" @click="onDownload" title="Tải file">
                                        <span class="-ap icon-download"></span>
                                    </button>
                                </div>`,                        
                    data () { 
                        return { data: {} }; 
                    },
                    methods: {
                        onDownload() {                  
                            EventBus.$emit('onDownload', this.data);
                        },
                        onView() {
                            EventBus.$emit('onView', this.data);
                        }
                    }
                    })
                }
            }
        }
    },

    methods:{
        async showModal() {
            this.$refs['dlgUploadFile'].show();
            await this.getDsFileHoSo();
            this.grid_File.items = [...this.grid_File.items];
        },

        hideModal() {
            this.txt_filePath = '';
            this.files = null;
            this.grid_File = {
                items: [],
                checked: []
            };
            this.$refs['dlgUploadFile'].hide();
        },

        onChangeFile(e) {
            this.files = e.target.files;
            if (this.files.length > 0) {
                for (let file of this.files) {
                    let extension = file.name.slice(-3);
                    if (extension === 'exe' || extension === 'msi' || extension === 'bat' || extension === '.js')
                        return this.$toast.error('Vui lòng không upload file có định dạng .exe/.msi/.bat!');
                }
                this.txt_filePath = this.files[0].name;
            }
        },

        onFileChecked(args) {
            this.grid_File.checked = args;
        },
        
        async onAdd() {
            if (this.txt_filePath !== '' || this.files != null) {
                let url = await this.uploadFileToMinio(this.files[0]);
                if (url == null) {
                    this.txt_filePath = '';
                    return this.$toast.error('Upload file thất bại!');
                } else {
                    let arr = url.relativeUrl.split('/');
                    let tenFile = arr[arr.length - 1];
                    let rs = await this.updateFileHoSo(1, url.relativeUrl, this.vfile_id, tenFile, this.vhopdong_id, this.vloai_hd);
                    if (rs) this.$toast.success('Thêm file thành công!');
                    else {
                        //Xóa file trên minio để dọn rác:
                        await this.deleteFileFromMinio(url.relativeUrl);
                        this.$toast.error('Có lỗi xảy ra khi thêm file!');
                    }
                    await this.getDsFileHoSo();
                }
            } else {
                this.$toast.error('Bạn chưa chọn file!');
            }
        },

        async onDelete() {
            if (this.grid_File.items.length === 0)
                return this.$toast.error('Chưa có danh sách file trên lưới để xóa!');
            if (this.grid_File.checked.length === 0)
                return this.$toast.error('Chưa chọn file trên lưới để xóa!');
            
            // let currentUser = this.$auth.getUserName().toLowerCase();
            // if (currentUser !== 'admin') {
            //     for (let item of this.grid_File.checked) {
            //         let file = this.grid_File.items.find(e => e.file_id === item);
            //         if (file.nguoi_cn != null && file.nguoi_cn.toLowerCase() !== currentUser)
            //             return this.$toast.error('Bạn không được quyền sửa xóa file hợp đồng của người dùng khác!');
            //     }
            // }
            for (let item of this.grid_File.checked) {
                try {
                    let url = `/web-toanha/danhmuc-hopdong-dautu/kiemtra-quyen-toanha?chucNang=FILE_HOPDONG&kieu=3&id=${item}&ttvtId=0`
                    let response = await this.$root.context.get(url)
                    if (response.error_code === 'BSS-00000000' && response.data == 'OK') {
                        //
                    } else {
                       return this.$toast.error(response.data ?? 'Bạn không được quyền sửa xóa file hợp đồng của người dùng khác!');
                    }
                } catch (error) {
                    console.log(error)
                    return this.$toast.error('Có lỗi xảy ra khi gọi api kiểm tra quyền hợp đồng')
                }
            }
            try {                
                await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn xóa các file đã chọn?', {
                "title": 'Thông báo',
                "size": 'sm',
                "buttonSize": 'md',
                "okVariant": 'primary',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Không đồng ý',
                "hideHeaderClose": false,
                "centered": true,
                "modal-class": ["f18","text-center"],
                })
                .then(async(value) => {
                    if (value){
                        let count = 0;
                        for (let item of this.grid_File.checked) {
                            let file = this.grid_File.items.find(e => e.file_id === item);
                            let rs = await this.deleteFileFromMinio(file.url);
                            if (rs)
                                var rs1 = await this.updateFileHoSo(3, '', file.file_id, '', 0, 0);
                                if (rs1) count++;
                            else this.$toast.error('Có lỗi xảy ra trong quá trình xóa file!');
                        }
                        if (count === this.grid_File.checked.length)
                            this.$toast.success('Xóa file thành công!');
                        await this.getDsFileHoSo();
                    }
                })
                .catch(err => {
                    console.log(err);
                })
            } catch (error) {
                console.log(error);
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            }
        },

        async onDownloadHandler(args) {
            try {
                this.loading(true);
                if (args.url == null || args.url === '')
                    return this.$toast.error('Không thể download file. Không có dữ liệu trên Server!');

                if (args.url.includes('http://') || args.url.includes('https://')) {
                    var newTab = window.open('', '_blank');
                    var fURL = newTab.document.createElement("a");
                    fURL.href = args.url;
                    newTab.document.body.appendChild(fURL);
                    fURL.click();
                    newTab.document.body.removeChild(fURL);
                } else {
                    let isExists = await this.checkExistsFileInMinio(args.url);
                    if (isExists) {
                        let rs = await this.getFileFromMinio(args.url);
                        var newTab = window.open('', '_blank');
                        var fURL = newTab.document.createElement("a");
                        fURL.href = URL.createObjectURL(new Blob([rs], { type: 'application/octet-stream' }));
                        fURL.setAttribute("download", args.ten_file);
                        newTab.document.body.appendChild(fURL);
                        fURL.click();
                        URL.revokeObjectURL(fURL.href)
                        newTab.document.body.removeChild(fURL);
                        newTab.close();
                    } else 
                        return this.$toast.error('Không thể download file. Không có dữ liệu trên Server!');
                }
            } catch (error) {
                console.log(error);
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            } finally {
                this.loading(false);
            }
        },

        async onViewHandler(args) {
            try {
                this.loading(true);
                if (args.url == null || args.url === '')
                    return this.$toast.error('Không có dữ liệu trên Server!');
                if (args.url.includes('http://') || args.url.includes('https://')) {
                    var newTab = window.open('', '_blank');
                    var fURL = newTab.document.createElement("a");
                    fURL.href = args.url;
                    newTab.document.body.appendChild(fURL);
                    fURL.click();
                    newTab.document.body.removeChild(fURL);
                } else {
                    let isExists = await this.checkExistsFileInMinio(args.url);
                    if (isExists) {
                        let url = await this.getPresignedUrlFromMinio(args.url);
                        if (url != null && url !== '') {
                            var newTab = window.open('', '_blank');
                            var fURL = newTab.document.createElement("a");
                            fURL.href = url;
                            newTab.document.body.appendChild(fURL);
                            fURL.click();
                            newTab.document.body.removeChild(fURL);
                        } else 
                            return this.$toast.error('Không có dữ liệu trên Server!');
                    } else 
                        return this.$toast.error('Không có dữ liệu trên Server!');
                }
            } catch (error) {
                console.log(error);
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            } finally {
                this.loading(false);
            }
        },
        
        async getDsFileHoSo() {
            try {
                this.loading(true);
                this.txt_filePath = '';
                this.files = null;
                this.grid_File.items = [];
                let url = `/web-toanha/danhmuc-hopdong-dautu/danhsach-file-hoso?hopDongId=${this.vhopdong_id}&loaiHd=${this.vloai_hd}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    this.grid_File.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async uploadFileToMinio(item) {
            try {
                this.loading(true);
                let formData = new FormData();
                formData.append("file", item);
                let url = `/web-suco/storage/uploadFile`;
                let response = await this.$root.context.post(url, formData);
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    if (response.data.status) {
                        return response.data.message;
                    } else return null;
                } else return null;
            } 
            catch(error) {
                console.log(error);
                this.$toast.error('Có lỗi xảy ra trong quá trình upload file!');
                return null;
            }
            finally {
                this.loading(false);
            }
        },

        async deleteFileFromMinio(src) {
            try {
                this.loading(true);
                let url = `/web-suco/storage/deleteFile`;
                let response = await this.$root.context.post(url, { fileSource: src });
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    return response.data.status;
                } else return false;
            } 
            catch(error) {
                console.log(error);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async getFileFromMinio(src) {
            try {
                this.loading(true);
                let url = `/web-suco/storage/downloadFile?fileSource=${encodeURIComponent(src)}`;
                let response = await this.$root.context.get(url);
                if (response) {
                    return response;
                } else return null;
            } 
            catch(error) {
                console.log(error);
                return null;
            }
            finally {
                this.loading(false);
            }
        },

        async updateFileHoSo(kieu, url, fileId, tenFile, hopDongId, loaiHd) {
            try {
                this.loading(true);
                let apiBody = {
                    kieu: kieu,
                    fileId: fileId,
                    tenFile: tenFile,
                    url: url,
                    hopDongId: hopDongId,
                    loaiHd: loaiHd,
                    ghiChu: ""
                };
                let apiUrl = `/web-toanha/danhmuc-hopdong-dautu/capnhat-file-hoso`;
                let response = await this.$root.context.post(apiUrl, apiBody);
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    return response.data.startsWith('OK');
                } else return false;
            }
            catch (error) {
                console.log(error);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async getPresignedUrlFromMinio(src) {
            try {
                let url = `/web-suco/storage/getPresignedUrl`;
                let response = await this.$root.context.post(url, { fileSource: src });
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    return response.data.result;
                } else return null;
            } 
            catch(error) {
                console.log(error);
                return null;
            }
            finally {}
        },

        async checkExistsFileInMinio(src) {
            try {
                let url = `/web-suco/storage/checkExistsFile`;
                let response = await this.$root.context.post(url, { fileSource: src });
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    return response.data.result;
                } else return false;
            } 
            catch(error) {
                console.log(error);
                return false;
            }
            finally {}
        },

        async getFileFromMinio(src) {
            try {
                let url = `/web-suco/storage/downloadFile?fileSource=${src}`;
                let response = await this.getOriginal(url);
                return response.data;
            } 
            catch(error) {
                console.log(error);
                return null;
            }
            finally {}
        },

        async getOriginal(url) {
            var instance = axios.create();
            instance.interceptors.request.use(request => {
                request.baseURL = process.env.API;
                request.headers = {
                    "Content-Type": "application/json",
                    Authorization: token.getAuthorization()
                };
                request.data = {};
                request.responseType = 'blob';
                return request;
            });
            var begin = Date.now();
            var r = await instance.get(url);
            if (process.env.NODE_ENV !== "production") {
                console.info(
                    "url: " + url + " (" + (Date.now() - begin) + "ms)"
                );
            }
            return r;
        }
    }
}
</script>
