<template src="./index.html">
</template>

<script>
export default {
    props: {
        idModal: String,
        maTB: String
    },
    data: function() {
        return {
            LoaiHinhTB: {
                DIDONGTRATRUOC: 21
            },
            loaitb_id: null,
            src: null,
            file: null,
            image: null,
            formData: null,
            stt: null,
            dsImages: [null, null, null, null]
        }
    },
    methods: {
        showModal: function() {
            this.src = '/static/img/No_Image_Available.ae6dd4f.jpeg';
            this.loaitb_id = this.LoaiHinhTB.DIDONGTRATRUOC
        },
        closeModal: function() {
            this.$bvModal.hide(this.idModal);
        },
        chonImage: function(stt) {
            // this.stt = stt;
            document.getElementById(`my-file-${stt}`).click();
        },
        previewImage: function(event, stt) {
            var input = event.target;
            console.log(input.files);
            if (input.files) {
                var reader = new FileReader();
                reader.onload = (e) => {
                    console.log(stt);
                    let src = e.target.result;
                    console.log(src);
                    document.getElementById(`show-file-${stt}`).style.backgroundImage = `url(${src})`;
                    // this.hasImage=false
                }
                this.image=input.files[0];
                // this.formData = new FormData()
                // this.formData.append("file",input.files[0])
                if(stt == 1) {
                    this.dsImages[0] = input.files[0];
                }
                else if(stt == 2) {
                    this.dsImages[1] = input.files[0];
                }
                else if(stt == 3) {
                    this.dsImages[2] = input.files[0];
                }
                else if(stt == 4) {
                    this.dsImages[3] = input.files[0];
                }
                reader.readAsDataURL(input.files[0]);
            }
        },
        taiAnh: async function() {
            try {
                this.loading(true);
                let uploadedFiles = [];
                if(!this.maTB) {
                    this.$root.toastError('Chưa có thuê bao');
                    return;
                }
                if(this.loaitb_id == this.LoaiHinhTB.DIDONGTRATRUOC) {
                    let date = new Date();
                    let today = ((date.getDate() > 9) ? date.getDate() : ('0' + date.getDate())) + '/' +  ((date.getMonth() > 8) ? (date.getMonth() + 1) : ('0' + (date.getMonth() + 1))) + '/' + date.getFullYear();
                    if(this.dsImages.every(e => e != null)) {
                        for (let i = 0; i < this.dsImages.length; i++) {
                            let formData = new FormData();
                            formData.append("file", this.dsImages[i]);
                            let uploadedFile = await this.uploadFile(formData);
                            if (uploadedFile.status) {
                            let urls = {
                                presignedUrl: uploadedFile.message.presignedUrl,
                                relativeUrl: uploadedFile.message.relativeUrl,
                            };
                            uploadedFiles.push(urls);
                            }
                        }
                        console.log(uploadedFiles);
                        if (uploadedFiles.length > 0) {
                            // this.$root.toastSuccess("Đã thêm được " + uploadedFiles.length + " hình ảnh mới");
                            let rs = await this.uploadCCBS(uploadedFiles);
                            if(rs.error_code == 'BSS-00003028') {
                                this.$root.toastError(rs.message);
                            }
                            else {
                                this.$root.toastSuccess("Đã thêm được " + uploadedFiles.length + " hình ảnh mới");
                            }
                        }
                    }
                    else {
                        this.$root.toastError('Phải chọn 4 ảnh tải lên.');
                    }
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        uploadFile: async function (args) {
            var result = await this.$root.context.post(
                "/web-ecms/storage/uploadFile",
                args
            );
            return result.data;
        },
        chonFile: function() {
            this.$refs.formFile.$el.querySelector("input").click();
        },
        formFile_Changed: function(event) {
            let input = event.target;
            let length = 0;
            if(input.files.length == 0) {
                return;
            }
            if(input.files.length > 4) {
                length = 4;
                this.$root.toastError('Số lượng ảnh bạn chọn lớn hơn 4. Hệ thống chỉ lấy 4 ảnh đầu tiên');
            }
            else {
                length = input.files.length;
            }
            console.log(input.files);
            for (let index = 1; index <= length; index++) {
                // this.previewImage(event, index)
                var reader = new FileReader();
                reader.onload = (e) => {
                    let src = e.target.result;
                    document.getElementById(`show-file-${index}`).style.backgroundImage = `url(${src})`;
                }
                if(index == 1) {
                    this.dsImages[0] = input.files[0];
                }
                else if(index == 2) {
                    this.dsImages[1] = input.files[1];
                }
                else if(index == 3) {
                    this.dsImages[2] = input.files[2];
                }
                else if(index == 4) {
                    this.dsImages[3] = input.files[3];
                }
                reader.readAsDataURL(input.files[index - 1]);
            }
        },
        uploadCCBS: async function(data) {
            var result = await this.$root.context.post("/ccbs/prepaid/ts_upload_img_tt_khcn", {
                gt_img_1: data[0].relativeUrl,
                gt_img_2: data[1].relativeUrl,
                gt_img_3: data[2].relativeUrl,
                gt_img_4: data[3].relativeUrl,
                so_tb: this.maTB
            });
            return result;
        }
    }
}
</script>

<style scoped>
.uploadImage {
    height: 100%;
    background-image: url('/static/img/No_Image_Available.ae6dd4f.jpeg');
    background-position: center;
    background-size: contain;
    background-repeat: no-repeat;
    height: 252px;
}
</style>