<style scoped>
.list-actions-top .list > li > div.button {
    display: block;
    padding: 2px 15px;
    color: #0176FF;
    border-left: 1px solid #BAE7FF;
    text-decoration: none;
    font-weight: 600;
    cursor: pointer;
}
</style>
<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogChonAnhHoSo" :position="position" :header="'Upload ảnh thuê bao di động: +84' + state.vma_tb" showCloseIcon="true" width="70%" target="#app .main-wrapper">
    <div class=""> 
        <div class="list-actions-top">
          <ul class="list"> 
            <!-- <li>
                <a href="javascript:;" @click="tsbtnTraCuu_Click">
                    <span class="icon one-reload"></span>Chọn Files
                </a>
            </li> -->
            <li>
              <a href="javascript:;" v-on:click="btnOK_Click">
                <span class="icon one-upload"></span> Tải lên CCBS
              </a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <!-- <div class="box-form"> -->
              <!-- <div class="legend-title">Thông tin thuê bao</div> 
              -->
              
              <!-- <div class="legend-title">File Ảnh</div>  -->
              <div class="row" v-if="!state.OPEN_SYNC_VINA_TRASAU">
                  <div class="col-sm-3 col-12">
                      <div class="info-row">
                          <img ref="img1" :src="filePreview1" width="182" height="101" />
                      </div>
                       <div class="info-row">
                        Ảnh CMND mặt trước
                      </div> 
                      <div class="info-row"> 
                        <div class="upload-button">
                          <button class="btn btn btn-primary" data-toggle="modal" >
                              Chọn file
                          </button>
                          <input type="file" class="file" ref="file1" id="upload-file-1" @change="changeFileUpload">
                        </div> 
                      </div>
                  </div> 
                  <div class="col-sm-3 col-12">
                      <div class="info-row">
                           <img ref="img2" :src="filePreview2" width="182" height="101"/>
                      </div>
                      <div class="info-row">
                        Ảnh phiếu đăng ký
                      </div>
                      <div class="info-row"> 
                        <div class="upload-button">
                          <button class="btn btn btn-primary" data-toggle="modal" >
                              Chọn file
                          </button>
                          <input type="file" class="file" ref="file2" id="upload-file-2" @change="changeFileUpload">
                        </div> 
                      </div>
                  </div> 
                  <div class="col-sm-3 col-12">
                      <div class="info-row">
                          <img ref="img3" :src="filePreview3" width="182" height="101"/>
                      </div>
                      <div class="info-row">
                        Ảnh CMND mặt sau
                      </div>
                      <div class="info-row"> 
                        <div class="upload-button">
                          <button class="btn btn btn-primary" data-toggle="modal" >
                              Chọn file
                          </button>
                          <input type="file" class="file" ref="file3" id="upload-file-3" @change="changeFileUpload">
                        </div> 
                      </div>
                  </div> 
                  <div class="col-sm-3 col-12">
                      <div class="info-row">
                           <img ref="img4" :src="filePreview4" width="182" height="101"/>
                      </div>
                      <div class="info-row">
                        Ảnh người đăng ký
                      </div>
                      <div class="info-row"> 
                        <div class="upload-button">
                          <button class="btn btn btn-primary" data-toggle="modal" >
                              Chọn file
                          </button>
                          <input type="file" class="file" ref="file4" id="upload-file-4" @change="changeFileUpload">
                        </div> 
                      </div>
                  </div> 
              </div>
              <div class="row" v-if="!state.OPEN_SYNC_VINA_TRASAU">
                  <div class="col-sm-3 col-12">
                      <div class="info-row">
                          <img ref="img1" :src="filePreviewts1" width="182" height="101" />
                      </div>
                       <div class="info-row">
                        Ảnh CMND mặt trước
                      </div> 
                      <div class="info-row"> 
                        <div class="upload-button">
                          <button class="btn btn btn-primary" data-toggle="modal" >
                              Chọn file
                          </button>
                          <input type="file" class="file" ref="filets1" id="upload-file-ts-1" @change="changeFileUpload">
                        </div> 
                      </div>
                  </div> 
                  <div class="col-sm-3 col-12">
                      <div class="info-row">
                           <img ref="img2" :src="filePreviewts2" width="182" height="101"/>
                      </div>
                      <div class="info-row">
                        Ảnh CMND mặt sau
                      </div>
                      <div class="info-row"> 
                        <div class="upload-button">
                          <button class="btn btn btn-primary" data-toggle="modal" >
                              Chọn file
                          </button>
                          <input type="file" class="file" ref="filets2" id="upload-file-ts-2" @change="changeFileUpload">
                        </div> 
                      </div>
                  </div> 
                  <div class="col-sm-3 col-12">
                      <div class="info-row">
                          <img ref="img3" :src="filePreviewts3" width="182" height="101"/>
                      </div>
                      <div class="info-row">
                        Ảnh người đến giao kết hợp đồng
                      </div>
                      <div class="info-row"> 
                        <div class="upload-button">
                          <button class="btn btn btn-primary" data-toggle="modal" >
                              Chọn file
                          </button>
                          <input type="file" class="file" ref="filets3" id="upload-file-ts-3" @change="changeFileUpload">
                        </div> 
                      </div>
                  </div> 
                  <div class="col-sm-3 col-12">
                      <div class="info-row">
                           <img ref="img4" :src="filePreviewts4" width="182" height="101"/>
                      </div>
                      <div class="info-row">
                        Ảnh người sử dụng thuê bao
                      </div>
                      <div class="info-row"> 
                        <div class="upload-button">
                          <button class="btn btn btn-primary" data-toggle="modal" >
                              Chọn file
                          </button>
                          <input type="file" class="file" ref="filets4" id="upload-file-ts-4" @change="changeFileUpload">
                        </div> 
                      </div>
                  </div> 
              </div>
              <div class="row" v-if="state.OPEN_SYNC_VINA_TRASAU">
                <div class="nav tabs tab-over">
                  <a href="#tab1" data-toggle="tab" class="minw0 active">Ảnh cá nhân</a>
                  <a href="#tab2" data-toggle="tab" class="minw0">Ảnh hợp đồng</a>                 
                </div>
                <div class="tab-content">
                  <div class="tab-pane active" id="tab1">
                    <div class="row">
                      <div class="col-sm" v-for="item in state.dsIdLoaiAnhCaNhan" :key="item.field_name">
                          <div class="info-row" >
                            <a href="javascript:;" @click="viewImage(item.imgPreview)">
                              <img  :src="item.imgPreview" width="300" height="200" />
                            </a>
                          </div>
                            <div class="info-row">
                              {{item.description}}
                          </div> 
                          <div class="info-row"> 
                            <div class="upload-button">
                              <button class="btn btn btn-primary" data-toggle="modal" >
                                  Chọn file
                              </button>
                              <input type="file" class="file" :id="item.upload_img_id" accept="image/*" @change="changeFileUpload1">
                            </div> 
                          </div>
                      </div> 
                    </div>
                  </div>
                  <div class="tab-pane" id="tab2">
                    <div class="row">
                    <div class="col-sm" v-for="item in state.dsIdLoaiAnhHopDong" :key="item.field_name">
                          <div class="info-row">
                            <a href="javascript:;" @click="viewImage(item.imgPreview)">
                              <img :src="item.imgPreview" width="300" height="200" />
                            </a>  
                          </div>
                            <div class="info-row">
                            {{item.description}}
                          </div> 
                          <div class="info-row"> 
                            <div class="upload-button">
                              <button class="btn btn btn-primary" data-toggle="modal" >
                                  Chọn file
                              </button>
                              <input type="file" class="file" :id="item.upload_img_id" accept="image/*" @change="changeFileUpload1">
                            </div> 
                          </div>
                      </div> 
                    </div>
                  </div>     
                </div> 
              <!-- </div>  -->
          </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script> 
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment' 
import api from './PopUpApi' 
import ccbs from "../../ccbsFunctions";
import { 
  LOAIHINH_THUEBAO, 
} from "@/constants";
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions"; 
export default {
  components: { 
    moment,
    DatePicker, 
    api, 
  },
  name: 'frmUploadAnh',
  data() {
    return {
      Loading: false,
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        ngay_cn: moment(new Date()).format("DD/MM/YYYY hh:mm A"),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
          "DD/MM/YYYY hh:mm A"
        ),
        nguoi_cn: this.$root.token.getUserName(),
        ma_nd: this.$root.token.getUserName(),
        ma_tinh: this.$root.token.getMaTinh(),
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      showTimePanel: false,
      tabIndex: 0,
      state: {}, 
      filePreview1: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAANlBMVEX///+/v7+8vLzKysr8/Pzg4ODFxcXb29vT09Pj4+P4+PjBwcH6+vry8vLY2NjIyMjs7Ozo6Oia8u11AAAEdUlEQVR4nO2d65bqIAxGp/Smrb34/i971KqlEC7FGuJZ3/7dKewJBgK4/PsDAAAAAAAAAAAAAAAA4KUam5+i3m94UsUPoVoYwlA6MIShfGAIQ/nAEIbyOcBQSeNww9NZGOXBhuq8+w1fxugfDG1gmB0YBoFhdmAYBIbZgWEQGGYHhkFgmB0YBoFhdmAYBIbZyWB4qVI6mgyv4WUeH7u007lL7vFeWA3n8rUFrZqWK5KchnWvPapOTGFkNDQPOJrhk45Hw2d4Ns+oVPlRz2NhMxyawkTtv2OWAJvhSBwzfjBO2+g/5TLs7BDeSF4eDP0Y+yiX4UydFKtT4pRxa3SKTcVchtQgLYrobhq0Kj7+XIb0aX+fZtipHfH/ScPlZZG5Jq9hk2RYP96lTnFPcxnWpGF52d2eNrPGPc5lOJC5NDrl65Sv1fs16nEuw8v20sdCP+9uTh8NZVSuYVvTnKlBmjAdaqu/uH8Qm2FVWuNUJYSwOu3tLV9tcelNw5SF9yZjRaVixvpwbrZRTKnyuyKysRXOGn/QBqpK25QzptVm9598eyfqXDaPnah+GlNmwr/aGOcqYl3DvJtYzXU7jvU1bbVmVdExE+pP7Qjb6TiiOBFmOPuyD7XyC+djWYadGt2KxE5PzMpWluHtXc6KQZ/rNYK5RpThfWXnrGzp6iRcQ0ky7KbHK+iBup3rtQZD6wZJhq+9HDIsri89BBsUZPguIal0Y871K6ECRY6hXkFaimQeXQjVUHIMNwWkuVax5/q1xcC6Rozh0G+7vYmidaqzCaJ/whBjaEZJr608Y7QIHvBIMbR3/dfB55jr3/hrKCGGxMHN+vmi53rtSW+uEWLYUj1/dsY1169tetc1MgwHaw9HU3TN9Svec0huw4os6CZHcO7dcc/163O+XMNteC0JRfdk0Aby6BNfDcW9i1EquzedI4R36vAYLfy5htnwtnBRvaHo/SZxjN8Nz7UO5ltf97WnMgbqHCfhRbmHKa/hdXl2qxjzQQsaunMNr+FzUlD62W8bORD9TM4gshpe3xVg+Z7BonJlmN55mMhpWGkV4GughtacsbgvLnAa6hXga6B666I9OGsoRsPtMbCa7l2q6OVaAs6uMxpejXDdd+SPSTMP+vyGdri6+bAQui8u8BmaIbwxUdcXkg0dNRTjOf6BNiSOGirrXYxjcXQ+632ag6EPE7kMiU/h4dA1FJfhgUnTzZTRkCOEt8apYcpj+P1E6u4+j+H3E+kCdUmKxZAjkS4QrbMY8nwKC7qGYjFkSaQL9rqGw5AthORhIoMhUyJ9ksPwsDI+Brv97xtWY8mJNUwZYtixYu0qyjhd+yYwDALD7MAwCAyzA8MgMMwODIPAMDtHGxZ1JYvjDRvWajCC7SYRfhsBhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvJJM/wpEgyv9U+R8iNaAAAAAAAAAAAAAAAAAP4r/gEYCYE2Xwz6DQAAAABJRU5ErkJggg==",
      filePreview2: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAANlBMVEX///+/v7+8vLzKysr8/Pzg4ODFxcXb29vT09Pj4+P4+PjBwcH6+vry8vLY2NjIyMjs7Ozo6Oia8u11AAAEdUlEQVR4nO2d65bqIAxGp/Smrb34/i971KqlEC7FGuJZ3/7dKewJBgK4/PsDAAAAAAAAAAAAAAAA4KUam5+i3m94UsUPoVoYwlA6MIShfGAIQ/nAEIbyOcBQSeNww9NZGOXBhuq8+w1fxugfDG1gmB0YBoFhdmAYBIbZgWEQGGYHhkFgmB0YBoFhdmAYBIbZyWB4qVI6mgyv4WUeH7u007lL7vFeWA3n8rUFrZqWK5KchnWvPapOTGFkNDQPOJrhk45Hw2d4Ns+oVPlRz2NhMxyawkTtv2OWAJvhSBwzfjBO2+g/5TLs7BDeSF4eDP0Y+yiX4UydFKtT4pRxa3SKTcVchtQgLYrobhq0Kj7+XIb0aX+fZtipHfH/ScPlZZG5Jq9hk2RYP96lTnFPcxnWpGF52d2eNrPGPc5lOJC5NDrl65Sv1fs16nEuw8v20sdCP+9uTh8NZVSuYVvTnKlBmjAdaqu/uH8Qm2FVWuNUJYSwOu3tLV9tcelNw5SF9yZjRaVixvpwbrZRTKnyuyKysRXOGn/QBqpK25QzptVm9598eyfqXDaPnah+GlNmwr/aGOcqYl3DvJtYzXU7jvU1bbVmVdExE+pP7Qjb6TiiOBFmOPuyD7XyC+djWYadGt2KxE5PzMpWluHtXc6KQZ/rNYK5RpThfWXnrGzp6iRcQ0ky7KbHK+iBup3rtQZD6wZJhq+9HDIsri89BBsUZPguIal0Y871K6ECRY6hXkFaimQeXQjVUHIMNwWkuVax5/q1xcC6Rozh0G+7vYmidaqzCaJ/whBjaEZJr608Y7QIHvBIMbR3/dfB55jr3/hrKCGGxMHN+vmi53rtSW+uEWLYUj1/dsY1169tetc1MgwHaw9HU3TN9Svec0huw4os6CZHcO7dcc/163O+XMNteC0JRfdk0Aby6BNfDcW9i1EquzedI4R36vAYLfy5htnwtnBRvaHo/SZxjN8Nz7UO5ltf97WnMgbqHCfhRbmHKa/hdXl2qxjzQQsaunMNr+FzUlD62W8bORD9TM4gshpe3xVg+Z7BonJlmN55mMhpWGkV4GughtacsbgvLnAa6hXga6B666I9OGsoRsPtMbCa7l2q6OVaAs6uMxpejXDdd+SPSTMP+vyGdri6+bAQui8u8BmaIbwxUdcXkg0dNRTjOf6BNiSOGirrXYxjcXQ+632ag6EPE7kMiU/h4dA1FJfhgUnTzZTRkCOEt8apYcpj+P1E6u4+j+H3E+kCdUmKxZAjkS4QrbMY8nwKC7qGYjFkSaQL9rqGw5AthORhIoMhUyJ9ksPwsDI+Brv97xtWY8mJNUwZYtixYu0qyjhd+yYwDALD7MAwCAyzA8MgMMwODIPAMDtHGxZ1JYvjDRvWajCC7SYRfhsBhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvJJM/wpEgyv9U+R8iNaAAAAAAAAAAAAAAAAAP4r/gEYCYE2Xwz6DQAAAABJRU5ErkJggg==",
      filePreview3: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAANlBMVEX///+/v7+8vLzKysr8/Pzg4ODFxcXb29vT09Pj4+P4+PjBwcH6+vry8vLY2NjIyMjs7Ozo6Oia8u11AAAEdUlEQVR4nO2d65bqIAxGp/Smrb34/i971KqlEC7FGuJZ3/7dKewJBgK4/PsDAAAAAAAAAAAAAAAA4KUam5+i3m94UsUPoVoYwlA6MIShfGAIQ/nAEIbyOcBQSeNww9NZGOXBhuq8+w1fxugfDG1gmB0YBoFhdmAYBIbZgWEQGGYHhkFgmB0YBoFhdmAYBIbZyWB4qVI6mgyv4WUeH7u007lL7vFeWA3n8rUFrZqWK5KchnWvPapOTGFkNDQPOJrhk45Hw2d4Ns+oVPlRz2NhMxyawkTtv2OWAJvhSBwzfjBO2+g/5TLs7BDeSF4eDP0Y+yiX4UydFKtT4pRxa3SKTcVchtQgLYrobhq0Kj7+XIb0aX+fZtipHfH/ScPlZZG5Jq9hk2RYP96lTnFPcxnWpGF52d2eNrPGPc5lOJC5NDrl65Sv1fs16nEuw8v20sdCP+9uTh8NZVSuYVvTnKlBmjAdaqu/uH8Qm2FVWuNUJYSwOu3tLV9tcelNw5SF9yZjRaVixvpwbrZRTKnyuyKysRXOGn/QBqpK25QzptVm9598eyfqXDaPnah+GlNmwr/aGOcqYl3DvJtYzXU7jvU1bbVmVdExE+pP7Qjb6TiiOBFmOPuyD7XyC+djWYadGt2KxE5PzMpWluHtXc6KQZ/rNYK5RpThfWXnrGzp6iRcQ0ky7KbHK+iBup3rtQZD6wZJhq+9HDIsri89BBsUZPguIal0Y871K6ECRY6hXkFaimQeXQjVUHIMNwWkuVax5/q1xcC6Rozh0G+7vYmidaqzCaJ/whBjaEZJr608Y7QIHvBIMbR3/dfB55jr3/hrKCGGxMHN+vmi53rtSW+uEWLYUj1/dsY1169tetc1MgwHaw9HU3TN9Svec0huw4os6CZHcO7dcc/163O+XMNteC0JRfdk0Aby6BNfDcW9i1EquzedI4R36vAYLfy5htnwtnBRvaHo/SZxjN8Nz7UO5ltf97WnMgbqHCfhRbmHKa/hdXl2qxjzQQsaunMNr+FzUlD62W8bORD9TM4gshpe3xVg+Z7BonJlmN55mMhpWGkV4GughtacsbgvLnAa6hXga6B666I9OGsoRsPtMbCa7l2q6OVaAs6uMxpejXDdd+SPSTMP+vyGdri6+bAQui8u8BmaIbwxUdcXkg0dNRTjOf6BNiSOGirrXYxjcXQ+632ag6EPE7kMiU/h4dA1FJfhgUnTzZTRkCOEt8apYcpj+P1E6u4+j+H3E+kCdUmKxZAjkS4QrbMY8nwKC7qGYjFkSaQL9rqGw5AthORhIoMhUyJ9ksPwsDI+Brv97xtWY8mJNUwZYtixYu0qyjhd+yYwDALD7MAwCAyzA8MgMMwODIPAMDtHGxZ1JYvjDRvWajCC7SYRfhsBhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvJJM/wpEgyv9U+R8iNaAAAAAAAAAAAAAAAAAP4r/gEYCYE2Xwz6DQAAAABJRU5ErkJggg==",
      filePreview4: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAANlBMVEX///+/v7+8vLzKysr8/Pzg4ODFxcXb29vT09Pj4+P4+PjBwcH6+vry8vLY2NjIyMjs7Ozo6Oia8u11AAAEdUlEQVR4nO2d65bqIAxGp/Smrb34/i971KqlEC7FGuJZ3/7dKewJBgK4/PsDAAAAAAAAAAAAAAAA4KUam5+i3m94UsUPoVoYwlA6MIShfGAIQ/nAEIbyOcBQSeNww9NZGOXBhuq8+w1fxugfDG1gmB0YBoFhdmAYBIbZgWEQGGYHhkFgmB0YBoFhdmAYBIbZyWB4qVI6mgyv4WUeH7u007lL7vFeWA3n8rUFrZqWK5KchnWvPapOTGFkNDQPOJrhk45Hw2d4Ns+oVPlRz2NhMxyawkTtv2OWAJvhSBwzfjBO2+g/5TLs7BDeSF4eDP0Y+yiX4UydFKtT4pRxa3SKTcVchtQgLYrobhq0Kj7+XIb0aX+fZtipHfH/ScPlZZG5Jq9hk2RYP96lTnFPcxnWpGF52d2eNrPGPc5lOJC5NDrl65Sv1fs16nEuw8v20sdCP+9uTh8NZVSuYVvTnKlBmjAdaqu/uH8Qm2FVWuNUJYSwOu3tLV9tcelNw5SF9yZjRaVixvpwbrZRTKnyuyKysRXOGn/QBqpK25QzptVm9598eyfqXDaPnah+GlNmwr/aGOcqYl3DvJtYzXU7jvU1bbVmVdExE+pP7Qjb6TiiOBFmOPuyD7XyC+djWYadGt2KxE5PzMpWluHtXc6KQZ/rNYK5RpThfWXnrGzp6iRcQ0ky7KbHK+iBup3rtQZD6wZJhq+9HDIsri89BBsUZPguIal0Y871K6ECRY6hXkFaimQeXQjVUHIMNwWkuVax5/q1xcC6Rozh0G+7vYmidaqzCaJ/whBjaEZJr608Y7QIHvBIMbR3/dfB55jr3/hrKCGGxMHN+vmi53rtSW+uEWLYUj1/dsY1169tetc1MgwHaw9HU3TN9Svec0huw4os6CZHcO7dcc/163O+XMNteC0JRfdk0Aby6BNfDcW9i1EquzedI4R36vAYLfy5htnwtnBRvaHo/SZxjN8Nz7UO5ltf97WnMgbqHCfhRbmHKa/hdXl2qxjzQQsaunMNr+FzUlD62W8bORD9TM4gshpe3xVg+Z7BonJlmN55mMhpWGkV4GughtacsbgvLnAa6hXga6B666I9OGsoRsPtMbCa7l2q6OVaAs6uMxpejXDdd+SPSTMP+vyGdri6+bAQui8u8BmaIbwxUdcXkg0dNRTjOf6BNiSOGirrXYxjcXQ+632ag6EPE7kMiU/h4dA1FJfhgUnTzZTRkCOEt8apYcpj+P1E6u4+j+H3E+kCdUmKxZAjkS4QrbMY8nwKC7qGYjFkSaQL9rqGw5AthORhIoMhUyJ9ksPwsDI+Brv97xtWY8mJNUwZYtixYu0qyjhd+yYwDALD7MAwCAyzA8MgMMwODIPAMDtHGxZ1JYvjDRvWajCC7SYRfhsBhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvJJM/wpEgyv9U+R8iNaAAAAAAAAAAAAAAAAAP4r/gEYCYE2Xwz6DQAAAABJRU5ErkJggg==",
      filePreviewts1: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAANlBMVEX///+/v7+8vLzKysr8/Pzg4ODFxcXb29vT09Pj4+P4+PjBwcH6+vry8vLY2NjIyMjs7Ozo6Oia8u11AAAEdUlEQVR4nO2d65bqIAxGp/Smrb34/i971KqlEC7FGuJZ3/7dKewJBgK4/PsDAAAAAAAAAAAAAAAA4KUam5+i3m94UsUPoVoYwlA6MIShfGAIQ/nAEIbyOcBQSeNww9NZGOXBhuq8+w1fxugfDG1gmB0YBoFhdmAYBIbZgWEQGGYHhkFgmB0YBoFhdmAYBIbZyWB4qVI6mgyv4WUeH7u007lL7vFeWA3n8rUFrZqWK5KchnWvPapOTGFkNDQPOJrhk45Hw2d4Ns+oVPlRz2NhMxyawkTtv2OWAJvhSBwzfjBO2+g/5TLs7BDeSF4eDP0Y+yiX4UydFKtT4pRxa3SKTcVchtQgLYrobhq0Kj7+XIb0aX+fZtipHfH/ScPlZZG5Jq9hk2RYP96lTnFPcxnWpGF52d2eNrPGPc5lOJC5NDrl65Sv1fs16nEuw8v20sdCP+9uTh8NZVSuYVvTnKlBmjAdaqu/uH8Qm2FVWuNUJYSwOu3tLV9tcelNw5SF9yZjRaVixvpwbrZRTKnyuyKysRXOGn/QBqpK25QzptVm9598eyfqXDaPnah+GlNmwr/aGOcqYl3DvJtYzXU7jvU1bbVmVdExE+pP7Qjb6TiiOBFmOPuyD7XyC+djWYadGt2KxE5PzMpWluHtXc6KQZ/rNYK5RpThfWXnrGzp6iRcQ0ky7KbHK+iBup3rtQZD6wZJhq+9HDIsri89BBsUZPguIal0Y871K6ECRY6hXkFaimQeXQjVUHIMNwWkuVax5/q1xcC6Rozh0G+7vYmidaqzCaJ/whBjaEZJr608Y7QIHvBIMbR3/dfB55jr3/hrKCGGxMHN+vmi53rtSW+uEWLYUj1/dsY1169tetc1MgwHaw9HU3TN9Svec0huw4os6CZHcO7dcc/163O+XMNteC0JRfdk0Aby6BNfDcW9i1EquzedI4R36vAYLfy5htnwtnBRvaHo/SZxjN8Nz7UO5ltf97WnMgbqHCfhRbmHKa/hdXl2qxjzQQsaunMNr+FzUlD62W8bORD9TM4gshpe3xVg+Z7BonJlmN55mMhpWGkV4GughtacsbgvLnAa6hXga6B666I9OGsoRsPtMbCa7l2q6OVaAs6uMxpejXDdd+SPSTMP+vyGdri6+bAQui8u8BmaIbwxUdcXkg0dNRTjOf6BNiSOGirrXYxjcXQ+632ag6EPE7kMiU/h4dA1FJfhgUnTzZTRkCOEt8apYcpj+P1E6u4+j+H3E+kCdUmKxZAjkS4QrbMY8nwKC7qGYjFkSaQL9rqGw5AthORhIoMhUyJ9ksPwsDI+Brv97xtWY8mJNUwZYtixYu0qyjhd+yYwDALD7MAwCAyzA8MgMMwODIPAMDtHGxZ1JYvjDRvWajCC7SYRfhsBhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvJJM/wpEgyv9U+R8iNaAAAAAAAAAAAAAAAAAP4r/gEYCYE2Xwz6DQAAAABJRU5ErkJggg==",
      filePreviewts2: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAANlBMVEX///+/v7+8vLzKysr8/Pzg4ODFxcXb29vT09Pj4+P4+PjBwcH6+vry8vLY2NjIyMjs7Ozo6Oia8u11AAAEdUlEQVR4nO2d65bqIAxGp/Smrb34/i971KqlEC7FGuJZ3/7dKewJBgK4/PsDAAAAAAAAAAAAAAAA4KUam5+i3m94UsUPoVoYwlA6MIShfGAIQ/nAEIbyOcBQSeNww9NZGOXBhuq8+w1fxugfDG1gmB0YBoFhdmAYBIbZgWEQGGYHhkFgmB0YBoFhdmAYBIbZyWB4qVI6mgyv4WUeH7u007lL7vFeWA3n8rUFrZqWK5KchnWvPapOTGFkNDQPOJrhk45Hw2d4Ns+oVPlRz2NhMxyawkTtv2OWAJvhSBwzfjBO2+g/5TLs7BDeSF4eDP0Y+yiX4UydFKtT4pRxa3SKTcVchtQgLYrobhq0Kj7+XIb0aX+fZtipHfH/ScPlZZG5Jq9hk2RYP96lTnFPcxnWpGF52d2eNrPGPc5lOJC5NDrl65Sv1fs16nEuw8v20sdCP+9uTh8NZVSuYVvTnKlBmjAdaqu/uH8Qm2FVWuNUJYSwOu3tLV9tcelNw5SF9yZjRaVixvpwbrZRTKnyuyKysRXOGn/QBqpK25QzptVm9598eyfqXDaPnah+GlNmwr/aGOcqYl3DvJtYzXU7jvU1bbVmVdExE+pP7Qjb6TiiOBFmOPuyD7XyC+djWYadGt2KxE5PzMpWluHtXc6KQZ/rNYK5RpThfWXnrGzp6iRcQ0ky7KbHK+iBup3rtQZD6wZJhq+9HDIsri89BBsUZPguIal0Y871K6ECRY6hXkFaimQeXQjVUHIMNwWkuVax5/q1xcC6Rozh0G+7vYmidaqzCaJ/whBjaEZJr608Y7QIHvBIMbR3/dfB55jr3/hrKCGGxMHN+vmi53rtSW+uEWLYUj1/dsY1169tetc1MgwHaw9HU3TN9Svec0huw4os6CZHcO7dcc/163O+XMNteC0JRfdk0Aby6BNfDcW9i1EquzedI4R36vAYLfy5htnwtnBRvaHo/SZxjN8Nz7UO5ltf97WnMgbqHCfhRbmHKa/hdXl2qxjzQQsaunMNr+FzUlD62W8bORD9TM4gshpe3xVg+Z7BonJlmN55mMhpWGkV4GughtacsbgvLnAa6hXga6B666I9OGsoRsPtMbCa7l2q6OVaAs6uMxpejXDdd+SPSTMP+vyGdri6+bAQui8u8BmaIbwxUdcXkg0dNRTjOf6BNiSOGirrXYxjcXQ+632ag6EPE7kMiU/h4dA1FJfhgUnTzZTRkCOEt8apYcpj+P1E6u4+j+H3E+kCdUmKxZAjkS4QrbMY8nwKC7qGYjFkSaQL9rqGw5AthORhIoMhUyJ9ksPwsDI+Brv97xtWY8mJNUwZYtixYu0qyjhd+yYwDALD7MAwCAyzA8MgMMwODIPAMDtHGxZ1JYvjDRvWajCC7SYRfhsBhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvJJM/wpEgyv9U+R8iNaAAAAAAAAAAAAAAAAAP4r/gEYCYE2Xwz6DQAAAABJRU5ErkJggg==",
      filePreviewts3: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAANlBMVEX///+/v7+8vLzKysr8/Pzg4ODFxcXb29vT09Pj4+P4+PjBwcH6+vry8vLY2NjIyMjs7Ozo6Oia8u11AAAEdUlEQVR4nO2d65bqIAxGp/Smrb34/i971KqlEC7FGuJZ3/7dKewJBgK4/PsDAAAAAAAAAAAAAAAA4KUam5+i3m94UsUPoVoYwlA6MIShfGAIQ/nAEIbyOcBQSeNww9NZGOXBhuq8+w1fxugfDG1gmB0YBoFhdmAYBIbZgWEQGGYHhkFgmB0YBoFhdmAYBIbZyWB4qVI6mgyv4WUeH7u007lL7vFeWA3n8rUFrZqWK5KchnWvPapOTGFkNDQPOJrhk45Hw2d4Ns+oVPlRz2NhMxyawkTtv2OWAJvhSBwzfjBO2+g/5TLs7BDeSF4eDP0Y+yiX4UydFKtT4pRxa3SKTcVchtQgLYrobhq0Kj7+XIb0aX+fZtipHfH/ScPlZZG5Jq9hk2RYP96lTnFPcxnWpGF52d2eNrPGPc5lOJC5NDrl65Sv1fs16nEuw8v20sdCP+9uTh8NZVSuYVvTnKlBmjAdaqu/uH8Qm2FVWuNUJYSwOu3tLV9tcelNw5SF9yZjRaVixvpwbrZRTKnyuyKysRXOGn/QBqpK25QzptVm9598eyfqXDaPnah+GlNmwr/aGOcqYl3DvJtYzXU7jvU1bbVmVdExE+pP7Qjb6TiiOBFmOPuyD7XyC+djWYadGt2KxE5PzMpWluHtXc6KQZ/rNYK5RpThfWXnrGzp6iRcQ0ky7KbHK+iBup3rtQZD6wZJhq+9HDIsri89BBsUZPguIal0Y871K6ECRY6hXkFaimQeXQjVUHIMNwWkuVax5/q1xcC6Rozh0G+7vYmidaqzCaJ/whBjaEZJr608Y7QIHvBIMbR3/dfB55jr3/hrKCGGxMHN+vmi53rtSW+uEWLYUj1/dsY1169tetc1MgwHaw9HU3TN9Svec0huw4os6CZHcO7dcc/163O+XMNteC0JRfdk0Aby6BNfDcW9i1EquzedI4R36vAYLfy5htnwtnBRvaHo/SZxjN8Nz7UO5ltf97WnMgbqHCfhRbmHKa/hdXl2qxjzQQsaunMNr+FzUlD62W8bORD9TM4gshpe3xVg+Z7BonJlmN55mMhpWGkV4GughtacsbgvLnAa6hXga6B666I9OGsoRsPtMbCa7l2q6OVaAs6uMxpejXDdd+SPSTMP+vyGdri6+bAQui8u8BmaIbwxUdcXkg0dNRTjOf6BNiSOGirrXYxjcXQ+632ag6EPE7kMiU/h4dA1FJfhgUnTzZTRkCOEt8apYcpj+P1E6u4+j+H3E+kCdUmKxZAjkS4QrbMY8nwKC7qGYjFkSaQL9rqGw5AthORhIoMhUyJ9ksPwsDI+Brv97xtWY8mJNUwZYtixYu0qyjhd+yYwDALD7MAwCAyzA8MgMMwODIPAMDtHGxZ1JYvjDRvWajCC7SYRfhsBhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvJJM/wpEgyv9U+R8iNaAAAAAAAAAAAAAAAAAP4r/gEYCYE2Xwz6DQAAAABJRU5ErkJggg==",
      filePreviewts4: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAANlBMVEX///+/v7+8vLzKysr8/Pzg4ODFxcXb29vT09Pj4+P4+PjBwcH6+vry8vLY2NjIyMjs7Ozo6Oia8u11AAAEdUlEQVR4nO2d65bqIAxGp/Smrb34/i971KqlEC7FGuJZ3/7dKewJBgK4/PsDAAAAAAAAAAAAAAAA4KUam5+i3m94UsUPoVoYwlA6MIShfGAIQ/nAEIbyOcBQSeNww9NZGOXBhuq8+w1fxugfDG1gmB0YBoFhdmAYBIbZgWEQGGYHhkFgmB0YBoFhdmAYBIbZyWB4qVI6mgyv4WUeH7u007lL7vFeWA3n8rUFrZqWK5KchnWvPapOTGFkNDQPOJrhk45Hw2d4Ns+oVPlRz2NhMxyawkTtv2OWAJvhSBwzfjBO2+g/5TLs7BDeSF4eDP0Y+yiX4UydFKtT4pRxa3SKTcVchtQgLYrobhq0Kj7+XIb0aX+fZtipHfH/ScPlZZG5Jq9hk2RYP96lTnFPcxnWpGF52d2eNrPGPc5lOJC5NDrl65Sv1fs16nEuw8v20sdCP+9uTh8NZVSuYVvTnKlBmjAdaqu/uH8Qm2FVWuNUJYSwOu3tLV9tcelNw5SF9yZjRaVixvpwbrZRTKnyuyKysRXOGn/QBqpK25QzptVm9598eyfqXDaPnah+GlNmwr/aGOcqYl3DvJtYzXU7jvU1bbVmVdExE+pP7Qjb6TiiOBFmOPuyD7XyC+djWYadGt2KxE5PzMpWluHtXc6KQZ/rNYK5RpThfWXnrGzp6iRcQ0ky7KbHK+iBup3rtQZD6wZJhq+9HDIsri89BBsUZPguIal0Y871K6ECRY6hXkFaimQeXQjVUHIMNwWkuVax5/q1xcC6Rozh0G+7vYmidaqzCaJ/whBjaEZJr608Y7QIHvBIMbR3/dfB55jr3/hrKCGGxMHN+vmi53rtSW+uEWLYUj1/dsY1169tetc1MgwHaw9HU3TN9Svec0huw4os6CZHcO7dcc/163O+XMNteC0JRfdk0Aby6BNfDcW9i1EquzedI4R36vAYLfy5htnwtnBRvaHo/SZxjN8Nz7UO5ltf97WnMgbqHCfhRbmHKa/hdXl2qxjzQQsaunMNr+FzUlD62W8bORD9TM4gshpe3xVg+Z7BonJlmN55mMhpWGkV4GughtacsbgvLnAa6hXga6B666I9OGsoRsPtMbCa7l2q6OVaAs6uMxpejXDdd+SPSTMP+vyGdri6+bAQui8u8BmaIbwxUdcXkg0dNRTjOf6BNiSOGirrXYxjcXQ+632ag6EPE7kMiU/h4dA1FJfhgUnTzZTRkCOEt8apYcpj+P1E6u4+j+H3E+kCdUmKxZAjkS4QrbMY8nwKC7qGYjFkSaQL9rqGw5AthORhIoMhUyJ9ksPwsDI+Brv97xtWY8mJNUwZYtixYu0qyjhd+yYwDALD7MAwCAyzA8MgMMwODIPAMDtHGxZ1JYvjDRvWajCC7SYRfhsBhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvJJM/wpEgyv9U+R8iNaAAAAAAAAAAAAAAAAAP4r/gEYCYE2Xwz6DQAAAABJRU5ErkJggg==",
      filePreviewts5: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAANlBMVEX///+/v7+8vLzKysr8/Pzg4ODFxcXb29vT09Pj4+P4+PjBwcH6+vry8vLY2NjIyMjs7Ozo6Oia8u11AAAEdUlEQVR4nO2d65bqIAxGp/Smrb34/i971KqlEC7FGuJZ3/7dKewJBgK4/PsDAAAAAAAAAAAAAAAA4KUam5+i3m94UsUPoVoYwlA6MIShfGAIQ/nAEIbyOcBQSeNww9NZGOXBhuq8+w1fxugfDG1gmB0YBoFhdmAYBIbZgWEQGGYHhkFgmB0YBoFhdmAYBIbZyWB4qVI6mgyv4WUeH7u007lL7vFeWA3n8rUFrZqWK5KchnWvPapOTGFkNDQPOJrhk45Hw2d4Ns+oVPlRz2NhMxyawkTtv2OWAJvhSBwzfjBO2+g/5TLs7BDeSF4eDP0Y+yiX4UydFKtT4pRxa3SKTcVchtQgLYrobhq0Kj7+XIb0aX+fZtipHfH/ScPlZZG5Jq9hk2RYP96lTnFPcxnWpGF52d2eNrPGPc5lOJC5NDrl65Sv1fs16nEuw8v20sdCP+9uTh8NZVSuYVvTnKlBmjAdaqu/uH8Qm2FVWuNUJYSwOu3tLV9tcelNw5SF9yZjRaVixvpwbrZRTKnyuyKysRXOGn/QBqpK25QzptVm9598eyfqXDaPnah+GlNmwr/aGOcqYl3DvJtYzXU7jvU1bbVmVdExE+pP7Qjb6TiiOBFmOPuyD7XyC+djWYadGt2KxE5PzMpWluHtXc6KQZ/rNYK5RpThfWXnrGzp6iRcQ0ky7KbHK+iBup3rtQZD6wZJhq+9HDIsri89BBsUZPguIal0Y871K6ECRY6hXkFaimQeXQjVUHIMNwWkuVax5/q1xcC6Rozh0G+7vYmidaqzCaJ/whBjaEZJr608Y7QIHvBIMbR3/dfB55jr3/hrKCGGxMHN+vmi53rtSW+uEWLYUj1/dsY1169tetc1MgwHaw9HU3TN9Svec0huw4os6CZHcO7dcc/163O+XMNteC0JRfdk0Aby6BNfDcW9i1EquzedI4R36vAYLfy5htnwtnBRvaHo/SZxjN8Nz7UO5ltf97WnMgbqHCfhRbmHKa/hdXl2qxjzQQsaunMNr+FzUlD62W8bORD9TM4gshpe3xVg+Z7BonJlmN55mMhpWGkV4GughtacsbgvLnAa6hXga6B666I9OGsoRsPtMbCa7l2q6OVaAs6uMxpejXDdd+SPSTMP+vyGdri6+bAQui8u8BmaIbwxUdcXkg0dNRTjOf6BNiSOGirrXYxjcXQ+632ag6EPE7kMiU/h4dA1FJfhgUnTzZTRkCOEt8apYcpj+P1E6u4+j+H3E+kCdUmKxZAjkS4QrbMY8nwKC7qGYjFkSaQL9rqGw5AthORhIoMhUyJ9ksPwsDI+Brv97xtWY8mJNUwZYtixYu0qyjhd+yYwDALD7MAwCAyzA8MgMMwODIPAMDtHGxZ1JYvjDRvWajCC7SYRfhsBhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvJJM/wpEgyv9U+R8iNaAAAAAAAAAAAAAAAAAP4r/gEYCYE2Xwz6DQAAAABJRU5ErkJggg==",
      filePreviewts6: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAANlBMVEX///+/v7+8vLzKysr8/Pzg4ODFxcXb29vT09Pj4+P4+PjBwcH6+vry8vLY2NjIyMjs7Ozo6Oia8u11AAAEdUlEQVR4nO2d65bqIAxGp/Smrb34/i971KqlEC7FGuJZ3/7dKewJBgK4/PsDAAAAAAAAAAAAAAAA4KUam5+i3m94UsUPoVoYwlA6MIShfGAIQ/nAEIbyOcBQSeNww9NZGOXBhuq8+w1fxugfDG1gmB0YBoFhdmAYBIbZgWEQGGYHhkFgmB0YBoFhdmAYBIbZyWB4qVI6mgyv4WUeH7u007lL7vFeWA3n8rUFrZqWK5KchnWvPapOTGFkNDQPOJrhk45Hw2d4Ns+oVPlRz2NhMxyawkTtv2OWAJvhSBwzfjBO2+g/5TLs7BDeSF4eDP0Y+yiX4UydFKtT4pRxa3SKTcVchtQgLYrobhq0Kj7+XIb0aX+fZtipHfH/ScPlZZG5Jq9hk2RYP96lTnFPcxnWpGF52d2eNrPGPc5lOJC5NDrl65Sv1fs16nEuw8v20sdCP+9uTh8NZVSuYVvTnKlBmjAdaqu/uH8Qm2FVWuNUJYSwOu3tLV9tcelNw5SF9yZjRaVixvpwbrZRTKnyuyKysRXOGn/QBqpK25QzptVm9598eyfqXDaPnah+GlNmwr/aGOcqYl3DvJtYzXU7jvU1bbVmVdExE+pP7Qjb6TiiOBFmOPuyD7XyC+djWYadGt2KxE5PzMpWluHtXc6KQZ/rNYK5RpThfWXnrGzp6iRcQ0ky7KbHK+iBup3rtQZD6wZJhq+9HDIsri89BBsUZPguIal0Y871K6ECRY6hXkFaimQeXQjVUHIMNwWkuVax5/q1xcC6Rozh0G+7vYmidaqzCaJ/whBjaEZJr608Y7QIHvBIMbR3/dfB55jr3/hrKCGGxMHN+vmi53rtSW+uEWLYUj1/dsY1169tetc1MgwHaw9HU3TN9Svec0huw4os6CZHcO7dcc/163O+XMNteC0JRfdk0Aby6BNfDcW9i1EquzedI4R36vAYLfy5htnwtnBRvaHo/SZxjN8Nz7UO5ltf97WnMgbqHCfhRbmHKa/hdXl2qxjzQQsaunMNr+FzUlD62W8bORD9TM4gshpe3xVg+Z7BonJlmN55mMhpWGkV4GughtacsbgvLnAa6hXga6B666I9OGsoRsPtMbCa7l2q6OVaAs6uMxpejXDdd+SPSTMP+vyGdri6+bAQui8u8BmaIbwxUdcXkg0dNRTjOf6BNiSOGirrXYxjcXQ+632ag6EPE7kMiU/h4dA1FJfhgUnTzZTRkCOEt8apYcpj+P1E6u4+j+H3E+kCdUmKxZAjkS4QrbMY8nwKC7qGYjFkSaQL9rqGw5AthORhIoMhUyJ9ksPwsDI+Brv97xtWY8mJNUwZYtixYu0qyjhd+yYwDALD7MAwCAyzA8MgMMwODIPAMDtHGxZ1JYvjDRvWajCC7SYRfhsBhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvJJM/wpEgyv9U+R8iNaAAAAAAAAAAAAAAAAAP4r/gEYCYE2Xwz6DQAAAABJRU5ErkJggg==",
      filePreviewts7: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAANlBMVEX///+/v7+8vLzKysr8/Pzg4ODFxcXb29vT09Pj4+P4+PjBwcH6+vry8vLY2NjIyMjs7Ozo6Oia8u11AAAEdUlEQVR4nO2d65bqIAxGp/Smrb34/i971KqlEC7FGuJZ3/7dKewJBgK4/PsDAAAAAAAAAAAAAAAA4KUam5+i3m94UsUPoVoYwlA6MIShfGAIQ/nAEIbyOcBQSeNww9NZGOXBhuq8+w1fxugfDG1gmB0YBoFhdmAYBIbZgWEQGGYHhkFgmB0YBoFhdmAYBIbZyWB4qVI6mgyv4WUeH7u007lL7vFeWA3n8rUFrZqWK5KchnWvPapOTGFkNDQPOJrhk45Hw2d4Ns+oVPlRz2NhMxyawkTtv2OWAJvhSBwzfjBO2+g/5TLs7BDeSF4eDP0Y+yiX4UydFKtT4pRxa3SKTcVchtQgLYrobhq0Kj7+XIb0aX+fZtipHfH/ScPlZZG5Jq9hk2RYP96lTnFPcxnWpGF52d2eNrPGPc5lOJC5NDrl65Sv1fs16nEuw8v20sdCP+9uTh8NZVSuYVvTnKlBmjAdaqu/uH8Qm2FVWuNUJYSwOu3tLV9tcelNw5SF9yZjRaVixvpwbrZRTKnyuyKysRXOGn/QBqpK25QzptVm9598eyfqXDaPnah+GlNmwr/aGOcqYl3DvJtYzXU7jvU1bbVmVdExE+pP7Qjb6TiiOBFmOPuyD7XyC+djWYadGt2KxE5PzMpWluHtXc6KQZ/rNYK5RpThfWXnrGzp6iRcQ0ky7KbHK+iBup3rtQZD6wZJhq+9HDIsri89BBsUZPguIal0Y871K6ECRY6hXkFaimQeXQjVUHIMNwWkuVax5/q1xcC6Rozh0G+7vYmidaqzCaJ/whBjaEZJr608Y7QIHvBIMbR3/dfB55jr3/hrKCGGxMHN+vmi53rtSW+uEWLYUj1/dsY1169tetc1MgwHaw9HU3TN9Svec0huw4os6CZHcO7dcc/163O+XMNteC0JRfdk0Aby6BNfDcW9i1EquzedI4R36vAYLfy5htnwtnBRvaHo/SZxjN8Nz7UO5ltf97WnMgbqHCfhRbmHKa/hdXl2qxjzQQsaunMNr+FzUlD62W8bORD9TM4gshpe3xVg+Z7BonJlmN55mMhpWGkV4GughtacsbgvLnAa6hXga6B666I9OGsoRsPtMbCa7l2q6OVaAs6uMxpejXDdd+SPSTMP+vyGdri6+bAQui8u8BmaIbwxUdcXkg0dNRTjOf6BNiSOGirrXYxjcXQ+632ag6EPE7kMiU/h4dA1FJfhgUnTzZTRkCOEt8apYcpj+P1E6u4+j+H3E+kCdUmKxZAjkS4QrbMY8nwKC7qGYjFkSaQL9rqGw5AthORhIoMhUyJ9ksPwsDI+Brv97xtWY8mJNUwZYtixYu0qyjhd+yYwDALD7MAwCAyzA8MgMMwODIPAMDtHGxZ1JYvjDRvWajCC7SYRfhsBhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvKBIQzlA0MYygeGMJQPDGEoHxjCUD4whKF8YAhD+cAQhvJJM/wpEgyv9U+R8iNaAAAAAAAAAAAAAAAAAP4r/gEYCYE2Xwz6DQAAAABJRU5ErkJggg==",
      
      controls: {
        lvFile: {
          dataSource: [],
          type: "gridview",
        }
      }
    }
  },
  created: async function () {
     
  },
  mounted: function () { 
    
  },
  watch: {
     
  },
  methods: {
    //frmUploadAnh
    viewImage(srcImg){
      var image = new Image();
      image.src = srcImg;
      var _window = window.open('');
      _window.document.write(image.outerHTML); 
    },
    dialogClose() {},
    openDialog(data) { 
      if(!data){
        console.log("openDialog: data mus be set");
        return;
      }
      console.log("open UploadAnh", data);
 
      this.state =  {
        vma_tb: data.matb,
        ma_kh: data.vma_kh,
        ma_hd: data.vma_hd, 
        loai_kh: data.vloai_kh,
        dsIdLoaiAnh: data.dsIdLoaiAnh,
        loaitb_id: LOAIHINH_THUEBAO.DIDONGTRASAU, //data.loaitb_id,
        hdtb_id: data.hdtb_id,
        OPEN_SYNC_VINA_TRASAU: data.OPEN_SYNC_VINA_TRASAU
      };
 
      this.pageLoad();
      this.$refs.dialogChonAnhHoSo.show()
    },
    async pageLoad(){
      try{
        this.loading(true);
        //download anh
        let imgsDownloadCus = [];
        let imgsDownloadSub = [];
        
        try{
          let req = {
            "input_type": "SUB",
            "ma_kh": this.state.ma_kh,
            "so_tb": "84" + this.state.vma_tb
          }
          imgsDownloadCus = this.GetData(await api.download_img_post(this.axios, req))
          req.input_type = "CUS";
          imgsDownloadSub = this.GetData(await api.download_img_post(this.axios, req))
        }catch(e){
          imgsDownloadCus = [];
          imgsDownloadSub = [];
        }
        //init image controls
        if (this.state.dsIdLoaiAnh && this.state.dsIdLoaiAnh.length > 0) {
          this.state.dsIdLoaiAnhCaNhan = commonFn.LowerCasePropertyList(this.state.dsIdLoaiAnh[0]).map((x, i) => {
                let imgOld = (imgsDownloadCus.length > i ?  imgsDownloadCus[i] : null) 
                let imgPreview = (imgOld ?  'data:image/png;base64,' + imgOld.image_base : this.filePreview1)
                let x1 = x;
                x1.imgPreview =  imgPreview;
                x1.upload_img_id = `upload_img_id_${x1.id}`;
                x1.img_id = `img_id_${x1.id}`;
                x1.img_upload = null;
                x1.is_upload = imgOld ? false: true;
                return x1;
              });  
        }
        if (this.state.dsIdLoaiAnh && this.state.dsIdLoaiAnh.length > 1) { 
          this.state.dsIdLoaiAnhHopDong = commonFn.LowerCasePropertyList(this.state.dsIdLoaiAnh[1]).map((x, i) => {
                let imgOld = (imgsDownloadSub.length > i ?  imgsDownloadSub[i] : null) 
                let imgPreview = (imgOld ? 'data:image/png;base64,' + imgOld.image_base : this.filePreview1)
                let x1 = x;
                x1.imgPreview =  imgPreview;
                x1.upload_img_id = `upload_img_id_${x1.id}`;
                x1.img_id = `img_id_${x1.id}`;
                x1.img_upload = null;
                x1.is_upload = imgOld ? false: true;
                return x1;
          }); 
        }
        setTimeout(()=>{this.$forceUpdate();}, 200) 
        console.log("this.state.dsIdLoaiAnhCaNhan", this.state.dsIdLoaiAnhCaNhan);
        console.log("this.state.dsIdLoaiAnhHopDong", this.state.dsIdLoaiAnhHopDong);
      }catch(ex){
        commonFn.showException(this, ex);
      }finally{
        this.loading(false);
      }
    }, 
    async btnOK_Click(){
        if (!this.state.vma_tb)
            return;
        this.loading(true);
        let upload_result = false;
        try{       
          let loaitb_id = this.state.loaitb_id;
          if (loaitb_id == LOAIHINH_THUEBAO.DIDONGTRATRUOC)
          {
              //let today = moment(new Date()).format("DD/MM/YYYY");  
              if (this.state.img1 && this.state.img2 && this.state.img3 && this.state.img4)
              {
                  //upload ảnh lên 
                  let files = await this.UploadFile([this.state.img1, this.state.img2, this.state.img3, this.state.img4]); 
                  if(!files || files.length <4) {
                    this.$toast.warning("Lỗi upload files.");
                    this.loading(false);
                    return;
                  }
                  let img1 = files[0].file_name, 
                  img2 = files[1].file_name, 
                  img3 = files[2].file_name, 
                  img4 = files[3].file_name
                  console.log(img1);
                  console.log(img2);
                  console.log(img3);
                  console.log(img4);
                  //ResizeImage();
                  let lydo = "84" + this.state.vma_tb;
                  await ccbs.ts_upload_img_tt_khcn(this.axios, "84" + this.state.vma_tb, lydo, img1, img2, img3, img4);
                  upload_result = true;   
                  this.$toast.success("Tải ảnh lên thành công");
                  this.$refs.dialogChonAnhHoSo.hide()
              }
              else
              { 
                  this.$toast.warning("Phải chọn 4 ảnh tải lên.");
              } 
          }
          else if (loaitb_id == LOAIHINH_THUEBAO.DIDONGTRASAU)
          { 
            if (!await this.checkAnhTruocKhiUpload())
            {
                this.loading(false);
                return;
            }
            
            if (await this.uploadAnh_TraSau_All())
            {
                upload_result = true;
            }
            else{
              this.$toast.error("Tải ảnh lên không thành công");
            }
          }  
          if(upload_result == true){
            this.$emit("onUploadSuccess", this.state);
          }
      }catch(ex){
        commonFn.showException(this,ex);
      }
      finally{
        this.loading(false);
      }
    },
    async UploadFile(files) {
      let formData = new FormData(); 
      for(var file of files) {
        formData.append('files', file);
      }
      let data = [];
      //this.kich_thuoc_file=parseFloat((files[0].size)/1024).toFixed(2) + " MB";
      await api.upload_file(this.axios, formData, { headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
        if(response.data.error_code === 'BSS-00000000') { 
          data = response.data.data;
          console.log('upload_file done!', response.data.data);
        }
        }).catch(function(){
          console.log('File upload failed!');
        });
        let list_file = data.join(',');
        await api.link_view_file(this.axios, { "list_file": list_file }).then((response) => {
          if(response.data.error_code === 'BSS-00000000') { 
            data = response.data.data;
            console.log('get link files done!', response.data.data);
          }
        }).catch(function(){
          console.log('get link files failed!');
        });
        return data;
    },
    async checkAnhTruocKhiUpload(){
      //kiem tra xem da chon anh chua
      return true;
    },
    async uploadAnh_TraSau_All(){
        // foreach (var control in xtraScrollableControl1.Controls)
        // {
        //     var imgControl = control as ImageCCBS;
        //     if (imgControl.IsUploaded && !imgControl.IsChanged) // anh da upload va ko co gi thay doi, ko upload nua.
        //         continue;
        //     if (!imgControl.IsNotNull && imgControl.Base64Code == null) // anh khong bat buoc
        //         continue;
        //     var result = uploadAnh_TraSau(imgControl);
        //     if (result)
        //         imgControl.IsUploaded = true;
        //     else if (imgControl.IsUploaded || !imgControl.IsNotNull) // neu tai len fail nhung da co anh da upload roi, hoac la anh khong bat buoc.
        //         continue;
        //     else
        //         return false;                                    
        // }
        /// vuth update 16/05 start: chỉ cần upload ảnh hđ 1 lần, nên hỏi lại nếu hợp đồng có nhiều thuê bao
        // var hdkh_id = new CheckDataFacade().MAP_ID("hdkh_id", DatabaseConstants.DB2 + ".hd_thuebao"
        //                                         , "where ma_tb = '" + vma_tb + "'");
        
        // let hdkh_id = this.GetData(await api.fn_tt_hd_thuebao(this.axios, { type:13, param: this.state.vma_tb }));                                                
        // // var da_upload_anh_kh = new CheckDataFacade().MAP_ID("hdtb_id", DatabaseConstants.DB2 + ".hd_thuebao"
        // //                                           , "where hdkh_id = " + hdkh_id + " and sync_stb = 1 ");
        // let da_upload_anh_kh = this.GetData(await api.fn_tt_hd_thuebao(this.axios, { type:14, param: hdkh_id }));                                                
        
        // if (da_upload_anh_kh != "-1")
        // {
        //     if (Message_Box.ShowQuestion("Đã upload ảnh thuê bao thành công. Ảnh hợp đồng khách hàng đã được upload trước đó, bạn có muốn upload lại ?") != DialogResult.Yes)
        //         return true;
        // }
        let imgsForUpload = []
        this.state.dsIdLoaiAnhCaNhan.forEach(item => {
          if (item.is_upload && item.not_null == 1 && !item.img_upload){  
            throw "Yêu cầu upload: " + item.description
          }  
          imgsForUpload.push(item); 
        });
        this.state.dsIdLoaiAnhHopDong.forEach(item => {
          if (item.is_upload && item.not_null == 1 &&!item.img_upload){  
            throw "Yêu cầu upload: " + item.description
          }
          imgsForUpload.push(item); 
        });
        console.log("imgsForUpload", imgsForUpload)
        if (!imgsForUpload.some(x => x.is_upload == true)) {
          throw "Không có sự thay đổi ảnh";
        }
        
        //upload ảnh lên 
        let upFiles = imgsForUpload.filter(x => x.is_upload == true).map(x=>x.img_upload);
        let files = await this.UploadFile(upFiles); 
        
        if(!files || (files.length != upFiles.length)) {
          this.$toast.warning("Lỗi upload files.");
          this.loading(false);
          return;
        }
        console.log("files", files)
        let j = 0;
        for(var i=0; i < imgsForUpload.length; i++){
          if(imgsForUpload[i].is_upload == true){
            imgsForUpload[i].img_upload = files[j].file_name; 
            j++;
          }
        } 
        let imgsCCBS = imgsForUpload.map(x=>{
          if(x.is_upload) {
            return x.img_upload;
          }
          else
            return "";
        });
        console.log("imgsCCBS", imgsCCBS);
        let req = {
          "loai_anh": 1,
          "loai_kh": this.state.loai_kh,
          "lydoanh_id": 0,
          "ma_hd": this.state.ma_hd,
          "ma_kh": this.state.ma_kh,
          "so_tb": "84" + this.state.vma_tb,
          "gt_imgs": imgsCCBS
        }
        this.GetData(await api.upload_img_post(this.axios, req)); 
        this.$toast.success("Tải ảnh lên thành công"); 
        j = 0;
        for(var i=0; i < imgsForUpload.length; i++){
          if(imgsForUpload[i].is_upload == true){
            imgsForUpload[i].is_upload = false; 
            j++;
          }
        }
        //this.$refs.dialogChonAnhHoSo.hide()
        return true; 
    },
    
    async tsbtnTraCuu_Click(){
      this.loading(true);
      await this.pageLoad();
      this.loading(false);
    },
    async validateAndUpload(file){  
      return new Promise((resolve, reject) => {
        var URL = window.URL || window.webkitURL;
        let img = new Image()
        img.onload = () => { 
          resolve(img.height)
        }
        img.onerror = () => {
          this.$toast.error("Bạn vui lòng chọn đúng file ảnh")
          reject
        }
        img.src = URL.createObjectURL(file);
      }) 
    },
    async changeFileUpload1(e){
      let imgFile = e.target.files[0];
      try{
        console.log('changeFileUpload1', e.target.id)
        console.log('files', e.target.files)  
        let reader; 
        await this.validateAndUpload(imgFile); 
        console.log(this.state.dsIdLoaiAnhCaNhan)
         console.log(this.state.dsIdLoaiAnhCaNhan)
        for(var i=0; i < this.state.dsIdLoaiAnhCaNhan.length; i++) {
          let x1 = this.state.dsIdLoaiAnhCaNhan[i];
          if (x1.upload_img_id == e.target.id && imgFile)  { 
            console.log("set image upload: ", e.target.id)
            console.log(x1.upload_img_id)
            reader = new FileReader
            reader.onload = e => {
              x1.imgPreview = e.target.result
            }
            reader.readAsDataURL(imgFile);
            x1.img_upload = imgFile;  
            x1.is_upload = true;
          }
          if (x1.upload_img_id == 'upload_img_id_'+(6+i).toString() && imgFile)  { 
            console.log("set image upload: ", e.target.id)
            console.log(x1.upload_img_id)
            reader = new FileReader
            reader.onload = e => {
              x1.imgPreview = e.target.result
            }
            reader.readAsDataURL(imgFile);
            x1.img_upload = imgFile;  
            x1.is_upload = true;
          }
        }
        for(var i=0; i < this.state.dsIdLoaiAnhHopDong.length; i++) {
          let x1 = this.state.dsIdLoaiAnhHopDong[i];
          if (x1.upload_img_id == e.target.id && imgFile)  { 
            console.log("set image upload: ", e.target.id)
            console.log(x1.upload_img_id)
            reader = new FileReader
            reader.onload = e => {
              x1.imgPreview = e.target.result
            }
            reader.readAsDataURL(imgFile);
            x1.img_upload = imgFile;  
            x1.is_upload = true;
          }
        }
        
        console.log("this.state.dsIdLoaiAnhCaNhan", this.state.dsIdLoaiAnhCaNhan)
        console.log("this.state.dsIdLoaiAnhHopDong", this.state.dsIdLoaiAnhHopDong)
        setTimeout(()=>{this.$forceUpdate();}, 200) 
      }
      catch(e){
        commonFn.showException(this, e);
      }
      for(var i=0; i < this.state.dsIdLoaiAnhHopDong.length; i++) {
        let x1 = this.state.dsIdLoaiAnhHopDong[i];
        if (x1.upload_img_id == e.target.id && imgFile && imgFile.length > 0)  { 
          console.log("set image upload: ", e.target.id)
          reader = new FileReader
          reader.onload = e => {
            x1.imgPreview = e.target.result
          }
          reader.readAsDataURL(imgFile[0]);
          x1.img_upload = imgFile[0];  
        }
      }
       
      console.log("this.state.dsIdLoaiAnhCaNhan", this.state.dsIdLoaiAnhCaNhan)
      console.log("this.state.dsIdLoaiAnhHopDong", this.state.dsIdLoaiAnhHopDong)
      setTimeout(()=>{this.$forceUpdate();}, 200) 
    },
    async changeFileUpload(e){
      console.log('changeFileUpload', e.target.id)
      let fileInput;
      let imgFile;
      let reader;
      switch(e.target.id){  
        case "upload-file-1":
          fileInput = this.$refs.file1;
          imgFile = fileInput.files
          if (imgFile && imgFile[0]) {
            reader = new FileReader
            reader.onload = e => {
              this.filePreview1 = e.target.result
            }
            reader.readAsDataURL(imgFile[0]);
            this.state.img1 = imgFile[0];
          }
          break;
        case "upload-file-2":
          fileInput = this.$refs.file2; 
          imgFile = fileInput.files
          if (imgFile && imgFile[0]) {
            reader = new FileReader
            reader.onload = e => {
              this.filePreview2 = e.target.result
            }
            reader.readAsDataURL(imgFile[0]);
            this.state.img2 = imgFile[0];
          }
          break;
        case "upload-file-3":
          fileInput = this.$refs.file3;
          imgFile = fileInput.files
          if (imgFile && imgFile[0]) {
            reader = new FileReader
            reader.onload = e => {
              this.filePreview3 = e.target.result
            }
            reader.readAsDataURL(imgFile[0]);
            this.state.img3 = imgFile[0];
          }
          break;
        case "upload-file-4":
          fileInput = this.$refs.file4;
          imgFile = fileInput.files
          if (imgFile && imgFile[0]) {
            reader = new FileReader
            reader.onload = e => {
              this.filePreview4 = e.target.result
            }
            reader.readAsDataURL(imgFile[0]);
            this.state.img4 = imgFile[0];
          }
          break;
        case "upload-file-ts-1":
          fileInput = this.$refs.filets1;
          imgFile = fileInput.files
          if (imgFile && imgFile[0]) {
            reader = new FileReader
            reader.onload = e => {
              this.filePreviewts1 = e.target.result
            }
            reader.readAsDataURL(imgFile[0]);
            this.state.imgts1 = imgFile[0];
          }
          break;
        case "upload-file-ts-2":
          fileInput = this.$refs.filets2;
          imgFile = fileInput.files
          if (imgFile && imgFile[0]) {
            reader = new FileReader
            reader.onload = e => {
              this.filePreviewts2 = e.target.result
            }
            reader.readAsDataURL(imgFile[0]);
            this.state.imgts2 = imgFile[0];
          }
          break;
        case "upload-file-ts-3":
          fileInput = this.$refs.filets3;
          imgFile = fileInput.files
          if (imgFile && imgFile[0]) {
            reader = new FileReader
            reader.onload = e => {
              this.filePreviewts3 = e.target.result
            }
            reader.readAsDataURL(imgFile[0]);
            this.state.imgts3 = imgFile[0];
          }
          break;
        case "upload-file-ts-4":
          fileInput = this.$refs.filets4;
          imgFile = fileInput.files
          if (imgFile && imgFile[0]) {
            reader = new FileReader
            reader.onload = e => {
              this.filePreviewts4 = e.target.result
            }
            reader.readAsDataURL(imgFile[0]);
            this.state.imgts4 = imgFile[0];
          }
          break;
      }  
    },
    GetData: function (response) { 
      if (response.data.error_code != 'BSS-00000000') {
        throw response.data.message;
      }
      if (response.data.error === 200 || response.data.error === '200') { 
        return response.data.data
      } else {
        console.log(response.data.error_code)
        throw response.data.message;
      }
      return null
    },
    
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }, 
}
</script>
<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>