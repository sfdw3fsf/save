<script>
import moment from 'moment/moment'

export default {
  name: 'comment-box',
  props: ['messages'],
  data() {
    return {
      tt_nd: {
        nhanvien_id:  this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        ngay_cn: moment(new Date()).format("DD/MM/YYYY hh:mm A"),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
          "DD/MM/YYYY hh:mm A"
        ),
        ten_dv: "",
        nguoi_cn: this.$root.token.getUserName()
      },
    }
  },
  computed: {
    lenght_Message() {
      return this.messages.length
    }
  }
}
</script>

<template>
  <section>
    <div class="py-5">
      <div class="row d-flex justify-content-center">
        <div class="col-md-12 col-lg-10">
          <div class="card text-body">
            <div style="text-align: center;" v-show="lenght_Message == 0">
              There are no comments yet on this issue.
            </div>

            <div class=""v-for="(msg, idx) in messages">
              <div class="card-body p-4" :style="{ backgroundColor: msg.nguoidung_id === tt_nd.nguoidung_id ? '#f1f1f1' : 'transparent' }">
                <!--              <h4 class="mb-0">Recent comments</h4>-->
                <!--              <p class="fw-light mb-4 pb-2">Latest Comments section by users</p>-->

                <div class="d-flex flex-start">
<!--                  <img class="rounded-circle shadow-1-strong me-3"-->
<!--                       src="https://mdbcdn.b-cdn.net/img/Photos/Avatars/img%20(23).webp" alt="avatar" width="60"-->
<!--                       height="60" />-->
                  <div>
                    <h6 class="fw-bold mb-1">{{msg.ten_nd}}</h6>
                    <div class="d-flex align-items-center mb-3 space-between">
                      <p class="mb-0" >
                        1/1/2024
                        <!--                      <span class="badge bg-primary">Pending</span>-->
                      </p>
<!--                      <a href="#!" class="link-muted ml-1"><i class="nc-icon-glyph ui-1_pencil"></i></a>-->
<!--                      <a href="#!" class="link-muted ml-1"><i class="nc-icon-glyph ui-1_trash"></i></a>-->
                    </div>
                    <p class="mb-0" v-html="msg.noidung">
                    </p>
                  </div>
                </div>
              </div>

              <hr class="my-0" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>

</style>
