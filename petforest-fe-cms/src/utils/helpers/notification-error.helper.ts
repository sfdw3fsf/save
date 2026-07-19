import { AxiosError } from 'axios';
import { toast } from 'react-toastify';
import { MESSAGE } from '../constants/message/message.constant';

export function handleError(error: AxiosError | string) {
  if (typeof error === 'string') toast.error(error);
  else if (typeof error?.message === 'string' && !error.isAxiosError) toast.error(error.message);
  else if (!error.isAxiosError) toast.error('문제가 발생했습니다.');
  else if (error.response) {
    const data: any = error.response.data;
    if (error.response.status >= 500) {
      toast.error('Server error');
    } else if (error.response.status === 409) {
      if (MESSAGE[(error.response.data as any).data.action as keyof typeof MESSAGE]) {
        toast.error(MESSAGE[(error.response.data as any).data.action as keyof typeof MESSAGE]);
      } else {
        toast.error('이름이 비어있는 행 부터 내용을 채워야 합니다.');
      }
    } else if (error.response.status === 422) {
      toast.error('데이터를 다시 확인해 주세요.');
    } else if (Array.isArray(data?.data))
      toast.error(data?.data.map((m: { field: string; message: string }) => m.field + m.message).join(', '));
    else if (typeof data?.message === 'string') {
      toast.error(data?.message);
    } else if (data?.data && 'message' in data.data) toast.error(data.data.message);
    else toast.error('문제가 발생했습니다.');
  } else if (error.message) toast.error(error.message);
  else toast.error('문제가 발생했습니다.');
}
