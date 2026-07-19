<template xmlns='http://www.w3.org/1999/html'>
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="hieu_ung_popup" ref="self_PopupThuHoiCATaoLoi" :position="position" :header="'Thu hồi chứng thư tạo lỗi'" showCloseIcon="true" width="50%" target="#app .main-wrapper">
    <div class="">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" @click="btnGhiLai_Click">
              <span class="icon one-save"></span> Ghi lại
            </a>
          </li>
          <li>
            <input
              style="display: none;"
              type="file"
              accept="audio/*, video/*, image/* ,application/pdf, application/vnd.ms-excel, application/vnd.ms-excel.sheet.macroEnabled.1, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, text/xml"
              multiple="multiple"
              class="file"
              ref="file_thuhoi_calois"
              @change="btnUploadFile_Handler"
            />
            <a href="javascript:;" @click="upfile_click">
              <span class="icon one-upload"></span> Up hồ sơ
            </a>
          </li>
          <li>
            <a href="javascript:;" @click="btnXoaHD_Click">
              <span class="icon one-remove-outline"></span> Xóa HĐ
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class='row'>
          <div class='col-sm-3'>
            <div class="box-form">
              <strong>Mã giao dịch (mã giao dịch thu hồi lỗi): </strong> <span style='color: red'>{{magd_th}}</span> </br>
              <strong>Mã thuê bao (mã thu hồi tạo lỗi): </strong> <span style='color: red'>{{matb_th}}</span></br>
            </div>
          </div>
          <div class='col-sm-9'>
            <div class="box-form">
              <table class="table table-bordered table-striped">
                <tbody>
                <tr>
                  <th scope="row">Username</th>
                  <td>{{this.dulieu.tskt_dvcntt[0].username}}</td>
                </tr>
                <tr>
                  <th scope="row">Serial number</th>
                  <td>{{this.dulieu.tskt_dvcntt[0].serial}}</td>
                </tr>
                <tr>
                  <th scope="row">O</th>
                  <td>{{this.dulieu.tskt_dvcntt[0].website}}</td>
                </tr>
                <tr>
                  <th scope="row">OU</th>
                  <td>{{this.dulieu.tskt_dvcntt[0].danhmuc_cn}}</td>
                </tr>

                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import api from './api'
import { mapActions, mapState } from 'vuex'
import { CompleteProfileITVAS } from '../../../../../../store/CompleteProfileITVAS'

