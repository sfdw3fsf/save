<template src="./frmHoSoKhieuNai.html">
    
</template>
<script>
import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom";
import VueDateExt from "@/modules/contract/setup/DeclareLandline/components/VueDateExt";
import moment from "moment";
import {
  SERVER_HSKN,
  DatabaseConstants
} from "@/const/enums";

export default {
    components: {
        Select2Ext,
        DataGridCustom,
        VueDateExt
    },
    data() {
        return {
            colums2: [
                {
                    fieldName: "stt",
                    headerText: "Stt",
                    allowFiltering: true,
                    allowSorting: true,
                    width: "auto",
                },
                {
                    fieldName: "ngay_hs",
                    headerText: "Ngày hồ sơ",
                    allowFiltering: true,
                    allowSorting: true,
                    width: "auto",
                },
                {
                    fieldName: "server_url",
                    headerText: "Đường dẫn Sever",
                    allowFiltering: true,
                    allowSorting: true,
                    width: "auto",
                },
                {
                    fieldName: "nguoi_cn",
                    headerText: "Người CN",
                    allowFiltering: true,
                    allowSorting: true,
                    width: "auto",
                },
            ],
            colums: [
                {
                    fieldName: "ma_kn",
                    headerText: "Mã KN",
                    allowFiltering: true,
                    allowSorting: true,
                    width: "auto",
                },
                {
                    fieldName: "ma_tb",
                    headerText: "Số máy/Account",
                    allowFiltering: true,
                    allowSorting: true,
                    width: "auto",
                },
                {
                    fieldName: "nguoi_kn",
                    headerText: "Người khiếu nại",
                    allowFiltering: true,
                    allowSorting: true,
                    width: "auto",
                },
                {
                    fieldName: "dienthoai_lh",
                    headerText: "Điện thoại",
                    allowFiltering: true,
                    allowSorting: true,
                    width: "auto",
                },
                {
                    fieldName: "tenchude",
                    headerText: "Chủ đề khiếu nại",
                    allowFiltering: true,
                    allowSorting: true,
                    width: "auto",
                },
                {
                    fieldName: "ten_nv",
                    headerText: "Người tiếp nhận",
                    allowFiltering: true,
                    allowSorting: true,
                    width: "auto",
                },
            ],
            tt_nd : {},
            tctn : {},
            cm : {},
            quanlyhs : {},
            dsHSKN : {},
            bangts : {},
            dtList: [],
            khieunai : {},
            tchopdong : {},
            fname : "",
            f_down_name : "",
            f_down_url : "",
            fname_bs : "",
            f_down_name_bs : "",
            f_down_url_bs : "",
            path : "",
            IP : "",
            flag : false,
            hosoid : 0,
            tthd_id : 0,
            tths_id: 0,
            hsbs_id : 0,
            hs_id : 0,
            tthsbs_id : 0,
            vma_gd : 0,
            vloaihdid:0,
            vhosoid:0,
            vhsbsid :0,
            hosokn_id : 0,
            khieunai_id : 0,
            ma_tb : "0",
            HopDong : 0, //1: la hop dong, 0: la phu luc
        }
    },
    methods: {
        async UpLoad(data) {
            await this.axios.post('/web-quantri/upload', data,{
                headers: { "Content-Type": "multipart/form-data" },
            })
        },
        ChonFile() {
            console.log(this.$refs.file_upload)
            this.$refs.file_upload.click();
        },
        confirmChonFile() {
            this.$refs.txtFileName.value = this.$refs.file_upload.value.split("\\").pop();
            console.log(this.$refs.file_upload.value)
        },
        show() {
            this.$refs.frmHoSoKhieuNai.show()
            this.frmHoSoKhieuNai_Load()
        }
        ,frmHoSoKhieuNai_Load()
        {
            this.SetButton(-1);
            //this.$refs.txtIpServer.value = DatabaseConstants.Report_Server;
            this.$refs.txtIpServer.value = SERVER_HSKN.IP;
            this.HienThiDanhSach();
            
        }

        //region Set button
        ,SetButton(kieu)
        {
            this.$refs.tsbtnNhapMoi.classList.add('disabled')
            this.$refs.tsbtnGhiLai.classList.add('disabled')
            this.$refs.tsbtnXoa.classList.add('disabled')
            this.$refs.tsbtnDownload.classList.add('disabled')
            
            if (kieu == -1)//Bat dau
            {
                this.$refs.txtMaTB.focus();
                this.$refs.tsbtnGhiLai.classList.remove('disabled')
            }

            if (kieu == 0)//Bat dau
            {
                this.$refs.txtMaTB.focus();
                this.$refs.tsbtnNhapMoi.classList.remove('disabled')
                Clear();
            }

            if (kieu == 1)//Them moi
            {
                this.$refs.txtMaTB.focus();
                this.$refs.tsbtnGhiLai.classList.remove('disabled')
                this.$refs.txtFileName.value = "";
                this.$refs.dtpNgayHS.value = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
                this.tths_id = 1;
                //Clear();
            }

            if (kieu == 3)//Edit
            {
                this.$refs.txtMaTB.focus();
                this.$refs.tsbtnNhapMoi.classList.remove('disabled')
                this.$refs.tsbtnXoa.classList.remove('disabled')
                this.$refs.tsbtnGhiLai.classList.remove('disabled')                
                this.$refs.tsbtnDownload.classList.remove('disabled')
            }
            if (kieu == 4)//xoa
            {
                this.$refs.txtMaTB.focus();
                this.$refs.tsbtnNhapMoi.classList.remove('disabled')
                this.$refs.txtFileName.value = "";
            }
        }
        //endregion

        //region Clear
        ,Clear()
        {
            this.$refs.txtMaTB.value = "";
            txtTenTB.value = "";
            txtDiaChiTB.value = "";
            this.$refs.txtFileName.value = "";
            this.$refs.dtpNgayHS.value = tt_nd.ngay_cn;            
            this.$refs.dgv_HoSo.dataSource = null;
        }
        //endregion

        ,tsbtnNhapMoi_Click()
        {
            this.SetButton(1);
        }

        ,tsbtnGhiLai_Click()
        {
            this.CapNhat();
        },

        //region Cap nhat
        KiemTraDuLieu(themmoi)
        {
            if (this.$refs.txtFileName.value == "")
            {
                this.$toast.error("Bạn chưa nhập đường dẫn file!");
                this.$refs.txtFileName.focus();
                return false;
            }
            return true;
        }
        //region TaoDuLieu
        ,async TaoDuLieu(themmoi, url)
        {
            try
            {   return new Promise(async (resolve, reject) => {
                    try {
                        this.dsHSKN = [];
                        let row = {}
                        if (themmoi)
                        {
                            let rs = await this.axios.post("web-hopdong/khoiphucthanhly/get_keys", {
                                "keyname": "HOSOKN_ID",
                            })
                            this.hosokn_id = Number(rs.data.data)
                        }
                        row.HOSOKN_ID = this.hosokn_id;
                        row.KHIEUNAI_ID = this.khieunai_id;
                        row.TEN_FILE = this.fname;
                        //row.SERVER_URL = "PTTB_SERVER\\HOSO_KN\\" + this.$refs.txtMaTB.value + "\\" + khieunai_id.toString() + "\\" + fname;
                        row.SERVER_URL = url;  
                        row.IP_SERVER = this.$refs.txtIpServer.value;
                        row.CLIENT_URL = this.$refs.txtFileName.value;
                        row.NGAY_HT = this.$refs.dtpNgayHS.value;
                        row.MAY_CN = this.$root.token.getUserName();
                        row.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
                        row.NGUOI_CN = this.$root.token.getUserName();
                        this.dsHSKN.push(row);
                        resolve(true);
                    }catch(err) {
                        reject(false)
                    }
                });
                
            }
            catch (err)
            {
                console.log(err)
                this.$toast.error("Có lỗi: " + err.message);
            }
        }
        //endregion
        ,async CapNhat()
        {
            try
            {
                let url
                if (this.khieunai_id == 0)
                {
                    this.$toast.error("Không có khiếu nại để upload hồ sơ !");
                    return;
                }
                    if (!(this.$refs.txtFileName.value))
                    {
                        this.$toast.error("Bạn chưa chọn đường dẫn đến file !");
                        return;
                    }

                    if (!(this.$refs.dtpNgayHS.value))
                    {
                        this.$toast.error("Ngày hồ sơ không được để trống !");
                        return;
                    }
                    // if (!File.Exists(this.$refs.txtFileName.value))
                    // {
                    //     this.$toast.error("File không tồn tại hoặc đã bị xóa !");
                    //     SetButton(3);
                    //     return;
                    // }
                    this.fname = this.$refs.txtFileName.value
                    //fname = khieunai_id.toString() + "\\" + fname;
                    
                    this.flag = true;
                    if (this.flag)
                    {
                        if (!this.KiemTraDuLieu(true)) return;
                        if (this.$refs.tsbtnNhapMoi.classList.contains("disabled"))
                        {
                            // Upload("FILES/" + DatabaseConstants.DB2.substring(4, 3) + "/HOSO_KN/" + this.$refs.txtMaTB.value + "/" + khieunai_id.toString());
                            let files = this.$refs.file_upload.files
                            console.log(files)
                            if(files.length > 0){
                                let formData = new FormData();
                                formData.append("files", files[0]);
                                let rs = await this.axios.post('/web-quantri/upload', formData,{
                                    headers: { "Content-Type": "multipart/form-data" },
                                })
                                url = rs.data.data[0]
                            } else {
                                let selected = this.$refs.dgv_HoSo.getSelectedRecords()
                                url = selected[0].server_url
                            }
                            await this.TaoDuLieu(true, url);
                            // khieunai.Insert_HSKN(dsHSKN);
                            
                            await this.axios.post("web-gqkn/TraCuuPhieuKN/sp_insert_hoso_khieunai", {
                                para: JSON.stringify(this.dsHSKN)
                            })
                            this.$toast.success("Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!");
                            this.HT_DanhSach_HS();
                            this.SetButton(3);
                        }
                        else
                        {
                            // int index = Convert.ToInt32(gridview_HoSo.FocusedRowHandle);
                            // if (tt_nd.ma_nd != gridview_HoSo.GetRowCellValue(index, "NGUOI_CN").toString())
                            // {
                            //     this.$toast.error("Bạn không có quyền sửa hồ sơ khiếu nại này!");
                            //     return;
                            // }
                            // DeleteUploadedFile(gridview_HoSo.GetRowCellValue(index, "SERVER_URL").toString());
                            // Upload("FILES/" + DatabaseConstants.DB2.substring(4, 3) + "/HOSO_KN/" + this.$refs.txtMaTB.value + "/" + khieunai_id.toString());
                            let files = this.$refs.file_upload.files
                            let formData = new FormData();
                            formData.append("files", files[0]);
                            if(files.length > 0) {
                                let rs = await this.axios.post('/web-quantri/upload', formData,{
                                    headers: { "Content-Type": "multipart/form-data" },
                                })
                                url = rs.data.data[0]
                            } else {
                                let selected = this.$refs.dgv_HoSo.getSelectedRecords()
                                url = selected[0].server_url 
                            }
                            //this.UpLoad(formData)
                            await this.TaoDuLieu(false, url);
                            // khieunai.Update_HSKN(dsHSKN);
                            await this.axios.post("web-gqkn/TraCuuPhieuKN/sp_update_hoso_khieunai", {para: JSON.stringify(this.dsHSKN)})
                            this.$toast.success("Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!");
                            this.HienThiDanhSach();
                            this.HT_DanhSach_HS();
                            // gridview_HoSo_FocusedRowChanged();
                        }
                    }
                    else
                    {
                        this.$toast.error("Upload file " + fname + " không thành công.");
                    }
            }
            catch (err)
            {
                this.$toast.error("Có lỗi: " + err.message);
            }
        }

        ,DeleteUploadedFile(prevPath)
        {
            try
            {
                // str = SERVER_HSKN.URL + @"&action=delete&filePath=" + prevPath;
                // System.Net.HttpWebRequest request = (System.Net.HttpWebRequest)System.Net.WebRequest.Create(str);
                // request.GetResponse();
                // flag = true;
            }
            catch (err)
            {
                this.$toast.error("Không kết nối được tới Server. Lỗi: " + err.message);
                this.flag = false;
            }
        }

        //endregion

        //region HienThiDanhSach
        ,async HienThiDanhSach()//(matb)
        {
            this.loading(true)
            try
            {
                let rs
                let ds = [];
                let admin = localStorage.getItem("admin")
                let kyhoadon
                let data = JSON.parse(admin)

                if ((data && !data.chukyno) || !data) {
                    kyhoadon = moment(new Date()).add(-1, 'months').format("YYYYMM01")
                } else {
                    kyhoadon = data.chukyno;
                }
                rs = await this.axios.post("web-gqkn/TraCuuPhieuKN/lay_thongtintb_khieunai_id", {
                    "vkhieunai_id": this.khieunai_id,
                    "vkyhoadon": kyhoadon,
                    "vma_tb": this.ma_tb
                })
                ds = rs.data.data;
                if (ds.length > 0)
                {
                    this.$refs.dgvDS_KHIEUNAI.dataSource = ds
                }
                else
                {
                    this.$refs.dgvDS_KHIEUNAI.dataSource = null;
                }
            }
            catch (err)
            {
                console.log(err)
                this.$toast.error("Có lỗi: " + err.message);
            }
            this.loading(false)
        }
        //endregion

        //region HT_DanhSach_HS
        ,async HT_DanhSach_HS()//(matb)
        {
            try
            {
                let ds = [];
                let admin = localStorage.getItem("admin")
                let kyhoadon
                let data = JSON.parse(admin)

                if ((data && !data.chukyno) || !data) {
                    kyhoadon = moment(new Date()).add(-1, 'months').format("YYYYMM01")
                } else {
                    kyhoadon = data.chukyno;
                }
                let rs = await this.axios.post("web-gqkn/TraCuuPhieuKN/lay_thongtin_hs_kn", {
                    "vkhieunai_id": this.khieunai_id,
                    "vkyhoadon": kyhoadon
                })
                ds = rs.data.data
                //DataTable dtHs = ds
                if (ds.length > 0)
                {
                    //dgvFile.AutoGenerateColumns = false;
                    //dgvFile.dataSource = ds
                    this.$refs.dgv_HoSo.dataSource = ds
                    this.SetButton(3);
                    //Cat xu ly ngay 24/12/2019 do su co IT360-62305
                }
                else
                {
                    //dgvFile.dataSource = null;
                    this.$refs.dgv_HoSo.dataSource = null;
                    this.SetButton(-1);
                    this.$refs.txtFileName.value = "";
                }
            }
            catch (err)
            {
                this.$toast.error("Có lỗi: " + err.message);
            }
        }
        //endregion

        ,async tsbtnDownload_Click()
        {
            console.log(this.f_down_url)
            console.log(this.f_down_name)
            if (!this.f_down_name || !this.f_down_url)
            {
                this.$toast.error("Khách hàng chưa có hồ sơ nào !");
                return;
            }

            //Cat xu ly ngay 24/12/2019 do su co IT360-62305
            try
            {

                //var url = "http://" + SERVER_HSKN.IP + "/" + this.f_down_url;
                let rs = await this.axios.post("/web-quantri/upload/link_view_file", {
                    list_file: this.f_down_url
                })

                window.open(rs.data.data[0].file_url, "_blank");
                // var fileName =  this.f_down_name;
                // if (url == "") return;

                //     let localFileName;
                
                //     var digSave = new SaveFileDialog {FileName = fileName};
                //     if (digSave.ShowDialog() != DialogResult.OK) return;
                //     if (!(digSave.FileName))
                //     {
                //         this.$toast.error("Chưa đặt tên file");
                //     }
                    // localFileName = digSave.FileName;
                
                
                // var digDownload = new frmDownloadDialog
                //                       { 
                //     fURL = url,
                //     fDes = localFileName,
                //     fName = localFileName
                // };
                
                // digDownload.Start();
        
            }
            catch(err)
            {
                this.$toast.error("Có lỗi trong quá trình dowload và mở file " + err.message);
            }
    
        }



        ,txtMaTB_KeyPress()
        {
            if (e.KeyChar == Convert.ToChar(Keys.Enter))
            {
                if (this.$refs.txtMaTB.value.Trim() != "")
                {
                    ma_tb = this.$refs.txtMaTB.value.Trim();
                    HienThiDanhSach();
                }
                this.$refs.txtMaTB.focus();
            }
        }

        ,dgvDSKhieuNai_SelectionChanged()
        {
            //try
            //{
            //    if (dgvDSKhieuNai.Rows.Count > 0)
            //    {
            //        int nIndex = dgvDSKhieuNai.CurrentRow.Index;
            //        //this.$toast.error(dtList.Rows[nIndex]["khieunai_id"].toString());
            //        this.$refs.txtMaTB.value = dtList.Rows[nIndex]["ma_tb"].toString();
            //        txtTenTB.value = dtList.Rows[nIndex]["ten_tb"].toString();
            //        txtDiaChiTB.value = dtList.Rows[nIndex]["diachi_tb"].toString();
            //        txtNguoiKN.value = dtList.Rows[nIndex]["nguoi_kn"].toString();                    
            //        txtDienThoaiLH.value = dtList.Rows[nIndex]["dienthoai_lh"].toString();
            //        txtTuNgay.value = dtList.Rows[nIndex]["hengq_tu"].toString();
            //        txtDenNgay.value = dtList.Rows[nIndex]["hengq_den"].toString();
            //        khieunai_id = Convert.ToInt64(dtList.Rows[nIndex]["khieunai_id"].toString());
            //        HT_DanhSach_HS();
            //    }
            //}
            //catch (err)
            //{
            //    this.$toast.error("Có lỗi: " + ex.toString());
            //}
        }

        ,btnDuongDan_Click()
        {
            
            this.openFileDialog1.ShowDialog();
        }

        ,openFileDialog1_FileOk()
        {
            // if (Path.GetExtension(openFileDialog1.FileName) != "")
            //     this.$refs.txtFileName.value = openFileDialog1.FileName;
            // else
            // {
                this.$toast.error("Định dạng file không hợp lệ.");
            // }
        }

        ,dgvFile_SelectionChanged()
        {
            //try
            //{
            //    if (dgvDSKhieuNai.Rows.Count > 0)
            //    {
            //        //int nIndex = dgvFile.CurrentRow.Index;
            //        //this.$toast.error(dtList.Rows[nIndex]["khieunai_id"].toString());
            //        this.$refs.txtFileName.value = dgvFile.CurrentRow.Cells["clienturl"].value.toString();
            //        hosokn_id = Convert.ToInt64(dgvFile.CurrentRow.Cells["hoso_id"].value.toString());
            //        fname = f_down_name = dgvFile.CurrentRow.Cells["tenfile"].value.toString();
            //        f_down_url = dgvFile.CurrentRow.Cells["serverurl"].value.toString(); 
            //    }
            //}
            //catch (err)
            //{
            //    this.$toast.error("Có lỗi: " + ex.toString());
            //}
        }

        ,tsbtnXoa_Click()
        {
            try
            {
                if (this.$refs.dgv_HoSo.dataSource.length > 0)
                {
                    let selected = this.$refs.dgv_HoSo.getSelectedRecords()
                    let item = selected[0]
                    if (this.$root.token.getUserName() != item.nguoi_cn)
                    {
                        this.$toast.error("Bạn không có quyền xóa hồ sơ khiếu nại này!");
                        return;
                    }
                    this.$confirm("Bạn chắc chắn muốn xóa hồ sơ khiếu nại này không?", {
                    confirmButtonText: "Có",
                    cancelButtonText: "Không",
                    type: "success",
                    }).then(async () => {
                        await this.axios.post("web-gqkn/TraCuuPhieuKN/sp_xoa_hoso_khieunai", {
                            "v_hosokn_id": item.hoso_id
                        })
                        this.$toast.success("Xóa hồ sơ thành công !");
                        this.HT_DanhSach_HS();
                        if (this.$refs.dgv_HoSo.dataSource.length > 0)
                            this.SetButton(3);
                        else
                            this.SetButton(-1);
                    });
                    
                }
            }
            catch (err)
            {
                this.$toast.error("Có lỗi : " + err.message);
            }
        }

        ,saveFileDialog1_FileOk()
        {
            // f_down_name = saveFileDialog1.FileName;
            // backgroundWorker1.RunWorkerAsync();
        }

        ,backgroundWorker1_DoWork_1()
        {
            // frmDownload f = new frmDownload();
            // f.fURL = SERVER_HSKN.IP + @"/" + f_down_url;//this.$refs.txtIpServer.value + "\\" + f_down_url;
            // f.fName = f_down_name;
            // f.fDes = f_down_name;
            // f.ShowDialog();
            // flag = f.chapnhan;  
        }

        ,backgroundWorker1_RunWorkerCompleted_1()
        {
            if (this.flag)
            {
                // System.Diagnostics.Process.Start(f_down_name);                
            }
            else
            {
                this.$toast.error("Download file không thành công !");
                return;
            }
        }

        ,gridviewDS_KHIEUNAI_FocusedRowChanged()
        {
            gridviewDS_KHIEUNAI_FocusedRowChanged();
        }

        ,gridviewDS_KHIEUNAI_FocusedRowChanged()
        {
            try
            {
                if (this.$refs.dgvDS_KHIEUNAI.dataSource.length < 0) return;
                let selected = this.$refs.dgvDS_KHIEUNAI.getSelectedRecords()
                if(selected.length > 0) {
                    let item = selected[0]
                    console.log(item, "vào đây cmnl")
                    this.khieunai_id = item.khieunai_id
                    this.$refs.txtMaTB.value = item.ma_tb
                    this.$refs.txtTenTB.value = item.ten_tb
                    this.$refs.txtDiaChiTB.value = item.diachi_tb
                    this.$refs.txtNguoiKN.value = item.nguoi_kn
                    this.$refs.txtDienThoaiLH.value = item.dienthoai_lh
                    this.$refs.txtTuNgay.value = item.hengq_tu
                    this.$refs.txtDenNgay.value = item.hengq_den
                    this.HT_DanhSach_HS();
                }
            }
            catch (err)
            {
                console.log(err)
                this.$toast.error("Có lỗi : " + err.message);
            }
        }

        ,gridview_HoSo_FocusedRowChanged()
        {
            gridview_HoSo_FocusedRowChanged();
        }

        ,gridview_HoSo_FocusedRowChanged()
        {
            try
            {
                if (this.$refs.dgv_HoSo.dataSource.length <= 0) return;
                let selected = this.$refs.dgv_HoSo.getSelectedRecords()
                if(selected.length > 0) {
                    let item = selected[0]
                    this.$refs.txtFileName.value = item.client_url
                    this.$refs.dtpNgayHS.value = item.ngay_hs
                    this.hosokn_id = item.hoso_id
                    this.fname = item.tenfile
                    this.f_down_url = item.server_url
                    this.f_down_name = item.tenfile
                }
            }
            catch (err)
            {
                this.$toast.error("Có lỗi : " + err.message);
            }
        }
    }
}
</script>

<style>
    .legend-title {
        font-family: 'Roboto' !important;
    }
    a.disabled {
  color: #d3d3d3 !important;
  pointer-events: none;
  cursor: not-allowed;
  opacity: 0.6;
}

a {
  cursor: pointer;
}
</style>