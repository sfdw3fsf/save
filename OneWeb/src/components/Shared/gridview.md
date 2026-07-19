# Gridview
Author: thangdm1@vnpt.vn

## Cách sử dụng:
Ở trang vue, chèn vào sau thẻ mở script  
`import gridview from '@/components/Shared/gridview'`  
Sau `export default {` chèn thêm vào components:  
`components: { gridview },`  
Ở phần template / trang html, chèn với cấu trúc sau:  
`<gridview id="table1" :datalist="listbox.ds_tbao_success.list" :headers="listbox.ds_tbao_success.header" flcol="false" v-bind:checkheader="false" lastcoltemplate="0"  @recordChange="grdChanged"></gridview>`

### Thuộc tính:
- `id`: tuỳ đặt
- `flcol`: giá trị true/false, mặc định là false. Khi true thì cột cuối cùng của bảng sẽ freeze trên giao diện grid (luôn hiện và đè lên các cột khác nếu giao diện không đủ hiển thị hết tất cả các cột)
- `checkheader`: giá trị true / false. Khi true thì sẽ hiện THÊM 1 cột chọn ở đầu, gắn mặc định với field `ischecked`
- `recordChange`: là sự kiện được emit khi chọn 1 dòng bất kỳ trên grid, tham số trả về là đối tượng được chọn (chỉ 1 đối tượng duy nhất)
- `lastcoltemplate`: là các loại mẫu cột dành cho cột cuối cùng. Hiện tại có 3 loại template cơ bản: 0 là hiện text giá trị bình thường, 1 là hiện view / download, 2 là hiện ô check (giống checkbox nhưng hiện dạng ô tick)
- `datalist`: gắn với danh sách đối tượng trong data
- `height` : default to max-height:400px, else will get value from height. e.g: `:height="'300px'"`
- `headers`: gắn với danh sách đối tượng header trong data. Header này có cấu trúc như sau:  
`[{name:'STT',text:'Số TT'},{name:'SO_TB',text:'Số máy'},{name:'NGAYSINH',text:'Ngày sinh'},]`
Mảng gồm các đối tượng, mỗi đối tượng tương đương 1 header (hay 1 cột trong grid), trong đó:
  - `name`: tên thuộc tính của đối tượng trong list (thuộc tính dữ liệu như thế nào thì điền vậy)
  - `text`: text hiển thị ở tiêu đề cột (Heading của cột)
  - `fieldtype`: có thể chọn loại hiển thị trong các type của input. Với checkbox, true / false value sẽ tương ứng là 1 / 0


