<template>
   <b-modal
    id="authProcess"
    title="Nhập thông tin xác nhận quyền"
    modal-class="auth-process"
    header-class="popup-header"
    content-class="auth-process-content"
    body-class="auth-process-body"
    size="md"
    hide-footer
  >
    <div class="popup-body">
        <div class="box-form">
            <div class="info-row"> 
                <div class="key w70">User</div>
                <div class="value">
                    <div class="select-custom">
                        <select name="" id="" class="form-control">
                            <option value=""></option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key w70">Password</div>
                <div class="value">
                    <div class="select-custom">
                        <select name="" id="" class="form-control">
                            <option value=""></option>
                        </select>
                    </div>
                </div>
            </div>
            
            <div class="group-buttons center">
                <button class="btn btn-main">
                    Ok
                </button>
                <button class="btn btn-second">
                    Cancel
                </button>
            </div>
        </div>
    </div>
  </b-modal>
</template>
<script>
import moment from "moment";
export default {
name: 'authProcess',
  data() {
    return {
      
    }
  },
  methods: {
    show() {
      this.$bvModal.show("authProcess");
    }
  }
}
</script>
