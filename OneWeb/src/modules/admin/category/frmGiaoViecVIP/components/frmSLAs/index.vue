<script>
import CommentBox from './components/CommentBox.vue'
import api from '../../api'
import { RichTextEditorComponent, Toolbar, Link, Image, Count, HtmlEditor, QuickToolbar } from '@syncfusion/ej2-vue-richtexteditor';
export default {
  name: 'frmSLAs',
  props: ['dulieu'],
  components: {
    'ejs-richtexteditor': RichTextEditorComponent,
    'comment-box': CommentBox,
  },
  provide: {
    richtexteditor: [Toolbar, Link, Image, Count, HtmlEditor, QuickToolbar]
  },
  data() {
    return {
      sla_time: null,
      ngaygiao: null,
      diff_in_minutes: null,
      duration: {
        seconds: null,
        minutes: null,
        hours: null,
        total: null,

      },
      interval_CountDown: null,
      status_list: ['open', 'resolved', 'outdate'],
      status: 'open',

      editorSetting: {
        height: 400,
        quickToolbarSettings: {
          image: [
            'Replace', 'Align', 'Caption', 'Remove', 'InsertLink', 'OpenImageLink', '-',
            'EditImageLink', 'RemoveImageLink', 'Display', 'AltText', 'Dimension',
          ],
          link: ['Open', 'Edit', 'UnLink']
        },
        toolbarSettings: {
          items: ['Bold', 'Italic', 'Underline', 'StrikeThrough',
            'FontName', 'FontSize', 'FontColor', 'BackgroundColor',
            'LowerCase', 'UpperCase', '|',
            'Formats', 'Alignments', 'OrderedList', 'UnorderedList',
            'Outdent', 'Indent', '|',
            'CreateLink', 'Image', '|', 'ClearFormat', 'Print',
            'SourceCode', 'FullScreen', '|', 'Undo', 'Redo']
        },
        show: true
      },
      list_messageComments: []
    }
  },
  async mounted() {
    console.log(this.dulieu)
    await this.lay_thoigian_sla_theo_hdtbid()
    // this.countDown()
    this.editorSetting.show = false
  },
  methods: {
    formatTime(total) {
      let absSeconds = Math.abs(total);
      let hours = Math.floor(absSeconds / 3600);
      let minutes = Math.floor((absSeconds % 3600) / 60);
      let secs = absSeconds % 60;

      this.duration.hours = total < 0 ? -hours : hours;  // Hiển thị giá trị âm nếu là thời gian âm
      this.duration.minutes = minutes; // minutes: minutes,
      this.duration.seconds = secs; // seconds: secs

      if(total < 0) {
        this.status = this.status_list[2]
      }
    },

    countDown(total) {
      this.interval_CountDown = setInterval(() => {
        // console.log(total)
        // Chuyển đổi số giây còn lại thành giờ, phút, giây
        this.formatTime(total);
        // console.log('test')
        // console.log(`${this.duration.hours}:${this.duration.minutes}:${this.duration.seconds}`);

        // Cập nhật giao diện người dùng (nếu cần)
        // document.getElementById('timer-display').textContent = `${time.hours}:${time.minutes}:${time.seconds}`;

        // Nếu total là 0 hoặc nhỏ hơn, tiếp tục trừ để chuyển sang âm
        total--;

      }, 1000); // Cập nhật mỗi giây (1000 ms)
    },
    stopCountDown() {
      // Dừng interval nếu nó đang chạy
      if (this.interval_CountDown) {
        clearInterval(this.interval_CountDown);
        console.log('Interval đã ngắt');
      }
    },

    async lay_thoigian_sla_theo_hdtbid() {
      this.stopCountDown()

      try {
        let rs = await api.lay_thoigian_sla_theo_hdtbid({
          "hdtb_id": this.dulieu.hdtb_id,
          "phanvung_id": this.dulieu.tinh_id
        })
        let data = JSON.parse(rs.data.data)
        this.sla_time = data.sla_time
        this.ngaygiao = data.ngaygiao
        let sla_time = Math.floor(Number(data.sla_time));               // Chuyển đổi và làm tròn xuống
        let diff_in_second = Math.floor(Number(data.diff_in_second));
        let duration = (sla_time * 60)  - diff_in_second;
        this.countDown(duration)
      }
      catch (e) {
        console.log(e)
      }
      finally {

      }
    },

    async sendMessage() {
      console.log(this.$refs.rteObj.getHtml())
      try {

        let content = this.$refs.rteObj.getHtml()
        let objMess = {
          phanvung_id: this.dulieu.tinh_id,
          hdtb_id: this.dulieu.hdtb_id,
          phieu_id: 123,
          donvi_id: 456,
          loaidv_id: 678,
          ten_donvi: "Trung tâm giải pháp Tài nguyên môi trường và nông nghiệp - Công ty Công nghệ thông tin VNPT",
          ten_loaidv: "Trung tâm giải pháp",
          noidung: content,
          nguoidung_id: 10016758,
          ten_nd: "Nguyễn Văn A",

        }

        //send message
        //...

        await this.loadMessage()
        this.fakeMessage(objMess)

      }
      catch (e) {
        console.log(e)
      }
      finally {

      }
    },
    async loadMessage() {
      try {
        //load message
        //...

      }
      catch (e) {

      }
      finally {

      }
    },
    fakeMessage(obj) {
      this.list_messageComments.push(obj)
    },

  }
}
</script>

