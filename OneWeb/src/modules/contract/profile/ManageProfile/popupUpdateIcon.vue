<template>
  <b-modal
    ref="popupUpdateIcon"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> UpdateIcon
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="hideModal"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="tsbtnNhapMoi_Click" :class="{ disabled: !tsbtnNhapMoi }">
              <span class="icon nc-icon-glyph ui-1_circle-add"></span>Thêm mới
            </a>
          </li>
          <li>
            <a @click="tsbtnGhiLai_Click" :class="{ disabled: !tsbtnGhiLai }">
              <span class="icon one-save"></span>Ghi lại
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form padt8 padb8 fw6">CẬP NHẬT ICON</div>
        <div class="box-form">
          <div class="info-row">
            <div class="key w70">Tên icon</div>
            <div class="value">
              <input
                type="text"
                class="form-control"
                v-model="txtTenIcon"
                ref="inputTenIcon"
              />
            </div>
          </div>
          <div class="info-row">
            <div class="key w70">File ảnh</div>
            <div class="value">
              <input
                type="text"
                class="form-control"
                v-model="file_full_path"
              />
            </div>
          </div>
          <div class="info-row">
            <div class="key w70"></div>
            <div class="value">
              <a class="upload-button">
                <img id="ItemPreview" width="100px" height="100px"/>
                <input
                  type="file"
                  ref="files"
                  class="file"
                  @change="onChangeFile($event)"
                  id="upload-file"
                />
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import ActionTop from "@/components/ActionTop.vue";
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
export default {
  name: "popupUpdateIcon",
  components: { breadcrumb, ActionTop },
  data() {
    return {
      tsbtnGhiLai: true,
      tsbtnNhapMoi: true,
      txtTenIcon: "",
      file_full_path: "",
      _imageData: "",
    };
  },
  mounted() {},
  methods: {
    showModal() {
      this.$refs["popupUpdateIcon"].show();
    },
    hideModal() {
      this.$refs["popupUpdateIcon"].hide();
    },
    ptbIcon_Click() {},
    tsbtnNhapMoi_Click() {
      this.tsbtnGhiLai = true;
      this.file_full_path = "";
      this.txtTenIcon = "";
      document.getElementById("ItemPreview").src ="";
    },
    onChangeFile(e) {
      if (!e.target.files.length) return;
      let file = e.target.files[0];
      this.file_full_path = e.target.value;
      let reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = (e) => {
        const src = e.target.result;
        const img = new Image();
        img.onload = () => {
          console.log(img.width+" x "+img.height);
          if( img.width >16 || img.height>16){
            this.$toast.error("Bạn chỉ được nhập ảnh có kích thước tối đa 16x16. Mời bạn chọn lại.!");
            this.file_full_path = "";
            this._imageData = "";
            document.getElementById("ItemPreview").src =null;
            return false;
          }else{
            
            this.getBase64(file).then((res) => {
              this._imageData = res.replace(/^data:(.*,)?/, '');
              document.getElementById("ItemPreview").src =  res;
              console.log(res.replace(/^data:(.*,)?/, ''))
            });
          }
        };
        img.onerror = () => {
          console.log("error");
        };
        img.src = src;
      };
      
    },
   
    getBase64(file) {
      return new Promise(function (resolve, reject) {
        let reader = new FileReader();
        let imgResult = "";
        reader.readAsDataURL(file);
        reader.onload = function () {
          imgResult = reader.result;
        };
        reader.onerror = function (error) {
          reject(error);
        };
        reader.onloadend = function () {
          resolve(imgResult);
        };
      });
    },
    tsbtnGhiLai_Click() {
      if (this.txtTenIcon.trim() == "") {
        this.$toast.error("Vui lòng nhập tên icon");
        this.$refs.inputTenIcon.focus();
        return false;
      }
      if (this.file_full_path == "") {
        this.$toast.error("Bạn chưa chọn Icon");
        return false;
      }
      //hs.THEMMOI_ICON(txtTenIcon.Text.Trim(), _imageData);
      this.loading(true);
      API.themmoi_icon(this.axios, {
        vicon: this.txtTenIcon.trim(),
        vicon_img: this._imageData,
      })
        .then((response) => {
          if (
            response.data.data &&
              response.data.error_code &&
              response.data.error_code === "BSS-00000000" &&
              response.data.data === "1"
          ) {
            this.$toast.success("Thêm mới thành công");
            this.tsbtnGhiLai = false;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
  },
};
</script>
