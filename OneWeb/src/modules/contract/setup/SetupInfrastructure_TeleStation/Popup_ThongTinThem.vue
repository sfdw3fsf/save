<template>
    <div>
        <!-- <button @click="openHDDT" class="btn btn-success m-5">Hóa đơn điện tử</button> -->
        
  <b-modal
    id="thongtinthem"
    ref="popup_thongtinthem"
    modal-class="popup-box"
    header-class="popup-header"
    body-class="none-padding"
    size="xl"
    hide-footer
  >
    <template #modal-header="{ close }">
      <div class="title">
        <span class="icon nc-icon-outline ui-1_settings"></span> Thông tin thêm
      </div>
      <div
        @click="close"
        class="close -ap icon-close"
        data-dismiss="modal"
      ></div>
    </template>


                <div class="popup-body">
                    <div class="box-form">
                        <div class="info-row">
                            <div class="key w95">
                                Từ ngày
                            </div>
                            <div class="value">
                                <div class="input-icon-right">
                                <vue-date
                                  format="DD/MM/YYYY"
                                  type="datetime"
                                  v-model="txtTuNgay"
                                ></vue-date>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w95">
                                Đến ngày
                            </div>
                            <div class="value">
                                <div class="input-icon-right">
                                <vue-date
                                  format="DD/MM/YYYY"
                                  type="datetime"
                                  v-model="txtDenNgay"
                                ></vue-date>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">
                                Đơn giá
                            </div>
                            <div class="value">
                                <input type="text" v-model="txtDonGia" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">
                                Thành tiền
                            </div>
                            <div class="value">
                                <div class="input-number-custom">
                                    <input type="number"  v-model="txtThanhTien" class="form-control tright">
                                </div>
                            </div>
                            <div class="value w20 nowrap padt7">
                                VNĐ
                            </div>
                        </div>
                        <div class="info-row" v-if="showSoSoiCap">
                            <div class="key w100">
                                <div class="check-action">
                                    <input type="checkbox" v-model="chkSoSoiCap" class="check">
                                    <span class="name">Số sợi cáp</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="input-number-custom">
                                    <input type="number" v-model="txtSoSoiCap" :disabled="!this.chkSoSoiCap" class="form-control tright">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="group-buttons -button tright">
                        <button class="btn btn-second" @click.prevent="btnXacNhan">
                            <span class="fa fa-check"></span> Xác nhận
                        </button>
                    </div>
                </div>
            


  </b-modal>


    </div>
</template>
<script>
import api from "./api";
import moment from "moment";

import Vue from 'vue'

export default {
name:'thongtinthem',

props: {
      showSoSoiCap: {
        type: Boolean ,
        default: true
    },
},

  data() {
    return {

    txtTuNgay: moment().format("DD/MM/YYYY"),
    txtDenNgay: moment().add(1, 'days').format('DD/MM/YYYY'),
    txtDonGia:"",
    txtThanhTien:"",
    chkSoSoiCap:false,
    txtSoSoiCap:"",
    grid_danhsach:[],
    headers: [
        {
            textAlign:"center",
          fieldName: 'stt',
          headerText: 'STT',
          allowFiltering: true,
          allowSorting: false
        },
        {
            textAlign:"center",
          fieldName: 'fkey',
          headerText: 'Fkey',
          allowFiltering: true,
          allowSorting: false
        },

        {
        textAlign:"center",
        fieldName:"pattern",
        headerText:"Mẫu hóa đơn",
        allowSorting:"true",
        allowFiltering:"true"
        },
        {
        textAlign:"center",
        fieldName:"serial",
        headerText:"Ký hiệu",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"center",
        fieldName:"invNum",
        headerText:"Số hóa đơn",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"center",
        fieldName:"name",
        headerText:"Tên hóa đơn",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"center",
        fieldName:"index",
        headerText:"Tháng xuất HĐ",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"center",
        fieldName:"cusname",
        headerText:"Tên khách hàng",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"center",
        fieldName:"signStatus_chu",
        headerText:"Khách hàng xem HĐ",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"center",
        fieldName:"publishDate",
        headerText:"Ngày phát hành",
        allowSorting:"true",
        allowFiltering:"true",
    },

        {
        textAlign:"center",
        fieldName:"amount",
        headerText:"Tổng tiền",
        type: "Number",
        format: "N",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"center",
        fieldName:"status_chu",
        headerText:"Trạng thái hóa đơn",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"center",
        fieldName:"payment_chu",
        headerText:"Trạng thái thanh toán",
        allowSorting:"true",
        allowFiltering:"true",
    },

        {
        textAlign:"center",
        fieldName:"payment_chu",
        headerText:"Trạng thái thanh toán",
        allowSorting:"true",
        allowFiltering:"true",
    },
        ],
    };
  },
  created() {
      
  },

  watch: {

  },

  methods: {

btnXacNhan()
{
  let TuNgay = moment(this.txtTuNgay,"DD/MM/YYYY").format("YYYYMMDD")
  let DenNgay = moment(this.txtDenNgay,"DD/MM/YYYY").format("YYYYMMDD")
  if (Number(TuNgay) > Number(DenNgay))
  {
      this.$toast.error("Ngày bắt đầu phải trước ngày kết thúc.");
      return;
  }
 
    let item = {
        "DonGia": this.txtDonGia,
        "ThanhTien":this.txtThanhTien,
        "SoSoiCap": this.txtSoSoiCap,
        "TuNgay": moment(this.txtTuNgay,"DD/MM/YYYY").format("DD/MM/YYYY"),
        "DenNgay": moment(this.txtDenNgay,"DD/MM/YYYY").format("DD/MM/YYYY")
    }


    this.$emit('chapnhan',item)
    this.hideModal()


},



showModal() {
  this.$bvModal.show("thongtinthem")
},
hideModal() {
    this.$bvModal.hide("thongtinthem")
},


clickButton(key) {

  if (key == "chapnhan") {
      this.ChapNhan()
  }
},



  },
};
</script>
<style>
/* @import "https://cdn.syncfusion.com/ej2/material.css"; */
.none-padding {
  padding: 0 !important;
}
.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  max-width: 1220px !important;
}

li.non-active a {
  color: #d3d3d3 !important;
}

/* .e-row:active, e-row:focus{background: #8d8b8b !important} */


.e-grid td.e-active{ 
  background-color: #8d8b8b !important}

</style>