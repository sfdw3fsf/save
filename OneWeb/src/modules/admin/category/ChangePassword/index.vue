<template>
  <div class='main-wrapper'>
    <div class='breadcrumb-top'>
      <div class='main-title'>ĐỔI MẬT KHẨU</div>
      <ul class='breadcrumb'>
        <li class='breadcrumb-item'>
          <a href='#'
            ><span class='nc-icon-outline ui-1_home-simple'></span> Trang chủ
          </a>
        </li>
        <li class='breadcrumb-item'><a href='#'>Lập hợp đồng</a></li>
        <li class='breadcrumb-item active'>Lắp đặt mới</li>
      </ul>
    </div>
    <hr class="hr">

    <div class='page-content' style='top: 90px'>
      <form @submit.prevent='submitRegistration'>
        <div class='note marb10 text-main fw6'>
          <span class='one-alert f20 text-warning inline vcenter'></span> <span class="text-alert">Mật
          khẩu tối thiểu 8 ký tự, phải bao gồm chữ, số và ký tự đặc biệt</span>
        </div>
        <div class='form-group'>
        <div :style="{display: 'flex', justifyContent: 'space-between'}">
          <label>Mật khẩu cũ</label>
          <div style="position: relative;"></div>
          <input
            type='password'
            class='form-control input-change-pass'
            placeholder='Mật khẩu cũ'    autofocus
            value=''
            v-model='user.txtMatkhauCu'
            autocomplete='off'

          />
          <!-- <button @click="toggleOldPasswordVisibility" class="toggle-password-button">
          <i class="fas" :class="showPasswordcu ? 'fa-eye-slash' : 'fa-eye'"></i>
        </button> -->
          </div>
          <div
            v-if='this.submitted && $v.user.txtMatkhauCu.$error'
            class='invalid-feedback bottom'
          >
            <span v-if='!$v.user.txtMatkhauCu.required'
              >Vui lòng nhập mật khẩu cũ</span
            >
            <span v-else-if='user.txtMatkhauCu && !$v.user.txtMatkhauCu.minLength'
              >Mật khẩu phải có ít nhất 8 ký tự</span
            >
          </div>
        </div>
        <div class='form-group'>
        <div :style="{display: 'flex', justifyContent: 'space-between'}">
          <label>Mật khẩu mới</label><input
            type='password'
            class='form-control input-new-pass'
            placeholder='Mật khẩu mới'
            value=''
            v-model='user.txtMatKhauMoi'
            autocomplete='off'
          />
          <!-- <button @click="toggleNewPasswordVisibility">
            <i class="fas" :class="showNewPassword ? 'fa-eye-slash' : 'fa-eye'"></i>
        </button> -->
          </div>
          <div
            v-if='this.submitted && $v.user.txtMatKhauMoi.$error'
            class='invalid-feedback bottom'
          >
            <span v-if='!$v.user.txtMatKhauMoi.required'
              >Vui lòng nhập mật khẩu mới</span
            >
            <span v-else-if='user.txtMatKhauMoi && !$v.user.txtMatKhauMoi.minLength'
              >Mật khẩu phải chứa ít nhất 8 kí tự</span
            >
            <span
              v-else-if='
                user.txtMatKhauMoi && !$v.user.txtMatKhauMoi.containsSpecial
              '
              >Mật khẩu phải chứa ít nhất một kí tự đặc biệt</span
            >
            <span
              v-else-if='user.txtMatKhauMoi && !$v.user.txtMatKhauMoi.containsNumber'
              >Mật khẩu phải chứa ít nhất một số</span
            >
            <span
              v-else-if='
                user.txtMatKhauMoi && !$v.user.txtMatKhauMoi.containsUppercase
              '
              >Mật khẩu phải chứa ít kí tự viết hoa</span
            >
            <span
              v-else-if='
                user.txtMatKhauMoi && !$v.user.txtMatKhauMoi.containsLowercase
              '
              >Mật khẩu phải chứa ít kí tự thường</span
            >
            <span
              v-else-if='
                user.txtMatKhauMoi && !$v.user.txtMatKhauMoi.containsForbidden
              '
              >Mật khẩu vi phạm ATTT</span
            >
          </div>
        </div>
        <div class='form-group'>
        <div :style="{display: 'flex', justifyContent: 'space-between'}">
          <label>Nhập lại</label><input
            type='password'
            class='form-control input-retype-pass'
            placeholder='Nhập lại mật khẩu mới'
            value=''
            v-model='user.txtNhapLai'
            autocomplete='off'
          />
          <!-- <button @click="toggleRetypePasswordVisibility">
            <i class="fas" :class="showRetypePassword ? 'fa-eye-slash' : 'fa-eye'"></i>
        </button> -->
          </div>
          <div
            v-if='this.submitted && $v.user.txtNhapLai.$error'
            class='invalid-feedback bottom'
          >
            <span v-if='!$v.user.txtNhapLai.required'
              >Nhập lại Mật khẩu mới</span
            >
            <span v-else-if='user.txtNhapLai && !$v.user.txtNhapLai.sameAsPassword'
              >Mật khẩu mới và Mật khẩu nhập lại phải giống nhau</span
            >
          </div>
        </div>
        <div class="footer-change-pass">
          <input type='submit' class='btnRegister' value='Chấp nhận' />
          <input type='button' class='btnRegister' style= 'color: black'        @click="redirectToHome" value='Kết thúc' />
        </div>
      </form>
    </div>
  </div>
</template>
<script>
import api from './api'
import {
  required,
  minLength
} from 'vuelidate/lib/validators'
import { Message } from 'element-ui'

