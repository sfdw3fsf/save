import { AxiosResponse } from 'axios';

export const downloadExcel = (resp: AxiosResponse<Blob, any>, filename: string, type = 'xlsx') => {
  const url = window.URL.createObjectURL(new Blob([resp.data]));
  const link = document.createElement('a');
  link.href = url;
  link.setAttribute('download', `${filename}.${type}`);
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
  window.URL.revokeObjectURL(url);
};
