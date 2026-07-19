<template>
  <b-modal ref="dlgUploadFile" size="lg" body-class="p-0" title="View File" hide-footer>
    <!-- <div class="list-actions-top">
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
    </div> -->
    <div class="popup-body">
      <!-- <div class="box-form">
        <div class="info-row">
          <div class="key w80">Đường dẫn</div>
          <div class="value">
            <div class="input-more-button -right">
              <label for="upload" class="btn">
                <span class="-ap icon-more_horiz"></span>
                <input ref="fileInput" type="file" id="upload" style="display: none" @change="onChangeFile"
                  @click="$refs.fileInput.value = null" />
              </label>
              <input type="text" class="form-control highlight" v-model="txt_filePath" readonly
                @change="onInputText($event.target.value)">
            </div>
          </div>
        </div>
      </div> -->
      <!-- <div class="box-form">
      </div> -->
        <DataGrid v-bind:columns="[
          { fieldName: 'TEN', headerText: 'Tên file', allowFiltering: true, allowSorting: false, width: 200 },
          { fieldName: '', headerText: '', allowFiltering: true, allowSorting: false, width: 100, template: downloadTemplate(this), cssClass: 'text-center' }
        ]" v-bind:dataSource="danhSachFile" 
          :showFilter="true" 
          :enabledSelectFirstRow="false" 
          :enablePagingServer="false" 
          ref="fileGrid">
        </DataGrid>
    </div>

    <ejs-dialog :enableResize='true' :allowDragging='true' :visible='false' :isModal="true" ref="popupViewFile"
      :header='"Form view file đính kèm"' showCloseIcon=true width='1300'>
      <FileViewerMinio :fileUrl="vurl">
      </FileViewerMinio>
    </ejs-dialog>
  </b-modal>

</template>
<script>
import Vue from 'vue';
import EventBus from '@/utils/eventBus'
import axios from 'axios'
import token from "@/plugins/token"
import FileViewerMinio from './FileViewerMinio.vue';
import API from '../api';

