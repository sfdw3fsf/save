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
                        {fieldName: 'TEN_FILE', headerText: 'Tên file', allowFiltering: true, allowSorting: false, width: 200},
                        {fieldName: 'NGUOI_CN', headerText: 'Người upload', allowFiltering: true, allowSorting: false, width: 100},
                        {fieldName: 'NGAY_CN', headerText: 'Ngày upload', allowFiltering: true, allowSorting: false, width: 100},
                        {fieldName: '', headerText: 'Xem/Tải về', allowFiltering: true, allowSorting: false, width: 100, template: downloadTemplate, cssClass: 'text-center'}
                    ]"
                        v-bind:dataSource="grid_File.items"
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
            htkh_id: null,
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
                    let rs = await this.updateFileHoSo(url.relativeUrl, tenFile);
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
            // try {
                if (this.grid_File.items.length === 0)
                    return this.$toast.error('Chưa có danh sách file trên lưới để xóa!');
                if (this.grid_File.checked.length === 0)
                    return this.$toast.error('Chưa chọn file trên lưới để xóa!');
                console.log(this.grid_File.checked , " = this.grid_File.checked")
                let currentUser = this.$auth.getUserName().toLowerCase();
                if (currentUser !== 'admin') {
                    for (let item of this.grid_File.checked) {
                        if (item.NGUOI_CN != null && item.NGUOI_CN.toLowerCase() !== currentUser)
                            return this.$toast.error('Bạn không được quyền sửa xóa file của người dùng khác!');
                    }
                }


                let isAccept = false
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
                    isAccept = value
                })
                .catch(err => {
                    console.log(err);
                })
                if(!isAccept){
                    return
                }

                let count = 0;
                console.log(this.grid_File.checked , " = this.grid_File.checked")
                for (let item of this.grid_File.checked) {
                    let rs = await this.deleteFileFromMinio(item.SERVER_URL);
                    if (rs){
                        console.log(rs , " = res")
                        let isXoaFile = await this.xoaFileHoTroKH(item.HOSOHTKH_ID)
                        if (isXoaFile) count++;
                    }else{
                        this.$toast.error('Có lỗi xảy ra trong quá trình xóa file!');
                    }
                }
                if (count === this.grid_File.checked.length)
                    this.$toast.success('Xóa file thành công!');
                await this.getDsFileHoSo();

            // } catch (error) {
            //     console.log(error);
            //     this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            // }
        },

        async onDownloadHandler(args) {
            try {
                this.loading(true);
                if (args.SERVER_URL == null || args.SERVER_URL === '')
                    return this.$toast.error('Không thể download file. Không có dữ liệu trên Server!');

                if (args.SERVER_URL.includes('http://') || args.SERVER_URL.includes('https://')) {
                    var newTab = window.open('', '_blank');
                    var fURL = newTab.document.createElement("a");
                    fURL.href = args.SERVER_URL;
                    newTab.document.body.appendChild(fURL);
                    fURL.click();
                    newTab.document.body.removeChild(fURL);
                } else {
                    let isExists = await this.checkExistsFileInMinio(args.SERVER_URL);
                    if (isExists) {
                        let rs = await this.getFileFromMinio(args.SERVER_URL);
                        var newTab = window.open('', '_blank');
                        var fURL = newTab.document.createElement("a");
                        fURL.href = URL.createObjectURL(new Blob([rs], { type: 'application/octet-stream' }));
                        fURL.setAttribute("download", args.TEN_FILE);
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
                console.log(args.SERVER_URL , " = args")
                if (args.SERVER_URL == null || args.SERVER_URL === '')
                    return this.$toast.error('Không có dữ liệu trên Server!');
                if (args.SERVER_URL.includes('http://') || args.SERVER_URL.includes('https://')) {
                    var newTab = window.open('', '_blank');
                    var fURL = newTab.document.createElement("a");
                    fURL.href = args.SERVER_URL;
                    newTab.document.body.appendChild(fURL);
                    fURL.click();
                    newTab.document.body.removeChild(fURL);
                } else {
                    let isExists = await this.checkExistsFileInMinio(args.SERVER_URL);
                    if (isExists) {
                        let url = await this.getPresignedUrlFromMinio(args.SERVER_URL);
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
        async xoaFileHoTroKH(HOSOHTKH_ID) {
            try {
                this.loading(true);
                if(HOSOHTKH_ID == null){
                    return
                }
                const baseUrl = process.env.API;
                await this.axios({
                url: `${baseUrl}/web-cskh/api/tiep-nhan-khach-hang/xoa-file-ho-so?hoSoId=${HOSOHTKH_ID}`,
                method: "DELETE",
                responseType: "blob",
                }).then((response) => {
                   if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                        return true
                    }else{
                        return false
                    }

                });
            }catch (error) {
                console.log(error.data.message);
            }finally {
                this.loading(false);
            }
        },
        async getDsFileHoSo() {
            try {
                this.loading(true);
                if(this.htkh_id == null){
                    return
                }
                this.txt_filePath = '';
                this.files = null;
                this.grid_File.items = [];
                let url = `web-cskh/api/tiep-nhan-khach-hang/danh-sach-file-ho-so?htkhId=${this.htkh_id}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    this.grid_File.items = response.data;
                    console.log(this.grid_File.items , "  = this.grid_File.items")
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
                    return true;
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

        async updateFileHoSo(url,ten_file) {
            try {
                this.loading(true);
                let apiBody = {
                   htkh_id: this.htkh_id,
                   ten_file: ten_file,
                   url: url
                };
                let apiUrl = `/web-cskh/api/tiep-nhan-khach-hang/cap-nhat-file-ho-so`;
                let response = await this.$root.context.post(apiUrl, apiBody);
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    return true;
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
                let url = `/web-qlvt/api/storage/checkExistsFile`;
                let response = await this.$root.context.post(url, { fileSource: src });
                if (response.error_code === 'BSS-00000000' && response.data != undefined) {
                    return response.data.data;
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