export default {
  name: 'ChangePass',
  data () {
    return {
      showNewPassword: false,
      showPasswordcu: false,
      showRetypePassword: false,
      submitted: false,
      user: {
        txtMatkhauCu: '',
        txtMatKhauMoi: '',
        txtNhapLai: '',
      }
    }
  },
  validations: {
    user: {
      txtMatkhauCu: { required },
      txtMatKhauMoi: {
        required,
        minLength: minLength(8),
        containsUppercase: function (value) {
          return /[A-Z]/.test(value)
        },
        containsLowercase: function (value) {
          return /[a-z]/.test(value)
        },
        containsNumber: function (value) {
          return /[0-9]/.test(value)
        },
        containsSpecial: function (value) {
          return /[#?!@$%^&*-]/.test(value)
        },
        containsForbidden: function (value) {
          // Danh sách các từ cấm
          var forbiddenWords = ["vnpt", "#123", "123456aa@", "@123"];
          var lowerValue = value.toLowerCase();
          for (var i = 0; i < forbiddenWords.length; i++) {
            if (lowerValue.includes(forbiddenWords[i])) {

              return false; // Chuỗi chứa từ cấm

            }
          }
          return true; // Chuỗi không chứa từ cấm
        }

      },
      txtNhapLai: {
        required,
        minLength: minLength(8),
        containsUppercase: function (value) {
          return /[A-Z]/.test(value)
        },
        containsLowercase: function (value) {
          return /[a-z]/.test(value)
        },
        containsNumber: function (value) {
          return /[0-9]/.test(value)
        },
        containsSpecial: function (value) {
          return /[#?!@$%^&*-]/.test(value)
        }
      }
    }
  },
  watch: {},
  mounted () {

  },
  created () {
    return this.$v.$touch()

  },
  computed: {
    isDisabled () {
      return this.$v.$invalid
    }
  },
  methods: {
    async submitRegistration () {
      this.user.success = false;
      this.submitted = true

  let result;
      this.$v.$touch()
      if (this.$v.$invalid) {
        return false // stop here if form is invalid
      } else {
        try {
          if (this.user.txtMatKhauMoi !== this.user.txtNhapLai) {
            // this.$toast.error('Nhập sai mật khẩu, hãy nhập lại!')
            this.$toast.error('Mật khẩu nhập lại không khớp, hãy kiểm tra lại!')
            return false
          }
          await api
            .Doimatkhau(this.axios, {
              p_matkhau: this.user.txtMatKhauMoi,
              p_matkhau_old: this.user.txtMatkhauCu
            })
            .then(res => {
              result = res.data.data ? res.data.data : [];
              if(result==0)
              {
                const inputField = document.querySelector('.input-new-pass');
                if (inputField) {
                  inputField.text = "";
                }

            alert( "Đổi mật khẩu thành công")
            }
              else{
                alert (result)
              }


            })
        } catch (err) {
          this.loading(false)
        }
        this.loading(false)
      }
    },
    toggleNewPasswordVisibility() {
      this.showNewPassword = !this.showNewPassword;
      const inputField = document.querySelector('.input-new-pass');
      if (inputField) {
        inputField.type = this.showNewPassword ? 'text' : 'password';
      }
    },
    toggleOldPasswordVisibility() {
      this.showPasswordcu = !this.showPasswordcu;
      const inputField = document.querySelector('.input-change-pass');
      if (inputField) {
        inputField.type = this.showPasswordcu ? 'text' : 'password';
      }
    },
    toggleRetypePasswordVisibility() {
      this.showRetypePassword = !this.showRetypePassword;
      const inputField = document.querySelector('.input-retype-pass');
      if (inputField) {
        inputField.type = this.showRetypePassword ? 'text' : 'password';
      }
    },
    redirectToHome () {
       this.$router.push({ name: 'Home' })
    }
  },
   setFocus() {
                 this.user.txtMatkhauCu.focus()// don't  work
            }
}
</script>
<style>
.invalid-feedback {
  color: red;
  display: initial
}
.form-group {
  margin: 10px;
  width: 450px
}

input[type='submit'] {
  width: 150px
}

.page-content {
    border: 1px solid #eaeaea;
    top: 90px;
    border-radius: 10px;
    width: 700px;
    padding: 20px;
    margin-left: 20px;
    bottom: unset;
}

.footer-change-pass {
  display: flex;
  justify-content: center;
  padding-left: 90px;
  position: unset;
}

.btnRegister {
  background: unset;
  margin: 0 5px;
  width: 100px !important;
  border: 1px solid #c4c5c9 !important;
  color: #c4c5c9;
}

input[type='submit']{
  width: unset;
  border: 1px solid #3c94ff;
  color: #3c94ff;
}

.text-alert {
  color: #40a4f2;
}

.form-group > label {
  width: 180px;
  font-size: 14px;
  margin-top: 10px;
}

.form-group>input::placeholder {
  color: #eaeaea;
  opacity: 1;
}

.input-change-pass {
  width: 330px !important;
}
.input-new-pass {
  width: 330px !important;
}
.input-retype-pass {
  width: 330px !important;
}
.breadcrumb-top {
  display: flex;
  justify-content: space-between;
}

.main-title {
  margin: 20px;
  font-weight: bold;
}

.breadcrumb {
  background: unset
}

.breadcrumb-item, .breadcrumb-item > a {
  color: #82757d;
  font-style: italic;
  font-size: 14px;
}

.breadcrumb > li {
  padding-top: 10px;
}
.hr {
  margin: 0px 0px 30px;
  border-color: #cfeeff;
}

.invalid-feedback {
  padding-left: 125px;
}
</style>