export default {
  name: 'PopupUploadFile',
  components: {
    FileViewerMinio: FileViewerMinio
  },
  data() {
    return {
      danhSachFile: [],
      vurl: '',
    }
  },
  methods: {
    async getPresigneUrl(params) {
      this.loading(true)
      let dsResult = []
      try {
        const response = await API.getPresigneUrl(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          dsResult = response.data.data.result
        }
        console.log('dsResult', dsResult);
      } catch (error) {
        console.log('apiGetPreUrlList error: ', error);
        this.$root.toastError('Lỗi đọc file từ hệ thống!')
      } finally {
        this.loading(false)
      }
      return dsResult      
    },
    downloadTemplate(parent) {
      return function() {
        return {
          //
          template: {
            template: `<div>
                        <button class="btn btn-success lh14 pad3" @click="onView" title="Xem file">
                          <span class="icon nc-icon-outline ui-1_eye-17"></span>
                        </button>
                        <button class="btn btn-danger lh14 pad3" @click="onDelete" title="Xoá file">
                          <span class="icon nc-icon-outline ui-1_simple-remove"></span>
                        </button>
                      </div>`,
            data() {
              return {
                parent: parent,
                data: this.data || {}
              }
            },
            watch: {},
            computed: {},
            methods: {
              async onView() {
                console.log('onView', this.data);
                if (!this.data.URL) {
                  const params = {'fileSource': this.data.FILE_PATH}
                  this.parent.vurl = await this.parent.getPresigneUrl(params)  
                } else {
                  this.parent.vurl = this.data.URL
                }
                this.parent.$refs.popupViewFile.show()
              },
              async onDelete() {
                console.log('onDelete', this.data);
                let isAccept = false
                await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn xóa file đã chọn?', {
                  "title": 'Thông báo',
                  "size": 'sm',
                  "buttonSize": 'md',
                  "okVariant": 'primary',
                  "okTitle": 'Đồng ý',
                  "cancelTitle": 'Không đồng ý',
                  "hideHeaderClose": false,
                  "centered": true,
                  "modal-class": ["f18", "text-center"],
                })
                  .then(async (value) => {
                    isAccept = value
                  })
                  .catch(err => {
                    console.log(err);
                  })
                if (!isAccept) {
                  return
                }
                try {
                  this.parent.loading(true)
                  this.parent.danhSachFile = this.parent.danhSachFile.filter(item => item.FILE_ID !== this.data.FILE_ID)
                  this.parent.$emit('deleteFile', this.data)
                } catch (error) {
                  console.log('apiGetPreUrlList error: ', error);
                  this.parent.$toast.error('Có lỗi xảy ra trong quá trình xoá file!')
                } finally {
                  this.parent.loading(false)
                }
              },
              onDownload() {

              },
            }
          }
        }
      }
    },
    showModal() {
      this.$refs.dlgUploadFile.show();
    },
    hideModal() {
      this.danhSachFile = null
      this.$refs['dlgUploadFile'].hide();
    },
    onFileChecked(args) {
      
    },
    // async onDelete() {
    //   try {
    //     let items = this.$refs.fileGrid.getSelectedRecords();
    //     if (items.length == 0) return this.$toast.error('Chưa chọn file trên lưới để xóa!');

    //     if (this.grid_File.items.length === 0) return this.$toast.error('Chưa có danh sách file trên lưới để xóa!');

    //     let currentUser = this.$auth.getUserName().toLowerCase();
    //     if (currentUser !== 'admin') {
    //       for (let item of this.grid_File.checked) {
    //         if (item.NGUOI_CN != null && item.NGUOI_CN.toLowerCase() !== currentUser)
    //           return this.$toast.error('Bạn không được quyền sửa xóa file của người dùng khác!');
    //       }
    //     }
    //     let isAccept = false
    //     await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn xóa các file đã chọn?', {
    //       "title": 'Thông báo',
    //       "size": 'sm',
    //       "buttonSize": 'md',
    //       "okVariant": 'primary',
    //       "okTitle": 'Đồng ý',
    //       "cancelTitle": 'Không đồng ý',
    //       "hideHeaderClose": false,
    //       "centered": true,
    //       "modal-class": ["f18", "text-center"],
    //     })
    //       .then(async (value) => {
    //         isAccept = value
    //       })
    //       .catch(err => {
    //         console.log(err);
    //       })
    //     if (!isAccept) {
    //       return
    //     }

    //     let count = 0;
    //     console.log(this.grid_File.checked, " = this.grid_File.checked")
    //     for (let item of this.grid_File.checked) {
    //       let rs = await this.deleteFileFromMinio(item.SERVER_URL);
    //       if (rs) {
    //         console.log(rs, " = res")
    //         let isXoaFile = await this.xoaFileHoTroKH(item.HOSOHTKH_ID)
    //         if (isXoaFile) count++;
    //       } else {
    //         this.$toast.error('Có lỗi xảy ra trong quá trình xóa file!');
    //       }
    //     }
    //     if (count === this.grid_File.checked.length)
    //       this.$toast.success('Xóa file thành công!');
    //     await this.getDsFileHoSo();

    //   } catch (error) {
    //     console.log(error);
    //     this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
    //   }
    // },
    // async onDownloadHandler(args) {
    //   try {
    //     this.loading(true);
    //     if (args.SERVER_URL == null || args.SERVER_URL === '')
    //       return this.$toast.error('Không thể download file. Không có dữ liệu trên Server!');

    //     if (args.SERVER_URL.includes('http://') || args.SERVER_URL.includes('https://')) {
    //       var newTab = window.open('', '_blank');
    //       var fURL = newTab.document.createElement("a");
    //       fURL.href = args.SERVER_URL;
    //       newTab.document.body.appendChild(fURL);
    //       fURL.click();
    //       newTab.document.body.removeChild(fURL);
    //     } else {
    //       let rs = await this.getFileFromMinio(args.SERVER_URL);
    //       if (rs != null && rs != '') {
    //         var newTab = window.open('', '_blank');
    //         var fURL = newTab.document.createElement("a");
    //         fURL.href = URL.createObjectURL(new Blob([rs], { type: 'application/octet-stream' }));
    //         fURL.setAttribute("download", args.TEN);
    //         newTab.document.body.appendChild(fURL);
    //         fURL.click();
    //         URL.revokeObjectURL(fURL.href)
    //         newTab.document.body.removeChild(fURL);
    //         newTab.close();
    //       } else {
    //         return this.$toast.error('Không có dữ liệu trên Server!');
    //       }
    //     }
    //   } catch (error) {
    //     console.log(error);
    //     this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
    //   } finally {
    //     this.loading(false);
    //   }
    // },
    // async onViewHandler(args) {
    //   try {
    //     this.loading(true);
    //     console.log(args.SERVER_URL, " = args")
    //     if (args.SERVER_URL == null || args.SERVER_URL === '')
    //       return this.$toast.error('Không có dữ liệu trên Server!');
    //     if (args.SERVER_URL.includes('http://') || args.SERVER_URL.includes('https://')) {
    //       var newTab = window.open('', '_blank');
    //       var fURL = newTab.document.createElement("a");
    //       fURL.href = args.SERVER_URL;
    //       newTab.document.body.appendChild(fURL);
    //       fURL.click();
    //       newTab.document.body.removeChild(fURL);
    //     } else {
    //       let url = await this.getPresignedUrlFromMinio(args.SERVER_URL);
    //       if (url != null && url !== '') {
    //         this.vurl = url;
    //         this.$refs.popupViewFile.show()
    //         // var newTab = window.open('', '_blank');
    //         // var fURL = newTab.document.createElement("a");
    //         // fURL.href = url;
    //         // newTab.document.body.appendChild(fURL);
    //         // fURL.click();
    //         // newTab.document.body.removeChild(fURL);
    //       } else
    //         return this.$toast.error('Không có dữ liệu trên Server!');
    //     }
    //   } catch (error) {
    //     console.log(error);
    //     this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
    //   } finally {
    //     this.loading(false);
    //   }
    // },
    // async uploadFileToMinio(item) {
    //   try {
    //     this.loading(true);
        // let formData = new FormData();
        // formData.append("file", item);
    //     let url = `/web-suco/storage/uploadFile`;
    //     let response = await this.$root.context.post(url, formData);
    //     if (response.error_code === 'BSS-00000000' && response.data != undefined) {
    //       if (response.data.status) {
    //         return response.data.message;
    //       } else {
    //         this.$toast.error(response.data.message);
    //         return null;
    //       }
    //     } else return null;
    //   }
    //   catch (error) {
    //     console.log(error);
    //     this.$toast.error('Có lỗi xảy ra trong quá trình upload file!');
    //     return null;
    //   }
    //   finally {
    //     this.loading(false);
    //   }
    // },
    // async deleteFileFromMinio(src) {
    //   try {
    //     this.loading(true);
    //     let url = `/web-suco/storage/deleteFile`;
    //     let response = await this.$root.context.post(url, { fileSource: src });
    //     if (response.error_code === 'BSS-00000000' && response.data != undefined) {
    //       return true;
    //     } else return false;
    //   }
    //   catch (error) {
    //     console.log(error);
    //     return false;
    //   }
    //   finally {
    //     this.loading(false);
    //   }
    // },
    // async getFileFromMinio(src) {
    //   try {
    //     this.loading(true);
    //     let url = `/web-suco/storage/downloadFile?fileSource=${encodeURIComponent(src)}`;
    //     let response = await this.$root.context.get(url);
    //     if (response) {
    //       return response;
    //     } else return null;
    //   }
    //   catch (error) {
    //     console.log(error);
    //     return null;
    //   }
    //   finally {
    //     this.loading(false);
    //   }
    // },
    // async getPresignedUrlFromMinio(src) {
    //   try {
    //     let url = `/web-suco/storage/getPresignedUrl`;
    //     let response = await this.$root.context.post(url, { fileSource: src });
    //     if (response.error_code === 'BSS-00000000' && response.data != undefined) {
    //       return response.data.result;
    //     } else return null;
    //   }
    //   catch (error) {
    //     console.log(error);
    //     return null;
    //   }
    //   finally { }
    // },
    // async getFileFromMinio(src) {
    //   try {
    //     let url = `/web-suco/storage/downloadFile?fileSource=${src}`;
    //     let response = await this.getOriginal(url);
    //     return response.data;
    //   }
    //   catch (error) {
    //     console.log(error);
    //     return null;
    //   }
    //   finally { }
    // },
    // async getOriginal(url) {
    //   var instance = axios.create();
    //   instance.interceptors.request.use(request => {
    //     request.baseURL = process.env.API;
    //     request.headers = {
    //       "Content-Type": "application/json",
    //       Authorization: token.getAuthorization()
    //     };
    //     request.data = {};
    //     request.responseType = 'blob';
    //     return request;
    //   });
    //   var begin = Date.now();
    //   var r = await instance.get(url);
    //   if (process.env.NODE_ENV !== "production") {
    //     console.info(
    //       "url: " + url + " (" + (Date.now() - begin) + "ms)"
    //     );
    //   }
    //   return r;
    // }
  }
}
</script>
