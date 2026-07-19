<template>
  <b-modal id="thongTinUser" modal-class="fullscreen-modal" body-class="body-content" header-class="popup-header" content-class="popup-box" hide-footer no-close-on-backdrop no-close-on-esc v-model="showModal">
    <template #modal-header>
      <div class="title">
        <span class="icon icon-notepad"></span>
        Thông tin User
      </div>
      <div class="close -ap icon-close" data-dismiss="modal" @click="closeModal"></div>
    </template>

    <div class="list-actions-top">
      <ul class="list">
        <li class="cursor-pointer">
          <a @click="btnNhapMoi_Click" :class="`${!tbnNhapMoi ? 'disabled' : ''}`"> <span class="icon one-file-attach"></span>Tạo mới </a>
        </li>
        <li class="cursor-pointer">
          <a @click="btnCapNhat_Click" :class="`${!tbnCapNhat ? 'disabled' : ''}`"> <span class="icon one-file-plus"></span>Cập nhật </a>
        </li>
        <li class="cursor-pointer">
          <a @click="btnXoa_Click" :class="`${!tbnXoa ? 'disabled' : ''}`"> <span class="icon one-trash"></span>Xoá </a>
        </li>
        <li @click="closeModal" class="cursor-pointer">
          <a class="text-danger"> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát </a>
        </li>
      </ul>
    </div>

    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">Thông tin User</div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w90 text-main fw6">Email</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt :dataSource="emails" :allowFiltering="true" dataTextField="email" dataValueField="email_id" v-model="selectedEmail"></SelectExt>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90 text-main fw6">Loại User</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt :dataSource="loaiUsers" :allowFiltering="true" dataTextField="ten_loai" dataValueField="loai_user_id" v-model="selectedLoaiUser"></SelectExt>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90 text-main fw6">Username</div>
              <div class="value">
                <input type="text" v-model="username" class="form-control" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w90 text-main fw6">Password</div>
              <div class="value">
                <input type="password" v-model="password" class="form-control" autocomplete="new-password" />
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title">Danh sách thành viên của đại lý</div>
              <DataGrid :columns="dsUsers.headers" :dataSource="dsUsers.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" dataKeyField="ma_tt" :showFilter="false" @selectedRowChanged="handleUserSelected" />
            </div>
          </div>
          <div class="col-sm-8 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin liên hệ thanh toán</div>
              <DataGrid ref="refHdUsers" :columns="hdKhachHangs.headers" :dataSource="hdKhachHangs.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" dataKeyField="" :showFilter="false" @selectedRowChanged="handleHdUserSelected" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import api from './api.js'
