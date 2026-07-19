<template src="./index.template.html"></template>
<script>
export default {
  name: "AnhChup",
  data: function () {
    return {
      images: [],
      imagesSelected: [],
      zoomImg: null,
    };
  },
  computed: {
    presignedUrls: function () {
      let result = [];
      if (this.images == null || this.images.length == 0) {
        for (let i = 0; i < this.images.length; i++) {
          result.push(this.images[i].presignedUrl);
        }
      }
      return result;
    },
    relativeUrls: function () {
      let result = [];
      if (this.images == null || this.images.length == 0) {
        for (let i = 0; i < this.images.length; i++) {
          result.push(this.images[i].relativeUrl);
        }
      }
      return result;
    },
  },
  methods: {
    onMousemoveImg(e) {
      this.$refs.panelZoomImage.style.left = e.layerX + 10 + "px";
      this.$refs.panelZoomImage.style.top = e.layerY + 10 + "px";
    },
    onMouseOverImg(src) {
      this.zoomImg = src;
    },
    onMouseLeaveImg() {
      this.zoomImg = null;
    },
    bindImages: async function (relativeUrls) {
      this.images = [];
      let imgs = [];
      if (!(relativeUrls == null || relativeUrls.length == 0)) {
        try {
          this.$root.showLoading(true);
          for (let i = 0; i < relativeUrls.length; i++) {
            const relativeUrl = relativeUrls[i];
            let rs = await this.getPresignedUrl(relativeUrl);
            if (rs != null) {
              if (!(rs.result == undefined || rs.result == null)) {
                imgs.push({
                  presignedUrl: rs.result,
                  relativeUrl: relativeUrl,
                });
              }
            }
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
      this.images = imgs;
    },
    themFiles: function () {
      this.$refs.formFile.$el.querySelector("input").click();
    },
    xoaFiles: async function () {
      this.$bvModal
        .msgBoxConfirm(
          "Bạn có chắc chắn muốn xóa danh sách hình ảnh đang chọn này?",
          {
            title: "Thông báo",
            size: "sm",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
            centered: true,
          }
        )
        .then(async (v) => {
          if (v) {
            let deletedFiles = [];
            if (this.imagesSelected.length > 0) {
              try {
                this.$root.showLoading(true);
                for (let i = 0; i < this.imagesSelected.length; i++) {
                  let rs = await this.deleteFile(
                    this.imagesSelected[i].relativeUrl
                  );
                  if (rs.status) {
                    deletedFiles.push({
                      presignedUrl: this.imagesSelected[i].presignedUrl,
                      relativeUrl: this.imagesSelected[i].relativeUrl,
                    });
                    this.images = this.images.filter(
                      (x) => x.relativeUrl != this.imagesSelected[i].relativeUrl
                    );
                  }
                }
              } finally {
                this.$root.showLoading(false);
              }
            }
            if (deletedFiles.length > 0) {
              this.$root.toastSuccess(
                "Đã xóa " + deletedFiles.length + " hình ảnh được chọn"
              );
              this.$emit("filesDeleted", {
                files: this.images,
                deletedFiles: deletedFiles,
              });
              this.$emit("filesChanged", this.images);
            }
          }
        });
    },
    formFile_Changed: async function (args) {
      let uploadedFiles = [];
      let files = args.target.files;
      if (files.length > 0) {
        try {
          this.$root.showLoading(true);
          for (let i = 0; i < files.length; i++) {
            let formData = new FormData();
            formData.append("file", files[i]);
            let uploadedFile = await this.uploadFile(formData);
            if (uploadedFile.status) {
              let urls = {
                presignedUrl: uploadedFile.message.presignedUrl,
                relativeUrl: uploadedFile.message.relativeUrl,
              };
              this.images.push(urls);
              uploadedFiles.push(urls);
            }
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
      if (uploadedFiles.length > 0) {
        this.$root.toastSuccess(
          "Đã thêm được " + uploadedFiles.length + " hình ảnh mới"
        );
        this.$emit("filesUploaded", {
          files: this.images,
          uploadedFiles: uploadedFiles,
        });
        this.$emit("filesChanged", this.images);
      }
    },
    uploadFile: async function (args) {
      var result = await this.$root.context.post(
        "/web-ecms/storage/uploadFile",
        args
      );
      return result.data;
    },
    getPresignedUrl: async function (fileSource) {
      var result = await this.$root.context.post(
        "/web-ecms/storage/getPresignedUrl",
        {
          fileSource: fileSource,
        }
      );
      return result.data;
    },
    deleteFile: async function (fileSource) {
      var result = await this.$root.context.post(
        "/web-ecms/storage/deleteFile",
        {
          fileSource: fileSource,
        }
      );
      return result.data;
    },
  },
};
</script>
