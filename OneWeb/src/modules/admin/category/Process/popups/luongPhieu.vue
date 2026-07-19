<template>
   <b-modal
    id="luongPhieu"
    :title="titleModal"
    modal-class="luong-phieu-modal"
    header-class="popup-header"
    content-class="luong-phieu-content"
    body-class="luong-phieu-body overflow-auto"
    size="xl"
    hide-footer
  >
    <div class="popup-body">
        <div class="grid-stack-box relative" v-if="this.dtTemp && this.dtTemp.length > 0">
            <div class="sitemap-box site-map-quy-trinh" style="width:180px;">
                <div :class="{'active' : item.isActive}" class="box-form  fw6 marb20 center red" v-for="(item, index) in dtTemp" :key="item.ten_loaidv" @click="toggleMenu(item.luong_id, item.loaidv_id)">
                    {{ (index + 1)  + ". " + item.ten_loaidv + " (Hướng: " + item.huonggiao_id + " - " + item.huonggiao + ")" }}
                    <div class="menu-context" style="left:30%;top: 20%;">
                        <a href="javascript:void(0)" @click="mnuNew_Click(index)" class="item padl10 padr10">Thêm mới</a>
                        <a href="javascript:void(0)" @click="mnuEdit_Click(index)" class="item padl10 padr10">Sửa</a>
                        <a href="javascript:void(0)" @click="mnuDelete_Click(item.loaidv_id, index)" class="item padl10 padr10">Xóa</a>
                        <a href="javascript:void(0)" @click="mnuLuongphieuDV_Click(item.loaidv_id)" class="item padl10 padr10">Luồng phiếu - đơn vị</a>
                        <a href="javascript:void(0)" @click="mnuLuongphieuTT_Click(item.loaidv_id)" class="item padl10 padr10">Luồng phiếu - thao tác</a>
                        <a href="javascript:void(0)" @click="mnuMoHinhBaoHong_Click(item.loaidv_id)" class="item padl10 padr10">Mô hình bảo hành thiết bị</a>
                    </div>
                </div>
            </div>
        </div>
        <ejs-contextmenu target='#luongPhieu' :items='menuItems' :select='onSelect'></ejs-contextmenu>
        <frmThemLuongPhieu ref="themLuongPhieu"
          :quyTrinhId="quytrinh_id"
          :dtTemp="dtTemp"
          :loaidv_id_cha="loaidv_id_cha"
          :loaidv_id_con="loaidv_id_con"
          :huonggiao_id="huonggiao_id"
          v-on:update-luong-phieu="updateLuongPhieu"
        />
        <frmSuaLuongPhieu ref="suaLuongPhieu"
          :quyTrinhId="quytrinh_id"
          :dtTemp="dtTemp"
          :loaidv_id="loaidv_id"
          :loaidv_id_cha="loaidv_id_cha"
          :loaidv_id_con="loaidv_id_con"
          :huonggiao_id="huonggiao_id"
          :stt="stt"
        />
        <luongPhieuDv ref="luongPhieuDv" :luongId="luongIdByQuyTrinh" :idx="idx"/>
<!--        <luongPhieuTt ref="luongPhieuTt" :luongId="luongIdByQuyTrinh"/>-->
         <!-- <ejs-dialog
      :isModal="true"
      :enableResize="true"
      :resizeHandles="['All']"
      :visible="false"
      ref="luongPhieuTt"
      :header='"Luồng phiếu - thao tác"'
      showCloseIcon="true"
      target=".main-wrapper"
      width="60%"
      allowDragging="true"
      @close="luongPhieuTt_OnClose"
      zindex="10000"
    > -->
      <!-- <div>
        <luongPhieuTt v-if="luongPhieuTtDialog" :luongId="luongIdByQuyTrinh"/>
      </div> -->
    <!-- </ejs-dialog> -->
        <baoHanhThietBi ref="baoHanhThietBi" :luongId="luongIdByQuyTrinh"/>

      <b-modal id="popupComponents" ref="popupComponents" size="xl" hide-footer :no-close-on-backdrop="true" body-class="modal-body p-0">
        <component :luongId="luongIdByQuyTrinh" :is="popupComponent" ref="generalPopup" isPopup="true" ></component> <!--v-dynamic-events="popupComponentEvents"-->
      </b-modal>
    </div>
  <!-- </div> -->
    </b-modal>