export default {
  name: 'popPopupThuHoiCATaoLoi',
  data(){
    return {
      dulieu: [],
      hieu_ung_popup: { effect: 'Zoom' },
      position: { X: 'center', Y: 'center' },
      matb_th: "",
      magd_th: "",
      isShow: false
    }
  },
  computed: {
    ...mapState(
      {
        tskt_dvcntt: state => state.CompleteProfileITVAS.tskt_dvcntt,
        thongtin_hdtb: state => state.CompleteProfileITVAS.thongtin_hdtb
      }
    )
  },
  methods: {

    async btnGhiLai_Click()
    {
      let self = this;
      this.loading(true)
      try {
        let huonggiao_tn = 20054; // tiep nhan thanh ly CA toan trinh
        let params = {
          "vhdtb_id_cu": self.dulieu.hdtb_id,
          "vhuonggiao_id": huonggiao_tn,
          "vmay_cn": self.dulieu.may_cn,
          "vip_cn": self.dulieu.ip_cn,
          "vnguoi_cn": self.dulieu.nguoi_cn
        }
        let taohd = await api.capnhat_hd_thuhoi_ca_loi(params);
        if(taohd.data.error_code != "BSS-00000000")
        {
          throw new Error(taohd.data.message_detail);
        }
        let kq_obj = JSON.parse(taohd.data.data);
        if (kq_obj.message != "ok")
          throw  new Error(kq_obj.message);

        this.matb_th = kq_obj.ma_tb;
        this.magd_th = kq_obj.ma_gd;
        this.hdkh_id = kq_obj.hdkh_id;
        this.hdtb_id = kq_obj.hdtb_id;
        this.$toast.success("Nhập hợp đồng thành công");
      }
      catch (e)
      {
        let loi = e.data?e.data.message_detail:e.message || "lỗi";
        this.$toast.error(loi);
      }
      finally {
        this.loading(false)
      }
    },
    btnXoaHD_Click()
    {
      let self = this;
      if(this.hdkh_id == 0){
        this.$toast.error('Không có hợp đồng để thực hiện!')
        return;
      }
      this.$bvModal.msgBoxConfirm('Bạn có chắc là sẽ xóa hợp đồng?', {
        title: 'Thông báo',
        size: 'md',
        okTitle: 'Yes',
        cancelTitle: 'No',
      }).then(async v => {
        if(!v) {
          return;
        }
        try{
          this.loading(true);
          let kt_xoa_pr = {
            hdkh_id: self.hdkh_id
          }
          let kq_xoa = await api.kt_xoa_hd_cntt(kt_xoa_pr);
          if(kq_xoa.data.error_code != "BSS-00000000")
          {
            throw new Error(kq_xoa.data.message_detail || kq_xoa.data.message);
          }
          if(kq_xoa.data.data != "ok")
          {
            throw new Error(kq_xoa.data.data);
          }
          let params = {
            'hdkh_id': self.hdkh_id,
            'loaihd_id': 4
          };
          let kq = await api.xoa_hdkh_v2(params);
          if (kq.data.error_code != "BSS-00000000")
          {
            throw new Error(kq.data.message_detail || kq.data.message);
          }
          self.matb_th = '';
          self.magd_th = '';
          self.hdkh_id = 0;
          self.hdtb_id = 0;
          this.$toast.success("Xóa hợp đồng thành công");

        }
        catch (e)
        {
          console.log(e);
          let loi = e.data?e.data.message_detail:e.message || "Lỗi xóa hợp đồng";
          this.$toast.error(loi)
        }
        finally {
          this.loading(false)
        }
      });

    },
    async btnUploadFile_Handler()
    {
      try {
        let self = this;
        let formData = new FormData()
        for (var file of this.$refs.file_thuhoi_calois.files) {
          formData.append('files', file)
        }
        console.log(formData);
        let fileUrls = await api.upload_file(formData, { 'Content-Type': 'multipart/form-data' });
        if (fileUrls.data.error_code != "BSS-00000000")
        {
          throw new Error(fileUrls.data.message);
        }
        console.log(fileUrls);
        if (fileUrls.data.data[0].length > 0)
        {
          let file_name =  fileUrls.data.data[0].replace(/^.*[\\/]/, '');
          let uphs = await  api.capnhat_file_hs(
            {
              vurl: fileUrls.data.data[0],
              vmay_cn: self.dulieu.may_cn,
              vnguoi_cn: self.dulieu.nguoi_cn,
              vip_cn: self.dulieu.ip_cn,
              vfile_name: file_name,
              vloaifile_id: 30, // xac nhan thu hoi tao loi
              vma_tb: self.dulieu.ma_tb,
              vhdtb_id: self.dulieu.hdtb_id,
              vma_kh: '',
              vma_gd: self.dulieu.ma_gd,
              vghichu: "Xác nhận thu hồi CTS tạo lỗi cho mã giao dịch: " + self.dulieu.ma_gd
            }
          );
          if (uphs.data.error_code != "BSS-00000000")
          {
            throw new Error(uphs.data.message_detail);
          }
          console.log(uphs);
        }
        console.log(fileUrls);
        this.$toast.success("Up hồ sơ thành công");
      }
      catch (e)
      {
        let loi = e.data?e.data.message_detail:e.message || "lỗi";
        this.$toast.error(loi);
      }

    },
    async loadhopdong()
    {
      let self = this;
      this.loading(true);
      try {
        console.log(self.dulieu);
        let kq = await api.load_hopdong_thuhoi_taoloi({
          hdtb_cha_id: self.dulieu.hdtb_id
        });
        self.matb_th = '';
        self.magd_th = '';
        self.hdkh_id = 0;
        self.hdtb_id = 0;
        /*if (kq.data.error_code == 'BSS-00000204')
        {
          self.matb_th = '';
          self.magd_th = '';
          self.hdkh_id = 0;
          self.hdtb_id = 0;
          return;
        }*/

        if (kq.data.error_code != 'BSS-00000000')
          throw new Error(kq.data.message_detail || kq.data.message);

        console.log(kq.data.data);
        self.hdtb_id = kq.data.data[0].hdtb_id;
        self.hdkh_id = kq.data.data[0].hdkh_id;
        self.matb_th = kq.data.data[0].ma_tb;
        self.magd_th = kq.data.data[0].ma_gd;
      }
      catch (e)
      {
        let loi = e.data?e.data.message_detail: e.message || "lỗi load hợp đồng";
        this.$toast.error(loi)
      }
      finally {
        this.loading(false);
      }
    },
    upfile_click()
    {
      this.$refs.file_thuhoi_calois.click()
    },
    async openDialog() {
      let self = this;
      this.dulieu = {
        ...self.thongtin_hdtb,
        tskt_dvcntt: {
          ...self.tskt_dvcntt
        }
      }
      await self.loadhopdong();
      //this.isShow = true;
      this.$refs.self_PopupThuHoiCATaoLoi.show();
    }
  }
}
</script>

<style scoped>

</style>
