<template src="./TransferIncurredDebt.html"></template>
<style scoped src="./TransferIncurredDebt.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import gridview from '@/components/Shared/gridview'
import TransferIncurredDebtAPI from './TransferIncurredDebtAPI'
import moment from 'moment'
export default {
  components: { breadcrumb,gridview },
  name: 'TransferIncurredDebt',
  props: [ 'isPopup' ],
  mounted () {
    //this.KyHDChanged(null,moment(new Date()).subtract(1,"months").format("YYYYMM"));
    this.ky_hoa_don.value = moment(new Date()).subtract(1,"months").format("YYYYMM");
    this.KyHDChanged(null,moment(new Date()).subtract(1,"months").format("YYYYMM"));
    TransferIncurredDebtAPI.post_sp_lay_ds_tieuchi(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.tieuchi.list = response.data.data;
            if(this.listbox.tieuchi.list && this.listbox.tieuchi.list.length>0) {
              if(this.listbox.tieuchi.value!=this.listbox.tieuchi.list[0].tieuchi_id) {
                this.listbox.tieuchi.value = this.listbox.tieuchi.list[0].tieuchi_id;
                this.LoadNo();
              }
            }
          }
          else this.$toast.error("Lỗi khi lấy thông tin: "+response.data.message);
        }
    ).catch(function(){
      console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
    });

    TransferIncurredDebtAPI.sp_lay_ds_dot_no(this.axios, {'vkyhoadon' : this.ky_hoa_don.value + '01'}).then(
      (res) => {
        if(res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000' && res.data.data) {
          this.listbox.dot.list = res.data.data ? res.data.data : []
          if (this.listbox.dot.list.length > 0) {
            this.listbox.dot.value = this.listbox.dot.list[0].id ? this.listbox.dot.list[0].id : 0
          }
        }
      }
    )
    // this.layThongTinChuyenNo()
  },
  destroyed () {},
  computed: {
    fky_hoa_don:{
      get() { 
        return moment(this.ky_hoa_don.value).format("MM/YYYY")
      },
      
    }
  },
  data () {
    return {
      ky_hoa_don: { 
        value: new Date(),
        dateconfig: {
          altFormat: 'm/Y',
          altInput: true,
          dateFormat: 'Ym',
          allowInput:true,
          onChange: this.KyHDChanged,
        },
        isEnabled: true,
      },
      header: {
        title: 'Chuyển nợ phát sinh',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
        ]
      },
      ghiChu: "",
      listbox: {
        chukyno: {
          list:[],
          value: "",
          isEnabled: true,
        },
        tieuchi: {
          list:[],
          value: 0,
        },
        dot: {
          list:[],
          value: 0,
          isEnabled: true,
        }
      },
      gridbox: {
        truocdonno: {
          list:[],
          header:[{name:'tenkm',text:'Tên khoản mục'},{name:'tongno',text:'Tổng nợ'},],
          cols: [
            {fieldName:'tenkm',headerText:'Tên khoản mục', allowFiltering: true, },
            {fieldName:'tongno',headerText:'Tổng nợ', allowFiltering: true, format:'N0', editType: 'numericedit', type: 'Number' },
          ],
          value: {},
        },
        saudonno: {
          list:[],
          header:[{name:'tenkm',text:'Tên khoản mục'},{name:'tongno',text:'Tổng nợ'},],
          cols: [
            {fieldName:'tenkm',headerText:'Tên khoản mục', allowFiltering: true, },
            {fieldName:'tongno',headerText:'Tổng nợ', allowFiltering: true, format:'N0', editType: 'numericedit', type: 'Number' },
          ],
          value: {},
        },
      },
      trangThaiChuyenNo : '',
      isShowStatus : false
    }
  },
  methods: {
    dotChanged() {
      this.LoadNo()
      console.log('dot')
    },
    KyHDChanged(obj,str) {
      let pdata = {
        "p_thangnam": str
      }
      this.loading(true);
      TransferIncurredDebtAPI.post_sp_lay_chukyno_theo_kyhd(this.axios,pdata).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.message) {
          this.listbox.chukyno.list = response.data.data;
          if(this.listbox.chukyno.list && this.listbox.chukyno.list.length>0)
          {
            for(var i=0;i<this.listbox.chukyno.list.length;i++)
              this.listbox.chukyno.list[i].kycuoc=str+this.listbox.chukyno.list[i].chuky;
            this.ky_hoa_don.isEnabled = false;
          }
          if(this.listbox.chukyno.list && this.listbox.chukyno.list.length==1) {
            this.listbox.chukyno.value=this.listbox.chukyno.list[0].kycuoc;
            this.listbox.chukyno.isEnabled = false;
          }
          else if(!this.listbox.chukyno.list || this.listbox.chukyno.list.length==0)
          {
            this.$toast.error('Không có thông tin về chu kỳ!');
            this.ghiChu += 'Không có thông tin về chu kỳ '+str+'!\r\n';
          }
        }
        else this.$toast.error("Lỗi khi lấy thông tin chu kỳ: "+response.data.message);
      }).catch(function(){
        this.$toast.error('Có lỗi xảy ra khi lấy thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
      }).finally(()=>{this.loading(false);this.LoadNo();});
    },
    ChuKyChanged()
    {
      this.listbox.chukyno.isEnabled = false;
      this.LoadNo();
    },
    LoadNo() {
      if(!this.listbox.tieuchi.value || this.listbox.tieuchi.value<=0 || !this.listbox.chukyno.value || this.listbox.chukyno.value<=0) return;
      let pdata = {
        "p_kieu": this.listbox.tieuchi.value,
        "p_kycuoc": this.listbox.chukyno.value,
        "p_loai": 1,
        "p_dot" : this.listbox.dot.value ? this.listbox.dot.value : 0
      }
      this.loading(true);
      var api1 = TransferIncurredDebtAPI.post_fn_lay_ds_ketqua_donno_ps(this.axios,pdata).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.message && response.data.data.is_ok=="1") {
          if(response.data.data.ds.length>0) {
            this.gridbox.truocdonno.list = response.data.data.ds;
            this.gridbox.truocdonno.header = [{name:'tenkm',text:'Tên khoản mục'},{name:'tongno',text:'Tổng nợ'},]
          }
          else {
            //this.$toast.error('Không có nợ trước khi dồn');
            this.gridbox.truocdonno.list = [];
            this.ghiChu += 'Không có nợ trước khi dồn cho kỳ cước '+this.listbox.chukyno.value+'!\r\n';
          }
        }
        else this.$toast.error("Lỗi khi lấy thông tin trước khi dồn nợ: "+response.data.message);
      }).catch(function(){
        this.$toast.error('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
      });
      pdata.p_loai = 2;
      var api2 = TransferIncurredDebtAPI.post_fn_lay_ds_ketqua_donno_ps(this.axios,pdata).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.message && response.data.data.is_ok=="1") {
          if(response.data.data.ds.length>0) {
            this.gridbox.saudonno.list = response.data.data.ds;
            this.gridbox.saudonno.header = [{name:'tenkm',text:'Tên khoản mục'},{name:'tongno',text:'Tổng nợ'},]
          }
          else {
            //this.$toast.error('Không có nợ trước khi dồn');
            this.gridbox.saudonno.list = [];
            this.ghiChu += 'Không có nợ sau khi dồn cho kỳ cước '+this.listbox.chukyno.value+'!\r\n';
          }
        }
        else this.$toast.error("Lỗi khi lấy thông tin sau khi dồn nợ: "+response.data.message);
      }).catch(function(){
        this.$toast.error('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
      });
      Promise.all([api1,api2]).then().finally(()=>{ this.loading(false); })
    },
    async layThongTinChuyenNo() {
      this.loading(true)
      this.isShowStatus = true
      let input = {
        "vkyhoadon": this.listbox.chukyno.value,
        "vdot" : this.listbox.dot.value ? this.listbox.dot.value : 0
      }
      TransferIncurredDebtAPI.fn_lay_tt_chuyenno_ps(this.axios,input).then((response) => {
        if(response) {
          if(response.data.error_code === 'BSS-00000000'){
            // this.$toast.success('Đã thực hiện xong quá trình chuyển nợ !');
            // this.ghiChu += "Đã thực hiện xong quá trình chuyển nợ !" + "\r\n";
            this.trangThaiChuyenNo = response.data.data ? response.data.data.replace(/\\r\\n/g, '<br />') : ''
          }
          else {
            this.$toast.error(response.data.message);
            // this.ghiChu += response.data.data + "\r\n";
          }
        }
        else this.$toast.error("Có lỗi trong quá trình tra cứu : \n: "+response.data.message);
      }).catch(function(){
        vm.$toast.error('Có lỗi xảy ra khi tra cứu thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
      }).finally(()=>{ this.loading(false); });
    },
    async ChuyenNo() {
      this.ghiChu = "";
      var pdata = {
        "vkyhoadon": this.listbox.chukyno.value,
        "vdot" : this.listbox.dot.value ? this.listbox.dot.value : 0
      }
      // this.$toast.success('Đã tiến hành chuyển nợ, để xem thông tin chuyển nợ hãy ấn nút Tra Cứu');
      this.$bvModal
      .msgBoxConfirm(
        `Đã tiến hành chuyển nợ, để xem thông tin chuyển nợ hãy ấn nút Tra Cứu`,
        {
          title: '',
          size: 'sm',
          okTitle: 'Oke',
        }
      )
      this.loading(true);
      var vm = this;
      // TransferIncurredDebtAPI.post_fn_chuyeno_phatsinh(this.axios,pdata).then((response) => {
      //   if(response) {
      //     if(response.data.error_code === 'BSS-00000000'){
      //       this.$toast.success('Đã thực hiện xong quá trình chuyển nợ !');
      //       this.ghiChu += "Đã thực hiện xong quá trình chuyển nợ !" + "\r\n";
      //     }
      //     else {
      //       this.$toast.error(response.data.message);
      //       this.ghiChu += response.data.data + "\r\n";
      //     }
      //   }
      //   else this.$toast.error("Có lỗi trong quá trình chuyển nợ : \n: "+response.data.message);
      // }).catch(function(){
      //   vm.$toast.error('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
      // }).finally(()=>{ this.loading(false); });
      
      try {
        let res = await this.axios.post('/web-quantri/chuyennops/fn_chuyeno_phatsinh', pdata)
        if (res) {
          if(res.data.error_code === 'BSS-00000000'){
            // this.$toast.success('Đã thực hiện xong quá trình chuyển nợ !');
            this.$bvModal
            .msgBoxConfirm(
              `Đã thực hiện xong quá trình chuyển nợ !`,
              {
                title: '',
                size: 'sm',
                okTitle: 'Oke',
              }
            )
            this.ghiChu += "Đã thực hiện xong quá trình chuyển nợ !" + "\r\n";
          }
          else {
            // let mess = res.data.message_detail ? res.data.message_detail : res.data.message
            // console.log('mess is ', mess)
            // this.$toast.error(res.data.message_detail ? res.data.message_detail : res.data.message);
            this.$bvModal
            .msgBoxConfirm(
              res.data.message_detail ? res.data.message_detail : res.data.message,
              {
                title: '',
                size: 'sm',
                okTitle: 'Oke',
              }
            )
            this.ghiChu += res.data.data + "\r\n";
          }
          this.loading(false)
        }
      } catch (error) {
        // this.$toast.error(error.data.message_detail ? error.data.message_detail : error.data.message);
        this.$bvModal
        .msgBoxConfirm(
          error.data.message_detail ? error.data.message_detail : error.data.message,
          {
            title: '',
            size: 'sm',
            okTitle: 'Oke',
          }
        )
        this.loading(false)
      }
    },
    CloseForm() {
      this.$emit('OnClosed');
    },
  }
}
</script>