<template>
  <div>
<!--    <button @click="test">est</button>-->
    <div class="popup-body">
      <div class="box-form">
        <div class="sla-view-issue-container p-3 border rounded bg-light">
          <div class="d-flex align-items-center mb-3">
            <span class="fw-bold">Thời gian còn:</span>
            <span class="ms-2 text-primary fs-5">{{ duration.hours }}:{{ duration.minutes }}h</span>
            <i class="nc-icon-glyph ui-2_time ms-2" :style="[this.status == 'open' ? {'color': 'bae7ff'} : {'color': 'da3545'} ]"></i>
          </div>
          <div class="sla-info mb-2">
            <span class="fw-bold">SLA - Thời hạn xử lý phiếu:</span>
            <span class="ms-2">{{sla_time / 60}}h</span>
            <i class="nc-icon-glyph ui-1_calendar-grid-58 ms-2"></i>
          </div>
          <div class="created-info">
            <span class="fw-bold">Created:</span>
            <span class="ms-2">{{ngaygiao}}</span>
          </div>
        </div>
      </div>

      <div class="box-form">
        <comment-box :messages="list_messageComments"/>
        <div class="button-comment mb-1">
          <button type="button"
                  :class="{disabled: editorSetting.show}"
                  class="btn btn-outline-primary"
                  @click="editorSetting.show = !editorSetting.show"
          >Comment</button>
        </div>
        <div class="editor-container richtext-container" v-if="editorSetting.show">
          <ejs-richtexteditor ref="rteObj" :quickToolbarSettings="editorSetting.quickToolbarSettings" :height="300"
                              :toolbarSettings="editorSetting.toolbarSettings">
          </ejs-richtexteditor>
          <div class="mt-1">
            <button @click="sendMessage" id="validateSubmit" class="btn btn-outline-primary" data-ripple="true">Send</button>
            <button @click="editorSetting.show = !editorSetting.show" id="validateSubmit" class="btn btn-outline-primary" data-ripple="true">Cancel</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss">
/* Chỉ áp dụng các styles của Syncfusion bên trong class .richtext-container */

.richtext-container {
  :global {
    @import "@syncfusion/ej2-popups/styles/material.css";
    @import "@syncfusion/ej2-vue-richtexteditor/styles/material.css";
    @import "@syncfusion/ej2-base/styles/material.css";
    @import "@syncfusion/ej2-buttons/styles/material.css";
  }
}

.sla-view-issue-container {
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 5px;
}

.sla-info,
.created-info {
  font-size: 1rem;
  color: #495057;
}

.sla-info i,
.created-info i {
  color: #6c757d;
}

.sla-view-issue-container i {
  font-size: 1.2rem;
}

.sla-view-issue-container .text-primary {
  font-weight: bold;
}

</style>
