<template>
     <b-modal
    ref="popupChiTietTrangThaiSMS"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chi tiết trạng thái SMS
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="popup-body">
        <div class="box-form">
            <div class="info-row">
                <div class="key w70">Số ĐT</div>
                <div class="value">
                    <input type="text" class="form-control highlight">
                </div>
            </div>
            <KDataGrid
                :columns="columns"
                :dataSource="dataSources"
                :enable-paging-server="false"
                :allowPaging="true"
                :showFilter="true"
                :showColumnCheckbox="false"
                :enabledSelectFirstRow="true"/>
        </div>
      </div>
    </div>
  </b-modal>   
</template>
<script>
import KDataGrid from '@/components/kylq_components/KDataGrid'
export default {
    name:'ChiTietTrangThaiSMSModal',
    components:{
        KDataGrid
    },
    data(){
        return {
            columns:[
                {
                    fieldName:'finaltime',
                    headerText:'FINALTIME',
                    allowFiltering:true,
                    width:70
                },
                {
                    fieldName:'sms_content',
                    headerText:'SMS_CONTENT',
                    allowFiltering:true,
                }
            ],
            dataSources:[]
        }
    },
    methods:{
        showModal() {
            this.$refs["popupChiTietTrangThaiSMS"].show()
        },
        hideModal() {
            this.$refs["popupChiTietTrangThaiSMS"].hide()
        },
        handleShowModal(){

        }
    }
}
</script>