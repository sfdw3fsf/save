<template>
<div>
    <div class="popup-header"> 
        <div class="title"><span class="icon one-notepad"></span> Xác nhận người dùng thật</div> 
        <div class="close -ap icon-close" data-dismiss="modal" @click="closeForm"> 
        </div> 
    </div> 
    <div class="popup-body">
        <div class="row">
            <div id="captcha" class="col-sm-3 col-3">
            </div>
            <div class="col-sm-6 col-6">
                <button @click.prevent="createCaptcha">Tạo mã mới</button>
            </div>
        </div>    
        <div class="row">
            <div>Nhập mã:
                <input type="text" placeholder="Captcha" v-model="cpatchaTextBox"/>
                <button @click.prevent="validateCaptcha">Xác nhận</button>
                
            </div>
        </div>
    </div> 
</div>
</template>
<style scoped>
#captcha {
    width:120px;
}
input[type=text] {
    padding: 12px 20px;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}
button{
  background-color: #4CAF50;
    border: none;
    color: white;
    padding: 12px 30px;
    text-decoration: none;
    margin: 4px 2px;
    cursor: pointer;
}
canvas{
  /*prevent interaction with the canvas*/
  pointer-events:none;
}
</style>
<script>
// import API from '../InstallNewSubsAPI'
export default {
    name: 'popupCaptcha',
    // props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            cpatchaTextBox: '',
            code: '',
            valid: false,
        }
    },
    mounted() {
        this.createCaptcha();
    },
    computed: {
    },
    methods: {
        closeForm() {
            this.acceptForm();
        },
        acceptForm() {
            this.$emit('form-close',this.valid);
        },
        validateCaptcha() {
            if (this.cpatchaTextBox == this.code) {
                // this.$toast.success("Valid Captcha");
                this.valid = true;
                this.acceptForm();
            }else{
                this.$toast.error("Sai captcha, vui lòng thử lại!");
                this.createCaptcha();
            }
        },
        createCaptcha() {
            this.valid = false;
            this.cpatchaTextBox = "";
            //clear the contents of captcha div first 
            document.getElementById('captcha').innerHTML = "";
            var charsArray =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@!#$%^&*";
            var lengthOtp = 6;
            var captcha = [];
            for (var i = 0; i < lengthOtp; i++) {
                //below code will not allow Repetition of Characters
                var index = Math.floor(Math.random() * charsArray.length + 1); //get the next character from the array
                if (captcha.indexOf(charsArray[index]) == -1)
                captcha.push(charsArray[index]);
                else i--;
            }
            var canv = document.createElement("canvas");
            canv.id = "captcha";
            canv.width = 120;
            canv.height = 50;
            var ctx = canv.getContext("2d");
            ctx.font = "25px Georgia";
            ctx.strokeText(captcha.join(""), 0, 30);
            //storing captcha so that can validate you can save it somewhere else according to your specific requirements
            this.code = captcha.join("");
            document.getElementById("captcha").appendChild(canv); // adds the canvas to the body element
        },
    }
}
</script>