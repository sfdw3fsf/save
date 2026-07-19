<template>
    <b-modal 
    size="xl" centered scrollable hide-footer
    :id='idModal' modal-class="fullscreen-modal"  
     no-close-on-backdrop no-close-on-esc>
    <template #modal-header>
      <div class="title">
        <span class="icon icon-notepad"></span>
        <h3>File Hồ Sơ</h3>
      </div>
      <div
        class="close -ap icon-close"
        data-dismiss="modal"
        @click="closeModal"
      ></div>
    </template>

    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">Danh sách File</div>
        <div class="row">


 
            <DataGrid ref="gridFileHS" :columns="gridFileHS.columns" :dataSource="gridFileHS.dataSource"
                        :showColumnCheckbox="true" :showFilter="true" 
                        :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }" panelDataHeight="270"
                        @rowSelected="handleFileSelected" 
                        :allowPaging="true" :enablePagingServer="false" 
            />
  

        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import Vue from 'vue';
import api from '../../api.js';
import apiHelper from '../../api.helper.js'
import helper from '../../helper.js'
export default {
    name: "PopUpDownloadFile",
    components: {
    },
    props: {
        idModal:{
            type: String,
            default:"popupDownloadFileHSId"
        }
    },
    data() {
        return {
            
            gridFileHS:{
                columns: [
                { fieldName: 'loai_file', headerText: 'Loại File' },
                { fieldName: 'ten_file', headerText: 'Tên File' },
                { fieldName: 'loai_file', headerText: 'Loại HĐ/PL' },
                { fieldName: 'ngay_yc', headerText: 'Ngày YC' },
                { fieldName: 'nhom_file', headerText: 'Nhóm file' },
                { fieldName: 'nguoi_cn', headerText: 'Người CN' },
                { fieldName: 'ngay_cn', headerText: 'Ngày CN' },
                {
                    fieldName: 'btn_xem', headerText: 'Xem', autoFit: "true", textAlign: "center",
                    template: function () {
                        return {
                            template: Vue.component("btnDownload_Template", {
                                template:
                                `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnDownload_Click">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-arrow-down" viewBox="0 0 16 16">
                                        <path d="M8.5 6.5a.5.5 0 0 0-1 0v3.793L6.354 9.146a.5.5 0 1 0-.708.708l2 2a.5.5 0 0 0 .708 0l2-2a.5.5 0 0 0-.708-.708L8.5 10.293V6.5z"/>
                                        <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2zM9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5v2z"/>
                                    </svg>
                                </button>`,
                                data: function () {
                                    return { data: {} };
                                },
                                methods: {
                                    btnDownload_Click() {
                                        if(!this.data?.url) return 
                                        helper.downloadURI(this.data.url, this.data.ten_file)
                                    },
                                },
                            }),
                        };
                    },
                }
            ],
                dataSource:[],
                selectedItem: null,
            },
        }
    },
    methods: {

        async onloadModal(item) {
           if(!item) return;
           this.loading(true);
           try {
            const response = await api.getFileDownloads(this.axios, item.baohong_id, item.phanVungId);
           this.gridFileHS.dataSource = apiHelper.getDataFromResponseApiReturnArray(response) || [];
         
           } catch (error) {
            console.log(error);
           }finally{
            this.loading(false);
           }
      
        },

        closeModal() {
            this.$bvModal.hide(this.idModal);
        },

        handleFileSelected(args){
            // console.log(args);
        },
    },

}
</script>

<style>
.popup {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
}

.popup-content {
    width: 1000px;
    height: 600px;
    background-color: rgb(252, 252, 252);
    padding: 20px;
    border-radius: 5px;
}

.scroll-box {
    overflow: auto;
}

.btn-close {
    margin-bottom: 5px;
}
</style>
