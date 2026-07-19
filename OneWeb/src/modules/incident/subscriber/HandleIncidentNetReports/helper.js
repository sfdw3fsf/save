import moment from 'moment'
import XLSX from 'xlsx'
import _ from 'lodash'

const helper = {


  exportExcel(arrData, nameReport) {
    const stdEx = moment().format('yyyymmddHHss')
    const data = XLSX.utils.json_to_sheet(arrData)
    const wb = XLSX.utils.book_new()
    XLSX.utils.book_append_sheet(wb, data, `Data`)
    var name = nameReport + `_${stdEx}` + '.xlsx'
    XLSX.writeFile(wb, name)
  },

  convertDateToString(date) {
    if (!date) return ''
    let parts = String(date).split('/')
    let str = parts[2] + '/' + parts[1] + '/' + parts[0]
    return str
  },

  strDDMMYYYY2YYYYMMDD(dtStr) {
    let parts_of_date = dtStr.split('/')
    return parts_of_date[2] + parts_of_date[1] + parts_of_date[0]
  },

  getDayPast(daysToSubtract) {
    const currentDate = new Date()
    const resultDate = new Date(currentDate)

    resultDate.setDate(currentDate.getDate() - daysToSubtract)

    return resultDate
  },
  downloadURI(uri, name) {
    var link = document.createElement('a')
    // If you don't know the name or want to use
    // the webserver default set name = ''
    link.setAttribute('download', name);
    link.href = uri;
    link.target = '_blank'; 
    document.body.appendChild(link);
    link.click();
    link.remove();
    } ,
   async downloadFile(url, filename) {
    const response = await fetch(url);
    
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    
    const contentLength = response.headers.get('content-length');
    const contentType = response.headers.get('content-type');
    
    const downloadStream = response.body;
    const reader = downloadStream.getReader();
    
    let receivedBytes = 0;
    const chunks = [];
    
    while (true) {
      const { done, value } = await reader.read();
      
      if (done) {
        break;
      }
      
      chunks.push(value);
      receivedBytes += value.length;
      
      // Calculate progress
      const progress = receivedBytes / contentLength;
      console.log(`Download progress: ${(progress * 100).toFixed(2)}%`);
    }
    
    // Combine chunks into a single Uint8Array
    const fileData = new Uint8Array(receivedBytes);
    let offset = 0;
    for (const chunk of chunks) {
      fileData.set(chunk, offset);
      offset += chunk.length;
    }
    
    // Create a Blob from the Uint8Array
    const blob = new Blob([fileData], { type: contentType });
    
    // Create a temporary download link and trigger the download
    const downloadLink = document.createElement('a');
    downloadLink.href = URL.createObjectURL(blob);
    downloadLink.download = filename;
    downloadLink.click();
    
    // Clean up
    URL.revokeObjectURL(downloadLink.href);
  }
}
export default helper
