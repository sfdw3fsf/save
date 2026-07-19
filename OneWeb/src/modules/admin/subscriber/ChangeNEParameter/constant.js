// export const config = [
//   {
//     label: ' Số Ne',
//     filterAble: true,
//     key: 'sone'
//   },
//   {
//     label: 'Số máy',
//     filterAble: true,
//     key: 'ds_matb'
//   },
//   {
//     label: 'Port MDF',
//     filterAble: true,
//     key: 'port_mdf'
//   },
//   {
//     label: 'Toạ độ MDF',
//     filterAble: true,
//     key: 'toado_mdf'
//   }
// ]
export const config = [
  { fieldName: 'sone', headerText: 'Số Ne', allowFiltering: true },
  {
    fieldName: 'ds_matb',
    headerText: 'Số máy',
    allowFiltering: true
  },
  {
    fieldName: 'port_mdf',
    headerText: 'Port MDF',
    allowFiltering: true,
    allowHtml: true
  },
  {
    fieldName: 'toado_mdf',
    headerText: 'Toạ độ MDF',
    allowFiltering: true,
    allowHtml: true
  }
]