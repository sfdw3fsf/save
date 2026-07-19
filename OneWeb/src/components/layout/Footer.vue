<template>
  <footer class='footer'>
    <a class="img" @dblclick="Report">
      <img @dblclick="linkToCCBS" src="@/assets/img/logo.svg" />
    </a>
    Công ty Công nghệ thông tin VNPT (VNPT-IT) &copy; 2021 | <i>User: {{citycode?citycode:$root.token.getMaTinh()}} - {{userName?userName:$root.token.getUserName()}}</i>
  </footer>
</template>
<script>
export default {
  name: 'Footer',
  props: {
    userName: "",
    citycode: ""
  },
  mounted() {
    // let bugpilotscript = document.createElement('script');
    // bugpilotscript.setAttribute('src', 'https://script.bugpilot.io/485d681a-6299-4b92-b2f2-a09b74410d80/adopto.js');
    // bugpilotscript.setAttribute("async","");
    // document.head.appendChild(bugpilotscript);
  },
  methods: {
    Report: async function () {
      if(window && window.Bugpilot) {
        window.Bugpilot.identify({
          id: this.$root.token.getNhanVienID(), // Required
          email: this.$root.token.getUserName(), // Required
          province: this.$root.token.getMaTinh(),
        });
        window.Bugpilot.saveReport({
          source: window.location.href?window.location.href:"Bao loi chung", // Optional
          //userProvidedDescription: "Lap Dat Moi Ban Cheo", // Optional
        });
        this.$toast.warning("Ok!");
      }
    },
    linkToCCBS: async function() {
      //get otp
      try {
        this.loading(true)
        let res = await this.axios.get('/tichhop/ccbs/createOTP',{})
      if(res.data && res.data.errorCode == 0) {
        let otp = res.data.data;
        let url = `https://dev-ccbs.vnpt.vn/#/auth/oneauth?auth=${otp}`;
        window.open(url, '_blank');
      }
      else 
        this.$toast.error('Lỗi kết nối hệ thống CCBS');
      } catch (error) {
        
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>
<style scoped>
footer {
  color:#fff;
  background-color:#035FCC;
  font-weight:bold;
}
footer img { height:17px; }
</style>