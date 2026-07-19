<template>
  <b-modal
    ref="popupEmail"
    size="xl"
    body-class="p-0"
    hide-header
    hide-header-close
    hide-footer
  >
  <div class="modal-content popup-box">
            <div class="popup-header">
                    <div class="title">
                        <span class="icon one-notepad"></span>THÔNG TIN EMAIL
                    </div>
                    <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
            <div class="popup-body">
                <Email v-bind:khachhang_id="khachhang_id" v-bind:hdkh_id="hdkh_id"/>
            </div>
        </div>
    
    <!-- <div class="modal-content popup-box">
      <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span>TRA CỨU THÔNG TIN EMAIL
                </div>
                <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#">
              <span class="icon ui-1_circle-add nc-icon-glyph"></span>Tạo mới
            </a>
          </li>
          <li>
            <a href="#">
              <span class="icon one-reload1"></span>Cập nhật
            </a>
          </li>
          <li>
            <a href="#">
              <span class="icon ui-1_circle-remove nc-icon-glyph"></span>Huỷ thao
              tác
            </a>
          </li>
          <li>
            <a href="#">
              <span class="icon nc-icon-glyph users_single-05"></span>User
            </a>
          </li>
          <li>
            <a href="#">
              <span class="icon one-trash"></span>Xoá thông tin Email
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin Email</div>
              <div class="info-row">
                <div class="key w60">Email</div>
                <div class="value">
                  <input type="text" class="form-control" />
                </div>
              </div>
              <div class="box-move-select-table mart20">
                <div class="box-col">
                  <div class="legend-title">Mục đích chưa gán</div>
                  <DataGrid
                    v-bind:columns="DsMucDichCG.headers"
                    v-bind:dataSource="DsMucDichCG.data"
                    :showColumnCheckbox="true"
                    :enable-paging-server="false"
                    :allowPaging="false"
                    data-key-field="mucdich_id"
                    :showFilter="true"
                    @selectedItemsChanged="select_selectedItemsChangedCG"
                  ></DataGrid>
                </div>
                <div class="actions">
                  <button class="btn" @click="MoveRight">
                    <span class="fa fa-angle-right"></span>
                  </button>
                  <button class="btn" @click="MoveRightAll">
                    <span class="fa fa-angle-double-right"></span>
                  </button>
                  <button class="btn" @click="MoveLeft">
                    <span class="fa fa-angle-left"></span>
                  </button>
                  <button class="btn" @click="MoveLeftAll">
                    <span class="fa fa-angle-double-left"></span>
                  </button>
                </div>
                <div class="box-col">
                  <div class="legend-title">Mục đích đã gán</div>
                  <DataGrid
                    v-bind:columns="DsMucDichDG.headers"
                    v-bind:dataSource="DsMucDichDG.data"
                    :showColumnCheckbox="true"
                    :enable-paging-server="false"
                    :allowPaging="false"
                    data-key-field="mucdich_id"
                    :showFilter="true"
                    @selectedItemsChanged="select_selectedItemsChangedDG"
                  ></DataGrid>
                </div>
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title">Danh sách Email</div>
              <DataGrid
                v-bind:columns="DsEmail.headers"
                v-bind:dataSource="DsEmail.data"
                :showColumnCheckbox="false"
                :enable-paging-server="false"
                :allowPaging="false"
                data-key-field="email_id"
                :showFilter="false"
                @rowClicked="select_selectEmail"
              ></DataGrid>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin liên hệ khách hàng</div>
              <DataGrid
                v-bind:columns="DsLienHeKH.headers"
                v-bind:dataSource="DsLienHeKH.data"
                :showColumnCheckbox="false"
                :enable-paging-server="false"
                :allowPaging="false"
                data-key-field
                :showFilter="false"
              ></DataGrid>
              <div class="legend-title mart20">Thông tin liên hệ thanh toán</div>
              <DataGrid
                v-bind:columns="DsLienHeTT.headers"
                v-bind:dataSource="DsLienHeTT.data"
                :showColumnCheckbox="false"
                :enable-paging-server="false"
                :allowPaging="false"
                data-key-field
                :showFilter="false"
              ></DataGrid>
            </div>
            <div class="box-form">
              <div class="legend-title">Thông tin liên hệ thuê bao</div>
              <DataGrid
                v-bind:columns="DsLienHeTB.headers"
                v-bind:dataSource="DsLienHeTB.data"
                :showColumnCheckbox="false"
                :enable-paging-server="false"
                :allowPaging="false"
                data-key-field
                :showFilter="false"
              ></DataGrid>
            </div>
          </div>
        </div>
      </div>
    </div> -->
  </b-modal>
</template>
<script>

import Email from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue'

export default {
  name: "ThongTinEmail",
  props: ['khachhang_id', 'hdkh_id'],
  components: {
    Email
  },
  methods: {

    showModal(){
      this.$refs.popupEmail.show();
    },
    hideModal(){
      this.$refs.popupEmail.hide();
    }
  },
};
</script>