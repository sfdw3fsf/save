<template >
    <div>
        <div class="breadcrumb-top">
            <div class="main-title">Hủy đặt chỗ account visa</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Lập hợp đồng </a></li>
                <li class="breadcrumb-item"><a href="#">Đồng bộ dữ liệu</a></li>
                <li class="breadcrumb-item active">Hủy đặt chỗ Account Visa</li>
            </ul>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="#" @click.prevent="btn_cancel_visa_click">
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy ĐC
                    </a>
                </li>
            </ul>
        </div>
        <div class="page-content">
            <div class="box-form">
                <div class="info-row">
                    <div class="key w60" style="font-weight:bold;">
                        Account
                    </div>
                    <div class="value">
                        <input type="text" ref = "txt_acc" class="form-control highlight" v-model="acc_visa" >
                    </div>
                </div>
            </div>
        </div>
        
        <footer class="footer">
            VNPT-IT &copy; 2021
        </footer>
    </div>
 
</template>


<script>

export default {
  mounted () {
    this.$refs.txt_acc.focus();
     
  },
  //end mounted
  destroyed () {},
  data () {
    return{
        acc_visa:""
    }
  },



  methods:{

    btn_cancel_visa_click(){

        this.$emit('cancel_visa_click',this.acc_visa);
    },

  }
}
</script>