export default {
  name: 'PopupThongTinUser',
  props: {
    vkhachhang_id: {
      type: Number,
      default: 0
    },
    vhdkh_id: {
      type: Number,
      default: 0
    },
    ma_kh:{
      type:String,
      default:''
    },
    ten_kh:{
      type:String,
      default:''
    },
  },
  data() {
    return {
      showModal: false,
      emails: [],
      loaiUsers: [],

      selectedEmail: 0,
      selectedLoaiUser: 0,
      username: '',
      password: '',
      tbnNhapMoi: false,
      tbnCapNhat: false,
      tbnXoa: false,

      hduser_id: 0,
      user_id: 0,
      vKieu: 1,

      //user
      userSelected: null,
      hdUserSelected: null,

      hdKhachHangs: {
        headers: [
          { fieldName: 'stt', headerText: 'STT', allowFiltering: true, allowSorting: false, width: 60, textAlign: 'Center' },
          { fieldName: 'ma_tt', headerText: 'Mã thanh toán', allowFiltering: true, allowSorting: false, width: 100, textAlign: 'Center' },
          { fieldName: 'ten_tt', headerText: 'Tên thanh toán', allowFiltering: true, allowSorting: false, width: 100, textAlign: 'Center' },
          { fieldName: 'trangthai', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false, width: 100, textAlign: 'Center' },

          { fieldName: 'dangky', headerText: 'Đăng ký', allowFiltering: true, allowSorting: false, width: 60, textAlign: 'Center', template: this.getCheckboxTemplate(this, 'dangky', 'huy','stt', 'refHdUsers') },
          { fieldName: 'huy', headerText: 'Huỷ', allowFiltering: true, allowSorting: false, allowCheckbox: true, template: this.getCheckboxTemplate(this, 'huy', "dangky",'stt','refHdUsers'), width: 60 }
        ],
        data: []
      },

      dsUsers: {
        headers: [
          { fieldName: 'user_name', headerText: 'User Name', allowFiltering: true, allowSorting: false, width: 100 },
          { fieldName: 'ten_loai', headerText: 'Loại User ', allowFiltering: true, allowSorting: false, width: 100 }
        ],
        data: []
      },
    }
  },
  watch: {
    async showModal(newValue) {
      if (newValue) {
        await this.onModalShow()
      }
    },
    async userSelected(newValue){
      if (newValue) {
        await this.getDanhSachHopDongUser()
      }
    }

  },

  methods: {
    //khi modal load
    async onModalShow() {
      await this.getLoaiUsers()
      await this.getDanhSachEmail()
      await this.getDanhSachUser()
      if(this.dsUsers.data.length==0){
        await this.getDanhSachHopDongUser()
      }
    },
    setButton(val) {
      this.vKieu = val
      this.tbnNhapMoi = false
      this.tbnCapNhat = false
      this.tbnXoa = false

      if (val === -1)
        //bat dau
        this.tbnCapNhat = true
      if (val === 0) {
        //bat dau
        this.tbnNhapMoi = true
        this.clean()
      }

      if (val === 1) {
        //Them moi
        this.tbnCapNhat = true
        this.clean()
      }
      if (val === 2) {
        //Huy
        this.tbnNhapMoi = true
        this.tbnXoa = true
        this.clean()
      }
      if (val === 3) {
        //Edit
        this.tbnNhapMoi = true
        this.tbnXoa = true
        this.tbnCapNhat = true
      }
    },

    //lay danh sách hđtt thông tin liên hệ thanh toán
    async getDanhSachHopDongUser() {
      const payload = {
        p_khachhang_id: this.vkhachhang_id,
        p_hdkh_id: this.vhdkh_id,
        p_hduser_id: this.hduser_id,
        p_user_id: this.user_id
      }
      const response = await api.getDanhSachHopDongUser(this.$root.axios, payload)
      const { error_code, data } = response.data
      if (error_code === 'BSS-00000000') {
        this.hdKhachHangs.data = data?data:[]
      }
    },
    async getDanhSachEmail() {
      const payload = {
        p_khachhang_id: this.vkhachhang_id,
        p_hdkh_id: this.vhdkh_id
      }
      const response = await api.getDanhSachEmail(this.$root.axios, payload)
      const { error_code, data } = response.data
      if (error_code === 'BSS-00000000') {
        this.emails = data
        if (data.length > 0) this.selectedEmail = data[0].loai_user_id
      }
    },

    //fill danh sach thanh vien dai li
    async getDanhSachUser() {
      const payload = {
        p_khachhang_id: this.vkhachhang_id,
        p_hdkh_id: this.vhdkh_id
      }
      const response = await api.getDanhSachUser(this.$root.axios, payload)
      const { error_code, data } = response.data
      if (error_code === 'BSS-00000000') {
        if (data.length > 0) {
          this.dsUsers.data = data
        } else {
          // this.vKieu = 1
          this.setButton(1) //neu ko co user thi set truong hop nhap moi
        }
      } else {
        // this.vKieu = 1
        this.setButton(1)
        this.dsUsers.data = [];
      }
    },

    //fill dropdown loai user
    async getLoaiUsers() {
      const response = await api.getLoaiUser(this.$root.axios)
      const { error_code, data } = response.data
      if (error_code === 'BSS-00000000') {
        this.loaiUsers = data
        if (data.length > 0) this.selectedLoaiUser = data[0].loai_user_id
        else this.selectedLoaiUser = null
      }
    },

    btnNhapMoi_Click() {
      this.setButton(1) //case them moi
      // this.vKieu = 1
    },
    clean() {
      this.hduser_id = 0
      this.user_id = 0
      this.username = ''
      this.password = ''
    },

    async btnCapNhat_Click() {
      if (!this.validateUser()) {
        return
      }
      this.$root.loading(true)

      //neu nhap moi thi

      try {
        const { json_hdtt, json_diachi, json_diachi_hddt, json_hdtt_user, json_hduser } = await this.createDataJson()

        const payload = {
          p_json_hduser: JSON.stringify(json_hduser),
          p_json_hdtt: json_hdtt.length > 0 ? JSON.stringify( json_hdtt ): '',
          p_json_diachi: json_diachi.length > 0 ? JSON.stringify( json_diachi ): '',
          p_json_diachi_hddt: json_diachi_hddt.length > 0 ? JSON.stringify(json_diachi_hddt) : '',
          p_json_hdtt_user: json_hdtt_user.length > 0 ?  JSON.stringify(json_hdtt_user ): '',
          p_insert: this.vKieu === 1 ? 1 : 2 //1 insert, 2 update
        }

        const response = await api.updateHopDongUser(this.$root.axios, payload)
        this.$root.loading(false)
        const { error_code } = response.data
        if (error_code === 'BSS-00000000') {
          this.username = ''
          this.password = ''
          this.$toast.success('Cập nhật thông tin user thành công!', 'title')
          await this.onModalShow()
        } else {
          this.$toast.error(`Có lỗi xaỷ ra: ${response.data.message}`, 'title')
        }
        // console.log('🚀 ~ btnCapNhat_Click ~ response', response)
      } catch (e) {
        console.log(e)
        this.$root.loading(false)
        this.$toast.error(`Có lỗi xaỷ ra: ${e.message}`)
      }
    },
    //xoa hopdong user
    async btnXoa_Click() {

      const payload = {
        p_hduser_id: this.userSelected.hduser_id
      }

      this.$bvModal
        .msgBoxConfirm('Xác nhận hành động', {
          title: 'Bạn thật sự muốn xóa User không ?',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (v) {
            try {
              this.$root.loading(true)
              const response = await api.deleteHopDongUser(this.$root.axios, payload)
              const { error_code } = response.data
              this.$root.loading(false)
              if (error_code === 'BSS-00000000') {
                await this.onModalShow()
                this.$toast.success('Xoá thông tin user thành công!', 'title')
              } else {
                this.$toast.error(`Có lỗi xaỷ ra: ${response.data.message}`, 'title')
              }
            } catch (e) {
              this.$toast.error(`Có lỗi xaỷ ra: ${e.message}`)
              this.$root.loading(false)
            }
          }
        })
     

      
    },

    closeModal() {
      this.$bvModal.hide('thongTinUser')
    },

    validateUser() {
      // let check = true;
      if (!this.username.trim()) {
        this.$toast.warning('Bạn chưa nhập thông tin User name!')
        return false
      }

      if (!this.password.trim()) {
        this.$toast.warning('Bạn chưa nhập thông tin Password!')
        return false
      }

      if (this.selectedLoaiUser === null || this.selectedLoaiUser === '') {
        this.$toast.warning('Bạn hãy chọn Loại user!')
        return false
      }
      return true
    },

    //tao data json pass vao ham cap nhat
    async createDataJson() {
      const json_hdtt = []
      const json_diachi = []
      const json_diachi_hddt = []
      const json_hdtt_user = []

      const json_hduser = {
        user_id: this.user_id, //nhap mới user = 0,
        user_name: this.username,
        pass_word: this.password,
        hdemail_id: this.selectedEmail,
        hdkh_id: this.vhdkh_id,
        loai_user_id: this.selectedLoaiUser,
        khachhang_id: 0,
        ma_kh:this.ma_kh,
        ten_kh:this.ten_kh
        
      }
      console.log('this.hdKhachHangs.data', this.hdKhachHangs.data)
      for (const hd of this.hdKhachHangs.data) {
        const kieuYc = parseInt(hd.dangky) === 1 ? 1 : 0
        if (parseInt(hd.dangky) === 1 || parseInt(hd.huy) === 1) {
          let hdtt_id = this.vKieu === 1 ? 0 : hd.hdtt_id 
          //nếu là thêm mới thì hoặc bằng 0 thì get key
          if(this.vKieu === 1 || hd.hdtt_id == 0){
            const respKey = await api.getKey(this.axios, JSON.stringify({
              "keyname": "HD_THANHTOAN"
            }))
            if(respKey && respKey.data.error_code === "BSS-00000200"){  
              hdtt_id = respKey.data.data
            }
          }
          // debugger
          const object_hdtt = {
            hdtt_id: hdtt_id,
            hdkh_id: this.vhdkh_id,
            thanhtoan_id: hd.thanhtoan_id,
            ma_tt: hd.ma_tt,
            ten_tt: hd.ten_tt,
            diachi_tt: hd.diachi_tt,
            httt_id: hd.httt_id,
            donvi_id: hd.donvi_id,
            nguoi_cn: await this.$root.token.getUserName(),
            may_cn: await this.$root.token.getMachine(), //check lai get CN
          }
    
          json_hdtt.push(object_hdtt)

          const { DCTT_USER, DC_INFO } = await this.createData_DCTT_USER(object_hdtt)

          json_diachi.push(DC_INFO)
          json_diachi_hddt.push(DCTT_USER)

          //doing
          const obj_hdtt_user = {
            hdtt_id: hdtt_id,
            kieu_yc: kieuYc, //1 dk 2 huy,
            hduser_id: this.hduser_id
          }
          json_hdtt_user.push(obj_hdtt_user)
        }
      }
      return {
        json_hduser,
        json_hdtt,
        json_diachi,
        json_diachi_hddt,
        json_hdtt_user
      }
    },

    //tao hop dong thanh toan user
    async createData_DCTT_USER(hduser) {
      const response = await this.axios.get('web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_dbttid', { params: { dbttid: hduser['thanhtoan_id'] } })

      const { error_code, data } = response.data

      if (error_code === 'BSS-00000000') {
        if (data.length > 0) {
          // this.json_diachi.push(data[0])
          // console.log(dcThanhToanInfo)
          return {
            DCTT_USER: {
              diachi_id: data[0].diachi_id,
              hdtt_id: hduser.hdtt_id,
              diachi_bc_id: data[0].diachibc_id,
              kinhdo: data[0].kinhdo,
              vido: data[0].vido,
              kinhdo_bc: data[0].kinhdo_bc,
              vido_bc: data[0].vido_bc
            },
            DC_INFO: data[0]
          }
        }
      }
      return null
    },
    async handleUserSelected(user) {
    
      if (user) {
        this.userSelected = user
        this.user_id = user['user_id'] //check
        this.hduser_id = user['hduser_id']
        this.selectedLoaiUser = user['loai_user_id']
        this.username = user['user_name']
        this.password = user['pass_word']
        this.setButton(3)
        // this.vKieu = 3;
        // await this.getDanhSachHopDongUser()
      }
    },

    async handleHdUserSelected(hd) {
      if (hd) {
        this.hdUserSelected = hd
      }
    },
    getCheckboxTemplate(parent, field, field2, uniq_field ,grid) {
      return function () {
        return {
          template: {
            template: `<div class="template_checkbox"> <input type="checkbox" @change="change" :checked="data[field] == 1" :id="uniq_field_computed" > </div>`,

            data() {
              return {
                field: field,
                field2: field2,
                uniq_field: uniq_field
              }
            },
            computed:{
              uniq_field_computed(){
                return this.data[uniq_field] +`_`+field.toUpperCase()
              }
            },
            methods: {
              change(event) {
                parent.$refs[grid].dataSource[this.data.index][field] = event.target.checked ? '1' : '0';
                if( event.target.checked){
                  parent.$refs[grid].dataSource[this.data.index][field2] = '0';      
                  const unq_feid_2 = this.data[uniq_field] + `_` + field2.toUpperCase()
                  document.getElementById(unq_feid_2).checked = false
                  // parent.$refs[refUnq].checked = false;
                  
                }
              }
            }
          }
        }
      }
    }
  }
}
</script>

<style>
.cursor-pointer {
  cursor: pointer;
}

.fullscreen-modal .modal-dialog {
  max-width: 100%;
  margin: 0;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100vh;
  padding: 25px;
  display: flex;
  position: fixed;
  z-index: 100000;
}
</style>
