<template>
  <main class="hatang-smart-cloud-wrapper">
    <div class="legend-title">
      <span>Danh sách hạ tầng smart cloud</span>
    </div>

    <DataGrid
      :allowPaging="true"
      :enablePagingServer="false"
      :showColumnCheckbox="false"
      :dataSource="dataSource"
      :columns="columns"
    ></DataGrid>
  </main>
</template>

<script>
export default {
  name: 'HaTangAoHoaTab',
  data() {
    return {
      columns: [
        { fieldName: 'stt', headerText: 'STT', width: 60 },
        { fieldName: 'maHaTang', headerText: 'Mã hạ tầng', width: 120 },
        { fieldName: 'tenHaTang', headerText: 'Tên hạ tầng', width: 180 },
        { fieldName: 'loaiHaTang', headerText: 'Loại hạ tầng', width: 150 },
        { fieldName: 'idc', headerText: 'IDC', width: 140 },
        { fieldName: 'nguoiQuanLy', headerText: 'Người quản lý', width: 150 },
        { fieldName: 'donViQuanLy', headerText: 'Đơn vị quản lý', width: 150 },
        { fieldName: 'trangThaiCcdv', headerText: 'Trạng thái CCDV', width: 180 },
        { fieldName: 'actions', headerText: 'Thao tác', width: 120, allowSorting: false }
      ],

      dataSource: [
        {
          stt: 1,
          maHaTang: 'SM_01',
          tenHaTang: 'Smart cloud beo',
          loaiHaTang: 'Smart cloud',
          idc: 'IDC_NTL',
          nguoiQuanLy: 'Nguyễn Văn A',
          donViQuanLy: 'cloud',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 2,
          maHaTang: 'SM_02',
          tenHaTang: 'VMWare Cluster 02',
          loaiHaTang: 'Cloud server',
          idc: 'IDC_HCM',
          nguoiQuanLy: 'Trần Quốc B',
          donViQuanLy: 'hạ tầng',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 3,
          maHaTang: 'BK_01',
          tenHaTang: 'Backup System 01',
          loaiHaTang: 'Backup',
          idc: 'IDC_HN',
          nguoiQuanLy: 'Lê Văn C',
          donViQuanLy: 'backup',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 4,
          maHaTang: 'IDG_01',
          tenHaTang: 'IDG Storage',
          loaiHaTang: 'IDG',
          idc: 'IDC_DN',
          nguoiQuanLy: 'Đỗ Mạnh D',
          donViQuanLy: 'storage',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 5,
          maHaTang: 'SM_05',
          tenHaTang: 'Smart cloud zone A',
          loaiHaTang: 'Smart cloud',
          idc: 'IDC_NTL',
          nguoiQuanLy: 'Nguyễn Văn E',
          donViQuanLy: 'cloud',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 6,
          maHaTang: 'BK_02',
          tenHaTang: 'Backup Volume 2',
          loaiHaTang: 'Backup',
          idc: 'IDC_HCM',
          nguoiQuanLy: 'Huỳnh Dương F',
          donViQuanLy: 'backup',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 7,
          maHaTang: 'SM_07',
          tenHaTang: 'Smart cloud test',
          loaiHaTang: 'Smart cloud',
          idc: 'IDC_HN',
          nguoiQuanLy: 'Phan Văn G',
          donViQuanLy: 'cloud',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        }
      ]
    }
  }
}
</script>
