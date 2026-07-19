<template>
    <div  style="position: relative; width: 100%; max-width: 600px; margin: 1.75rem auto;">
        <div class="popup-body mart10">
            <div class="info-row">
                <div class="key w60">
                    User
                </div>
                <div class="value">
                    <input type="text" class="form-control" v-model="username">
                </div>
            </div>
            <div class="info-row">
                <div class="key w60">
                    Password
                </div>
                <div class="value">
                    <input type="text" class="form-control" v-model="pass" >
                </div>
            </div>
            <div class="group-buttons -bottom tright">
                <button class="btn btn-main" v-on:click="btn_login_click">OK</button>
                <button class="btn btn-second">Cancel</button>
            </div>
        </div>
    </div>

 
</template>

<script>

export default {
  mounted () {
   
  },
  //end mounted
  destroyed () {},
  data () {
    return {
        username: "",
        pass: ""
        }
  },



  methods:{

    btn_login_click(){
      
        var user = {"username":this.username,"pass":this.pass };

        this.$emit('login',user);
    },

  }
}
</script>
