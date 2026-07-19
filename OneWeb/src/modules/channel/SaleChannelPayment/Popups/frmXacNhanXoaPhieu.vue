<template>
  <b-modal
      id="popup-frmXacNhanXoaPhieu"
      size="lg"
      hide-footer
      hide-header
      hide-header-close
      body-class="modal-body p-0"
      centered 
  >
      <div class="modal-content popup-box">
          <div class="popup-header">
              <div class="title">
                <span class="icon fa fa-trash"></span>Xác nhận xoá phiếu
              </div>
              <div
                  class="close -ap icon-close"
                  data-dismiss="modal"
                  @click="$bvModal.hide('popup-frmXacNhanXoaPhieu')"
              ></div>
          </div>
          <div class="popup-body">
            <div class="list-actions-top">
      <ul class="list">
        <li>
          <a>
            <span class="icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" viewBox="0 0 1024 1024"><path fill="currentColor" d="M512 64a448 448 0 1 1 0 896a448 448 0 0 1 0-896zm-55.808 536.384l-99.52-99.584a38.4 38.4 0 1 0-54.336 54.336l126.72 126.72a38.272 38.272 0 0 0 54.336 0l262.4-262.464a38.4 38.4 0 1 0-54.272-54.336L456.192 600.384z"/></svg>
            </span> Chấp nhận
          </a>
        </li>
        <li>
          <a  @click="$bvModal.hide('popup-frmXacNhanXoaPhieu')">
            <span class="icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 20 20"><path fill="currentColor" d="M11 3.5a.5.5 0 0 0-.576-.494l-7 1.07A.5.5 0 0 0 3 4.57v10.86a.5.5 0 0 0 .424.494l7 1.071a.5.5 0 0 0 .576-.494V10h5.172l-.997.874a.5.5 0 0 0 .658.752l1.996-1.75a.5.5 0 0 0 0-.752l-1.996-1.75a.499.499 0 1 0-.658.752l.997.874H11V3.5Zm-2.5 7.75a.75.75 0 1 1 0-1.5a.75.75 0 0 1 0 1.5Zm4 4.75H12v-5h1v4.5a.5.5 0 0 1-.5.5ZM12 8V4h.5a.5.5 0 0 1 .5.5V8h-1Z"/></svg>
            </span> Thoát
          </a>
        </li>
        
      </ul>
    </div>
              <div class="box-form">
                <div class="row">
                    <div class="col-3">
                        <svg xmlns="http://www.w3.org/2000/svg" version="1.1" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:svgjs="http://svgjs.com/svgjs" width="150" height="150" x="0" y="0" viewBox="0 0 512 512" style="enable-background:new 0 0 512 512" xml:space="preserve" class=""><g><circle cx="184.81" cy="155.74" r="12.69" fill="#035fcc" data-original="#41045b" opacity="1" class=""></circle><path fill="#035fcc" d="M414.25 117.55v248.22c0 7.37-3.31 14.36-9.01 19.04l-63.45 54.44c-10.71 9.04-26.72 7.69-35.77-3.02a25.42 25.42 0 0 1-5.99-16.26v-23.22h-76.14c-28.03 0-50.76-22.73-50.76-50.76v-38.07c0-7.01 5.68-12.69 12.69-12.69s12.69 5.68 12.69 12.69v38.07c0 14.02 11.36 25.38 25.38 25.38h76.14v-204.2a38.099 38.099 0 0 1 12.69-28.81l54.13-46.19H223.9c-10.95 0-20.66 7-24.11 17.39a12.676 12.676 0 0 1-12.69 7.99c-7.01 0-12.69-5.68-12.69-12.69-.12-1.01-.12-2.03 0-3.05 6.95-21.33 27.07-35.57 49.49-35.02h139.59c12.79-.1 25.12 4.77 34.39 13.58a50.76 50.76 0 0 1 16.37 37.18z" data-original="#41045b" opacity="1" class=""></path><path fill="#68acfc" d="M100.8 224.65c-4.06 4.06-4.07 10.65 0 14.72l41.5 39.21c4.19 3.93 10.78 3.71 14.71-.48.05-.05.09-.1.14-.15 1.77-1.9 2.76-4.39 2.79-6.98v-26.52h88.83c7.01 0 12.69-5.68 12.69-12.69s-5.68-12.69-12.69-12.69h-88.32v-25.38c-.07-5.82-4.84-10.48-10.66-10.41-2.58.09-5.05 1.07-6.98 2.79l-42 38.58z" data-original="#a085ec" class="" opacity="1"></path></g></svg>
                    </div>
                    <div class="col-9">
                        <div class="title red">Bạn có đồng ý xoá 1 phiếu với tổng tiền: 1.000.000?</div>
                        <div class="info-row">
                            <div class="key">Mật khẩu</div>
                            <div class="value">
                                <input type="password" class="form-control" placeholder="Nhập mật khẩu">
                                </div>  
                        </div>
                    </div>
                </div>
              </div>
          </div>
      </div>
  </b-modal>
</template>
<script>
export default {
  name: "frmXacNhanXoaPhieu",
  
};
</script>
