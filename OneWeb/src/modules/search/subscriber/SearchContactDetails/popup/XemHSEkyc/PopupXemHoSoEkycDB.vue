<template>
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="hieu_ung_popup" ref="frmXemHSEkycDB" :position="position"
              :header="'Xem hồ sơ EKYC'" showCloseIcon="true" width="80%"

              target="#app .main-wrapper">
    <div class="">
      <div class="list-actions-top">

      </div>
      <div class="popup-body">
        <div class='row'>
          <div class='col-sm-12'>
            <div class="box-form">
              <DataGrid class="table-result table-gachle"
                        v-bind:columns="grvFileHSEkyc.headers"
                        v-bind:dataSource="grvFileHSEkyc.list"
                        :enable-paging-server="false"
                        :allowPaging="true"
                        :commands=
                          "[
                                {name: 'TAI_VE', cssClass: 'btn btn-primary one-download', title : 'Tải về'}
                                ]"
                        :commandColumn="{headerText: 'Tải về/ Duyệt', width: 200, firstColumn: false, show: true}"
                        :showFilter="true"
                        @commandClicked="thaotacfile"
              >
              </DataGrid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import api from './api'
import fileDownload from 'js-file-download'
export default {
  name: 'PopupXemHoSoEkycDB',
  data()
  {
    return {
      hieu_ung_popup: { effect: 'Zoom' },
      position: { X: 'center', Y: 'center' },
      grvFileHSEkyc : {
        headers: [
          {fieldName: 'loai_hd', headerText: 'Loại hợp đồng', allowFiltering: false},
          {fieldName: 'ma_gd', headerText: 'Mã giao dịch', allowFiltering: false},
          {fieldName: 'ma_tb', headerText: 'Mã thuê bao', allowFiltering: false},
          {fieldName: 'url', headerText: 'Đường link', allowFiltering: false},
          {fieldName: 'name', headerText: 'Loại file', allowFiltering: false}
        ],
        list: []
      },
      dulieu: {}
    }
  },
  methods: {
    async openDialog(dulieu)
    {
      this.$refs.frmXemHSEkycDB.show();
      this.dulieu = dulieu;
      await this.layDSfileHoSo()
    },
    async layDSfileHoSo()
    {
      try {
       // this.dulieu.thuebao_id = 45369403;
        this.loading(true);
        this.grvFileHSEkyc.list = [] //clear all
        let kq = await api.lay_ds_hoso_ekyc_danhba({thuebao_id: this.dulieu.thuebao_id});
        console.log(kq);
        let ds_hs = kq.data.data;
        let arr_res = [];
        let obj = JSON.parse(kq.data.data)
        obj.forEach(x => {
          x.dulieu.forEach(item => {
            let ra = {
              ...item,
              loai_hd: x.loai_hd,
              hdtb_id: x.hdtb_id,
              ten_kieuld: x.ten_kieuld,
              thuebao_id: x.thuebao_id,
              ma_tb: x.ma_tb,
              ma_gd: x.ma_gd
            }
            this.grvFileHSEkyc.list.push(ra);
          })
        })
        console.log(arr_res);
      }
      catch (e)
      {
        let loi = e.message || e.data.message;
        this.$toast.error("Lỗi " +  loi);
      }
      finally {
        this.loading(false);
      }
    },
    async thaotacfile(name, dataItem)
    {
      if (name === 'TAI_VE') {
        try {
          let kq = await  api.tai_file_ekyc({
            StrRequest: dataItem.url
          });
          // let kq = axios2.post(`http://10.70.52.244:6061/api/SmartCA/DownFileEKYC`, {
          //   StrRequest: "/ekyc_images/upload/017089003196/20230224000302rear_preview.jpg"
          // });
          const byteCharacters = this.b64toBlob(kq.data.data.dulieu, kq.data.data.mime_type);

          var filename = dataItem.url.substring(dataItem.url.lastIndexOf('/')+1);
          console.log(kq.data.data);
          fileDownload(byteCharacters, filename, kq.data.data.mime_type);
        }
        catch (ex)
        {
          throw ex;
        }
        console.log(dataItem);
        return;
      }
    },
    b64toBlob (b64Data, contentType='', sliceSize=512) {
      const byteCharacters = atob(b64Data);
      const byteArrays = [];

      for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
        const slice = byteCharacters.slice(offset, offset + sliceSize);

        const byteNumbers = new Array(slice.length);
        for (let i = 0; i < slice.length; i++) {
          byteNumbers[i] = slice.charCodeAt(i);
        }

        const byteArray = new Uint8Array(byteNumbers);
        byteArrays.push(byteArray);
      }

      const blob = new Blob(byteArrays, {type: contentType});
      return blob;
    },
  }
}
</script>

<style scoped>

</style>
