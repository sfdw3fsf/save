<template src="./SwitchboardCommand.html"></template>
<style scoped src="./SwitchboardCommand.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'
import SwitchboardCommandAPI from './SwitchboardCommandAPI'
// import moment from 'moment'
export default {
  components: { breadcrumb, },
  name: 'SwitchboardCommand',
  mounted () {
    Split(['#boxLeft', '#boxRight'], {
      sizes: [20, 80],
      gutterSize: 16,
      onDragEnd: function (sizes) {

      },
    });
  },
  destroyed () {},
  computed: {
  },
  data () {
    return {
      tongdai: 399,
      lenh: "",
      memolenh: "",
      memoketqua: "",
      isXoaKQ: false,
      header: {
        title: 'Thực thi lệnh tổng đài',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
        ]
      },
    }
  },
  methods: {
    async ThucThiLenh() {
      var taplenh = this.lenh.replaceAll('\r\n','').replaceAll('\n','').split(';');
      var tapkq = "";
      var sent = 0;
      var done = 0;
      console.log(taplenh);
      for(var l of taplenh)
        if(l && l.trim().length>0)
        {
          sent++;
          var podata = {
            "TongDai": this.tongdai,
            "_sCommand": l.trim()+";",
          }
          this.loading(true);
          this.memolenh += l +";\r\n";
          
          let resp = await SwitchboardCommandAPI.post_ThucThiLenh(this.axios,podata).then((response) => {
            var ketqua = response.data.message;
            if(response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              if(response.data.data && response.data.data.ThucThiLenhResult)
                ketqua = response.data.data.ThucThiLenhResult;
            }
            else tapkq += ketqua;
            var temp = l+"\r\n>\r\n";
            temp += "========================================================================\r\n\r\n";
            temp += ketqua.replace("\n", "\r\n");
            temp += "\r\n\r\n========================================================================\r\n\r\n";
            this.memoketqua = temp+this.memoketqua;
            let pdata = {
              "list": [
                {
                  "ip_cn": null,
                  "ketqua": ketqua,
                  "lenh": l+";",
                  "ngay_cn": null,
                  "nguoi_cn": null
                }
              ],
            }
            SwitchboardCommandAPI.post_insert_log_tongdai(this.axios,pdata).then((response) => {
              tapkq = "Không ghi được log lệnh lên API!";
              //console.log("Ghi log thanh cong!");
            });
          });
      }
      if(tapkq) alert(tapkq);
      else alert('Thực thi lệnh thành công!');
      this.loading(false);
      this.lenh = "";
    },
    XoaKetQua() {
      if(this.isXoaKQ) {
        this.memoketqua = "";
        this.isXoaKQ=false;
      }
    },
  }
}
</script>
