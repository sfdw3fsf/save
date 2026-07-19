<template>
    <b-modal	
     id="frmTrangThaiSMS"	
     title="Chi tiết trạng thái SMS"	
     modal-class="trang-thai-modal"	
     header-class="popup-header"	
     content-class="trang-thai-content"	
     body-class="trang-thai-body overflow-auto"	
     size="xl"	
     hide-footer	
   >
     <div class="popup-body">
        <div class="grid-stack-box">
            <div class="box-col box-form" id="boxLeft">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w90">Số điện thoại</div>
                            <input type="text" class="form-control " v-model="txtSoDT" v-on:keyup.enter="txtSoDT_KeyPress">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="box-form">
            <div class="table-content">
                <DataGrid
                    ref="gridDanhSach"
                    v-bind:columns="gridKetQua.header"                    
                    v-bind:dataSource="gridKetQua.list"
                    :enable-paging-server="false"                                        
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="false"
                >
                </DataGrid>
            </div>
        </div>
     </div>
     </b-modal>	
 </template>
 <script>
 import moment from "moment";
 import Vue from 'vue'
 import API from '../API'
 export default {
 name: 'frmTrangThaiSMS',
 components: {
     API
   },
   props: ['tungay', 'so_dt'],
   mounted() {
   },
   data() {
     return {
        txtSoDT : '',
        gridKetQua : {
            list : [],
            header : [
                {fieldName: 'FINALTIME', headerText: 'FINALTIME', allowFiltering: true, width : 'auto'}, 
                {fieldName: 'SMCONTENT' , headerText: 'SMS_CONTENT', allowFiltering: true, width : 'auto'}, 
            ],
            value : {},
            isEnabled : true,
        },
     };
       // isActive : false,
   },
   
   methods: {
    show() {
       this.$bvModal.show("frmTrangThaiSMS")
       this.frmTrangThaiSMS_Load()
    //    this.HienThiDanhSach();
    },
     showAlert(mesage)
     {
       this.$toast.error(mesage);
     },
     showSuccess(mesage) {
       this.$toast.success(mesage);
     },
     GetData: function (response) {
       if (response.data.error === 200 || response.data.error === '200') {
         return response.data.data
       } else {
         console.log(response.data.error_code)
       }
       return []
     },

     frmTrangThaiSMS_Load() {
        let enddate = moment(new Date()).format('DDMMyyyy') + "235959"
        let startdate =  moment(new Date(), "DDMMyyyy").add(-10, 'days') + "000000"
        console.log('this.tungay1 is ', this.tungay)
        if (this.tungay != "")
        {
            startdate = this.tungay.replace(/[/:]/g,"")
            enddate = moment(this.tungay, "DDMMYYYY").add(10, 'days').format('DDMMYYYY') + "235959"
        }

        this.txtSoDT = this.so_dt;

        if (this.txtSoDT.length >=10)
        {
            this.TracuuSMS()
        }
     },

     txtSoDT_KeyPress() {
        if (this.txtSoDT.length >=10)
        {
            this.TracuuSMS()
        }
     },

     async TracuuSMS() {
        let enddate = moment(new Date()).format('DDMMyyyy') + "235959"
        let startdate =  moment(new Date()).add(-10, 'days').format('DDMMyyyy') + "000000"
        if (this.tungay != "")
        {
          console.log('run in this')
            startdate = this.tungay.replace(/[/:]/g, "")
            enddate = moment(this.tungay, "DDMMYYYY").add(10, 'days').format('DDMMYYYY') + " 235959"
        }

       
        this.loading(true)
        let res = await this.axios.post('/tichhop/sms/TraCuuSMS', {
            'msisdn' : this.txtSoDT,
            'startDate' : startdate,
            'endDate' : enddate
        })

        if (res && res.data) {
            if (res.data.data && res.data.data.length > 0) {
                this.gridKetQua.list = res.data.data ? res.data.data : []
            } else {
                this.gridKetQua.list = []
            }
        }
        this.loading(false)
     }

   }
 }
 </script>
 <style scoped>
    .trang-thai-modal .modal-dialog {
    height : 100%;
  }

  .trang-thai-modal .modal-dialog .trang-thai-content {
    height : 100%;
  }

  .trang-thai-modal .trang-thai-body {
    overflow-y: auto;
  }
 </style>
 