</template>
<script>
import moment from "moment";
import Vue from 'vue'
import API from '../API'
import { ContextMenuComponent } from "@syncfusion/ej2-vue-navigations";
import { ContextMenuPlugin } from "@syncfusion/ej2-vue-navigations";
import { enableRipple } from '@syncfusion/ej2-base';
import frmThemLuongPhieu from './frmThemLuongPhieu.vue';
import frmSuaLuongPhieu from './frmSuaLuongPhieu.vue';
import luongPhieuDv from './luongPhieuDv.vue';
// import luongPhieuTt from './luongPhieuTt.vue';
import baoHanhThietBi from './baoHanhThietBi.vue';
Vue.use(ContextMenuPlugin);
enableRipple(true);
export default {
name: 'luongPhieu',
components: {
    API,
    "ejs-contextmenu": ContextMenuComponent,
    frmThemLuongPhieu,
    luongPhieuDv,
    // luongPhieuTt,
    baoHanhThietBi,
    frmSuaLuongPhieu
  },
  props: ['quyTrinhId', 'tenQuyTrinh'],
  mounted() {
      this.quytrinh_id = this.quyTrinhId
      this.HienThiDanhSach();
      this.frmLuongPhieu();
      console.log("quyTrinhId")
      console.log(this.quyTrinhId)
      console.log("tenQuyTrinh")
      console.log(this.tenQuyTrinh)
  },
  data() {
    return {
      luongPhieuTtDialog: false,
      luong_id : 0,
      quytrinh_id : 0,
      ten_quy_trinh : this.tenQuyTrinh,
      titleModal : "Định nghĩa luồng phiếu quy trình - ",
      dtHuonggiao_ldv : [],
      startX : 0,
      startY : 0,
      endX : 0,
      endY : 0,
      load : true,
      idx : -1,
      stt : 0,
      dtTemp : [],
      viewMenuThemMoi: false,
      luongphieu : {},
      top: '0px',
      left: '0px',
      menuItems:[
        {
            text: 'Thêm mới',
        },
        {
            text: 'Cập nhật'
        },
        {
            text: 'Lấy danh sách'
        },
        {
            text: 'Thoát'
        },
      ],
      luongIdByQuyTrinh : 0,
      loaidv_id_cha : 0,
      loaidv_id_con : 0,
      huonggiao_id : 0,
      loaidv_id : 0,

      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        // 'form-close': this.popupClosed,
        // acceptChangeCTV: this.popupClosed,
        // acceptChangeNGT: this.popupClosed,
        // PopUpCongTacVien_Thoat: this.popupClosed
      },
      popupComponentName: '',
    };
      // isActive : false,
  },
  // watch: {
  //   quyTrinhId(val) {
  //     this.quytrinh_id = val;
  //   },
  //   tenQuyTrinh(val) {
  //     this.ten_quy_trinh = val;
  //   }
  // },
  watch: {
    quyTrinhId(val) {
      this.quytrinh_id = val;
    }
  },
  methods: {
    async show_dynamic_popup() {
      this.popupComponent = () => import('./luongPhieuTt.vue')
      this.popupComponentName = 'luongPhieuTt'
      this.Popup('popupComponents')
    },
    Popup(modalId) {
      this.loading(false)
      if (this.$refs[modalId]) this.$refs[modalId].show()
      else this.$bvModal.show(modalId)
    },

    async luongPhieuTt_OnClick() {
      this.luongPhieuTtDialog = true
      this.$refs.luongPhieuTt.show()
    },
    async luongPhieuTt_OnClose() {
      this.luongPhieuTtDialog = false
    },
    show() {
      this.$bvModal.show("luongPhieu");
      this.HienThiDanhSach();
    },
    showAlert(mesage)
    {
      this.$toast.error(mesage);
    },
    showSuccess(mesage) {
      this.$toast.success(mesage);
    },
    frmLuongPhieu()
    {
      // this.titleModal = "Định nghĩa luồng phiếu quy trình - " + this.tenQuyTrinh;
      // this.HienThiDanhSach()
    },
    async mnuMoHinhBaoHong_Click(id) {
      let input = {
        "quytrinh_id": this.quytrinh_id,
        "loaidv_dich_id": id
      }
      let luong = this.GetData(await API.get_luong_id_by_quytrinh_id(this.axios, input))
      // console.log('luong id is ', luong['luong_id'])
      if (luong && luong.length > 0) {
        this.luongIdByQuyTrinh = luong[0]['luong_id']
      }
      this.$refs.baoHanhThietBi.show()
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },

    updateLuongPhieu(val) {
      console.log('this.dtTemp ngoai luong phieu sau update is ', val)
      this.dtTemp = val
      this.$bvModal.hide("themLuongPhieu");
    },

    async CapNhat() {
      if (this.dtTemp.length < 0) {
        this.ShowAlert("Chưa định nghĩa luồng phiếu trong quy trình !");
        return;
      }

      let dsHUONGGIAO_LDV = []
      let dsLUONGDV = []
      let dsLUONGTT = []

      for (let i = 0; i < this.dtTemp.length; i++) {
        let luong_id = 0
        let loaidv_dich_id = 0
        if (this.dtTemp[i].loaidv_dich_id != 0) {
          loaidv_dich_id = this.dtTemp[i].loaidv_dich_id
        }

        let obj = {
          'HUONGGIAO_ID' : this.dtTemp[i].huonggiao_id,
          'QUYTRINH_ID' : this.quytrinh_id,
          'LOAIDV_NGUON_ID' : this.dtTemp[i].loaidv_id
        }

        let input = {
          'p_ds_para' : JSON.stringify(obj)
        }

        let data = this.GetData(await API.fn_lay_tt_taodulieu_luongphieu(this.axios, input))
        data = JSON.parse(data)

        if (data && data.ERROR_CODE) {
          if (data.RESULT) {
            luong_id = data.RESULT.LUONG_ID ? data.RESULT.LUONG_ID : 0

            if (data.RESULT.DS_LUONGDV && data.RESULT.DS_LUONGDV.length > 0) {
              data.RESULT.DS_LUONGDV.forEach(item => {
                dsLUONGDV.push(item)
              })
            }
            if (data.RESULT.DS_LUONGTT && data.RESULT.DS_LUONGTT.length > 0) {
              data.RESULT.DS_LUONGTT.forEach(item => {
                dsLUONGTT.push(item)
              })
            }
          }
        }

        dsHUONGGIAO_LDV.push({
          'QUYTRINH_ID' : this.quytrinh_id,
          'HUONGGIAO_ID' : this.dtTemp[i].huonggiao_id,
          'LOAIDV_DICH_ID' : loaidv_dich_id,
          'LOAIDV_NGUON_ID' : this.dtTemp[i].loaidv_id,
          'THUTU_ID' : i + 1,
          'LUONG_ID' : luong_id
        })

      }
      let obj1 = {
        'HUONGGIAO_LDV' : dsHUONGGIAO_LDV,
        'LUONG_DV' : dsLUONGDV,
        'LUONG_THAOTAC' : dsLUONGTT

      }
      let input1 = {
        'p_ds_para' : JSON.stringify(obj1)
      }
      let res = await API.fn_ins_huonggiao_ldv_luongphieu(this.axios, input1)
      let data = JSON.parse(res.data.data)
      if(data.ERROR_CODE == 1) {
        this.$toast.success(`${data.MESSAGE}`)
      } else {
        this.$toast.error(`${data.MESSAGE}`)
      }
      this.HienThiDanhSach()
    },

    toggleMenu(luongId, dichVuId){
      // let clicked = false
      if (this.dtTemp.length > 0) {
        this.dtTemp = this.dtTemp.map(item => {
          // item.isActive = false
          if (item.luong_id == luongId && item.loaidv_id == dichVuId) {
            item.isActive = !item.isActive
          } else {
            item.isActive = false
          }
          //item.isActive = false
          return item
        })
      }
    },
    LoadControl() {
      let LuongphieuCount = this.dtTemp ? this.dtTemp.length : 0
      if (LuongphieuCount > 0)
      {
          // luongphieu = new LUONGPHIEU[LuongphieuCount];
          this.Load_UserControl(0);
          this.idx = 0
          this.stt = 0
      }
      for (let i = 1; i < LuongphieuCount; i++)
      {
          this.Load_UserControl(i);
      }
    },
    Load_UserControl(stt) {
      let dr = []
      if (stt == 0)
      {
          // dr = dtTemp.Select("loaidv_nguon_id is null or loaidv_nguon_id = 0");
          dr = this.dtTemp.filter((item) => {
            return item.loaidv_nguon_id == null || item.loaidv_nguon_id == 0
          })
          let obj = {
            'stt' : stt,
            'loaidv_id' : dr[0]["loaidv_id"],
            'ten_loaidv' : (stt + 1) + ' ' + dr[0]["ten_loaidv"] + " (Hướng: " + dr[0]["huonggiao_id"] + " - " + dr[0]["huonggiao"] + ")",
            'huonggiao_id' : dr[0]["huonggiao_id"]
          }

          this.luongphieu[stt] = obj
          // let id1 = this.luongphieu[stt].loaidv_id
          // let dr1 = this.dtTemp.filter(item => {
          //   return item.loaidv_nguon_id == id1
          // })

          // if (dr1.length > 0) {
          //   this.luongphieu[stt].loaidv_dich_id = dr[0]["loaidv_id"];
          // }
          // else {
          //   this.luongphieu[stt].loaidv_dich_id = 0;
          // }
          // let id2 = this.luongphieu[stt].loaidv_id
          // let dr2 = this.dtTemp.filter(item => {
          //   return item.loaidv_dich_id == id2
          // })
          // if (dr2.length > 0) {
          //     this.luongphieu[stt].loaidv_nguon_id = dr[0]["loaidv_id"]
          // }
          // else {
          //   this.luongphieu[stt].loaidv_nguon_id = 0;
          // }
      }
      else
      {
        let id = this.dtTemp[stt - 1].loaidv_id
        dr = this.dtTemp.filter((item) => {
          return item.loaidv_nguon_id == id
        })
        let obj = {
          'stt' : stt,
          'loaidv_id' : dr[0]["loaidv_id"],
          'ten_loaidv' : (stt + 1) + ' ' + dr[0]["ten_loaidv"] + " (Hướng: " + dr[0]["huonggiao_id"] + " - " + dr[0]["huonggiao"] + ")",
          'huonggiao_id' : dr[0]["huonggiao_id"]
        }

        this.luongphieu[stt] = obj
      }
      let id1 = this.luongphieu[stt].loaidv_id
        let dr1 = this.dtTemp.filter(item => {
          return item.loaidv_nguon_id == id1
        })

        if (dr1.length > 0) {
          this.luongphieu[stt].loaidv_dich_id = dr[0]["loaidv_id"];
        }
        else {
          this.luongphieu[stt].loaidv_dich_id = 0;
        }
        let id2 = this.luongphieu[stt].loaidv_id
        let dr2 = this.dtTemp.filter(item => {
          return item.loaidv_dich_id == id2
        })
        if (dr2.length > 0) {
            this.luongphieu[stt].loaidv_nguon_id = dr[0]["loaidv_id"]
        }
        else {
          this.luongphieu[stt].loaidv_nguon_id = 0;
        }
    },
    HienThiDanhSach : async function() {
      this.titleModal = "Định nghĩa luồng phiếu quy trình - " + this.tenQuyTrinh;
      let input = {
          "vquytrinh_id": this.quytrinh_id
      }
      this.dtTemp = this.GetData(await API.lay_ds_luongphieu_theo_qt(this.axios, input));
      if (this.dtTemp && this.dtTemp.length > 0) {
        this.dtTemp.map(item => {
          item.isActive = false
        })
      }
      this.LoadControl()
    },
    onSelect: function(args ) {
      if(args.item.text === 'Thêm mới') {
        this.$refs.themLuongPhieu.show();
        this.LoadControl();
      }
      if(args.item.text === 'Lấy danh sách') {
        this.HienThiDanhSach()
      }
      if(args.item.text === 'Cập nhật') {
        this.CapNhat()
      }
      if(args.item.text === 'Thoát') {
        this.$bvModal.hide('luongPhieu')
      }
    },
    mnuThemMoi_Click() {
      this.$refs.themLuongPhieu.show();
      this.load = false;
      this.idx = 0;
      this.stt = 0;
    },

    mnuNew_Click(stt) {
      this.$refs.themLuongPhieu.show();
      this.loaidv_id_cha = this.luongphieu[stt].loaidv_id ? this.luongphieu[stt].loaidv_id : 0;
      this.loaidv_id_con = this.luongphieu[stt].loaidv_dich_id ? this.luongphieu[stt].loaidv_dich_id : 0;
      this.huonggiao_id= this.luongphieu[stt].huonggiao_id ? this.luongphieu[stt].huonggiao_id : 0;
    },
    mnuEdit_Click(stt) {
      console.log('this.luongphieu[stt] is ', this.luongphieu[stt])
      this.loaidv_id = this.luongphieu[stt].loaidv_id ? this.luongphieu[stt].loaidv_id : 0,
      this.loaidv_id_cha = this.luongphieu[stt].loaidv_nguon_id ? this.luongphieu[stt].loaidv_nguon_id : 0;
      this.loaidv_id_con = this.luongphieu[stt].loaidv_dich_id ? this.luongphieu[stt].loaidv_dich_id : 0;
      this.huonggiao_id= this.luongphieu[stt].huonggiao_id ? this.luongphieu[stt].huonggiao_id : 0;
      this.stt = stt
      this.$refs.suaLuongPhieu.show();
    },

    mnuDelete_Click(id, index) {
      //Nối luồng phiếu cha của luông phiếu hiện tại vào luông phiếu con của luông phiếu hiện tại
      //Xóa luông phiếu hiện tại
      //Hiển thị lại luông phiếu.
      console.log('id is ', id)
      let drcha = [];
      let dr = [];
      let drcon = [];
      this.idx = id

      drcha = this.dtTemp.filter(item => {
        return item.loaidv_dich_id == id
      })
      dr = this.dtTemp.filter(item => {
        return item.loaidv_id == id
      })
      drcon = this.dtTemp.filter(item => {
        return item.loaidv_nguon_id == id
      })
      if (drcha.length > 0) {
        drcha[0].loaidv_dich_id = dr[0].loaidv_dich_id;
      }
      if (drcon.length > 0) {
        drcon[0].loaidv_nguon_id = dr[0].loaidv_nguon_id;
      }
      this.dtTemp.splice(index,1)
      this.LoadControl()
    },

    async mnuLuongphieuDV_Click(id) {
      let input = {
        "quytrinh_id": this.quytrinh_id,
        "loaidv_dich_id": id
      }
      let luong = this.GetData(await API.get_luong_id_by_quytrinh_id(this.axios, input))
      // console.log('luong id is ', luong['luong_id'])
      if (luong && luong.length > 0) {
        this.luongIdByQuyTrinh = luong[0]['luong_id']
      }
      this.idx = id
      this.$refs.luongPhieuDv.show();
    },

    async mnuLuongphieuTT_Click(id) {
      let input = {
        "quytrinh_id": this.quytrinh_id,
        "loaidv_dich_id": id
      }
      let luong = this.GetData(await API.get_luong_id_by_quytrinh_id(this.axios, input))
      // console.log('luong id is ', luong['luong_id'])
      if (luong && luong.length > 0) {
        this.luongIdByQuyTrinh = luong[0]['luong_id']
      } else {
        this.luongIdByQuyTrinh = 0
      }
      // this.$refs.luongPhieuTt.show()
      await this.show_dynamic_popup()
      // this.luongPhieuTt_OnClick()
    }
  }
}
</script>
<style scoped>
  .site-map-quy-trinh .box-form{
    cursor: pointer;
    position: relative;
  }
  .site-map-quy-trinh .box-form .menu-context {
    z-index: 1;
    width : 250px;
    border: 1px solid lightgray;
    display : none;
  }
  .site-map-quy-trinh .box-form.active .menu-context {
    display : block;
  }

  .luong-phieu-modal .modal-dialog {
    height : 100%;
  }

  .luong-phieu-modal .modal-dialog .luong-phieu-content {
    height : 100%;
  }

  .luong-phieu-modal .luong-phieu-body {
    overflow-y: auto;
  }
</style>
