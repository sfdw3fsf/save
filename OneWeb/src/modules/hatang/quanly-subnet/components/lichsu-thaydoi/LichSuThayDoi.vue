<template>
  <DataGrid
    :allowPaging="true"
    :enablePagingServer="false"
    :showColumnCheckbox="false"
    :dataSource="dataSource"
    :columns="columns"
  ></DataGrid>
</template>

<script>
export default {
  name: 'LichSuThayDoiSubnetTab',
  data() {
    return {
      columns: [
        { fieldName: 'stt', headerText: 'STT', width: 60 },
        { fieldName: 'tenTacDong', headerText: 'Tên tác động', width: 180 },
        { fieldName: 'truongThayDoi', headerText: 'Trường thông tin thay đổi', width: 220 },
        { fieldName: 'giaTriCu', headerText: 'Giá trị cũ', width: 160 },
        { fieldName: 'giaTriMoi', headerText: 'Giá trị mới', width: 160 },
        { fieldName: 'nguoiThucHien', headerText: 'Người thực hiện', width: 160 },
        { fieldName: 'thoiDiem', headerText: 'Thời điểm', width: 180 }
      ],
      dataSource: [
        {
          stt: 1,
          tenTacDong: 'Cập nhật thông tin',
          truongThayDoi: 'Gateway',
          giaTriCu: '10.10.1.1',
          giaTriMoi: '10.10.1.254',
          nguoiThucHien: 'Nguyễn Văn A',
          thoiDiem: '2025-01-10 14:32'
        },
        {
          stt: 2,
          tenTacDong: 'Cập nhật thông tin',
          truongThayDoi: 'Subnet',
          giaTriCu: '10.10.1.0/24',
          giaTriMoi: '10.10.2.0/24',
          nguoiThucHien: 'Trần Quốc B',
          thoiDiem: '2025-01-09 09:12'
        },
        {
          stt: 3,
          tenTacDong: 'Thay đổi trạng thái',
          truongThayDoi: 'CCDV',
          giaTriCu: 'chưa cung cấp',
          giaTriMoi: 'đã cung cấp',
          nguoiThucHien: 'Huỳnh Dương C',
          thoiDiem: '2025-01-07 16:45'
        },
        {
          stt: 4,
          tenTacDong: 'Cập nhật VLAN',
          truongThayDoi: 'VLAN',
          giaTriCu: 'VLAN10',
          giaTriMoi: 'VLAN20',
          nguoiThucHien: 'Lê Văn D',
          thoiDiem: '2025-01-06 11:21'
        },
        {
          stt: 5,
          tenTacDong: 'Đổi hạ tầng mạng',
          truongThayDoi: 'Hạ tầng mạng',
          giaTriCu: 'HTM_beo01',
          giaTriMoi: 'HTM_beo02',
          nguoiThucHien: 'Phạm Minh E',
          thoiDiem: '2025-01-05 08:50'
        },
        {
          stt: 6,
          tenTacDong: 'Chỉnh sửa Subnet con',
          truongThayDoi: 'Tên Subnet',
          giaTriCu: 'Net_Srv_01',
          giaTriMoi: 'Net_Srv_Main',
          nguoiThucHien: 'Trương Quốc F',
          thoiDiem: '2025-01-03 10:08'
        },
        {
          stt: 7,
          tenTacDong: 'Thêm mới',
          truongThayDoi: 'Subnet con',
          giaTriCu: '-',
          giaTriMoi: '10.20.30.0/24',
          nguoiThucHien: 'Nguyễn Văn G',
          thoiDiem: '2025-01-02 17:40'
        },
        {
          stt: 8,
          tenTacDong: 'Xóa subnet',
          truongThayDoi: 'Subnet',
          giaTriCu: '10.50.1.0/24',
          giaTriMoi: '-',
          nguoiThucHien: 'Lê Minh H',
          thoiDiem: '2025-01-02 15:10'
        }
      ]
    }
  },

  props: {
    mode: {
      type: String,
      default: 'VIEW'
    }
  }
}
</script>
